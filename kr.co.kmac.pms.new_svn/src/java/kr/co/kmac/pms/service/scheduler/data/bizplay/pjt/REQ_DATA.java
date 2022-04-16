package kr.co.kmac.pms.service.scheduler.data.bizplay.pjt;

import java.util.ArrayList;
import java.util.List;
/**
{
	'API_KEY':'ba0a66b8-e554-14ed-0a00-ce447681d53a',
	'ORG_CD':'C',
	'API_ID':'1811'
	'REQ_DATA': { 
		'BIZ_NO':'',
		'WORK_TYPE':'', 
		'REQ_DETAIL':[ 
			{'ITEM_ERP_CD':   '', 'ITEM_NM':   '', 'ITEM_MEMO':   '', 'ACT_YN':   '', 'ITEM_GR_CD':   '', 'ITEM_SUB_YN':   ''},
			{'ITEM_ERP_CD':   '', 'ITEM_NM':   '', 'ITEM_MEMO':   '', 'ACT_YN':   '', 'ITEM_GR_CD':   '', 'ITEM_SUB_YN':   ''},
			{'ITEM_ERP_CD':   '', 'ITEM_NM':   '', 'ITEM_MEMO':   '', 'ACT_YN':   '', 'ITEM_GR_CD':   '', 'ITEM_SUB_YN':   ''}
		]
	}
}
 */
public class REQ_DATA {

	private String BIZ_NO;
	private String WORK_TYPE = "09";
	private List<REQ_DETAIL> REQ_DETAIL = new ArrayList<REQ_DETAIL>();

	public String getBIZ_NO() {
		return this.BIZ_NO;
	}

	public void setBIZ_NO(String bIZ_NO) {
		this.BIZ_NO = bIZ_NO;
	}

	public String getWORK_TYPE() {
		return this.WORK_TYPE;
	}

	public void setWORK_TYPE(String wORK_TYPE) {
		this.WORK_TYPE = wORK_TYPE;
	}

	public List<REQ_DETAIL> getREQ_DETAIL() {
		return REQ_DETAIL;
	}

	public void setREQ_DETAIL(List<REQ_DETAIL> REQ_DETAIL) {
		this.REQ_DETAIL = REQ_DETAIL;
	}

}
