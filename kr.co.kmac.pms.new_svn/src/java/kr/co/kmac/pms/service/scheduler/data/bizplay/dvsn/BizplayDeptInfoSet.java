package kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
SVC_CD				�����ڵ�
SECR_KEY				����Ű
PTL_STS				��Ż����
REQ_DATA				
	REC			
		USE_INTT_ID		�̿���ID
		BSUN_ID		�����ID
		DVSN_CD		�μ��ڵ�
		DVSN_NM		�μ���
		HGRN_DVSN_CD		�����μ��ڵ�
		OTPT_SQNC		��¼���
		ACVT_YN		Ȱ������
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
