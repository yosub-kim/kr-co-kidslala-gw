<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Generator" content="EditPlus" charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=1600">
<%-- ====================== Renewal jQuery include jsp ============= --%>
<script type="text/javascript" src="/resources/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-ui.js"></script>
<script type="text/javascript" src="/resources/js/jquery.selectric.min.js"></script><!-- 셀렉트 박스 UI-->

<script type="text/javascript">
jQuery.noConflict();
var j$ = jQuery;
</script> 

<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" /><!-- 서브페이지에서만 사용 -->		
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" /><!-- 셀렉트 박스 UI -->
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<%--
<%@ include file="/common/include/includeJavaScript.jsp"%>
 --%>


<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
// 부서 코드 추가
<% String result = (String)session.getAttribute("dept"); %>
function wec_OnInitCompleted(){
	document.Wec.Value = frm.content.value;
}
function btnSave_onclick() {

	var result = '<c:out value="${bbsId}"/>';
	
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
	
	jQuery.ajax({
        type : 'post',
        url : '/action/BoardAction.do?mode=saveBoard',
        data : jQuery("form[name=frm]").serialize(),
        dataType : 'json',
        error: function(xhr, status, error){				
			alert("저장할 수 없습니다.");
        },
        success : function(json){
			location.href = "/action/BoardAction.do?mode=selectList&bbsId=<c:out value="${bbsId}" />&projectCode=<c:out value="${projectCode}"/>&projectName=<c:out value="${projectName}"/>";
			if(result == "qna"){
				/* window.open("http://localhost/pwalertqna.jsp"); */
				location.href("/pwalertqna.jsp");
				/* getAuthToken();  */
			}
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

//첨부파일 다운로드
function fileDownload1(uuid){
	fileDownload(uuid, '<c:out value="${param.log}"/>');
}

//첨부파일 다운로드
function fileDownload(uuid, log){
	if(uuid != ""){
		location.href = "/servlet/RepositoryDownLoadServlet?fileId=" + uuid;
	}
}


</script>
</head>
<body>
<form name="frm" method="post">
	<div class="location">
		<p class="menu_title"><c:choose><c:when test="${projectCode != '' }" ><c:out value="${projectName}" /></c:when><c:otherwise><code:code tableName="StandardBBS_Master"  code="${bbsId}"/></c:otherwise></c:choose></p>
		<ul>
			<li class="home">HOME</li>
			<li><c:choose><c:when test="${bbsId eq 'templatebbs' }">업무지원</c:when><c:otherwise>게시판</c:otherwise></c:choose></li>
			<li>
			<c:choose>
				<c:when test="${projectCode != ''}">
					<td width="100%" align="left"><span class="mainTitle"><c:out value="${projectName}"/></span></td>
				</c:when>
				<c:otherwise>
					<td width="100%" align="left"><span class="mainTitle"><code:code tableName="StandardBBS_Master"  code="${bbsId}"/></span></td>
				</c:otherwise>
			</c:choose>		
			</li>
		</ul>
	</div>
	<!-- // location -->
	
	<div class="contents sub"><!-- 서브 페이지 .sub -->
		<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>" />
		
		<div class="fixed_box">
			<div class="title">
				<div class="h1_area">
					<p class="h1"><i class="mdi mdi-file-document-outline"></i>내용 작성</p>
				</div>
				<div class="btn_area">
					<button type="button" class="btn line btn_blue" onclick="location.href='javascript:btnSave_onclick();'"><i class="mdi mdi-content-save-outline"></i>저장</button>
					<button type="button" class="btn line btn_grey" onclick="location.href='javascript:history.back()'"><i class="mdi mdi-backburger"></i>목록</button>
				</div>
			</div>
				
					
			<div class="fixed_contents sc">
				<!-- Table Write-->	
				<table class="tbl-view write"><!-- 내용 작성 .write -->
					<thead>
						<colgroup>
							<col style="width: 11%">
							<col style="Width: 39%">
							<col style="width: 11%">
							<col style="Width: 39%">
						</colgroup>
						<tr>
							<th><label for="subject">제목</label></th>
							<c:choose><c:when test="${projectCode != '' }" ><td></c:when><c:otherwise><td colspan="3"></c:otherwise></c:choose>
							<input type="text" name="subject" class="subject" value="<c:out value="${boardData.subject}" />" /></td>
							<input type="hidden" name="saveMode" value="<c:out value="${saveMode}"/>">
							<input type="hidden" name="bbsId" value="<c:out value="${bbsId}"/>">
							<input type="hidden" name="seq"   value="<c:out value="${boardData.seq}"/>">
							<input type="hidden" name="ref"   value="<c:out value="${boardData.ref}"/>">
							<input type="hidden" name="step"  value="<c:out value="${boardData.step}"/>">
							<input type="hidden" name="lev"   value="<c:out value="${boardData.lev}"/>">	
							
							<c:if test="${projectCode != ''}">
								<th><div style="height: 8px"></div><label for="subject">외근일</label>&nbsp<input type="checkbox" name="refSchedule" value="Y" id="Y" onclick="enableWorkDate(this)" class="btn_check" checked><label for="Y"></label>
								</th>
								<td colspan="3">
									<div id="workDateDiv">
										<fmt:parseDate value="${workDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
										<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="workDateVal"/>
										<script>
											jQuery(function(){jQuery( "#workDate" ).datepicker({});});
										</script>
										<input type="text" name="workDate" id="workDate" readonly="readonly" value="<c:out value="${workDateVal}" />"/>
								</div>			
									</div>
								</td>
									</c:if>		
								</tr>
						<tr>
							<c:choose><c:when test="${bbsId eq 'publicNotice' }">
								<th><label for="subject">최상위글</label></th>
								<td style="vertical-align:middle;" colspan="3">
									<ul class="chek_ui" style="padding: 8 0 0 0;"><li>
										<input type="checkbox" class="btn_check"  name="topArticle" id="Y" value="Y" <c:if test="${boardData.topArticle=='Y'}">checked</c:if> <c:if test="${modeText=='답글'}">disabled</c:if>><label for="Y"></label> 체크 시 최상위글로 표시
									</li></ul>
								</td>
								<%-- <th><label for="subject">공개범위</label></th>							
								<td>
									<ul class="chek_ui" style="padding: 8 0 0 0;">
									<li><input type="radio" name="prjType" id="1" value="1" class="btn_radio" <c:if test="${boardData.prjType=='1'}">checked</c:if> checked>전체<label for="1"></label></li>
					              	<li><input type="radio" name="prjType" id="2" value="2" class="btn_radio" <c:if test="${boardData.prjType=='2'}">checked</c:if>>상근<label for="2"></label></li>
					              	<li><input type="radio" name="prjType" id="3" value="3" class="btn_radio" <c:if test="${boardData.prjType=='3'}">checked</c:if>>상임<label for="3"></label></li>
									</ul>
								</td> --%>
							</c:when>
							<c:when test="${projectCode != ''}"> </c:when>
							<c:otherwise>
								<th><label for="subject">최상위글</label></th>
								<td colspan="3">
									<ul class="chek_ui" style="padding: 8 0 0 0;"><li>
									<input type="checkbox" class="btn_check" name="topArticle" id="Y" value="Y" <c:if test="${boardData.topArticle=='Y'}">checked</c:if> <c:if test="${modeText=='답글'}">disabled</c:if>><label for="Y"></label> 체크 시 최상위글로 표시
									</li></ul>
								</td>
							</c:otherwise></c:choose>
						</tr>
						
						<tr class="file">
							<th><label for="file">첨부파일</label></th>
							<td colspan="3" style="padding: 0 5 0 5 ">
								<jsp:include page="/common/repository/fileUpload.jsp"><jsp:param value="Y" name="log"  /></jsp:include>
							</td>
						</tr>
						
					</thead>												
					<tbody>
						<tr>
							<td colspan="4" style="padding: 0 1% 0 1% ">
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
				<!-- // Table Write-->
			</div>		
		</div>	
		<!-- // fixed box -->
	</div>
</form>
</body>
</html>		