/*
 * $Id: ExpertPoolListWrapper.java,v 1.4 2012/11/14 09:27:38 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: ExpertPoolListWrapper.java,v 1.4 2012/11/14 09:27:38 cvs Exp $
 */
public class ExpertPoolListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setName(rs.getString("name"));
			expertPool.setGender(rs.getString("gender"));
			expertPool.setCompany(rs.getString("company"));
			expertPool.setProjectName(rs.getString("projectName"));
			expertPool.setProjectMemberCount(rs.getString("projectMemberCount"));
			expertPool.setEndRate(rs.getString("endRate"));
			expertPool.setModifiedDate(rs.getDate("modifiedDate"));
			expertPool.setFuncField(rs.getString("funcField"));
			expertPool.setConsultingMajor(rs.getString("consultingMajor"));
			try {
				expertPool.setPhoto(rs.getString("photo"));
				expertPool.setJobClass(rs.getString("jobClass"));
				expertPool.setDept(rs.getString("dept"));
				expertPool.setEmail(rs.getString("email"));
				expertPool.setMobileNo(rs.getString("mobileNo"));
			} catch (Exception e) {
			}
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolListWrapper fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}