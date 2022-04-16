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
                    <ul><p><c:out value="${year}" />년 <c:out value="${month}" />월</p></ul>
                </div>
                <ul>
                    <li><button type="button" class="btn c-sub" title="검색 창 열기" onclick="javascript:layer_open(this,'pop_search');"><i class="mdi mdi-magnify"></i></button></li>
                </ul>
                <!-- 검색 창 팝업 -->
                <div id="pop_search" class="popup_layer pop_search">
                    <div class="popup_bg"></div>
                    <div class="popup_inner">
                        <div class="pop_close">
                            <input type="text" name="pname" id="pname" placeholder="프로젝트명 검색" />
							<button type="button" class="btn-close" onclick="javascript:layer_close('pop_search');" title="닫기"><i class="mdi mdi-close"></i></button>
                        </div>
                        <div class="write_group">
                            <select name="year" id="year">
                            	<option value="">선택</option>
                            	<c:forEach var="i" begin="2006" end="2022">
									<option value="<c:out value="${i}"/>" <c:if test="${year == i}">selected</c:if>><c:out value="${i}"/>년</option>
								</c:forEach>
							</select>
							<select name="month" id="month">
								<option value="">선택</option>
								<option value="01" <c:if test="${month == 01}">selected</c:if>>01월</option>
								<option value="02" <c:if test="${month == 02}">selected</c:if>>02월</option>
								<option value="03" <c:if test="${month == 03}">selected</c:if>>03월</option>
								<option value="04" <c:if test="${month == 04}">selected</c:if>>04월</option>
								<option value="05" <c:if test="${month == 05}">selected</c:if>>05월</option>
								<option value="06" <c:if test="${month == 06}">selected</c:if>>06월</option>
								<option value="07" <c:if test="${month == 07}">selected</c:if>>07월</option>
								<option value="08" <c:if test="${month == 08}">selected</c:if>>08월</option>
								<option value="09" <c:if test="${month == 09}">selected</c:if>>09월</option>
								<option value="10" <c:if test="${month == 10}">selected</c:if>>10월</option>
								<option value="11" <c:if test="${month == 11}">selected</c:if>>11월</option>
								<option value="12" <c:if test="${month == 12}">selected</c:if>>12월</option>
							</select>
                        </div>            		                   
                        <button type="button" class="btn c-wt" onclick="doSearch();" title="검색">검색</button>
                    </div>
                </div>
                <!-- // 검색 창 팝업 -->
           </div>
            
            <!-- contents -->
           <div class="contents">
              <div class="tbl-wrap">
                  <table class="tbl-ty1">
                  	<colgroup>
                  		<col width="33%" />
                  		<col width="33%" />
                  		<col width="33%" />
                  	</colgroup>
                  	<thead>
                  		<tr>
                  			<th>프로젝트 명</th>
                  			<th>PJ관리부서</th>
                  			<th>실수령액</th>
                  		</tr>
                  	</thead>
                  	<tbody>
                  	<c:choose>
                  		<c:when test="${!empty result.list }">
		                  	<c:forEach var="result" items="${result.list }">
		                  		<tr>
		                  			<td><c:out value="${result.pname}" /></td>
		                  			<td><c:out value="${result.deptName}" /></td>
		                  			<td><fmt:formatNumber value="${result.teaAmt}" type="number" var="teaAmt" pattern="#,###"/>
		                  			<c:out value="${teaAmt}" />원</td>
		                  		</tr>
		                  	</c:forEach>
		                  	<tr>
		                  		<th colspan="2">성과급 합계</th>
		                  		<td>
		                  			<fmt:formatNumber value="${result.list[0].total}" type="number" var="total" pattern="#,###"/>
		                  			<c:out value="${total }"/>원
		                  		</td>
		                  	</tr>
		                  </c:when>
		                  <c:otherwise>
                  			<td colspan='3'>검색 결과가 없습니다.</td>
                  		  </c:otherwise>
                    </c:choose>
                  	</tbody>
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
		<input type="hidden" name="pname"			value="<c:out value="${pname }"/>">
		<input type="hidden" name="year"			value="<c:out value="${year }"/>">
		<input type="hidden" name="month"			value="<c:out value="${month }"/>">
	
	<!-- footerx -->
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include>
	
</div>
</form>
</body>
</html>
