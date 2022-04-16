/*
 * $Id: WeeklyReportScheduleServiceDaoImpl.java,v 1.6 2019/02/28 15:35:15 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.wreport.data.WeeklyReport;
import kr.co.kmac.pms.service.scheduler.dao.WeeklyReportScheduleServiceDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: WeeklyReportScheduleServiceDaoImpl.java,v 1.6 2019/02/28 15:35:15 cvs Exp $
 */
public class WeeklyReportScheduleServiceDaoImpl extends JdbcDaoSupport implements WeeklyReportScheduleServiceDao {

	private StringBuffer query;//매월 스케줄러
	private StringBuffer query1;//프로젝트 코드 베이스 진행 상태 상관없이 현재월 프로젝트 레포트 생성
	private StringBuffer query2;//실행 품의 후 생성
	private StringBuffer query3;//미할당 지도일지 생성
	
	public StringBuffer getQuery() {
		this.query  = new StringBuffer();                                                                                                                         
		this.query.append(" 		select b.*,																														");
		this.query.append(" 				a.projectCode, a.projectName, a.projectState, a.assignYear, 															");
		this.query.append("					right('0'+convert(varchar, a.assignMonth), 2) as assignMonth, a.assignWeekOfMonth, a.assignDate,						");
		this.query.append(" 				a.realStartDate, a.realEndDate, a.weekOfProject, a.thisWeekFromDate, a.thisWeekToDate,  									");
		this.query.append(" 				a.nextWeekFromDate, a.nextWeekToDate, a.planProgress, a.arSsn, a.arName, 												");												
		this.query.append(" 				(case when a.plSsn is null or a.plSsn = a.pmSsn then '' else a.pmSsn end) pmSsn, 										");
		this.query.append(" 				(case when a.plName is null or a.plName = a.pmName then '' else a.pmName end) pmName,									");
		this.query.append(" 				(case when a.plSsn is null then a.pmSsn else plSsn end) plSsn, 															");
		this.query.append(" 				(case when a.plName is null then a.pmName else a.plName end) plName from (												");
		this.query.append(" 		   select  projectCode, projectName, projectState,									                                          ");
		this.query.append(" 				 DATEPART( YEAR, DATEADD( DAY, ( ( 7 - DATEPART( WEEKDAY, CONVERT(DATETIME, getDate()) ) ) % 7 ) - 3, CONVERT(DATETIME, getDate()) ) ) AS assignYear,         ");
		this.query.append(" 				 DATEPART( MONTH, DATEADD( DAY, ( ( 7 - DATEPART( WEEKDAY, CONVERT(DATETIME, getDate()) ) ) % 7 ) - 3, CONVERT(DATETIME, getDate()) ) ) AS assignMonth,       ");
		this.query.append(" 				 (DATEPART(DAY, DATEADD(DAY,((7 - DATEPART( WEEKDAY, getDate()) ) % 7 ) - 3, getDate())) - 1 ) / 7 + 1 AS assignWeekOfMonth,                                  ");
		this.query.append(" 				 Convert(varchar(10),Getdate(),112) as assignDate,																		");
		this.query.append(" 				 realStartDate, realEndDate,																								");
		this.query.append(" 				 CEILING(CAST(DATEDIFF(DD, cast(realStartDate as DATETIME), Getdate()) AS float)/7) as weekOfProject,					");		
		this.query.append(" 				 CONVERT(VARCHAR(10), CONVERT(DATETIME, getdate())-DATEPART(DW, getdate())+2, 112) thisWeekFromDate,					");	
		this.query.append(" 				 CONVERT(VARCHAR(10), CONVERT(DATETIME, getdate())-DATEPART(DW, getdate())+8, 112) thisWeekToDate,						");
		this.query.append(" 				 CONVERT(VARCHAR(10), CONVERT(DATETIME, getdate())-DATEPART(DW, getdate())+9, 112) nextWeekFromDate,					");
		this.query.append(" 				 CONVERT(VARCHAR(10), CONVERT(DATETIME, getdate())-DATEPART(DW, getdate())+15, 112) nextWeekToDate,						");
		this.query.append(" 				 round(cast((DATEDIFF(DAY, cast(realStartDate as DATETIME), GETDATE())+1) as float) / cast((DATEDIFF(DAY, cast(realStartDate as DATETIME), cast(realEndDate as DATETIME))+1) as float)*100, 2) as planProgress,");	
		this.query.append(" 				 (select ssn from projectMember where projectCode = p.projectCode and role = 'AR' AND trainingYN = 'Y') as arSsn,							");
		this.query.append(" 				 dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'AR' AND trainingYN = 'Y')) as arName,	");		
		this.query.append(" 				 (select ssn from projectMember where projectCode = p.projectCode  and role = 'PM' AND trainingYN = 'Y') as pmSsn,							");
		this.query.append(" 				 dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'PM' AND trainingYN = 'Y')) as pmName,	");		
		this.query.append(" 				 (select ssn from projectMember where projectCode = p.projectCode  and role = 'PL' AND trainingYN = 'Y') as plSsn,							");
		this.query.append(" 				 dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'PL' AND trainingYN = 'Y')) as plName	");		
		this.query.append(" 		   from project p																	");
		this.query.append(" 		   where projectState in ('3')													");	
		this.query.append(" 		   and projectTypeCode = 'MM'														");
		this.query.append(" 		   and Convert(varchar(10),Getdate(),112) between realStartDate and realEndDate		");	
		this.query.append(" 	    ) a full outer join ( 																");
		this.query.append(" 		   select projectCode as w_projectCode , assignYear as w_assignYear, assignMonth as w_assignMonth, assignWeekOfMonth as w_assignWeekOfMonth from ProjectWeeklyReport");
		this.query.append(" 	    ) b																		 ");
		this.query.append(" 	    on a.projectCode = b.w_projectCode                                       ");
		this.query.append(" 	    and a.assignYear = b.w_assignYear                                        ");
		this.query.append(" 	    and a.assignMonth = b.w_assignMonth                                      ");
		this.query.append(" 	    and a.assignWeekOfMonth = b.w_assignWeekOfMonth                          ");
		this.query.append(" 	    where w_projectCode is null			                                     ");
		return query;
	}

	public void setQuery(StringBuffer query) {
		this.query = query;
	}
	
	public StringBuffer getQuery1() {
		this.query1  = new StringBuffer();                                                                                                                         
		this.query1.append(" 		select  b.*, 																													");
		this.query1.append(" 				a.projectCode, a.projectName, a.projectState, a.assignYear, 															");
		this.query1.append("				right('0'+convert(varchar, a.assignMonth), 2) as assignMonth, a.assignWeekOfMonth, a.assignDate, 						");
		this.query1.append(" 				a.realStartDate, a.realEndDate, a.weekOfProject, a.thisWeekFromDate, a.thisWeekToDate,  									");
		this.query1.append(" 				a.nextWeekFromDate, a.nextWeekToDate, a.planProgress, a.arSsn, a.arName, 												");												
		this.query1.append(" 				(case when a.plSsn is null or a.plSsn = a.pmSsn then '' else a.pmSsn end) pmSsn, 										");
		this.query1.append(" 				(case when a.plName is null or a.plName = a.pmName then '' else a.pmName end) pmName,									");
		this.query1.append(" 				(case when a.plSsn is null then a.pmSsn else plSsn end) plSsn, 															");
		this.query1.append(" 				(case when a.plName is null then a.pmName else a.plName end) plName from (												");
		this.query1.append(" 		   select  projectCode, projectName, projectState,									                                          	");
		this.query1.append(" 				 DATEPART( YEAR, DATEADD( DAY, ( ( 7 - DATEPART( WEEKDAY, CONVERT(DATETIME, getDate()) ) ) % 7 ) - 3, CONVERT(DATETIME, getDate()) ) ) AS assignYear,         ");
		this.query1.append(" 				 DATEPART( MONTH, DATEADD( DAY, ( ( 7 - DATEPART( WEEKDAY, CONVERT(DATETIME, getDate()) ) ) % 7 ) - 3, CONVERT(DATETIME, getDate()) ) ) AS assignMonth,       ");
		this.query1.append(" 				 (DATEPART(DAY, DATEADD(DAY,((7 - DATEPART( WEEKDAY, getDate()) ) % 7 ) - 3, getDate())) - 1 ) / 7 + 1 AS assignWeekOfMonth,                                  ");
		this.query1.append(" 				   Convert(varchar(10),Getdate(),112) as assignDate,																	");
		this.query1.append(" 				   realStartDate, realEndDate,																							");
		this.query1.append(" 				   CEILING(CAST(DATEDIFF(DD, cast(realStartDate as DATETIME), Getdate()) AS float)/7) as weekOfProject,					");		
		this.query1.append(" 				   CONVERT(VARCHAR(10), CONVERT(DATETIME, getdate())-DATEPART(DW, getdate())+2, 112) thisWeekFromDate,					");	
		this.query1.append(" 				   CONVERT(VARCHAR(10), CONVERT(DATETIME, getdate())-DATEPART(DW, getdate())+8, 112) thisWeekToDate,					");
		this.query1.append(" 				   CONVERT(VARCHAR(10), CONVERT(DATETIME, getdate())-DATEPART(DW, getdate())+9, 112) nextWeekFromDate,					");
		this.query1.append(" 				   CONVERT(VARCHAR(10), CONVERT(DATETIME, getdate())-DATEPART(DW, getdate())+15, 112) nextWeekToDate,					");
		this.query1.append(" 				   round(cast((DATEDIFF(DAY, cast(realStartDate as DATETIME), GETDATE())+1) as float) / cast((DATEDIFF(DAY, cast(realStartDate as DATETIME), cast(realEndDate as DATETIME))+1) as float)*100, 2) as planProgress,");	
		this.query1.append(" 				   (select ssn from projectMember where projectCode = p.projectCode and role = 'AR' AND trainingYN = 'Y') as arSsn,		");
		this.query1.append(" 				   dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'AR' AND trainingYN = 'Y')) as arName,	");		
		this.query1.append(" 				   (select ssn from projectMember where projectCode = p.projectCode  and role = 'PM' AND trainingYN = 'Y') as pmSsn,							");
		this.query1.append(" 				   dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'PM' AND trainingYN = 'Y')) as pmName,	");		
		this.query1.append(" 				   (select ssn from projectMember where projectCode = p.projectCode  and role = 'PL' AND trainingYN = 'Y') as plSsn,							");
		this.query1.append(" 				   dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'PL' AND trainingYN = 'Y')) as plName		");		
		this.query1.append(" 		   from project p																	");
		this.query1.append(" 		   where projectTypeCode = 'MM'														");
		this.query1.append(" 		   and Convert(varchar(10),Getdate(),112) between realStartDate and realEndDate		");		
		this.query1.append(" 	    ) a full outer join ( 																");
		this.query1.append(" 		   select projectCode as w_projectCode , assignYear as w_assignYear, assignMonth as w_assignMonth, assignWeekOfMonth as w_assignWeekOfMonth from ProjectWeeklyReport");
		this.query1.append(" 	    ) b																		 ");
		this.query1.append(" 	    on a.projectCode = b.w_projectCode                                       ");
		this.query1.append(" 	    and a.assignYear = b.w_assignYear                                        ");
		this.query1.append(" 	    and a.assignMonth = b.w_assignMonth                                      ");
		this.query1.append(" 	    and a.assignWeekOfMonth = b.w_assignWeekOfMonth                          ");
		this.query1.append(" 	    where w_projectCode is null			                                     ");
		return query1;
	}

	public void setQuery1(StringBuffer query1) {
		this.query1 = query1;
	}

	public StringBuffer getQuery2() {
		this.query2  = new StringBuffer();                                                                                                                         
		this.query2.append(" 	select																																						");
		this.query2.append(" 		    b.dt, b.assignYear, right('0'+convert(varchar, b.assignMonth), 2) as assignMonth, b.assignWeekOfMonth,                                              ");
		this.query2.append(" 		    projectCode, projectName, projectState, assignDate, realStartDate, realEndDate,                                                                       ");
		this.query2.append(" 		    CEILING(CAST(DATEDIFF(DD, cast(realStartDate as DATETIME), dt) AS float)/7) as weekOfProject,                                                        ");
		this.query2.append(" 		    thisWeekFromDate, thisWeekToDate, nextWeekFromDate, nextWeekToDate, planProgress,                                                                   ");
		this.query2.append(" 		    arSsn, arName,                                                                                                    									");
		this.query2.append("			(case when plSsn is null or plSsn = pmSsn then '' else pmSsn end) pmSsn,																			");
		this.query2.append("			(case when plName is null or plName = pmName then '' else pmName end) pmName,																		");
		this.query2.append("			(case when plSsn is null then pmSsn else plSsn end) plSsn, 																							");
		this.query2.append("			(case when plName is null then pmName else plName end) plName																						");
		this.query2.append(" 		from (                                                                                                                		                            ");
		this.query2.append(" 		     select  projectCode, projectName, projectState,                                                                                                    ");
		this.query2.append(" 				   Convert(varchar(10),Getdate(),112) as assignDate,																	 				        ");
		this.query2.append(" 				   realStartDate, realEndDate,																							 				        ");
		this.query2.append(" 				   round(cast((DATEDIFF(DAY, cast(realStartDate as DATETIME), GETDATE())+1) as float) / cast((DATEDIFF(DAY, cast(realStartDate as DATETIME),      ");
		this.query2.append(" 				   cast(realEndDate as DATETIME))+1) as float)*100, 2) as planProgress, 				                                                            ");
		this.query2.append(" 				   (select ssn from projectMember where projectCode = p.projectCode and role = 'AR' AND trainingYN = 'Y') as arSsn,		 				        ");
		this.query2.append(" 				   dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'AR' AND trainingYN = 'Y')) as arName,	"); 				   
		this.query2.append(" 				   (select ssn from projectMember where projectCode = p.projectCode  and role = 'PM' AND trainingYN = 'Y') as pmSsn,							"); 				   
		this.query2.append(" 				   dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'PM' AND trainingYN = 'Y')) as pmName,	"); 				   
		this.query2.append(" 				   (select ssn from projectMember where projectCode = p.projectCode  and role = 'PL' AND trainingYN = 'Y') as plSsn,							"); 				   
		this.query2.append(" 				   dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'PL' AND trainingYN = 'Y')) as plName		"); 		   
		this.query2.append(" 			from project p																	 		                                                            ");
		this.query2.append(" 			where projectTypeCode = 'MM'										                                                                                ");
		this.query2.append(" 			and projectCode = '#######'                                                                                                                         ");
		this.query2.append(" 		) a  cross join (                                                                                                                                       ");
		this.query2.append(" 		    select min(dt) dt, assignYear, assignMonth, assignWeekOfMonth, thisWeekFromDate, thisWeekToDate, nextWeekFromDate, nextWeekToDate                   ");
		this.query2.append(" 		    from (                                                                                                                                              ");
		this.query2.append(" 			   select  dt,                                                                                                                                      ");
		this.query2.append(" 					 DATEPART( YEAR, DATEADD( DAY, ( ( 7 - DATEPART( WEEKDAY, CONVERT(DATETIME, dt) ) ) % 7 ) - 3, CONVERT(DATETIME, dt) ) ) AS assignYear,     ");
		this.query2.append(" 					 DATEPART( MONTH, DATEADD( DAY, ( ( 7 - DATEPART( WEEKDAY, CONVERT(DATETIME, dt) ) ) % 7 ) - 3, CONVERT(DATETIME, dt) ) ) AS assignMonth,   ");
		this.query2.append(" 					 (DATEPART(DAY, DATEADD(DAY,((7 - DATEPART( WEEKDAY, dt) ) % 7 ) - 3, dt)) - 1 ) / 7 + 1 AS assignWeekOfMonth,                              ");
		this.query2.append(" 					 CONVERT(VARCHAR(10), CONVERT(DATETIME, dt)-DATEPART(DW, dt)+2, 112) thisWeekFromDate,					 				                    ");
		this.query2.append(" 					 CONVERT(VARCHAR(10), CONVERT(DATETIME, dt)-DATEPART(DW, dt)+8, 112) thisWeekToDate,					 				                    ");
		this.query2.append(" 					 CONVERT(VARCHAR(10), CONVERT(DATETIME, dt)-DATEPART(DW, dt)+9, 112) nextWeekFromDate,					 				                    ");
		this.query2.append(" 					 CONVERT(VARCHAR(10), CONVERT(DATETIME, dt)-DATEPART(DW, dt)+15, 112) nextWeekToDate				 				                        ");
		this.query2.append(" 	                                                                                                                                                            ");
		this.query2.append(" 			   From (                                                                                                                                           ");
		this.query2.append(" 				  select convert(char(8),dateadd(d,number, Convert(varchar(8),DATEADD(MM,-6, GETDATE()),112)),112) dt                                           ");
		this.query2.append(" 				  from master..spt_values                                                                                                                       ");
		this.query2.append(" 				  where type = 'P'                                                                                                                              ");
		this.query2.append(" 			   ) r                                                                                                                                              ");
		this.query2.append(" 		    ) r2                                                                                                                                                ");
		this.query2.append(" 		    group by assignYear, assignMonth, assignWeekOfMonth, thisWeekFromDate, thisWeekToDate, nextWeekFromDate, nextWeekToDate                             ");
		this.query2.append(" 		)b 		                                                                                                                                                ");
		this.query2.append(" 		where a.realStartDate <= b.thisWeekToDate                                                                                                                           ");
		this.query2.append(" 		and b.dt <= Convert(varchar(10),Getdate(),112)                                                                                                          ");
		this.query2.append(" 		order by 1                                                                                                                                              ");
		return query2;
	}

	public void setQuery2(StringBuffer query2) {
		this.query2 = query2;
	}


	public StringBuffer getQuery3() {
		this.query3  = new StringBuffer();                                                                                                                         
		this.query3.append(" select a.*, b.w_projectCode from (		");
		this.query3.append("     select																																						  ");
		this.query3.append(" 		   b.dt, b.assignYear, right('0'+convert(varchar, b.assignMonth), 2) as assignMonth, b.assignWeekOfMonth,                                                  ");
		this.query3.append(" 		   projectCode, projectName, projectState, assignDate, realStartDate, realEndDate,                                                                          ");
		this.query3.append(" 		   CEILING(CAST(DATEDIFF(DD, cast(realStartDate as DATETIME), dt) AS float)/7) as weekOfProject,                                                          ");
		this.query3.append("		   thisWeekFromDate, thisWeekToDate, nextWeekFromDate, nextWeekToDate, planProgress,                                                                   	");
		this.query3.append("		   arSsn, arName,                                                                                                    										");
		this.query3.append("		  (case when plSsn is null or plSsn = pmSsn then '' else pmSsn end) pmSsn,																				");
		this.query3.append("		  (case when plName is null or plName = pmName then '' else pmName end) pmName,																			");
		this.query3.append("		  (case when plSsn is null then pmSsn else plSsn end) plSsn, 																								");
		this.query3.append(" 		  (case when plName is null then pmName else plName end) plName																						  ");
		this.query3.append(" 	    from (                                                                                                                		                              ");
		this.query3.append(" 		    select  projectCode, projectName, projectState,                                                                                                       ");
		this.query3.append(" 				  Convert(varchar(10),Getdate(),112) as assignDate,																	 				           ");
		this.query3.append(" 				  realStartDate, realEndDate,																							 				            ");
		this.query3.append(" 				  round(cast((DATEDIFF(DAY, cast(realStartDate as DATETIME), GETDATE())+1) as float) / cast((DATEDIFF(DAY, cast(realStartDate as DATETIME),         ");
		this.query3.append(" 				  cast(realEndDate as DATETIME))+1) as float)*100, 2) as planProgress, 				                                                               ");
		this.query3.append("				  (select ssn from projectMember where projectCode = p.projectCode and role = 'AR' AND trainingYN = 'Y') as arSsn,		 				        	"); 				   
		this.query3.append("				  dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'AR' AND trainingYN = 'Y')) as arName,	 				   	"); 				   
		this.query3.append("				  (select ssn from projectMember where projectCode = p.projectCode  and role = 'PM' AND trainingYN = 'Y') as pmSsn,							 				   	"); 				   
		this.query3.append("				  dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'PM' AND trainingYN = 'Y')) as pmName,	 				   	"); 				   
		this.query3.append("				  (select ssn from projectMember where projectCode = p.projectCode  and role = 'PL' AND trainingYN = 'Y') as plSsn,							 				   	"); 		   
		this.query3.append(" 				  dbo.getExpertPoolName((select ssn from projectMember where projectCode = p.projectCode  and role = 'PL' AND trainingYN = 'Y')) as plName		 		     ");
		this.query3.append(" 		    from project p																	 		                                                              ");
		this.query3.append(" 		    where projectTypeCode = 'MM'	  ");
		this.query3.append(" 		    and projectState in ( '3')							                                                                                  ");
		this.query3.append(" 		    and projectCode = '#######'                                                                                                                           ");
		this.query3.append(" 	    ) a  cross join (                                                                                                                                         ");
		this.query3.append(" 		   select min(dt) dt, assignYear, assignMonth, assignWeekOfMonth, thisWeekFromDate, thisWeekToDate, nextWeekFromDate, nextWeekToDate                     ");
		this.query3.append(" 		   from (                                                                                                                                                ");
		this.query3.append(" 			  select  dt,                                                                                                                                        ");
		this.query3.append(" 					DATEPART( YEAR, DATEADD( DAY, ( ( 7 - DATEPART( WEEKDAY, CONVERT(DATETIME, dt) ) ) % 7 ) - 3, CONVERT(DATETIME, dt) ) ) AS assignYear,       ");
		this.query3.append(" 					DATEPART( MONTH, DATEADD( DAY, ( ( 7 - DATEPART( WEEKDAY, CONVERT(DATETIME, dt) ) ) % 7 ) - 3, CONVERT(DATETIME, dt) ) ) AS assignMonth,     ");
		this.query3.append(" 					(DATEPART(DAY, DATEADD(DAY,((7 - DATEPART( WEEKDAY, dt) ) % 7 ) - 3, dt)) - 1 ) / 7 + 1 AS assignWeekOfMonth,                                ");
		this.query3.append(" 					CONVERT(VARCHAR(10), CONVERT(DATETIME, dt)-DATEPART(DW, dt)+2, 112) thisWeekFromDate,					 				                     ");
		this.query3.append(" 					CONVERT(VARCHAR(10), CONVERT(DATETIME, dt)-DATEPART(DW, dt)+8, 112) thisWeekToDate,					 				                         ");
		this.query3.append(" 					CONVERT(VARCHAR(10), CONVERT(DATETIME, dt)-DATEPART(DW, dt)+9, 112) nextWeekFromDate,					 				                     ");
		this.query3.append(" 					CONVERT(VARCHAR(10), CONVERT(DATETIME, dt)-DATEPART(DW, dt)+15, 112) nextWeekToDate				 				                             ");
		this.query3.append("                                                                                                                                                                 ");
		this.query3.append(" 			  From (                                                                                                                                             ");
		this.query3.append(" 				 select convert(char(8),dateadd(d,number, Convert(varchar(8),DATEADD(MM,-6, GETDATE()),112)),112) dt                                             ");
		this.query3.append(" 				 from master..spt_values                                                                                                                         ");
		this.query3.append(" 				 where type = 'P'                                                                                                                                ");
		this.query3.append(" 			  ) r                                                                                                                                                ");
		this.query3.append(" 		   ) r2                                                                                                                                                  ");
		this.query3.append(" 		   group by assignYear, assignMonth, assignWeekOfMonth, thisWeekFromDate, thisWeekToDate, nextWeekFromDate, nextWeekToDate                               ");
		this.query3.append(" 	    )b 		                                                                                                                                                 ");
		this.query3.append(" 	    where a.realStartDate <= b.thisWeekToDate                                                                                                                ");
		this.query3.append(" 	    and b.dt <= Convert(varchar(10),Getdate(),112)                                                                                                           ");            
		this.query3.append("  ) a full outer join (                       ");            
		this.query3.append("     select projectCode as w_projectCode , assignYear as w_assignYear, assignMonth as w_assignMonth, assignWeekOfMonth as w_assignWeekOfMonth from ProjectWeeklyReport                      ");            
		this.query3.append(" ) b                      ");            
		this.query3.append(" 														                      ");            
		this.query3.append("  on a.projectCode = b.w_projectCode                                                             ");            
		this.query3.append("  and a.assignYear = b.w_assignYear                                                              ");            
		this.query3.append("  and a.assignMonth = b.w_assignMonth                                                            ");            
		this.query3.append("  and a.assignWeekOfMonth = b.w_assignWeekOfMonth                                                ");            
		this.query3.append("   where w_projectCode is  null and a.projectCode is not null                       ");            
		this.query3.append("  order by 1,2,3                      ");            
		return query3;
	}

	public void setQuery3(StringBuffer query3) {
		this.query3 = query3;
	}


	private static RowMapper weeklyReportScheduleDataRowMapper = new RowMapper() {
		public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
			WeeklyReport weeklyReport = new WeeklyReport();
			weeklyReport.setProjectCode(rs.getString("projectCode"));
			weeklyReport.setAssignDate(rs.getString("assignDate"));
			weeklyReport.setAssignYear(rs.getString("assignYear"));
			weeklyReport.setAssignMonth(rs.getString("assignMonth"));
			weeklyReport.setAssignWeekOfMonth(rs.getString("assignWeekOfMonth"));
			weeklyReport.setWeekOfProject(rs.getString("weekOfProject"));
			weeklyReport.setThisWeekFromDate(rs.getString("thisWeekFromDate"));
			weeklyReport.setThisWeekToDate(rs.getString("thisWeekToDate"));
			weeklyReport.setNextWeekFromDate(rs.getString("nextWeekFromDate"));
			weeklyReport.setNextWeekToDate(rs.getString("nextWeekToDate"));
			weeklyReport.setPlanProgress(rs.getString("planProgress"));
			weeklyReport.setState("writer");
			weeklyReport.setWriterSsn(rs.getString("plSsn"));
			weeklyReport.setWriterName(rs.getString("plName"));
			weeklyReport.setReviewerSsn(rs.getString("pmSsn"));
			weeklyReport.setReviewerName(rs.getString("pmName"));
			weeklyReport.setApproverSsn(rs.getString("arSsn"));
			weeklyReport.setApproverName(rs.getString("arName"));
			return weeklyReport;
		}
	};

	
	@SuppressWarnings("unchecked")
	@Override
	public List<WeeklyReport> getWeeklyReportList() {
		return getJdbcTemplate().query(this.getQuery().toString(), weeklyReportScheduleDataRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeeklyReport> getWeeklyReportList(String projectCode) {
		return getJdbcTemplate().query(this.getQuery1().append(" and projectCode = '"+projectCode+"' ").toString(), weeklyReportScheduleDataRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeeklyReport> getWeeklyReportListWhenApproved(String projectCode) {
		return getJdbcTemplate().query(StringUtil.replace(this.getQuery2().toString(), "#######", projectCode), weeklyReportScheduleDataRowMapper);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<WeeklyReport> getWeeklyReportListMissed(String projectCode) {
		return getJdbcTemplate().query(StringUtil.replace(this.getQuery3().toString(), "#######", projectCode), weeklyReportScheduleDataRowMapper);
	}
	
}
