/*
 * $Id: CustomerSelectListWrapper.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.customer.data.CustomerData;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

/**
 * TODO Provide description for "CustomerSelectListWrapper"
 * @author halberd
 * @version $Id: CustomerSelectListWrapper.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 */
public class CustomerSelectListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			CustomerData customerData = new CustomerData();
			
			customerData.setIdx(rs.getString("idx"));
			customerData.setMaster_idx(rs.getString("master_idx"));
			customerData.setCom_idx(rs.getString("com_idx"));
			customerData.setEmbbs_idx(rs.getString("embbs_idx"));
			customerData.setEmbbsName(rs.getString("embbsName"));
			customerData.setEmbbsCompany(rs.getString("embbsCompany"));
			customerData.setEmbbsDept(rs.getString("embbsDept"));
			customerData.setEmbbsPhone(rs.getString("embbsPhone"));
			customerData.setEmbbsEmail(rs.getString("embbsEmail"));
			customerData.setEmbbsGather(rs.getString("embbsGather"));
			customerData.setSubject(rs.getString("subject"));
			customerData.setWriter(rs.getString("writer"));
			customerData.setWriterSsn(rs.getString("writerSsn"));
			customerData.setContent(rs.getString("content"));
			customerData.setOpinion(rs.getString("opinion"));
			customerData.setInfo_date(rs.getString("info_date"));
			customerData.setRegdate(rs.getString("regdate"));
			customerData.setSanupgubun(rs.getString("sanupgubun"));
			customerData.setReadCnt(rs.getString("readCnt"));
			customerData.setIp(rs.getString("ip"));
			customerData.setCustomerInfoType(rs.getString("customerInfoType"));
			customerData.setReceiveGrade(rs.getString("receiveGrade"));
			customerData.setProjectName(rs.getString("projectName"));
			customerData.setProjectCode(rs.getString("projectCode"));
			customerData.setWriterDept(rs.getString("writerDept"));
			customerData.setCustomerName(rs.getString("customerName"));
			customerData.setCustomerCode(rs.getString("customerCode"));
			customerData.setIndustryTypeCode(rs.getString("industryTypeCode"));
			customerData.setBusinessTypeCode(rs.getString("businessTypeCode"));
			customerData.setPmSsn(rs.getString("pmSsn"));
			
			customerData.setCustomerInfoName(rs.getString("customerInfoName"));
			customerData.setWriterDeptName(rs.getString("writerDeptName"));
			customerData.setBusinessTypeName(rs.getString("businessTypeName"));
			
			return customerData;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CustomerSelectListWrapper fails",
					null,
					e);
		}
		
	}

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
