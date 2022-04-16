<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>투입 제한 인력</title>
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
var myDate = new Date();
var nowYear = myDate.getFullYear();
function goPage(next_page) {
	document.restrictUserListForm.pageNo.value = next_page ;
	document.restrictUserListForm.target = "";		
	document.restrictUserListForm.action = "/action/ExpertPoolManagerAction.do?mode=getRestrictExpertPoolList";
	document.restrictUserListForm.submit();
}
function doSearch() {
	document.restrictUserListForm.target = "";		
	document.restrictUserListForm.action = "/action/ExpertPoolManagerAction.do?mode=getRestrictExpertPoolList";
	document.restrictUserListForm.submit();
}
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function getAge(regno){
	var birthYear = regno.substring(0,2);
	var gbn       = regno.substring(6,7);
	var age = 0;
	if ((gbn == "1") || (gbn == "2")) {
		var age = parseInt(nowYear,10) - parseInt("19" + birthYear);
	} else if ((gbn == "3") || (gbn == "4")) {
		var age = parseInt(nowYear,10) - parseInt("20" + birthYear);
	} else {
		var age = "&nbsp;";
	}
	return age;	
}
function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveRestrictExpertPoolListToExcel';
	surl += "&restrictUser=" + document.restrictUserListForm.restrictUser.value;
	surl += "&jobClass=" + document.restrictUserListForm.jobClass.value;
	surl += "&name=" + document.restrictUserListForm.name.value;
	document.location = surl;
}
function restrictClose(){
	$('restirctInfo').hide();
}
function restrictShow(ssn){
	document.restrictUserListForm.ssn.value=ssn;
	
	$('restirctInfo').style.top = window.event.clientY;
	$('restirctInfo').style.left = 455;
	$('restirctInfo').show();
}
function restirctRequest() {
	var sFrm = document.forms["restrictUserListForm"];
	var status = AjaxRequest.submit(
			sFrm, 
			{	'url': "/action/ExpertPoolManagerAction.do?mode=setRestrictUserList",
				'onSuccess':function(obj){
					alert('투입 제한 여부를 설정하였습니다.');
					doSearch();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("execution Fail");
				}
			}
	); status = null;	
}
</script> 
</head>


<body>
	<form name="restrictUserListForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo" value="<c:out value="${list.valueListInfo.pagingPage}"/>"> 
			<input type="hidden" name="ssn" value=""/>"> 
		</div>
		<!-- location -->
		<div class="location">
			<p class="menu_title">투입제한인력(관)</p>
			<ul>
				<li class="home">HOME</li>
				<li>경영관리</li>
				<li>인력관리</li>
				<li>투입제한인력(관)</li>
			</ul>
		</div>
		
		<div class="contents sub"><!-- 서브 페이지 .sub -->
			<!-- 검색 창 -->					
			<div class="box">

				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>투입제한여부</p>
								<select name='restrictUser' class='select'">
									<option value="Y" <c:if test="${restrictUser=='Y'}">selected</c:if>>예</option>
									<option value="N" <c:if test="${restrictUser=='N'}">selected</c:if>>아니오</option>
								</select>
							</div>
							<div class="label_group">
								<p>구분</p>
								<code:codelist tableName="EMP_WORKING_TYPE" isSelectBox="Y" all="Y" selectedInfo="${jobClass}" 
									attribute=" name='jobClass' class='select' "/>
							</div>
							<div class="label_group">
								<p>이름</p>
								<input type="text" name="name" value="<c:out value="${name}"/>" class="textInput_left" onkeypress="if(event.keyCode == 13) doSearch();">
							</div>
						</div>
					</div>
					<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색<i class="mdi mdi-magnify"></i></button></div>
				</div>
			</div>
			
			<div class="box">
				<div class="a-both total">
					<p>총<span><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>건</p>
				</div>
				
				<div class="tbl-wrap sc">
					<table  id="projectProgressTable" class="tbl-list all"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 10%">
							<col style="Width: 10%">
							<col style="width: 10%">
							<col style="Width: 10%">
							<col style="Width: 10%">
							<col style="Width: *%">
							<col style="Width: 10%">
						</colgroup>
						<thead>
							<tr>
								<th>성명</th>
								<th>부서</th>
								<th>연령</th>
								<th>성별</th>
								<th>전문분야</th>
								<th>제한사유</th>
								<th>제한설정</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
							<c:when test="${!empty list.list}">
								<c:forEach var="rs" items="${list.list}">
									<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
										<td onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;"><c:out value="${rs.name}" /></td>
										<td><c:out value="${rs.deptName}" escapeXml="false"/></td>
										<td><script>document.write(getAge('<c:out value="${rs.userId}" />'));</script></td>
										<td align="center" width="40px">
											<c:choose>
												<c:when test="${rs.gender == '0'}">F</c:when>
												<c:otherwise>M</c:otherwise>
											</c:choose>
										</td>
										<td>&nbsp;<c:out value="${rs.consultingMajor}" escapeXml="false"/></td>
										<td><c:out value="${rs.restrictContents}" escapeXml="false" /></td>
										<td>
											<img src="/images/main_ti_icon<c:if test="${restrictUser =='N'}">02</c:if>.jpg" alt="제한설정" style="cursor: hand;" onclick="restrictShow('<c:out value="${rs.ssn}" />');" width="18" height="18" border="0">
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr><td colspan="7">검색된 데이터가 없습니다.</td></tr>
							</c:otherwise>
						</c:choose>
					</tbody>
					</table>
					<div class="paging_area">
						<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml2( 
									<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
									10, 
									<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
						</SCRIPT>
					</div>
				</div>
			</div>
		</div>
		<div id="restirctInfo" style="position: absolute; background: white; display: none; width: 400px; border: solid 1px #EFEFEF; margin-left: 37%; ">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">제한설정</p>
				</div>
				<div class="select_box">
					<button type="button" class="btn line btn_pink" onclick="restirctRequest()"><i class="mdi mdi-content-save-outline"></i>저장</button>
					<button type="button" class="btn line btn_blue" onclick="restrictClose()"><i class="mdi mdi-close-box-outline"></i>닫기</button>
				</div>
			</div>
			<div style="margin: 10 10 10 10;">
				<div class="board_contents">
					<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 40%"/>
							<col style="width: 60%"/>
						</colgroup>
						<tr>
							<th>제한하기</th>
							<td>
								<input type="radio" class="btn_radio" name="restrictYn" id="restrictYn_Y" value="Y" <c:if test="${restrictUser=='Y'}">checked</c:if>><label for="restrictYn_Y"></label>예&nbsp;&nbsp;&nbsp;
								<input type="radio" class="btn_radio"  name="restrictYn" id="restrictYn_N" value="N" <c:if test="${restrictUser=='N'}">checked</c:if>><label for="restrictYn_N"></label>아니오
							</td>
						</tr>
						<tr>
							<th>사유</th>
							<td><input type="text" name="restrictContents" style="width:100%;" /></td>
						</tr>
					</table>
				</div>
			</div>
	</div>
			
			<table style="" cellpadding="3" cellspacing="3">
				<tr>
					<td><h4 class="subTitle">투입 제한 설정</h4></td>
				</tr>
				<tr>
					<td><table id="restrictInfoTable" width="290px">
						<tbody id="restrictInfoTableBody">
							<tr>
								<td class="detailTableTitle_center" width="30%">제한하기</td>
								<td class="detailTableField_left" width="*">
									<input type="radio" name="restrictYn" value="Y" <c:if test="${restrictUser=='N'}">checked</c:if>>예&nbsp;&nbsp;&nbsp;
									<input type="radio" name="restrictYn" value="N" <c:if test="${restrictUser=='Y'}">checked</c:if>>아니오
								</td>
							</tr>
							<tr>
								<td class="detailTableTitle_center" width="30%">사유</td>
								<td class="detailTableField_left" width="*">
									<input type="text" name="restrictContents" style="width:100%;" />
								</td>
							</tr>
						</tbody>
					</table></td>
				</tr>
				<tr>
					<td height="36">
						<div class="btNbox txtR">
							<a class="btNe006bc6 txt2btn" href="#" onclick="restirctRequest()">등록</a>
							<a class="btNa0a0a0 txt2btn" href="#" onclick="restrictClose()">닫기</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>