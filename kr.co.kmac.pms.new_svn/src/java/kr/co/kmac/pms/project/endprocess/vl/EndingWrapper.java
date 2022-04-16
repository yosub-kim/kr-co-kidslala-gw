/*
 * $Id: EndingWrapper.java,v 1.2 2009/11/01 05:04:38 cvs1 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 2. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.endprocess.data.Ending;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * @author CHO DAE HYON
 * @version $Id: EndingWrapper.java,v 1.2 2009/11/01 05:04:38 cvs1 Exp $
 */
public class EndingWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			Ending data = new Ending(); 
			data.setEndGubun(rs.getString("endGubun"));
			data.setEndBackground(rs.getString("endBackground"));
			data.setEndResult(rs.getString("endResult"));
			data.setEndReview(rs.getString("endReview"));
			data.setEndReason(rs.getString("endReason"));
			data.setEndSuggestion(rs.getString("endSuggestion"));
			data.setEndRate(rs.getString("endRate"));
			data.setBusinessTypeCode(rs.getString("businessTypeCode"));
			data.setProcessTypeCode(rs.getString("processTypeCode"));
			data.setGroupComment(rs.getString("groupComment"));
			data.setCfoComment(rs.getString("cfoComment"));
			data.setCboComment(rs.getString("cboComment"));
			data.setEndTaskState(rs.getString("endTaskState"));
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("EndingWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
