package kr.co.kmac.pms.sanction.projectchange.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RunningMemberChange {
	private String projectCode;
	private int memberChangeSeq;

	private String runningMemberSsn;
	private String runningMemberName;
	private String runningMemberRole;
	private String runningMemberCost;
	private String runningMemberTrainingYn;
	private String runningMemberContributionCost;
	private String runningMemberTeachingDay;
	private String runningMemberPosition;
	private String runningMemberResRate;

	private String runningMemberStartDate;
	private String runningMemberEndDate;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public int getMemberChangeSeq() {
		return memberChangeSeq;
	}

	public void setMemberChangeSeq(int memberChangeSeq) {
		this.memberChangeSeq = memberChangeSeq;
	}

	public String getRunningMemberSsn() {
		return runningMemberSsn;
	}

	public void setRunningMemberSsn(String runningMemberSsn) {
		this.runningMemberSsn = runningMemberSsn;
	}

	public String getRunningMemberName() {
		return runningMemberName;
	}

	public void setRunningMemberName(String runningMemberName) {
		this.runningMemberName = runningMemberName;
	}

	public String getRunningMemberRole() {
		return runningMemberRole;
	}

	public void setRunningMemberRole(String runningMemberRole) {
		this.runningMemberRole = runningMemberRole;
	}

	public String getRunningMemberCost() {
		return runningMemberCost;
	}

	public void setRunningMemberCost(String runningMemberCost) {
		this.runningMemberCost = runningMemberCost;
	}

	public String getRunningMemberTrainingYn() {
		return runningMemberTrainingYn;
	}

	public void setRunningMemberTrainingYn(String runningMemberTrainingYn) {
		this.runningMemberTrainingYn = runningMemberTrainingYn;
	}

	public String getRunningMemberContributionCost() {
		return runningMemberContributionCost;
	}

	public void setRunningMemberContributionCost(String runningMemberContributionCost) {
		this.runningMemberContributionCost = runningMemberContributionCost;
	}

	public String getRunningMemberTeachingDay() {
		return runningMemberTeachingDay;
	}

	public void setRunningMemberTeachingDay(String runningMemberTeachingDay) {
		this.runningMemberTeachingDay = runningMemberTeachingDay;
	}

	public String getRunningMemberPosition() {
		return runningMemberPosition;
	}

	public void setRunningMemberPosition(String runningMemberPosition) {
		this.runningMemberPosition = runningMemberPosition;
	}

	public String getRunningMemberResRate() {
		return runningMemberResRate;
	}

	public void setRunningMemberResRate(String runningMemberResRate) {
		this.runningMemberResRate = runningMemberResRate;
	}

	public String getRunningMemberStartDate() {
		return runningMemberStartDate;
	}

	public void setRunningMemberStartDate(String runningMemberStartDate) {
		this.runningMemberStartDate = runningMemberStartDate;
	}

	public String getRunningMemberEndDate() {
		return runningMemberEndDate;
	}

	public void setRunningMemberEndDate(String runningMemberEndDate) {
		this.runningMemberEndDate = runningMemberEndDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
