package kr.co.kmac.pms.moudb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mozilla.javascript.ObjArray;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import kr.co.kmac.pms.moudb.dao.MoudbDao;
import kr.co.kmac.pms.moudb.data.MoudbDetail;
import kr.co.kmac.pms.moudb.data.MoudbDetailContactPoint;
import kr.co.kmac.pms.moudb.form.MoudbDetailForm;


public class MoudbDaoImpl extends JdbcDaoSupport implements MoudbDao {
	
	private MoudbInsertQuery moudbInsertQuery;
	private MoudbContactPointInsertQuery moudbContactPointInsertQuery;
	private MoudbUpdateQuery moudbUpdateQuery;
	private MoudbDeleteQuery moudbDeleteQuery;
	private MoudbRetireveQuery moudbRetireveQuery;
	private MoudbContactPointDeleteQuery moudbContactPointDeleteQuery;
	
	protected void initDao()throws Exception {
		this.moudbInsertQuery = new MoudbInsertQuery(getDataSource());
		this.moudbContactPointInsertQuery = new MoudbContactPointInsertQuery(getDataSource());
		this.moudbUpdateQuery = new MoudbUpdateQuery(getDataSource());
		this.moudbDeleteQuery = new MoudbDeleteQuery(getDataSource());
		this.moudbRetireveQuery = new MoudbRetireveQuery(getDataSource());
		this.moudbContactPointDeleteQuery = new MoudbContactPointDeleteQuery(getDataSource());
	}
	
	protected class MoudbInsertQuery extends SqlUpdate {
		protected MoudbInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO mou_Board2"
					+ "			(idx, writer, writer_id, regdate, updatedate, com_name, com_boss"
					+ "			 , country, com_addr, homepage, com_tel, com_special_quality"
					+ "			 , com_expert, com_cooperation, relation_level, join_project_result)"
					+ "VALUES" + "(?, ?, ?, getdate(), getdate(),?,	?, ?, ?, ?, ?,	?, ?, ?, ?, ?)	");
			declareParameter(new SqlParameter(Types.INTEGER));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
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
		protected int insert(MoudbDetailForm form) throws DataAccessException {
			return this.update(new Object[]{form.getIdx(), form.getWriter(), form.getWriter_id(), 
					form.getCom_name(), form.getCom_boss(), form.getCountry(), form.getCom_addr(), form.getHomepage(),
					form.getCom_tel(), form.getCom_special_quality(), form.getCom_expert(), form.getCom_cooperation(),
					form.getRelation_level(), form.getJoin_project_result()
			});
		}
	}
	
	protected class MoudbContactPointInsertQuery extends BatchSqlUpdate {
		protected MoudbContactPointInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO mou_person"
					+ "			(idx, mou_idx, name, grade, tel, mobile, email, job, regdate, writer_id)"
					+ "		VALUES (?, ?,	?, ?, ?, ?, ?,	?, getdate(), ?)");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		protected int insert(MoudbDetailForm form) throws DataAccessException {
			for (int i = 0; i < form.getContactName().length; i++) {
				this.update(new Object[]{form.getIdx(), form.getMou_idx(), form.getContactName()[i], form.getContactGrade()[i],
						form.getContactTel()[i], form.getContactMobile()[i], form.getContactEmail()[i], form.getContactJob()[i],
						form.getWriter_id()
				});
			}
			return this.flush().length;
		}
	}
	
	protected class MoudbUpdateQuery extends SqlUpdate {
		protected MoudbUpdateQuery(DataSource ds) {
			super(ds, "UPDATE mou_Board2"
					+ " SET updatedate=getdate(), com_name=?, com_boss=?"
					+ "		, country=?, com_addr=?, homepage=?, com_tel=?, com_special_quality=?"
					+ "		, com_expert=?, com_cooperation=?, relation_level=?, join_project_result=?"
					+ " WHERE idx=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			
			compile();
		}
		protected int update(MoudbDetailForm form) throws DataAccessException {
			return this.update(new Object[]{form.getCom_name(), form.getCom_boss(), form.getCountry(),
					form.getCom_addr(), form.getHomepage(), form.getCom_tel(), form.getCom_special_quality(),
					form.getCom_expert(), form.getCom_cooperation(), form.getRelation_level(),
					form.getJoin_project_result(), form.getIdx()});
		}
	}
	
	protected class MoudbDeleteQuery extends SqlUpdate {
		protected MoudbDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM mou_Board2 WHERE idx=?");
			declareParameter(new SqlParameter(Types.INTEGER));
			
			compile();
		}
		protected int delete(String idx) throws DataAccessException {
			return this.update(new Object[]{idx});
		}
	}
	
	protected class MoudbRetireveQuery extends MappingSqlQuery {
		protected MoudbRetireveQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected MoudbRetireveQuery(DataSource ds) {
			super(ds, "SELECT * FROM mou_Board2 WHERE idx=?");
			declareParameter(new SqlParameter(Types.INTEGER));
				
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			MoudbDetail detail = new MoudbDetail();
			detail.setIdx(rs.getString("idx"));
			detail.setWriter(rs.getString("writer"));
			detail.setWriter_id(rs.getString("writer_id"));
			detail.setRegdate(rs.getString("regdate"));
			detail.setUpdatedate(rs.getString("updatedate"));
			detail.setCom_name(rs.getString("com_name"));
			detail.setCom_boss(rs.getString("com_boss"));
			detail.setCountry(rs.getString("country"));
			detail.setCom_addr(rs.getString("com_addr"));
			detail.setHomepage(rs.getString("homepage"));
			detail.setCom_tel(rs.getString("com_tel"));
			detail.setCom_special_quality(rs.getString("com_special_quality"));
			detail.setCom_expert(rs.getString("com_expert"));
			detail.setCom_cooperation(rs.getString("com_cooperation"));
			detail.setRelation_level(rs.getString("relation_level"));
			detail.setJoin_project_result(rs.getString("join_project_result"));
			return detail;
		}
	}
	
	protected class MoudbContactPointDeleteQuery extends SqlUpdate {
		protected MoudbContactPointDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM mou_person WHERE idx=?");
			declareParameter(new SqlParameter(Types.INTEGER));
			
			compile();
		}
		protected int delete(String idx) throws DataAccessException {
			return this.update(new Object[]{idx});
		}
	}
	
	synchronized public String create(MoudbDetailForm moudbDetailForm) throws DataAccessException {
		String idx = String.valueOf(getJdbcTemplate().queryForInt(" SELECT ISNULL( max(IDX),0)+1 FROM mou_Board2 "));
		moudbDetailForm.setIdx(idx);
		this.moudbInsertQuery.insert(moudbDetailForm);
		this.moudbContactPointInsertQuery.insert(moudbDetailForm);
		return idx;
	}
	
	public boolean isExist(String com_name) throws DataAccessException {
		int cnt = getJdbcTemplate().queryForInt(" SELECT COUNT(idx) FROM mou_Board2 WHERE com_name = '"
				+ com_name.trim() + "'");
		return cnt == 0 ? true : false;
	}
	
	private List getContactPoint(String idx) throws DataAccessException {
		return getJdbcTemplate().query("SELECT * FROM mou_person WHERE idx='" + idx + "'", 
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						MoudbDetailContactPoint point = new MoudbDetailContactPoint();
						point.setIdx(rs.getString("idx"));
						point.setMou_idx(rs.getString("mou_idx"));
						point.setContactName(rs.getString("name"));
						point.setContactGrade(rs.getString("grade"));
						point.setContactTel(rs.getString("tel"));
						point.setContactMobile(rs.getString("mobile"));
						point.setContactEmail(rs.getString("email"));
						point.setContactJob(rs.getString("job"));
						return point;
					}
		});
	}

	@Override
	public void update(MoudbDetailForm moudbDetailForm) throws DataAccessException {
		this.moudbUpdateQuery.update(moudbDetailForm);
		this.moudbContactPointDeleteQuery.delete(moudbDetailForm.getIdx());
		this.moudbContactPointInsertQuery.insert(moudbDetailForm);
		
	}

	@Override
	public void remove(String idx) throws DataAccessException {
		this.moudbContactPointDeleteQuery.delete(idx);
		this.moudbDeleteQuery.delete(idx);
	}

	@Override
	public MoudbDetail retrieve(String idx) throws DataAccessException {
		MoudbDetail detail = (MoudbDetail) this.moudbRetireveQuery.execute(new Object[]{idx}).get(0);
		detail.setContactList(this.getContactPoint(idx));
		return detail;
	}
}
