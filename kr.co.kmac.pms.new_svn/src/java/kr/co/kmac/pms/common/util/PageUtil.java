package kr.co.kmac.pms.common.util;
/*-------------------------------------------------------------------
 * 프로젝트		: PMS 리뉴얼
 * 프로그램명	: PageUtil.java
 * 설명			: 페이지 HTML 생성 클래스
 * Version		: 1.0
 * 작성일자		: 2009/03/06
 * 작성자		: 허권주
 *-------------[수정 로그]-------------------------------------------
 * 수정일자		:
 * 수정내용		:
 --------------------------------------------------------------------*/
import jxl.common.Logger;

public final class PageUtil {

   private static final Logger logger = Logger.getLogger(PageUtil.class);
	//private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private PageUtil() {
        super();
    }

    /**
     * 페이지 출력 
     * @location  : PageUtil.getPagingStr
     * @writeDay  : 2007. 8. 6 오후 4:58:24
     *
     * @param imgPath 버튼 아이콘의 절대 경로 지정 
     * @param nTotalPage 전체 페이지 수 
     * @param nCurrentPage 현재 페이지 
     * @param nPagePerBlock 페이지 블럭당 페이지 링크 출력 수 
     * @param inUrl 실제 링크가 걸릴 주소(또는 스크립트 함수명)
     * @param nFunc inUrl 인수가 함수명인지 주소인지 지정 (true : 함수, false : 주소) 
     * @param pageStr 페이지 변수 선언 (defautl : pageIndex) 
     * @return 페이지 링크 html 
     */
    public static String getPagingStr(String imgPath, int nTotalPage, 
    		int nCurrentPage, int nPagePerBlock, String inUrl, boolean nFunc, String pageStr)
	{
		/*if (logger.isDebugEnabled())
			logger.debug("getPagingStr(int, int, int, String) - in");*/

		// 리턴할 문자열 사용변수
		StringBuffer sb = new StringBuffer("");
		sb.append("<div align=\"center\" style=\"padding-top:5\">\n");
		sb.append("  <table width=\"100%\" cellpadding=\"0\" class=\"table_foot_title\" ").
			append("cellspacing=\"0\" border=\"0\">\n");
		sb.append("  <tr class=\"TdBG_gray1_center\">\n");
		sb.append("    <td align=\"center\">\n");

		int nPage = 0;
		int nInc = 1;
		String url = inUrl;
		String pageParam = "";
		String tailFunc = "";

		if ( nFunc ) {
			url = "javascript:" + inUrl;
			pageParam = "(";
			tailFunc = ");";
		} else {
			pageParam = (url.indexOf("?") == -1 ? "?" : "&") + 
				( pageStr.equals("") ? "pageIndex" : pageStr ) + "=";
		}
		if( nPagePerBlock == 0 ) nPagePerBlock = 10;

		nPage = (int)((nCurrentPage-1)/nPagePerBlock) * nPagePerBlock + 1;

		int nCurrentBlock = nPage;
		int nLastBlock = (int)((nTotalPage-1)/nPagePerBlock) * nPagePerBlock + 1;

		//	이전 pagePerBlock개
		if( nCurrentPage > nPagePerBlock )
			sb.append("    &nbsp;<a href=\"").
				append( url ).
				append( pageParam ).
				append( nPage-1 ).
				append( tailFunc ).
				append("\" class=pag ><span id=\"leftIcon\"><img src=\"").
				append( imgPath ).
				append("/mydongwon/page_arrow_left.gif\" align=\"top\" ").
				append("onError=\"this.style.display='none';this.parentElement.outerText='☜';\" />").
				append("</span></a>&nbsp;\n");
		else
			;//tempStr.append("\n<img src=\"/images/butt_prev.gif\" align=\"top\" /> 이전 ");

		if( nCurrentPage > nPagePerBlock )
		    sb.append("    &nbsp;<a href=\"").
				append( url ).
				append( pageParam ).append("1").
				append( tailFunc ).append("\" class=\"page\" >1</a>..&nbsp;\n");

		//	중간 바로 가는 페이지 출력
		while( nInc++ <= nPagePerBlock  && nPage <= nTotalPage){

			if( nPage == nCurrentPage)
				sb.append("    &nbsp;<strong>[").
					append( nPage ).append("]</strong>&nbsp;\n");
			else
				sb.append("    &nbsp;<a class=\"page\" href=\"").
					append( url ).
					append( pageParam ).
					append( nPage ).
					append( tailFunc ).append("\" >[").
					append( nPage ).append("]</a>&nbsp;\n");

			nPage++;
		}

		if( nCurrentBlock < nLastBlock )
		    sb.append("    &nbsp;..<a href=\"").
				append( url ).
				append( pageParam ).
				append( nTotalPage ).
				append( tailFunc ).append("\" class=\"page\" >").
				append( nTotalPage ).append("</a>&nbsp;\n");

		//	다음 pagePerBlock개
		if( nCurrentBlock < nLastBlock )
			sb.append("    &nbsp;<a href=").
				append( url ).
				append( pageParam ).
				append( nPage ).
				append( tailFunc ).append( " class=pag ><span id=\"rightIcon\"><img src=\"").
				append( imgPath ).append("/mydongwon/page_arrow_right.gif\" align=\"top\" ").
				append("onError=\"this.style.display='none';this.parentElement.outerText='☞';\" />").
				append("</span></a>&nbsp;\n");
		else
			;//tempStr.append("\n 다음 <img src=\"/images/butt_next.gif\" align=\"top\" />");

		sb.append("    </td>\n");
		sb.append("  </tr>\n");
		sb.append("  </table>\n");
		sb.append("</div>\n");

		return sb.toString();
	}

    /**
     * 페이지 출력 
     * @location  : PageUtil.getPagingStr
     * @writeDay  : 2007. 8. 6 오후 5:01:54
     *
     * @param imgPath 버튼 아이콘의 절대 경로 지정 
     * @param nTotalRows 전체 항목 수 
     * @param currentPage 현재 페이지 
     * @param nRowOfPage 페이지당 출력 항목 수 
     * @param nPagePerBlock 페이지 블럭당 페이지 링크 출력 수 
     * @param url 실제 링크가 걸릴 주소(또는 스크립트 함수명)
     * @return 페이지 링크 html 
     */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, int nRowOfPage, int nPagePerBlock, String url){
		return getPagingStr(imgPath, nTotalRows, currentPage, nRowOfPage, nPagePerBlock, url, false, "");
	}

	/**
	 * 페이지 출력 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 오후 5:04:03
	 *
     * @param imgPath 버튼 아이콘의 절대 경로 지정 
     * @param nTotalRows 전체 항목 수 
     * @param currentPage 현재 페이지 
     * @param nRowOfPage 페이지당 출력 항목 수 
     * @param nPagePerBlock 페이지 블럭당 페이지 링크 출력 수 
     * @param url 실제 링크가 걸릴 주소(또는 스크립트 함수명)
     * @param pageStr 페이지 변수 선언 (defautl : pageIndex) 
     * @return 페이지 링크 html 
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, int nRowOfPage, int nPagePerBlock, String url, String pageStr){
		return getPagingStr(imgPath, nTotalRows, currentPage, nRowOfPage, nPagePerBlock, url, false, pageStr);
	}

	/**
	 * 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 오후 5:04:14
	 *
     * @param imgPath 버튼 아이콘의 절대 경로 지정 
     * @param nTotalRows 전체 항목 수 
     * @param currentPage 현재 페이지 
     * @param nRowOfPage 페이지당 출력 항목 수 
     * @param nPagePerBlock 페이지 블럭당 페이지 링크 출력 수 
     * @param url 실제 링크가 걸릴 주소(또는 스크립트 함수명)
     * @param nFunc inUrl 인수가 함수명인지 주소인지 지정 (true : 함수, false : 주소) 
     * @param pageStr 페이지 변수 선언 (defautl : pageIndex) 
	 * @return
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, int nRowOfPage, int nPagePerBlock, String url, boolean nFunc, String pageStr){
		double tmpPage = (double)nTotalRows / nRowOfPage;
		int nCpage = (currentPage == null || currentPage.trim().equals(""))? 1 : Integer.parseInt( currentPage );
		int nTotalPage = (int) Math.ceil( tmpPage );
		if( nTotalPage == 0 ) nTotalPage = 1;
		return getPagingStr(imgPath, nTotalPage, nCpage, nPagePerBlock, url, nFunc, pageStr );
	}

	/**
	 * 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 오후 5:04:17
	 *
     * @param imgPath 버튼 아이콘의 절대 경로 지정 
     * @param nTotalRows 전체 항목 수 
     * @param currentPage 현재 페이지 
     * @param url 실제 링크가 걸릴 주소(또는 스크립트 함수명)
     * @param nFunc inUrl 인수가 함수명인지 주소인지 지정 (true : 함수, false : 주소) 
	 * @return
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, String url, boolean nFunc) {
		return getPagingStr(imgPath, nTotalRows, currentPage, 10, 10, url, nFunc, "");
	}

	/**
	 * 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 오후 5:04:21
	 *
     * @param imgPath 버튼 아이콘의 절대 경로 지정 
     * @param nTotalRows 전체 항목 수 
     * @param currentPage 현재 페이지 
     * @param url 실제 링크가 걸릴 주소(또는 스크립트 함수명)
	 * @return
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, String url) {
		return getPagingStr(imgPath, nTotalRows, currentPage, 10, 10, url, false, "");
	}

	/**
	 * 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 오후 5:04:29
	 *
     * @param imgPath 버튼 아이콘의 절대 경로 지정 
     * @param nTotalRows 전체 항목 수 
     * @param currentPage 현재 페이지 
     * @param url 실제 링크가 걸릴 주소(또는 스크립트 함수명)
     * @param nFunc inUrl 인수가 함수명인지 주소인지 지정 (true : 함수, false : 주소) 
     * @param pageStr 페이지 변수 선언 (defautl : pageIndex) 
	 * @return
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, String url, boolean nFunc, String pageStr) {
		return getPagingStr(imgPath, nTotalRows, currentPage, 10, 10, url, nFunc, pageStr);
	}

	/**
	 * 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 오후 5:04:26
	 *
     * @param imgPath 버튼 아이콘의 절대 경로 지정 
     * @param nTotalRows 전체 항목 수 
     * @param currentPage 현재 페이지 
     * @param url 실제 링크가 걸릴 주소(또는 스크립트 함수명)
     * @param pageStr 페이지 변수 선언 (defautl : pageIndex) 
	 * @return
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, String url, String pageStr) {
		return getPagingStr(imgPath, nTotalRows, currentPage, 10, 10, url, false, pageStr);
	}

}