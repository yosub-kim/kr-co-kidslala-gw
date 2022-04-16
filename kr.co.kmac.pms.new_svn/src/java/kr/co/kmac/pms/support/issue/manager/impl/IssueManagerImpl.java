package kr.co.kmac.pms.support.issue.manager.impl;

import kr.co.kmac.pms.support.issue.dao.IssueDao;
import kr.co.kmac.pms.support.issue.data.IssueList;
import kr.co.kmac.pms.support.issue.exception.IssueException;
import kr.co.kmac.pms.support.issue.form.IssueDetailForm;
import kr.co.kmac.pms.support.issue.manager.IssueManager;

public class IssueManagerImpl implements IssueManager {

	private IssueDao issueDao;

	public IssueDao getIssueDao() {
		return issueDao;
	}

	public void setIssueDao(IssueDao issueDao) {
		this.issueDao = issueDao;
	}
	
	public void create(IssueDetailForm issueDetailForm) throws IssueException {
		getIssueDao().create(issueDetailForm);
	}
	
	@Override
	public String select(String ssn) throws IssueException {
		return getIssueDao().select(ssn);
	}
	
	@Override
	public String select2(String ssn) throws IssueException {
		return getIssueDao().select2(ssn);
	}

}
