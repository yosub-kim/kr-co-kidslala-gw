package kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn;

import java.util.List;

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
public class REQ_DATA {

	private List<REC> REC;

	public List<REC> getREC() {
		return REC;
	}

	public void setREC(List<REC> rEC) {
		REC = rEC;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
