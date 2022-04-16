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
		ABSNN_NO			사업자번호	C	15	필수	"BSNN_NO
							- KMAC (116-81-29581)
							- 미디어센터 (116-81-30996)
							- 리서치센터 (107-82-07824)" 
		BSNN_NM			사업장명	C	200		"KMAC"
		FLNM			성명		C	100	필수	name
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
public class REC {

	private String EMP_NO;
	private String USE_INTT_ID = "UTLZ_2003031465790";
	private String BSNN_NO;
	private String BSNN_NM="KMAC";
	private String FLNM;
	private String ENG;
	private String CLPH_NTNL_CD="82 ";
	private String CLPH_NO;
	private String DVSN_CD;
	private String DVSN_NM;
	private String JBCL_NM;
	private String RSPT_NM;
	private String USER_ID;
	private String USER_PW="12345678";
	private String EML;
	private String STTS;
	private String AMNN_DTTM;
	private String OOH_ZPCD;
	private String OOH_POST_ADRS;
	private String OOH_DTL_ADRS;

	public String getEMP_NO() {
		return EMP_NO;
	}

	public void setEMP_NO(String eMP_NO) {
		EMP_NO = eMP_NO;
	}

	public String getUSE_INTT_ID() {
		return USE_INTT_ID;
	}

	public void setUSE_INTT_ID(String uSE_INTT_ID) {
		USE_INTT_ID = uSE_INTT_ID;
	}

	public String getBSNN_NO() {
		return BSNN_NO;
	}

	public void setBSNN_NO(String bSNN_NO) {
		BSNN_NO = bSNN_NO;
	}

	public String getBSNN_NM() {
		return BSNN_NM;
	}

	public void setBSNN_NM(String bSNN_NM) {
		BSNN_NM = bSNN_NM;
	}

	public String getFLNM() {
		return FLNM;
	}

	public void setFLNM(String fLNM) {
		FLNM = fLNM;
	}

	public String getENG() {
		return ENG;
	}

	public void setENG(String eNG) {
		ENG = eNG;
	}

	public String getCLPH_NTNL_CD() {
		return CLPH_NTNL_CD;
	}

	public void setCLPH_NTNL_CD(String cLPH_NTNL_CD) {
		CLPH_NTNL_CD = cLPH_NTNL_CD;
	}

	public String getCLPH_NO() {
		return CLPH_NO;
	}

	public void setCLPH_NO(String cLPH_NO) {
		CLPH_NO = cLPH_NO;
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

	public String getJBCL_NM() {
		return JBCL_NM;
	}

	public void setJBCL_NM(String jBCL_NM) {
		JBCL_NM = jBCL_NM;
	}

	public String getRSPT_NM() {
		return RSPT_NM;
	}

	public void setRSPT_NM(String rSPT_NM) {
		RSPT_NM = rSPT_NM;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getUSER_PW() {
		return USER_PW;
	}

	public void setUSER_PW(String uSER_PW) {
		USER_PW = uSER_PW;
	}

	public String getEML() {
		return EML;
	}

	public void setEML(String eML) {
		EML = eML;
	}

	public String getSTTS() {
		return STTS;
	}

	public void setSTTS(String sTTS) {
		STTS = sTTS;
	}

	public String getAMNN_DTTM() {
		return AMNN_DTTM;
	}

	public void setAMNN_DTTM(String aMNN_DTTM) {
		AMNN_DTTM = aMNN_DTTM;
	}

	public String getOOH_ZPCD() {
		return OOH_ZPCD;
	}

	public void setOOH_ZPCD(String oOH_ZPCD) {
		OOH_ZPCD = oOH_ZPCD;
	}

	public String getOOH_POST_ADRS() {
		return OOH_POST_ADRS;
	}

	public void setOOH_POST_ADRS(String oOH_POST_ADRS) {
		OOH_POST_ADRS = oOH_POST_ADRS;
	}

	public String getOOH_DTL_ADRS() {
		return OOH_DTL_ADRS;
	}

	public void setOOH_DTL_ADRS(String oOH_DTL_ADRS) {
		OOH_DTL_ADRS = oOH_DTL_ADRS;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}