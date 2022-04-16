/*
 * $Id: MailContents.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 20.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.mailsender.data;

import java.util.Properties;

public class MailContents {
	private Properties content = null;

	public void setContent(Properties content) {
		this.content = content;
	}

	public String getContentInfo(String key) {
		String result = (String) this.content.get(key);
		return result;
	}
}
