<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<title>프로젝트 현황</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goProjectDetail(projectCode) {
	document.location.href = "/action/ProjectSearchAction.do"
		+"?mode=getProjectInfoTab&viewMode=projectSearch&projectCode="+projectCode;
}
function goProjectEndDetail(projectCode, businessTypeCode, processTypeCode, evalRole) {
	document.location.href = "/project/endProcess/projectEndInfoTab.jsp?viewMode=projectSearch&projectCode="+projectCode+"&businessTypeCode="+businessTypeCode+"&processTypeCode="+processTypeCode+"&evalRole="+evalRole;
}
function goPage(next_page) {
	/*
	var param = "&businessTypeCode="+$("businessTypeCode").value 
				+ "&projectState=" + $("projectState").value
				+ "&realStartDate="+$("realStartDate").value
				+ "&realEndDate="+$("realEndDate").value
				+ "&runningDivCode="+$("runningDivCode").value
				+ "&projectName="+$("projectName").value
				+ "&pageNo="+next_page;
	document.location.href="/action/ProjectSearchAction.do?mode=getProjectSearchList"+param;
	*/
	document.projectSearchListForm.pageNo.value = next_page ;
	document.projectSearchListForm.target = "";		
	document.projectSearchListForm.action = "/action/ProjectSearchAction.do?mode=getProjectSearchList";
	document.projectSearchListForm.submit();
}
function goProjectBBS(projectCode, projectName) {		
	document.projectSearchListForm.target = "";
	document.projectSearchListForm.action = "/action/BoardAction.do?mode=selectList_pjt&bbsId="+projectCode+"&projectCode="+projectCode+"&projectName="+projectName;  
	document.projectSearchListForm.submit();
}
// QM게시판 구축
function goQMBBS(projectCode, projectName) {		
	document.projectSearchListForm.target = "";
	document.projectSearchListForm.action = "/action/BoardAction.do?mode=selectList2&bbsId="+projectCode+"&projectCode="+projectCode+"&projectName="+projectName;  
	document.projectSearchListForm.submit();
}
function doSearch() {
	document.projectSearchListForm.target = "";		
	document.projectSearchListForm.action = "/action/ProjectSearchAction.do?mode=getProjectSearchList";
	document.projectSearchListForm.submit();
}
function delayInfoClose(){
	$('delayInfo').hide();
	location.reload(true);
}
function Page_OnLoad() {
	if (document.getElementById("isPrevDiv").value == "E") {
		$('currDivList').hide();
		$('prevDivList').show();
		$('prevDivList2').hide();
	} else if (document.getElementById("isPrevDiv").value == "F"){
		$('currDivList').hide();
		$('prevDivList').hide();
		$('prevDivList2').show();
	} else {
		$('currDivList').show();
		$('prevDivList').hide();
		$('prevDivList2').hide();
	}
}
function togglePrevDivList(obj) {
	var sFrm = document.projectSearchListForm;
	sFrm.prevDiv2.checked=false;
	
	if (obj.checked) {
		document.getElementById("isPrevDiv").value = "E";
		$('currDivList').hide();
		$('prevDivList').show();
		$('prevDivList2').hide();
	} else {
		document.getElementById("isPrevDiv").value = "N";
		$('currDivList').show();
		$('prevDivList').hide();
		$('prevDivList2').hide();
	}
}

function togglePrevDivList2(obj) {
	var sFrm = document.projectSearchListForm;
	sFrm.prevDiv.checked=false;
	
	if (obj.checked) {
		document.getElementById("isPrevDiv").value = "F";
		$('currDivList').hide();
		$('prevDivList').hide();
		$('prevDivList2').show();
	} else {
		document.getElementById("isPrevDiv").value = "N";
		$('currDivList').show();
		$('prevDivList').hide();
		$('prevDivList2').hide();
	}
}

function delayInfoShow(state, projectCode){
	$('delayInfo').style.top = event.clientY;
	$('delayInfo').show();
	
	AjaxRequest.post (
			{	'url': "/action/ProjectSearchAction.do?mode=getProjectDelayInfo",
				'parameters' : { "projectCode": projectCode}, 
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.projectDelayInfoList;
					
					var table = $('delayInfoTable');
					var tbody = $('delayInfoTableBody').childElements();
		           	var tdCount = $('delayInfoTableHeader').down('tr').childElements().size();
/* 
					if(state == 'N'){
						$('delayInfoTitle').innerHTML = "진행정보 상세";
					}else{
						$('delayInfoTitle').innerHTML = "지연정보 상세";
					} */
					
		           	tbody.each( function(delayInfo){
		           		delayInfo.remove();
		    		});
		    		
 		    		rsObj.each(function(delayInfo){
			    	    var newRow=table.insertRow(-1);
			    		var newCellArr = new Array();
			    		for ( var i=0;i<tdCount;i++ ){
			    			newCellArr[i] = newRow.insertCell(-1);
			    		}		
		    			if(delayInfo.taskLevel == 'SANCTION_STATE_ENDRIVIEW_DRAFT'){
			    			newCellArr[0].innerHTML = "리뷰지작성";
		    			}else if(delayInfo.taskLevel == 'SANCTION_STATE_ENDRIVIEW_RIVIEW'){
			    			newCellArr[0].innerHTML = "리뷰지검토";
		    			}else if(delayInfo.taskLevel == 'SANCTION_STATE_ENDRIVIEW_APPROVE'){
			    			newCellArr[0].innerHTML = "리뷰지승인";
		    			}else{
		    				newCellArr[0].innerHTML = delayInfo.taskTypeName;
		    			}
		    			newCellArr[1].innerHTML = delayInfo.assigneeName;
		    			//newCellArr[2].innerHTML = delayInfo.assignDate;
		    			//newCellArr[3].innerHTML = delayInfo.executeDate;
			    		newCellArr[0].className = "detailTableField_center";
			    		newCellArr[1].className = "detailTableField_center";
			    		//newCellArr[2].className = "detailTableField_center";
			    		//newCellArr[3].className = "detailTableField_center";
		    		});	
				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("error");
				}
			}
		);
}
// 일정지연 프로젝트 화면 추가_김요섭_20191025
function openTip_1() {
	var configWin;
	configWin = window.open("/action/ProjectSearchAction.do?mode=getProjectSearchList&runningDeptCode=합계&projectState=3&delayState=R&businessTypeCode=", "configWin","top=100,left=100,width=770,height=825,scrollbars=yes");
	configWin.focus();
}
function openTip_2() {
	var configWin;
	configWin = window.open("/action/ProjectSearchAction.do?mode=getProjectSearchList&runningDeptCode=합계&projectState=4&delayState=ER&businessTypeCode=", "configWin","top=100,left=100,width=770,height=825,scrollbars=yes");
	configWin.focus();
}
function openTip_3() {
	var configWin;
	configWin = window.open("/action/ProjectSearchAction.do?mode=getProjectSearchList&runningDeptCode=합계&projectState=5&delayState=RR&businessTypeCode=", "configWin","top=100,left=100,width=770,height=845,scrollbars=yes");
	configWin.focus();
}

function saveToExcel(){
	document.projectSearchListForm.target = "";		
	document.projectSearchListForm.action = "/action/ProjectSearchAction.do?mode=saveToExcel";
	document.projectSearchListForm.submit();
}

function openErpPopUp() { 
	var win = new Window('modal_window', {
		className : "dialog",
		title : "누락 계산서 체크",
		top : 50,
		left : 50,
		width : 694,
		height : 400,
		zIndex : 150,
		opacity : 1, 
		resizable : true,
		showEffectOptions : {duration : 0.1},
		hideEffectOptions : {duration : 0.1},		
		url : "/action/ProjectEndingAction.do?mode=selectEndingForOutputUpload3&projectCode=1901139"
	});
	win.show(true);
	win.setDestroyOnClose();
}
</script> 
</head>

<!-- <body leftmargin="15" topmargin="0" style="overflow-x:hidden;overflow-y:hidden">  -->
<body onload="Page_OnLoad();">
	<form name="projectSearchListForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo" value="<c:out value="${list.valueListInfo.pagingPage}"/>"> 
			<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>" />
			<input type="hidden" name="projectName" value="<c:out value="${projectName}"/>" />
			<input type="hidden" name="delayState" value="<c:out value="${delayState}" />" />
			<input type="hidden" name="ssn" value="<c:out value="${ssn}" />" />
			<input type="hidden" name="runningPUCode" value="<c:out value="${runningPUCode}" />" />
			<input type="hidden" name="industryTypeCode" value="<c:out value="${industryTypeCode}" />" />
			<input type="hidden" name="endGubun" value="<c:out value="${endGubun}" />" />
			<input type="hidden" name="costOver" value="<c:out value="${costOver}" />" />
			<input type="hidden" name="endProcess" value="<c:out value="${endProcess}" />" />
			<input type="hidden" name="etcCostOver" value="<c:out value="${etcCostOver}" />" />
			<input type="hidden" name="payCostOver" value="<c:out value="${payCostOver}" />" />
			<input type="hidden" name="chartSsn" value="<c:out value="${chartSsn}" />" />
			<input type="hidden" name="customerName" value="<c:out value="${customerName}"/>" />
			<input type="hidden" name="isPrevDiv" id="isPrevDiv" value="<c:out value="${isPrevDiv}" />" />
			<input type="hidden" name="runningDivCode" id="runningDivCode" value="<c:out value="${runningDivCode }" />" />
		<%-- 	<input type="hidden" name="projectState" id="projectState" value="<c:out value="${projectState }" />" /> --%>
		</div>
		
		<!-- location -->
		<div class="location">
			<p class="menu_title">프로젝트 현황</p>
			<ul>
				<li class="home">HOME</li>
				<li>PMS</li>
				<li>프로젝트 현황</li>
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
								<p>비즈니스 타입</p>
								<SELECT name='businessTypeCode' class='select'>
									<option value='' <c:if test="${businessTypeCode==''}">selected</c:if>>전체</option>
									<option value='BTA' <c:if test="${businessTypeCode=='BTA'}">selected</c:if>>컨설팅</option>
									<option value='BTB' <c:if test="${businessTypeCode=='BTB'}">selected</c:if>>진흥</option>
									<option value='BTC' <c:if test="${businessTypeCode=='BTC'}">selected</c:if>>인증</option>
									<option value='BTJ' <c:if test="${businessTypeCode=='BTJ'}">selected</c:if>>진단</option>
									<option value='BTD' <c:if test="${businessTypeCode=='BTD'}">selected</c:if>>리서치</option>
									<option value='BTE' <c:if test="${businessTypeCode=='BTE'}">selected</c:if>>교육</option>
									<option value='BTF' <c:if test="${businessTypeCode=='BTF'}">selected</c:if>>국제사업</option>
									<option value='BTG' <c:if test="${businessTypeCode=='BTG'}">selected</c:if>>미디어</option>
									<option value='BTI' <c:if test="${businessTypeCode=='BTH'}">selected</c:if>>위원회</option>
									<option value='BTK' <c:if test="${businessTypeCode=='BTK'}">selected</c:if>>과제관리</option>
								</SELECT>
							</div>
							
							<div class="label_group">
								<p>진행 단계</p>
								<code:codelist tableName="PROJECT_STATE" attribute=" name='projectState' id='projectState' class='select'" isSelectBox="Y" all="Y" selectedInfo="${projectState}" />
							</div>
							
							<div class="label_group">
								<p>조직단위 (과거 조직 : <input type="checkbox" name="prevDiv" id="prevDiv" class="btn_check" value="E" title="과거 부서명으로 검색 시 선택" onclick="togglePrevDivList(this);" <c:if test="${isPrevDiv == 'E'}">checked</c:if>><label for="prevDiv"></label> 18-20년도 &nbsp
								<input type="checkbox" style="vertical-align:-2px;" name="prevDiv2" id="prevDiv2" class="btn_check" value="F" title="과거 부서명으로 검색 시 선택" onclick="togglePrevDivList2(this);" <c:if test="${isPrevDiv == 'F'}">checked</c:if>><label for="prevDiv2"></label> 14-17년도  )
								</p>
								<div id="currDivList" style="display:none">
								<org:divList enabled="1" depth="2" attribute=" name='runningDeptCode' class='select' " 
									divType="A" all="Y" isSelectBox="Y" selectedInfo="${runningDeptCode}"></org:divList>
								</div>
								<div id="prevDivList" style="display:none">
								<select name="runningDivCode1" class="select">
									<option value="" selected="">전체</option>
									<option value="9011" <c:if test="${runningDivCode1=='9011'}">selected</c:if>>진단평가1본부</option>
									<option value="9012" <c:if test="${runningDivCode1=='9012'}">selected</c:if>>진단평가2본부</option>
									<option value="9013" <c:if test="${runningDivCode1=='9013'}">selected</c:if>>진단평가3본부</option>
									<option value="9021" <c:if test="${runningDivCode1=='9021'}">selected</c:if>>컨설팅1본부</option>
									<option value="9022" <c:if test="${runningDivCode1=='9022'}">selected</c:if>>컨설팅2본부</option>
									<option value="9023" <c:if test="${runningDivCode1=='9023'}">selected</c:if>>컨설팅3본부</option>
									<option value="9024" <c:if test="${runningDivCode1=='9024'}">selected</c:if>>컨설팅4본부</option>
									<option value="9031" <c:if test="${runningDivCode1=='9031'}">selected</c:if>>R&C1본부</option>
									<option value="9032" <c:if test="${runningDivCode1=='9032'}">selected</c:if>>R&C2본부</option>
									<option value="9033" <c:if test="${runningDivCode1=='9033'}">selected</c:if>>R&C3본부</option>
									<option value="6201" <c:if test="${runningDivCode1=='6201'}">selected</c:if>>리서치센터</option>
									<option value="9041" <c:if test="${runningDivCode1=='9041'}">selected</c:if>>L&D1본부</option>
									<option value="9042" <c:if test="${runningDivCode1=='9042'}">selected</c:if>>L&D2본부</option>
									<option value="9043" <c:if test="${runningDivCode1=='9043'}">selected</c:if>>L&D3본부</option>
									<option value="9051" <c:if test="${runningDivCode1=='9051'}">selected</c:if>>미디어센터</option>
									<option value="9083" <c:if test="${runningDivCode1=='9083'}">selected</c:if>>스마트/PI센터</option>
									<option value="9065" <c:if test="${runningDivCode1=='9065'}">selected</c:if>>에너지환경센터</option>
									<option value="9067" <c:if test="${runningDivCode1=='9067'}">selected</c:if>>GBP센터</option>
								</select>
								</div>
								<div id="prevDivList2" style="display:none">
								<select name="runningDivCode2" class="select">
									<option value="" selected="">전체</option>
									<option value="7010" <c:if test="${runningDivCode2=='7010'}">selected</c:if>>진단평가본부</option>
									<option value="7020" <c:if test="${runningDivCode2=='7020'}">selected</c:if>>컨설팅1본부</option>
									<option value="7030" <c:if test="${runningDivCode2=='7030'}">selected</c:if>>컨설팅2본부</option>
									<option value="7170" <c:if test="${runningDivCode2=='7170'}">selected</c:if>>컨설팅3본부</option>
									<option value="7040" <c:if test="${runningDivCode2=='7040'}">selected</c:if>>R&C1본부</option>
									<option value="7050" <c:if test="${runningDivCode2=='7050'}">selected</c:if>>R&C2본부</option>
									<option value="7060" <c:if test="${runningDivCode2=='7060'}">selected</c:if>>L&D본부</option>
									<option value="7100" <c:if test="${runningDivCode2=='7100'}">selected</c:if>>PI센터</option>
									<option value="7130" <c:if test="${runningDivCode2=='7130'}">selected</c:if>>에너지/환경센터</option>
									<option value="7180" <c:if test="${runningDivCode2=='7180'}">selected</c:if>>GBP센터</option>
									<option value="7140" <c:if test="${runningDivCode2=='7140'}">selected</c:if>>미디어센터</option>
									<option value="7150" <c:if test="${runningDivCode2=='7150'}">selected</c:if>>경영기획실</option>
								</select>
								</div>
							</div>
						</div>
						
						<div class="select_box">
							<div class="label_group">
								<p>기간</p>										
								<div class="month_check">
									<fmt:parseDate value="${realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var4"/>
									<fmt:formatDate value="${var4}" pattern="yyyy-MM-dd" var="start"/>
									<fmt:parseDate value="${realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var4"/>
									<fmt:formatDate value="${var4}" pattern="yyyy-MM-dd" var="end"/>
									<script>
										jQuery(function(){jQuery( "#realStartDate" ).datepicker({});});
										jQuery(function(){jQuery( "#realEndDate" ).datepicker({});});
									</script>
									<input type="text" name="realStartDate" id="realStartDate" style="width:43%" readonly="readonly" value="<c:out value="${start}" />" /> 
									<span>~</span>
									<input type="text" name="realEndDate" id="realEndDate" style="width:43%" readonly="readonly" value="<c:out value="${end}" />" />
								</div>
							</div>
							<div class="label_group">
								<p>유형</p>
								<select name="projectTypeCode" class="select" id="projectTypeCode">
									<option value="" selected="">전체</option>
									<option value="MM" <c:if test="${projectTypeCode=='MM'}">selected</c:if>>수주성</option>
									<option value="ND" <c:if test="${projectTypeCode=='ND'}">selected</c:if>>기획성</option>
								</select>
							</div>
							<div class="label_group">
								<p>선택</p>
								<select name="keywordType" class="select" style="width:30%">
									<option value="PNAME" <c:if test="${keywordType=='PNAME'}">selected</c:if>>프로젝트</option>
									<option value="PCODE" <c:if test="${keywordType=='PCODE'}">selected</c:if>>코드</option>
									<option value="CUST" <c:if test="${keywordType=='CUST'}">selected</c:if>>고객사</option>
								</select>
								<input type="text" name="keyword" value="<c:out value="${keyword}"/>" class="textInput" style="width: 65%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</div>
						</div>
				</div>
				<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색<i class="mdi mdi-magnify"></i></button></div>
				<%-- <div class="btNbox txtR">
					<% if(session.getAttribute("userId").equals("lokal07") || session.getAttribute("userId").equals("yskim") || session.getAttribute("userId").equals("kmacrpa")) {%>
						<a class="btN006bc6 txt2btn" href="#" onclick="openErpPopUp()">누락 계산서 체크</a>
					<%} %>
					<a class="btN3fac0c txt4btn" href="javascript:saveToExcel();"><b>Excel 저장</b></a>
				</div> --%>
			</div>
		</div>
		
		<!-- 테이블 리스트 -->			
		<div class="box">
			<div class="a-both total">
				<p>총<span><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>건</p>
				<div class="btn_area">
					<button type="button" class="btn btn_aqua" onclick="saveToExcel()">EXCEL 저장</button>
					<button type="button" class="btn btn_a_grey" onclick="location.href='/_new/project/searchList'">프로젝트 전체현황</button>
				</div>
			</div>
				
			<div class="tbl-wrap sc">
				<table  id="projectProgressTable" class="tbl-list all"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<thead>
							<tr>
								<th>상태</th>
								<th>구분</th>
								<th>부서</th>
								<th>프로젝트</th>
								<th>종료일</th>
								<th>PJT 게시판</th>
								<th>QM 게시판</th>	
								<th>상세정보</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty list.list}">
									<c:forEach var="rs" items="${list.list}">
										<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
											<c:choose>
												<c:when test="${rs.projectState == '1'}"><td>등록절차</td></c:when>
												<c:when test="${rs.projectState == '2'}"><td>품의중</td></c:when>
												<c:when test="${rs.projectState == '4'}">
													<c:choose>
														<c:when test="${rs.delayState == 'ER'}">
															<td class="status tooltip">평가지연
																<span class="ico-progress" onclick="delayInfoShow('D', '<c:out value="${rs.projectCode}" />');">
																</span>
															</td>
														</c:when>
														<c:otherwise>
															<td>평가
																<a href="#" onclick="delayInfoShow('N', '<c:out value="${rs.projectCode}" />');">
																			<span class="mdi mdi-progress-question"></span></a>
															</td>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:when test="${rs.projectState == '5'}">
													<c:choose>
														<c:when test="${rs.delayState == 'RR'}">
															<td class="status tooltip">리뷰지연
																<span class="ico-progress" onclick="delayInfoShow('D', '<c:out value="${rs.projectCode}" />');">
																</span>
															</td>
														</c:when>
															<c:otherwise>
															<td>리뷰
																<a href="#" onclick="delayInfoShow('N', '<c:out value="${rs.projectCode}" />');">
																			<span class="mdi mdi-progress-question"></span></a>
															</td>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:when test="${rs.projectState == '6'}"><td>완료</td></c:when>
												<c:when test="${rs.projectState == '7'}"><td>취소</td></c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${rs.delayState == 'B'}"><td>진행</td></c:when>
														<c:when test="${rs.delayState == 'R'}">
														<td class="status tooltip">일정지연
																<span class="ico-progress" onclick="delayProjectTaskInfoShow('D', '<c:out value="${rs.projectCode}" />')";>
																</span>
														</td>
														</c:when>
														<c:otherwise><font color="#FF0000">평가대기</font></c:otherwise>
													</c:choose> 
												</c:otherwise>
											</c:choose>
											<td><c:out value="${rs.businessTypeCodeName}" /></td>
											<td>
												<c:choose>
												<c:when test="${isPrevDiv == 'N' }"><c:out value="${rs.runningDeptCodeName}" /></c:when>
												<c:otherwise><c:out value="${rs.runningDivCodeName}" /></c:otherwise>
												</c:choose>
											</td>
											<td class="subject" style="text-align: left; cursor: hand;" onclick="goProjectDetail('<c:out value="${rs.projectCode}" />')"><p>[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></p><c:if test="${rs.costOver == 'Y'}">&nbsp/ <span class="pink">예산초과<span class="mdi mdi-check-circle"></span></span></c:if></td>
											<td>
												<fmt:parseDate value="${rs.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
												<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="formattedFrom"/>
												<c:out value="${formattedFrom}" />
											</td>
											<td><a href="#" onclick="goProjectBBS('<c:out value="${rs.projectCode}" />','<c:out value="${rs.projectName}" />')"><c:out value="${rs.boardArticleCount}" /></a></td>
											<td><a href="#" onclick="goQMBBS('<c:out value="${rs.projectCode}" />','<c:out value="${rs.projectName}" />')"><c:out value="${rs.boardArticleCountQM}" /></a></td>
											<td><c:if test="${rs.projectState > '2'}"><a href="javascript:goProjectDetail('<c:out value="${rs.projectCode}" />')" class="ico-file"></c:if></td> 
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr><td colspan="9">검색된 데이터가 없습니다.</td></tr>
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
	</form>
</body>
<div id="delayInfo" style="position: absolute; background: white; display: none; width: 320px; border: solid 1px #EFEFEF; margin-left: 10px;">
		<div class="title_both">
			<div class="h1_area">
				<p class="h1">상태 정보</p>
			</div>
			<div class="select_box">
			<button type="button" class="btn line btn_blue" onclick="delayInfoClose()"><i class="mdi mdi-close-box-outline"></i>닫기</button>
			</div>
		</div>
		<div style="margin: 10 10 10 10;">
			<div class="board_contents">
				<table id="delayInfoTable"  class="tbl-edit td-c">	
				<colgroup>
					<col style="width: 60%"/>
					<col style="width: *"/>
				</colgroup>
				<tr>
					<th>액티비티</th>
					<th>담당자</th>
					<thead id="delayInfoTableHeader">
						<tr>
							<td></td>
							<td></td>
						</tr>
					</thead>
					<tbody id="delayInfoTableBody">
					</tbody>
				</tr>
				</table>
			</div>
		</div>
</div>
<div id="delayProjectTaskInfo" style="position: absolute; background: white; display: none; width: 320px; border: solid 1px #EFEFEF; margin-left: 10px; ">
	<div class="title_both">
		<div class="h1_area">
			<p class="h1">상태 정보</p>
		</div>
		<div class="select_box">
		<button type="button" class="btn line btn_blue" onclick="delayProjectTaskInfoClose()"><i class="mdi mdi-close-box-outline"></i>닫기</button>
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
					<th>액티비티</th>
					<td><span id="activityName"></span></td>
				</tr>
				<!-- <tr>
					<th>예정 완료일</th>
					<td><span id="activityendDate"></span></td>
				</tr> -->
			</table>
		</div>
	</div>
</div>
</html>