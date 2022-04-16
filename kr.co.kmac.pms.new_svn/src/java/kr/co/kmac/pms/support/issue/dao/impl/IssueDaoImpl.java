package kr.co.kmac.pms.support.issue.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.support.issue.dao.IssueDao;
import kr.co.kmac.pms.support.issue.exception.IssueException;
import kr.co.kmac.pms.support.issue.form.IssueDetailForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.core.SqlParameter;

public class IssueDaoImpl extends JdbcDaoSupport implements IssueDao {
	
	private IssueInsertQuery issueInsertQuery;
	
	protected void initDao() throws Exception {
		this.issueInsertQuery = new IssueInsertQuery(getDataSource());
	}
	
	protected class IssueInsertQuery extends SqlUpdate {
		protected IssueInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO INS058M"
					+ "			(no, year, seqno, sabun, in_date, dept_name"
					+ "			, count_t, gubun, receive, content, gaksa)"
					+ "VALUES" + "(?, year(getdate()), ?, ?, convert(varchar(8), getdate(), 112), ?,		?, ?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		protected int insert(IssueDetailForm form) throws DataAccessException {
			return this.update(new Object[]{form.getNo(), form.getSeqno(), form.getSabun(), form.getDept_name(), form.getCount_t(), 
					form.getGubun(), form.getReceive(), form.getContent(), form.getGaksa()
			});
		}
	}
	
	public void create(IssueDetailForm issueDetailForm) throws DataAccessException {
		String seqno = String.valueOf(getJdbcTemplate().queryForInt("SELECT ISNULL( max(seqno),0)+1 FROM INS058M "));
		String no = String.valueOf(getJdbcTemplate().queryForInt("SELECT ISNULL( max(no),0)+1 FROM INS058M "));
		issueDetailForm.setSeqno(seqno);
		issueDetailForm.setNo(no);
		this.issueInsertQuery.insert(issueDetailForm);
		
	}
	
	@Override
	public String select(String ssn) throws IssueException {
		String dept_name = (String) getJdbcTemplate().queryForObject("select aliasName from smgroup a where (select dept from expertpool b where b.ssn = '" + ssn + "') = a.id", String.class);
		return dept_name;
	}
	
	@Override
	public String select2(String ssn) throws IssueException {
		String sabun = (String) getJdbcTemplate().queryForObject("select emplno from expertpool where ssn = '" + ssn +"'", String.class);
		return sabun;
	}

}
