package kr.co.kmac.pms.service.scheduler.data.bizplay.empl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
SVC_CD				�����ڵ�	C		�ʼ�	"emplinfo_erp_cu002"
SECR_KEY			����Ű	C		�ʼ�	"def79897-b672-db4d-3314-90be10eddf7e"
PTL_STS				��Ż����	C		�ʼ�	"C"
PTL_ID				��ŻID	C		�ʼ�	"PTL_3"
CHNL_ID				ä��ID	C		�ʼ�	"CHNL_1"
USE_INTT_ID			�̿���ID	C		�ʼ�	"UTLZ_2003031465790"
USER_ID				�����ID	C		�ʼ�	"hdlee@kmac.co.kr" 
REQ_DATA								
	REC				RECORD			
		EMP_NO			�����ȣ	C	20	�ʼ�	emplNo
		USE_INTT_ID		�̿���ID	C	50	�ʼ�	"UTLZ_2003031465790"
		ABSNN_NO			����ڹ�ȣ	C	15	�ʼ�	"BSNN_NO
							- KMAC (116-81-29581)
							- �̵��� (116-81-30996)
							- ����ġ���� (107-82-07824)" 
		BSNN_NM			������	C	200		"KMAC"
		FLNM			����		C	100	�ʼ�	name
		ENG				��������	C	100		-
		CLPH_NTNL_CD	�޴��������ڵ�	C	100	�ʼ�	"82"
		CLPH_NO			�޴�����ȣ	C	20	�ʼ�	mobileNo
		DVSN_CD			�μ��ڵ�	C	20		dept
		DVSN_NM			�μ���	C	100		deptname
		JBCL_NM			���޸�	C	100		companyPositionName
		RSPT_NM			��å��	C	100		-
		USER_ID			�����ID	C	50	�ʼ�	email
		USER_PW			����ں�й�ȣ	C	20	�ʼ�	"12345678"
		EML				�̸���	C	100	�ʼ�	bizplay��Ż�� ����(����,bizplay�� ����)   email ������?
		STTS			����	C	1	�ʼ�	enable
		AMNN_DTTM		�����Ͻ�	C	14		-
		OOH_ZPCD		���ÿ����ȣ	C	20		-
		OOH_POST_ADRS	�����ּ�	C	200		-
		OOH_DTL_ADRS	���û��ּ�	C	200		-
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