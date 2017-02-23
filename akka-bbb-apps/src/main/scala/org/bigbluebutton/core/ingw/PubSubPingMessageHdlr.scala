package org.bigbluebutton.core.ingw

import org.bigbluebutton.core.api.PubSubPing
import org.bigbluebutton.core.bus.{ BigBlueButtonEvent, IncomingEventBus }

trait PubSubPingMessageHdlr {

  val eventBus: IncomingEventBus

  def handle(msg: PubSubPingMessage): Unit = {
    eventBus.publish(
      BigBlueButtonEvent(
        "meeting-manager",
        new PubSubPing(msg.payload.system, msg.payload.timestamp)))
  }
}
