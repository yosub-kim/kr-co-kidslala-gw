/*
 * $Id: ExpertCategoryCodeListWrapper.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.code.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.common.code.data.ExpertCategory;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpertCategoryCodeListWrapper.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 */
public class ExpertCategoryCodeListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ExpertCategory expertCategory = new ExpertCategory();
			expertCategory.setCodeSetName(rs.getString(1));
			expertCategory.setKey(rs.getString(2));
			expertCategory.setData(rs.getString(3));
			expertCategory.setDetail1(rs.getString(4));
			expertCategory.setDetail2(rs.getString(5));
			return expertCategory;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertCategoryCodeListWrapper fails", null, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
