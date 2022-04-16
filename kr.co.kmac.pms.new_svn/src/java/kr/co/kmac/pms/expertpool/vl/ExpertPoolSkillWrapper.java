/*
 * $Id: ExpertPoolSkillWrapper.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 2. 23.
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
 * @author CHO DAE HYON
 * @version $Id: ExpertPoolSkillWrapper.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public class ExpertPoolSkillWrapper implements ObjectWrapper {
	
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		 try {
			 ExpertPool data = new ExpertPool();
			 data.setGubun(rs.getString("gubun"));
			 data.setTotal(rs.getString("total"));
			 data.setEmploy(rs.getString("employ"));
			 data.setContractEmploy(rs.getString("contractEmploy"));
			 data.setConsultant(rs.getString("consultant"));
			 data.setIndustryExpert(rs.getString("industryExpert"));
			 data.setProfessor(rs.getString("professor"));
			 data.setBusinessExpert(rs.getString("businessExpert"));
			 data.setEtcEmploy(rs.getString("etcEmploy"));
			 return data;					
		 } catch (SQLException e) {
			 throw exceptionTranslator.translate("SearchCategoryListWrapper fails", null, e);
		 }
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
