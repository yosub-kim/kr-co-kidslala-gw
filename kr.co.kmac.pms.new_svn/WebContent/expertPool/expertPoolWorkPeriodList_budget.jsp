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


<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function doSearch() {
	document.frm.submit();
}
function storeBUSalary(){
	var ActionURL = "/action/ExpertPoolManagerAction.do?mode=setBudgetCheck";
	var sFrm = document.forms["frm"];
	var flag = false;
	var checkedCount = 0;
	var vMsg = new Array();
	
	for(var i = 0; i < sFrm.elements.length; i++){
		var ele = sFrm.elements[i];
		if(ele.name == "chkRole"){
			if(ele.checked){
				vMsg.push(ele.value);
				checkedCount++;
			}
		} 
	}
	
	if(checkedCount == 0) {
		alert("전송할 항목을 선택하세요.");
		return;
	}

	
	var status = AjaxRequest.submit(
			sFrm
			,{ 'url':ActionURL
				,'onSuccess':function(obj){
					window.location.reload(true);
				}
				,'onLoading':function(obj){}
				,'onError':function(obj){				
					alert("전송할 수 없습니다.");
				}
			}
		); status = null;	
}

function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveExpertPoolWorkPeriodList_budget';
 	surl += "&startDate=" + document.frm.startDate.value;
	surl += "&endDate=" + document.frm.endDate.value;
	surl += "&selectCheck=" + document.frm.selectCheck.value;
	surl += "&runningDeptCode=" + document.frm.runningDeptCode.value;
	document.location = surl;
}
</script>
</head>


<body>
<form name="frm" method="post">
	<div style="display: none;">
		<input type="hidden" id="today" name="today" value="<c:out value="${today}" />" />
		<input type="hidden" id="runningDeptCode" name="runningDeptCode" value="<c:out value="${runningDeptCode}" />" />
	</div>
	
		<!-- location -->
		<div class="location">
			<p class="menu_title">프로젝트 예산현황</p>
			<ul>
				<li class="home">HOME</li>
				<li>경영관리</li>
				<li>사업관리</li>
				<li>프로젝트 예산현황</li>
			</ul>
		</div>
		<!-- // location -->
		
		<!-- contents sub -->
		<div class="contents sub">
	
		<!-- 신규 프로젝트 등록 -->
		<div class="box">
				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>전송 여부</p>
								<select name="selectCheck" class="select" >
									<option value="%%" <c:if test="${'%%' == selectCheck }">selected</c:if>>전체</option>
									<option value="Y" <c:if test="${'Y' == selectCheck }">selected</c:if>>전송</option>
									<option value="N" <c:if test="${'N' == selectCheck }">selected</c:if>>미전송</option>
								</select>
							</div>
							
							<div class="label_group">
								<p>조직단위</p>
								<org:divList enabled="1" depth="2" attribute=" name='runningDeptCode' class='select' " 
									divType="A" all="Y" isSelectBox="Y" selectedInfo="${runningDeptCode}"></org:divList>
							</div>
							
							<div class="label_group">
								<p>기간</p>
								<fmt:parseDate value="${startDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var1"/>
									<fmt:formatDate value="${var1}" pattern="yyyy-MM-dd" var="start"/>
									<fmt:parseDate value="${endDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="var2"/>
									<fmt:formatDate value="${var2}" pattern="yyyy-MM-dd" var="end"/>
									<script>
										jQuery(function(){jQuery( "#startDate" ).datepicker({});});
										jQuery(function(){jQuery( "#endDate" ).datepicker({});});
									</script>
									<input type="text" name="startDate" id="startDate" readonly="readonly" style="width:40%" value="<c:out value="${start}" />" />~
									<input type="text" name="endDate" id="endDate" readonly="readonly" style="width:40%" value="<c:out value="${end}" />" />												
							</div>
						</div>
					</div>
					<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색 <i class="mdi mdi-magnify"></i></button></div>
				</div>
			</div>
				
				<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">프로젝트 별 예산현황</p>
				</div>
				<div class="select_box">
					<button type="button" class="btn line btn_blue" onclick="storeBUSalary();"><i class="mdi mdi-clipboard-check-outline"></i>전송</button>
					<button type="button" class="btn line btn_excel" onclick="saveListToExcel();"><i class="mdi mdi-microsoft-excel"></i>EXCEL 저장</button>
				</div>
			</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c pd-none"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
							<colgroup>
								<col style="width: 5%"/>
								<col style="width: 10%"/>
								<col style="width: 30%" />
								<col style="width: 10%"/>
								<col style="width: 5%"/>
								<col style="width: 5%"/>
								<col style="width: 5%"/>
								<col style="width: 10%"/>
								<col style="width: 10%"/>
								<col style="width: 10%"/>
							</colgroup>
							<tr>
								<th>선택</th>
								<th>P-Code</th>
								<th>P-Name</th>
								<th>수주월</th>
								<th>수주액</th>
								<th>예산원가</th>
								<th>예산이익</th>
								<th>시작일</th>
								<th>종료일</th>
								<th>부서명</th>
							</tr>
							<tbody id="ListData">
									<c:choose>
										<c:when test="${!empty list.list}">
										<c:forEach var="rs" items="${list.list}">
												<c:choose>
												<c:when test="${rs.checkYN == 'Y' }">
													<tr>
														<td>
															완료
														</td>
														<td><c:out value="${rs.projectCode}" /></td>
														<td><c:out value="${rs.projectName}" /></td>
														<td><c:out value="${rs.registerDate}" /></td>
														<td><fmt:formatNumber value="${rs.sum}" pattern="#,###" /></td>
														<td><fmt:formatNumber value="${rs.detailSum}" pattern="#,###" /></td>
														<td><fmt:formatNumber value="${rs.detailBen}" pattern="#,###" /></td>
														<td><c:out value="${rs.realStartDate}"/></td>
														<td><c:out value="${rs.realEndDate}" /></td>
														<td><c:out value="${rs.aliasName}" /></td>
													</tr>
												</c:when>
												<c:otherwise>
													<tr>
														<td>
															<ul><li>
															<input type="checkbox" id="<c:out value="${rs.projectCode }" />" name="chkRole" class="btn_check" value="<c:out value="${rs.projectCode }" />" />
															<label for="<c:out value="${rs.projectCode }" />"></label>
															</li></ul>
														</td>
														<td><c:out value="${rs.projectCode}" /></td>
														<td><c:out value="${rs.projectName}" /></td>
														<td><c:out value="${rs.registerDate}" /></td>
														<td><fmt:formatNumber value="${rs.sum}" pattern="#,##0" /></td>
														<td><fmt:formatNumber value="${rs.detailSum}" pattern="#,###" /></td>
														<td><fmt:formatNumber value="${rs.detailBen}" pattern="#,###" /></td>
														<td><c:out value="${rs.realStartDate}"/></td>
														<td><c:out value="${rs.realEndDate}" /></td>
														<td><c:out value="${rs.aliasName}" /></td>
													</tr>
												</c:otherwise>
												</c:choose>
										</c:forEach>
										</c:when>
										<c:otherwise>
											<tr><td align="center" colspan="10">검색된 데이터가 없습니다.</td></tr>
										</c:otherwise>
									</c:choose>							
								</tbody>
						</table>
					</div>
				</div>
		</div>
	</form>
</body>
	
</html>