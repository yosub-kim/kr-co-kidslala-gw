<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<title>프로젝트 현황</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
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
}
function Page_OnLoad() {
	if (document.getElementById("isPrevDiv").value == "Y") {
		$('currDivList').hide();
		$('prevDivList').show();
		
	} else {
		$('currDivList').show();
		$('prevDivList').hide();
	}
}
function togglePrevDivList(obj) {
	if (obj.checked) {
		document.getElementById("isPrevDiv").value = "Y";
		$('currDivList').hide();
		$('prevDivList').show();
	} else {
		document.getElementById("isPrevDiv").value = "N";
		$('currDivList').show();
		$('prevDivList').hide();
	}
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
		           	var tdCount = $('delayInfoTableHeader').down('tr').childElements().size();


		           	for(var k=(table.rows.length-1);  k >= 1; k--){
		           		table.deleteRow(k);
		           	}
		           	
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
		</div>
		
		<table width="100%" cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<% String back = request.getParameter("backButtonYN"); %>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="프로젝트 전체 현황" />
						<jsp:param name="backButtonYN" value="<%=back %>" />
					</jsp:include>
				</td>
			</tr>
			
			<!-- 검색 영역 -->
			<tr>
				<td height="21" align="left" valign="top">
					<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table border="0" width="100%" style="border-collapse: collapse;">
						<colgroup> 
							<col width="80px">
							<col width="90px">
							<col width="80px">
							<col width="90px">
							<col width="90px"> 
							<col width="*">
						</colgroup>
						<tr>
							<td class="searchTitle_center">비즈니스타입</td> 
							<td class="searchField_center">
								<SELECT name='businessTypeCode' class='selectbox' style='width: 100%;'>
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
							</td>
							<td class="searchTitle_center">상태</td>
							<td class="searchField_center">
								<code:codelist tableName="PROJECT_STATE" attribute=" name='projectState' id='projectState' class='selectbox' style='width:100%;' " isSelectBox="Y" all="Y" selectedInfo="${projectState}" />
							</td>
							<td class="searchTitle_center">기간</td>
							<td class="searchField_center">
								<fmt:parseDate value="${realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
								<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
								<fmt:parseDate value="${realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
								<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
								<script>
									jQuery(function(){jQuery( "#realStartDate" ).datepicker({});});
									jQuery(function(){jQuery( "#realEndDate" ).datepicker({});});
								</script>
								<input type="text" name="realStartDate" id="realStartDate" readonly="readonly" size="6" value="<c:out value="${start}" />" />~<input 
								type="text" name="realEndDate" id="realEndDate" readonly="readonly" size="6" value="<c:out value="${end}" />" />
							</td>
						</tr>
						<tr>
							<td class="searchTitle_center">조직단위별</td>
							<td class="searchField_center">
								<div id="currDivList" style="display:none">
								<org:divList enabled="1" depth="2" attribute=" name='runningDeptCode' class='selectbox' style='width:100%' " 
									divType="A" all="Y" isSelectBox="Y" selectedInfo="${runningDeptCode}"></org:divList>
								</div>
								<div id="prevDivList" style="display:none">
								<select name="runningDivCode" class="selectbox" style="width:100%">
									<option value="" selected="">전체</option>
									<option value="7010" <c:if test="${runningDivCode=='7010'}">selected</c:if>>진단평가본부</option>
									<option value="7020" <c:if test="${runningDivCode=='7020'}">selected</c:if>>컨설팅1본부</option>
									<option value="7030" <c:if test="${runningDivCode=='7030'}">selected</c:if>>컨설팅2본부</option>
									<option value="7170" <c:if test="${runningDivCode=='7170'}">selected</c:if>>컨설팅3본부</option>
									<option value="7040" <c:if test="${runningDivCode=='7040'}">selected</c:if>>R&C1본부</option>
									<option value="7050" <c:if test="${runningDivCode=='7050'}">selected</c:if>>R&C2본부</option>
									<option value="7060" <c:if test="${runningDivCode=='7060'}">selected</c:if>>L&D본부</option>
									<option value="7100" <c:if test="${runningDivCode=='7100'}">selected</c:if>>PI센터</option>
									<option value="7130" <c:if test="${runningDivCode=='7130'}">selected</c:if>>에너지/환경센터</option>
									<option value="7180" <c:if test="${runningDivCode=='7180'}">selected</c:if>>GBP센터</option>
									<option value="7140" <c:if test="${runningDivCode=='7140'}">selected</c:if>>미디어센터</option>
									<option value="7150" <c:if test="${runningDivCode=='7150'}">selected</c:if>>경영기획실</option>
								</select>
								</div>
							</td>
							<td class="searchTitle_center">유형</td>
							<td class="searchField_center">
								<select name="projectTypeCode" class="selectbox" id="projectTypeCode" style="width: 100%;">
									<option value="" selected="">전체</option>
									<option value="MM" <c:if test="${projectTypeCode=='MM'}">selected</c:if>>수주성</option>
									<option value="ND" <c:if test="${projectTypeCode=='ND'}">selected</c:if>>기획성</option>
								</select>
							</td>
							</td>
							<td class="searchField_center">
								<select name="keywordType" class="selectbox" style="width:100%">
									<option value="PNAME" <c:if test="${keywordType=='PNAME'}">selected</c:if>>프로젝트명</option>
									<option value="PCODE" <c:if test="${keywordType=='PCODE'}">selected</c:if>>프로젝트코드</option>
									<option value="CUST" <c:if test="${keywordType=='CUST'}">selected</c:if>>고객사명</option>
								</select>
							</td>
							<td class="searchField_center">
								<input type="text" name="keyword" value="<c:out value="${keyword}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</td>
						</tr>
						<tr>
							<td colspan="6" class="searchField_left">
								<input type="checkbox" style="vertical-align:-2px;" name="prevDiv" id="prevDiv" value="Y" title="과거 부서명으로 검색 시 선택" onclick="togglePrevDivList(this);" <c:if test="${isPrevDiv == 'Y'}">checked</c:if>> 이전 조직단위명으로 프로젝트 검색
							</td>
						</tr> 					
					</table>
					<%@ include file="/common/include/searchBox_Footer.jsp"%>
				</td>
			</tr>
					
			<!-- 본문 리스트 시작 -->			
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0" >
						<tr>
							<td height="35px">
								<!-- 페이지 정보, 버튼 -->			
								<div class="btNbox pb15">
									<div class="btNlefTdiv">				
									<table cellSpacing="0" width="218%" border="0" style="table-layout:fixed">
									
									<!-- 일정지연 프로젝트 화면 추가_김요섭_20191025 -->
									<%-- <%if(session.getAttribute("dept").equals("9261")){ %>
														<td align="left" width="200" height="10">
															<img src="/images/sub/line3Blit.gif" alt="">
															<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
															<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
														</td>
														<td align="right" width="200" height="10">
															<a class="btN3fac0c txt2btn" href="javascript:openTip_1();"><strong>일정지연</strong></a>
															<a class="btN3fac0c txt2btn" href="javascript:openTip_2();"><strong>평가지연</strong></a>
															<a class="btN3fac0c txt2btn" href="javascript:openTip_3();"><strong>리뷰지연</strong></a>
														</td>
									<%} else {%> --%>
											<c:set var="test" value="${list.valueListInfo.totalNumberOfEntries}" />
											<td align="left" width="200" height="10">
													<img src="/images/sub/line3Blit.gif" alt="">
													<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
													<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
											</td>
										<%-- <%}%>	 --%>
										</table>				
									</div>
								</div>
							</td>
						</tr>
						<!-- 프로젝트 일정지연 화면 -->				
						<tr>
							<td>
								<table class="listTable" style="table-layout: fixed;">
									<thead>
										<tr height="25px">
											<td width="60px">상태</td>
											<td width="70px">Biz 구분</td>
											<td width="70px">조직단위</td>
											<td width="*">프로젝트명</td>
											<td width="80px">종료일</td>
											<td width="40px">PJT<br>게시판</td>
											<td width="40px">QM<br>게시판</td>
											<td width="35px">진행<br>정보</td>
											
											<!-- 일정지연 프로젝트 화면 추가_김요섭_20191025 -->
											<%-- <%if(session.getAttribute("dept").equals("9261")) { %>
											<c:choose>
												<c:when test="${list.valueListInfo.totalNumberOfEntries < 400 && rs.projectState != '6'}">
													<td width="50px">PM</td>
												</c:when>
												<c:otherwise>
													<td width="35px">종료<br>정보</td>
												</c:otherwise>
											</c:choose>
											<%} else {%> --%>
												<td width="35px">종료<br>정보</td>
											<%-- <% } %> --%>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:choose>
											<c:when test="${!empty list.list}">
												<c:forEach var="rs" items="${list.list}">
													<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
														<td style="padding: 7px 1px 7px 1px"><c:choose>
															<c:when test="${rs.projectState == '1'}">등록절차</c:when>
															<c:when test="${rs.projectState == '2'}">품의중</c:when>
															<c:when test="${rs.projectState == '4'}">
																<c:choose>
																	<c:when test="${rs.delayState == 'ER'}"><font color="#FF0000">평가지연</font>
																		<img alt="평가지연정보" src="/images/redAlert.png" align="absmiddle" style="cursor: hand;" onclick="delayInfoShow('<c:out value="${rs.projectCode}" />');"></c:when>
																	<c:otherwise>평가</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${rs.projectState == '5'}">
																<c:choose>
																	<c:when test="${rs.delayState == 'RR'}"><font color="#FF0000">리뷰지연</font>
																		<img alt="리뷰지연정보" src="/images/redAlert.png" align="absmiddle" style="cursor: hand;" onclick="delayInfoShow('<c:out value="${rs.projectCode}" />');"></c:when>
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
														<td><c:out value="${rs.businessTypeCodeName}" /></td>
														<td>
															<c:choose>
															<c:when test="${isPrevDiv == 'N' }"><c:out value="${rs.runningDeptCodeName}" /></c:when>
															<c:otherwise><c:out value="${rs.runningDivCodeName}" /></c:otherwise>
															</c:choose>
														</td>
														<td class="myoverflow" <c:if test="${rs.costOver == 'Y'}"> bgcolor="#ffdfff" </c:if>>[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></td>
														<td>
															<fmt:parseDate value="${rs.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
															<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="formattedFrom"/>
															<c:out value="${formattedFrom}" />
														</td>
														<td><a href="#" onclick="goProjectBBS('<c:out value="${rs.projectCode}" />','<c:out value="${rs.projectName}" />')"><c:out value="${rs.boardArticleCount}" /></a></td>
														<td><a href="#" onclick="goQMBBS('<c:out value="${rs.projectCode}" />','<c:out value="${rs.projectName}" />')"><c:out value="${rs.boardArticleCountQM}" /></a></td>
														<td><c:if test="${rs.projectState > '2'}"><img alt="진행정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="goProjectDetail('<c:out value="${rs.projectCode}" />')"></c:if></td> 
														
														<!-- 일정지연 프로젝트 화면 추가_김요섭_20191025 -->
														<%-- <%if(session.getAttribute("dept").equals("9261")) { %>
														<c:choose>
															<c:when test="${list.valueListInfo.totalNumberOfEntries < 400 && rs.projectState != '6'}">
																<td><c:out value="${rs.pmname}" /></td> 
															</c:when>
															<c:otherwise>
																<td><c:if test="${rs.projectState == '6'}"><img alt="종료정보" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="goProjectEndDetail('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.businessTypeCode}" />', '<c:out value="${rs.processTypeCode}" />', '<c:out value="${evalRole}" />')"></c:if></td>
															</c:otherwise>
														</c:choose>
														<% }else{ %> --%>
														<td><c:if test="${rs.projectState == '6'}"><img alt="종료정보" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="goProjectEndDetail('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.businessTypeCode}" />', '<c:out value="${rs.processTypeCode}" />', '<c:out value="${evalRole}" />')"></c:if></td>
														<%-- <% } %> --%>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr><td align="center" colspan="9">검색된 데이터가 없습니다.</td></tr>
											</c:otherwise>
										</c:choose>
										
									</tbody>
								</table>
							</td>
						</tr>
						<%-- 페이징 영역 시작--%>
						<tr>
							<td align="center">
								<table border="0" cellspacing="0" cellpadding="0">
									<tr height='30'>
										<td width="100%" align="center">
											<SCRIPT LANGUAGE="JavaScript"> 
												document.write( makePageHtml( 
														<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
														1000, 
														<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														1000)
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
			<td><h4 class="subTitle">지연정보 상세</h4></td>
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
		<tr>
			<td colspan="2">
				<div class="btNbox mt5 txtR">
					<a class="btNa0a0a0 txt2btn" href="javascript:delayInfoClose();">닫기</a>
				</div>	
			</td>
		</tr>
		<tr>
			<td height="5px"></td>
		</tr>
	</table>
</div>
</html>