package kr.co.kmac.pms.project.search.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.search.dao.HashTagDao;
import kr.co.kmac.pms.project.search.data.HashTag;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class HashTagDaoImpl extends JdbcDaoSupport implements HashTagDao {
	private HashTagInsertQuery hashTagInsertQuery;
	private HashTagSelectQuery1 hashTagSelectQuery1;
	private HashTagSelectQuery2 hashTagSelectQuery2;
	private HashTagSelectQuery3 hashTagSelectQuery3;
	private HashTagDeleteQuery1 hashTagDeleteQuery1;
	private HashTagDeleteQuery2 hashTagDeleteQuery2;
	private HashTagDeleteQuery3 hashTagDeleteQuery3;
	private RecommendInsertQuery recommendInsertQuery;
	private RecommendDeleteQuery recommendDeleteQuery;
	private RecommendCntSelectQuery recommendCntSelectQuery;
	private ExpertHashTagInsertQuery expertHashTagInsertQuery;

	@Override
	protected void initDao() throws Exception {
		this.hashTagInsertQuery = new HashTagInsertQuery(getDataSource());
		this.hashTagSelectQuery1 = new HashTagSelectQuery1(getDataSource());
		this.hashTagSelectQuery2 = new HashTagSelectQuery2(getDataSource());
		this.hashTagSelectQuery3 = new HashTagSelectQuery3(getDataSource());
		this.hashTagDeleteQuery1 = new HashTagDeleteQuery1(getDataSource());
		this.hashTagDeleteQuery2 = new HashTagDeleteQuery2(getDataSource());
		this.hashTagDeleteQuery3 = new HashTagDeleteQuery3(getDataSource());
		this.recommendInsertQuery = new RecommendInsertQuery(getDataSource());
		this.recommendCntSelectQuery = new RecommendCntSelectQuery(getDataSource());
		this.recommendDeleteQuery = new RecommendDeleteQuery(getDataSource());
		this.expertHashTagInsertQuery = new ExpertHashTagInsertQuery(getDataSource());
	}

	protected class HashTagInsertQuery extends SqlUpdate {
		protected HashTagInsertQuery(DataSource ds) {
			super(ds,
					"INSERT INTO ProjectHashtag                               "
							+ "	(uuid                                                   "
							+ "	,projectCode, hashTag, isShow, tagType					"
							+ " ,createDate, createrSsn)                                "
							+ "	VALUES(?,     ?, ?, ?, ?,    getDate(), ?)				");
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(HashTag hashTag) throws DataAccessException {
			return this.update(new Object[] { 
					hashTag.getUuid(),
					hashTag.getProjectCode(), hashTag.getHashTag(), hashTag.getIsShow(), hashTag.getTagType(), 
					hashTag.getCreaterSsn() });
		}
	}
	
	protected class ExpertHashTagInsertQuery extends SqlUpdate {
		protected ExpertHashTagInsertQuery(DataSource ds) {
			super(ds,
					"INSERT INTO ExpertHashtag                               "
							+ "	(uuid                                                   "
							+ "	,expertSsn, hashTag, isShow, tagType					"
							+ " ,createDate, createrSsn)                                "
							+ "	VALUES(?,     ?, ?, ?, ?,    getDate(), ?)				");
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(HashTag hashTag) throws DataAccessException {
			return this.update(new Object[] { 
					hashTag.getUuid(),
					hashTag.getExpertSsn(), hashTag.getHashTag(), hashTag.getIsShow(), hashTag.getTagType(), 
					hashTag.getCreaterSsn() });
		}
	}

	protected class HashTagDeleteQuery1 extends SqlUpdate {
		protected HashTagDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectHashtag WHERE projectCode=?	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class HashTagDeleteQuery2 extends SqlUpdate {
		protected HashTagDeleteQuery2(DataSource ds) {
			super(ds,
					" DELETE FROM ProjectHashtag WHERE projectCode=? and uuid=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode, String uuid)
				throws DataAccessException {
			return this.update(new Object[] { projectCode, uuid });
		}
	}
	
	protected class HashTagDeleteQuery3 extends SqlUpdate {
		protected HashTagDeleteQuery3(DataSource ds) {
			super(ds,
					" DELETE FROM ExpertHashTag WHERE expertSsn=? and uuid=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String expertSsn, String uuid)
				throws DataAccessException {
			return this.update(new Object[] { expertSsn, uuid });
		}
	}

	protected class RecommendCntSelectQuery extends MappingSqlQuery {
		protected RecommendCntSelectQuery(DataSource ds) {
			super(ds,
					"	select likeSsn as recCnt from recommendCnt where bbsid=? and seq = ? and likeSsn = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected RecommendCntSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			HashTag hashTag = new HashTag();
			hashTag.setRecCnt(rs.getString("recCnt"));
			return hashTag;
		}
	}
	
	protected class HashTagSelectQuery1 extends MappingSqlQuery {
		protected HashTagSelectQuery1(DataSource ds) {
			super(ds,
					"	SELECT 	* FROM ProjectHashtag where projectCode=? and isShow = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected HashTagSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			HashTag hashTag = new HashTag();
			hashTag.setUuid(rs.getString("uuid"));
			hashTag.setProjectCode(rs.getString("projectCode"));
			hashTag.setHashTag(rs.getString("hashTag"));
			hashTag.setIsShow(rs.getString("isShow"));
			hashTag.setTagType(rs.getString("tagType"));
			hashTag.setCreateDate(rs.getDate("createDate"));
			hashTag.setCreaterSsn(rs.getString("createrSsn"));
			return hashTag;
		}
	}

	protected class HashTagSelectQuery2 extends HashTagSelectQuery1 {
		protected HashTagSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	* FROM ProjectHashtag WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	/*¿©±â ÇÒ Â÷·Ê*/
	protected class HashTagSelectQuery3 extends MappingSqlQuery {
		protected HashTagSelectQuery3(DataSource ds) {
			super(ds,
					"	SELECT 	* FROM ExpertHashtag where expertSsn=? and isShow = ? and tagType = 'ByUser' ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			HashTag hashTag = new HashTag();
			hashTag.setUuid(rs.getString("uuid"));
			hashTag.setProjectCode(rs.getString("expertSsn"));
			hashTag.setHashTag(rs.getString("hashTag"));
			hashTag.setIsShow(rs.getString("isShow"));
			hashTag.setTagType(rs.getString("tagType"));
			hashTag.setCreateDate(rs.getDate("createDate"));
			hashTag.setCreaterSsn(rs.getString("createrSsn"));
			return hashTag;
		}
	}
	
	protected class RecommendInsertQuery extends SqlUpdate {
		protected RecommendInsertQuery(DataSource ds) {
			super(ds,
					"INSERT INTO recommendCnt                               "
							+ "	(bbsid, seq, likeSsn, likeYN)                                                   "
							+ "	VALUES(?, ?, ?, 'Y')				");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(HashTag hashTag) throws DataAccessException {
			return this.update(new Object[] { 
					hashTag.getBbsId(), hashTag.getSeq(), hashTag.getCreaterSsn() });
		}
	}
	
	protected class RecommendDeleteQuery extends SqlUpdate {
		protected RecommendDeleteQuery(DataSource ds) {
			super(ds,
					" DELETE FROM recommendCnt WHERE bbsid=? and seq=? and likeSsn=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(HashTag hashTag)
				throws DataAccessException {
			return this.update(new Object[] { hashTag.getBbsId(), hashTag.getSeq(), hashTag.getCreaterSsn() });
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HashTag> select(String projectCode, String isShow)
			throws DataAccessException {
		return this.hashTagSelectQuery1.execute(new Object[] { projectCode, isShow });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HashTag> select2(String bbsId, String seq, String ssn)throws DataAccessException {
		return this.recommendCntSelectQuery.execute(new Object[] { bbsId, seq, ssn });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HashTag> select(String projectCode) throws DataAccessException {
		return this.hashTagSelectQuery2.execute(new Object[] { projectCode });
	}
	
	@SuppressWarnings("unckecked")
	@Override
	public List<HashTag> select3(String expertSsn, String isShow) throws DataAccessException {
		return this.hashTagSelectQuery3.execute(new Object[] {expertSsn, isShow});
	}

	@Override
	public int insert(HashTag hashTag) throws DataAccessException {
		return this.hashTagInsertQuery.insert(hashTag);
	}

	@Override
	public int insert2(HashTag hashTag) throws DataAccessException {
		return this.recommendInsertQuery.insert(hashTag);
	}
	@Override
	public int insert3(HashTag hashTag) throws DataAccessException {
		return this.expertHashTagInsertQuery.insert(hashTag);
	}
	
	@Override
	public int delete2(HashTag hashTag) throws DataAccessException {
		return this.recommendDeleteQuery.delete(hashTag);
	}

	@Override
	public int delete(String projectCode) throws DataAccessException {
		return this.hashTagDeleteQuery1.delete(projectCode);
	}

	@Override
	public int delete(String projectCode, String uuid) throws DataAccessException {
		return this.hashTagDeleteQuery2.delete(projectCode, uuid);
	}
	
	@Override
	public int delete3(String expertSsn, String uuid) throws DataAccessException {
		return this.hashTagDeleteQuery3.delete(expertSsn, uuid);
	}

}