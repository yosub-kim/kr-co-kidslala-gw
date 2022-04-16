/*
 * $Id: MobileBoardIndexListWrapper.java,v 1.2 2012/11/11 10:51:28 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.board.data.BoardIndex;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class MobileBoardIndexListWrapper implements ObjectWrapper {

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
			BoardIndex boardIndex = new BoardIndex();
			
			boardIndex.setBbsId(rs.getString("bbsId")); // Job Date: 2008-03-24
			boardIndex.setBbsName(rs.getString("bbsName"));
			boardIndex.setRecentCnt(rs.getString("recentCnt"));
			boardIndex.setTotalCnt(rs.getString("totalCnt"));
			return boardIndex;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("MobileBoardIndexListWrapper fails", null, e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo valueListInfo) {
		// TODO Auto-generated method stub

	}
}
