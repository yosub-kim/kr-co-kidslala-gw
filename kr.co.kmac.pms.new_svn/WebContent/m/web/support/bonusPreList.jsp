<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">


function goPage(pageNumber) {
	frm.pg.value = pageNumber;
	frm.submit();
}
function doSearch() {
	frm.submit();
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
<div id="salaryListPage"  class="sub_container">

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
                    <ul><p>실시간 성과급 예상 내역</p></ul>
                </div>
           </div>
            
            <!-- contents -->
           <div class="contents">
              <div class="tbl-wrap">
                  <table class="tbl-ty1">
                  	<colgroup>
                  		<col width="50%" />
                  		<col width="25%" />
                  		<col width="25%" />
                  	</colgroup>
                  	<thead>
                  		<tr>
                  			<th>프로젝트 명</th>
                  			<th>품의 진행 여부</th>
                  			<th>성과급 소계</th>
                  		</tr>
                  	<tbody>
                  	<c:choose>
                  		<c:when test="${!empty result.list }">
		                  	<c:forEach var="result" items="${result.list }">
		                  		<tr>
		                  			<td><c:out value="${result.projectName}" /></td>
		                  			<td><c:out value="${result.isSanction}" /></td>
		                  			<td style="text-align: right">
										<fmt:formatNumber value="${result.eachSalary}" type="number" var="eachSalary" pattern="#,###"/>
										<c:out value="${eachSalary}" />
									</td>
		                  		</tr>
		                  	</c:forEach>
		                  	<tr>
		                  		<th colspan="2">현재 성과급 합계</th>
		                  		<td style="text-align: right">
		                  			<fmt:formatNumber value="${result.list[0].totalRealTimeSalary}" type="number" var="totalSalary" pattern="#,###"/>
		                  			<c:out value="${totalSalary }"/>
		                  		</td>
		                  	</tr>
                  		</c:when>
	                  	<c:otherwise>
	                  		<td colspan='3'>검색 결과가 없습니다.</td>
	                  	</c:otherwise>
                  	</c:choose>
                  	</tbody>
                  	</thead>
                  </table>
				
				<div class="paging_area">
					<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml( 
									<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
									10, 
									<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
					</SCRIPT>
				</div>
			</div>
		</div>
		
		<input type="hidden" name="pg"				value="<c:out value="${pg }"/>">
	
	<!-- footerx -->
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include>
	
</div>
</form>
</body>
</html>
