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
var popupVal = 0;
function closePreport(){
	if(popupVal != 1){
		$("title").value = "";
		$("workContent").value = "";
		$("impoInstContent").value = "";
		$("nextPlan").value = "";
		$("consultantOpinion").value = "";
		$("requestContent").value = "";
		$("selWorkId").value = "";
		
		$('preportContent').hide();
		popupVal = 0;
	} 
}
function holdPreport(){
	popupVal = 1;
}
function showPreport(workId, writer, obj) {
	var elt         = $('preportContent');	 
	var y  = parent.document.viewport.getScrollOffsets().top + 20;
	//alert(parent.document.viewport.getScrollOffsets().top);
	var styles = { position : 'absolute',
		top      : y + 'px',
		left     : '100px' };	 
	elt.setStyle(styles);
	
	//if(popupVal != 1){
		//alert(obj.style.top);
		AjaxRequest.post (
			{	'url': "/action/ProjectReportContentAction.do?mode=getProjectReportContnetByWorkId",
				'parameters' : { "workId": workId},
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var fileNames = "";
					if(res.result == 'SUCCESS'){
						$("pjtName").value = res.projectReportContent.projectName;
						$("writeDate").value = res.projectReportContent.assignDate;
						$("writer").value = writer;
						$("title").value = res.projectReportContent.title;
						$("workContent").value = res.projectReportContent.workContent;
						$("impoInstContent").value = res.projectReportContent.impoInstContent;
						$("nextPlan").value = res.projectReportContent.nextPlan;
						$("consultantOpinion").value = res.projectReportContent.consultantOpinion;
						$("requestContent").value = res.projectReportContent.requestContent;
						if(res.fileList.size() > 0) {
							for(var i=0; i < res.fileList.size(); i++) {
								fileNames += res.fileList[i].attachFileName + " \n";
							}
							$("attachFile").value = fileNames;
						} else {
							$("attachFile").value = "";
						}
						
						$("selWorkId").value = workId;
					}
					elt.show();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("확인할 수 없습니다.");
				}
			}
		); 			
	//}
}
function execurePreportConfirm(){
	var payAmount = $$('select[name="payAmount"]');
	var appYN = $$('select[name="appYN"]');
	var chkYN = true
	for (var i = 0; i < appYN.length; i++) {
		if(appYN[i].value == 'Y' || appYN[i].value == 'N'){
			chkYN = false;
			break;
		}
	}
	if(chkYN){
		alert("승인/반려를 선택한 지도일지가 없습니다.");
		return;
	}
	for (var i = 0; i < appYN.length; i++) {
		if(appYN[i].value == 'Y'){
			if(payAmount[i].value == '-'){
				alert("일괄처리를 위해서는 " +(i+1)+"번째 지도일지의 요율을 선택해주십시오");
				return;
			}
		}
	}

	if(confirm("목록상에 있는 지도일지의 승인/반려가 일괄로 처리됩니다.\n진행하시겠습니까?")){
		var ActionURL = "/action/ProjectReportContentAction.do?mode=executeProjectReportTaskForList";
		var sFrm = document.forms["formExec"];
		var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('등록하였습니다.');
					document.location = "/action/ProjectReportCabinetAction.do?mode=getMyProjectReportList";
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
		); status = null;	
	}
}	

function selectWork(workId) {
	document.myWorkListForm.action = "/action/ProjectReportContentAction.do?mode=getProjectReport&workId="+workId;
	document.myWorkListForm.target = "";		
	document.myWorkListForm.submit();
}
function goPage(next_page) {
	document.myWorkListForm.pageNo.value = next_page ;
	document.myWorkListForm.pageSize.value = $("pageSizeSel").value
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/ProjectReportCabinetAction.do?mode=getMyProjectReportList";
	document.myWorkListForm.submit();
}
function doSearch() {
	document.myWorkListForm.projectName.value=document.form1.projectName.value;
	document.myWorkListForm.writerName.value=document.form1.writerName.value;
	document.myWorkListForm.pageSize.value = $("pageSizeSel").value
	document.myWorkListForm.target = "";		
	document.myWorkListForm.action = "/action/ProjectReportCabinetAction.do?mode=getMyProjectReportList";
	document.myWorkListForm.submit();
}
function preportWriteStatus(){
	location.href = "/action/ProjectMonthlyReportAction.do?mode=selectProjectReportList";
}
function changePayAmountAll(val){
	if(confirm("Rate를 일괄 적용하시겠습니까 ?")){
		var payAmount = $$('select[name="payAmount"]');
		for (var i = 0; i < payAmount.length; i++) {
			payAmount[i].value = val;
		}
	}
}
function changeAppYNAll(val){
	if(confirm("승인/반려를 일괄 적용하시겠습니까?")){
		var appYN = $$('select[name="appYN"]');
		for (var i = 0; i < appYN.length; i++) {
			appYN[i].value = val;
		}
	}
}
</script>
</head>

<body>
		<form name="myWorkListForm" method="post" style="display: none">
			<div style='display: none;'>
				<input type="hidden" name="pageNo">
				<input type="hidden" name="pageSize" id="pageSize" value="<c:out value="${list.valueListInfo.pagingNumberPer}" default="15"/>">
				<input type="hidden" name="projectName" id="projectName" value="<c:out value="${projectName}"/>">
				<input type="hidden" name="writerName" id="writerName" value="<c:out value="${writerName}"/>">
			</div>
		</form>

		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="내 프로젝트 레포트" />
					</jsp:include>
				</td>
			</tr>
			
			<!-- 검색 영역 -->
			<form name="form1" method="post">
			<tr>
				<td>
					<%@ include file="/common/include/searchBox_Header.jsp"%>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<colgroup> 
								<col width="100px">
								<col width="150px">
								<col width="100px">
								<col width="*">
							</colgroup>
							<tr>
								<td class="searchTitle_center">작성자명</td>
								<td class="searchField_left">
									<input type="text" name="writerName" id="writerName" value="<c:out value="${writerName}"/>" class="textInput_left" style="width: 100%">
								</td>
								<td class="searchTitle_center">프로젝트명</td>
								<td class="searchField_left">
									<input type="text" name="projectName" id="projectName" value="<c:out value="${projectName}"/>" class="textInput_left" style="width: 100%">
								</td>
							</tr>
						</table>
					<%@ include file="/common/include/searchBox_Footer.jsp"%>
				</td>
			</tr>
			</form>			
					
			
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35px">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
										<!-- <a class="btN3fac0c txt2btn" href="javascript:preportWriteStatus()">프로젝트 레포트 작성현황</a> -->
									</div>
									<div class="btNrighTdiv">
										<select id="pageSizeSel" name="pageSizeSel" onchange="javascript:doSearch();">
											<option <c:if test="${list.valueListInfo.pagingNumberPer eq '5' }">selected="selected"</c:if>>5</option>
											<option <c:if test="${list.valueListInfo.pagingNumberPer eq '15' }">selected="selected"</c:if>>15</option>
											<option <c:if test="${list.valueListInfo.pagingNumberPer eq '50' }">selected="selected"</c:if>>50</option>
											<option <c:if test="${list.valueListInfo.pagingNumberPer eq '100'}">selected="selected"</c:if>>100</option>
											<option <c:if test="${list.valueListInfo.pagingNumberPer eq '300'}">selected="selected"</c:if>>300</option>
											<option <c:if test="${list.valueListInfo.pagingNumberPer eq '500'}">selected="selected"</c:if>>500</option>
										</select>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<form name="formExec" method="post">
									<table class="listTable" style="table-layout: fixed">
										<thead>
											<tr>
												<td width="50px">상태</td>
												<td width="*">프로젝트명</td>
												<!-- <td width="85px">지도일</td> -->
												<%-- <td width="150px">작성자소속</td> --%>
												<td width="60px">작성자</td>
												<td width="85px">작성일</td>
											<%if(session.getAttribute("companyPosition").equals("08CF")
													|| session.getAttribute("companyPosition").equals("10TM")
													|| session.getAttribute("companyPosition").equals("09CJ")
													|| session.getAttribute("userId").equals("ocs573")
													|| session.getAttribute("userId").equals("leehk67")){%>	
												<td width="80px">Rate</td>
												<td width="80px">승인/반려<br/>
													<select name="appYNAll" onchange="changeAppYNAll(this.value)">
														<option value="N/A"	selected="selected">선택</option>
														<option value="Y"	>승인</option>
														<option value="N"	>반려</option>
													</select>
												</td>
												<td width="30px">미리보기</td>
											<%} %>
											</tr>
										</thead>			
										<tbody id="ListData">
											<c:if test="${!empty list.list}">
												<c:forEach var="rs" items="${list}">
													<tr>  
														<td><span id="state"><c:choose>
																<c:when test="${rs.level == 'SANCTION_STATE_DRAFT'}">작성</c:when>
																<c:when test="${rs.level == 'SANCTION_STATE_REJECT_DRAFT'}"><span class="bold colore74c3a">반려</span></c:when>
																<c:when test="${rs.level == 'SANCTION_STATE_REVIEW'}">검토</c:when>
																<c:when test="${rs.level == 'SANCTION_STATE_APPROVE'}">승인</c:when>
																<c:when test="${rs.level == 'SANCTION_STATE_COMPLETE'}">완료</c:when>
															</c:choose></span>	
														</td>
														<td class="myoverflow" style="vertical-align:middle; padding: 0px 5px;">
															<span id="projectName" onclick="selectWork('<c:out value="${rs.id}" />')" style="cursor: hand;">
																<c:out value="${rs.projectName}" />
															</span>
														</td>
														<%-- <td><fmt:parseDate value="${rs.assignDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/><span id="assignDate"><fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/></span></td> --%>
														<%-- <td class="myoverflowC"><span id="draftUserDept"><c:out value="${rs.draftUserDeptName}" /></span></td>--%>
														<td><span id="draftUserId"><c:out value="${rs.draftUserName}" /></span></td>
														<td><c:if test="${rs.level != 'SANCTION_STATE_DRAFT' && rs.level != 'SANCTION_STATE_REJECT_DRAFT'}"><span id="draftDate"><fmt:formatDate value="${rs.draftDate}" pattern="yyyy-MM-dd" /></span></c:if></td>
													<%if(session.getAttribute("companyPosition").equals("08CF")
															|| session.getAttribute("companyPosition").equals("10TM")
															|| session.getAttribute("companyPosition").equals("09CJ")
															|| session.getAttribute("userId").equals("ocs573")
															|| session.getAttribute("userId").equals("leehk67")){%>
														<td>
															<c:if test="${rs.level == 'SANCTION_STATE_APPROVE'}">
															 	<input type="hidden" name="workId" value="<c:out value="${rs.id}"/>"/>	
																<select name="payAmount">
																	<option value="-"	<c:if test="${rs.payAmount == '-' || rs.payAmount == ''}">selected="selected"</c:if>>선택</option>
																	<option value="0"	<c:if test="${rs.payAmount == '0'}">selected="selected"</c:if>>0</option>
																	<option value="0.5"	<c:if test="${rs.payAmount == '0.5'}">selected="selected"</c:if>>0.5</option>
																	<option value="1"	<c:if test="${rs.payAmount == '1'}">selected="selected"</c:if> >1.0</option>
																	<option value="1.5"	<c:if test="${rs.payAmount == '1.5'}">selected="selected"</c:if>>1.5</option>
																	<option value="2"	<c:if test="${rs.payAmount == '2'}">selected="selected"</c:if>>2.0</option>
																</select>
															</c:if>
														</td>
														<td>
															<c:if test="${rs.level == 'SANCTION_STATE_APPROVE'}">
																<select name="appYN">
																	<option value="N/A"	selected="selected">선택</option>
																	<option value="Y"	>승인</option>
																	<option value="N"	>반려</option>
																</select>
															</c:if>
														</td>
														<td style="vertical-align: middle; text-align: center; padding: 0px;">
															<c:if test="${rs.level == 'SANCTION_STATE_APPROVE'}">
																	<img id="previewIcon" alt="preview" src="/images/sub/preview.png" onmouseover="javascript:showPreport('<c:out value="${rs.id}" />', '<c:out value="${rs.draftUserName}" />',this);"
																		onclick="javascript:holdPreport()"
																		onmouseout="javascript:closePreport();" style="cursor: pointer;">
															</c:if>
														</td>
													<%} %>
													</tr>
												</c:forEach>
											</c:if>
											<c:if test="${empty list.list}">
											<%if(session.getAttribute("companyPosition").equals("08CF") 
													|| session.getAttribute("companyPosition").equals("10TM")
													|| session.getAttribute("companyPosition").equals("09CJ")
													|| session.getAttribute("userId").equals("ocs573")
													|| session.getAttribute("userId").equals("leehk67")){%>
												<td colspan="8">검색된 데이터가 없습니다. </td>
											<% } else { %>
												<td colspan="4">검색된 데이터가 없습니다. </td>
											<%} %>
											</c:if>										
										</tbody>
									</table>
								</form>
								
							</td>
						</tr>
						<!-- 본문 리스트 종료 -->
						<%if(session.getAttribute("companyPosition").equals("08CF") 
								|| session.getAttribute("companyPosition").equals("10TM")
								|| session.getAttribute("companyPosition").equals("09CJ")
								|| session.getAttribute("userId").equals("ocs573")
								|| session.getAttribute("userId").equals("leehk67")){%>
						<tr>
							<td>
								<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<div class="btNbox txtR">
												<a title="" class="btNe14f42 txt4btn" href="#" onclick="javascript:execurePreportConfirm();">지도일지 일괄처리</a>
											</div>
										</td>
									</tr>
								</table>								
							</td>
						</tr>
						<%} %>															
															
						<%-- 페이징 영역 시작--%>
						<tr>
							<td>
								<div class="paging">
											<SCRIPT LANGUAGE="JavaScript"> 
												document.write( makePageHtml( 
														<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
														<c:out value="${list.valueListInfo.pagingNumberPer}" default="15"/>, 
														<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														<c:out value="${list.valueListInfo.pagingNumberPer}" default="15"/>)
												) ;
											</SCRIPT>
								</div>
							</td>
						</tr>
						<%-- 페이징 영역 끝--%>
					</table>
				</td>
			</tr>
		</table>			
			
</body>
</html>

<div id="preportContent" style="width: 560px; display: none; position: absolute; left: 100px; top: 50px; background-color: white; border-width: 1px; border-style: solid; border-color: gray; padding: 5px; margin: 5px;">
	<input type="hidden" id="selWorkId">
	<table width="551" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<h4 class="subTitle mb5">지도일지 내용</h4>
			</td>
		</tr>
		<!-- sub 타이틀 영역 종료--> 	
		<tr>
			<td>		
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td width="80" class="detailTableTitle_center">프로젝트명</td>
						<td width="*" class="detailTableField_left"><input type="text" id="pjtName" class="contentInput_left" style="width: 100%"></td>
					</tr>
					<tr>
						<td width="80" class="detailTableTitle_center">지도일</td>
						<td width="*" class="detailTableField_left"><input type="text" id="writeDate" class="contentInput_left" style="width: 100%"></td>
					</tr>
					<tr>
						<td width="80" class="detailTableTitle_center">작성자</td>
						<td width="*" class="detailTableField_left"><input type="text" id="writer" class="contentInput_left" style="width: 100%"></td>
					</tr>
					<tr>
						<td width="80" class="detailTableTitle_center">제목</td>
						<td width="*" class="detailTableField_left"><input type="text" id="title" class="contentInput_left" style="width: 100%"></td>
					</tr>
					<tr>
						<td width="80" class="detailTableTitle_center">금번진행사항</td>
						<td width="*" class="detailTableField_left" style="height:200px"><textarea id="workContent" style="width: 100%; height: 98%; class="textArea"></textarea></td>
					</tr>
					<tr>
						<td width="80" class="detailTableTitle_center">중요조치사항</td>
						<td width="*" class="detailTableField_left" style="height:60px"><textarea id="impoInstContent" style="width: 100%; height: 96%; " class="textArea"></textarea></td>
					</tr>
					<tr>
						<td width="80" class="detailTableTitle_center">향후일정</td>
						<td width="*" class="detailTableField_left" style="height:60px"><textarea id="nextPlan" style="width: 100%; height: 96%; " class="textArea"></textarea></td>
					</tr>
					<tr>
						<td width="80" class="detailTableTitle_center">컨설턴트의견</td>
						<td width="*" class="detailTableField_left" style="height:60px"><textarea id="consultantOpinion" style="width: 100%; height: 96%; " class="textArea"></textarea></td>
					</tr>
					<tr>
						<td width="80" class="detailTableTitle_center">지원요구사항</td>
						<td width="*" class="detailTableField_left" style="height:60px"><textarea id="requestContent" style="width: 100%; height: 96%; " class="textArea"></textarea></td>
					</tr>
					<tr>
						<td width="80" class="detailTableTitle_center">첨부파일</td>
						<td width="*" class="detailTableField_left"><textarea id="attachFile" class="textArea" style="width: 100%; height: 96%;"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div class="btNbox txtR">
								<a class="btNe14f42 txt4btn" href="#" onclick="javascript:selectWork($('selWorkId').value)">상세보기</a>
								<a class="btNa0a0a0 txt4btn" href="#" onclick="javascript:popupVal=0;closePreport();">닫기</a>
							</div>
						</td>
					</tr>
				</table>								
			</td>
		</tr>
	</table>					
</div>
