/*
 * $Id: SanctionCountOnProjectWrapper.java,v 1.1 2009/09/27 10:27:36 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 26.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.sanction.statistics.data.SanctionCountOnProject;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class SanctionCountOnProjectWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			SanctionCountOnProject sanctionCountOnProject = new SanctionCountOnProject();
			sanctionCountOnProject.setProjectCode(rs.getString("projectCode"));
			sanctionCountOnProject.setProjectName(rs.getString("projectName"));
			sanctionCountOnProject.setDivCode(rs.getString("runningDivCode"));
			sanctionCountOnProject.setDivName(rs.getString("runningDivName"));
			sanctionCountOnProject.setM0000(rs.getString("m0000"));
			sanctionCountOnProject.setS0000(rs.getString("s0000"));
			sanctionCountOnProject.setDraft11(rs.getString("draft11"));
			sanctionCountOnProject.setDraft14(rs.getString("draft14"));
			sanctionCountOnProject.setR0000(rs.getString("r0000"));
			sanctionCountOnProject.setC0000(rs.getString("c0000"));
			return sanctionCountOnProject;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("SanctionCountOnProjectWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
