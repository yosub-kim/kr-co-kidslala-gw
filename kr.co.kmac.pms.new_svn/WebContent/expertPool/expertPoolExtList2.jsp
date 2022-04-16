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

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

var myDate = new Date();
var nowYear = myDate.getFullYear();

function getAge(regno){
	var birthYear = regno.substring(0,2);
	var gbn       = regno.substring(6,7);
	var age = 0;
	if ((gbn == "1") || (gbn == "2")) {
		var age = parseInt(nowYear,10) - parseInt("19" + birthYear)+1;
	} else if ((gbn == "3") || (gbn == "4")) {
		var age = parseInt(nowYear,10) - parseInt("20" + birthYear)+1;
	} else {
		var age = "&nbsp;";
	}
	return age;	
}
function setSearchCondition(obj) {
	var svalue = obj.options[obj.selectedIndex].value;
	if (svalue == "3") {
		document.getElementById("jobClass").style.display = "";
		document.getElementById("runningDeptCode").style.display = "none";
		document.getElementById("keyword").style.width = "62%";
		document.getElementById("keyword").style.display = "none";
		document.getElementById("keyword").value = "";
		//document.getElementById("keyword").disabled = true;
		$('specialFieldTr').show();
		toggleSpecialField(document.getElementById("jobClass").value);
	} else if (svalue == "5") {
		document.getElementById("runningDeptCode").style.display = "";
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("keyword").style.width = "62%";
		document.getElementById("keyword").style.display = "none";
		document.getElementById("keyword").value = "";
		//document.getElementById("keyword").disabled = true;
		$('specialFieldTr').show();
		toggleSpecialField(document.getElementById("runningDeptCode").value);
	} else {
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("runningDeptCode").style.display = "none";
		document.getElementById("keyword").style.display = "";
		document.getElementById("keyword").style.width = "80%";
		//document.getElementById("keyword").disabled = false;
		$('specialFieldTr').hide();
	}
}
function Page_OnLoad() {
	if (document.getElementById("keyfield").value == "3") {
		document.getElementById("jobClass").style.display = "";
		document.getElementById("runningDeptCode").style.display = "none";
		document.getElementById("keyword").style.width = "62%";		
		document.getElementById("keyword").style.display = "none";
		document.getElementById("keyword").value = "";
		//document.getElementById("keyword").disabled = true;
		$('specialFieldTr').show();
		if($('selectedGroup').value != ''){
			changeSpecialField($('selectedGroup').value);
		}
		toggleSpecialField(document.getElementById("jobClass").value);
	} else if (document.getElementById("keyfield").value == "5") {
		document.getElementById("runningDeptCode").style.display = "";
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("keyword").style.width = "62%";		
		document.getElementById("keyword").style.display = "none";
		document.getElementById("keyword").value = "";
		//document.getElementById("keyword").disabled = true;
		$('specialFieldTr').show();
		if($('selectedGroup').value != ''){
			changeSpecialField($('selectedGroup').value);
		}
		toggleSpecialField(document.getElementById("runningDeptCode").value);
	} else {
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("runningDeptCode").style.display = "none";
		document.getElementById("keyword").style.display = "";
		document.getElementById("keyword").style.width = "80%";
		//document.getElementById("keyword").disabled = false;
		$('specialFieldTr').hide();
	}
}
function doSearch() {
	document.frm.pg.value="1";
	frm.submit();
}
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function changeSpecialField(val){
	AjaxRequest.get(
		{
			'url':"/action/ExpertPoolSpecialFieldAction.do?mode=searchSpecialField&deptId="+val
			, 'anotherParameter':'false'
			, 'onSuccess':function(obj){   
	        	
	           	var divObj = $('specialFieldListDiv');
	        	var specialFieldHtml = "";
	           	var specialFieldList = eval('(' + obj.responseText + ')').specialField;
	           	specialFieldList.each(function(specialField) {
	           		if(($('specialFields').value).indexOf(specialField.specialId) > 0){
		           		specialFieldHtml += "<input type='checkbox' name='specialField' value='"+specialField.specialId+"' checked>";
	           		}else{
		           		specialFieldHtml += "<input type='checkbox' name='specialField' value='"+specialField.specialId+"' >";	           			
	           		}
	           		specialFieldHtml += specialField.specialName + " &nbsp;&nbsp; ";
	    		});
	           	divObj.innerHTML = specialFieldHtml;
			}
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
				alert("데이터를 가져오지 못 했습니다.");
			}
		});
}
function toggleSpecialField(value){
	if(value == 'J' || value == 'C' || value == 'F'){
		$('specialFieldTr').show();
	}else{
		$('specialFieldTr').hide();
	}
}
function goPage(pageNumber){
	frm.pg.value = pageNumber;
	frm.submit();
}
function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveExpertPoolListToExcel';
	surl += "&jobClass=" + document.frm.jobClass.value;
	//surl += "&name=" + document.frm.name.value;
	document.location = surl;
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<form name="frm">
<%-- 작업영역 --%>
<form name="frm">

	<!-- location -->
	<div class="location">
		<p class="menu_title">인력관리</p>
		<ul>
			<li class="home">HOME</li>
			<li>경영관리</li>
			<li>인력관리</li>
			<li>인력 POOL</li>
		</ul>
	</div>
	
	<div class="contents sub"><!-- 서브 페이지 .sub -->
			<!-- 검색 창 -->					
			<div class="box">
				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>구분</p>
								<SELECT  name='jobClass' id='jobClass' style='width: 100%;' class='select'>
									<option value="" <c:if test="${jobClass == '' }">selected</c:if>>전체</option>
									<option value="A" <c:if test="${jobClass=='A'}">selected</c:if>>상근</option>
									<option value="J" <c:if test="${jobClass=='J'}">selected</c:if>>상임</option>
									<option value="N" <c:if test="${jobClass=='H2'}">selected</c:if>>RA</option>							
								</SELECT>
							</div>
							<div class="label_group">
								<p>부서</p>
								<org:divList enabled="1" depth="2" attribute=" name='dept' id='dept' style='width: 49%' class='select'" 
									divType="A" all="Y" isSelectBox="Y" selectedInfo="${dept}"></org:divList>
								<input type="text" name="deptName"  id="deptName"  style="width: 49%;" value="<c:out value="${deptName}"/>" onKeyPress="if(event.keyCode==13) {doSearch();}">
							</div>
						</div>
						<div class="select_box">
							<div class="label_group">
								<p>성명</p>
								<input type="text" name="name"  id="name" value="<c:out value="${name}"/>" onKeyPress="if(event.keyCode==13) {doSearch();}">
							</div>
							<div class="label_group">
								<p>회사명</p>
								<input type="text" name="company"  id="company"  value="<c:out value="${company}"/>" onKeyPress="if(event.keyCode==13) {doSearch();}">
							</div>
						</div>
					</div>
					<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색<i class="mdi mdi-magnify"></i></button></div>
				</div>
			</div>
			
			<div class="box">
				<div class="a-both total">
					<p>총<span><c:out value="${totalNumberOfEntries}"/></span>건</p>
					<div class="btn_area">
						<%if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){ %>
						<button type="button" class="btn line btn_excel" onclick="saveListToExcel();"><i class="mdi mdi-microsoft-excel"></i>EXCEL 다운&nbsp</button>&nbsp
						<%} %>
						<button type="button" class="btn btn_pink" onclick="location.href='/action/ExpertPoolManagerAction.do?mode=loadFormExpertPool'">인력 등록</button>
					</div>
				</div>
				
				<div class="tbl-wrap sc">
					<table id="projectProgressTable" class="tbl-list all"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 10%">
								<col style="Width: 14%">
								<col style="width: 14%">
								<col style="Width: 14%">
								<col style="Width: 14%">
								<col style="Width: 14%">
								<col style="Width: *%">
							</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th class="subject">성명</th>
								<th>소속</th>
								<th>직위</th>
								<th>부서</th>
								<th>연락처</th>
								<th>이메일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="rs" items="${list}">
							<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;">
								<td><c:choose>
									<c:when test="${rs.jobClass eq 'A' }" >상근</c:when>
									<c:when test="${rs.jobClass eq 'B' }" >상근</c:when>
									<c:when test="${rs.jobClass eq 'J' }" >상임</c:when>
									<c:when test="${rs.jobClass eq 'N' }" >RA</c:when>
								</c:choose></td>
								<td><a href='#'><c:out value="${rs.name}" /></a></td>
								<td><c:out value="${rs.company}"/></td>
								<td><c:out value="${rs.companyPositionName}"/></td>
								<td><c:out value="${rs.deptName}" escapeXml="false"/></td>
								<td><c:out value="${rs.companyTelNo }" /></td>
								<td><c:out value="${rs.email}"/></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<div class="paging_area">
						<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml2( 
									<c:out value="${pagingPage}" default="1"/>, 
									10, 
									<c:out value="${totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
						</SCRIPT>
					</div>
				</div>
			</div>
			<%-- Hidden 필드 시작 --%>
				<input type="hidden" name="mode" id="mode" value="getExpertPoolExtList2">
				<input type="hidden" name="specialFields" id="specialFields" value="<c:out value="${specialFields}"/>">
				<input type="hidden" name="keyfield" id="keyfield" value="<c:out value="${keyfield}"/>">
				<input type="hidden" name="keyword"  id="keyword"  value="<c:out value="${keyword}"/>">
				<input type="hidden" name="pg"       id="pg"       value="<c:out value="${pagingPage}"/>">
				<input type="hidden" name="name"       id="name"       value="<c:out value="${name}"/>">
				<input type="hidden" name="deptName"       id="deptName"       value="<c:out value="${deptName}"/>">
				<input type="hidden" name="company"       id="company"       value="<c:out value="${company}"/>">
				<input type="hidden" name="runningDeptCode"       id="runningDeptCode"       value="<c:out value="${runningDeptCode}"/>">
				<%-- Hidden 필드 종료 --%>
		</div>
</form>
</body>
</html>