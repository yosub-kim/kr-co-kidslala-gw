package kr.co.kmac.pms.service.scheduler.data.bizplay.empl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
SVC_CD				서비스코드	C		필수	"emplinfo_erp_cu002"
SECR_KEY			인증키	C		필수	"def79897-b672-db4d-3314-90be10eddf7e"
PTL_STS				포탈상태	C		필수	"C"
PTL_ID				포탈ID	C		필수	"PTL_3"
CHNL_ID				채널ID	C		필수	"CHNL_1"
USE_INTT_ID			이용기관ID	C		필수	"UTLZ_2003031465790"
USER_ID				사용자ID	C		필수	"hdlee@kmac.co.kr" 
REQ_DATA								
	REC				RECORD			
		EMP_NO			사원번호	C	20	필수	emplNo
		USE_INTT_ID		이용기관ID	C	50	필수	"UTLZ_2003031465790"
		BSNN_NO			사업자번호	C	15	필수	"BSNN_NO
							- KMAC (116-81-29581)
							- 미디어센터 (116-81-30996)
							- 리서치센터 (107-82-07824)"
		BSNN_NM			사업장명	C	200		"KMAC"
		FLNM			성명	C	100	필수	name
		ENG				영문성명	C	100		-
		CLPH_NTNL_CD	휴대폰국가코드	C	100	필수	"82"
		CLPH_NO			휴대폰번호	C	20	필수	mobileNo
		DVSN_CD			부서코드	C	20		dept
		DVSN_NM			부서명	C	100		deptname
		JBCL_NM			직급명	C	100		companyPositionName
		RSPT_NM			직책명	C	100		-
		USER_ID			사용자ID	C	50	필수	email
		USER_PW			사용자비밀번호	C	20	필수	"12345678"
		EML				이메일	C	100	필수	bizplay포탈과 연계(직원,bizplay웹 변경)   email 맞지요?
		STTS			상태	C	1	필수	enable
		AMNN_DTTM		수정일시	C	14		-
		OOH_ZPCD		자택우편번호	C	20		-
		OOH_POST_ADRS	자택주소	C	200		-
		OOH_DTL_ADRS	자택상세주소	C	200		-
 */
public class BizplayExpertPoolInfoSet { 
	
	private String SVC_CD = "emplinfo_erp_cu002";
	private String SECR_KEY = "def79897-b672-db4d-3314-90be10eddf7e";
	private String PTL_STS = "C";
	private String PTL_ID = "PTL_3";
	private String CHNL_ID = "CHNL_1";
	private String USE_INTT_ID = "UTLZ_2003031465790";
	private String USER_ID = "hdlee@kmac.co.kr";
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

	public String getPTL_ID() {
		return PTL_ID;
	}

	public void setPTL_ID(String pTL_ID) {
		PTL_ID = pTL_ID;
	}

	public String getCHNL_ID() {
		return CHNL_ID;
	}

	public void setCHNL_ID(String cHNL_ID) {
		CHNL_ID = cHNL_ID;
	}

	public String getUSE_INTT_ID() {
		return USE_INTT_ID;
	}

	public void setUSE_INTT_ID(String uSE_INTT_ID) {
		USE_INTT_ID = uSE_INTT_ID;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
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
