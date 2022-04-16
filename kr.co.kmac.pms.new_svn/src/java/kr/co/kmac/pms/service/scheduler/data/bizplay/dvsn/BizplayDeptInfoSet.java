package kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
SVC_CD				서비스코드
SECR_KEY				인증키
PTL_STS				포탈상태
REQ_DATA				
	REC			
		USE_INTT_ID		이용기관ID
		BSUN_ID		사업장ID
		DVSN_CD		부서코드
		DVSN_NM		부서명
		HGRN_DVSN_CD		상위부서코드
		OTPT_SQNC		출력순서
		ACVT_YN		활동여부
 */
public class BizplayDeptInfoSet { 
	
	private String SVC_CD = "dvsninfo_erp_cu003";
	private String SECR_KEY = "def79897-b672-db4d-3314-90be10eddf7e";
	private String PTL_STS ="C";
	private REQ_DATA REQ_DATA;

	public String getSVC_CD() {
		return SVC_CD;
	}

	public void setSVC_CD(String sVC_CD) {
		SVC_CD = sVC_CD;
	}

	public String getSECR_KEY() {
		return SECR_KEY;
	}

	public void setSECR_KEY(String sECR_KEY) {
		SECR_KEY = sECR_KEY;
	}

	public String getPTL_STS() {
		return PTL_STS;
	}

	public void setPTL_STS(String pTL_STS) {
		PTL_STS = pTL_STS;
	}

	public REQ_DATA getREQ_DATA() {
		return REQ_DATA;
	}

	public void setREQ_DATA(REQ_DATA rEQ_DATA) {
		REQ_DATA = rEQ_DATA;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
