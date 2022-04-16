package kr.co.kmac.pms.sanction.projectchange.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import kr.co.kmac.pms.sanction.general.form.SanctionForm;
import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChangeArray;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChangeArray;

/**
 * @struts:form name="memberChangeSanctionAction"
 */
public class MemberChangeSanctionForm extends SanctionForm implements AddMemberChangeArray, RunningMemberChangeArray {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2118643951204406355L;

	private String projectCode;
	private int memberChangeSeq;
	private String budgetEntNo;

	private String[] runningMemberSsn;
	private String[] runningMemberName;
	private String[] runningMemberRole;
	private String[] runningMemberCost;
	private String[] runningMemberTrainingYn;
	private String[] runningMemberContributionCost;
	private String[] runningMemberTeachingDay;
	private String[] runningMemberPosition;
	private String[] runningMemberResRate;

	private String[] addMemberSsn;
	private String[] addMemberName;
	private String[] addMemberRole;
	private String[] addMemberCost;
	private String[] addMemberTrainingYn;
	private String[] addMemberContributionCost;
	private String[] addMemberPosition;
	private String[] addMemberResRate;
	private String[] addMemberStartDate;
	private String[] addMemberEndDate;
	

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
	
	public String getBudgetEntNo() {
		return budgetEntNo;
	}

	public void setBudgetEntNo(String budgetEntNo) {
		this.budgetEntNo = budgetEntNo;
	}

	public String[] getRunningMemberSsn() {
		return runningMemberSsn;
	}

	public void setRunningMemberSsn(String[] runningMemberSsn) {
		this.runningMemberSsn = runningMemberSsn;
	}

	public String[] getRunningMemberName() {
		return runningMemberName;
	}

	public void setRunningMemberName(String[] runningMemberName) {
		this.runningMemberName = runningMemberName;
	}

	public String[] getRunningMemberRole() {
		return runningMemberRole;
	}

	public void setRunningMemberRole(String[] runningMemberRole) {
		this.runningMemberRole = runningMemberRole;
	}

	public String[] getRunningMemberCost() {
		return runningMemberCost;
	}

	public void setRunningMemberCost(String[] runningMemberCost) {
		this.runningMemberCost = runningMemberCost;
	}

	public String[] getRunningMemberTrainingYn() {
		return runningMemberTrainingYn;
	}

	public void setRunningMemberTrainingYn(String[] runningMemberTrainingYn) {
		this.runningMemberTrainingYn = runningMemberTrainingYn;
	}

	public String[] getRunningMemberContributionCost() {
		return runningMemberContributionCost;
	}

	public void setRunningMemberContributionCost(String[] runningMemberContributionCost) {
		this.runningMemberContributionCost = runningMemberContributionCost;
	}

	public String[] getRunningMemberTeachingDay() {
		return runningMemberTeachingDay;
	}

	public void setRunningMemberTeachingDay(String[] runningMemberTeachingDay) {
		this.runningMemberTeachingDay = runningMemberTeachingDay;
	}

	public String[] getRunningMemberPosition() {
		return runningMemberPosition;
	}

	public void setRunningMemberPosition(String[] runningMemberPosition) {
		this.runningMemberPosition = runningMemberPosition;
	}

	public String[] getRunningMemberResRate() {
		return runningMemberResRate;
	}

	public void setRunningMemberResRate(String[] runningMemberResRate) {
		this.runningMemberResRate = runningMemberResRate;
	}

	public String[] getAddMemberSsn() {
		return addMemberSsn;
	}

	public void setAddMemberSsn(String[] addMemberSsn) {
		this.addMemberSsn = addMemberSsn;
	}

	public String[] getAddMemberName() {
		return addMemberName;
	}

	public void setAddMemberName(String[] addMemberName) {
		this.addMemberName = addMemberName;
	}

	public String[] getAddMemberRole() {
		return addMemberRole;
	}

	public void setAddMemberRole(String[] addMemberRole) {
		this.addMemberRole = addMemberRole;
	}

	public String[] getAddMemberCost() {
		return addMemberCost;
	}

	public void setAddMemberCost(String[] addMemberCost) {
		this.addMemberCost = addMemberCost;
	}

	public String[] getAddMemberTrainingYn() {
		return addMemberTrainingYn;
	}

	public void setAddMemberTrainingYn(String[] addMemberTrainingYn) {
		this.addMemberTrainingYn = addMemberTrainingYn;
	}

	public String[] getAddMemberContributionCost() {
		return addMemberContributionCost;
	}

	public void setAddMemberContributionCost(String[] addMemberContributionCost) {
		this.addMemberContributionCost = addMemberContributionCost;
	}

	public String[] getAddMemberPosition() {
		return addMemberPosition;
	}

	public void setAddMemberPosition(String[] addMemberPosition) {
		this.addMemberPosition = addMemberPosition;
	}

	public String[] getAddMemberResRate() {
		return addMemberResRate;
	}

	public void setAddMemberResRate(String[] addMemberResRate) {
		this.addMemberResRate = addMemberResRate;
	}

	public String[] getAddMemberStartDate() {
		return addMemberStartDate;
	}

	public void setAddMemberStartDate(String[] addMemberStartDate) {
		this.addMemberStartDate = addMemberStartDate;
	}

	public String[] getAddMemberEndDate() {
		return addMemberEndDate;
	}

	public void setAddMemberEndDate(String[] addMemberEndDate) {
		this.addMemberEndDate = addMemberEndDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
