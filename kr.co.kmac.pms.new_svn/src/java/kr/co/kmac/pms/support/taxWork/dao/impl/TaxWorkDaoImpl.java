package kr.co.kmac.pms.support.taxWork.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.support.exception.SupportException;
import kr.co.kmac.pms.support.taxWork.dao.TaxWorkDao;
import kr.co.kmac.pms.support.taxWork.data.TaxWorkMobileData;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

public class TaxWorkDaoImpl extends JdbcDaoSupport implements TaxWorkDao {
	
	private DataSource erpDataSource;
	private SalarySelectQuery salarySelectQuery;
	
	protected void initDao()throws Exception {
		this.salarySelectQuery = new SalarySelectQuery(getDataSource());
	}

	public DataSource getErpDataSource() {
		return erpDataSource;
	}

	public void setErpDataSource(DataSource erpDataSource) {
		this.erpDataSource = erpDataSource;
	}

	@Override
	public String comp_code(String empno) throws SupportException {
		// TODO Auto-generated method stub
		setDataSource(getErpDataSource());
		String comp_code = (String)getJdbcTemplate().queryForObject("SELECT comp_code FROM perinfo WHERE sno = '" + empno + "'", String.class);
		
		return comp_code;
	}

	@Override
	public String jumin(String empno) throws SupportException {
		// TODO Auto-generated method stub
		setDataSource(getErpDataSource());
		String jumin = (String)getJdbcTemplate().queryForObject("SELECT ju_no FROM perinfo WHERE sno = '" + empno + "'", String.class);
		
		return jumin;
	}

	@Override
	public TaxWorkMobileData select(String idx) throws DataAccessException {
		// TODO Auto-generated method stub
		TaxWorkMobileData taxWorkMobileData = (TaxWorkMobileData) this.salarySelectQuery.execute(new Object[]{idx}).get(0);
		
		return taxWorkMobileData;
	}
	
	protected class SalarySelectQuery extends MappingSqlQuery {
		
		protected SalarySelectQuery(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected SalarySelectQuery(DataSource ds) {
			super(ds, "SELECT * FROM salaryData WHERE idx = ?");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			TaxWorkMobileData taxWorkMobileData = new TaxWorkMobileData();
			
			taxWorkMobileData.setIdx(rs.getString("idx"));
			taxWorkMobileData.setProv_date(rs.getString("prov_date"));
			taxWorkMobileData.setSsn(rs.getString("ssn"));
			taxWorkMobileData.setJik(rs.getString("jik"));
			taxWorkMobileData.setEmpno(rs.getString("empno"));
			taxWorkMobileData.setBank(rs.getString("bank"));
			taxWorkMobileData.setBankNo(rs.getString("bankNo"));
			taxWorkMobileData.setRealPay(rs.getString("realPay"));
			taxWorkMobileData.setBasicPay(rs.getString("basicPay"));
			taxWorkMobileData.setJikPay(rs.getString("jikPay"));
			taxWorkMobileData.setTaskPay(rs.getString("taskPay"));
			taxWorkMobileData.setLunchPay(rs.getString("lunchPay"));
			taxWorkMobileData.setCarPay1(rs.getString("carPay1"));
			taxWorkMobileData.setCarPay2(rs.getString("carPay2"));
			taxWorkMobileData.setCarPay3(rs.getString("carPay3"));
			taxWorkMobileData.setOthersPay(rs.getString("othersPay"));
			taxWorkMobileData.setBonus(rs.getString("bonus"));
			taxWorkMobileData.setPieceRate(rs.getString("pieceRate"));
			taxWorkMobileData.setBonus1(rs.getString("bonus1"));
			taxWorkMobileData.setProfitShare(rs.getString("profitShare"));
			taxWorkMobileData.setBasicPaySo(rs.getString("basicPaySo"));
			taxWorkMobileData.setAllowanceSo(rs.getString("allowanceSo"));
			taxWorkMobileData.setAllowance(rs.getString("allowance"));
			taxWorkMobileData.setIncomeTax1(rs.getString("incomeTax1"));
			taxWorkMobileData.setIncomeTax2(rs.getString("incomeTax2"));
			taxWorkMobileData.setInsurance1(rs.getString("insurance1"));
			taxWorkMobileData.setInsurance2(rs.getString("insurance2"));
			taxWorkMobileData.setInsurance3(rs.getString("insurance3"));
			taxWorkMobileData.setInsurance4(rs.getString("insurance4"));
			taxWorkMobileData.setClub(rs.getString("club"));
			taxWorkMobileData.setParkingPay(rs.getString("parkingPay"));
			taxWorkMobileData.setExmption1(rs.getString("exmption1"));
			taxWorkMobileData.setExmption2(rs.getString("exmption2"));
			taxWorkMobileData.setIncomeTax3(rs.getString("incomeTax3"));
			taxWorkMobileData.setIncomeTax4(rs.getString("incomeTax4"));
			taxWorkMobileData.setInsurance5(rs.getString("insurance5"));
			taxWorkMobileData.setInsurance6(rs.getString("insurance6"));
			taxWorkMobileData.setInsurance7(rs.getString("insurance7"));
			taxWorkMobileData.setInsurance8(rs.getString("insurance8"));
			taxWorkMobileData.setTotalExmption(rs.getString("totalExmption"));
			taxWorkMobileData.setWriter(rs.getString("writer"));
			taxWorkMobileData.setGubun(rs.getString("gubun"));
			taxWorkMobileData.setRemarks(rs.getString("remarks"));
			taxWorkMobileData.setResignPay(rs.getString("resignPay"));
			taxWorkMobileData.setInsurance9(rs.getString("insurance9"));
			taxWorkMobileData.setRegdate(rs.getString("regdate"));
			taxWorkMobileData.setRemarks1(rs.getString("remarks_1"));
			taxWorkMobileData.setGroupTaskPay(rs.getString("groupTaskPay"));
			taxWorkMobileData.setSpecialPay(rs.getString("specialPay"));
			
			return taxWorkMobileData;
		}
	}

}
