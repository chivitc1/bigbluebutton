package org.bigbluebutton.core

import org.bigbluebutton.core.api.{ NewUserPresence2x, RegisterUser2xCommand, TimestampGenerator, ValidateAuthToken }
import org.bigbluebutton.core.domain.{ VoiceConf, Welcome, _ }
import org.bigbluebutton.core.models.{ MeetingStatus => _, _ }

trait MeetingTestFixtures {
  val intMeetingId = IntMeetingId("foo")
  val extMeetingId = ExtMeetingId("bar")
  val meetingName = Name("test-meeting")
  val recorded = Recorded(true)
  val voiceConf = VoiceConf("85115")
  val duration = 120
  val autoStartRecording = false
  val allowStartStopRecording = true
  val maxUsers = 5
  val allowVoiceOnly = false
  val isBreakout = false

  val intUserId = IntUserId("user1")
  val extUserId = ExtUserId("user1")
  val userName = Name("Juan")
  val authToken = AuthToken("LetMeIn!")
  val avatar = Avatar("http://www.gravatar.com/sdsdas")
  val logoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val welcome = Welcome("Hello World!")
  val dialNum1 = DialNumber("6135551234")

  val props: MeetingProperties2x = MeetingProperties2x(intMeetingId,
    extMeetingId,
    meetingName,
    recorded,
    voiceConf,
    duration,
    autoStartRecording,
    allowStartStopRecording,
    TimestampGenerator.generateTimestamp(),
    maxUsers,
    allowVoiceOnly,
    isBreakout)

  val abilities: MeetingPermissions = new MeetingPermissions

  val registeredUsers = new RegisteredUsers2x
  val users = new Users3x
  val chats = new ChatModel
  val layouts = new LayoutModel
  val polls = new PollModel
  val whiteboards = new WhiteboardModel
  val presentations = new PresentationModel
  val breakoutRooms = new BreakoutRoomModel
  val captions = new CaptionModel
  val extension: ExtensionStatus = new ExtensionStatus

  val registerUserCommand = RegisterUser2xCommand(
    intMeetingId, intUserId, userName, Set(ModeratorRole),
    extUserId, authToken, avatar,
    logoutUrl,
    welcome,
    Set(dialNum1),
    Set("config1", "config2"),
    Set("data12", "data2"))

  val validateAuthTokenCommand = new ValidateAuthToken(intMeetingId, intUserId, authToken, "none", "none")

  val userJoinCommand: NewUserPresence2x = new NewUserPresence2x(intMeetingId,
    intUserId,
    authToken,
    SessionId("session-1"),
    PresenceId("presence-1"),
    FlashWebUserAgent)
}