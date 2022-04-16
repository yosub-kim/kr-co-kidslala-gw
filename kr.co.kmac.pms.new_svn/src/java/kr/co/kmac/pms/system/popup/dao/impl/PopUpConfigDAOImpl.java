/*
 * $Id: PopUpConfigDAOImpl.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 * created by    : Administrator
 * creation-date : 2005. 10. 27
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.popup.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.system.popup.dao.PopUpConfigDAO;
import kr.co.kmac.pms.system.popup.data.PopUpConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO 클래스 설명
 * @author Administrator
 * @version $Id: PopUpConfigDAOImpl.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 */
public class PopUpConfigDAOImpl extends JdbcDaoSupport implements PopUpConfigDAO {

	public static Log log = LogFactory.getLog(PopUpConfigDAOImpl.class);
	protected PopUpConfigSelectQuery popUpConfigSelectQuery;
	protected PopUpconfigUpdateQuery popUpconfigUpdateQuery;

	protected class PopUpConfigSelectQuery extends MappingSqlQuery {
		protected PopUpConfigSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected PopUpConfigSelectQuery(DataSource ds) {
			super(ds, "SELECT width, height, isEnable, content FROM popUpInfo ");
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PopUpConfig popUpConfig = new PopUpConfig();
			popUpConfig.setWidth(rs.getString("width"));
			popUpConfig.setHeight(rs.getString("height"));
			popUpConfig.setIsEnable(rs.getString("isEnable"));
			popUpConfig.setContent(rs.getString("content"));
			return popUpConfig;
		}
	}

	protected class PopUpconfigUpdateQuery extends SqlUpdate {
		protected PopUpconfigUpdateQuery(DataSource ds) {
			super(ds, " UPDATE popUpInfo SET width = ? ,height = ? ,isEnable = ? ,content = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int updatePopup(PopUpConfig popUpConfig) {
			return this.update(new Object[]{popUpConfig.getWidth(), popUpConfig.getHeight(),
					popUpConfig.getIsEnable(), popUpConfig.getContent()});
		}
	}
 
	protected void initDao() throws Exception {
		this.popUpconfigUpdateQuery = new PopUpconfigUpdateQuery(getDataSource());
		this.popUpConfigSelectQuery = new PopUpConfigSelectQuery(getDataSource());
	}
	/*
	 * (non-Javadoc)
	 * @see kr.co.kmac.pms.popup.dao.PopUpConfigDAO#getPopUp()
	 */
	public PopUpConfig getPopUp() throws Exception {
		return (PopUpConfig) popUpConfigSelectQuery.findObject(new Object[]{});
	}

	/*
	 * (non-Javadoc)
	 * @see kr.co.hcc.hqis.db.popconf.dao.HccPopUpConfigDAO#updatePopUp(kr.co.hcc.hqis.db.popconf.model.HccPopUpConfig)
	 */
	public boolean updatePopUp(PopUpConfig PopUpConfig) throws Exception {
		return popUpconfigUpdateQuery.updatePopup(PopUpConfig) > 0;
	}
}
