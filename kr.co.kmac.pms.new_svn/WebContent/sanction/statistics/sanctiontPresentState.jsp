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
function goPage(next_page) {
	document.location.href = "/action/SanctiontPresentStateAction.do?mode=getAllSanctiontPresentState&pageNo="+next_page
		+"&approvalType="+$('approvalType').value
		+"&divCode="+$('divCode').value
		+"&prevDivCode="+$('prevDivCode').value
		+"&prevDivCode2="+$('prevDivCode2').value
		+"&prevDivCode3="+$('prevDivCode3').value
		+"&ing="+$('ing').value
		+"&isPrevDiv="+$('isPrevDiv').value
		+"&startDate="+$('startDate').value
		+"&endDate="+$('endDate').value
		+"&name="+encodeURIComponent($('name').value)
		+"&projectName="+encodeURIComponent($('projectName').value);
}
function doSearch() {
	if ($('startDate').value > $('endDate').value) {
		alert("종료일이 시작일보다 앞설 수 없습니다.");
		return;
	}
	
	if (document.getElementById("isPrevDiv").value == "A") {
		document.getElementById("prevDivCode3").value == "";
		document.getElementById("prevDivCode").value == "";
	} else if(document.getElementById("isPrevDiv").value == "B") {
		document.getElementById("prevDivCode2").value == "";
		document.getElementById("prevDivCode").value == "";
	} else if(document.getElementById("isPrevDiv").value == "C") {
		document.getElementById("prevDivCode2").value == "";
		document.getElementById("prevDivCode3").value == "";
	}
	
	document.sanctionPresentStateForm.target = "";		
	document.sanctionPresentStateForm.action = "/action/SanctiontPresentStateAction.do?mode=getAllSanctiontPresentState";
	document.sanctionPresentStateForm.submit();
}
function sanctionItemClick(projectCode, approvalType, seq, workType, woriTypeUrl) {
	document.sanctionPresentStateForm.target = "";		
	document.sanctionPresentStateForm.action = woriTypeUrl+"&readonly=true&projectCode="+projectCode+"&approvalType="+approvalType+"&seq="+seq;
	document.sanctionPresentStateForm.submit();
}
function Page_OnLoad() {
	if (document.getElementById("isPrevDiv").value == "A") {
		$('currDivList').hide();
		$('prevDivList').hide();
		$('prevDivList2').show();
		$('prevDivList3').hide();
		
	} else if(document.getElementById("isPrevDiv").value == "B") {
		$('currDivList').hide();
		$('prevDivList').hide();
		$('prevDivList2').hide();
		$('prevDivList3').show();
	} else if(document.getElementById("isPrevDiv").value == "C") {
		$('currDivList').hide();
		$('prevDivList').show();
		$('prevDivList2').hide();
		$('prevDivList3').hide();
	} else{
		$('currDivList').show();
		$('prevDivList').hide();
		$('prevDivList2').hide();
		$('prevDivList3').hide();
	}
}
function togglePrevDivList(obj) {
	// checkbox init
	document.sanctionPresentStateForm.prevDiv2.checked = false; 
	document.sanctionPresentStateForm.prevDiv3.checked = false;
	
	if (obj.checked) {
		alert("2010년 이전 데이터는 과거 부서명으로 검색할 수 없습니다.");
		document.getElementById("isPrevDiv").value = "C";
		$('currDivList').hide();
		$('prevDivList2').hide();
		$('prevDivList3').hide();
		$('prevDivList').show();
	} else {
		document.getElementById("isPrevDiv").value = "N";
		$('currDivList').show();
		$('prevDivList2').hide();
		$('prevDivList3').hide();
		$('prevDivList').hide();
	}
}
function togglePrevDivList2(obj) {
	// checkbox init
	document.sanctionPresentStateForm.prevDiv.checked = false;
	document.sanctionPresentStateForm.prevDiv3.checked = false;
	
	if (obj.checked) {
		document.getElementById("isPrevDiv").value = "A";
		$('currDivList').hide();
		$('prevDivList2').show();
		$('prevDivList3').hide();
		$('prevDivList').hide();
	} else {
		document.getElementById("isPrevDiv").value = "N";
		$('currDivList').show();
		$('prevDivList2').hide();
		$('prevDivList3').hide();
		$('prevDivList').hide();
	}
}
function togglePrevDivList3(obj) {
	// checkbox init
	document.sanctionPresentStateForm.prevDiv.checked = false; 
	document.sanctionPresentStateForm.prevDiv2.checked = false;

	if (obj.checked) {
		document.getElementById("isPrevDiv").value = "B";
		$('currDivList').hide();
		$('prevDivList2').hide();
		$('prevDivList3').show();
		$('prevDivList').hide();
	} else {
		document.getElementById("isPrevDiv").value = "N";
		$('currDivList').show();
		$('prevDivList2').hide();
		$('prevDivList3').hide();
		$('prevDivList').hide();
	}
}
</script>
</head>

<body onload="Page_OnLoad();">
<jsp:useBean id="now" class="java.util.Date"/>
<form name="sanctionPresentStateForm" id="sanctionPresentStateForm" method="post">
	<div style='display: none;'>
		<input type="hidden" name="isPrevDiv" id="isPrevDiv" value="<c:out value="${isPrevDiv}" />" />
	</div>

	<!-- location -->
	<div class="location">
		<p class="menu_title">결재현황</p>
		<ul>
			<li class="home">HOME</li>
			<li>전자결재</li>
			<li>결재현황</li>
		</ul>
	</div>
	<!-- // location -->
		
	<div class="contents sub"><!-- 서브 페이지 .sub -->
		<!-- 검색 창 -->					
		<div class="box">
			<div class="search_box total">
				<div class="select_group">
					<div class="select_box">
						<div class="label_group">
							<p>결재유형</p>
							<SELECT name='approvalType' id='approvalType' class='select' style='width: 100%;'>
								<option value="">전체</option>
								<c:forEach var="item" items="${sanctionTemplateList}">
									<option value="<c:out value="${item.approvalType}"/>" <c:if test="${approvalType == item.approvalType}">selected</c:if>><c:out
										value="${item.approvalName}" /></option>
								</c:forEach>
							</SELECT>
						</div>
						<div class="label_group">
							<p>조직단위 <% if(session.getAttribute("dept").equals("2000") || session.getAttribute("dept").equals("2040") || session.getAttribute("dept").equals("2050") 
									|| session.getAttribute("dept").equals("9381") || session.getAttribute("dept").equals("9371") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9360")) {%>(과거 조직 : 
							<input type="checkbox" style="vertical-align:-2px;" name="prevDiv2" id="prevDiv2" class="btn_check" value="A" title="21년도 조직 선택" onclick="togglePrevDivList2(this);" <c:if test="${isPrevDiv == 'A'}">checked</c:if>><label for="prevDiv2"></label> 21년도
							<input type="checkbox" style="vertical-align:-2px;" name="prevDiv3" id="prevDiv3" class="btn_check" value="B" title="20년도 조직 선택" onclick="togglePrevDivList3(this);" <c:if test="${isPrevDiv == 'B'}">checked</c:if>><label for="prevDiv3"></label> 20년도
							<input type="checkbox" style="vertical-align:-2px;" name="prevDiv" id="prevDiv" class="btn_check" value="C" title="과거 조직 선택" onclick="togglePrevDivList(this);" <c:if test="${isPrevDiv == 'C'}">checked</c:if>><label for="prevDiv"></label> 20년도 이전)<%} %></p>
							<div id="currDivList" style="display:none">
								<org:divList enabled="1" divType="A" isSelectBox="Y" depth="2" selectedInfo="${divCode}" attribute=" name='divCode'  id='divCode'  class='select' " all="Y"></org:divList>
							</div>
							<div id="prevDivList" style="display:none">
								<org:divList enabled="0" divType="A" isSelectBox="Y" depth="1" selectedInfo="${prevDivCode}" attribute=" name='prevDivCode'  id='prevDivCode'  class='select' " all="Y"></org:divList>
							</div>
							
							<div id="prevDivList2" style="display:none">
								<select id="prevDivCode2" name="prevDivCode2" class="select">
									<option value="" selected="">전체</option>
									<option value="9201" <c:if test="${prevDivCode2=='9201'}">selected</c:if>>기업가치딘단본부</option>
									<option value="9202" <c:if test="${prevDivCode2=='9202'}">selected</c:if>>사업가치진단본부</option>
									<option value="9203" <c:if test="${prevDivCode2=='9203'}">selected</c:if>>ESG혁신본부</option>
									<option value="9204" <c:if test="${prevDivCode2=='9204'}">selected</c:if>>리서치혁신본부</option>
									<option value="9205" <c:if test="${prevDivCode2=='9205'}">selected</c:if>>고객가치컨설팅센터</option>
									<option value="9211" <c:if test="${prevDivCode2=='9211'}">selected</c:if>>공공컨설팅1본부</option>
									<option value="9212" <c:if test="${prevDivCode2=='9212'}">selected</c:if>>공공컨설팅2본부</option>
									<option value="9213" <c:if test="${prevDivCode2=='9213'}">selected</c:if>>공공컨설팅3본부</option>
									<option value="9214" <c:if test="${prevDivCode2=='9214'}">selected</c:if>>공공컨설팅4본부</option>
									<option value="9215" <c:if test="${prevDivCode2=='9215'}">selected</c:if>>공공컨설팅5본부</option>
									<option value="9216" <c:if test="${prevDivCode2=='9216'}">selected</c:if>>에너지/환경본부</option>
									<option value="9217" <c:if test="${prevDivCode2=='9217'}">selected</c:if>>공공조사1본부</option>
									<option value="9218" <c:if test="${prevDivCode2=='9218'}">selected</c:if>>공공조사2본부</option>
									<option value="6201" <c:if test="${prevDivCode2=='6201'}">selected</c:if>>리서치센터</option>
									<option value="9231" <c:if test="${prevDivCode2=='9231'}">selected</c:if>>스마트/PI본부</option>
									<option value="9232" <c:if test="${prevDivCode2=='9232'}">selected</c:if>>정부정책사업본부</option>
									<option value="9233" <c:if test="${prevDivCode2=='9233'}">selected</c:if>>데이터사업센터</option>
									<option value="9241" <c:if test="${prevDivCode2=='9241'}">selected</c:if>>스마트교육본부</option>
									<option value="9242" <c:if test="${prevDivCode2=='9242'}">selected</c:if>>기업교육센터</option>
									<option value="9243" <c:if test="${prevDivCode2=='9243'}">selected</c:if>>공공교육센터</option>
									<option value="9244" <c:if test="${prevDivCode2=='9244'}">selected</c:if>>대학사업센터</option>
									<option value="9281" <c:if test="${prevDivCode2=='9281'}">selected</c:if>>미디어센터</option>
									<option value="9251" <c:if test="${prevDivCode2=='9251'}">selected</c:if>>전략사업센터</option>
									<option value="9261" <c:if test="${prevDivCode2=='9261'}">selected</c:if>>경영기획센터</option>
									<option value="9262" <c:if test="${prevDivCode2=='9262'}">selected</c:if>>경영관리센터</option>
									<option value="9263" <c:if test="${prevDivCode2=='9263'}">selected</c:if>>지식혁신센터</option>
									<option value="6201" <c:if test="${prevDivCode2=='6201'}">selected</c:if>>리서치센터</option>
								</select>
							</div>
							<div id="prevDivList3" style="display:none">
								<select id="prevDivCode3" name="prevDivCode3" class="select">
									<option value="" selected="">전체</option>
									<option value="9011" <c:if test="${prevDivCode3=='9011'}">selected</c:if>>진단평가1본부</option>
									<option value="9012" <c:if test="${prevDivCode3=='9012'}">selected</c:if>>진단평가2본부</option>
									<option value="9013" <c:if test="${prevDivCode3=='9013'}">selected</c:if>>진단평가3본부</option>
									<option value="9021" <c:if test="${prevDivCode3=='9021'}">selected</c:if>>컨설팅1본부</option>
									<option value="9022" <c:if test="${prevDivCode3=='9022'}">selected</c:if>>컨설팅2본부</option>
									<option value="9023" <c:if test="${prevDivCode3=='9023'}">selected</c:if>>컨설팅3본부</option>
									<option value="9024" <c:if test="${prevDivCode3=='9024'}">selected</c:if>>컨설팅4본부</option>
									<option value="9031" <c:if test="${prevDivCode3=='9031'}">selected</c:if>>R&C1본부</option>
									<option value="9032" <c:if test="${prevDivCode3=='9032'}">selected</c:if>>R&C2본부</option>
									<option value="9033" <c:if test="${prevDivCode3=='9033'}">selected</c:if>>R&C3본부</option>
									<option value="6201" <c:if test="${prevDivCode3=='6201'}">selected</c:if>>리서치센터</option>
									<option value="9041" <c:if test="${prevDivCode3=='9041'}">selected</c:if>>L&D1본부</option>
									<option value="9042" <c:if test="${prevDivCode3=='9042'}">selected</c:if>>L&D2본부</option>
									<option value="9043" <c:if test="${prevDivCode3=='9043'}">selected</c:if>>L&D3본부</option>
									<option value="9051" <c:if test="${prevDivCode3=='9051'}">selected</c:if>>미디어센터</option>
									<option value="9083" <c:if test="${prevDivCode3=='9083'}">selected</c:if>>스마트/PI센터</option>
									<option value="9065" <c:if test="${prevDivCode3=='9065'}">selected</c:if>>에너지환경센터</option>
									<option value="9067" <c:if test="${prevDivCode3=='9067'}">selected</c:if>>GBP센터</option>
								</select>
							</div>
						</div>
						<div class="label_group">
							<p>구분</p>
							<select name="ing" id="ing" class="select" style="width: 100%" >
							<option value="">전체</option>
							<option <c:if test="${ing == '진행'}">selected</c:if>>진행</option>
							<option <c:if test="${ing == '종료'}">selected</c:if>>종료</option>
							<option <c:if test="${ing == '반려'}">selected</c:if>>반려</option>
						</select> 
						</div>
					</div>
					<div class="select_box">
						<div class="label_group">
							<p>기안일</p>
							<fmt:parseDate value="${startDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
							<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
							<fmt:parseDate value="${endDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
							<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
							<script>
								jQuery(function(){jQuery( "#startDate" ).datepicker({});});
								jQuery(function(){jQuery( "#endDate" ).datepicker({});});
							</script>
							<input type="text" name="startDate" id="startDate" readonly="readonly" size="7" style="width: 40%" value="<c:out value="${start}" />" />&nbsp
							<input type="text" name="endDate" id="endDate" readonly="readonly" size="7" style="width: 40%"  value="<c:out value="${end}" />" />
						</div>
						<div class="label_group">
							<p>기안자</p>
							<input type="text" name="name" id="name" value="<c:out value="${name}"/>" onkeydown = "if(event.keyCode==13) doSearch();">					
						</div>
						<div class="label_group">
							<p>제목</p>
							<input type="text" name="projectName" id="projectName" value="<c:out value="${projectName}"/>" onkeydown = "if(event.keyCode==13) doSearch();">					
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
				<table id="projectProgressTable" class="tbl-list all"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<thead>
						<tr>
							<th>결재유형</th>
							<th>제목</th>
							<th>소속</th>
							<th>기안자</th>
							<th>기안일</th>
							<th colspan="2">진행상태</th>
						</tr>
					</thead>
					<tbody>
					<c:if test="${!empty list.list}">
						<c:forEach var="rs" items="${list.list}">
							<tr style="cursor: hand;" onmouseover="row_over(this)" onmouseout="row_out(this)" onclick="sanctionItemClick('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.approvalType}" />', '<c:out value="${rs.seq}" />', '<c:out value="${rs.workType}" />', '<c:out value="${rs.workTypeUrl}" />')">
								<fmt:parseDate value="${rs.registerDate}" type="DATE" dateStyle="SHORT" pattern="yyyy-MM-dd HH:mm:ss.SSS" var="var4" />
								<fmt:formatDate value="${var4}" pattern="yyyy-MM-dd" var="registerDate" />
								<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />

								<td><c:out value="${rs.approvalTypeName}" /></td>
								<td class="subject"><p><c:out value="${rs.projectName}" /></p></td>
								<td><c:out value="${rs.registerDeptName}" /></td>
								<td><c:out value="${rs.registerName}" /></td>
								<td><c:choose><c:when test="${registerDate eq null}">-</c:when><c:otherwise><c:out value="${registerDate}" /></c:otherwise></c:choose></td>
								<c:choose><c:when test="${rs.present == '반려'}"><td><font color="#F290A5">반려&nbsp<span class="ico-progress"></span></td></c:when>
										  <c:when test="${rs.present == '종료' }"><td colspan="2">종료</td></c:when>
										  <c:otherwise><td><c:out value="${rs.present}" /></td></c:otherwise></c:choose>
								<c:choose><c:when test="${rs.present == '종료' }"></c:when><c:otherwise><td><c:out value="${rs.presentName}" /></td></c:otherwise></c:choose>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty list.list}">
						<td align="center" colspan="6">검색 결과가 없습니다.</td>
					</c:if>
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
</form>
</body>
</html>