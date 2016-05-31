/**
* BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
* 
* Copyright (c) 2012 BigBlueButton Inc. and by respective authors (see below).
*
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License as published by the Free Software
* Foundation; either version 3.0 of the License, or (at your option) any later
* version.
* 
* BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
* PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License along
* with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
*
*/
package org.bigbluebutton.core.recorders.events;

import org.bigbluebutton.core.service.recorder.RecordEvent;

public abstract class AbstractWhiteboardRecordEvent extends RecordEvent {
	
	public AbstractWhiteboardRecordEvent() {
		setModule("WHITEBOARD");
	}

	public void setPresentation(String name) {
		eventMap.put("presentation", name);
	}

	public void setPageNumber(String page) {
		/**
		 * Subtract 1 from the page number to be zero-based to be
		 * compatible with 0.81 and earlier. (ralam Sept 2, 2014)
		 */
		Integer num = new Integer(page);
//		System.out.println("WB Page Number real pagenum=[" + num + "] rec pagenum=[" + (num - 1) + "]");
		eventMap.put("pageNumber", new Integer(num - 1).toString());
	}
	
	public void setWhiteboardId(String id) {
		eventMap.put("whiteboardId", id);
	}
}
