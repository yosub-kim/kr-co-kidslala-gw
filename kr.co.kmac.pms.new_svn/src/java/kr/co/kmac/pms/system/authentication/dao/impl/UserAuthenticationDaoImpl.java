/*
 * $Id: UserAuthenticationDaoImpl.java,v 1.1 2013/01/30 09:19:15 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authentication.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.system.authentication.dao.UserAuthenticationDao;
import kr.co.kmac.pms.system.authentication.data.UserAuthentication;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;


public class UserAuthenticationDaoImpl extends JdbcDaoSupport implements UserAuthenticationDao{
	private UserAuthenticationRetrieve1 userAuthenticationRetrieve1;
	private UserAuthenticationRetrieve2 userAuthenticationRetrieve2;
	private UserAuthenticationCreate userAuthenticationCreate;
	private UserAuthenticationUpdate userAuthenticationUpdate;
	private UserAuthenticationUpdate2 userAuthenticationUpdate2;

	protected class UserAuthenticationRetrieve1 extends MappingSqlQuery {
		protected UserAuthenticationRetrieve1(DataSource ds, String query) {
			super(ds, query);
		}
		protected UserAuthenticationRetrieve1(DataSource ds) {
			super(ds, " select 	seq, userSsn, authReqDate, authToken, authValue, isValid, device	" 
					+ "	  from 	UserAuthentication													" 
					+ "	 where 	userSsn = ? 														" 
					+ "	 and 	device = ?	 														" 
					+ "  ORDER  BY seq DESC															"
			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserAuthentication userAuthentication = new UserAuthentication();
			userAuthentication.setSeq(rs.getInt("seq"));
			userAuthentication.setUserSsn(rs.getString("userSsn"));
			userAuthentication.setAuthReqDate(rs.getDate("authReqDate"));
			userAuthentication.setAuthToken(rs.getString("authToken"));
			userAuthentication.setAuthValue(rs.getString("authValue"));
			userAuthentication.setValid(rs.getBoolean("isValid"));
			userAuthentication.setDevice(rs.getString("device"));
			return userAuthentication;
		}
	}
	
	protected class UserAuthenticationRetrieve2 extends UserAuthenticationRetrieve1 {
		protected UserAuthenticationRetrieve2(DataSource ds, String query) {
			super(ds, query);
			
		}
		protected UserAuthenticationRetrieve2(DataSource ds) {
			super(ds, " select seq, userSsn, authReqDate, authToken, authValue, isValid, device  from ( "
					+ " select seq, userSsn, authReqDate, authToken, authValue, isValid, isnull(device, 'empty') as device	" 
					+ " from  UserAuthentication )p 												"
					+ "	 where 	userSsn = ? 														"
					+ "	 and 	device = ?	 													" 
					+ "	 and	isValid = ? 														" 
					+ "  ORDER  BY seq DESC															"
			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			compile();
		}
	}
	
	protected class UserAuthenticationCreate extends SqlUpdate {
		protected UserAuthenticationCreate(DataSource ds) {
			super(ds, 
					" 	INSERT 	INTO	UserAuthentication" +
					"			( 		" +
					"				userSsn, authReqDate, authToken, authValue, isValid, device	" +
					"			)		" +
					" 	VALUES	(		" +
					"				?, getDate(), ?, ?, 1, ? 	" +
					"			) 		"
			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int create(UserAuthentication userAuthentication) throws DataAccessException {
			return this.update(new Object[]{
					userAuthentication.getUserSsn(), userAuthentication.getAuthToken(),
					userAuthentication.getAuthValue(), userAuthentication.getDevice()
			});
		}
	}
	
	protected class UserAuthenticationUpdate extends SqlUpdate {
		protected UserAuthenticationUpdate(DataSource ds) {
			super(ds, 
					" 	update 	UserAuthentication 		" +
					"	   set 	userSsn=?, authToken=?, authValue=?, isValid=?, device=? " +
					" 	 where 	seq=? 	" 
			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BOOLEAN));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
		protected int store(UserAuthentication userAuthentication) throws DataAccessException {
			return this.update(new Object[]{
					userAuthentication.getUserSsn(), userAuthentication.getAuthToken(), 
					userAuthentication.getAuthValue(), userAuthentication.isValid(), userAuthentication.getDevice(), 
					userAuthentication.getSeq()
			});
		}
	}
	
	
	protected class UserAuthenticationUpdate2 extends SqlUpdate {
		protected UserAuthenticationUpdate2(DataSource ds) {
			super(ds, 
					" 	update 	UserAuthentication							" +
					"	   set 	isValid=0									" +
					" 	 where 	userSsn=? and  device=?						" 
					);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int store(String userSsn, String device) throws DataAccessException {
			return this.update(new Object[]{userSsn, device});
		}
	}
	
	protected void initDao() throws Exception {
		this.userAuthenticationRetrieve1 = new UserAuthenticationRetrieve1(getDataSource());
		this.userAuthenticationRetrieve2 = new UserAuthenticationRetrieve2(getDataSource());
		this.userAuthenticationCreate   = new UserAuthenticationCreate(getDataSource());
		this.userAuthenticationUpdate = new UserAuthenticationUpdate(getDataSource());
		this.userAuthenticationUpdate2 = new UserAuthenticationUpdate2(getDataSource());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAuthentication> getUserAuthentication(String userSsn, String device) throws DataAccessException {
		return this.userAuthenticationRetrieve1.execute(new Object[] { userSsn, device });
	}

	@Override
	public UserAuthentication getUserAuthentication(String userSsn, String device, boolean isValid) throws DataAccessException {
		//null값 조회
		if("empty".equals(device)){
			device = "empty";
		}
		List<UserAuthentication> userAuthenticationList = this.userAuthenticationRetrieve2.execute(new Object[] { userSsn, device, isValid });
		if(userAuthenticationList.size() > 0){			
			return (UserAuthentication) this.userAuthenticationRetrieve2.execute(new Object[] { userSsn, device, isValid }).get(0);
		} else {
			return new UserAuthentication(userSsn, "not_auth", false);
		}
	}

	@Override
	public void insertUserAuthentication(UserAuthentication userAuthentication) throws DataAccessException {
		this.userAuthenticationCreate.create(userAuthentication);
		
	}

	@Override
	public void updateUserAuthentication(UserAuthentication userAuthentication) throws DataAccessException {
		this.userAuthenticationUpdate.store(userAuthentication);		
	}

	@Override
	public void updateUserAuthentication2(String userSsn, String device) throws DataAccessException {
		this.userAuthenticationUpdate2.store(userSsn, device);			
	}	

	
}
