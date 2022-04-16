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

<body style="width:730px">
<div style="overflow:auto; width:735px; height:900px;"">
	<form name="searchCategoryForm" method="post">
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
	
	<!-- sub 타이틀 영역 시작-->
					<tr><td height='5'></td></tr>
					<tr>
						<td><h4 class="subTitle mb5">조직단위별 상품 및 프로세스 목록</h4></td>
						
					</tr>
	<!-- sub 타이틀 영역 종료-->
	
	<!-- 검색 영역 -->
					<tr>
						<td height="21" align="left" valign="top">
							<%@ include file="/common/include/searchBox_Header.jsp"%>
							<table border="0" width="100%" style="border-collapse: collapse; ">
								<colgroup> 
									<col align="right"	width="110px">
									<col align="left"   width="180px">
									<col align="right"	width="110px">
									<col align="left"   width="230px">
									<col align="left"   width="110px"> 
								</colgroup>
								<tr>
									<td class="searchTitle_center">조직단위</td>
									<td class="searchField_left">
										<org:divList enabled="1" depth="2" attribute=" name='runningDivCode' class='selectbox' style='width:100%' " 
											divType="A" all="Y" isSelectBox="Y" selectedInfo="${runningDivCode}"></org:divList>						
									</td>
									<td class="searchTitle_center">상품명</td>
									<td class="searchField_left">
										<input type="text" name="processCategoryName" value="<c:out value="${processCategoryName}" />" class="textInput_left" style="width: 100%;" >				
									</td>
								</tr>
								<tr>
									<td class="searchTitle_center">비즈니스 유형</td>
									<td class="searchField_left">
										<select name="businessTypeCode" class="selectbox" style="width: 100%;" >
											<option value="">전체</option>
											<c:forEach var="item" items="${businessTypeCodeList}" >
												<option value="<c:out value="${item.key1}"/>" <c:if test="${item.key1 == businessTypeCode}">selected</c:if>><c:out value="${item.data1}" /></option>
											</c:forEach>
										</select>						
									</td>
									<td class="searchTitle_center">프로젝트 분야</td>
									<td class="searchField_left">
										<select name="businessFunctionType" class="selectbox" style="width: 100%;" >
											<option value="">전체</option>
											<c:forEach var="item" items="${businessFunctionTypeList}" >
												<option value="<c:out value="${item.key1}"/>" <c:if test="${item.key1 == businessFunctionType}">selected</c:if>><c:out value="${item.data1}" /></option>
											</c:forEach>
										</select>						
									</td>
								</tr>
								<tr>
									<td class="searchTitle_center">사용여부</td>
									<td class="searchField_left">
										<select name="useYN" class="selectbox" style="width: 100%;" >
											<option value="">전체</option>
											<option value="Y">사용</option>
											<option value="N">미사용</option>
										</select>						
									</td>
									<td class="searchTitle_center">프로세스 유형</td>
									<td class="searchField_left" colspan="3">
										<select name="processTemplateCode" class="selectbox" style="width: 100%" >
											<option value="">전체</option>
											<c:forEach var="item" items="${processTemplateList}" >
												<option value="<c:out value="${item.processTemplateCode}"/>" <c:if test="${item.processTemplateCode == processTemplateCode}">selected</c:if>><c:out value="${item.processTemplateName}"/></option>
											</c:forEach>
										</select>
									</td>
								</tr>
							</table>
							<%@ include file="/common/include/searchBox_Footer.jsp"%>
						</td>
					</tr>			
			<!-- SPACER -->						
	
			<!-- 본문시작-->
					<tr><td height="7px"></td></tr>
					<tr>
						<td height="35px">
							<div class="btNbox pb15 pt15">
								<div class="btNlefTdiv">				
									<img src="/images/sub/line3Blit.gif" alt="">
									<span id="processCategoryTotCnt" class="bold colore74c3a"><c:out value="${listSize}"/></span>
									<span>Total</span>
								</div>
								<div class="btNrighTdiv">
									<a title="새 상품 등록" class="btNe14f42 txt4btn" href="#" onclick="goProcessCategoryDetail('')">등록</a>
								</div>
							</div>	
						</td>
					</tr>
					<tr><td height="5px"></td></tr>
					<tr>
						<td>
							<table id="categoryTable" class="tableStyle05">
								<colgroup> 
									<col width="100px" />
									<col width="90px" />
									<col width="90px" />
									<col width="150px" />
									<col width="*" />
									<col width="60px" />
								</colgroup>
								<thead id="categoryTableHeader">
									<tr> 
										<td>조직단위</td>
										<td>비즈니스유형</td>
										<td>프로젝트분야</td>
										<td>상품명</td>
										<td>프로세스 유형</td>										
										<td>수정</td>
									</tr>
								</thead>									
								<tbody id="categoryTableBody">
									<c:if test="${!empty list}">
										<c:forEach var="rs" items="${list}">
											<tr onmouseover="row_over(this)" onmouseout="row_out(this)" style="height:30">
												<td><c:out value="${rs.runningDivName}" /></td>
												<td><c:out value="${rs.businessTypeName}" /></td>
												<td><c:out value="${rs.businessFunctionName}" /></td>
												<td class="myoverflow"><c:out value="${rs.processCategoryName}" /></td>
												<td class="myoverflow"><c:out value="${rs.processTemplateName}" /></td>												
												<td>
													<div class="btNbox txtC">
														<a title="수정" class="btN006bc6" style="padding: 5px 10px" href="#" onclick="goProcessCategoryDetail('<c:out value="${rs.processCategoryCode}" />')"  >수정</a>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${empty list}">
										<tr>
											<td align='center' colspan="6">검색 결과가 없습니다. </td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</td>
					</tr>					
					<tr>
						<td height="7"></td>
					</tr>
					<tr>
						<td>
							<table width="100%" height="36" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<div class="btNbox txtR">
											<a title="새 상품 등록" class="btNe14f42 txt4btn" href="#" onclick="goProcessCategoryDetail('')">등록</a>
										</div>
									</td>
								</tr>
							</table>						
						</td>
					</tr>
	<!-- 본문종료-->
					
				</table>
			</td>
		</tr>
	</table>						

	</form>
	</div>
</body>

</html>