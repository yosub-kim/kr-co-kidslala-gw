<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<script type="text/javascript">
function doSearch() {
	document.emergencyConnection.target = "";		
	document.emergencyConnection.action = "/action/EmergencyConnectionAction.do?mode=retrieveListForMobile";
	document.emergencyConnection.submit();
}
</script>
<head>
	<%@ include file="/m/web/common/includeMeta.jsp"%>
	<%@ include file="/m/web/common/includeCss.jsp"%>
	<%@ include file="/m/web/common/includeJavaScript.jsp"%>
</head> 
<body> 
<form name="emergencyConnection" method="post">

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
               <p>비상연락망</p>
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
                       <input type="text" name="name" class="textInput_left"  placeholder="이름을 입력하시오." value="<c:out value="${name}"/>" >					
                       <button type="button" class="btn-close" onclick="javascript:layer_close('pop_search');" title="닫기"><i class="mdi mdi-close"></i></button>
                   </div>
                   <div class="write_group">
							<select name="jobClass" id="jobClass"  >
								<option value="A" <c:if test="${jobClass=='A'}">selected</c:if>>상근</option>
								<option value="J" <c:if test="${jobClass=='J'}">selected</c:if>>상임</option>
								<option value="N" <c:if test="${jobClass=='N'}">selected</c:if>>RA</option>
						</select>
                   </div>
                   <button type="button" class="btn c-wt" title="검색" onclick="location.href='javascript:doSearch();'">검색</button>
               </div>
           </div>
           <!-- // 검색 창 팝업 -->
    	</div>
      </div>
      
      <!-- Contents -->
      <div class="contents">
      	<div class="tbl-wrap">

                    <table class="tbl-ty1">
                        <colgroup>
                            <col width="20%" />
                            <col width="20%" />
                            <col width="*" />
                           <%--  <col width="20%" /> --%>
                        </colgroup>
                        <thead>
                            <tr>
                                <th>부서</th>
                                <th>이름</th>
                                <th>연락처(내선)/이메일</th>
                            </tr>
                        </thead>					
                        <tbody>
                       		 <c:forEach var="grp" items="${result}">
								<tr>
									<c:if test="${grp.dept != chkVal}">
										<td rowspan="<c:out value="${grp.rowCnt }" />" style="text-align: center"><c:out value="${grp.dept}" escapeXml="false" /></td>
									</c:if>
									<td onclick="location.href='/action/ExpertPoolManagerAction.do?mode=infoviewForMobile&ssn=<c:out value="${grp.ssn }" />'" style="text-align: center;"><c:out value="${grp.name}" /></td>
								<c:choose><c:when test="${jobClass != 'J' and jobClass != 'R'}">
									<td onclick="location.href='/action/ExpertPoolManagerAction.do?mode=infoviewForMobile&ssn=<c:out value="${grp.ssn }" />'" style="text-align: center;"><c:out value="${grp.mobileNo}" /> (<c:choose><c:when test="${grp.companyTelNo == '' }">-</c:when><c:otherwise><c:out value="${grp.companyTelNo}" /></c:otherwise></c:choose>) /<br><c:out value="${grp.email}" /></td>
								</c:when><c:otherwise>
									<td onclick="location.href='/action/ExpertPoolManagerAction.do?mode=infoviewForMobile&ssn=<c:out value="${grp.ssn }" />'" style="text-align: center;"><c:out value="${grp.mobileNo}" /> /<br><c:out value="${grp.email}" /></td>
								</c:otherwise></c:choose>
								</tr>
								<c:set var="chkVal" value="${grp.dept}"/>
							</c:forEach>
							<c:if test="${empty result}"><tr><td colspan="4">데이터가 없습니다.</td></tr></c:if>
                       </tbody>
                  </table>
              </div>
      </div>
      
      
	 
		<!-- footer -->
		<jsp:include page="/m/web/common/footer.jsp" >
			<jsp:param value="data_position" name=""/>
		</jsp:include>
		<!-- // footer -->
</form>
</body>
</html>
