package kr.co.kmac.pms.attach.manager;

import kr.co.kmac.pms.attach.exception.AttachException;
import net.mlw.vlh.ValueList;

import org.springframework.web.context.WebApplicationContext;

public interface AttachTemplateManager {

	public ValueList selectOutputTypeCodeList(WebApplicationContext wc, String projectCode, String businessTypeCode) throws AttachException;

	public ValueList selectOutputBusTypeCodeList(WebApplicationContext wc, String busTypeCode) throws AttachException;

	public ValueList selectListForTask(WebApplicationContext wc, String taskFormTypeId, String pg, String pageSize) throws AttachException;
}
