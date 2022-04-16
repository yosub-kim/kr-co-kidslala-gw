/*
 * $Id: ProjectDmSearch.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 4. 13.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.dmlist.data;

/**
 * TODO 클래스 설명
 * @author 오영택
 * @version $Id: ProjectDmSearch.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 */
public class ProjectDmSearch {

	private int idx;
	private String p_name;
	private String p_company;
	private String p_part;
	private String p_tel1;
	private String p_email;
	private String writer;
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String pName) {
		p_name = pName;
	}
	public String getP_company() {
		return p_company;
	}
	public void setP_company(String pCompany) {
		p_company = pCompany;
	}
	public String getP_part() {
		return p_part;
	}
	public void setP_part(String pPart) {
		p_part = pPart;
	}
	public String getP_tel1() {
		return p_tel1;
	}
	public void setP_tel1(String pTel1) {
		p_tel1 = pTel1;
	}
	public String getP_email() {
		return p_email;
	}
	public void setP_email(String pEmail) {
		p_email = pEmail;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
}