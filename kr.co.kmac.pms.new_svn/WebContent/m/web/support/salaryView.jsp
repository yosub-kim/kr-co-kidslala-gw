<%@ taglib prefix="c" 		uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">


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
                    <ul><p>급여 상세내역</p></ul>
                </div>
           </div>
            
            <!-- contents -->
           <div class="contents">
              <div class="tbl-wrap">
              	<br/>
              	<div class="tbl-box">
              		<div class="tbl-head">
              			<p>기본정보</p>
              		</div>
              		<table class="tbl-ty1">
                  	<colgroup>
                  		<col width="33%" />
                  		<col width="33%" />
                  		<col width="33%" />
                  	</colgroup>
                  	<c:choose>
                  		<c:when test="${result.gubun eq '7' }">
                  			<thead>
		                  	<tr>
		                  		<th>성명</th>
		                  		<th>소속</th>
		                  		<th>직위</th>
		                  	</tr>
		                  	<tr>
		                  		<td><%=session.getAttribute("name") %></td>
		                  		<td><%=session.getAttribute("deptName") %></td>
		                  		<td><c:out value="${result.jik}" /></td>
		                 	</tr>
		                 	<tr>
		                  		<th>사번</th>
		                  		<th>귀속 지급기간</th>
		                  		<th>급여총액</th>
		                  	</tr>
		                  	<tr>
		                  		<td><c:out value="${result.empno}" /></td>
		                  		<td><c:out value="${result.remarks}" /></td>
		                  		<td><fmt:formatNumber value="${result.realPay}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	</thead>
		                 </c:when>
		                 <c:otherwise>
		               		<thead>
		                 	<tr>
		                  		<th>성명</th>
		                  		<th>소속</th>
		                  		<th>직위</th>
		                  	</tr>
		                  	<tr>
		                  		<td><%=session.getAttribute("name") %></td>
		                  		<td><%=session.getAttribute("deptName") %></td>
		                  		<td><c:out value="${result.jik}" /></td>
		                 	</tr>
		                 	<tr>
		                  		<th>사번</th>
		                  		<th>계좌번호</th>
		                  		<th>급여총액</th>
		                  	</tr>
		                  	<tr>
		                  		<td><c:out value="${result.empno}" /></td>
		                  		<td><c:out value="${result.bankNo}" /></td>
		                  		<td><fmt:formatNumber value="${result.realPay}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	</head>
		                 </c:otherwise>
		            </c:choose>
                  </table>
              	</div>
              	
              	<div class="tbl-box">
              		<div class="tbl-head">
              			<p>지급내역</p>
              		</div>
              		<table class="tbl-ty1">
                  	<colgroup>
                  		<col width="33%" />
                  		<col width="33%" />
                  		<col width="33%" />
                  	</colgroup>
                  	<c:choose>
                  		<c:when test="${result.gubun eq '7' }">
                  			<thead>
		                  	<tr>
		                  		<th>기본급</th>
		                  		<th>직책수당</th>
		                  		<th>업무추진비</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.basicPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.jikPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.taskPay}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                  		<th>중식대</th>
		                  		<th>교통비</th>
		                  		<th>PI성과급</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.lunchPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.carPay1}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.pieceRate}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                  		<th>PS성과급</th>
		                  		<th>퇴직급여충당금</th>
		                  		<th>국민연금 지원</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.profitShare}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.resignPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.insurance1}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                  		<th>건강보험 지원</th>
		                  		<th>고용보험 지원</th>
		                  		<th>산재보험 지원</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.taskPinsurance2ay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.insurance4}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.insurance9}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                  		<th>특별급</th>
		                  		<th colspan="2">비고</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.specialPay}" pattern="###,###,###" />원</td>
		                  		<td colspan="2"><c:out value="${result.remarks}" /></td>
		                 	</tr>
		                 	</thead>
		                 </c:when>
		                 <c:when test="${result.prov_date > '2019-03-01' }">
		                 	<thead>
		                 	<tr>
		                  		<th>기본급</th>
		                  		<th>직책수당</th>
		                  		<th>업무추진비</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.basicPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.jikPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.taskPay}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                  		<th>중식대</th>
		                  		<th>교통비(차량유)</th>
		                  		<th>교통비(차량무)</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.lunchPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.carPay1}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.carPay2}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                  		<th>기타수당</th>
		                  		<th>PI(1)성과급</th>
		                  		<th>PI(2)성과급</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.othersPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.bonus}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.carPay3}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                 		<th>PS성과급</th>
		                  		<th>소급(기본급/성과급/제수당)</th>
		                  		<th>그룹업무추진비</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.profitShare}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.basicPaySo}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.groupTaskPay}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                 		<th>실행성과급</th>
		                  		<th>지급총액</th>
		                  		<th>특별급</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.allowanceSo}" pattern="###,###,###" />원</td>
		                  		<td style="color:#f089a5"><fmt:formatNumber value="${result.allowance}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.specialPay}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                 		<th colspan="3">비고</th>
		                 	</tr>
		                 	<tr>
		                 		<td colspan="3"><c:out value="${result.remarks}" /></td>
		                 	</tr>
		                 	<tr>
		                 		<td colspan="3"><c:out value="${result.remarks1}" /></td>
		                 	</tr>
		                 	</thead>
		                 </c:when>
		                 <c:otherwise>
		                 	<thead>
		                 	<tr>
		                  		<th>기본급</th>
		                  		<th>직책수당</th>
		                  		<th>업무추진비</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.basicPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.jikPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.taskPay}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                  		<th>중식대</th>
		                  		<th>교통비(차량유)</th>
		                  		<th>교통비(차량무)</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.lunchPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.carPay1}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.carPay2}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                  		<th>교통(차량타인)</th>
		                  		<th>기타수당</th>
		                  		<th>PI(1)성과급</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.carPay3}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.othersPay}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.bonus}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                 		<th>PS성과급</th>
		                  		<th>기본급(소급)</th>
		                  		<th>제수당(소급)</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.profitShare}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.basicPaySo}" pattern="###,###,###" />원</td>
		                  		<td><fmt:formatNumber value="${result.allowanceSo}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                 		<th>성과급(PI)</th>
		                  		<th colspan="2">지급총액</th>
		                  	</tr>
		                  	<tr>
		                  		<td><fmt:formatNumber value="${result.pieceRate}" pattern="###,###,###" />원</td>
		                  		<td colspan="2" style="color:#f089a5"><fmt:formatNumber value="${result.allowance}" pattern="###,###,###" />원</td>
		                 	</tr>
		                 	<tr>
		                 		<th colspan="3">비고</th>
		                 	</tr>
		                 	<tr>
		                 		<td colspan="3"><c:out value="${result.remarks}" /></td>
		                 	</tr>
		                 	</thead>
		                 </c:otherwise>
		           	</c:choose>
                  </table>
              	</div>
              	
              	<div class="tbl-box">
              		<div class="tbl-head">
              			<p>공제내역</p>
              		</div>
              		<table class="tbl-ty1">
                  	<colgroup>
                  		<col width="33%" />
                  		<col width="33%" />
                  		<col width="33%" />
                  	</colgroup>
                  	<thead>
                  	<tr>
                  		<th>소득세</th>
                  		<th>지방소득세</th>
                  		<th>국민연금</th>
                  	</tr>
                  	<tr>
                  		<td><fmt:formatNumber value="${result.incomeTax1}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.incomeTax2}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.insurance1}" pattern="###,###,###" />원</td>
                 	</tr>
                 	<tr>
                  		<th>건강보험</th>
                  		<th>장기요양보험료</th>
                  		<th>고용보험</th>
                  	</tr>
                  	<tr>
                  		<td><fmt:formatNumber value="${result.insurance2}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.insurance3}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.insurance4}" pattern="###,###,###" />원</td>
                 	</tr>
                 	<tr>
                  		<th>동호회비</th>
                  		<th>주차카드</th>
                  		<th>기타공제(1)</th>
                  	</tr>
                  	<tr>
                  		<td><fmt:formatNumber value="${result.club}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.parkingPay}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.exmption1}" pattern="###,###,###" />원</td>
                 	</tr>
                 	<tr>
                  		<th>기타공제(2)</th>
                  		<th>연말소득세</th>
                  		<th>연말주민세</th>
                  	</tr>
                  	<tr>
                  		<td><fmt:formatNumber value="${result.exmption2}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.incomeTax3}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.incomeTax4}" pattern="###,###,###" />원</td>
                 	</tr>
                 	<tr>
                  		<th>추가국민연금</th>
                  		<th>추가건강보험</th>
                  		<th>추가장기요양</th>
                  	</tr>
                  	<tr>
                  		<td><fmt:formatNumber value="${result.insurance5}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.insurance6}" pattern="###,###,###" />원</td>
                  		<td><fmt:formatNumber value="${result.insurance7}" pattern="###,###,###" />원</td>
                 	</tr>
                 	<tr>
                  		<th>추가고용보험</th>
                  		<th colspan="2">공제액계</th>
                  	</tr>
                  	<tr>
                  		<td><fmt:formatNumber value="${result.insurance8}" pattern="###,###,###" />원</td>
                  		<td colspan="2" style="color:#f089a5"><fmt:formatNumber value="${result.totalExmption}" pattern="###,###,###" />원</td>
                 	</tr>
                 	</thead>
                  </table>
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
