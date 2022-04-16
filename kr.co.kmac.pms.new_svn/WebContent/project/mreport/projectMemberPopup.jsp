<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<%@page import="kr.co.kmac.pms.common.util.DateTime"%><html>
<head>
<title>실행 인력 선택</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%-- 공통js,css include --%>
<%@ include file="/common/include/includeCss.jsp"%>
<%@ include file="/common/include/includeJavaScript.jsp"%>

<script type="text/javascript">
<%-- 개별 스크립트 영역 --%> 
function registProjectMember() {
	var chkBoxSsn = $$('input[name="ssn"]');
	var chkBoxName = $$('input[name="name"]');
	var str='';
	for ( var i=0;i<chkBoxSsn.length;i++){ 
		if ( chkBoxSsn[i].checked ) {
			str = str+chkBoxName[i].value+", ";
			//opener.document.getElementById("projectMemberStr_html").innerHTML +=  
			//	"<span>"+chkBoxName[i].value+"&nbsp;&nbsp;<img border='0' src='/images/delete.gif' onclick='$(this).up().remove();resetProjectMemberStr()'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";	
		}
	}
	
	//str = str.substring(0, str.length-2);
	//opener.document.getElementById("projectMemberStr").value = opener.document.getElementById("projectMemberStr").value + ', ' + str;
	opener.document.getElementById("projectMemberStr").value = str.substring(0, str.length-2);
	self.close();
}

</script>
</head>

<body style="width:100%; padding-left:5px; padding-right:5px">
	<form name="projectReportScheduleForm" method="post">
	<div style="display: none;">
		<input type="text" name="projectCode" id="projectCode" value="<c:out value="${projectCode}"/>" >
		<input type="text" name="businessTypeCode" id="businessTypeCode" value="<c:out value="${businessTypeCode}"/>" >
	</div>
	<table width="95%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<h4 class="subTitle">실행 인력 선택</h4>
			</td>
		</tr>
					<!-- sub 타이틀 영역 종료--> 
				
		<tr>
			<td>
				<table width='100%' cellpadding="0" cellspacing="0">
					<tr>  
						<td class="detailTableField_left" width="*"> 
							<div style="width: 100%; height: 165px; margin-left: 0px; margin-right: 0px; margin-bottom: 3px; margin-top: 3px; overflow: auto">
								<table class="listTable">
									<colgroup>
										<col align="center"  width="30%">								
										<col align="center" width="40%">								
										<col align="center" width="30%">
									</colgroup>
									<thead>
										<tr height="25px">
											<td scope="col">선택</td>
											<td scope="col">이름</td> 
											<td scope="col">Role</td>
										</tr>
									</thead>			
									<tbody id="ListData">
										<c:forEach var="rs" items="${projectMemberList.list}">
											<tr>
												<td>
													<input id="ssn" type="checkbox" name="ssn" value="<c:out value="${rs.ssn}" />">
													<input id="name" type="hidden" name="name" value="<c:out value="${rs.name}" />">
												</td>
												<td><c:out value="${rs.name}" /></td>
												<td><c:out value="${rs.role}" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	
		<tr>
			<td height="7"></td>
		</tr>
		<tr>
			<td style="height:36px; background:#F3F3F3;">
				<div class="btNbox pt10 pb10 txtR">
					<a class="btNa0a0a0 txt2btn" href="#" onclick="self.close()">닫기</a>
					<a class="btNe006bc6 txt2btn" href="#" onclick="registProjectMember()">등록</a>
				</div>
			</td>
		</tr>
	</table>						

	</form>
</body>

</html>					