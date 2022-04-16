/*
 * $Id: AttachOutputTypeCodeListWrapper.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.attach.data.AttachOutputType;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: AttachOutputTypeCodeListWrapper.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 */
public class AttachOutputTypeCodeListWrapper implements ObjectWrapper {
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
			AttachOutputType outputTypeCode = new AttachOutputType();
			outputTypeCode.setCodeSetName(rs.getString("table_name"));
			outputTypeCode.setKey(rs.getString("key_1"));
			outputTypeCode.setData(rs.getString("key_2"));
			outputTypeCode.setDetail1(rs.getString("data_1"));
			outputTypeCode.setDetail2(rs.getString("data_2"));
			return outputTypeCode;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("AttachOutputTypeCodeListWrapper fails", null, e);
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
