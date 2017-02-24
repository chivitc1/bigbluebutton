package org.bigbluebutton.common.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MessageFromJsonConverter {

	public static IBigBlueButtonMessage convert(String message) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(message);
		
		if (obj.has("header") && obj.has("payload")) {
			JsonObject header = (JsonObject) obj.get("header");
			JsonObject payload = (JsonObject) obj.get("payload");
			
			if (header.has("name")) {
				String messageName = header.get("name").getAsString();
				switch (messageName) {
				  case CreateMeetingMessage.CREATE_MEETING_REQUEST_EVENT:
					  return processCreateMeeting(payload);
				  case ValidateAuthTokenMessage.VALIDATE_AUTH_TOKEN:
					  return processValidateAuthTokenMessage(header, payload);
					  // return ValidateAuthTokenMessage.fromJson(message);
				  case UserConnectedToGlobalAudio.USER_CONNECTED_TO_GLOBAL_AUDIO:
					return UserConnectedToGlobalAudio.fromJson(message);
				  case UserDisconnectedFromGlobalAudio.USER_DISCONNECTED_FROM_GLOBAL_AUDIO:
					return UserDisconnectedFromGlobalAudio.fromJson(message);
				  case GetAllMeetingsRequest.GET_ALL_MEETINGS_REQUEST_EVENT:
					return new GetAllMeetingsRequest("the_string_is_not_used_anywhere");
				}
			}
		}
		return null;
	}
		
	private static IBigBlueButtonMessage processValidateAuthTokenMessage(JsonObject header, JsonObject payload) {
		String id = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
		String userid = payload.get(MessageBodyConstants.USER_ID).getAsString();
		String authToken = payload.get(MessageBodyConstants.AUTH_TOKEN).getAsString();
		String replyTo = header.get(MessageBodyConstants.REPLY_TO).getAsString();
		String sessionId = "tobeimplemented";
		return new ValidateAuthTokenMessage(id, userid, authToken, replyTo,
		    sessionId);
	}
	
	private static IBigBlueButtonMessage processCreateMeeting(JsonObject payload) {
		String id = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
		String externalId = payload.get(MessageBodyConstants.EXTERNAL_MEETING_ID).getAsString();
		String name = payload.get(MessageBodyConstants.NAME).getAsString();
		Boolean record = payload.get(MessageBodyConstants.RECORDED).getAsBoolean();
		String voiceBridge = payload.get(MessageBodyConstants.VOICE_CONF).getAsString();
		Long duration = payload.get(MessageBodyConstants.DURATION).getAsLong();
		Boolean autoStartRecording = payload.get(MessageBodyConstants.AUTO_START_RECORDING).getAsBoolean();
		Boolean allowStartStopRecording = payload.get(MessageBodyConstants.ALLOW_START_STOP_RECORDING).getAsBoolean();
		Boolean webcamsOnlyForModerator = payload.get(MessageBodyConstants.WEBCAMS_ONLY_FOR_MODERATOR).getAsBoolean();
		String moderatorPassword = payload.get(MessageBodyConstants.MODERATOR_PASS).getAsString();
		String viewerPassword = payload.get(MessageBodyConstants.VIEWER_PASS).getAsString();
		Long createTime = payload.get(MessageBodyConstants.CREATE_TIME).getAsLong();
		String createDate = payload.get(MessageBodyConstants.CREATE_DATE).getAsString();
		
		return new CreateMeetingMessage(id, externalId, name, record, voiceBridge, 
				          duration, autoStartRecording, allowStartStopRecording,
				          webcamsOnlyForModerator, moderatorPassword, viewerPassword,
				          createTime, createDate);
	}
	


}
