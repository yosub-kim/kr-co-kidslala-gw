package kr.co.kmac.pms.project.expense.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionForm;

/**
 * @struts:form name="projectExpenseAction"
 */
public class ExpenseForm extends ActionForm {

	private static final long serialVersionUID = -139554310561188606L;

	private String projectCode;
	private String year;
	private String month;

	private String[] ssn;
	private int[] seq;
	private String[] amount;
	private String[] payYn;
	
	private String[] puSsn;
	private int[] puSeq;
	private String[] puMMRate;
	private String[] restAmount;
	private String[] puSsn2;
	private String[] puSeq2;

	public String[] getPuSsn2() {
		return puSsn2;
	}

	public void setPuSsn2(String[] puSsn2) {
		this.puSsn2 = puSsn2;
	}

	public String[] getPuSeq2() {
		return puSeq2;
	}

	public void setPuSeq2(String[] puSeq2) {
		this.puSeq2 = puSeq2;
	}

	public String[] getRestAmount() {
		return restAmount;
	}

	public void setRestAmount(String[] restAmount) {
		this.restAmount = restAmount;
	}

	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode the projectCode to set
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
	 * @return the ssn
	 */
	public String[] getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String[] ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the amount
	 */
	public String[] getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String[] amount) {
		this.amount = amount;
	}

	/**
	 * @return the seq
	 */
	public int[] getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int[] seq) {
		this.seq = seq;
	}

	/**
	 * @return the payYn
	 */
	public String[] getPayYn() {
		return payYn;
	}

	/**
	 * @param payYn the payYn to set
	 */
	public void setPayYn(String[] payYn) {
		this.payYn = payYn;
	}
	
	public String[] getPuSsn() {
		return puSsn;
	}

	public void setPuSsn(String[] puSsn) {
		this.puSsn = puSsn;
	}

	public int[] getPuSeq() {
		return puSeq;
	}

	public void setPuSeq(int[] puSeq) {
		this.puSeq = puSeq;
	}

	public String[] getPuMMRate() {
		return puMMRate;
	}

	public void setPuMMRate(String[] puMMRate) {
		this.puMMRate = puMMRate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
