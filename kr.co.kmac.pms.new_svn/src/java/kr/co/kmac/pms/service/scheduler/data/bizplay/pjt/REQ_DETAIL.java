package kr.co.kmac.pms.service.scheduler.data.bizplay.pjt;
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
public class REQ_DETAIL {

	private String ITEM_ERP_CD;
	private String ITEM_NM;
	private String ITEM_MEMO;
	private String ACT_YN;
	private String ITEM_GR_CD;
	private String ITEM_SUB_YN;

	public String getITEM_ERP_CD() {
		return ITEM_ERP_CD;
	}

	public void setITEM_ERP_CD(String iTEM_ERP_CD) {
		ITEM_ERP_CD = iTEM_ERP_CD;
	}

	public String getITEM_NM() {
		return ITEM_NM;
	}

	public void setITEM_NM(String iTEM_NM) {
		ITEM_NM = iTEM_NM;
	}

	public String getITEM_MEMO() {
		return ITEM_MEMO;
	}

	public void setITEM_MEMO(String iTEM_MEMO) {
		ITEM_MEMO = iTEM_MEMO;
	}

	public String getACT_YN() {
		return ACT_YN;
	}

	public void setACT_YN(String aCT_YN) {
		ACT_YN = aCT_YN;
	}

	public String getITEM_GR_CD() {
		return ITEM_GR_CD;
	}

	public void setITEM_GR_CD(String iTEM_GR_CD) {
		ITEM_GR_CD = iTEM_GR_CD;
	}

	public String getITEM_SUB_YN() {
		return ITEM_SUB_YN;
	}

	public void setITEM_SUB_YN(String iTEM_SUB_YN) {
		ITEM_SUB_YN = iTEM_SUB_YN;
	}
}