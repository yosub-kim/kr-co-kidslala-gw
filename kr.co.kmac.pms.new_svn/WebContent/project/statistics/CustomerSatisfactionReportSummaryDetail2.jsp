<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>고객만족도  상세 현황</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function goPage(pageNumber){
	//alert(pageNumber);
	frm.pg.value = pageNumber;
	frm.submit();
}
function Show_DetailView(projectCode, businessTypeCode, seq, id){
	var ActionURL = "/action/CustomerSatisfactionReportAction.do";
	ActionURL += "?mode=getValueCustomerSatisfaction";
	ActionURL += "&projectCode=" + projectCode;
	ActionURL += "&seq=" + seq;
	
	var status = AjaxRequest.get({
		'url':ActionURL
		, 'anotherParameter':'false'
		, 'onSuccess':function(obj){
	       	var res = eval('(' + obj.responseText + ')');  // json타입의 값을 자바스크립트 객체에 담음
	       	var rsObj = res.detailView;
	       	var rsObj2 = res.detailViewProEs;	//
	       	var rsObj3 = res.detailViewConEs;
	       	var rsObj4 = res.detailViewPubEduProEs;	//
	       	//alert(rsObj[0].projectName);
	       	var sHtml = "";

	    if(businessTypeCode=="BTA" || businessTypeCode=="BTJ") {	// 컨설팅 및 진단

	    	sHtml += "<table width=\"640\">\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td height='30'><font color='blue'>* 고객만족도 상세</font></td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td>\n";
	       	sHtml += "<table border=0 cellpadding=0 cellspacing=0>\n";
	       	sHtml += "<tr align=\"center\">\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"100\">구분</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"540\" colspan=\"6\">항목</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableTitle_center' rowspan=\"2\">프로젝트 전반에<br>대한 평가</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >전반적만족도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >추천의향</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >산출물만족도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >계약내용준수</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >평가</td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer3 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer4 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer5 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer6 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'><b>" + rsObj2[0].estimateP + "</b></td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "</table>\n";
	       	sHtml += "</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "</table><p>";

	    } else if(businessTypeCode=="BTD") {	// 리서치

	    	sHtml += "<table width=\"640\">\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td height='30'><font color='blue'>* 고객만족도 상세</font></td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td>\n";
	       	sHtml += "<table border=0 cellpadding=0 cellspacing=0>\n";
	       	sHtml += "<tr align=\"center\">\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"100\">구분</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"540\" colspan=\"6\">항목</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableTitle_center' rowspan=\"2\">프로젝트 전반에<br>대한 평가</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >전반적만족도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >추천의향</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >산출물만족도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >계약내용준수</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >평가</td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer3 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer4 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer5 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer6 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'><b>" + rsObj2[0].estimateP + "</b></td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "</table>\n";
	       	sHtml += "</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "</table><p>";

	    } else if(businessTypeCode=="N4" || businessTypeCode=="SS") {

	    	sHtml += "<table width=\"640\">\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td height='30'><font color='blue'>* 고객만족도 상세</font></td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td>\n";
	       	sHtml += "<table border=0 cellpadding=0 cellspacing=0>\n";
	       	sHtml += "<tr align=\"center\">\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"100\">구분</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"540\" colspan=\"6\">항목</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableTitle_center' rowspan=\"2\">교육 전반에<br>대한 평가</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >전반적만족도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >추천의향</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >현업활용도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >계약내용준수</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >평가</td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer3 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer4 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer5 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer6 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'><b>" + rsObj2[0].estimateP + "</b></td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "</table>\n";
	       	sHtml += "</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "</table><p>";

	    } else if(businessTypeCode=="DE") {

	    	sHtml += "<table width=\"640\">\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td height='30'><font color='blue'>* 고객만족도 상세</font></td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td>\n";
	       	sHtml += "<table border=0 cellpadding=0 cellspacing=0>\n";
	       	sHtml += "<tr align=\"center\">\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"100\">구분</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"540\" colspan=\"6\">항목</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableTitle_center' rowspan=\"2\">연수 전반에<br>대한 평가</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >전반적만족도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >추천의향</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >숙박, 교통 및<br>음식 만족도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >현업 적용<br>도움 여부</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"108\" >평가</td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer3 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer4 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer5 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'>" + rsObj2[0].answer6 + " / 25</td>\n";
	       	sHtml += "<td class='detailTableField_center'><b>" + rsObj2[0].estimateP + "</b></td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "</table>\n";
	       	sHtml += "</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "</table><p>";

	    } else if(businessTypeCode=="N1" || businessTypeCode=="N2") {

	    	sHtml += "<table width=\"640\">\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td height='30'><font color='blue'>* 고객만족도 상세</font></td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td>\n";
	       	sHtml += "<table border=0 cellpadding=0 cellspacing=0>\n";
	       	sHtml += "<tr align=\"center\">\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"100\">구분</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"540\" colspan=\"7\">항목</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableTitle_center' rowspan=\"2\">교육에 대한 평가</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"80\" >내용의<br>체계성</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"80\" >현업활용도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"80\" >교육시설</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"80\" >교육진행</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"80\" >부대시설</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"80\" >전반적<br>만족도</td>\n";
	       	sHtml += "<td class='detailTableTitle_center' width=\"80\" >평가</td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "<tr>\n";
	       	sHtml += "<td class='detailTableField_center' width=\"80\" >" + rsObj4[0].answer1 + " / 20</td>\n";
	       	sHtml += "<td class='detailTableField_center' width=\"80\" >" + rsObj4[0].answer2 + " / 20</td>\n";
	       	sHtml += "<td class='detailTableField_center' width=\"80\" >" + rsObj4[0].answer3 + " / 10</td>\n";
	       	sHtml += "<td class='detailTableField_center' width=\"80\" >" + rsObj4[0].answer4 + " / 20</td>\n";
	       	sHtml += "<td class='detailTableField_center' width=\"80\" >" + rsObj4[0].answer5 + " / 10</td>\n";
	       	sHtml += "<td class='detailTableField_center' width=\"80\" >" + rsObj4[0].answer6 + " / 20</td>\n";
	       	sHtml += "<td class='detailTableField_center' width=\"80\" ><b>" + rsObj4[0].estimateP + "</b></td>\n";	
	       	sHtml += "</tr>\n";
	       	sHtml += "</table>\n";
	       	sHtml += "</td>\n";
	       	sHtml += "</tr>\n";
	       	sHtml += "</table><p>";
			   
	    }
		    
	       document.getElementById("DetailView_"+id).innerHTML = sHtml;
	       var lst = document.getElementById("ListData").childNodes.length/2;
	       for(var i=1;i<=lst;i++){
		       if(i == parseInt(id,10)){
			       if(document.getElementById("DetailView_"+i).style.display == ""){
			    	   document.getElementById("DetailView_"+i).style.display = "none";
			       } else {
				       document.getElementById("DetailView_"+i).style.display = "";
			       }
		       } else {
		    	   document.getElementById("DetailView_"+i).style.display = "none";
		       }
	       }
	    
		}
		,'onLoading':function(obj){}
		,'onError':function(obj){
				alert(obj.responseText);
		}
	}); status = null;
}

</script>
</head>
<body style="width: 690px; padding-left:10px">
<form name="frm" method="post">
<%@ include file="/common/include/includeBodyTop.jsp"%>
<%-- 작업영역 --%>
	<table width="100%">
		<tr>
			<td>
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="고객만족도 상세 현황 보기" />
					<jsp:param name="backButtonYN" value="N" />
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td height="35px">
				<div class="btNbox pb15">
					<div class="btNlefTdiv">				
						<img src="/images/sub/line3Blit.gif" alt="">
						<span class="bold colore74c3a"><c:out value="${result.valueListInfo.totalNumberOfEntries}"/></span>
						<span>Total - Page(<span><c:out value="${result.valueListInfo.pagingPage}"/></span> of <span><c:out value="${result.valueListInfo.totalNumberOfPages}"/></span>)</span>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" class="listTable" id='listTable'>
					<thead>
						<tr height="25px">
							<td width="15%">비즈니스유형</td>	
							<td width="12%">조직단위</td>
							<td width="*">프로젝트명</td>
							<td width="*">고객사명</td>
							<td width="12%">고객만족도</td>
							<!-- <td width="10%">결과보기</td>  -->
						</tr>
					</thead>
					<tbody id="ListData">
					<c:forEach var="result" items="${result.list}" varStatus="i">
						<tr>						
							<td class="myoverflowC"><c:out value="${result.businessTypeName}" /></td>
							<td><c:out value="${result.runningDivName}" />
							<td class="myoverflow"><c:out value="${result.projectName}" />
							<td><c:out value="${result.customerName}" />
							<td style="cursor: hand;" onclick="Show_DetailView('<c:out value="${result.projectCode}" />','<c:out value="${result.businessTypeCode}" />','<c:out value="${result.seq}" />','<c:out value="${i.count}" />');"><c:out value="${result.score}" /></td>
							<!-- 
							<td align='center'><a href="#" onclick="Show_DetailView('<c:out value="${result.projectCode}" />','<c:out value="${result.businessTypeCode}" />','<c:out value="${result.seq}" />','<c:out value="${i.count}" />');"><img src='/images/btn_glass.jpg'></a></td>
							 -->
						</tr>
						<tr height="0">
							<td colspan="5" id="DetailView_<c:out value="${i.count}" />" style="display:none;"></td>
						</tr>
					</c:forEach>
					<c:if test="${empty result.list}"><tr><td colspan="5">검색 결과가 존재하지 않습니다.<br></td></tr></c:if>
					</tbody>
				</table>
			</td>
		</tr>
		<tr><td height='7'></td></tr>
		<%-- 페이징 영역 시작--%>
		<tr>
			<td>
				<%-- 페이징 영역 시작--%>
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr height="30">
							<td width="100%">
								<SCRIPT LANGUAGE="JavaScript">
									document.write( makePageHtml( 
											<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
											15, 
											<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
											<c:out value="${result.valueListInfo.pagingNumberPer}"/>)
									) ;
								</SCRIPT>
							</td>
						</tr>
					</table>									
				<input type="hidden" name="mode"			value="getValueCustomerSatisfactionReportSummaryDetail">
				<input type="hidden" name="year"			value="<c:out value="${year}"/>">
				<input type="hidden" name="month"			value="<c:out value="${month}"/>">
				<input type="hidden" name="runningDivCode"	value="<c:out value="${runningDivCode}"/>">
				<input type="hidden" name="businessTypeCode"	value="<c:out value="${businessTypeCode}"/>">
				<input type="hidden" name="grade"			value="<c:out value="${grade}"/>">
				<input type="hidden" name="pg"       	value="<c:out value="${pg}"/>">
			</td>
		</tr>
		<%-- 페이징 영역 끝--%>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>
</form>
</body>
</html>