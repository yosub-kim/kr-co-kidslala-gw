<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>평가보기</title>
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goExpendList(projectcode) {
	document.frm.submit();	
	//alert("준비중입니다.");
}

function Show_DetailView(ssn,projectCode,item,id){
	var ActionURL = "/action/ProjectMemberEvalAction.do";
	ActionURL += "?mode=getConsultantEvalListDetailView";
	ActionURL += "&projectCode=" + projectCode;
	ActionURL += "&item=" + encodeURIComponent(item);
	ActionURL += "&ssn=" + ssn;
	
	var status = AjaxRequest.get({
		'url':ActionURL
		, 'anotherParameter':'false'
		, 'onSuccess':function(obj){
	       	var res = eval('(' + obj.responseText + ')');  // json타입의 값을 자바스크립트 객체에 담음
	       	var rsObj = res.detailView;
	       	var rsObj2 = res.detailViewProEs;
	       	var rsObj3 = res.detailViewConEs;
	       	var rsObj4 = res.detailViewPubEduProEs;
	       	var sHtml = "";
			
	    if(item=="컨설턴트평가") {
	       	// 컨설턴트 평가 항목 변경 check
	       	var itemChk = rsObj[0].answer10;
	       	// 상향/하양평가 check
	       	var evalChk = rsObj[0].evalChk;
			
	    	// 평가 항목 개편 이전
	    	if(itemChk != ""){
		    	sHtml += "<div style='padding: 30 0 30 0'>"
		    	sHtml += "<div class='board_box'>";
		    	sHtml += "<div class='title_both'>";
		    	sHtml += "<div class='h1_area'>";
		    	sHtml += "<p class='h1'>평가 내용</p>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "<div class='board_contents'>";
		    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
		    	sHtml += "<colgroup>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "</colgroup>";
		    	sHtml += "<tr>";
		    	sHtml += "<th>직무지식</th>";
		    	sHtml += "<th>정보수집/분석력</th>";
		    	sHtml += "<th>응용개선능력</th>";
		    	sHtml += "<th>이해력</th>";
		    	sHtml += "<th>수행력</th>";
		    	sHtml += "<th>판단력</th>";
		    	sHtml += "<th>표현력</th>";
		    	sHtml += "<th>협조성</th>";
		    	sHtml += "<th>책임감</th>";
		    	sHtml += "<th>도전의식</th>";
		    	sHtml += "<th>총 점</th>";
		    	sHtml += "<tr>";
		    	sHtml += "</tr>";
		    	sHtml += "<tr>";
		    	sHtml += "<td>"+rsObj[0].answer1+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer2+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer3+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer4+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer5+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer6+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer7+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer8+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer9+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer10+"</td>";
		    	sHtml += "<td>";
		    	sHtml += (parseInt(rsObj[0].answer1,10)+parseInt(rsObj[0].answer2,10)+parseInt(rsObj[0].answer3,10)+parseInt(rsObj[0].answer4,10)+parseInt(rsObj[0].answer5,10)+parseInt(rsObj[0].answer6,10)+parseInt(rsObj[0].answer7,10)+parseInt(rsObj[0].answer8,10)+parseInt(rsObj[0].answer9,10)+parseInt(rsObj[0].answer10,10));
		    	sHtml += "</td>";
		    	sHtml += "</tr>";
		    	sHtml += "</table>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
	    	}else if(evalChk == "down"){// 하향평가
		    	sHtml += "<div style='padding: 30 0 30 0'>"
		    	sHtml += "<div class='board_box'>";
		    	sHtml += "<div class='title_both'>";
		    	sHtml += "<div class='h1_area'>";
		    	sHtml += "<p class='h1'>평가 내용</p>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "<div class='board_contents'>";
		    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
		    	sHtml += "<colgroup>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: *'>";
		    	sHtml += "</colgroup>";
		    	sHtml += "<tr>";
		    	sHtml += "<th>1. 직무지식</th>";
		    	sHtml += "<th>2. 이해력</th>";
		    	sHtml += "<th>3. 협조성</th>";
		    	sHtml += "<th>4. 책임감</th>";
		    	sHtml += "<th>5. 도전의식</th>";
		    	sHtml += "<th>총 점</th>";
		    	sHtml += "<tr>";
		    	sHtml += "</tr>";
		    	sHtml += "<tr>";
		    	sHtml += "<td>"+rsObj[0].answer1+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer2+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer3+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer4+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer5+"</td>";
		    	sHtml += "<td>";
		    	sHtml += (parseInt(rsObj[0].answer1,10)+parseInt(rsObj[0].answer2,10)+parseInt(rsObj[0].answer3,10)+parseInt(rsObj[0].answer4,10)+parseInt(rsObj[0].answer5,10));
		    	sHtml += "</td>";
		    	sHtml += "</tr>";
		    	sHtml += "</table>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
	    	}else{ //상향평가
    			sHtml += "<div style='padding: 30 0 30 0'>"
		    	sHtml += "<div class='board_box'>";
		    	sHtml += "<div class='title_both'>";
		    	sHtml += "<div class='h1_area'>";
		    	sHtml += "<p class='h1'>평가 내용</p>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "<div class='board_contents'>";
		    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
		    	sHtml += "<colgroup>";
		    	sHtml += "<col style='width: 27%'>";
		    	sHtml += "<col style='width: 27%'>";
		    	sHtml += "<col style='width: 27%'>";
		    	sHtml += "<col style='width: *'>";
		    	sHtml += "</colgroup>";
		    	sHtml += "<tr>";
		    	sHtml += "<th>1. 협조성</th>";
		    	sHtml += "<th>2. 책임감</th>";
		    	sHtml += "<th>3. 시너지</th>";
		    	sHtml += "<th>총 점</th>";
		    	sHtml += "<tr>";
		    	sHtml += "</tr>";
		    	sHtml += "<tr>";
		    	sHtml += "<td>"+rsObj[0].answer1+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer2+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer3+"</td>";
		    	sHtml += "<td>";
		    	sHtml += (parseInt(rsObj[0].answer1,10)+parseInt(rsObj[0].answer2,10)+parseInt(rsObj[0].answer3,10));
		    	sHtml += "</td>";
		    	sHtml += "</tr>";
		    	sHtml += "</table>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
	    	}
	    } else if(item=="PL평가") {
	       	// 컨설턴트 평가 항목 변경 check
	       	var itemChk = rsObj[0].answer10;
	       	// 상향/하양평가 check
	       	var evalChk = rsObj[0].evalChk;

	    	// 평가 항목 개편 이전
	    	if(itemChk != ""){
	    		sHtml += "<div style='padding: 30 0 30 0'>"
		    	sHtml += "<div class='board_box'>";
		    	sHtml += "<div class='title_both'>";
		    	sHtml += "<div class='h1_area'>";
		    	sHtml += "<p class='h1'>평가 내용</p>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "<div class='board_contents'>";
		    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
		    	sHtml += "<colgroup>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "<col style='width: 9%'>";
		    	sHtml += "</colgroup>";
		    	sHtml += "<tr>";
		    	sHtml += "<th>방법론 제시력</th>";
		    	sHtml += "<th>문제 해결력</th>";
		    	sHtml += "<th>대안 창출력</th>";
		    	sHtml += "<th>납기준수</th>";
		    	sHtml += "<th>원가관리</th>";
		    	sHtml += "<th>품질유지</th>";
		    	sHtml += "<th>고객리더십</th>";
		    	sHtml += "<th>불만관리</th>";
		    	sHtml += "<th>책임감</th>";
		    	sHtml += "<th>후배육성</th>";
		    	sHtml += "<th>총 점</th>";
		    	sHtml += "<tr>";
		    	sHtml += "</tr>";
		    	sHtml += "<tr>";
		    	sHtml += "<td>"+rsObj[0].answer1+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer2+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer3+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer4+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer5+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer6+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer7+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer8+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer9+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer10+"</td>";
		    	sHtml += "<td>";
		    	sHtml += (parseInt(rsObj[0].answer1,10)+parseInt(rsObj[0].answer2,10)+parseInt(rsObj[0].answer3,10)+parseInt(rsObj[0].answer4,10)+parseInt(rsObj[0].answer5,10)+parseInt(rsObj[0].answer6,10)+parseInt(rsObj[0].answer7,10)+parseInt(rsObj[0].answer8,10)+parseInt(rsObj[0].answer9,10)+parseInt(rsObj[0].answer10,10));
		    	sHtml += "</td>";
		    	sHtml += "</tr>";
		    	sHtml += "</table>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
	    	}else if(evalChk == "down"){// 하향평가
		    	sHtml += "<div style='padding: 30 0 30 0'>"
		    	sHtml += "<div class='board_box'>";
		    	sHtml += "<div class='title_both'>";
		    	sHtml += "<div class='h1_area'>";
		    	sHtml += "<p class='h1'>평가 내용</p>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "<div class='board_contents'>";
		    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
		    	sHtml += "<colgroup>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: *'>";
		    	sHtml += "</colgroup>";
		    	sHtml += "<tr>";
		    	sHtml += "<th>1. 납기준수</th>";
		    	sHtml += "<th>2. 원가관리</th>";
		    	sHtml += "<th>3. 품질유지</th>";
		    	sHtml += "<th>4. 고객관리</th>";
		    	sHtml += "<th>5. 후배육성</th>";
		    	sHtml += "<th>총 점</th>";
		    	sHtml += "<tr>";
		    	sHtml += "</tr>";
		    	sHtml += "<tr>";
		    	sHtml += "<td>"+rsObj[0].answer1+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer2+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer3+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer4+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer5+"</td>";
		    	sHtml += "<td>";
		    	sHtml += (parseInt(rsObj[0].answer1,10)+parseInt(rsObj[0].answer2,10)+parseInt(rsObj[0].answer3,10)+parseInt(rsObj[0].answer4,10)+parseInt(rsObj[0].answer5,10));
		    	sHtml += "</td>";
		    	sHtml += "</tr>";
		    	sHtml += "</table>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
	    	}else{ //상향평가
	    		sHtml += "<div style='padding: 30 0 30 0'>"
		    	sHtml += "<div class='board_box'>";
		    	sHtml += "<div class='title_both'>";
		    	sHtml += "<div class='h1_area'>";
		    	sHtml += "<p class='h1'>평가 내용</p>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "<div class='board_contents'>";
		    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
		    	sHtml += "<colgroup>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: 17%'>";
		    	sHtml += "<col style='width: *'>";
		    	sHtml += "</colgroup>";
		    	sHtml += "<tr>";
		    	sHtml += "<th>1. 직무지식</th>";
		    	sHtml += "<th>2. 일정/자원관리</th>";
		    	sHtml += "<th>3. 표현력</th>";
		    	sHtml += "<th>4. 고객리더십</th>";
		    	sHtml += "<th>5. 후배육성</th>";
		    	sHtml += "<th>총 점</th>";
		    	sHtml += "<tr>";
		    	sHtml += "</tr>";
		    	sHtml += "<tr>";
		    	sHtml += "<td>"+rsObj[0].answer1+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer2+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer3+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer4+"</td>";
		    	sHtml += "<td>"+rsObj[0].answer5+"</td>";
		    	sHtml += "<td>";
		    	sHtml += (parseInt(rsObj[0].answer1,10)+parseInt(rsObj[0].answer2,10)+parseInt(rsObj[0].answer3,10)+parseInt(rsObj[0].answer4,10)+parseInt(rsObj[0].answer5,10));
		    	sHtml += "</td>";
		    	sHtml += "</tr>";
		    	sHtml += "</table>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
		    	sHtml += "</div>";
	    	}
	    } else if(item=="컨설팅고객평가") {
	    	
	    	sHtml += "<div style='padding: 30 0 30 0'>"
	    	sHtml += "<div class='board_box'>";
	    	sHtml += "<div class='title_both'>";
	    	sHtml += "<div class='h1_area'>";
	    	sHtml += "<p class='h1'>평가 내용</p>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";
	    	sHtml += "<div class='board_contents'>";
	    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
	    	sHtml += "<colgroup>";
	    	sHtml += "<col style='width: *'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "</colgroup>";
	    	sHtml += "<tr>";
	    	sHtml += "<th>구 분</th>"
	    	sHtml += "<th colspan='6'>항 목</th>"
	    	sHtml += "</tr>";
	    	sHtml += "<tr>";
	    	sHtml += "<th rowspan='2'>프로젝트 평가</th>";
	    	sHtml += "<th>전반적 만족도</th>";
	    	sHtml += "<th>추천 의향</th>";
	    	sHtml += "<th>산출물 만족도</th>";
	    	sHtml += "<th>계약 내용 준수</th>";
	    	sHtml += "<th>-</th>";
	    	sHtml += "<th>평가</th>";
	    	sHtml += "</tr>"
	    	sHtml += "<tr>"
	       	sHtml += "<td>" + rsObj2[0].answer3 + " / 25</td>";
	       	sHtml += "<td>" + rsObj2[0].answer4 + " / 25</td>";
	       	sHtml += "<td>" + rsObj2[0].answer5 + " / 25</td>";
	       	sHtml += "<td>" + rsObj2[0].answer6 + " / 25</td>";
	       	sHtml += "<td >-</td>";
	       	sHtml += "<td>" + rsObj2[0].estimateP + "</td>";	
	    	sHtml += "</tr>";
	    	sHtml += "<tr>";
	    	sHtml += "<th rowspan='2'>컨설턴트 평가</th>";
	    	sHtml += "<th>고객 요구 대응</th>";
	    	sHtml += "<th>전문성</th>";
	    	sHtml += "<th>의사 소통</th>";
	    	sHtml += "<th>일정 준수도</th>";
	    	sHtml += "<th>종합 만족도</th>";
	    	sHtml += "<th>평가</th>";
	    	sHtml += "</tr>"
	    	sHtml += "<tr>"
	       	sHtml += "<td>" + rsObj3[0].answer8 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer9 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer10 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer12 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer13 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].estimateC + "</td>";	
	    	sHtml += "</tr>";
	    	sHtml += "<tr>"
		    sHtml += "<td colspan='6'>종합평가 [(프로젝트 평가 * 0.5)+(개별 컨설턴트 평가 * 0.5) = 100점]</td>";
	       	sHtml += "<td>" + ((rsObj2[0].estimateP * 0.5) + (rsObj3[0].estimateC * 0.5)) + "</td>";
	    	sHtml += "</tr>"
	    	sHtml += "</table>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";

	    } else if(item=="리서치고객평가") {

	    	sHtml += "<div style='padding: 30 0 30 0'>"
	    	sHtml += "<div class='board_box'>";
	    	sHtml += "<div class='title_both'>";
	    	sHtml += "<div class='h1_area'>";
	    	sHtml += "<p class='h1'>평가 내용</p>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";
	    	sHtml += "<div class='board_contents'>";
	    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
	    	sHtml += "<colgroup>";
	    	sHtml += "<col style='width: *'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "</colgroup>";
	    	sHtml += "<tr>";
	    	sHtml += "<th>구 분</th>"
	    	sHtml += "<th colspan='6'>항 목</th>"
	    	sHtml += "</tr>";
	    	sHtml += "<tr>";
	    	sHtml += "<th rowspan='2'>프로젝트 평가</th>";
	    	sHtml += "<th>전반적 만족도</th>";
	    	sHtml += "<th>추천 의향</th>";
	    	sHtml += "<th>산출물 만족도</th>";
	    	sHtml += "<th>계약 내용 준수</th>";
	    	sHtml += "<th>-</th>";
	    	sHtml += "<th>평가</th>";
	    	sHtml += "</tr>"
	    	sHtml += "<tr>"
	       	sHtml += "<td>" + rsObj2[0].answer3 + " / 25</td>";
	       	sHtml += "<td>" + rsObj2[0].answer4 + " / 25</td>";
	       	sHtml += "<td>" + rsObj2[0].answer5 + " / 25</td>";
	       	sHtml += "<td>" + rsObj2[0].answer6 + " / 25</td>";
	       	sHtml += "<td >-</td>";
	       	sHtml += "<td>" + rsObj2[0].estimateP + "</td>";	
	    	sHtml += "</tr>";
	    	sHtml += "<tr>";
	    	sHtml += "<th rowspan='2'>컨설턴트 평가</th>";
	    	sHtml += "<th>고객 요구 대응</th>";
	    	sHtml += "<th>전문성</th>";
	    	sHtml += "<th>의사 소통</th>";
	    	sHtml += "<th>일정 준수도</th>";
	    	sHtml += "<th>종합 만족도</th>";
	    	sHtml += "<th>평가</th>";
	    	sHtml += "</tr>"
	    	sHtml += "<tr>"
	       	sHtml += "<td>" + rsObj3[0].answer8 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer9 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer10 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer12 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer13 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].estimateC + "</td>";	
	    	sHtml += "</tr>";
	    	sHtml += "<tr>"
		    sHtml += "<td colspan='6'>종합평가 [(프로젝트 평가 * 0.5)+(개별 컨설턴트 평가 * 0.5) = 100점]</td>";
	       	sHtml += "<td>" + ((rsObj2[0].estimateP * 0.5) + (rsObj3[0].estimateC * 0.5)) + "</td>";
	    	sHtml += "</tr>"
	    	sHtml += "</table>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";

	    } else if(item=="사내교육고객평가") {

	    	sHtml += "<div style='padding: 30 0 30 0'>"
	    	sHtml += "<div class='board_box'>";
	    	sHtml += "<div class='title_both'>";
	    	sHtml += "<div class='h1_area'>";
	    	sHtml += "<p class='h1'>평가 내용</p>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";
	    	sHtml += "<div class='board_contents'>";
	    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
	    	sHtml += "<colgroup>";
	    	sHtml += "<col style='width: *'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "<col style='width: 13%'>";
	    	sHtml += "</colgroup>";
	    	sHtml += "<tr>";
	    	sHtml += "<th>구 분</th>"
	    	sHtml += "<th colspan='6'>항 목</th>"
	    	sHtml += "</tr>";
	    	sHtml += "<tr>";
	    	sHtml += "<th rowspan='2'>프로젝트 평가</th>";
	    	sHtml += "<th>전반적 만족도</th>";
	    	sHtml += "<th>추천 의향</th>";
	    	sHtml += "<th>산출물 만족도</th>";
	    	sHtml += "<th>계약 내용 준수</th>";
	    	sHtml += "<th>-</th>";
	    	sHtml += "<th>평가</th>";
	    	sHtml += "</tr>"
	    	sHtml += "<tr>"
	       	sHtml += "<td>" + rsObj2[0].answer3 + " / 25</td>";
	       	sHtml += "<td>" + rsObj2[0].answer4 + " / 25</td>";
	       	sHtml += "<td>" + rsObj2[0].answer5 + " / 25</td>";
	       	sHtml += "<td>" + rsObj2[0].answer6 + " / 25</td>";
	       	sHtml += "<td >-</td>";
	       	sHtml += "<td>" + rsObj2[0].estimateP + "</td>";	
	    	sHtml += "</tr>";
	    	sHtml += "<tr>";
	    	sHtml += "<th rowspan='2'>컨설턴트 평가</th>";
	    	sHtml += "<th>강의 수준</th>";
	    	sHtml += "<th>강의 준비</th>";
	    	sHtml += "<th>강의 스킬</th>";
	    	sHtml += "<th>성의 및 열의</th>";
	    	sHtml += "<th>종합 만족도</th>";
	    	sHtml += "<th>평가</th>";
	    	sHtml += "</tr>"
	    	sHtml += "<tr>"
	       	sHtml += "<td>" + rsObj3[0].answer8 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer9 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer10 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer12 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].answer13 + " / 20</td>";
	       	sHtml += "<td>" + rsObj3[0].estimateC + "</td>";	
	    	sHtml += "</tr>";
	    	sHtml += "<tr>"
		    sHtml += "<td colspan='6'>종합평가 [(교육 평가 * 0.5)+(개별 강사 평가 * 0.5) = 100점]</td>";
	       	sHtml += "<td>" + ((rsObj2[0].estimateP * 0.5) + (rsObj3[0].estimateC * 0.5)) + "</td>";
	    	sHtml += "</tr>"
	    	sHtml += "</table>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";

	    } else if(item=="공개교육고객평가") {

	    	sHtml += "<div style='padding: 30 0 30 0'>"
	    	sHtml += "<div class='board_box'>";
	    	sHtml += "<div class='title_both'>";
	    	sHtml += "<div class='h1_area'>";
	    	sHtml += "<p class='h1'>평가 내용</p>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";
	    	sHtml += "<div class='board_contents'>";
	    	sHtml += "<table  id='projectProgressTable' class='tbl-edit td-c'>";
	    	sHtml += "<colgroup>";
	    	sHtml += "<col style='width: *'>";
	    	sHtml += "<col style='width: 11%'>";
	    	sHtml += "<col style='width: 11%'>";
	    	sHtml += "<col style='width: 11%'>";
	    	sHtml += "<col style='width: 11%'>";
	    	sHtml += "<col style='width: 11%'>";
	    	sHtml += "<col style='width: 11%'>";
	    	sHtml += "<col style='width: 11%'>";
	    	sHtml += "<col style='width: 11%'>";
	    	sHtml += "</colgroup>";
	    	sHtml += "<tr>";
	    	sHtml += "<th>구 분</th>"
	    	sHtml += "<th colspan='8'>항 목</th>"
	    	sHtml += "</tr>";
	    	sHtml += "<tr>";
	    	sHtml += "<th rowspan='2'>교육에 대한 평가</th>";
	    	sHtml += "<th>내용의 체계성</th>";
	    	sHtml += "<th>현업 활용도</th>";
	    	sHtml += "<th>교육 시설</th>";
	    	sHtml += "<th>교육 진행</th>";
	    	sHtml += "<th>부대 시설</th>";
	    	sHtml += "<th>전반적 만족도</th>";
	    	sHtml += "<th>평가</th>";
	    	sHtml += "</tr>"
	    	sHtml += "<tr>"
		    sHtml += "<td>" + rsObj4[0].answer1 + " / 20</td>";
	       	sHtml += "<td>" + rsObj4[0].answer2 + " / 20</td>";
	       	sHtml += "<td>" + rsObj4[0].answer3 + " / 10</td>";
	       	sHtml += "<td>" + rsObj4[0].answer4 + " / 20</td>";
	       	sHtml += "<td>" + rsObj4[0].answer5 + " / 10</td>";
	       	sHtml += "<td>" + rsObj4[0].answer6 + " / 20</td>";
	       	sHtml += "<td >" + rsObj4[0].estimateP + "</td>";
	    	sHtml += "</tr>";
	    	sHtml += "<tr>";
	    	sHtml += "<th rowspan='2'>강사에 대한 평가</th>";
	    	sHtml += "<th>강의 교재</th>";
	    	sHtml += "<th>강의 준비</th>";
	    	sHtml += "<th>강의 스킬</th>";
	    	sHtml += "<th>성의 및 열의</th>";
	    	sHtml += "<th>-</th>";
	    	sHtml += "<th>-</th>";
	    	sHtml += "<th>평가</th>";
	    	sHtml += "</tr>"
	    	sHtml += "<tr>"
		    sHtml += "<td>" + rsObj3[0].answer8 + " / 25</td>";
	       	sHtml += "<td>" + rsObj3[0].answer9 + " / 25</td>";
	       	sHtml += "<td>" + rsObj3[0].answer10 + " / 25</td>";
	       	sHtml += "<td>" + rsObj3[0].answer12 + " / 25</td>";
	       	sHtml += "<td>-</td>";
	       	sHtml += "<td>-</td>";
	       	sHtml += "<td>" + rsObj3[0].estimateC + "</td>";
	    	sHtml += "</tr>";
	    	sHtml += "<tr>"
		    sHtml += "<td colspan='6'>종합평가 [(교육 평가 * 0.5)+(개별 강사  평가 * 0.5) = 100점]</td>";
	       	sHtml += "<td>" + ((rsObj2[0].estimateP * 0.5) + (rsObj3[0].estimateC * 0.5)) + "</td>";
	    	sHtml += "</tr>"
	    	sHtml += "</table>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";
	    	sHtml += "</div>";   
	    }
		    
	       document.getElementById("DetailView_text_"+id).innerHTML = sHtml;
	       
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
<body>
<%-- 작업영역 --%>
	<!-- location -->
	<div class="location">
		<p class="menu_title">컨설턴트 평가 현황</p>
		<ul>
			<li class="home">HOME</li>
			<li>PMS</li>
			<li>프로젝트 현황</li>
			<li>컨설턴트 평가 현황</li>
		</ul>
	</div>
	<!-- // location -->
	
	
	<div class="contents sub">
		<div class="board_box">
			<div class="title_both">
				<div class="h1_area">
					<p class="h1">평가 현황 보기</p>
				</div>
			</div>
		
			<div class="board_contents">
				<table  id="projectProgressTable" class="tbl-edit td-c"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
					<thead>
						<colgroup>
							<col style="width: 15%" />
							<col style="width: *" />
							<col style="width: 15%" />
							<col style="width: 15%" />
							<col style="width: 15%" />
							<col style="width: 15%" />
						</colgroup>
						<tr>
							<th>컨설턴트명</th>
							<th>프로젝트명</th>
							<th>프로젝트 종료일</th>
							<th>평가자</th>
							<th>평가 점수</th>
							<th>상세 보기</th>	
						</tr>
					</thead>
					<tbody id="ListData">
						<c:forEach var="result" items="${result.list}" varStatus="i">
							<tr>						
								<td><c:out value="${result.name}" /></td>
								<td class="myoverflow"><c:out value="${result.projectName}" /></td>
								<td>
									<fmt:parseDate value="${result.realEndDate}" type="DATE" dateStyle="SHORT"  pattern="yyyyMMdd" var="date"/>
									<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" var="formattedFrom"/>
									<c:out value="${formattedFrom}" />
								</td>
								<td><c:out value="${result.writerName }" /></td>
								<td><c:out value="${result.total}" /></td>
								<td><a href="#" onclick="Show_DetailView('<c:out value="${result.ssn}" />','<c:out value="${result.projectCode}" />','<c:out value="${result.item}" />','<c:out value="${i.count}" />');"><img src='/images/btn_glass.jpg'></a></td>
							</tr>
							<tr style="display: none;"  id="DetailView_<c:out value="${i.count}" />" > 
								<td colspan="6" id="DetailView_text_<c:out value="${i.count}" />">&nbsp;</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<script>
					function goPage(pageNumber){
						document.pagingfrm.pg.value = pageNumber;
						document.pagingfrm.submit();
					}
				</script>
				<form name="pagingfrm">
					<div class="paging_area">
						<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml2( 
									<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
									10, 
									<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
						</SCRIPT>
					</div>
					<input type="hidden" name="mode"     value="getConsultantEvalListDetail">
					<input type="hidden" name="pg"       value="<c:out value="${pg}"/>">
					<input type="hidden" name="item"       value="<c:out value="${item}"/>">
					<input type="hidden" name="ssn"       value="<c:out value="${ssn}"/>">
				</form>
			</div>
		</div>
	</div>
</body>
</html>