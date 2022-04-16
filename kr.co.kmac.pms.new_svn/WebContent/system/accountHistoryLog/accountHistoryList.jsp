<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>통합 인트라넷 접근 권한 이력 관리</title>

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function changeDiv(obj) {
	if(obj == 'A'){
		$('div0').innerHTML = $('div1').innerHTML; 
	}else if(obj == 'J'){
		$('div0').innerHTML = $('div2').innerHTML; 
	}else if(obj == 'C'){
		$('div0').innerHTML = $('div3').innerHTML;
	}else{
		$('div0').innerHTML = $('div4').innerHTML; 
	}
}


function viewEtc(event, seq, etc) {
	
	$('updateEtc').style.display = 'none';
	$('updateEtc').style.top = event.clientY;
	$('updateEtc').style.left = event.clientX - 350;//'243';
	$('updateEtc').style.display= 'inline';
	$('seq').value = seq;
	if(etc == '-') {
		$('etcContent').value = '';
	} else {
		$('etcContent').value = etc;
	}
}

function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}

function updateEtc(){
	var seq = $('seq').value;
	var etc = $('etcContent').value;
	AjaxRequest.post (
			{	'url': "/action/AccessLogAction.do?mode=updateEtc",
				'parameters' : { "seq": seq ,
								"etc" : $('etcContent').innerHTML}, 
				'onSuccess':function(obj){
					alert("비고를 저장하였습니다."); 						
					document.form.target = "";		
					document.form.action = "/action/AccessLogAction.do?mode=getAccountHistoryList";
					document.form.submit();
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("저장할 수 없습니다.");
				}
			}
		);
}
function updateEtcClose(){
	$('updateEtc').style.display = 'none';
}
function goPage(next_page) {
	document.form.pg.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/AccessLogAction.do?mode=getAccountHistoryList";
	document.form.submit();
}
function doSearch() {
	document.form.target = "";		
	document.form.action = "/action/AccessLogAction.do?mode=getAccountHistoryList";
	document.form.submit();
}
</script>
</head>

<body>
<form name="form" method="post">	
<input type="hidden" name="pg" id="pg"> 

	<table width="100%" cellpadding="0" cellspacing="0">
	<!-- SPACER -->
		<tr>
			<td>
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="접근권한 이력 관리" />
				</jsp:include>
			</td>
		</tr>
		<!-- 검색 영역 -->
		<tr>
			<td height="21" align="left" valign="top">
				<%@ include file="/common/include/searchBox_Header.jsp"%>					
					<table border="0" width="100%" style="border-collapse: collapse; ">
						<colgroup> 
							<col width="80px">
							<col width="100px">
							<col width="*>
							<col width="80px">
							<col width="100px">
						</colgroup>
						<tr>
							<td class="searchTitle_center">이력 구분</td>
							<td class="searchField_center" colspan="2">
								<select name="accessType" class="selectbox" style="width: 100%;">
									<option value="">전체</option>
									<option value="C" <c:if test="${accessType=='C'}">selected</c:if>>생성</option>
									<option value="U" <c:if test="${accessType=='U'}">selected</c:if>>변경</option>
									<option value="D"  <c:if test="${accessType=='D'}">selected</c:if>>말소</option>
								</select>
							</td>
							<td class="searchTitle_center">날짜</td> 
							<td class="searchField_left">
								<fmt:parseDate value="${from}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
								<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
								<fmt:parseDate value="${to}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
								<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
								<script>
									jQuery(function(){jQuery( "#from" ).datepicker({});});
									jQuery(function(){jQuery( "#to" ).datepicker({});});
								</script>
								<input type="text" name="from" id="from" readonly="readonly" size="8" value="<c:out value="${start}" />" />~<input 
								type="text" name="to" id="to" readonly="readonly" size="8" value="<c:out value="${end}" />" />
							</td>
						</tr>
						<tr>
							<td class="searchTitle_center">소속</td>
							<td class="searchField_center">
								<select name="jobClass" id="jobClass" style="width:100%" class="selectbox" onchange="changeDiv(this.value);">
									<option value="" <c:if test="${jobClass == '' }">selected</c:if>>전체</option>
									<option value="A" <c:if test="${jobClass == 'A' }">selected</c:if>>상근</option>
									<option value="J" <c:if test="${jobClass == 'J' }">selected</c:if>>전문가그룹</option>
									<option value="C" <c:if test="${jobClass == 'C' }">selected</c:if>>엑스퍼트</option>
									<option value="H" <c:if test="${jobClass == 'H' }">selected</c:if>>RA</option>
								</select>
							</td>
							<td class="searchField_center">
								<div id="div0">
									<c:choose>
									<c:when test="${jobClass=='A'}">
										<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='J'}">
										<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='C'}">
										<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
									</c:when>
									<c:when test="${jobClass=='H'}">
										<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
									</c:when>
									<c:otherwise>
										<org:divList enabled="1" divType="" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
									</c:otherwise>
									</c:choose>
								</div>
							</td>
							<td class="searchTitle_center">성명</td>
							<td class="searchField_left">
								<input type="text" name="name" id="name" value="<c:out value="${name}"/>" class="textInput_left" style="width: 100%;" onkeypress="if(event.keyCode == 13) doSearch();">
							</td>
						</tr>
					</table>
				<%@ include file="/common/include/searchBox_Footer.jsp"%>
			</td>
		
		</tr>					
	 	
		<tr>
			<td height="35px">
				<div class="btNbox pb15">
					<div class="btNlefTdiv">				
						<img src="/images/sub/line3Blit.gif" alt="">
						<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
						<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
					</div>
				</div>
			</td>
		</tr>
		
		<tr>
			<td>
				<table id="projectRollingMonitorTable" class="listTable" style="table-layout: fixed">
					<thead id="projectRollingMonitorTableHeader">
						<tr> 
							<td width="40px">구분</td> 
							<td width="80px">성명</td> 
							<td width="90px">직업구분</td> 
							<td width="150px">소속 </td>
							<td width="*">메뉴권한</td> 
							<td width="90px">날짜 </td>
							<td width="50px">비고</td>
						</tr>  
					</thead>									
					<tbody id="projectRollingMonitorTableBody"> 
						<c:if test="${!empty list.list}">
							<c:forEach var="rs" items="${list.list}">
								<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
									<td<c:if test="${rs.type == '말소'}"> style="background:#ffdfff;"</c:if>><c:out value="${rs.type}"/></td> 
									<td onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;"><c:out value="${rs.name}"/></td>
									<td><c:out value="${rs.jobClassName}"/></td> 
									<td><c:out value="${rs.deptName}"/></td> 
									<td><c:out value="${rs.menuRoleName}"/></td>
									<td><c:out value="${rs.date}"/></td>
									<td style="cursor:hand;" onmousedown="viewEtc(event, '<c:out value="${rs.seq}"/>', '<c:out value="${rs.etc}"/>')">
										<c:choose>
											<c:when test="${rs.etc != '-'}">
												<img src="/images/btn_glass.jpg" border="0">
											</c:when>
											<c:otherwise>
												<c:out value="${rs.etc}"/>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty list.list}"><tr><td colspan="7"><br>검색 결과가 존재하지 않습니다 .<br><br></td></tr></c:if>
					</tbody>
				</table>
			</td>
		</tr>
		<%-- 페이징 영역 시작 --%>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="30">
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
</form>
<div style="display: none;">
	<div id="div1">
		<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
	</div>
	<div id="div2">
		<org:divList enabled="1" divType="J" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
	</div>
	<div id="div3">	
		<org:divList enabled="1" divType="C" depth="1" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
	</div>
	<div id="div4">
		<org:divList enabled="1" divType="A" depth="2" isSelectBox="Y" selectedInfo="${deptCode}" attribute=" name='deptCode'  class='selectbox' style='width:100%;' " all="Y"></org:divList>
	</div>
</div>
<div id="updateEtc" 
	style="position: absolute; background: white; display: none; width: 310px; text-align: center; border-style: solid; border-color: gray; border-width: 2px;">
	<table cellpadding="3" cellspacing="3">
		<tr>
			<td><h4 class="subTitle">비고 조회 및 수정</h4></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td>
							<input type="hidden" id="seq" name="seq" value=""/>
						</td>
					</tr>
					<tr>
						<td class="detailTableField_left" style="width:300; height:80;">
							<textarea name="etcContent" id="etcContent" style="width: 100%; height: 98%;"></textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<div class="btNbox pt5 pb10 txtR">
					<a class="btNe006bc6" style="padding: 4px 10px" href="#" onclick="updateEtc()">수정</a>
					<a class="btNa0a0a0" style="padding: 4px 10px" href="#" onclick="updateEtcClose()">닫기</a>
				</div>
			</td>
		</tr>
	</table>
</div>
</body>

</html>