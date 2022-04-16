/*
 * $Id: ScheduleDaoImpl.java,v 1.58 2019/03/19 06:22:31 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 4. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.schedule.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.schedule.dao.ScheduleDao;
import kr.co.kmac.pms.schedule.data.CustomerPickerInfo;
import kr.co.kmac.pms.schedule.data.DailyProjectInfo;
import kr.co.kmac.pms.schedule.data.DailyScheduleInfo;
import kr.co.kmac.pms.schedule.data.HolidayInfo;
import kr.co.kmac.pms.schedule.data.ScheduleDailyMasterInfo;
import kr.co.kmac.pms.schedule.data.ScheduleInfoData;
import kr.co.kmac.pms.schedule.data.ScheduleUserInfo;
import kr.co.kmac.pms.schedule.exception.ScheduleException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ScheduleDaoImpl extends JdbcDaoSupport implements ScheduleDao {
	private static final String SCHEDULE_QUERY = " select  *  from     "
			+ " (    "
			+ " 	select 	e.ssn, name, dept, jobClass, projectName, funcField, consultingMajor, [year],[month],[day],processTypeCode, customerName, ? as relationUser, ? as place,  "
			+ " 			? as startHour, ? as startMin, ? as endHour, ? as endMin, 'report' as scheduleType,companyPosition, rank, enable  from       "	// job date: 2007-04-13 purpose: sort, added enable
			+ " 			expertPool e , project p, projectMember m , projectReportDetail d     "
			+ " 	where 	e.ssn=m.ssn      "
			+ " 	and     m.projectCode=p.projectCode      "
			+ " 	and     chargessn = e.ssn    "
			+ "   and     m.projectCode=d.projectCode "
			+ "   and		e.enable = '1'              "
			+ " 	and 	[year]=?    "
			+ " 	and 	[month]=?    "
			+ " 	group by e.ssn, name, dept, jobClass, projectName, funcField, consultingMajor, [year],[month],[day],processTypeCode, customerName,companyPosition, rank, enable  "	// job date: 2007-04-13 purpose: sort, added enable
			+ " 	union    "
			+ " 	select 	ssn, name, dept, jobClass, ? as projectName, funcField, consultingMajor, ? as [year], ? as [month], ? as [day],? as processTypeCode,? as customerName, ? as relationUser, ? as place, "
			+ " 	 		? as startHour, ? as startMin, ? as endHour, ? as endMin, 'report' as scheduleType,companyPosition, rank,  "
			+ "				(CASE WHEN (SELECT id from smrole where id = expertPool.companyPosition) IS NULL THEN '0' ELSE expertPool.enable END) as enable "	// job date: 2007-04-13 purpose: sort, added row

			+ "	from	expertPool     "
			+ " 	where ssn not in (    "
			+ " 		select e.ssn  from       "
			+ " 		expertPool e,  projectReportDetail d     "
			+ " 		where e.ssn=d.chargessn      "
			+ " 		and [year]=?    "
			+ " 		and [month]=?    "
			+ " 		group by e.ssn    "
			+ " 	    )    "
			+ "	and	enable = '1' "
			+ " 	union "
			+ " 	select 	s.ssn, name, dept, jobClass, content as projectName, funcField, consultingMajor, [year], [month], [day],  type as processTypeCode, customerName, relationUser, place,  "
			+ " 			startHour, startMin, endHour, endMin, 'user' as scheduleType, companyPosition, rank, enable "	// job date: 2007-04-13 purpose: sort, added enable
			+ "	from   	schedule s, expertpool e " + " 	where s.ssn=e.ssn    "
			+ "	and	e.enable='1' " + " 	and [year]=?     " + " 	and [month]=?    "
			+ " ) M                " + " where day like ?   " + " and ssn like ?     " + " and M.enable = '1' ";	// job date: 2007-04-13 purpose: sort, added M.enable = '1'

	private static final String KMAC_WHERE = " and jobClass=?	and companyPosition <> '' and left(companyPosition,1) <> '4' "
			+ " order by  (case dept when '2000'	then '100' " 
			+ "			 			 when '2010'	then '101' "
			+ "						 when '2031'	then '102' "  
			+ "						 when '2032'	then '103' "	// job date: 2007-04-12 purpose: sort for CIO Han
			//+ "						 when '2033'	then '104' " // Job Date: 2007-11-09	purpose: remove research CBO 
			+ "						 when '2034'	then '105' "
			+ "						 when '2035'	then '106' "	// job date: 2007-04-12 purpose: sort for CIO Han 
			+ "						 when '2110'	then '107' "
			+ "						 when '2111'	then '108' " 
			+ "						 when '2112'	then '109' "
			+ "						 when '2113'	then '110' " 
			+ "						 when '2310'	then '111' " 
			+ "						 when '2311'	then '112' " 
			+ "						 when '2312'	then '113' " 
			+ "						 when '2120'	then '114' "
			+ "						 when '2121'	then '115' " 
			+ "						 when '2122'	then '116' "
			+ "						 when '2123'	then '117' " 
			+ "						 when '2130'	then '118' "
			+ "						 when '2131'	then '119' " 
			+ "						 when '2132'	then '120' "
			+ "						 when '2133'	then '121' " 
			+ "						 when '2140'	then '122' "
			+ "						 when '2141'	then '123' " 
			+ "						 when '2142'	then '124' "
			+ "						 when '2143'	then '125' " 
			+ "						 when '2144'	then '126' "
			+ "						 when '2150'	then '127' "
			+ "						 when '2151'	then '128' "
			+ "						 when '2152'	then '129' " 
			+ "						 when '2153'	then '130' "
			+ "						 when '2160'	then '131' " 
			+ "						 when '2161'	then '132' "
			+ "						 when '2162'	then '133' " 
			+ "						 when '2163'	then '134' "
			+ "						 when '2320'	then '135' "
			+ "						 when '2321'	then '136' "
			+ "						 when '2322'	then '137' " 
			+ "						 when '2323'	then '138' "
			+ "						 when '2360'	then '139' " 
			+ "						 when '2361'	then '140' "
			+ "						 when '2370'	then '141' " 
			+ "						 when '2371'	then '142' "
			+ "						 when '2400'	then '143' "
			+ "						 when '2401'	then '144' "
			+ "						 when '6100'	then '145' " 
			+ "						 when '6200'	then '146' "
			+ "						 when '6300'	then '147' " 
			+ "						 when '6400'	then '148' "
			+ "						 when '6500'	then '149' "
			//+ "						 when '5600'	then '150' "
			//+ "						 when '5700'	then '151' "
			+ "						 when '2180'	then '152' " 
			+ "						 when '2190'	then '153' "
			+ "						 when '2191'	then '154' " 
			+ "						 when '2192'	then '155' "
			+ "						 when '2193'	then '156' "
			+ "						 when '2194'	then '157' "
			+ "				end) asc, companyPosition, rank, name, day, startHour, startMin       ";

	private static final String NOT_KMAC_WHERE = " and (jobClass='B' or jobClass = 'J')	 and companyPosition <> '' and companyPosition <> '61DT' and companyPosition <> '62DT'  "	// Job Date: 2008-09-22 Author: yhyim	description: PU와 상임을 같은 그룹으로 
			+ " order by dept asc, jobClass, companyPosition, name, day, startHour, startMin ";

	private static final String NOT_USER_WHERE = " and jobClass = ?   " 
		+ " order by dept asc, jobClass, companyPosition, name, day, startHour, startMin ";

	private ScheduleOfKMACUser scheduleOfKMACUser;
	private ScheduleOfNotKMACUser scheduleOfNotKMACUser;
	private ScheduleOfNotUser scheduleOfNotUser;
	private SMGroupFind sMGroupFind;
	private SanctionDeptFind sanctionDeptFind;
	private SanctionDeptFind2 sanctionDeptFind2;
	private SanctionDeptFind3 sanctionDeptFind3;
	private SanctionDeptFind4 sanctionDeptFind4;
	private ScheduleCreate scheduleCreate;
	private ScheduleDelete scheduleDelete;
	private ScheduleCreate_time scheduleCreate_time;
	private ScheduleCreate_time2 scheduleCreate_time2;
	private ScheduleDelete_time scheduleDelete_time;
	private ScheduleLogCreate scheduleLogCreate;
	
	private ProjectScheduleByMonth projectScheduleByMonth;
	private ProjectManpowerByMonth projectManpowerByMonth;
	private ProjectPJTByMonth projectPJTByMonth;
	private ProjectBoardByMonth projectBoardByMonth;
	private ProjectSchedule projectSchedule;
	private ScheduleByMonth scheduleByMonth;
	private ScheduleByMonth_time scheduleByMonth_time;
	private ScheduleByMonthCnt_time scheduleByMonthCnt_time;
	private ScheduleByConCnt scheduleByConCnt;
	private ScheduleByMonthCnt_time_cco scheduleByMonthCnt_time_cco;
	private ScheduleByMonthCnt_time_cbo scheduleByMonthCnt_time_cbo;
	private ScheduleByMonthCnt_customer scheduleByMonthCnt_customer;
	private ScheduleByMonthCnt_customer2 scheduleByMonthCnt_customer2;
	private InternalScheduleByMonth internalScheduleByMonth;
	private ExternalScheduleByMonth externalScheduleByMonth;
	private DayOffByMonth dayOffByMonth;
	private EducationByMonth educationByMonth;
	private UpdayByMonth updayByMonth; 
	private CustomerPickerByMonth customerPickerByMonth;
	private CustomerPickerDetailByMonth customerPickerDetailByMonth;
	private HolidayByMonth holidayByMonth; 
	private ScheduleInfo scheduleInfo;
	private ScheduleInfo_time scheduleInfo_time;
	private ScheduleUserList scheduleUserList;
	private ScheduleRA13List scheduleRA13List;
	private ScheduleRA4List scheduleRA4List;
	private ScheduleExpertList scheduleExpertList;
	private ScheduleExpertRealList scheduleExpertRealList;
	private ScheduleExpertRealList_after scheduleExpertRealList_after;
	
	private ScheduleDailyMasterList scheduleDailyMasterList;
	private HolidayRetrieveQuery holidayRetrieveQuery;
	
	protected class ScheduleOfKMACUser extends MappingSqlQuery {
		protected ScheduleOfKMACUser(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleOfKMACUser(DataSource ds) {
			super(ds, SCHEDULE_QUERY + KMAC_WHERE);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
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
		/*
		 * (non-Javadoc)
		 * @see org.springframework.jdbc.object.MappingSqlQuery#mapRow(java.sql.ResultSet, int)
		 */
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleInfoData scheduleInfoData = new ScheduleInfoData();
			scheduleInfoData.setSsn(rs.getString("ssn"));
			scheduleInfoData.setName(rs.getString("name"));
			scheduleInfoData.setProjectName(rs.getString("projectName"));
			scheduleInfoData.setConsultingMajor(rs.getString("consultingMajor"));
			scheduleInfoData.setYear(rs.getString("year"));
			scheduleInfoData.setMonth(rs.getString("month"));
			scheduleInfoData.setDay(rs.getString("day"));
			scheduleInfoData.setFuncField(rs.getString("funcField"));
			scheduleInfoData.setDept(rs.getString("dept"));
			scheduleInfoData.setProcessTypeCode(rs.getString("processTypeCode"));
			scheduleInfoData.setCustomerName(rs.getString("customerName"));
			scheduleInfoData.setCustomerName(rs.getString("customerName"));
			scheduleInfoData.setRelationUser(rs.getString("relationUser"));
			scheduleInfoData.setPlace(rs.getString("place"));
			scheduleInfoData.setStartHour(rs.getString("startHour"));
			scheduleInfoData.setStartMin(rs.getString("startMin"));
			scheduleInfoData.setEndHour(rs.getString("endHour"));
			scheduleInfoData.setEndMin(rs.getString("endMin"));
			scheduleInfoData.setScheduleType(rs.getString("scheduleType"));
			scheduleInfoData.setCompanyPosition(rs.getString("companyPosition"));
			scheduleInfoData.setJobClass(rs.getString("jobClass"));
			return scheduleInfoData;
		}
	}

	protected class ScheduleOfNotKMACUser extends ScheduleOfKMACUser {
		protected ScheduleOfNotKMACUser(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleOfNotKMACUser(DataSource ds) {
			super(ds, SCHEDULE_QUERY + NOT_KMAC_WHERE);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			//declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ScheduleOfNotUser extends ScheduleOfKMACUser {
		protected ScheduleOfNotUser(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleOfNotUser(DataSource ds) {
			super(ds, SCHEDULE_QUERY + NOT_USER_WHERE);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
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
	}

	protected class SMGroupFind extends MappingSqlQuery {
		protected SMGroupFind(DataSource ds, String query) {
			super(ds, query);
		}
		protected SMGroupFind(DataSource ds) {
			super(ds, "select name from  SMGroup where  id=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String deptName = null;
			deptName = rs.getString("name");
			return deptName;
		}
	}

	protected class SanctionDeptFind extends MappingSqlQuery {
		protected SanctionDeptFind(DataSource ds, String query) {
			super(ds, query);
		}
		protected SanctionDeptFind(DataSource ds) {
			super(ds, " select (case when s.cfoSsn is null then 'N' when s.cfoSsn = '' then 'N' else 'Y' end) as dept from work w ,sanctiondoc s where w.refWorkId3 = s.seq and w.id=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dept = null;
			dept = rs.getString("dept");
			return dept;
		}
	}
	
	protected class SanctionDeptFind2 extends MappingSqlQuery {
		protected SanctionDeptFind2(DataSource ds, String query) {
			super(ds, query);
		}
		protected SanctionDeptFind2(DataSource ds) {
			super(ds, " select (case when cfoSsn is null then 'N' when cfoSsn = '' then 'N' else 'Y' end) as dept from sanctiondoc where seq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dept = null;
			dept = rs.getString("dept");
			return dept;
		}
	}
	
	protected class SanctionDeptFind3 extends MappingSqlQuery {
		protected SanctionDeptFind3(DataSource ds, String query) {
			super(ds, query);
		}
		protected SanctionDeptFind3(DataSource ds) {
			super(ds, " select (case when registerDept = '9381' then 'Y' else 'N'  end) as dept from sanctiondoc where seq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dept = null;
			dept = rs.getString("dept");
			return dept;
		}
	}
	
	protected class SanctionDeptFind4 extends MappingSqlQuery {
		protected SanctionDeptFind4(DataSource ds, String query) {
			super(ds, query);
		}
		protected SanctionDeptFind4(DataSource ds) {
			super(ds, " select (case when s.registerDept  = '9381' then 'Y' else 'N'  end) as dept from work w ,sanctiondoc s where w.refWorkId3 = s.seq and w.id= ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dept = null;
			dept = rs.getString("dept");
			return dept;
		}
	}

	protected class ScheduleCreate extends SqlUpdate {
		protected ScheduleCreate(DataSource ds) {
			super(
					ds,
					" INSERT INTO schedule( "
				  + "		ssn, [year], [month], [day],"
				  + "       seq, "
				  + "       startHour, startMin, endHour, endMin,"
				  + "       type, content, customerName, relationUser,"
				  + "       place, secretYN, googleSyncId, workType, createDate)"
				  + " VALUES("
				  + "       ?, ?, ?, ?, "
				  + " 	    dbo.getScheduleSeq(?,?,?,?),"
				  + "       ?, ?, ?, ?,"
				  + "       ?, ?, ?, ?,"
				  + "       ?, ?, ?, ?, GETDATE() ) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
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
		protected int insert(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException {
			return this.update(new Object[]{
										dailyScheduleInfo.getSsn(), 
										dailyScheduleInfo.getYear(),
										dailyScheduleInfo.getMonth(), 
										dailyScheduleInfo.getDay(),
										
										dailyScheduleInfo.getSsn(), 
										dailyScheduleInfo.getYear(),
										dailyScheduleInfo.getMonth(), 
										dailyScheduleInfo.getDay(),
										
										dailyScheduleInfo.getStartHour(), 
										dailyScheduleInfo.getStartMin(),
										dailyScheduleInfo.getEndHour(), 
										dailyScheduleInfo.getEndMin(),
										
										dailyScheduleInfo.getType(),
										dailyScheduleInfo.getContent(), 
										dailyScheduleInfo.getCustomerName(),
										dailyScheduleInfo.getRelationUser(), 
										
										dailyScheduleInfo.getPlace(),
										dailyScheduleInfo.getSecretYN(),
										dailyScheduleInfo.getGoogleSyncId(),
										dailyScheduleInfo.getWorkType()
										});
		}
	}
	
	protected class ScheduleLogCreate extends SqlUpdate {
		protected ScheduleLogCreate(DataSource ds) {
			super(
					ds,
					" INSERT INTO scheduleLog( "
				  + "		viewSsn, viewDate, scheduleSsn, scheduleDate, viewIp)"
				  + " VALUES("
				  + "       ?, GETDATE(), ?, ?, ?) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int insert(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException {
			return this.update(new Object[]{
										dailyScheduleInfo.getViewSsn(), 
										dailyScheduleInfo.getScheduleSsn(),
										dailyScheduleInfo.getScheduleDate(),
										dailyScheduleInfo.getViewIp()
										});
		}
	}
	protected class ScheduleCreate_time extends SqlUpdate {
		protected ScheduleCreate_time(DataSource ds) {
			super(
					ds,
					" INSERT INTO schedule_time( "
				  + "		ssn, [year], [month], [day],"
				  + "       seq, "
				  + "       startHour, startMin, endHour, endMin,"
				  + " 		createDate, approvalSsn, approvalDate, approvalYN)"
				  + " VALUES("
				  + "       ?, ?, ?, ?, "
				  + " 	    dbo.getScheduletimeSeq(?,?,?,?),"
				  + "       ?, ?, ?, ?,"
				  + "       GETDATE(), null, null, 'Y') ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
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
		protected int insert(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException {
			return this.update(new Object[]{
										dailyScheduleInfo.getSsn(), 
										dailyScheduleInfo.getYear(),
										dailyScheduleInfo.getMonth(), 
										dailyScheduleInfo.getDay(),
										
										dailyScheduleInfo.getSsn(), 
										dailyScheduleInfo.getYear(),
										dailyScheduleInfo.getMonth(), 
										dailyScheduleInfo.getDay(),
										
										dailyScheduleInfo.getStartHour(), 
										dailyScheduleInfo.getStartMin(),
										dailyScheduleInfo.getEndHour(), 
										dailyScheduleInfo.getEndMin(),
										});
		}
	}
	
	protected class ScheduleCreate_time2 extends SqlUpdate {
		protected ScheduleCreate_time2(DataSource ds) {
			super(
					ds,
					" INSERT INTO schedule_time( "
				  + "		ssn, [year], [month], [day],"
				  + "       seq, "
				  + "       startHour, startMin, endHour, endMin,"
				  + " 		createDate, approvalSsn, approvalDate, approvalYN)"
				  + " VALUES("
				  + "       ?, ?, ?, ?, "
				  + " 	    dbo.getScheduletimeSeq(?,?,?,?),"
				  + "       ?, ?, ?, ?,"
				  + "       GETDATE(), null, null, 'N') ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
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
		protected int insert(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException {
			return this.update(new Object[]{
										dailyScheduleInfo.getSsn(), 
										dailyScheduleInfo.getYear(),
										dailyScheduleInfo.getMonth(), 
										dailyScheduleInfo.getDay(),
										
										dailyScheduleInfo.getSsn(), 
										dailyScheduleInfo.getYear(),
										dailyScheduleInfo.getMonth(), 
										dailyScheduleInfo.getDay(),
										
										dailyScheduleInfo.getStartHour(), 
										dailyScheduleInfo.getStartMin(),
										dailyScheduleInfo.getEndHour(), 
										dailyScheduleInfo.getEndMin(),
										});
		}
	}
	
	protected class ScheduleDelete extends SqlUpdate {
		protected ScheduleDelete(DataSource ds) {
			super(ds, " Delete from schedule where year=? and month=? and day=? and ssn = ? and seq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
		protected int delete(String year, String month, String day, String ssn, int seq) {
			return update(new Object[]{year, month, day, ssn, seq});
		}
	}

	protected class ScheduleDelete_time extends SqlUpdate {
		protected ScheduleDelete_time(DataSource ds) {
			super(ds, " Delete from schedule_time where year=? and month=? and day=? and ssn = ? and seq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
		protected int delete(String year, String month, String day, String ssn, int seq) {
			return update(new Object[]{year, month, day, ssn, seq});
		}
	}

	protected class ProjectScheduleByMonth extends MappingSqlQuery {
		protected ProjectScheduleByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected ProjectScheduleByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT  PRD.projectCode, PRD.year, PRD.month, PRD.day, " +
					"		  PRD.chargeSsn, PRD.time, P.projectName,        " +
					"         P.customerName, P.customerWorkPName            " +
					" FROM    ProjectReportDetail AS PRD INNER JOIN          " +
					"         Project AS P ON PRD.projectCode = P.projectCode" +
					" WHERE  (PRD.chargeSsn = ?) " +
					"    AND (PRD.year = ?)      " +
					"    AND (PRD.month = ?)     " +
					"    AND (PRD.day LIKE ?)       " +
					" ORDER BY PRD.year, PRD.month, PRD.day, PRD.time"
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyProjectInfo dailyProjectInfo = new DailyProjectInfo();
			
			dailyProjectInfo.setProjectCode(rs.getString("projectCode"));
			dailyProjectInfo.setProjectName(rs.getString("projectName"));
			dailyProjectInfo.setYear(rs.getString("year"));
			dailyProjectInfo.setMonth(rs.getString("month"));
			dailyProjectInfo.setDay(rs.getString("day"));
			dailyProjectInfo.setChargeSsn(rs.getString("chargeSsn"));
			dailyProjectInfo.setTime(rs.getString("time"));
			dailyProjectInfo.setCustomerName(rs.getString("customerName"));
			dailyProjectInfo.setRelationUser(rs.getString("customerWorkPName"));
			
			return dailyProjectInfo;
		}
	}
	
	// 일정관리에서 MM 투입일정 보이도록 변경
	protected class ProjectManpowerByMonth extends MappingSqlQuery {
		protected ProjectManpowerByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected ProjectManpowerByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT  PRD.projectCode, PRD.year, PRD.month, PRD.day, 	" +
					"		  PRD.chargeSsn, '' as time, P.projectName,			" +
					"         P.customerName, P.customerWorkPName				" +
					" FROM    ProjectManpowerDetail AS PRD INNER JOIN			" +
					"         Project AS P ON PRD.projectCode = P.projectCode	" +
					" WHERE  (PRD.chargeSsn = ?) 								" +
					"    AND (PRD.year = ?)      								" +
					"    AND (PRD.month = ?)     								" +
					"    AND (PRD.day LIKE ?)       							" +
					" ORDER BY PRD.year, PRD.month, PRD.day						"
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyProjectInfo dailyProjectInfo = new DailyProjectInfo();
			
			dailyProjectInfo.setProjectCode(rs.getString("projectCode"));
			dailyProjectInfo.setProjectName(rs.getString("projectName"));
			dailyProjectInfo.setYear(rs.getString("year"));
			dailyProjectInfo.setMonth(rs.getString("month"));
			dailyProjectInfo.setDay(rs.getString("day"));
			dailyProjectInfo.setChargeSsn(rs.getString("chargeSsn"));
			dailyProjectInfo.setTime(rs.getString("time"));
			dailyProjectInfo.setCustomerName(rs.getString("customerName"));
			dailyProjectInfo.setRelationUser(rs.getString("customerWorkPName"));
			
			return dailyProjectInfo;
		}
	}
	
	// pjt게시판 
	protected class ProjectPJTByMonth extends MappingSqlQuery {
		protected ProjectPJTByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected ProjectPJTByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT	BBSID as projectCode, " +
					"			datename(yy,workDate) year, datename(mm, workDate) as month, right(workDate, 2) as day,	" + 
					"			WRITERID as chargeSsn, seq as time, subject as projectName,						" +
					"			(select customerName from project where projectCode=BBSID) as customerName,		" + 
					"			dbo.getProjectName(bbsid) as customerWorkPName	" +
					" FROM standardbbs						" +
					" WHERE refSchedule = 'Y'				" +
					"	AND WRITERID = ?					" +
					"	AND datename(yy, workDate) = ?		" +
					"	AND datename(mm, workDate) = ?		" +
					"	AND right(workDate, 2) LIKE ?		" +
					" ORDER BY workDate "								
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyProjectInfo dailyProjectInfo = new DailyProjectInfo();
			
			dailyProjectInfo.setProjectCode(rs.getString("projectCode"));
			dailyProjectInfo.setProjectName(rs.getString("projectName"));
			dailyProjectInfo.setYear(rs.getString("year"));
			dailyProjectInfo.setMonth(rs.getString("month"));
			dailyProjectInfo.setDay(rs.getString("day"));
			dailyProjectInfo.setChargeSsn(rs.getString("chargeSsn"));
			dailyProjectInfo.setTime(rs.getString("time"));
			dailyProjectInfo.setCustomerName(rs.getString("customerName"));
			dailyProjectInfo.setRelationUser(rs.getString("customerWorkPName"));
			return dailyProjectInfo;
		}
	} 
	
	protected class ProjectBoardByMonth extends MappingSqlQuery {
		protected ProjectBoardByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected ProjectBoardByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT	BBSID as projectCode, " +
					"			datename(yy,workDate) year, datename(mm, workDate) as month, right(workDate, 2) as day,	" + 
					"			WRITERID as chargeSsn, seq as time, subject as projectName,						" +
					"			(select customerName from project where projectCode=BBSID) as customerName,		" + 
					"			dbo.getProjectName(bbsid) as customerWorkPName	" +
					" FROM standardbbs						" +
					" WHERE refSchedule = 'Y'				" +
					"	AND WRITERID = ?					" +
					"	AND datename(yy, workDate) = ?		" +
					"	AND datename(mm, workDate) = ?		" +
					"	AND right(workDate, 2) LIKE ?		" +
					" ORDER BY workDate "
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyProjectInfo dailyProjectInfo = new DailyProjectInfo();
			
			dailyProjectInfo.setProjectCode(rs.getString("projectCode"));
			dailyProjectInfo.setProjectName(rs.getString("projectName"));
			dailyProjectInfo.setYear(rs.getString("year"));
			dailyProjectInfo.setMonth(rs.getString("month"));
			dailyProjectInfo.setDay(rs.getString("day"));
			dailyProjectInfo.setChargeSsn(rs.getString("chargeSsn"));
			dailyProjectInfo.setTime(rs.getString("time"));
			dailyProjectInfo.setCustomerName(rs.getString("customerName"));
			dailyProjectInfo.setRelationUser(rs.getString("customerWorkPName"));
			
			return dailyProjectInfo;
		}
	}

	protected class ProjectSchedule extends MappingSqlQuery {
		protected ProjectSchedule(DataSource ds, String query) {
			super(ds, query);
		}
		protected ProjectSchedule(DataSource ds) {
			super(ds, "" +
					" SELECT  PRD.projectCode, PRD.year, PRD.month, PRD.day, " +
					"		  PRD.chargeSsn, PRD.time, P.projectName,        " +
					"         P.customerName, P.customerWorkPName            " +
					" FROM    ProjectReportDetail AS PRD INNER JOIN          " +
					"         Project AS P ON PRD.projectCode = P.projectCode" +
					" WHERE  (PRD.projectCode = ?)	" +
					"    AND (PRD.chargeSsn = ?) 	" +
					"    AND (PRD.year = ?)      	" +
					"    AND (PRD.month = ?)     	" +
					"    AND (PRD.day LIKE ?)       " 
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyProjectInfo dailyProjectInfo = new DailyProjectInfo();
			
			dailyProjectInfo.setProjectCode(rs.getString("projectCode"));
			dailyProjectInfo.setProjectName(rs.getString("projectName"));
			dailyProjectInfo.setYear(rs.getString("year"));
			dailyProjectInfo.setMonth(rs.getString("month"));
			dailyProjectInfo.setDay(rs.getString("day"));
			dailyProjectInfo.setChargeSsn(rs.getString("chargeSsn"));
			dailyProjectInfo.setTime(rs.getString("time"));
			dailyProjectInfo.setCustomerName(rs.getString("customerName"));
			dailyProjectInfo.setRelationUser(rs.getString("customerWorkPName"));
			
			return dailyProjectInfo;
		}
	}
	
	protected class ScheduleByMonth extends MappingSqlQuery {
		protected ScheduleByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT  s.year, s.month, s.day, s.seq, s.startHour, s.startMin,											" + 
					" 					          s.endHour, s.endMin, s.ssn, s.type, s.content, 			   					" +
					" 					          s.customerName, s.relationUser, s.place, s.workType, s.googleSyncId, e.dept   " +   
					" 					  FROM  Schedule s, expertpool e    													" +
					" 					  WHERE (s.ssn = ?)																		" +
					" 						AND (s.ssn = e.ssn)    																" +
					" 					    AND (s.year = ?)    																" +
					" 					    AND (s.month = ?)    																" +
					" 					    AND (s.day LIKE ?)   																" +
					" 					    AND (s.secretYN LIKE ?)    															" +
					" 					  ORDER BY s.year, s.month, s.day, s.startHour, s.startMin								" 
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setYear(rs.getString("year"));
			dailyScheduleInfo.setMonth(rs.getString("month"));
			dailyScheduleInfo.setDay(rs.getString("day"));
			dailyScheduleInfo.setSeq(rs.getInt("seq"));
			dailyScheduleInfo.setStartHour(rs.getString("startHour"));
			dailyScheduleInfo.setStartMin(rs.getString("startMin"));
			dailyScheduleInfo.setEndHour(rs.getString("endHour"));
			dailyScheduleInfo.setEndMin(rs.getString("endMin"));
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			dailyScheduleInfo.setType(rs.getString("type"));
			dailyScheduleInfo.setContent(rs.getString("content"));
			dailyScheduleInfo.setCustomerName(rs.getString("customerName"));
			dailyScheduleInfo.setRelationUser(rs.getString("relationUser"));
			dailyScheduleInfo.setPlace(rs.getString("place"));
			dailyScheduleInfo.setWorkType(rs.getString("workType"));
			dailyScheduleInfo.setGoogleSyncId(rs.getString("googleSyncId"));
			dailyScheduleInfo.setDept(rs.getString("dept"));
			
			return dailyScheduleInfo;
		}
	}
	
	protected class ScheduleByMonth_time extends MappingSqlQuery {
		protected ScheduleByMonth_time(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleByMonth_time(DataSource ds) {
			super(ds, "" +
					" SELECT year, month, day, seq, startHour, startMin, " +
					"		endHour, endMin, ssn " +
					"	FROM schedule_time	" +
					"	WHERE (ssn=?)	" +
					"	AND (year=?)	" +
					"	AND (month=?)	" +
					"	AND (day LIKE ?)	" +
					"	ORDER BY year, month, day, startHour, startMin	" 
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setYear(rs.getString("year"));
			dailyScheduleInfo.setMonth(rs.getString("month"));
			dailyScheduleInfo.setDay(rs.getString("day"));
			dailyScheduleInfo.setSeq(rs.getInt("seq"));
			dailyScheduleInfo.setStartHour(rs.getString("startHour"));
			dailyScheduleInfo.setStartMin(rs.getString("startMin"));
			dailyScheduleInfo.setEndHour(rs.getString("endHour"));
			dailyScheduleInfo.setEndMin(rs.getString("endMin"));
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			
			return dailyScheduleInfo;
		}
	}
	
	protected class ScheduleByMonthCnt_time extends MappingSqlQuery {
		protected ScheduleByMonthCnt_time(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleByMonthCnt_time(DataSource ds) {
			super(ds, "" +
					" select * from (      " +
					" 		  select a.ssn, companyposition, case companyposition  when '06CB' THEN 'CBO' WHEN '07CC' THEN 'CBO' WHEN '09CJ' THEN 'COO' WHEN '08CF' THEN 'COO' ELSE 'NOT' end STR " +
					" 		  from schedule_time  a, expertpool b     " +
					" 		  where a.ssn=b.ssn and b.dept in (select dept from expertpool where ssn=? and (companyposition like ('08CF') or companyposition like ('09CJ')))   " +
					" 		  and year=? and month=? and approvalyn='N' and b.jobclass <> 'H'     " +
					" 		  group by a.ssn, b.dept, companyposition   " +
					" 		   " +
					" 		  union " +
					"  " +
					" 		  select a.ssn, companyposition, case companyposition  when '06CB' THEN 'CBO' WHEN '07CC' THEN 'CBO' WHEN '09CJ' THEN 'COO' WHEN '08CF' THEN 'COO' ELSE 'NOT' end STR   " +
					" 		  from schedule_time  a, expertpool b     " +
					" 		  where a.ssn=b.ssn and  " +
					" 		  b.dept in (case (select dept from expertpool where ssn=? and (companyposition like ('08CF') or companyposition like ('09CJ'))) when '9261' then '9265' end )  " +
					" 		  and year=? and month=? and approvalyn='N' and b.jobclass <> 'H'     " +
					" 		  group by a.ssn, b.dept, companyposition  " +
					"  )t where ssn <> ? "
					 ); 
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			return dailyScheduleInfo;
		}
	}

	protected class ScheduleByConCnt extends MappingSqlQuery {
		protected ScheduleByConCnt(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleByConCnt(DataSource ds) {
			super(ds, "" +
					" select chargessn as ssnCnt from ( 		" 	+
					"	select pm.year, pm.month, pm.chargessn 		" 	+
					"	from ProjectManpowerDetail pm  				" 	+
					"	 											" 	+
					"	union 										"	+
					"	 											"	+
					"	select pr.year, pr.month, pr.chargessn 		" 	+
					"	from ProjectReportDetail pr 				" 	+
					"	)p 											"	+
					"	where p.chargessn=? and year=? and month=?  "
					); 
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setSsnCnt(rs.getString("ssnCnt"));
			return dailyScheduleInfo;
		}
	}
	
	protected class ScheduleByMonthCnt_time_cco extends MappingSqlQuery {
		protected ScheduleByMonthCnt_time_cco(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleByMonthCnt_time_cco(DataSource ds) {
			super(ds, "" +
					" select * from (    " +
					"      select dbo.getexpertpoolname(a.ssn) name, a.ssn, companyposition, case companyposition when '06CB' THEN 'CBO' WHEN '07CC' THEN 'CBO' WHEN '09CJ' THEN 'COO' WHEN '08CF' THEN 'COO' ELSE 'NOT' end title  from schedule_time  a, expertpool b " +
					"      where a.ssn=b.ssn and LEFT(b.dept,3) in ('906','908') "+
					"      and year=? and month=? and approvalyn='N'  AND A.ssn<>? "+
					"      group by a.ssn, companyposition   "+
					" )t where  title='COO'" 
					 ); 
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			return dailyScheduleInfo;
		}
	}
	
	protected class ScheduleByMonthCnt_time_cbo extends MappingSqlQuery {
		protected ScheduleByMonthCnt_time_cbo(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleByMonthCnt_time_cbo(DataSource ds) {
			super(ds, "" +
					" select * from (    "+
					" select a.ssn, companyposition, case companyposition when '06CB' THEN 'CBO' WHEN '07CC' THEN 'CBO' WHEN '09CJ' THEN 'COO' WHEN '08CF' THEN 'COO' ELSE 'NOT' end title  from schedule_time  a, expertpool b "+  
			      	" where a.ssn=b.ssn and LEFT(b.dept,3) in (select LEFT(dept,3) from expertpool where ssn=?) "+
			      	" and year=? and month=? and approvalyn='N'  AND A.ssn<>? and b.jobclass <> 'H'  "+
					" group by a.ssn, companyposition   "+
					" )t where  title='COO'" 
					 ); 
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			return dailyScheduleInfo;
		}
	}
	
	protected class ScheduleByMonthCnt_customer extends MappingSqlQuery {
		protected ScheduleByMonthCnt_customer(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleByMonthCnt_customer(DataSource ds) {
			super(ds, "" +
					" SELECT * FROM eventpopup_chk WHERE chkdate = convert(varchar(10), ?, 126) AND ssn = ? AND eventName = 'comlist_popup1' " 
					 ); 
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			return dailyScheduleInfo;
		}
	}
	
	protected class ScheduleByMonthCnt_customer2 extends MappingSqlQuery {
		protected ScheduleByMonthCnt_customer2(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleByMonthCnt_customer2(DataSource ds) {
			super(ds, "" +
					" SELECT * FROM eventpopup_chk WHERE chkdate = convert(varchar(10), ?, 126) AND ssn = ? AND eventName = 'comlist_popup2' " 
					 ); 
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			return dailyScheduleInfo;
		}
	}
	
	protected class InternalScheduleByMonth extends MappingSqlQuery {
		protected InternalScheduleByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected InternalScheduleByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT  year, month, day,seq, startHour, startMin,		" +
					"         endHour, endMin, ssn, type, [content], workType,	" +
					"         customerName, relationUser, place, googleSyncId   " +
					" FROM  Schedule " +
					" WHERE (ssn = ?) " +
					"   AND (year = ?) " +
					"   AND (month = ?) " +
					"   AND (day LIKE ?)" +
					"   AND (secretYN LIKE ?)" +
					"   AND (workType = 'I')	" +
					"   AND (type <> '휴가')	" +
					" ORDER BY year, month, day, startHour, startMin"
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setYear(rs.getString("year"));
			dailyScheduleInfo.setMonth(rs.getString("month"));
			dailyScheduleInfo.setDay(rs.getString("day"));
			dailyScheduleInfo.setSeq(rs.getInt("seq"));
			dailyScheduleInfo.setStartHour(rs.getString("startHour"));
			dailyScheduleInfo.setStartMin(rs.getString("startMin"));
			dailyScheduleInfo.setEndHour(rs.getString("endHour"));
			dailyScheduleInfo.setEndMin(rs.getString("endMin"));
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			dailyScheduleInfo.setType(rs.getString("type"));
			dailyScheduleInfo.setContent(rs.getString("content"));
			dailyScheduleInfo.setWorkType(rs.getString("workType"));
			dailyScheduleInfo.setCustomerName(rs.getString("customerName"));
			dailyScheduleInfo.setRelationUser(rs.getString("relationUser"));
			dailyScheduleInfo.setPlace(rs.getString("place"));
			dailyScheduleInfo.setGoogleSyncId(rs.getString("googleSyncId"));
			
			return dailyScheduleInfo;
		}
	}
	
	protected class ExternalScheduleByMonth extends MappingSqlQuery {
		protected ExternalScheduleByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected ExternalScheduleByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT  year, month, day,seq, startHour, startMin,		" +
					"         endHour, endMin, ssn, type, [content], workType,	" +
					"         customerName, relationUser, place, googleSyncId   " +
					" FROM  Schedule " +
					" WHERE (ssn = ?) " +
					"   AND (year = ?) " +
					"   AND (month = ?) " +
					"   AND (day LIKE ?)" +
					"   AND (secretYN LIKE ?)" +
					"   AND (workType = 'E')	" +
					"   AND (type <> '휴가')	" +
					" ORDER BY year, month, day, startHour, startMin"
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setYear(rs.getString("year"));
			dailyScheduleInfo.setMonth(rs.getString("month"));
			dailyScheduleInfo.setDay(rs.getString("day"));
			dailyScheduleInfo.setSeq(rs.getInt("seq"));
			dailyScheduleInfo.setStartHour(rs.getString("startHour"));
			dailyScheduleInfo.setStartMin(rs.getString("startMin"));
			dailyScheduleInfo.setEndHour(rs.getString("endHour"));
			dailyScheduleInfo.setEndMin(rs.getString("endMin"));
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			dailyScheduleInfo.setType(rs.getString("type"));
			dailyScheduleInfo.setContent(rs.getString("content"));
			dailyScheduleInfo.setWorkType(rs.getString("workType"));
			dailyScheduleInfo.setCustomerName(rs.getString("customerName"));
			dailyScheduleInfo.setRelationUser(rs.getString("relationUser"));
			dailyScheduleInfo.setPlace(rs.getString("place"));
			dailyScheduleInfo.setGoogleSyncId(rs.getString("googleSyncId"));
			
			return dailyScheduleInfo;
		}
	}
	
	protected class DayOffByMonth extends MappingSqlQuery {
		protected DayOffByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected DayOffByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT  year, month, day,seq, startHour, startMin,		" +
					"         endHour, endMin, ssn, type, [content], 			" +
					"         customerName, relationUser, place, googleSyncId   " +
					" FROM  Schedule " +
					" WHERE (ssn = ?) " +
					"   AND (year = ?) " +
					"   AND (month = ?) " +
					"   AND (day LIKE ?)" +
					"	AND type = '휴가' " +
					" ORDER BY year, month, day, startHour, startMin"
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setYear(rs.getString("year"));
			dailyScheduleInfo.setMonth(rs.getString("month"));
			dailyScheduleInfo.setDay(rs.getString("day"));
			dailyScheduleInfo.setSeq(rs.getInt("seq"));
			dailyScheduleInfo.setStartHour(rs.getString("startHour"));
			dailyScheduleInfo.setStartMin(rs.getString("startMin"));
			dailyScheduleInfo.setEndHour(rs.getString("endHour"));
			dailyScheduleInfo.setEndMin(rs.getString("endMin"));
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			dailyScheduleInfo.setType(rs.getString("type"));
			dailyScheduleInfo.setContent(rs.getString("content"));
			dailyScheduleInfo.setCustomerName(rs.getString("customerName"));
			dailyScheduleInfo.setRelationUser(rs.getString("relationUser"));
			dailyScheduleInfo.setPlace(rs.getString("place"));
			dailyScheduleInfo.setGoogleSyncId(rs.getString("googleSyncId"));
			
			return dailyScheduleInfo;
		}
	}
	
	protected class EducationByMonth extends MappingSqlQuery {
		protected EducationByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected EducationByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT  year, month, day,seq, startHour, startMin,		" +
					"         endHour, endMin, ssn, type, [content], 			" +
					"         customerName, relationUser, place, googleSyncId   " +
					" FROM  Schedule " +
					" WHERE (ssn = ?) " +
					"   AND (year = ?) " +
					"   AND (month = ?) " +
					"   AND (day LIKE ?)" +
					"	AND type = '교육참석' " +
					" ORDER BY year, month, day, startHour, startMin"
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setYear(rs.getString("year"));
			dailyScheduleInfo.setMonth(rs.getString("month"));
			dailyScheduleInfo.setDay(rs.getString("day"));
			dailyScheduleInfo.setSeq(rs.getInt("seq"));
			dailyScheduleInfo.setStartHour(rs.getString("startHour"));
			dailyScheduleInfo.setStartMin(rs.getString("startMin"));
			dailyScheduleInfo.setEndHour(rs.getString("endHour"));
			dailyScheduleInfo.setEndMin(rs.getString("endMin"));
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			dailyScheduleInfo.setType(rs.getString("type"));
			dailyScheduleInfo.setContent(rs.getString("content"));
			dailyScheduleInfo.setCustomerName(rs.getString("customerName"));
			dailyScheduleInfo.setRelationUser(rs.getString("relationUser"));
			dailyScheduleInfo.setPlace(rs.getString("place"));
			dailyScheduleInfo.setGoogleSyncId(rs.getString("googleSyncId"));
			
			return dailyScheduleInfo;
		}
	}
	
	protected class UpdayByMonth extends MappingSqlQuery {
		protected UpdayByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected UpdayByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT  year, month, day,seq, startHour, startMin,		" +
					"         endHour, endMin, ssn, type, [content], 			" +
					"         customerName, relationUser, place, googleSyncId   " +
					" FROM  Schedule " +
					" WHERE (ssn = ?) " +
					"   AND (year = ?) " +
					"   AND (month = ?) " +
					"   AND (day LIKE ?)" +
					"	AND type = 'Up-day' " +
					" ORDER BY year, month, day, startHour, startMin"
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setYear(rs.getString("year"));
			dailyScheduleInfo.setMonth(rs.getString("month"));
			dailyScheduleInfo.setDay(rs.getString("day"));
			dailyScheduleInfo.setSeq(rs.getInt("seq"));
			dailyScheduleInfo.setStartHour(rs.getString("startHour"));
			dailyScheduleInfo.setStartMin(rs.getString("startMin"));
			dailyScheduleInfo.setEndHour(rs.getString("endHour"));
			dailyScheduleInfo.setEndMin(rs.getString("endMin"));
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			dailyScheduleInfo.setType(rs.getString("type"));
			dailyScheduleInfo.setContent(rs.getString("content"));
			dailyScheduleInfo.setCustomerName(rs.getString("customerName"));
			dailyScheduleInfo.setRelationUser(rs.getString("relationUser"));
			dailyScheduleInfo.setPlace(rs.getString("place"));
			dailyScheduleInfo.setGoogleSyncId(rs.getString("googleSyncId"));
			
			return dailyScheduleInfo;
		}
	}	
	
	protected class CustomerPickerByMonth extends MappingSqlQuery {
		protected CustomerPickerByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected CustomerPickerByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT '' as seq, idx, 					 		" +
					"		 p.picker_ssn as ssn,					 	" +
					"        DATENAME(YEAR, c.info_date) as [year],		" +
					"        DATENAME(MONTH, c.info_date) as [month], 	" +
					"        DATENAME(DAY, c.info_date) as [day], 		" +
					"        CONVERT(date, c.info_date) as infoDate		" +
					" FROM  Customer c, customer_pickers p				" +
					" WHERE 1=1							 				" +
					"   AND	c.idx = p.customer_idx						" +
					"   AND	c.embbsMethod = '1'							" +
					"   AND	(p.picker_ssn = ?)						 	" +
					"   AND (c.info_date >= ?)							" +
					"   AND (c.info_date <  ?)							" +
					" ORDER BY c.info_date								"
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustomerPickerInfo customerPickerInfo = new CustomerPickerInfo();
			
			customerPickerInfo.setYear(rs.getString("year"));
			customerPickerInfo.setMonth(rs.getString("month"));
			customerPickerInfo.setDay(rs.getString("day"));
			customerPickerInfo.setIdx(rs.getString("idx"));
			customerPickerInfo.setPickerSsn(rs.getString("ssn"));
			customerPickerInfo.setInfoDate(rs.getString("infoDate"));
			
			return customerPickerInfo;
		}
	}
	
	protected class CustomerPickerDetailByMonth extends MappingSqlQuery {
		protected CustomerPickerDetailByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected CustomerPickerDetailByMonth(DataSource ds) {
			super(ds, "" +
					" SELECT '' as seq, idx, 					 		" +
					"		 p.picker_ssn as ssn,					 	" +
					"        DATENAME(YEAR, c.info_date) as [year],		" +
					"        DATENAME(MONTH, c.info_date) as [month], 	" +
					"        DATENAME(DAY, c.info_date) as [day], 		" +
					"        CONVERT(date, c.info_date) as infoDate,	" +
					"		 subject, customerName,			 			" +
					"        embbsName as customerWorkName 				" +
					" FROM  Customer c, customer_pickers p				" +
					" WHERE 1=1							 				" +
					"   AND	c.idx = p.customer_idx						" +
					"   AND	c.embbsMethod = '1'							" +
					"   AND	(p.picker_ssn = ?)						 	" +
					"   AND (c.info_date >= ?)							" +
					"   AND (c.info_date <  ?)							" +
					" ORDER BY c.info_date								"
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustomerPickerInfo customerPickerInfo = new CustomerPickerInfo();
			
			customerPickerInfo.setYear(rs.getString("year"));
			customerPickerInfo.setMonth(rs.getString("month"));
			customerPickerInfo.setDay(rs.getString("day"));
			customerPickerInfo.setIdx(rs.getString("idx"));
			customerPickerInfo.setPickerSsn(rs.getString("ssn"));
			customerPickerInfo.setInfoDate(rs.getString("infoDate"));
			customerPickerInfo.setSubject(rs.getString("subject"));
			customerPickerInfo.setCustomerName(rs.getString("customerName"));
			customerPickerInfo.setCustomerWorkName(rs.getString("customerWorkName"));
			
			return customerPickerInfo;
		}
	}
	
	protected class HolidayByMonth extends MappingSqlQuery {
		protected HolidayByMonth(DataSource ds, String query) {
			super(ds, query);
		}
		protected HolidayByMonth(DataSource ds) {
			super(ds, "" + 
					" SELECT [hyear], [hmonth], [hday], [hName] " +
					" FROM [newKmacPms].[dbo].[holliday] " +
					" WHERE hyear=? AND hmonth=? " +
					" ORDER BY hday"
				);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			HolidayInfo holidayInfo = new HolidayInfo();
			
			holidayInfo.setYear(rs.getString("hyear"));
			holidayInfo.setMonth(rs.getString("hmonth"));
			holidayInfo.setDay(rs.getString("hday"));
			holidayInfo.setHolidayName(rs.getString("hName"));
			
			return holidayInfo;
		}
	}
	
	protected class HolidayRetrieveQuery extends MappingSqlQuery{
		protected HolidayRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected HolidayRetrieveQuery(DataSource ds) {
			super(ds, "	SELECT count(*) holiday FROM holliday WHERE hyear=left(?,4) and hmonth=SUBSTRING(?,5,2) and hday = right(?,2)	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Integer(rs.getInt("holiday"));
		}
	}
	
	protected class ScheduleInfo extends MappingSqlQuery {
		protected ScheduleInfo(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleInfo(DataSource ds) {
			super(ds, "" +
					" SELECT  year, month, day,seq, startHour, startMin, " +
					"         endHour, endMin, ssn, type, [content], " +
					"         customerName, relationUser, place , secretYN, googleSyncId, workType     " +
					" FROM  Schedule " +
					" WHERE (ssn = ?) " +
					"   AND (year = ?) " +
					"   AND (month = ?) " +
					"   AND (day = ?)" +
					"   AND (seq = ?) " +
					" ORDER BY year, month, day, startHour, startMin"
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setYear(rs.getString("year"));
			dailyScheduleInfo.setMonth(rs.getString("month"));
			dailyScheduleInfo.setDay(rs.getString("day"));
			dailyScheduleInfo.setSeq(rs.getInt("seq"));
			dailyScheduleInfo.setStartHour(rs.getString("startHour"));
			dailyScheduleInfo.setStartMin(rs.getString("startMin"));
			dailyScheduleInfo.setEndHour(rs.getString("endHour"));
			dailyScheduleInfo.setEndMin(rs.getString("endMin"));
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			dailyScheduleInfo.setType(rs.getString("type"));
			dailyScheduleInfo.setContent(rs.getString("content"));
			dailyScheduleInfo.setCustomerName(rs.getString("customerName"));
			dailyScheduleInfo.setRelationUser(rs.getString("relationUser"));
			dailyScheduleInfo.setPlace(rs.getString("place"));
			dailyScheduleInfo.setSecretYN(rs.getString("secretYN"));
			dailyScheduleInfo.setGoogleSyncId(rs.getString("googleSyncId"));
			dailyScheduleInfo.setWorkType(rs.getString("workType"));
			
			return dailyScheduleInfo;
		}
	}
	
	protected class ScheduleInfo_time extends MappingSqlQuery {
		protected ScheduleInfo_time(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleInfo_time(DataSource ds) {
			super(ds, "" +
					" SELECT year, month, day, seq, startHour, startMin, " +
					"		endHour, endMin, ssn " +
					"	FROM schedule_time	" +
					"	WHERE (ssn=?)	" +
					"	AND (year=?)	" +
					"	AND (month=?)	" +
					"	AND (day = ?)	" +
					"   AND (seq = ?) " +
					"	ORDER BY year, month, day, startHour, startMin	" 
				  );
			
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
			
			dailyScheduleInfo.setYear(rs.getString("year"));
			dailyScheduleInfo.setMonth(rs.getString("month"));
			dailyScheduleInfo.setDay(rs.getString("day"));
			dailyScheduleInfo.setSeq(rs.getInt("seq"));
			dailyScheduleInfo.setStartHour(rs.getString("startHour"));
			dailyScheduleInfo.setStartMin(rs.getString("startMin"));
			dailyScheduleInfo.setEndHour(rs.getString("endHour"));
			dailyScheduleInfo.setEndMin(rs.getString("endMin"));
			dailyScheduleInfo.setSsn(rs.getString("ssn"));
			
			return dailyScheduleInfo;
		}
	}
	
	protected class ScheduleUserList extends MappingSqlQuery {
		protected ScheduleUserList(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleUserList(DataSource ds) {
			super(ds, "" +
					" SELECT  userId,ssn, isNull(groupName,'기타') groupName, groupParentId, (case when posname = 'CSO' then '8' else groupSeq end) as groupSeq	   	" +
					"		, (case groupId when '9280' then '9281' when '922A' then '92101' WHEN '921A' THEN '92001' WHEN '923A' THEN '92301' WHEN '924A' THEN '92401' else groupId end) as groupId, userName, replace(replace(isNull(posName,'기타'),'CCO','KMAC'),'CSO','부문장') AS posName					" +      	
					"		, CASE left(posName,3)                 	" +					
					"			WHEN 'CEO'		THEN 'CEO'			" +
					"			WHEN 'CBO'		THEN '부문장'			" +
					"			WHEN 'CSO'		THEN '부문장'			" +
					"			WHEN 'CCO'		THEN 'CCO'			" + 	
					"			WHEN '자문위'		THEN 'Advisor'		" +
					"			WHEN '엑스퍼'		THEN '엑스퍼트'		" +
					"			WHEN '비서'		THEN '비서실'			" +
					"	  		ELSE                             	" +
					"				CASE IsNull(groupParentId,'')	" +
					"					WHEN '2000' THEN '부문장'  	" +
					"					ELSE replace(replace(replace(replace(replace(replace(LTrim(groupName),'센터',''),'본부',''),' 상임',''),'상임',''),'기획실','기획'),'부문','')     	" +
					"				END                          	" +
				 	"		  END  AS labelName                  	" +
					"		, CASE IsNull((case when posName = 'CSO' then '7080' else groupParentId end),'')      	" +
					"			WHEN '' THEN 0                   	" +
					"			WHEN '2030' THEN 1               	" + 
					"			WHEN '2000' THEN 2               	" +
					"			WHEN '7080' THEN 2               	" +
					"			WHEN '2720' THEN 3               	" +
					"			ELSE                             	" +
					"				CASE posName					" +
					"					WHEN 'IM' THEN 4			" +
					"					ELSE 5						" +
					"				END                            	" +
				 	"		 END  AS OrdSeq                   		" +
					" FROM    vUserInfo                          	" +
					" WHERE (enabled = '1') AND (jobclass in (?, 'B', 'H'))      	" +
					"	AND userId IS NOT NULL     					" +					
					"	AND ssn NOT IN ('E000817', 'A001559', 'G003279')		" +
					" AND poscode <> '64GT'	 " +
					" AND jobclass <> (case ? when 'J' then 'H' else '' end) "+
					" AND jobclass <> (case ? when 'J' then 'B' else '' end) "+
					" AND groupId not in ('9264','9265')  "+
					" ORDER BY OrdSeq,groupSeq,groupId,posCode,posName,userName		"
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleUserInfo scheduleUserInfo = new ScheduleUserInfo();
			
			scheduleUserInfo.setSsn(rs.getString("ssn"));
			scheduleUserInfo.setUserName(rs.getString("userName"));
			scheduleUserInfo.setGroupName(rs.getString("labelName"));
			
			return scheduleUserInfo;
		}
	}
	
	protected class ScheduleRA13List extends MappingSqlQuery {
		protected ScheduleRA13List(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleRA13List(DataSource ds) {
			super(ds, "" +
					" SELECT  userId,ssn, isNull(groupName,'기타') groupName, groupParentId	" +
					"		, groupId, userName, replace(isNull(posName,'기타'),'CCO','KMAC') AS posName				" +      	
					"		, CASE left(posName,3)                 	" +					
					"			WHEN 'CEO'		THEN 'CEO'			" +
					"			WHEN 'CBO'		THEN 'CBO'			" +
					"			WHEN 'CCO'		THEN 'CCO'			" + 	
					"			WHEN '자문위'		THEN 'Advisor'		" +
					"			WHEN '엑스퍼'		THEN '엑스퍼트'		" +
					"			WHEN '비서'		THEN '비서실'			" +
					"	  		ELSE                             	" +
					"				CASE IsNull(groupParentId,'')	" +
					"					WHEN '2000' THEN 'CBO'  	" +
					"					ELSE replace(replace(replace(LTrim(groupName),'센터',''),'본부',''),'기획실','기획')    	" +
					"				END                          	" +
				 	"		  END  AS labelName                  	" +
					"		, CASE IsNull(groupParentId,'')      	" +
					"			WHEN '' THEN 0                   	" +
					"			WHEN '2030' THEN 1               	" + 
					"			WHEN '2000' THEN 2               	" +
					"			WHEN '7080' THEN 2               	" +
					"			WHEN '2720' THEN 3               	" +
					"			ELSE                             	" +
					"				CASE posName					" +
					"					WHEN 'IM' THEN 4			" +
					"					ELSE 5						" +
					"				END                            	" +
				 	"		 END  AS OrdSeq                   		" +
					" FROM    vUserInfo                          	" +
					" WHERE (enabled = '1') AND (jobclass = ?)   	" +
					"	AND poscode <> '64GT'		   				" +
					"	AND userId IS NOT NULL     					" +
					" ORDER BY OrdSeq,groupSeq,groupId,posCode,posName,userName		"
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleUserInfo scheduleUserInfo = new ScheduleUserInfo();
			
			scheduleUserInfo.setSsn(rs.getString("ssn"));
			scheduleUserInfo.setUserName(rs.getString("userName"));
			scheduleUserInfo.setGroupName(rs.getString("labelName"));
			
			return scheduleUserInfo;
		}
	}
	
	protected class ScheduleRA4List extends MappingSqlQuery {
		protected ScheduleRA4List(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleRA4List(DataSource ds) {
			super(ds, "" +
					" SELECT  userId,ssn, isNull(groupName,'기타') groupName, groupParentId	" +
					"		, groupId, userName, replace(isNull(posName,'기타'),'CCO','KMAC') AS posName				" +      	
					"		, CASE left(posName,3)                 	" +					
					"			WHEN 'CEO'		THEN 'CEO'			" +
					"			WHEN 'CBO'		THEN 'CBO'			" +
					"			WHEN 'CCO'		THEN 'CCO'			" + 	
					"			WHEN '자문위'		THEN 'Advisor'		" +
					"			WHEN '엑스퍼'		THEN '엑스퍼트'		" +
					"			WHEN '비서'		THEN '비서실'			" +
					"	  		ELSE                             	" +
					"				CASE IsNull(groupParentId,'')	" +
					"					WHEN '2000' THEN 'CBO'  	" +
					"					ELSE replace(replace(replace(LTrim(groupName),'센터',''),'본부',''),'기획실','기획')    	" +
					"				END                          	" +
				 	"		  END  AS labelName                  	" +
					"		, CASE IsNull(groupParentId,'')      	" +
					"			WHEN '' THEN 0                   	" +
					"			WHEN '2030' THEN 1               	" + 
					"			WHEN '2000' THEN 2               	" +
					"			WHEN '7080' THEN 2               	" +
					"			WHEN '2720' THEN 3               	" +
					"			ELSE                             	" +
					"				CASE posName					" +
					"					WHEN 'IM' THEN 4			" +
					"					ELSE 5						" +
					"				END                            	" +
				 	"		 END  AS OrdSeq                   		" +
					" FROM    vUserInfo                          	" +
					" WHERE (enabled = '1') AND (jobclass = ?)   	" +
					"	AND poscode = '64GT'		   				" +
					"	AND userId IS NOT NULL     					" +	
					" ORDER BY OrdSeq,groupSeq,groupId,posCode,posName,userName		"
				  );
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleUserInfo scheduleUserInfo = new ScheduleUserInfo();
			
			scheduleUserInfo.setSsn(rs.getString("ssn"));
			scheduleUserInfo.setUserName(rs.getString("userName"));
			scheduleUserInfo.setGroupName(rs.getString("labelName"));
			
			return scheduleUserInfo;
		}
	}
	
	protected class ScheduleExpertList extends MappingSqlQuery {
		protected ScheduleExpertList(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleExpertList(DataSource ds) {
			super(ds, "" +
					" SELECT  userId, ssn, isNull(groupName,'기타') groupName, groupParentId	" +
					"		, groupId, userName, isNull(posName,'기타') posName				" +      	
					"		, '엑스퍼트'  AS labelName               " +
					" FROM    vUserInfo                          	" +
					" WHERE (enabled = '1') AND (jobclass = ?)   	" +
					"	AND userId IS NOT NULL     					" +
					"	AND ssn in (		     					" +
					"		select chargeSsn						" +
					"		from ProjectReportDetail				" +
					"		where year = ?	 						" +
					"		and month = ?							" +
					"		group by chargeSsn						" +
					"		union									" +
					"		select chargeSsn						" +
					"		from ProjectManpowerDetail				" +
					"		where year = ?	 						" +
					"		and month = ?							" +
					"		group by chargeSsn						" +
					"	)											" +
					" ORDER BY posCode,userName						"
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleUserInfo scheduleUserInfo = new ScheduleUserInfo();
			
			scheduleUserInfo.setSsn(rs.getString("ssn"));
			scheduleUserInfo.setUserName(rs.getString("userName"));
			scheduleUserInfo.setGroupName(rs.getString("labelName"));
			
			return scheduleUserInfo;
		}
	}

	protected class ScheduleExpertRealList extends MappingSqlQuery {
		protected ScheduleExpertRealList(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleExpertRealList(DataSource ds) {
			super(ds, "" +
					"  SELECT G.* ,p.projectname, dbo.getexpertpoolName(m.ssn) as PMname, replace(replace(replace(LTrim(sg.description),'센터',''),'본부',''),'기획실','기획') as labelName, sg.description groupName, pmd.ex1, pmd2.ex2    " +   
					"					  FROM 																								  																						    	" +   
					"					  (																									  																						    	" +   
					"					  	SELECT 																	  																													    " +   
					"					  			e.userId,e.ssn, e.groupParentId	   							  																															" +       
					"					  		, e.groupId, e.userName, replace(isNull(posName,'기타'),'CCO','KMAC') AS posName, e.posCode, ex.Year, ex.month,		 	           															" +   					    																		  																							    
					"					  		(																							  																							    " +   
					"					  			SELECT top 1 p.projectCode FROM project p, projectMember m 								  																							    " +   
					"					  			WHERE p.projectcode = m.projectcode 													  																							    " +   
					"					  			AND realStartDate <= ex.year  +  '-'  +  ex.month   + '-'   + '32'															  															" +								       
					"					  			AND m.trainingYN = 'Y'																	  																							    " +   
					"					  			and p.projectName is not null 															  																							    " +   
					"					  			AND m.ssn = e.ssn					    											  																							    	" +   
					"					  			order by e.ssn, realstartdate desc, projectcode desc									  																							    " +   
					"					  		) as maxProjectcode 																		  																							    " +   
					"					  	FROM vUserInfo e, vUserInfo_exp ex  																				  																			" +   				    
					"					  	WHERE e.jobClass = ? and e.ssn = ex.ssn															  																							" +       
					"					  ) G																						  																							    		" +   
					"					  Join project P on g.maxProjectcode = p.projectcode 												  																							    " +   
					"					  join projectmember M on g.maxProjectcode = M.projectcode and M.role='PM' 							  																						    	" +   
					"					  join smgroup sg on p.runningdeptcode = sg.id 																																						" +
					"					  left join																																														   	" +
					"  					   (																																															   	" +
					"  						select chargessn, year, month, '1' as ex1 From projectManpowerdetail																														   	" +
					"  						where year=? and month=?																																								   	" +
					"  						group by chargessn, year, month																																								   	" +
					"  					  )pmd																																															   	" +
					"  					   on G.ssn = pmd.chargessn 																																									   	" +
					"  					  left join																																														   	" +
					"  					   (																																															   	" +
					"  						select chargessn, year, month, '1' as ex2 From projectReportDetail																															   	" +
					"  						where year=? and month=?																																								   	" +
					"  						group by chargessn, year, month																																								   	" +
					"  					  )pmd2																																															   	" +
					"  					  on G.ssn = pmd2.chargessn 																  																						    	   		" +
					"					  WHERE maxProjectcode is not null and sg.description not like '%팀%' and G.year = ? and G.month = ?																							   "  +
					"					  and 	(pmd.ex1 = '1' or pmd2.ex2 = '1')																			  							   						    						" +
					"					  order by sg.seq, sg.id, userName														" 
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleUserInfo scheduleUserInfo = new ScheduleUserInfo();
			
			scheduleUserInfo.setSsn(rs.getString("ssn"));
			scheduleUserInfo.setUserName(rs.getString("userName"));
			scheduleUserInfo.setGroupName(rs.getString("labelName"));
			
			return scheduleUserInfo;
		}
	}
	
	protected class ScheduleExpertRealList_after extends MappingSqlQuery {
		protected ScheduleExpertRealList_after(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleExpertRealList_after(DataSource ds) {
			super(ds, "" +
					" SELECT G.* ,p.projectname, dbo.getexpertpoolName(m.ssn) as PMname, replace(replace(replace(LTrim(sg.description),'센터',''),'본부',''),'기획실','기획') as labelName, sg.description groupName, pmd.ex1, pmd2.ex2 " +
					" 					   FROM 																								  																						" +    
					" 					   (																									  																						" +    
					" 					   	SELECT 																	  																													" +    
					" 					   			userId,ssn, groupParentId	   							  																															" +    
					" 					   		, groupId, userName, replace(isNull(posName,'기타'),'CCO','KMAC') AS posName, posCode		 	           																				" +    
					" 					   		, CASE IsNull(groupParentId,'')      	   													  																							" +    
					" 					   			WHEN '' THEN 0                   	   													  																							" +    
					" 					   			WHEN '2030' THEN 1               	    												  																							" +    
					" 					   			WHEN '2000' THEN 2               	   													  																							" +    
					" 					   			WHEN '7080' THEN 2               	   													  																							" +    
					" 					   			WHEN '2720' THEN 3               	   													  																							" +    
					" 					   			ELSE                             	   													  																							" +    
					" 					   				CASE posName					   													  																							" +    
					" 					   					WHEN 'IM' THEN 4			   													  																							" +    
					" 					   					ELSE 5						   													  																							" +    
					" 					   				END                            	   													  																							" +    
					" 					   		END  AS OrdSeq,																				  																							" +    
					" 					   		(																							  																							" +    
					" 					   			SELECT top 1 p.projectCode FROM project p, projectMember m 								  																							" +    
					" 					   			WHERE p.projectcode = m.projectcode 													  																							" +    
					" 					   			AND p.projectState = '3' 																  																							" +    
					" 					   			AND realEndDate > getdate() 															  																							" +    
					" 					   			AND m.trainingYN = 'Y'																	  																							" +    
					" 					   			and p.projectName is not null 															  																							" +    
					" 					   			AND m.ssn = e.ssn						    											  																							" +    
					" 					   			order by e.ssn, realstartdate desc, projectcode desc									  																							" +    
					" 					   		) as maxProjectcode																		  																							    " +
					" 					   	FROM vUserInfo e  																				  																							" +    
					" 					   	WHERE e.jobClass = ?																		  																							    " +
					" 					   ) G																								  																							" +    
					" 					   Join project P on g.maxProjectcode = p.projectcode 												  																							" +    
					" 					   join projectmember M on g.maxProjectcode = M.projectcode and M.role='PM' 							  																						" +    
					" 					   join smgroup sg on p.runningdeptcode = sg.id																																					" +
					" 					  left join																																														" +
					" 					   (																																															" +
					" 						select chargessn, year, month, '1' as ex1 From projectManpowerdetail																														" +
					" 						where year=? and month=?																																								" +
					" 						group by chargessn, year, month																																								" +
					" 					  )pmd																																															" +
					" 					   on G.ssn = pmd.chargessn 																																									" +
					" 					  left join																																														" +
					" 					   (																																															" +
					" 						select chargessn, year, month, '1' as ex2 From projectReportDetail																															" +
					" 						where year=? and month=?																																								" +
					" 						group by chargessn, year, month																																								" +
					" 					  )pmd2																																															" +
					" 					  on G.ssn = pmd2.chargessn 														  																						    				" +
					" 					   WHERE maxProjectcode is not null																																								" +
					" 					   and 	(pmd.ex1 = '1' or pmd2.ex2 = '1')														  																							    " +
					" 					   order by sg.seq, sg.id, userName																																								" 
					);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleUserInfo scheduleUserInfo = new ScheduleUserInfo();
			
			scheduleUserInfo.setSsn(rs.getString("ssn"));
			scheduleUserInfo.setUserName(rs.getString("userName"));
			scheduleUserInfo.setGroupName(rs.getString("labelName"));
			
			return scheduleUserInfo;
		}
	}

	
	protected class ScheduleDailyMasterList extends MappingSqlQuery {
		protected ScheduleDailyMasterList(DataSource ds, String query) {
			super(ds, query);
		}
		protected ScheduleDailyMasterList(DataSource ds) {
			super(ds, "select userId, ssn, groupName, groupParentId, groupId, userName, posName, labelName, OrdSeq, groupSeq, posCode,																																															\n" + 
					" 		replace(sum(D01),0,'') D01, replace(sum(D02),0,'') D02,	replace(sum(D03),0,'') D03,	replace(sum(D04),0,'') D04,	replace(sum(D05),0,'') D05,	replace(sum(D06),0,'') D06,	replace(sum(D07),0,'') D07,	      \n" +
					" 		replace(sum(D08),0,'') D08,	replace(sum(D09),0,'') D09,	replace(sum(D10),0,'') D10,	replace(sum(D11),0,'') D11,	replace(sum(D12),0,'') D12,	replace(sum(D13),0,'') D13,	replace(sum(D14),0,'') D14,	      \n" +
					" 		replace(sum(D15),0,'') D15,	replace(sum(D16),0,'') D16,	replace(sum(D17),0,'') D17,	replace(sum(D18),0,'') D18,	replace(sum(D19),0,'') D19,	replace(sum(D20),0,'') D20,	replace(sum(D21),0,'') D21,	      \n" +
					" 		replace(sum(D22),0,'') D22,	replace(sum(D23),0,'') D23,	replace(sum(D24),0,'') D24,	replace(sum(D25),0,'') D25,	replace(sum(D26),0,'') D26,	replace(sum(D27),0,'') D27,	replace(sum(D28),0,'') D28,	      \n" +
					" 		replace(sum(D29),0,'') D29,	replace(sum(D30),0,'') D30,	replace(sum(D31),0,'') D31                                                                                                                        \n" +
					" from (                                                                                                                                                                                                        \n" +
					" 	select a.userId, a.ssn, a.groupName, a.groupParentId, a.groupId, a.userName, a.posName, a.labelName, a.OrdSeq, a.groupSeq, a.posCode, a.scheduleType,                                                      \n" +
					" 			D01, 	D02,	D03,	D04,	D05,	D06,	D07,	D08,	D09,	D10,	D11,	D12,	D13,	D14,	D15,	D16,	D17,	D18,	                                                                                            \n" +
					" 			D19,	D20,	D21,	D22,	D23,	D24,	D25,	D26,	D27,	D28,	D29,	D30,	D31                                                                                                                             \n" +
					" 	from (                                                                                                                                                                                                      \n" +
					" 		select userId, ssn, groupName, groupParentId, groupId, userName, posName, labelName, OrdSeq, groupSeq, posCode, scheduleType                                                                              \n" +
					" 		from (                                                                                                                                                                                                    \n" +
					" 			select userId, ssn, groupName, groupParentId, groupId, userName, posName, labelName, OrdSeq, groupSeq, posCode                                                                                          \n" +
					" 			from (                                                                                                                                                                                                  \n" +
					" 				SELECT jobclass, userId,ssn, isNull(groupName,'기타') groupName, groupParentId	                                                                                                                                \n" +
					" 					, groupId, userName, isNull(posName,'기타') posName				      	                                                                                                                                  \n" +
					" 					, CASE left(posName,3)                 						                                                                                                                                                  \n" +
					" 						WHEN 'CEO'		THEN 'CEO'			                                                                                                                                                                    \n" +
					" 						WHEN 'CBO'		THEN 'CBO'			                                                                                                                                                                    \n" +
					" 						WHEN 'CCO'		THEN 'CCO'			                                                                                                                                                                    \n" +
					" 						WHEN 'CIO'		THEN 'CIO'                                                                                                                                                                          \n" +
					" 						WHEN '본부장'	THEN 'CBO'           	                                                                                                                                                              \n" +
					" 						WHEN '센터장'	THEN 'CBO'           	                                                                                                                                                              \n" +
					" 						WHEN '자문위' 	THEN 'Advisor'		                                                                                                                                                                \n" +
					" 						WHEN '엑스퍼' 	THEN '엑스퍼트'		                                                                                                                                                                \n" +
					"   						ELSE                             	                                                                                                                                                              \n" +
					" 							CASE IsNull(groupParentId,'')	                                                                                                                                                                  \n" +
					" 								WHEN '2000' THEN 'CBO'  	                                                                                                                                                                    \n" +
					" 								ELSE replace(replace(replace(LTrim(groupName),'센터',''),'본부',''),'기획실','기획')    	                                                                                                    \n" +
					" 							END                          	                                                                                                                                                                  \n" +
					" 					  END  AS labelName                  	                                                                                                                                                              \n" +
					" 					, CASE IsNull(groupParentId,'')      	                                                                                                                                                              \n" +
					" 						WHEN '' THEN 0                   	                                                                                                                                                                \n" +
					" 						WHEN '2030' THEN 1               	                                                                                                                                                                \n" +
					" 						WHEN '2000' THEN 2               	                                                                                                                                                                \n" +
					" 						WHEN '7080' THEN 2               	                                                                                                                                                                \n" +
					" 						WHEN '2720' THEN 3               	                                                                                                                                                                \n" +
					" 						ELSE                             	                                                                                                                                                                \n" +
					" 							CASE posName					                                                                                                                                                                          \n" +
					" 								WHEN 'IM' THEN 4			                                                                                                                                                                        \n" +
					" 								ELSE 5						                                                                                                                                                                            \n" +
					" 							END                            	                                                                                                                                                                \n" +
					" 					 END  AS OrdSeq,                                                                                                                                                                                    \n" +
					" 					 groupSeq,                                                                                                                                                                                          \n" +
					" 					 posCode      		                                                                                                                                                                                  \n" +
					" 				FROM    vUserInfo                          	                                                                                                                                                          \n" +
					" 				WHERE (enabled = '1') AND (jobclass in (?, 'B', 'H'))    	                                                                                                                                                          \n" +
					" 				AND jobclass not in (case ? when 'J' then 'B' else '' end)					\n" +
					" 				AND jobclass not in (case ? when 'J' then 'H' else '' end)					\n" +
					" 				AND jobclass not in (case ? when 'N' then 'B' else '' end)					\n" +
					" 				AND jobclass not in (case ? when 'J' then 'H' else '' end)					\n" +
					" 				AND jobclass not in (case ? when 'C' then 'B' else '' end)					\n" +
					" 				AND jobclass not in (case ? when 'J' then 'H' else '' end)					\n" +
					" 				AND userId IS NOT NULL     					                                                                                                                                                                  \n" +
					" 				AND ssn NOT IN ('E000817', 'A001559', 'G003279') -- 권해상, 이유택 자문위원, 미디어 김수연							 \n" +		
					" 				AND groupId not in ('9077') 											\n" +
					" 			) a                                                                                                                                                                                                     \n" +
					" 		) a cross join (                                                                                                                                                                                          \n" +
					" 			select [type] as scheduleType from ScheduleTypeInfo                                                                                                                                                     \n" +
					" 		) b                                                                                                                                                                                                       \n" +
					" 	) a left outer join (                                                                                                                                                                                       \n" +
					" 		select                                                                                                                                                                                                    \n" +
					" 			YEAR, MONTH, SSN, TYPE,                                                                                                                                                                                 \n" +
					" 			D01, 	D02,	D03,	D04,	D05,	D06,	D07,	D08,	D09,	D10,	D11,	D12,	D13,	D14,	D15,	D16,	D17,	D18,	D19,	D20,	D21,	D22,	                                                                    \n" +
					" 			D23,	D24,	D25,	D26,	D27,	D28,	D29,	D30,	D31                                                                                                                                                     \n" +
					" 		from (                                                                                                                                                                                                    \n" +
					" 			SELECT  year, month, chargeSsn as ssn, '고객정보' as type, [01] as D01, [02] as D02, [03] as D03, [04] as D04, [05] as D05, [06] as D06, [07] as D07, [08] as D08, [09] as D09,                         \n" +
					" 					[10] as D10, [11] as D11, [12] as D12, [13] as D13, [14] as D14, [15] as D15, [16] as D16, [17] as D17, [18] as D18, [19] as D19, [20] as D20, [21] as D21, [22] as D22, [23] as D23,               \n" +
					" 					[24] as D24, [25] as D25, [26] as D26, [27] as D27, [28] as D28, [29] as D29, [30] as D30, [31] as D31                                                                                              \n" +
					" 			FROM (                                                                                                                                                                                                  \n" +
					" 				SELECT year, month, day, ssn as chargeSsn, 4000 cnt                                                                                                                                     							\n" +
					" 				from (                                                                                                                                                                                                \n" +
					" 					SELECT	DATENAME(YEAR, info_date) as [year],                                                                                                                                                        \n" +
					" 							DATENAME(MONTH, info_date) as [month],                                                                                                                                                          \n" +
					" 							RIGHT(CONVERT(varchar,info_date,112),2) as [day],                                                                                                                                               \n" +
					" 							picker_ssn as ssn,                                                                                                                                                                                   \n" +
					" 							CONVERT(date,info_date) as infoDate                                                                                                                                                             \n" +
					" 					FROM  customer_pickers                                                                                                                                                                                      \n" +
					" 					WHERE 1=1                                                                                                                                                                                           \n" +
					"					AND info_date >= ?																																													\n" + 
					"					AND info_date <  dateadd(day, 1, ?)																																									\n" + 
					" 					AND	embbsMethod = '1'                                                                                                                                                                               \n" +
					" 				) a                                                                                                                                                                                                   \n" +
					" 				group by year, month, day, ssn                                                                                                                                                                        \n" +
					" 			) AS Ab                                                                                                                                                                                                 \n" +
					" 			PIVOT (                                                                                                                                                                                                 \n" +
					" 				sum(cnt) FOR day IN ([01], [02], [03], [04], [05], [06], [07], [08], [09], [10], [11], [12], [13], [14], [15],                                                                                        \n" +
					" 				[16], [17], [18], [19], [20], [21], [22], [23], [24], [25], [26], [27], [28], [29], [30], [31])                                                                                                       \n" +
					" 			) Ab                                                                                                                                                                                                    \n" +
					" 		                                                                                                                                                                                                          \n" +
					" 			UNION ALL                                                                                                                                                                                               \n" +
					" 		                                                                                                                                                                                                          \n" +
					" 			SELECT year, month, ssn, type, [01] as D01, [02] as D02, [03] as D03, [04] as D04, [05] as D05, [06] as D06, [07] as D07, [08] as D08, [09] as D09, [10] as D10, [11] as D11,                           \n" +
					" 					[12] as D12, [13] as D13, [14] as D14, [15] as D15, [16] as D16, [17] as D17, [18] as D18, [19] as D19, [20] as D20, [21] as D21, [22] as D22, [23] as D23, [24] as D24, [25] as D25,               \n" +
					" 					[26] as D26, [27] as D27, [28] as D28, [29] as D29, [30] as D30, [31] as D31                                                                                                                        \n" +
					" 			FROM (                                                                                                                                                                                                  \n" +
					" 				SELECT  year, month, day, ssn, type, 61111 cnt								                                                                                                                                        \n" +
					" 				FROM  Schedule                                                                                                                                                                                        \n" +
					"  				WHERE 1=1                                                                                                                                                                                             \n" +
					" 				AND (secretYN = 'N')                                                                                                                                                                                  \n" +
					" 				AND (year = ?)                                                                                                                                                                                        \n" +
					" 				AND (month = ?)                                                                                                                                                                                       \n" +
					" 				AND type = 'Up-day'                                                                                                                                                                                     \n" +
					" 				group by year, month, day, ssn, type, workType                                                                                                                                                        \n" +
					" 			) AS Ab                                                                                                                                                                                                 \n" +
					" 			PIVOT (                                                                                                                                                                                                 \n" +
					" 				sum(cnt) FOR day IN ([01], [02], [03], [04], [05], [06], [07], [08], [09], [10], [11], [12], [13], [14], [15],                                                                                        \n" +
					" 				[16], [17], [18], [19], [20], [21], [22], [23], [24], [25], [26], [27], [28], [29], [30], [31])                                                                                                       \n" +
					" 			) Ab                                                                                                                                                                                                    \n" +
					" 		                                                                                                                                                                                                          \n" +
					" 			UNION ALL                                                                                                                                                                                               \n" +
					" 		                                                                                                                                                                                                          \n" +
					" 			SELECT year, month, ssn, type, [01] as D01, [02] as D02, [03] as D03, [04] as D04, [05] as D05, [06] as D06, [07] as D07, [08] as D08, [09] as D09, [10] as D10, [11] as D11,                           \n" +
					" 					[12] as D12, [13] as D13, [14] as D14, [15] as D15, [16] as D16, [17] as D17, [18] as D18, [19] as D19, [20] as D20, [21] as D21, [22] as D22, [23] as D23, [24] as D24, [25] as D25,               \n" +
					" 					[26] as D26, [27] as D27, [28] as D28, [29] as D29, [30] as D30, [31] as D31                                                                                                                        \n" +
					" 			FROM (                                                                                                                                                                                                  \n" +
					" 				SELECT  year, month, day, ssn, type, -60000 cnt								                                                                                                                                        \n" +
					" 				FROM  Schedule                                                                                                                                                                                        \n" +
					"  				WHERE 1=1                                                                                                                                                                                             \n" +
					" 				AND (secretYN = 'N')                                                                                                                                                                                  \n" +
					" 				AND (year = ?)                                                                                                                                                                                        \n" +
					" 				AND (month = ?)                                                                                                                                                                                       \n" +
					" 				AND type = '휴가'                                                                                                                                                                                     \n" +
					" 				group by year, month, day, ssn, type, workType                                                                                                                                                        \n" +
					" 			) AS Ab                                                                                                                                                                                                 \n" +
					" 			PIVOT (                                                                                                                                                                                                 \n" +
					" 				sum(cnt) FOR day IN ([01], [02], [03], [04], [05], [06], [07], [08], [09], [10], [11], [12], [13], [14], [15],                                                                                        \n" +
					" 				[16], [17], [18], [19], [20], [21], [22], [23], [24], [25], [26], [27], [28], [29], [30], [31])                                                                                                       \n" +
					" 			) Ab                                                                                                                                                                                                    \n" +
					"                                                                                                                                                                                                               \n" +
					" 			UNION ALL                                                                                                                                                                                               \n" +
					" 		                                                                                                                                                                                                          \n" +
					" 			SELECT year, month, ssn, type, [01] as D01, [02] as D02, [03] as D03, [04] as D04, [05] as D05, [06] as D06, [07] as D07, [08] as D08, [09] as D09, [10] as D10, [11] as D11,                           \n" +
					" 					[12] as D12, [13] as D13, [14] as D14, [15] as D15, [16] as D16, [17] as D17, [18] as D18, [19] as D19, [20] as D20, [21] as D21, [22] as D22, [23] as D23, [24] as D24, [25] as D25,               \n" +
					" 					[26] as D26, [27] as D27, [28] as D28, [29] as D29, [30] as D30, [31] as D31                                                                                                                        \n" +
					" 			FROM (                                                                                                                                                                                                  \n" +
					" 				SELECT  year, month, day, ssn, type, -120000 cnt								                                                                                                                                        \n" +
					" 				FROM  Schedule                                                                                                                                                                                        \n" +
					"  				WHERE 1=1                                                                                                                                                                                             \n" +
					" 				AND (secretYN = 'N')                                                                                                                                                                                  \n" +
					" 				AND (year = ?)                                                                                                                                                                                        \n" +
					" 				AND (month = ?)                                                                                                                                                                                       \n" +
					" 				AND type = '개인휴무'                                                                                                                                                                                     \n" +
					" 				group by year, month, day, ssn, type, workType                                                                                                                                                        \n" +
					" 			) AS Ab                                                                                                                                                                                                 \n" +
					" 			PIVOT (                                                                                                                                                                                                 \n" +
					" 				sum(cnt) FOR day IN ([01], [02], [03], [04], [05], [06], [07], [08], [09], [10], [11], [12], [13], [14], [15],                                                                                        \n" +
					" 				[16], [17], [18], [19], [20], [21], [22], [23], [24], [25], [26], [27], [28], [29], [30], [31])                                                                                                       \n" +
					" 			) Ab                                                                                                                                                                                                    \n" +
					"                                                                                                                                                                                                               \n" +
					" 			UNION ALL                                                                                                                                                                                               \n" +
					" 		                                                                                                                                                                                                          \n" +
					" 			SELECT year, month, ssn, type, [01] as D01, [02] as D02, [03] as D03, [04] as D04, [05] as D05, [06] as D06, [07] as D07, [08] as D08, [09] as D09,                                                     \n" +
					" 					[10] as D10, [11] as D11, [12] as D12, [13] as D13, [14] as D14, [15] as D15, [16] as D16, [17] as D17, [18] as D18, [19] as D19, [20] as D20,                                                      \n" +
					" 					[21] as D21, [22] as D22, [23] as D23, [24] as D24, [25] as D25, [26] as D26, [27] as D27, [28] as D28, [29] as D29, [30] as D30, [31] as D31                                                       \n" +
					" 			FROM (                                                                                                                                                                                                  \n" +
					" 				SELECT  year, month, day, ssn, type, workType, 51111 cnt								                                                                                                                              \n" +
					" 				FROM  Schedule	                                                                                                                                                                                      \n" +
					" 				WHERE 1=1	                                                                                                                                                                                            \n" +
					" 				AND (secretYN = 'N')	                                                                                                                                                                                \n" +
					" 				AND (year = ?)	                                                                                                                                                                                      \n" +
					" 				AND (month = ?)	                                                                                                                                                                                      \n" +
					" 				AND type = '교육참석'	                                                                                                                                                                                \n" +
					" 				group by year, month, day, ssn, type, workType	                                                                                                                                                      \n" +
					" 			) AS Ab	                                                                                                                                                                                                \n" +
					" 			PIVOT (	                                                                                                                                                                                                \n" +
					" 				sum(cnt) FOR day IN ([01], [02], [03], [04], [05], [06], [07], [08], [09], [10], [11], [12], [13], [14], [15], [16], [17], [18], [19], [20],                                                          \n" +
					" 				[21], [22], [23], [24], [25], [26], [27], [28], [29], [30], [31])	                                                                                                                                    \n" +
					" 			) Ab	                                                                                                                                                                                                  \n" +
					" 			                                                                                                                                                                                                        \n" +
					" 			UNION ALL	                                                                                                                                                                                              \n" +
					" 			                                                                                                                                                                                                        \n" +
					" 			SELECT year, month, ssn, (case when workType='E' then '외부일정' when workType='I' then '내부일정' end) as type, [01] as D01, [02] as D02, [03] as D03, [04] as D04, [05] as D05, [06] as D06,          \n" +
					" 					[07] as D07, [08] as D08, [09] as D09, [10] as D10, [11] as D11, [12] as D12, [13] as D13, [14] as D14, [15] as D15, [16] as D16, [17] as D17, [18] as D18, [19] as D19, [20] as D20,               \n" +
					" 					[21] as D21, [22] as D22, [23] as D23, [24] as D24, [25] as D25, [26] as D26, [27] as D27, [28] as D28, [29] as D29, [30] as D30, [31] as D31	                                                      \n" +
					" 			FROM (	                                                                                                                                                                                                \n" +
					" 				SELECT  year, month, day, ssn,'' type, workType, (case when workType = 'E' then 20 when workType = 'I' then 1 end) cnt								                                                                \n" +
					" 				FROM  Schedule 	                                                                                                                                                                                      \n" +
					" 				WHERE 1=1	                                                                                                                                                                                            \n" +
					" 				AND (secretYN = 'N')	                                                                                                                                                                                \n" +
					" 				AND (year = ?)                                                                                                                                                                                        \n" +
					" 				 AND (month = ?)	                                                                                                                                                                                    \n" +
					" 				AND type not in ('휴가','개인휴무', '교육참석', 'Up-day')	                                                                                                                                                                \n" +
					" 				group by year, month, day, ssn, workType	                                                                                                                                                            \n" +
					" 			) AS Ab	                                                                                                                                                                                                \n" +
					" 			PIVOT (	                                                                                                                                                                                                \n" +
					" 				sum(cnt) FOR day IN ([01], [02], [03], [04], [05], [06], [07], [08], [09], [10], [11], [12], [13], [14], [15], [16], [17], [18], [19], [20], [21], [22], [23], [24],                                  \n" +
					" 				[25], [26], [27], [28], [29], [30], [31])	                                                                                                                                                            \n" +
					" 			) Ab	                                                                                                                                                                                                  \n" +
					" 				                                                                                                                                                                                                      \n" +
					" 			UNION ALL	                                                                                                                                                                                              \n" +
					" 				                                                                                                                                                                                                      \n" +
					" 			SELECT  year, month, chargeSsn as ssn, '프로젝트' as type, [01] as D01, [02] as D02, [03] as D03, [04] as D04, [05] as D05, [06] as D06, [07] as D07, [08] as D08, [09] as D09,                         \n" +
					" 					[10] as D10, [11] as D11, [12] as D12, [13] as D13, [14] as D14, [15] as D15, [16] as D16, [17] as D17, [18] as D18, [19] as D19, [20] as D20, [21] as D21, [22] as D22, [23] as D23,               \n" +
					" 					[24] as D24, [25] as D25, [26] as D26, [27] as D27, [28] as D28, [29] as D29, [30] as D30, [31] as D31	                                                                                            \n" +
					" 			FROM (	                                                                                                                                                                                                \n" +
					" 				SELECT year, month,day, chargeSsn, 300 cnt								                                                                                                                                            \n" +
					" 				from (	                                                                                                                                                                                              \n" +
					" 					SELECT  PRD.projectCode, PRD.year, PRD.month, PRD.day, PRD.chargeSsn	                                                                                                                              \n" +
					" 					FROM    ProjectReportDetail AS PRD 	                                                                                                                                                              \n" +
					" 							INNER JOIN Project AS P 	                                                                                                                                                                      \n" +
					" 					ON PRD.projectCode = P.projectCode	                                                                                                                                                                \n" +
					" 					WHERE  1=1	                                                                                                                                                                                        \n" +
					" 					AND (PRD.year = ?)      	                                                                                                                                                                            \n" +
					" 		 			AND (PRD.month = ?)	                                                                                                                                                                                  \n" +
					" 					UNION ALL	                                                                                                                                                                                          \n" +
					" 					SELECT  PRD.projectCode, PRD.year, PRD.month, PRD.day, PRD.chargeSsn	                                                                                                                              \n" +
					" 					FROM    ProjectManpowerDetail AS PRD 	                                                                                                                                                              \n" +
					" 							INNER JOIN Project AS P 	                                                                                                                                                                      \n" +
					" 					ON PRD.projectCode = P.projectCode	                                                                                                                                                                \n" +
					" 					WHERE  1=1	                                                                                                                                                                                        \n" +
					" 					AND (PRD.year = ?)      	                                                                                                                                                                            \n" +
					" 		 			AND (PRD.month = ?)	                                                                                                                                                                                  \n" +
					" 					UNION ALL	                                                                                                                                                                                          \n" +
					" 					SELECT	BBSID as projectCode, 	                                                                                                                                                                    \n" +
					" 							datename(yy,workDate) year, datename(mm, workDate) as month, right(workDate, 2) as day,		                                                                                                      \n" +
					" 							WRITERID as chargeSsn	                                                                                                                                                                          \n" +
					" 					FROM standardbbs							                                                                                                                                                                      \n" +
					" 					WHERE refSchedule = 'Y'							                                                                                                                                                                \n" +
					"					AND workDate >= ?																																													\n" + 
					"					AND workDate <  dateadd(day, 1, ?)																																									\n" + 
					" 				) a	                                                                                                                                                                                                  \n" +
					" 				group by year, month, day, chargeSsn	                                                                                                                                                                \n" +
					" 			) AS Ab	                                                                                                                                                                                                \n" +
					" 			PIVOT (	                                                                                                                                                                                                \n" +
					" 				sum(cnt) FOR day IN ([01], [02], [03], [04], [05], [06], [07], [08], [09], [10], [11], [12], [13], [14], [15], [16], [17], [18],                                                                      \n" +
					" 				 [19], [20], [21], [22], [23], [24], [25], [26], [27], [28], [29], [30], [31])	                                                                                                                      \n" +
					" 			) Ab	                                                                                                                                                                                                  \n" +
					" 		) res                                                                                                                                                                                                     \n" +
					" 	) b                                                                                                                                                                                                         \n" +
					" 	on a.ssn = b.ssn                                                                                                                                                                                            \n" +
					" 	and a.scheduleType = b.type                                                                                                                                                                                 \n" +
					" ) g                                                                                                                                                                                                           \n" +
					" group by userId, ssn, groupName, groupParentId, groupId, userName, posName, labelName, OrdSeq, groupSeq, posCode                                                                                              \n" +
					" order by userName, OrdSeq, groupSeq, groupId, posCode, posName		 ");
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
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
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScheduleDailyMasterInfo data = new ScheduleDailyMasterInfo();
			data.setUserId(rs.getString("userId"));
			data.setSsn(rs.getString("ssn"));
			data.setGroupName(rs.getString("groupName"));
			data.setGroupParentId(rs.getString("groupParentId"));
			data.setGroupId(rs.getString("groupId"));
			data.setUserName(rs.getString("userName"));
			data.setPosName(rs.getString("posName"));
			data.setLabelName(rs.getString("labelName"));
			data.setOrdSeq(rs.getString("ordSeq"));
			data.setGroupSeq(rs.getString("groupSeq"));
			//data.setScheduleType(rs.getString("scheduleType"));
			//data.setYear(rs.getString("year"));
			//data.setMonth(rs.getString("month"));
			data.setD01(rs.getInt("D01"));
			data.setD02(rs.getInt("D02"));
			data.setD03(rs.getInt("D03"));
			data.setD04(rs.getInt("D04"));
			data.setD05(rs.getInt("D05"));
			data.setD06(rs.getInt("D06"));
			data.setD07(rs.getInt("D07"));
			data.setD08(rs.getInt("D08"));
			data.setD09(rs.getInt("D09"));
			data.setD10(rs.getInt("D10"));
			data.setD11(rs.getInt("D11"));
			data.setD12(rs.getInt("D12"));
			data.setD13(rs.getInt("D13"));
			data.setD14(rs.getInt("D14"));
			data.setD15(rs.getInt("D15"));
			data.setD16(rs.getInt("D16"));
			data.setD17(rs.getInt("D17"));
			data.setD18(rs.getInt("D18"));
			data.setD19(rs.getInt("D19"));
			data.setD20(rs.getInt("D20"));
			data.setD21(rs.getInt("D21"));
			data.setD22(rs.getInt("D22"));
			data.setD23(rs.getInt("D23"));
			data.setD24(rs.getInt("D24"));
			data.setD25(rs.getInt("D25"));
			data.setD26(rs.getInt("D26"));
			data.setD27(rs.getInt("D27"));
			data.setD28(rs.getInt("D28"));
			data.setD29(rs.getInt("D29"));
			data.setD30(rs.getInt("D30"));
			data.setD31(rs.getInt("D31"));
			return data;
		}
	}
	
	
	protected void initDao() throws Exception {
		this.scheduleOfKMACUser = new ScheduleOfKMACUser(getDataSource());
		this.scheduleOfNotKMACUser = new ScheduleOfNotKMACUser(getDataSource());
		this.scheduleOfNotUser = new ScheduleOfNotUser(getDataSource());
		this.sMGroupFind = new SMGroupFind(getDataSource());
		this.scheduleCreate = new ScheduleCreate(getDataSource());
		this.scheduleDelete = new ScheduleDelete(getDataSource());
		this.scheduleLogCreate = new ScheduleLogCreate(getDataSource());
		this.scheduleCreate_time = new ScheduleCreate_time(getDataSource());
		this.scheduleCreate_time2 = new ScheduleCreate_time2(getDataSource());
		this.scheduleDelete_time = new ScheduleDelete_time(getDataSource());
		this.projectScheduleByMonth = new ProjectScheduleByMonth(getDataSource());
		this.projectManpowerByMonth = new ProjectManpowerByMonth(getDataSource());
		this.projectPJTByMonth = new ProjectPJTByMonth(getDataSource());
		this.projectBoardByMonth = new ProjectBoardByMonth(getDataSource());
		this.projectSchedule = new ProjectSchedule(getDataSource());
		this.scheduleByMonth = new ScheduleByMonth(getDataSource());
		this.scheduleByMonth_time = new ScheduleByMonth_time(getDataSource());
		this.internalScheduleByMonth = new InternalScheduleByMonth(getDataSource());
		this.externalScheduleByMonth = new ExternalScheduleByMonth(getDataSource());
		this.dayOffByMonth = new DayOffByMonth(getDataSource());
		this.educationByMonth = new EducationByMonth(getDataSource());
		this.updayByMonth = new UpdayByMonth(getDataSource());
		this.holidayByMonth = new HolidayByMonth(getDataSource());
		this.customerPickerByMonth = new CustomerPickerByMonth(getDataSource());
		this.customerPickerDetailByMonth = new CustomerPickerDetailByMonth(getDataSource());
		this.scheduleInfo = new ScheduleInfo(getDataSource());
		this.scheduleInfo_time = new ScheduleInfo_time(getDataSource());
		this.scheduleUserList = new ScheduleUserList(getDataSource());
		this.scheduleRA13List = new ScheduleRA13List(getDataSource());
		this.scheduleRA4List = new ScheduleRA4List(getDataSource());
		this.scheduleExpertList = new ScheduleExpertList(getDataSource());
		this.scheduleExpertRealList = new ScheduleExpertRealList(getDataSource());
		this.scheduleExpertRealList_after = new ScheduleExpertRealList_after(getDataSource());
		this.scheduleDailyMasterList = new ScheduleDailyMasterList(getDataSource());
		this.holidayRetrieveQuery = new HolidayRetrieveQuery(getDataSource());
		this.scheduleByMonthCnt_time = new ScheduleByMonthCnt_time(getDataSource());
		this.scheduleByMonthCnt_time_cco = new ScheduleByMonthCnt_time_cco(getDataSource());
		this.scheduleByMonthCnt_time_cbo = new ScheduleByMonthCnt_time_cbo(getDataSource());
		this.scheduleByMonthCnt_customer = new ScheduleByMonthCnt_customer(getDataSource());
		this.scheduleByMonthCnt_customer2 = new ScheduleByMonthCnt_customer2(getDataSource());
		this.sanctionDeptFind = new SanctionDeptFind(getDataSource());
		this.sanctionDeptFind2 = new SanctionDeptFind2(getDataSource());
		this.sanctionDeptFind3 = new SanctionDeptFind3(getDataSource());
		this.sanctionDeptFind4 = new SanctionDeptFind4(getDataSource());
		this.scheduleByConCnt = new ScheduleByConCnt(getDataSource());
	}

	public List scheduleListOfUser(String searchYear, String searchMonth, String searchDay,
			String ssn, String jobClass) throws DataAccessException {
		if (searchDay == null || "".equals(searchDay))
			searchDay = "%%";

		if (ssn == null)
			ssn = "%%";
		List resultList = null;

		if ("A".equals(jobClass)) {
			resultList = scheduleOfKMACUser.execute(new Object[]{"", "", "", "", "", "",
					searchYear, searchMonth, "", "", "", "", "", "", "", "", "", "", "", "",
					searchYear, searchMonth, searchYear, searchMonth, searchDay, ssn, jobClass});
		} else if ("B".equals(jobClass)) {
			resultList = scheduleOfNotKMACUser.execute(new Object[]{"", "", "", "", "", "",
					searchYear, searchMonth, "", "", "", "", "", "", "", "", "", "", "", "",
					//searchYear, searchMonth, searchYear, searchMonth, searchDay, ssn, jobClass});
					searchYear, searchMonth, searchYear, searchMonth, searchDay, ssn});
		} else {
			// resultList = scheduleOfNotUser.execute(new Object[]{"", "", searchYear, searchMonth, "","", "", "", "",
			// "", "",
			// searchYear, searchMonth, searchDay, ssn, "A", "B"});
			resultList = scheduleOfNotUser.execute(new Object[]{"", "", "", "", "", "", searchYear,
					searchMonth, "", "", "", "", "", "", "", "", "", "", "", "", searchYear,
					searchMonth, searchYear, searchMonth, searchDay, ssn, jobClass});
			/*
			 * resultList = scheduleOfNotKMACUser.execute(new Object[]{"", "", "", "", "", "", searchYear, searchMonth,
			 * "", "", "", "", "", "", "", "", "", "", "", "", searchYear, searchMonth, searchYear, searchMonth,
			 * searchDay, ssn, jobClass});
			 */
		}
		return resultList;
	}

	public List<DailyScheduleInfo> getScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws DataAccessException {
		if("".equals(day)) day ="%%";
		if("".equals(secretYN)) secretYN = "%%";
		List<DailyScheduleInfo> resultList = scheduleByMonth.execute(new Object[]{ssn, year, month, day, secretYN});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getScheduleListByMonth_time(String ssn, String year, String month, String day) throws DataAccessException {
		if("".equals(day)) day ="%%";
		List<DailyScheduleInfo> resultList = scheduleByMonth_time.execute(new Object[]{ssn, year, month, day});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_time(String year, String month, String ssn) throws DataAccessException {
		List<DailyScheduleInfo> resultList = scheduleByMonthCnt_time.execute(new Object[]{ssn, year, month, ssn, year, month, ssn });
		return resultList;
	}
	
	public List<DailyScheduleInfo> getConScheduleCnt(String year, String month, String ssn) throws DataAccessException {
		List<DailyScheduleInfo> resultList = scheduleByConCnt.execute(new Object[]{ssn, year, month});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_time_cco(String year, String month, String ssn) throws DataAccessException {
		List<DailyScheduleInfo> resultList = scheduleByMonthCnt_time_cco.execute(new Object[]{year, month, ssn});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_time_cbo(String year, String month, String ssn) throws DataAccessException {
		List<DailyScheduleInfo> resultList = scheduleByMonthCnt_time_cbo.execute(new Object[]{ssn, year, month, ssn});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_customer(String day, String ssn) throws DataAccessException {
		List<DailyScheduleInfo> resultList = scheduleByMonthCnt_customer.execute(new Object[]{day, ssn});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_customer2(String day, String ssn) throws DataAccessException {
		List<DailyScheduleInfo> resultList = scheduleByMonthCnt_customer2.execute(new Object[]{day, ssn});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getInternalScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws DataAccessException {
		if("".equals(day)) day ="%%";
		if("".equals(secretYN)) secretYN = "%%";
		List<DailyScheduleInfo> resultList = internalScheduleByMonth.execute(new Object[]{ssn, year, month, day, secretYN});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getExternalScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws DataAccessException {
		if("".equals(day)) day ="%%";
		if("".equals(secretYN)) secretYN = "%%";
		List<DailyScheduleInfo> resultList = externalScheduleByMonth.execute(new Object[]{ssn, year, month, day, secretYN});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getDayOffListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		if("".equals(day)) day ="%%";
		List<DailyScheduleInfo> resultList = dayOffByMonth.execute(new Object[]{ssn, year, month, day});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getEducationListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		if("".equals(day)) day ="%%";
		List<DailyScheduleInfo> resultList = educationByMonth.execute(new Object[]{ssn, year, month, day});
		return resultList;
	}
	
	public List<DailyScheduleInfo> getUpdayListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		if("".equals(day)) day ="%%";
		List<DailyScheduleInfo> resultList = updayByMonth.execute(new Object[]{ssn, year, month, day});
		return resultList;
	}
	
	public List<CustomerPickerInfo> getCustomerPickerListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		String from = "";
		String to = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		if("".equals(day)) {
			// 검색 월의 1일부터 다음 월의 1일까지 범위 설정
			from = year + "-" + month + "-01";
			cal.setTime(Date.valueOf(from));
			cal.add(Calendar.MONTH, 1);
			to = df.format(cal.getTime());
		} else {
			// 검색 날짜와 다음 날짜까지의 범위 설정
			from = year + "-" + month + "-" + day;
			cal.setTime(Date.valueOf(from));
			cal.add(Calendar.DATE, 1);
			to = df.format(cal.getTime());
		}
		List<CustomerPickerInfo> resultList = customerPickerByMonth.execute(new Object[]{ssn, from, to});
		return resultList;
	}
	
	public List<CustomerPickerInfo> getCustomerPickerDetailListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		String from = year + "-" + month + "-" + day;
		String to = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(Date.valueOf(from));
		cal.add(Calendar.DATE, 1);
		to = df.format(cal.getTime());
		
		List<CustomerPickerInfo> resultList = customerPickerDetailByMonth.execute(new Object[]{ssn, from, to});
		return resultList;
	}
	
	public List<DailyProjectInfo> getProjectScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		if("".equals(day)) day ="%%";
		List<DailyProjectInfo> resultList = projectScheduleByMonth.execute(new Object[]{ssn, year, month, day});
		//resultList.addAll(projectBoardByMonth.execute(new Object[]{ssn, year, month, day}));
		return resultList;
	}
	
	public List<DailyProjectInfo> getProjectReportScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		if("".equals(day)) day ="%%";
		List<DailyProjectInfo> resultList = projectScheduleByMonth.execute(new Object[]{ssn, year, month, day});
		return resultList;
	}
	
	// 일정관리에서 MM 투입일정 보이도록 변경
	public List<DailyProjectInfo> getProjectManpowerScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		if("".equals(day)) day ="%%";
		List<DailyProjectInfo> resultList = projectManpowerByMonth.execute(new Object[]{ssn, year, month, day});
		return resultList;
	}
	
	public List<DailyProjectInfo> getProjectPJTScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		if("".equals(day)) day ="%%";
		List<DailyProjectInfo> resultList = projectPJTByMonth.execute(new Object[]{ssn, year, month, day});
		return resultList;
	}

	public List<DailyProjectInfo> getProjectBoardScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException {
		if("".equals(day)) day ="%%";
		List<DailyProjectInfo> resultList = projectBoardByMonth.execute(new Object[]{ssn, year, month, day});
		return resultList;
	}
	
	@Override
	public DailyProjectInfo getProjectSchedule(String projectCode, String ssn, String year, String month, String day) throws ScheduleException {
		List<DailyProjectInfo> list = projectSchedule.execute(new Object[] { projectCode, ssn, year, month, day });
		if (list.size() != 1)
			throw new ScheduleException("Search Error: " + projectCode + ":" + ssn + ":" + year + ":" + month + ":" + day);
		return (DailyProjectInfo) list.get(0);
	}

	public List<HolidayInfo> getHolidayListByMonth(String year, String month) throws DataAccessException {
		List<HolidayInfo> resultList = holidayByMonth.execute(new Object[]{year, month});
		return resultList;
	}
	
	public DailyScheduleInfo getScheduleInfo(String ssn, String year, String month, String day, int seq) throws DataAccessException {
		DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
		List<DailyScheduleInfo> tempLst = scheduleInfo.execute(new Object[]{ssn, year, month, day, seq});
		dailyScheduleInfo = tempLst.get(0);

		return dailyScheduleInfo;
	}
	
	public DailyScheduleInfo getScheduleInfo_time(String ssn, String year, String month, String day, int seq) throws DataAccessException {
		DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
		List<DailyScheduleInfo> tempLst = scheduleInfo_time.execute(new Object[]{ssn, year, month, day, seq});
		dailyScheduleInfo = tempLst.get(0);
		
		return dailyScheduleInfo;
	}
	public String getGroupName(String dept) throws DataAccessException {
		return (String) sMGroupFind.findObject(dept);
	}
	
	public String getSanctionDept(String seq) throws DataAccessException {
		return (String) sanctionDeptFind.findObject(seq);
	}
	
	public String getSanctionDept2(String seq) throws DataAccessException {
		return (String) sanctionDeptFind2.findObject(seq);
	}
	
	public String getSanctionDept3(String seq) throws DataAccessException {
		return (String) sanctionDeptFind3.findObject(seq);
	}
	
	public String getSanctionDept4(String seq) throws DataAccessException {
		return (String) sanctionDeptFind4.findObject(seq);
	}

	public void create(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException {
		this.scheduleCreate.insert(dailyScheduleInfo);
	}
	
	public void createLog(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException {
		this.scheduleLogCreate.insert(dailyScheduleInfo);
	}

	public void remove(String year, String month, String day, String ssn, int seq)
			throws DataAccessException {
		this.scheduleDelete.delete(year, month, day, ssn, seq);
	}
	
	public void create_time(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException {
		this.scheduleCreate_time.insert(dailyScheduleInfo);
	}
	
	public void create_time2(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException {
		this.scheduleCreate_time2.insert(dailyScheduleInfo);
	}

	public void remove_time(String year, String month, String day, String ssn, int seq)
			throws DataAccessException {
		this.scheduleDelete_time.delete(year, month, day, ssn, seq);
	}
	
	public List<ScheduleUserInfo> getScheduleUserInfoList(String jobClass) throws DataAccessException {
		List<ScheduleUserInfo> userList = scheduleUserList.execute(new Object[]{jobClass, jobClass, jobClass});
		return userList;
	}
	
	public List<ScheduleUserInfo> getScheduleExpertInfoList(String jobClass, String year, String month) throws DataAccessException {
		List<ScheduleUserInfo> userList = scheduleExpertList.execute(new Object[]{jobClass, year, month, year, month});
		return userList;
	}
	
	public List<ScheduleUserInfo> getScheduleExpertRealInfoList(String jobClass, String year, String month) throws DataAccessException {
		List<ScheduleUserInfo> userList = scheduleExpertRealList.execute(new Object[]{jobClass, year, month, year, month, year, month});
		return userList;
	}
	
	public List<ScheduleUserInfo> getScheduleExpertRealInfoList_after(String jobClass, String year, String month) throws DataAccessException {
		List<ScheduleUserInfo> userList = scheduleExpertRealList_after.execute(new Object[]{jobClass, year, month, year, month});
		return userList;
	}	
	
	public List<ScheduleUserInfo> getScheduleRA13InfoList(String jobClass) throws DataAccessException {
		List<ScheduleUserInfo> userList = scheduleRA13List.execute(new Object[]{jobClass});
		return userList;
	}
	
	public List<ScheduleUserInfo> getScheduleRA4InfoList(String jobClass) throws DataAccessException {
		List<ScheduleUserInfo> userList = scheduleRA4List.execute(new Object[]{jobClass});
		return userList;
	}

	@Override
	public List<ScheduleDailyMasterInfo> getScheduleDailyMasterInfoList(String jobClass, String year, String month) throws DataAccessException {
		String from = year + "-" + month + "-" + "01";
		Calendar cal = Calendar.getInstance();
	    cal.setTime(Date.valueOf(from));
	    int endOfDay = cal.getActualMaximum(Calendar.DATE);
	    String to = year+month+String.valueOf(endOfDay);	
				
		List<ScheduleDailyMasterInfo> list = scheduleDailyMasterList.execute(new Object[]{jobClass, jobClass, jobClass, jobClass,  jobClass,  jobClass, jobClass, from, to, year, month, year, month, year, month, year, month, year, month, year, month, year, month, from, to});
		return list;
	}

	@Override
	public boolean isHoliday(String yyyymmdd) throws DataAccessException {
		return (Integer)this.holidayRetrieveQuery.findObject(new Object[] { yyyymmdd, yyyymmdd, yyyymmdd }) > 0 ? true : false;
	}

}
