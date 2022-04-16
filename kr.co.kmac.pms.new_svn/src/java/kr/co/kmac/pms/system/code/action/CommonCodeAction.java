package kr.co.kmac.pms.system.code.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.code.data.CodeDefinition;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.system.code.data.CommonCodeArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="responseForm" path="/action/CommonCodeAction" parameter="mode" scope="request"
 * @struts.action-forward name="codeCMTable" path="/system/code/codeCMTableList.jsp" redirect="false"
 * @struts.action-forward name="codeDefineForm" path="/system/code/codeDefineForm.jsp" redirect="false"
 * @struts.action-forward name="codeDataForm" path="/system/code/codeDataForm.jsp" redirect="false"
 * @struts.action-forward name="testUploadForm" path="/test/uploadForm.jsp" redirect="false"
 */

public class CommonCodeAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(CommonCodeAction.class);
	private CommonCodeManager commonCodeManager;

	/**
	 * @return the commonCodeManager
	 */
	public CommonCodeManager getCommonCodeManager() {
		return commonCodeManager;
	}

	/**
	 * @param commonCodeManager the commonCodeManager to set
	 */
	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}

	public ActionForward codeCMTable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<CodeDefinition> cmTableList = commonCodeManager.getCodeDefinition();
		List cmTableDataList = null;
		if (cmTableList.size() > 0) {
			cmTableDataList = commonCodeManager.getCodeEntityList(cmTableList.get(0).getTableName());
		}

		request.setAttribute("cmTableList", cmTableList);
		request.setAttribute("cmTableDataList", cmTableDataList);

		return mapping.findForward("codeCMTable");
	}
	
	public ActionForward codeCMTable_eun(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<CodeDefinition> cmTableList = commonCodeManager.getCodeDefinition_eun();
		List cmTableDataList = null;
		if (cmTableList.size() > 0) {
			cmTableDataList = commonCodeManager.getCodeEntityList(cmTableList.get(0).getTableName());
		}

		request.setAttribute("cmTableList", cmTableList);
		request.setAttribute("cmTableDataList", cmTableDataList);

		return mapping.findForward("codeCMTable_eun");
	}
	

	public ActionForward inputDefineForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String tableName = request.getParameter("tableName");

		CodeDefinition codeDef = commonCodeManager.getCodeDefinition(tableName);
		if (tableName.equals("")) {
			request.setAttribute("title", "기준코드 정의 입력");
			request.setAttribute("saveMode", "INSERT");
		} else {
			request.setAttribute("title", "기준코드 정의 수정");
			request.setAttribute("saveMode", "UPDATE");
		}

		request.setAttribute("codeDefine", codeDef);
		return mapping.findForward("codeDefineForm");
	}

	public ActionForward inputDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String tableName = request.getParameter("tableName");
		String key1 = request.getParameter("key1");
		String key2 = request.getParameter("key2");
		String key3 = request.getParameter("key3");

		CodeDefinition codeDef = commonCodeManager.getCodeDefinition(tableName);
		CodeEntity codeEnt = commonCodeManager.getCodeEntity(tableName, key1);
		List<CommonCodeArray> commonCodeArr = new ArrayList<CommonCodeArray>();
		// commonCodeArr.
		logger.debug("결과 : " + codeDef.getKey1Label());
		commonCodeArr.add(new CommonCodeArray(codeDef.getKey1Label(), "key1", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getKey2Label(), "key2", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getKey3Label(), "key3", "", "none"));

		commonCodeArr.add(new CommonCodeArray(codeDef.getData1Label(), "data1", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getData2Label(), "data2", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getData3Label(), "data3", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getData4Label(), "data4", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getData5Label(), "data5", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getData6Label(), "data6", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getData7Label(), "data7", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getData8Label(), "data8", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getData9Label(), "data9", "", "none"));
		commonCodeArr.add(new CommonCodeArray(codeDef.getData10Label(), "data10", "", "none"));

		commonCodeArr.add(new CommonCodeArray("정렬순서", "orderInfo", "", "inline"));

		if (codeEnt != null) {
			commonCodeArr.get(0).setCodeValue(codeEnt.getKey1());
			commonCodeArr.get(1).setCodeValue(codeEnt.getKey2());
			commonCodeArr.get(2).setCodeValue(codeEnt.getKey3());

			commonCodeArr.get(3).setCodeValue(codeEnt.getData1());
			commonCodeArr.get(4).setCodeValue(codeEnt.getData2());
			commonCodeArr.get(5).setCodeValue(codeEnt.getData3());
			commonCodeArr.get(6).setCodeValue(codeEnt.getData4());
			commonCodeArr.get(7).setCodeValue(codeEnt.getData5());
			commonCodeArr.get(8).setCodeValue(codeEnt.getData6());
			commonCodeArr.get(9).setCodeValue(codeEnt.getData7());
			commonCodeArr.get(10).setCodeValue(codeEnt.getData8());
			commonCodeArr.get(11).setCodeValue(codeEnt.getData9());
			commonCodeArr.get(12).setCodeValue(codeEnt.getData10());

			commonCodeArr.get(13).setCodeValue(codeEnt.getOrderInfo());

			request.setAttribute("title", "기준정보 수정");
			request.setAttribute("saveMode", "UPDATE");
			request.setAttribute("saveKey", key1);
		} else {
			request.setAttribute("title", "기준정보 입력");
			request.setAttribute("saveMode", "INSERT");
			request.setAttribute("saveKey", "");
		}

		request.setAttribute("commonCodeArr", commonCodeArr);
		request.setAttribute("tableName", tableName);

		return mapping.findForward("codeDataForm");
	}

	public void codeDataRetrieve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap map = new HashMap();
		try {
			String tableName = request.getParameter("tableName");

			CodeDefinition codeDef = commonCodeManager.getCodeDefinition(tableName);
			List codeList = commonCodeManager.getCodeEntityList(tableName);

			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "전문가 정보가 입력되었습니다.");
			map.put("codeDef", codeDef);
			map.put("codeList", codeList);

			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "전문가 정보 입력 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void saveDefinition(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();
		try {
			String saveMode = request.getParameter("saveMode");
			String tableName = request.getParameter("tableName");
			String tableDesc = request.getParameter("tableDesc");

			String key1Label = request.getParameter("key1Label");
			String key2Label = request.getParameter("key2Label");
			String key3Label = request.getParameter("key3Label");

			String data1Label = request.getParameter("data1Label");
			String data2Label = request.getParameter("data2Label");
			String data3Label = request.getParameter("data3Label");
			String data4Label = request.getParameter("data4Label");
			String data5Label = request.getParameter("data5Label");

			String data6Label = request.getParameter("data6Label");
			String data7Label = request.getParameter("data7Label");
			String data8Label = request.getParameter("data8Label");
			String data9Label = request.getParameter("data9Label");
			String data10Label = request.getParameter("data10Label");

			CodeDefinition codeDefinition = null;
			if (saveMode.equals("UPDATE")) {
				codeDefinition = commonCodeManager.getCodeDefinition(tableName);
			} else if (saveMode.equals("INSERT")) {
				codeDefinition = new CodeDefinition();
			}
			codeDefinition.setTableName(tableName);
			codeDefinition.setTableDesc(tableDesc);

			codeDefinition.setKey1Label(key1Label);
			codeDefinition.setKey2Label(key2Label);
			codeDefinition.setKey3Label(key3Label);

			codeDefinition.setData1Label(data1Label);
			codeDefinition.setData2Label(data2Label);
			codeDefinition.setData3Label(data3Label);
			codeDefinition.setData4Label(data4Label);
			codeDefinition.setData5Label(data5Label);
			codeDefinition.setData6Label(data6Label);
			codeDefinition.setData7Label(data7Label);
			codeDefinition.setData8Label(data8Label);
			codeDefinition.setData9Label(data9Label);
			codeDefinition.setData10Label(data10Label);

			if (saveMode.equals("UPDATE")) {
				commonCodeManager.updateCodeDefinition(codeDefinition);
			} else if (saveMode.equals("INSERT")) {
				commonCodeManager.createCodeDefinition(codeDefinition);
			}

			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "저장완료");

			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "저장 중 오류!");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String tableName = request.getParameter("tableName");
			String saveKey = request.getParameter("saveKey");
			String saveMode = request.getParameter("saveMode");

			String key1 = ServletRequestUtils.getStringParameter(request, "key1", "$EMPTY");
			String key2 = ServletRequestUtils.getStringParameter(request, "key2", "$EMPTY");
			String key3 = ServletRequestUtils.getStringParameter(request, "key3", "$EMPTY");

			String data1 = request.getParameter("data1");
			String data2 = request.getParameter("data2");
			String data3 = request.getParameter("data3");
			String data4 = request.getParameter("data4");
			String data5 = request.getParameter("data5");
			String data6 = request.getParameter("data6");
			String data7 = request.getParameter("data7");
			String data8 = request.getParameter("data8");
			String data9 = request.getParameter("data9");
			String data10 = request.getParameter("data10");

			String orderInfo = request.getParameter("orderInfo");

			if (saveMode.equals("INSERT")) {
				CodeEntity codeEntity = new CodeEntity();
				codeEntity.setTableName(tableName);
				codeEntity.setOrderInfo(orderInfo);

				codeEntity.setKey1(key1);
				codeEntity.setKey2(key2);
				codeEntity.setKey3(key3);

				codeEntity.setData1(data1);
				codeEntity.setData2(data2);
				codeEntity.setData3(data3);
				codeEntity.setData4(data4);
				codeEntity.setData5(data5);
				codeEntity.setData6(data6);
				codeEntity.setData7(data7);
				codeEntity.setData8(data8);
				codeEntity.setData9(data9);
				codeEntity.setData10(data10);

				codeEntity.setCreateUserId(SessionUtils.getUserObjext().getId());

				commonCodeManager.createCodeEntity(codeEntity);
			} else {
				CodeEntity codeEntity = commonCodeManager.getCodeEntity(tableName, saveKey);

				codeEntity.setKey1(key1);
				codeEntity.setKey2(key2);
				codeEntity.setKey3(key3);

				codeEntity.setData1(data1);
				codeEntity.setData2(data2);
				codeEntity.setData3(data3);
				codeEntity.setData4(data4);
				codeEntity.setData5(data5);
				codeEntity.setData6(data6);
				codeEntity.setData7(data7);
				codeEntity.setData8(data8);
				codeEntity.setData9(data9);
				codeEntity.setData10(data10);

				codeEntity.setOrderInfo(orderInfo);
				codeEntity.setUpdateUserId(SessionUtils.getUserObjext().getId());

				commonCodeManager.updateCodeEntity(codeEntity.getTableName(), codeEntity.getKey1(), 
						codeEntity.getKey2(), codeEntity.getKey3(), codeEntity);
			}
			CodeDefinition codeDef = commonCodeManager.getCodeDefinition(tableName);
			List codeList = commonCodeManager.getCodeEntityList(tableName);

			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "저장완료");
			map.put("codeDef", codeDef);
			map.put("codeList", codeList);

			AjaxUtil.successWrite(response, map);

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "저장 중 오류!");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void deleteData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();
		try {
			String[] chkData = request.getParameterValues("chkData");

			for (int i = 0; i < chkData.length; i++) {
				String[] dataInfo = chkData[i].split("~~|~~");
				String tableName = dataInfo[0];
				String key1 = dataInfo[2];
				commonCodeManager.deleteCodeEntity(tableName, key1);
			}

			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "삭제완료");

			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "삭제 중 오류!");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void deleteDefine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();
		try {
			String[] chkDefine = request.getParameterValues("chkDefine");

			for (int i = 0; i < chkDefine.length; i++) {

				String tableName = chkDefine[i];

				commonCodeManager.deleteCodeDefinition(tableName);
			}

			JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "삭제완료");

			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "삭제 중 오류!");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/********************************
	 * 테스트 Upload
	 ********************************/
	public ActionForward testUploadForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("testUploadForm");
	}
}