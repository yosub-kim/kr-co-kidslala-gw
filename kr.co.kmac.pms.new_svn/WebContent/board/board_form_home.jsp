<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%-- 공통js,css include --%>
<%@page import="org.acegisecurity.BadCredentialsException"%>
<%@page import="org.acegisecurity.LockedException"%>

<%-- ====================== Renewal jQuery include jsp ============= --%>
<script type="text/javascript" src="/resources/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.selectric.min.js"></script><!-- 셀렉트 박스 UI-->

<script type="text/javascript">
jQuery.noConflict();
var j$ = jQuery;
</script> 

<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" /><!-- 서브페이지에서만 사용 -->		
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" /><!-- 셀렉트 박스 UI -->
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">

<!-- Q&A 문자 발송 추가 코드  (수정할 것)-->
<!-- <script src="http://code.jquery.com/jquery-latest.js"></script>  -->

<script type="text/javascript">

<%-- 개별 스크립트 영역 --%>
<!-- Q&A 문자 발송 추가 코드  -->

// 부서 코드 추가
<% String result = (String)session.getAttribute("dept"); %>
function wec_OnInitCompleted(){
	document.Wec.Value = frm.content.value;
}
function btnSave_onclick() {

	var result = '<c:out value="${bbsId}"/>';
	var writerId = '<c:out value="${writerId}"/>';
	
	var today = new Date();
	var sFrm = document.frm;
	if(sFrm.subject.value == ""){
		alert("제목을 입력하세요.");
		sFrm.subject.focus();
		return;
	}
	
	if(sFrm.projectCode.value != '' && sFrm.refSchedule.checked && document.getElementById("workDate").value == '') {
		alert("외근 날짜를 선택하세요.");
		sFrm.workDate.focus();
		return;
	}
	
	if(sFrm.projectCode.value != '' && sFrm.refSchedule.checked && document.getElementById("workDate").value != '' 
			&& dateToYYYYMMDD(today) < document.getElementById("workDate").value) {
		alert("외근일을 오늘(" + dateToYYYYMMDD(today) +") 이후로 미리 지정할 수 없습니다.");
		sFrm.workDate.focus();
		return;
	}
	
	// 에디터의 내용이 textarea에 적용됩니다.
	//oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
	sFrm.content.value = jQuery('#summernote').summernote('code');
	
	// 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("ir1").value를 이용해서 처리하면 됩니다.
	if(sFrm.content.value == ""){
		alert("내용을 입력하세요.");
		return;
	}
	var ActionURL = "/action/BoardAction.do";
	
	jQuery.ajax({
        type : 'post',
        url : '/action/BoardAction.do?mode=saveBoard',
        data : jQuery("form[name=frm]").serialize(),
        dataType : 'json',
        error: function(xhr, status, error){				
			alert("저장할 수 없습니다.");
        },
        success : function(json){
			location.href = "/action/BoardAction.do?mode=selectList_home&bbsId=<c:out value="${bbsId}" />&writerId=<c:out value="${writerId}"/>";
        }
    });
}
function enableWorkDate(obj) {
	
	if (obj.checked) {
		document.getElementById("workDateDiv").style.display="";	// set visiable div layer 
	} else {
		document.getElementById("workDate").value="";
		document.getElementById("workDateDiv").style.display="none";
	}
		
}
function dateToYYYYMMDD(date){
    function pad(num) {
        num = num + '';
        return num.length < 2 ? '0' + num : num;
    }
    return date.getFullYear() + '-' + pad(date.getMonth()+1) + '-' + pad(date.getDate());
}
function btnCancel_onclick(){
	history.back();
}
</script>
</head>
<body onload="">

	<form name="frm" method="post">
	<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>" />
	
	<%-- location --%>
	<div id="sub_location">
		<div class="location">
			<p class="menu_title">
				<td width="100%" align="left">재택근무 업무보고서</td>
			</p>
			<ul>
				<li class="home">HOME</li>
				<li>스케줄 관리</li>
				<li>재택근무 업무보고서</li>
			</ul>
		</div>
	</div>
	<!-- // location -->
	
<div class="contents sub"><!-- 서브 페이지 .sub -->
		<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>" />
		<input type="hidden" name="topArticle" value="Y" />
		
		<div class="fixed_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1"><i class="mdi mdi-file-document-outline"></i>내용 작성</p>
				</div>
				<div class="btn_area">
					<button type="button" class="btn line btn_blue" onclick="location.href='javascript:btnSave_onclick();'"><i class="mdi mdi-content-save-outline"></i>저장</button>
					<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back();'"><i class="mdi mdi-backburger"></i>목록</button>
				</div>
			</div>
			
			<div class="fixed_contents sc">
				<!-- Table Write-->	
				<table class="tbl-view write"><!-- 내용 작성 .write -->
					<thead>
						<tr>
							<th><label for="subject">제목</label></th>
							<td><input type="text" name="subject" value="<c:out value="${boardData.subject}"/>" class="textInput_left" style="width: 100%;">
								<input type="hidden" name="saveMode" value="<c:out value="${saveMode}"/>">
								<input type="hidden" name="bbsId" value="<c:out value="${bbsId}"/>">
								<input type="hidden" name="seq"   value="<c:out value="${boardData.seq}"/>">
								<input type="hidden" name="ref"   value="<c:out value="${boardData.ref}"/>">
								<input type="hidden" name="step"  value="<c:out value="${boardData.step}"/>">
								<input type="hidden" name="lev"   value="<c:out value="${boardData.lev}"/>">
							</td>
						</tr>
						<tr class="file">
							<th><label for="file">첨부파일</label></th>
							<td style="padding: 0 2% 0 2% ">
								<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"  /></jsp:include>
							</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="2" style="padding: 0 2% 0 2% ">
								<!-- 에이터 영역 .text_editor -->
								<div class="text_editor sc">
								<%--
									<textarea name="content" id="content" style="display:none;"><c:out value="${boardData.content}"/></textarea>
								 --%>
									<textarea id="summernote" name="content"><c:out value="${boardData.content}"/></textarea>
										<!-- 웹에디터 시작-->
										<%@ include file="/common/edit2/webEditor2.jsp"%>
										<!--웹에디터 끝 -->
								</div>
								<!-- // 에이터 영역 .text_editor -->												
							</td>
						</tr>		
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>
<!-- // location -->
</body>
</html>