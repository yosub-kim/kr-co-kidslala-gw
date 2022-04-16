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
<link rel="stylesheet" href="/resources/css/selectric.css"
	type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function modify_click(idx) {
	document.location = "/action/MoudbAction.do?mode=loadForm&idx=" + idx;
}

function delete_click() {
	var msg = "정말 삭제 하시겠습니까?";
	if(confirm(msg)){
		var ActionURL = "/action/MoudbAction.do";	
		ActionURL += "?mode=removeMoudb";
		var sFrm = document.forms["frm"];
		

		var status = AjaxRequest.submit(
				sFrm
				,{ 'url':ActionURL
					,'onSuccess':function(obj){
						document.location = "/action/MoudbAction.do?mode=selectList";
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
	<form name="frm">

		<!-- location -->
		<div class="location">
			<p class="menu_title">MOU 내용 조회</p>
			<ul>
				<li class="home">HOME</li>
				<li>네트워크</li>
				<li>Infra 관리</li>
				<li>MOU 리스트</li>
			</ul>
		</div>
		<!-- // location -->
		<div style="display: none">
			<input type="hidden" name="idx" value="<c:out value="${moudbDetail.idx}"/>"> 
			<input type="hidden" name="com_name" value="<c:out value="${moudbDetail.com_name }"/>">
		</div>
		<div class="contents sub">
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">기관 기본 정보</p>
					</div>
					<div class="select_box">
						<button type="button" class="btn line btn_blue" onclick="modify_click(<c:out value="${moudbDetail.idx}"/>)"><i class="mdi mdi-clipboard-check-outline"></i>수정</button>
						<button type="button" class="btn line btn_pink" onclick="delete_click()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
					</div>
				</div>
				<div class="board_contents">
					<table id="projectProgressTable" class="tbl-edit">
						<!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%" />
							<col style="width: 35%" />
							<col style="width: 15%" />
							<col style="width: 35%" />
						</colgroup>
						<tr>
							<th>기관명</th>
							<td><c:out value="${moudbDetail.com_name}" /></td>
							<th>대표자</th>
							<td><c:out value="${moudbDetail.com_boss}" /></td>
						</tr>
						<tr>
							<th>국가(지역)</th>
							<td><c:out value="${moudbDetail.country}" /></td>
							<th>주소</th>
							<td><c:out value="${moudbDetail.com_addr}" /></td>
						</tr>
						<tr>
							<th>홈페이지</th>
							<td><c:out value="${moudbDetail.homepage}" /></td>
							<th>대표전화</th>
							<td><c:out value="${moudbDetail.com_tel}" /></td>
						</tr>
						<tr>
							<th>기관특성</th>
							<td><c:out value="${moudbDetail.com_special_quality}" /></td>
							<th>전문분야</th>
							<td><c:out value="${moudbDetail.com_expert}" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">KMAC 협력 정보</p>
					</div>
				</div>
				<div class="board_contents">
					<table id="projectProgressTable" class="tbl-edit">
						<!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%" />
							<col style="width: 50%" />
							<col style="width: 15%" />
							<col style="width: 20%" />
						</colgroup>
						<tr>
							<th>협력분야</th>
							<td><c:out value="${moudbDetail.com_cooperation}" /></td>
							<th>관계수준</th>
							<td><c:out value="${moudbDetail.relation_level}" /></td>
						</tr>
						<tr>
							<th>공동사업실적</th>
							<td colspan="3" style="white-space: pre-line;">
							<!-- <div class="reply_view"> -->
								<c:out value="${moudbDetail.join_project_result}" /></td>
								<%-- <p class="reply_text">
									<c:out value="${moudbDetail.join_project_result}" escapeXml="false" />
								</p>
							</div>
							</td> --%>
					</table>
				</div>
			</div>

			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">담당자 정보</p>
					</div>
				</div>
				<div class="board_contents">
					<table id="projectProgressTable" class="tbl-edit td-c">
						<!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 10%" />
							<col style="width: 10%" />
							<col style="width: 20%" />
							<col style="width: 20%" />
							<col style="width: 25%" />
							<col style="width: 15%" />
						</colgroup>
						<tr>
							<th>이름</th>
							<th>직책</th>
							<th>전화번호</th>
							<th>휴대폰</th>
							<th>메일</th>
							<th>담당분야</th>
						</tr>
						<c:choose>
							<c:when test="${!empty moudbDetail.contactList}">
								<c:forEach var="contact" items="${moudbDetail.contactList}" varStatus="i">
									<tr>
										<td><c:out value="${contact.contactName}" /></td>
										<td><c:out value="${contact.contactGrade}" /></td>
										<td><c:out value="${contact.contactTel}" /></td>
										<td><c:out value="${contact.contactMobile}" /></td>
										<td><c:out value="${contact.contactEmail}" /></td>
										<td><c:out value="${contact.contactJob}" /></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td style="text-align:center;" colspan="6">등록된 담당자가 없습니다.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
			</div>

			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">MOU 파일 정보</p>
					</div>
				</div>
				<div class="board_contents">
					<table id="projectProgressTable" class="tbl-edit">
						<!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%" />
							<col style="width: *" />
						</colgroup>
						<tr>
							<th>첨부파일</th>
							<td><jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log" /></jsp:include></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>