/**
 * BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
 * <p>
 * Copyright (c) 2012 BigBlueButton Inc. and by respective authors (see below).
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

import java.util.ArrayList;
import java.util.List;

import static org.bigbluebutton.presentation.FileTypeConstants.*;

@SuppressWarnings("serial")
public final class SupportedFileTypes {
    // Set as private to prevent instantiation
    private SupportedFileTypes() {
    }

    private static final List<String> SUPPORTED_FILE_LIST = new ArrayList<String>(15) {
        {
            // Add all the supported files
            add(XLS);
            add(XLSX);
            add(DOC);
            add(DOCX);
            add(PPT);
            add(PPTX);
            add(ODT);
            add(RTF);
            add(TXT);
            add(ODS);
            add(ODP);
            add(PDF);
            add(JPG);
            add(JPEG);
            add(PNG);
        }
    };

    private static final List<String> OFFICE_FILE_LIST = new ArrayList<String>(11) {
        {
            // Add all Offile file types
            add(XLS);
            add(XLSX);
            add(DOC);
            add(DOCX);
            add(PPT);
            add(PPTX);
            add(ODT);
            add(RTF);
            add(TXT);
            add(ODS);
            add(ODP);
        }
    };

    private static final List<String> IMAGE_FILE_LIST = new ArrayList<String>(3) {
        {
            // Add all image file types
            add(JPEG);
            add(JPG);
            add(PNG);
        }
    };

    /*
     * Returns if the file with extension is supported.
     */
    public static boolean isFileSupported(String fileExtension) {
        return SUPPORTED_FILE_LIST.contains(fileExtension.toLowerCase());
    }

    /*
     * Returns if the office file is supported.
     */
    public static boolean isOfficeFile(String fileExtension) {
        return OFFICE_FILE_LIST.contains(fileExtension.toLowerCase());
    }

    public static boolean isPdfFile(String fileExtension) {
        return "pdf".equalsIgnoreCase(fileExtension);
    }

    /*
     * Returns if the iamge file is supported
     */
    public static boolean isImageFile(String fileExtension) {
        return IMAGE_FILE_LIST.contains(fileExtension.toLowerCase());
    }
}