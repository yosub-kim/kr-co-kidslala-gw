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
<div id="chargerInfoPage"  class="sub_container">

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
                    <ul><p>담당자 안내</p></ul>
                </div>
           </div>
            
            <!-- contents -->
           <div class="contents">
              <div class="tbl-wrap">
                  <table class="tbl-ty1">
                  	<colgroup>
                  		<col width="20%" />
                  		<col width="50%" />
                  		<col width="15%" />
                  		<col width="13%" />
                  	</colgroup>
                  	<thead>
                  		<tr>
                  			<th>구분</th>
                  			<th>업무지원</th>
                  			<th>담당자</th>
                  			<th>내선번호</th>
                  		</tr>
                  		<tr>
                  			<th>사내규정</th>
                  			<td style="text-align:left;">- 사내규정, 운영기준 관리</td>
                  			<td>지민우</td>
                  			<td>669</td>
                  		</tr>
                  		<tr>
                  			<th>인력운영 /<br>채용</th>
                  			<td style="text-align:left;">
                  				- 채용 및 입사/퇴사 관리, 상임 위촉/해촉 관리<br/>
                  				- 회사 및 인력현황, 인력Pool 관리
                  			</td>
                  			<td>마지용</td>
                  			<td>670</td>
                  		</tr>
                  		<tr>
                  			<th>급여 /<br>성과급</th>
                  			<td style="text-align:left;">
                  				- 급/상여, 퇴직금, 인건비 지급 관리<br/>
                  				- 성과급, 사업기여 성과급 지급 관리
                  			</td>
                  			<td>김은경</td>
                  			<td>521</td>
                  		</tr>
                  		<tr>
                  			<th>휴가관리</th>
                  			<td style="text-align:left;">- 휴가 및 연차관리</td>
                  			<td>김은경</td>
                  			<td>521</td>
                  		</tr>
                  		<tr>
                  			<th rowspan="3">비용처리 /<br>법인카드</th>
                  			<td style="text-align:left;">
                  				- 각종 비용, 정산, 출장비, 세금계산서 발행 등 회계처리<br/>(담당: 공공혁신2부문, 스마트혁신부문, 정부정책사업본주)
                  			</td>
                  			<td>한진우</td>
                  			<td>673</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">
                  				- 각종 비용, 정산, 출장비, 세금계산서 발행 등 회계처리<br/>(담당: 기업가치혁신부문, 고객가치혁신부문)<br/>
                  				- 법인카드 분실 및 한도 증액 문의
                  			</td>
                  			<td>정미용</td>
                  			<td>646</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">
                  				- 각종 비용, 정산, 출장비, 세금계산서 발행 등 회계처리<br/>(담당: 공공혁신1부문, 인재개발부문(정부정책 제외), 미디어센터)<br/>
                  			</td>
                  			<td>문혜영</td>
                  			<td>675</td>
                  		</tr>
                  		<tr>
                  			<th rowspan="2">복리후생</th>
                  			<td style="text-align:left;">
                  				- 선택적복지제도 운영, 복지카드 운영<br/>
                  				- 상근(주차카드), 상임(주차비 지원) 관리<br/>
                  				- 4대보험, 상해보험, 건강검진 운영<br/>
                  				- 상조물품 관리(화환 및 상조물품/근조기/긴급연락망 운영)<br/>
                  				- 콘도 예약관리(한화리조트)<br/>
                  				- 공헌상 운영
                  			</td>
                  			<td>김은경</td>
                  			<td>521
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">
                  				- 장학금(본인/자녀), 미취학 자녀 교육비 지원 문의<br/>
                  				- 명절선물, 생일쿠폰<br/>
                  				- 경조금 지급관리
                  			</td>
                  			<td>장미정</td>
                  			<td>137</td>
                  		</tr>
                  		<tr>
                  			<th rowspan="4">증명서발급 /<br>관리</th>
                  			<td style="text-align:left;">
                  				- 재직증명서, 경력증명/확인서<br/>
                  				- (상근)근로소득 원천징수영수증 발급(연말정산 운영)<br/>
                  				- 4대보험 관련 확인서<br/>
                  				- 법인등기부등본, 법인인감증명서 발급
                  			</td>
                  			<td>김은경</td>
                  			<td>521</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">- (상임)사업소득/기타소득 원천징수영수증 발급</td>
                  			<td>한진우</td>
                  			<td>673</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">- 국세, 지방세, 재무제표확인원 관리</td>
                  			<td>문혜영</td>
                  			<td>675</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">- 중견기업확인, 소프트웨어사업자 등 각종 확인서 관리</td>
                  			<td>윤상호</td>
                  			<td>551</td>
                  		</tr>
                  		<tr>
                  			<th>사무용품</th>
                  			<td style="text-align:left;">
                  				- 물품 신청(TCS119) 관리<br/>
                  				- 기타 소모품(KMAC 로고) 지급 관리
                  			</td>
                  			<td>윤상호</td>
                  			<td>551</td>
                  		</tr>
                  		<tr>
                  			<th>시설관리</th>
                  			<td style="text-align:left;">
                  				- 스튜디오 운영 관리 및 지원<br/>
                  				- 사무환경 관리, 회의실 운영 관리<br/>
                  				- 사물함 관리(상근, 상임)<br/>
                  				- 전화기, 전화번호, 제본기기 관리
                  			</td>
                  			<td>윤상호</td>
                  			<td>551</td>
                  		</tr>
                  		<tr>
                  			<th>명함</th>
                  			<td style="text-align:left;">- 명함 제작 및 배포 관리</td>
                  			<td>이승리</td>
                  			<td>114</td>
                  		</tr>
                  		<tr>
                  			<th>인장 관리</th>
                  			<td style="text-align:left;">- 사용인감, 명판 등 인장관리</td>
                  			<td>문혜영</td>
                  			<td>675</td>
                  		</tr>
                  		<tr>
                  			<th rowspan="4">IT /<br>시스템지원</th>
                  			<td style="text-align:left;">
                  				- PC/노트북 설치 및 유지보스<br/>
                  				- 전상 장비 렌탈/예약관리<br/>
                  				- 프린터 설치, 고장, 교체 등 관련 문의<br/>
                  				- 소프트웨어 설치 문의
                  			</td>
                  			<td>정정모</td>
                  			<td>364</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">- 사내 인트라넷(PMS)운영 관리, 에러/수정 문의</td>
                  			<td>김요섭</td>
                  			<td>644</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">- 교육관리 시스템 관련 문의</td>
                  			<td>사용환</td>
                  			<td>369</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">- 나라장터 RPA 운영 관련 문의</td>
                  			<td>오영택</td>
                  			<td>642</td>
                  		</tr>
                  		<tr>
                  			<th>홈페이지</th>
                  			<td style="text-align:left;">- 회사 홈페이지 운영 관리, 수정 문의</td>
                  			<td>박진영</td>
                  			<td>133</td>
                  		</tr>
                  		<tr>
                  			<th rowspan="3">지식 /<br>정보 /<br>법률 지원</th>
                  			<td style="text-align:left;">
                  				- 지식 Q&A 및 KDB 컨텐츠 이용 관련 문의<br/>
                  				- 내/외부 지식 및 산출물 검색 문의
                  			</td>
                  			<td>길홍미</td>
                  			<td>739</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">
                  				- 국내외 지식정보 검색 및 관련 사이트 이용관련 문의<br/>
                  				(STATISTA, KISS, KIS-Line 등)
                  			</td>
                  			<td>신효숙</td>
                  			<td>158</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">- 법무, 노무, 상표권 등 관련 볍률 지원 문의</td>
                  			<td>지민우</td>
                  			<td>669</td>
                  		</tr>
                  		<tr>
                  			<th>입찰 계정 관리</th>
                  			<td style="text-align:left;">
                  				- 나라장터 관리(입찰대리인 등록, 보안 토큰 구매)<br/>
                  				- 전자입찰계정 관리
                  			</td>
                  			<td>윤상호</td>
                  			<td>551</td>
                  		</tr>
                  		<tr>
                  			<th>정보 /<br>보안</th>
                  			<td style="text-align:left;">- 정보보안 및 개인정보보호 관련 문의</td>
                  			<td>오영택</td>
                  			<td>642</td>
                  		</tr>
                  		<tr>
                  			<th rowspan="2">신고 /<br>상담</th>
                  			<td style="text-align:left;">
                  				- 코로나19 관련 문의/신고<br>
                  				- 협력사 관리, 직원윤리 관련 문의/신고<br/>
                  				- 직장내 성희롱, 괴롭힘, 고충 문의/신고
                  			</td>
                  			<td>지민우</td>
                  			<td>669</td>
                  		</tr>
                  		<tr>
                  			<td style="text-align:left;">- 직장내 성희롱, 괴롭힘, 고충 문의/신고</td>
                  			<td>장미정</td>
                  			<td>137</td>
                  		</tr>
                  		<tr>
                  			<th>비즈니스스쿨</th>
                  			<td style="text-align:left;">- 비즈니스스쿨 강의실, 예약문의</td>
                  			<td>서은미</td>
                  			<td>625</td>
                  		</tr>
                  		<tr>
                  			<th>안내데스크</th>
                  			<td style="text-align:left;">
                  				- 외부 방문자 주차 등록 신청<br/>
                  				- 사무실 출입카드 관리
                  			</td>
                  			<td>이승리</td>
                  			<td>114</td>
                  		</tr>
                  	</thead>
                  </table>
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
