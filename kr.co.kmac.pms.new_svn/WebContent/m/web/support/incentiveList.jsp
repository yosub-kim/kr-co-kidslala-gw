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
<div id="incentiveListPage"  class="sub_container">

       <!-- header -->
		<jsp:include page="/m/web/common/header.jsp" >
			<jsp:param value="fixed" name="data_position" />
		</jsp:include>
		<!-- // header -->
		
		<!-- sub_container -->
		<%-- <input type="hidden" id="boardListPg" value="0"/>
		<input type="hidden" id="boardListId" value="<c:out value="${param.bbsId}"/>"/> --%>
		
		<div>
            <div class="topbar">
                <div>
                    <button type="button" class="btn arrowL" title="이전 페이지" onclick="history.back()"><i class="mdi mdi-arrow-left"></i></button>
                    <ul><p>입금내역</p></ul>
                </div>
                <ul>
                    <li><button type="button" class="btn c-sub" title="검색 창 열기" onclick="javascript:layer_open(this,'pop_search');"><i class="mdi mdi-magnify"></i></button></li>
                </ul>
                <!-- 검색 창 팝업 -->
                <div id="pop_search" class="popup_layer pop_search">
                    <div class="popup_bg"></div>
                    <div class="popup_inner">
                        <div class="pop_close">
                            <div class="datepicker">
								<input type="text" placeholder="날짜 입력" name="start_date" id="start_date"/><button type="button" class="btn_datepicker" title="캘린더오픈"><i class="mdi mdi-calendar-month"></i></button>
							</div>
							<span style="color:white;">~</span>
							<div class="datepicker">
								<input type="text" placeholder="날짜 입력" name="end_date" id="end_date"/><button type="button" class="btn_datepicker" title="캘린더오픈"><i class="mdi mdi-calendar-month"></i></button>
							</div>
							<button type="button" class="btn-close" onclick="javascript:layer_close('pop_search');" title="닫기"><i class="mdi mdi-close"></i></button>
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
                			<col width="20%" />
                			<col width="45%" />
                			<col width="35%" />
                		</colgroup>
                		<thead>
                			<tr>
                				<th>집행일</th>
                				<th>적요</th>
                				<th>금액</th>
                			</tr>
                		</thead>
                		<tbody>
                		<c:forEach var="result" items="${result.list }">
                			<tr>
                				<td><c:out value="${result.fund_date }" /></td>
                				<td><c:out value="${result.remark }" /></td>
                				<td><c:out value="${result.amt }" />원</td>
                			</tr>
                		</c:forEach>
                		<c:if test="${empty result.list}">
	                  		<td colspan='3'>검색 결과가 없습니다.</td>
	                  	</c:if>
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
            
        <input type="hidden" name="pg"		value="<c:out value="${pg }"/>">
	
	<!-- footerx -->
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include>
	
</div>
</form>
</body>
</html>
