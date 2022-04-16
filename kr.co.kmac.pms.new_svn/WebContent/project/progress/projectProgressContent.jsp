<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">


<script type="text/javascript">
	
	function draftRequest(){
		var attachOutputType = $$('select[name="attachOutputType"]');
		var attachOutputName = $$('input[name="attachOutputName"]');
		for (var i = 0; i < attachOutputType.length; i++) {
			if(attachOutputType[i].value == ''){
				alert((i+1)+"번째 첨부파일 타입을 입력하세요.");
				return;
			}
		}
		for (var i = 0; i < attachOutputName.length; i++) {
			if(attachOutputName[i].value == ''){
				alert((i+1)+"번째 첨부파일 문서명을 입력하세요.");
				return;
			}
		}

		var sFrm = document.forms["progressCotentForm"];
		
		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/ProjectProgressManagementAction.do?mode=storeProjectProgessContent",
					'onSuccess':function(obj){
						var res = eval('(' + obj.responseText + ')');
						alert('저장하였습니다.');
						
			           	if(res.isFinished == true) {
							alert('우측하단의 평가/리뷰 버튼을 클릭하여 일정관리를 종료하세요.\n\n그 전까지 평가대기 프로젝트로 등록됩니다.');
			           	}
			           	
						opener.refreshGanttChart();
						window.close();
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;				 
	}
	
	window.onload=function(){
		layer_open(this, 'pop_register');
	}
</script>
</head>

<body>

<form name="progressCotentForm" method="post" enctype="multipart/form-data">
	<div id="hiddneValue" style="display: none;">
		<input type="text" name="projectCode" value="<c:out value="${projectProgressContent.projectCode}" />" />
		<input type="text" name="workSeq" value="<c:out value="${projectProgressContent.workSeq}" />"  />
		<input type="text" name="contentId" value="<c:out value="${projectProgressContent.contentId}" />"  />
	</div>
	<div id="pop_register" class="popup_layer pop_register">
		<!-- <div class="popup_bg"></div> -->
		<div class="popup_inner tbl-sc" style="width:800px; ">
			<div class="a-both">
				<p class="h1">액티비티 완료</p>			
			</div>
			<div class="popup_contents">
				<div class="sign_area">	
					<table  id="projectProgressTable" class="tbl-edit"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
								<col style="width: 20%"/>
								<col style="width: 80%"/>
						</colgroup>
						<tr>
							<th>제목</th>
							<td>
								<c:choose>
									<c:when test="${readOnly}">
										<c:out value="${projectProgressContent.title}" escapeXml="true"/>
									</c:when>
									<c:otherwise>
										<input type="text" name="title" class="contentInput_left" style="width: 100%" 
											 value="<c:out value="${projectProgressContent.title}"/>"></td>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>세부 내용</th>
							<td style="height:300px;"><textarea name="content" class="textArea" style="width: 100%; height: 98%; overflow: auto;" <c:if test="${readOnly}">readonly</c:if>><c:out value="${projectProgressContent.content}"/></textarea>
							</td>
						</tr>
						<tr>
							<th>산출물</th>
							<td>
							<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"/></jsp:include>
							</td>
						</tr>
					</table>
					</div>
				</div>
				<div class="btn_area">
					<button type="button" class="btn btn_blue" onclick="draftRequest();">저장</button>
					<button type="button" class="btn btn_pink" onclick="window.close();">취소</button>
				</div>
		</div>
	</div>


	<%-- 	<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<h4 class="mainTitle"><c:choose><c:when test="${readOnly}">수행 내용 보기</c:when><c:otherwise>수행 내용 등록</c:otherwise></c:choose></h4>	
				</td>
			</tr>
			
			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<h4 class="subTitle">수행 내용</h4>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 		
										
			<tr>
				<td>		
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100" class="detailTableTitle_center">제목</td>
							<td width="*" class="detailTableField_left">
								<c:choose>
									<c:when test="${readOnly}">
										<c:out value="${projectProgressContent.title}" escapeXml="true"/>
									</c:when>
									<c:otherwise>
										<input type="text" name="title" class="contentInput_left" style="width: 100%" 
											 value="<c:out value="${projectProgressContent.title}"/>"></td>
									</c:otherwise>
								</c:choose>
						</tr>
						<tr>
							<td class="detailTableTitle_center">수행 내용</td>
							<td class="detailTableField_left" style="height:250px"><textarea name="content" class="textArea" style="width: 100%; height: 98%;" 
									<c:if test="${readOnly}">
										readonly
									</c:if>><c:out value="${projectProgressContent.content}"/></textarea></td>
						</tr>
					</table>
				</td>
			</tr>				
			<tr>
				<td height="2"></td>
			</tr>				
			<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"/></jsp:include>
					

			<!-- 버튼부분-->
			<tr>
				<td height="10"></td>
			</tr>
			<tr>
				<td>
					<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="btNbox mb10 mt10 txtR">
										<a class="btNe006bc6 txt2btn "href="#" onclick="draftRequest()">저장</a>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 버튼종료-->		
		</table> --%>					
	</form>						
</body>
</html>
