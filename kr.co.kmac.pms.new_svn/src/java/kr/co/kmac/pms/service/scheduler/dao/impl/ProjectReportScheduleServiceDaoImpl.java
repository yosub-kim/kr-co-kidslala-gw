/*
 * $Id: ProjectReportScheduleServiceDaoImpl.java,v 1.11 2017/09/25 08:13:59 cvs Exp $
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
import kr.co.kmac.pms.service.scheduler.dao.ProjectReportScheduleServiceDao;
import kr.co.kmac.pms.service.scheduler.data.ProjectReportScheduleData;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportScheduleServiceDaoImpl.java,v 1.11 2017/09/25 08:13:59 cvs Exp $
 */
public class ProjectReportScheduleServiceDaoImpl extends JdbcDaoSupport implements ProjectReportScheduleServiceDao {
	private static RowMapper projectReportScheduleDataRowMapper = new RowMapper() {
		public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
			ProjectReportScheduleData projectReportScheduleData = new ProjectReportScheduleData();
			projectReportScheduleData.setProjectCode(rs.getString("projectCode"));
			projectReportScheduleData.setYear(rs.getString("year"));
			projectReportScheduleData.setMonth(rs.getString("month"));
			projectReportScheduleData.setDay(rs.getString("day"));
			projectReportScheduleData.setWorkSeq(rs.getString("workSeq"));
			projectReportScheduleData.setChargeSsn(rs.getString("chargeSsn"));
			projectReportScheduleData.setProcessTypeCode(rs.getString("processTypeCode"));
			projectReportScheduleData.setTaskFormTypeId(rs.getString("taskFormTypeId"));
			projectReportScheduleData.setTaskFormTypeName(rs.getString("taskFormTypeName"));
			projectReportScheduleData.setBusinessTypeCode(rs.getString("businessTypeCode"));
			return projectReportScheduleData;
		}
	};

	@Override
	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilGivenDate(String yyyymmdd) {
		StringBuffer query = new StringBuffer();
		query.append("select 	g.projectname, g.projectCode, g.year, g.month, g.day, g.workSeq, g.chargeSsn,  ");
		query.append("			g.processTypeCode, g.projectCode+g.chargeSsn+g.year+g.month+g.day taskFormTypeId, h.taskFormTypeName, g.businessTypeCode    ");
		query.append("from                                                                                     ");
		query.append("(                                                                                        ");
		query.append("	select 	f.projectname, e.projectCode, e.year, e.month, e.day,  e.workSeq,              ");
		query.append("	        e.chargeSsn, f.processTypeCode, f.businessTypeCode                             ");
		query.append("	from                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select b.projectName, a.* , b.processTypeCode							           ");
		query.append("		from 												 					           ");
		query.append("		(												 						           ");
		query.append("			select 	a.projectCode, a.year, a.month, a.day, a.workseq, a.chargessn          ");
		query.append("			from 											 					           ");
		query.append("			(											 						           ");
		query.append("			  select a.projectCode, a.year, a.month, a.day, a.week, a.workseq, b.chargessn ");
		query.append("				from projectReport a, projectReportDetail b					 	           ");
		query.append("				where a.projectCode = b.projectCode						 		           ");
		query.append("				and a.year = b.year								 				           ");
		query.append("				and a.month = b.month								 			           ");
		query.append("				and a.day = b.day								 				           ");
		query.append("				and (b.chargessn <> '' )							 			           ");
		query.append("				and  a.year =  ?      													   ");
		query.append("				and  a.month =  ?      													   ");
		query.append("				and  a.day <=  ?				      									   ");
		query.append("			) a											                                   ");
		query.append("			left outer join 									                           ");
		query.append("			(											                                   ");
		query.append("				select * from projectReportContent						                   ");
		query.append("			) b											                                   ");
		query.append("			on a.projectCode = b.projectCode 							                   ");
		query.append("			and a.year = left(b.assigndate, 4)								               ");
		query.append("			and a.month = substring(b.assigndate, 5, 2)						               ");
		query.append("			and a.day = right(b.assigndate, 2)								               ");
		query.append("			and b.writerssn = a.chargessn 									               ");
		query.append("			where b.seq is null 									                       ");
		query.append("		) a, 												 					           ");
		query.append("		project b										 	 					           ");
		query.append("		where a.projectCode = b.projectCode              	 					           ");
		query.append("		and	  b.projectState >= 3	and	b.projectState < 7 	 					           ");
		query.append("	) e,                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select 	projectname, projectCode, processTypeCode, businessTypeCode from project   ");
		query.append("	) f                                                                                    ");
		query.append("	where 	e.projectCode = f.projectCode                                                  ");
		query.append(") g,                                                                                     ");
		query.append("(                                                                                        ");
		query.append("	select projectCode, workSeq, workName as taskFormTypeName							   ");            
		query.append("	from projectScheduleM with(nolock)													   ");
		query.append(") h                                                                                      ");
		query.append("where g.projectCode = h.projectCode		                                	           ");
		query.append("and	g.workSeq = h.workSeq             		                     	 		           ");
		query.append("and  	g.year = ? 																		   ");
		query.append("and  	g.month = ? 																	   ");
		query.append("and  	g.day <= ?				 														   ");

		return getJdbcTemplate().query(query.toString(), new Object[] { yyyymmdd.substring(0, 4), yyyymmdd.substring(5, 7), yyyymmdd.substring(8, 10), yyyymmdd.substring(0, 4), yyyymmdd.substring(5, 7), yyyymmdd.substring(8, 10) },
				projectReportScheduleDataRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilToday(String projectCode) {
		StringBuffer query = new StringBuffer();
		query.append("select 	g.projectname, g.projectCode, g.year, g.month, g.day, g.workSeq, g.chargeSsn,  ");
		query.append("			g.processTypeCode, g.projectCode+g.chargeSsn+g.year+g.month+g.day taskFormTypeId, h.taskFormTypeName, g.businessTypeCode    ");
		query.append("from                                                                                     ");
		query.append("(                                                                                        ");
		query.append("	select 	f.projectname, e.projectCode, e.year, e.month, e.day,  e.workSeq,              ");
		query.append("	        e.chargeSsn, f.processTypeCode, f.businessTypeCode                             ");
		query.append("	from                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select b.projectName, a.* , b.processTypeCode							           ");
		query.append("		from 												 					           ");
		query.append("		(												 						           ");
		query.append("			select 	a.projectCode, a.year, a.month, a.day, a.workseq, a.chargessn          ");
		query.append("			from 											 					           ");
		query.append("			(											 						           ");
		query.append("			  select a.projectCode, a.year, a.month, a.day, a.week, a.workseq, b.chargessn ");
		query.append("				from projectReport a, projectReportDetail b					 	           ");
		query.append("				where a.projectCode = b.projectCode						 		           ");
		query.append("				and a.year = b.year								 				           ");
		query.append("				and a.month = b.month								 			           ");
		query.append("				and a.day = b.day								 				           ");
		query.append("				and (b.chargessn <> '' )							 			           ");
		query.append("				and  a.year+a.month+a.day <=  ?											   ");
		query.append("				and  a.projectCode = ? 							               			   ");
		query.append("			) a											                                   ");
		query.append("			left outer join 									                           ");
		query.append("			(											                                   ");
		query.append("				select * from projectReportContent						                   ");
		query.append("			) b											                                   ");
		query.append("			on a.projectCode = b.projectCode 							                   ");
		query.append("			and a.year+a.month+a.day = b.assigndate							               ");
		query.append("			and b.writerssn = a.chargessn 									               ");
		query.append("			where b.seq is null 									                       ");
		query.append("		) a, 												 					           ");
		query.append("		project b										 	 					           ");
		query.append("		where a.projectCode = b.projectCode              	 					           ");
		query.append("		and	  b.projectState >= 3	and	b.projectState < 7 	 					           ");
		query.append("	) e,                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select 	projectname, projectCode, processTypeCode, businessTypeCode from project   ");
		query.append("	) f                                                                                    ");
		query.append("	where 	e.projectCode = f.projectCode                                                  ");
		query.append(") g,                                                                                     ");
		query.append("(                                                                                        ");
		//query.append("	select 	distinct processTypeCode, workSeq, taskFormTypeId, taskFormTypeName            ");
		//query.append("	from 	OutputTemplateDetail                   	 							           ");
		//query.append("	select 	processTemplateCode processTypeCode, workSeq, activityName taskFormTypeName    ");            
		//query.append("	from 	dbo.ProcessTemplateDetail													   ");
		query.append("	select 	projectCode, workSeq, workName as taskFormTypeName							   ");            
		query.append("	from 	projectScheduleM with(nolock)												   ");
		query.append("	where	projectCode = ?																   ");
		query.append(") h                                                                                      ");
		//query.append("where g.processTypeCode = h.processTypeCode                                	           ");
		query.append("where g.projectCode = h.projectCode                                	           		   ");
		query.append("and	g.workSeq = h.workSeq             		                     	 		           ");
		//query.append("and	h.taskFormTypeId is not null             		                     	           ");
		query.append("and  	g.year+g.month+g.day <= ?														   ");
		query.append("and 	g.year+g.month+g.day >= '20060701'								                   ");

		return getJdbcTemplate().query(query.toString(),
				new Object[] { StringUtil.getCurr("yyyyMMdd"), projectCode, projectCode, StringUtil.getCurr("yyyyMMdd") }, projectReportScheduleDataRowMapper);
	}

	@SuppressWarnings("unchecked")
	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilToday() {
		StringBuffer query = new StringBuffer();
		query.append("select 	g.projectname, g.projectCode, g.year, g.month, g.day, g.workSeq, g.chargeSsn,  ");
		query.append("			g.processTypeCode, g.projectCode+g.chargeSsn+g.year+g.month+g.day taskFormTypeId, h.taskFormTypeName, g.businessTypeCode    ");
		query.append("from                                                                                     ");
		query.append("(                                                                                        ");
		query.append("	select 	f.projectname, e.projectCode, e.year, e.month, e.day,  e.workSeq,              ");
		query.append("	        e.chargeSsn, f.processTypeCode, f.businessTypeCode                             ");
		query.append("	from                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select b.projectName, a.* , b.processTypeCode							           ");
		query.append("		from 												 					           ");
		query.append("		(												 						           ");
		query.append("			select 	a.projectCode, a.year, a.month, a.day, a.workseq, a.chargessn          ");
		query.append("			from 											 					           ");
		query.append("			(											 						           ");
		query.append("			  select a.projectCode, a.year, a.month, a.day, a.week, a.workseq, b.chargessn ");
		query.append("				from projectReport a, projectReportDetail b					 	           ");
		query.append("				where a.projectCode = b.projectCode						 		           ");
		query.append("				and a.year = b.year								 				           ");
		query.append("				and a.month = b.month								 			           ");
		query.append("				and a.day = b.day								 				           ");
		query.append("				and (b.chargessn <> '' )							 			           ");
		query.append("				and  a.year+a.month+a.day <=  ?      									   ");
		query.append("				and  a.year+a.month+a.day >=  '20060701'					               ");
		query.append("			) a											                                   ");
		query.append("			left outer join 									                           ");
		query.append("			(											                                   ");
		query.append("				select * from projectReportContent						                   ");
		query.append("			) b											                                   ");
		query.append("			on a.projectCode = b.projectCode 							                   ");
		query.append("			and a.year+a.month+a.day = b.assigndate							               ");
		query.append("			and b.writerssn = a.chargessn 									               ");
		query.append("			where b.seq is null 									                       ");
		query.append("		) a, 												 					           ");
		query.append("		project b										 	 					           ");
		query.append("		where a.projectCode = b.projectCode              	 					           ");
		query.append("		and	  b.projectState >= 3	and	b.projectState < 7 	 					           ");
		query.append("	) e,                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select 	projectname, projectCode, processTypeCode, businessTypeCode from project   ");
		query.append("	) f                                                                                    ");
		query.append("	where 	e.projectCode = f.projectCode                                                  ");
		query.append(") g,                                                                                     ");
		query.append("(                                                                                        ");
		//query.append("	select 	distinct processTypeCode, workSeq, taskFormTypeId, taskFormTypeName            ");
		//query.append("	from 	OutputTemplateDetail                   	 							           ");
		//query.append("	select 	processTemplateCode processTypeCode, workSeq, activityName taskFormTypeName    ");            
		//query.append("	from 	dbo.ProcessTemplateDetail													   ");
		query.append("	select projectCode, workSeq, workName as taskFormTypeName							   ");            
		query.append("	from projectScheduleM with(nolock)													   ");
		query.append(") h                                                                                      ");
		//query.append("where g.processTypeCode = h.processTypeCode                                	           ");
		query.append("where g.projectCode = h.projectCode		                                	           ");
		query.append("and	g.workSeq = h.workSeq             		                     	 		           ");
		//query.append("and	h.taskFormTypeId is not null             		                     	           ");
		query.append("and  	g.year+g.month+g.day <= ? 														   ");
		query.append("and 	g.year+g.month+g.day >= '20060701'								                   ");

		return getJdbcTemplate().query(query.toString(), new Object[] { StringUtil.getCurr("yyyyMMdd"), StringUtil.getCurr("yyyyMMdd") },
				projectReportScheduleDataRowMapper);
		//return getJdbcTemplate().query(query.toString(), new Object[] { "20100430", "20100430" },
		//		projectReportScheduleDataRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilToday(String projectCode, String ssn) {
		StringBuffer query = new StringBuffer();
		query.append("select 	g.projectname, g.projectCode, g.year, g.month, g.day, g.workSeq, g.chargeSsn,  ");
		query.append("			g.processTypeCode, g.projectCode+g.chargeSsn+g.year+g.month+g.day taskFormTypeId, h.taskFormTypeName, g.businessTypeCode    ");
		query.append("from                                                                                     ");
		query.append("(                                                                                        ");
		query.append("	select 	f.projectname, e.projectCode, e.year, e.month, e.day,  e.workSeq,              ");
		query.append("	        e.chargeSsn, f.processTypeCode, f.businessTypeCode                             ");
		query.append("	from                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select b.projectName, a.* , b.processTypeCode							           ");
		query.append("		from 												 					           ");
		query.append("		(												 						           ");
		query.append("			select 	a.projectCode, a.year, a.month, a.day, a.workseq, a.chargessn          ");
		query.append("			from 											 					           ");
		query.append("			(											 						           ");
		query.append("			  select a.projectCode, a.year, a.month, a.day, a.week, a.workseq, b.chargessn ");
		query.append("				from projectReport a, projectReportDetail b					 	           ");
		query.append("				where a.projectCode = b.projectCode						 		           ");
		query.append("				and a.year = b.year								 				           ");
		query.append("				and a.month = b.month								 			           ");
		query.append("				and a.day = b.day								 				           ");
		query.append("				and  a.year+a.month+a.day <=  ?											   ");
		query.append("				and  a.projectCode = ? 							               			   ");
		query.append("				and  b.chargessn = ? 							               			   ");
		query.append("			) a											                                   ");
		query.append("			left outer join 									                           ");
		query.append("			(											                                   ");
		query.append("				select * from projectReportContent						                   ");
		query.append("			) b											                                   ");
		query.append("			on a.projectCode = b.projectCode 							                   ");
		query.append("			and a.year+a.month+a.day = b.assigndate							               ");
		query.append("			and b.writerssn = a.chargessn 									               ");
		query.append("			where b.seq is null 									                       ");
		query.append("		) a, 												 					           ");
		query.append("		project b										 	 					           ");
		query.append("		where a.projectCode = b.projectCode              	 					           ");
		query.append("		and	  b.projectState >= 3	and	b.projectState < 7 	 					           ");
		query.append("	) e,                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select 	projectname, projectCode, processTypeCode, businessTypeCode from project   ");
		query.append("	) f                                                                                    ");
		query.append("	where 	e.projectCode = f.projectCode                                                  ");
		query.append(") g,                                                                                     ");
		query.append("(                                                                                        ");
		//query.append("	select 	distinct processTypeCode, workSeq, taskFormTypeId, taskFormTypeName            ");
		//query.append("	from 	OutputTemplateDetail                   	 							           ");
		//query.append("	select 	processTemplateCode processTypeCode, workSeq, activityName taskFormTypeName    ");            
		//query.append("	from 	dbo.ProcessTemplateDetail													   ");
		query.append("	select projectCode, workSeq, workName as taskFormTypeName							   ");            
		query.append("	from projectScheduleM with(nolock)													   ");
		query.append("	where projectCode = ?																   ");
		query.append(") h                                                                                      ");
		//query.append("where g.processTypeCode = h.processTypeCode                                	           ");
		query.append("where g.projectCode = h.projectCode		                                	           ");
		query.append("and	g.workSeq = h.workSeq             		                     	 		           ");
		//query.append("and	h.taskFormTypeId is not null             		                     	           ");
		query.append("and  	g.year+g.month+g.day <= ?														   ");
		query.append("and 	g.year+g.month+g.day >= '20060701'								                   ");

		return getJdbcTemplate()
				.query(query.toString(), new Object[] { StringUtil.getCurr("yyyyMMdd"), projectCode, ssn, projectCode, StringUtil.getCurr("yyyyMMdd") },
						projectReportScheduleDataRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilApproveDate(String projectCode, String approveDate) {
		StringBuffer query = new StringBuffer();
		query.append("select 	g.projectname, g.projectCode, g.year, g.month, g.day, g.workSeq, g.chargeSsn,  ");
		query.append("			g.processTypeCode, g.projectCode+g.chargeSsn+g.year+g.month+g.day taskFormTypeId, h.taskFormTypeName, g.businessTypeCode    ");
		query.append("from                                                                                     ");
		query.append("(                                                                                        ");
		query.append("	select 	f.projectname, e.projectCode, e.year, e.month, e.day,  e.workSeq,              ");
		query.append("	        e.chargeSsn, f.processTypeCode, f.businessTypeCode                             ");
		query.append("	from                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select b.projectName, a.* , b.processTypeCode							           ");
		query.append("		from 												 					           ");
		query.append("		(												 						           ");
		query.append("			select 	a.projectCode, a.year, a.month, a.day, a.workseq, a.chargessn          ");
		query.append("			from 											 					           ");
		query.append("			(											 						           ");
		query.append("			  select a.projectCode, a.year, a.month, a.day, a.week, a.workseq, b.chargessn ");
		query.append("				from projectReport a, projectReportDetail b					 	           ");
		query.append("				where a.projectCode = b.projectCode						 		           ");
		query.append("				and a.year = b.year								 				           ");
		query.append("				and a.month = b.month								 			           ");
		query.append("				and a.day = b.day								 				           ");
		query.append("				and (b.chargessn <> '' )							 			           ");
		query.append("				and  a.year+a.month+a.day <=  ?											   ");
		query.append("				and  a.projectCode = ? 							               			   ");
		query.append("			) a											                                   ");
		query.append("			left outer join 									                           ");
		query.append("			(											                                   ");
		query.append("				select * from projectReportContent						                   ");
		query.append("			) b											                                   ");
		query.append("			on a.projectCode = b.projectCode 							                   ");
		query.append("			and a.year+a.month+a.day = b.assigndate							               ");
		query.append("			and b.writerssn = a.chargessn 									               ");
		query.append("			where b.seq is null 									                       ");
		query.append("		) a, 												 					           ");
		query.append("		project b										 	 					           ");
		query.append("		where a.projectCode = b.projectCode              	 					           ");
		/* 2013-12-02 에 하단 라인의 주석을 해지 함 */
		query.append("		and	  b.projectState >= 3	and b.projectState < 7	 					           ");
		query.append("	) e,                                                                                   ");
		query.append("	(                                                                                      ");
		query.append("		select 	projectname, projectCode, processTypeCode, businessTypeCode from project   ");
		query.append("	) f                                                                                    ");
		query.append("	where 	e.projectCode = f.projectCode                                                  ");
		query.append(") g,                                                                                     ");
		query.append("(                                                                                        ");
		//query.append("	select 	distinct processTypeCode, workSeq, taskFormTypeId, taskFormTypeName            ");
		//query.append("	from 	OutputTemplateDetail                   	 							           ");
		//query.append("	select 	processTemplateCode processTypeCode, workSeq, activityName taskFormTypeName    ");            
		//query.append("	from 	dbo.ProcessTemplateDetail													   ");
		query.append("	select 	projectCode, workSeq, workName as taskFormTypeName							   ");            
		query.append("	from 	projectScheduleM with(nolock)												   ");
		query.append("	where	projectCode = ?																   ");
		query.append(") h                                                                                      ");
		//query.append("where g.processTypeCode = h.processTypeCode                                	           ");
		query.append("where g.projectCode = h.projectCode                                	           		   ");
		query.append("and	g.workSeq = h.workSeq             		                     	 		           ");
		//query.append("and	h.taskFormTypeId is not null             		                     	           ");
		query.append("and  	g.year+g.month+g.day <= ?														   ");
		query.append("and 	g.year+g.month+g.day >= '20060701'								                   ");

		return getJdbcTemplate().query(query.toString(),
				new Object[] { approveDate, projectCode, projectCode, approveDate }, projectReportScheduleDataRowMapper);
	}

	public String getTeamManagerSsn(String deptCode) {
		String query = " select * from ExpertPool where companyPosition = '10TM' and dept = '" + deptCode + "' ";
		return (String) getJdbcTemplate().queryForMap(query).get("ssn");
	}

	public String getCfoManagerSsn(String divCode) {
		String query = " select * from expertpool where jobclass = 'A' and dept = '" + divCode + "' and companyPosition like '0%'";
		return (String) getJdbcTemplate().queryForMap(query).get("ssn");
	}
}
