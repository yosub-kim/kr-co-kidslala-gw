<%@ taglib prefix="c" 			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="expertPool"	uri="/WEB-INF/expertPool.tld" %>
<%@ taglib prefix="fmt"			uri="http://java.sun.com/jstl/fmt" %>
<script type="text/javascript">
function changeApprovalType() {
	if ($("#approvalTypeSelect").val() == "draft2") {
		alert("진행 중인 프로젝트의 법인카드신청 품의는 \n My Project 메뉴에서 프로젝트 관련 품의 항목 중 \n '법인카드신청' 을 선택하여 진행하시기 바랍니다. ");
	}
	document.sanctionDraftForm.approvalType.value = $("approvalTypeSelect").value;
	document.sanctionDraftForm.target = "";
	if($('isReSanction').value == 'true'){
		document.sanctionDraftForm.action = "/action/GeneralSanctionAction.do?mode=loadFormGeneralForReSanction";
	}else if($('callCenterSanction').value == 'true'){
		document.sanctionDraftForm.action = "/action/GeneralSanctionAction.do?mode=loadFormCallCenterSanction";
	}else{
		document.sanctionDraftForm.action = "/action/GeneralSanctionAction.do?mode=loadFormGeneralSanctionForMobile&approvalType="+$("#approvalTypeSelect").val();
	}
	document.sanctionDraftForm.submit();
}
</script>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
	<script type="text/javascript" src="/resources/js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.selectric.min.js"></script><!-- 셀렉트 박스 UI-->
	<script type="text/javascript">
jQuery.noConflict();
var j$ = jQuery;
</script>
</head>
<body> 
<div data-role="page">
	<form id="sanctionDraftForm" name="sanctionDraftForm" method="post" enctype="multipart/form-data">
		<div id="hiddneValue" style="display: none;">
			<input type="text" name="taskId" id="taskId" value="<c:out value="${sanctionDoc.taskId}" />" />
			<input type="text" name="approvalType" value="<c:out value="${sanctionDoc.approvalType}" />" />
			<input type="text" name="projectCode" value="<c:out value="${sanctionDoc.projectCode}" />" />
			<input type="text" name="seq" id="seq" value="<c:out value="${sanctionDoc.seq}" />" /> 
			<input type="text" name="state" id="state" value="<c:out value="${sanctionDoc.state}" />" />
			<input type="text" name="isReSanction" id="isReSanction" value="<c:out value="${isReSanction}" />" />
			<input type="text" name="callCenterSanction" id="callCenterSanction" value="<c:out value="${callCenterSanction}" />" />
			<input type="text" name="eduCourseCode" id="eduCourseCode" value="<c:out value="${eduCourseCode}" />" />
		</div>
		
		<!-- header -->
		<jsp:include page="/m/web/common/header.jsp" >
			<jsp:param value="fixed" name="data_position" />
		</jsp:include>
		<!-- // header -->
		
		<!-- topbar -->
		<div class="topbar">
	        <div>
	            <button type="button" class="btn arrowL" title="이전 페이지" onclick="history.back()"><i class="mdi mdi-arrow-left"></i></button>
	            <ul><p>전자결재</p></ul>
	        </div>
	        <c:if test="${!readonly && sanctionDoc.state == 'SANCTION_STATE_DRAFT'}">
		        <ul>
		            <li><button type="button" class="btn c-main" title="등록" onclick="executeSanction()"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
		        </ul>
	        </c:if>
	    </div>
	    <!-- // topbar -->

		<!-- Contents -->
        <div class="contents">
           	<div class="inner">   
               <%@include file="/m/web/sanction/sanctionLineInfo.jsp" %>
               <div class="write">
               		<c:choose><c:when test="${sanctionTemplate.approvalType == 'A' }"><input type="text" value="프로젝트 실행품의" /></c:when>
               		<c:when test="${sanctionTemplate.approvalType == 'PA' }" ><input type="text" value="프로젝트 기획품의" /></c:when>
               		<c:otherwise><select name="approvalTypeSelect" id="approvalTypeSelect" onchange="changeApprovalType();">
	                   	<option value="" <c:if test="${sanctionTemplate.approvalType == '' }">selected</c:if>><c:out value="${item.approvalName}"/>결재유형 선택</option>
						 <c:forEach var="item" items="${sanctionTemplateList}">
							<option value="<c:out value="${item.approvalType}"/>" <c:if test="${sanctionTemplate.approvalType == item.approvalType}">selected</c:if>><c:out value="${item.approvalName}"/></option>
						</c:forEach>
					</select></c:otherwise></c:choose>
                   <input type="text" name="subject" id="subject" class="contentInput_left" placeholder="제목 입력" style="width: 100%" onchange="checkApprovalType()" 
						value="<c:out value="${sanctionDoc.subject}"/>" <c:if test="${readonly}">readonly</c:if>>
                   <textarea name="content" id="content" style="min-height: 200px;" placeholder="내용 입력"  <c:if test="${readonly}">readonly</c:if>><c:choose><c:when test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT'}"><c:out value="${sanctionTemplate.templateText}" /></c:when><c:otherwise><c:out value="${sanctionDoc.content}" /></c:otherwise></c:choose></textarea>
               		
               	   <c:if test="${sanctionTemplate.hasAttach}">
		        		<jsp:include page="/common/repository/fileUpload_mobile.jsp"><jsp:param value="Y" name="log"  /></jsp:include>   
		           </c:if>
               </div>
       		 </div>  
       		 <br>
       		 <c:if test="${!readonly}">
       		  	<c:if test="${sanctionDoc.state == 'SANCTION_STATE_DRAFT' || sanctionDoc.state == 'SANCTION_STATE_REJECT_DRAFT' }">
					<input type="hidden" name="isApproved" id="isApproved" value="Y"/>
				</c:if>
				
				<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT' && sanctionDoc.state != 'SANCTION_STATE_REJECT_DRAFT' }">
	               <div class="tbl-box">
	               	   	 <div class="tbl-head">
	               	   		<p>승인/반려</p>
	               	   		<ul>
	               	   			<li><button type="button" class="btn c-main" title="작성" onclick="executeSanction();"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
	               	   		</ul>
	               	  	</div>
	               	   <table class="tbl-ty1">
	                      <colgroup>
	                          <col width="33%" />
	                          <col width="*" />
	                      </colgroup>
	                      <tbody>
	                        <tr>
	                            <th>승인 여부</th>
	                            <td>
									<ul class="chek_ui" style="margin: -5 0 -5 0">
				                       <li>
				                           <input type="radio"  name="isApproved" id="isApproved_1" class="btn_radio" value="Y" checked />
				                           <label for="isApproved_1"><p>승인</p></label>       
				                       </li>
				                       <li>
				                           <input type="radio"  name="isApproved" id="isApproved_2" value="N" class="btn_radio" />
				                           <label for="isApproved_2"><p>반려</p></label>       
				                       </li>
				                   </ul>
								</td>
	                        </tr>
		                    <tr>
		                    	<th>의견 작성</th>
		                    	<td style="height: 130px;"><div style="padding: 0 0 10 0"><textarea name="rejectReason" id="rejectReason" class="textArea" style="width: 100%; height: 100px;" <c:if test="${readonly}">readonly</c:if>></textarea></div></td>
		                    </tr>
	               		</tbody>	
	           		</table>
	           		<%-- <c:if test="${sanctionTemplate.hasWholeApprove}">
						<div data-role="fieldcontain">
						    <fieldset data-role="controlgroup">
							   		<legend>전결여부:</legend>
							   		<input type="checkbox" name="isWholeApproval" id="isWholeApproval" class="custom" value="Y"  <c:if test="${readonly}">disabled</c:if>/>
							   		<label for="isWholeApproval">내 자신이 전결</label>
						    </fieldset>
						</div>
					</c:if> --%>
	            </div>
          	  </c:if>
            </c:if>
            
           	<c:if test="${sanctionDoc.state != 'SANCTION_STATE_DRAFT'}">
           		 <div class="tbl-box">
	               	   <div class="tbl-head">
	               	   		<p>내용</p>
	               	   </div>
	               	   <table class="tbl-ty1">
	                      <colgroup>
	                          <col width="33%" />
	                          <col width="*" />
	                      </colgroup>
	                      <tbody>
			                <tr>
			                    <th>승인/반려 내용</th>
			                    <td style="height:130px;"><div style="padding: 0 0 10 0">
									<textarea name="rejectReasonView" id="rejectReasonView" placeholder="승인/반려 내용" style="width: 100%; height: 100px;" readonly><c:out value="${sanctionDoc.rejectReason}" /></textarea>
								</div></td>
			                 </tr>
			               </tbody>
			            </table>
			      </div>
             </c:if>
       </div>
       
               
	
		<!-- footer -->	
		<jsp:include page="/m/web/common/footer.jsp" >
			<jsp:param value="data_position" name=""/>
		</jsp:include>
		<!-- //footer -->	
	
	</form>
</div><!-- /page -->
</body>
</html>
