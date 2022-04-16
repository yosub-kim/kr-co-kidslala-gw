package kr.co.kmac.pms.support.certificate.data;

import java.util.Calendar;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.common.util.DateUtil;

public class CertificateData extends AttachForm {

	private static final long serialVersionUID = -2995777027938211016L;
	private String num;
	private String writerId;
	private String userId;
	private String ManagerId;
	private String writerDept;
	private String userDept;
	private String ctype;
	private String gubun;
	private String use_kind;
	private String use_place;
	private String regdate;
	private String approvedate;
	private String manager_approvedate;
	private String print_st;
	private String print_et;
	private String print_yn;
	private String remark;
	private String status;
	private String start_date;
	private String writer;
	private String username;
	private String certify_num;
	private String newIcon;
	
	public String getNewIcon() {
		return newIcon;
	}

	public void setNewIcon(String newIcon) {
		this.newIcon = newIcon;
	}

	public String getCertify_num() {
		return certify_num;
	}

	public void setCertify_num(String certify_num) {
		this.certify_num = certify_num;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getManagerId() {
		return ManagerId;
	}

	public void setManagerId(String managerId) {
		ManagerId = managerId;
	}

	public String getWriterDept() {
		return writerDept;
	}

	public void setWriterDept(String writerDept) {
		this.writerDept = writerDept;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getUse_kind() {
		return use_kind;
	}

	public void setUse_kind(String use_kind) {
		this.use_kind = use_kind;
	}

	public String getUse_place() {
		return use_place;
	}

	public void setUse_place(String use_place) {
		this.use_place = use_place;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(String approvedate) {
		this.approvedate = approvedate;
	}

	public String getManager_approvedate() {
		return manager_approvedate;
	}

	public void setManager_approvedate(String manager_approvedate) {
		this.manager_approvedate = manager_approvedate;
	}

	public String getPrint_st() {
		return print_st;
	}

	public void setPrint_st(String print_st) {
		this.print_st = print_st;
	}

	public String getPrint_et() {
		return print_et;
	}

	public void setPrint_et(String print_et) {
		this.print_et = print_et;
	}

	public String getPrint_yn() {
		return print_yn;
	}

	public void setPrint_yn(String print_yn) {
		this.print_yn = print_yn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

}
