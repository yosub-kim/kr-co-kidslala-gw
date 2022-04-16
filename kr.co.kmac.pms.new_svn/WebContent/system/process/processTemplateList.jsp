<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
</script>
</head>

<body>
	<form name="searchTemplateForm" method="post">
	<table width="100%" cellpadding="0" cellspacing="0">
	<!-- sub 타이틀 영역 시작-->					
		<tr>
			<h4 class="subTitle mt5 mb5">프로세스 템플릿</h4>
		</tr>
	<!-- sub 타이틀 영역 종료-->

	<!-- 검색 영역 -->
		<tr>
			<td>
				<div class="tableWrap01">
					<table border="0" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; "> 
						<tr>
							<td width="*">
								<table border="0" width="100%" style="padding-right: 3px;">
									<colgroup> 
										<col width="120px" />
										<col width="*" />
										<col width="120px" />
									</colgroup>
									<tr>
										<td class="searchTitle_center">프로세스 템플릿 명</td>
										<td class="searchField_center">
											<input type="text" name="processTemplateName" id="processTemplateName" size="68" class="textInput_left">							
										</td>
									</tr>
								</table>
							</td>
							<td width="70px" class="searchField_center" style="border-left:0px"><a href="javascript:searchTemplateList();"><img src="/images/sub/btnSrch.gif" height="27" width="27"></a></td>
						</tr>
					</table>
		  		</div> 									
			</td>
		</tr>			
		<!-- 본문시작-->					
		<tr>
			<td height="36">
				<div class="btNbox pt15 pb15">
					<div class="btNleftdiv">
						<img src="/images/sub/line3Blit.gif" alt="">
						<span id="processTemplateTotCnt" class="bold colore74c3a"><c:out value="${listSize}"/></span>
						<span>Total</span>
					</div>
					<div class="btNrighTdiv">
						<a title="새 템플릿 등록" class="btNe14f42 txt2btn" href="#" onclick="javascirpt:openProcessTempalte()">등록</a>
					</div>
				</div>
			</td>
		</tr>
		<tr><td height="5"></td></tr>
		<tr>
			<td>
				<table id="templateTable" class="tableStyle05">
					<colgroup>
						<col width="120px" />
						<col width="*" />
						<col width="100px" />
					</colgroup>
					<thead id="templateTableHeader">
						<tr> 
							<td>코드</td> 
							<td>프로세스 템플릿 이름</td>
							<td>수정</td>
						</tr>
					</thead>									
					<tbody id="templateTableBody">
						<c:if test="${!empty list}">
							<c:forEach var="rs" items="${list}">
								<tr onmouseover="row_over(this)" onmouseout="row_out(this)" style="height:30">
									<td><c:out value="${rs.processTemplateCode}" /></td>
									<td style="text-align: left;"><c:out value="${rs.processTemplateName}" /></td>
									<td><a title="수정" class="btn006bc6 pb5 pt5 txt2btn" href="#" onclick="javascript:goProcessTemplateDetail('<c:out value="${rs.processTemplateCode}" />');" >수정</a></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty list}">
							<tr>
								<td align="center" colspan="3">검색된 데이터가 없습니다. </td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<td height="5"></td>
		</tr>
<!-- 버튼부분-->
		<tr>
			<td>
				<table width="100%" height="36" bgcolor="#f3f3f3" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div class="btNbox txtR">
								<a title="새 템플릿 등록" class="btNe14f42 txt2btn" href="#" onclick="javascirpt:openProcessTempalte()">등록</a>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
<!-- 버튼종료-->					
<!-- 본문종료-->
	</table>						
	</form>
</body>

</html>					