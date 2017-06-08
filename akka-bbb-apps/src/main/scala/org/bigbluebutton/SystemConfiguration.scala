package org.bigbluebutton

import com.typesafe.config.ConfigFactory
import scala.util.Try

trait SystemConfiguration {

  val config = ConfigFactory.load()

  lazy val redisHost = Try(config.getString("redis.host")).getOrElse("127.0.0.1")
  lazy val redisPort = Try(config.getInt("redis.port")).getOrElse(6379)
  lazy val redisPassword = Try(config.getString("redis.password")).getOrElse("")
  lazy val httpInterface = Try(config.getString("http.interface")).getOrElse("")
  lazy val httpPort = Try(config.getInt("http.port")).getOrElse(9090)
  lazy val bbbWebHost = Try(config.getString("services.bbbWebHost")).getOrElse("localhost")
  lazy val bbbWebPort = Try(config.getInt("services.bbbWebPort")).getOrElse(8888)
  lazy val bbbWebAPI = Try(config.getString("services.bbbWebAPI")).getOrElse("localhost")
  lazy val bbbWebSharedSecret = Try(config.getString("services.sharedSecret")).getOrElse("changeme")
  lazy val bbbWebModeratorPassword = Try(config.getString("services.moderatorPassword")).getOrElse("changeme")
  lazy val bbbWebViewerPassword = Try(config.getString("services.viewerPassword")).getOrElse("changeme")
  lazy val keysExpiresInSec = Try(config.getInt("redis.keyExpiry")).getOrElse(14 * 86400) // 14 days
  lazy val red5DeskShareIP = Try(config.getString("red5.deskshareip")).getOrElse("127.0.0.1")
  lazy val red5DeskShareApp = Try(config.getString("red5.deskshareapp")).getOrElse("")

  lazy val inactivityDeadline = Try(config.getInt("inactivity.deadline")).getOrElse(2 * 3600) // 2 hours
  lazy val inactivityTimeLeft = Try(config.getInt("inactivity.timeLeft")).getOrElse(5 * 60) // 5 minutes

  lazy val expireLastUserLeft = Try(config.getInt("expire.lastUserLeft")).getOrElse(60) // 1 minute
  lazy val expireNeverJoined = Try(config.getInt("expire.neverJoined")).getOrElse(5 * 60) // 5 minutes

  lazy val meetingManagerChannel = Try(config.getString("eventBus.meetingManagerChannel")).getOrElse("MeetingManagerChannel")
  lazy val outMessageChannel = Try(config.getString("eventBus.outMessageChannel")).getOrElse("OutgoingMessageChannel")
  lazy val incomingJsonMsgChannel = Try(config.getString("eventBus.incomingJsonMsgChannel")).getOrElse("IncomingJsonMsgChannel")
  lazy val outBbbMsgMsgChannel = Try(config.getString("eventBus.outBbbMsgMsgChannel")).getOrElse("OutBbbMsgChannel")
  lazy val recordServiceMessageChannel = Try(config.getString("eventBus.recordServiceMessageChannel")).getOrElse("RecordServiceMessageChannel")

  lazy val toAkkaAppsRedisChannel = Try(config.getString("redis.toAkkaAppsRedisChannel")).getOrElse("to-akka-apps-redis-channel")
  lazy val fromAkkaAppsRedisChannel = Try(config.getString("redis.fromAkkaAppsRedisChannel")).getOrElse("from-akka-apps-redis-channel")
  lazy val fromAkkaAppsChannel = Try(config.getString("eventBus.fromAkkaAppsChannel")).getOrElse("from-akka-apps-channel")
  lazy val toAkkaAppsChannel = Try(config.getString("eventBus.toAkkaAppsChannel")).getOrElse("to-akka-apps-channel")
  lazy val fromClientChannel = Try(config.getString("eventBus.fromClientChannel")).getOrElse("from-client-channel")
  lazy val toClientChannel = Try(config.getString("eventBus.toClientChannel")).getOrElse("to-client-channel")
  lazy val toAkkaAppsJsonChannel = Try(config.getString("eventBus.toAkkaAppsChannel")).getOrElse("to-akka-apps-json-channel")
  lazy val fromAkkaAppsJsonChannel = Try(config.getString("eventBus.fromAkkaAppsChannel")).getOrElse("from-akka-apps-json-channel")
  lazy val fromAkkaAppsOldJsonChannel = Try(config.getString("eventBus.fromAkkaAppsOldChannel")).getOrElse("from-akka-apps-old-json-channel")

  lazy val eslHost = Try(config.getString("freeswitch.esl.host")).getOrElse("127.0.0.1")
  lazy val eslPort = Try(config.getInt("freeswitch.esl.port")).getOrElse(8021)
  lazy val eslPassword = Try(config.getString("freeswitch.esl.password")).getOrElse("ClueCon")
  lazy val fsProfile = Try(config.getString("freeswitch.conf.profile")).getOrElse("cdquality")

}
