package kr.co.kmac.pms.project.expense.data;

public class ExpenseDetailAdded extends ExpenseDetail{
	private String totalMM;
	private String totalBudget;
	private String monthlyMM;
	private String monthlyBudget;
	private String restBudget;
	private String restAmount;
	
	private String jobClassName;
	private String mmRate;
	
	private String restCtmCheckYN;
	private String restApprovalYN;
	
	public String getTotalMM() {
		return totalMM;
	}
	public void setTotalMM(String totalMM) {
		this.totalMM = totalMM;
	}
	public String getTotalBudget() {
		return totalBudget;
	}
	public void setTotalBudget(String totalBudget) {
		this.totalBudget = totalBudget;
	}
	public String getMonthlyMM() {
		return monthlyMM;
	}
	public void setMonthlyMM(String monthlyMM) {
		this.monthlyMM = monthlyMM;
	}
	public String getMonthlyBudget() {
		return monthlyBudget;
	}
	public void setMonthlyBudget(String monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}
	public String getRestBudget() {
		return restBudget;
	}
	public void setRestBudget(String restBudget) {
		this.restBudget = restBudget;
	}
	public String getRestAmount() {
		return restAmount;
	}
	public void setRestAmount(String restAmount) {
		this.restAmount = restAmount;
	}
	
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getMmRate() {
		return mmRate;
	}
	public void setMmRate(String mmRate) {
		this.mmRate = mmRate;
	}
	public String getRestCtmCheckYN() {
		return restCtmCheckYN;
	}
	public void setRestCtmCheckYN(String restCtmCheckYN) {
		this.restCtmCheckYN = restCtmCheckYN;
	}
	public String getRestApprovalYN() {
		return restApprovalYN;
	}
	public void setRestApprovalYN(String restApprovalYN) {
		this.restApprovalYN = restApprovalYN;
	}
}
