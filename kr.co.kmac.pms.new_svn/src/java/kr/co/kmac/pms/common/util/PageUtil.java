package kr.co.kmac.pms.common.util;
/*-------------------------------------------------------------------
 * ������Ʈ		: PMS ������
 * ���α׷���	: PageUtil.java
 * ����			: ������ HTML ���� Ŭ����
 * Version		: 1.0
 * �ۼ�����		: 2009/03/06
 * �ۼ���		: �����
 *-------------[���� �α�]-------------------------------------------
 * ��������		:
 * ��������		:
 --------------------------------------------------------------------*/
import jxl.common.Logger;

public final class PageUtil {

   private static final Logger logger = Logger.getLogger(PageUtil.class);
	//private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private PageUtil() {
        super();
    }

    /**
     * ������ ��� 
     * @location  : PageUtil.getPagingStr
     * @writeDay  : 2007. 8. 6 ���� 4:58:24
     *
     * @param imgPath ��ư �������� ���� ��� ���� 
     * @param nTotalPage ��ü ������ �� 
     * @param nCurrentPage ���� ������ 
     * @param nPagePerBlock ������ ���� ������ ��ũ ��� �� 
     * @param inUrl ���� ��ũ�� �ɸ� �ּ�(�Ǵ� ��ũ��Ʈ �Լ���)
     * @param nFunc inUrl �μ��� �Լ������� �ּ����� ���� (true : �Լ�, false : �ּ�) 
     * @param pageStr ������ ���� ���� (defautl : pageIndex) 
     * @return ������ ��ũ html 
     */
    public static String getPagingStr(String imgPath, int nTotalPage, 
    		int nCurrentPage, int nPagePerBlock, String inUrl, boolean nFunc, String pageStr)
	{
		/*if (logger.isDebugEnabled())
			logger.debug("getPagingStr(int, int, int, String) - in");*/

		// ������ ���ڿ� ��뺯��
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

		//	���� pagePerBlock��
		if( nCurrentPage > nPagePerBlock )
			sb.append("    &nbsp;<a href=\"").
				append( url ).
				append( pageParam ).
				append( nPage-1 ).
				append( tailFunc ).
				append("\" class=pag ><span id=\"leftIcon\"><img src=\"").
				append( imgPath ).
				append("/mydongwon/page_arrow_left.gif\" align=\"top\" ").
				append("onError=\"this.style.display='none';this.parentElement.outerText='��';\" />").
				append("</span></a>&nbsp;\n");
		else
			;//tempStr.append("\n<img src=\"/images/butt_prev.gif\" align=\"top\" /> ���� ");

		if( nCurrentPage > nPagePerBlock )
		    sb.append("    &nbsp;<a href=\"").
				append( url ).
				append( pageParam ).append("1").
				append( tailFunc ).append("\" class=\"page\" >1</a>..&nbsp;\n");

		//	�߰� �ٷ� ���� ������ ���
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

		//	���� pagePerBlock��
		if( nCurrentBlock < nLastBlock )
			sb.append("    &nbsp;<a href=").
				append( url ).
				append( pageParam ).
				append( nPage ).
				append( tailFunc ).append( " class=pag ><span id=\"rightIcon\"><img src=\"").
				append( imgPath ).append("/mydongwon/page_arrow_right.gif\" align=\"top\" ").
				append("onError=\"this.style.display='none';this.parentElement.outerText='��';\" />").
				append("</span></a>&nbsp;\n");
		else
			;//tempStr.append("\n ���� <img src=\"/images/butt_next.gif\" align=\"top\" />");

		sb.append("    </td>\n");
		sb.append("  </tr>\n");
		sb.append("  </table>\n");
		sb.append("</div>\n");

		return sb.toString();
	}

    /**
     * ������ ��� 
     * @location  : PageUtil.getPagingStr
     * @writeDay  : 2007. 8. 6 ���� 5:01:54
     *
     * @param imgPath ��ư �������� ���� ��� ���� 
     * @param nTotalRows ��ü �׸� �� 
     * @param currentPage ���� ������ 
     * @param nRowOfPage �������� ��� �׸� �� 
     * @param nPagePerBlock ������ ���� ������ ��ũ ��� �� 
     * @param url ���� ��ũ�� �ɸ� �ּ�(�Ǵ� ��ũ��Ʈ �Լ���)
     * @return ������ ��ũ html 
     */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, int nRowOfPage, int nPagePerBlock, String url){
		return getPagingStr(imgPath, nTotalRows, currentPage, nRowOfPage, nPagePerBlock, url, false, "");
	}

	/**
	 * ������ ��� 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 ���� 5:04:03
	 *
     * @param imgPath ��ư �������� ���� ��� ���� 
     * @param nTotalRows ��ü �׸� �� 
     * @param currentPage ���� ������ 
     * @param nRowOfPage �������� ��� �׸� �� 
     * @param nPagePerBlock ������ ���� ������ ��ũ ��� �� 
     * @param url ���� ��ũ�� �ɸ� �ּ�(�Ǵ� ��ũ��Ʈ �Լ���)
     * @param pageStr ������ ���� ���� (defautl : pageIndex) 
     * @return ������ ��ũ html 
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, int nRowOfPage, int nPagePerBlock, String url, String pageStr){
		return getPagingStr(imgPath, nTotalRows, currentPage, nRowOfPage, nPagePerBlock, url, false, pageStr);
	}

	/**
	 * 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 ���� 5:04:14
	 *
     * @param imgPath ��ư �������� ���� ��� ���� 
     * @param nTotalRows ��ü �׸� �� 
     * @param currentPage ���� ������ 
     * @param nRowOfPage �������� ��� �׸� �� 
     * @param nPagePerBlock ������ ���� ������ ��ũ ��� �� 
     * @param url ���� ��ũ�� �ɸ� �ּ�(�Ǵ� ��ũ��Ʈ �Լ���)
     * @param nFunc inUrl �μ��� �Լ������� �ּ����� ���� (true : �Լ�, false : �ּ�) 
     * @param pageStr ������ ���� ���� (defautl : pageIndex) 
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
	 * @writeDay  : 2007. 8. 6 ���� 5:04:17
	 *
     * @param imgPath ��ư �������� ���� ��� ���� 
     * @param nTotalRows ��ü �׸� �� 
     * @param currentPage ���� ������ 
     * @param url ���� ��ũ�� �ɸ� �ּ�(�Ǵ� ��ũ��Ʈ �Լ���)
     * @param nFunc inUrl �μ��� �Լ������� �ּ����� ���� (true : �Լ�, false : �ּ�) 
	 * @return
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, String url, boolean nFunc) {
		return getPagingStr(imgPath, nTotalRows, currentPage, 10, 10, url, nFunc, "");
	}

	/**
	 * 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 ���� 5:04:21
	 *
     * @param imgPath ��ư �������� ���� ��� ���� 
     * @param nTotalRows ��ü �׸� �� 
     * @param currentPage ���� ������ 
     * @param url ���� ��ũ�� �ɸ� �ּ�(�Ǵ� ��ũ��Ʈ �Լ���)
	 * @return
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, String url) {
		return getPagingStr(imgPath, nTotalRows, currentPage, 10, 10, url, false, "");
	}

	/**
	 * 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 ���� 5:04:29
	 *
     * @param imgPath ��ư �������� ���� ��� ���� 
     * @param nTotalRows ��ü �׸� �� 
     * @param currentPage ���� ������ 
     * @param url ���� ��ũ�� �ɸ� �ּ�(�Ǵ� ��ũ��Ʈ �Լ���)
     * @param nFunc inUrl �μ��� �Լ������� �ּ����� ���� (true : �Լ�, false : �ּ�) 
     * @param pageStr ������ ���� ���� (defautl : pageIndex) 
	 * @return
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, String url, boolean nFunc, String pageStr) {
		return getPagingStr(imgPath, nTotalRows, currentPage, 10, 10, url, nFunc, pageStr);
	}

	/**
	 * 
	 * @location  : PageUtil.getPagingStr
	 * @writeDay  : 2007. 8. 6 ���� 5:04:26
	 *
     * @param imgPath ��ư �������� ���� ��� ���� 
     * @param nTotalRows ��ü �׸� �� 
     * @param currentPage ���� ������ 
     * @param url ���� ��ũ�� �ɸ� �ּ�(�Ǵ� ��ũ��Ʈ �Լ���)
     * @param pageStr ������ ���� ���� (defautl : pageIndex) 
	 * @return
	 */
	public static String getPagingStr(String imgPath, int nTotalRows, 
			String currentPage, String url, String pageStr) {
		return getPagingStr(imgPath, nTotalRows, currentPage, 10, 10, url, false, pageStr);
	}

}