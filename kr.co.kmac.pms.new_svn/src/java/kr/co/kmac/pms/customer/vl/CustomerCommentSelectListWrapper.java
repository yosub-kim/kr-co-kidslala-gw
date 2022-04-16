/*
 * $Id: CustomerCommentSelectListWrapper.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.customer.data.CustomerCommentForm;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

/**
 * TODO Provide description for "CustomerSelectListWrapper"
 * @author halberd
 * @version $Id: CustomerCommentSelectListWrapper.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 */
public class CustomerCommentSelectListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			CustomerCommentForm customerCommentForm = new CustomerCommentForm();
			
			customerCommentForm.setIdx(rs.getString("idx"));
			customerCommentForm.setParent_idx(rs.getString("parent_idx"));
			customerCommentForm.setWriter(rs.getString("writer"));
			customerCommentForm.setWriterId(rs.getString("writerId"));
			customerCommentForm.setComment(rs.getString("comment"));
			customerCommentForm.setFileName(rs.getString("fileName"));
			customerCommentForm.setUpFile(rs.getString("upFile"));
			customerCommentForm.setRegdate(rs.getString("regdate"));

			return customerCommentForm;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CustomerCommentSelectListWrapper fails",
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
