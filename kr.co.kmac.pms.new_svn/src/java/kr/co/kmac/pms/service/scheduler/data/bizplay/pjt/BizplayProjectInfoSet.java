package kr.co.kmac.pms.service.scheduler.data.bizplay.pjt;


/**
	API_KEY				인증키	"ba0a66b8-e554-14ed-0a00-ce447681d53a"	O
	ORG_CD				기관코드	"BSNN_NO (기업고객 사업자번호 )
							- KMAC (116-81-29581)
							- 미디어센터 (116-81-30996)
							- 리서치센터 (107-82-07824)"	O 
	API_ID				API 명	“1811”	O
"입력부
	(REQ_DATA)"	BIZ_NO				사업자번호	"BSNN_NO (기업고객 사업자번호 )
									- KMAC (116-81-29581)
									- 미디어센터 (116-81-30996)
									- 리서치센터 (107-82-07824)"	O
		WORK_TYPE				작업구분	-	
		REQ_DETAIL (배열 값)  :요청데이터								
		1	ITEM_ERP_CD			항목ERP코드	projectcode  (프로젝트코드)	O
		2	ITEM_NM				항목명		projectname (프로젝트명)	O
		3	ITEM_MEMO			내용	-	
		4	ACT_YN				상태			projectstate (프로젝트상태 (Y/N))	O
		5	ITEM_GR_CD			상위항목ERP코드	-	
		6	ITEM_SUB_YN			하위항목여부	-	
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
