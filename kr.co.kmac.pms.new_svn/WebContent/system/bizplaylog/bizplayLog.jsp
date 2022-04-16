<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>BizPlay Interface Log</title>

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>

function goPage(next_page) {
	document.form.pg.value = next_page ;
	document.form.target = "";		
	document.form.action = "/action/BizplayLogAction.do?mode=getBizplayLogList";
	document.form.submit();
}
function doSearch() {
	document.form.target = "";		
	document.form.action = "/action/BizplayLogAction.do?mode=getBizplayLogList";
	document.form.submit();
}
function Resend_dept(){
	if(!confirm("Resend_dept ?(전체)")){return;}
	var ActionURL = "/action/BizplayLogAction.do?mode=resendBizplayDeptInfo";
	var sFrm = document.form;
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				var resultMsg = eval('(' + obj.responseText + ')').resultMsg;
				alert(resultMsg);			
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				var resultMsg = eval('(' + obj.responseText + ')').ErrMsg;
				alert(resultMsg);
			}
		}
	); status = null;	
}
function Resend_expertpool(){
	if(!confirm("Resend_expertpool ?(전체)")){return;}
	var ActionURL = "/action/BizplayLogAction.do?mode=resendBizplayExpertpoolInfo";
	var sFrm = document.form;
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				var resultMsg = eval('(' + obj.responseText + ')').resultMsg;
				alert(resultMsg);			
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				var resultMsg = eval('(' + obj.responseText + ')').ErrMsg;
				alert(resultMsg);
			}
		}
	); status = null;	
}
function Resend_project(){
	if(!confirm("Resend_project ?(과거1년)")){return;}
	var ActionURL = "/action/BizplayLogAction.do?mode=resendBizplayProjectInfo";
	var sFrm = document.form;
	var status = AjaxRequest.submit(
		sFrm
		,{ 'url':ActionURL
			,'onSuccess':function(obj){
				var resultMsg = eval('(' + obj.responseText + ')').resultMsg;
				alert(resultMsg);			
			}
			,'onLoading':function(obj){}
			,'onError':function(obj){				
				var resultMsg = eval('(' + obj.responseText + ')').ErrMsg;
				alert(resultMsg);
			}
		}
	); status = null;	
}
</script>
</head>

<body>
<div style="margin: 70 0 0 20 ">
<form name="form" method="post">	
	<input type="hidden" name="pg" id="pg">
	<table width="100%" cellpadding="0" cellspacing="0">
	<!-- SPACER -->
		<tr>
			<td>
				<jsp:include page="/common/include/includePageMainTitle.jsp" flush="true">
					<jsp:param name="title" value="BizPlay Interface Log" />
				</jsp:include>
			</td>
		</tr>
		<!-- 검색 영역 -->
		<tr>
			<td height="21" align="left" valign="top">
				<%@ include file="/common/include/searchBox_Header.jsp"%>					
					<table border="0" width="100%" style="border-collapse: collapse; ">
						<colgroup> 
							<col width="80px">
							<col width="100px">
							<col width="*>
							<col width="80px">
							<col width="100px">
						</colgroup>
						<tr>
							<td class="searchTitle_center">Enp Point</td>
							<td class="searchField_center" colspan="2">
								<select name="endPoint" class="selectbox" style="width: 100%;">
									<option value="">전체</option>
									<option value="emplinfo_erp_cu002" <c:if test="${endPoint=='dvsninfo_erp_cu002'}">selected</c:if>>emplinfo_erp_cu002</option>
									<option value="dvsninfo_erp_cu003" <c:if test="${endPoint=='dvsninfo_erp_cu003'}">selected</c:if>>dvsninfo_erp_cu003</option>
									<option value="1811"  <c:if test="${endPoint=='1811'}">selected</c:if>>1811</option>
								</select>
							</td>
							<td class="searchTitle_center">날짜</td> 
							<td class="searchField_left">
								<script>
									jQuery(function(){jQuery( "#from" ).datepicker({});});
									jQuery(function(){jQuery( "#to" ).datepicker({});});
								</script>
								<input type="text" name="from" id="from" readonly="readonly" size="8" value="<c:out value="${from}" />" /> ~ 
								<input type="text" name="to" id="to" readonly="readonly" size="8" value="<c:out value="${to}" />" />
							</td>
						</tr>
					</table>
				<%@ include file="/common/include/searchBox_Footer.jsp"%>
			</td>
		
		</tr>					
	 	
		<tr>
			<td height="35px">
				<div class="btNbox pb15">
					<div class="btNlefTdiv">				
						<img src="/images/sub/line3Blit.gif" alt="">
						<span class="bold colore74c3a"><c:out value="${list.valueListInfo.totalNumberOfEntries}"/></span>
						<span>Total - Page(<span><c:out value="${list.valueListInfo.pagingPage}"/></span> of <span><c:out value="${list.valueListInfo.totalNumberOfPages}"/></span>)</span>
					</div>				
				</div>
			</td>
		</tr>
		<tr>
			<td height="35px">
				<div class="btNbox pb15">
					<div >
						<a class="btNaaa txt2btn" href="javascript:Resend_dept()">Resend(Dept-All)</a>
						<a class="btNaaa txt2btn" href="javascript:Resend_expertpool()">Resend(Expertpool-All)</a>
						<a class="btNaaa txt2btn" href="javascript:Resend_project()">Resend(Project-All)</a>
					</div>					
				</div>
			</td>
		</tr>
		
		<tr>
			<td>
				<table id="projectRollingMonitorTable" class="listTable" style="table-layout: fixed">
					<thead id="projectRollingMonitorTableHeader">
						<tr> 
							<td width="80px">createdTime</td> 
							<td width="120px">endPoint</td> 
							<td width="45%">sendText</td> 
							<td width="45%">resultText </td>
							<td width="10%">exceptionText</td>
						</tr>  
					</thead>									
					<tbody id="projectRollingMonitorTableBody"> 
						<c:if test="${!empty list.list}">
							<c:forEach var="rs" items="${list.list}">
								<tr onmouseover="row_over(this)" onmouseout="row_out(this)">
									<td><c:out value="${rs.createdTime}"/></td> 
									<td><c:out value="${rs.endPoint}"/></td> 
									<td><textarea name="sendTxt" id="sendTxt" style="width: 100%; height: 150px; overflow: auto;" class="textArea" readonly><c:out value="${rs.sendTxt}"/></textarea></td>
									<td><textarea name="sendTxt" id="sendTxt" style="width: 100%; height: 150px; overflow: auto;" class="textArea" readonly><c:out value="${rs.resultTxt}"/></textarea></td>
									<td><textarea name="sendTxt" id="sendTxt" style="width: 100%; height: 150px; overflow: auto;" class="textArea" readonly><c:out value="${rs.exceptionStr}"/></textarea></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty list.list}"><tr><td colspan="5"><br>검색 결과가 존재하지 않습니다 .<br><br></td></tr></c:if>
					</tbody>
				</table>
			</td>
		</tr>
		<%-- 페이징 영역 시작 --%>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="30">
						<td width="100%" align="center">
							<SCRIPT LANGUAGE="JavaScript">
								document.write( makePageHtml( 
										<c:out value="${list.valueListInfo.pagingPage}" default="1"/>, 
										15, 
										<c:out value="${list.valueListInfo.totalNumberOfEntries}" default="0"/> , 
										15)
								) ;
							</SCRIPT>
						</td>
					</tr>
				</table>									
			</td>
		</tr>
		<%-- 페이징 영역 끝--%>
					
	</table>
</form>
</div>
</body>

</html>