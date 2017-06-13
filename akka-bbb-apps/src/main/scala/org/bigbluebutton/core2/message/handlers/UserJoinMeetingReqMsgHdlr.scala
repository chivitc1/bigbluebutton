package org.bigbluebutton.core2.message.handlers

import org.bigbluebutton.common2.messages._
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.GuestPolicy
import org.bigbluebutton.core.models.{ RegisteredUsers, UserState, Users2x, UsersState }
import org.bigbluebutton.core.running.MeetingActor
import org.bigbluebutton.core2.MeetingStatus2x

trait UserJoinMeetingReqMsgHdlr {
  this: MeetingActor =>

  val outGW: OutMessageGateway

  def handle(msg: UserJoinMeetingReqMsg): Unit = {
    log.warning("Received user joined voice conference " + msg)

    def broadcastEvent(userState: UserState): Unit = {
      val routing = Routing.addMsgToClientRouting(MessageTypes.BROADCAST_TO_MEETING, props.meetingProp.intId, userState.intId)
      val envelope = BbbCoreEnvelope(UserJoinedMeetingEvtMsg.NAME, routing)
      val header = BbbClientMsgHeader(UserJoinedMeetingEvtMsg.NAME, props.meetingProp.intId, userState.intId)

      val body = UserJoinedMeetingEvtMsgBody(intId = userState.intId, extId = userState.extId, name = userState.name,
        role = userState.role, guest = userState.guest, authed = userState.authed,
        waitingForAcceptance = userState.waitingForAcceptance, emoji = userState.emoji,
        presenter = userState.presenter, locked = userState.locked, avatar = userState.avatar)

      val event = UserJoinedMeetingEvtMsg(header, body)
      val msgEvent = BbbCommonEnvCoreMsg(envelope, event)
      outGW.send(msgEvent)

      record(event)
    }

    for {
      regUser <- RegisteredUsers.findWithToken(msg.body.authToken, liveMeeting.registeredUsers)
    } yield {
      val waitingForAcceptance = regUser.guest &&
        MeetingStatus2x.getGuestPolicy(liveMeeting.status) == GuestPolicy.ASK_MODERATOR && regUser.waitingForAcceptance
      val lockStatus = getInitialLockStatus(regUser.role)

      val userState = UserState(intId = regUser.id, extId = regUser.externId, name = regUser.name, role = regUser.role,
        guest = regUser.guest, authed = regUser.authed, waitingForAcceptance = waitingForAcceptance,
        emoji = "none", presenter = false, locked = false, avatar = regUser.avatarURL)
      Users2x.add(liveMeeting.users2x, regUser.id)
      UsersState.add(liveMeeting.usersState, userState)
      broadcastEvent(userState)
    }

  }
}
