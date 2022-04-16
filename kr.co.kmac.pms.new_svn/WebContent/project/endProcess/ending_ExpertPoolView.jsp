<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>참여 프로젝트 세부정보 및 평가</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
</script>
</head>
<body style="padding-left:5px; padding-right:5px">
<%-- 작업영역 --%>
	<table width="760" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">

					<!-- sub 타이틀 영역 시작--> 
					<tr>
						<td>
							<h4 class="subTitle">프로젝트 개요</td>
						</td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
			
					<!-- Hidden 필드 시작 -->
					<input type="hidden" name="projectCode" value="<c:out value="${projectSummaryData.projectCode}" />">
					<input type="hidden" name="endGubun" value="">
					<!-- Hidden 필드 종료 -->
					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0">
								<colgroup>
									<col width="120px"/>
									<col width="250px"/>
									<col width="120px"/>
									<col width="*"/> 
								</colgroup>
								<thead>
									<tr>
										<td class="detailTableTitle_center">프로젝트명</td>
										<td class="detailTableField_left"><c:out value="${projectSummaryData.projectName}" /></td>
										<td class="detailTableTitle_center">담당 CBO</td>
										<td class="detailTableField_left"><c:out value="${projectSummaryData.runningDivName}" escapeXml="false" /></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">고객사</td>
										<td class="detailTableField_left"><c:out value="${projectSummaryData.customerName}" /></td>
										<td class="detailTableTitle_center">컨택포인트</td>
										<td class="detailTableField_left"><c:out value="${projectSummaryData.customerContPName}" /></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">산업구분</td>
										<td class="detailTableField_left"><code:code code="${projectSummaryData.industryTypeCode}" /></td>
										<td class="detailTableTitle_center">추진기간</td>
											<fmt:parseDate value="${projectSummaryData.realStartDate}" pattern="yyyyMMdd" var="realStartDate" />
											<fmt:formatDate value="${realStartDate}" pattern="yyyy-MM-dd" dateStyle="short" var="formatDate1" />
											
											<fmt:parseDate value="${projectSummaryData.realEndDate}" pattern="yyyyMMdd" var="realEndDate" />
											<fmt:formatDate value="${realEndDate}" pattern="yyyy-MM-dd" dateStyle="short" var="formatDate2" />
										<td class="detailTableField_left"><c:out value="${formatDate1}" /> ~ <c:out value="${formatDate2}" /></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">비즈니스 타입</td>
										<td class="detailTableField_left"><code:code tableName="BUSINESS_TYPE_CODE" code="${projectSummaryData.businessTypeCode}" /></td>
										<td class="detailTableTitle_center">비즈니스 솔루션</td>
										<td class="detailTableField_left"><code:code code="${projectSummaryData.projectDetailCode}" /></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">PM</td>
										<td class="detailTableField_left"><expertPool:exportname ssn="${projectSummaryData.pmName}" /></td>
										<td class="detailTableTitle_center">PL</td>
										<td class="detailTableField_left"><expertPool:exportname ssn="${projectSummaryData.plName}" /></td>
									</tr>
									<tr>
										<td class="detailTableTitle_center">참여 컨설턴트</td>
										<td class="detailTableField_left" colspan="3"><c:out value="${projectSummaryData.memberName}" /></td>
									</tr>
								</thead>
							</table>
						</td>
					</tr>
					


				<c:if test="${projectSummaryData.projectState < '4'}">
					<!-- sub 타이틀 영역 시작--> 
					<tr>
						<td>
							<h4 class="subTitle pt5">프로젝트 진행 중</h4>
						</td>
					</tr>
					<!-- sub 타이틀 영역 종료-->
				</c:if>
				<c:if test="${projectSummaryData.projectState == '6'}">
					<!-- sub 타이틀 영역 시작--> 
					<tr>
						<td>
							<h4 class="subTitle pt5">프로젝트 리뷰 내용</h4>
						</td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
					
					
										
					<tr>
						<td>
							<table cellSpacing="0" cellpadding="0" width="100%" style="table-layout:fixed;">
								<colgroup>
									<col width="120px"/>
									<col width="*"/>
								</colgroup>
								<tbody>
									<tr height="120px">
										<td class="detailTableTitle_center"><c:out value="${objTitle.strA}" escapeXml="false"/></td>
										<td class="detailTableField_left">
											<textarea name="endBackground" style="width:100%; height: 97%;" class="textArea" readonly><c:out value="${ending.endBackground}" escapeXml="false"/></textarea>
										</td>
									</tr>
									<tr height="120px">
										<td class="detailTableTitle_center"><c:out value="${objTitle.strB}" escapeXml="false"/></td>
										<td class="detailTableField_left">
											<textarea name="endResult" style="width:100%; height: 97%;" class="textArea" readonly><c:out value="${ending.endResult}" escapeXml="false"/></textarea>
										</td>
									</tr>
									<tr height="120px">
										<td class="detailTableTitle_center"><c:out value="${objTitle.strC}" escapeXml="false"/></td>
										<td class="detailTableField_left">
											<textarea name="endReview" style="width:100%; height: 97%;" class="textArea" readonly><c:out value="${ending.endReview}" escapeXml="false"/></textarea>
										</td>
									</tr>
									<tr height="120px">
										<td class="detailTableTitle_center"><c:out value="${objTitle.strD}" escapeXml="false"/></td>
										<td class="detailTableField_left">
											<textarea name="endSuggestion" style="width:100%; height: 97%;" class="textArea" readonly><c:out value="${ending.endSuggestion}" escapeXml="false"/></textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</c:if>	

				<c:if test="${projectSummaryData.projectState > '4' && projectSummaryData.projectState < '7'}">
					<!-- sub 타이틀 영역 시작--> 
					<tr>
						<td>
							<h4 class="subTitle pt5">프로젝트 만족도 평가 </h4>
						</td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
					
					<tr>
						<td>
							<table class="tableStyle05">
								<thead>
									<tr>
										<td width="20%">전반적만족도</td>
										<td width="20%">추천의향</td>
										<td width="20%">산출물만족도</td>
										<td width="20%">계약내용준수</td>
										<td width="20%">평가</td>
									</tr>
								</thead>
								<tbody>
								<c:if test="${!empty projectRolling.list}">
									<c:forEach var="rs" items="${projectRolling.list}">
										<tr height="25px">
											<td align="center"><c:out value="${rs.answer3}"/></td>
											<td align="center"><c:out value="${rs.answer4}"/></td>
											<td align="center"><c:out value="${rs.answer5}"/></td>
											<td align="center"><c:out value="${rs.answer6}"/></td>
											<td align="center"><b><c:out value="${rs.estimateP}"/> 점</b></td>			
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty projectRolling.list}">
									<tr height="25px">
										<td colspan="5" align="center">프로젝트 만족도 평가 결과가 없습니다.</td>
									</tr>
								</c:if>
								</tbody>
							</table>
						</td>
					</tr>
					
					<!-- sub 타이틀 영역 시작--> 
					<tr>
						<td>
							<h4 class="subTitle pt5">컨설턴트 평가 </h4>
						</td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
					
					<tr>
						<td>
							<table class="tableStyle05">
								<thead>
									<tr>
										<td width="16%">고객요구평가</td>
										<td width="16%">전문성</td>
										<td width="16%">의사소통</td>
										<td width="16%">일정준수도</td>
										<td width="16%">종합만족도</td>
										<td width="*">평가</td>
									</tr>
								</thead>
								<tbody>
								<c:if test="${!empty projectRollingC.list}">
									<c:forEach var="rs" items="${projectRollingC.list}">
										<tr height="25px">
											<td align="center"><c:out value="${rs.answer8}"/></td>
											<td align="center"><c:out value="${rs.answer9}"/></td>
											<td align="center"><c:out value="${rs.answer10}"/></td>
											<td align="center"><c:out value="${rs.answer12}"/></td>
											<td align="center"><c:out value="${rs.answer13}"/></td>
											<td align="center"><b><c:out value="${rs.estimateC}"/> 점</b></td>			
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty projectRollingC.list}">
									<tr height="25px">
										<td colspan="6" align="center">컨설턴트 평가 결과가 없습니다.</td>
									</tr>
								</c:if>
								</tbody>
							</table>
						</td>
					</tr>
				</c:if>	
		<tr>
			<td height="10px"></td>
		</tr>
	</table>
</body>
</html>