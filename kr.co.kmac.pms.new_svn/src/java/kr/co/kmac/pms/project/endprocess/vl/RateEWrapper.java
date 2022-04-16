/*
 * $Id: RateEWrapper.java,v 1.2 2010/06/16 14:29:09 cvs2 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 13.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.endprocess.data.ProjectClose;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * @author CHO DAE HYON
 * @version $Id: RateEWrapper.java,v 1.2 2010/06/16 14:29:09 cvs2 Exp $
 */
public class RateEWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectClose data = new ProjectClose();
			data.setSsn(rs.getString("ssn"));
			data.setName(rs.getString("name"));
			data.setSubject(rs.getString("subject"));
			data.setAnswer1(rs.getString("answer1"));
			data.setAnswer2(rs.getString("answer2"));
			data.setAnswer3(rs.getString("answer3"));
			data.setAnswer4(rs.getString("answer4"));
			data.setEstimateC(rs.getString("estimateC"));
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("RollingWrapper fails", null, e);
		}
	}
	
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}
}
