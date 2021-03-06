/*
 * $Id: WorkCabinetListWrapper.java,v 1.3 2014/07/31 08:35:02 cvs Exp $
 * created by    : ?ȼ?ȣ
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.workcabinet.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.sanction.workcabinet.data.CabinetEntity;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class WorkCabinetListWrapper implements ObjectWrapper {

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
			CabinetEntity entity = new CabinetEntity();
			entity.setId(rs.getString("id"));
			entity.setTitle(rs.getString("title"));
			entity.setAssigneeId(rs.getString("assigneeId"));
			entity.setAssignerId(rs.getString("assignerId"));
			entity.setState(rs.getString("state"));
			entity.setType(rs.getString("type"));
			entity.setLevel(rs.getString("level"));
			entity.setRefWorkId1(rs.getString("refWorkId1"));
			entity.setRefWorkId2(rs.getString("refWorkId2"));
			entity.setRefWorkId3(rs.getString("refWorkId3"));
			entity.setApprovalName(rs.getString("approvalName"));
			entity.setDocument(rs.getString("document"));
			entity.setDraftUserDept(rs.getString("draftUserDept"));
			entity.setDraftUserDeptName(rs.getString("draftUserDeptName"));
			entity.setDraftUserId(rs.getString("draftUserId"));
			entity.setDraftUserName(rs.getString("draftUserName"));
			entity.setDraftDate(rs.getDate("draftDate"));
			entity.setCreateDate(rs.getDate("createDate"));
			entity.setOpenDate(rs.getDate("openDate"));
			entity.setExecuteDate(rs.getDate("executeDate"));
			entity.setProjectName(rs.getString("projectName"));
			try{
				entity.setUseMobile(rs.getString("useMobile"));
			}catch(Exception e){
				e.getMessage();
			}
			
			return entity;
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
