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
		BSNN_NO			����ڹ�ȣ	C	15	�ʼ�	"BSNN_NO
							- KMAC (116-81-29581)
							- �̵��� (116-81-30996)
							- ����ġ���� (107-82-07824)"
		BSNN_NM			������	C	200		"KMAC"
		FLNM			����	C	100	�ʼ�	name
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
