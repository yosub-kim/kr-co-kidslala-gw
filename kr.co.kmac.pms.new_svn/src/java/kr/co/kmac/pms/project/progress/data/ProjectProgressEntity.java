package kr.co.kmac.pms.project.progress.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectProgressEntity {

	private String projectCode;
	private String workSeq;
	private String workName;
	private int level;
	private int parentWorkSeq;

	private String startDate;
	private String endDate;
	private String realEndDate;
	private int orderSeq;
	private String contentId;
	private List<ProjectProgressEntity> projectProgressEntityList = new ArrayList<ProjectProgressEntity>();
	private String writeDate;
	private String projectTypeCode;
	
	public String getProjectTypeCode(){
		return projectTypeCode;
	}
	
	public void setProjectTypeCode(String projectTypeCode){
		this.projectTypeCode = projectTypeCode;
	}
	
	public String getWriteDate(){
		return writeDate;
	}
	
	public void setWriteDate(String writeDate){
		this.writeDate = writeDate;
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
	 * @return the workSeq
	 */
	public String getWorkSeq() {
		return workSeq;
	}

	/**
	 * @param workSeq the workSeq to set
	 */
	public void setWorkSeq(String workSeq) {
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
	 * @return the parentWorkSeq
	 */
	public int getParentWorkSeq() {
		if (this.parentWorkSeq > 0)
			return parentWorkSeq;
		else
			return -1;
	}

	/**
	 * @param parentWorkSeq the parentWorkSeq to set
	 */
	public void setParentWorkSeq(int parentWorkSeq) {
		this.parentWorkSeq = parentWorkSeq;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
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
		return endDate;
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
		return realEndDate;
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
	 * @return the projectProgressEntityList
	 */
	public List<ProjectProgressEntity> getProjectProgressEntityList() {
		return projectProgressEntityList;
	}

	/**
	 * @param projectProgressEntityList the projectProgressEntityList to set
	 */
	public void setProjectProgressEntityList(List<ProjectProgressEntity> projectProgressEntityList) {
		this.projectProgressEntityList = projectProgressEntityList;
	}

	/**
	 * @param realEndDate the realEndDate to set
	 */
	public void setRealEndDate(String realEndDate) {
		this.realEndDate = realEndDate;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
