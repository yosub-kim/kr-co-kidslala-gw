/*
 * $Id: MenuLogDaoImpl.java,v 1.1 2010/12/05 15:58:18 cvs1 Exp $
 * created by    : √÷¿Œ»£
 * creation-date : 2005. 10. 13.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.accesslog.dao.impl;

import java.sql.Types;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import kr.co.kmac.pms.common.org.data.User;
import kr.co.kmac.pms.system.accesslog.dao.IMenuLogDao;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

public class MenuLogDaoImpl extends JdbcDaoSupport implements IMenuLogDao {

	private InsertQuery insertQuery;

	protected class InsertQuery extends SqlUpdate {
		protected InsertQuery(DataSource ds) {
			super(ds, "insert into MenuLog values (?, ?, ?, getdate(), ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.TIMESTAMP));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		/*
		 * CREATE TABLE MenuLog( sessionId varchar(50) NOT NULL, remoteIpAddress varchar(50) NOT NULL, createdtime datetime NOT NULL, clicktime datetime NOT
		 * NULL, id varchar(50) NOT NULL, name varchar(50) NOT NULL, menu varchar(50) NOT NULL )
		 */
		protected void insert(HttpSession session, String menuId) {
			WebAuthenticationDetails wad = (WebAuthenticationDetails) ((SecurityContext) session.getAttribute("ACEGI_SECURITY_CONTEXT"))
					.getAuthentication().getDetails();
			User user = (User) ((SecurityContext) session.getAttribute("ACEGI_SECURITY_CONTEXT")).getAuthentication().getPrincipal();
			super.update(new Object[] { session.getId(), wad.getRemoteAddress(), new Date(session.getCreationTime()), user.getId(), user.getName(),
					menuId });
		}
	}

	protected void initDao() throws Exception {
		this.insertQuery = new InsertQuery(getDataSource());
	}

	@Override
	public void insertMenuLog(HttpSession session, String menuId) throws DataAccessException {
		this.insertQuery.insert(session, menuId);
	}

}
