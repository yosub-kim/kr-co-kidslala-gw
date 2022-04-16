<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript" src='<c:url value="/js/scriptaculous/scriptaculous.js"/>'></script>


<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function storeProcessCategory() {
	var ActionURL = "/action/ProcessCategoryAction.do?mode=storeProcessCategory";
	var sFrm = document.forms["processCategoryDetailForm"];
	
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('저장하였습니다.');
					window.parent.Windows.close("modal_window");
					document.location = "/system/process/processManagement.jsp?pane=category"
			},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("처리할 수 없습니다.");
				}
			}
	); status = null;		
}

function deleteProcessCategory() {
	var ActionURL = "/action/ProcessCategoryAction.do?mode=deleteProcessCategory";
	var sFrm = document.forms["processCategoryDetailForm"];
	
	var status = AjaxRequest.submit(
			sFrm,
			{	'url':ActionURL,
				'onSuccess':function(obj){
					alert('삭제하였습니다.');
					window.parent.Windows.close("modal_window");
					document.location = "/system/process/processManagement.jsp?pane=category"			
				},
				'onLoading':function(obj){},
				'onError':function(obj){
					alert("처리할 수 없습니다.");
				}
			}
	); status = null;		
}

function closeProcessCategoryDetail(){
	Windows.closeAll();
}

</script>
</head>

<body style="background-color: #FFFFFF; width:610">
	<form name="processCategoryDetailForm" method="post">
		<div style="display: none;">
			<input name="processCategoryCode" type="text" value="<c:out value="${processCategory.processCategoryCode}"/>">
		</div>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0">
		<!-- sub 타이틀 영역 시작-->
						<tr>
							<td height="27" ><h4 class="subTitle">프로세스 등록</h4></td>
						</tr>
		<!-- sub 타이틀 영역 종료-->

		<!-- 본문 영역 시작-->
						<tr>
							<td>		
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr>
								 		<td>
											<table id="processCategoryTable" width='100%' cellpadding="0" cellspacing="0">
													<tr> 
														<td width="90px" class='detailTableTitle_center'>조직단위</td>
														<td width="*" class='detailTableField_center'>
															<org:divList enabled="1" depth="2" attribute=" name='runningDivCode' class='selectboxPopup' style='width:100%' " 
																divType="A" isSelectBox="Y" selectedInfo="${processCategory.runningDivCode}"></org:divList>
															<%--
															<select name="runningDivCode" class='selectbox' style="width: 100%;" >
																<c:forEach var="item" items="${runningDivCodeList}" >
																	<option value="<c:out value="${item.id}"/>" <c:if test="${item.id == processCategory.runningDivCode}">selected</c:if>><c:out value="${item.name}"/></option>
																</c:forEach>
															</select>
															 --%>
														</td>
													</tr> 
													<tr> 
														<td class='detailTableTitle_center'>BizType</td>
														<td class='detailTableField_center'>
															<select name="businessTypeCode" class='selectboxPopup' style="width: 100%;" >
																<c:forEach var="item" items="${businessTypeCodeList}" >
																	<option value="<c:out value="${item.key1}"/>" <c:if test="${item.key1 == processCategory.businessTypeCode}">selected</c:if>><c:out value="${item.data1}"/></option>
																</c:forEach>
															</select>
														</td>
													</tr> 
													<tr> 
														<td class='detailTableTitle_center'>프로젝트 분야</td>
														<td class='detailTableField_center'>
															<select name="businessFunctionType" class='selectboxPopup' style="width: 100%;" >
																<option value=""></option>
																<c:forEach var="item" items="${businessFunctionTypeList}" >
																	<option value="<c:out value="${item.key1}"/>" <c:if test="${item.key1 == processCategory.businessFunctionType}">selected</c:if>><c:out value="${item.data1}"/></option>
																</c:forEach>
															</select>
														</td>
													</tr> 
													<tr> 
														<td class='detailTableTitle_center'>Process</td>
														<td class='detailTableField_center'>
															<select name="processTemplateCode" class='selectboxPopup' style="width: 100%;" >
																<c:forEach var="item" items="${processTemplateList}" >
																	<option value="<c:out value="${item.processTemplateCode}"/>" <c:if test="${item.processTemplateCode == processCategory.processTemplateCode}">selected</c:if>><c:out value="${item.processTemplateName}"/></option>
																</c:forEach>
															</select>
														</td>
													</tr> 
													<tr> 
														<td class='detailTableTitle_center'>프로세스 분류명</td>
														<td class='detailTableField_left'><input name="processCategoryName" type="text" value="<c:out value="${processCategory.processCategoryName}"/>" class="contentInput_left" style="width: 100%;"></td>
													</tr> 
											</table>
								 		</td> 
								 	</tr>
								</table>			
							</td>
						</tr>			
		<!-- 본문 영역 종료-->
		
		 
		<!-- 버튼부분-->
						<tr>
							<td height='7'></td>
						</tr>
						<tr>
							<td>
								<table width='100%' height="36" bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<div class="btNbox txtR">
												<a title="저장" class="btNe006bc6 txt2btn" href="#" onclick="storeProcessCategory()">저장</a>
												<a title="삭제" class="btNe14f42 txt2btn" href="#" onclick="deleteProcessCategory()">삭제</a>
											</div>
									</tr>
								</table>
							</td>
						</tr>
		<!-- 버튼종료-->
					</table>
				</td>
			</tr>
		</table>						
	</form>					
</body>

</html>					