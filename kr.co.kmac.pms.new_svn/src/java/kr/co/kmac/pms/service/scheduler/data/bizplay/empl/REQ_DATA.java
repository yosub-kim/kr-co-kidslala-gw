package kr.co.kmac.pms.service.scheduler.data.bizplay.empl;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
{
	'SVC_CD': ''
	'SECR_KEY': ''
	'PTL_STS': ''
	'PTL_ID': ''
	'CHNL_ID': ''
	'USE_INTT_ID': ''
	'USER_ID': ''
	'REQ_DATA':{ ''
		'REC':[ ''
			{ ''
				'EMP_NO': ''
				'USE_INTT_ID': ''
				'BSNN_NO': ''
				'BSNN_NM': '' 
				'FLNM': ''
				'ENG': ''
				'CLPH_NTNL_CD': ''
				'CLPH_NO': '' 
				'DVSN_CD': ''
				'DVSN_NM': ''
				'JBCL_NM': ''
				'RSPT_NM': ''
				'USER_ID': ''
				'USER_PW': ''
				'EML': ''
				'STTS': ''
				'AMNN_DTTM': ''
				'OOH_ZPCD': ''
				'OOH_POST_ADRS': ''
				'OOH_DTL_ADRS': ''
			}
		]
	}
}
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
