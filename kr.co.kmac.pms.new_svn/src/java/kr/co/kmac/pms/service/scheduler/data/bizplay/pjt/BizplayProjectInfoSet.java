package kr.co.kmac.pms.service.scheduler.data.bizplay.pjt;


/**
	API_KEY				����Ű	"ba0a66b8-e554-14ed-0a00-ce447681d53a"	O
	ORG_CD				����ڵ�	"BSNN_NO (����� ����ڹ�ȣ )
							- KMAC (116-81-29581)
							- �̵��� (116-81-30996)
							- ����ġ���� (107-82-07824)"	O 
	API_ID				API ��	��1811��	O
"�Էº�
	(REQ_DATA)"	BIZ_NO				����ڹ�ȣ	"BSNN_NO (����� ����ڹ�ȣ )
									- KMAC (116-81-29581)
									- �̵��� (116-81-30996)
									- ����ġ���� (107-82-07824)"	O
		WORK_TYPE				�۾�����	-	
		REQ_DETAIL (�迭 ��)  :��û������								
		1	ITEM_ERP_CD			�׸�ERP�ڵ�	projectcode  (������Ʈ�ڵ�)	O
		2	ITEM_NM				�׸��		projectname (������Ʈ��)	O
		3	ITEM_MEMO			����	-	
		4	ACT_YN				����			projectstate (������Ʈ���� (Y/N))	O
		5	ITEM_GR_CD			�����׸�ERP�ڵ�	-	
		6	ITEM_SUB_YN			�����׸񿩺�	-	
 */
public class BizplayProjectInfoSet {
	
	private String API_KEY = "ba0a66b8-e554-14ed-0a00-ce447681d53a";
	private String ORG_CD;
	private String API_ID = "1811";
	private REQ_DATA REQ_DATA;
	
	public String getAPI_KEY() {
		return API_KEY;
	}
	public void setAPI_KEY(String aPI_KEY) {
		API_KEY = aPI_KEY;
	}
	public String getORG_CD() {
		return ORG_CD;
	}
	public void setORG_CD(String oRG_CD) {
		ORG_CD = oRG_CD;
	}
	public String getAPI_ID() {
		return API_ID;
	}
	public void setAPI_ID(String aPI_ID) {
		API_ID = aPI_ID;
	}
	public REQ_DATA getREQ_DATA() {
		return REQ_DATA;
	}
	public void setREQ_DATA(REQ_DATA rEQ_DATA) {
		REQ_DATA = rEQ_DATA;
	}
	

}
