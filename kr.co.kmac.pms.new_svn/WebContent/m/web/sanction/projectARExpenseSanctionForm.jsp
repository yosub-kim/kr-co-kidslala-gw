<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="java.util.Calendar"%>
<%@page import="kr.co.kmac.pms.sanction.general.data.SanctionDoc"%>

<%@page import="java.util.List"%>
<%@page import="kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.TableGrid"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Cell"%>
<%@page import="kr.co.kmac.pms.common.tag.tablegrid.Row"%>
<%@page import="kr.co.kmac.pms.common.util.StringUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
<%
	boolean canSanction = true;
	int exceedCnt = 0;
	int reportExceedCnt = 0;
	//리스트 오브젝트 
	List<ExpenseRealTimeResultDetailForSanction> list = (List)request.getAttribute("expenselist");
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' width='100%' cellpadding='0' cellspacing='0' style='table-layout: fixed;' ");
	tableGrid.setHeadAnnex(false); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();		
	headerRow.setAttribute(" style='height:38px;' ");
	headerRow.addCell(new Cell("구분").setAttribute(" width='70' "));
	headerRow.addCell(new Cell("이름").setAttribute(" width='70' ")); 
	headerRow.addCell(new Cell("총 금액").setAttribute(" width='90' "));
	headerRow.addCell(new Cell("금액<br>(70%)").setAttribute(" width='70' "));
	headerRow.addCell(new Cell("금액<br>(30%)").setAttribute(" width='70' "));
	headerRow.addCell(new Cell("프로젝트").setAttribute(" width='210' "));
	headerRow.addCell(new Cell("프로젝트<br>소계").setAttribute(" align='center' width='80' "));
	headerRow.addCell(new Cell("지급 성과급<br>누계").setAttribute(" align='center' width='80' "));
	/* headerRow.addCell(new Cell("성과급<br>예산").setAttribute(" align='center' width='70' ")); */
	tableGrid.addHeadRow(headerRow);
	
	Row row = null;
	if(list != null && list.size() > 0){
		for(ExpenseRealTimeResultDetailForSanction data : list){
			String isExceedStyle = "";
			String isReportExceedStyle = "";
			String isMMExceedStyle = "";
			if(data.getSalaryIsExceed().equals("Y")){
				isExceedStyle = " background-color: #ffdfff; ";
				canSanction = false;
				exceedCnt = exceedCnt + 1;
			}
			if(data.getSalaryIsHolding().equals("Y")){
				isReportExceedStyle = " background-color: #ffb3cc; ";
				canSanction = false;
				reportExceedCnt = reportExceedCnt + 1;
			}
			if(data.getSalaryIsMMExceed().equals("Y")) {
				isMMExceedStyle = " background-color: #ffd9b3; ";
			}
			row = new Row().setAttribute(" row='salary' ");
			row.addCell(new Cell(data.getSalaryJobClassDesc()).setAttribute("  "));
			row.addCell(new Cell(data.getSalaryName()).setAttribute(" style='text-align:center; "+isReportExceedStyle+" ' "));
 			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryTotalRealTimeSalary())).setAttribute(" style='padding: 7px 2px 7px 2px; text-align: right' "));
 			/*상근 (2) 성과급 화면 변화*/
 			if(data.getSalaryJobClassDesc().equals("상근(2)")){
 				row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryRealTimeSalaryEachProject() * 7 / 10)).setAttribute(" style='padding: 7px 2px 7px 2px; text-align: right' "));
 				row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryRealTimeSalaryEachProject() * 3 / 10)).setAttribute(" style='padding: 7px 2px 7px 2px; text-align: right' "));
 			}else{
 				row.addCell(new Cell("-"));
 				row.addCell(new Cell("-"));
 			}
 			row.addCell(new Cell("[" + data.getSalaryProjectCode() + "] " + data.getSalaryProjectName()).setAttribute(" style='padding: 7px 2px 7px 2px; "+isExceedStyle+" overflow: hidden; text-overflow: ellipsis; white-space: nowrap; text-align: left'"));
 			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryRealTimeSalaryEachProject())).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: right'  "));
 			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryCumulativeRealTimeSalary())).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: right'  "));
 			/* row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryBudgetEachProject())).setAttribute("  style='padding: 7px 2px 7px 2px; text-align: right'  ")); */
			tableGrid.addRow(row); 
		}
	}else{
		row = new Row(); 
		row.addCell(new Cell("검색 결과가 존재하지 않습니다. ").setAttribute(" align='center' colspan='8' " ));
		tableGrid.addRow(row); 
	}
	tableGrid.tableCheck(); 
	
%>

<script type="text/javascript">

	function deleteWork(){
		if(confirm("삭제 하시겠습니까?")) {
			var sFrm = document.forms["generalSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm,
					{	'url': "/action/ProjectARExpenseSanctionAction.do?mode=deleteARExpenseSanction",
						'onSuccess':function(obj){
							var res = eval('(' + obj.responseText + ')');
							if(res.result == 'SUCCESS'){alert('삭제하였습니다.');
								document.generalSanctionForm.target = "";
								document.generalSanctionForm.action = "/action/WorkCabinetAction.do?mode=getMyWorkList";
								document.generalSanctionForm.submit();								
							}		
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("삭제할 수 없습니다.");
						}
					}
			);	 
		}
	}

	function viewDetail(ssn){
		var url="http://intranet.kmac.co.kr/kmac/projectexpert/IndividualProjectSchedule.asp?mm=&gubun=M&ssn="+ssn;
		//alert(url);
		window.open(url, 'preportList', 'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=820,height=590,directories=no,menubar=no');
	}
	
	 var doubleSubmitFlag = false;
	 function doubleSubmitCheck(){
		if(doubleSubmitFlag){
			return doubleSubmitFlag;
		}else{
			doubleSubmitFlag = true;
			return false;
	 	}
	}
	function draftRequest(){
			var ActionURL;
			if($("subject").value == ""){
				alert('제목을 입력하세요.');
				document.generalSanctionForm.subject.focus();
				return;
			}
			if(document.generalSanctionForm.state.value == "SANCTION_STATE_DRAFT"){
				$('isApproved_1').value= 'Y';
				ActionURL = "/action/ProjectARExpenseSanctionAction.do?mode=createARExpenseSanction";
			}else{
				ActionURL = "/action/ProjectARExpenseSanctionAction.do?mode=executeARExpenseSanction";
				<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' && sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT' }">
					if(generalSanctionForm.isApproved[0].checked == false && generalSanctionForm.isApproved[1].checked == false){
						alert('승인여부를 선택하세요.');return;
					}
				</c:if>
			}
			if(doubleSubmitCheck()) return;
			var sFrm = document.forms["generalSanctionForm"];
			var status = AjaxRequest.submit(
					sFrm, 
					{	'url':ActionURL,
						'onSuccess':function(obj){
							alert('등록하였습니다.');
							if($('state').value == "SANCTION_STATE_DRAFT"){
								document.location = "/action/WorkCabinetAction.do?mode=getMyWorkListForMobile";
							}else{
								document.location = "/action/WorkCabinetAction.do?mode=getMyWorkListForMobile";
							}
						},
						'onLoading':function(obj){},
						'onError':function(obj){
							alert("저장할 수 없습니다.");
						}
					}
			); status = null;
	}

	var levelInfo;
	function openExpertPoolPopUp(_levelInfo) {
		levelInfo = _levelInfo;
		orgFinder_Open('setSanctionLevelInfo');	
	}
	
	function setSanctionLevelInfo(expertPoolList){
		if(levelInfo == "refMember"){
			expertPoolList.each(function(expertPool) {
				$('refMemberSsn').value = $('refMemberSsn').value + ($('refMemberSsn').value.length > 0 ? ", ": "") + expertPool.ssn;
				$('refMemberName').value = $('refMemberName').value + ($('refMemberName').value.length > 0 ? ", ": "") +  expertPool.name; 	
			});
		}else if(levelInfo == "entrust"){
			expertPoolList.each(function(expertPool) {
				$('entrustUserSsn').value = expertPool.ssn;
				$('entrustUserName').value = expertPool.name; 	
			});
		}else{
			var n=0;
			var snactionNameList = $$('input[sanctionInfo="name"]');
			var snactionSsnList = $$('input[sanctionInfo="ssn"]');
			var snactionDeptList = $$('input[sanctionInfo="dept"]');
			for (var i = 0; i < snactionNameList.length; ++i) {
				try{
					if(levelInfo <= snactionNameList[i].getAttribute("seq")){
						snactionNameList[i].value = expertPoolList[n].name;
						snactionSsnList[i].value = expertPoolList[n].ssn;
						snactionDeptList[i].value = expertPoolList[n].dept;
						n++;
					}
				}catch(e){}
			}
		}
	}

	function refTaskRequest(){
		var sFrm = document.forms["generalSanctionForm"];

		var status = AjaxRequest.submit(
				sFrm,
				{	'url': "/action/ProjectARExpenseSanctionAction.do?mode=executeRefWork",
					'onSuccess':function(obj){
						alert('등록하였습니다.');
						document.location = "/action/WorkCabinetAction.do?mode=getMyRefWorkList";
					},
					'onLoading':function(obj){},
					'onError':function(obj){
						alert("저장할 수 없습니다.");
					}
				}
		); status = null;		
	}
	function saveToExcel(){
		document.location = '/action/ProjectARExpenseSanctionAction.do?mode=saveToExcel&workId='+document.generalSanctionForm.taskId.value;
	}
	
	function canSanction(){
		var from = <c:out value="${canSanction.data1}" />;
		var to =  <c:out value="${canSanction.data2}" />;
		var toDate = <%= Calendar.getInstance().get(Calendar.DATE)%>;
		var dept = <%= session.getAttribute("dept") %>;
		
		if(dept == 9300 || dept == 9310 || dept == 9320 || dept == 9330 || dept == 9340 || dept == 9350 || dept == 9360 || dept == 9370 || dept == 9380 || dept == 9381 || dept == 2000 || dept == 2040){	
		} else {
			if(from > toDate && to < toDate){
				$('submitBtn').innerHTML = '[알림] 해당 월의 성과급 품의가 마감되었습니다. 기타 문의는 521로 연락 바랍니다.';		
			}
		}
	} 
	
	function exceedInfoClose(){
		$('exceedInfo').hide();
	}
	function reportExceedInfoClose(){
		$('reportExceedInfo').hide();
	}
	function exceedInfoShow(){
		$('exceedInfo').style.top = event.clientY-100;
		$('exceedInfo').style.left = 250;
		$('exceedInfo').show();
	}
	function reportExceedInfoShow(){
		$('reportExceedInfo').style.top = event.clientY-100;
		$('reportExceedInfo').style.left = 250;
		$('reportExceedInfo').show();
	}	
	function getExpertDivCode(deptCode) {
		switch (deptCode) {
		case "2110" : return "2701"; break;	// 경영전략
		case "2310" : return "2702"; break;	// 인사조직
		case "2150" : return "2703"; break;	// 경영품질
		case "2380" : return "2704"; break; // SCM
		case "2120" : return "2705"; break;	// CS경영
		case "2130" : return "2706"; break;	// 마케팅
		case "2160" : return "2707"; break;	// 생산혁신
		case "2390" : return "2708"; break;	// 기술경영
		case "2320" : return "2709"; break;	// L&D
		case "2360" : return "2710"; break;	// GBC
		default : return "";
		}
	}
	function jobClassJRealTimeSalary() {
		var year = $('year').value;
		var month = $('month').value;
		var deptCode = getExpertDivCode($('deptCode').value);	// CFO에 해당하는 전문가그룹 부서 코드 설정
		var url = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResult&readOnly=ON&jobClass=J&year="+year+"&month=" + month +"&deptCode="+deptCode;
		//alert(url);
		window.open(url,'realTimeSalary','top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=790,height=520,directories=no,menubar=no');
	}
	function showRealTimeSalaryByProject() {
		var salaryWin;
		var surl = "/action/ProjectExpenseAction.do?mode=realTimeProjectExpenseManagement";
		var sfeature = "top=0,left=50,width=800,height=610,scrollbars=yes";
		salaryWin = window.open(surl,"RealTime Salary by Project",sfeature);
		salaryWin.focus();
	}
	function showRealTimeSalaryByExpertGroup(year, month) {
		var salaryWin;
		var surl = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultByExpertGroup";
		surl += "&year=" + year + "&month=" + month;
		var sfeature = "top=0,left=50,width=800,height=610,scrollbars=yes";
		salaryWin = window.open(surl,"RealTime Salary by Expert Group",sfeature);
		salaryWin.focus();
	}

</script>
</head> 

<body onload="canSanction()"> 


<form id="generalSanctionForm" name="generalSanctionForm" method="post" enctype="multipart/form-data">
	<div id="hiddneValue" style="display: none;">
		<input type="text" id="taskId" name="taskId" value="<c:out value="${sanctionDoc.taskId}" />" />
		<input type="text" id="approvalType" name="approvalType" value="<c:out value="${sanctionDoc.approvalType}" />" />
		<input type="text" id="projectExpenseSeq" name="projectExpenseSeq" value="<c:out value="${sanctionDoc.seq}" />" /> 
		<input type="text" id="state" name="state" value="<c:out value="${sanctionDoc.state}" />" />
		<input type="text" id="year" name="year" value="<c:out value="${year}" />" />
		<input type="text" id="month" name="month" value="<c:out value="${month}" />" />
		<input type="text" id="assignDate" name="assignDate" value="<c:out value="${assignDate}" />" />
		<input type="text" id="deptCode" name="deptCode" value="<c:out value="${deptCode}" />" />
	</div>
	
		<!-- header -->
		<jsp:include page="/m/web/common/header.jsp" >
			<jsp:param value="fixed" name="data_position" />
		</jsp:include>
		<!-- // header -->
		
		<!-- topbar -->
		<div class="topbar">
	        <div>
	            <button type="button" class="btn arrowL" title="이전 페이지" onclick="history.back()"><i class="mdi mdi-arrow-left"></i></button>
	            <ul><p>성과급 품의하기</p></ul>
	        </div>
	    </div>
	    <!-- // topbar -->
		
		<div class="contents sub"><!-- 서브 페이지 .sub -->
			<div class="fixed_box">
				<div class="inner">   
               		<%@include file="/m/web/sanction/sanctionLineInfo.jsp" %>
               		 <div class="write">
               		 	<input type="hidden" name="approvalType" value="<c:out value="${sanctionTemplate.approvalType}"/>">
               		 	<input type="text" name="subject" id="subject" class="contentInput_left" style="width: 100%" 
						value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${year}"/>년 <c:out value="${month}"/>월 성과급 품의</c:when><c:otherwise><c:out value="${sanctionDoc.subject}"/></c:otherwise></c:choose>" readonly>
					</div>
				</div>
				<div class="tbl-box">
	               	<div class="tbl-head">
	               	   <p>성과급 내역</p>
	               	   <c:if test="${!readonly}">
	               	   		<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
	               	   			<ul>
		               	   			<li><button type="button" class="btn c-main" title="작성" onclick="draftRequest()"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
		               	   		</ul>
	               	   		</c:if>
	               	   </c:if>
	               	</div>
	                <table class="tbl-ty1">
	                    <colgroup>
	                        <col width="20%" />
	                        <col width="*" />
	                        <col width="20%" />
	                    </colgroup>
	                    <thead>
	                    	<th>이름</th>
	                    	<th>프로젝트</th>
	                    	<th>소계</th>
	                    </thead>
	                    <tbody>
	                   		<c:forEach var="rs" items="${expenselist}">
								<tr>
									<td><c:out value="${rs.salaryName }" /></td>
									<td style="text-align:left;">[<c:out value="${rs.salaryProjectCode}" />]&nbsp<c:out value="${rs.salaryProjectName }" /></td>
									<td style="text-align:right;"><fmt:formatNumber value="${rs.salaryRealTimeSalaryEachProject }" type="number"/></td>
								</tr>
							</c:forEach>
							<c:if test="${empty expenselist}">
								<td colspan="3">성과급 내역이 없습니다.</td>
							</c:if>
	                    </tbody>
	                 </table>
				</div>
			</div>
			
			<!-- 결재선택/의견작성 -->
			<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' }">
					<div style="display:none">
						 <div class="tbl-box">
		               	   	<div class="tbl-head">
		               	   		<p>승인/반려</p>
		               	   		<ul>
		               	   			<li><button type="button" class="btn c-main" title="작성" onclick="draftRequest();"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
		               	   		</ul>
		               	  	</div>
							<table class="tbl-ty1">
                      		<colgroup>
                         		<col width="33%" />
                          		<col width="*" />
                     		</colgroup>
                      		<tbody>
		                        <tr>
		                            <th>승인 여부</th>
		                            <td>
										<ul class="chek_ui" style="margin: -5 0 -5 0">
					                       <li>
					                           <input type="radio"  name="isApproved" id="isApproved_1" class="btn_radio" value="Y" checked />
					                           <label for="isApproved_1"><p>승인</p></label>       
					                       </li>
					                       <li>
					                           <input type="radio"  name="isApproved" id="isApproved_2" value="N" class="btn_radio" />
					                           <label for="isApproved_2"><p>반려</p></label>       
					                       </li>
					                   </ul>
									</td>
		                        </tr>
			                    <tr>
			                    	<th>의견 작성</th>
			                    	<td style="height: 130px;"><div style="padding: 0 0 10 0"><textarea name="rejectReason" id="rejectReason" class="textArea" style="width: 100%; height: 100px;" <c:if test="${readonly}">readonly</c:if>></textarea></div></td>
			                    </tr>
               				</tbody>	
           					</table>
						</div>
					</div>
					</c:when><c:otherwise>
					<c:if test="${!readonly }">
					<div class="tbl-box">
		               	   	<div class="tbl-head">
		               	   		<p>승인/반려</p>
		               	   		<ul>
		               	   			<li><button type="button" class="btn c-main" title="작성" onclick="draftRequest();"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
		               	   		</ul>
		               	  	</div>
							<table class="tbl-ty1">
                      		<colgroup>
                         		<col width="33%" />
                          		<col width="*" />
                     		</colgroup>
                      		<tbody>
		                        <tr>
		                            <th>승인 여부</th>
		                            <td>
										<ul class="chek_ui" style="margin: -5 0 -5 0">
					                       <li>
					                           <input type="radio"  name="isApproved" id="isApproved_1" class="btn_radio" value="Y" checked />
					                           <label for="isApproved_1"><p>승인</p></label>       
					                       </li>
					                       <li>
					                           <input type="radio"  name="isApproved" id="isApproved_2" value="N" class="btn_radio" />
					                           <label for="isApproved_2"><p>반려</p></label>       
					                       </li>
					                   </ul>
									</td>
		                        </tr>
			                    <tr>
			                    	<th>의견 작성</th>
			                    	<td style="height: 130px;"><div style="padding: 0 0 10 0"><textarea name="rejectReason" id="rejectReason" class="textArea" style="width: 100%; height: 100px;" <c:if test="${readonly}">readonly</c:if>></textarea></div></td>
			                    </tr>
               				</tbody>	
           					</table>
						</div>
						</c:if>
					</c:otherwise></c:choose>
	    </div>
			
		<div style="display: none;">
			<table class="listTable" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
				<c:forEach var="rs" items="${expenselist}">
							<input type="hidden" name="salaryType"						value="<c:out value="${rs.salaryType}"/>" >
							<input type="hidden" name="salaryDeptType"					value="<c:out value="${rs.salaryDeptType}"/>" >
							<input type="hidden" name="salaryJobClass"					value="<c:out value="${rs.salaryJobClass}"/>" >
							<input type="hidden" name="salaryJobClassDesc"				value="<c:out value="${rs.salaryJobClassDesc}"/>" >
							<input type="hidden" name="salaryJobClassErp"				value="<c:out value="${rs.salaryJobClassErp}"/>" >
							<input type="hidden" name="salaryYear"						value="<c:out value="${rs.salaryYear}"/>" >
							<input type="hidden" name="salaryMonth"						value="<c:out value="${rs.salaryMonth}"/>" >
							<input type="hidden" name="salaryName"						value="<c:out value="${rs.salaryName}"/>" >
							<input type="hidden" name="salarySsn"						value="<c:out value="${rs.salarySsn}"/>" >
							<input type="hidden" name="salaryTotalRealTimeSalary"		value="<c:out value="${rs.salaryTotalRealTimeSalary}"/>" >
							<input type="hidden" name="salaryProjectCode"				value="<c:out value="${rs.salaryProjectCode}"/>" >
							<input type="hidden" name="salaryProjectName"				value="<c:out value="${rs.salaryProjectName}"/>" >
							<input type="hidden" name="salaryPreportCount"				value="<c:out value="${rs.salaryPreportCount}"/>" >
							<input type="hidden" name="salaryRealTimeSalaryEachProject"	value="<c:out value="${rs.salaryRealTimeSalaryEachProject}"/>" >
							<input type="hidden" name="salaryDept"						value="<c:out value="${rs.salaryDept}"/>" >
							<input type="hidden" name="salarySeq"						value="<c:out value="${rs.salarySeq}"/>" >
							<input type="hidden" name="salaryResRate"					value="<c:out value="${rs.salaryResRate}"/>" >
							<input type="hidden" name="salaryCost"						value="<c:out value="${rs.salaryCost}"/>" >
							<input type="hidden" name="salaryMMValueRatio"				value="<c:out value="${rs.salaryMMValueRatio}"/>" >
							<input type="hidden" name="salaryIsHolding"					value="<c:out value="${rs.salaryIsHolding}"/>" >
							<input type="hidden" name="salaryIsMMExceed"				value="<c:out value="${rs.salaryIsMMExceed}"/>" >
							<input type="hidden" name="salaryCumulativeRealTimeSalary"	value="<c:out value="${rs.salaryCumulativeRealTimeSalary}"/>" >
							<input type="hidden" name="salaryBudgetEachProject"			value="<c:out value="${rs.salaryBudgetEachProject}"/>" >								
				</c:forEach>
			</table>
		</div>
					
					<!-- // 결재선택/의견작성 -->					
						<script type="text/javascript">
							function showEntrustInfo(){
								$('entrustInfo').style.top = document.body.scrollHeight-120;
								$('entrustInfo').style.left = 457;
								$('entrustInfo').show();
							}
							
							function entrustRequest(){
								if($F('entrustUserName') ==""){alert("위임자를 지정하시기 바랍니다.");return;}
								var sFrm = document.forms["generalSanctionForm"];
								var status = AjaxRequest.submit(
										sFrm, 
										{	'url': "/action/GeneralSanctionAction.do?mode=entrustGeneralSanction",
											'onSuccess':function(obj){
												alert('업무를 위임하였습니다.');
												document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
											},
											'onLoading':function(obj){},
											'onError':function(obj){
												alert("execution Fail");
											}
										}
								); status = null;			
							}	
						</script>
				</div>				
			</div>
		</div>
	</div>
</form>
</body>
</html>

<!-- 업무위임, 성과급 팝업 주석 -->
<%-- <div id="entrustInfo" 
			style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
			<table style="" cellpadding="3" cellspacing="3">
				<tr>
					<td><img alt="" src="/images/title_de02.jpg" align="top"><span style="subTitle"> <b>업무위임 요청</b></span></td>
				</tr>
				<tr>
					<td><table id="exceedInfoTable" width="290px">
						<thead id="exceedInfoTableHeader">
							<tr>
								<td class="detailTableTitle_center" width="27%">위임자</td>
								<td class="detailTableField_left" width="65%">
								<input type="hidden" id="entrustUserSsn" name="entrustUserSsn">
								<input type="Text" id="entrustUserName" name="entrustUserName" class="textInput_left" readonly></td>
								<td class="detailTableField_left" width="8%">
								<img alt="위임자 선택" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="openExpertPoolPopUp('entrust')"></td>
							</tr>
						</thead>
						<tbody id="exceedInfoTableBody">
						</tbody>
					</table></td>
				</tr>
				<tr align="right">
					<td colspan="2">
						<img alt="위임 실행" src="/images/btn_regist.jpg" onclick="entrustRequest()" style="cursor: hand;"/>
						<img alt="취소" src="/images/btn_close.jpg" onclick="$('entrustInfo').hide()" style="cursor: hand;"/>
					</td>
				</tr>
			</table>
		</div>
	</form>						
		
				
</body>

<div id="exceedInfo" 
	style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
	<table>
		<tr>
			<td height="3px" colspan="3"></td>
		</tr>
		<tr>
			<td width="3px"></td>
			<td>
				<table>
					<tr>
						<td><h4 class="subTitle">성과급 품의 알림</h4></td>
					</tr>
					<tr>
						<td height="5"></td>
					</tr>
					<table width="98%" align="left" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="2" align="left" bgcolor="#C0C0C0" colspan="5"></td>
						</tr>
						<tr>
							<td width="2" bgcolor="#C0C0C0"></td>
							<td colspan="3" height="5"></td>
							<td width="2" bgcolor="#C0C0C0"></td>
						</tr>
						<tr>
							<td width="2" bgcolor="#C0C0C0"></td>
							<td width="5"></td>
							<td>
								<strong>
									인건비 예산초과 컨설턴트 성과급이 <%=exceedCnt %>건</strong> 존재하여 성과급  품의가 불가능 합니다.<br>
									조치 후 품의하여 주시기 바랍니다<br><br>
									문의: 경영기획실 오영택 시니어(642)
							</td>
							<td width="5"></td>
							<td width="2" bgcolor="#C0C0C0"></td>
						</tr>
						<tr>
							<td width="2" bgcolor="#C0C0C0"></td>
							<td colspan="3" height="5"></td>
							<td width="2" bgcolor="#C0C0C0"></td>
						</tr>
						<tr>
							<td height="2" align="left" bgcolor="#C0C0C0" colspan="5"></td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr align="right">
							<td colspan="5" bgcolor='#F3F3F3'>
								<div class="btNbox pt10 pb10 txtR">
									<a class="btNa0a0a0 txt2btn" href="javascript:exceedInfoClose();" >닫기</a>
								</div>
							</td>
						</tr>
					</table>
				</table>
			</td>
			<td width="3px"</td>
		</tr>
		<tr>
			<td height="3px" colspan="3"></td>
		</tr>
	</table>
</div>
<div id="reportExceedInfo" 
	style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
	<table>
		<tr>
			<td height="3px" colspan="3"></td>
		</tr>
		<tr>
			<td width="3px"></td>
			<td>
				<table>
					<tr>
						<td><h4 class="subTitle">성과급 품의 알림</h4></td>
					</tr>
					<tr>
						<td height="5"></td>
					</tr>
					<table width="98%" align="left" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="2" align="left" bgcolor="#C0C0C0" colspan="5"></td>
						</tr>
						<tr>
							<td width="2" bgcolor="#C0C0C0"></td>
							<td colspan="3" height="5"></td>
							<td width="2" bgcolor="#C0C0C0"></td>
						</tr>
						<tr>
							<td width="2" bgcolor="#C0C0C0"></td>
							<td width="5"></td>
							<td>
								<strong>
									지도일지 가용일 초과 컨설턴트 성과급이 <%=reportExceedCnt %>건</strong> 존재하여 성과급  품의가 불가능 합니다.<br>
									조치 후 품의하여 주시기 바랍니다<br><br>
									문의: 경영기획실 오영택 시니어(642)
							</td>
							<td width="5"></td>
							<td width="2" bgcolor="#C0C0C0"></td>
						</tr>
						<tr>
							<td width="2" bgcolor="#C0C0C0"></td>
							<td colspan="3" height="5"></td>
							<td width="2" bgcolor="#C0C0C0"></td>
						</tr>
						<tr>
							<td height="2" align="left" bgcolor="#C0C0C0" colspan="5"></td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
						<tr align="right">
							<td colspan="5" bgcolor="#f3f3f3">
								<div class="btNbox pt10 pb10 txtR">
									<a class="btNa0a0a0 txt2btn" href="javascript:reportExceedInfoClose();" >닫기</a>
								</div>
							</td>
						</tr>
					</table>
				</table>
			</td>
			<td width="3px"</td>
		</tr>
		<tr>
			<td height="3px" colspan="3"></td>
		</tr>
	</table>
</div>
 --%>