/*
 * $Id: ProjectReportMonthlyAnnualWrapper.java,v 1.1 2011/09/10 05:49:55 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.preport.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.preport.data.ProjectReportMonthlyAnnual;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportMonthlyAnnualWrapper.java,v 1.1 2011/09/10 05:49:55 cvs Exp $
 */
public class ProjectReportMonthlyAnnualWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectReportMonthlyAnnual projectReportAnnual = new ProjectReportMonthlyAnnual();
			projectReportAnnual.setYear(rs.getString("year"));
			projectReportAnnual.setDept(rs.getString("dept"));
			projectReportAnnual.setDeptName(rs.getString("deptName"));
			projectReportAnnual.setJobClass(rs.getString("jobClass"));
			projectReportAnnual.setJobClassName(rs.getString("jobClassName"));
			projectReportAnnual.setMemberCnt01(rs.getInt("memberCnt01"));
			projectReportAnnual.setMemberCnt02(rs.getInt("memberCnt02"));
			projectReportAnnual.setMemberCnt03(rs.getInt("memberCnt03"));
			projectReportAnnual.setMemberCnt04(rs.getInt("memberCnt04"));
			projectReportAnnual.setMemberCnt05(rs.getInt("memberCnt05"));
			projectReportAnnual.setMemberCnt06(rs.getInt("memberCnt06"));
			projectReportAnnual.setMemberCnt07(rs.getInt("memberCnt07"));
			projectReportAnnual.setMemberCnt08(rs.getInt("memberCnt08"));
			projectReportAnnual.setMemberCnt09(rs.getInt("memberCnt09"));
			projectReportAnnual.setMemberCnt10(rs.getInt("memberCnt10"));
			projectReportAnnual.setMemberCnt11(rs.getInt("memberCnt11"));
			projectReportAnnual.setMemberCnt12(rs.getInt("memberCnt12"));
			projectReportAnnual.setPreportCnt01(rs.getInt("preportCnt01"));
			projectReportAnnual.setPreportCnt02(rs.getInt("preportCnt02"));
			projectReportAnnual.setPreportCnt03(rs.getInt("preportCnt03"));
			projectReportAnnual.setPreportCnt04(rs.getInt("preportCnt04"));
			projectReportAnnual.setPreportCnt05(rs.getInt("preportCnt05"));
			projectReportAnnual.setPreportCnt06(rs.getInt("preportCnt06"));
			projectReportAnnual.setPreportCnt07(rs.getInt("preportCnt07"));
			projectReportAnnual.setPreportCnt08(rs.getInt("preportCnt08"));
			projectReportAnnual.setPreportCnt09(rs.getInt("preportCnt09"));
			projectReportAnnual.setPreportCnt10(rs.getInt("preportCnt10"));
			projectReportAnnual.setPreportCnt11(rs.getInt("preportCnt11"));
			projectReportAnnual.setPreportCnt12(rs.getInt("preportCnt12"));
			return projectReportAnnual;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ProjectReportMonthlyAnnualWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}

}
