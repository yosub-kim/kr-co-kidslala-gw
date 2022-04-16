/*
 * $Id: AttachSelectListForTaskWrapper.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 * creation-date : 2006. 3. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.attach.data.AttachDataForTask;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

/**
 * TODO Provide description for "AttachSelectListWrapper"
 * @author halberd
 * @version $Id: AttachSelectListForTaskWrapper.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 */
public class AttachSelectListForTaskWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			AttachDataForTask attachDataForTask = new AttachDataForTask();
			
			attachDataForTask.setAttachCreateDate(rs.getString("attachCreateDate"));
			attachDataForTask.setAttachCreatorId(rs.getString("attachCreatorId"));
			attachDataForTask.setAttachSeq(rs.getString("attachSeq"));
			attachDataForTask.setAttachWorkName(rs.getString("attachWorkName"));
			attachDataForTask.setAttachOutputName(rs.getString("attachOutputName"));
			attachDataForTask.setAttachFileId(rs.getString("attachFileId"));
			attachDataForTask.setAttachIsEssential(rs.getString("attachIsEssential"));
			attachDataForTask.setAttachFileName(rs.getString("attachFileName"));
			attachDataForTask.setAttachGubun(rs.getString("attachGubun"));
			attachDataForTask.setAttachGubun2(rs.getString("attachGubun2"));
			attachDataForTask.setAttachOutputType(rs.getString("attachOutputType"));
			attachDataForTask.setAttachOutputBusType(rs.getString("attachOutputBusType"));
			attachDataForTask.setAttachCreatorName(rs.getString("attachCreatorName"));

			return attachDataForTask;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("AttachSelectListForTaskWrapper fails",
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
