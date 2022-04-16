/*
 * $Id: ExpenseRealTimeResultDetailEtc.java,v 1.1 2011/10/25 13:49:37 cvs Exp $
 * created by    : yhyim
 * creation-date : 2011. 10. 25.
 * =========================================================
 * Copyright (c) 2011 KMAC All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.data;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpenseRealTimeResultDetailEtc.java,v 1.1 2011/10/25 13:49:37 cvs Exp $
 */
public class ExpenseRealTimeResultDetailEtc extends ExpenseRealTimeResult {
	
	private String jobClass;
	private String name;
	private String totalAmount;
	private String seq;
	private String amount;
	private String approvalYN;
	private String grandTotalAmount;
	
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getApprovalYN() {
		return approvalYN;
	}
	public void setApprovalYN(String approvalYN) {
		this.approvalYN = approvalYN;
	}
	public String getGrandTotalAmount() {
		return grandTotalAmount;
	}
	public void setGrandTotalAmount(String grandTotalAmount) {
		this.grandTotalAmount = grandTotalAmount;
	}
}