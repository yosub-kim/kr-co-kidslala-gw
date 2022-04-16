/*
 * $Id: GroupWrapper.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 28.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.common.org.util.GroupUtils;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * ValueList를 위한 Group 객체 Wrapper
 * @author 최인호
 * @version $Id: GroupWrapper.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */
public class GroupWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		try {
			ResultSet rs = (ResultSet) objectToBeWrapped;
			Group group = (Group) GroupUtils.createGroup(rs.getString("id"),
					rs.getString("name"),
					rs.getInt("groupType"));
			group.setEnabled(rs.getBoolean("enabled"));
			group.setMemberRule(rs.getString("memberRule"));
			group.setDescription(rs.getString("description"));
			group.setParentId(rs.getString("parentId"));
			group.setPath(rs.getString("path"));
			group.setDepth(rs.getInt("depth"));
			return group;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("Group wrapper fails", null, e);
		}
	}

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo info) {
	}
}
