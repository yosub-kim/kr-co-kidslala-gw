<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="net.mlw.vlh.ValueList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>프로젝트 고객만족도</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

</script>
</head>
<body style="padding-left:5px">
<c:forEach var="DetailResult" items="${result.list}">
<%-- 작업영역 --%>
	<table class="s700" cellpadding="0" cellspacing="0">
		<tr>
			<td height='7'></td>
		</tr>
		<tr>
			<td class="Title">
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="프로젝트 고객만족도 보기" />
					<jsp:param name="backButtonYN" value="N" />
				</jsp:include>
			</td>
		</tr>
		<tr>	
			<td>
				<h4 class="subTitle">프로젝트 명</h4>
			</td>
		</tr>
		<tr>
			<td align="left">
				<table cellpadding="0" cellspacing="0" class="listTable" width="700" id='listTable'>
					<tr>
						<td width="15%" class='detailTableTitle_center'><b><font class='SF'>프로젝트 명</font></b></td>
						<td style="border-top: 1px solid #dedede"><b><c:out value="${DetailResult.projectName}"/></b></td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td>
				<h4 class="subTitle mt15"><c:out value="${DetailResult.businessTypeName}"/> 만족도</b>
			</td>
		</tr>
		<tr>
			<td align="left">
				
				<c:choose>
				    <c:when test="${DetailResult.businessTypeName=='컨설팅' or DetailResult.businessTypeName=='리서치' or DetailResult.businessTypeName=='진단' or DetailResult.processTypeCode=='N4' or DetailResult.processTypeCode=='SS' or DetailResult.processTypeCode=='DE'}">
				      	<table width="100%" cellpadding="0" cellspacing="0" class="listTable" id='listTable'>
				 			<tr class='detailTableTitle_center'>
					 			<td width="25%"><b>고객사</b></td>
					 			<td width="15%"><b>담당자</b></td>
					 			<td width="50%"><b>항목</b></td>
					 			<td width="10%"><b>배점</b></td>
				 			</tr>
				 			<tr>
					 			<td rowspan="4" align="center"><c:out value="${DetailResult.customerName}"/></td>
					 			<td rowspan="4" align="center">
					 			<c:choose>
				  					<c:when test="${DetailResult.businessTypeName=='컨설팅'}">
					 					<c:out value="${DetailResult.jikwee}"/><p>
					 				</c:when>
					 			</c:choose>					 				
					 				<c:out value="${DetailResult.customerWorkPName}"/></td>	
					 			<td>전반적 만족도 (배점 25점)</td>
					 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs3}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>추천의향 (배점 25점)</td>
					 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs4}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>
					 			<c:choose>
					 				<c:when test="${DetailResult.processTypeCode=='N4' or DetailResult.processTypeCode=='SS'}">현업활용도</c:when>
					 				<c:when test="${DetailResult.processTypeCode=='DE'}">숙박, 교통 및 음식 만족도</c:when>
					 				<c:otherwise>산출물 만족도</c:otherwise>
					 			</c:choose> (배점 25점)
					 			</td>
					 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs5}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>
					 			<c:choose>
					 				<c:when test="${DetailResult.processTypeCode=='DE'}">현업 적용 도움 여부</c:when>
					 				<c:otherwise>계약내용준수</c:otherwise>
					 			</c:choose> (배점 25점)
					 			</td>
					 			<td align="right" style="padding-right:5pt"><c:out value="${DetailResult.cs6}"/> 점</td>
				 			</tr>	 			
				 			<tr>
				 				<td class='detailTableTitle_center'><b>평가</b></td>
					 			<td colspan="3" align="right" style="padding-right:5pt"><b><c:out value="${DetailResult.cs3+DetailResult.cs4+DetailResult.cs5+DetailResult.cs6}"/> 점</b></td>
				 			</tr>
				 		</table>
				    </c:when>
				    <c:when test="${DetailResult.processTypeCode=='N1' or DetailResult.processTypeCode=='N2'}">
				    	<table width="100%" cellpadding="0" cellspacing="0" class="listTable" id='listTable'>
				 			<tr class='detailTableTitle_center'>
					 			<td width="35%">고객사</td>
					 			<td width="50%">항목</td>
					 			<td width="15%">배점</td>
				 			</tr>
				 			<tr>
					 			<td rowspan="6" align="center"><c:out value="${DetailResult.customerName}"/></td>
					 			<td>내용의 체계성 (배점 20점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs1}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>현업활용도 (배점 20점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs2}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>교육시설(배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs3}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>교육진행(배점 20점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs4}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>부대시설(배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs5}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>전반적 만족도(배점 20점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs6}"/> 점</td>
				 			</tr>				 			
				 			<tr>
				 				<td class='detailTableTitle_center'>평가</td>
					 			<td colspan="2" align="right" style="padding-right:20pt"><b><c:out value="${DetailResult.cs1+DetailResult.cs2+DetailResult.cs3+DetailResult.cs4+DetailResult.cs5+DetailResult.cs6}"/> 점</b></td>
				 			</tr>
				 		</table>
				    </c:when>
				    <c:otherwise>
				 		<table width="100%" cellpadding="0" cellspacing="0" class="listTable" id='listTable'>
				 			<tr class='detailTableTitle_center'>
					 			<td width="35%">고객사</td>
					 			<td width="50%">항목</td>
					 			<td width="15%">배점</td>
				 			</tr>
				 			<tr>
					 			<td rowspan="10"><c:out value="${DetailResult.customerName}"/></td>
					 			<td>전반적만족도 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs1}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>업체선정 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs2}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>현업적용 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs3}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>일정준수 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs4}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>숙박 및 음식 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs5}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>사전정보제공 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs6}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>진행 및 전문성 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs7}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>통역의 언어구사력 및 전문지식 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs8}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>가이드의 성실성, 친절성, 적극성 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs9}"/> 점</td>
				 			</tr>
				 			<tr>
					 			<td>추천의향 (배점 10점)</td>
					 			<td align="right" style="padding-right:20pt"><c:out value="${DetailResult.cs10}"/> 점</td>
				 			</tr>
				 			<tr>
				 				<td class='detailTableTitle_center'>평가</td>
					 			<td colspan="2" align="right" style="padding-right:20pt"><b><c:out value="${DetailResult.cs1+DetailResult.cs2+DetailResult.cs3+DetailResult.cs4+DetailResult.cs5+DetailResult.cs6+DetailResult.cs7+DetailResult.cs8+DetailResult.cs9+DetailResult.cs10}"/> 점</b></td>
				 			</tr>
				 		</table>
				    </c:otherwise>
				</c:choose>

			</td>
		</tr>
		
	<c:choose>
	<c:when test="${DetailResult.businessTypeName=='컨설팅' or DetailResult.businessTypeName=='리서치' or DetailResult.businessTypeName=='진단'}">
		<tr>
			<td>
				<h4 class="subTitle mt15">컨설턴트 만족도</h4>
			<td>
		</tr> 		
		<tr>
			<td align="left">
				<table cellpadding="0" cellspacing="0" class="listTable" width="700" id='listTable'>
					<tr height="19" class='detailTableTitle_center'>
						<td width="*"><b>성명</b></td>
						<td width="15%"><b>고객요구대응</b></td>
						<td width="15%"><b>전문성</b></td>
						<td width="15%"><b>의사소통</b></td>
						<td width="15%"><b>일정준수도</b></td>
						<td width="15%"><b>종합만족도</b></td>
						<td width="10%"><b>평가</b></td>
					</tr>
					<c:forEach var="DetailResult2" items="${result2.list}">
					<tr height="19" align="center">
						<td><c:out value="${DetailResult2.cname}"/></td>
						<td><c:out value="${DetailResult2.rc8}"/> / 20</td>
						<td><c:out value="${DetailResult2.rc9}"/> / 20</td>
						<td><c:out value="${DetailResult2.rc10}"/> / 20</td>
						<td><c:out value="${DetailResult2.rc12}"/> / 20</td>
						<td><c:out value="${DetailResult2.rc13}"/> / 20</td>
						<td><b><c:out value="${DetailResult2.rc8+DetailResult2.rc9+DetailResult2.rc10+DetailResult2.rc12+DetailResult2.rc13}"/></b></td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		
		<tr>
			<td>
				<h4 class="subTitle mt15"><c:out value="${DetailResult.businessTypeName}"/> 프로젝트 의견/불만사항</h4>
			</td>
		</tr>
		<tr>
			<td align="left">
				<textarea rows='10' style='padding:3px;width:700'><c:out value="${DetailResult.opinion}"/></textarea>
			</td>
		</tr>
	</c:when>
	<c:when test="${DetailResult.processTypeCode=='N4' or DetailResult.processTypeCode=='SS'}">
		<tr>
			<td>
				<h4 class="subTitle mt15">강사 만족도</h4>
			</td>
		</tr> 		
		<tr>
			<td align="left">
				<table cellpadding="0" cellspacing="0" class="listTable" width="700" id='listTable'>
					<tr height="19" class='detailTableTitle_center'>
						<td width="*"><b>성명</b></td>
						<td width="15%"><b>강의수준</b></td>
						<td width="15%"><b>강의준비</b></td>
						<td width="15%"><b>강의스킬</b></td>
						<td width="15%"><b>성의 및 열의</b></td>
						<td width="15%"><b>종합만족도</b></td>
						<td width="10%"><b>평가</b></td>
					</tr>
					<c:forEach var="DetailResult2" items="${result2.list}">
					<tr height="19" align="center">
						<td><c:out value="${DetailResult2.cname}"/></td>
						<td><c:out value="${DetailResult2.rc8}"/> / 20</td>
						<td><c:out value="${DetailResult2.rc9}"/> / 20</td>
						<td><c:out value="${DetailResult2.rc10}"/> / 20</td>
						<td><c:out value="${DetailResult2.rc12}"/> / 20</td>
						<td><c:out value="${DetailResult2.rc13}"/> / 20</td>
						<td><b><c:out value="${DetailResult2.rc8+DetailResult2.rc9+DetailResult2.rc10+DetailResult2.rc12+DetailResult2.rc13}"/></b></td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="subTitle mt15"><c:out value="${DetailResult.businessTypeName}"/> 의견/불만사항</h4>
			</td>
		</tr>
		<tr>
			<td align="left">
				<textarea rows='10' style='padding:3px;width:700'><c:out value="${DetailResult.opinion}"/></textarea>
			</td>
		</tr>
	</c:when>
	<c:when test="${DetailResult.processTypeCode=='N1' or DetailResult.processTypeCode=='N2'}">
		<tr>
			<td>
				<h4 class="subTitle mt15">강사 만족도</h4>
			</td>
		</tr> 		
		<tr>
			<td align="left">
				<table cellpadding="0" cellspacing="0" class="listTable" width="700" id='listTable'>
					<tr height="19" class='detailTableTitle_center'>
						<td width="*"><b>성명</b></td>
						<td width="16%"><b>강의교재</b></td>
						<td width="16%"><b>강의준비</b></td>
						<td width="16%"><b>강의스킬</b></td>
						<td width="16%"><b>성의 및 열의</b></td>
						<td width="16%"><b>평가</b></td>
					</tr>
					<c:if test="${!empty result2.list}">
						<c:forEach var="DetailResult2" items="${result2.list}">
						<tr height="19" align="center">
							<td><c:out value="${DetailResult2.cname}"/></td>
							<td><c:out value="${DetailResult2.rc8}"/> / 25</td>
							<td><c:out value="${DetailResult2.rc9}"/> / 25</td>
							<td><c:out value="${DetailResult2.rc10}"/> / 25</td>
							<td><c:out value="${DetailResult2.rc12}"/> / 25</td>
							<td><c:out value="${DetailResult2.rc8+DetailResult2.rc9+DetailResult2.rc10+DetailResult2.rc12}"/></td>
						</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty result2.list}">
						<td align="center"  colspan="6">평가 대상 강사가 없습니다.</td>
					</c:if>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="subTitle mt15"><c:out value="${DetailResult.businessTypeName}"/> 프로젝트 의견/불만사항</h4>
			</td>
		</tr>
		<tr>
			<td align="left">
				<textarea rows='10' style='padding:3px;width:700'><c:out value="${DetailResult.opinion}"/></textarea>
			</td>
		</tr>
	</c:when>
	</c:choose>
		<tr><td height='7'></td></tr>	
	</table>
</div>
</body>
</c:forEach>
</html>