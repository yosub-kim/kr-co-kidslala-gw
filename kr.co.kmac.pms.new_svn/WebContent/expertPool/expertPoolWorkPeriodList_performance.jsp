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
<form name="expenseResultListForm" method="post">
	
		<div class="location">
			<p class="menu_title">인사평가 결과</p>
			<ul>
				<li class="home">HOME</li>
				<li>인사평가 결과</li>
			</ul>
		</div>
		<!-- // location -->
		
			<!-- contents sub -->
	<div class="contents sub">
		<div class="board_box">
			<div class="title_both" style="background-color: #006699;">
				<div class="h1_area" style="background-color: #006699;">
					<p class="h1" style="background-color: #006699; color: #fff;">결과 공유 및 피드백 운영</p>
				</div>
				<!-- <div class="select_box">
				<button type="button" class="btn line btn_blue" onclick="refresh()"><i class="mdi mdi-clipboard-check-outline"></i>데이터 가져오기</button>
				</div> -->
			</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 12%" />
								<col style="width: 12%" />
								<col style="width: 15%" />
								<!-- <col style="width: 12%" /> -->
								<col style="width: *%" />
							</colgroup>
							<tr>
								<th style="background-color: #006699; color: #fff;">피평가자</th>
								<th style="background-color: #006699; color: #fff;">직위/직책</th>
								<th style="background-color: #006699; color: #fff;">평가</th>
								<!-- <th style="background-color: #006699; color: #fff;">종합등급</th> -->
								<th style="background-color: #006699; color: #fff;">주요 평가의견</th>
							</tr>
							<tbody id="ListData">
					<c:set var="ssnChk" value="" />
					<c:forEach var="rs" items="${list.list}" varStatus="status">
						<tr>
							<c:if test="${rs.ssn != ssnChk }">
								<td rowspan="<c:out value="${rs.ssnCnt}"/>" ><c:out value="${rs.name }" /></td>
							</c:if>
							<c:if test="${rs.ssn != ssnChk }">
								<td rowspan="<c:out value="${rs.ssnCnt}"/>" ><c:out value="${rs.companyPositionName }" /></td>
							</c:if>
							<td style="height: 200px"><c:out value="${rs.evalContent }" /><c:if test="${status.index == '1' }"><div style="font-size: 13px;">(리더십 / 팔로우십 <br>/ 공통역량)</div></c:if></td>
							<td><textarea disabled name="content" id="content" class="textArea" style="width: 100%; height: 80%;"><c:out value="${rs.contents }" /></textarea></td>
							<%-- <td><c:out value="${rs.grade }" /></td> --%>
						</tr>
 						<c:set var="ssnChk" value="${rs.ssn}" />
					</c:forEach>
					<c:if test="${empty list.list}"><tr><td colspan='4' align='center'><br>검색 결과가 존재하지 않습니다.<br><br></td></tr></c:if>
					</tbody>
				</table>
			</div>
		</div>
		<%-- <div class="pass_check">
			<p>※ 위 등급은 역량평가 및 실적을 바탕으로 산출한 '계산등급' 입니다.</p>
			<p>※ 역량평가와 업적평가 결과 합산 및 인사평가심의를 통해 '최종 인사평가등급'이 산출됩니다.</p>
		</div> 
		<div style="padding: 0 0 10 0"></div>
		<div class="board_box">
			<div class="title_both" style="background-color: #006699;">
				<div class="h1_area" style="background-color: #006699;">
					<p class="h1" style="background-color: #006699; color: #fff;">종합인사평가 등급</p>
				</div>
			</div>
		
			<div class="a-both stretch">
				<div>
					<div class="board_contents">
						<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 33%" />
								<col style="width: 33%" />
								<col style="width: 33%" />
							</colgroup>
							<tr>
								<th style="background-color: #006699; color: #fff;">2019년</th>
								<th style="background-color: #006699; color: #fff;">2020년</th>
								<th style="background-color: #006699; color: #fff;">2021년</th>
							</tr>
							<tbody id="ListData">
								<tr>
									<c:forEach var="rs" items="${list2.list}" varStatus="status">
										<c:if test="${status.index < 3 }">
											<td style="height: 50px">
												<c:out value="${rs.grade } "/><br>
												
												<!-- -2 -->
												<c:if test="${status.index == 0 }">
													<c:choose>
														<c:when test="${status.index == 0 && rs.grade == '-' }">
														</c:when>
														<c:otherwise>
															(20%)
														</c:otherwise>
													</c:choose>
												</c:if>
												
												<!-- -1 -->
												<c:if test="${status.index == 1 }">
													<c:choose>
														<c:when test="${status.index == 1 && rs.grade == '-' }">
														</c:when>
														<c:when test="${gradeChk == '-' }">
															(50%)
														</c:when>
														<c:otherwise>
															(30%)
														</c:otherwise>
													</c:choose>
												</c:if>
												<!-- 올해 -->
												<c:if test="${status.index == 2 }">
													<c:choose>
														<c:when test="${status.index == 2 && rs.grade == '-' }">
														</c:when>
														<c:when test="${gradeChk == '-' }">
															(100%)
														</c:when>
														<c:otherwise>
															(50%)
														</c:otherwise>
													</c:choose>
												</c:if>
											</td>
										</c:if>
										<!-- chk -->
										<c:set var="gradeChk" value="${rs.grade}"/>
									</c:forEach>
								</tr>
								<c:if test="${empty list2.list}"><tr><td colspan='3' align='center'><br>검색 결과가 존재하지 않습니다.<br><br></td></tr></c:if>
							</tbody>
						</table>
					</div>
				</div>
				<div>
					<div class="board_contents">
						<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: *" />
							</colgroup>
							<tr>
								<th style="background-color: #006699; color: #fff;">2021년 인사평가 최종등급</th>
							</tr>
							<tbody id="ListData">
								<tr>
									<c:forEach var="rs" items="${list2.list}" varStatus="status">
										<c:if test="${status.index == 3 }">
											<td style="height: 50px"><c:out value="${rs.grade } "/></td>
										</c:if>
									</c:forEach>
								</tr>
								<c:if test="${empty list2.list}"><tr><td colspan='1' align='center'><br>검색 결과가 존재하지 않습니다.<br><br></td></tr></c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div> --%>
		</div>
	</div>
</div>
	
	
</form>
</body>
</html>