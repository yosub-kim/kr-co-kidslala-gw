<%@ page contentType="text/html; charset=utf-8"
	import="java.util.List"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>메뉴 <c:out value="${ ttlGbn }" /></title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function page_load(){
	
}

function getDataList(tabName){
	var ActionURL = "/action/CommonCodeAction.do";	
	ActionURL += "?mode=codeDataRetrieve";
	ActionURL += "&tableName=" + tabName;

	var status = AjaxRequest.get(
		{ 
			'url':ActionURL
			, 'anotherParameter':'false'
			, 'onSuccess':function(obj){
		     	var res = eval('(' + obj.responseText + ')');
		      	//var rsObj = res.returnValue;
		      	var codeDef = res.codeDef;
		      	var vHead = new Array();
		      	if(vHead.length == 0){
		      		var nObj = new Object();
		      		nObj.columnName = "check";
					nObj.columnLabel = "선택";
					vHead.push(nObj);
		      	}
		      	for(var i = 1; i < 4;i++){
			      	var keyLabel = eval("codeDef.key" + i + "Label");
					if(keyLabel != ""){
						var nObj = new Object();
						nObj.columnName = "key" + i;
						nObj.columnLabel = keyLabel;
						vHead.push(nObj);
						//vHead.push('"columnName":"key_' + i + '","columnLabel":"' + keyLabel + '"');
					}
		      	}

		      	for(var i = 1; i < 11;i++){
			      	var dataLabel = eval("codeDef.data" + i + "Label");
					if(dataLabel != ""){
						var nObj = new Object();
						nObj.columnName = "data" + i;
						nObj.columnLabel = dataLabel;
						vHead.push(nObj);
						//vHead.push('"columnName":"data_' + i + '","columnLabel":"' + dataLabel + '"');
					}
		      	}
		      	
		      	document.getElementById("tableName").innerHTML = codeDef.tableName;
		      	document.getElementById("tableDesc").innerHTML = codeDef.tableDesc;
		      	var codeList = res.codeList;
		      	var pObj = document.getElementById("headList");
		      	for(var i = pObj.childNodes.length -1; i > -1; --i){
		      		pObj.removeChild(pObj.childNodes[i]);
		      	}
		      	for(var i = 0; i < vHead.length; i++){
					var cObj = document.createElement("td");
					//cObj.className = "col_Header";
					//if (i == 0) {
						cObj.style ="padding:5px 1px;";
					//}
					var txtObj = document.createTextNode(vHead[i].columnLabel);
					cObj.appendChild(txtObj);
					pObj.appendChild(cObj);
		      	}
		      	var pList = document.getElementById("dataList");
		      	for(var i = pList.childNodes.length -1; i > -1; --i){
		      		pList.removeChild(pList.childNodes[i]);
		      	}
		      	for(var j = 0; j < codeList.length; j++){
			      	var trObj = document.createElement("tr");
			      	trObj.setAttribute("onmouseover","row_over(this);");
			      	trObj.setAttribute("onmouseout"	,"row_out(this);");
					for(var i = 0; i < vHead.length; i++){
						var cObj = document.createElement("td");
						//cObj.className = "detailTableField_left";
						if(vHead[i].columnName == "check"){
							var chkBox = document.createElement("input");
							chkBox.type = "checkbox";
							chkBox.name = "chkData";
							chkBox.value = codeDef.tableName + "~~|~~" + codeList[j].key1 + "~~|~~" + codeList[j].key2 + "~~|~~" + codeList[j].key3;
							cObj.appendChild(chkBox);
						} else {
							var vData = eval("codeList[j]." + vHead[i].columnName);
							var txtObj = document.createTextNode(vData);
							if(i == 1){
								var aObj = document.createElement("a");
								aObj.href="javascript:modify_data('" + codeDef.tableName + "','" + codeList[j].key1 + "','" + codeList[j].key2 + "','" + codeList[j].key3 + "')"
								aObj.appendChild(txtObj);
								cObj.appendChild(aObj);
							} else {
								cObj.appendChild(txtObj);
							}
						}
						trObj.appendChild(cObj);
					}
					pList.appendChild(trObj);
		      	}		      	
			}
			, 'onLoading':function(obj){}
			, 'onError':function(obj){				
				alert("데이터를 가져오는데 실패하였습니다.");
			}
		}
	); status = null;	
}

function btnData_Add(){
	var tableName = document.getElementById("tableName").innerText;
	modify_data(tableName,"","","");
}
function btnData_Del(){
	var tableName = document.getElementById("tableName").innerText;
	var sFrm = document.forms["frmData"];
	
	var selectedExpert = $$('input[name="chkData"]');
	
	var checkedCount = 0;
	for(var i = 0; i < selectedExpert.length; i++){
		var ele = selectedExpert[i];
		//if((ele.type == "checkbox") && (ele.name== "chkData")){
			if(ele.checked){
				checkedCount++;
			}
		//}
	}
	if(checkedCount < 1) {
		alert("삭제할 항목을 선택하세요.");
		return;
	}
	var cfrmMsg = "정말 삭제하시겠습니까?";
	if(confirm(cfrmMsg)){
		var ActionURL = "/action/CommonCodeAction.do";	
		ActionURL += "?mode=deleteData";
		
		var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					getDataList(tableName);
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){				
					alert("삭제할 수 없습니다.");
				}
			}
		); status = null;
	}
}


function btnDefine_Del(){
	
	var sFrm = document.forms["frmDefine"];
	var checkedCount = 0;
	alert("items: " + sFrm.elements.length);
	for(var i = 0; i < sFrm.elements.length; i++){
		var ele = sFrm.elements[i];
		if((ele.type == "checkbox") && (ele.name== "chkDefine")){
			if(ele.checked){
				checkedCount++;
				alert("checked: " + checkedCount);
			}
		}
	}
	if(checkedCount == 0) {
		alert("삭제할 항목을 선택하세요.");
		return;
	}
	var cfrmMsg = "정말 삭제하시겠습니까?";
	if(confirm(cfrmMsg)){
		var ActionURL = "/action/CommonCodeAction.do";	
		ActionURL += "?mode=deleteDefine";
		
		var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
				location.href = document.location;
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){				
					alert("삭제할 수 없습니다.");
				}
			}
		); status = null;
	}
}
function btnDefine_Add(){
	modify_define("");
}
function modify_define(tabName) {
	var defForm;
	var sURL      = "/action/CommonCodeAction.do?mode=inputDefineForm";
	sURL += "&tableName=" + tabName;

	var sFeatures = "width=600, height=350, top=100, left=100";
	defForm = window.open(sURL, "defForm", sFeatures);
	defForm.focus();
}
function modify_data(tabName, key1, key2, key3){
	
	var inputForm;
	var sURL      = "/action/CommonCodeAction.do?mode=inputDataForm";
	sURL += "&tableName=" + tabName;
	sURL += "&key1=" + key1;
	sURL += "&key2=" + key2;
	sURL += "&key3=" + key3;
	var sFeatures = "width=500, height=400, top=100, left=100";
	inputForm = window.open(sURL, "inputForm", sFeatures);
	inputForm.focus();
}
</script>
<style type="text/css">
<!--
.out_Line {
	margin: 1px;padding: 1px; height: 400px; border-width:1px; border-color:#B2BEC1; border-style: solid; overflow: auto;
}
-->
</style>
</head>
<body onload="page_load();">
<%-- 작업영역 --%>
<div style="margin: 70 0 0 20 ">
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12" colspan="2">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="성과급 기준 코드" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td style="width: 300px;">
			<div class="btNbox pb10 txtR">
				<a class="btN006bc6 txt2btn" href="javascript:btnDefine_Add();">추가</a>
				<a class="btNe14f42 txt2btn" href="javascript:btnDefine_Del();">삭제</a>
			</div>
		</td>
		<td>
			<div class="btNbox pb10 txtR">
				<a class="btN006bc6 txt2btn" href="javascript:btnData_Add();">추가</a>
				<a class="btNe14f42 txt2btn" href="javascript:btnData_Del();">삭제</a>	
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="out_Line" style="width: 300px;">
			<table class="tableStyle05" style="table-layout: fixed;">
				<thead>
					<tr>
						<td style="width:25px;padding:1px 1px">선택</td>
						<td>기준코드</td>
						<td>코드명칭</td>
					</tr>
				</thead>
				<form name="frmDefine" method="post">
				<tbody>
					<c:forEach var="code" items="${cmTableList}">
						<tr>
							<td style="text-align: center">
								<input type="checkbox" name="chkDefine" value="<c:out value="${code.tableName}"/>">
							</td>
							<td class="myoverflow">
								<a href="javascript:modify_define('<c:out value="${code.tableName}"/>');" title="<c:out value="${code.tableName}"/>"><c:out value="${code.tableName}"/></a>
							</td>
							<td class="myoverflow">
								<a href="javascript:getDataList('<c:out value="${code.tableName}"/>');" title="<c:out value="${code.tableDesc}"/>"><c:out value="${code.tableDesc}"/></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</form>
			</table>
			</div>
		</td>
		<td>
			<div class="out_Line" style=" width: 450px;">
			<table cellSpacing="0" cellspacing="0" width="100%" border="0">
				<tbody>
					<tr>
						<td class="detailTableTitle_center" style="width:100px;">기준코드</td>
						<td class="detailTableField_left" id="tableName"></td>
					</tr>
					<tr>
						<td class="detailTableTitle_center">코드명칭</td>
						<td class="detailTableField_left" id="tableDesc"></td>
					</tr>
				</tbody>
			</table>
			<br>
			<form name="frmData" method="post">
			<table class="tableStyle05">
				<colgroup>
					<col style="width: 30px; text-align: center;"/>
					<col style="width:    *;"/>
					<col style="width:    *;"/>
				</colgroup>
				<thead>
					<tr id="headList">
					</tr>
				</thead>
					
				<tbody id="dataList">

				</tbody>
			</table>
			</form>
			</div>
		</td>
	</tr>
</table>
</div>
</body>
</html>