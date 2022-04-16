<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>협력기관 검색</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var rowTemplete = "";
var noRow = "";

function Page_OnLoad(){
	var listObj = document.getElementById("companyList");
	rowTemplete = listObj.childNodes[1].outerHTML;
	noRow = listObj.childNodes[2].outerHTML;
	listObj.removeChild(listObj.childNodes[2]);
	listObj.removeChild(listObj.childNodes[1]);
	
	frm.searchKey.focus();
}
function press_Enter(){
	if(event.keyCode == 13) doSearch();	
}
//function btnSearch_click(){
function doSearch(){
	
	var ActionURL = "/action/CommonCodeRetrieveAction.do"; ///action/CommonCodeRetrieveAction.do?mode=getCodeEntityList&tableName=
	ActionURL += "?mode=getRelationWithKmacList";
	if(frm.searchKey.value == ""){
		alert("검색하실 회사명을 입력해주세요.");
		return;
	}
	ActionURL += "&searchKey=" + encodeURIComponent(frm.searchKey.value);

	//alert(ActionURL);
	var pNode = document.getElementById("companyList");
	//alert("페이지 로드");
	var status = AjaxRequest.get(
		{
			'url':ActionURL
			, 'anotherParameter':'false'
			, 'onSuccess':function(obj){  // 
				//alert(obj.responseText);
	           	var res = eval('(' + obj.responseText + ')');
	           	var rsObj = res.returnValue;

	           	var divObj = document.createElement("div");
	        	divObj.id = "xxxTempDivForTemplete";
	        	document.body.appendChild(divObj);
	        	for(var i = pNode.childNodes.length-1; i >= 0; i--){
	        		pNode.removeChild(pNode.childNodes[i]);
	        	}
	            if(rsObj.length >= 1){
		            for(var i=0; i < rsObj.length; i++){
			            var temp = rowTemplete;
		            	temp = temp.replaceAll("#ORGCODE#"		, rsObj[i].OrgCODE);
		            	temp = temp.replaceAll("#NAME#"			, rsObj[i].name);
		            	temp = temp.replaceAll("#GUSANG#"		, rsObj[i].gusang);
		            	temp = temp.replaceAll("#PM#"			, rsObj[i].PM);
		            	temp = temp.replaceAll("#COUNTRY#"		, rsObj[i].country);
		            	temp = temp.replaceAll("#CEO#"			, rsObj[i].ceo);
		            	temp = temp.replaceAll("#ESTYEAR#"		, rsObj[i].EstYear);
		            	temp = temp.replaceAll("#EMPLOYEES#"	, rsObj[i].Employees);
		            	temp = temp.replaceAll("#SALES#"		, rsObj[i].sales);
		            	temp = temp.replaceAll("#PHONE#"		, rsObj[i].phone);
		            	temp = temp.replaceAll("#FAX#"			, rsObj[i].fax);
		            	temp = temp.replaceAll("#HOMPAGE#"		, rsObj[i].hompage);
		            	temp = temp.replaceAll("#ZIPCODE#"		, rsObj[i].zipcode);
		            	temp = temp.replaceAll("#ADDRESS_01#"	, rsObj[i].address_01);
		            	temp = temp.replaceAll("#ADDRESS_02#"	, rsObj[i].address_02);
		            	temp = temp.replaceAll("#BUSINESSTYPE#"	, rsObj[i].businessType);
		            	temp = temp.replaceAll("#CATEGORY#"		, rsObj[i].category);
		            	temp = temp.replaceAll("#OTHERS#"		, rsObj[i].others);
		            	temp = temp.replaceAll("#WRITER#"		, rsObj[i].writer);
		            	temp = temp.replaceAll("#VCHECK#"		, rsObj[i].vcheck);
		            	temp = temp.replaceAll("#MODIFIER#"		, rsObj[i].modifier);
		            	temp = temp.replaceAll("#RELNAME#"		, rsObj[i].RELNAME);
						
		                var rowData = HTMLtoDOM(temp);
		                pNode.appendChild(rowData);
		            } 
	            }else{
	            	var temp = noRow;
				
	                var rowData = HTMLtoDOM(temp);
	                pNode.appendChild(rowData);
	            }          
			}// END  -- onSuccess
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
				alert(obj.responseText);
						
				alert("데이터를 가져오지 못 했습니다.");
			}//END -- onError
		});//END -- status
	status = null;	
	
}

function CompanyInfo(){
	this.OrgCODE      = "";
	this.name         = "";
	this.gusang       = "";
	this.PM           = "";
	this.country      = ""; 
	this.ceo          = "";
	this.EstYear      = ""; 
	this.Employees    = ""; 
	this.sales        = "";
	this.phone        = "";
	this.fax          = "";
	this.hompage      = ""; 
	this.zipcode      = ""; 
	this.address_01   = "";
	this.address_02   = "";
	this.businessType = "";
	this.category     = "";
	this.others       = "";
	this.writer       = "";
	this.vcheck       = "";
	this.modifier     = "";
}
function btnSelect_onclick(){
	var chkBox = document.getElementsByName("chkCompnay");
	var cominfoList = new Array();
	var chkCount = 0;
	for(var i = 0; i < chkBox.length; i++){
		if(chkBox[i].checked){
			var cominfo = new CompanyInfo();
			cominfo.OrgCODE      = chkBox[i].xUserColumn_OrgCODE;     
			cominfo.name         = chkBox[i].xUserColumn_name;        
			cominfo.gusang       = chkBox[i].xUserColumn_gusang;      
			cominfo.PM           = chkBox[i].xUserColumn_PM;          
			cominfo.country      = chkBox[i].xUserColumn_country;      
			cominfo.ceo          = chkBox[i].xUserColumn_ceo;         
			cominfo.EstYear      = chkBox[i].xUserColumn_EstYear;      
			cominfo.Employees    = chkBox[i].xUserColumn_Employees;    
			cominfo.sales        = chkBox[i].xUserColumn_sales;       
			cominfo.phone        = chkBox[i].xUserColumn_phone;       
			cominfo.fax          = chkBox[i].xUserColumn_fax;         
			cominfo.hompage      = chkBox[i].xUserColumn_hompage;      
			cominfo.zipcode      = chkBox[i].xUserColumn_zipcode;      
			cominfo.address_01   = chkBox[i].xUserColumn_address_01;  
			cominfo.address_02   = chkBox[i].xUserColumn_address_02;  
			cominfo.businessType = chkBox[i].xUserColumn_businessType;
			cominfo.category     = chkBox[i].xUserColumn_category;    
			cominfo.others       = chkBox[i].xUserColumn_others;      
			cominfo.writer       = chkBox[i].xUserColumn_writer;      
			cominfo.vcheck       = chkBox[i].xUserColumn_vcheck;      
			cominfo.modifier     = chkBox[i].xUserColumn_modifier;

			cominfoList[chkCount] = cominfo;
			chkCount ++;
		}
	}
	if(chkCount != 0) {
		opener.<c:out value="${callbackFunc }" />(cominfoList);
		window.close();
	}
}

</script>
</head>
<body onload="Page_OnLoad();" style="padding-left: 4px; padding-right: 4px">
<%-- 작업영역 --%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<h4 class="mainTitle">협력기관 검색</h4>
		</td>
	</tr>
	<tr>
		<td>
			<table cellSpacing="0" width="100%">
				<colgroup>
					<col width="85px">
					<col width="*"> 
					<col width="85px">
				</colgroup> 
				<thead>
					<form name="frm" onsubmit="return false;">
					<tr>
						<td class="searchTitle_center">기관명</td>
						<td class="searchField_left">
							<input type="text" name="searchKey" class="textInput_left" onkeypress="press_Enter();">
							<!-- <a href="#" onclick="btnSearch_click();"><img src="/images/btn_search.jpg" align="absmiddle"></a> -->
						</td>
						<td class="searchField_center" style="border-left:0px"><a href="javascript:doSearch();"><img src="/images/sub/btnSrch.gif" height="27" width="27"></a></td>
					</tr>
					</form>
				</thead>
			</table>
		</td>
	</tr>
	<tr>
		<td height="8"></td>
	</tr>
	<tr>
		<td>
			<div class="outLine1" style="width:392px;height:190; vertical-align:top; overflow:auto;">
			<table class="listTable" style="table-layout:fixed;">
				<thead>
					<tr>
						<td style="width: 40px">선택</td>
						<td width="*">회사명</td>
						<td style="width: 90px">KMAC와 관계</td>
					</tr>
				</thead>
				<tbody id="companyList">
					<tr>
						<td colspan="3">협력기관명을 검색하세요.</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" name="chkCompnay"
								xUserColumn_OrgCODE      = "#ORGCODE#"
								xUserColumn_name         = "#NAME#"
								xUserColumn_gusang       = "#GUSANG#"
								xUserColumn_PM           = "#PM#"
								xUserColumn_country      = "#COUNTRY#"
								xUserColumn_ceo          = "#CEO#"
								xUserColumn_EstYear      = "#ESTYEAR#"
								xUserColumn_Employees    = "#EMPLOYEES#"
								xUserColumn_sales        = "#SALES#"
								xUserColumn_phone        = "#PHONE#"
								xUserColumn_fax          = "#FAX#"
								xUserColumn_hompage      = "#HOMPAGE#"
								xUserColumn_zipcode      = "#ZIPCODE#"
								xUserColumn_address_01   = "#ADDRESS_01#"
								xUserColumn_address_02   = "#ADDRESS_02#"
								xUserColumn_businessType = "#BUSINESSTYPE#"
								xUserColumn_category     = "#CATEGORY#"
								xUserColumn_others       = "#OTHERS#"
								xUserColumn_writer       = "#WRITER#"
								xUserColumn_vcheck       = "#VCHECK#"
								xUserColumn_writtendate  = "#WRITTENDATE#"
								xUserColumn_modifier     = "#MODIFIER#"
								xUserColumn_modifydate   = "#MODIFYDATE#">
						</td>
						<td class="detailTableField_left"><a title="#NAME#">#NAME#</a></td>
						<td >#RELNAME#</td>
					</tr>
					<tr>
						<td colspan="3">등록된 협력기관이 없습니다.</td>
					</tr>
				</tbody>
			</table>
			</div>
		</td>
	</tr>
	<tr>
		<td height="8"></td>
	</tr>
	<tr>
		<td>
			<table width='100%' height='36' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<div class="btNbox pt10 pb10 txtR">
						<a class="btNaaa txt2btn" href="javascript:btnSelect_onclick();">선택</a>
						<a class="btNa0a0a0 txt2btn" href="javascript:window.close();">닫기</a>
					</div>
				</td>
			</tr>
		</td>
	</tr>
</table>
<div id="xxxTempDivForTemplete" style="display:none"></div>
</body>
</html>