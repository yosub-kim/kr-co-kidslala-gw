package kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * SVC_CD �����ڵ� SECR_KEY ����Ű PTL_STS ��Ż���� REQ_DATA REC USE_INTT_ID �̿���ID BSUN_ID �����ID DVSN_CD �μ��ڵ� DVSN_NM �μ��� HGRN_DVSN_CD �����μ��ڵ� OTPT_SQNC ��¼��� ACVT_YN Ȱ������
 */
public class REC { 

	private String USE_INTT_ID = "UTLZ_2003031465790";
	private String BSUN_ID;
	private String DVSN_CD;
	private String DVSN_NM;
	private String HGRN_DVSN_CD;
	private String OTPT_SQNC;
	private String ACVT_YN;

	public String getUSE_INTT_ID() {
		return USE_INTT_ID;
	}

	public void setUSE_INTT_ID(String uSE_INTT_ID) {
		USE_INTT_ID = uSE_INTT_ID;
	}

	public String getBSUN_ID() {
		return BSUN_ID;
	}

	public void setBSUN_ID(String bSUN_ID) {
		BSUN_ID = bSUN_ID;
	}

	public String getDVSN_CD() {
		return DVSN_CD;
	}

	public void setDVSN_CD(String dVSN_CD) {
		DVSN_CD = dVSN_CD;
	}

	public String getDVSN_NM() {
		return DVSN_NM;
	}

	public void setDVSN_NM(String dVSN_NM) {
		DVSN_NM = dVSN_NM;
	}

	public String getHGRN_DVSN_CD() {
		return HGRN_DVSN_CD;
	}

	public void setHGRN_DVSN_CD(String hGRN_DVSN_CD) {
		HGRN_DVSN_CD = hGRN_DVSN_CD;
	}

	public String getOTPT_SQNC() {
		return OTPT_SQNC;
	}

	public void setOTPT_SQNC(String oTPT_SQNC) {
		OTPT_SQNC = oTPT_SQNC;
	}

	public String getACVT_YN() {
		return ACVT_YN;
	}

	public void setACVT_YN(String aCVT_YN) {
		ACVT_YN = aCVT_YN;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}