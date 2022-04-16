<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">

function chk(p){
	if(p=='3' || p=='6') {
		$("#startdate").show();
	} else {
		$("#startdate").hide();
	}
}
function btnSave_onClick(){
	var frm = document.frm;
	
	if(frm.use_kind.value == "") {
		alert("용도를 기입해 주십시오.");
		frm.use_kind.focus();
		return;
	}
	
	if(frm.use_place.value == "") {
		alert("제출처를 기입해 주십시오.");
		frm.use_place.focus();
		return;
	}
	
	if(frm.ctype.value == "") {
		alert("증명서를 선택해 주십시오.");
		frm.ctype.focus();
		return;
	} else if(frm.ctype.value == "3" || frm.ctype.value == "6") {
		if(frm.start_date.value == "" ) {
			alert("기간을 선택해 주십시오.");
			frm.start_date.focus();
			return;
		}
	}
	
	  jQuery.ajax({
		type : 'post',
		url : '/action/MobileCertificateAction.do?mode=saveCertificate',
		data : jQuery("form[name=frm]").serialize(),
		dataType : 'json',
		error : function(xhr, status, error){
			alert("저장할 수 없습니다.");
		},
		success : function(json){
			location.href = "/action/MobileCertificateAction.do?mode=list";
			alert("신청되었습니다.");
		}
	});  
	
	 /* $.getJSON("/action/MobileCertificateAction.do?mode=sendSMSCertificate", {"ctype":$("#ctype").val(), "use_place":$("#use_place").val()},
			function(data, status) {
				if(data.resultMsg == "1"){
					jQuery.ajax({
						type : 'post',
						url : '/action/MobileCertificateAction.do?mode=saveCertificate',
						data : jQuery("form[name=frm]").serialize(),
						error : function(xhr, status, error){
							alert("신청 오류.");
						},
						success : function(json){
							location.href = "/action/MobileCertificateAction.do?mode=list";
							alert("신청되었습니다.");
						}
					});
				} else {
					alert("오류 발생.")
				}
			});    */
}
</script>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head> 
<body> 
<form name="frm" method="post">
<div id="certificateFormPage"  class="sub_container">

       <!-- header -->
		<jsp:include page="/m/web/common/header.jsp" >
			<jsp:param value="fixed" name="data_position" />
		</jsp:include>
		<!-- // header -->
		
		<!-- sub_container -->
		<div>
            <div class="topbar">
                <div>
                    <button type="button" class="btn arrowL" title="이전 페이지" onclick="history.back()"><i class="mdi mdi-arrow-left"></i></button>
                    <ul><p>증명서 신청</ul>
                </div>
                <ul>
                    <li><button type="button" class="btn c-main" title="게시글 작성" onclick="javascript:btnSave_onClick();"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
                </ul>
           </div>
            
            <!-- contents -->
            <div class="contents">
            	<div class="inner">
            		<div class="write info">
            			<input type="hidden" name="userDept" value='<%=session.getAttribute("deptName") %>' />
            			<input type="hidden" name="mobileNo" value='<%=session.getAttribute("mobileNo") %>' />
            			<input type="text" value="본인" disabled />
            			<input type="text" name="use_kind" placeholder="용도" />
            			<input type="text" name="use_place" id="use_place" placeholder="제출처" />
            			<select name="ctype" id="ctype" onchange="javascript:chk(this.value);">
            				<%
            					String jobClass = (String)session.getAttribute("jobClass");
            					pageContext.setAttribute("jobClass", jobClass);
            				%>
            				<c:choose>
	            				<c:when test="${jobClass == 'A' || jobclass == 'B' || jobclass == 'N'}">
		            				<option value="">증명서 구분</option>
		            				<option value="2">경력확인서</option>	
									<option value="3">갑근세 원천징수증명서</option>
									<option value="5">학위증명서</option>
								</c:when>
								<c:otherwise>
									<option value="">증명서 구분</option>
									<option value="1">재직증명서</option>
									<option value="3">갑근세 원천징수증명서</option>
									<option value="5">학위증명서</option>
									<option value="6">근로소득원천징수영수증</option>	
								</c:otherwise>
							</c:choose>
            			</select>
            			<div id="startdate" style="display:none;">
	            			<select name="start_date" id="start_date">
	            				<option value="">기간 선택</option>
	            				<c:forEach var="yy" begin="2020" end="2022">
									<option value="<c:out value="${yy}"/>" <c:if test="${start_date == yy}">selected</c:if>><c:out value="${yy}"/></option>
								</c:forEach>
	            			</select>
            			</div>
            		</div>
            		
            	</div>
            </div>
		</div>
	
	<!-- footerx -->
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include>
	
</div>
</form>
</body>
</html>
