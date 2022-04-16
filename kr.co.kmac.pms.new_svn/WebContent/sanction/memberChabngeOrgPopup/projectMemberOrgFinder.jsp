<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>투입할 인력 검색</title>
<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function doSearch(){
	location.href = "/action/ExpertPoolManagerAction.do?mode=orgFinderForProjectMember"
					+ "&searchName=" + encodeURIComponent($("searchText").value)
					+ "&callbackFunc=<c:out value="${callbackFunc }" />"
					+ "&projectCode=<c:out value="${projectCode }" />";
}

function Page_Load() {
	$("searchText").focus();
}

function expertPoolByCheckBox(chkBox) {
	var expertPool = new Object();
	expertPool.ssn                 = chkBox.exprt_ssn;
	expertPool.name                = chkBox.exprt_name;
	expertPool.company             = chkBox.exprt_company;
	expertPool.dept                = chkBox.exprt_dept;
	expertPool.deptName            = chkBox.exprt_deptName;
	expertPool.companyPosition     = chkBox.exprt_companyPosition;
	expertPool.companyPositionName = chkBox.exprt_companyPositionName;
	expertPool.jobClass            = chkBox.exprt_jobClass;
	expertPool.email               = chkBox.exprt_email;
	expertPool.rate                = chkBox.exprt_rate;
	expertPool.minAmt			   = chkBox.exprt_minAmt;
	expertPool.maxAmt       	   = chkBox.exprt_maxAmt;
	expertPool.minEdu              = chkBox.exprt_minEdu;
	expertPool.maxEdu              = chkBox.exprt_maxEdu;
	return expertPool;
}                        

function find_Complete() {
	var expertPoolList = new Array();
	var selectedExpert = $$('input[name="chkSelectedExpert"]');
	if(selectedExpert.length > 0) {
		for(var i = 0; i < selectedExpert.length ; i++) {
			if(selectedExpert[i].checked){
				var expertPool = expertPoolByCheckBox(selectedExpert[i]);
				expertPoolList.push(expertPool);
			}
		}
	
		opener.<c:out value="${callbackFunc }" />(expertPoolList);
	}
	window.close();
}

</script>
</head>
 
<body style="margin: 5px;" onLoad="Page_Load();">

		<table width="765" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<table width="765" height="20" border="0" cellpadding="0" cellspacing="0">
						<tr><td height='5'></td></tr>
						<tr>
							
							<td width="100%" align="left" valign="bottom"><span class="subTitle">&nbsp;투입할 인력 검색</span></td>
						</tr>
						<tr><td height='10'></td></tr>
					</table>
				</td>
			</tr>
<!-- 본문시작-->

			<tr>
				<td>
					<table width="765" style="table-layout:fixed" border="0">
						<tr>
							<td width="411" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="14"><img src="/images/search_im01.jpg" width="14" height="44"></td>
													<td width="70" align="left" background="/images/search_im03.jpg"><img src="/images/search_im05.jpg"></td>
													<td width="*" align="right" valign="middle" background="/images/search_im03.jpg">
														<table width="100%">
															<tr>
																<td width="60px" align="right">성명&nbsp;</td>
																<td width="*"><input type="text" name="searchText" id="searchText" value="<c:out value="${searchText }" />" class="textInput_left" onkeypress="if(event.keyCode == 13) doSearch();"></td>
																<td width="65px" align="center"><a href="javascript:doSearch();"><img src="/images/btn_search.jpg"></a></td>
															</tr>
														</table>
													</td> 
													<td width="14" align="right"><img src="/images/search_im02.jpg" width="14" height="44"></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr><td height="3"></td> </tr>
									<tr valign="top">
										<td>
											<div class="outLine1">
												<div style='overflow:auto;width:98%;height:326; vertical-align:top; border-width:1px; border-color: #c7c7c7; border-style: solid; border-collapse:collapse; margin: 0px;'>
													<table width="100%" cellpadding="0" cellspacing="0" valign="top" style="border-collapse:collapse; table-layout : fixed;" class="listTable">
														<thead>
															<tr>
																<td width="28">&nbsp;</td>
																<td width="60">이름</td>
																<td width="*">소속</td>
																<td width="100">부서</td>
															</tr> 
														</thead>
														<tbody>
															<c:choose>
															<c:when test="${!empty list.list}">
															<c:forEach var="expert" items="${list.list}">
															<tr>
																<td>
																	<input type="checkbox" name="chkSelectedExpert" value=""
																		exprt_ssn                 = "<c:out value="${expert.ssn}"/>"                  
																		exprt_name                = "<c:out value="${expert.name}"/>"                 
																		exprt_company             = "<c:out value="${expert.company}"/>"              
																		exprt_dept                = "<c:out value="${expert.dept}"/>"             
																		exprt_deptName            = "<c:out value="${expert.deptName}"/>"                 
																		exprt_companyPosition     = "<c:out value="${expert.companyPosition}"/>"      
																		exprt_companyPositionName = "<c:out value="${expert.companyPositionName}"/>"  
																		exprt_jobClass            = "<c:out value="${expert.jobClass}"/>"             
																		exprt_email               = "<c:out value="${expert.email}"/>"                
																		exprt_rate                = "<c:out value="${expert.rate}"/>"                
																		exprt_minAmt         	  = "<c:out value="${expert.minAmt}"/>" 
																		exprt_maxAmt        	  = "<c:out value="${expert.maxAmt}"/>" 
																		exprt_minEdu			  = "<c:out value="${expert.minEdu}"/>" 
																		exprt_maxEdu              = "<c:out value="${expert.maxEdu}"/>" 
																		>
																</td>
																<td align="center" nowrap="nowrap"><c:out value="${expert.name}"/></td>
																<td align="left" nowrap="nowrap"><c:out value="${expert.company}"/></td>
																<td align="left"  nowrap="nowrap"><c:out value="${expert.deptName}"/></td>
															</tr>
															</c:forEach>
															</c:when>
															<c:otherwise>
															<tr>
																<td align="center" colspan="4">
																	<c:if test="${empty init}">
																	** 신청하지 않은 엑스퍼트입니다. 엑스퍼트 신청을 하십시오. ** 
																	</c:if>
																</td>
															</tr>
															</c:otherwise>
															</c:choose>
														</tbody>
													</table>
												</div>
											</div>
										</td>
									</tr>
									

						
							<!-- 버튼부분-->
									<tr>
										<td>
											<table width="100%" height="32" bgcolor="#F3F3F3" cellpadding="0" cellspacing="0">
												<tr>
													<td><p align="right">
														<a href="#" onclick="find_Complete()"><img alt="등록" src="/images/btn_regist.jpg" border="0"></a>
														<a href="#" onclick="window.close();"><img alt="닫기" src="/images/btn_close.jpg" border="0"></a>
														</p>
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
				</td>
			</tr>
<!-- 본문종료-->
		</table>

</body>

</html>					