package kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn;

import java.util.List;

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
