package kr.co.kmac.pms.project.progress.data;

import java.util.List;

import kr.co.kmac.pms.common.util.StringUtil;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectProgress {

	private String projectCode;
	private int workSeq;
	private String workName;
	private int level;
	private int parantWorkSeq;
	private String startDate;
	private String endDate;
	private String realEndDate;
	private String outputName;
	private int workType;
	private int orderSeq;
	private String contentId;
	private String writeDate;
	
	public String getWriteDate(){
		return writeDate;
	}
	
	public void setWriteDate(String writeDate){
		this.writeDate = writeDate;
	}

	private List<ProjectProgressDetail> projectProgressDetailList;

	private List<ProjectProgress> projectProgressList;

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
	 * @return the workSeq
	 */
	public int getWorkSeq() {
		return workSeq;
	}

	/**
	 * @param workSeq the workSeq to set
	 */
	public void setWorkSeq(int workSeq) {
		this.workSeq = workSeq;
	}

	/**
	 * @return the workName
	 */
	public String getWorkName() {
		return workName;
	}

	/**
	 * @param workName the workName to set
	 */
	public void setWorkName(String workName) {
		this.workName = workName;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the parantWorkSeq
	 */
	public int getParantWorkSeq() {
		return parantWorkSeq;
	}

	/**
	 * @param parantWorkSeq the parantWorkSeq to set
	 */
	public void setParantWorkSeq(int parantWorkSeq) {
		this.parantWorkSeq = parantWorkSeq;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return StringUtil.replace(startDate, "-", "");
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return StringUtil.replace(endDate, "-", "");
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the realEndDate
	 */
	public String getRealEndDate() {
		return StringUtil.replace(realEndDate, "-", "");
	}

	/**
	 * @param realEndDate the realEndDate to set
	 */
	public void setRealEndDate(String realEndDate) {
		this.realEndDate = realEndDate;
	}

	/**
	 * @return the outputName
	 */
	public String getOutputName() {
		return outputName;
	}

	/**
	 * @param outputName the outputName to set
	 */
	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	/**
	 * @return the workType
	 */
	public int getWorkType() {
		return workType;
	}

	/**
	 * @param workType the workType to set
	 */
	public void setWorkType(int workType) {
		this.workType = workType;
	}

	/**
	 * @return the orderSeq
	 */
	public int getOrderSeq() {
		return orderSeq;
	}

	/**
	 * @param orderSeq the orderSeq to set
	 */
	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	/**
	 * @return the contentId
	 */
	public String getContentId() {
		return contentId;
	}

	/**
	 * @param contentId the contentId to set
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	/**
	 * @return the projectProgressDetailList
	 */
	public List<ProjectProgressDetail> getProjectProgressDetailList() {
		return projectProgressDetailList;
	}

	/**
	 * @param projectProgressDetailList the projectProgressDetailList to set
	 */
	public void setProjectProgressDetailList(List<ProjectProgressDetail> projectProgressDetailList) {
		this.projectProgressDetailList = projectProgressDetailList;
	}

	/**
	 * @return the projectProgressList
	 */
	public List<ProjectProgress> getProjectProgressList() {
		return projectProgressList;
	}

	/**
	 * @param projectProgressList the projectProgressList to set
	 */
	public void setProjectProgressList(List<ProjectProgress> projectProgressList) {
		this.projectProgressList = projectProgressList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
