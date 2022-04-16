/*
 * $Id: ExpoertPoolZipcodeListWrapper.java,v 1.1 2012/09/07 09:08:26 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 6. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPoolZipCode;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpoertPoolZipcodeListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPoolZipCode data = new ExpertPoolZipCode();
		try {
			data.setSido(rs.getString("sido"));
			data.setGugun(rs.getString("gugun"));
			try {
				data.setDong(rs.getString("dong"));
				data.setRi(rs.getString("ri"));
				data.setBunji(rs.getString("bunji"));
				data.setRoadName(rs.getString("roadName"));
				data.setRoadBunji(rs.getString("roadBunji"));
				data.setPost(rs.getString("post"));
			} catch (Exception e) {
			}
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpoertPoolZipcodeListWrapper fails", null, e);
		}
		return data;
	}

	public void setValueListInfo(ValueListInfo arg0) {

	}

}
