package kr.co.kmac.pms.project.voc.data;

import kr.co.kmac.pms.common.util.StringUtil;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 17.
 * @description :
 */
public class ProjectVocInfoData {
	private String rownum;
	private String projectCode;
	private String requestDate;
	private String sendDate;
	private String receiveDate;
	private String responseDate;
	private String vocType;
	private String receiveName;
	private String receiveEmail;

	/**
	 * @return Returns the rownum.
	 */
	public String getRownum() {
		return rownum;
	}

	/**
	 * @param rownum The rownum to set.
	 */
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	/**
	 * @return Returns the projectCode.
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode The projectCode to set.
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return Returns the type.
	 */
	public String getVocType() {
		return vocType;
	}

	/**
	 * @param type The type to set.
	 */
	public void setVocType(String type) {
		this.vocType = type;
	}

	/**
	 * @return Returns the vocDate.
	 */
	public String getRequestDate() {
		return StringUtil.replace(this.requestDate, "-", "");
	}

	/**
	 * @param vocDate The vocDate to set.
	 */
	public void setRequestDate(String vocDate) {
		this.requestDate = vocDate;
	}

	/**
	 * @return Returns the receiveDate.
	 */
	public String getReceiveDate() {
		return receiveDate;
	}

	/**
	 * @param receiveDate The receiveDate to set.
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * @return Returns the responseDate.
	 */
	public String getResponseDate() {
		return responseDate;
	}

	/**
	 * @param responseDate The responseDate to set.
	 */
	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	/**
	 * @return Returns the receiveEmail.
	 */
	public String getReceiveEmail() {
		return receiveEmail;
	}

	/**
	 * @param receiveEmail The receiveEmail to set.
	 */
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}

	/**
	 * @return Returns the receiveName.
	 */
	public String getReceiveName() {
		return receiveName;
	}

	/**
	 * @param receiveName The receiveName to set.
	 */
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	/**
	 * @return the sendDate
	 */
	public String getSendDate() {
		return sendDate;
	}

	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
}
