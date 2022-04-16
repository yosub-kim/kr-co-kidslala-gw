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
function paperPrint(ctype, certify_num) {
	var url = "";
	if(ctype == "1" || ctype == "2")
		url = "print1.asp";
	else if(ctype == "3")
		url = "print2.asp";
	else if(ctype == "4")
		url = "print3.asp";
	else if(ctype == "5")
		url = "print5.asp";
	else
		url = "print6.asp";
	
	location.href='https://intranet.kmac.co.kr/kmac/certificate/' + url + '?certify_num=' + certify_num;
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
<div id="certificateListPage"  class="sub_container">

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
                    <li><button type="button" class="btn c-sub" title="검색 창 열기" onclick="javascript:layer_open(this,'pop_search');"><i class="mdi mdi-magnify"></i></button></li>
                    <li><button type="button" class="btn c-main" title="게시글 작성" onclick="location.href='/action/MobileCertificateAction.do?mode=inputForm'"><i class="mdi mdi-pencil-plus-outline"></i></button></li>
                </ul>
                <!-- 검색 창 팝업 -->
                <div id="pop_search" class="popup_layer pop_search">
                    <div class="popup_bg"></div>
                    <div class="popup_inner">
                        <div class="pop_close">
                            <p></p>
							<button type="button" class="btn-close" onclick="javascript:layer_close('pop_search');" title="닫기"><i class="mdi mdi-close"></i></button>
                        </div>
                        <div class="write_group">
                            <select name="ctype" id="ctype">
                            	<option value="">신청구분</option>
								<option value="1" <c:if test="${ctype=='1'}">selected</c:if>>재직증명서</option>
								<option value="2" <c:if test="${ctype=='2'}">selected</c:if>>경력확인서</option>
								<option value="5" <c:if test="${ctype=='5'}">selected</c:if>>학위확인서</option>
								<option value="6" <c:if test="${ctype=='6'}">selected</c:if>>근로소득원천징수영수증</option>
							</select>
							<select name="status" id="status">
								<option value="">진행현황</option>
								<option value="0" <c:if test="${status == '0' }">selected</c:if>>신청</option>
								<option value="1" <c:if test="${status == '1' }">selected</c:if>>진행</option>
								<option value="2" <c:if test="${status == '2' }">selected</c:if>>완료</option>
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
                  <table class="tbl list">
                  	<tbody>
                  		<c:forEach var="result" items="${result.list}">
                  			<tr>
                  				<td style="cursor:pointer" onclick="javascript:paperPrint('<c:out value="${result.ctype }" />','<c:out value="${result.certify_num }" />');">
                  					<div>
                  						<div>
                  							<c:choose><c:when test="${result.newIcon eq 'new'}"><div class="subject new"></c:when><c:otherwise><div class="subject"></c:otherwise></c:choose>
                  							<p><strong>[
                  							<c:if test="${result.ctype eq '1' }">재 직 증 명 서</c:if>
											<c:if test="${result.ctype eq '2' }">경 력 확 인 서</c:if>
											<c:if test="${result.ctype eq '3' }">소득세원천징수증명서</c:if>
											<c:if test="${result.ctype eq '4' }">상임 컨설턴트 원천징수</c:if>
											<c:if test="${result.ctype eq '5' }">학 위 증 명 서</c:if>
											<c:if test="${result.ctype eq '6' }">근로소득원천징수영수증</c:if>]
                  							</strong>&nbsp;/&nbsp;
                  							<c:if test="${result.gubun eq '1' }">본인</c:if>
											<c:if test="${result.gubun eq '2' }">타인</c:if>&nbsp;/&nbsp;
                  							<c:out value="${result.username }" /></p>
                  						</div><br/>
                  						<div><p>
                  							<c:out value="${result.use_kind }" />&nbsp;/&nbsp;
                  							<c:out value="${result.use_place }" />
                  						</p></div><br/>
                  						<ul class="info">
                  							<li><i class="mdi mdi-clock-outline"></i><p><c:out value="${result.regdate }" /></p></li>
                  							<li><i class="mdi mdi-account"></i><p><c:out value="${result.writer }" /></p></li>
                  						</ul>
                  					</div>
                  				</td>
                  			</tr>
                  		</c:forEach>
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
		<input type="hidden" name="pname"			value="<c:out value="${ctype }"/>">
		<input type="hidden" name="year"			value="<c:out value="${status }"/>">
	
	<!-- footerx -->
	<jsp:include page="/m/web/common/footer.jsp" >
		<jsp:param value="data_position" name=""/>
	</jsp:include>
	
</div>
</form>
</body>
</html>
