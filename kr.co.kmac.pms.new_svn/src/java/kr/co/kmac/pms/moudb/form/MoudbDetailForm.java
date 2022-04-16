package kr.co.kmac.pms.moudb.form;

import kr.co.kmac.pms.attach.form.AttachForm;

/**
 * @struts:form name="moudbActionForm"
 */

public class MoudbDetailForm extends AttachForm {
	
	private String idx;
	private String writer;
	private String writer_id;
	private String regdate;
	private String updatedate;
	private String com_name;
	private String com_boss;
	private String country;
	private String com_addr;
	private String homepage;
	private String com_tel;
	private String com_special_quality;
	private String com_expert;
	private String com_cooperation;
	private String relation_level;
	private String join_project_result;
	
	private String[] contactName;
	private String[] contactGrade;
	private String[] contactTel;
	private String[] contactMobile;
	private String[] contactEmail;
	private String[] contactJob;
	
	private String mou_idx;

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWriter_id() {
		return writer_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public String getCom_name() {
		return com_name;
	}

	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}

	public String getCom_boss() {
		return com_boss;
	}

	public void setCom_boss(String com_boss) {
		this.com_boss = com_boss;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCom_addr() {
		return com_addr;
	}

	public void setCom_addr(String com_addr) {
		this.com_addr = com_addr;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getCom_tel() {
		return com_tel;
	}

	public void setCom_tel(String com_tel) {
		this.com_tel = com_tel;
	}

	public String getCom_special_quality() {
		return com_special_quality;
	}

	public void setCom_special_quality(String com_special_quality) {
		this.com_special_quality = com_special_quality;
	}

	public String getCom_expert() {
		return com_expert;
	}

	public void setCom_expert(String com_expert) {
		this.com_expert = com_expert;
	}

	public String getCom_cooperation() {
		return com_cooperation;
	}

	public void setCom_cooperation(String com_cooperation) {
		this.com_cooperation = com_cooperation;
	}

	public String getRelation_level() {
		return relation_level;
	}

	public void setRelation_level(String relation_level) {
		this.relation_level = relation_level;
	}

	public String getJoin_project_result() {
		return join_project_result;
	}

	public void setJoin_project_result(String join_project_result) {
		this.join_project_result = join_project_result;
	}

	public String[] getContactName() {
		return contactName;
	}

	public void setContactName(String[] contactName) {
		this.contactName = contactName;
	}

	public String[] getContactGrade() {
		return contactGrade;
	}

	public void setContactGrade(String[] contactGrade) {
		this.contactGrade = contactGrade;
	}

	public String[] getContactTel() {
		return contactTel;
	}

	public void setContactTel(String[] contactTel) {
		this.contactTel = contactTel;
	}

	public String[] getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String[] contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String[] getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String[] contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String[] getContactJob() {
		return contactJob;
	}

	public void setContactJob(String[] contactJob) {
		this.contactJob = contactJob;
	}

	public String getMou_idx() {
		return mou_idx;
	}

	public void setMou_idx(String mou_idx) {
		this.mou_idx = mou_idx;
	}
	
}
