<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<%--<script type="text/javascript" src='<c:url value="/js/project.js"/>'></script>
include 해서 사용 할 경우 ie6에서 에러발생하므로 주석처리
--%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
</script>
</head>
<% 
	String result = null;
	String projecttypecode = null;
%>
<body>
	<form name="projectProgressForm" method="post">
		<div style="display: none;">
			<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
			<input type="text" name="viewMode" id="viewMode" value="<c:out value="${viewMode}"/>" >
			<input type="text" name="readOnly" id="readOnly" value="<c:out value="${readOnly}"/>" >
			
			<input type="text" name="showToolbar" id="showToolbar" value="<c:out value="${showToolbar}"/>" >
			<input type="text" name="contentEdit" id="contentEdit" value="<c:out value="${contentEdit}"/>" >
			<input type="text" name="contentShow" id="contentShow" value="<c:out value="${contentShow}"/>" >
			<input type="text" name="showEndDate" id="showEndDate" value="<c:out value="${showEndDate}"/>" >
		</div>
		<!-- 프로젝트 일정 관리 -->
		<div id="tab_menu_content">
			<div class="board_box">
				<div class="title">
					<div class="h1_area">
						<p class="h1">프로젝트 일정관리</p>
						<fmt:parseDate value="${project.realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="fromdate"/> 
						<fmt:formatDate value="${fromdate}" pattern="yyyy.MM.dd" var="formattedFrom"/>
						<fmt:parseDate value="${project.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="todate"/>
						<fmt:formatDate value="${todate}" pattern="yyyy.MM.dd" var="formattedTo"/>
						<p class="term">[프로젝트 기간: <span><c:out value="${formattedFrom}"/> ~ <c:out value="${formattedTo}"/></span>]</p>
						<c:if test="${showEndDate ne true }">
							<button type="button" class="btn line btn_blue" onclick="javaxcript:prgressAdd();"><i class="mdi mdi-square-edit-outline"></i>행 추가</button>
							<button type="button" class="btn line btn_pink" onclick="javascript:prgressDelete();"><i class="mdi mdi-trash-can-outline"></i>행 삭제</button>				
							<button type="button" class="btn line btn_blue" onclick="javascript:prgressSave();"><i class="mdi mdi-square-edit-outline"></i>일정저장</button>
						</c:if>	
					</div>
					<c:if test="${project.projectState== '3' && viewMode == 'myProject' && readOnly == 'false'}">
						<div class="text-R">
							<p>모든 액티비티를 진행하였으면 우측 버튼을 눌러 프로젝트 일정관리를 종료하십시오.</p>
							<%
								pageContext.setAttribute("result", result);
								pageContext.setAttribute("projecttypecode", projecttypecode);
							%>
							<c:choose>
									<c:when test="${empty result && projecttypecode eq 'MM'}"> 
										<button type="button" class="btn btn_blue" onclick="alert('미 작성 주간진척관리가 있어 평가단계로 넘어갈 수 없습니다.');'">프로젝트 평가/리뷰</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn_blue" onclick="completeProjectProcess();">프로젝트 평가/리뷰</button>
									</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</div>
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 12%"/>
							<col style="width: 12%"/>
							<col style="*" />
							<!-- <col style="width: 20%" /> -->
							<!-- <col style="width: 20%" /> -->
							<c:if test="${showEndDate eq true }">
								<col style="width: 20%" />
							</c:if>
						</colgroup>
						<tr>
							<th>선택</th>
							<th>순서</th>
							<th>세부 액티비티</th>
							<!-- <th class="detailTableField_center">시작일</th> -->
							<!-- <th class="detailTableField_center">액티비티 완료일(예정)</th> -->
							<c:if test="${showEndDate eq true }">
								<th>액티비티 완료 (완료일)</th>
							</c:if>															
						</tr>
						<c:choose>
							<c:when test="${!empty list}">
								<c:forEach var="item" items="${list}">
										<tr>
											<td>
												<ul>
													 <li>																					
														<input type="checkbox" name="projectProgressCheck" id="<c:out value="${item.workSeq }"/>" class="btn_check"  value="C"/>
														<label for="<c:out value="${item.workSeq }"/>"></label>
													</li>														
												</ul>
												<input type="hidden" id="workSeq" name="workSeq" value="<c:out value="${item.workSeq }"/>"/>
												<input type="hidden" id="contentId" name="contentId" value="<c:out value="${item.contentId }"/>"/>
											</td>
											<td>
												<input type="text" name="orderSeq" value="<c:out value="${item.orderSeq}"/>" />
											</td>
											<td>
												<input type="text" name="workName" value="<c:out value="${item.workName}"/>"/>
											</td>
											<%-- <td class="detailTableField_center">
												<div class="input_date">
													<input type="text" name="startDate" id="startDate_<c:out value="${item.workSeq}"/>" value="<c:out value="${item.startDate}"/>" />
													<!-- <button type="button" class="btn_datepicker"></button> -->
												</div>
											</td>
											<td class="detailTableField_center">
												<div class="input_date">
													<input type="text" name="endDate" id="endDate_<c:out value="${item.workSeq}"/>" <c:choose><c:when test="${rs.realStartDate == null}"> </c:when><c:otherwise><c:out value="${rs.realStartDate}" /></c:otherwise></c:choose>value="<c:out value="${item.endDate}"/>" style="width: 80%;" readonly="readonly"/>
												</div>
											</td> --%>
											<c:choose>
												<c:when test="${showEndDate eq true }">
													<td align="center">
														<c:if test="${project.projectState== '3' && viewMode == 'myProject' && readOnly == 'false'}">
															<button type="button" class="btn line btn_blue" onclick="javascript:openProjectProgressContent('<c:out value="${item.projectCode}"/>', '<c:out value="${item.contentId}"/>', '<c:out value="${item.workSeq}"/>', '<c:out value="${readOnly}"/>')"><i class="mdi mdi-square-edit-outline"></i>완료</button>
														</c:if>
													<c:choose><c:when test="${item.realEndDate ne '' }">( <c:out value="${item.realEndDate}"/> )</c:when><c:otherwise>( - )</c:otherwise></c:choose>
													</td>
												</c:when>
												<c:otherwise>
													<input type="hidden" id="realEndDate" name="realEndDate" value="<c:out value="${item.realEndDate}"/>" />
												</c:otherwise>
											</c:choose>
											<!-- <script>
												jQuery(function(){jQuery( "#startDate_<c:out value="${item.workSeq}"/>" ).datepicker({});});
												jQuery(function(){jQuery( "#endDate_<c:out value="${item.workSeq}"/>" ).datepicker({});});
											</script> -->
											<c:set var="resultreal" value="${item.writeDate}" />
											<c:set var="projectTypeCode" value="${item.projectTypeCode}" />
										</tr>
									</c:forEach>
								</c:when>
							</c:choose>
						</tr>
					</table>
				</div>
			</div>
			</div>
			<!-- // 프로젝트 일정 관리 -->					
	</form>
</body>
</html>					