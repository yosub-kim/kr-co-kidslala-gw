package kr.co.kmac.pms.project.expense.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.expense.dao.ExpenseHistoryDao;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;

public class ExpenseHistoryDaoImpl extends JdbcDaoSupport implements ExpenseHistoryDao {

	private ExpenseHistoryStoredProcedure expenseHistoryStoredProcedure;
	private ExpenseHistoryStoredProcedure_rest expenseHistoryStoredProcedure_rest;
	private ExpenseHistoryStoredProcedure_mmplan expenseHistoryStoredProcedure_mmplan;

	@Override
	protected void initDao() throws Exception {
		super.initDao();
		this.expenseHistoryStoredProcedure = new ExpenseHistoryStoredProcedure(getDataSource());
		this.expenseHistoryStoredProcedure_rest = new ExpenseHistoryStoredProcedure_rest(getDataSource());
		this.expenseHistoryStoredProcedure_mmplan = new ExpenseHistoryStoredProcedure_mmplan(getDataSource());
	}

	// 프로젝트 성과급 지급 내역(100%) 
	private class ExpenseHistoryStoredProcedure extends StoredProcedure {
		protected ExpenseHistoryStoredProcedure(DataSource ds) {
			super(ds, "dbo.sp_kmacPms_projectSalarySum_mmplan2");
			declareParameter(new SqlParameter("projectCode", Types.VARCHAR));
			compile();
		}

		public Map execute(String projectCode) throws DataAccessException {
			Map<String, String> inputs = new HashMap<String, String>();
			inputs.put("projectCode", projectCode);
			return super.execute(inputs);
		}
	}

	@Override
	public List<List<String>> select(String projectCode) throws DataAccessException {
		int cnt = this.getJdbcTemplate().queryForInt(
				"select count(*) from ProjectTeachingFeeMDetail p                    "
						+ "where approvalYn='Y' and billSendYn='Y' and projectCode=? ", new Object[] { projectCode });
		cnt += this.getJdbcTemplate().queryForInt(
				"select count(*) from ProjectTeachingRestFeeDetail p                    "
						+ "where approvalYn='Y' and billSendYn='Y' and projectCode=? ", new Object[] { projectCode });
		List<List<String>> outterList = new ArrayList<List<String>>();

		if (cnt > 0) {
			Map results = this.expenseHistoryStoredProcedure.execute(projectCode);
			for (Iterator it = results.keySet().iterator(); it.hasNext();) {
				ArrayList<ListOrderedMap> list = (ArrayList<ListOrderedMap>) results.get(it.next());
				List<String> innerList = null;
				if (list.size() > 0) {
					innerList = new ArrayList<String>();
					ListOrderedMap listOrderedMap = (ListOrderedMap) list.get(0);
					for (Iterator it1 = listOrderedMap.keySet().iterator(); it1.hasNext();) {
						innerList.add((String) it1.next());
					}
					outterList.add(innerList);
				}
				for (ListOrderedMap listOrderedMap : list) {
					innerList = new ArrayList<String>();
					for (Iterator it1 = listOrderedMap.keySet().iterator(); it1.hasNext();) {
						String hearderName = (String) it1.next();
						innerList.add((String) (listOrderedMap.get(hearderName) + ""));
					}
					outterList.add(innerList);
				}
			}
		}
		return outterList;
	}
	
	// 프로젝트 성과급 지급 내역(20%) 
	private class ExpenseHistoryStoredProcedure_rest extends StoredProcedure {
		protected ExpenseHistoryStoredProcedure_rest(DataSource ds) {
			super(ds, "dbo.sp_kmacPms_projectSalarySum_rest");
			declareParameter(new SqlParameter("projectCode", Types.VARCHAR));
			compile();
		}

		public Map execute(String projectCode) throws DataAccessException {
			Map<String, String> inputs = new HashMap<String, String>();
			inputs.put("projectCode", projectCode);
			return super.execute(inputs);
		}
	}

	@Override
	public List<List<String>> select_rest(String projectCode) throws DataAccessException {
		int cnt = this.getJdbcTemplate().queryForInt(
				"select count(*) from ProjectTeachingFeeMDetail p                    "
						+ "where approvalYn='Y' and billSendYn='Y' and projectCode=? ", new Object[] { projectCode });
		cnt += this.getJdbcTemplate().queryForInt(
				"select count(*) from ProjectTeachingRestFeeDetail p                    "
						+ "where approvalYn='Y' and billSendYn='Y' and projectCode=? ", new Object[] { projectCode });
		List<List<String>> outterList = new ArrayList<List<String>>();

		if (cnt > 0) {
			Map results = this.expenseHistoryStoredProcedure_rest.execute(projectCode);
			for (Iterator it = results.keySet().iterator(); it.hasNext();) {
				ArrayList<ListOrderedMap> list = (ArrayList<ListOrderedMap>) results.get(it.next());
				List<String> innerList = null;
				if (list.size() > 0) {
					innerList = new ArrayList<String>();
					ListOrderedMap listOrderedMap = (ListOrderedMap) list.get(0);
					for (Iterator it1 = listOrderedMap.keySet().iterator(); it1.hasNext();) {
						innerList.add((String) it1.next());
					}
					outterList.add(innerList);
				}
				for (ListOrderedMap listOrderedMap : list) {
					innerList = new ArrayList<String>();
					for (Iterator it1 = listOrderedMap.keySet().iterator(); it1.hasNext();) {
						String hearderName = (String) it1.next();
						innerList.add((String) (listOrderedMap.get(hearderName) + ""));
					}
					outterList.add(innerList);
				}
			}
		}
		return outterList;
	}
	
	// 프로젝트 성과급 지급 내역(80%) 
	private class ExpenseHistoryStoredProcedure_mmplan extends StoredProcedure {
		protected ExpenseHistoryStoredProcedure_mmplan(DataSource ds) {
			super(ds, "dbo.sp_kmacPms_projectSalarySum_mmplan2");
			declareParameter(new SqlParameter("projectCode", Types.VARCHAR));
			compile();
		}

		public Map execute(String projectCode) throws DataAccessException {
			Map<String, String> inputs = new HashMap<String, String>();
			inputs.put("projectCode", projectCode);
			return super.execute(inputs);
		}
	}

	@Override
	public List<List<String>> select_mmplan(String projectCode) throws DataAccessException {
		int cnt = this.getJdbcTemplate().queryForInt(
				"select count(*) from ProjectTeachingFeeMDetail p                    "
						+ "where approvalYn='Y' and billSendYn='Y' and projectCode=? ", new Object[] { projectCode });
		cnt += this.getJdbcTemplate().queryForInt(
				"select count(*) from ProjectTeachingRestFeeDetail p                    "
						+ "where approvalYn='Y' and billSendYn='Y' and projectCode=? ", new Object[] { projectCode });
		List<List<String>> outterList = new ArrayList<List<String>>();

		if (cnt > 0) {
			Map results = this.expenseHistoryStoredProcedure_mmplan.execute(projectCode);
			for (Iterator it = results.keySet().iterator(); it.hasNext();) {
				ArrayList<ListOrderedMap> list = (ArrayList<ListOrderedMap>) results.get(it.next());
				List<String> innerList = null;
				if (list.size() > 0) {
					innerList = new ArrayList<String>();
					ListOrderedMap listOrderedMap = (ListOrderedMap) list.get(0);
					for (Iterator it1 = listOrderedMap.keySet().iterator(); it1.hasNext();) {
						innerList.add((String) it1.next());
					}
					outterList.add(innerList);
				}
				for (ListOrderedMap listOrderedMap : list) {
					innerList = new ArrayList<String>();
					for (Iterator it1 = listOrderedMap.keySet().iterator(); it1.hasNext();) {
						String hearderName = (String) it1.next();
						innerList.add((String) (listOrderedMap.get(hearderName) + ""));
					}
					outterList.add(innerList);
				}
			}
		}
		return outterList;
	}

}
