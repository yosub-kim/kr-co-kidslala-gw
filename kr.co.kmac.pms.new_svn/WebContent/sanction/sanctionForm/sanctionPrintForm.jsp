<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
	window.onload = function() {
		window.print();
	}
</script>
</head>

<body>
<form name="generalSanctionForm" method="post" enctype="multipart/form-data">
	<div id="hiddneValue" style="display: none;">
		<input type="text" name="taskId" id="taskId" value="<c:out value="${sanctionDoc.taskId}" />" />
		<input type="text" name="approvalType" value="<c:out value="${sanctionDoc.approvalType}" />" />
		<input type="text" name="projectCode" value="<c:out value="${sanctionDoc.projectCode}" />" />
		<input type="text" name="seq" id="seq" value="<c:out value="${sanctionDoc.seq}" />" /> 
		<input type="text" name="state" id="state" value="<c:out value="${sanctionDoc.state}" />" />
	</div>


		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="기안하기 (전자결재)" />
						<jsp:param name="backButtonYN" value="N" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td height="10"></td>
			</tr>		
		
 			<%@include file="/sanction/common/sanctionLineInfo.jsp" %>
 			<tr>
 				<td>
		 			<table>
		 				<tr>
		 					<td></td>
		 				</tr>
		 			</table>			 
 				</td>
			</tr>						
							
			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<h4 class="subTitle">기안 내용</h4>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 		
										
				
			<tr>
				<td>		
					<table cellpadding="0" cellspacing="0">
						<c:if test="${projectCode != null && projectCode != ''}">
							<tr>
								<td width="100" class="detailTableTitle_center">프로젝트명</td>
								<td width="665" class="detailTableField_left">[<c:out value="${projectCode}"/>] <c:out value="${projectName}"/></td>
							</tr>
						</c:if>
						<tr>
							<td width="100" class="detailTableTitle_center">제목</td>
							<td width="665" class="detailTableField_left"><c:out value="${sanctionDoc.subject}"/></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">품의 내용</td>
							<td width="665" class="detailTableField_left">
								<textarea name="content" id="content" class="textArea" 
								style="width: 100%; height: 300px; overflow:visible" readonly><c:out value="${sanctionDoc.content}" /></textarea>
							</td>
						</tr>
					</table>
				</td>
			</tr>				
			<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
		</table>					
	</form>
</body>
</html>
