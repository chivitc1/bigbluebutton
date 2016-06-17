/**
 * BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
 * <p>
 * Copyright (c) 2016 BigBlueButton Inc. and by respective authors (see below).
 * <p>
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 * <p>
 * BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along
 * with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
 */

package org.bigbluebutton.presentation;

import com.google.gson.Gson;
import org.bigbluebutton.common.messages.MessagingConstants;
import org.bigbluebutton.pubsub.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class OfficeToPdfConversionSuccessFilter {
    private static Logger log = LoggerFactory.getLogger(OfficeToPdfConversionSuccessFilter.class);

    private final MessageSender messageSender;

    public OfficeToPdfConversionSuccessFilter(MessageSender m) {
        messageSender = m;
    }

    public boolean didConversionSucceed(UploadedPresentation pres) {
        notifyProgressListener(pres);
        return pres.isLastStepSuccessful();
    }

    private void notifyProgressListener(UploadedPresentation pres) {
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("conference", pres.getMeetingId());
        msg.put("room", pres.getMeetingId());
        msg.put("returnCode", "CONVERT");
        msg.put("presentationId", pres.getId());
        msg.put("presentationName", pres.getId());
        msg.put("filename", pres.getName());

        if (pres.isLastStepSuccessful()) {
            log.info("Notifying of OFFICE_DOC_CONVERSION_SUCCESS for " + pres.getUploadedFile().getAbsolutePath());
            msg.put("message", "Office document successfully converted.");
            msg.put("messageKey", "OFFICE_DOC_CONVERSION_SUCCESS");
        } else {
            log.info("Notifying of OFFICE_DOC_CONVERSION_FAILED for " + pres.getUploadedFile().getAbsolutePath());
            msg.put("message", "Failed to convert Office document.");
            msg.put("messageKey", "OFFICE_DOC_CONVERSION_FAILED");
        }

        sendNotification(msg);
    }

    private void sendNotification(Map<String, Object> msg) {
        if (messageSender != null) {
            Gson gson = new Gson();
            String updateMsg = gson.toJson(msg);
            log.debug("sending: " + updateMsg);
            messageSender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, updateMsg);
        } else {
            log.warn("MessageSender has not been set!.");
        }
    }
}