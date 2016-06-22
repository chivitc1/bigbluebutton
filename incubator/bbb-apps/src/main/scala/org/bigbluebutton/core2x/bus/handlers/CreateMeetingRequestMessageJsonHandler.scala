package org.bigbluebutton.core2x.bus.handlers

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMessage.CreateMeetingRequestInMessage
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain._
import org.bigbluebutton.messages.CreateMeetingRequestMessage
import org.bigbluebutton.messages.vo.{ ExtensionPropertiesBody, MeetingPropertiesBody, RecordingPropertiesBody }

trait CreateMeetingRequestMessageJsonHandler extends UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {

    def extractRecordingProperties(recProps: RecordingPropertiesBody): Option[MeetingRecordingProp] = {
      for {
        recorded <- Option(recProps.recorded)
        autoStartRecording <- Option(recProps.autoStartRecording)
        allowStartStopRecording <- Option(recProps.allowStartStopRecording)
      } yield new MeetingRecordingProp(Recorded(recorded), autoStartRecording, allowStartStopRecording)
    }

    def extractExtensionProperties(props: ExtensionPropertiesBody): Option[MeetingExtensionProp2x] = {
      for {
        maxExtensions <- Option(props.maxExtensions)
        extendByMinutes <- Option(props.extendByMinutes)
        sendNotice <- Option(props.sendNotice)
      } yield new MeetingExtensionProp2x(maxExtensions, extendByMinutes, sendNotice)
    }

    def extractMeetingProperties(props: MeetingPropertiesBody,
      extProps: ExtensionPropertiesBody,
      recProps: RecordingPropertiesBody): Option[MeetingProperties2x] = {
      for {
        recordingProps <- extractRecordingProperties(recProps)
        extensionProps <- extractExtensionProperties(extProps)
        id <- Option(props.id)
        extId <- Option(props.externalId)
        name <- Option(props.name)
        voiceConf <- Option(props.voiceConf)
        duration <- Option(props.duration)
        maxUsers <- Option(props.maxUsers)
        allowVoiceOnly <- Option(props.allowVoiceOnly)
        isBreakout <- Option(props.isBreakout)
      } yield new MeetingProperties2x(
        IntMeetingId(id),
        ExtMeetingId(extId),
        Name(name),
        VoiceConf(voiceConf),
        duration,
        maxUsers,
        allowVoiceOnly,
        isBreakout,
        extensionProps,
        recordingProps)
    }

    def publish(props: MeetingProperties2x): Unit = {
      eventBus.publish(
        BigBlueButtonInMessage("meeting-manager", new CreateMeetingRequestInMessage(props.id, props)))
    }

    if (msg.name == CreateMeetingRequestMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = CreateMeetingRequestMessage.fromJson(msg.data)
      for {
        props <- Option(m.body.props)
        extProps <- Option(props.extensionProp)
        recProps <- Option(props.recordingProp)
        mProps <- extractMeetingProperties(props, extProps, recProps)
      } yield publish(mProps)
    } else {
      super.handleReceivedJsonMessage(msg)
    }
  }
}
