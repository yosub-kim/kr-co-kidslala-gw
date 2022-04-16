package kr.co.kmac.pms.service.scheduler.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.mreport.data.MonthlyReport;
import kr.co.kmac.pms.service.scheduler.dao.MonthlyReportScheduleServiceDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: MonthlyReportScheduleServiceDaoImpl.java,v 1.6 2019/02/28 15:35:15 cvs Exp $
 */
public class MonthlyReportScheduleServiceDaoImpl extends JdbcDaoSupport implements MonthlyReportScheduleServiceDao {

	private StringBuffer query;// 매주 스케줄러

	public StringBuffer getQuery() {
		this.query = new StringBuffer();
		this.query.append("		select b.*,																														                                                      		   ");
		this.query.append("       		a.projectCode, a.projectName, a.projectState, a.assignYear, 															                                                               ");
		this.query.append("	    		right('0'+convert(varchar, a.assignMonth), 2) as assignMonth, a.assignDate,						                                                                               		   ");
		this.query.append("       		a.realStartDate, a.realEndDate, a.weekOfProject, monthOfProject,                                                                                                                       ");
		this.query.append("      		a.arSsn, a.arName, 		 a.pmSsn, a.pmName,		  a.plSsn, a.plName,       a.writerSsn, a.writerName                                                                                   ");
		this.query.append("     from (			                                                                                                                                                                               ");
		this.query.append("     		select p1.projectCode, p1.projectName, p1.projectState,                                                                                                                                ");
		this.query.append("     				YEAR(GETDATE()) AS assignYear, MONTH(GETDATE()) AS assignMonth, Convert(varchar(10),Getdate(),112) as assignDate,                                                              ");
		this.query.append("     				p1.realStartDate, p1.realEndDate,                                                                                                                                              ");
		this.query.append("     				CEILING(CAST(DATEDIFF(DD, cast(p1.realStartDate as DATETIME), Getdate()) AS float)/7) as weekOfProject,                                                                        ");
		this.query.append("  					DATEDIFF(MM, cast(p1.realStartDate as DATETIME), getDate())+1 as monthOfProject,                                                                                               ");
		this.query.append("     				(select ssn from projectMember where projectCode = p1.projectCode and role = 'AR' AND trainingYN = 'Y') as arSsn,                                                              ");
		this.query.append("  					dbo.getExpertPoolName((select ssn from projectMember where projectCode = p1.projectCode  and role = 'AR' AND trainingYN = 'Y')) as arName,                                     ");
		this.query.append("     				(select ssn from projectMember where projectCode = p1.projectCode  and role = 'PM' AND trainingYN = 'Y') as pmSsn,                                                             ");
		this.query.append("  					dbo.getExpertPoolName((select ssn from projectMember where projectCode = p1.projectCode  and role = 'PM' AND trainingYN = 'Y')) as pmName,                                     ");
		this.query.append("     				(select ssn from projectMember where projectCode = p1.projectCode  and role = 'PL' AND trainingYN = 'Y') as plSsn,                                                             ");
		this.query.append("     				dbo.getExpertPoolName((select ssn from projectMember where projectCode = p1.projectCode  and role = 'PL' AND trainingYN = 'Y')) as plName,                                     ");
		this.query.append("     				p.ssn writerSsn, e.name writerName, p.trainingYN, e.jobClass, p.role, prd.ssn_Cnt                                                                                                           ");
		this.query.append("     		from (                                                                                                                                                                                 ");
		this.query.append("  				select * from project where projectState in ('3') and projectTypeCode = 'ND'                                                                                                       ");
		this.query.append("  			) p1                                                                                                                                                                                   ");
		this.query.append("  			inner join (                                                                                                                                                                           ");
		this.query.append("  				select * from ProjectMember where role in ('PL', 'MB') and trainingYN = 'Y'                                                                                                        ");
		this.query.append("  			) p                                                                                                                                                                                    ");
		this.query.append("  			on p1.projectCode = p.projectCode                                                                                                                                                      ");
		this.query.append("     		inner join (                                                                                                                                                                           ");
		this.query.append("     			select * from expertpool where (jobClass in ('B','C','J') or dept in ('9090'))                                                                                                                           ");
		this.query.append("     		)e                                                                                                                                                                                     ");
		this.query.append("     		on p.ssn = e.ssn                                                                                                                                                                       ");
		this.query.append("    			inner join (					                                                                                                                                                 ");
		this.query.append("    				select projectcode, count(chargessn) as ssn_Cnt, chargessn From projectReportDetail where year = year(GETDATE()) and month =MONTH(GETDATE()) group by projectcode, chargessn	             ");
		this.query.append("    			) prd					                                                                                                                                                          ");
		this.query.append("    			on  p1.projectcode = prd.projectcode and p.ssn = prd.chargessn 					                                                                                                                                 ");
		this.query.append("     ) a full outer join ( 																                                                                                                    ");
		this.query.append("       	select projectCode as w_projectCode , assignYear as w_assignYear, assignMonth as w_assignMonth, writerSsn as w_writerSsn  from dbo.ProjectMonthlyReport                                                        ");
		this.query.append("     ) b																		                                                                                                                ");
		this.query.append("     on a.projectCode = b.w_projectCode                                                                                                                                                      ");
		this.query.append("     and a.assignYear = b.w_assignYear                                                                                                                                                       ");
		this.query.append("     and a.assignMonth = b.w_assignMonth                                                                                                                                                     ");
		this.query.append("     and a.writerSsn = b.w_writerSsn                                                                                                                                                     ");
		this.query.append("     where w_projectCode is null			                                                                                                                                                    ");

		return query;
	}

	public void setQuery(StringBuffer query) {
		this.query = query;
	}

	private StringBuffer query3;// 미할당 지도일지 생성 --> 어드민 기능과 연결

	public StringBuffer getQuery3() {
		this.query3 = new StringBuffer();
		this.query3.append(" 	select a.*, b.m_projectCode from (		                                                                                                                             ");
		this.query3.append("    	select																																						     ");
		this.query3.append("            	b.dt, b.assignYear, right('0'+convert(varchar, b.assignMonth), 2) as assignMonth,                                                                        ");
		this.query3.append("            	projectCode, projectName, projectState, assignDate, realStartDate, realEndDate,                                                                          ");
		this.query3.append("            	CEILING(CAST(DATEDIFF(DD, cast(realStartDate as DATETIME), dt) AS float)/7) as weekOfProject,               	                                         ");
		this.query3.append("            	DATEDIFF(MM, cast(realStartDate as DATETIME), getDate())+1 as monthOfProject,   				            	                                         ");
		this.query3.append("    			arSsn, arName,		pmSsn, pmName,			plSsn, plName,			writerSsn, writerName                                                                ");
		this.query3.append("    	from (		                                                                                                                                                     ");
		this.query3.append("    		select p1.projectCode, p1.projectName, p1.projectState,                                                                                                      ");
		this.query3.append("    				Convert(varchar(10),Getdate(),112) as assignDate,                                                                                                    ");
		this.query3.append("    				p1.realStartDate, p1.realEndDate,                                                                                                                    ");
		this.query3.append("    				CEILING(CAST(DATEDIFF(DD, cast(p1.realStartDate as DATETIME), Getdate()) AS float)/7) as weekOfProject,                                              ");
		this.query3.append("    				DATEDIFF(MM, cast(p1.realStartDate as DATETIME), getDate())+1 as monthOfProject,                                                                     ");
		this.query3.append("    				(select ssn from projectMember where projectCode = p1.projectCode and role = 'AR' AND trainingYN = 'Y') as arSsn,                                    ");
		this.query3.append("    				dbo.getExpertPoolName((select ssn from projectMember where projectCode = p1.projectCode  and role = 'AR' AND trainingYN = 'Y')) as arName,           ");
		this.query3.append("    				(select ssn from projectMember where projectCode = p1.projectCode  and role = 'PM' AND trainingYN = 'Y') as pmSsn,                                   ");
		this.query3.append("    				dbo.getExpertPoolName((select ssn from projectMember where projectCode = p1.projectCode  and role = 'PM' AND trainingYN = 'Y')) as pmName,    	     ");
		this.query3.append("    				(select ssn from projectMember where projectCode = p1.projectCode  and role = 'PL' AND trainingYN = 'Y') as plSsn,    	                             ");
		this.query3.append("    				dbo.getExpertPoolName((select ssn from projectMember where projectCode = p1.projectCode  and role = 'PL' AND trainingYN = 'Y')) as plName,    	     ");
		this.query3.append("    				p.ssn writerSsn, e.name writerName, p.trainingYN, e.jobClass, p.role, prd.ssn_Cnt     	                                                                         ");
		this.query3.append("    		from (      	 		                                                                                                                                     ");
		this.query3.append("    			select * from project where (projectState in ('3') or adminOpen = 'Y') and projectTypeCode = 'ND'						                                 ");
		this.query3.append("    		) p1                                                                                                                                                         ");
		this.query3.append("    		inner join (                                                                                                                                                 ");
		this.query3.append("    			select * from ProjectMember where role in ('PL', 'MB') and trainingYN = 'Y'                                                                              ");
		this.query3.append("    		) p                                                                                                                                                          ");
		this.query3.append("    		on p1.projectCode = p.projectCode                                                                                                                            ");
		this.query3.append("    		inner join (                                                                                                                                                 ");
		this.query3.append("    			select * from expertpool where (jobClass in ('B','C','J') or dept in ('9090'))                                                                                         ");
		this.query3.append("    		)e                                                                                                                                                           ");
		this.query3.append("    		on p.ssn = e.ssn                                                                                                                                                         ");
		this.query3.append("    		inner join (					                                                                                                                         ");
		this.query3.append("    			select projectcode, count(chargessn) as ssn_Cnt, chargessn From projectReportDetail where year = left('#######', 4) and month = right('#######', 2) group by projectcode, chargessn	             ");
		this.query3.append("    		) prd					                                                                                                                         ");
		this.query3.append("    		on  p1.projectcode = prd.projectcode and p.ssn = prd.chargessn 					                                                                                                                ");
		this.query3.append("    	) a  cross join (                                                                                                                                                ");
		this.query3.append("    		select  dt, left(dt, 4) AS assignYear, substring(dt, 5, 2) AS assignMonth                                                                                    ");
		this.query3.append("    		From (                                                                                                                                                       ");
		this.query3.append("            	select * from (select distinct left(convert(char(10),dateadd(d,number,'2020-01-01'),112), 6) + '01' dt from master..spt_values where type = 'P') a       ");
		this.query3.append("    			where dt = '#######'+'01'                                                                                                                                 ");
		this.query3.append("    		) r                                                                                                                                                          ");
		this.query3.append("    	)b 		                                                                                                                                                         ");
		this.query3.append("    	where left(a.realStartDate,6)+'01' <= (assignYear + assignMonth)                                                                                                ");
		this.query3.append("    ) a full outer join (                                                                                                                                                ");
		this.query3.append("        select projectCode as m_projectCode , assignYear as m_assignYear, assignMonth as m_assignMonth, writerSsn as m_writerSsn from ProjectMonthlyReport                                         ");
		this.query3.append("    ) b   											                                                                                                                     ");
		this.query3.append("    on a.projectCode = b.m_projectCode                                                                                                                                   ");
		this.query3.append("    and a.assignYear = b.m_assignYear                                                                                                                                    ");
		this.query3.append("    and a.assignMonth = b.m_assignMonth                                                                                                                                  ");
		this.query3.append("    and a.writerSsn = m_writerSsn                                                                                                                                  ");
		this.query3.append("    where dt is not null and m_projectCode is null                                                                                               ");

		return query3;
	}

	public void setQuery3(StringBuffer query3) {
		this.query3 = query3;
	}

	private static RowMapper monthlyReportScheduleDataRowMapper = new RowMapper() {
		public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
			MonthlyReport monthlyReport = new MonthlyReport();
			monthlyReport.setProjectCode(rs.getString("projectCode"));
			monthlyReport.setAssignDate(rs.getString("assignDate"));
			monthlyReport.setAssignYear(rs.getString("assignYear"));
			monthlyReport.setAssignMonth(rs.getString("assignMonth"));

			monthlyReport.setWeekOfProject(rs.getString("weekOfProject"));
			monthlyReport.setMonthOfProject(rs.getString("monthOfProject"));

			// monthlyReport.setReportTitle(rs.getString("reportTitle"));
			// monthlyReport.setReportContent(rs.getString("reportContent"));
			// monthlyReport.setEtcContent(rs.getString("progressRatio"));

			monthlyReport.setState("writer");
			monthlyReport.setWriterSsn(rs.getString("writerSsn"));
			monthlyReport.setWriterName(rs.getString("writerName"));
			monthlyReport.setReviewerSsn(rs.getString("pmSsn"));
			monthlyReport.setReviewerName(rs.getString("pmName"));
			monthlyReport.setApproverSsn(rs.getString("arSsn"));
			monthlyReport.setApproverName(rs.getString("arName"));
			return monthlyReport;
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	public List<MonthlyReport> getMonthlyReportList() {
		return getJdbcTemplate().query(this.getQuery().toString(), monthlyReportScheduleDataRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonthlyReport> getMonthlyReportListMissed(String projectCode) {
		String query = StringUtil.replace(this.getQuery3().toString(), "#######", DateUtil.getCurrentYyyymmdd().substring(0, 6));
		query = query + "and projectCode = '" + projectCode + "'";

		return getJdbcTemplate().query(query, monthlyReportScheduleDataRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MonthlyReport> getMonthlyReportUntilGivenDated(String yyyymmdd) {
		String query = StringUtil.replace(this.getQuery3().toString(), "#######", StringUtil.replace(yyyymmdd, "-", "").substring(0, 6));
		return getJdbcTemplate().query(query, monthlyReportScheduleDataRowMapper);
	}

}
