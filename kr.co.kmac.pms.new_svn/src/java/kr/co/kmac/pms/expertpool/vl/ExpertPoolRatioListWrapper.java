/*
 * $Id: ExpertPoolRatioListWrapper.java,v 1.1 2010/12/26 14:34:27 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPoolRatio;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPoolRatioListWrapper.java,v 1.1 2010/12/26 14:34:27 cvs Exp $
 */
public class ExpertPoolRatioListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPoolRatio expertPool = new ExpertPoolRatio();
		try {
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setName(rs.getString("name"));
			expertPool.setDept(rs.getString("dept"));
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setConsultingFunction(rs.getString("consultingFunction"));
			expertPool.setConsultingMajor(rs.getString("consultingMajor"));
			expertPool.setRunningRatio(rs.getString("runningRatio"));

		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolRatioListWrapper fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}