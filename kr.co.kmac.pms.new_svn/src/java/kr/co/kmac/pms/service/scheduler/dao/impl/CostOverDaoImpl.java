/*
 * $Id: CostOverDaoImpl.java,v 1.11 2018/10/16 01:43:34 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.service.scheduler.dao.CostOverDao;
import kr.co.kmac.pms.service.scheduler.data.CostOverData;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class CostOverDaoImpl extends JdbcDaoSupport implements CostOverDao {
	private static final String COSTOVER_QUERY = 
			  " SELECT projid FROM																	                                              "
			+ " (																					                                              "
			+ "	 Select	Projid,																		                                              "
			+ " 		sum(Monplanamtsum) AS Monplanamtsum,										                                              "
			+ " 		sum(Slip_Amt) AS Slip_Amt,													                                              "
			+ " 		sum(Monplanamtsum)-sum(Slip_Amt) AS Diff_Amt     							                                              "
			+ " 	From 																			                                              "
			+ " 	(							 													                                              "
			+ " 		(Select	substr(Mng_Item_Value2,1,7) As Projid,								                                              "
			+ " 			0 Monplanamtsum,														                                              "
			+ " 			sum(Debit_Amt+credit_Amt) As Slip_Amt									                                              "
			+ " 		 From	Ag0101d																                                              "
			+ " 		Where	Comp_Code = 'C'														                                              "
			+ " 		  And	Fact_Code = 'A01'													                                              "
			+ " 		  And	Draw_Dept Like '%'													                                              "
			+ " 		  And	Acct_Code Like '5%'                             					                                              "
			+ "		  	  And	Acct_Code In ('5000301','5000403','5000601','5000306','5000401','5000402','5000500','5001201','5001202','5001203','5001204','5001212','5001213','5000303','5001215','5000309','5000310')		  "
			+ " 		Group By substr(Mng_Item_Value2,1,7))										                                              "
			+ " 	   Union 																		                                              "
			+ " 		(Select	Projid,sum(Amt) As Monplanamtsum, 0 Slip_Amt 						                                              "
			+ " 		 From	A_Projdd 															                                              "
			+ " 		Where	Comp_Code ='C' 														                                              "
			+ " 		  And	Acct_Code Not Like '4%'												                                              "
			+ "		  	  And	Acct_Code In ('5000301','5000403','5000601','5000306','5000401','5000402','5000500','5001201','5001202','5001203','5001204','5001212','5001213','5000303','5001215','5000309','5000310')		  "
			+ " 		Group By Projid)                                        					                                              "
			+ "		 )																				                                              "
			+ " 	Group By Projid																	                                              "
			+ " )                                   												                                              "
			+ " WHERE diff_amt < 0 																	                                              "
			+ " AND monplanamtsum <> '0'                                            				                                              "
			+ "                                                                     				                                              "
			+ " UNION                                                               				                                              "
			+ "                                                                     				                                              "
			+ " SELECT projid FROM																	                                              "
			+ " (																					                                              "
			+ "	 Select	Projid,																		                                              "
			+ " 		sum(Monplanamtsum) AS Monplanamtsum,										                                              "
			+ " 		sum(Slip_Amt) AS Slip_Amt,													                                              "
			+ " 		sum(Monplanamtsum)-sum(Slip_Amt) AS Diff_Amt     							                                              "
			+ " 	From 																			                                              "
			+ " 	(							 													                                              "
			+ " 		(Select	substr(Mng_Item_Value2,1,7) As Projid,								                                              "
			+ " 			0 Monplanamtsum,														                                              "
			+ " 			sum(Debit_Amt+credit_Amt) As Slip_Amt									                                              "
			+ " 		 From	Ag0101d																                                              "
			+ " 		Where	Comp_Code = 'C'														                                              "
			+ "		  	  And	Fact_Code = 'A01'													                                              "
			+ " 		  And	Draw_Dept Like '%'													                                              "
			+ " 		  And	Acct_Code Like '5%'                             					                                              "
			+ "		  	  And	Acct_Code Not In ('5000301','5000403','5000601','5000306','5000401','5000402','5000500','5001201','5001202','5001203','5001204','5001212','5001213','5000303','5001215','5000309','5000310')	  "
			+ " 		Group By substr(Mng_Item_Value2,1,7))										                                              "
			+ " 	   Union 																		                                              "
			+ " 		(Select	Projid,sum(Amt) As Monplanamtsum, 0 Slip_Amt 						                                              "
			+ " 		 From	A_Projdd 															                                              "
			+ " 		Where	Comp_Code ='C' 														                                              "
			+ "		  	  And	Acct_Code Not Like '4%'												                                              "
			+ "		  	  And	Acct_Code Not In ('5000301','5000403','5000601','5000306','5000401','5000402','5000500','5001201','5001202','5001203','5001204','5001212','5001213','5000303','5001215','5000309','5000310')	  "
			+ " 		Group By Projid)                                        					                                              "
			+ "		 )																				                                              "
			+ " 	Group By Projid																	                                              "
			+ " )                                   												                                              "
			+ " WHERE diff_amt < 0 																	                                              "
			+ " AND monplanamtsum <> '0'															                                              ";

	private static final String PAYCOSTOVER_QUERY = "												                                              "
			+ " SELECT Projid FROM																	                                              "
			+ " (															     					                                              "
			+ "	Select	Projid,												            			                                              "
			+ "		sum(Monplanamtsum) AS Monplanamtsum,											                                              "
			+ "		sum(Slip_Amt) AS Slip_Amt,														                                              "
			+ "		sum(Monplanamtsum)-sum(Slip_Amt) AS Diff_Amt     								                                              "
			+ "	From 																				                                              "
			+ "	(							 														                                              "
			+ "		(Select	substr(Mng_Item_Value2,1,7) As Projid,				       				                                              "
			+ "			0 Monplanamtsum,								                			                                              "
			+ "			sum(Debit_Amt+credit_Amt) As Slip_Amt						     			                                              "
			+ "		 From	Ag0101d										                  			                                              "
			+ "		Where	Comp_Code = 'C'									               			                                              "
			+ "		  And	Fact_Code = 'A01'								                		                                              "
			+ "		  And	Draw_Dept Like '%'								                 		                                              "
			+ "		  And	Acct_Code Like '5%'								                   		                                              "
			+ "		  And	Acct_Code In ('5000301','5000403','5000601','5000306','5000401','5000402','5000500','5001201','5001202','5001203','5001204','5001212','5001213','5000303','5001215','5000309','5000310')			  "
			+ "		Group By substr(Mng_Item_Value2,1,7))							           		                                              "
			+ "	   Union																			                                              "
			+ "		(Select	Projid, sum(Amt) As Monplanamtsum, 0 Slip_Amt 	   				   		                                              "
			+ "		 From	A_Projdd 									                       		                                              "
			+ "		Where	Comp_Code ='C' 									                   		                                              "
			+ "		  And	Acct_Code Not Like '4%'								               		                                              "
			+ "		  And	Acct_Code In ('5000301','5000403','5000601','5000306','5000401','5000402','5000500','5001201','5001202','5001203','5001204','5001212','5001213','5000303','5001215','5000309','5000310')			  "
			+ "		Group By Projid)                                                           		                                              "
			+ "	)																					                                              "
			+ "	Group By Projid																		                                              "
			+ ")                                   											   		                                              "
			+ "WHERE diff_amt < 0 		  															                                              "
			+ "AND monplanamtsum <> '0' 															                                              ";

	private static final String ETCCOSTOVER_QUERY = "												                                              "
			+ " SELECT Projid FROM																	                                              "
			+ " (															     					                                              "
			+ "	Select	Projid,												            			                                              "
			+ "		sum(Monplanamtsum) AS Monplanamtsum,											                                              "
			+ "		sum(Slip_Amt) AS Slip_Amt,														                                              "
			+ "		sum(Monplanamtsum)-sum(Slip_Amt) AS Diff_Amt     								                                              "
			+ " From 																				                                              "
			+ " (							 														                                              "
			+ "		(Select	substr(Mng_Item_Value2,1,7) As Projid,				       				                                              "
			+ "			0 Monplanamtsum,								                			                                              "
			+ "			sum(Debit_Amt+credit_Amt) As Slip_Amt						     			                                              "
			+ "		 From	Ag0101d										                  			                                              "
			+ "		Where	Comp_Code = 'C'									               			                                              "
			+ "		  And	Fact_Code = 'A01'								                		                                              "
			+ "		  And	Draw_Dept Like '%'								                 		                                              "
			+ "		  And	Acct_Code Like '5%'								                   		                                              "
			+ "		  And	Acct_Code Not In ('5000301','5000403','5000601','5000306','5000401','5000402','5000500','5001201','5001202','5001203','5001204','5001212','5001213','5000303','5001215','5000309','5000310')		  "
			+ "		Group	By substr(Mng_Item_Value2,1,7))							           		                                              "
			+ "	   Union																			                                              "
			+ "		(Select	Projid, sum(Amt) As Monplanamtsum, 0 Slip_Amt 	   				   		                                              "
			+ "		 From	A_Projdd 									                       		                                              "
			+ "		Where	Comp_Code ='C' 									                   		                                              "
			+ "		  And	Acct_Code Not Like '4%'								               		                                              "
			+ "		  And	Acct_Code Not In ('5000301','5000403','5000601','5000306','5000401','5000402','5000500','5001201','5001202','5001203','5001204','5001212','5001213','5000303','5001215','5000309','5000310')		  "
			+ "		Group By Projid)                                                           		                                              "
			+ "	)																					                                              "
			+ "	Group By Projid																		                                              "
			+ ")                                   											   		                                              "
			+ "WHERE diff_amt < 0 											                       	                                              "
			+ "AND monplanamtsum <> '0' 															                                              ";

	
	private CostOverRetrieve costOverRetrieve;
	private UpdateProjectCostOver updateProjectCostOver;
	private ResetUpdateProjectCostOver resetUpdateProjectCostOver;
	// Job Date: 2007-10-15,16 Author: yhyim Description: declaration of classes for update
	private PayCostOverRetrieve payCostOverRetrieve;
	private UpdateProjectPayCostOver updateProjectPayCostOver;
	private EtcCostOverRetrieve etcCostOverRetrieve;
	private UpdateProjectEtcCostOver updateProjectEtcCostOver;

	protected class CostOverRetrieve extends MappingSqlQuery {
		protected CostOverRetrieve(DataSource ds, String query) {
			super(ds, query);
		}

		protected CostOverRetrieve(DataSource ds) {
			super(ds, COSTOVER_QUERY);
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CostOverData costOverData = new CostOverData();
			costOverData.setProjId(rs.getString("projid"));
			return costOverData;
		}
	}

	// Job Date: 2007-10-15 Author: yhyim Description: get project list of pay cost over
	protected class PayCostOverRetrieve extends CostOverRetrieve {
		protected PayCostOverRetrieve(DataSource ds) {
			super(ds, PAYCOSTOVER_QUERY);
			compile();
		}
	}

	// Job Date: 2007-10-15 Author: yhyim Description: get project list of etc cost over
	protected class EtcCostOverRetrieve extends CostOverRetrieve {
		protected EtcCostOverRetrieve(DataSource ds) {
			super(ds, ETCCOSTOVER_QUERY);
			compile();
		}
	}

	private DataSource erpDataSource;

	public DataSource getErpDataSource() {
		return erpDataSource;
	}

	public void setErpDataSource(DataSource erpDataSource) {
		this.erpDataSource = erpDataSource;
	}

	protected void initDao() throws Exception {
		this.costOverRetrieve = new CostOverRetrieve(getErpDataSource());
		this.updateProjectCostOver = new UpdateProjectCostOver(getDataSource());
		this.resetUpdateProjectCostOver = new ResetUpdateProjectCostOver(getDataSource());
		// Job Date: 2007-10-16 Author: yhyim Description: Instance of Classes
		this.payCostOverRetrieve = new PayCostOverRetrieve(getErpDataSource());
		this.updateProjectPayCostOver = new UpdateProjectPayCostOver(getDataSource());
		this.etcCostOverRetrieve = new EtcCostOverRetrieve(getErpDataSource());
		this.updateProjectEtcCostOver = new UpdateProjectEtcCostOver(getDataSource());
	}

	protected class UpdateProjectCostOver extends SqlUpdate {
		protected UpdateProjectCostOver(DataSource ds) {
			super(ds, " UPDATE project SET costOver='Y' WHERE projectState not between '6' and '7' and projectCode = ?  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int updateCostOver(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	// Job Date: 2007-10-16 Author: yhyim Description: update of pay cost over project state
	protected class UpdateProjectPayCostOver extends SqlUpdate {
		protected UpdateProjectPayCostOver(DataSource ds) {
			super(ds, " UPDATE project SET payCostOver='Y' WHERE projectState not between '6' and '7' and projectCode = ?  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int updateCostOver(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	// Job Date: 2007-10-16 Author: yhyim Description: update of etc cost over project state
	protected class UpdateProjectEtcCostOver extends SqlUpdate {
		protected UpdateProjectEtcCostOver(DataSource ds) {
			super(ds, " UPDATE project SET etcCostOver='Y' WHERE projectState not between '6' and '7' and projectCode = ?  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int updateCostOver(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ResetUpdateProjectCostOver extends SqlUpdate {
		protected ResetUpdateProjectCostOver(DataSource ds) {
			super(ds, " UPDATE project SET costOver='N', payCostOver='N', etcCostOver='N'  ");
			compile();
		}

		protected int resetUpdateCostOver() throws DataAccessException {
			return this.update();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CostOverData> costOverList(String start_date, String end_date) throws DataAccessException {
		// return costOverRetrieve.execute(new Object[]{start_date, end_date, start_date, end_date});
		return costOverRetrieve.execute();
	}

	// Job Date: 2007-10-15 Author: yhyim Description: get list of pay cost over project
	@SuppressWarnings("unchecked")
	@Override
	public List<CostOverData> payCostOverList(String start_date, String end_date) throws DataAccessException {
		return payCostOverRetrieve.execute();
	}

	// Job Date: 2007-10-15 Author: yhyim Description: get list of pay etc over project
	@SuppressWarnings("unchecked")
	@Override
	public List<CostOverData> etcCostOverList(String start_date, String end_date) throws DataAccessException {
		return etcCostOverRetrieve.execute();
	}

	@Override
	public void updateProjectCostOver(String projectCode) throws DataAccessException {
		// resetUpdateProjectCostOver.resetUpdateCostOver(); // Job Date: 2007-07-25 Description: move reset function to next method
		updateProjectCostOver.updateCostOver(projectCode);
	}

	// Job Date: 2007-07-26 Author: yhyim Description: Add method for reset Project cost over
	@Override
	public void resetUpdateProjectCostOver() throws DataAccessException {
		resetUpdateProjectCostOver.resetUpdateCostOver();
	}

	@Override
	public void updateProjectEtcCostOver(String projectCode) throws DataAccessException {
		updateProjectEtcCostOver.updateCostOver(projectCode);
	}

	@Override
	public void updateProjectPayCostOver(String projectCode) throws DataAccessException {
		updateProjectPayCostOver.updateCostOver(projectCode);
	}
}
