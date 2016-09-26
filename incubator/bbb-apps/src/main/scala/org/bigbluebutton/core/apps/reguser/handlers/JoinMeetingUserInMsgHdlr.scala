package org.bigbluebutton.core.apps.reguser.handlers

import org.bigbluebutton.core.BigBlueButtonInMessage
import org.bigbluebutton.core.api.IncomingMsg.JoinMeetingUserInMsg2x
import org.bigbluebutton.core.api.OutGoingMsg.{ UserSessionTokenAssignedBody, UserSessionTokenAssignedMsg, UserSessionTokenAssignedOutMsg2x }
import org.bigbluebutton.core.api.{ BroadcastMsgType, MsgHeader, OutGoingMsg }
import org.bigbluebutton.core.apps.reguser.RegisteredUserActor
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }

trait JoinMeetingUserInMsgHdlr {
  this: RegisteredUserActor =>

  def handle(msg: JoinMeetingUserInMsg2x): Unit = {
    if (sessionTokens.contains(msg.body.sessionToken)) {
      val header = msg.header.copy(dest = msg.body.meetingId.value)
      val message = msg.copy(header = header)
      inGW.publish(BigBlueButtonInMessage(msg.body.meetingId.value, message))
    } else {
      println(" *** No valid session.")
    }
  }
}
