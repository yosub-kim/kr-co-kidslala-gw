<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<title>리뷰지 작성 안내</title>
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
<body>
<%-- 작업영역 --%>
<table width="735px">
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
							<jsp:param name="title" value="프로젝트 리뷰 작성 안내" />
						</jsp:include>
					</td>
				</tr>
			
								
				<!-- sub 타이틀 영역 시작--> 
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><h4 class="subTitle mt15 mb5">프로젝트 리뷰 항목 설명</h4></td>
							</tr>
						</table>
					</td>
				</tr>
				<!-- sub 타이틀 영역 종료--> 		
											
			
				<tr>
					<td> 
						<table cellSpacing="0" cellpadding="0" width="100%">
							<tr>
								<td width="120px" class="detailTableTitle_center">프로젝트 배경 이란?</td>
								<td width="*" class="detailTableField_left">-프로젝트 발주 배경 및 개요</td>
								<td height="28px"></td>
							</tr>
							<tr>
								<td width="120px" class="detailTableTitle_center">프로젝트 성과 란?</td>
								<td width="*" class="detailTableField_left">-프로젝트 목표를 달성함으로써 얻은 고객의 성과</td>
								<td height="28px"></td>
							</tr>
							<tr>
								<td width="120px" class="detailTableTitle_center">프로젝트 리뷰 란?</td>
								<td width="*" class="detailTableField_left">-프로젝트 준비, 진행, 결과 도출 과정에서 있었던 주요 사항</td>
								<td height="28px"></td>
							</tr>
							<tr>
								<td width="120px" class="detailTableTitle_center">시사점 이란?</td>
								<td width="*" class="detailTableField_left">-상품에 관련된 내용: CASE 로서의 의의, 방법론 보완 및 개발의 필요성 등<br>
								-시장에 관련된 내용: 이 CASE 가 유사 업종에 확대 가능한지 여부 등
								<td height="40px"></td>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			
			
				<!-- sub 타이틀 영역 시작--> 
				<tr>
					<td>
						<h4 class="subTitle mt15 mb5">프로젝트 리뷰 작성 예시</h4></td>
					</td>
				</tr>
				<!-- sub 타이틀 영역 종료--> 
						
				<tr>
					<td> 
						<table cellSpacing="0" cellpadding="0" width="100%">
							<tr>
								<td width="120px" class="detailTableTitle_center">프로젝트 배경</td>
								<td width="*" class="detailTableField_left">000경영이란 회사가 나아가야 할 목표와 방향을 제시하고, 그 목표에 올바르게 도달하기 위한 정상적인  경영활동을 의미하며, 추구하는 목적을 달성하기 위한 올바른 경영관점과 추진체계를 필요로 함<p>이에 따라 본 프로젝트는 000사의 000경영을 실현하기 위한 평가지수의 개발 및 측정을 통해 정도 경영, 장기적으로는 지속가능경영을 그 목적으로 함</td>
								<td height=""></td>
							</tr>
							<tr>
								<td width="120px" class="detailTableTitle_center">프로젝트 성과</td>
								<td width="*" class="detailTableField_left">기존의 평가시스템(CSI 등)은 설문(자기기입식)에 의존하였으나, "정도경영평가지수"는 설문 및 설문을 검증할 수 있는 70개의 KPI로 구성되어 있어 객관성을 확보하였음<p>
		국내기업을 대상으로 업종별 "정도경영 순위 점수" 발표 가능(클라이언트도 희망하고 있음)
								</td>
								<td height=""></td>
							</tr>
							<tr>
								<td width="120px" class="detailTableTitle_center">프로젝트 리뷰</td>
								<td width="*" class="detailTableField_left">컨설팅 제안서 작성 시점에서 실행 컨설턴트와 사전 커뮤니케이션 부족으로 컨설팅 범위가 불분명한 상태에서 클라이언트의 요구조건을 충족시키는 과정에서 완성도를 지나치게 요구 하는 상황 전개. 클라이언트의 높은 요구조건을 충족시킬 수 있도록 접근하는데, 많은 인내심과 설득력을 필요로 하였음<p>
		컨설팅 제안시 커뮤니케이션 부족 현상을 보완하기 위해 킥오프 미팅전에 투입컨설턴트가 제안서를 충분히 숙지할 수 있는 시간 필요<p>
		킥오프 시점에서는 클라이언트와 컨설턴트간에 컨설팅 범위 및 아웃풋에 대한 충분한 공감대 형성 필요<p>
		전임 CEO 평가와 관련하여 시행되었던 프로젝트가 중도에 CEO가 교체되면서 프로젝트 중단의 위기가 있었지만 PL이 잘 대응하여 마지막까지 잘 마무리 되었음<br>
		(대표이사 면담을 통해 컨설팅 결과 활용도에 대한 장점을 체계 있게 설명)
								</td>
								<td height=""></td>
							</tr>
							<tr>
								<td width="120px" class="detailTableTitle_center">시사점</td>
								<td width="*" class="detailTableField_left">내용과 방법론 모두 처음 시도하는 프로젝트였지만 좋은 케이스가 마련됨<p>CEO가 경영성과를 파악하기 위한 관련 지표 개발에 의미가 있음<br>특히 민간기업의 경영성과 지표개발에 대한 Needs에 활용할 예정</td>
								<td height=""></td>
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
						<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="2" cellspacing="0">
							<tr>
								<td>
									<div class="btNbox txtR">
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