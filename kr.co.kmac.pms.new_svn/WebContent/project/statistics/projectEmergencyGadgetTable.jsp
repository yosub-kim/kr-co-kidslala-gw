<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSearch() {
}
</script>
</head>
 
<body>
	<form name="emergencyForm" method="post">
	<div style="display: none;">
	</div>
	<table width="210" cellpadding="0" cellspacing="0">
		<tr>
			<td width="210" valign="top">
				<table width="210" cellpadding="0" cellspacing="0"> 
					<tr>
						<td>
							<table width='210' cellpadding="0" cellspacing="0" class="listTable" >
								<colgroup>
									<col width="33%" align="center"> 
									<col width="34%" align="center"> 
									<col width="33%" align="center"> 
								</colgroup>
								<thead>
									<tr>
										<td>일정지연</td>
										<td>평가지연</td>
										<td>리뷰지연</td>
									</tr>
								</thead>
								<tbody> 
									<c:if test="${!empty list}">
										<c:forEach var="rs" items="${list.list}">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
												<td><a href="/action/ProjectSearchAction.do?mode=getProjectSearchList&businessTypeCode=<c:out value="${rs.key_1}"/>&ssn=<%= SessionUtils.getUsername(request)%>&delayState=R&projectState=3" target=""><c:out value="${rs.scheduleDelayCnt}"/></a></td>
												<td><a href="/action/ProjectSearchAction.do?mode=getProjectSearchList&businessTypeCode=<c:out value="${rs.key_1}"/>&ssn=<%= SessionUtils.getUsername(request)%>&delayState=ER&projectState=4" target=""><c:out value="${rs.evalDelayCnt}"/></a></td>
												<td><a href="/action/ProjectSearchAction.do?mode=getProjectSearchList&businessTypeCode=<c:out value="${rs.key_1}"/>&ssn=<%= SessionUtils.getUsername(request)%>&delayState=RR&projectState=5" target=""><c:out value="${rs.reviewDelayCnt}"/></a></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>						

	</form>
</body>

</html>					