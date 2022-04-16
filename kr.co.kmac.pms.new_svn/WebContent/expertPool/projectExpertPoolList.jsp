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

var myDate = new Date();
var nowYear = myDate.getFullYear();

function getAge(regno){
	var birthYear = regno.substring(0,2);
	var gbn       = regno.substring(6,7);
	var age = 0;
	if ((gbn == "1") || (gbn == "2")) {
		var age = parseInt(nowYear) - parseInt("19" + birthYear);
	} else if ((gbn == "3") || (gbn == "4")) {
		var age = parseInt(nowYear,10) - parseInt("20" + birthYear);
	} else {
		var age = "&nbsp;";
	}
	return age;	
}
function Page_OnLoad() {
	if(document.getElementById("jobClass").value =="C"){
		document.getElementById("jobClass").style.width="105px";
		document.getElementById("rank").style.display="";
		document.getElementById("rank").style.width="115px";
	} else {
		document.getElementById("jobClass").style.width="100%";
		document.getElementById("rank").style.display="none";
	}
	
	if($('selectedGroup').value != ''){
		changeSpecialField($('selectedGroup').value);
	}
}
function setSearchCondition(obj) {
	var svalue = obj.options[obj.selectedIndex].value;
	if (svalue == "C") {
		document.getElementById("jobClass").style.width = "100px";
		document.getElementById("rank").style.display="";
		document.getElementById("rank").style.width="100px";
	} else {
		document.getElementById("jobClass").style.width="100%";
		document.getElementById("rank").style.display="none";
		document.getElementById("rank").value="";
	}
}
function doSearch() {
	document.frm.pg.value="1";
	frm.submit();
}
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoview&ssn="+ssn;
}
function changeSpecialField(val){
	AjaxRequest.get(
		{
			'url':"/action/ExpertPoolSpecialFieldAction.do?mode=searchSpecialField&deptId="+val
			, 'anotherParameter':'false'
			, 'onSuccess':function(obj){   
	        	
	           	var divObj = $('specialFieldListDiv');
	        	var specialFieldHtml = "";
	           	var specialFieldList = eval('(' + obj.responseText + ')').specialField;
	           	specialFieldList.each(function(specialField) {
	           		if(($('specialFields').value).indexOf(specialField.specialId) > 0){
		           		specialFieldHtml += "<input type='checkbox' name='specialField' value='"+specialField.specialId+"' checked>";
	           		}else{
		           		specialFieldHtml += "<input type='checkbox' name='specialField' value='"+specialField.specialId+"' >";	           			
	           		}
	           		specialFieldHtml += specialField.specialName + " &nbsp;&nbsp; ";
	    		});
	           	divObj.innerHTML = specialFieldHtml;
			}
			, 'onLoading':function(obj){}
			, 'onError':function(obj){
				alert("데이터를 가져오지 못 했습니다.");
			}
		});
}
function goPage(pageNumber){
	frm.pg.value = pageNumber;
	frm.submit();
}
</script>
</head>
<body onload="Page_OnLoad();">
<%-- 작업영역 --%>
<form name="frm">
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="12">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="전문가 POOL" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td>
			<%@ include file="/common/include/searchBox_Header.jsp"%>
				<table  cellSpacing="0" cellpadding="0" width="100%">					
					<tr>
						<td class="searchTitle_center" width="100">구분</td>
						<td class="searchField_left" width="250">
							<select name="jobClass" style="width: 50%" class="selectbox" onpropertychange="setSearchCondition(this);">
								<option value="J" <c:if test="${jobClass=='J'}">selected</c:if>>상임</option>
								<option value="C" <c:if test="${jobClass=='C'}">selected</c:if>>엑스퍼트</option>
							</select>
							<code:codelist tableName="EXPERT_FIELD" isSelectBox="Y" all="Y" selectedInfo="${rank}" 
								attribute=" name='rank' id='rank' class='selectbox' style='display:none;' "/>
						</td>
						<td class="searchTitle_center" width="100">성명</td>
						<td class="searchField_right" width="*">
							<input type="text" name="name" style="width: 100%" value="<c:out value="${name}"/>" class="textInput_left">
						</td>
					</tr>
					<%-- <tr>
						<td class="searchTitle_center" width="100">전문분야</td>
						<td class="searchField_left" colspan="3">
							<table style="width:100%" border="0">
								<tr>
									<td style="width: 105px;">
										<select name="selectedGroup" id="selectedGroup" class="selectbox" style="width:105px;" onchange="changeSpecialField(this.value)" >
											<option>전문분야 선택</option>
											<c:forEach var="rs" items="${groupList}">
												<option value="<c:out value="${rs.id}"/>" <c:if test="${selectedGroup eq rs.id}">selected</c:if>><c:out value="${rs.name}"/></option>
											</c:forEach>
										</select>
									</td>
									<td style="padding-left: 5px;">
										<div id="specialFieldListDiv" style="width: 100%;border-color: c7c7c7; border-width: 1px; border-style: none; overflow: auto;"></div>
									</td>
								</tr>
							</table>
						</td>					
					</tr> --%>
				</table>
			<%@ include file="/common/include/searchBox_Footer.jsp"%>		
		</td>
	</tr>
	<tr>
		<td height="25">
			<img src="/images/sub/line3Blit.gif" alt="">
			<span class="bold colore74c3a"><c:out value="${totalNumberOfEntries}"/></span>
			<span>Total - Page(<span><c:out value="${pagingPage}"/></span> of <span><c:out value="${totalNumberOfPages}"/></span>)</span>
		</td>
	</tr>
	<tr>
		<td>
			<table class="listTable" style="table-layout: fixed">
				<thead>
					<tr height="25px">
						<td width="80px">성명</td>
						<td width="130px">등급</td>
						<td width="40px">연령</td>
						<td width="*">전문분야</td>
						<td width="100px"><c:choose><c:when test="${jobClass eq 'C'}">엑스퍼트 분류</c:when><c:otherwise>비고</c:otherwise></c:choose></td>
					</tr>
				</thead>
				<tbody id="ListData">
					<c:forEach var="rs" items="${list}">
						<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;">
							<td><c:out value="${rs.name}" /></td>
							<td><c:out value="${rs.companyPositionName}" escapeXml="false"/></td>
							<td><script>document.write(getAge('<c:out value="${rs.uid}" />'));</script></td>
							<td style="text-align: left"><c:out value="${rs.consultingMajor}" escapeXml="false"/></td>
							<td>
							<c:choose>
								<c:when test="${jobClass eq 'C'}">
									<code:code tableName="EXPERT_FIELD" code="${rs.rank}"/>
								</c:when>
								<c:otherwise>
									<c:out value="${rs.remark}"/>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty list}">
						<tr height="25px">
							<td colspan="5">검색된 데이터가 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<%-- 페이징 영역 시작 --%>
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml( 
									<c:out value="${pagingPage}" default="1"/>, 
									15, 
									<c:out value="${totalNumberOfEntries}" default="0"/> , 
									15)
							) ;
						</SCRIPT>
					</td>
				</tr>
			</table>									
			<%-- Hidden 필드 시작 --%>
			<input type="hidden" name="mode" 			id="mode"	value="getProjectExpertPoolList">
			<input type="hidden" name="specialFields"	id="specialFields" value="<c:out value="${specialFields}"/>">
			<input type="hidden" name="name" 			id="name" value="<c:out value="${name}"/>">
			<input type="hidden" name="jobClass"		id="jobClass" value="<c:out value="${jobClass}"/>">
			<input type="hidden" name="pg"       value="<c:out value="${pagingPage}"/>">
			<%-- Hidden 필드 종료 --%>

			<%-- 페이징 영역 끝--%>
		</td>
	</tr>
</table>
</form>
</body>
</html>