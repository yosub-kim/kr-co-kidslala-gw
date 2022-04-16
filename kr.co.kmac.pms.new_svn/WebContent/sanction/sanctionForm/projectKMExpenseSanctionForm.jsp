<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%>
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
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<%
	//리스트 오브젝트 
	List<ExpenseRealTimeResultDetailForSanction> list = (List)request.getAttribute("expenselist");
	//테이블 그리드 생성
	TableGrid tableGrid = new TableGrid();
	tableGrid.setAttribute(" class='listTable' cellpadding='0' cellspacing='0' width='765' style='table-layout: fixed;' ");
	tableGrid.setHeadAnnex(false); // true 이면 Header Cell 병합
	tableGrid.setAnnex(true); // true 이면 Cell 병합

	//헤더 정의 
	Row headerRow = new Row();
	headerRow.setAttribute(" style='height:38px;' ");
	headerRow.addCell(new Cell("구분").setAttribute(" align='center' width='55' "));
	headerRow.addCell(new Cell("이름").setAttribute(" align='center' width='60' "));
	headerRow.addCell(new Cell("주민번호").setAttribute(" align='center' width='60' "));
	headerRow.addCell(new Cell("금액").setAttribute(" align='center' width='70' "));
	headerRow.addCell(new Cell("프로젝트").setAttribute(" align='center' width='*' "));
	headerRow.addCell(new Cell("성과<br>요율").setAttribute(" align='center' width='33' "));
	headerRow.addCell(new Cell("기준금액").setAttribute(" align='center' width='65' "));
	headerRow.addCell(new Cell("지도율").setAttribute(" align='center' width='45' "));
	headerRow.addCell(new Cell("PJT소계<br>(성과급)").setAttribute(" align='center' width='65' "));	
	tableGrid.addHeadRow(headerRow);
	
	Row row = null;
	if(list != null && list.size() > 0){ 
		for(ExpenseRealTimeResultDetailForSanction data : list){
			row = new Row().setAttribute(" row='salary' onmouseover='row_over(this)' onmouseout='row_out(this)' ");
			row.addCell(new Cell(data.getSalaryJobClassDesc()).setAttribute(" align='center' "));
			row.addCell(new Cell(data.getSalaryName()).setAttribute(" align='center' "));
			row.addCell(new Cell(data.getSalarySsn().substring(0,6) + "-*******").setAttribute(" align='center' "));
			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryTotalRealTimeSalary())).setAttribute(" align='right' "));
			row.addCell(new Cell("[" + data.getSalaryProjectCode() + "]" + data.getSalaryProjectName()).setAttribute(" style='overflow: hidden; text-overflow: ellipsis; white-space: nowrap;'  "));
			row.addCell(new Cell(data.getSalaryResRate()).setAttribute("  align='center'  "));
			row.addCell(new Cell(StringUtil.double2Money(data.getSalaryCost())).setAttribute("  align='right'  "));
			row.addCell(new Cell(data.getSalaryPreportCount()).setAttribute("  align='center'  "));
			row.addCell(new Cell(StringUtil.longt2Money(data.getSalaryRealTimeSalaryEachProject())).setAttribute("  align='right' style='cursor:hand;'  onclick='goDetailPage(\""+data.getSalaryProjectCode()+ "\",\""+data.getSalarySsn().substring(0,6)+data.getSalarySsn().substring(7,14)+"\")'  "));			
			tableGrid.addRow(row); 
		}
	}else{
		row = new Row(); 
		row.addCell(new Cell("검색 결과가 존재하지 않습니다. ").setAttribute(" align='center' colspan='9' " ));
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
					{	'url': "/action/ProjectKMExpenseSanctionAction.do?mode=deleteKMExpenseSanction",
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
		
		var selectedExpert = $$('tr[row="salary"]');
		if(selectedExpert.length < 1){
			alert("품의할 성과급 내역이 없습니다.");
			return;
		}
		
		var ActionURL;
		if($("subject").value == ""){
			alert('제목을 입력하세요.');
			document.generalSanctionForm.subject.focus();
			return;
		}
		if($F('cioSsn') == ""){
			alert("승인자를 지정하세요.");return;
		} 		  
		if($('state').value == "SANCTION_STATE_DRAFT"){
			$('isApproved').value= 'Y';
			ActionURL = "/action/ProjectKMExpenseSanctionAction.do?mode=createKMExpenseSanction";
		}else{
			ActionURL = "/action/ProjectKMExpenseSanctionAction.do?mode=executeKMExpenseSanction";
			/* <c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' && sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT' }">
				if(generalSanctionForm.isApproved[0].checked == false && generalSanctionForm.isApproved[1].checked == false){
					alert('승인여부를 선택하세요.');return;
				}
			</c:if> */
			<!-- Missing start tag for "c:choose" -->
			<div style="display: none;">
			<c:choose><c:when>
			</div>
			
		}
		if(doubleSubmitCheck()) return;
		var sFrm = document.forms["generalSanctionForm"];
		var status = AjaxRequest.submit(
				sFrm, 
				{	'url':ActionURL,
					'onSuccess':function(obj){
						alert('등록하였습니다.');
						if($('state').value == "SANCTION_STATE_DRAFT"){
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
						}else{
							document.location = "/action/WorkCabinetAction.do?mode=getMyWorkList";
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
				{	'url': "/action/ProjectKMExpenseSanctionAction.do?mode=executeRefWork",
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
	function goDetailPage(projectCode, ssn, isSanction) {
		document.generalSanctionForm.target = "";
		var actionURL = "";

		if($("readySanction").value == ""){
			actionURL = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultDetail&projectCode="+projectCode+"&ssn="+ssn+"&isSanction=ING&sanctionState="+$('state').value;
		} else {
			actionURL = "/action/RealTimeProjectExpenseAction.do?mode=retrieveExpenseRealTimeResultDetail&projectCode="+projectCode+"&ssn="+ssn+"&readySanction=Y";
		}
		document.generalSanctionForm.action = actionURL;
		document.generalSanctionForm.submit();
		return;	
	}
	function saveToExcel(){
		document.location = '/action/ProjectKMExpenseSanctionAction.do?mode=saveToExcel&workId='+document.generalSanctionForm.taskId.value;
	}
</script>
</head>

<body>

<%@ include file="/common/include/includeBodyTop.jsp"%>

<form name="generalSanctionForm" method="post" enctype="multipart/form-data">
	<div id="hiddneValue" style="display: none;">
		<input type="text" name="taskId" value="<c:out value="${sanctionDoc.taskId}" />" />
		<input type="text" name="approvalType" value="<c:out value="${sanctionDoc.approvalType}" />" />
		<input type="text" name="projectExpenseSeq" value="<c:out value="${sanctionDoc.seq}" />" /> 
		<input type="text" name="state" value="<c:out value="${sanctionDoc.state}" />" />
		<input type="text" name="year" value="<c:out value="${year}" />" />
		<input type="text" name="month" value="<c:out value="${month}" />" />
		<input type="text" name="assignDate" value="<c:out value="${assignDate}" />" />
		<input type="text" name="deptCode" value="<c:out value="${deptCode}" />" />
		<input type="text" name="readySanction" value="<c:out value="${readySanction}" />" />
	</div>


		<table width="765" cellpadding="0" cellspacing="0">
			<!-- SPACER -->
			<tr>
				<td width="765" height="8">&nbsp;</td>
			</tr>

			<tr>
				<td>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="전문가 성과급 품의" />
						<jsp:param name="backButtonYN" value="Y" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td height="10"></td>
			</tr>		
		
 			<%@include file="/sanction/common/sanctionLineInfo.jsp" %>			 
						
							
			<!-- sub 타이틀 영역 시작--> 
			<tr>
				<td>
					<table width="765" height="20" border="0" cellpadding="0" cellspacing="0">
						<tr><td height='10'></td></tr>
						<tr>
							
							<td width="100%" align="left" valign="bottom"><span class="subTitle">&nbsp;기안 내용</span></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- sub 타이틀 영역 종료--> 		
										
							
							
							
			<tr>
				<td>		
					<table width="765" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100" class="detailTableTitle_center">결재 유형</td>
							<td width="665" class="detailTableField_left">
								<c:out value="${sanctionTemplate.approvalName}" />
								<input type="hidden" name="approvalType" value="<c:out value="${sanctionTemplate.approvalType}"/>"></td>
						</tr>
						<tr>
							<td width="100" class="detailTableTitle_center">제목</td>
							<td width="665" class="detailTableField_left"><input type="text" name="subject" class="textInput_left" style="width: 100%" 
							value="<c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${year}"/>년 <c:out value="${month}"/>월 성과급 품의</c:when><c:otherwise><c:out value="${sanctionDoc.subject}"/></c:otherwise></c:choose>" readonly></td>
						</tr>
						<tr>
							<td height="20" colspan="2"></td>
						</tr>
						<tr>
							<td height="2" align="left" bgcolor="#907e63" colspan="2"></td>
						</tr>	
						<tr>
							<td colspan="2">
								<% out.println(tableGrid.getTotalHtml());%>
								<div style="display: none;">
									<table class="listTable" cellpadding="0" cellspacing="0" width="765" style="table-layout: fixed;">
										<c:forEach var="rs" items="${expenselist}">
											<tr>
												<td>
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
													<input type="hidden" name="salaryCost"						value="<c:out value="${rs.salaryCost}"/>" >
													<input type="hidden" name="salaryResRate"					value="<c:out value="${rs.salaryResRate}"/>" >
												</td>
											</tr>										
										</c:forEach>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td height="2" align="left" bgcolor="#907e63" colspan="2"></td>
						</tr>							
					</table>
				</td>
			</tr>				


			<%@include file="/sanction/common/sanctionApproveInfo.jsp" %>
					

						
		<!-- 버튼부분-->
			<tr>
				<td height='7'></td>
			</tr>
			<c:if test="${!readonly}"> 
				<tr>
					<td>
						<table width='765' height='32' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
							<tr>
								<td align='right'>
									<img alt="등록요청" 	src="/images/btn_regist_request.jpg" border="0" onclick="draftRequest()" style="cursor: hand;">
									<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
										<img alt="업무위임" src="/images/btn_mandate.jpg" border="0" onclick="showEntrustInfo();" style="cursor: hand;">
									</c:if>
									<c:if test="${sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT'}">
										<img alt="업무삭제" 		src="/images/btn_delete.jpg" border="0" onclick="deleteWork()" style="cursor: hand;">
										<img alt="임시저장" src="/images/btn_tempor_save.jpg" border="0" onclick="tempSaveWork()" style="cursor: hand;">
									</c:if>
									<c:if test="${sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}">
										<img alt="excel저장" src="/images/btn_excel.jpg" border="0" onclick="" style="cursor: hand;">
									</c:if>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</c:if>
			<c:if test="${readonly && isRefWork}"> 
				<tr>
					<td>
						<table width='765' height='32' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">
									<img alt="등록요청" 	src="/images/btn_regist_request.jpg" border="0" onclick="refTaskRequest()" style="cursor: hand;">
									<c:if test="${sanctionDoc.state == 'SANCTION_STATE_COMPLETE'}">
										<img alt="excel저장" src="/images/btn_excel.jpg" border="0" onclick="saveToExcel()" style="cursor: hand;">
									</c:if>
								</td>
							</tr>
						</table>
					</td>
				</tr>			
			</c:if>			
		<!-- 버튼종료-->								
		</table>					
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
		<div id="entrustInfo" 
			style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
			<table style="" cellpadding="3" cellspacing="3">
				<tr>
					<td><img alt="" src="/images/title_de02.jpg" align="top"><span style="subTitle"> <b>업무위임 요청</b></span></td>
				</tr>
				<tr>
					<td><table id="delayInfoTable" width="290px">
						<thead id="delayInfoTableHeader">
							<tr>
								<td class="detailTableTitle_center" width="27%">위임자</td>
								<td class="detailTableField_left" width="65%">
								<input type="hidden" id="entrustUserSsn" name="entrustUserSsn">
								<input type="Text" id="entrustUserName" name="entrustUserName" class="textInput_left" readonly></td>
								<td class="detailTableField_left" width="8%">
								<img alt="위임자 선택" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="openExpertPoolPopUp('entrust')"></td>
							</tr>
						</thead>
						<tbody id="delayInfoTableBody">
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
		
				
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
</html>
