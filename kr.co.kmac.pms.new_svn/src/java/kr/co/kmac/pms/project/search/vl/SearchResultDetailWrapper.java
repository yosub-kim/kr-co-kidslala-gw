/*
 * $Id: ExpenseRealTimeResultDetailWrapper.java,v 1.5 2011/11/03 05:06:05 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 26.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.search.vl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.search.data.SearchResult;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class SearchResultDetailWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			SearchResult searchResult = new SearchResult();
			searchResult.setProjectCode(rs.getString("projectCode"));
			searchResult.setProjectName(rs.getString("projectName"));
			searchResult.setCustomerName(rs.getString("customerName"));
			searchResult.setAttachFileId(rs.getString("attachFileId"));
			searchResult.setAttachFileName(rs.getString("attachFileName"));
			searchResult.setAttachOutputName(rs.getString("attachOutputName"));
			searchResult.setTaskId(rs.getString("taskId"));
			searchResult.setTaskFormTypeId(rs.getString("taskFormTypeId"));
			searchResult.setTaskFormTypeName(rs.getString("taskFormTypeName"));
			searchResult.setAttachCreateDate(rs.getString("attachCreateDate"));
			searchResult.setAttachCreatorName(rs.getString("attachCreatorName"));
			searchResult.setAttachOutputType(rs.getString("attachOutputType"));
			searchResult.setAttachOUtputTypeName(rs.getString("attachOUtputTypeName"));
			searchResult.setAttachOutputBusType(rs.getString("attachOutputBusType"));
			searchResult.setAttachOutputBusTypeName(rs.getString("attachOutputBusTypeName"));
			if (rs.getString("hashTag") != null && rs.getString("hashTag").length() > 1) {
				searchResult.setHashTag(Arrays.asList(StringUtil.split(rs.getString("hashTag"), "|")));
			}			

			return searchResult;
		} catch (SQLException e) {
			throw exceptionTranslator.translate(
					"SearchResultDetailWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
