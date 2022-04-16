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

</script>
</head>
<body>
<%@ include file="/common/include/includeBodyTop.jsp"%>
<%-- 작업영역 --%>
<div id="PageFull">
<table width='765' cellpadding="0" cellspacing="0">
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td class="Title">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="진행정보 History" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td>
			<table width="765" height="20" border="0" cellpadding="0" cellspacing="0">
				<tr><td height='10'></td></tr>
				<tr>
					
					<td width="100%" align="left" valign="bottom">&nbsp;<span class="subTitle">고객정보 현황</span></td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td height='3'></td>
	</tr>
	<tr>
		<td align="center">
			<table cellSpacing="0" cellpadding="0" width="100%" class="listTable" style="table-layout:fixed;">
				<thead>
				<tr>
					<td width="30px">순서</td>
					<td width="200px">제목</td>
					<td width="70px">조직단위</td>
					<td width="80px">비즈니스타입</td>
					<td width="80px">고객사</td>
					<td width="80px">작성자</td>
					<td width="80px">작성일</td>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${!empty result1.list}">
					<c:forEach var="list1" items="${result1.list}" varStatus="i">
						<tr customerCode="<c:out value="${list1.customerCode}"/>">
							<td align='center'><c:out value="${list1.rownum}"/></td>
							<td class="col_Body_Button"  style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; ">
								<a title="<c:out value="${list1.subject}"/>" 
								   href="#">
								   <c:out value="${list1.subject}"/>
								</a>
							</td>
							<td align='center'><c:out value="${list1.runningDiv}"/></td>
							<td align='center'><c:out value="${list1.businessType}"/></td>
							<td align='center'  style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; "><c:out value="${list1.customerName}"/></td>
							<td align='center'><c:out value="${list1.writerName}"/></td>
							<td align='center'>
								<fmt:parseDate value="${list1.writeDate}" var="dateFmt" pattern="yyyy-mm-dd"/>
									<fmt:formatDate value="${dateFmt}" pattern="yyyy-mm-dd"/>
							</td>
						</tr>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td align="center" colspan="7">연계된 고객정보가 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>	
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
		<td>
			<table width="765" height="20" border="0" cellpadding="0" cellspacing="0">
				<tr><td height="10"></td></tr>
				<tr>
					
					<td width="100%" align="left" valign="bottom">&nbsp;<span class="subTitle">프로젝트 현황</span></td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td height='3'></td>
	</tr>
	<tr>
		<td align="center">
			<table cellSpacing="0" cellpadding="0" width="100%" class="listTable" style="table-layout:fixed;">
				<thead>
				<tr>				
					<td width="60px">순서</td>
					<td width="80px">비즈니스타입</td>
					<td width="80px">조직단위</td>
					<td width="*">프로젝트명</td>
					<td width="100px">진행상태</td>
					<td width="100px">등록자</td>
				</tr>
				</thead>
				<tbody>
				<c:choose>
				<c:when test="${!empty result2.list}">
					<c:forEach var="list2" items="${result2.list}" varStatus="i">
					<tr projectCode="<c:out value="${list2.projectCode}"/>">
						<!--  
						businessTypeCode : <c:out value="${list2.businessTypeCode}"/>
						businessType     : <c:out value="${list2.businessType}"/>
						runningDivCode   : <c:out value="${list2.runningDivCode}"/>
						runningDiv       : <c:out value="${list2.runningDiv}"/>
						projectName      : <c:out value="${list2.projectName}" escapeXml="false"/>
						projectStateName : <c:out value="${list2.projectStateName}"/>
						pmSsn            : <c:out value="${list2.pmSsn}"/>
						pmName           : <c:out value="${list2.pmName}"/>
						-->
						<td align="center"><c:out value="${list2.rownum}"/></td>
						<td align="center"><c:out value="${list2.businessType}"/></td>
						<td align="center"><c:out value="${list2.runningDiv}"/></td>
						<td style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; "><c:out value="${list2.projectName}" escapeXml="false"/></td>
						<td align="center"><c:out value="${list2.projectStateName}"/></td>
						<td align="center"><c:out value="${list2.pmName}"/></td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td align="center" colspan="6">연계된 프로젝가 없습니다.</td></tr>
				</c:otherwise>
				</c:choose>
				</tbody>
			</table>				
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	<tr>
	<tr>
		<td>
			<table width="765" height="20" border="0" cellpadding="0" cellspacing="0">
				<tr><td height='10'></td></tr>
				<tr>
					
					<td width="100%" align="left" valign="bottom">&nbsp;<span class="subTitle">인력 현황</span></td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td height='3'></td>
	</tr>
	<tr>
		<td align="center">
			<table cellSpacing="0" cellpadding="0" width="100%" class="listTable" style="table-layout:fixed;">
				<colgroup>
					<col width="60px"/>
					<col width="100px"/>
					<col width="*"/>
					<col width="100px"/>
					<col width="100px"/>
				</colgroup>
				<thead>
				<tr>
					<td>순서</td>
					<td>이름</td>
					<td>소속기관</td>
					<td>등록자</td>
					<td>등록일</td>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${!empty result3.list}">
					<c:forEach var="list3" items="${result3.list}">
					<%--
						rownum      : <c:out value="${list3.rownum}"/>          
						ssn         : <c:out value="${list3.ssn}"/>                
						name        : <c:out value="${list3.name}"/>              
						companyId   : <c:out value="${list3.companyId}"/>    
						company     : <c:out value="${list3.company}"/>
						createrId   : <c:out value="${list3.createrId}"/>    
						createrName : <c:out value="${list3.createrName}"/>
						createdDate : <c:out value="${list3.createdDate}"/>
					--%>
					<tr>
						<td align="center"><c:out value="${list3.rownum}"/></td>
						<td align="center" class="col_Body_Button">
							<a title="<c:out value="${list3.name}"/>"
								href="#">
								<c:out value="${list3.name}"/>
							</a>
						</td>
						<td ><c:out value="${list3.company}" escapeXml="false"/></td>
						<td align="center"><c:out value="${list3.createrName}"/></td>
						<td align="center">
							<fmt:parseDate value="${list3.createdDate}" var="dateFmt" pattern="yyyy-mm-dd"/>
								<fmt:formatDate value="${dateFmt}" pattern="yyyy-mm-dd"/>
						</td>
					</tr>
					</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="5" align="center">등록된 인력이 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>				
		</td>
	</tr>
	<tr>
		<td height='7'></td>
	</tr>
	
</table>
</div>
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>