package kr.co.kmac.pms.service.scheduler.manager.impl;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.service.exception.ServiceException;
import kr.co.kmac.pms.service.scheduler.dao.BizplayInfoSendDao;
import kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.BizplayDeptInfo;
import kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.BizplayDeptInfoSet;
import kr.co.kmac.pms.service.scheduler.data.bizplay.empl.BizplayExpertPoolInfo;
import kr.co.kmac.pms.service.scheduler.data.bizplay.empl.BizplayExpertPoolInfoSet;
import kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.BizplayProjectInfo;
import kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.BizplayProjectInfoSet;
import kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.REQ_DETAIL;
import kr.co.kmac.pms.service.scheduler.manager.BizplayInfoSendManager;

public class BizplayInfoSendManagerImpl implements BizplayInfoSendManager {

	private BizplayInfoSendDao bizplayInfoSendDao;

	// @Override
	// public List<BizplayProjectInfo> projectInfoList() throws ServiceException {
	//
	// return this.getBizplayInfoSendDao().projectInfoList();
	// }
	//
	// @Override
	// public List<BizplayProjectInfo> projectInfoList(String companyCode) throws ServiceException {
	// return this.getBizplayInfoSendDao().projectInfoList(companyCode);
	// }

	@Override
	public List<BizplayProjectInfoSet> projectInfoJsonObject(String companyCode) throws ServiceException {
		List<BizplayProjectInfoSet> bizplayProjectInfoSets = new ArrayList<BizplayProjectInfoSet>();
		List<BizplayProjectInfo> bizplayProjectInfoList = this.getBizplayInfoSendDao().projectInfoList(companyCode);

		if (bizplayProjectInfoList != null && bizplayProjectInfoList.size() > 0) {
			int listSize = bizplayProjectInfoList.size();
			int returnListSize = (listSize / 30) + 1;
			int lastPageSize = (listSize % 30);
			int index = 0;
			for (int i = 0; i < returnListSize; i++) {
				List<BizplayProjectInfo> list = new ArrayList<BizplayProjectInfo>();
				for (int j = 0; j < 30; j++) {
					list.add(bizplayProjectInfoList.get(index));
					index++;
					if ((i + 1) == returnListSize && (j + 1) == lastPageSize) {
						break;
					}
				}

				BizplayProjectInfoSet bizplayProjectInfoSet = convenrtProjectInfoToJson(list);
				bizplayProjectInfoSets.add(bizplayProjectInfoSet);
			}
		}

		return bizplayProjectInfoSets;
	}

	@Override
	public List<BizplayProjectInfoSet> projectInfoJsonObjectAll(String companyCode) throws ServiceException {
		List<BizplayProjectInfoSet> bizplayProjectInfoSets = new ArrayList<BizplayProjectInfoSet>();
		List<BizplayProjectInfo> bizplayProjectInfoList = this.getBizplayInfoSendDao().projectInfoListAll(companyCode);

		if (bizplayProjectInfoList != null && bizplayProjectInfoList.size() > 0) {
			int listSize = bizplayProjectInfoList.size();
			int returnListSize = (listSize / 30) + 1;
			int lastPageSize = (listSize % 30);
			int index = 0;
			for (int i = 0; i < returnListSize; i++) {
				List<BizplayProjectInfo> list = new ArrayList<BizplayProjectInfo>();
				for (int j = 0; j < 30; j++) {
					list.add(bizplayProjectInfoList.get(index));
					index++;
					if ((i + 1) == returnListSize && (j + 1) == lastPageSize) {
						break;
					}
				}

				BizplayProjectInfoSet bizplayProjectInfoSet = convenrtProjectInfoToJson(list);
				bizplayProjectInfoSets.add(bizplayProjectInfoSet);
			}
		}

		return bizplayProjectInfoSets;
	}

	// @Override
	// public List<BizplayExpertPoolInfo> expertPoolList() throws ServiceException {
	// return this.getBizplayInfoSendDao().expertPoolList();
	// }
	//
	// @Override
	// public List<BizplayExpertPoolInfo> expertPoolList(String companyCode) throws ServiceException {
	// return this.getBizplayInfoSendDao().expertPoolList(companyCode);
	// }

	@Override
	public List<BizplayExpertPoolInfoSet> expertPoolJsonObject(String companyCode) throws ServiceException {
		List<BizplayExpertPoolInfoSet> bizplayExpertPoolInfoSets = new ArrayList<BizplayExpertPoolInfoSet>();
		List<BizplayExpertPoolInfo> bizplayExpertPoolInfos = this.getBizplayInfoSendDao().expertPoolList(companyCode);

		if (bizplayExpertPoolInfos != null && bizplayExpertPoolInfos.size() > 0) {
			int listSize = bizplayExpertPoolInfos.size();
			int returnListSize = (listSize / 30) + 1;
			int lastPageSize = (listSize % 30);
			int index = 0;
			for (int i = 0; i < returnListSize; i++) {
				List<BizplayExpertPoolInfo> list = new ArrayList<BizplayExpertPoolInfo>();
				for (int j = 0; j < 30; j++) {
					list.add(bizplayExpertPoolInfos.get(index));
					index++;
					if ((i + 1) == returnListSize && (j + 1) == lastPageSize) {
						break;
					}
				}

				BizplayExpertPoolInfoSet bizplayProjectInfoSet = convenrtExpertPoolInfoToJson(list);
				bizplayExpertPoolInfoSets.add(bizplayProjectInfoSet);
			}
		}

		return bizplayExpertPoolInfoSets;
	}

	// @Override
	// public List<BizplayDeptInfo> deptInfoList() throws ServiceException {
	// return this.getBizplayInfoSendDao().deptList();
	// }
	//
	// @Override
	// public List<BizplayDeptInfo> deptInfoList(String companyCode) throws ServiceException {
	// return this.getBizplayInfoSendDao().deptList(companyCode);
	// }

	@Override
	public List<BizplayDeptInfoSet> deptInfoJsonObject(String companyCode) throws ServiceException {
		List<BizplayDeptInfoSet> bizplayExpertPoolInfoSets = new ArrayList<BizplayDeptInfoSet>();
		List<BizplayDeptInfo> bizplayDeptInfos = this.getBizplayInfoSendDao().deptList(companyCode);

		if (bizplayDeptInfos != null && bizplayDeptInfos.size() > 0) {
			int listSize = bizplayDeptInfos.size();
			int returnListSize = (listSize / 30) + 1;
			int lastPageSize = (listSize % 30);
			int index = 0;
			for (int i = 0; i < returnListSize; i++) {
				List<BizplayDeptInfo> list = new ArrayList<BizplayDeptInfo>();
				for (int j = 0; j < 30; j++) {
					list.add(bizplayDeptInfos.get(index));
					index++;
					if ((i + 1) == returnListSize && (j + 1) == lastPageSize) {
						break;
					}
				}

				BizplayDeptInfoSet bizplayProjectInfoSet = convenrtDeptInfoToJson(list);
				bizplayExpertPoolInfoSets.add(bizplayProjectInfoSet);
			}
		}

		return bizplayExpertPoolInfoSets;
	}

	public BizplayInfoSendDao getBizplayInfoSendDao() {
		return bizplayInfoSendDao;
	}

	public void setBizplayInfoSendDao(BizplayInfoSendDao bizplayInfoSendDao) {
		this.bizplayInfoSendDao = bizplayInfoSendDao;
	}

	private BizplayProjectInfoSet convenrtProjectInfoToJson(List<BizplayProjectInfo> list) {
		BizplayProjectInfoSet infoSet = new BizplayProjectInfoSet();
		kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.REQ_DATA req_DATA = new kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.REQ_DATA();
		// req_DATA.setWORK_TYPE("");
		req_DATA.setREQ_DETAIL(new ArrayList<kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.REQ_DETAIL>());
		if (list != null && list.size() > 0) {
			for (BizplayProjectInfo info : list) {
				REQ_DETAIL detail = new REQ_DETAIL();
				detail.setITEM_ERP_CD(info.getProjectCode());
				detail.setITEM_NM(info.getProjectName());
				detail.setITEM_MEMO("");
				detail.setACT_YN(info.getProjectState());
				detail.setITEM_GR_CD("2000");
				detail.setITEM_SUB_YN("");
				req_DATA.getREQ_DETAIL().add(detail);
				req_DATA.setBIZ_NO(info.getBsnnNo());
				infoSet.setORG_CD(info.getBsnnNo());
			}
		}
		infoSet.setREQ_DATA(req_DATA);

		return infoSet;
	}

	private BizplayExpertPoolInfoSet convenrtExpertPoolInfoToJson(List<BizplayExpertPoolInfo> list) {
		BizplayExpertPoolInfoSet infoSet = new BizplayExpertPoolInfoSet();
		kr.co.kmac.pms.service.scheduler.data.bizplay.empl.REQ_DATA req_DATA = new kr.co.kmac.pms.service.scheduler.data.bizplay.empl.REQ_DATA();
		req_DATA.setREC(new ArrayList<kr.co.kmac.pms.service.scheduler.data.bizplay.empl.REC>());

		if (list != null && list.size() > 0) {
			for (BizplayExpertPoolInfo info : list) {
				kr.co.kmac.pms.service.scheduler.data.bizplay.empl.REC rec = new kr.co.kmac.pms.service.scheduler.data.bizplay.empl.REC();
				rec.setEMP_NO(info.getEmplNo());
				rec.setUSE_INTT_ID("UTLZ_2003031465790");
				rec.setBSNN_NO(info.getBsnnNo());
				rec.setBSNN_NM("KMAC");
				rec.setFLNM(info.getName());
				rec.setENG("");
				rec.setCLPH_NTNL_CD("82");
				rec.setCLPH_NO(info.getMobileNo());
				rec.setDVSN_CD(info.getDept());
				rec.setDVSN_NM(info.getDeptName());
				rec.setJBCL_NM(info.getCompanyPositionName());
				rec.setRSPT_NM("");
				rec.setUSER_ID(info.getEmail());
				rec.setUSER_PW("12345678");
				rec.setEML(info.getEmail());
				rec.setSTTS(info.getEnable());
				rec.setAMNN_DTTM("");
				rec.setOOH_ZPCD("");
				rec.setOOH_POST_ADRS("");
				rec.setOOH_DTL_ADRS("");

				req_DATA.getREC().add(rec);

			}
		}

		infoSet.setREQ_DATA(req_DATA);

		return infoSet;
	}

	private BizplayDeptInfoSet convenrtDeptInfoToJson(List<BizplayDeptInfo> list) {
		BizplayDeptInfoSet infoSet = new BizplayDeptInfoSet();
		kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.REQ_DATA req_DATA = new kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.REQ_DATA();
		req_DATA.setREC(new ArrayList<kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.REC>());

		if (list != null && list.size() > 0) {
			for (BizplayDeptInfo info : list) {
				kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.REC rec = new kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.REC();
				rec.setUSE_INTT_ID("UTLZ_2003031465790");
				rec.setBSUN_ID(info.getBsnnNo());
				rec.setDVSN_CD(info.getDept());
				rec.setDVSN_NM(info.getDeptName());
				rec.setHGRN_DVSN_CD(info.getParentId());
				rec.setOTPT_SQNC(info.getSeq());
				rec.setACVT_YN(info.getEnabled());
				req_DATA.getREC().add(rec);
			}
		}

		infoSet.setREQ_DATA(req_DATA);

		return infoSet;

	}

	@Override
	public boolean insertHistory(String endPoint, String sendTxt, String ResultTxt, String exceptionStr) throws ServiceException {
		return this.getBizplayInfoSendDao().insertHistory(endPoint, sendTxt, ResultTxt, exceptionStr);
	}

}
