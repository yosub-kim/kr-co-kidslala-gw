/*
 * $Id: ThxExpertPoolListWrapper.java,v 1.1 2014/06/13 00:57:43 cvs Exp $
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
 * 
 * @author jiwoongLee
 * @version $Id: ThxExpertPoolListWrapper.java,v 1.1 2014/06/13 00:57:43 cvs Exp $
 */
public class ThxExpertPoolListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setCompanyId(rs.getString("Gubun"));
			expertPool.setDept(rs.getString("id"));
			expertPool.setDeptName(rs.getString("dept"));
			expertPool.setName(rs.getString("name"));
			expertPool.setUserId(rs.getString("userid"));
			expertPool.setCompanyPosition(rs.getString("companyposition"));
			expertPool.setCompanyPositionName(rs.getString("companypositionname"));
			expertPool.setEmail(rs.getString("email"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ThxExpertPoolListWrapper fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
