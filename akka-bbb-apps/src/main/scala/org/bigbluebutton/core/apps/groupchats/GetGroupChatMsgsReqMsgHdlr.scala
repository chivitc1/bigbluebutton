package org.bigbluebutton.core.apps.groupchats

import org.bigbluebutton.common2.msgs._
import org.bigbluebutton.core.bus.MessageBus
import org.bigbluebutton.core.domain.MeetingState2x
import org.bigbluebutton.core.running.LiveMeeting

trait GetGroupChatMsgsReqMsgHdlr {
  def handle(msg: GetGroupChatMsgsReqMsg, state: MeetingState2x,
             liveMeeting: LiveMeeting, bus: MessageBus): MeetingState2x = {

    def buildGetGroupChatMsgsRespMsg(meetingId: String, userId: String,
                                     msgs: Vector[GroupChatMessage]): BbbCommonEnvCoreMsg = {
      val routing = Routing.addMsgToClientRouting(MessageTypes.DIRECT, meetingId, userId)
      val envelope = BbbCoreEnvelope(GetGroupChatMsgsRespMsg.NAME, routing)
      val header = BbbClientMsgHeader(GetGroupChatMsgsRespMsg.NAME, meetingId, userId)

      val body = GetGroupChatMsgsRespMsgBody(userId, msgs)
      val event = GetGroupChatMsgsRespMsg(header, body)

      BbbCommonEnvCoreMsg(envelope, event)
    }

    state.groupChats.find(msg.body.chatId) foreach { gc =>
      if (gc.access == GroupChatAccess.PUBLIC || gc.isUserMemberOf(msg.body.requesterId)) {
        val respMsg = buildGetGroupChatMsgsRespMsg(
          liveMeeting.props.meetingProp.intId,
          msg.body.requesterId, gc.msgs.values.toVector
        )
        bus.outGW.send(respMsg)
      }
    }

    state
  }
}