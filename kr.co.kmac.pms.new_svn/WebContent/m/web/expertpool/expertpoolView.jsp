<%@ taglib prefix="c"			uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="code"		uri="/WEB-INF/commonCode.tld" %>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="kr.co.kmac.pms.expertpool.data.*"%>
<!DOCTYPE html>
<html lang="en">
<script type="text/javascript">
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
</script>
<head>
<%@ include file="/m/web/common/includeMeta.jsp"%>
<%@ include file="/m/web/common/includeCss.jsp"%>
<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head> 
<body>
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
               <p>인력 정보</p>
           </div>
        </div>
    </div>    
    <%ExpertPool ex = (ExpertPool)request.getAttribute("expertPool");%>	
    <!-- contents -->
      <div class="contents">
            <div class="tbl-wrap">
                    <table class="tbl view">							
                        <tbody>
                            <tr>
                                <td>
                                    <div>
                                        <div>
                                        	<div class="profile">
                                                <div class="img" style="width: 130px; height: 130px;">
                                                <c:choose><c:when test="${expertPool.photo ne '' }"><img id="MyPic" style="cursor:hand;" src="/servlet/PhotoDownLoadServlet?fileId=<c:out value="${expertPool.photo }"/>"></c:when><c:otherwise>
													<i class="mdi mdi-account"></i>
												</c:otherwise></c:choose></div>
                                                <p class="name"><c:out value="${expertPool.name}"/></p>
                                                <p class="resident_num"><p class="resident_num"><script>document.write(getAge('<%=ex.getUid().substring(0,7) %>'));</script>세 </p></p>
                                            </div>
                                        	<div class="sub_title"><p style="font-size: 18px;">개인정보</p></div>
                                            <ul class="info com">
                                            	<%-- <li><i class="mdi mdi-domain"></i><p><c:out value="${expertPool.name}"/></p></li> --%>
                                            	<%-- <li><i class="mdi mdi-account-tie"></i><p><c:out value="${expertPool.name}"/></p></li>
                                                <li><i class="mdi mdi-domain"></i><p><c:out value="${expertPool.company}"/> - <c:out value="${expertPool.deptName}"/></p></li>
                                                <li><i class="mdi mdi-format-list-bulleted"></i><p><code:code tableName="EMP_WORKING_TYPE" code="${expertPool.jobClass}"/>
												<c:if test="${expertPool.jobClass eq 'C' }">(<code:code tableName="EXPERT_FIELD" code="${expertPool.rank}"/>)</c:if></p></li> --%>
												<%-- <li><i class="mdi mdi-sitemap"></i><p><c:out value="${expertPool.deptName}"/></p></li> --%>
                                                <li><i class="mdi mdi-cellphone"></i><p><c:out value="${expertPool.mobileNo}"/></p></li>
                                               <%--  <li><i class="mdi mdi-phone"></i><p><c:out value="${expertPool.companyTelNo}"/></p></li>
                                                <li><i class="mdi mdi-fax"></i><p><c:out value="${expertPool.companyFaxNo}"/></p></li>
                                                <li><i class="mdi mdi-email"></i><p><c:out value="${expertPool.email}"/></p></li> --%>
                                            </ul>
                                        </div>
                                        <br><br>
                                        <div>
                                        	<div class="sub_title"><p style="font-size: 18px;">회사정보</p></div>
                                            <ul class="info com">
                                            	<%-- <li><i class="mdi mdi-domain"></i><p><c:out value="${expertPool.name}"/></td></p></li> --%>
                                            	<%-- <li><i class="mdi mdi-account-tie"></i><p><c:out value="${expertPool.name}"/></p></li> --%>
                                                <li><i class="mdi mdi-domain"></i><p><c:out value="${expertPool.company}"/> / <c:out value="${expertPool.deptName}"/></p></li>
                                                <li><i class="mdi mdi-format-list-bulleted"></i><p><code:code tableName="EMP_WORKING_TYPE" code="${expertPool.jobClass}"/> / <c:out value="${expertPool.companyPositionName}"/></p></li>
												<%-- <li><i class="mdi mdi-sitemap"></i><p><c:out value="${expertPool.deptName}"/></p></li> --%>
                                                <%-- <li><i class="mdi mdi-cellphone"></i><p><c:out value="${expertPool.mobileNo}"/></p></li> --%>
                                                <li><i class="mdi mdi-phone"></i><p><c:out value="${expertPool.companyTelNo}"/></p></li>
                                                <li><i class="mdi mdi-fax"></i><p><c:out value="${expertPool.companyFaxNo}"/></p></li>
                                                <li><i class="mdi mdi-email"></i><p><c:out value="${expertPool.email}"/></p></li>
                                            </ul>
                                        </div>
                                    </div>
                                </td>
                            </tr>               
                        </tbody>
                    </table>                          
                </div>

	
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="nofixed" name="data_position" />
	</jsp:include><!-- /footerx	 -->
	
	
</div><!-- /page -->
</body>
</html>
