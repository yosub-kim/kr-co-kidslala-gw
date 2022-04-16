function makePageHtml(nPageNo, nPageSize, nRecordCount, nListSize) {

	var nTotalEnd = Math.floor(nRecordCount / nListSize); // 맨 끝 페이지를 구한다.(총
	// 글수 / 화면에 보여질 페이지의
	// 수)

	// 마지막 페이지의 글 수가 초과하면 페이지수 1 추가
	if ((nRecordCount % nListSize) != 0) {
		++nTotalEnd;
	}

	//현재 페이지가 마지막 페이지보다 클때 전 페이지를 보여준다.
	if (nPageNo > nTotalEnd) {
		nPageNo -= 1;
	}

	var nStartPage = Math.floor(((nPageNo - 1) / nPageSize)) * nPageSize + 1;
	var nEndPageTmp = nStartPage + nPageSize - 1;
	var nEndPage = (nTotalEnd > nEndPageTmp) ? nEndPageTmp : nTotalEnd;

	var nPrevious = (nStartPage == 1) ? 0 : (nStartPage - 1);
	var nNext = (nTotalEnd > nEndPage) ? (nEndPage + 1) : 0;

	pageHtml = "";

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
						+ nPrevious
						+ "')\"><img alt='이전' src='/images/sub/btnPrev.gif' border='0' align=absmiddle></a>";

			} else {
				pageHtml += "<img alt='이전' src='/images/sub/btnPrev.gif' border='0' align=absmiddle>";
			}
			pageHtml += "	</td> ";
		}

		pageHtml += "	<td style='font-size:11px; font-family: tahoma;'> ";
		for ( var i = nStartPage; i <= nEndPage; i++) {
			if (i == nPageNo) {
				pageHtml += "&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' class='on'><B>" + i + "</B></a>";
			} else {
				pageHtml += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:goPage('" + i + "')\" >" + i	+ "</a>";
			}
		}
		pageHtml += "&nbsp;&nbsp;&nbsp;&nbsp;</td> ";

		if (nRecordCount != 0) {
			pageHtml += "	<td> ";
			if (nNext != 0) {
				pageHtml += "<a href=\"javascript:goPage('"
						+ nNext
						+ "')\"><img alt='다음' src='/images/sub/btnNext.gif' border='0' align=absmiddle></a>";
			} else {
				pageHtml += "<img alt='다음' src='/images/sub/btnNext.gif' border='0' align=absmiddle>";
			}
			pageHtml += "	</td> ";
		}
	}

	pageHtml += "</td>";
	pageHtml += "</tr>";
	pageHtml += "</table>";

	return pageHtml;

}
function makePageHtml2(nPageNo, nPageSize, nRecordCount, nListSize) {

	var nTotalEnd = Math.floor(nRecordCount / nListSize); // 맨 끝 페이지를 구한다.(총
	// 글수 / 화면에 보여질 페이지의
	// 수)

	// 마지막 페이지의 글 수가 초과하면 페이지수 1 추가
	if ((nRecordCount % nListSize) != 0) {
		++nTotalEnd;
	}

	//현재 페이지가 마지막 페이지보다 클때 전 페이지를 보여준다.
	if (nPageNo > nTotalEnd) {
		nPageNo -= 1;
	}

	var nStartPage = Math.floor(((nPageNo - 1) / nPageSize)) * nPageSize + 1;
	var nEndPageTmp = nStartPage + nPageSize - 1;
	var nEndPage = (nTotalEnd > nEndPageTmp) ? nEndPageTmp : nTotalEnd;

	var nPrevious = (nStartPage == 1) ? 0 : (nStartPage - 1);
	var nNext = (nTotalEnd > nEndPage) ? (nEndPage + 1) : 0;

	pageHtml = "";

	pageHtml += "	<div class='paging'>";
	pageHtml += "	<div class='pagination'> ";

	if (nRecordCount > 0) {

		if (nRecordCount != 0) {
			if (nPrevious != 0) {

				pageHtml += "<a href=\"javascript:goPage('"
						+ nPrevious
						+ "')\" class='icon'><i class='mdi mdi-page-first'></i></a>";

			} else {
				pageHtml += "<i class='mdi mdi-page-first'></i>";
			}
		}

		for ( var i = nStartPage; i <= nEndPage; i++) {
			if (i == nPageNo) {
				pageHtml += "<a href='#' class='current'>" + i + "</a>";
			} else {
				pageHtml += "<a href=\"javascript:goPage('" + i + "')\" >" + i	+ "</a>";
			}
		}

		if (nRecordCount != 0) {
			if (nNext != 0) {
				pageHtml += "<a href=\"javascript:goPage('"
						+ nNext
						+ "')\"  class='icon'><i class='mdi mdi-page-last'></i></a>";
			} else {
				pageHtml += "<i class='mdi mdi-page-last'></i>";
			}
		}
	}

	pageHtml += "</div>";
	pageHtml += "</div>";

	return pageHtml;

}



/*
		function goPage(next_page) {
			document._form.pageNo.value = next_page ;
			document._form.target = "";		
			document._form.action = "/common/sanction/getSanctionList.common";
			document._form.submit();
		}
		<SCRIPT LANGUAGE="JavaScript">
			document.write( makePageHtml( 
					<c:out value="${sanctionList[0].PAGE_NO}" default="1"/>, 
					10, 
					<c:out value="${sanctionList[0].TOTAL_COUNT}" default="0"/> , 
					10)
			) ;
		</SCRIPT>
*/