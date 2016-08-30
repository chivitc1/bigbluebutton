package org.bigbluebutton.bbbred5apps;

import java.util.Map;

public interface IBigBlueButtonRed5App {
    void userDisconnected(String meetingId, String userId, String sessionId);
    void userConnected(String meetingId, String userId, Boolean muted, Map<String, Boolean>
            lockSettings, String sessionId);

    void validateAuthToken(String meetingId, String userId, String token, String correlationId,
                           String sessionId);

    void handleJsonMessage(String json);



    // Chat
    void getChatHistory(String meetingID, String requesterID, String replyTo);
    void sendPublicMessage(String meetingID, String requesterID, Map<String, String> message);
    void sendPrivateMessage(String meetingID, String requesterID, Map<String, String> message);



    void handleChatMessageFromServer(String message);
}
