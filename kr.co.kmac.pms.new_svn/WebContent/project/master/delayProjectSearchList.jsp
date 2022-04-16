<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>지연 프로젝트 현황</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function selectAll() {
	for (var i = 0; i < document.projectSearchListForm.elements.length; i++) {
		if (document.projectSearchListForm.elements[i].name == "id[]" && document.projectSearchListForm.elements[i].disabled==false) {				
			document.projectSearchListForm.elements[i].checked = document.projectSearchListForm.checkAll.checked;
		}
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
function popSMS() {
	var cntArry = [];
	var cntArryNoReddcy = [];
	var val = '';
	var msg = '';
	
	var projectCodeList = $$('input[name="id[]"]');
	var pmSsnList = $$('input[name="pmSsn[]"]');

	for (var i = 0; i < projectCodeList.length; ++i) {
		if(projectCodeList[i].checked){
			cntArry.push(pmSsnList[i].value);
		}
	}
	if (cntArry.length == 0) {	
		if (val == 'Y') {
			alert('SMS를 발송할 지연 프로젝트를 선택하지 않았습니다.');  
		} else {
			alert('프로젝트가 존재하지 않습니다.');  
		}
		return; 			
	} else {
		cntArryNoReddcy = removeDuplicatesArray(cntArry)
		$('smsCnt').update("(SMS 수신인원 "+cntArryNoReddcy.length+"명)");
		$('delaySMS').show();			
	}
}



function removeDuplicatesArray(arr) {
	var tempArr = [];
	for (var i = 0; i < arr.length; i++) {
		if (tempArr.length == 0) {
			tempArr.push(arr[i]);
	    } else {
			var duplicatesFlag = true;
			for (var j = 0; j < tempArr.length; j++) {
				if (tempArr[j] == arr[i]) {
					duplicatesFlag = false;
					break;
				}
			}
			if (duplicatesFlag) {
				tempArr.push(arr[i]);
			}
		}
	}
	return tempArr;
}



function goProjectEndDetail(projectCode, businessTypeCode, processTypeCode, evalRole) {
	document.location.href = "/project/endProcess/projectEndInfoTab.jsp?viewMode=projectSearch&projectCode="+projectCode+"&businessTypeCode="+businessTypeCode+"&processTypeCode="+processTypeCode+"&evalRole="+evalRole;
}
function goPage(next_page) {
	document.projectSearchListForm.pageNo.value = next_page ;
	document.projectSearchListForm.target = "";		
	document.projectSearchListForm.action = "/action/ProjectSearchAction.do?mode=getDelayProjectSearchList";
	document.projectSearchListForm.submit();
}
function delayInfoClose(){
	$('delayInfo').hide();
}
function delayInfoShow(projectCode){
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
		    			}else if(delayInfo.taskLevel == 'SANCTION_STATE_ENDRIVIEW_VERIFICATE'){
			    			newCellArr[0].innerHTML = "리뷰지검증";
		    			}else{
		    				newCellArr[0].innerHTML = delayInfo.taskTypeName;
		    			}
		    			newCellArr[1].innerHTML = delayInfo.assigneeName;
		    			newCellArr[2].innerHTML = delayInfo.assignDate;
		    			//newCellArr[3].innerHTML = delayInfo.executeDate;
			    		newCellArr[0].className = "detailTableField_center";
			    		newCellArr[1].className = "detailTableField_center";
			    		newCellArr[2].className = "detailTableField_center";
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

</script> 
</head>


<body>
	<form name="projectSearchListForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo" value="<c:out value="${list.valueListInfo.pagingPage}"/>"> 
			<input type="hidden" name="delayState" value="<c:out value="${delayState}" />" />
			<input type="hidden" name="projectState" value="<c:out value="${projectState}" />" />
			<input type="hidden" name="ssn" value="<c:out value="${ssn}" />" />
			<input type="hidden" name="runningPUCode" value="<c:out value="${runningPUCode}" />" />
			<input type="hidden" name="industryTypeCode" value="<c:out value="${industryTypeCode}" />" />
			<input type="hidden" name="endGubun" value="<c:out value="${endGubun}" />" />
			<input type="hidden" name="endProcess" value="<c:out value="${endProcess}" />" />
			<input type="hidden" name="chartSsn" value="<c:out value="${chartSsn}" />" />
			<input type="hidden" name="businessTypeCode" value="<c:out value="${businessTypeCode}" />" />
			
		</div>
		
		<table width="765" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<% String back = request.getParameter("backButtonYN"); %>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="지연 프로젝트 현황" />
						<jsp:param name="backButtonYN" value="<%=back %>" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td height="10"></td>
			</tr>			
			
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td>
					<table width="765" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35px">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
										<img src="/images/sub/line3Blit.gif" alt="">
										<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
										<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
									</div>
								<%if(session.getAttribute("dept").equals("9360") || session.getAttribute("dept").equals("9362") || session.getAttribute("dept").equals("9381")){%>
									<div class="btNrighTdiv">
										<a class="btNe14f42 txt2btn" href="javascript:popSMS();">SMS발송</a>
									</div>
								<%} %>
								</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table width="765" class="listTable">
									<thead>
										<tr height="25px">
											<td width="62px">상태</td>
											<td width="70px">Biz 구분</td>
											<td width="70px">조직단위</td>
											<td width="*">프로젝트명</td>
											<td width="75px">종료일</td>
											<!-- <td width="40px">게시판</td>  -->
											<td width="65px">PM</td>
											<td width="35px"><input type="checkbox" class="checkbox" name="checkAll" onclick="javascript:selectAll();"></td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:choose>
											<c:when test="${!empty list.list}">
												<c:forEach var="rs" items="${list.list}">
													<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
														<td align="center"><c:choose>
															<c:when test="${rs.projectState == '1'}">등록절차</c:when>
															<c:when test="${rs.projectState == '2'}">품의중</c:when>
															<c:when test="${rs.projectState == '4'}">
																<c:choose>
																	<c:when test="${rs.delayState == 'ER'}"><font color="#FF0000">평가지연</font>
																		<img alt="평가지연정보" src="/images/redAlert.jpg" align="absmiddle" style="cursor: hand;" onclick="delayInfoShow('<c:out value="${rs.projectCode}" />');"></c:when>
																	<c:otherwise>평가</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${rs.projectState == '5'}">
																<c:choose>
																	<c:when test="${rs.delayState == 'RR'}"><font color="#FF0000">리뷰지연</font>
																		<img alt="리뷰지연정보" src="/images/redAlert.jpg" align="absmiddle" style="cursor: hand;" onclick="delayInfoShow('<c:out value="${rs.projectCode}" />');"></c:when>
																	<c:otherwise>리뷰</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${rs.projectState == '6'}">완료</c:when>
															<c:when test="${rs.projectState == '7'}">취소</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${rs.delayState == 'B'}">진행</c:when>
																	<c:when test="${rs.delayState == 'R'}"><font color="#FF0000">일정지연</font></c:when>
																	<c:otherwise><font color="#FF0000">평가대기</font></c:otherwise>
																</c:choose> 
															</c:otherwise>
														</c:choose></td>
														<td align="center"><c:out value="${rs.businessTypeCodeName}" /></td>
														<td align="center"><c:out value="${rs.runningDeptCodeName}" /></td>
														<td class="myoverflow" <c:if test="${rs.costOver == 'Y'}"> bgcolor="#ffdfff" </c:if>>[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></td>
														<td align="center">
															<fmt:parseDate value="${rs.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
															<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="formattedFrom"/>
															<c:out value="${formattedFrom}" />
														</td>
														<td align="center"><c:out value="${rs.pmSsn}" /></td>
														<td align="center">
															<input type="checkbox" class="checkbox" name="id[]" value="<c:out value="${rs.projectCode}" />" />
															<input type="hidden" name="pmSsn[]" value="<c:out value="${rs.plSsn}" />" />
															<input type="hidden" name="projectCode[]" value="<c:out value="${rs.projectCode}" />" />
														</td> 
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr><td align="center" colspan="8">검색된 데이터가 없습니다.</td></tr>
											</c:otherwise>
										</c:choose>
										
									</tbody>
								</table>
							</td>
						</tr>
						<%-- 페이징 영역 시작--%>
						<tr>
							<td align="left">
								<table width="765" border="0" cellspacing="0" cellpadding="0">
									<tr height='30'>
										<td width="100%" align="center">
											<SCRIPT LANGUAGE="JavaScript"> 
												document.write( makePageHtml( 
														<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
														15, 
														<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														150)
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
</body>
<div id="delayInfo" 
	style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
	<table cellpadding="3" cellspacing="3" width="100%">
		<tr>
			<td><img alt="" src="/images/title_de02.jpg" align="top"><span style="subTitle"> <b>지연정보 상세</b></span></td>
		</tr>
		<tr>
			<td><table id="delayInfoTable" width="100%">
				<thead id="delayInfoTableHeader">
					<tr>
						<td class="detailTableTitle_center" width="45%">지연단계</td>
						<td class="detailTableTitle_center" width="25%">담당자</td>
						<td class="detailTableTitle_center" width="30%">요청일</td>
					</tr>
				</thead>
				<tbody id="delayInfoTableBody">
				</tbody>
			</table></td>
		</tr>
		<tr align="right">
			<td colspan="2"><img alt="" src="/images/btn_close.jpg" onclick="delayInfoClose()" style="cursor: hand;"/></td>
		</tr>
	</table>
</div>
<div id="delaySMS" 
	style="position: absolute; background: white; display: none; 
	left: 350px; top: 60px;
	width: 200px; text-align: center; height: 300px;
	border-style: solid; border-color: gray; border-width: 2px; padding: 10px;">
	<table cellpadding="3" cellspacing="3" width="100%">
		<tr>
			<td>
				<h4 class="subTitle">
				<c:choose>
					<c:when test="${projectState eq '3' }">일정</c:when>
					<c:when test="${projectState eq '4' }">평가</c:when>
					<c:when test="${projectState eq '5' }">리뷰</c:when>
				</c:choose>지연 알림 SMS 발송
				</h4>
			</td>
		</tr>
		<tr>
			<td>
				<input type="hidden" id="callbackNo" name="callbackNo" value="02-3786-0642">
				<c:choose>
					<c:when test="${projectState eq '3' }">
					<textarea style="width: 100%; height: 150px;" name="smsMessage" id="smsMessage">안녕하십니까, 
귀하의 담당 프로젝트에 일정 지연 발생이 확인 되오니 신속한 조치 바랍니다.
- KMAC 경영기획실 -</textarea>
					</c:when>
					<c:when test="${projectState eq '4' }">
					<textarea style="width: 100%; height: 150px;" name="smsMessage" id="smsMessage">안녕하십니까, 
귀하의 담당 프로젝트에 평가 지연 발생이 확인 되오니 신속한 조치 바랍니다.
- KMAC 경영기획실 -</textarea>
					</c:when>
					<c:when test="${projectState eq '5' }">
					<textarea style="width: 100%; height: 150px;" name="smsMessage" id="smsMessage">안녕하십니까, 
귀하의 담당 프로젝트에 리뷰 지연 발생이 확인 되오니 신속한 조치 바랍니다.
- KMAC 경영기획실 -</textarea>
					</c:when>
				</c:choose>				
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