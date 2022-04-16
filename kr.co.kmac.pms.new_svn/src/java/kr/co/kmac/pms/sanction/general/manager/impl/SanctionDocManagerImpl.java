package kr.co.kmac.pms.sanction.general.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.data.WorkConstants;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.dao.SanctionDocCCDao;
import kr.co.kmac.pms.sanction.general.dao.SanctionDocDao;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.general.data.SanctionDoc;
import kr.co.kmac.pms.sanction.general.data.SanctionDocCC;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SanctionDocManagerImpl implements SanctionDocManager {
	private static final Log logger = LogFactory.getLog(SanctionDocManager.class);
	private WorklistManager worklistManager;
	private SanctionDocDao sanctionDocDao;
	private SanctionDocCCDao sanctionDocCCDao;
	private PmsInfoMailSender pmsInfoMailSender;

	private String assigneeUserSsn = null;
	private String assigneeUserName = null;
	private String assigneeUserPos = null;

	private String assignerUserSsn = null;
	private String assignerUserName = null;
	private String assignerUserPos = null;

	@Override
	public HashMap<String, String> draftSaveGeneralSanction(String workType, SanctionDoc sanctionDoc) throws SanctionException {
		HashMap<String, String> resMap = new HashMap<String, String>();
		if (sanctionDoc == null) {
			throw new SanctionException("Sanction Doc should be not not null.");
		}
		if (sanctionDoc.getState() == null) {
			throw new SanctionException("Sanction Doc must have State.");
		}
		sanctionDoc.setWorkType(workType);
		sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);
		sanctionDoc.setRegisterDate(null);
		sanctionDoc.setCreaterDate(null);
		assignerUserSsn = sanctionDoc.getRegisterSsn();
		assigneeUserSsn = sanctionDoc.getRegisterSsn();
		assigneeUserName = sanctionDoc.getRegisterName();
		assigneeUserPos = "기안자";

		sanctionDoc = this.getSanctionDocDao().insert(sanctionDoc);
		if (sanctionDoc.getSanctionDocCC() != null && sanctionDoc.getSanctionDocCC().size() > 0) {
			this.getSanctionDocCCDao().insert(String.valueOf(sanctionDoc.getSeq()), sanctionDoc.getSanctionDocCC());
		}

		Work assigneeWork = this.getWorklistManager().getWorkTemplate(workType, assignerUserSsn, assigneeUserSsn, sanctionDoc.getProjectCode(),
				sanctionDoc.getApprovalType(), String.valueOf(sanctionDoc.getSeq()));
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(sanctionDoc.getRegisterDate());
		assigneeWork.setDraftUserId(sanctionDoc.getRegisterSsn());
		assigneeWork.setDraftUserDept(sanctionDoc.getRegisterDept());
		assigneeWork.setLevel(sanctionDoc.getState());
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(sanctionDoc.getSubject());

		this.getWorklistManager().assignWork(assigneeWork);

		resMap.put("seq", String.valueOf(sanctionDoc.getSeq()));
		resMap.put("resultMessage", "결재 업무가 임시저장 되었습니다.");
		resMap.put("assignMessage", assigneeUserPos + " : " + assigneeUserName + "님 업무함에 업무가 저장 되었습니다. ");
		return resMap;
	}

	@Override
	public HashMap<String, String> createGeneralSanctionWork(String workType, SanctionDoc sanctionDoc) throws SanctionException {
		HashMap<String, String> resMap = new HashMap<String, String>();
		if (sanctionDoc == null) {
			throw new SanctionException("Sanction Doc should be not not null.");
		}
		if (sanctionDoc.getState() == null) {
			throw new SanctionException("Sanction Doc must have State.");
		}
		sanctionDoc.setWorkType(workType);
		sanctionDoc.setRegisterDate(new Date());
		sanctionDoc.setCreaterDate(new Date());
		assignerUserSsn = sanctionDoc.getRegisterSsn();
		assignerUserName = sanctionDoc.getRegisterName();
		assignerUserPos = "기안자";
		if (StringUtil.isNotNull(sanctionDoc.getTeamManagerSsn())) {
			assigneeUserSsn = sanctionDoc.getTeamManagerSsn();
			assigneeUserName = sanctionDoc.getTeamManagerName();
			assigneeUserPos = "검토자";
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_REVIEW);
		} else {
			if (StringUtil.isNotNull(sanctionDoc.getCfoSsn())) {
				assigneeUserSsn = sanctionDoc.getCfoSsn();
				assigneeUserName = sanctionDoc.getCfoName();
				assigneeUserPos = "확인자";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CHECK);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
					assigneeUserSsn = sanctionDoc.getCioSsn();
					assigneeUserName = sanctionDoc.getCioName();
					assigneeUserPos = "승인자";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_APPROVE);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
						assigneeUserName = sanctionDoc.getAssistant1Name();
						assigneeUserPos = "협의1";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
							assigneeUserName = sanctionDoc.getAssistant2Name();
							assigneeUserPos = "협의2";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
								assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
								assigneeUserName = sanctionDoc.getAssistant3Name();
								assigneeUserPos = "협의3";
								sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
							} else {
								if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
									assigneeUserSsn = sanctionDoc.getCeoSsn();
									assigneeUserName = sanctionDoc.getCeoName();
									assigneeUserPos = "대표이사";
									sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
								}
							}
						}
					}
				}
			}
		}

		sanctionDoc = this.getSanctionDocDao().insert(sanctionDoc);
		if (sanctionDoc.getSanctionDocCC() != null && sanctionDoc.getSanctionDocCC().size() > 0) {
			this.getSanctionDocCCDao().insert(String.valueOf(sanctionDoc.getSeq()), sanctionDoc.getSanctionDocCC());
		}

		Work assigneeWork = this.getWorklistManager().getWorkTemplate(workType, assignerUserSsn, assigneeUserSsn, sanctionDoc.getProjectCode(),
				sanctionDoc.getApprovalType(), String.valueOf(sanctionDoc.getSeq()));
		assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
		assigneeWork.setCreateDate(new Date());
		assigneeWork.setDraftDate(sanctionDoc.getRegisterDate());
		assigneeWork.setDraftUserId(sanctionDoc.getRegisterSsn());
		assigneeWork.setDraftUserDept(sanctionDoc.getRegisterDept());
		assigneeWork.setLevel(sanctionDoc.getState());
		assigneeWork.setState(WorkConstants.WORK_STATE_READY);
		assigneeWork.setTitle(sanctionDoc.getSubject());

		this.getWorklistManager().assignWork(assigneeWork);

		resMap.put("seq", String.valueOf(sanctionDoc.getSeq()));
		resMap.put("resultMessage", "결재 업무가 정상적으로 등록되었습니다.");
		resMap.put("assignMessage", assigneeUserPos + " : " + assigneeUserName + "님에게 업무가 할당되었습니다. ");
		return resMap;
	}

	private SanctionDoc getAssignInfoSanctionDoc(SanctionDoc sanctionDoc) {
		if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_DRAFT)
				|| sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_REJECT_DRAFT)) {
			sanctionDoc.setRegisterDate(new Date());
			sanctionDoc.setCreaterDate(new Date());
			assignerUserSsn = sanctionDoc.getRegisterSsn();
			assignerUserName = sanctionDoc.getRegisterName();
			assignerUserPos = "기안자";
			if (StringUtil.isNotNull(sanctionDoc.getTeamManagerSsn())) {
				assigneeUserSsn = sanctionDoc.getTeamManagerSsn();
				assigneeUserName = sanctionDoc.getTeamManagerName();
				assigneeUserPos = "검토자";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_REVIEW);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCfoSsn())) {
					assigneeUserSsn = sanctionDoc.getCfoSsn();
					assigneeUserName = sanctionDoc.getCfoName();
					assigneeUserPos = "확인자";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CHECK);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
						assigneeUserSsn = sanctionDoc.getCioSsn();
						assigneeUserName = sanctionDoc.getCioName();
						assigneeUserPos = "승인자";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_APPROVE);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
							assigneeUserName = sanctionDoc.getAssistant1Name();
							assigneeUserPos = "협의1";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
								assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
								assigneeUserName = sanctionDoc.getAssistant2Name();
								assigneeUserPos = "협의2";
								sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
							} else {
								if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
									assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
									assigneeUserName = sanctionDoc.getAssistant3Name();
									assigneeUserPos = "협의3";
									sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
								} else {
									if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
										assigneeUserSsn = sanctionDoc.getCeoSsn();
										assigneeUserName = sanctionDoc.getCeoName();
										assigneeUserPos = "대표이사";
										sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
									}
								}
							}
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_REVIEW)) {
			sanctionDoc.setTeamManagerDate(new Date());
			assignerUserSsn = sanctionDoc.getTeamManagerSsn();
			assignerUserName = sanctionDoc.getTeamManagerName();
			assignerUserPos = "검토자";
			if (StringUtil.isNotNull(sanctionDoc.getCfoSsn())) {
				assigneeUserSsn = sanctionDoc.getCfoSsn();
				assigneeUserName = sanctionDoc.getCfoName();
				assigneeUserPos = "확인자";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CHECK);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
					assigneeUserSsn = sanctionDoc.getCioSsn();
					assigneeUserName = sanctionDoc.getCioName();
					assigneeUserPos = "승인자";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_APPROVE);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
						assigneeUserName = sanctionDoc.getAssistant1Name();
						assigneeUserPos = "협의1";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
							assigneeUserName = sanctionDoc.getAssistant2Name();
							assigneeUserPos = "협의2";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
								assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
								assigneeUserName = sanctionDoc.getAssistant3Name();
								assigneeUserPos = "협의3";
								sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
							} else {
								if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
									assigneeUserSsn = sanctionDoc.getCeoSsn();
									assigneeUserName = sanctionDoc.getCeoName();
									assigneeUserPos = "대표이사";
									sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
								}
							}
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_CHECK)) {
			sanctionDoc.setCfoDate(new Date());
			assignerUserSsn = sanctionDoc.getCfoSsn();
			assignerUserName = sanctionDoc.getCfoName();
			assignerUserPos = "확인자";
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CHECK);
			if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
				assigneeUserSsn = sanctionDoc.getCioSsn();
				assigneeUserName = sanctionDoc.getCioName();
				assigneeUserPos = "승인자";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_APPROVE);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
					assigneeUserName = sanctionDoc.getAssistant1Name();
					assigneeUserPos = "협의1";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
						assigneeUserName = sanctionDoc.getAssistant2Name();
						assigneeUserPos = "협의2";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
							assigneeUserName = sanctionDoc.getAssistant3Name();
							assigneeUserPos = "협의3";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
								assigneeUserSsn = sanctionDoc.getCeoSsn();
								assigneeUserName = sanctionDoc.getCeoName();
								assigneeUserPos = "대표이사";
								sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
							}
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_APPROVE)) {
			sanctionDoc.setCioDate(new Date());
			assignerUserSsn = sanctionDoc.getCioSsn();
			assignerUserName = sanctionDoc.getCioName();
			assignerUserPos = "승인자";
			if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
				assigneeUserName = sanctionDoc.getAssistant1Name();
				assigneeUserPos = "협의1";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
					assigneeUserName = sanctionDoc.getAssistant2Name();
					assigneeUserPos = "협의2";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
						assigneeUserName = sanctionDoc.getAssistant3Name();
						assigneeUserPos = "협의3";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
							assigneeUserSsn = sanctionDoc.getCeoSsn();
							assigneeUserName = sanctionDoc.getCeoName();
							assigneeUserPos = "대표이사";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST1)) {
			sanctionDoc.setAssistant1Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant1Ssn();
			assignerUserName = sanctionDoc.getAssistant1Name();
			assignerUserPos = "협의1";
			if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
				assigneeUserName = sanctionDoc.getAssistant2Name();
				assigneeUserPos = "협의2";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
					assigneeUserName = sanctionDoc.getAssistant3Name();
					assigneeUserPos = "협의3";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
						assigneeUserSsn = sanctionDoc.getCeoSsn();
						assigneeUserName = sanctionDoc.getCeoName();
						assigneeUserPos = "대표이사";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST2)) {
			sanctionDoc.setAssistant2Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant2Ssn();
			assignerUserName = sanctionDoc.getAssistant2Name();
			assignerUserPos = "협의2";
			if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
				assigneeUserName = sanctionDoc.getAssistant3Name();
				assigneeUserPos = "협의3";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
					assigneeUserSsn = sanctionDoc.getCeoSsn();
					assigneeUserName = sanctionDoc.getCeoName();
					assigneeUserPos = "대표이사";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST3)) {
			sanctionDoc.setAssistant3Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant3Ssn();
			assignerUserName = sanctionDoc.getAssistant3Name();
			assignerUserPos = "협의3";
			if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
				assigneeUserSsn = sanctionDoc.getCeoSsn();
				assigneeUserName = sanctionDoc.getCeoName();
				assigneeUserPos = "대표이사";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_CEO)) {
			assignerUserSsn = sanctionDoc.getCeoSsn();
			assignerUserName = sanctionDoc.getCeoName();
			assignerUserPos = "대표이사";
			sanctionDoc.setCeoDate(new Date());
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_COMPLETE);
		}
		return sanctionDoc;
	}

	private SanctionDoc getEntrustInfoSanctionDoc(SanctionDoc sanctionDoc, String entrustUserName, String entrustUserSsn) {
		if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_DRAFT)
				|| sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_REVIEW)) {
			// sanctionDoc.setTeamManagerDate(new Date());
			assignerUserSsn = sanctionDoc.getTeamManagerSsn();
			assignerUserName = sanctionDoc.getTeamManagerName();
			assignerUserPos = "검토자";
			// sanctionDoc.setTeamManagerName(entrustUserName);
			// sanctionDoc.setTeamManagerSsn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getCfoSsn())) {
				assigneeUserSsn = sanctionDoc.getCfoSsn();
				assigneeUserName = sanctionDoc.getCfoName();
				assigneeUserPos = "확인자";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
					assigneeUserSsn = sanctionDoc.getCioSsn();
					assigneeUserName = sanctionDoc.getCioName();
					assigneeUserPos = "승인자";
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
						assigneeUserName = sanctionDoc.getAssistant1Name();
						assigneeUserPos = "협의1";
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
							assigneeUserName = sanctionDoc.getAssistant2Name();
							assigneeUserPos = "협의2";
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
								assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
								assigneeUserName = sanctionDoc.getAssistant3Name();
								assigneeUserPos = "협의3";
							} else {
								if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
									assigneeUserSsn = sanctionDoc.getCeoSsn();
									assigneeUserName = sanctionDoc.getCeoName();
									assigneeUserPos = "대표이사";
								}
							}
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_CHECK)) {
			// sanctionDoc.setCfoDate(new Date());
			assignerUserSsn = sanctionDoc.getCfoSsn();
			assignerUserName = sanctionDoc.getCfoName();
			assignerUserPos = "확인자";
			// sanctionDoc.setCfoName(entrustUserName);
			// sanctionDoc.setCfoSsn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
				assigneeUserSsn = sanctionDoc.getCioSsn();
				assigneeUserName = sanctionDoc.getCioName();
				assigneeUserPos = "승인자";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
					assigneeUserName = sanctionDoc.getAssistant1Name();
					assigneeUserPos = "협의1";
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
						assigneeUserName = sanctionDoc.getAssistant2Name();
						assigneeUserPos = "협의2";
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
							assigneeUserName = sanctionDoc.getAssistant3Name();
							assigneeUserPos = "협의3";
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
								assigneeUserSsn = sanctionDoc.getCeoSsn();
								assigneeUserName = sanctionDoc.getCeoName();
								assigneeUserPos = "대표이사";
							}
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_APPROVE)) {
			// sanctionDoc.setCioDate(new Date());
			assignerUserSsn = sanctionDoc.getCioSsn();
			assignerUserName = sanctionDoc.getCioName();
			assignerUserPos = "승인자";
			// sanctionDoc.setCioName(entrustUserName);
			// sanctionDoc.setCioSsn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
				assigneeUserName = sanctionDoc.getAssistant1Name();
				assigneeUserPos = "협의1";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
					assigneeUserName = sanctionDoc.getAssistant2Name();
					assigneeUserPos = "협의2";
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
						assigneeUserName = sanctionDoc.getAssistant3Name();
						assigneeUserPos = "협의3";
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
							assigneeUserSsn = sanctionDoc.getCeoSsn();
							assigneeUserName = sanctionDoc.getCeoName();
							assigneeUserPos = "대표이사";
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST1)) {
			// sanctionDoc.setAssistant1Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant1Ssn();
			assignerUserName = sanctionDoc.getAssistant1Name();
			assignerUserPos = "협의1";
			// sanctionDoc.setAssistant1Name(entrustUserName);
			// sanctionDoc.setAssistant1Ssn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
				assigneeUserName = sanctionDoc.getAssistant2Name();
				assigneeUserPos = "협의2";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
					assigneeUserName = sanctionDoc.getAssistant3Name();
					assigneeUserPos = "협의3";
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
						assigneeUserSsn = sanctionDoc.getCeoSsn();
						assigneeUserName = sanctionDoc.getCeoName();
						assigneeUserPos = "대표이사";
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST2)) {
			// sanctionDoc.setAssistant2Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant2Ssn();
			assignerUserName = sanctionDoc.getAssistant2Name();
			assignerUserPos = "협의2";
			// sanctionDoc.setAssistant2Name(entrustUserName);
			// sanctionDoc.setAssistant2Ssn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
				assigneeUserName = sanctionDoc.getAssistant3Name();
				assigneeUserPos = "협의3";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
					assigneeUserSsn = sanctionDoc.getCeoSsn();
					assigneeUserName = sanctionDoc.getCeoName();
					assigneeUserPos = "대표이사";
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST3)) {
			// sanctionDoc.setAssistant3Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant3Ssn();
			assignerUserName = sanctionDoc.getAssistant3Name();
			assignerUserPos = "협의3";
			// assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
			// assigneeUserName = sanctionDoc.getAssistant3Name();
			if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
				assigneeUserSsn = sanctionDoc.getCeoSsn();
				assigneeUserName = sanctionDoc.getCeoName();
				assigneeUserPos = "대표이사";
			}
		}
		return sanctionDoc;
	}

	@Override
	public HashMap<String, String> executeGeneralSanctionWork(SanctionDoc sanctionDoc) throws SanctionException {
		// 초기상태 저장
		String stateInit = sanctionDoc.getState();
		HashMap<String, String> resMap = new HashMap<String, String>();
		assigneeUserSsn = "";
		if (sanctionDoc == null) {
			throw new SanctionException("Sanction Document should be not null.");
		}
		if (sanctionDoc.getState() == null) {
			throw new SanctionException("Sanction Document must have State.");
		}

		if (stateInit.equals(SanctionConstants.SANCTION_STATE_DRAFT)) {
			Work work = this.getWorklistManager().getWork(sanctionDoc.getTaskId());
			work.setDraftDate(new Date());
			this.getWorklistManager().updateDraftDate(work.getId());
		}

		sanctionDoc = getAssignInfoSanctionDoc(sanctionDoc);

		System.out.println(sanctionDoc.getIsWholeApproval() +": 전결 여부");
		if (sanctionDoc.getIsWholeApproval().equals("Y")) {
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_COMPLETE);
		}
		if (assigneeUserSsn == null || assigneeUserSsn.equals("")) {
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_COMPLETE);
		}
		String sanctionMsg = "";
		if (sanctionDoc.getIsApproved().equals("N")) {// 승인이나 기안일 경우
			// 반려일 경우 처리
			assigneeUserSsn = sanctionDoc.getRegisterSsn();
			assigneeUserName = sanctionDoc.getRegisterName();
			assigneeUserPos = "기안자";
			sanctionDoc.setRegisterDate(null);
			sanctionDoc.setCreaterDate(null);
			sanctionDoc.setTeamManagerDate(null);
			sanctionDoc.setCfoDate(null);
			sanctionDoc.setCioDate(null);
			sanctionDoc.setAssistant1Date(null);
			sanctionDoc.setAssistant2Date(null);
			sanctionDoc.setAssistant3Date(null);
			sanctionDoc.setAssistant4Date(null);
			sanctionDoc.setCeoDate(null);
			sanctionDoc.setReject("Y");
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_REJECT_DRAFT);
			this.getWorklistManager().rejectWork(sanctionDoc.getTaskId());
			sanctionMsg = "반려사유";
		} else {
			sanctionDoc.setReject("N");
			this.getWorklistManager().executeWork(sanctionDoc.getTaskId());
			sanctionMsg = "확인내용";
		}

		if (!stateInit.equals(SanctionConstants.SANCTION_STATE_DRAFT)) {
			sanctionDoc.setRejectReasonView("\n" + assignerUserPos + " " + assignerUserName + "님의 " + sanctionMsg + " |" + " "
					+ DateTime.getTimeStampString() + "\n"+ sanctionDoc.getRejectReason() + "\n\n" + sanctionDoc.getRejectReasonView());
		}
		// 결재 문서 업데이트
		this.getSanctionDocDao().update(sanctionDoc);
		if (sanctionDoc.getSanctionDocCC() != null && sanctionDoc.getSanctionDocCC().size() > 0) {
			this.getSanctionDocCCDao().delete(String.valueOf(sanctionDoc.getSeq()));
			this.getSanctionDocCCDao().insert(String.valueOf(sanctionDoc.getSeq()), sanctionDoc.getSanctionDocCC());
		} else {
			this.getSanctionDocCCDao().delete(String.valueOf(sanctionDoc.getSeq()));
		}

		// 진행중
		if (!sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_COMPLETE)) {
			// 다음 업무 할당
			Work assigneeWork = this.getWorklistManager().getWork(sanctionDoc.getTaskId());
			assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
			assigneeWork.setTitle(sanctionDoc.getSubject());
			assigneeWork.setAssignerId(assignerUserSsn);
			assigneeWork.setAssigneeId(assigneeUserSsn);
			assigneeWork.setLevel(sanctionDoc.getState());
			assigneeWork.setState(WorkConstants.WORK_STATE_READY);
			assigneeWork.setCreateDate(new Date());
			this.getWorklistManager().assignWork(assigneeWork);

			resMap.put("assignMessage", assigneeUserPos + " : " + assigneeUserName + "님에게 업무가 할당되었습니다. ");
		} else {
			// 결재 종료--> 기획사업현황 업데이트
			// try {
			// this.getSanctionDocDao().updateEduState(sanctionDoc.getProjectCode());
			// } catch (Exception e) {
			// logger.error(sanctionDoc.getProjectCode() + " 기획사업 업데이트 에러");
			// }
			// 참조자에게 업무 할당
			List<SanctionDocCC> list = sanctionDoc.getSanctionDocCC();
			if (list != null && list.size() > 0) {
				for (SanctionDocCC sanctionDocCC : list) {
					Work assigneeWork = this.getWorklistManager().getWork(sanctionDoc.getTaskId());
					assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
					assigneeWork.setAssignerId("");
					assigneeWork.setAssigneeId(sanctionDocCC.getRefMemberSsn());
					assigneeWork.setLevel("");
					assigneeWork.setState(WorkConstants.WORK_STATE_READY);
					assigneeWork.setCreateDate(new Date());
					assigneeWork.setType("ref_" + assigneeWork.getType());
					this.getWorklistManager().assignWork(assigneeWork);
					sanctionDocCC.setApproveRefMailSended(true);
					this.getSanctionDocCCDao().update(sanctionDocCC);
				}
				try {
					this.getPmsInfoMailSender().sendSanctonRefInfo(list, assignerUserName, assignerUserPos, sanctionDoc);
				} catch (Exception e) {
					logger.error("Send ref Mail error");
				}
			}
		}

		resMap.put("state", sanctionDoc.getState());
		resMap.put("resultMessage", "결재 업무가 정상적으로 등록되었습니다.");

		if (!stateInit.equals(SanctionConstants.SANCTION_STATE_DRAFT) && !stateInit.equals(SanctionConstants.SANCTION_STATE_REJECT_DRAFT)) {
			try {
				// 메일 전송시 업무 현 업무 처리자로 상태 변경
				sanctionDoc.setState(stateInit);
				this.getPmsInfoMailSender().sendSanctonInfo(assigneeUserName, assigneeUserPos, assignerUserName, assignerUserPos, sanctionDoc);
			} catch (Exception e) {
				logger.error("Send Mail error");
			}
		}

		return resMap;
	}

	@Override
	public void entrustGeneralSanctionDoc(SanctionDoc sanctionDoc) throws SanctionException {
		/*
		 * Job Date: 2010-06-29 Author: yhyim Description: 업무위임기능 초기 버전, 업무에 대한 내용도 업데이트 함 Work work =
		 * this.getWorklistManager().getWork(sanctionDoc.getTaskId()); sanctionDoc = this.getAssignInfoSanctionDoc(sanctionDoc);
		 * 
		 * work.setAssigneeId(sanctionDoc.getEntrustUserSsn()); work.setAssignerId(this.assignerUserName); this.getWorklistManager().entrustWork(work);
		 * 
		 * sanctionDoc.setRejectReason("\n-- " + assignerUserPos + " " + assignerUserName + " 님의 업무위임 [" + DateTime.getTimeStampString() + "]\n" +
		 * sanctionDoc.getEntrustUserName() + "님에게 업무가 위임되었습니다." + "\n\n" + sanctionDoc.getRejectReasonView()); // 결재 문서 업데이트
		 * this.getSanctionDocDao().update(sanctionDoc);
		 */

		// 업무 위임
		Work work = this.getWorklistManager().getWork(sanctionDoc.getTaskId());
		work.setAssigneeId(sanctionDoc.getEntrustUserSsn());
		work.setIsEntrust("Y");
		this.getWorklistManager().entrustWork(work);

		// 위임 정보 설정
		sanctionDoc = this.getEntrustInfoSanctionDoc(sanctionDoc, sanctionDoc.getEntrustUserName(), sanctionDoc.getEntrustUserSsn());
		sanctionDoc.setRejectReason("\n-- " + assignerUserPos + " " + assignerUserName + " 님의 업무위임 [" + DateTime.getTimeStampString() + "]\n"
				+ sanctionDoc.getEntrustUserName() + "님에게 업무가 위임되었습니다." + "\n\n" + sanctionDoc.getRejectReasonView());
		// 결재 문서 업데이트
		this.getSanctionDocDao().update(sanctionDoc);
	}

	@Override
	public HashMap<String, String> executeRefWork(SanctionDoc sanctionDoc, String assigneeSsn) throws SanctionException {
		HashMap<String, String> resMap = new HashMap<String, String>();
		this.getWorklistManager().executeWork(sanctionDoc.getTaskId());
		SanctionDocCC sanctionDocCC = this.getSanctionDocCCDao().select(String.valueOf(sanctionDoc.getSeq()), assigneeSsn);
		sanctionDocCC.setApproveCheckedDate(new Date());
		this.getSanctionDocCCDao().update(sanctionDocCC);
		resMap.put("resultMessage", "참조 업무가 완료되었습니다.");
		return resMap;
	}

	@Override
	public SanctionDoc getGeneralSanctionDoc(String projectCode, String approvalType, String seq) throws SanctionException {
		SanctionDoc doc = this.getSanctionDocDao().select(seq, projectCode, approvalType);
		doc.setSanctionDocCC(this.getSanctionDocCCDao().select(String.valueOf(doc.getSeq())));
		return doc;
	}

	@Override
	public SanctionDoc getGeneralSanctionDoc(String projectCode) throws SanctionException {
		SanctionDoc doc = this.getSanctionDocDao().select(projectCode);
		return doc;
	}

	@Override
	public void deleteGeneralSanctionDoc(SanctionDoc sanctionDoc) throws SanctionException {
		this.getSanctionDocCCDao().delete(String.valueOf(sanctionDoc.getSeq()));
		this.getSanctionDocDao().delete(String.valueOf(sanctionDoc.getSeq()), sanctionDoc.getProjectCode(), sanctionDoc.getApprovalType());
		this.getWorklistManager().terminatWork(sanctionDoc.getTaskId());
	}
	
	@Override
	public void insertGeneralSanctionDocLog(SanctionDoc sanctionDoc) throws SanctionException {
		this.getSanctionDocDao().insertLog(sanctionDoc);
	}

	@Override
	public void saveGeneralSanctionDoc(SanctionDoc sanctionDoc) throws SanctionException {
		Work assigneeWork = this.getWorklistManager().getWork(sanctionDoc.getTaskId());
		assigneeWork.setTitle(sanctionDoc.getSubject());
		this.getWorklistManager().updateWork(assigneeWork);
		this.getSanctionDocCCDao().update(String.valueOf(sanctionDoc.getSeq()), sanctionDoc.getSanctionDocCC());
		this.getSanctionDocDao().update(sanctionDoc);
	}

	@Override
	public boolean isIngSanctionExist(String projectCode, String approvalType) throws SanctionException {
		return this.getSanctionDocDao().isIngSanctionExist(projectCode, approvalType);
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public SanctionDocDao getSanctionDocDao() {
		return sanctionDocDao;
	}

	public void setSanctionDocDao(SanctionDocDao sanctionDocDao) {
		this.sanctionDocDao = sanctionDocDao;
	}

	public SanctionDocCCDao getSanctionDocCCDao() {
		return sanctionDocCCDao;
	}

	public void setSanctionDocCCDao(SanctionDocCCDao sanctionDocCCDao) {
		this.sanctionDocCCDao = sanctionDocCCDao;
	}

	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}

}
