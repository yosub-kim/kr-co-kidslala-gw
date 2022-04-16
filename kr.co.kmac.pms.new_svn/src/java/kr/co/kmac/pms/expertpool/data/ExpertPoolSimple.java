/*
 * $Id: ExpertPoolSimple.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 2.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.data;

/**
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: ExpertPoolSimple.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */
public class ExpertPoolSimple {

	private String name;
	private String jobClass;
	private String expertFieldByFunc;
	private String expertFieldByBiz;
	private String latestProject;
	private String currentPrjCnt;
	private String level;
	private String lastModifiedDate;

	/**
	 * @return Returns the currentPrjCnt.
	 */
	public String getCurrentPrjCnt() {
		return this.currentPrjCnt;
	}
	/**
	 * @param currentPrjCnt The currentPrjCnt to set.
	 */
	public void setCurrentPrjCnt(String currentPrjCnt) {
		this.currentPrjCnt = currentPrjCnt;
	}
	/**
	 * @return Returns the expertFieldByBiz.
	 */
	public String getExpertFieldByBiz() {
		return this.expertFieldByBiz;
	}
	/**
	 * @param expertFieldByBiz The expertFieldByBiz to set.
	 */
	public void setExpertFieldByBiz(String expertFieldByBiz) {
		this.expertFieldByBiz = expertFieldByBiz;
	}
	/**
	 * @return Returns the expertFieldByFunc.
	 */
	public String getExpertFieldByFunc() {
		return this.expertFieldByFunc;
	}
	/**
	 * @param expertFieldByFunc The expertFieldByFunc to set.
	 */
	public void setExpertFieldByFunc(String expertFieldByFunc) {
		this.expertFieldByFunc = expertFieldByFunc;
	}
	/**
	 * @return Returns the jobClass.
	 */
	public String getJobClass() {
		return this.jobClass;
	}
	/**
	 * @param jobClass The jobClass to set.
	 */
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	/**
	 * @return Returns the lastModifiedDate.
	 */
	public String getLastModifiedDate() {
		return this.lastModifiedDate;
	}
	/**
	 * @param lastModifiedDate The lastModifiedDate to set.
	 */
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	/**
	 * @return Returns the latestProject.
	 */
	public String getLatestProject() {
		return this.latestProject;
	}
	/**
	 * @param latestProject The latestProject to set.
	 */
	public void setLatestProject(String latestProject) {
		this.latestProject = latestProject;
	}
	/**
	 * @return Returns the level.
	 */
	public String getLevel() {
		return this.level;
	}
	/**
	 * @param level The level to set.
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
