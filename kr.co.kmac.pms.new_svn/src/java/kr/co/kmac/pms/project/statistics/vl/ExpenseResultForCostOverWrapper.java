package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.statistics.data.ExpenseResultForCostOver;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ExpenseResultForCostOverWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	@Override
	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ExpenseResultForCostOver expenseResultForCostOver = new ExpenseResultForCostOver();
			expenseResultForCostOver.setRunningDivCodeCnt(rs.getInt("runningDivCodeCnt"));
			expenseResultForCostOver.setRunningDivCode(rs.getString("runningDivCode"));
			expenseResultForCostOver.setRunningDivName(rs.getString("runningDivName"));
			expenseResultForCostOver.setProjectCodeCnt(rs.getInt("projectCodeCnt"));
			expenseResultForCostOver.setProjectCode(rs.getString("projectCode"));
			expenseResultForCostOver.setProjectName(rs.getString("projectName"));
			expenseResultForCostOver.setAcctCode(rs.getString("acctCode"));
			expenseResultForCostOver.setPlanAmount(StringUtil.longt2Money(rs.getLong("planAmount")));
			expenseResultForCostOver.setTotalAmount(StringUtil.longt2Money(rs.getLong("totalAmount")));
			expenseResultForCostOver.setExeAmount(StringUtil.longt2Money(rs.getLong("exeAmount")));
			expenseResultForCostOver.setRealtimeSalary(StringUtil.longt2Money(rs.getLong("realtimeSalary")));
			//expenseResultForCostOver.setRealTimeSalaryAddedBasicSalary(StringUtil.longt2Money(rs.getLong("realTimeSalaryAddedBasicSalary")));
			expenseResultForCostOver.setGrandPlanAmount(StringUtil.longt2Money(rs.getLong("grandPlanAmount")));
			expenseResultForCostOver.setGrandTotalAmount(StringUtil.longt2Money(rs.getLong("grandTotalAmount")));
			expenseResultForCostOver.setGrandExeAmount(StringUtil.longt2Money(rs.getLong("grandExeAmount")));
			expenseResultForCostOver.setGrandRealtimeSalary(StringUtil.longt2Money(rs.getLong("grandRealtimeSalary")));
			//expenseResultForCostOver.setGrandRealTimeSalaryAddedBasicSalary(StringUtil.longt2Money(rs.getLong("grandRealTimeSalaryAddedBasicSalary")));
			expenseResultForCostOver.setIsOverYn(rs.getString("isOverYn"));
			return expenseResultForCostOver;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpenseResultForCostOverWrapper fails", null, e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
