<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<title>기준금액 및 기여금액 입력 안내</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통 js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function closePopup() {
	//window.parent.Windows.close("modal_window");
	this.close();
}
</script>
</head>
<body style="padding-left:10px; padding-right:10px; width:100%">
<%-- 작업영역 --%>
	<h4 class="subTitle">기준금액 입력 안내</h4>
	
	<table class="listTable" style="width: 650px">
		<thead>
			<tr>
				<td>직업구분</td>
				<td>컨설팅</td>
				<td>교육</td>
				<td>리서치</td>
				<td>진단평가</td>
				<td>해외연수</td>
				<td>위원회</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="bold" style="border-left-width:0">상근</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>0</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td class="last">0</td>
			</tr>
			<tr style="background:#e7e7e7">
				<td class="bold" style="border-left-width:0">전문가그룹</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>시간당 단가</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td class="last">시간당 단가</td>
			</tr>
			<tr>
				<td class="bold" style="border-left-width:0">엑스퍼트</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>시간당 단가</td>
				<td>-</td>
				<td>-</td>
				<td>-</td>
				<td class="last">-</td>
			</tr>
			<tr style="background:#e7e7e7">
				<td class="bold" style="border-left-width:0">RA</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>-</td>
				<td>-</td>
				<td>-</td>
				<td>-</td>
				<td class="last">-</td>
			</tr>
			<tr>
				<td class="bold" style="border-left-width:0">산업계강사 등</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>시간당 단가</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td>M/D 단가<br>M/M 단가</td>
				<td class="last">시간당 단가</td>
			</tr>
		</tbody>
	</table>
	<ul>
		<li>교육/위원회의 경우 상근은 기준금액을 0으로 입력함<br>(지도일정에 배정하는 강의시간을 참고하여 원가반영금액 자동 계산)</li>
		<li>연수 간사인 경우 성과요율 입력란에 0.5를 입력해야 함</li>
	</ul>

	<h4 class="subTitle">기여금액(실수주단가 또는 기대매출단가) 입력 안내</h4>
	
	<table class="listTable" style="width: 650px">
		<thead>
			<tr>
				<td rowspan="2">직업구분</td>
				<td colspan="2">실수주단가(수주사업)</td>
				<td colspan="5">기대매출단가(기획사업)</td>
				
			</tr>
			<tr>
				<td>컨설팅</td>
				<td>사내교육</td>
				<td>교육/연수</td>
				<td>리서치</td>
				<td>진단평가</td>
				<td>해외연수</td>
				<td>위원회</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="bold" style="border-left-width:0px">상근</td>
				<td>수주단가</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td class="last">0</td>
			</tr>
			<tr style="background:#e7e7e7">
				<td class="bold" style="border-left-width:0px">전문가그룹</td>
				<td>수주단가</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td class="last">0</td>
			</tr>
			<tr>
				<td class="bold" style="border-left-width:0px">엑스퍼트</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>-</td>
				<td>-</td>
				<td>-</td>
				<td class="last">-</td>
			</tr>
			<tr style="background:#e7e7e7">
				<td class="bold" style="border-left-width:0px">RA</td>
				<td>0</td>
				<td>-</td>
				<td>-</td>
				<td>-</td>
				<td>-</td>
				<td>-</td>
				<td class="last">-</td>
			</tr>
			<tr>
				<td class="bold" style="border-left-width:0px">산업계강사 등</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td class="last">0</td>
			</tr>		
		</tbody>
	</table>

	<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0" class="mt15"  style="width: 650px">
		<tr>
			<td>
				<div class="btNbox mb10 mt10 txtR">
					<a class="btNa0a0a0 txt2btn" href="javascript:closePopup();">닫기</a>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>