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
<meta name="viewport" content="width=1600">
<script type="text/javascript" src="/js/contextMenu/contextMenu.js"></script> 
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goProjectDetail(projectCode, projectRole) {
	document.location.href = "/action/ProjectSearchAction.do"
		+"?mode=getProjectInfoTab&viewMode=myProject&projectCode="+projectCode+"&projectRole="+projectRole;
}
function goPage(next_page) {
	document.myProjectListForm.pageNo.value = next_page ;
	document.myProjectListForm.target = "";		
	document.myProjectListForm.action = "/action/MyProjectAction.do?mode=getMyProjectList";
	/* document.myProjectListForm.action = "/action/MyProjectAction.do?mode=getMyProjectList&businessTypeCode="+'<c:out value="${businessTypeCode }" />'+"&projectState="+'<c:out value="${projectState }" />'+"&pjtEndYear="+'<c:out value="${pjtEndYear }" />';
	 */document.myProjectListForm.submit();
}
function goProjectBBS(projectCode, projectName) {		
	document.myProjectListForm.target = "";		
	document.myProjectListForm.action = "/action/BoardAction.do?mode=selectList&bbsId="+projectCode+"&projectCode="+projectCode+"&projectName="+projectName;
	document.myProjectListForm.submit();
}
function goQMBBS(projectCode, projectName) {		
	document.myProjectListForm.target = "";
	document.myProjectListForm.action = "/action/BoardAction.do?mode=selectList2&bbsId="+projectCode+"&projectCode="+projectCode+"&projectName="+projectName;  
	document.myProjectListForm.submit();
}
function goProjectEndDetail(projectCode, businessTypeCode) {
	document.location.href = "/project/endProcess/projectEndInfoTab.jsp?viewMode=projectSearch&projectCode="+projectCode+"&businessTypeCode="+businessTypeCode;
}
function projectChange(type, projectCode, businessTypeCode) {
	var projectCode = projectCode;
	var businessTypeCode = businessTypeCode;

	if (type == '10') {//인력(상근,전문가,RA)
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/MemberChangeSanctionAction.do?mode=loadFormMemberChangeSanction&projectCode="+projectCode+"&approvalType=M";
		document.myProjectListForm.submit();
	} else if (type == '11') {//인력(엑스퍼트,강사,교수)
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/MemberChangeSanctionAction.do?mode=loadFormMemberChangeSanction&projectCode="+projectCode+"&approvalType=ME";
		document.myProjectListForm.submit();
	} else if (type == '14') {//인력(엑스퍼트)
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/MemberChangeSanctionAction.do?mode=loadFormMemberChangeSanction&projectCode="+projectCode+"&approvalType=MI";
		document.myProjectListForm.submit();
	} else if (type == '12') {//인력(교수)
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/MemberChangeSanctionAction.do?mode=loadFormMemberChangeSanction&projectCode="+projectCode+"&approvalType=MF";
		document.myProjectListForm.submit();
	} else if (type == '13') {//인력(교수)
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/MemberChangeSanctionAction.do?mode=loadFormMemberChangeSanction&projectCode="+projectCode+"&approvalType=MG";
		document.myProjectListForm.submit();
	} else if (type == '2') {//일정
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/ScheduleChangeSanctionAction.do?mode=loadFormScheduleChangeSanction&projectCode="+projectCode+"&approvalType=S";
		document.myProjectListForm.submit();
	} else if (type == '3') {//예산
		if (businessTypeCode != 'BTA' && businessTypeCode != 'BTD' && businessTypeCode != 'BTJ') {
			alert("예산변경품의는 컨설팅/진단/리서치/사내교육/수주연수 만 가능합니다.");
			return;
		}
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/BudgetChangeSanctionAction.do?mode=loadFormBudgetChangeSanction&projectCode="+projectCode+"&approvalType=draft14";
		document.myProjectListForm.submit();
	} else if (type == '4') {//프로젝트가지급
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/GeneralSanctionAction.do?mode=loadFormGeneralSanction&projectCode="+projectCode+"&approvalType=draft1";
		document.myProjectListForm.submit();
	} else if (type == '5') {//법인카드신청
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/GeneralSanctionAction.do?mode=loadFormGeneralSanction&projectCode="+projectCode+"&approvalType=draft2";
		document.myProjectListForm.submit();
	} else if (type == '6') {//사업관련품의
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/GeneralSanctionAction.do?mode=loadFormGeneralSanction&projectCode="+projectCode+"&approvalType=draft11";
		document.myProjectListForm.submit();
	} else if (type == '7') {//취소품의
		if (businessTypeCode == 'BTZ') {
			alert("공개교육, 기획성연수, 세미나, 컨퍼런스 는 기획사업 취소품의를 하시기 바랍니다.");
			return;
		}
		document.myProjectListForm.target = "";
		document.myProjectListForm.action = "/action/ProjectDropSanctionAction.do?mode=loadFormProjectDropSanction&projectCode="+projectCode+"&approvalType=PC";
		document.myProjectListForm.submit();
	}
}

function doSearch() {
	document.myProjectListForm.target = "";		
	document.myProjectListForm.action = "/action/MyProjectAction.do?mode=getMyProjectList";
	document.myProjectListForm.submit();
}
function delayProjectTaskInfoClose(){
	$('delayProjectTaskInfo').hide();
}
function delayProjectTaskInfoShow(state, projectCode){
	$('delayProjectTaskInfo').style.top = event.clientY;
	$('delayProjectTaskInfo').show();

	AjaxRequest.post (
			{	'url': "/action/ProjectSearchAction.do?mode=getProjectTaskDelayInfo",
				'parameters' : { "projectCode": projectCode}, 
				'onSuccess':function(obj){
					var res = eval('(' + obj.responseText + ')');
					var rsObj = res.projectTaskDelayInfo;
					
					$('activityName').innerHTML = rsObj[0].taskName;
					$('activityendDate').innerHTML = rsObj[0].endDate.substring(0, 4)+'-'+rsObj[0].endDate.substring(4, 6)+'-'+rsObj[0].endDate.substring(6, 8);
					if(state == 'F'){
						$('delayProjectTaskInfoTitle').innerHTML = "일정지연 예정 안내";
						$('delayCmt').innerHTML = "일정지연까지 <b><font color='red'>" + rsObj[0].duration + "</font></b>일 남았습니다";
					}else{
						$('delayProjectTaskInfoTitle').innerHTML = "일정지연 안내";
						$('delayCmt').innerHTML = "완료일로부터 <b><font color='red'>" + rsObj[0].duration + "</font></b>일 지났습니다.";
					}

				}, 
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("error");
				}
			}
		);
}
function delayInfoClose(){
	$('delayInfo').hide();
	location.reload(true);
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
</script> 
</head>

<body>
	<form name="myProjectListForm" method="post">
		<div style='display: none;'>
			<input type="hidden" name='pageNo' value="<c:out value="${list.valueListInfo.pagingPage}"/>"> 
			<input type="hidden" name="costOver" value="<c:out value="${costOver }" />">
				<input type="hidden" name="delayState" value="<c:out value="${delayState}" />" />
			
			<!-- my project costOver, delayState check -->
		</div>
		
		<!-- location -->
		<div class="location">
			<p class="menu_title">MY PROJECT</p>
			<ul>
				<li class="home">HOME</li>
				<li>PMS</li>
				<li>프로젝트 관리</li>
				<li>MY PROJECT</li>
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
								<SELECT name='businessTypeCode' class='select' style='width: 100%;'>
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
								<code:codelist tableName="PROJECT_STATE" attribute=" name='projectState' class='select' style='width:99%;' " isSelectBox="Y" all="Y" selectedInfo="${projectState}" />
							</div>
							
							<div class="label_group">
								<p>기간</p>										
								<div class="month_check">
									<fmt:parseDate value="${realStartDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var4"/>
									<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="start"/>
									<fmt:parseDate value="${realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var4"/>
									<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
									<script>
										jQuery(function(){jQuery( "#realStartDate" ).datepicker({});});
										jQuery(function(){jQuery( "#realEndDate" ).datepicker({});});
									</script>
									<input type="text" name="realStartDate" id="realStartDate" style="width:44%" readonly="readonly" value="<c:out value="${start}" />" /> 
									<span>~</span>
									<input type="text" name="realEndDate" id="realEndDate" style="width:44%" readonly="readonly" value="<c:out value="${end}" />" />
								</div>											
							</div>
						</div>
						<div class="select_box">								
							<div class="label_group">
								<p>고객사</p>
								<input type="text" name="customerName" value="<c:out value="${customerName}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</div>
							<div class="label_group">
								<p>프로젝트</p>
								<input type="text" name="projectName" value="<c:out value="${projectName}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</div>
						</div>
					</div>
					<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색<i class="mdi mdi-magnify"></i></button></div>
				</div>
				<!-- //search_box -->
			</div>
								
			<!-- 테이블 리스트 -->					
			<div class="box">
				<div class="a-both total">
					<p>총<span><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>건</p>
				</div>
		
				<div class="tbl-wrap sc">
					<table  id="projectProgressTable" class="tbl-list all"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<thead>
							<tr>
								<th>상태</th>
								<th>ROLE</th>
								<th>프로젝트</th>
								<th>종료일</th>
								<th>PJT 게시판</th>
								<th>QM 게시판</th>	
								<th>품의</th>
								<th>상세정보</th>
							</tr>
							</thead>
							<tbody>
									<c:if test="${!empty list.list}">
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
																	<c:when test="${rs.delayState == 'B'}">
																		<td>진행
																			<c:if test="${rs.delayForecast == 'R'}">
																				<span class="ico-clock" onclick="delayProjectTaskInfoShow('F', '<c:out value="${rs.projectCode}" />');"></span>
																				<%-- <img alt="프로젝트 일정지연 예정" src="/images/delayAlert.jpg" align="absmiddle" style="cursor: hand;" 
																				onclick="delayProjectTaskInfoShow('F', '<c:out value="${rs.projectCode}" />');"> --%>
																			</c:if>
																		</td>
																	</c:when>
																	<c:when test="${rs.delayState == 'R'}">
																		<td class="status tooltip">일정지연
																			<span class="ico-progress" onclick="delayProjectTaskInfoShow('D', '<c:out value="${rs.projectCode}" />')";>
																			</span>
																			<!-- <span class="ico-progress">
																			</span> -->
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td>
																		<font color="#FF0000">평가대기</font>
																		<img alt="프로젝트 평가대기" src="/images/redAlert.png" align="absmiddle"
																		<img alt="프로젝트 평가대기" src="/images/redAlert.jpg" align="absmiddle"
																	</td>
																	</c:otherwise>
																</c:choose> 
															</c:otherwise>
														</c:choose>
													<!-- BIZ 구분 주석 -->
													<%-- <td><c:out value="${rs.businessTypeCodeName}" /></td> --%>
													<td><c:out value="${rs.roleName}" /></td>
													<td class="subject" onclick="goProjectDetail('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.role}" />')" style="text-align: left; cursor: hand;" title="<code:code tableName="PROJECT_TYPE_CODE" code="${rs.projectTypeCode}" />" class="myoverflow"><p>[<c:out value="${rs.projectCode}" />] <c:out value="${rs.projectName}" /></p><c:if test="${rs.costOver == 'Y'}"> / <span class="pink">예산초과<span class="mdi mdi-check-circle"></span>
													</c:if></a></td>
													<td><fmt:formatDate value="${rs.realEndDate}" pattern="yyyy-MM-dd" /></td>
													<td><a href="#" onclick="goProjectBBS('<c:out value="${rs.projectCode}" />','<c:out value="${rs.projectName}" />')"><c:out value="${rs.boardArticleCount}" /></a></td>
													<td><a href="#" onclick="goQMBBS('<c:out value="${rs.projectCode}" />','<c:out value="${rs.projectName}" />')"><c:out value="${rs.boardArticleCountQM}" /></a></td>
													<%-- <td><img alt="진행정보" src="/images/btn_glass_y.jpg"  style="cursor: hand;" onclick="goProjectDetail('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.role}" />')"></td>  --%>
													<c:choose><c:when test="${(rs.projectState=='3'||rs.adminOpen=='Y') && rs.role!='MB'}">
														<td class="menu_open">
															<span class="ico-draw"></span>
															<ul class="menu_ul">
																<li><a href="javascript:projectChange('10','<c:out value="${rs.projectCode}" />', <c:choose><c:when test="${rs.processTypeCode== 'N4' || rs.processTypeCode== 'DE'}">'BTA'</c:when><c:when test="${rs.processTypeCode=='N1' || rs.processTypeCode=='N2' || rs.processTypeCode=='EE' || rs.processTypeCode=='DD'}">'BTZ'</c:when><c:otherwise>'<c:out value="${rs.businessTypeCode}" />'</c:otherwise></c:choose>);">인력변경품의</a></li>
																<li><a href="javascript:projectChange('2','<c:out value="${rs.projectCode}" />', <c:choose><c:when test="${rs.processTypeCode== 'N4' || rs.processTypeCode== 'DE'}">'BTA'</c:when><c:when test="${rs.processTypeCode=='N1' || rs.processTypeCode=='N2' || rs.processTypeCode=='EE' || rs.processTypeCode=='DD'}">'BTZ'</c:when><c:otherwise>'<c:out value="${rs.businessTypeCode}" />'</c:otherwise></c:choose>);">일정변경품의</a></li>
																<li><a href="javascript:projectChange('3','<c:out value="${rs.projectCode}" />', <c:choose><c:when test="${rs.processTypeCode== 'N4' || rs.processTypeCode== 'DE'}">'BTA'</c:when><c:when test="${rs.processTypeCode=='N1' || rs.processTypeCode=='N2' || rs.processTypeCode=='EE' || rs.processTypeCode=='DD'}">'BTZ'</c:when><c:otherwise>'<c:out value="${rs.businessTypeCode}" />'</c:otherwise></c:choose>);">예산변경품의</a></li>
																<li><a href="javascript:projectChange('4','<c:out value="${rs.projectCode}" />', <c:choose><c:when test="${rs.processTypeCode== 'N4' || rs.processTypeCode== 'DE'}">'BTA'</c:when><c:when test="${rs.processTypeCode=='N1' || rs.processTypeCode=='N2' || rs.processTypeCode=='EE' || rs.processTypeCode=='DD'}">'BTZ'</c:when><c:otherwise>'<c:out value="${rs.businessTypeCode}" />'</c:otherwise></c:choose>);">가지급신청</a></li>
																<li><a href="javascript:projectChange('5','<c:out value="${rs.projectCode}" />', <c:choose><c:when test="${rs.processTypeCode== 'N4' || rs.processTypeCode== 'DE'}">'BTA'</c:when><c:when test="${rs.processTypeCode=='N1' || rs.processTypeCode=='N2' || rs.processTypeCode=='EE' || rs.processTypeCode=='DD'}">'BTZ'</c:when><c:otherwise>'<c:out value="${rs.businessTypeCode}" />'</c:otherwise></c:choose>);">법인카드신청</a></li>
																<li><a href="javascript:projectChange('6','<c:out value="${rs.projectCode}" />', <c:choose><c:when test="${rs.processTypeCode== 'N4' || rs.processTypeCode== 'DE'}">'BTA'</c:when><c:when test="${rs.processTypeCode=='N1' || rs.processTypeCode=='N2' || rs.processTypeCode=='EE' || rs.processTypeCode=='DD'}">'BTZ'</c:when><c:otherwise>'<c:out value="${rs.businessTypeCode}" />'</c:otherwise></c:choose>);">사업관련품의</a></li>
																<li><a href="javascript:projectChange('7','<c:out value="${rs.projectCode}" />', <c:choose><c:when test="${rs.processTypeCode== 'N4' || rs.processTypeCode== 'DE'}">'BTA'</c:when><c:when test="${rs.processTypeCode=='N1' || rs.processTypeCode=='N2' || rs.processTypeCode=='EE' || rs.processTypeCode=='DD'}">'BTZ'</c:when><c:otherwise>'<c:out value="${rs.businessTypeCode}" />'</c:otherwise></c:choose>);">PJ취소품의</a></li>
															</ul>
														</td>
													</c:when>
													<c:otherwise><td>-</td></c:otherwise></c:choose>
													<%-- <img alt="변경품의" src="/images/btn_resanction.png"  style="cursor: hand;" 
													onclick="javascript:ProjectMenuView('<c:out value="${rs.projectCode}" />', <c:choose><c:when test="${rs.processTypeCode== 'N4' || rs.processTypeCode== 'DE'}">'BTA'</c:when><c:when test="${rs.processTypeCode=='N1' || rs.processTypeCode=='N2' || rs.processTypeCode=='EE' || rs.processTypeCode=='DD'}">'BTZ'</c:when><c:otherwise>'<c:out value="${rs.businessTypeCode}" />'</c:otherwise></c:choose>);"></c:if></td> --%>
													<%-- <td align="center"><c:if test="${!(rs.attach == 'Y' && (rs.projectCode == rs.parentProjectCode)) }" ><c:if test="${rs.projectState == '6'}"><img alt="종료정보" src="/images/btn_glass.jpg"  style="cursor: hand;" onclick="goProjectEndDetail('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.businessTypeCode}" />')"></c:if></c:if></td> --%>
												<td><a href="javascript:goProjectDetail('<c:out value="${rs.projectCode}" />', '<c:out value="${rs.role}" />')" class="ico-file"></td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${empty list.list}">
											<td colspan="10">검색된 데이터가 없습니다. </td>
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
		</div>
	</form>
</body>
<div id="class_menuInfo" style="position: absolute; display: none; top: 0; left: 0; border-color:#808080; border-style: solid; border-width: 1px; ">
	<table border="1" width="210" bordercolor="#808080" cellspacing="0" cellpadding="0" style="border-collapse: collapse; ">
		<tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('10');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;인력변경품의</font></td>
					</tr>
				</table>
			</td>
		</tr>
		<!-- <tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('11');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;인력변경품의(엑스퍼트,강사,교수)</font></td>
					</tr>
				</table>
			</td>
		</tr>  -->
		<!-- <tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('14');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;엑스퍼트 등급품의</font></td>
					</tr>
				</table>
			</td>
		</tr> --> 
		<!-- <tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('12');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;인력변경품의(산업계 강사)</font></td>
					</tr>
				</table>
			</td>
		</tr> 
		<tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('13');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;인력변경품의(대학 교수)</font></td>
					</tr>
				</table>
			</td>
		</tr> --> 
		<tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('2');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;일정변경품의</font></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('3');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;예산변경품의</font></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('4');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;가지급신청</font></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('5');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;법인카드신청</font></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('6');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;사업관련품의</font></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#ffffff" bordercolor="#ffffff" height="20" style="cursor: hand; top: 0; left: 0" onclick="projectChange('7');" onmouseout="onMouseOut(this);" onmouseover="onMouseOver(this, 'none');">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><font size="2">&nbsp;<img src="/images/main_dot.jpg" width="2" height="2" align="absmiddle">&nbsp;PJ취소품의</font></td>
					</tr>
				</table> 
			</td>
		</tr>
	</table>
</div>	

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