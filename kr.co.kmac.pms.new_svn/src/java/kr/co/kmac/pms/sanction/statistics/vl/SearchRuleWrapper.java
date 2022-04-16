/*
 * $Id: SearchRuleWrapper.java,v 1.1 2010/01/17 10:49:01 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 26.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class SearchRuleWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			return rs.getString("key_1");
		} catch (SQLException e) {
			throw exceptionTranslator.translate("SearchRuleWrapper fails",
					null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
