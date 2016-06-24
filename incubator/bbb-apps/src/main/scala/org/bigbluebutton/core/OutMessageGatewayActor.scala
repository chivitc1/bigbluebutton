package org.bigbluebutton.core

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Props
import org.bigbluebutton.core.api._
import org.bigbluebutton.core.service.recorder.RecorderApplication

object OutMessageGatewayActor {
  def props(meetingId: String, recorder: RecorderApplication, sender: MessageSender): Props =
    Props(classOf[OutMessageGatewayActor], meetingId, recorder, sender)
}

class OutMessageGatewayActor(val meetingId: String, val recorder: RecorderApplication, val msgSender: MessageSender)
    extends Actor with ActorLogging {

  private val recorderActor = context.actorOf(RecorderActor.props(recorder), "recorderActor-" + meetingId)
  private val msgSenderActor = context.actorOf(MessageSenderActor.props(msgSender), "senderActor-" + meetingId)

  def receive = {
    case msg: IOutMessage => {
      msgSenderActor forward msg
      recorderActor forward msg
    }
    case _ => // do nothing
  }

}