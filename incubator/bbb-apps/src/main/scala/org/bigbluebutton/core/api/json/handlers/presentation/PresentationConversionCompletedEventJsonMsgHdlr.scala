package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.{ BigBlueButtonInMessage, IncomingEventBus2x }
import org.bigbluebutton.core.api.IncomingMsg.PresentationConversionCompletedEventInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.apps.presentation.{ Presentation }
import org.bigbluebutton.core.api.json.{ InHeaderAndJsonBody }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.core.domain.IntMeetingId
import org.bigbluebutton.messages.presentation.PresentationConversionCompletedEventMessage

trait PresentationConversionCompletedEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {

  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, messageKey: String, code: String, presentation: Presentation): Unit = {
      log.debug(s"Publishing ${msg.header.name} [ ${presentation.id} $code]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PresentationConversionCompletedEventInMessage(meetingId, messageKey, code,
            presentation)))
    }

    if (msg.header.name == PresentationConversionCompletedEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}

