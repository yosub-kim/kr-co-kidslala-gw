<!--#include virtual="/kmac/comlist/inc/func.asp"-->
<!--#include virtual="/kmac/Common/inc/LogonCheck.asp"-->
<!--#include virtual="/kmac/common/inc/page.asp"-->
<!--include virtual="/kmac/comlist/inc/authAdmin.asp"-->
<!--#include virtual="/kmac/Common/inc/inc_cnn.asp"-->
<!--#include virtual="/kmac/Common/inc/loding.asp"-->
<%
  ' 1일 고객정보 작성건수 5회 제한 (2012년 1월 6일)
  writeok = "Y"  
  SQL = "SELECT COUNT(*) CNT FROM CUSTOMER WHERE convert(varchar(10),regdate,121) = '" & left(now,10) & "' and writerssn = '" & session("jumin") & "' "
	Set Rs = pms_conn1.execute(SQL)    		
	IF Rs("cnt") > 4 then 
		writeok = "N"		
	end if

  '김지연 위원의 경우 5회 제한 하지 않음
  if session("userid")="jykim77" then
  	writeok = "Y"		
	end if

   page = Request("page")
   If page = "" Then page = 1	
   	
  search_ok = ""

	pageSize = 13 '보여줄 리스트 수
	pageList = 10 '페이징 목록 수
	comcode = request("comCode")
	sort1 = request("sort1")
	
	'기본검색
	function_type = Trim(request("function_type"))
	writer = Trim(request("writer"))
	customerName = Trim(request("customerName"))
	customerName = Replace(request("customerName"),"!@","&")
	refer_dept = Trim(request("refer_dept"))
	subject = Trim(request("subject"))
	subject = Replace(request("subject"),"!@","&")
	
	'상세검색
	regdate1 = Trim(request("regdate1"))
	regdate2 = Trim(request("regdate2"))
	embbsName = Trim(request("embbsName"))
	customerinfo_type = request("customerinfo_type")	
	embbsdept = Trim(request("embbsdept"))
	industry_type = request("industry_type")
	business_type = request("business_type")
	content = request("content")
	
	'comcode="124-81-00998"
	ppYn = Trim(request("ppYn"))
	custChk = Trim(request("custChk"))
	tgubun = Trim(request("tgubun"))
	date1 = request("date1")
	date2 = request("date2")

If date1 <>"" And date2 <> "" Then 
	idate1 = Left(date1,4)&mid(date1,6,2)&right(date1,2)
	idate2 = Left(date2,4)&mid(date2,6,2)&right(date2,2)
Else
	yy = Year(now)
	mm = Month(now)
	dd = Day(now)
	If mm < 10 Then mm = "0"&mm
	If dd < 10 Then dd = "0"&dd
	
	date1 = yy &"-"&mm&"-01"
	date2 = yy &"-"&mm&"-"&dd
	idate1 = yy&mm&"01"
	idate2 = yy&mm&dd
End If 


	search = request("search")
	searchkey = request("searchkey")
	link = "comcode=" & comcode & "&sort1=" & sort1 & "&function_type=" & function_type & "&writer=" & writer & "&customerName=" & Replace(customerName,"&","!@") & "&refer_dept=" & refer_dept & "&subject=" & Replace(subject,"&","!@") & "&regdate1=" & regdate1 & "&regdate2=" & regdate2 & "&embbsName=" & embbsName & "&customerinfo_type=" & customerinfo_type & "&embbsdept=" & embbsdept & "&industry_type=" & industry_type & "&business_type=" & business_type & "&content=" & content & "&ppYn=" & ppYn	 & "&tgubun=" & tgubun	 & "&custChk=" & custChk  & "&date1=" & date1	 & "&date2=" & date2 	' 페이징할때의 파라미터값

%>

<html>
<head>
	<META HTTP-EQUIV= "Content-Type" CONTENT="text/html; charset=euc-kr">
	<title>리스트 보여주기(1~)</title>
	<style>
		td{font-size:9pt}
		select {width:200px;}
	</style>	
<script language = "JavaScript">
<!--
function open_url(url,openwin,width,height)
{
open(url,openwin, "width="+width+", height="+height+", toolbar=no, location=no");
} 
function open_url2(url,openwin,width,height)
{
open(url,openwin, "width="+width+", height="+height+", toolbar=no, location=no, scrollbars=yes");
} 
function pageGo(totpg)
{
	var pgnum = document.frm.pgnum.value;
	pgnum = parseInt(pgnum);
	totpg = parseInt(totpg);
	if(pgnum>totpg)
	{
		alert("전체 페이지수보다 낮은값을 입력하세요");
	}
	else
	{
		document.SearchForm.page.value=pgnum;
		document.SearchForm.submit();
	}
} 
function do_search()
{
	document.SearchForm.submit();
}

function show_div()
{
	document.all.detail_search.style.display='';
	document.all.display_btn.innerHTML="<img src='/kmac/kmac_image/btn_customer_search_close.jpg'	border=0 align='absmiddle'>";
}
//-->
</script>
<script language="javascript" src="/kmac/common/js/common.js"></script>
<link href="/kmac/common/css/data_css.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0" style="overflow-x:hidden;overflow-y:auto" 
<% if regdate1<>"" or regdate2<>"" or embbsName<>"" or customerinfo_type<>"" or embbsdept<>"" or industry_type<>"" or business_type<>"" then %>
	onload="show_div();"<% end if %>
>
<table border="0" cellpadding="0" cellspacing="0" width="770">
<tr>
	<td width="770" height="8">&nbsp;</td>
</tr>
<tr>
	<td align="center" height="12" width="100%">
		<div id="mainTitle">
		<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="22"><img src="/kmac/kmac_image/title_de01.jpg" width="15" height="15"></td>
				<td width="70%" align="left" valign="bottom"><span class="mainTitle">고객정보 관리</span></td>
				<td align="right">&nbsp;
				<% if search_ok <> "" then %>
					<img src="/kmac/kmac_image/btn_previous.jpg" onclick="javascript:history.back();" style="cursor:hand">&nbsp;
				<% end if %>
				<a href='javascript:void(0)' id="display_btn" onclick="this.innerHTML=(detail_search.style.display=='none')?'<img src=\'/kmac/kmac_image/btn_customer_search_close.jpg\'	border=0 align=\'absmiddle\'>':'<img src=\'/kmac/kmac_image/btn_customer_search_open.jpg\'	border=0 align=\'absmiddle\'>';detail_search.style.display=(detail_search.style.display=='none')?'block':'none'";><img src="/kmac/kmac_image/btn_customer_search_open.jpg"	border=0 align="absmiddle"></a>
				</td>
			</tr>
		</table>
		</div>
	</td>
</tr>
<tr>
	<td height="10"></td>
</tr>	
<tr>
<td>

<table width="100%"  border="0" cellpadding="0" cellspacing="0">
<tr><td>
	
<!-- #include virtual="/kmac/Common/inc/search_header.asp" -->

<table width="100%" border="0" cellpadding="0" cellspacing="0" height="78">
<form name="SearchForm" method="post" action="customer_list.asp">
<input type="hidden" name="comcode" value="<%=comcode%>">
<input type="hidden" name="page">
  <tr height="23">
    <td width="15%" class="searchTitle_center">Function별</td>
    <td width="30%" class="searchField_left">
	  <select name="function_type" id="function_type" style="width:100%">
	 	 <option value="">[::전체::]</option>
<%
sql2 =  " SELECT	id, description as name  FROM	smgroup with(nolock)"
sql2 = sql2 & "   WHERE	enabled='1' and depth=1 and left(id,1) = '2' "
sql2 = sql2 & "    ORDER	BY seq "
'response.write sql2
  Set Rs = pms_conn1.execute(sql2)
  Do While Not rs.eof 
%>
		<option value="<%=rs("id")%>" <%If function_type=Trim(rs("id")) Then response.write "selected"%>><%=rs("name")%></option>
<%
rs.movenext
Loop
rs.close
%>
      </select>
	</td>
    <td width="15%" class="searchTitle_center">작성자</td>
    <td width="30%" class="searchField_left">
	  	<INPUT TYPE="text" NAME="writer" size="10"  style="width:100%" value="<%=writer%>" style="ime-mode:active" onkeypress="if(event.keyCode == 13) return do_search();">
	</td>
  </tr>
  <tr height="23">
     <td width="15%" class="searchTitle_center">고객사명</td>
     <td width="30%" class="searchField_left">
	  	<INPUT TYPE="text" NAME="customerName" size="25"  style="width:100%" value="<%=customerName%>" style="ime-mode:active" onkeypress="if(event.keyCode == 13) return do_search();">
		 </td>
		 <td width="15%" class="searchTitle_center">주요상담영역</td>
    <td width="30%" class="searchField_left">
	  <select name="refer_dept" id="refer_dept" style="width:100%">
	 	 <option value="">[::전체::]</option>
<%
sql2 =  " SELECT	id, aliasName as name  FROM	smgroup with(nolock)"
sql2 = sql2 & "   WHERE	enabled=1 and (depth=1 and left(id,2) <> '20')  and seq < 24 "
sql2 = sql2 & "    ORDER	BY seq "
'response.write sql2
  Set Rs = pms_conn1.execute(sql2)
  Do While Not rs.eof 
%>
		<option value="<%=rs("id")%>" <%If refer_dept=Trim(rs("id")) Then response.write "selected"%>><%=rs("name")%></option>
<%
rs.movenext
Loop
rs.close
%>
      </select>
	</td>
  </tr>
  <tr height="21">
    <td width="15%" class="searchTitle_center">제  목</td>
    <td colspan="3"  class="searchField_left">		
			<INPUT TYPE="text" NAME="subject" size="84"  style="width:100%" value="<%=subject%>" style="ime-mode:active" onkeypress="if(event.keyCode == 13) return do_search();">
		</td>
  </tr>
</table>
</td>

<!-- #include virtual="/kmac/Common/inc/search_footer.asp" -->

</td></tr>
</table>
</td></tr>

<tr height="10">
	<td colspan=7></td>
</tr>

<tr>
	<td align="right">				
		
		<DIV id="detail_search" style='display:none'>
				
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="23"><b>▶ 상세검색</td>
			</tr>
				
		<tr><td>
		
		
		<!-- #include virtual="/kmac/Common/inc/search_header.asp" -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0" height="78">
		  <tr height="23">
		    <td width="15%" class="searchTitle_center">등록기간</td>
		    <td width="30%" class="searchField_left">
			  	<Div id='popCal' style='POSITION:absolute;visibility:hidden;border:2px ridge;width:10'>
						<iframe name="popCalFrame" src="js/popcjs.htm" frameborder="0" scrolling="no" width=183 height=193></iframe>
					</DIV>
			    &nbsp;<input type="text" size="10" name="regdate1" value="<%=regdate1%>"><img src="images/calendar.gif" border="0" onClick="popCalFrame.fPopCalendar(regdate1, regdate1, popCal);return false"     align="absmiddle"> 
					&nbsp;&nbsp;~  &nbsp;&nbsp;<input type="text" name="regdate2" size="10" value="<%=regdate2%>"><img src="images/calendar.gif" border="0" onClick="popCalFrame.fPopCalendar(regdate2, regdate2, popCal);return false"     align="absmiddle">
				</td>
		    <td width="15%" class="searchTitle_center">정보제공자</td>
		    <td width="30%" class="searchField_left">
				 	<INPUT TYPE="text" NAME="embbsName" size="10"  style="width:100%" value="<%=embbsName%>" style="ime-mode:active" onkeypress="if(event.keyCode == 13) return do_search();">
				</td>
		  </tr>
		  <tr height="23">
		    <td width="15%" class="searchTitle_center">정보유형</td>
		    <td width="30%" class="searchField_left">
			  <select name="customerinfo_type" id="customerinfo_type" style="width:100%">
				  <option value="">[::전체::]</option>
			<%
			sql2 =  " SELECT	table_name, key_1, data_1, data_2, data_3  FROM	cmtabledata "
			sql2 = sql2 & "   WHERE	table_name='CUSTOMERINFO_TYPE_CODE'	and data_2 = 'Y'	 "
			sql2 = sql2 & " 	ORDER	BY seq ASC "
			'response.write sql2
			  Set Rs = pms_conn1.execute(sql2)
			  Do While Not rs.eof 
			%>
					<option value="<%=rs("key_1")%>" <%If customerinfo_type=Trim(rs("key_1")) Then response.write "selected"%>><%=rs("data_1")%></option>
			<%
			rs.movenext
			Loop
			rs.close
			%>
      </select>
			</td>
		    <td width="15%" class="searchTitle_center">정보제공부서</td>
		    <td width="30%" class="searchField_left">
				  <INPUT TYPE="text" NAME="embbsdept" size="10"  style="width:100%" value="<%=embbsdept%>" style="ime-mode:active">
				</td>
		  </tr>
		  
		  <tr height="23">
		  	<td width="15%" class="searchTitle_center">산업별</td>
		    <td width="30%" class="searchField_left">
			  <select name="industry_type" id="industry_type" style="width:100%">
			  <option value="">[::전체::]</option> 
		<%
		sql2 =  " SELECT	table_name, key_1, data_1, data_2, data_3  FROM	cmtabledata WITH(NOLOCK) "
		sql2 = sql2 & "   WHERE	table_name='INDUSTRY_TYPE_CODE'		 "
		sql2 = sql2 & "    ORDER	BY key_1 "
		'response.write sql2
		  Set Rs = pms_conn1.execute(sql2)
		  Do While Not rs.eof 
		%>
				<option value="<%=rs("key_1")%>" <%If industry_type=Trim(rs("key_1")) Then response.write "selected"%>><%=rs("data_1")%></option>
		<%
		rs.movenext
		Loop
		rs.close
		%>
		      </select>
				</td>		  
		    <td width="15%" class="searchTitle_center">비즈니스타입</td>
		    <td width="30%" class="searchField_left">
			  <select name="business_type" id="business_type" style="width:100%">
			  <option value="">[::전체::]</option>
		<%
		sql2 =  " SELECT	table_name, key_1, data_1, data_2, data_3  FROM	cmtabledata WITH(NOLOCK) "
		sql2 = sql2 & "   WHERE	table_name='BUSINESS_TYPE_CODE' and data_3 = 'CUSTOMER' "
		sql2 = sql2 & "    ORDER	BY seq "
		'response.write sql2
		  Set Rs = pms_conn1.execute(sql2)
		  Do While Not rs.eof 
		%>
				<option value="<%=rs("key_1")%>" <%If business_type=Trim(rs("key_1")) Then response.write "selected"%>><%=rs("data_1")%></option>
		<%
		rs.movenext
		Loop
		rs.close
		%>
			</SELECT>
			</td>		  	
		</tr>
		<tr height="21">
	    <td width="15%" class="searchTitle_center">정보내용</td>
	    <td colspan="3"  class="searchField_left">		
				<INPUT TYPE="text" NAME="content" size="84"  style="width:100%" value="<%=content%>" style="ime-mode:active" onkeypress="if(event.keyCode == 13) return do_search();">
			</td>
  </tr>
		  
		</table>
		</td>		

		<!-- #include virtual="/kmac/Common/inc/search_footer.asp" -->

		</td></tr>
		</table>
		
		</DIV>
		</form>
	</td>
</tr>


<tr><td>
<%
  ' 리스트 가져오기
  sp_name="sp_customerInfoList"
  sql=" exec "& sp_name &" '"& comcode &"','"& sort1 &"','"& function_type &"','"& writer &"','"& customerName &"','"& refer_dept &"','"& subject &"' ,'"& replace(regdate1,"-","") &"' ,'"& replace(regdate2,"-","") &"' ,'"& embbsName &"' ,'"& customerinfo_type &"' ,'"& embbsdept &"' ,'"& industry_type &"' ,'"& business_type &"' ,'"& content &"','"& pageSize &"','"& page &"','"& ppYn &"','"& tgubun &"','"& custChk &"','"& idate1 &"','"& idate2 &"'"
	
	response.write sql
  rs.open sql,pms_conn1,1

  if Rs.eof = false then 	
  	TotalRecords = Rs("count")
  	pagecount = int((TotalRecords-1)/pageSize) + 1
  End if
%>
		<table border="0" width="100%" cellpadding="0" cellspacing="0">
		<tr height="30">
			<td colspan=6 align=left><strong>
				<%=TotalRecords%> Total - Page(<%=page%> of <%=pagecount%>)
			</strong>			
			</td>
			<td align="right">
				<input style="FONT-SIZE: 9pt; COLOR: 14311c; WIDTH: 145px; HEIGHT: 25px; BACKGROUND-COLOR: #f2f2f2;cursor:hand" type="button" value="☞ 고객정보유형 안내" onclick="open_url('images/customerInfoGuide.gif','customerInfoGuide','870','600')">&nbsp;
				<% if writeok="Y" then %>
					<a href="customer_ins.asp?comcode=<%=comcode%>"><img src="/kmac/kmac_image/btn_regist.jpg" border=0 align="absmiddle"></a>
				<% else %>
					<a href="#" onclick="javascript:alert('금일 고객정보 작성건수가 5회를\n초과하여 더이상 등록하실 수\n없습니다.');"><img src="/kmac/kmac_image/btn_regist.jpg" border=0 align="absmiddle"></a>
				<% end if %>
			</td>
		</tr>
		</table>
</td></tr>
<tr><td>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="2" align="left" bgcolor="#907e63"></td>
			</tr>
      <tr>
        <td><table width="100%"  border="0" style="table-layout:fixed" class="listTable">
						<thead>
						<tr height="25">
							<td width="8%" >정보유형</td>
							<td width="38%" >제목</td>
							<td width="8%" >Function</td>
							<td width="8%" >주요상담</td>
							<td width="14%" >고객사/협력기관</a>
							<td width="7%" >작성자</a>
							<td width="9%" >작성일</a>
							<td width="5%" >조회</a>
						</tr>
						</thead>
						<TBODY>
<%

if Rs.eof Then 
		Response.Write "<tr class='detailTableField_left'>" & _
												"<td colspan='8' align='center'>해당하는 자료가 없습니다</td>" & _
												"</tr>"
Else
	num =int(TotalRecords)-(((page-1)*pagesize))	
	do while not rs.Eof
	If Not rs("customerName") = "" Then 
		customerName = Left(rs("customerName"),8)
	Else
		customerName = ""
	End If 
	If Not rs("regdate") = "" Then regdates = Left(rs("regdate"),10)
		
	' 보기 이쁘게 내용길이 수정	
	writerDeptName = left(replace(replace(replace(rs("writerDeptName"),"본부",""),"센터",""),"CS/","CS"),5)
	
'	if rs("businessTypeName") = "리더스 클럽" then
'		businessTypeName = "리더스"
'	else
'		businessTypeName = rs("businessTypeName")
'	end if
	
	subject = reConvertBr(rs("subject"))
	customerInfoName = replace(rs("customerInfoName"),"경쟁사정보","경쟁사")
	customerInfoName = replace(customerInfoName,"상담/프로모션","상담")
	customerInfoName = replace(customerInfoName,"수주/수주실패","수주여부")
	
	refer_name = replace(rs("refer_Name"),"에너지환경","에너지")
%>

		<tr height="23" onmouseover="row_over(this)" onmouseout="row_out(this)">
			<td align="center" height="22" valign="middle"><%=customerInfoName%></td>
			<td style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; "><a href="customer_detail.asp?idx=<%=rs("idx")%>&<%=link%>&page=<%=page%>"><%=subject%></a></td>
			<td align="center" valign="middle" nowrap><%=writerDeptName%></td>
			<td align="center" valign="middle"><%=refer_name%></td>
			<td align="center" style="white-space:nowrap; text-overflow: ellipsis; overflow: hidden; " <%if rs("customerCode") <> "" then response.write "bgcolor='#dbf0fe'"%>><%=customerName%></td>
			<td align="center" valign="middle"><%=rs("writer")%></td>
			<td align="center" valign="middle"><%=regdates%></td>
			<td align="center" valign="middle"><%=rs("readCnt")%></td>
		</tr>

<%      
		rs.moveNext
		num = num - 1

	loop  
End If 
%>
		</TBODY>
		</table>
</td></tr>
<tr>
	<td height="2" align="left" bgcolor="#907e63"></td>
</tr>
<tr><td>
</td></tr>
<tr><td>

		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr height="25" align="center">
			<td align="center" width="40%" valign="middle"><% paging pageCount,page,pageList,"customer_list.asp",link %></td>
		</tr>
		<tr>
			<td align="right" width="100%" height="12"></td>
		</tr>
		</table>
	</td>
</tr>
</table>
<%

pms_conn1.close
set pms_conn1 = nothing


%>
</body>
</html>
<% ' 관리자만 페이지 지정 이동 가능 %>
<% if session("userid") = "wsang" or session("userid") = "shs" or session("userid") = "lokal07" then %>
	<div style="position:relative;top:0px;left:10px;width:150;">
		<table width="100%">
			<form name="frm">
			<tr><td>
			페이지 <input type="text" name="pgnum" value="<%=page%>" size="3" onKeyPress="if(event.keyCode==13){pageGo('<%=pagecount%>');}"> <input type="button" value="이동" onclick="pageGo('<%=pagecount%>');">
			</td></tr>
			</form>
		</table>
	</div>
<% end if %>