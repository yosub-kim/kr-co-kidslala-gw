<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.common.util.SessionUtils"%>
<%@page import="kr.co.kmac.pms.expertpool.data.ExpertPool"%>
<%@page import="kr.co.kmac.pms.expertpool.manager.ExpertPoolManager"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
<%@ include file="/m/web/common/includeMeta.jsp"%>
<%@ include file="/m/web/common/includeCss.jsp"%>
<%@ include file="/m/web/common/includeJavaScript.jsp"%>
<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	ExpertPoolManager expertPoolManager = null;
	if (wac != null) {
		expertPoolManager = (ExpertPoolManager) wac.getBean("expertPoolManager");
	}
	ExpertPool expertPool = expertPoolManager.retrieve(SessionUtils.getUsername(request));
		
	String userId = expertPool.getUserId();
%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
var myDate = new Date();
var nowYear = myDate.getFullYear();

function getAge(regno){
	var birthYear = regno.substring(0,2);
	var gbn       = regno.substring(6,7);
	var age = 0;
	if ((gbn == "1") || (gbn == "2")) {
		var age = parseInt(nowYear,10) - parseInt("19" + birthYear)+1;
	} else if ((gbn == "3") || (gbn == "4")) {
		var age = parseInt(nowYear,10) - parseInt("20" + birthYear)+1;
	} else {
		var age = "&nbsp;";
	}
	return age;	
}
function setSearchCondition(obj) {
	var svalue = obj.options[obj.selectedIndex].value;
	if (svalue == "3") {
		document.getElementById("jobClass").style.display = "";
		document.getElementById("runningDeptCode").style.display = "none";
		document.getElementById("keyword").style.width = "62%";
		document.getElementById("keyword").style.display = "none";
		document.getElementById("keyword").value = "";
		//document.getElementById("keyword").disabled = true;
		$('specialFieldTr').show();
		toggleSpecialField(document.getElementById("jobClass").value);
	} else if (svalue == "5") {
		document.getElementById("runningDeptCode").style.display = "";
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("keyword").style.width = "62%";
		document.getElementById("keyword").style.display = "none";
		document.getElementById("keyword").value = "";
		//document.getElementById("keyword").disabled = true;
		$('specialFieldTr').show();
		toggleSpecialField(document.getElementById("runningDeptCode").value);
	} else {
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("runningDeptCode").style.display = "none";
		document.getElementById("keyword").style.display = "";
		document.getElementById("keyword").style.width = "80%";
		//document.getElementById("keyword").disabled = false;
		$('specialFieldTr').hide();
	}
}
function Page_OnLoad() {
	if (document.getElementById("keyfield").value == "3") {
		document.getElementById("jobClass").style.display = "";
		document.getElementById("runningDeptCode").style.display = "none";
		document.getElementById("keyword").style.width = "62%";		
		document.getElementById("keyword").style.display = "none";
		document.getElementById("keyword").value = "";
		//document.getElementById("keyword").disabled = true;
		$('specialFieldTr').show();
		if($('selectedGroup').value != ''){
			changeSpecialField($('selectedGroup').value);
		}
		toggleSpecialField(document.getElementById("jobClass").value);
	} else if (document.getElementById("keyfield").value == "5") {
		document.getElementById("runningDeptCode").style.display = "";
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("keyword").style.width = "62%";		
		document.getElementById("keyword").style.display = "none";
		document.getElementById("keyword").value = "";
		//document.getElementById("keyword").disabled = true;
		$('specialFieldTr').show();
		if($('selectedGroup').value != ''){
			changeSpecialField($('selectedGroup').value);
		}
		toggleSpecialField(document.getElementById("runningDeptCode").value);
	} else {
		document.getElementById("jobClass").style.display = "none";
		document.getElementById("runningDeptCode").style.display = "none";
		document.getElementById("keyword").style.display = "";
		document.getElementById("keyword").style.width = "80%";
		//document.getElementById("keyword").disabled = false;
		$('specialFieldTr').hide();
	}
}
function doSearch() {
	document.frm.pg.value="1";
	frm.submit();
}
function detail(ssn) {
	document.location.href = "/action/ExpertPoolManagerAction.do?mode=infoviewForMobile&ssn="+ssn;
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
function toggleSpecialField(value){
	if(value == 'J' || value == 'C' || value == 'F'){
		$('specialFieldTr').show();
	}else{
		$('specialFieldTr').hide();
	}
}
function goPage(pageNumber){
	frm.pg.value = pageNumber;
	frm.submit();
}
</script>
</head>
<body>
<%-- 작업영역 --%>
<form name="frm">

	<!-- header -->
	<jsp:include page="/m/web/common/header.jsp" >
		<jsp:param value="fixed" name="data_position" />
	</jsp:include>
	<!-- // header -->
	
	 <!-- sub_container -->
     <div class="sub_container">
     	<div class="topbar">
           <div>
               <button type="button" class="btn arrowL" title="이전 페이지" onclick="history.back();"><i class="mdi mdi-arrow-left"></i></button>
               <p>인력 POOL</p>
           </div>
           
           <ul>
               <li><button type="button" class="btn c-sub" title="검색 창 열기" onclick="javascript:layer_open(this,'pop_search');"><i class="mdi mdi-magnify"></i></button></li>
               <!-- <li><button type="button" class="btn c-main" title="게시글 작성" onclick="location.href='boardWrite.html'"><i class="mdi mdi-pencil-plus-outline"></i></button></li> -->
           </ul>
           
           <!-- 검색 창 팝업 -->
           <div id="pop_search" class="popup_layer pop_search">
               <div class="popup_bg"></div>
               <div class="popup_inner">
                   <div class="pop_close">
                       <input type="text" name="name" class="textInput_left"  placeholder="이름 입력" value="<c:out value="${name}"/>" >					
                       <button type="button" class="btn-close" onclick="javascript:layer_close('pop_search');" title="닫기"><i class="mdi mdi-close"></i></button>
                   </div>
                   <div class="write_group">
                        <SELECT  name='jobClass' id='jobClass' style="width:50%;">
							<option value="" <c:if test="${jobClass == '' }">selected</c:if>>전체</option>
							<option value="A" <c:if test="${jobClass=='A'}">selected</c:if>>상근</option>
							<option value="J" <c:if test="${jobClass=='J'}">selected</c:if>>상임</option>
							<option value="N" <c:if test="${jobClass=='H2'}">selected</c:if>>RA</option>
							<%if(expertPool.getJobClass().equals("A") || expertPool.getJobClass().equals("B")){ %>
								<option value="C" <c:if test="${jobClass=='C'}">selected</c:if>>엑스퍼트</option>
								<option value="D" <c:if test="${jobClass=='D'}">selected</c:if>>산업계강사</option>
								<option value="E" <c:if test="${jobClass=='E'}">selected</c:if>>대학교수</option>
								<option value="R" <c:if test="${jobClass=='R'}">selected</c:if>>아르바이트</option>
							<%} %>
						</SELECT>
						<org:divList enabled="1" depth="2" attribute=" name='dept' id='dept' style='width: 50%'" 
							divType="A" all="Y" isSelectBox="Y" selectedInfo="${dept}"></org:divList>
						<input type="hidden" name="deptName"  id="deptName"  style="width: 49%;" value="<c:out value="${deptName}"/>" onKeyPress="if(event.keyCode==13) {doSearch();}">
						<input type="hidden" name="mode" id="mode" value="getExpertPoolExtListForMobile">
                   </div>
                   <button type="button" class="btn c-wt" title="검색" onclick="location.href='javascript:doSearch();'">검색</button>
               </div>
           </div>
           <!-- // 검색 창 팝업 -->
    	</div>
      </div>
      
       <div class="contents">
          <div class="tbl-wrap">
               <table class="tbl list p-10"">							
                   <tbody>
                  	 <c:forEach var="rs" items="${list}">
						<tr onclick="detail('<c:out value="${rs.ssn}" />')" style="cursor: hand;">
							<td><a>
							<div class="a-l">
								<div class="profile">
									<!-- <div class="img"><i class="mdi mdi-account"></i></div> -->
									<div class="img">
										<c:choose><c:when test="${rs.photo ne '' }"><img id="MyPic" style="cursor:hand;" src="/servlet/PhotoDownLoadServlet?fileId=<c:out value="${rs.photo }"/>"></c:when><c:otherwise>
											<i class="mdi mdi-account"></i>
										</c:otherwise></c:choose>
									</div>
								</div>
								<div class="info_box">
									<p><strong>[<c:out value="${rs.deptName}" escapeXml="false"/>]</strong> <c:out value="${rs.name}" /> <c:out value="${rs.companyPositionName}"/></p>
									<ul class="info">
	                                    <li><i class="mdi mdi-cellphone"></i><p><c:out value="${rs.mobileNo }" /></p></li>
	                                    <li><i class="mdi mdi-at"></i><p><c:out value="${rs.email}"/></p></li>
	                                </ul>
								</div>
							</div>
							</a></td>
						</tr>
					</c:forEach>
					<c:if test="${empty list }">
						<tr><td align='center'>검색 결과가 존재하지 않습니다.<br></td></tr>
					</c:if>
					</tbody>
				</table>
				<div class="paging_area">
					<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml( 
								<c:out value="${pagingPage}" default="1"/>, 
								5, 
								<c:out value="${totalNumberOfEntries}" default="0"/> , 
								10)
							) ;
					</SCRIPT>
				</div>
			</div>
		</div>
		<%-- Hidden 필드 시작 --%>
		<div style="display:none">
			<input type="hidden" name="mode" id="mode" value="getExpertPoolExtList">
			<input type="hidden" name="specialFields" id="specialFields" value="<c:out value="${specialFields}"/>">
			<input type="hidden" name="keyfield" id="keyfield" value="<c:out value="${keyfield}"/>">
			<input type="hidden" name="keyword"  id="keyword"  value="<c:out value="${keyword}"/>">
			<input type="hidden" name="pg"       id="pg"       value="<c:out value="${pagingPage}"/>">
			<input type="hidden" name="name"       id="name"       value="<c:out value="${name}"/>">
			<input type="hidden" name="deptName"       id="deptName"       value="<c:out value="${deptName}"/>">
			<input type="hidden" name="company"       id="company"       value="<c:out value="${company}"/>">
			<input type="hidden" name="runningDeptCode"       id="runningDeptCode"       value="<c:out value="${runningDeptCode}"/>">
		</div>
		<%-- Hidden 필드 종료 --%>
</form>
</body>
</html>