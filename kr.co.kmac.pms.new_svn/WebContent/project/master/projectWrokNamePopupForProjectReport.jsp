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
function ProjectWorkName(){
	workSeq = "";
	workName = "";
	outputName = "";
}

function selectProjectWorkName(selectedElmt) {
	var i = $(selectedElmt).up().up().rowIndex -1;		
	var projectWorkName = new ProjectWorkName();
	projectWorkName.workSeq = ($$('span[id="_workSeq"]')[i]).innerText;;
	projectWorkName.workName = ($$('span[id="_workName"]')[i]).innerText;
	projectWorkName.outputName = ($$('span[id="_outputName"]')[i]).innerText;
	
	window.parent.setProjectWorkName(projectWorkName); 
	window.parent.Windows.close("modal_window");
}
</script> 
</head>

<body>
		<div style="display: none;">
			<input type="text" name="projectCode" />
		</div>

		<table class='s658' cellpadding="0" cellspacing="0">
			<tr>
				<td width="658" valign="top">
					<table class='s658' cellpadding="0" cellspacing="0">
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
							<td width="658">
								<table class='s658' cellpadding="0" cellspacing="0">
								<tr>
									<td><b><font class='SF'><c:out value="${list.valueListInfo.totalNumberOfEntries}"/> Total- Page(<c:out value="${list.valueListInfo.pagingPage}"/> of <c:out value="${list.valueListInfo.totalNumberOfPages}"/>)</font></b></td>
								</tr>
								</table>
							</td>
						</tr>
						<tr><td height='5'></td></tr> 
						<tr>
							<td>
								<table class='s658' cellpadding="0" cellspacing="0">
									<thead>
										<tr height="25px">
											<td class="BG3" width="30px">순번</td>
											<td class="BG3" width="50%">업무명</td>
											<td class="BG3" width="50%">산출물</td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:forEach var="rs" items="${list.list}">
											<tr>
												<td class="BGC"><span id="_workSeq"><c:out value="${rs.workSeq}" /></span></td>
												<td class="BGC"><a href="#" onclick="selectProjectWorkName(this)"><span id="_workName"><c:out value="${rs.workName}" /></span></a></td>
												<td class="BGC"><span id="_outputName"><c:out value="${rs.outputName}" /></span></td>
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
		<%-- 버튼부분
						<tr>
							<td height='7'></td>
						</tr>
						<tr>
							<td>
								<table width='658' height='32' bgcolor='#F3F3F3' cellpadding="0" cellspacing="0">
									<tr>
										<td><p align='right'><img src='/images/dbt_b16on.gif' align="absmiddle" onclick="javascirpt:openErpPopUp()">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
		버튼종료--%>

					</table>
				</td>
			</tr>
		</table>						
	
</body>

</html>					