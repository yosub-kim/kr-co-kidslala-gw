<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>

<html>
<head>
<title>전사고객검색</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- 공통js,css include --%>
<%-- <%@ include file="/common/include/includeCss.jsp"%> --%>
<%@ include file="/common/include/includeJavaScript.jsp"%>
<link rel="stylesheet" href="/resources/css/common.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/board.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/selectric.css" type="text/css" />
<link rel="stylesheet" href="/resources/css/dev.css" type="text/css" />
<meta name="viewport" content="width=1600">
<script type="text/javascript">
<%-- 개별 스크립트 영역 --%>
function Dm(){
	idx             = "";
	name          = "";
	tel              = "";
	email          = "";
}

function doSearch()
{
	document.myProjectListForm.submit();
}
function use_dm(idx,name,company,tel,email)
{
	var dm = new Dm();
	dm.idx = idx;
	dm.name = name;
	dm.company = company;
	dm.tel = tel;
	dm.email = email;
	opener.<c:out value="${callbackFunc}" />(dm);
	window.close();
}

function open_regist(userid)
{
	window.open('http://intranet.kmac.co.kr/kmac/ver2/standard/customer_register.asp?mode=embbsSearch','dm_insert');
}

function open_modify(userid)
{
	var idx=0;
	var cnt=0;
		
	for (var i = 0; i < document.myProjectListForm.elements.length; i++) 
	{
		if ((document.myProjectListForm.elements[i].name == "chk_idx")&&(document.myProjectListForm.elements[i].checked==true))
	 	{
			idx = document.myProjectListForm.elements[i].value;
			cnt = 1;
			break;
		}			
	}
	
	 if (cnt==0) {	alert("수정할 고객을 선택하세요");  return ; }	
	 else {
		 window.open('http://intranet.kmac.co.kr/kmac/ver2/standard/customer_edit.asp?embbsSearch=1&idx='+idx,'dm_edit');
	}
}

function goPage(next_page){	
	document.myProjectListForm.pg.value = next_page ;
	document.myProjectListForm.target = "";		
	document.myProjectListForm.action = "/action/ProjectDmSearchAction.do?mode=getProjectDmSearchList&callbackFunc=setDM";
	document.myProjectListForm.submit();
}
/*popup layout*/
window.onload=function(){
	layer_open(this, 'pop_register');
}
</script>
</head>
<body>
	<form name="myProjectListForm" method="post">
	
	<div id="pop_register" class="popup_layer pop_register">
		<!-- <div class="popup_bg"></div> -->
		<div class="popup_inner tbl-sc" style="width:1300px; ">
			<div class="a-both">
				<p class="h1">고객사 정보</p>
			</div>

			<div class="box">
				<div class="search_box total">
					<div class="select_group">
						<div class="select_box">
							<div class="label_group">
								<p>검색조건</p>
								<select name="search_Gubun" class="select" style="width:20%">
									<option value="" <c:if test="${search_Gubun == '' }">selected</c:if>>전체</option>
									<option value="p_name" <c:if test="${search_Gubun == 'p_name'}">selected</c:if>>성명</option>
									<option value="p_company" <c:if test="${search_Gubun == 'p_company'}">selected</c:if>>회사명</option>
								</select>		
								<input type="text" name="searchKey" class="textInput_left" style="width: 79%" size="40" value="<c:out value="${searchKey}"/>">
							</div>
						</div>
					</div>
					<div><button type="button" class="btn btn_blue" onclick="doSearch();">검색<i class="mdi mdi-magnify"></i></button></div>
				</div>
			</div>
			<br>
				
			<div class="board_box">
				<div class="title_both">
					<div class="h1_area">
						<p class="term">상세정보</p>
					</div>
				</div>
				<div class="board_contents">
					<table id="dmTable" class="tbl-edit td-c pd-none"><!-- td 영역이 가운데 정렬 일 경우 td-c -->
						<colgroup>
							<col style="width: 15%">
							<col style="width: 25%">
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 15%">
						</colgroup>
						<tr>
							<th>성명</th>
							<th>회사명</th>
							<th>부서명</th>
							<th>이메일</th>
							<th>연락처</th>
							<th>등록자</th>
						</tr>
						<tbody>
						<c:forEach var="rs" items="${result.list}">
							<tr onmouseover="row_over2(this)" onmouseout="row_out(this)" >
								<td onClick="use_dm('<c:out value="${rs.idx}" />','<c:out value="${rs.p_name}" />','<c:out value="${rs.p_company}" />','<c:out value="${rs.p_tel1}" />','<c:out value="${rs.p_email}" />')"><c:out value="${rs.p_name}" /></td>
								<td title="<c:out value="${rs.p_company}" />" class="myoverflowC" onClick="use_dm('<c:out value="${rs.idx}" />','<c:out value="${rs.p_name}" />','<c:out value="${rs.p_company}" />','<c:out value="${rs.p_tel1}" />','<c:out value="${rs.p_email}" />')"><c:out value="${rs.p_company}" /></td>	
								<td title="<c:out value="${rs.p_part}" />" class="myovereflowC" onClick="use_dm('<c:out value="${rs.idx}" />','<c:out value="${rs.p_name}" />','<c:out value="${rs.p_company}" />','<c:out value="${rs.p_tel1}" />','<c:out value="${rs.p_email}" />')"><c:out value="${rs.p_part}" /></td>		
								<td title="<c:out value="${rs.p_email}" />" class="myoverflow" onClick="use_dm('<c:out value="${rs.idx}" />','<c:out value="${rs.p_name}" />','<c:out value="${rs.p_company}" />','<c:out value="${rs.p_tel1}" />','<c:out value="${rs.p_email}" />')"><c:out value="${rs.p_email}" /></td>	
								<td title="<c:out value="${rs.p_tel1}" />" class="myoverflowC" onClick="use_dm('<c:out value="${rs.idx}" />','<c:out value="${rs.p_name}" />','<c:out value="${rs.p_company}" />','<c:out value="${rs.p_tel1}" />','<c:out value="${rs.p_email}" />')"><c:out value="${rs.p_tel1}" /></td>
								<td onClick="use_dm('<c:out value="${rs.idx}" />','<c:out value="${rs.p_name}" />','<c:out value="${rs.p_company}" />','<c:out value="${rs.p_tel1}" />','<c:out value="${rs.p_email}" />')"><c:out value="${rs.writer}" /></td>	
							</tr>
						</c:forEach>
					</tbody>
					</table>
					<div class="paging_area">
						<SCRIPT LANGUAGE="JavaScript">
							document.write( makePageHtml2( 
									<c:out value="${result.valueListInfo.pagingPage}" default="1"/>, 
									10, 
									<c:out value="${result.valueListInfo.totalNumberOfEntries}" default="0"/> , 
									10)
							) ;
						</SCRIPT>
					</div>
					<div style="display:none">
						<input type="hidden" name="pg"       value="<c:out value="${pg}"/>">				
						<input type="hidden" name="search_Gubun"       value="<c:out value="${search_Gubun}"/>">
						<input type="hidden" name="searchKey"       value="<c:out value="${searchKey}"/>">
					</div>
					</div>
				</div>
			</div>
		</div>	
	</form>
</body>
</html>