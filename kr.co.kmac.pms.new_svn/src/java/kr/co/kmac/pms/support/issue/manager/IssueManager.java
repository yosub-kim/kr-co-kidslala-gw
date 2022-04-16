package kr.co.kmac.pms.support.issue.manager;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.support.issue.exception.IssueException;
import kr.co.kmac.pms.support.issue.form.IssueDetailForm;

public interface IssueManager {
	
	public void create(IssueDetailForm issueDetailForm) throws DataAccessException;
	
	public String select(String ssn) throws IssueException;
	
	public String select2(String ssn) throws IssueException;

}
