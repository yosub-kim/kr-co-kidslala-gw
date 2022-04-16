<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function goPage(next_page) {
	document.form.pageNo.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/WeeklyReportAction.do?mode=getWeeklyReportStatus";
	document.form.submit();
}
function doSearch() {
	document.form.target = "";		 
	document.form.action = "/action/WeeklyReportAction.do?mode=getWeeklyReportStatus";
	document.form.submit();
}
function openWeekluReportList(projectCode, state) {
	var url = "/action/WeeklyReportAction.do?mode=getWeeklyReportList"
		+"&projectCode="+projectCode
		+"&state="+state
		+"&viewMode=weeklyReportStatus"
		+"&readonly=true";
 window.open(url,'projectReport','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=784,height=510,directories=no,menubar=no');	
}
function createWreport(projectCode){
	//var ActionURL = "/action/WeeklyReportAction.do?mode=assignWeeklyReportByProjectCode&projectCode="+projectCode;
	var ActionURL = "/action/WeeklyReportAction.do?mode=assignWeeklyReport&projectCode="+projectCode;
	var sFrm = document.form;
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')').res;
				if(res >= 0){
					alert("해당 프로젝트의 주간보고 전체(금일기준:"+res+"건)를 생성 했습니다.");
					document.form.target = "";		 
					document.form.action = "/action/WeeklyReportAction.do?mode=getWeeklyReportStatus";
					document.form.submit();
				} else {
					alert("전체 주간보고 생성 시 오류가 발생했습니다.");			
				}
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				alert("등록할 수 없습니다.");
			}
		}
	); status = null;	
}
/*
function createWreportAll(){
	var ActionURL = "/action/WeeklyReportAction.do?mode=assignWeeklyReport";
	var sFrm = document.form;
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')').res;
				if(res >= 0){
					alert(res+"건의 주간보고를 생성 했습니다.");
					document.form.target = "";		 
					document.form.action = "/action/WeeklyReportAction.do?mode=getWeeklyReportStatus";
					document.form.submit();
				} else {
					alert("전체 주간보고 생성 시 오류가 발생했습니다.");					
				}
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				alert("등록할 수 없습니다.");
			}
		}
	); status = null;	
}
*/
//#######################
function getWeeklyReportDetail(projectCode, assignYear, assignMonth, assignWeekOfMonth) {
	var url = "/action/WeeklyReportAction.do?mode=getWeeklyReport"
			+"&projectCode="+projectCode
			+"&assignYear="+assignYear
			+"&assignMonth="+assignMonth
			+"&assignWeekOfMonth="+assignWeekOfMonth
			+"&readonly=true";
	 window.open(url,'projectReport','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=784,height=510,directories=no,menubar=no');
}
function selectAll() {
	for (var i = 0; i < document.form.elements.length; i++) {
		if (document.form.elements[i].name == "id[]" && document.form.elements[i].disabled==false) {				
			document.form.elements[i].checked = document.form.checkAll.checked;
		}
	}
}
function popSMS() {
	var cntArry = [];
	var val = '';
	var msg = '';
	
	var projectCodeList = $$('input[name="id[]"]');

	for (var i = 0; i < projectCodeList.length; ++i) {
		if(projectCodeList[i].checked){
			cntArry.push(projectCodeList[i].value);
		}
	}
	if (cntArry.length == 0) {
		alert('SMS를 발송할 프로젝트를 선택하지 않았습니다.');  
		return; 			
	} else {
		$('smsCnt').update("(SMS 수신인원 "+cntArry.length+"명)");
		$('delaySMS').show();			
	}
}
function closeSMS(){
	$('smsCnt').update('');
	$('delaySMS').hide();
}
function sendSMS(){//작업 필요 - 화면에 나오는 받는 사람 중복제거 
	var projectCodeStr="";
	var projectCodeList = $$('input[name="id[]"]');
	for (var i = 0; i < projectCodeList.length; ++i) {
		if(projectCodeList[i].checked){
			projectCodeStr = projectCodeStr+projectCodeList[i].value+";";
		}
	}
	//alert(projectCodeStr);	
	AjaxRequest.post (
		{	'url': "/action/SmsCommonAction.do?mode=sendProjectDelaySmS",
			'parameters' : { "projectCodeStr": projectCodeStr, smsMessage: $("smsMessage").value, callbackNo: $("callbackNo").value}, 
			'onSuccess':function(obj){
				var res = eval('(' + obj.responseText + ')');
				alert("전송이 완료되었습니다.");//hmData.put("result", "SUCCESS");
				closeSMS()
			}, 
			'onLoading':function(obj){},
			'onError':function(obj){
				alert("error");
			}
		}
	);
}

</script>
</head>

<body style="padding-left:2px">
<div style="margin: 70 0 0 20 ">
<form name="form" method="post">	
	<input type="hidden" name="pageNo" id="pageNo"> 
	<input type="hidden" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >

	<table width="740px" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100%" valign="top">
				<table width="100%" cellpadding="0" cellspacing="0"> 

					<!-- sub 타이틀 영역 시작-->  
					<tr>
						<td><h4 class="subTitle mt15 mb5">주간진척관리 작성현황</h4></td>
					</tr>
					<!-- sub 타이틀 영역 종료--> 
					<!-- 검색 영역 -->
					<tr>
						<td height="21" align="left" valign="top">
							<%@ include file="/common/include/searchBox_Header.jsp"%>
							<table border="0" width="100%" style="border-collapse: collapse;">
								<colgroup> 
									<col width="85px">
									<col width="120px">
									<col width="95px">
									<col width="90px">
									<col width="50px"> 
									<col width="*">
								</colgroup>
								<tr>
									<td class="searchTitle_center">상태</td>
									<td class="searchField_center">
										<code:codelist tableName="PROJECT_STATE" attribute=" name='projectState' id='projectState' class='selectbox' style='width:99%;' " isSelectBox="Y" all="Y" selectedInfo="${projectState}" />
									</td>
									<td class="searchTitle_center">기간</td>
									<td class="searchField_center">
										<fmt:parseDate value="${fromDate}" type="DATE" dateStyle="SHORT"  pattern="yyyy-MM-dd" var="var1"/>
										<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
										<fmt:parseDate value="${toDate}" type="DATE" dateStyle="SHORT"  pattern="yyyy-MM-dd" var="var2"/>
										<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
										<script>
											jQuery(function(){jQuery( "#fromDate" ).datepicker({});});
											jQuery(function(){jQuery( "#toDate" ).datepicker({});});
										</script>
										<input type="text" name="fromDate" id="fromDate" readonly="readonly" size="8" value="<c:out value="${start}" />" />~<input 
										type="text" name="toDate" id="toDate" readonly="readonly" size="8" value="<c:out value="${end}" />" />
									</td>
								</tr>
								<tr>
									<td class="searchTitle_center">조직단위별</td>
									<td class="searchField_center">
										<org:divList enabled="1" depth="2" attribute=" name='runningDeptCode' class='selectbox' style='width:100%' " 
											divType="A" all="Y" isSelectBox="Y" selectedInfo="${runningDeptCode}"></org:divList>
									</td>
									<td class="searchField_center">
										<select name="keywordType" class="selectbox" style="width:100%">
											<option value="PNAME" <c:if test="${keywordType=='PNAME'}">selected</c:if>>프로젝트명</option>
											<option value="PCODE" <c:if test="${keywordType=='PCODE'}">selected</c:if>>프로젝트코드</option>
										</select>
									</td>
									<td colspan="3" class="searchField_center">
										<input type="text" name="keyword" value="<c:out value="${keyword}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
									</td>
								</tr>
							</table>
							<%@ include file="/common/include/searchBox_Footer.jsp"%>
						</td>
					</tr>			
				 	
				 	
					<tr><td height='10'></td></tr>
					<tr>
						<td width="100%">
							<!-- 페이지 정보, 버튼 -->			
							<div class="btNbox pb15">
								<div class="btNlefTdiv">				
									<img src="/images/sub/line3Blit.gif" alt="">
									<span class="bold colore74c3a"><c:out value="${weeklyReportStatus.valueListInfo.totalNumberOfEntries}"/></span>
									<span>Total - Page(<span><c:out value="${weeklyReportStatus.valueListInfo.pagingPage}"/></span> of <span><c:out value="${weeklyReportStatus.valueListInfo.totalNumberOfPages}"/></span>)</span>
								</div>
								<%if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){%>									
									<div class="btNrighTdiv">
									<!-- 
										<a class="btNe14f42 txt2btn" href="javascript:createWreportAll();">전체 생성</a>
									 -->
										<a class="btNe14f42 txt2btn" href="javascript:popSMS();">SMS보내기</a>
									</div>
								<%}%>
							</div>
						</td>
					</tr>
					
					
					



					
					<tr><td height='5'></td></tr> 					
					
					<tr>
						<td>
							<table id="weeklyReportTable" class='listTable' width="100%" cellpadding="0" cellspacing="0">
								<thead id="weeklyReportTableHeader">
									<tr> 
										<td width="7%">상태</td>
										<td width="*">프로젝트명</td> 
										<td width="9%">작성자</td>
										<td width="8%">작성중</td>
										<td width="8%">검토중</td> 
										<td width="8%">승인중</td>   
										<td width="8%">작성완료</td> 
										<%if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){%>
										<td width="12%">금주<br/>주간보고<br/>생성</td>
										<td width="8%">선택<br/>
										<input type="checkbox" class="checkbox" name="checkAll" onclick="javascript:selectAll();"></td>
										<%}%>
									</tr>  
								</thead>									
								<tbody id="weeklyReportTableBody"> 
									<c:if test="${!empty weeklyReportStatus.list}">
										<c:forEach var="rs" items="${weeklyReportStatus.list}">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)" >
												<td align="center"><c:out value="${rs.projectStateStr}"/></td> 
												<td class="myoverflow" align="center">[<c:out value="${rs.projectCode}"/>] <c:out value="${rs.projectName}"/></td> 
												<td align="center"><c:out value="${rs.writerName}"/></td>
												<td align="center"><a href="javascript:openWeekluReportList('<c:out value="${rs.projectCode}" />', 'writer');"> <c:out value="${rs.writerCnt}"/></a></td>
												<td align="center"><a href="javascript:openWeekluReportList('<c:out value="${rs.projectCode}" />', 'reviewer');"> <c:out value="${rs.reviewerCnt}"/></a></td>
												<td align="center"><a href="javascript:openWeekluReportList('<c:out value="${rs.projectCode}" />', 'approver');"> <c:out value="${rs.approverCnt}"/></a></td>
												<td align="center"><a href="javascript:openWeekluReportList('<c:out value="${rs.projectCode}" />', 'complete');"> <c:out value="${rs.completeCnt}"/></a></td>
												<%if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){%>
												<td align="center"><a class="btN3fac0c txt2btn fr" href="javascript:createWreport('<c:out value="${rs.projectCode}" />');">생성</a></td>
												<td align="center">
													<input type="checkbox" class="checkbox" name="id[]" value="<c:out value="${rs.projectCode}" />" />
													<input type="hidden" name="projectCode[]" value="<c:out value="${rs.projectCode}" />" />
												</td>
												<%}%>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${empty weeklyReportStatus.list}">
										<tr>
											<td align="center" colspan="10">검색 결과가 없습니다.</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</td>
					</tr>
					<%-- 페이징 영역 시작 --%>
					<tr>
						<td width="793">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr height='30'>
									<td width="100%" align="center">
										<SCRIPT LANGUAGE="JavaScript">
											document.write( makePageHtml( 
													<c:out value="${weeklyReportStatus.valueListInfo.pagingPage}" default="1"/>, 
													10, 
													<c:out value="${weeklyReportStatus.valueListInfo.totalNumberOfEntries}" default="0"/> , 
													10)
											) ;
										</SCRIPT>
									</td>
								</tr>
							</table>									
						</td>
					</tr>
					<%-- 페이징 영역 끝--%>
						
					
				</table>
			</td>
		</tr>
	</table>						
</form>
</div>
</body>
<div id="delaySMS" 
	style="position: absolute; background: white; display: none; 
	left: 350px; top: 60px;
	width: 200px; text-align: center; height: 300px;
	border-style: solid; border-color: gray; border-width: 2px; padding: 10px;">
	<table cellpadding="3" cellspacing="3" width="100%">
		<tr>
			<td>
				<h4 class="subTitle">
				주간진척관리 작성지연 SMS 발송</br>
				※ 발송대상 : PM
				</h4>
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" id="callbackNo" name="callbackNo" value="02-3786-0642">
				<textarea name="smsMessage" id="smsMessage" style="width: 100%; height: 150px;" >
안녕하십니까, 
귀하의 담당 프로젝트에 주간진척현황 작성 지연이 확인 되오니 신속한 조치 바랍니다.
- KMAC 경영기획실 -</textarea>
		
			</td>
		</tr>
		<tr align="right">
			<td colspan="2">  <span id="smsCnt">(SMS 수신인원 0 명)</span><br/>
			<span style="font-weight: bold; color: blue;">확인 버튼을 누르시면 SMS발송이<br/>완료 됩니다.</span>
			</td>
		</tr>
	</table>
	<div class="btNrighTdiv" style="margin-top: 15px; width: 100%;">
		<a class="btNe006bc6 txt2btn" href="javascript:sendSMS();">확인</a>
		<a class="btNa0a0a0 txt2btn" href="javascript:closeSMS();">취소</a>
	</div>
</div>

</html>					