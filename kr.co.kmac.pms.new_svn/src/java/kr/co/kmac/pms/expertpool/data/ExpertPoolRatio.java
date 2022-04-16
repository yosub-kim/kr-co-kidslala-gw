/*
 * $Id: ExpertPoolRatio.java,v 1.1 2010/12/26 14:34:27 cvs Exp $ created by :
 * jiwoongLee creation-date : 2006. 10. 13.
 * ========================================================= Copyright (c) 2005
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.data;

/**
 * Àü¹®°¡ Ç®°ü·Ã ¸ðµ¨ °´Ã¼
 * 
 * @author jiwoongLee
 * @version $Id: ExpertPoolRatio.java,v 1.1 2010/12/26 14:34:27 cvs Exp $
 */
public class ExpertPoolRatio {

	private String ssn;
	private String name;
	private String dept;
	private String deptName;
	private String consultingFunction;
	private String consultingMajor;

	private String runningRatio;

	public String getConsultingFunction() {
		return consultingFunction;
	}

	public void setConsultingFunction(String consultingFunction) {
		this.consultingFunction = consultingFunction;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getConsultingMajor() {
		return consultingMajor;
	}

	public void setConsultingMajor(String consultingMajor) {
		this.consultingMajor = consultingMajor;
	}

	public String getRunningRatio() {
		return runningRatio;
	}

	public void setRunningRatio(String runningRatio) {
		this.runningRatio = runningRatio;
	}

}