/*
 * $Id: CustomerProjectPresentReportWrapper.java,v 1.2 2010/01/26 16:40:54 cvs1 Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.customer.data.CustomerProjectPresentReport;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * 
 * @author halberd
 * @version $Id: CustomerPresentReportForIndividualWrapper.java,v 1.2 2008/09/28
 *          03:08:55 cvs Exp $
 */
public class CustomerProjectPresentReportWrapper implements ObjectWrapper {
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
			CustomerProjectPresentReport report = new CustomerProjectPresentReport();
			report.setId(rs.getString("id"));
			report.setName(rs.getString("name"));
			report.setMemberCnt(rs.getString("memberCnt"));
			report.setCustomerCnt(rs.getString("customerCnt"));
			report.setCtf(rs.getString("ctf"));
			report.setCtb(rs.getString("ctb"));
			report.setCtc(rs.getString("ctc"));
			report.setCth(rs.getString("cth"));
			report.setCti(rs.getString("cti"));
			report.setSubTotal(rs.getString("subTotal"));
			report.setCte(rs.getString("cte"));
			report.setCtg(rs.getString("ctg"));
			report.setCtz(rs.getString("ctz"));
			report.setCtd(rs.getString("ctd"));
			report.setItb(rs.getString("itb"));
			report.setIta(rs.getString("ita"));
			report.setItc(rs.getString("itc"));
			report.setItd(rs.getString("itd"));
			report.setIte(rs.getString("ite"));

			report.setA(rs.getString("a"));
			report.setB(rs.getString("b"));
			report.setC(rs.getString("c"));
			report.setD(rs.getString("d"));
			report.setE(rs.getString("e"));
			report.setF(rs.getString("f"));
			//report.setG(rs.getString("g"));
			//report.setH(rs.getString("h"));
			report.setPrjTotal(rs.getString("grandTotal"));
			
			
			try {report.setWriterName(rs.getString("writerName"));} catch (Exception e) {}
			try {report.setWriterSsn(rs.getString("ssn"));} catch (Exception e) {}
			try {report.setPosition(rs.getString("position"));} catch (Exception e) {}

			return report;
		} catch (SQLException e) {
			throw exceptionTranslator.translate(
					"CustomerProjectPresentReportWrapper fails", null, e);
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
