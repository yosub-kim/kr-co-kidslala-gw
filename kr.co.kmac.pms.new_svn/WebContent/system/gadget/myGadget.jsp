<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<style type="text/css">
<!--
.out_Line {
	margin: 1px;padding: 1px; height: 320px; width: 100%; border-width:1px; border-color:#B2BEC1; border-style: solid; overflow: auto;
}
-->
</style>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function gadget_Append(){
	document.location = "/action/GadgetAction.do?mode=gadgetForm";
}

function appendMyGadget() {
	var sFrm = document.forms["frm2"];
	var ActionURL = "/action/GadgetAction.do";	
	ActionURL += "?mode=configMyGadget";
	
	var checkedCount = 0;
	for(var i = 0; i < sFrm.elements.length; i++){
		var ele = sFrm.elements[i];
		if((ele.type == "checkbox")&&(ele.name=="gadgetId")){
			if(ele.checked){
				checkedCount ++;
			}
		} 
	}
	if(checkedCount == 0) {
		alert("추가할 가젯을 선택해주세요.");
		return false;
	}
	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					alert("저장하였습니다.");
					document.location.reload();
					//document.location="/action/GadgetAction.do?mode=myGadgetConfig";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
}
function deleteMyGadget() {
	var sFrm = document.forms["frm"];
	var ActionURL = "/action/GadgetAction.do";	
	ActionURL += "?mode=configMyGadget";
	
	var checkedCount = 0;
	for(var i = 0; i < sFrm.elements.length; i++){
		var ele = sFrm.elements[i];
		if((ele.type == "checkbox")&&(ele.name=="gadgetId")){
			if(ele.checked){
				checkedCount ++;
			}
		} 
	}
	if(checkedCount == 0) {
		alert("삭제할 가젯을 선택해주세요.");
		return false;
	}
	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					alert("저장하였습니다.");
					document.location.reload();
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;	
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td align="center">
			<table>
				<tr>
					<td>
						<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
							<tr>
								
								<td width="100%" align="left" valign="bottom"><span class="subTitle">&nbsp;가젯 리스트</span></td>
							</tr>
						</table>
						<div class="out_Line">
							<table cellSpacing="0" cellpadding="0" width="100%" class="listTable" style="table-layout:fixed">
								<thead>
									<tr height="25px">
										<td width="32px">선택</td>
										<td width="*">Gadget 명</td>
										<!--  <td width="*">LinkURL</td> -->
										<td width="32px">정렬</td>
									</tr>
								</thead>
								<form name="frm2" method="post">
									<input type="hidden" name="saveMode" value="INSERT">
								<tbody>
									<c:forEach var="gadget" items="${gadgetList}" varStatus="i"> 
										<tr>
											<td >
												<c:choose>
													<c:when test="${ myGadget.fixedYN == 'Y' }">&nbsp;</c:when>
													<c:otherwise><input type="checkbox" name="gadgetId" value="<c:out value="${ gadget.gadgetId }"/>"></c:otherwise>
												</c:choose>
											</td>
											<td class="detailTableField_left"><c:out value="${gadget.gadgetName}"/></td>
											<!--  <td class="detailTableField_left" nowrap><c:out value="${gadget.linkUrl}" escapeXml="false"/></td> -->
											<td align='center'><c:out value="${gadget.ordSeq}"/></td>
										</tr>
									</c:forEach>
								</tbody>
								</form>
							</table>
						</div>
					</td>
					<td valign="middle" align="center">
				    	<a href="#" onclick="appendMyGadget();"><img src="/images/btn_right_arrow.gif" alt="추가" class="IMGBTN"></a>
				    	<br><br>
				    	<a href="#" onclick="deleteMyGadget();"><img src="/images/btn_left_arrow.gif" alt="삭제" class="IMGBTN"></a>
					</td>
					<td>
						<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
							<tr>
								
								<td width="100%" align="left" valign="bottom"><span class="subTitle">&nbsp;나의 가젯</span></td>
							</tr>
						</table>
						<div class="out_Line">
							<table cellSpacing="0" cellpadding="0" width="100%" class="listTable" style="table-layout:fixed">
								<thead>
									<tr height="25px">
										<td width="32px">선택</td>
										<td width="*">Gadget 명</td>
										<!-- <td width="*">LinkURL</td> -->
										<td width="32px">정렬</td>
									</tr>
								</thead>
								<form name="frm" method="post">
									<input type="hidden" name="saveMode" value="DELETE">
								<tbody>
									<c:forEach var="gadget" items="${myGadgetList}" varStatus="i"> 
										<tr>
											<td >
												<c:choose>
													<c:when test="${ gadget.fixedYN == 'Y' }">&nbsp;</c:when>
													<c:otherwise><input type="checkbox" name="gadgetId" value="<c:out value="${ gadget.gadgetId }"/>"></c:otherwise>
												</c:choose>
											</td>
											<td class="detailTableField_left"><c:out value="${gadget.gadgetName}"/></td>
											<!-- <td class="detailTableField_left" nowrap><c:out value="${gadget.linkUrl}" escapeXml="false"/></td> -->
											<td align='center'>
												<c:out value="${gadget.ordSeq}"/>
												<input type="hidden" name="mygadgetId" value="<c:out value="${ gadget.gadgetId }"/>">
												<input type="hidden" name="ordSeq"   value="<c:out value="${ gadget.ordSeq   }"/>">
											</td>
										</tr>
									</c:forEach>
								</tbody>
								</form>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>