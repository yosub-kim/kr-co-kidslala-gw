<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>우편번호 검색</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var rowTemplete = "";
rowTemplete += "<tr onclick=\"zipCode_Select('#ZIPCODE#','#ADDRESS#');\" onmouseover=\"row_over(this);\" onmouseout=\"row_out(this);\">\n";
rowTemplete += "	<td align=\"center\" class=\"col_Body_Center\">#ZIPCODE#</td>\n";
rowTemplete += "	<td class=\"detailTableField_left\">#ADDRESS#</td>\n";
rowTemplete += "</tr>";
 
var noRow = "";
noRow += "<tr>\n";
noRow += "	<td colspan=2 class=\"col_Body_Center\" height=\"27px\" align=\"center\">동/면 이름이 존재하지 않습니다.</td>\n";
noRow += "</tr>";
function Page_OnLoad(){
	//frm.searchKey.focus();
}
function press_Enter(){
	if(event.keyCode == 13) doSearch();	
}
function doSearch(){
	if($("toggleVar").value == "dongNameSearch"){
		
		var ActionURL = "/action/CommonCodeRetrieveAction.do"; ///action/CommonCodeRetrieveAction.do?mode=getCodeEntityList&tableName=
		ActionURL += "?mode=getZipCodeEntityList";
		ActionURL += "&tableName=ZIPCODE";
		if(frm.searchKey.value == ""){
			alert("검색하실 동/면을 입력해주세요.");
			return false;
		}
		ActionURL += "&data3=" + encodeURIComponent(frm.searchKey.value);
			
		var pNode = document.getElementById("zipCodeList");
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
				            var address = rsObj[i].data1;
				            address += " " + rsObj[i].data2;
				            address += " " + rsObj[i].data3;
				            address += ((rsObj[i].data4 == "") ? "" : " " + rsObj[i].data4);
				            address += ((rsObj[i].data5 == "") ? "" : " " + rsObj[i].data5);
				            address += ((rsObj[i].data6 == "") ? "" : " " + rsObj[i].data6);
				            address += ((rsObj[i].data7 == "") ? "" : " " + rsObj[i].data7);
				            
			                var zipCode = rsObj[i].key1.substring(0,3) + "-" + rsObj[i].key1.substring(3,6);
			                var temp = rowTemplete;
			                temp = temp.replaceAll("#ZIPCODE#" , zipCode);
			                temp = temp.replaceAll("#ADDRESS#" , address);
							
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
	}else{
		if($("gugun").value == ""){alert("검색하실 시군구을 선택해주세요.");return false;}
		if($("roadName").value == ""){alert("검색하실 도로명을 입력해주세요.");return false;}
		
		var ActionURL = "/action/ExpertPoolCodeRetrieveAction.do?mode=getRoadZipCodeRoadList";
		ActionURL += "&sido=" + encodeURIComponent($("sido").value);		
		ActionURL += "&gugun=" + encodeURIComponent($("gugun").value);		
		ActionURL += "&roadName=" + encodeURIComponent($("roadName").value);		
			
		var pNode = document.getElementById("zipCodeList");
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
				            var address = rsObj[i].sido;
					            address += " " + rsObj[i].gugun;
					            address += " " + rsObj[i].dong;
					            address += " " + rsObj[i].roadName;
					            address += " " + rsObj[i].roadBunji;
			                var zipCode = rsObj[i].post;
			                var temp = rowTemplete;
			                temp = temp.replaceAll("#ZIPCODE#" , zipCode);
			                temp = temp.replaceAll("#ADDRESS#" , address);
							
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
}

function zipCode_Select(zipcode,address){
	opener.<c:out value="${callbackFunc }" />(zipcode,address);
	window.close();
}

function changeSearch(){
	if($("toggleVar").value == "dongNameSearch"){
		$("dongNameSearch").show();
		$("roadNameSearch").hide();
	}else{
		$("roadNameSearch").show();
		$("dongNameSearch").hide();
	}	
}

function laodGugunData(obj) {
	var ActionURL = "/action/ExpertPoolCodeRetrieveAction.do?mode=getRoadZipCodeSidoList";
	ActionURL += "&sido=" + encodeURIComponent($(obj).value);
	var status = AjaxRequest.get(
		{
			'url':ActionURL
			, 'anotherParameter':'false'
			, 'onSuccess':function(obj){  // 
				$("gugunDiv").innerHTML = "";
				
	           	var res = eval('(' + obj.responseText + ')');
	           	var rsObj = res.returnValue;
	           	var resStr = '<select id="gugun" title="시군구 선택" name="gugun" style="font-size: 12px; width: 120px;">';
				resStr += "<OPTION>시군구 선택</OPTION>";
				for(var i=0; rsObj.length > i; i++){
					resStr += "<OPTION value="+rsObj[i].gugun+">"+rsObj[i].gugun+"</OPTION>"; 
				}	
				resStr += "</select><br/>";
				
				$('gugunDiv').innerHTML = resStr; 
			}// END  -- onSuccess
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
				alert("데이터를 가져오지 못 했습니다.");
			}//END -- onError
		});//END -- status
	status = null;	
		
}
</script>
</head>
<body onload="Page_OnLoad();">
<%-- 작업영역 --%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="8"></td>
	</tr>
	<tr>
		<td height="12" style="padding-left:4px;">
			<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
				<tr>
					
					<td width="100%" align="left" valign="bottom"><span class="mainTitle">&nbsp;우편번호 검색</span></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="8"></td>
	</tr>
	<tr>
		<td style="padding-left: 4px; padding-right: 4px;">
			<%@ include file="/common/include/searchBox_Header.jsp"%>
			<table cellSpacing="0" width="100%">
				<form name="frm" onsubmit="return false;">
				<tr>
					<td class="detailTableTitle_center" width="80">
						<select id="toggleVar" onchange="changeSearch()" style="font-size: 12px;">
							<option value="dongNameSearch">동이름검색</option>
							<option value="roadNameSearch">도로명검색</option>
						</select>
					</td>
					<td class="detailTableField_left" width="*">
						<div id="dongNameSearch" style="display: inline;">
							<input type="text" class="textInput_left" style="ime-mode:active" name="searchKey" onkeypress="press_Enter();">
						</div> 
						<div id="roadNameSearch" style="display: none;">
							<SELECT id="sido" title="시도선택" onchange="laodGugunData(this);" name="sido" style="font-size: 12px;">
								<OPTION value="">시도 선택</OPTION><OPTION value=서울>서울</OPTION><OPTION value=강원>강원</OPTION><OPTION value=대전>대전</OPTION><OPTION value=충남>충남</OPTION><OPTION value=충북>충북</OPTION>
								<OPTION value=경기>경기</OPTION><OPTION value=인천>인천</OPTION><OPTION value=광주>광주</OPTION><OPTION value=전남>전남</OPTION><OPTION value=전북>전북</OPTION><OPTION value=부산>부산</OPTION>
								<OPTION value=경남>경남</OPTION><OPTION value=경북>경북</OPTION><OPTION value=대구>대구</OPTION><OPTION value=울산>울산</OPTION><OPTION value=제주>제주</OPTION>
							</SELECT> 
							<span id="gugunDiv" >
							</span>
							<input type="text" class="textInput_left" style="ime-mode:active" name="roadName" onkeypress="press_Enter();">
						</div>
					</td>
				</tr>
				</form>
			</table>
			<%@ include file="/common/include/searchBox_Footer.jsp"%>
		</td>
	</tr>	
	<tr>
		<td height="8"></td>
	</tr>
	<tr>
		<td style="padding-left:4px;">
			<div class="outLine1" style='width:392px;height:200; vertical-align:top; overflow:auto; cursor:hand;'>
			<table cellSpacing="0" cellpadding="0" width="100%" class="listTable" style="table-layout:fixed;">
				<thead>
					<tr>
						<td style="WIDTH: 80px">우편번호</td>
						<td class="col_Header">주  소</td>
					</tr>
				</thead>
				<tbody id="zipCodeList">
					<tr>
						<td height="27px" colspan="2" align="center">동 이름을 입력하여 검색하세요.</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<div id="xxxTempDivForTemplete" style="display:none"></div>
</body>
</html>