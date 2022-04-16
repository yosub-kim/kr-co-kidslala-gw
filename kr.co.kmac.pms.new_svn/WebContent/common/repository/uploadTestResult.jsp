<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

</head> 

<body>
<%@ include file="/common/include/includeBodyTop.jsp"%>
<form name="_FORM" method="post"  enctype="multipart/form-data" action="<c:url value="/action/RepositoryTestAction.do?mode=uploadTestResult"/>">
	<table border="1">
		<tr>
	 		<td>
				<table id="attachFileTable" width="100%" border="1">
					<thead id="attachFileTableHeader">
						<tr>
							<td>필수여부</td>
							<td>첨부타입</td>
							<td>첨부문서명</td>
							<td>내려받기</td> 
						</tr>
					</thead>
					<c:if test="${!empty list}">
						<c:forEach var="rs" items="list">
							<tbody id="attachFileTableBody">
								<tr>
									<td><select name="attachIsEssential">
												<option value="0">필수</option>
												<option value="1">비필수</option>
											</select></td>
									<td><select name="attachOutputType">
												<option value="0">보고서</option>
												<option value="1">기타</option>
											</select></td>
									<td><c:out value="${rs.orginalFileName}"/> </td>
									<td>img</td>
								</tr>
							</tbody>
						</c:forEach>
					</c:if>
				</table>
	 		</td> 
	 	</tr>
	 	<tr>
			<td align="right"><input type="button" value="행추가" onclick="javascript:addRowAttachFile()"/><input type="button" value="행삭제" onclick="javascript:deleteRowAttachFile()"/></td>
		</tr>	
	</table>
	<br>
 <input type="submit">
</form>
 
 
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>

</html>					 