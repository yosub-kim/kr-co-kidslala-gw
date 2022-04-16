package kr.co.kmac.pms.sanction.projectchange.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AddMemberChange {
	private String projectCode;
	private int memberChangeSeq;

	private String addMemberSsn;
	private String addMemberName;
	private String addMemberRole;
	private String addMemberCost;
	private String addMemberTrainingYn;
	private String addMemberContributionCost;
	private String addMemberResRate;
	private String addMemberPosition;

	private String addMemberStartDate;
	private String addMemberEndDate;

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

	public String getAddMemberSsn() {
		return addMemberSsn;
	}

	public void setAddMemberSsn(String addMemberSsn) {
		this.addMemberSsn = addMemberSsn;
	}

	public String getAddMemberName() {
		return addMemberName;
	}

	public void setAddMemberName(String addMemberName) {
		this.addMemberName = addMemberName;
	}

	public String getAddMemberRole() {
		return addMemberRole;
	}

	public void setAddMemberRole(String addMemberRole) {
		this.addMemberRole = addMemberRole;
	}

	public String getAddMemberCost() {
		return addMemberCost;
	}

	public void setAddMemberCost(String addMemberCost) {
		this.addMemberCost = addMemberCost;
	}

	public String getAddMemberTrainingYn() {
		return addMemberTrainingYn;
	}

	public void setAddMemberTrainingYn(String addMemberTrainingYn) {
		this.addMemberTrainingYn = addMemberTrainingYn;
	}

	public String getAddMemberContributionCost() {
		return addMemberContributionCost;
	}

	public void setAddMemberContributionCost(String addMemberContributionCost) {
		this.addMemberContributionCost = addMemberContributionCost;
	}

	public String getAddMemberResRate() {
		return addMemberResRate;
	}

	public void setAddMemberResRate(String addMemberResRate) {
		this.addMemberResRate = addMemberResRate;
	}

	public String getAddMemberPosition() {
		return addMemberPosition;
	}

	public void setAddMemberPosition(String addMemberPosition) {
		this.addMemberPosition = addMemberPosition;
	}

	public String getAddMemberStartDate() {
		return addMemberStartDate;
	}

	public void setAddMemberStartDate(String addMemberStartDate) {
		this.addMemberStartDate = addMemberStartDate;
	}

	public String getAddMemberEndDate() {
		return addMemberEndDate;
	}

	public void setAddMemberEndDate(String addMemberEndDate) {
		this.addMemberEndDate = addMemberEndDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
