package org.bigbluebutton.common.messages;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SendPublicChatMessage implements IBigBlueButtonMessage {
  public static final String SEND_PUBLIC_CHAT_MESSAGE = "send_public_chat_message";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String requesterId;
  public final Map<String, String> messageInfo;

  public SendPublicChatMessage(String meetingId, String requesterId,
                               Map<String, String> message) {
    this.meetingId = meetingId;
    this.requesterId = requesterId;
    this.messageInfo = message;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();

    Map<String, String> message = new HashMap<String, String>();

    message.put(ChatKeyUtil.CHAT_TYPE, messageInfo.get(ChatKeyUtil.CHAT_TYPE));
    message.put(ChatKeyUtil.MESSAGE, messageInfo.get(ChatKeyUtil.MESSAGE));
    message.put(ChatKeyUtil.TO_USERNAME, messageInfo.get(ChatKeyUtil.TO_USERNAME));
    message.put(ChatKeyUtil.FROM_TZ_OFFSET, messageInfo.get(ChatKeyUtil.FROM_TZ_OFFSET));
    message.put(ChatKeyUtil.FROM_COLOR, messageInfo.get(ChatKeyUtil.FROM_COLOR));
    message.put(ChatKeyUtil.TO_USERID, messageInfo.get(ChatKeyUtil.TO_USERID));
    message.put(ChatKeyUtil.FROM_USERID, messageInfo.get(ChatKeyUtil.FROM_USERID));
    message.put(ChatKeyUtil.FROM_TIME, messageInfo.get(ChatKeyUtil.FROM_TIME));
    message.put(ChatKeyUtil.FROM_USERNAME, messageInfo.get(ChatKeyUtil.FROM_USERNAME));

    payload.put(MessageBodyConstants.MESSAGE, message);
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(SEND_PUBLIC_CHAT_MESSAGE, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static SendPublicChatMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (SEND_PUBLIC_CHAT_MESSAGE.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.MESSAGE)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();

            JsonObject msgObj = (JsonObject) payload.get(MessageBodyConstants.MESSAGE).getAsJsonObject();
            Map<String, String> messageInfo = new HashMap<String, String>();

            if (msgObj.has(ChatKeyUtil.CHAT_TYPE)
                    && msgObj.has(ChatKeyUtil.MESSAGE)
                    && msgObj.has(ChatKeyUtil.TO_USERNAME)
                    && msgObj.has(ChatKeyUtil.FROM_TZ_OFFSET)
                    && msgObj.has(ChatKeyUtil.FROM_COLOR)
                    && msgObj.has(ChatKeyUtil.TO_USERID)
                    && msgObj.has(ChatKeyUtil.FROM_USERID)
                    && msgObj.has(ChatKeyUtil.FROM_TIME)
                    && msgObj.has(ChatKeyUtil.FROM_USERNAME)) {
              messageInfo.put(ChatKeyUtil.CHAT_TYPE, msgObj.get(ChatKeyUtil.CHAT_TYPE).getAsString());
              messageInfo.put(ChatKeyUtil.MESSAGE, msgObj.get(ChatKeyUtil.MESSAGE).getAsString());
              messageInfo.put(ChatKeyUtil.TO_USERNAME, msgObj.get(ChatKeyUtil.TO_USERNAME).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_TZ_OFFSET, msgObj.get(ChatKeyUtil.FROM_TZ_OFFSET).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_COLOR, msgObj.get(ChatKeyUtil.FROM_COLOR).getAsString());
              messageInfo.put(ChatKeyUtil.TO_USERID, msgObj.get(ChatKeyUtil.TO_USERID).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_USERID, msgObj.get(ChatKeyUtil.FROM_USERID).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_TIME, msgObj.get(ChatKeyUtil.FROM_TIME).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_USERNAME, msgObj.get(ChatKeyUtil.FROM_USERNAME).getAsString());

              String requesterId = messageInfo.get(ChatKeyUtil.FROM_USERID);

              return new SendPublicChatMessage(meetingId, requesterId, messageInfo);
            } else if (msgObj.has(MessageBodyConstants.CHAT_TYPE)
                    && msgObj.has(MessageBodyConstants.MESSAGE)
                    && msgObj.has(MessageBodyConstants.TO_USERNAME)
                    && msgObj.has(MessageBodyConstants.FROM_TZ_OFFSET)
                    && msgObj.has(MessageBodyConstants.FROM_COLOR)
                    && msgObj.has(MessageBodyConstants.TO_USERID)
                    && msgObj.has(MessageBodyConstants.FROM_USERID)
                    && msgObj.has(MessageBodyConstants.FROM_TIME)
                    && msgObj.has(MessageBodyConstants.FROM_USERNAME)) {
              messageInfo.put(ChatKeyUtil.CHAT_TYPE, msgObj.get(MessageBodyConstants.CHAT_TYPE).getAsString());
              messageInfo.put(ChatKeyUtil.MESSAGE, msgObj.get(MessageBodyConstants.MESSAGE).getAsString());
              messageInfo.put(ChatKeyUtil.TO_USERNAME, msgObj.get(MessageBodyConstants.TO_USERNAME).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_TZ_OFFSET, msgObj.get(MessageBodyConstants.FROM_TZ_OFFSET).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_COLOR, msgObj.get(MessageBodyConstants.FROM_COLOR).getAsString());
              messageInfo.put(ChatKeyUtil.TO_USERID, msgObj.get(MessageBodyConstants.TO_USERID).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_USERID, msgObj.get(MessageBodyConstants.FROM_USERID).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_TIME, msgObj.get(MessageBodyConstants.FROM_TIME).getAsString());
              messageInfo.put(ChatKeyUtil.FROM_USERNAME, msgObj.get(MessageBodyConstants.FROM_USERNAME).getAsString());

              String requesterId = messageInfo.get(ChatKeyUtil.FROM_USERID);

              return new SendPublicChatMessage(meetingId, requesterId, messageInfo);
            }
          }
        }
      }
    }
    return null;
  }
}
