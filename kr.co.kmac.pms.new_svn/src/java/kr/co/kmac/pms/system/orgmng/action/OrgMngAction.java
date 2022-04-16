package kr.co.kmac.pms.system.orgmng.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.OrgUnit;
import kr.co.kmac.pms.common.org.manager.IEntityManager;
import kr.co.kmac.pms.common.org.manager.IGroupManager;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.common.org.util.GroupUtils;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.data.ExpertPoolCareerHst;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSchoolHst;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="responseForm" path="/action/OrgMngAction" parameter="mode" scope="request"
 * @struts.action-forward name="orgMngPage" path="/system/orgmng/orgMng.jsp" redirect="false"
 */

public class OrgMngAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(OrgMngAction.class);
	private ExpertPoolManager expertPoolManager;
	private IOrgUnitManager orgUnitManager;
	private IGroupManager groupManager;
	private IEntityManager entityManager;
	private GroupUtils groupUtils;

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public IOrgUnitManager getOrgUnitManager() {
		return orgUnitManager;
	}

	public void setOrgUnitManager(IOrgUnitManager orgUnitManager) {
		this.orgUnitManager = orgUnitManager;
	}

	public IGroupManager getGroupManager() {
		return groupManager;
	}

	public void setGroupManager(IGroupManager groupManager) {
		this.groupManager = groupManager;
	}

	public IEntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(IEntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ActionForward orgMngPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("orgMngPage");
	}

	public void loadGroupInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		String id = request.getParameter("id");
		try {

			IGroup iGroup = this.getEntityManager().retrieveGroup(id);
			IGroup pGroup = (IGroup) new Group("", "");
			if (iGroup.getParentId() != null) {
				pGroup = this.getEntityManager().retrieveGroup(iGroup.getParentId());
			}

			//JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "grop Data Load 성공");

			map.put("id", iGroup.getId());
			map.put("name", iGroup.getName());
			map.put("description", iGroup.getDescription());
			map.put("parentId", pGroup.getId());
			map.put("parentName", pGroup.getName());
			map.put("path", iGroup.getPath());
			map.put("depth", iGroup.getDepth());
			map.put("seq", iGroup.getOrderSeq());
			map.put("enable", iGroup.isEnabled());

			AjaxUtil.successWrite(response, map);
		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "grop Data Load 실패");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	@SuppressWarnings("static-access")
	public void saveGroupInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();

		try {
			String saveMode = request.getParameter("saveMode");
			String parentId = request.getParameter("parentId");
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			// String groupType = request.getParameter("groupType");
			String description = request.getParameter("description");
			//String path = request.getParameter("path");
			//String depth = request.getParameter("depth");
			String seq = request.getParameter("seq");
			String enable = request.getParameter("enable");
			boolean enabled = (enable.equals("1")) ? true : false;
			int groupType = 2000;

			if (saveMode.equals("INSERT")) {
				IGroup iGroup = groupUtils.createGroup(id, name, groupType);
				iGroup.setDescription(description);
				iGroup.setEnabled(enabled);
				iGroup.setOrderSeq(seq);
				iGroup.setParentId(parentId);

				IGroup pGroup = this.getEntityManager().retrieveGroup(parentId);

				this.getEntityManager().store(iGroup);
				this.getOrgUnitManager().addChild((OrgUnit) pGroup, (OrgUnit) iGroup);

			} else if (saveMode.equals("UPDATE")) {
				IGroup iGroup = this.getEntityManager().retrieveGroup(id);

				iGroup.setName(name);
				iGroup.setDescription(description);
				iGroup.setEnabled(enabled);
				iGroup.setOrderSeq(seq);
				iGroup.setParentId(parentId);

				IGroup pGroup = this.getEntityManager().retrieveGroup(parentId);

				this.getEntityManager().store(iGroup);
				this.getOrgUnitManager().addChild((OrgUnit) pGroup, (OrgUnit) iGroup);
			}
			map.put("resultMsg", "Group추가 성공");

			AjaxUtil.successWrite(response, map);
		} catch (CodeException e) {
			map.put("resultMsg", "Group추가 실패");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void saveUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		String saveMode = request.getParameter("saveMode");

		String uid = request.getParameter("uid");
		String ssn = request.getParameter("ssn");
		String name = request.getParameter("name");
		String chnName = StringUtil.isNull(request.getParameter("chnName"), "");
		String engName = StringUtil.isNull(request.getParameter("engName"), "");
		String identify = StringUtil.isNull(request.getParameter("identify"), "");
		String passWord = StringUtil.isNull(request.getParameter("password"), "");
		if (passWord.equals(""))
			passWord = request.getParameter("prepassword");
		String enable = request.getParameter("enable");
		String gender = request.getParameter("gender");
		String nationality = request.getParameter("nationality");
		String telNo = request.getParameter("telNo");
		String mobileNo = request.getParameter("mobileNo");
		String zipCode = request.getParameter("zipCode");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String companyId = request.getParameter("companyId");
		String company = request.getParameter("company");
		String deptCode = request.getParameter("deptCode");
		String deptName = StringUtil.replace(request.getParameter("deptName"), " ", "");	// 부서명 공백 제거
		String companyPosition = request.getParameter("companyPosition");
		String companyPositionName = request.getParameter("companyPositionName");
		String jobClass = request.getParameter("jobClass");
		String companyZipcode = request.getParameter("companyZipcode");
		String companyAddress1 = request.getParameter("companyAddress1");
		String companyAddress2 = request.getParameter("companyAddress2");
		String companyTelNo = request.getParameter("companyTelNo");
		String companyFaxNo = request.getParameter("companyFaxNo");
		String email = request.getParameter("email");
		String rate = request.getParameter("rate");
		String rank = ServletRequestUtils.getStringParameter(request, "rank", "");
		String role = request.getParameter("menuCode");
		String remark = request.getParameter("remark");
		String emplNo = request.getParameter("emplNo");
		String acctBeginDate = request.getParameter("acctBeginDate");
		String acctExpireDate = request.getParameter("acctExpireDate");
		String blockDownload = request.getParameter("blockDownload");
		String blockLogin = request.getParameter("blockLogin");

		logger.debug("saveMode : " + saveMode);
		logger.debug("uid : " + uid);

		ExpertPool expertPool = new ExpertPool();
		try {
			if (saveMode.equals("INSERT")) {
				expertPool.setUid(uid);
				expertPool.setName(name);
				expertPool.setChnName(chnName);
				expertPool.setEngName(engName);
				expertPool.setUserId(identify);
				expertPool.setPassword(passWord);
				expertPool.setEnable(enable);
				expertPool.setGender(gender);
				expertPool.setNationality(nationality);
				expertPool.setTelNo(telNo);
				expertPool.setMobileNo(mobileNo);
				expertPool.setZipCode(zipCode);
				expertPool.setAddress1(address1);
				expertPool.setAddress2(address2);
				expertPool.setCompanyId(companyId);
				expertPool.setCompany(company);
				expertPool.setDept(deptCode);
				expertPool.setDeptName(deptName);
				expertPool.setCompanyPosition(companyPosition);
				expertPool.setCompanyPositionName(companyPositionName);
				expertPool.setJobClass(jobClass);
				expertPool.setCompanyZipcode(companyZipcode);
				expertPool.setCompanyAddress1(companyAddress1);
				expertPool.setCompanyAddress2(companyAddress2);
				expertPool.setCompanyTelNo(companyTelNo);
				expertPool.setCompanyFaxNo(companyFaxNo);
				expertPool.setEmail(email);
				expertPool.setCreaterId(SessionUtils.getUsername(request));
				expertPool.setRate(rate);
				expertPool.setRank(rank);
								
				expertPool.setRole(role);
				expertPool.setExtRole(role);
				expertPool.setEmplNo(emplNo);
				expertPool.setRemark(remark);
				expertPool.setAcctBeginDate(acctBeginDate);
				expertPool.setAcctExpireDate(acctExpireDate);
				expertPool.setBlockDownload(blockDownload);
				expertPool.setAccountNonLocked(blockLogin);
				
				
				String photoId = ServletRequestUtils.getStringParameter(request, "photoId", "");
				if(!photoId.equals(""))
					expertPool.setPhoto(photoId);

				expertPool.setExpertPoolCareerHst(new ArrayList<ExpertPoolCareerHst>());
				expertPool.setExpertPoolSchoolHst(new ArrayList<ExpertPoolSchoolHst>());

				// expertPool.setmsetModifiedDate(modifiedDate);
				// expertPool.setModifierId(modifierId());

				expertPool.setSsn(getExpertPoolManager().getSsn(jobClass));
				
				getExpertPoolManager().create(expertPool);

				if ((expertPool.getJobClass().equals("A") || expertPool.getJobClass().equals("J") || expertPool.getJobClass().equals("H")) && !expertPool.getRate().equals("1")
						&& !expertPool.getRate().equals("") || (expertPool.getJobClass().equals("C") && expertPool.getEnable().equals("1")) || expertPool.getJobClass().equals("B") || expertPool.getJobClass().equals("N")){
					getExpertPoolManager().createHistory(expertPool);
					getExpertPoolManager().createAccountHistory("C", expertPool);
					if (!expertPool.getJobClass().equals("C")){
						getExpertPoolManager().createSanctionLine(expertPool);
					}
				}

			} else if (saveMode.equals("UPDATE")) {
				ExpertPool expertPoolRef = new ExpertPool();
				expertPool = expertPoolManager.retrieve(ssn);
				expertPoolRef = expertPoolManager.retrieve(ssn);
				expertPool.setName(name);
				expertPool.setChnName(chnName);
				expertPool.setEngName(engName);
				expertPool.setUserId(identify);
				expertPool.setPassword(passWord);
				expertPool.setEnable(enable);
				expertPool.setGender(gender);
				expertPool.setNationality(nationality);
				expertPool.setTelNo(telNo);
				expertPool.setMobileNo(mobileNo);
				expertPool.setZipCode(zipCode);
				expertPool.setAddress1(address1);
				expertPool.setAddress2(address2);
				expertPool.setCompany(company);
				expertPool.setCompanyId(companyId);
				expertPool.setDept(deptCode);
				expertPool.setDeptName(deptName);

				expertPool.setCompanyPosition(companyPosition);
				expertPool.setCompanyPositionName(companyPositionName);

				expertPool.setJobClass(jobClass);
				expertPool.setCompanyZipcode(companyZipcode);
				expertPool.setCompanyAddress1(companyAddress1);
				expertPool.setCompanyAddress2(companyAddress2);
				expertPool.setCompanyTelNo(companyTelNo);
				expertPool.setCompanyFaxNo(companyFaxNo);
				expertPool.setEmail(email);
				
				String photoId = ServletRequestUtils.getStringParameter(request, "photoId", "");
				if(!photoId.equals(""))
					expertPool.setPhoto(photoId);
				
				expertPool.setModifierId(SessionUtils.getUsername(request));
				expertPool.setRate(rate);
				expertPool.setRank(rank);
				
				expertPool.setRole(role);
				expertPool.setExtRole(role);
				expertPool.setEmplNo(emplNo);
				expertPool.setRemark(remark);
				expertPool.setAcctBeginDate(acctBeginDate);
				expertPool.setAcctExpireDate(acctExpireDate);
				expertPool.setBlockDownload(blockDownload);
				expertPool.setAccountNonLocked(blockLogin);

				getExpertPoolManager().store(expertPool);
				// 계정잠금이 아니오 일 경우 잠김을 리셋한다.
				if (blockLogin.equals("1")) 
					getExpertPoolManager().accountReset(expertPool.getSsn());
				// 계정잠금이 예 일 경우 잠김을 설정한다.
				if (blockLogin.equals("0")) 
					getExpertPoolManager().accountLocked(expertPool.getSsn());

				if ((expertPool.getJobClass().equals("A") || expertPool.getJobClass().equals("J") || expertPool.getJobClass().equals("H")) 
						&& !expertPool.getRate().equals("1") 
						&& !expertPool.getRate().equals("") 
						|| (expertPool.getJobClass().equals("C") && expertPool.getEnable().equals("1"))){
					getExpertPoolManager().storeHistory(expertPool);
					if (!expertPool.getJobClass().equals("C"))
						getExpertPoolManager().storeSanctionLine(expertPool);
				}
				
				// 퇴사처리 인 경우의 메뉴 접근 권한 처리
				if (expertPoolRef.getEnable().equals("1") && enable.equals("0")) {
					getExpertPoolManager().createAccountHistory("D", expertPoolRef);
				}
				// 직군변경 또는 승진인 경우의 메뉴 접근 권한 처리
				if ((expertPool.getJobClass().equals("A") || expertPool.getJobClass().equals("J") || expertPool.getJobClass().equals("H")) 
						&& !expertPool.getRate().equals("1") 
						&& !expertPool.getRate().equals("") 
						|| (expertPool.getJobClass().equals("C") && expertPool.getEnable().equals("1"))){
					if (!expertPool.getJobClass().equals(expertPoolRef.getJobClass()) 
							|| !expertPool.getCompanyPosition().equals(expertPoolRef.getCompanyPosition())
							|| !expertPool.getRole().equals(expertPoolRef.getRole())) {
						getExpertPoolManager().createAccountHistory("U", expertPool);
					}
				}
				
				// JobDate: 2018-08-15	Author: yhyim	Description: 비활성화
				/*
				if (expertPool.getJobClass().equals("A")) {
					getExpertPoolManager().updateEduSystemAccount(expertPool);
				}
				*/
			}
			//JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "전문가 정보가 입력되었습니다.");
			AjaxUtil.successWrite(response, map);
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("result", false);
			map.put("resultMsg", "전문가 정보 입력 중 오류가 발생했습니다.");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}
}