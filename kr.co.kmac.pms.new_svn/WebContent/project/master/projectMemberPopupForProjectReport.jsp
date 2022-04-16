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
function ProjectMember(){
	ssn	= "";
	name	= "";
	time	= "";
}

function selectProjectMember() {
	var memberList = $$('input[name="member"]');
	var teachTimeList = $$('input[name="teachTime"]');

	var projectMemberList = new Array();
	for (var i = 0; i < memberList.length; ++i) {
		if(memberList[i].checked){
			var projectMember = new ProjectMember();
			projectMember.ssn = memberList[i].memberSsn;;
			projectMember.name = memberList[i].memberName+(teachTimeList[i].value!='' ? "("+teachTimeList[i].value+")" : "");
			projectMember.time = teachTimeList[i].value;
			projectMemberList.push(projectMember);
		}
	}
	window.parent.setProjectReportWriter(projectMemberList); 
	window.parent.Windows.close("modal_window");
}
</script> 
</head>

<body>
		<div style="display: none;">
			<input type="text" name="projectCode" />
		</div>

		<table class='s258' cellpadding="0" cellspacing="0">
			<tr>
				<td width="258" valign="top">
					<table class='s258' cellpadding="0" cellspacing="0">
						<tr>
							<td height='7'></td>
						</tr>
		<%-- 타이틀 영역 시작
						<tr>
							<td width="658" height="27" background="/images/Tback.gif">&nbsp;<img src="/images/icon_2.gif" align="absmiddle"><b><font class='TF1'>&nbsp;프로세스 템플릿 관리 </font></b></td>
						</tr>
						<tr>
							<td height='7'></td>
						</tr>
		타이틀 영역 종료--%>
				
		<!-- 검색부분 시작
						<tr>
							<td>
								<table cellpadding="0" cellspacing="0" width="658">
									<tr>
										<td width='9'><img src="/images/search_b1.gif"></td>
										<td width="640" background="/images/search_bg1.gif"></td>
										<td width="9"><img src="/images/search_b2.gif"></td>
									</tr>
									<tr>
										<td width="9" background="/images/search_bg2.gif"></td>
										<td width="640" bgcolor='#F7F6F0'><p align='center'>
											<table width="640" cellpadding="0" cellspacing="0">
												<tr>
													<td><p align='right'><font class='SF'>프로젝트 상태 : </font><select name='projectState' class='selectbox' style="width: 140px;" >
															<option value="">-- 선택 --</option>
															<option value="ing">등록중</option>
															<option value="sanction">품의중</option>
															<option value="finished">품의완료</option>
														</select>
														<font class='SF'>프로젝트명</font>  <input type="text" name="projectName" size="50" class="textInput_left">&nbsp;&nbsp;<img src='/images/search_bt.gif' align="absmiddle" onclick="searchResisterProject()"></p></td>
												</tr>
											</table>
										</td>
										<td width="9" background="/images/search_bg3.gif"></td>
									</tr>
									<tr>
										<td width="9"><img src="/images/search_b3.gif"></td>
										<td width="640" background="/images/search_bg4.gif"></td>
										<td width="9"><img src="/images/search_b4.gif"></td>
									</tr>
								</table>
							</td>
						</tr>
		 검색부분 종료-->		
		<!-- 본문시작-->
						<tr><td height='10'></td></tr>
						<tr>
							<td width="258">
								<table class='s258' cellpadding="0" cellspacing="0">
								<tr>
									<td><b><font class='SF'><c:out value="${list.valueListInfo.totalNumberOfEntries}"/> Total- Page(<c:out value="${list.valueListInfo.pagingPage}"/> of <c:out value="${list.valueListInfo.totalNumberOfPages}"/>)</font></b></td>
								</tr>
								</table>
							</td>
						</tr>
						<tr><td height='5'></td></tr> 
						<tr>
							<td>
								<table class='s258' cellpadding="0" cellspacing="0">
									<col>								
									<col>								
									<col>								
									<col <c:if test="${businessTypeCode=='BTA' || businessTypeCode=='BTD' }">style="display: none;"</c:if>>								
									<thead>
										<tr height="25px">
											<td class="BG3" width="30px">선택</td>
											<td class="BG3" width="88px">이름</td> 
											<td class="BG3" width="75px">직책</td>
											<td class="BG3" width="65px">강의시간</td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:forEach var="rs" items="${list.list}">
											<tr>
												<td class="BGC"><input type="checkbox" name="member" memberSsn="<c:out value="${rs.ssn}" />" memberName="<c:out value="${rs.name}" />" ></td>
												<td class="BGC"><c:out value="${rs.name}" /></td>
												<td class="BGC"><c:out value="${rs.role}" /></td>
												<td class="BGC"><input type="text" name="teachTime"  class="textInput_left" style="width: 100%"></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td> 
						</tr>
						<%-- 페이징 영역 시작
						<tr>
							<td width="658">
								<table class='s658' cellpadding="0" cellspacing="0">
									<tr height='30'>
										<td width="100%" align="center">
											<SCRIPT LANGUAGE="JavaScript">
												document.write( makePageHtml( 
														<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
														10, 
														<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
														10)
												) ;
											</SCRIPT>
										</td>
									</tr>
								</table>									
							</td>
						</tr>
						 페이징 영역 끝--%>						
		<!-- 본문종료-->
		<!-- 버튼부분-->
						<tr>
							<td height='7'></td>
						</tr>
						<tr>
							<td>
								<table width='258' height='32' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
									<tr>
										<td><p align='right'><img alt="선택" src='/images/dbt_b16on.gif' align="absmiddle" onclick="selectProjectMember()" style="cursor: hand;">&nbsp;</p></td>
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