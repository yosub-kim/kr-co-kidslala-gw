<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/m/web/common/includeJavaScript.jsp"%>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
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
	<form id="boardListForm" method="post" action="">
		<input type="hidden" name="saveMode" id="saveMode" value="<c:out value="${saveMode}"/>">
		<input type="hidden" name="bbsId" 	id="bbsId" value="<c:out value="${bbsId}"/>">
		<input type="hidden" name="seq"   	id="seq"   value="<c:out value="${boardData.seq}"/>">
		<input type="hidden" name="ref"   	id="ref"   value="<c:out value="${boardData.ref}"/>">
		<input type="hidden" name="step"  	id="step"  value="<c:out value="${boardData.step}"/>">
		<input type="hidden" name="lev"   	id="lev"   value="<c:out value="${boardData.lev}"/>">											
		<input type="hidden" 				id="modeText" value="<c:out value="${modeText}" />"/>	
	
       	<!-- header -->
		<jsp:include page="/m/web/common/header.jsp" >
			<jsp:param value="fixed" name="data_position" />
		</jsp:include>
		<!-- // header -->
		
		<!-- topbar -->
		<div class="topbar">
	        <div>
	            <button type="button" class="btn arrowL" title="이전 페이지" onclick="history.back()"><i class="mdi mdi-arrow-left"></i></button>
	            <ul><p><c:choose><c:when test="${bbsId eq 'publicNotice'}" >공지사항</c:when><c:otherwise>열린세상</c:otherwise></c:choose></p></ul>
	        </div>
	        <ul>
	            <li><button type="button" class="btn c-main" title="작성" onclick="saveBoard('<c:out value="${codeEntity.key1 }"/>')"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
	        </ul>
	    </div>
	    <!-- // topbar -->
		
		<!-- Contents -->
        <div class="contents">
           	<div class="inner">
                <div class="write">
                   <div data-role="fieldcontain"> 
                  		<input type="text" name="subject" id="subject" value="<c:out value="${boardData.subject}"/>" data-mini="true" />
                   </div>
                   <div data-role="fieldcontain">
                  	 <textarea name="content" id="content" style="min-height: 200px;"><c:out value="${boardData.content}" escapeXml="false"/></textarea>
                   </div>    
                   <jsp:include page="/common/repository/fileUpload_mobile.jsp"><jsp:param value="Y" name="log"  /></jsp:include>                       
                </div>
           	</div>
        </div>
        <!-- // Contents -->
	</form>
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="" name="data_position" />
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
