/*
 * $Id: CustomerPresentReportForIndividualWrapper.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.customer.data.CustomerPresentReport;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO Provide description for "CustomerPresentReportForPermanentWrapper"
 * @author halberd
 * @version $Id: CustomerPresentReportForIndividualWrapper.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 */
public class CustomerPresentReportForIndividualWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */	
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			CustomerPresentReport customerPresentReport = new CustomerPresentReport();
			customerPresentReport.setId(rs.getString("id"));
			customerPresentReport.setName(rs.getString("name"));
			customerPresentReport.setCustomerCnt(rs.getString("customerCnt"));
			
			customerPresentReport.setWriterName(rs.getString("writerName"));
			customerPresentReport.setWriterSsn(rs.getString("writerSsn"));
			customerPresentReport.setPosition(rs.getString("position"));
			
			customerPresentReport.setA(rs.getString("a"));
			customerPresentReport.setB(rs.getString("b"));
			customerPresentReport.setC(rs.getString("c"));
			customerPresentReport.setSubTotal(rs.getString("subTotal"));
			customerPresentReport.setD(rs.getString("d"));
			customerPresentReport.setE(rs.getString("e"));
			customerPresentReport.setF(rs.getString("f"));
			customerPresentReport.setG(rs.getString("g"));
			customerPresentReport.setGrandTotal(rs.getString("grandTotal"));
			
			return customerPresentReport;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CustomerPresentReportForIndividualWrapper fails", null, e);
		}		
	}

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
