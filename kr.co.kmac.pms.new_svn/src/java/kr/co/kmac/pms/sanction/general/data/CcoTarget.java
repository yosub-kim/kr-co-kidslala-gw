/*
 * $Id: CcoTarget.java,v 1.1 2013/03/25 15:19:47 cvs Exp $
 * created by    : yhyim
 * creation-date : 2013. 3. 15.
 * =========================================================
 * Copyright KMAC. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.data;

/**
 * TODO 클래스 설명
 * 
 * @author yhyim
 * @version $Id: CcoTarget.java,v 1.1 2013/03/25 15:19:47 cvs Exp $
 */


public class CcoTarget {
	private String ccoGubun;
	private String companyName;
	private String groupName;
	private String year;
	private String ccoCode;
	
	public String getCcoGubun() {
		return ccoGubun;
	}
	public void setCcoGubun(String ccoGubun) {
		this.ccoGubun = ccoGubun;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCcoCode() {
		return ccoCode;
	}
	public void setCcoCode(String ccoCode) {
		this.ccoCode = ccoCode;
	}
}