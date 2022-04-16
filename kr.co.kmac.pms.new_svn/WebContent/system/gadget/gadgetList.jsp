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
function gadget_Append(){
	document.location = "/action/GadgetAction.do?mode=gadgetForm&role="+frm.role.value+"&keyword="+frm.keyword.value;
}
function doSearch() {
	//frm.submit();
	document.location = "/action/GadgetAction.do?mode=gadgetList&role="+frm.role.value+"&keyword="+frm.keyword.value;
}
</script>
</head>
<body>
<form name="frm" method="post">
<%-- 작업영역 --%>
<div style="margin: 70 0 0 20 ">
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="Gadget 리스트" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td>
			<%@ include file="/common/include/searchBox_Header.jsp"%>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; ">
				<colgroup> 
					<col width="90px">
					<col width="100px"> 
					<col width="90px">
					<col width="*"> 
				</colgroup>
				<tr> 
					<td class="searchTitle_center">Role</td>
					<td class="searchField_left">
						<select name="role" id="role" class="selectbox" style="width: 100%">
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
						</select>						
					</td>
					<td class="searchTitle_center">Gadget 명</td>
					<td class="searchField_left">
						<input type="text" name="keyword" id="keyword" class="textInput_left kor_mode" value="<c:out value="${keyword }"/>" style="width:100%">
					</td>
				</tr>
			</table>
			<%@ include file="/common/include/searchBox_Footer.jsp"%>
		</td>
	</tr>
	<tr>
		<td height="35px">
			<div class="btNbox mt15 txtR" style="padding-bottom: 10px">
				<a class="btNe14f42 txt4btn" href="#" onClick="gadget_Append();" title="Gadget 추가">추가</a>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center">
			<table class="listTable">
				<thead>
					<tr height="25px">
						<td width="6%">순서</td>
						<td width="20%">Role</td>
						<td width="*">Gadget 명</td>
						<td width="10%">정렬번호</td>
						<td width="12%">Gadget 유형</td>
						<td width="10%">사용</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="gadget" items="${gadgetList}" varStatus="i"> 
						<tr onmouseover="row_over(this)" onmouseout="row_out(this)" title="Gadget을 수정하려면 Gadget명을 클릭하세요.">
							<td><c:out value="${i.count}"/></td>
							<td>
								<select class="selectboxPopup" style="width: 100%" disabled>
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
								</select>
							</td>
							<td><a href="/action/GadgetAction.do?mode=gadgetForm&gadgetId=<c:out value="${gadget.gadgetId}" />&role=<c:out value="${role}" />&keyword=<c:out value="${keyword}" />"><c:out value="${gadget.gadgetName}"/></a></td>
							<td><c:out value="${gadget.ordSeq}"/></td>
							<td>
								<c:choose>
									<c:when test="${gadget.gadgetType == 'B'}">가운데</c:when>
									<c:otherwise>우측</c:otherwise>
								</c:choose>
							</td>
							<td align="center">
								<c:choose>
									<c:when test="${gadget.useYN == 'Y' }">사용함</c:when>
									<c:otherwise>사용안함</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div>
			
			</div>					
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>