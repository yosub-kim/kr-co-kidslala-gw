package kr.co.kmac.pms.project.expense.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExpenseDetail {
	private String projectCode;
	private String year;
	private String month;

	private String ssn;
	private String uid;
	private String name;
	private String jobClass;
	private String jobClassErp;
	private String deptType;
	private int seq;
	private String amount;
	private String tmCheckYn;
	private String ctmCheckYn;
	private String approvalYn;
	private String billSendYn;
	private String payYn;

	private String erpSenderSsn;

	private String tea_amt;

	private String resRate;
	
	private String job;
	private String cnt;
	private String money;
	private String realmoney;
	private String projectTypeCode;
	
	private String tea_amt_seventy;
	private String tea_amt_thirty;
	
	public String getTea_amt_seventy() {
		return tea_amt_seventy;
	}

	public void setTea_amt_seventy(String tea_amt_seventy) {
		this.tea_amt_seventy = tea_amt_seventy;
	}

	public String getTea_amt_thirty() {
		return tea_amt_thirty;
	}

	public void setTea_amt_thirty(String tea_amt_thirty) {
		this.tea_amt_thirty = tea_amt_thirty;
	}

	public String getProjectTypeCode() {
		return projectTypeCode;
	}

	public void setProjectTypeCode(String projectTypeCode) {
		this.projectTypeCode = projectTypeCode;
	}

	public String getRealmoney() {
		return realmoney;
	}

	public void setRealmoney(String realmoney) {
		this.realmoney = realmoney;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getResRate() {
		return resRate;
	}

	public void setResRate(String resRate) {
		this.resRate = resRate;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the projectCode
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
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the tmCheckYn
	 */
	public String getTmCheckYn() {
		return tmCheckYn;
	}

	/**
	 * @param tmCheckYn the tmCheckYn to set
	 */
	public void setTmCheckYn(String tmCheckYn) {
		this.tmCheckYn = tmCheckYn;
	}

	/**
	 * @return the ctmCheckYn
	 */
	public String getCtmCheckYn() {
		return ctmCheckYn;
	}

	/**
	 * @param ctmCheckYn the ctmCheckYn to set
	 */
	public void setCtmCheckYn(String ctmCheckYn) {
		this.ctmCheckYn = ctmCheckYn;
	}

	/**
	 * @return the approvalYn
	 */
	public String getApprovalYn() {
		return approvalYn;
	}

	/**
	 * @param approvalYn the approvalYn to set
	 */
	public void setApprovalYn(String approvalYn) {
		this.approvalYn = approvalYn;
	}

	/**
	 * @return the billSendYn
	 */
	public String getBillSendYn() {
		return billSendYn;
	}

	/**
	 * @param billSendYn the billSendYn to set
	 */
	public void setBillSendYn(String billSendYn) {
		this.billSendYn = billSendYn;
	}

	/**
	 * @return the payYn
	 */
	public String getPayYn() {
		return payYn;
	}

	/**
	 * @param payYn the payYn to set
	 */
	public void setPayYn(String payYn) {
		this.payYn = payYn;
	}

	public String getErpSenderSsn() {
		return erpSenderSsn;
	}

	public void setErpSenderSsn(String erpSenderSsn) {
		this.erpSenderSsn = erpSenderSsn;
	}

	public String getTea_amt() {
		return tea_amt;
	}

	public void setTea_amt(String teaAmt) {
		tea_amt = teaAmt;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobClassErp() {
		return jobClassErp;
	}

	public void setJobClassErp(String jobClassErp) {
		this.jobClassErp = jobClassErp;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
