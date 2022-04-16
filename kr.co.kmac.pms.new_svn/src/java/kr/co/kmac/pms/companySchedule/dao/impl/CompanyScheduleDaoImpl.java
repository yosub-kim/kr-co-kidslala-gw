/*
 * $Id: CompanyScheduleDaoImpl.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 6. 2.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.companySchedule.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.companySchedule.dao.CompanyScheduleDao;
import kr.co.kmac.pms.companySchedule.data.CompanyScheduleInfoData;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class CompanyScheduleDaoImpl extends JdbcDaoSupport implements CompanyScheduleDao {
	private ScheduleRetrieve scheduleRetrieve;
	private ScheduleRetrieveOfMonth scheduleRetrieveOfMonth;
	private ScheduleCreate scheduleCreate;
	private ScheduleDelete scheduleDelete;

	protected class ScheduleRetrieve extends MappingSqlQuery {
		protected ScheduleRetrieve(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleRetrieve(DataSource ds) {
			super(ds,
					" SELECT * " +
					" FROM CompanySchedule " +
					" WHERE [year]=? AND [month]=? " +
					"	AND [day] = ? AND startHour = ? AND startMin = ? ");
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CompanyScheduleInfoData info = new CompanyScheduleInfoData();
			info.setYear(rs.getString("year"));
			info.setMonth(rs.getString("month"));
			info.setDay(rs.getString("day"));
			info.setStartHour(rs.getString("startHour"));
			info.setStartMin(rs.getString("startMin"));
			info.setEndHour(rs.getString("endHour"));
			info.setEndMin(rs.getString("endMin"));
			info.setContent(rs.getString("content"));
			info.setPlace(rs.getString("place"));
			return info;
		}
	}

	protected class ScheduleRetrieveOfMonth extends MappingSqlQuery {
		protected ScheduleRetrieveOfMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleRetrieveOfMonth(DataSource ds) {
			super(ds, "	SELECT 	 * 						" 
					+ "	  FROM 	companySchedule 				"
					+ "	 WHERE 	([year] = ?) AND ([month] = ?) AND ([day] LIKE ?) "
					+ "  ORDER BY 	year, month, day, startHour, startMin			");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CompanyScheduleInfoData info = new CompanyScheduleInfoData();
			info.setYear(rs.getString("year"));
			info.setMonth(rs.getString("month"));
			info.setDay(rs.getString("day"));
			info.setStartHour(rs.getString("startHour"));
			info.setStartMin(rs.getString("startMin"));
			info.setEndHour(rs.getString("endHour"));
			info.setEndMin(rs.getString("endMin"));
			info.setContent(rs.getString("content"));
			info.setPlace(rs.getString("place"));
			return info;
		}
	}

	protected class ScheduleCreate extends SqlUpdate {
		protected ScheduleCreate(DataSource ds) {
			super(
					ds,
					" INSERT INTO CompanySchedule( "
		    	  + "		 [year], [month], [day], startHour, startMin, endHour, endMin, content, place)"
				  + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?  ) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int insert(CompanyScheduleInfoData scheduleInfoData) throws DataAccessException {
			return this.update(new Object[]{scheduleInfoData.getYear(),
					scheduleInfoData.getMonth(), scheduleInfoData.getDay(),
					scheduleInfoData.getStartHour(), scheduleInfoData.getStartMin(),
					scheduleInfoData.getEndHour(), scheduleInfoData.getEndMin(),
					scheduleInfoData.getContent(), scheduleInfoData.getPlace()});
		}
	}

	protected class ScheduleDelete extends SqlUpdate {
		protected ScheduleDelete(DataSource ds) {
			super(ds, " Delete from CompanySchedule where year=? and month=? and day=? and startHour = ? and startMin = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int delete(String year, String month, String day, String startHour , String startMin) {
			return update(new Object[]{year, month, day, startHour, startMin});
		}
	}

	protected void initDao() throws Exception {
		this.scheduleRetrieve = new ScheduleRetrieve(getDataSource());
		this.scheduleRetrieveOfMonth = new ScheduleRetrieveOfMonth(getDataSource());
		this.scheduleCreate = new ScheduleCreate(getDataSource());
		this.scheduleDelete = new ScheduleDelete(getDataSource());
	}

	@SuppressWarnings("unchecked")
	public List<CompanyScheduleInfoData> scheduleList(String searchYear, String searchMonth, String searchDay, String startHour, String startMin)
			throws DataAccessException {
		List<CompanyScheduleInfoData> resultList = null;

		resultList = scheduleRetrieve.execute(new Object[]{searchYear, searchMonth, searchDay, startHour, startMin});
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<CompanyScheduleInfoData> scheduleListOfMonth(String searchYear, String searchMonth, String searchDay)
			throws DataAccessException {
		List<CompanyScheduleInfoData> resultList = null;
		if (searchDay == null || "".equals(searchDay))
			searchDay = "%%";

		resultList = scheduleRetrieveOfMonth.execute(new Object[]{searchYear, searchMonth, searchDay});
		return resultList;
	}

	public void create(CompanyScheduleInfoData scheduleInfoData) throws DataAccessException {
		this.scheduleCreate.insert(scheduleInfoData);
	}

	public void remove(String year, String month, String day, String startHour , String startMin) throws DataAccessException {
		this.scheduleDelete.delete(year, month, day, startHour, startMin);
	}

}
