<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<%@page import="org.springframework.web.bind.ServletRequestUtils"%>
<%
	// nPageNo, nPageSize, nRecordCount, nListSize
	int nPageNo			= ServletRequestUtils.getIntParameter(request, "nPageNo", 1);
	int nPageSize		= ServletRequestUtils.getIntParameter(request, "nPageSize", 10);
	int nRecordCount	= ServletRequestUtils.getIntParameter(request, "nRecordCount", 10);
	int nListSize		= ServletRequestUtils.getIntParameter(request, "nListSize", 10);
	
	System.out.println("ddddddddddddddddddddddddddddddddddddddddddd : " + Integer.toString(nRecordCount));
	
	int nTotalEnd = nRecordCount / nListSize;
	
	if ((nRecordCount % nListSize) != 0) {
		++nTotalEnd;
	}
	
	if (nPageNo > nTotalEnd) {
		nPageNo -= 1;
	}
	
	int nStartPage = ((nPageNo - 1) / nPageSize) * nPageSize + 1;
	int nEndPageTmp = nStartPage + nPageSize - 1;
	int nEndPage = (nTotalEnd > nEndPageTmp) ? nEndPageTmp : nTotalEnd;

	int nPrevious = (nStartPage == 1) ? 0 : (nStartPage - 1);
	int nNext = (nTotalEnd > nEndPage) ? (nEndPage + 1) : 0;
	
	String pageHtml = "";
	pageHtml += "<table cellpadding='0' cellspacing='0' width='100%' style='margin:20 0 0 0;'> ";
	pageHtml += "<tr> ";
	pageHtml += "	<td align='center'> ";
	pageHtml += " 	<table> ";
	pageHtml += "	<tr> ";

	if (nRecordCount > 0) {

		if (nRecordCount != 0) {
			pageHtml += "	<td> ";
			if (nPrevious != 0) {

				pageHtml += "<a href=\"javascript:goPage('"
						+ Integer.toString(nPrevious)
						+ "')\"><img alt='이전' src='/images/sub_btn_prev.gif' border='0' align=absmiddle></a>";

			} else {
				pageHtml += "<img alt='이전' src='/images/sub_btn_prev.gif' border='0' align=absmiddle>";
			}
			pageHtml += "	</td> ";
		}

		pageHtml += "	<td style='font-size:11px; font-family: tahoma;'> ";
		for ( int i = nStartPage; i <= nEndPage; i++) {
			if (i == nPageNo) {
				pageHtml += "&nbsp;&nbsp;|&nbsp;&nbsp;<a href='#' class='on'><B>" + Integer.toString(i) + "</B></a>";
			} else {
				pageHtml += "&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:goPage('" + i + "')\" >" + Integer.toString(i)	+ "</a>";
			}
		}
		pageHtml += "&nbsp;&nbsp;|&nbsp;&nbsp;</td> ";

		if (nRecordCount != 0) {
			pageHtml += "	<td> ";
			if (nNext != 0) {
				pageHtml += "<a href=\"javascript:goPage('"
						+ Integer.toString(nNext)
						+ "')\"><img alt='다음' src='/images/sub_btn_next.gif' border='0' align=absmiddle></a>";
			} else {
				pageHtml += "<img alt='다음' src='/images/sub_btn_next.gif' border='0' align=absmiddle>";
			}
			pageHtml += "	</td> ";
		}
	}

	pageHtml += "</td>";
	pageHtml += "</tr>";
	pageHtml += "</table>";	
	
%>
<%=pageHtml%>