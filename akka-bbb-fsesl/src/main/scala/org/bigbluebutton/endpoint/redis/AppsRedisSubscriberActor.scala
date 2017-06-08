package org.bigbluebutton.endpoint.redis

import java.io.PrintWriter
import java.io.StringWriter
import java.net.InetSocketAddress

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.common.converters.FromJsonDecoder
import org.bigbluebutton.common.messages.PubSubPongMessage
import akka.actor.ActorSystem
import akka.actor.OneForOneStrategy
import akka.actor.Props
import akka.actor.SupervisorStrategy.Resume
import freeswitch.pubsub.receivers.RedisMessageReceiver
import redis.actors.RedisSubscriberActor
import redis.api.pubsub.Message
import redis.api.pubsub.PMessage
import redis.api.servers.ClientSetname

object AppsRedisSubscriberActor extends SystemConfiguration {

  val channels = Seq("time")
  val patterns = Seq("bigbluebutton:to-voice-conf:*", "bigbluebutton:from-bbb-apps:*")

  def props(system: ActorSystem, msgReceiver: RedisMessageReceiver): Props =
    Props(classOf[AppsRedisSubscriberActor], system, msgReceiver,
      redisHost, redisPort,
      channels, patterns).withDispatcher("akka.rediscala-subscriber-worker-dispatcher")
}

class AppsRedisSubscriberActor(val system: ActorSystem, msgReceiver: RedisMessageReceiver, redisHost: String,
  redisPort: Int,
  channels: Seq[String] = Nil, patterns: Seq[String] = Nil)
    extends RedisSubscriberActor(new InetSocketAddress(redisHost, redisPort),
      channels, patterns, onConnectStatus = connected => { println(s"connected: $connected") }) with SystemConfiguration {

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case e: Exception => {
      val sw: StringWriter = new StringWriter()
      sw.write("An exception has been thrown on AppsRedisSubscriberActor, exception message [" + e.getMessage() + "] (full stacktrace below)\n")
      e.printStackTrace(new PrintWriter(sw))
      log.error(sw.toString())
      Resume
    }
  }

  val decoder = new FromJsonDecoder()

  var lastPongReceivedOn = 0L
  system.scheduler.schedule(10 seconds, 10 seconds)(checkPongMessage())

  // Set the name of this client to be able to distinguish when doing
  // CLIENT LIST on redis-cli
  write(ClientSetname("BbbFsEslAkkaSub").encodedRequest)

  def checkPongMessage() {
    val now = System.currentTimeMillis()

    if (lastPongReceivedOn != 0 && (now - lastPongReceivedOn > 30000)) {
      log.error("FSESL pubsub error!");
    }
  }

  def onMessage(message: Message) {
    log.debug(s"message received: $message")
  }

  def onPMessage(pmessage: PMessage) {
    //    log.debug(s"pattern message received: $pmessage")

    val msg = decoder.decodeMessage(pmessage.data.utf8String)

    if (msg != null) {
      msg match {
        case m: PubSubPongMessage => {
          if (m.payload.system == "BbbFsESL") {
            lastPongReceivedOn = System.currentTimeMillis()
          }
        }
        case _ => // do nothing
      }
    } else {
      msgReceiver.handleMessage(pmessage.patternMatched, pmessage.channel, pmessage.data.utf8String)
    }

  }

  def handleMessage(msg: String) {
    log.warning("**** TODO: Handle pubsub messages. ****")
  }
}
