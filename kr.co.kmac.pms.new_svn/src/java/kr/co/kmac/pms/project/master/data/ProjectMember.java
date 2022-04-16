package kr.co.kmac.pms.project.master.data;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.project.master.form.ProjectMasterInfoForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectMember {
	private String projectCode;
	private String ssn;
	private String name;
	private String role;
	private String cost;
	private String trainingYn;
	private String contributionCost;
	private String teachingDay;
	private String position;
	private String resRate;

	private String jobClass;
	private String minAmt;
	private String maxAmt;
	private String minEdu;
	private String maxEdu;
	private String minMMAmt;
	private String maxMMAmt;

	private String startDate;
	private String endDate;
	
	private String year;
	private String month;
	private String cnt;
	
	public String getCnt(){
		return cnt;
	}
	
	public void setCnt(String cnt){
		this.cnt = cnt;
	}
	
	public String getYear(){
		return year;
	}
	
	public void setYear(String year){
		this.year = year;
	}
	
	public String getMonth(){
		return month;
	}
	
	public void setMonth(String month){
		this.month = month;
	}
	
	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(String minAmt) {
		this.minAmt = minAmt;
	}

	public String getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(String maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getMinEdu() {
		return minEdu;
	}

	public void setMinEdu(String minEdu) {
		this.minEdu = minEdu;
	}

	public String getMaxEdu() {
		return maxEdu;
	}

	public void setMaxEdu(String maxEdu) {
		this.maxEdu = maxEdu;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the cost
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}

	/**
	 * @return the trainingYn
	 */
	public String getTrainingYn() {
		return trainingYn;
	}

	/**
	 * @param trainingYn the trainingYn to set
	 */
	public void setTrainingYn(String trainingYn) {
		this.trainingYn = trainingYn;
	}

	/**
	 * @return the contributionCost
	 */
	public String getContributionCost() {
		return contributionCost;
	}

	/**
	 * @param contributionCost the contributionCost to set
	 */
	public void setContributionCost(String contributionCost) {
		this.contributionCost = contributionCost;
	}

	/**
	 * @return the teachingDay
	 */
	public String getTeachingDay() {
		return teachingDay;
	}

	/**
	 * @param teachingDay the teachingDay to set
	 */
	public void setTeachingDay(String teachingDay) {
		this.teachingDay = teachingDay;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the resRate
	 */
	public String getResRate() {
		return resRate;
	}

	/**
	 * @param resRate the resRate to set
	 */
	public void setResRate(String resRate) {
		this.resRate = resRate;
	}
	
	public String getMinMMAmt() {
		return minMMAmt;
	}

	public void setMinMMAmt(String minMMAmt) {
		this.minMMAmt = minMMAmt;
	}

	public String getMaxMMAmt() {
		return maxMMAmt;
	}

	public void setMaxMMAmt(String maxMMAmt) {
		this.maxMMAmt = maxMMAmt;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public static List<ProjectMember> valueOf(ProjectMasterInfoForm form) {
		List<ProjectMember> list = new ArrayList<ProjectMember>();
		ProjectMember projectMember = null;
		if (form.getMemberSsn() != null) {
			for (int i = 0; i < form.getMemberSsn().length; i++) {
				projectMember = new ProjectMember();
				projectMember.setProjectCode(form.getProjectCode());
				projectMember.setSsn(form.getMemberSsn()[i]);
				projectMember.setName(form.getMemberName()[i]);
				projectMember.setRole(form.getMemberRole()[i]);
				projectMember.setPosition(form.getMemberPosition()[i]);
				projectMember.setResRate(form.getMemberResRate()[i]);
				projectMember.setCost(form.getMemberCost()[i]);
				projectMember.setTrainingYn(form.getMemberTrainingYn()[i]);
				//projectMember.setContributionCost(form.getMemberContributionCost()[i]);`
				//projectMember.setStartDate(form.getMemberStartDate()[i]);
				//projectMember.setEndDate(form.getMemberEndDate()[i]);
				list.add(projectMember);
			}
		}
		return list;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
