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
		var age = parseInt(nowYear,10) - parseInt("19" + birthYear);
	} else if ((gbn == "3") || (gbn == "4")) {
		var age = parseInt(nowYear,10) - parseInt("20" + birthYear);
	} else {
		var age = "&nbsp;";
	}
	return age;	
}
function setSearchCondition(obj) {
	var svalue = obj.options[obj.selectedIndex].value;
	if (svalue == "3") {
		document.getElementById("jobClass").style.display = "";
		document.getElementById("keyword").style.width = "62%";
		document.getElementById("keyword").disabled = true;
		document.getElementById("keyword").value = "";
	} else {
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("keyword").style.width = "80%";
		document.getElementById("keyword").disabled = false;
	}
}
function Page_OnLoad() {
	if (document.getElementById("keyfield").value == "3") {
		document.getElementById("jobClass").style.display = "";
		document.getElementById("keyword").style.width = "62%";		
		document.getElementById("keyword").disabled = true;
		document.getElementById("keyword").value = "";
		$('specialFieldTr').show();
		if($('selectedGroup').value != ''){
			changeSpecialField($('selectedGroup').value);
		}
	} else {
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("keyword").style.width = "80%";
		document.getElementById("keyword").disabled = false;
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
				//alert(obj.responseText);
	        	
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
function toggleSpecialField(value){
	if(value == 'J'){
		$('specialFieldTr').show();
	}else{
		$('specialFieldTr').hide();
	}
}
function goPage(pageNumber){
	frm.pg.value = pageNumber;
	frm.submit();
}
function saveListToExcel() {
	var surl = '/action/ExpertPoolManagerAction.do?mode=saveExpertPoolListToExcel';
	surl += "&jobClass=" + document.frm.jobClass.value;
	surl += "&name=" + document.frm.name.value;
	document.location = surl;
}
</script>
</head>
<body onload="Page_OnLoad();">
<%-- 작업영역 --%>
<form name="frm">
<table width="765" cellpadding="0" cellspacing="0">
	<tr>
		<td width="765" height="8">&nbsp;</td>
	</tr>
	<tr>
		<td height="12">
			<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
				<jsp:param name="title" value="인력 POOL 관리" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr> 
	<tr>
		<td>
			<%@ include file="/common/include/searchBox_Header.jsp"%>
				<table  cellSpacing="0" cellpadding="0" width="100%" height="26">					
					<tr>
						<td class="searchTitle_center" width="100">검색조건</td>
						<td class="searchField_right">
							<select name="keyfield" class="selectbox" style="width:105px;" onpropertychange="setSearchCondition(this);">
								<option value="1" <c:if test="${keyfield=='1'}">selected</c:if>>성명</option>
								<option value="2" <c:if test="${keyfield=='2'}">selected</c:if>>회사명</option>
								<option value="3" <c:if test="${keyfield=='3'}">selected</c:if>>직업구분</option>
								<%--<option value="4" <c:if test="${keyfield=='4'}">selected</c:if>>등록자</option>--%>
							</select>
							<code:codelist tableName="EMP_WORKING_TYPE" isSelectBox="Y" all="N" selectedInfo="${jobClass}" 
								attribute=" name='jobClass' class='selectbox' style='display:none;width:105px;' onchange='toggleSpecialField(this.value)' "/>
							<input type="text" name="keyword"  style="width:80%" value="<c:out value="${keyword}"/>" class="textInput_left">
							<input type="hidden" name="mode" value="getExpertPoolList">
						</td>
					</tr>
					<tr  id="specialFieldTr" style="display: none;">
						<td class="searchTitle_center" width="100">전문분야</td>
						<td class="searchField_left">
							<table style="width: 100%" border=0>
								<tr>
									<td style="width: 105px;">
										<select name="selectedGroup" id="selectedGroup" class="selectbox" style="width:105px;" onchange="changeSpecialField(this.value)" >
											<option>전문분야 선택</option>
											<c:forEach var="rs" items="${groupList}">
												<option value="<c:out value="${rs.id }"/>" <c:if test="${selectedGroup eq rs.id }">selected</c:if>><c:out value="${rs.name }"/></option>
											</c:forEach>
										</select>
									</td>
									<td style="width: 100%; padding-left: 5px;">
										<div id="specialFieldListDiv" style="width: 100%;border-color: c7c7c7; border-width: 1px; border-style: none; overflow: auto;">
										</div>
									</td>
								</tr>
							</table>
						</td>					
					</tr>
				</table>
			<%@ include file="/common/include/searchBox_Footer.jsp"%>		
		</td>
	</tr>
	<tr>
		<td height='10'></td>
	</tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td height="25">
						<b><font class='SF'><c:out value="${totalNumberOfEntries}"/> Total - Page(<c:out value="${pagingPage}"/>/<c:out value="${totalNumberOfPages}"/>)</font></b>
					</td>
					<td align="right">
					<%if(session.getAttribute("userId").equals("yhyim") || session.getAttribute("userId").equals("eun")){ %>
						<a href="javascript:saveListToExcel();"><img src="/images/btn_excel.jpg" width="88" height="21" border="0"></a>
					<%}%>
						<a href="/action/ExpertPoolManagerAction.do?mode=loadFormExpertPool"><img src="/images/btn_regist.jpg" width="61" height="21" border="0"></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="left">
			<table width="100%" class="listTable">
				<thead>
					<tr height="25px">
						<td>성명</td>
						<td>소속별</td>
						<td>연령</td>
						<td>성별</td>
						<td>전문분야</td> 
					</tr>
				</thead>
				<tbody id="ListData">
					<c:forEach var="rs" items="${list}">
						<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;">
							<td align="center" width="80px"><c:out value="${rs.name}" /></td>
							<td width="200px"><c:out value="${rs.company}" escapeXml="false"/></td>
							<td align="center" width="40px"><script>document.write(getAge('<c:out value="${rs.ssn}" />'));</script></td>
							<td align="center" width="40px">
								<c:choose>
									<c:when test="${rs.gender == '0'}">여자</c:when>
									<c:otherwise>남자</c:otherwise>
								</c:choose>
							</td>
							<td class="detailTableField_left">&nbsp;<c:out value="${rs.consultingMajor}" escapeXml="false"/></td>
						</tr>
					</c:forEach>
					<c:if test="${empty list}">
						<tr height="25px">
							<td align="center" colspan="5">검색된 데이터가 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<%-- 페이징 영역 시작 --%>
			
			<table width="765" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" align="center">
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
			<input type="hidden" name="mode" value="getExpertPoolList">
			<input type="hidden" name="specialFields" id="specialFields" value="<c:out value="${specialFields}"/>">
			<input type="hidden" name="keyfield" value="<c:out value="${keyfield}"/>">
			<input type="hidden" name="keyword"  value="<c:out value="${keyword}"/>">
			<input type="hidden" name="pg"       value="<c:out value="${pagingPage}"/>">
			<input type="hidden" name="jobClass" value="<c:out value="${jobClass}"/>">
			<%-- Hidden 필드 종료 --%>

			<%-- 페이징 영역 끝--%>
		</td>
	</tr>
</table>
</form>
</body>
</html>