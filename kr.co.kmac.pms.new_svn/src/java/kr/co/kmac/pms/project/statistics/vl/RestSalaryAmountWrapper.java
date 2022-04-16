/*
 * $Id: RestSalaryAmountWrapper.java,v 1.1 2019/02/09 05:47:23 cvs Exp $
 * created by    : ¿”øµ»∆
 * creation-date : 2019. 01. 20.
 */
package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.RestSalaryAmount;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class RestSalaryAmountWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object arg0) {
		ResultSet rs = (ResultSet) arg0;
		try {
			RestSalaryAmount restSalaryAmount = new RestSalaryAmount();
			restSalaryAmount.setProjectCode(rs.getString("projectCode"));
			restSalaryAmount.setPlanAmount(rs.getString("planAmount"));
			restSalaryAmount.setExeAmount(rs.getString("exeAmount"));
			restSalaryAmount.setMonthlyAmount(rs.getString("monthlyAmount"));
			restSalaryAmount.setDiffAmount(rs.getString("diffAmount"));
			return restSalaryAmount;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("RestSalaryAmountWrapper fails", null, e);
		}
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}
