/*
 * $Id: RateCWrapper.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
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
 * @version $Id: RateCWrapper.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public class RateCWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectClose data = new ProjectClose();
			data.setSsn(rs.getString("ssn"));
			data.setName(rs.getString("name"));
			try{data.setProjectName(rs.getString("projectName"));}catch(Exception e) {}
			data.setAnswer1(rs.getString("answer1"));
			data.setAnswer2(rs.getString("answer2"));
			data.setAnswer3(rs.getString("answer3"));
			data.setAnswer4(rs.getString("answer4"));
			data.setAnswer5(rs.getString("answer5"));
			data.setAnswer6(rs.getString("answer6"));
			data.setAnswer7(rs.getString("answer7")); 
			data.setAnswer8(rs.getString("answer8"));
			data.setAnswer9(rs.getString("answer9"));
			data.setAnswer10(rs.getString("answer10"));
			data.setComment(rs.getString("comment"));
			try{data.setTotal(rs.getString("total"));}catch(Exception e) {}
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("RollingWrapper fails", null, e);
		}
	}
	
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}
}
