package kr.co.kmac.pms.project.search.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.repository.action.RepositoryDispatchActionSupport;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @struts.action name="searchAction" path="/action/SearchAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="searchResult" path="/project/search/searchResultList.jsp" redirect="false"
 */
public class SearchAction extends RepositoryDispatchActionSupport {

	private static final Log logger = LogFactory.getLog(SearchAction.class);

	public ActionForward serchRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String pagingPage = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String pagingNumberPer = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", null);
		
		String projectNameChk = ServletRequestUtils.getStringParameter(request, "projectNameChk", null);
		String projectCodeChk = ServletRequestUtils.getStringParameter(request, "projectCodeChk", null);
		String customerNameChk = ServletRequestUtils.getStringParameter(request, "customerNameChk", null);
		String attachFileNameChk = ServletRequestUtils.getStringParameter(request, "attachFileNameChk", null);
		String attachOutputNameChk = ServletRequestUtils.getStringParameter(request, "attachOutputNameChk", null);
		String hashTagChk = ServletRequestUtils.getStringParameter(request, "hashTagChk", null);
		String attachCreatorNameChk = ServletRequestUtils.getStringParameter(request, "attachCreatorNameChk", null);

		try {
			if(keyword == null ){
				request.setAttribute("list", null);
				request.setAttribute("keyword", keyword);
			} else {
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("searchDataSelectListBean", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();

				filters.put("keyword", "%" + keyword.toUpperCase() + "%");
				
				if(projectNameChk != null) filters.put("projectNameChk", projectNameChk);
				if(projectCodeChk != null) filters.put("projectCodeChk", projectCodeChk);
				if(customerNameChk != null) filters.put("customerNameChk", customerNameChk);
				if(attachFileNameChk != null) filters.put("attachFileNameChk", attachFileNameChk);
				if(attachOutputNameChk != null) filters.put("attachOutputNameChk", attachOutputNameChk);
				if(hashTagChk != null) filters.put("hashTagChk", hashTagChk);
				if(attachCreatorNameChk != null) filters.put("attachCreatorNameChk", attachCreatorNameChk);
				
				filters.put(ValueListInfo.PAGING_PAGE, pagingPage);
				filters.put(ValueListInfo.PAGING_NUMBER_PER, pagingNumberPer);

				info.setFilters(filters);

				ValueList valueList = vlh.getValueList("searchDataSelectListEntry", info);
				request.setAttribute("list", valueList);

				request.setAttribute("keyword", keyword);
				request.setAttribute("projectNameChk", projectNameChk);	
				request.setAttribute("projectCodeChk", projectCodeChk);	
				request.setAttribute("customerNameChk", customerNameChk);	
				request.setAttribute("attachFileNameChk", attachFileNameChk);	
				request.setAttribute("attachOutputNameChk", attachOutputNameChk);	
				request.setAttribute("hashTagChk", hashTagChk);	
				request.setAttribute("attachCreatorNameChk", attachCreatorNameChk);
			}			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("searchResult");
	}

	
	public void getFileDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fileId = ServletRequestUtils.getRequiredStringParameter(request, "fileId");

		try {
		
			URL naver = new URL("http://172.16.0.122:9200/kmac_index_2021/_doc/"+fileId);
            HttpURLConnection urlConn = (HttpURLConnection)naver.openConnection();
            urlConn.setDoOutput(true);
            urlConn.setRequestMethod("POST");
         
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String inputLine;
            while((inputLine = br.readLine()) != null){
                   System.out.println(inputLine);
            }
            br.close();
            
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
}
