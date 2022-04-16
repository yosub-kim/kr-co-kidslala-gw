/*
 * $Id: SelectListWrapper.java,v 1.3 2012/06/29 08:37:00 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.board.data.BoardDataForList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class SelectListWrapper implements ObjectWrapper {

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
			BoardDataForList boardDataForList = new BoardDataForList();

			boardDataForList.setLev(rs.getString("lev"));
			boardDataForList.setRef(rs.getString("ref"));
			boardDataForList.setBbsId(rs.getString("bbsId")); // Job Date: 2008-03-24
			boardDataForList.setSubject(rs.getString("subject"));
			boardDataForList.setMaskName(rs.getString("maskname"));
			boardDataForList.setWriter(rs.getString("writer"));
			boardDataForList.setWtime(rs.getString("wtime"));
			boardDataForList.setReadCnt(rs.getString("readcnt"));
			boardDataForList.setWriterId(rs.getString("writerid"));
			boardDataForList.setEmail(rs.getString("email"));
			boardDataForList.setSeq(rs.getString("seq"));
			boardDataForList.setFileCnt(rs.getInt("fileCnt"));
			boardDataForList.setTopArticle(rs.getString("topArticle"));
			try {
				boardDataForList.setPrjType(rs.getString("prjType"));
				boardDataForList.setLikeCnt(rs.getString("likeCnt"));
				boardDataForList.setCommentCnt(rs.getString("commentCnt"));
			} catch (Exception e) {
			}
			return boardDataForList;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("SelectListWrapper fails", null, e);
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
