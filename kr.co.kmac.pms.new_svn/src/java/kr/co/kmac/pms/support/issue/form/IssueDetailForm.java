package kr.co.kmac.pms.support.issue.form;

import kr.co.kmac.pms.attach.form.AttachForm;

public class IssueDetailForm extends AttachForm {
	
	private String no;
	private String year;
	private String seqno;
	private String sabun;
	private String wtime;
	private String cddpt;
	private String dept_name;
	private String count_t;
	private String gubun;
	private String receive;
	private String content;
	private String gaksa;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getSabun() {
		return sabun;
	}

	public void setSabun(String sabun) {
		this.sabun = sabun;
	}

	public String getWtime() {
		return wtime;
	}

	public void setWtime(String wtime) {
		this.wtime = wtime;
	}

	public String getCddpt() {
		return cddpt;
	}

	public void setCddpt(String cddpt) {
		this.cddpt = cddpt;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getCount_t() {
		return count_t;
	}

	public void setCount_t(String count_t) {
		this.count_t = count_t;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGaksa() {
		return gaksa;
	}

	public void setGaksa(String gaksa) {
		this.gaksa = gaksa;
	}

}
