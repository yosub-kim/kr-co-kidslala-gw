package kr.co.kmac.pms.system.gadget.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.system.gadget.dao.GadgetDao;
import kr.co.kmac.pms.system.gadget.data.Content;
import kr.co.kmac.pms.system.gadget.data.Gadget;
import kr.co.kmac.pms.system.gadget.data.MyGadget;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class GadgetDaoImpl extends JdbcDaoSupport implements GadgetDao {

	private MyGadgetList myGadgetList;
	private MyGadgetListByRole myGadgetListByRole;
	private MyMobileGadgetListByRole myMobileGadgetListByRole;
	private MyWideGadgetList myWideGadgetList;
	private GadgetRetrieve gadgetRetrieve;
	private GadgetRetrieveMore gadgetRetrieveMore;
	private GadgetCreate gadgetCreate;
	private GadgetUpdate gadgetUpdate;
	private GadgetDelete gadgetDelete;
	private MyGadgetAppend myGadgetAppend;
	private MyGadgetModify myGadgetModify;
	private MyGadgetDelete myGadgetDelete;
	private NotSelectedGadgetList notSelectedGadgetList;
	private GadgetMaxOrdSeq gadgetMaxOrdSeq;

	// MyGadGet 리스트
	protected class MyGadgetList extends MappingSqlQuery {
		protected MyGadgetList(DataSource ds, String query) {
			super(ds, query);
		}

		protected MyGadgetList(DataSource ds) {
			super(ds, " SELECT		A.gadgetId 								" 
					+ "				, A.gadgetName							" 
					+ "				, A.gadgetRole							" 
					+ "				, A.tableHeader							" 
					+ "				, A.sqlText								" 
					+ "				, A.linkUrl								"
					+ "				, ISNULL(B.ordseq, A.ordSeq) AS ordSeq	" 
					+ "				, A.fixedYN								" 
					+ "				, A.gadgetType		, A.useYN			" 
					+ " FROM	GadGet AS A LEFT OUTER JOIN	"
					+ "		(SELECT	userId, gadgetId, ordseq	" 
					+ "         FROM	MyGadGet	" 
					+ "         WHERE      (userId = ?)) AS B	"
					+ "			ON A.gadgetId = B.gadgetId	" 
					+ " WHERE	(A.useYN = 'Y') AND (A.fixedYN = 'Y') AND (A.gadgetType like ? ) AND (B.userId IS NOT NULL)	" 
					+ " ORDER BY ordSeq ");

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Gadget gadget = new Gadget();
			gadget.setGadgetId(rs.getString("gadgetId"));
			gadget.setGadgetName(rs.getString("gadgetName"));
			gadget.setSqlText(rs.getString("sqlText"));
			gadget.setLinkUrl(rs.getString("linkUrl"));
			gadget.setOrdSeq(rs.getInt("ordSeq"));
			gadget.setFixedYN(rs.getString("fixedYN"));
			gadget.setGadgetType(rs.getString("gadgetType"));
			gadget.setUseYN(rs.getString("useYN"));
			gadget.setGadgetRole(rs.getString("gadgetRole"));
			gadget.setTableHeader(rs.getString("tableHeader"));
			return gadget;
		}
	}
	
	// MyGadGetByRole 리스트
	protected class MyGadgetListByRole extends MappingSqlQuery {
		protected MyGadgetListByRole(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected MyGadgetListByRole(DataSource ds) {
			super(ds, " SELECT		A.gadgetId 					" 
					+ "				, A.gadgetName				" 
					+ "				, A.gadgetRole				" 
					+ "				, A.tableHeader				" 
					+ "				, A.sqlText					" 
					+ "				, A.linkUrl					"
					+ "				, A.ordSeq					" 
					+ "				, A.fixedYN					" 
					+ "				, A.gadgetType				"
					+ "				, A.useYN					" 
					+ " FROM	NewGadGet AS A						" 
					+ " WHERE	(A.gadgetRole = ?) AND (A.useYN = 'Y') AND (A.fixedYN = 'Y') AND (A.gadgetType like ? )	" 
					+ " ORDER BY ordSeq ");
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Gadget gadget = new Gadget();
			gadget.setGadgetId(rs.getString("gadgetId"));
			gadget.setGadgetName(rs.getString("gadgetName"));
			gadget.setSqlText(rs.getString("sqlText"));
			gadget.setLinkUrl(rs.getString("linkUrl"));
			gadget.setOrdSeq(rs.getInt("ordSeq"));
			gadget.setFixedYN(rs.getString("fixedYN"));
			gadget.setGadgetType(rs.getString("gadgetType"));
			gadget.setUseYN(rs.getString("useYN"));
			gadget.setGadgetRole(rs.getString("gadgetRole"));
			gadget.setTableHeader(rs.getString("tableHeader"));
			return gadget;
		}
	}
	
	protected class MyMobileGadgetListByRole extends MappingSqlQuery {
		protected MyMobileGadgetListByRole(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected MyMobileGadgetListByRole(DataSource ds) {
			super(ds, " SELECT		A.gadgetId 					" 
					+ "				, A.gadgetName				" 
					+ "				, A.gadgetRole				" 
					+ "				, A.tableHeader				" 
					+ "				, A.sqlText					" 
					+ "				, A.linkUrl					"
					+ "				, A.ordSeq					" 
					+ "				, A.fixedYN					" 
					+ "				, A.gadgetType				"
					+ "				, A.useYN					" 
					+ " FROM	NewGadGet AS A						" 
					+ " WHERE	(A.gadgetRole = ?) AND (A.useYN = 'Y') AND (A.fixedYN = 'Y')	" 
					+ " ORDER BY ordSeq ");
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Gadget gadget = new Gadget();
			gadget.setGadgetId(rs.getString("gadgetId"));
			gadget.setGadgetName(rs.getString("gadgetName"));
			gadget.setSqlText(rs.getString("sqlText"));
			gadget.setLinkUrl(rs.getString("linkUrl"));
			gadget.setOrdSeq(rs.getInt("ordSeq"));
			gadget.setFixedYN(rs.getString("fixedYN"));
			gadget.setGadgetType(rs.getString("gadgetType"));
			gadget.setUseYN(rs.getString("useYN"));
			gadget.setGadgetRole(rs.getString("gadgetRole"));
			gadget.setTableHeader(rs.getString("tableHeader"));
			return gadget;
		}
	}

	// MyGadGet 리스트
	protected class MyWideGadgetList extends MappingSqlQuery {
		protected MyWideGadgetList(DataSource ds, String query) {
			super(ds, query);
		}

		protected MyWideGadgetList(DataSource ds) {
			super(ds, " SELECT     A.gadgetId " 
					+ "		, A.gadgetName	"
					+ "		, A.gadgetRole	" 
					+ "		, A.tableHeader	"  
					+ "		, A.sqlText		" 
					+ "		, A.linkUrl		"
					+ "		, ISNULL(B.ordseq, A.ordSeq) AS ordSeq	" 
					+ "		, A.fixedYN		" 
					+ ", A.gadgetType		, A.useYN		" 
					+ " FROM	GadGet AS A LEFT OUTER JOIN	"
					+ "		(SELECT	userId, gadgetId, ordseq	"
					+ "         FROM	MyGadGet	"
					+ "         WHERE      (userId = ?)) AS B	"
					+ "			ON A.gadgetId = B.gadgetId	"
					+ "	WHERE (A.useYN = 'Y') AND (A.gadgetType ='B' ) AND (B.userId IS NOT NULL)	" 
					+ " ORDER BY ordSeq ");

			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Gadget gadget = new Gadget();
			gadget.setGadgetId(rs.getString("gadgetId"));
			gadget.setGadgetName(rs.getString("gadgetName"));
			gadget.setSqlText(rs.getString("sqlText"));
			gadget.setLinkUrl(rs.getString("linkUrl"));
			gadget.setOrdSeq(rs.getInt("ordSeq"));
			gadget.setFixedYN(rs.getString("fixedYN"));
			gadget.setGadgetType(rs.getString("gadgetType"));
			gadget.setUseYN(rs.getString("useYN"));
			gadget.setGadgetRole(rs.getString("gadgetRole"));
			gadget.setTableHeader(rs.getString("tableHeader"));

			return gadget;
		}
	}
	
	// Not Selected 리스트
	protected class NotSelectedGadgetList extends MappingSqlQuery {
		protected NotSelectedGadgetList(DataSource ds, String query) {
			super(ds, query);
		}

		protected NotSelectedGadgetList(DataSource ds) {
			super(ds, " SELECT     A.gadgetId						" 
					+ " 	, A.gadgetName							"
					+ "		, A.gadgetRole							" 
					+ "		, A.tableHeader							"   
					+ " 	, A.sqlText								" 
					+ " 	, A.linkUrl								"
					+ " 	, ISNULL(B.ordseq, A.ordSeq) AS ordSeq	" 
					+ " 	, A.fixedYN								"
					+ " 	, A.useYN	, A.gadgetType				"
					+ " FROM	GadGet AS A LEFT OUTER JOIN			" 
					+ "		(SELECT	userId, gadgetId, ordseq		"
					+ "        FROM	MyGadGet						"
					+ "        WHERE      (userId = ?)) AS B		" 
					+ " 		ON A.gadgetId = B.gadgetId			" 
					+ " WHERE	(A.useYN = 'Y')						"
					+ " 		AND (A.fixedYN = 'N')				" 
					+ " 		AND (B.userId IS NULL)				" 
					+ " ORDER BY ordSeq ");

			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Gadget gadget = new Gadget();
			gadget.setGadgetId(rs.getString("gadgetId"));
			gadget.setGadgetName(rs.getString("gadgetName"));
			gadget.setSqlText(rs.getString("sqlText"));
			gadget.setLinkUrl(rs.getString("linkUrl"));
			gadget.setOrdSeq(rs.getInt("ordSeq"));
			gadget.setFixedYN(rs.getString("fixedYN"));
			gadget.setGadgetType(rs.getString("gadgetType"));
			gadget.setUseYN(rs.getString("fixedYN"));
			gadget.setGadgetRole(rs.getString("gadgetRole"));
			gadget.setTableHeader(rs.getString("tableHeader"));

			return gadget;
		}
	}

	// GadGet 리스트
	protected class GadgetRetrieve extends MappingSqlQuery {
		protected GadgetRetrieve(DataSource ds, String query) {
			super(ds, query);
		}

		protected GadgetRetrieve(DataSource ds) {
			super(ds, " SELECT  gadgetId " 
					+ "		, gadgetName	" 
					+ "		, gadgetRole	"
					+ "		, tableHeader	"
					+ "		, sqlText		" 
					+ "		, linkUrl		" 
					+ "		, ordSeq		" 
					+ "		, fixedYN		" 
					+ "		, useYN	, gadgetType	"
					+ " FROM	NewGadGet "
					+ " WHERE (gadgetId LIKE ?) " 
					+ " ORDER BY ordSeq ");

			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Gadget gadget = new Gadget();
			gadget.setGadgetId(rs.getString("gadgetId"));
			gadget.setGadgetName(rs.getString("gadgetName"));
			gadget.setSqlText(rs.getString("sqlText"));
			gadget.setLinkUrl(rs.getString("linkUrl"));
			gadget.setOrdSeq(rs.getInt("ordSeq"));
			gadget.setFixedYN(rs.getString("fixedYN"));
			gadget.setUseYN(rs.getString("useYN"));
			gadget.setGadgetType(rs.getString("gadgetType"));
			gadget.setGadgetRole(rs.getString("gadgetRole"));
			gadget.setTableHeader(rs.getString("tableHeader"));

			return gadget;
		}
	}
	
	// GadGet 리스트
		protected class GadgetRetrieveMore extends MappingSqlQuery {
			protected GadgetRetrieveMore(DataSource ds, String query) {
				super(ds, query);
			}

			protected GadgetRetrieveMore(DataSource ds) {
				super(ds, " SELECT  gadgetId 			" 
						+ "		, gadgetName			" 
						+ "		, gadgetRole			"
						+ "		, tableHeader			"
						+ "		, sqlText				" 
						+ "		, linkUrl				" 
						+ "		, ordSeq				" 
						+ "		, fixedYN				" 
						+ "		, useYN	, gadgetType	"
						+ " FROM	NewGadGet 				"
						+ " WHERE (gadgetId LIKE ?) 	"
						+ " AND	  (gadgetRole LIKE ?)	"
						+ " AND	  (gadgetName LIKE ?)	"
						+ " ORDER BY ordSeq ");

				declareParameter(new SqlParameter(Types.VARCHAR));
				declareParameter(new SqlParameter(Types.VARCHAR));
				declareParameter(new SqlParameter(Types.VARCHAR));
				compile();
			}

			protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Gadget gadget = new Gadget();
				gadget.setGadgetId(rs.getString("gadgetId"));
				gadget.setGadgetName(rs.getString("gadgetName"));
				gadget.setSqlText(rs.getString("sqlText"));
				gadget.setLinkUrl(rs.getString("linkUrl"));
				gadget.setOrdSeq(rs.getInt("ordSeq"));
				gadget.setFixedYN(rs.getString("fixedYN"));
				gadget.setUseYN(rs.getString("useYN"));
				gadget.setGadgetType(rs.getString("gadgetType"));
				gadget.setGadgetRole(rs.getString("gadgetRole"));
				gadget.setTableHeader(rs.getString("tableHeader"));

				return gadget;
			}
		}
	
	// Max ordSeq 구하기
	protected class GadgetMaxOrdSeq extends MappingSqlQuery {
		protected GadgetMaxOrdSeq(DataSource ds, String query) {
			super(ds, query);
		}

		protected GadgetMaxOrdSeq(DataSource ds) {
			super(ds, " SELECT  IsNULL(MAX(ordSeq),20) AS mxOrdSeq "
					 + " FROM	MyGadGet " 
					 + " WHERE (userId = ?) ");

			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			int mxOrdSeq = rs.getInt("mxOrdSeq");

			return mxOrdSeq;
		}
	}

	// 새로운 가젯 생성
	protected class GadgetCreate extends SqlUpdate {
		protected GadgetCreate(DataSource ds) {
			super(ds, 
					" INSERT INTO NewGadGet " +
					"	( " +
					"		gadgetId  ,gadgetName ,sqlText ,linkUrl ,ordSeq ,fixedYN , gadgetType ,useYN, gadgetRole, tableHeader " +
					"	)  " +
					" VALUES" +
					"	( " +
					"		 dbo.getGadgetId() , ?  , ?  , ? , ?  , ?  ,  ?,  ?,  ?,  ? " +
					"	)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int create(Gadget gadget) throws DataAccessException {
			return this.update(new Object[] { gadget.getGadgetName(), gadget.getSqlText(), gadget.getLinkUrl(), gadget.getOrdSeq(),
					gadget.getFixedYN(), gadget.getGadgetType() , gadget.getUseYN(), gadget.getGadgetRole(), gadget.getTableHeader() });
		}
	}

	// 가젯 수정
	protected class GadgetUpdate extends SqlUpdate {
		protected GadgetUpdate(DataSource ds) {
			super(ds, 
					" UPDATE	NewGadGet	" + 
					" 	SET	gadgetName = ?	" +
					"		, sqlText  = ?	" +
					"		, linkUrl  = ?	" +
					"		, ordSeq   = ?	" +
					" 		, fixedYN  = ?	" +
					"		, gadgetType = ?" +
					"		, useYN    = ?	" + 
					"		, gadgetRole    = ?	" + 
					"		, tableHeader    = ?	" + 
					" WHERE	(gadgetId = ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(Gadget gadget) throws DataAccessException {
			return this.update(new Object[] { gadget.getGadgetName(), gadget.getSqlText(), gadget.getLinkUrl(), gadget.getOrdSeq(),
					gadget.getFixedYN(), gadget.getGadgetType() , gadget.getUseYN(), gadget.getGadgetRole(), gadget.getTableHeader(), gadget.getGadgetId() });
		}
	}
	
	// 가젯 삭제
	protected class GadgetDelete extends SqlUpdate {
		protected GadgetDelete(DataSource ds) {
			super(ds, 
					" DELETE	NewGadGet	" + 
					" WHERE	(gadgetId = ?) and (gadgetRole = ?) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int delete(Gadget gadget) throws DataAccessException {
			return this.update(new Object[] { gadget.getGadgetId(), gadget.getGadgetRole() });
		}
	}

	// My가젯 추가
	protected class MyGadgetAppend extends SqlUpdate {
		protected MyGadgetAppend(DataSource ds) {
			super(ds, "INSERT INTO MyGadGet ( userId ,gadgetId ,ordseq) "
					+ " VALUES ( ? , ? , ? )");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected int create(MyGadget myGadget) throws DataAccessException {
			return this.update(new Object[] { myGadget.getUserId(), myGadget.getGadgetId(), myGadget.getOrdSeq() });
		}
	}

	// My가젯 순서변경
	protected class MyGadgetModify extends SqlUpdate {
		protected MyGadgetModify(DataSource ds) {
			super(ds, " UPDATE    MyGadGet " + " SET     ordseq = ? " + " WHERE (userId = ?) AND (gadgetId = ?)");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(MyGadget myGadget) throws DataAccessException {
			return this.update(new Object[] { myGadget.getOrdSeq(), myGadget.getUserId(), myGadget.getGadgetId() });
		}
	}

	// My가젯 삭제
	protected class MyGadgetDelete extends SqlUpdate {
		protected MyGadgetDelete(DataSource ds) {
			super(ds, " DELETE    MyGadGet " + " WHERE (userId = ?) AND (gadgetId = ?)");

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(MyGadget myGadget) throws DataAccessException {
			return this.update(new Object[] { myGadget.getUserId(), myGadget.getGadgetId() });
		}
	}

	// 초기화
	protected void initDao() throws Exception {
		myGadgetList = new MyGadgetList(getDataSource());
		myGadgetListByRole = new MyGadgetListByRole(getDataSource());
		myMobileGadgetListByRole = new MyMobileGadgetListByRole(getDataSource());
		myWideGadgetList = new MyWideGadgetList(getDataSource());
		gadgetRetrieve = new GadgetRetrieve(getDataSource());
		gadgetRetrieveMore = new GadgetRetrieveMore(getDataSource());
		gadgetCreate = new GadgetCreate(getDataSource());
		gadgetUpdate = new GadgetUpdate(getDataSource());
		gadgetDelete = new GadgetDelete(getDataSource());
		myGadgetAppend = new MyGadgetAppend(getDataSource());
		myGadgetModify = new MyGadgetModify(getDataSource());
		myGadgetDelete = new MyGadgetDelete(getDataSource());
		notSelectedGadgetList = new NotSelectedGadgetList(getDataSource());
		gadgetMaxOrdSeq = new GadgetMaxOrdSeq(getDataSource());
	}

	public List<Gadget> getMyGadgetList(String userId, String gadgetType) throws DataAccessException {
		return myGadgetList.execute(new Object[]{userId, gadgetType});
	}
	
	public List<Gadget> getMyGadgetListByRole(String role, String gadgetType) throws DataAccessException {
		return myGadgetListByRole.execute(new Object[]{role, gadgetType});
	}
	
	public List<Gadget> getMyMobileGadgetListByRole(String role) throws DataAccessException{
		return myMobileGadgetListByRole.execute(new Object[]{role});
	}

	public List<Gadget> getMyWideGadgetList(String userId) throws DataAccessException {
		return myWideGadgetList.execute(userId);
	}
	
	public List<Gadget> getGadgetList(String gadgetId) throws DataAccessException {
		return gadgetRetrieve.execute(gadgetId);
	}
	
	public List<Gadget> getGadgetList(String gadgetId, String gadgetRole, String gadgetName) throws DataAccessException {
		return gadgetRetrieveMore.execute(new Object[]{gadgetId, gadgetRole, gadgetName});
	}

	public List<Gadget> getNotSelectGadgetList(String userId) throws DataAccessException {
		return notSelectedGadgetList.execute(userId);
	}

	public void createGadget(Gadget gadget) throws DataAccessException {
		gadgetCreate.create(gadget);
	}
	
	public int getMaxOrdSeq(String userId) throws DataAccessException {
		return Integer.parseInt(gadgetMaxOrdSeq.execute(userId).get(0).toString());
	}
	
	public void updateGadget(Gadget gadget) throws DataAccessException {
		gadgetUpdate.update(gadget);
	}

	public void deleteGadget(Gadget gadget) throws DataAccessException {
		gadgetDelete.delete(gadget);
	}
	
	public void appendMyGadget(MyGadget myGadget) throws DataAccessException {
		myGadgetAppend.create(myGadget);
	}

	public void updateMyGadget(MyGadget myGadget) throws DataAccessException {
		myGadgetModify.update(myGadget);
	}

	public void deleteMyGadget(MyGadget myGadget) throws DataAccessException {
		myGadgetDelete.delete(myGadget);
	}

	// 컨테츠 리스트
	@SuppressWarnings("unchecked")
	public List<Content> getContentList(String sqlText) throws DataAccessException {
		return getJdbcTemplate().query(sqlText, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Content content = new Content();
				try {content.setInfo0(rs.getString("info0"));} catch (Exception e) {}
				try {content.setInfo1(rs.getString("info1"));} catch (Exception e) {}
				try {content.setInfo2(rs.getString("info2"));} catch (Exception e) {}
				try {content.setInfo3(rs.getString("info3"));} catch (Exception e) {}
				try {content.setInfo4(rs.getString("info4"));} catch (Exception e) {}
				try {content.setInfo5(rs.getString("info5"));} catch (Exception e) {}
				try {content.setInfo6(rs.getString("info6"));} catch (Exception e) {}
				try {content.setInfo7(rs.getString("info7"));} catch (Exception e) {}
				try {content.setInfo8(rs.getString("info8"));} catch (Exception e) {}
				try {content.setInfo9(rs.getString("info9"));} catch (Exception e) {}
				try {content.setInfo0Style(rs.getString("info0Style"));} catch (Exception e) {}
				try {content.setInfo1Style(rs.getString("info1Style"));} catch (Exception e) {}
				try {content.setInfo2Style(rs.getString("info2Style"));} catch (Exception e) {}
				try {content.setInfo3Style(rs.getString("info3Style"));} catch (Exception e) {}
				try {content.setInfo4Style(rs.getString("info4Style"));} catch (Exception e) {}
				try {content.setInfo5Style(rs.getString("info5Style"));} catch (Exception e) {}
				try {content.setInfo6Style(rs.getString("info6Style"));} catch (Exception e) {}
				try {content.setInfo7Style(rs.getString("info7Style"));} catch (Exception e) {}
				try {content.setInfo8Style(rs.getString("info8Style"));} catch (Exception e) {}
				try {content.setInfo9Style(rs.getString("info9Style"));} catch (Exception e) {}
				try {content.setInfo0Style2(rs.getString("info0Style2"));} catch (Exception e) {}
				try {content.setInfo1Style2(rs.getString("info1Style2"));} catch (Exception e) {}
				try {content.setInfo2Style2(rs.getString("info2Style2"));} catch (Exception e) {}
				try {content.setInfo3Style2(rs.getString("info3Style2"));} catch (Exception e) {}
				try {content.setInfo4Style2(rs.getString("info4Style2"));} catch (Exception e) {}
				try {content.setInfo5Style2(rs.getString("info5Style2"));} catch (Exception e) {}
				try {content.setInfo6Style2(rs.getString("info6Style2"));} catch (Exception e) {}
				try {content.setLinkUrl(rs.getString("linkUrl"));} catch (Exception e) {}
				try {content.setSpecialStyle(rs.getString("specialStyle"));} catch (Exception e) {}
				return content;
			}
		});
	}
}