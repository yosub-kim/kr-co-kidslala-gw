<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function doSubmit() {
	var ActionURL = "/action/GadgetAction.do";	
	ActionURL += "?mode=saveGadget";
	var sFrm = document.forms["frmGadget"];
	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					alert("등록하였습니다.");
					document.location = "/action/GadgetAction.do?mode=gadgetList&role=<c:out value="${role}"/>&keyword=<c:out value="${keyword}"/>";
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
	); status = null;
}

function doDelete() {
	if(confirm("삭제하시겠습니까?")) {
		var ActionURL = "/action/GadgetAction.do";	
		ActionURL += "?mode=saveGadget";
		var sFrm = document.forms["frmGadget"];
		sFrm.saveMode.value = "DELETE";
		var status = AjaxRequest.submit(
				sFrm
				,{ 'url':ActionURL
					,'onSuccess':function(obj){
						alert("삭제하였습니다.");
						document.location = "/action/GadgetAction.do?mode=gadgetList&role=<c:out value="${role}"/>&keyword=<c:out value="${keyword}"/>";
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){
						alert("삭제할 수 없습니다.");
					}
				}
		); status = null;
	}
}

</script>
</head>
<body>
<%-- 작업영역 --%>
<div style="margin: 70 0 0 20 ">
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="가젯 관리" />
				<jsp:param name="backButtonYN" value="Y"/>
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td align="center">
			<table cellSpacing="0" cellpadding="0" width="100%" style="table-layout:fixed;">
				<colgroup>
					<col style="width : 100px;"/>
					<col style="width : *;"/>
					<col style="width : 100px;"/>
					<col style="width : *"/>
					<col style="width : 100px;"/>
					<col style="width : *"/>
					<col style="width : 100px;"/>
					<col style="width : *"/>
				</colgroup>
				<form name="frmGadget" method="post">
					<input type="hidden" name="saveMode" value="<c:out value="${saveMode}"/>">
					<input type="hidden" name="role" value="<c:out value="${role}"/>">
					<input type="hidden" name="keyword" value="<c:out value="${keyword}"/>">
				<tr height="25px">
					<td class="detailTableTitle_center" width="100px">Role</td>
					<td class="detailTableField_left">
						<select name="gadgetRole" class="selectboxPopup" style="width:100%">
								<option value="" <c:if test="${role == ''}">selected</c:if>>전체</option>
							<option value="ROLE2006080115163639526" <c:if test="${role == 'ROLE2006080115163639526'}">selected</c:if>>CEO</option>
							<option value="ROLE2006080116025853557" <c:if test="${role == 'ROLE2006080116025853557'}">selected</c:if>>CCO</option>
							<option value="ROLE2006080116033211358" <c:if test="${role == 'ROLE2006080116033211358'}">selected</c:if>>CBO</option>
							<option value="ROLE2006080116041070759" <c:if test="${role == 'ROLE2006080116041070759'}">selected</c:if>>COO</option>
							<option value="ROLE17A73D54752" <c:if test="${role == 'ROLE17A73D54752' }">selected</c:if>>팀장</option>
							<option value="ROLE2006080116061636366" <c:if test="${role == 'ROLE2006080116061636366'}">selected</c:if>>상근</option>
							<option value="ROLE17404E902CA" <c:if test="${role == 'ROLE17404E902CA' }">selected</c:if>>상근(채용)</option>	
							<option value="ROLE179AB746279" <c:if test="${role == 'ROLE179AB746279' }">selected</c:if>>상근2/자문위원</option>
							<option value="ROLE125B1491C42" <c:if test="${role == 'ROLE125B1491C42'}">selected</c:if>>상임</option>	
							<option value="ROLE12EB7551DAA" <c:if test="${role == 'ROLE12EB7551DAA'}">selected</c:if>>RA</option>	
							<option value="ROLE2006080118352520784" <c:if test="${role == 'ROLE2006080118352520784'}">selected</c:if>>엑스퍼트</option>		
							<option value="ROLE2007062609404844612" <c:if test="${role == 'ROLE2007062609404844612'}">selected</c:if>>경영기획실</option>
							<option value="ROLE2006121122104979912" <c:if test="${role == 'ROLE2006121122104979912'}">selected</c:if>>경영기획실(인사)</option>
							<option value="ROLE12EE7071EC7" <c:if test="${role == 'ROLE12EE7071EC7'}">selected</c:if>>경영기획실(관리자)</option>
							<option value="ROLE2006050120451853989" <c:if test="${role == 'ROLE2006050120451853989'}">selected</c:if>>ALL_MENU</option>
							<option value="mobileA" <c:if test="${role == 'mobileA'}">selected</c:if>>mobile(상근)</option>
							<option value="mobileJ" <c:if test="${role == 'mobileJ'}">selected</c:if>>mobile(상임)</option>
							<option value="mobileN" <c:if test="${role == 'mobileN'}">selected</c:if>>mobile(RA)</option>
							<option value="ROLE18078999415" <c:if test="${role == 'ROLE18078999415'}">selected</c:if>>키즈라라</option>
						</select>
					</td>
					<td class="detailTableTitle_center">고정여부</td>
					<td class="detailTableField_left">
						<select name="fixedYN" class="selectboxPopup" style="width:100%" disabled="disabled">
							<option value="Y" <c:if test="${gadget.fixedYN == 'Y' }">selected</c:if>>고정</option>
							<option value="N" <c:if test="${gadget.fixedYN == 'N' }">selected</c:if>>비고정</option>
						</select>
					</td>
					<td class="detailTableTitle_center">정렬번호</td>
					<td class="detailTableField_left">
						<input type="text" name="ordSeq" value="<c:out value="${gadget.ordSeq}"/>" class="contentInput_left" style="ime-mode:disable;">
					</td>
					<td class="detailTableTitle_center">사용여부</td>
					<td class="detailTableField_left">
						<select name="useYN" class="selectboxPopup" style="width:100%">
							<option value="Y" <c:if test="${gadget.useYN == 'Y' }">selected</c:if>>사용</option>
							<option value="N" <c:if test="${gadget.useYN == 'N' }">selected</c:if>>사용안함</option>
						</select>
					</td>
				</tr>
				<tr>
					
					<td class="detailTableTitle_center">Gadget 명</td>
					<td class="detailTableField_left" colspan="3">
						<input type="text" name="gadgetName" value="<c:out value="${gadget.gadgetName}"/>" class="contentInput_left">
						<input type="hidden" name="gadgetId" value="<c:out value="${gadget.gadgetId}"/>">
					</td>
					
					<td class="detailTableTitle_center">Gadget 유형</td>
					<td class="detailTableField_left" colspan="3">
						<select name="gadgetType" class="selectboxPopup" style="width:100%">
							<option value="B" <c:if test="${gadget.gadgetType == 'B'}">selected</c:if>>가운데</option>
							<option value="A" <c:if test="${gadget.gadgetType == 'A'}">selected</c:if>>우측</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="detailTableTitle_center">Link URL</td>
					<td class="detailTableField_left" colspan="7">
						<input type="text" name="linkUrl" value="<c:out value="${gadget.linkUrl}"/>" class="contentInput_left eng_mode" size="129">
					</td>
				</tr>
				<tr height="350px">
					<td class="detailTableTitle_center" valign="top"><br>SQL Query</td>
					<td class="detailTableField_left" colspan="7">
						<textarea name="sqlText" class="textBoxN eng_mode" style="width:100%;height:100%;"><c:out value="${gadget.sqlText}"/></textarea>
					</td>
				</tr>
				<tr height="200px">
					<td class="detailTableTitle_center" valign="top"><br>Table Header</td>
					<td class="detailTableField_left" colspan="7">
						<textarea name="tableHeader" class="textBoxN eng_mode" style="width:100%;height:100%;"><c:out value="${gadget.tableHeader}" escapeXml="true"/></textarea>
					</td>
				</tr>
				</form>
			</table>
		</td>
	</tr>
	<tr>
		<td height="50px" align="center">
			<div class="btNbox txtC">
				<a class="btNe006bc6 txt2btn" href="javascript:doSubmit();">저장</a>
			<c:if test="${saveMode != 'INSERT' }">
				<a class="btNe14f42 txt2btn" href="javascript:doDelete();">삭제</a>
			</c:if>
				<a class="btNa0a0a0 txt2btn" href="javascript:history.back()">이전</a>
			</div>
		</td>
	</tr>
</table>
</div>
</body>
</html>