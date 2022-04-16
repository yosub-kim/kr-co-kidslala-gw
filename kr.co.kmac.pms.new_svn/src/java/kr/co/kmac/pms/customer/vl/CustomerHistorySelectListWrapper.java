/*
 * $Id: CustomerHistorySelectListWrapper.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.customer.data.CustomerHistoryForm;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

/**
 * TODO Provide description for "CustomerHistorySelectListWrapper"
 * @author halberd
 * @version $Id: CustomerHistorySelectListWrapper.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 */
public class CustomerHistorySelectListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			CustomerHistoryForm customerHistoryForm = new CustomerHistoryForm();
			
			customerHistoryForm.setCustomerName(rs.getString("customerName"));
			customerHistoryForm.setCustomerCode(rs.getString("customerCode"));
			customerHistoryForm.setCustomerCnt(rs.getString("customerCnt"));
			customerHistoryForm.setProjectCnt(rs.getString("projectCnt"));
			customerHistoryForm.setCertCnt(rs.getString("certCnt"));
			customerHistoryForm.setPromotionCnt(rs.getString("promotionCnt"));
			
			return customerHistoryForm;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CustomerHistorySelectListWrapper fails", null, e);
		}
		
	}

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
