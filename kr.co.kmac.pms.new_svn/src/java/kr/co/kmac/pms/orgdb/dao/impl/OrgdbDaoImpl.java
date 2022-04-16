/*
 * $Id: OrgdbDaoImpl.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : May 22, 2008
 * =========================================================
 * Copyright (c) 2008 ManInSoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.orgdb.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.orgdb.dao.OrgdbDao;
import kr.co.kmac.pms.orgdb.data.OrgdbDetail;
import kr.co.kmac.pms.orgdb.data.OrgdbDetailContactPoint;
import kr.co.kmac.pms.orgdb.form.OrgdbDetailForm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class OrgdbDaoImpl extends JdbcDaoSupport implements OrgdbDao {

	private OrgdbInsertQuery orgdbInsertQuery;
	private OrgdbContactPointInsertQuery orgdbContactPointInsertQuery;
	private OrgdbSpecialFieldInsertQuery orgdbSpecialFieldInsertQuery;
	private OrgdbUpdateQuery orgdbUpdateQuery;
	private OrgdbRetireveQuery orgdbRetireveQuery;
	private OrgdbDeleteQuery orgdbDeleteQuery;
	private OrgdbContactPointDeleteQuery orgdbContactPointDeleteQuery;
	private OrgdbSpecialFieldDeleteQuery orgdbSpecialFieldDeleteQuery;
	private ProjectOrgdbUpdateQuery projectOrgdbUpdateQuery;
	private CustomerOrgdbUpdateQuery customerOrgdbUpdateQuery;
	private ExpertOrgdbUpdateQuery expertOrgdbUpdateQuery;
	private OrgdbCheckUpdateQuery orgdbCheckUpdateQuery;
	private OrgdbCheckDeleteQuery orgdbCheckDeleteQuery;

	protected void initDao() throws Exception {
		this.orgdbInsertQuery = new OrgdbInsertQuery(getDataSource());
		this.orgdbContactPointInsertQuery = new OrgdbContactPointInsertQuery(getDataSource());
		this.orgdbSpecialFieldInsertQuery = new OrgdbSpecialFieldInsertQuery(getDataSource());
		this.orgdbUpdateQuery = new OrgdbUpdateQuery(getDataSource());
		this.orgdbRetireveQuery = new OrgdbRetireveQuery(getDataSource());
		this.orgdbDeleteQuery = new OrgdbDeleteQuery(getDataSource());
		this.orgdbContactPointDeleteQuery = new OrgdbContactPointDeleteQuery(getDataSource());
		this.orgdbSpecialFieldDeleteQuery = new OrgdbSpecialFieldDeleteQuery(getDataSource());
		this.projectOrgdbUpdateQuery = new ProjectOrgdbUpdateQuery(getDataSource());
		this.customerOrgdbUpdateQuery = new CustomerOrgdbUpdateQuery(getDataSource());
		this.expertOrgdbUpdateQuery = new ExpertOrgdbUpdateQuery(getDataSource());
		this.orgdbCheckUpdateQuery = new OrgdbCheckUpdateQuery(getDataSource());
		this.orgdbCheckDeleteQuery = new OrgdbCheckDeleteQuery(getDataSource());
	}

	protected class OrgdbInsertQuery extends SqlUpdate {
		protected OrgdbInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO OrgDBMain                                                                    "
					+ "      (OrgCODE, name, gusang, PM, country, ceo, EstYear, Employees, sales, phone"
					+ "       , fax, hompage, zipcode, address_01, address_02                                  "
					+ "       , businessType, category , others, writer, vcheck, writtendate, modifier, modifydate)"
					+ "VALUES" + "(?, ?, ?, ?, ?,      ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?,  "
					+ "?, ?, ?, ?, ?, getdate(), ?, getdate())");
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
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}
		protected int insert(OrgdbDetailForm form) throws DataAccessException {
			return this.update(new Object[]{form.getOrgCode(), form.getOrgName(), form.getOldName(), form.getPmName(),
					form.getNationality(), form.getRepName(), form.getEstYear(), form.getEmployeeCnt(),
					form.getSalesAmount(), form.getTelNo(), form.getFaxNo(), form.getHomepage(), form.getZipCode(),
					form.getAddress1(), form.getAddress2(), form.getBusinessType(), form.getBusinessCatogory(),
					form.getWorkComment(), form.getCreator(), "Y", form.getCreator()});
		}
	}

	protected class OrgdbContactPointInsertQuery extends BatchSqlUpdate {
		protected OrgdbContactPointInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO OrgDBContactPoint                                                         "
					+ "           (OrgCODE, MName, MJobLeve, Mbis, Mphone, Mhandphone, Mmail)            "
					+ "    VALUES           (?, ?, ?, ?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}
		protected int insert(OrgdbDetailForm form) throws DataAccessException {
			for (int i = 0; i < form.getContactName().length; i++) {
				this.update(new Object[]{form.getOrgCode(), form.getContactName()[i], form.getContactPosition()[i],
						form.getContactWork()[i], form.getContactTel()[i], form.getContactMobile()[i],
						form.getContactEmail()[i]});
			}
			return this.flush().length;
		}
	}

	protected class OrgdbSpecialFieldInsertQuery extends BatchSqlUpdate {
		protected OrgdbSpecialFieldInsertQuery(DataSource ds) {
			super(ds, "INSERT INTO OrgDBSpecialField (OrgCODE, kmacrelation, major) VALUES(?, ?, ?)   ");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}
		protected int insert(OrgdbDetailForm form) throws DataAccessException {
			String[] sfield = StringUtil.split(form.getRelWithkmac1() + "|" + form.getRelWithkmac2() + "|"
					+ form.getRelWithkmac3() + "|" + form.getRelWithkmac4(), "|");
			for (int i = 0; i < 1; i++) {
				this.update(new Object[]{form.getOrgCode(), sfield[i], form.getSpecialField1()});
			}
			return this.flush().length;
		}
	}

	protected class OrgdbUpdateQuery extends SqlUpdate {
		protected OrgdbUpdateQuery(DataSource ds) {
			super(
					ds,
					"Update OrgDBMain                                                                    "
							+ " set  name=?, gusang=?, PM=?, country=?, ceo=?, EstYear=?, Employees=?, sales=?, phone=?  "
							+ "       , fax=?, hompage=?, zipcode=?, address_01=?, address_02=?                                   "
							+ "       , businessType=?, category=?, others=?, modifier=?, vcheck=?, modifydate=getDate()       "
							+ " where orgCode = ?");
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
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.INTEGER));

			compile();
		}
		protected int update(OrgdbDetailForm form) throws DataAccessException {
			return this.update(new Object[]{form.getOrgName(), form.getOldName(), form.getPmName(),
					form.getNationality(), form.getRepName(), form.getEstYear(), form.getEmployeeCnt(),
					form.getSalesAmount(), form.getTelNo(), form.getFaxNo(), form.getHomepage(), form.getZipCode(),
					form.getAddress1(), form.getAddress2(), form.getBusinessType(), form.getBusinessCatogory(),
					form.getWorkComment(), form.getModifier(), "Y", form.getOrgCode()});
		}
	}

	protected class OrgdbRetireveQuery extends MappingSqlQuery {
		protected OrgdbRetireveQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected OrgdbRetireveQuery(DataSource ds) {
			super(ds, "select * from OrgDBMain where orgCode = ? ");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrgdbDetail detail = new OrgdbDetail();
			detail.setOrgCode(rs.getString("orgCode"));
			detail.setOrgName(rs.getString("name"));
			detail.setPmName(rs.getString("pm"));
			detail.setOldName(rs.getString("gusang"));
			detail.setNationality(rs.getString("country"));
			detail.setBusinessType(rs.getString("businessType"));
			detail.setRepName(rs.getString("ceo"));
			detail.setBusinessCatogory(rs.getString("category"));
			detail.setSalesAmount(rs.getString("sales"));
			detail.setEstYear(rs.getString("estYear"));
			detail.setTelNo(rs.getString("phone"));
			detail.setEmployeeCnt(rs.getString("Employees"));
			detail.setHomepage(rs.getString("hompage"));
			detail.setFaxNo(rs.getString("fax"));
			detail.setZipCode(rs.getString("zipcode"));
			detail.setAddress1(rs.getString("address_01"));
			detail.setAddress2(rs.getString("address_02"));
			detail.setWorkComment(rs.getString("others"));
			detail.setCreator(rs.getString("writer"));
			detail.setCreateDate(rs.getString("writtenDate"));
			detail.setModifier(rs.getString("modifier"));
			detail.setModifyDate(rs.getString("modifydate"));
			return detail;
		}
	}

	protected class OrgdbDeleteQuery extends SqlUpdate {
		protected OrgdbDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM OrgDBMain WHERE orgCode=? ");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
		protected int delete(String orgCode) throws DataAccessException {
			return this.update(new Object[]{orgCode});
		}
	}

	protected class OrgdbContactPointDeleteQuery extends SqlUpdate {
		protected OrgdbContactPointDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM OrgDBContactPoint WHERE orgCode=? ");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
		protected int delete(String orgCode) throws DataAccessException {
			return this.update(new Object[]{orgCode});
		}
	}

	protected class OrgdbSpecialFieldDeleteQuery extends SqlUpdate {
		protected OrgdbSpecialFieldDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM OrgDBSpecialField  WHERE orgCode=? ");
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
		protected int delete(String orgCode) throws DataAccessException {
			return this.update(new Object[]{orgCode});
		}

	}

	synchronized public String create(OrgdbDetailForm orgdbDetailForm) throws DataAccessException {
		String orgCode = String.valueOf(getJdbcTemplate().queryForInt("	SELECT ISNULL( max(ORGCODE),0)+1 FROM OrgDBMain "));
		orgdbDetailForm.setOrgCode(orgCode);
		this.orgdbInsertQuery.insert(orgdbDetailForm);
		this.orgdbContactPointInsertQuery.insert(orgdbDetailForm);
		this.orgdbSpecialFieldInsertQuery.insert(orgdbDetailForm);
		return orgCode;
	}

	public boolean isExist(String orgName) throws DataAccessException {
		int cnt = getJdbcTemplate().queryForInt("select count(orgCode) from orgdbMain where name = '" + orgName.trim()
				+ "'");
		return cnt == 0 ? true : false;
	}

	public OrgdbDetail retrieve(String orgCode) throws DataAccessException {
		OrgdbDetail detail = (OrgdbDetail) this.orgdbRetireveQuery.execute(new Object[]{orgCode}).get(0);
		detail.setContactList(this.getContactPoint(orgCode));
		return this.getSpecialField(detail);
	}

	public void remove(String orgCode) throws DataAccessException {
		this.orgdbSpecialFieldDeleteQuery.delete(orgCode);
		this.orgdbContactPointDeleteQuery.delete(orgCode);
		this.orgdbDeleteQuery.delete(orgCode);
	}

	public void update(OrgdbDetailForm orgdbDetailForm) throws DataAccessException {
		this.orgdbSpecialFieldDeleteQuery.delete(orgdbDetailForm.getOrgCode());
		this.orgdbContactPointDeleteQuery.delete(orgdbDetailForm.getOrgCode());
		this.orgdbContactPointInsertQuery.insert(orgdbDetailForm);
		this.orgdbSpecialFieldInsertQuery.insert(orgdbDetailForm);
		this.orgdbUpdateQuery.update(orgdbDetailForm);
	}

	private List getContactPoint(String orgCode) throws DataAccessException {
		return getJdbcTemplate().query("select * from OrgDBContactPoint where orgCode = '" + orgCode + "' ",
				new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						OrgdbDetailContactPoint point = new OrgdbDetailContactPoint();
						point.setOrgCode(rs.getString("orgCode"));
						point.setContactEmail(rs.getString("Mmail"));
						point.setContactMobile(rs.getString("Mhandphone"));
						point.setContactName(rs.getString("MName"));
						point.setContactPosition(rs.getString("MJobLeve"));
						point.setContactTel(rs.getString("Mphone"));
						point.setContactWork(rs.getString("Mbis"));
						return point;
					}
				});
	}

	private OrgdbDetail getSpecialField(OrgdbDetail orgdbDetail) throws DataAccessException {
		List sFieldList = getJdbcTemplate().queryForList("select * from OrgDBSpecialField where orgCode = '"
				+ orgdbDetail.getOrgCode() + "' ");
		for (int i = 0; i < sFieldList.size(); i++) {
			String kmacrelation = (String) ((Map) sFieldList.get(i)).get("kmacrelation");
			String specialField = (String) ((Map) sFieldList.get(i)).get("major");
			orgdbDetail.setRelWithkmac1(kmacrelation);
			orgdbDetail.setSpecialField1(specialField);
		}
		return orgdbDetail;
	}

	protected class ProjectOrgdbUpdateQuery extends SqlUpdate {
		protected ProjectOrgdbUpdateQuery(DataSource ds) {
			super(ds, " UPDATE ProjectOrgdbRel SET orgCode = ?, orgName = ? WHERE projectCode = ?  AND idx = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		public void projectUpdate(String orgCode, String orgName, String projectCode, String idx) {
			this.update(new Object[]{orgCode, orgName, projectCode, idx});
		}
	}

	protected class CustomerOrgdbUpdateQuery extends SqlUpdate {
		protected CustomerOrgdbUpdateQuery(DataSource ds) {
			super(ds, " UPDATE Customer SET customerCode = ?, customerName = ?	 WHERE idx = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		public void customerUpdate(String customerCode, String customerName, String idx) {
			this.update(new Object[]{customerCode, customerName, idx});
		}
	}

	protected class ExpertOrgdbUpdateQuery extends SqlUpdate {
		protected ExpertOrgdbUpdateQuery(DataSource ds) {
			super(ds, " UPDATE ExpertPool SET companyId = ?, company = ?	 WHERE ssn = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		public void expertUpdate(String companyId, String companyName, String ssn) {
			this.update(new Object[]{companyId, companyName, ssn});
		}
	}
	
	protected class OrgdbCheckUpdateQuery extends SqlUpdate {
		protected OrgdbCheckUpdateQuery(DataSource ds) {
			super(ds, "INSERT INTO orgdbCheck                                                                    "
					+ "      (orgCode, checkYN, checkSsn, checkDate) "
					+ "VALUES" + "(?, 'Y', ?, getdate())");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}
		protected int insert(String orgCode, String ssn) throws DataAccessException {
			return this.update(new Object[]{ orgCode, ssn });
		}
	}
	
	protected class OrgdbCheckDeleteQuery extends SqlUpdate {
		protected OrgdbCheckDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM orgdbCheck                                                                    "
					+ "WHERE orgCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}
		protected int delete(String orgCode) throws DataAccessException {
			return this.update(new Object[]{ orgCode });
		}
	}
	/**
	 * @author hyunseong
	 * @jobDate &2008. 6. 11.
	 * @description : 협력사 정보 수정을 위한 Dao 클래스
	 */
	public void projectOrgdbUpdate(String orgCode, String orgName, String projectCode, String idx)
			throws DataAccessException {
		this.projectOrgdbUpdateQuery.projectUpdate(orgCode, orgName, projectCode, idx);
	}

	public void customerOrgdbUpdate(String customerCode, String customerName, String idx) throws DataAccessException {
		this.customerOrgdbUpdateQuery.customerUpdate(customerCode, customerName, idx);
	}

	public void expertOrgdbUpdate(String companyId, String companyName, String ssn) throws DataAccessException {
		this.expertOrgdbUpdateQuery.expertUpdate(companyId, companyName, ssn);
	}
	
	public void OrgdbCheckUpdate(String orgCode, String ssn) throws DataAccessException {
		this.orgdbCheckUpdateQuery.insert(orgCode, ssn);
	}
	
	public void OrgdbCheckDelete(String orgCode) throws DataAccessException {
		this.orgdbCheckDeleteQuery.delete(orgCode);
	}

}
