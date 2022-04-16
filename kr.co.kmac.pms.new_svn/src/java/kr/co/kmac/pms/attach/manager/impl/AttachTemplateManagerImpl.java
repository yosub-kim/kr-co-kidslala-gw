package kr.co.kmac.pms.attach.manager.impl;

import java.util.HashMap;
import java.util.Map;

import kr.co.kmac.pms.attach.exception.AttachException;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

public class AttachTemplateManagerImpl implements AttachTemplateManager {
	private static final Log logger = LogFactory.getLog(AttachTemplateManager.class);

	@Override
	public ValueList selectOutputTypeCodeList(WebApplicationContext wc, String projectCode, String businessTypeCode) throws AttachException {
		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		filters.put(ValueListInfo.PAGING_PAGE, "1");
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));

		if (projectCode != null && !projectCode.equals(""))
			filters.put("projectCode", projectCode);
		if (businessTypeCode != null && !businessTypeCode.equals(""))
			filters.put("businessTypeCode", businessTypeCode);

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("attachDataSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("key_1", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			valueList = vlh.getValueList("attachOutputTypeCodeSelect", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;
	}
	
	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.attach.manager.AttachTemplateManager#selectOutputBusTypeCodeList(org.springframework.web.context.WebApplicationContext, java.lang.String)
	 */
	@Override
	public ValueList selectOutputBusTypeCodeList(WebApplicationContext wc, String busTypeCode) throws AttachException {
		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		filters.put(ValueListInfo.PAGING_PAGE, "1");
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));

		if (busTypeCode != null && !busTypeCode.equals(""))
			filters.put("busTypeCode", busTypeCode);

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("attachDataSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("key_1", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			valueList = vlh.getValueList("attachOutputBusTypeCodeSelect", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;	}



	public ValueList selectListForTask(WebApplicationContext wc, String taskFormTypeId, String pg, String pageSize) throws AttachException {
		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		if (pg != null && !pg.equals(""))
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		if (pageSize != null && !pageSize.equals(""))
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
		if (taskFormTypeId != null && !taskFormTypeId.equals(""))
			filters.put("taskFormTypeId", taskFormTypeId);
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("attachDataSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			valueList = vlh.getValueList("outputTemplateDetailSelectListForTaskEntry", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;
	}
}
