package kr.co.kmac.pms.project.expense.data;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExpenseHisory {

	private String projectCode;
	private String year;
	private String month;
	private List<ExpenseDetail> expenseDetailList;

	/**
	 * @return the projetCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projetCode the projetCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the expenseDetailList
	 */
	public List<ExpenseDetail> getExpenseDetailList() {
		return expenseDetailList;
	}

	/**
	 * @param expenseDetailList the expenseDetailList to set
	 */
	public void setExpenseDetailList(List<ExpenseDetail> expenseDetailList) {
		this.expenseDetailList = expenseDetailList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
