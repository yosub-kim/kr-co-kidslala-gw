package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.RestSalaryAmount;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class IncentiveSalaryAmountWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			RestSalaryAmount restSalaryAmount = new RestSalaryAmount();
			restSalaryAmount.setProjectCode(rs.getString("projectCode"));
			restSalaryAmount.setPlanAmount(rs.getString("planAmount"));
			restSalaryAmount.setExeAmount(rs.getString("exeAmount"));
			restSalaryAmount.setDiffAmount(rs.getString("diffAmount"));
			return restSalaryAmount;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("IncentiveSalaryAmountWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
