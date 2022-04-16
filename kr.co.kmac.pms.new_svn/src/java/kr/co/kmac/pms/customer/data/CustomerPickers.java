package kr.co.kmac.pms.customer.data;

public class CustomerPickers {
	private String seq; // 자동증가값
	private String customerIdx;// 고객정보 idx
	private String pickerSsn;// 수집자 ssn
	private String infoDate;// 정보수집일
	private String embbsMethod;// 정보수집방법
	private String bizTripYN;// 자동입력값

	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	/**
	 * @return the customerIdx
	 */
	public String getCustomerIdx() {
		return customerIdx;
	}

	/**
	 * @param customerIdx
	 *            the customerIdx to set
	 */
	public void setCustomerIdx(String customerIdx) {
		this.customerIdx = customerIdx;
	}

	/**
	 * @return the pickerSsn
	 */
	public String getPickerSsn() {
		return pickerSsn;
	}

	/**
	 * @param pickerSsn
	 *            the pickerSsn to set
	 */
	public void setPickerSsn(String pickerSsn) {
		this.pickerSsn = pickerSsn;
	}

	/**
	 * @return the infoDate
	 */
	public String getInfoDate() {
		return infoDate;
	}

	/**
	 * @param infoDate
	 *            the infoDate to set
	 */
	public void setInfoDate(String infoDate) {
		this.infoDate = infoDate;
	}

	/**
	 * @return the embbsMethod
	 */
	public String getEmbbsMethod() {
		return embbsMethod;
	}

	/**
	 * @param embbsMethod
	 *            the embbsMethod to set
	 */
	public void setEmbbsMethod(String embbsMethod) {
		this.embbsMethod = embbsMethod;
	}

	/**
	 * @return the bizTripYN
	 */
	public String getBizTripYN() {
		return bizTripYN;
	}

	/**
	 * @param bizTripYN
	 *            the bizTripYN to set
	 */
	public void setBizTripYN(String bizTripYN) {
		this.bizTripYN = bizTripYN;
	}

}
