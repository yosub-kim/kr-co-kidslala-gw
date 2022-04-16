package kr.co.kmac.pms.project.master.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.master.dao.ErpProjectMemberDao;
import kr.co.kmac.pms.project.master.data.ProjectMember;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

public class ErpProjectMemberDaoImpl extends JdbcDaoSupport implements ErpProjectMemberDao {
	private ErpProjectMemberSelectQuery1 erpProjectMemberSelectQuery1;

	@Override
	protected void initDao() throws Exception {
		this.erpProjectMemberSelectQuery1 = new ErpProjectMemberSelectQuery1(getDataSource());
	}

	protected class ErpProjectMemberSelectQuery1 extends MappingSqlQuery {
		protected ErpProjectMemberSelectQuery1(DataSource ds) {
			super(
					ds,
										   "	SET ANSI_WARNINGS OFF																					  "
									     + " 	SET ARITHIGNORE ON																					 	                                                                          "                     
										 + "		SET ARITHABORT OFF																					       																	  "
										 + "    SELECT M3.projectCode,                                                                                      																	  "
										 + "    		M3.EMPNO AS ssn,                                                             						   																	  "
										 + "    		M3.name,  																																								  "
										 + "		(CASE WHEN (M3.PROJECTSCALE = 'L') THEN 																																	  "
										 + "			(CASE WHEN M3.PJTRNK = 'PL' THEN 'PL' ELSE 'MB' END)																													  "
										 + "		ELSE																																										  "
										 + "			(CASE WHEN M3.PJTRNK = 'subPL' THEN 'PL' ELSE 'MB' END)																													  "
										 + "		END)																																										  "
										 + "		AS role,																																									  "
										 + "    		M4.wantsaleamt AS cost,                                                         			  			   																  "
										 + "    		'Y' AS trainingYN,                                                           						   																	  "
										 + "    		(CASE WHEN M3.OPTYPE = '01' THEN 0 ELSE M4.SEPUNITAMT END) AS contributionCost,    					   																	  "
										 + "    		M3.workMD AS teachingDay,                                                    						   																	  "
										 + "    		M3.RATE as resRate,                                                 		 						   																	  "
										 + "    		M3.position,																		  				   																	  "
										 + "    		dbo.getJobClass(M3.EMPNO) as jobClass,																   																	  "
										 + "    		amt.MIN_AMT as minAmt, amt.MAX_AMT as maxAmt,											  			   																	  "
										 + "    		amt.EDU_MIN as minEdu, amt.EDU_MAX  as maxEdu,										  				   																	  "
										 + "    		amt.MM_MIN_AMT as minMMAmt, amt.MM_MAX_AMT as maxMMAmt												   																	  "
										 + "    FROM																								      	   																	  "
										 + "    (																									       																		  "
										 + "    	SELECT M2.pjtType, M2.projectCode, M2.entNo, M2.empNo, M2.name, M2.position, M2.rate, M2.stdCost, SUM(M2.workMD) AS workMD, M2.OPTYPE, M2.PROJECTSCALE, M2.PJTRNK	       	  "
										 + "    	FROM 																							       		    															  "
										 + "    	(																								      		   																  "
										 + "    		/* 교육 프로젝트 성과급 정보는 MD를 곱해서 계산된 값이므로 MD로 나누어 시간당 단가를 구한다 */					     					   								  "
										 + "    		SELECT	M1.*, 																				       		    															  "
										 + "    				(CASE WHEN M1.jobClass='C' OR M1.jobClass='J' THEN												   																  "
										 + "    					(CASE WHEN pjtType='BTE' THEN round(M1.realAmt / M1.workMD, 0)								   																  "
										 + "    						ELSE M1.realAmt END)     														      	   																  "
										 + "    				ELSE (CASE WHEN pjtType='BTE' THEN 0 ELSE M1.realAmt END) END) AS stdCost					   																	  "
										 + "    		FROM																						       		   																  "
										 + "    		(																							      		   																  "
										 + "    				SELECT	(CASE mt.pjtType 																      	   																  "
										 + "    							WHEN '03' THEN 'BTE' 															       																  "
										 + "    							WHEN '04' THEN 'BTE' 															       																  "
										 + "    							WHEN '07' THEN 'BTE' 															       																  "
										 + "    							WHEN '09' THEN 'BTE' 															       																  "
										 + "    							WHEN '10' THEN 'BTE' 															       																  "
										 + "    							WHEN '18' THEN 'BTE' 															       																  "
										 + "    							WHEN '25' THEN 'BTE' 															       																  "
										 + "    							WHEN '31' THEN 'BTE'															        															  "
										 + "    							WHEN '34' THEN 'BTE'															       																  "
										 + "    							WHEN '35' THEN 'BTE'															        															  "
										 + "    							WHEN '40' THEN 'BTE'															        															  "
										 + "    						 ELSE 'BTA' END) AS pjtType, mt.projid AS projectCode, 									   																  "
										 + "    						md.entNo, e.jobClass, md.empNo, e.name, 												   																  "
										 + "							(CASE WHEN e.jobClass = 'C' THEN md.POSIRNK												   																  "
										 + "								  WHEN e.jobClass in ('A','B','J','H','N') THEN e.companyPosition							   														  "
										 + "								  ELSE e.companyPositionName END) as position,										   			  													  "
										 + "    						md.YYYYMM, md.seq, 	mt.OPTYPE,													           																  "
										 + "    						md.rate, md.workMD, md.insenAmt,mt.PROJECTSCALE, md.PJTRNK,													       										  "
										 + "    						/* insenAmt 값이 성과요율을 곱한 값이므로 역으로 나누어 준다 */							  	  			   												  			  "
										 + "							1 as realAmt		 																																	  "
										 + "    				  FROM	DWPM.DBO.DW_PROJECTWORKMAN_MM md, DWPM.DBO.DW_PROJECTMST mt, expertPool e								       											  "
										 + "    				 WHERE	1=1																			                   															  "
										 + "    				   AND	md.entno = mt.entno																           																  "
										 + "    				   AND	md.empNo = e.ssn																           																  "
										 + "    		) M1																							             															  "
										 + "    	) M2																								           																  "
										 + "    	GROUP BY M2.pjtType, M2.projectCode, M2.entNo, M2.empNo, M2.name, M2.position, M2.rate, M2.stdCost, M2.OPTYPE, M2.PROJECTSCALE, M2.PJTRNK	 		   						  "
										 + "    ) M3, DWPM.DBO.DW_PROJECTWORKMAN M4, expertPool e																	       														  "
										 + "	   LEFT OUTER JOIN																								   																  "
										 + "	   DWPM.DBO.DW_view_CP_Amt amt																							   														  "
										 + "	   ON e.rate =  amt.CP_CODE																						   																  "
										 + "    WHERE M3.EMPNO = M4.EMPNO																				       																	  "
										 + "    AND M3.ENTNO = M4.ENTNO																				            																  "
										 + "    AND M3.EMPNO = e.ssn																							   																  "
										 + "    AND M3.ENTNO = ?																						       																	  "
										 + "    ORDER BY 2																							         																	  "

			);
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ErpProjectMemberSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setSsn(rs.getString("ssn"));
			projectMember.setName(rs.getString("name"));
			projectMember.setRole(rs.getString("role"));
			projectMember.setCost(new DecimalFormat("###.#####").format(rs.getDouble("cost")));
			projectMember.setTrainingYn(rs.getString("trainingYn"));
			projectMember.setContributionCost(rs.getString("contributionCost"));
			projectMember.setTeachingDay(rs.getString("teachingDay"));
			projectMember.setResRate(rs.getString("resRate"));
			projectMember.setPosition(rs.getString("position"));
			projectMember.setJobClass(rs.getString("jobClass"));
			projectMember.setMaxAmt(rs.getString("maxAmt"));
			projectMember.setMinAmt(rs.getString("minAmt"));
			projectMember.setMaxEdu(rs.getString("maxEdu"));
			projectMember.setMinEdu(rs.getString("minEdu"));
			projectMember.setMaxMMAmt(rs.getString("maxMMAmt"));
			projectMember.setMinMMAmt(rs.getString("minMMAmt"));
			return projectMember;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> select(String entNo) throws DataAccessException {
		return this.erpProjectMemberSelectQuery1.execute(new Object[] { entNo });
	}

}
