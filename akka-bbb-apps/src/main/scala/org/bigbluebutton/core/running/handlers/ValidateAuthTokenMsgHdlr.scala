package org.bigbluebutton.core.running.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.{ UserJoining, ValidateAuthToken, ValidateAuthTokenReply }
import org.bigbluebutton.core.models.RegisteredUsers
import org.bigbluebutton.core.running.{ MeetingActor, MeetingStateModel }

trait ValidateAuthTokenMsgHdlr {
  this: MeetingActor =>

  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handleValidateAuthToken(msg: ValidateAuthToken): Unit = {

    RegisteredUsers.findWithToken(msg.token, state.registeredUsers.toVector) match {
      case Some(u) =>
        val replyTo = state.mProps.meetingID + '/' + msg.userId

        //send the reply
        outGW.send(new ValidateAuthTokenReply(state.mProps.meetingID, msg.userId, msg.token, true, msg.correlationId))

        //join the user
        handle(new UserJoining(state.mProps.meetingID, msg.userId, msg.token))

      case None =>
        outGW.send(new ValidateAuthTokenReply(state.mProps.meetingID, msg.userId, msg.token, false, msg.correlationId))
    }

  }
}
