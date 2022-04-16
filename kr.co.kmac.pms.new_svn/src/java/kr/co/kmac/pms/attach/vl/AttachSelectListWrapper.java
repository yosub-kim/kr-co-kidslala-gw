/*
 * $Id: AttachSelectListWrapper.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 * creation-date : 2006. 3. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.attach.data.AttachData;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

/**
 * TODO Provide description for "AttachSelectListWrapper"
 * @author halberd
 * @version $Id: AttachSelectListWrapper.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 */
public class AttachSelectListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			AttachData attachData = new AttachData();
			
			attachData.setSeq(rs.getString("seq"));
			attachData.setTaskId(rs.getString("taskId"));
			attachData.setProjectCode(rs.getString("projectCode"));
			attachData.setTaskFormTypeId(rs.getString("taskFormTypeId"));
			attachData.setAttachCreateDate(rs.getString("attachCreateDate"));
			attachData.setAttachCreatorId(rs.getString("attachCreatorId"));
			attachData.setAttachCreatorName(rs.getString("attachCreatorName"));
			attachData.setAttachSeq(rs.getString("attachSeq"));
			attachData.setAttachWorkName(rs.getString("attachWorkName"));
			attachData.setAttachOutputName(rs.getString("attachOutputName"));
			attachData.setAttachFileId(rs.getString("attachFileId"));
			attachData.setAttachIsEssential(rs.getString("attachIsEssential"));
			attachData.setAttachFileName(rs.getString("attachFileName"));
			attachData.setAttachOutputType(rs.getString("attachOutputType"));
			attachData.setAttachOutputTypeName(rs.getString("attachOutputTypeName"));
			attachData.setAttachOutputBusType(rs.getString("attachOutputBusType"));
			attachData.setAttachOutputBusTypeName(rs.getString("attachOutputBusTypeName"));
			
			return attachData;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("AttachSelectListWrapper fails",
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
