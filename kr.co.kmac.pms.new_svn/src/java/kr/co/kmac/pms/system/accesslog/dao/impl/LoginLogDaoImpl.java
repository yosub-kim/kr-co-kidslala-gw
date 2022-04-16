/*
 * $Id: LoginLogDaoImpl.java,v 1.3 2013/01/16 00:01:35 cvs Exp $
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
import kr.co.kmac.pms.system.accesslog.dao.ILoginLogDao;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

public class LoginLogDaoImpl extends JdbcDaoSupport implements ILoginLogDao {

	private InsertQuery insertQuery;

	protected class InsertQuery extends SqlUpdate {
		protected InsertQuery(DataSource ds) {
			super(ds, "insert into LoginLog values (?, ?, ?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.TIMESTAMP));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}
/*
 * CREATE TABLE LoginLog(
	sessionId varchar(50) NOT NULL,
	remoteIpAddress varchar(50) NOT NULL,
	id varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	createdtime datetime NOT NULL
)
 */
		protected void insert(HttpSession session, String clientIp) {
			WebAuthenticationDetails wad = (WebAuthenticationDetails) ((SecurityContext) session.getAttribute("ACEGI_SECURITY_CONTEXT"))
					.getAuthentication().getDetails();
			User user = (User) ((SecurityContext) session.getAttribute("ACEGI_SECURITY_CONTEXT")).getAuthentication().getPrincipal();
			super.update(new Object[] { 
					session.getId(), 
					clientIp, 
					user.getId(),
					user.getName(),
					new Date(session.getCreationTime()),
					session.getAttribute("deviceType")
				});
		}
	}

	protected void initDao() throws Exception {
		this.insertQuery = new InsertQuery(getDataSource());
	}

	public void insertLoginLog(HttpSession session, String clientIp) throws DataAccessException {
		try {
			this.insertQuery.insert(session, clientIp);
		} catch (Exception e) {
		}
	}

}
