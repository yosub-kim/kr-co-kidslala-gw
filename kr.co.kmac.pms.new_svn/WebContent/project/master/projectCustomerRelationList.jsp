<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>프로젝트 니즈발굴 연계 현황</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goProjectDetail(projectCode) {
	document.location.href = "/action/ProjectSearchAction.do"
		+"?mode=getProjectInfoTab&viewMode=projectSearch&projectCode="+projectCode;
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
	document.projectSearchListForm.action = "/action/ProjectSearchAction.do?mode=getProjectCustomerRelationList";
	document.projectSearchListForm.submit();
}
function goCustomerRelationInfo(projectCode) {		
	var url = "/action/ProjectMasterInfoAction.do?mode=getProjectCustomerInfo&projectCode="+projectCode+"&isAdmin=Y";
	window.open(url, 'CustomerInfo',
			'top=0,left=0,status=no,toolbar=no,scrollbars=yes,width=795,height=560,directories=no,menubar=no');
}
function doSearch() {
	document.projectSearchListForm.target = "";		
	document.projectSearchListForm.action = "/action/ProjectSearchAction.do?mode=getProjectCustomerRelationList";
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


<!-- <body leftmargin="15" topmargin="0" style="overflow-x:hidden;overflow-y:hidden">  -->
<body>
<%@ include file="/common/include/includeBodyTop.jsp"%>
	<form name="projectSearchListForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name="pageNo" value="<c:out value="${list.valueListInfo.pagingPage}"/>"> 
			<input type="hidden" name="projectCode" value="<c:out value="${projectCode}"/>" />
			<input type="hidden" name="delayState" value="<c:out value="${delayState}" />" />
			<input type="hidden" name="ssn" value="<c:out value="${ssn}" />" />
			<input type="hidden" name="runningPUCode" value="<c:out value="${runningPUCode}" />" />
			<input type="hidden" name="industryTypeCode" value="<c:out value="${industryTypeCode}" />" />
			<input type="hidden" name="costOver" value="<c:out value="${costOver}" />" />
			<input type="hidden" name="endProcess" value="<c:out value="${endProcess}" />" />
			<input type="hidden" name="etcCostOver" value="<c:out value="${etcCostOver}" />" />
			<input type="hidden" name="payCostOver" value="<c:out value="${payCostOver}" />" />
		</div>
		
		<table cellpadding="0" cellspacing="0">
			<!-- 타이틀 영역 -->
			<tr>
				<td height="12">
					<% String back = request.getParameter("backButtonYN"); %>
					<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
						<jsp:param name="title" value="프로젝트  니즈발굴 연계 현황" />
						<jsp:param name="backButtonYN" value="<%=back %>" />
					</jsp:include>
				</td>
			</tr>
			
			<!-- 검색 영역 -->
			<tr>
				<td height="21" align="left" valign="top">
					<%@ include file="/common/include/searchBox_Header.jsp"%>
					<table border="0" width="100%" style="border-collapse: collapse; ">
						<colgroup> 
							<col width="90px">
							<col width="110px">
							<col width="80px">
							<col width="100px">
							<col width="100px"> 
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
								</SELECT>								
							</td>
							<td class="searchTitle_center">상태</td>
							<td class="searchField_center">
								<code:codelist tableName="PROJECT_STATE" attribute=" name='projectState' class='selectbox' style='width:99%;' " isSelectBox="Y" all="Y" selectedInfo="${projectState}" />
							</td>
							<td class="searchTitle_center">연계 여부</td>
							<td class="searchField_center">
								<SELECT name="cnt" class="selectbox" style="width: 100%;">
									<option value="" <c:if test="${cnt==''}">selected</c:if>>전체</option>
									<option value="Y" <c:if test="${cnt=='Y'}">selected</c:if>>예</option>
									<option value="N" <c:if test="${cnt=='N'}">selected</c:if>>아니오</option>
								</SELECT>
							</td>
						</tr>
						<tr>
							<td class="searchTitle_center">조직단위별</td>
							<td class="searchField_center">
								<org:divList enabled="1" depth="1" attribute=" name='runningDivCode' class='selectbox' style='width:100pt' " 
									divType="A" all="Y" isSelectBox="Y" selectedInfo="${runningDivCode}"></org:divList>
							</td>
							<td class="searchTitle_center">PJ 연도</td>
							<td class="searchField_center">
								<date:year beforeYears="1" afterYears="0" hasAll="Y" attribute=" name='pjtYear' class='selectbox' style='width:*' " selectedInfo="${pjtYear}" />								
							</td>
							<td class="searchTitle_center">프로젝트명</td>
							<td class="searchField_center">
								<input type="text" name="projectName" value="<c:out value="${projectName}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</td>
						</tr>									
					</table>
					<%@ include file="/common/include/searchBox_Footer.jsp"%>
				</td>
			</tr>
						

			<!-- 본문 리스트 시작 -->			
			<tr>
				<td align="left" valign="top">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<!-- 페이지 정보, 버튼 -->			
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="18" width="*"><div><img src="/images/sub/line3Blit.gif" alt=""> <span style="font-weight: bold; color: #e74c3a;"> <c:out value="${list.valueListInfo.totalNumberOfEntries}"/> </span><span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/> </span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span></div></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table class="clear tableStyle01 mt15 plusStyle">
									<thead>
										<tr height="25px">
											<th width="65px">상태</th>
											<th width="70px">Biz 구분</th>
											<th width="70px">조직단위</th>
											<th width="*">프로젝트 명</th>
											<th width="70px">종료일</th>
											<th width="50px">PM</th>
											<th width="50px">니즈발굴건수</th>
											<th width="30px">진행정보</th>
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
																		<img alt="리뷰지연정보" src="/images/redAlert.jpg" align="absmiddle" style="cursor: hand;" onclick="delayInfoShow('<c:out value="${rs.projectCode}" />');"></c:when>
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
																	<c:otherwise><font color="#FF0000">일정지연</font></c:otherwise>
																</c:choose> 
															</c:otherwise>
														</c:choose></td>
														<td align="center"><c:out value="${rs.businessTypeCodeName}" /></td>
														<td align="center"><c:out value="${rs.runningDivCodeName}" /></td>
														<td class="myoverflow" <c:if test="${rs.costOver == 'Y'}"> bgcolor="#ffdfff" </c:if>>[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></td>
														<td align="center">
															<fmt:parseDate value="${rs.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
															<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="formattedFrom"/>
															<c:out value="${formattedFrom}" />
														</td>
														<td align="center"><c:out value="${rs.plSsn}" /></a></td>
														<td align="center"><a href="#" onclick="goCustomerRelationInfo('<c:out value="${rs.projectCode}" />')"><c:out value="${rs.boardArticleCount}" /></a></td>
														<td align="center"><c:if test="${rs.projectState > '2'}"><img alt="진행정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="goProjectDetail('<c:out value="${rs.projectCode}" />')"></c:if></td> 
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
								<table border="0" cellspacing="0" cellpadding="0">
									<tr height='30'>
										<td width="100%" align="center">
											<SCRIPT LANGUAGE="JavaScript"> 
												document.write( makePageHtml( 
														<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
														15, 
														<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														15)
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
<%@ include file="/common/include/includeBodyBottom.jsp"%>
</body>
<div id="delayInfo" 
	style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 3px;">
	<table style="" cellpadding="3" cellspacing="3">
		<tr>
			<td><img alt="" src="/images/title_de02.jpg" align="top"><span style="subTitle"> <b>지연정보 상세</b></span></td>
		</tr>
		<tr>
			<td><table id="delayInfoTable" width="290px">
				<thead id="delayInfoTableHeader">
					<tr>
						<td class="detailTableTitle_center" width="45%">지연단계</td>
						<td class="detailTableTitle_center" width="25%">담당자</td>
						<td class="detailTableTitle_center" width="30%">요청일</td>
						<!-- 
						<td class="detailTableTitle_center" width="25%">확인일</td>
						-->
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
</html>