/*
 * $Id: ExpertPoolProjectHst.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.data;

/**
 * TODO Ŭ���� ����
 * @author jiwoongLee
 * @version $Id: ExpertPoolProjectHst.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */
public class ExpertPoolProjectHst {

	private String ssn;
	private String seqNo;
	private String projectCode;

	// �Ʒ��� ������ project ���̺�� �����ؼ������;� �� ����.
	private String projectName;
	private String running; // ����ð�
	private String field;// �о�
	private String clientCompany;// ����ȸ��
	private String output;// �����
	private String remark;// ���
	private String estimateLevel;// �򰡵��
	// �۾���...
	/**
	 * @return Returns the clientCompany.
	 */
	public String getClientCompany() {
		return this.clientCompany;
	}
	/**
	 * @param clientCompany The clientCompany to set.
	 */
	public void setClientCompany(String clientCompany) {
		this.clientCompany = clientCompany;
	}
	/**
	 * @return Returns the estimateLevel.
	 */
	public String getEstimateLevel() {
		return this.estimateLevel;
	}
	/**
	 * @param estimateLevel The estimateLevel to set.
	 */
	public void setEstimateLevel(String estimateLevel) {
		this.estimateLevel = estimateLevel;
	}
	/**
	 * @return Returns the field.
	 */
	public String getField() {
		return this.field;
	}
	/**
	 * @param field The field to set.
	 */
	public void setField(String field) {
		this.field = field;
	}
	/**
	 * @return Returns the output.
	 */
	public String getOutput() {
		return this.output;
	}
	/**
	 * @param output The output to set.
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	/**
	 * @return Returns the projectCode.
	 */
	public String getProjectCode() {
		return this.projectCode;
	}
	/**
	 * @param projectCode The projectCode to set.
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/**
	 * @return Returns the projectName.
	 */
	public String getProjectName() {
		return this.projectName;
	}
	/**
	 * @param projectName The projectName to set.
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return this.remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the running.
	 */
	public String getRunning() {
		return this.running;
	}
	/**
	 * @param running The running to set.
	 */
	public void setRunning(String running) {
		this.running = running;
	}
	/**
	 * @return Returns the seqNo.
	 */
	public String getSeqNo() {
		return this.seqNo;
	}
	/**
	 * @param seqNo The seqNo to set.
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	/**
	 * @return Returns the ssn.
	 */
	public String getSsn() {
		return this.ssn;
	}
	/**
	 * @param ssn The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

}
