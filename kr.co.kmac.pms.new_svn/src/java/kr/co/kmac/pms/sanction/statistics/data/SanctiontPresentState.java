package kr.co.kmac.pms.sanction.statistics.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SanctiontPresentState {

	private String seq;
	private String approvalType;
	private String approvalTypeName;
	private String projectName;
	private String projectCode;
	private String divCode;
	private String divName;
	private String registerDept;
	private String registerDeptName;
	private String registerSsn;
	private String registerName;
	private String registerDate;
	private String state;
	private String present;
	private String presentName;
	private String workType;
	private String workTypeUrl;
	private String useMobile;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getApprovalTypeName() {
		return approvalTypeName;
	}

	public void setApprovalTypeName(String approvalTypeName) {
		this.approvalTypeName = approvalTypeName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getDivCode() {
		return divCode;
	}

	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}

	public String getDivName() {
		return divName;
	}

	public void setDivName(String divName) {
		this.divName = divName;
	}

	public String getRegisterDept() {
		return registerDept;
	}

	public void setRegisterDept(String registerDept) {
		this.registerDept = registerDept;
	}

	public String getRegisterDeptName() {
		return registerDeptName;
	}

	public void setRegisterDeptName(String registerDeptName) {
		this.registerDeptName = registerDeptName;
	}

	public String getRegisterSsn() {
		return registerSsn;
	}

	public void setRegisterSsn(String registerSsn) {
		this.registerSsn = registerSsn;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPresent() {
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
	}

	public String getPresentName() {
		return presentName;
	}

	public void setPresentName(String presentName) {
		this.presentName = presentName;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWorkTypeUrl() {
		return workTypeUrl;
	}

	public void setWorkTypeUrl(String workTypeUrl) {
		this.workTypeUrl = workTypeUrl;
	}

	public String getUseMobile() {
		return useMobile;
	}

	public void setUseMobile(String useMobile) {
		this.useMobile = useMobile;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}