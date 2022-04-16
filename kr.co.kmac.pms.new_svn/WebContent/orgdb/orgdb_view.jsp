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

function modify_click(orgCode) {
	document.location = "/action/OrgdbAction.do?mode=loadForm&orgCode=" + orgCode;
}

function delete_click() {
	var msg = "정말 삭제 하시겠습니까?";
	if(confirm(msg)){
		var ActionURL = "/action/OrgdbAction.do";	
		ActionURL += "?mode=removeOrgdb";
		var sFrm = document.forms["frm"];
		

		var status = AjaxRequest.submit(
				sFrm
				,{ 'url':ActionURL
					,'onSuccess':function(obj){
						document.location = "/action/OrgdbAction.do?mode=selectList";
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){
						alert("삭제할 수 없습니다.");
					}
				}
		); status = null;
	}
}
function saveBoardComment() {
	var sFrm = document.frmCmmt;
	var ActionURL = "/action/BoardAction.do";	
	ActionURL += "?mode=saveBoardComment2";

	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				location.reload();
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				alert("저장할 수 없습니다.");
			}
		} 
	); status = null;	
}

function deleteBoardComment(objTR, bbsId, seq, commentSeq) {
	if(confirm("삭제 하시겠습니까?")) {
		var ActionURL = "/action/BoardAction.do";	
		ActionURL += "?mode=deleteBoardComment";
		ActionURL += "&bbsId="+bbsId;
		ActionURL += "&seq="+seq;
		ActionURL += "&commentSeq="+commentSeq;

		var status = AjaxRequest.get(
				{	'url': ActionURL,
					'async' : false,
					'anotherParameter':'false',
					'onSuccess':function(obj){  // 
			           	var res = eval('(' + obj.responseText + ')');
				           	$(objTR).up().up().remove();
					}, 
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("데이터를 가져오지 못했습니다.");
					}
				});
	}	
}
function orgdb_check(){
	var sFrm = document.frm;
	
	if(confirm("승인 하시겠습니까?"))
	{
		var ActionURL = "/action/OrgdbAction.do";	
		ActionURL += "?mode=orgdbCheck";
		var status = AjaxRequest.submit(
				sFrm
				,{ 'url':ActionURL
					,'onSuccess':function(obj){
						alert("승인하였습니다.");
						document.location = "/action/OrgdbAction.do?mode=selectList";
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){
						alert("[오류] 경영기획센터 김요섭(644) 문의");
					}
				}
		); status = null;
	}else{
		return;
	}	
}
function orgdb_check2(){
	var sFrm = document.frm;
	
	if(confirm("승인취소 하시겠습니까?"))
	{
		var ActionURL = "/action/OrgdbAction.do";	
		ActionURL += "?mode=orgdbCheck2";
		var status = AjaxRequest.submit(
				sFrm
				,{ 'url':ActionURL
					,'onSuccess':function(obj){
						alert("승인취소하였습니다.");
						document.location = "/action/OrgdbAction.do?mode=selectList";
					}
					,'onLoading':function(obj){}
					,'onError':function(obj){
						alert("[오류] 경영기획센터 김요섭(644) 문의");
					}
				}
		); status = null;
	}else{
		return;
	}	
}
</script>
</head>
<body>
		<form name="frm">
				
		<!-- location -->
		<div class="location">
			<p class="menu_title">협력사 등록 관리</p>
			<ul>
				<li class="home">HOME</li>
				<li>네트워크</li>
				<li>Infra 관리</li>
				<li>협력사 등록 관리</li>
			</ul>
		</div>
	<!-- // location -->
		<div style="display:none">
			<input type="hidden" name="orgCode" value="<c:out value="${orgdbDetail.orgCode}"/>">
			<input type="hidden" name="orgName" value="<c:out value="${orgdbDetail.orgName }"/>">
		</div>
		<div class="contents sub">
			<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">협력사 정보
					<% if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){ %>
					<button type="button" class="btn line btn_blue" onclick="orgdb_check()"><i class="mdi mdi-check-decagram"></i>승인</button>
					<button type="button" class="btn line btn_pink" onclick="orgdb_check2()"><i class="mdi mdi-check-decagram"></i>승인 취소</button>
					<%} %>
					</p>
				</div>
				<div class="select_box">
					<button type="button" class="btn line btn_blue" onclick="modify_click(<c:out value="${orgdbDetail.orgCode}"/>)"><i class="mdi mdi-clipboard-check-outline"></i>수정</button>
					<button type="button" class="btn line btn_pink" onclick="delete_click()"><i class="mdi mdi-trash-can-outline"></i>삭제</button>
				</div>
			</div>
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 15%"/>
								<col style="width: 21%"/>
								<col style="width: 15%" />
								<col style="width: 22%" />
								<col style="width: 15%" />
								<col style="width: 22%" />
							</colgroup>
							<tr>
								<th>회사명(사업자등록증)</th>
								<td><c:out value="${orgdbDetail.orgName}"/></td>
								<th>대표자명</th>
								<td><c:out value="${orgdbDetail.repName}"/></td>
								<th>전문분야</th>
								<td><c:out value="${orgdbDetail.relWithkmac1 }" /></td>										
							</tr>
							<tr>
								<th>대표 연락처</th>
								<td><c:out value="${orgdbDetail.telNo}"/></td>
								<th>FAX</th>
								<td><c:out value="${orgdbDetail.faxNo}"/></td>
								<th>홈페이지</th>
								<td><c:out value="${orgdbDetail.homepage}"/></td>										
							</tr>
							<tr>
								<th>주소</th>
								<td colspan="5"><c:out value="${orgdbDetail.zipCode}"/> <c:out value="${orgdbDetail.address1}"/> <c:out value="${orgdbDetail.address2}"/></td>								
							</tr>
							<tr>
								<th>담당역할 및 회사소개</th>
								<td colspan="5"><c:out value="${orgdbDetail.workComment}"/></td>									
							</tr>
					</table>
				</div>
			</div>
		</form>
		
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">담당자 정보</p>
				</div>
			</div>
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 10%"/>
								<col style="width: 15%" />
								<col style="width: 15%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
								<col style="width: 20%" />
							</colgroup>
							<tr>
								<th>순서</th>
								<th>담당자</th>
								<th>직위</th>
								<th>전화번호</th>
								<th>이메일</th>
								<th>담당 업무</th>
							</tr>
							<c:choose>
							<c:when test="${!empty orgdbDetail.contactList}">
								<c:forEach var="contact" items="${orgdbDetail.contactList}" varStatus="i">
									<tr>
										<td ><c:out value="${i.count}"/></td>
										<td ><c:out value="${contact.contactName}"/></td>
										<td title="<c:out value="${contact.contactPosition}"/>"><c:out value="${contact.contactPosition}"/></td>
										<td ><c:out value="${contact.contactTel}"/></td>
										<td ><c:out value="${contact.contactEmail}"/></td>
										<td><c:out value="${contact.contactWork }"/></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
									<tr><td align="center" colspan="6">연락 담당자가 없습니다.</td></tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
			</div>
			
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="h1">사업자 등록증, 통장사본, 회사 소개서/제안서</p>
					</div>
				</div>
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%" />
							<col style="width: *"/>
						</colgroup>
						<tr>
							<th>첨부파일</th>
							<td><jsp:include page="/common/repository/fileUpload_orgdb.jsp"><jsp:param value="Y" name="log"/></jsp:include></td>
						</tr>
					</table>
				</div>
			</div>
			
			<form name="frmCmmt" method="post" >
				<table class="tbl-view-reply">
					<thead>
						<tr>
							<td><i class="mdi mdi-reply"></i>댓글 <span><c:out value="${result.boardCommentDataListSize}" /></span></td>
						</tr>
					</thead>
							<tbody>
								<tr>
									<td class="reply_write_box">
										<div class="reply_write">
											<input type="hidden" name="seq" value="<c:out value="${orgdb}" />" />
											<textarea name="commentContents" class="sc" style="width:100%; height:100%;" maxlength="1000"></textarea>
											<button type="button" class="btn btn_blue" onclick="saveBoardComment()">등록</button>
										</div>
									</td>
								</tr>
								<c:if test="${!empty result.boardCommentDataList }">
									<c:forEach items="${result.boardCommentDataList }" var="item">
										<tr>
											<td>
												<div class="reply_view">
													<div class="a-both">
														<div class="wirter">
															<p class="name">
																<c:out value="${item.writer }" />
															</p>
															<p class="date">
																<c:out value="${item.writeTime }" />
															</p>
														</div>
													</div>
													<div class="btn_area">
														<!-- <button type="button" class="btn line btn_blue" onclick="location.href='javascript:;'"><i class="mdi mdi-square-edit-outline"></i>수정</button> -->
														<c:if test="${myUserId == item.writerId}">
															<button type="button" class="btn line btn_pink"
																onclick="location.href='javascript:deleteBoardComment(this, '<c:out value="${item.bbsId}"/>','<c:out value="${item.seq}"/>','<c:out value="${item.commentSeq}"/>');'">
																<i class="mdi mdi-trash-can-outline"></i>삭제
															</button>
														</c:if>
													</div>
													<div class="file_link">
														<input type="hidden" name="seq" value="<c:out value="${orgdb}" />">
													</div>
													<p class="reply_text">
														<c:out value="${item.content }" escapeXml="false" />
													</p>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
					</table>
					</form>
				<!-- // Table View Reply -->
		</div>
</body>
</html>