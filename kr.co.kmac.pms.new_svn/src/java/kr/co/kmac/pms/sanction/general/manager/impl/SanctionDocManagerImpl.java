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
		assigneeUserPos = "�����";

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
		resMap.put("resultMessage", "���� ������ �ӽ����� �Ǿ����ϴ�.");
		resMap.put("assignMessage", assigneeUserPos + " : " + assigneeUserName + "�� �����Կ� ������ ���� �Ǿ����ϴ�. ");
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
		assignerUserPos = "�����";
		if (StringUtil.isNotNull(sanctionDoc.getTeamManagerSsn())) {
			assigneeUserSsn = sanctionDoc.getTeamManagerSsn();
			assigneeUserName = sanctionDoc.getTeamManagerName();
			assigneeUserPos = "������";
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_REVIEW);
		} else {
			if (StringUtil.isNotNull(sanctionDoc.getCfoSsn())) {
				assigneeUserSsn = sanctionDoc.getCfoSsn();
				assigneeUserName = sanctionDoc.getCfoName();
				assigneeUserPos = "Ȯ����";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CHECK);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
					assigneeUserSsn = sanctionDoc.getCioSsn();
					assigneeUserName = sanctionDoc.getCioName();
					assigneeUserPos = "������";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_APPROVE);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
						assigneeUserName = sanctionDoc.getAssistant1Name();
						assigneeUserPos = "����1";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
							assigneeUserName = sanctionDoc.getAssistant2Name();
							assigneeUserPos = "����2";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
								assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
								assigneeUserName = sanctionDoc.getAssistant3Name();
								assigneeUserPos = "����3";
								sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
							} else {
								if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
									assigneeUserSsn = sanctionDoc.getCeoSsn();
									assigneeUserName = sanctionDoc.getCeoName();
									assigneeUserPos = "��ǥ�̻�";
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
		resMap.put("resultMessage", "���� ������ ���������� ��ϵǾ����ϴ�.");
		resMap.put("assignMessage", assigneeUserPos + " : " + assigneeUserName + "�Կ��� ������ �Ҵ�Ǿ����ϴ�. ");
		return resMap;
	}

	private SanctionDoc getAssignInfoSanctionDoc(SanctionDoc sanctionDoc) {
		if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_DRAFT)
				|| sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_REJECT_DRAFT)) {
			sanctionDoc.setRegisterDate(new Date());
			sanctionDoc.setCreaterDate(new Date());
			assignerUserSsn = sanctionDoc.getRegisterSsn();
			assignerUserName = sanctionDoc.getRegisterName();
			assignerUserPos = "�����";
			if (StringUtil.isNotNull(sanctionDoc.getTeamManagerSsn())) {
				assigneeUserSsn = sanctionDoc.getTeamManagerSsn();
				assigneeUserName = sanctionDoc.getTeamManagerName();
				assigneeUserPos = "������";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_REVIEW);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCfoSsn())) {
					assigneeUserSsn = sanctionDoc.getCfoSsn();
					assigneeUserName = sanctionDoc.getCfoName();
					assigneeUserPos = "Ȯ����";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CHECK);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
						assigneeUserSsn = sanctionDoc.getCioSsn();
						assigneeUserName = sanctionDoc.getCioName();
						assigneeUserPos = "������";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_APPROVE);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
							assigneeUserName = sanctionDoc.getAssistant1Name();
							assigneeUserPos = "����1";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
								assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
								assigneeUserName = sanctionDoc.getAssistant2Name();
								assigneeUserPos = "����2";
								sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
							} else {
								if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
									assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
									assigneeUserName = sanctionDoc.getAssistant3Name();
									assigneeUserPos = "����3";
									sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
								} else {
									if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
										assigneeUserSsn = sanctionDoc.getCeoSsn();
										assigneeUserName = sanctionDoc.getCeoName();
										assigneeUserPos = "��ǥ�̻�";
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
			assignerUserPos = "������";
			if (StringUtil.isNotNull(sanctionDoc.getCfoSsn())) {
				assigneeUserSsn = sanctionDoc.getCfoSsn();
				assigneeUserName = sanctionDoc.getCfoName();
				assigneeUserPos = "Ȯ����";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CHECK);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
					assigneeUserSsn = sanctionDoc.getCioSsn();
					assigneeUserName = sanctionDoc.getCioName();
					assigneeUserPos = "������";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_APPROVE);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
						assigneeUserName = sanctionDoc.getAssistant1Name();
						assigneeUserPos = "����1";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
							assigneeUserName = sanctionDoc.getAssistant2Name();
							assigneeUserPos = "����2";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
								assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
								assigneeUserName = sanctionDoc.getAssistant3Name();
								assigneeUserPos = "����3";
								sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
							} else {
								if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
									assigneeUserSsn = sanctionDoc.getCeoSsn();
									assigneeUserName = sanctionDoc.getCeoName();
									assigneeUserPos = "��ǥ�̻�";
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
			assignerUserPos = "Ȯ����";
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CHECK);
			if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
				assigneeUserSsn = sanctionDoc.getCioSsn();
				assigneeUserName = sanctionDoc.getCioName();
				assigneeUserPos = "������";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_APPROVE);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
					assigneeUserName = sanctionDoc.getAssistant1Name();
					assigneeUserPos = "����1";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
						assigneeUserName = sanctionDoc.getAssistant2Name();
						assigneeUserPos = "����2";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
							assigneeUserName = sanctionDoc.getAssistant3Name();
							assigneeUserPos = "����3";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
								assigneeUserSsn = sanctionDoc.getCeoSsn();
								assigneeUserName = sanctionDoc.getCeoName();
								assigneeUserPos = "��ǥ�̻�";
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
			assignerUserPos = "������";
			if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
				assigneeUserName = sanctionDoc.getAssistant1Name();
				assigneeUserPos = "����1";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST1);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
					assigneeUserName = sanctionDoc.getAssistant2Name();
					assigneeUserPos = "����2";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
						assigneeUserName = sanctionDoc.getAssistant3Name();
						assigneeUserPos = "����3";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
							assigneeUserSsn = sanctionDoc.getCeoSsn();
							assigneeUserName = sanctionDoc.getCeoName();
							assigneeUserPos = "��ǥ�̻�";
							sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST1)) {
			sanctionDoc.setAssistant1Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant1Ssn();
			assignerUserName = sanctionDoc.getAssistant1Name();
			assignerUserPos = "����1";
			if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
				assigneeUserName = sanctionDoc.getAssistant2Name();
				assigneeUserPos = "����2";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST2);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
					assigneeUserName = sanctionDoc.getAssistant3Name();
					assigneeUserPos = "����3";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
						assigneeUserSsn = sanctionDoc.getCeoSsn();
						assigneeUserName = sanctionDoc.getCeoName();
						assigneeUserPos = "��ǥ�̻�";
						sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST2)) {
			sanctionDoc.setAssistant2Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant2Ssn();
			assignerUserName = sanctionDoc.getAssistant2Name();
			assignerUserPos = "����2";
			if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
				assigneeUserName = sanctionDoc.getAssistant3Name();
				assigneeUserPos = "����3";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_ASSIST3);
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
					assigneeUserSsn = sanctionDoc.getCeoSsn();
					assigneeUserName = sanctionDoc.getCeoName();
					assigneeUserPos = "��ǥ�̻�";
					sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST3)) {
			sanctionDoc.setAssistant3Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant3Ssn();
			assignerUserName = sanctionDoc.getAssistant3Name();
			assignerUserPos = "����3";
			if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
				assigneeUserSsn = sanctionDoc.getCeoSsn();
				assigneeUserName = sanctionDoc.getCeoName();
				assigneeUserPos = "��ǥ�̻�";
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_CEO);
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_CEO)) {
			assignerUserSsn = sanctionDoc.getCeoSsn();
			assignerUserName = sanctionDoc.getCeoName();
			assignerUserPos = "��ǥ�̻�";
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
			assignerUserPos = "������";
			// sanctionDoc.setTeamManagerName(entrustUserName);
			// sanctionDoc.setTeamManagerSsn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getCfoSsn())) {
				assigneeUserSsn = sanctionDoc.getCfoSsn();
				assigneeUserName = sanctionDoc.getCfoName();
				assigneeUserPos = "Ȯ����";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
					assigneeUserSsn = sanctionDoc.getCioSsn();
					assigneeUserName = sanctionDoc.getCioName();
					assigneeUserPos = "������";
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
						assigneeUserName = sanctionDoc.getAssistant1Name();
						assigneeUserPos = "����1";
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
							assigneeUserName = sanctionDoc.getAssistant2Name();
							assigneeUserPos = "����2";
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
								assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
								assigneeUserName = sanctionDoc.getAssistant3Name();
								assigneeUserPos = "����3";
							} else {
								if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
									assigneeUserSsn = sanctionDoc.getCeoSsn();
									assigneeUserName = sanctionDoc.getCeoName();
									assigneeUserPos = "��ǥ�̻�";
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
			assignerUserPos = "Ȯ����";
			// sanctionDoc.setCfoName(entrustUserName);
			// sanctionDoc.setCfoSsn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getCioSsn())) {
				assigneeUserSsn = sanctionDoc.getCioSsn();
				assigneeUserName = sanctionDoc.getCioName();
				assigneeUserPos = "������";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
					assigneeUserName = sanctionDoc.getAssistant1Name();
					assigneeUserPos = "����1";
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
						assigneeUserName = sanctionDoc.getAssistant2Name();
						assigneeUserPos = "����2";
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
							assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
							assigneeUserName = sanctionDoc.getAssistant3Name();
							assigneeUserPos = "����3";
						} else {
							if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
								assigneeUserSsn = sanctionDoc.getCeoSsn();
								assigneeUserName = sanctionDoc.getCeoName();
								assigneeUserPos = "��ǥ�̻�";
							}
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_APPROVE)) {
			// sanctionDoc.setCioDate(new Date());
			assignerUserSsn = sanctionDoc.getCioSsn();
			assignerUserName = sanctionDoc.getCioName();
			assignerUserPos = "������";
			// sanctionDoc.setCioName(entrustUserName);
			// sanctionDoc.setCioSsn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getAssistant1Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant1Ssn();
				assigneeUserName = sanctionDoc.getAssistant1Name();
				assigneeUserPos = "����1";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
					assigneeUserName = sanctionDoc.getAssistant2Name();
					assigneeUserPos = "����2";
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
						assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
						assigneeUserName = sanctionDoc.getAssistant3Name();
						assigneeUserPos = "����3";
					} else {
						if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
							assigneeUserSsn = sanctionDoc.getCeoSsn();
							assigneeUserName = sanctionDoc.getCeoName();
							assigneeUserPos = "��ǥ�̻�";
						}
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST1)) {
			// sanctionDoc.setAssistant1Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant1Ssn();
			assignerUserName = sanctionDoc.getAssistant1Name();
			assignerUserPos = "����1";
			// sanctionDoc.setAssistant1Name(entrustUserName);
			// sanctionDoc.setAssistant1Ssn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getAssistant2Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant2Ssn();
				assigneeUserName = sanctionDoc.getAssistant2Name();
				assigneeUserPos = "����2";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
					assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
					assigneeUserName = sanctionDoc.getAssistant3Name();
					assigneeUserPos = "����3";
				} else {
					if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
						assigneeUserSsn = sanctionDoc.getCeoSsn();
						assigneeUserName = sanctionDoc.getCeoName();
						assigneeUserPos = "��ǥ�̻�";
					}
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST2)) {
			// sanctionDoc.setAssistant2Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant2Ssn();
			assignerUserName = sanctionDoc.getAssistant2Name();
			assignerUserPos = "����2";
			// sanctionDoc.setAssistant2Name(entrustUserName);
			// sanctionDoc.setAssistant2Ssn(entrustUserSsn);
			if (StringUtil.isNotNull(sanctionDoc.getAssistant3Ssn())) {
				assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
				assigneeUserName = sanctionDoc.getAssistant3Name();
				assigneeUserPos = "����3";
			} else {
				if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
					assigneeUserSsn = sanctionDoc.getCeoSsn();
					assigneeUserName = sanctionDoc.getCeoName();
					assigneeUserPos = "��ǥ�̻�";
				}
			}
		} else if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_ASSIST3)) {
			// sanctionDoc.setAssistant3Date(new Date());
			assignerUserSsn = sanctionDoc.getAssistant3Ssn();
			assignerUserName = sanctionDoc.getAssistant3Name();
			assignerUserPos = "����3";
			// assigneeUserSsn = sanctionDoc.getAssistant3Ssn();
			// assigneeUserName = sanctionDoc.getAssistant3Name();
			if (StringUtil.isNotNull(sanctionDoc.getCeoSsn())) {
				assigneeUserSsn = sanctionDoc.getCeoSsn();
				assigneeUserName = sanctionDoc.getCeoName();
				assigneeUserPos = "��ǥ�̻�";
			}
		}
		return sanctionDoc;
	}

	@Override
	public HashMap<String, String> executeGeneralSanctionWork(SanctionDoc sanctionDoc) throws SanctionException {
		// �ʱ���� ����
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

		System.out.println(sanctionDoc.getIsWholeApproval() +": ���� ����");
		if (sanctionDoc.getIsWholeApproval().equals("Y")) {
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_COMPLETE);
		}
		if (assigneeUserSsn == null || assigneeUserSsn.equals("")) {
			sanctionDoc.setState(SanctionConstants.SANCTION_STATE_COMPLETE);
		}
		String sanctionMsg = "";
		if (sanctionDoc.getIsApproved().equals("N")) {// �����̳� ����� ���
			// �ݷ��� ��� ó��
			assigneeUserSsn = sanctionDoc.getRegisterSsn();
			assigneeUserName = sanctionDoc.getRegisterName();
			assigneeUserPos = "�����";
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
			sanctionMsg = "�ݷ�����";
		} else {
			sanctionDoc.setReject("N");
			this.getWorklistManager().executeWork(sanctionDoc.getTaskId());
			sanctionMsg = "Ȯ�γ���";
		}

		if (!stateInit.equals(SanctionConstants.SANCTION_STATE_DRAFT)) {
			sanctionDoc.setRejectReasonView("\n" + assignerUserPos + " " + assignerUserName + "���� " + sanctionMsg + " |" + " "
					+ DateTime.getTimeStampString() + "\n"+ sanctionDoc.getRejectReason() + "\n\n" + sanctionDoc.getRejectReasonView());
		}
		// ���� ���� ������Ʈ
		this.getSanctionDocDao().update(sanctionDoc);
		if (sanctionDoc.getSanctionDocCC() != null && sanctionDoc.getSanctionDocCC().size() > 0) {
			this.getSanctionDocCCDao().delete(String.valueOf(sanctionDoc.getSeq()));
			this.getSanctionDocCCDao().insert(String.valueOf(sanctionDoc.getSeq()), sanctionDoc.getSanctionDocCC());
		} else {
			this.getSanctionDocCCDao().delete(String.valueOf(sanctionDoc.getSeq()));
		}

		// ������
		if (!sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_COMPLETE)) {
			// ���� ���� �Ҵ�
			Work assigneeWork = this.getWorklistManager().getWork(sanctionDoc.getTaskId());
			assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
			assigneeWork.setTitle(sanctionDoc.getSubject());
			assigneeWork.setAssignerId(assignerUserSsn);
			assigneeWork.setAssigneeId(assigneeUserSsn);
			assigneeWork.setLevel(sanctionDoc.getState());
			assigneeWork.setState(WorkConstants.WORK_STATE_READY);
			assigneeWork.setCreateDate(new Date());
			this.getWorklistManager().assignWork(assigneeWork);

			resMap.put("assignMessage", assigneeUserPos + " : " + assigneeUserName + "�Կ��� ������ �Ҵ�Ǿ����ϴ�. ");
		} else {
			// ���� ����--> ��ȹ�����Ȳ ������Ʈ
			// try {
			// this.getSanctionDocDao().updateEduState(sanctionDoc.getProjectCode());
			// } catch (Exception e) {
			// logger.error(sanctionDoc.getProjectCode() + " ��ȹ��� ������Ʈ ����");
			// }
			// �����ڿ��� ���� �Ҵ�
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
		resMap.put("resultMessage", "���� ������ ���������� ��ϵǾ����ϴ�.");

		if (!stateInit.equals(SanctionConstants.SANCTION_STATE_DRAFT) && !stateInit.equals(SanctionConstants.SANCTION_STATE_REJECT_DRAFT)) {
			try {
				// ���� ���۽� ���� �� ���� ó���ڷ� ���� ����
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
		 * Job Date: 2010-06-29 Author: yhyim Description: �������ӱ�� �ʱ� ����, ������ ���� ���뵵 ������Ʈ �� Work work =
		 * this.getWorklistManager().getWork(sanctionDoc.getTaskId()); sanctionDoc = this.getAssignInfoSanctionDoc(sanctionDoc);
		 * 
		 * work.setAssigneeId(sanctionDoc.getEntrustUserSsn()); work.setAssignerId(this.assignerUserName); this.getWorklistManager().entrustWork(work);
		 * 
		 * sanctionDoc.setRejectReason("\n-- " + assignerUserPos + " " + assignerUserName + " ���� �������� [" + DateTime.getTimeStampString() + "]\n" +
		 * sanctionDoc.getEntrustUserName() + "�Կ��� ������ ���ӵǾ����ϴ�." + "\n\n" + sanctionDoc.getRejectReasonView()); // ���� ���� ������Ʈ
		 * this.getSanctionDocDao().update(sanctionDoc);
		 */

		// ���� ����
		Work work = this.getWorklistManager().getWork(sanctionDoc.getTaskId());
		work.setAssigneeId(sanctionDoc.getEntrustUserSsn());
		work.setIsEntrust("Y");
		this.getWorklistManager().entrustWork(work);

		// ���� ���� ����
		sanctionDoc = this.getEntrustInfoSanctionDoc(sanctionDoc, sanctionDoc.getEntrustUserName(), sanctionDoc.getEntrustUserSsn());
		sanctionDoc.setRejectReason("\n-- " + assignerUserPos + " " + assignerUserName + " ���� �������� [" + DateTime.getTimeStampString() + "]\n"
				+ sanctionDoc.getEntrustUserName() + "�Կ��� ������ ���ӵǾ����ϴ�." + "\n\n" + sanctionDoc.getRejectReasonView());
		// ���� ���� ������Ʈ
		this.getSanctionDocDao().update(sanctionDoc);
	}

	@Override
	public HashMap<String, String> executeRefWork(SanctionDoc sanctionDoc, String assigneeSsn) throws SanctionException {
		HashMap<String, String> resMap = new HashMap<String, String>();
		this.getWorklistManager().executeWork(sanctionDoc.getTaskId());
		SanctionDocCC sanctionDocCC = this.getSanctionDocCCDao().select(String.valueOf(sanctionDoc.getSeq()), assigneeSsn);
		sanctionDocCC.setApproveCheckedDate(new Date());
		this.getSanctionDocCCDao().update(sanctionDocCC);
		resMap.put("resultMessage", "���� ������ �Ϸ�Ǿ����ϴ�.");
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
