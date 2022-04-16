/*
 * $Id: ProjectRollingMonitorWrapper.java,v 1.2 2011/09/27 14:50:16 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 13.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.endprocess.data.ProjectRollingMonitor;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectRollingMonitorWrapper.java,v 1.2 2011/09/27 14:50:16 cvs Exp $
 */
public class ProjectRollingMonitorWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectRollingMonitor data = new ProjectRollingMonitor();
			data.setProjectCode(rs.getString("projectCode"));
			data.setCustomerSeq(rs.getString("customerSeq"));
			data.setProjectName(rs.getString("projectName"));
			data.setRunningDivCode(rs.getString("runningDivCode"));
			data.setRunningDiv(rs.getString("runningDiv"));
			data.setBusinessTypeCode(rs.getString("businessTypeCode"));
			data.setBusinessTypeName(rs.getString("businessTypeName"));
			data.setProjectState(rs.getString("projectState"));
			data.setProjectStateName(rs.getString("projectStateName"));
			data.setRealEndDate(rs.getString("realEndDate"));
			data.setSurveySendDate(rs.getString("surveySendDate"));
			data.setReceiveDate(rs.getString("receiveDate"));
			data.setReceiveEmail(rs.getString("receiveEmail"));
			data.setSurveyEndDate(rs.getString("surveyEndDate"));
			data.setEvalDelayDate(rs.getString("evalDelayDate"));
			try{
				data.setAnswer(rs.getString("answer"));
			}catch(Exception e){
				e.getMessage();
			}
			return data;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectRollingMonitorWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}
}
