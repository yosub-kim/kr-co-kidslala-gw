<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="org.springframework.web.bind.ServletRequestUtils"%>
<%
	String title = ServletRequestUtils.getStringParameter(request, "title", "");
	String backButtonYN = ServletRequestUtils.getStringParameter(request, "backButtonYN", "N");
%>
<!--<div id="mainTitle" style="width:100%;">  -->
	<table width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
		<colgroup>
			<col style="width:   *;"/>
			<% if(backButtonYN.equals("Y")) { %>
			<col style="width: 100px;"/>
			<% } %>
		</colgroup>
		<tr>
			<td><h3 class="mainTitle"><%=title %></h3></td>
			<% if(backButtonYN.equals("Y")) { %>
			<td>
				<div class="btNdiv txtR">
					<a class="btNaaa txt2btn" href="javascript:history.back()">이전</a>
				</div>
			</td>
			<% } %>
		</tr>
	</table>	
<!--</div>-->