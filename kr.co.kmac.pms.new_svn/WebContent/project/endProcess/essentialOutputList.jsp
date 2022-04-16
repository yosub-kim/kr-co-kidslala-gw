<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<title>필수 산출물 목록</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function closePopup() {
	//window.parent.Windows.close("modal_window");
	this.close();
}
</script>
</head>
<body style="padding-left: 2px; padding-right: 2px; width: 405px">
<%-- 작업영역 --%>
<table>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
		
				<!-- sub 타이틀 영역 시작--> 
				<tr>
					<td>
						<h4 class="subTitle">비즈니스 유형별 필수 산출물 목록</h4>
					</td>
				</tr>
				<!-- sub 타이틀 영역 종료--> 		
											
						
				<tr>
					<td> 
						<table cellSpacing="0" cellpadding="0" width="100%" border="0">
							<tr>
								<td width="100px" class="detailTableTitle_center">컨설팅</td>
								<td width="*" class="detailTableField_left">제안서, 계약서, 수행계획서, 착수보고서, 최종보고서</td>
							</tr>
							<tr>
								<td width="100px" class="detailTableTitle_center">진흥</td>
								<td width="*" class="detailTableField_left">기획서, 피드백보고서</td>
							</tr>
							<tr>
								<td width="100px" class="detailTableTitle_center">인증</td>
								<td width="*" class="detailTableField_left">기획서, 피드백보고서</td>
							</tr>
							<tr>
								<td width="100px" class="detailTableTitle_center">리서치</td>
								<td width="*" class="detailTableField_left">제안서, 계약서, 설문지, Topline, 최종보고서</td>
							</tr>
							<tr>
								<td width="100px" class="detailTableTitle_center">교육</td>
								<td width="*" class="detailTableField_left">기획서(공개교육), 제안서(사내교육), 교재</td>
							</tr>
							<tr>
								<td width="100px" class="detailTableTitle_center">국제사업</td>
								<td width="*" class="detailTableField_left">기획서(기획연수), 제안서(수주연수), 결과보고서(수주연수)</td>
							</tr>
							<tr>
								<td width="100px" class="detailTableTitle_center">미디어</td>
								<td width="*" class="detailTableField_left">기획서</td>
							</tr>
							<tr>
								<td width="100px" class="detailTableTitle_center">위원회</td>
								<td width="*" class="detailTableField_left">기획서, 활동보고서</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="5"></td>
				</tr>
				<!-- 버튼부분-->
				<tr>
					<td>
						<table width="100%" height="36" bgcolor="#F3F3F3">
							<tr>
								<td>
									<div class="btNbox pt10 pb10 txtR">
										<a title="닫기" class="btNa0a0a0 txt2btn" href="#" onclick="closePopup()">닫기</a>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<!-- 버튼종료-->
			</table>
		</td>
	</tr>
</table>
</body>
</html>