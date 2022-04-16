<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

</script>
</head>

<body>
	<div style="display: none;">
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}" />"  />
	</div> 
	
	<div id="tab_menu_content">
		<div class="board_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1">프로젝트 VOC 현황</p>
				</div>
			</div>
		
		<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 10%"/>
							<col style="width: 15%"/>
							<col style="width: 15%" />
							<col style="width: 15%" />
							<col style="width: 15%" />
							<col style="width: 15%" />
							<col style="width: 15%" />
						</colgroup>
						<tr>
							<th class="detailTableField_center">번호</th>
							<th class="detailTableField_center">발송 예정일</th>
							<th class="detailTableField_center">발송일</th>
							<th class="detailTableField_center">수신일</th>
							<th class="detailTableField_center">응답일</th>
							<th class="detailTableField_center">응답자</th>
							<th class="detailTableField_center">내용</th>												
						</tr>
						<c:if test="${!empty vocList}">
							<c:forEach var="rs" items="${vocList}">
										<tr>
										<fmt:parseDate value="${rs.requestDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data1"/>
												<fmt:formatDate value="${data1}" pattern="yyyy-MM-dd" var="requestDate"/>
										
												<fmt:parseDate value="${rs.sendDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data2"/>
												<fmt:formatDate value="${data2}" pattern="yyyy-MM-dd" var="sendDate"/>
											
												<fmt:parseDate value="${rs.receiveDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data3"/>
												<fmt:formatDate value="${data3}" pattern="yyyy-MM-dd" var="receiveDate"/>
											
												<fmt:parseDate value="${rs.responseDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="data4"/>
												<fmt:formatDate value="${data4}" pattern="yyyy-MM-dd" var="responseDate"/>
											
												<td align="center"><c:out value="${rs.rownum}"/></td>
												<td align="center"><c:out value="${requestDate}"/></td>
												<td align="center"><c:out value="${sendDate}"/></td>
												<td align="center"><c:out value="${receiveDate}"/></td>
												<td align="center"><c:out value="${responseDate}"/></td>
												<td align="center"><c:out value="${rs.receiveName}"/></td>
												<td align="center">
													<c:if test="${rs.responseDate != null}">
														<img alt="VOC내용보기" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="openVocContent('<c:out value="${rs.requestDate}"/>', '<c:out value="${rs.projectCode}"/>')">
													</c:if>
											
										</tr>
							</c:forEach>
						</c:if>
									<c:if test="${empty vocList}">
										<td align="center" colspan="7">등록된 정보가 없습니다.</td>
									</c:if>
						</tr>
					</table>
				</div>
			</div>
		</div>
	
</body>
</html>