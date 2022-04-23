/*
 * $Id: ExpertPoolDaoImpl.java,v 1.69 2019/03/05 00:55:03 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 1. 17
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolCareerHstDao;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolDao;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolSchoolHstDao;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolSpecialFieldDao;
import kr.co.kmac.pms.expertpool.data.BankAccount;
import kr.co.kmac.pms.expertpool.data.CustermGroup;
import kr.co.kmac.pms.expertpool.data.CustermRole;
import kr.co.kmac.pms.expertpool.data.ExpertPool;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: ExpertPoolDaoImpl.java,v 1.69 2019/03/05 00:55:03 cvs Exp $
 */
public class ExpertPoolDaoImpl extends JdbcDaoSupport implements ExpertPoolDao {

	private ExpertPoolInsertQuery expertPoolInsertQuery;
	private ExpertPoolUpdateQuery expertPoolUpdateQuery;
	private ExpertPoolUpdateQuery2 expertPoolUpdateQuery2;	// Job Date: 2018-08-19	Author: yhyim	Description: update expertPool with password
	private RestrictUserUpdateQuery restrictUserUpdateQuery;
	private RestrictUserUpdateQuery2 restrictUserUpdateQuery2;
	private ExpertPoolDeleteQuery expertPoolDeleteQuery;
	private ExpertPoolRetireveQuery expertPoolRetireveQuery;
	private ExpertPoolRetireveQuery2 expertPoolRetireveQuery2;
	private ExpertPoolRetireveQuery3 expertPoolRetireveQuery3;
	private ExpertPoolRetireveByRoleQuery expertPoolByRole;
	private ExpertPoolFindQuery expertPoolFindQuery;
	private BankAccountKMACRetireveQuery bankAccountKMACRetireveQuery;
	private BankAccountNonKMACRetireveQuery bankAccountNonKMACRetireveQuery;
	private SMGroupFindQuery smGroupFindQuery;
	private CustermAddUserMemberQuery custermAddUserMemberQuery;
	private CustermRemoveUserMemberQuery custermRemoveUserMemberQuery;
	private SMRoleFindQuery smRoleFindQuery;
	private CustermAddUserRoleQuery custermAddUserRoleQuery;
	private CustermRemoveUserRoleQuery custermRemoveUserRoleQuery;
	private ExpertPoolUserIdQuery expertPoolUserIdQuery;
	private SMBUGroupFindQuery smBUGroupFindQuery;	// Job Date: 2007-09-05	Author: yhyim	Description: classify BU and PU
	private SMPUGroupFindQuery smPUGroupFindQuery;	// Job Date: 2007-09-05	Author: yhyim	Description: classify BU and PU
	private ExpertPoolHistoryInsertQuery expertPoolHistoryInsertQuery;	// Job Date: 2008-01-11	Description: insert dept history 
	private ExpertPoolHistoryUpdateQuery expertPoolHistoryUpdateQuery;	// Job Date: 2008-01-11	Description: update dept history
	private ExpertSanctionLineInsertQuery expertSanctionLineInsertQuery;	// Job Date: 2010-07-05	Description: insert sanction line
	private ExpertSanctionLineUpdateQuery expertSanctionLineUpdateQuery;	// Job Date: 2010-07-05	Description: update sanction line
	private ExpertPoolAccountHistoryInsertQuery expertPoolAccountHistoryInsertQuery;
	private SMBURoleFindQuery smBURoleFindQuery;
	private SMPURoleFindQuery smPURoleFindQuery;
	private MenuRoleFindQuery menuRoleFindQuery;
	private ExpertPoolRetireveNameQuery expertPoolRetireveNameQuery;
	private ExpertPoolRetireveUidQuery expertPoolRetireveUidQuery;
	
	private EduSystemAccountUpdateQuery eduSystemAccountUpdateQuery;
	
	private ExpertPoolCareerHstDao expertPoolCareerHstDao;
	private ExpertPoolSchoolHstDao expertPoolSchoolHstDao;
	private ExpertPoolSpecialFieldDao expertPoolSpecialFieldDao;
	
	private DailyDownloadLimitInsertQuery dailyDownloadLimitInsertQuery;
	private DailyDownloadLimitUpdateQuery dailyDownloadLimitUpdateQuery;
	private DailyDownloadLimitStateUpdateQuery dailyDownloadLimitStateUpdateQuery;
	private DownloadLimitInitQuery downloadLimitInitQuery; 
	private BudgetCheckInsertQuery budgetCheckInsertQuery;
	private DailyStoreHomeTaxValue dailyStoreHomeTaxValue;
	
	private PasswordResetQuery passwordResetQuery;
	
	private WorkTimeInsertQuery workTimeInsertQuery;
	
	private DataSource erpDataSource;// 회계 테이터 베이스 접근

	protected class ExpertPoolInsertQuery extends SqlUpdate {
		protected ExpertPoolInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ExpertPool(                                                                       "
					+ "		uid, ssn, userId, name, chnName, engName, password, enable,                               "
					+ "     gender, nationality,telNo, mobileNo, zipCode,                                             "
					+ "		address1, address2, companyId, company, dept,                                             "
					+ "     deptName, companyPosition, companyPositionName, jobClass, companyZipcode,                 "
					+ "     companyAddress1, companyAddress2, companyTelNo, companyFaxNo, email,                      "
					+ "     photo, finalMajor, langSkill1, langSkill1Level, langSkill2,                               "
					+ "     langSkill2Level, langSkill3, langSkill3Level, essayTitle, indField,                       "
					+ "     funcField, consultingMajor, consultingMinor, consultingDetail, researchEassy,             "
					+ "     publishedBook, createdDate, createrId, remark, emplNo, role,                              "
					+ "     extRole, rate, rank, modifierId, lastModifiedDate, acctBeginDate, acctExpireDate, blockDownload, resume )"
					+ " VALUES(SecureDB.DBSEC.ENCRYPT_AES(?), ?, ?, ?, ?, ?, SecureDB.DBSEC.ENCRYPT_PWD(?), ?,   ?, ?, ?, ?, ?,       ?, ?, ?, ?, ?,       ?, ?, ?, ?, ?,       ?, ?, ?, ?, ?,"
					+ "        ?, ?, ?, ?, ?,      ?, ?, ?, ?, ?,       ?, ?, ?, ?, ?,       ?, getdate(), ?, ?, ?, ?, 		?, ?, ?, ?, getdate()-11, ?, ?, ?, ? ) ");
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
		
		protected int insert(ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[]{expertPool.getUid(), expertPool.getSsn(), expertPool.getUserId(), expertPool.getName(), expertPool.getChnName(), expertPool.getEngName(),
					expertPool.getPassword(), expertPool.getEnable(), expertPool.getGender(),
					expertPool.getNationality(), expertPool.getTelNo(), expertPool.getMobileNo(),
					expertPool.getZipCode(), expertPool.getAddress1(), expertPool.getAddress2(),
					expertPool.getCompanyId(), expertPool.getCompany(), expertPool.getDept(), expertPool.getDeptName(),
					expertPool.getCompanyPosition(), expertPool.getCompanyPositionName(), expertPool.getJobClass(), expertPool.getCompanyZipcode(),
					expertPool.getCompanyAddress1(), expertPool.getCompanyAddress2(), expertPool.getCompanyTelNo(),
					expertPool.getCompanyFaxNo(), expertPool.getEmail(), expertPool.getPhoto(),
					expertPool.getFinalMajor(), expertPool.getLangSkill1(), expertPool.getLangSkill1Level(),
					expertPool.getLangSkill2(), expertPool.getLangSkill2Level(), expertPool.getLangSkill3(),
					expertPool.getLangSkill3Level(), expertPool.getEssayTitle(), expertPool.getIndField(),
					expertPool.getFuncField(), expertPool.getConsultingMajor(), expertPool.getConsultingMinor(),
					expertPool.getConsultingDetail(), expertPool.getResearchEassy(), expertPool.getPublishedBook(),
					expertPool.getCreaterId(), expertPool.getRemark(), expertPool.getEmplNo(), expertPool.getRole(), expertPool.getExtRole(),
					expertPool.getRate(), expertPool.getRank(), expertPool.getModifierId(), expertPool.getAcctBeginDate(), expertPool.getAcctExpireDate(), expertPool.getBlockDownload(), expertPool.getResume() });
		}
	}
	
	// Job Date: 2008-01-11 Author: yhyim Description: dept history insert when new expert manually insert
	protected class ExpertPoolHistoryInsertQuery extends SqlUpdate {
		protected ExpertPoolHistoryInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO deptHistory(                   "
					+ "		ssn, jobClass, dept, name, rate, companyPosition, userid, year, month, day, isRunning)"
					+ " VALUES(?, ?, ?, ?, ?,            ?, ?, ?, ?, ?, 'Y' ) ");
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
		protected int insert(ExpertPool expertPool, String year, String month, String day) throws DataAccessException {
			return this.update(new Object[]{
					expertPool.getSsn(), expertPool.getJobClass(), expertPool.getDept(), 
					expertPool.getName(), expertPool.getRate(), expertPool.getCompanyPosition(), expertPool.getUserId(),
					year, month, day});
		}
	}
	
	// Job Date: 2010-07-05 Author: yhyim Description: set register sanction line insert when new expert manually insert
	protected class ExpertSanctionLineInsertQuery extends SqlUpdate {
		protected ExpertSanctionLineInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO sanctionLine(                   "
					+ "		id, registerSsn, registerDept, registerName)"
					+ " VALUES(?, ?, ?, ?) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int insert(ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[]{
					expertPool.getSsn(), expertPool.getSsn(), expertPool.getDept(), expertPool.getName()});
		}
	}
	
	// Job Date: 2018-04-18 Author: yhyim Description: dept history insert when new expert manually insert
	protected class ExpertPoolAccountHistoryInsertQuery extends SqlUpdate {
		protected ExpertPoolAccountHistoryInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO AccountHistory(                   "
					+ "		type, userId, name, jobClass, jobClassName, dept, deptName, companyPosition, companyPositionName, menuRole, menuRoleName, date, etc )"
					+ " VALUES(?, ?, ?, ?, (select data_1 from CMTABLEDATA where table_name='EMP_WORKING_TYPE' and key_1 = ?), ?, ?, ?, ?, ?, (select roleName from role where roleNum = ?), getDate(), '' ) ");
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
		protected int insert(String accessType, ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[]{
					accessType, expertPool.getUserId(), expertPool.getName(), expertPool.getJobClass(), expertPool.getJobClass(), expertPool.getDept(), expertPool.getDeptName(), 
					expertPool.getCompanyPosition(), expertPool.getCompanyPositionName(), expertPool.getRole(), expertPool.getRole() });
		}
	}	
	
	protected class WorkTimeInsertQuery extends SqlUpdate{
		protected WorkTimeInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO workDay(ssn, workDate, workType, workIp)"
					+ " VALUES(?, getDate(), ?, ?) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int insert(String ssn, String workType, String ip) throws DataAccessException {
			return this.update(new Object[]{ ssn, workType, ip });
		}
	}
	
	protected class DailyDownloadLimitInsertQuery extends SqlUpdate {
		protected DailyDownloadLimitInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO DailyDownloadLimit(           "
					+ "		ssn, limitDate, isLimit)"
					+ " VALUES(?, getDate(), 'Y') ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int insert(String ssn) throws DataAccessException {
			return this.update(new Object[]{ ssn });
		}
	}
	
	protected class DailyStoreHomeTaxValue extends SqlUpdate {
		protected DailyStoreHomeTaxValue(DataSource ds) {
			super(ds, " INSERT INTO homeTaxCheck(           "
					+ "		writeDate, readDate, checkValue, companyNum, businessName, sumMoney)"
					+ " VALUES(?, ?, ?, ?, ?, ?) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int insert(String value1, String value2, String value3, String value4, String value5, String value6) throws DataAccessException {
			return this.update(new Object[]{ value1, value2, value3, value4, value5, value6 });
		}
	}
	
	
	protected class BudgetCheckInsertQuery extends SqlUpdate {
		protected BudgetCheckInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO budgetCheck   "
					+ " VALUES(?, getDate(), 'Y') ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int insert(String projectCode) throws DataAccessException {
			return this.update(new Object[]{ projectCode });
		}
	}
	
	protected class DailyDownloadLimitUpdateQuery extends SqlUpdate {
		protected DailyDownloadLimitUpdateQuery(DataSource ds) {
			super(ds, " UPDATE DailyDownloadLimit SET limitDate=getDate(), isLimit='Y' WHERE ssn = ?  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			//setRequiredRowsAffected(1);
		}
		protected int updateState(String ssn) throws DataAccessException {
			return this.update(new Object[] { ssn });
		}
	}
	
	protected class DailyDownloadLimitStateUpdateQuery extends SqlUpdate {
		protected DailyDownloadLimitStateUpdateQuery(DataSource ds) {
			super(ds, " UPDATE DailyDownloadLimit SET limitDate=getDate(), isLimit=?, isUnLimit=? WHERE ssn = ?  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			//setRequiredRowsAffected(1);
		}
		protected int updateState(String ssn, String isLimit, String isUnLimit) throws DataAccessException {
			return this.update(new Object[] { isLimit, isUnLimit, ssn });
		}
	}
	
	protected class DownloadLimitInitQuery extends SqlUpdate {
		protected DownloadLimitInitQuery(DataSource ds) {
			super(ds, " UPDATE DailyDownloadLimit SET isLimit=?, isUnlimit=?  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			//setRequiredRowsAffected(1);
		}
		protected int updateState(String isLimit, String isUnlimit) throws DataAccessException {
			return this.update(new Object[] { isLimit, isUnlimit });
		}
	}

	protected class ExpertPoolUpdateQuery extends SqlUpdate {
		protected ExpertPoolUpdateQuery(DataSource ds) {
			super(ds, " UPDATE ExpertPool                                  "
					+ "	SET name=?, chnName=?, engName=?, enable=?, gender=?, nationality=?, telNo=?, mobileNo=?, zipCode=?, address1=?, address2=?, "//
					+ "		companyId=?, company=?, dept=?, deptName=?, companyPosition=?, companyPositionName=?, jobClass=?, "//
					+ "     companyZipcode=?,companyAddress1=?, companyAddress2=?, companyTelNo=?, companyFaxNo=?, email=?, photo=?, "//
					+ "     finalMajor=?,langSkill1=?, langSkill1Level=?, langSkill2=?, langSkill2Level=?, langSkill3=?, langSkill3Level=?, "//
					+ "     essayTitle=?, indField=?, funcField=?, consultingMajor=?, consultingMinor=?, consultingDetail=?, "//
					+ "		researchEassy=?, publishedBook=?, modifiedDate=getdate(), modifierId=?, "//
					+ "		emplNo=?, remark=?, role=?, extRole=?, rate=?, rank=?, userId=?, resume=?, acctBeginDate=?, acctExpireDate=?, blockDownload=?, accountNonLocked=?, salaryMailingYN=?, lastModifiedDate=? "
					+ " WHERE ssn = ?                                                  ");
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
			declareParameter(new SqlParameter(Types.VARCHAR));//
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//
			
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
			declareParameter(new SqlParameter(Types.TIMESTAMP));

			declareParameter(new SqlParameter(Types.VARCHAR));
			
			
			compile();
		}
		protected int update(ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[] { expertPool.getName(), expertPool.getChnName(), expertPool.getEngName(), expertPool.getEnable(), expertPool.getGender(),
					expertPool.getNationality(), expertPool.getTelNo(), expertPool.getMobileNo(), expertPool.getZipCode(), expertPool.getAddress1(),
					expertPool.getAddress2(), expertPool.getCompanyId(), expertPool.getCompany(), expertPool.getDept(), expertPool.getDeptName(),
					expertPool.getCompanyPosition(), expertPool.getCompanyPositionName(), expertPool.getJobClass(), expertPool.getCompanyZipcode(), expertPool.getCompanyAddress1(),
					expertPool.getCompanyAddress2(), expertPool.getCompanyTelNo(), expertPool.getCompanyFaxNo(), expertPool.getEmail(),
					expertPool.getPhoto(), expertPool.getFinalMajor(), expertPool.getLangSkill1(), expertPool.getLangSkill1Level(),
					expertPool.getLangSkill2(), expertPool.getLangSkill2Level(), expertPool.getLangSkill3(), expertPool.getLangSkill3Level(),
					expertPool.getEssayTitle(), expertPool.getIndField(), expertPool.getFuncField(), expertPool.getConsultingMajor(),
					expertPool.getConsultingMinor(), expertPool.getConsultingDetail(), expertPool.getResearchEassy(), expertPool.getPublishedBook(),
					expertPool.getModifierId(), expertPool.getEmplNo(), expertPool.getRemark(), expertPool.getRole(), expertPool.getExtRole(),
					expertPool.getRate(), expertPool.getRank(), expertPool.getUserId(), expertPool.getResume(), expertPool.getAcctBeginDate(), expertPool.getAcctExpireDate(), expertPool.getBlockDownload(), expertPool.getAccountNonLocked(), expertPool.getSalaryMailingYN(), expertPool.getLastModifiedDate(), expertPool.getSsn() });
		}
	}
	
	protected class ExpertPoolUpdateQuery2 extends SqlUpdate {
		protected ExpertPoolUpdateQuery2(DataSource ds) {
			super(ds, " UPDATE ExpertPool                                  "
					+ "	SET password = SecureDB.DBSEC.ENCRYPT_PWD(?), name=?, chnName=?, engName=?, enable=?, gender=?, nationality=?, telNo=?, mobileNo=?, zipCode=?, address1=?, address2=?, "//
					+ "		companyId=?, company=?, dept=?, deptName=?, companyPosition=?, companyPositionName=?, jobClass=?, "//
					+ "     companyZipcode=?,companyAddress1=?, companyAddress2=?, companyTelNo=?, companyFaxNo=?, email=?, photo=?, "//
					+ "     finalMajor=?,langSkill1=?, langSkill1Level=?, langSkill2=?, langSkill2Level=?, langSkill3=?, langSkill3Level=?, "//
					+ "     essayTitle=?, indField=?, funcField=?, consultingMajor=?, consultingMinor=?, consultingDetail=?, "//
					+ "		researchEassy=?, publishedBook=?, modifiedDate=getdate(), modifierId=?, "//
					+ "		emplNo=?, remark=?, role=?, extRole=?, rate=?, rank=?, userId=?, resume=?, acctBeginDate=?, acctExpireDate=?, blockDownload=?, accountNonLocked=?, lastModifiedDate=? "
					+ " WHERE ssn = ?                                                  ");
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
			declareParameter(new SqlParameter(Types.VARCHAR));//
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));//
			
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
			declareParameter(new SqlParameter(Types.TIMESTAMP));

			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		protected int update(ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[] { expertPool.getPassword(), expertPool.getName(), expertPool.getChnName(), expertPool.getEngName(), expertPool.getEnable(), expertPool.getGender(),
					expertPool.getNationality(), expertPool.getTelNo(), expertPool.getMobileNo(), expertPool.getZipCode(), expertPool.getAddress1(),
					expertPool.getAddress2(), expertPool.getCompanyId(), expertPool.getCompany(), expertPool.getDept(), expertPool.getDeptName(),
					expertPool.getCompanyPosition(), expertPool.getCompanyPositionName(), expertPool.getJobClass(), expertPool.getCompanyZipcode(), expertPool.getCompanyAddress1(),
					expertPool.getCompanyAddress2(), expertPool.getCompanyTelNo(), expertPool.getCompanyFaxNo(), expertPool.getEmail(),
					expertPool.getPhoto(), expertPool.getFinalMajor(), expertPool.getLangSkill1(), expertPool.getLangSkill1Level(),
					expertPool.getLangSkill2(), expertPool.getLangSkill2Level(), expertPool.getLangSkill3(), expertPool.getLangSkill3Level(),
					expertPool.getEssayTitle(), expertPool.getIndField(), expertPool.getFuncField(), expertPool.getConsultingMajor(),
					expertPool.getConsultingMinor(), expertPool.getConsultingDetail(), expertPool.getResearchEassy(), expertPool.getPublishedBook(),
					expertPool.getModifierId(), expertPool.getEmplNo(), expertPool.getRemark(), expertPool.getRole(), expertPool.getExtRole(),
					expertPool.getRate(), expertPool.getRank(), expertPool.getUserId(), expertPool.getResume(), expertPool.getAcctBeginDate(), expertPool.getAcctExpireDate(), expertPool.getBlockDownload(), expertPool.getAccountNonLocked(), expertPool.getLastModifiedDate(), expertPool.getSsn() });
		}
	}	
	
	protected class RestrictUserUpdateQuery extends SqlUpdate {
		protected RestrictUserUpdateQuery(DataSource ds) {
			super(ds, " UPDATE expertPool SET restrictUser = ?, absence = ?, modifiedDate = getDate() WHERE ssn = ?  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			//setRequiredRowsAffected(1);
		}
		protected int updateState(String ssn, String state, String absence) throws DataAccessException {
			return this.update(new Object[] { state, absence, ssn });
		}
	}
	
	protected class RestrictUserUpdateQuery2 extends SqlUpdate {
		protected RestrictUserUpdateQuery2(DataSource ds) {
			super(ds, " UPDATE expertPool SET restrictUser = ?, absence = ?, modifiedDate = getDate(), restrictContents = ? WHERE ssn = ?  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			//setRequiredRowsAffected(1);
		}
		protected int updateState(String ssn, String state, String absence, String restrictContents) throws DataAccessException {
			return this.update(new Object[] { state, absence, restrictContents, ssn });
		}
	}
	
	
	protected class EduSystemAccountUpdateQuery extends SqlUpdate {
		protected EduSystemAccountUpdateQuery(DataSource ds) {
			super(ds, " UPDATE education.dbo.ADMIN_MEM_T "
					+ "	SET pwd=? "
					+ " WHERE id = ?   ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
		}
		protected int update(ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[] { expertPool.getPassword(), expertPool.getUserId() });
		}
	}
	
	
	// Job Date: 2008-01-11	Author: yhyim	Description: dept history update when new expert manually update
	protected class ExpertPoolHistoryUpdateQuery extends SqlUpdate {
		protected ExpertPoolHistoryUpdateQuery(DataSource ds) {
			super(ds, " UPDATE deptHistory                         "
					+ "	SET dept=?, jobClass=?, rate=?, companyPosition=?, day=? "
					+ " WHERE ssn = ? AND year = ? AND month = ?   ");
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
		protected int update(ExpertPool expertPool, String year, String month, String day) throws DataAccessException {
			return this.update(new Object[]{
					expertPool.getDept(), expertPool.getJobClass(), expertPool.getRate(), expertPool.getCompanyPosition(), day,
					expertPool.getSsn(), year, month});
		}
	}
	
	// Job Date: 2010-07-05	Author: yhyim	Description: register sanction line  update when expert information manually update
	protected class ExpertSanctionLineUpdateQuery extends SqlUpdate {
		protected ExpertSanctionLineUpdateQuery(DataSource ds) {
			super(ds, " UPDATE sanctionLine		"
					+ "	SET registerDept=? "
					+ " WHERE id = ?   ");
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}
		protected int update(ExpertPool expertPool) throws DataAccessException {
			return this.update(new Object[]{
					expertPool.getDept(), expertPool.getSsn()});
		}
	}

	protected class ExpertPoolDeleteQuery extends SqlUpdate {
		protected ExpertPoolDeleteQuery(DataSource ds) {
			super(ds, " Delete from ExpertPool where ssn = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected int delete(String ssn) {
			return update(new Object[]{ssn});
		}
	}
	
	protected class ExpertPoolRetireveQuery extends MappingSqlQuery {
		protected ExpertPoolRetireveQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected ExpertPoolRetireveQuery(DataSource ds) {
			super(ds, " select ssn, userId, name, password, enable, absence, gender, nationality, telNo, mobileNo, zipCode, address1, address2, company, dept, companyPosition, jobClass, companyZipcode, companyAddress1, companyAddress2, companyTelNo, companyFaxNo, email, photo, finalMajor, langSkill1, langSkill1Level, langSkill2, langSkill2Level, langSkill3, langSkill3Level, essayTitle, indField, funcField, consultingMajor, consultingMinor, consultingDetail, researchEassy, publishedBook, createdDate, createrId, modifiedDate, modifierId, remark, rate, rank, Role, extRole, resume, companyId, LastModifiedDate, lastModifiedCheckDate, SEQ, replace(replace(LTrim(deptName),' 상임',''),'상임','') as deptName, companyPositionName, expert_maindept, emplNo, acctBeginDate, acctExpireDate, blockDownload, chnName, engName, restrictUser, app_yn, SecureDB.DBSEC.DECRYPT_AES(uid) uid, loginAttepmtCnt, accountNonLocked, accountLockedDate, "
					+ "		f.originalFileName resumeName, e.salaryMailingYN, d.* 				"
					+ " from ExpertPool e WITH(NOLOCK)						"
					+ " 	left outer join fileRepository  f WITH(NOLOCK)	"
					+ "		on e.resume = f.fileId 							"
					+ " 	left outer join DWPM.DBO.DW_view_CP_Amt d WITH(NOLOCK)	"
					+ "		on e.rate = d.cp_code 							"
					+ " where e.ssn = ?										");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPool expertPool = new ExpertPool();
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setUid(rs.getString("uid"));
			expertPool.setName(rs.getString("name"));
			expertPool.setChnName(rs.getString("chnName"));
			expertPool.setEngName(rs.getString("engName"));
			expertPool.setPassword(rs.getString("password"));
			expertPool.setEnable(rs.getString("enable"));
			expertPool.setAbsence(rs.getString("absence"));
			expertPool.setGender(rs.getString("gender"));
			expertPool.setNationality(rs.getString("nationality"));
			expertPool.setTelNo(rs.getString("telNo"));
			expertPool.setMobileNo(rs.getString("mobileNo"));
			expertPool.setZipCode(rs.getString("zipcode"));
			expertPool.setAddress1(rs.getString("address1"));
			expertPool.setAddress2(rs.getString("address2"));
			expertPool.setCompanyId(rs.getString("companyId"));
			expertPool.setCompany(rs.getString("company"));
			expertPool.setDept(rs.getString("dept"));
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setCompanyPosition(rs.getString("companyPosition"));
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
			expertPool.setJobClass(rs.getString("jobClass"));
			expertPool.setCompanyZipcode(rs.getString("companyZipCode"));
			expertPool.setCompanyAddress1(rs.getString("companyAddress1"));
			expertPool.setCompanyAddress2(rs.getString("companyAddress2"));
			expertPool.setCompanyTelNo(rs.getString("companyTelNo"));
			expertPool.setCompanyFaxNo(rs.getString("companyFaxNo"));
			expertPool.setEmail(rs.getString("email"));
			expertPool.setPhoto(rs.getString("photo"));
			expertPool.setFinalMajor(rs.getString("finalMajor"));
			expertPool.setLangSkill1(rs.getString("langSkill1"));
			expertPool.setLangSkill1Level(rs.getString("langSkill1Level"));
			expertPool.setLangSkill2(rs.getString("langSkill2"));
			expertPool.setLangSkill2Level(rs.getString("langSkill2Level"));
			expertPool.setLangSkill3(rs.getString("langSkill3"));
			expertPool.setLangSkill3Level(rs.getString("langSkill3Level"));
			expertPool.setEssayTitle(rs.getString("essayTitle"));
			expertPool.setIndField(rs.getString("IndField"));
			expertPool.setFuncField(rs.getString("FuncField"));
			expertPool.setConsultingMajor(rs.getString("consultingMajor"));
			expertPool.setConsultingMinor(rs.getString("consultingMinor"));
			expertPool.setConsultingDetail(rs.getString("consultingDetail"));
			expertPool.setResearchEassy(rs.getString("researchEassy"));
			expertPool.setPublishedBook(rs.getString("publishedBook"));
			expertPool.setCreatedDate(rs.getDate("createdDate"));
			expertPool.setCreaterId(rs.getString("createrId"));
			expertPool.setModifiedDate(rs.getDate("modifiedDate"));
			expertPool.setLastModifiedDate(rs.getDate("lastModifiedDate"));
			expertPool.setModifierId(rs.getString("modifierId"));
			expertPool.setRemark(rs.getString("remark"));
			expertPool.setEmplNo(rs.getString("emplNo"));
			expertPool.setRole(rs.getString("role"));
			expertPool.setExtRole(rs.getString("extRole"));
			expertPool.setUserId(rs.getString("userId"));
			expertPool.setRate(rs.getString("rate"));
			expertPool.setRank(rs.getString("rank"));
			expertPool.setResume(rs.getString("resume"));
			expertPool.setSalaryMailingYN(rs.getString("salaryMailingYN"));
			try {
				expertPool.setResumeName(rs.getString("resumeName"));
			} catch (Exception e) {
			}
			expertPool.setAcctBeginDate(rs.getString("acctBeginDate"));
			expertPool.setAcctExpireDate(rs.getString("acctExpireDate"));
			expertPool.setBlockDownload(rs.getString("blockDownload"));
			expertPool.setAccountNonLocked(rs.getString("accountNonLocked"));
			try {
				expertPool.setMinAmt(rs.getString("min_Amt"));
				expertPool.setMaxAmt(rs.getString("max_Amt")); 
				expertPool.setMinEdu(rs.getString("Edu_min"));
				expertPool.setMaxEdu(rs.getString("Edu_max"));
			} catch (Exception e) {
			}

			expertPool.setExpertPoolCareerHst(expertPoolCareerHstDao.findAll(rs.getString("ssn")));
			expertPool.setExpertPoolSchoolHst(expertPoolSchoolHstDao.findAll(rs.getString("ssn")));
			expertPool.setExpertPoolSpecialFields(expertPoolSpecialFieldDao.getSpecialFieldList(rs.getString("ssn")));
			return expertPool;
		}
	}
	
	protected class ExpertPoolRetireveQuery2 extends MappingSqlQuery {
		protected ExpertPoolRetireveQuery2(DataSource ds, String query) {
			super(ds, query);
		}
		protected ExpertPoolRetireveQuery2(DataSource ds) {
			super(ds, " select * From( "
					+ "	select (Case when p.attachOutPutName = '비공개' then 'N' else 'Y' end) as resultYN, p.attachFileId, p.attachCreatorId,runningDeptCode, runningDivCode "
					+ "	from projecttaskformattachdata p	"
					+ " left join "
					+ " ( "
					+ "	select projectcode, runningDivCode, runningDeptCode From project "
					+ "  )pjt "
					+ "  on p.projectcode = pjt.projectcode "
					+ " where p.attachFileId = ? "
					+ " )t																							");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPool expertPool = new ExpertPool();
			expertPool.setResultYN(rs.getString("resultYN"));
			expertPool.setAttachFileId(rs.getString("attachFileId"));
			expertPool.setAttachCreatorId(rs.getString("attachCreatorId"));
			expertPool.setRunningDeptCode(rs.getString("runningDeptCode"));
			expertPool.setRunningDivCode(rs.getString("runningDivCode"));

			return expertPool;
		}
	}
	
	protected class ExpertPoolRetireveQuery3 extends MappingSqlQuery {
		protected ExpertPoolRetireveQuery3(DataSource ds, String query) {
			super(ds, query);
		}
		protected ExpertPoolRetireveQuery3(DataSource ds) {
			super(ds, " select count(resultYN) as downloadCnt from ("
					+ " select (Case when p.attachOutPutName = '비공개' then 'N' else 'Y' end) as resultYN, p.attachFileId, p.attachCreatorId "
					+ " from projecttaskformattachdata p																				 "
					+ "	where p.attachFileId = ?	)e																						");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPool expertPool = new ExpertPool();
			expertPool.setDownloadCnt(rs.getString("downloadCnt"));
			return expertPool;
		}
	}
	
	private ExpertPool getExpertPool(ResultSet rs){
		ExpertPool expertPool = new ExpertPool();
		return expertPool;
	}
	protected class ExpertPoolRetireveNameQuery extends MappingSqlQuery {
		protected ExpertPoolRetireveNameQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected ExpertPoolRetireveNameQuery(DataSource ds) {
			super(ds, " select ssn, userId, name, password, enable, absence, gender, nationality, telNo, mobileNo, zipCode, address1, address2, company, dept, companyPosition, jobClass, companyZipcode, companyAddress1, companyAddress2, companyTelNo, companyFaxNo, email, photo, finalMajor, langSkill1, langSkill1Level, langSkill2, langSkill2Level, langSkill3, langSkill3Level, essayTitle, indField, funcField, consultingMajor, consultingMinor, consultingDetail, researchEassy, publishedBook, createdDate, createrId, modifiedDate, modifierId, remark, rate, rank, Role, extRole, resume, companyId, LastModifiedDate, lastModifiedCheckDate, SEQ, deptName, companyPositionName, expert_maindept, emplNo, acctBeginDate, acctExpireDate, blockDownload, chnName, engName, restrictUser, app_yn, SecureDB.DBSEC.DECRYPT_AES(uid) uid, loginAttepmtCnt, accountNonLocked, accountLockedDate from ExpertPool WITH(NOLOCK) where name like ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPool expertPool = new ExpertPool();
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setUid(rs.getString("uid"));
			expertPool.setName(rs.getString("name"));
			expertPool.setChnName(rs.getString("chnName"));
			expertPool.setEngName(rs.getString("engName"));
			expertPool.setPassword(rs.getString("password"));
			expertPool.setEnable(rs.getString("enable"));
			expertPool.setAbsence(rs.getString("absence"));
			expertPool.setGender(rs.getString("gender"));
			expertPool.setNationality(rs.getString("nationality"));
			expertPool.setTelNo(rs.getString("telNo"));
			expertPool.setMobileNo(rs.getString("mobileNo"));
			expertPool.setZipCode(rs.getString("zipcode"));
			expertPool.setAddress1(rs.getString("address1"));
			expertPool.setAddress2(rs.getString("address2"));
			expertPool.setCompanyId(rs.getString("companyId"));
			expertPool.setCompany(rs.getString("company"));
			expertPool.setDept(rs.getString("dept"));
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setCompanyPosition(rs.getString("companyPosition"));
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
			expertPool.setJobClass(rs.getString("jobClass"));
			expertPool.setCompanyZipcode(rs.getString("companyZipCode"));
			expertPool.setCompanyAddress1(rs.getString("companyAddress1"));
			expertPool.setCompanyAddress2(rs.getString("companyAddress2"));
			expertPool.setCompanyTelNo(rs.getString("companyTelNo"));
			expertPool.setCompanyFaxNo(rs.getString("companyFaxNo"));
			expertPool.setEmail(rs.getString("email"));
			expertPool.setPhoto(rs.getString("photo"));
			expertPool.setFinalMajor(rs.getString("finalMajor"));
			expertPool.setCreatedDate(rs.getDate("createdDate"));
			expertPool.setCreaterId(rs.getString("createrId"));
			expertPool.setModifiedDate(rs.getDate("modifiedDate"));
			expertPool.setLastModifiedDate(rs.getDate("LastModifiedDate"));
			expertPool.setModifierId(rs.getString("modifierId"));
			expertPool.setRemark(rs.getString("remark"));
			expertPool.setEmplNo(rs.getString("emplNo"));
			expertPool.setRole(rs.getString("role"));
			expertPool.setExtRole(rs.getString("extRole"));
			expertPool.setUserId(rs.getString("userId"));
			expertPool.setRate(rs.getString("rate"));
			expertPool.setRank(rs.getString("rank"));
			expertPool.setAcctBeginDate(rs.getString("acctBeginDate"));
			expertPool.setAcctExpireDate(rs.getString("acctExpireDate"));
			expertPool.setBlockDownload(rs.getString("blockDownload"));
			expertPool.setAccountNonLocked(rs.getString("accountNonLocked"));
			return expertPool;
		}
	}
	
	protected class ExpertPoolRetireveUidQuery extends MappingSqlQuery {
		protected ExpertPoolRetireveUidQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected ExpertPoolRetireveUidQuery(DataSource ds) {
			super(ds, " select ssn, userId, name, password, enable, absence, gender, nationality, telNo, mobileNo, zipCode, address1, address2, company, dept, companyPosition, jobClass, companyZipcode, companyAddress1, companyAddress2, companyTelNo, companyFaxNo, email, photo, finalMajor, langSkill1, langSkill1Level, langSkill2, langSkill2Level, langSkill3, langSkill3Level, essayTitle, indField, funcField, consultingMajor, consultingMinor, consultingDetail, researchEassy, publishedBook, createdDate, createrId, modifiedDate, modifierId, remark, rate, rank, Role, extRole, resume, companyId, LastModifiedDate, lastModifiedCheckDate, SEQ, deptName, companyPositionName, expert_maindept, emplNo, acctBeginDate, acctExpireDate, blockDownload, chnName, engName, restrictUser, app_yn, SecureDB.DBSEC.DECRYPT_AES(uid) uid, loginAttepmtCnt, accountNonLocked, accountLockedDate from ExpertPool WITH(NOLOCK) where uid = SecureDB.DBSEC.ENCRYPT_AES(?) ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPool expertPool = new ExpertPool();
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setUid(rs.getString("uid"));
			expertPool.setName(rs.getString("name"));
			expertPool.setChnName(rs.getString("chnName"));
			expertPool.setEngName(rs.getString("engName"));
			expertPool.setPassword(rs.getString("password"));
			expertPool.setEnable(rs.getString("enable"));
			expertPool.setAbsence(rs.getString("absence"));
			expertPool.setGender(rs.getString("gender"));
			expertPool.setNationality(rs.getString("nationality"));
			expertPool.setTelNo(rs.getString("telNo"));
			expertPool.setMobileNo(rs.getString("mobileNo"));
			expertPool.setZipCode(rs.getString("zipcode"));
			expertPool.setAddress1(rs.getString("address1"));
			expertPool.setAddress2(rs.getString("address2"));
			expertPool.setCompanyId(rs.getString("companyId"));
			expertPool.setCompany(rs.getString("company"));
			expertPool.setDept(rs.getString("dept"));
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setCompanyPosition(rs.getString("companyPosition"));
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
			expertPool.setJobClass(rs.getString("jobClass"));
			expertPool.setCompanyZipcode(rs.getString("companyZipCode"));
			expertPool.setCompanyAddress1(rs.getString("companyAddress1"));
			expertPool.setCompanyAddress2(rs.getString("companyAddress2"));
			expertPool.setCompanyTelNo(rs.getString("companyTelNo"));
			expertPool.setCompanyFaxNo(rs.getString("companyFaxNo"));
			expertPool.setEmail(rs.getString("email"));
			expertPool.setPhoto(rs.getString("photo"));
			expertPool.setFinalMajor(rs.getString("finalMajor"));
			expertPool.setCreatedDate(rs.getDate("createdDate"));
			expertPool.setCreaterId(rs.getString("createrId"));
			expertPool.setModifiedDate(rs.getDate("modifiedDate"));
			expertPool.setLastModifiedDate(rs.getDate("LastModifiedDate"));
			expertPool.setModifierId(rs.getString("modifierId"));
			expertPool.setRemark(rs.getString("remark"));
			expertPool.setEmplNo(rs.getString("emplNo"));
			expertPool.setRole(rs.getString("role"));
			expertPool.setExtRole(rs.getString("extRole"));
			expertPool.setUserId(rs.getString("userId"));
			expertPool.setRate(rs.getString("rate"));
			expertPool.setRank(rs.getString("rank"));
			expertPool.setAcctBeginDate(rs.getString("acctBeginDate"));
			expertPool.setAcctExpireDate(rs.getString("acctExpireDate"));
			expertPool.setBlockDownload(rs.getString("blockDownload"));
			expertPool.setAccountNonLocked(rs.getString("accountNonLocked"));
			return expertPool;
		}
	}

	protected class ExpertPoolRetireveByRoleQuery extends MappingSqlQuery {
		protected ExpertPoolRetireveByRoleQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected ExpertPoolRetireveByRoleQuery(DataSource ds) {
			super(ds, "select * from ExpertPool WITH(NOLOCK)  where role = ? OR extRole = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpertPool expertPool = new ExpertPool();
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setUid(rs.getString("uid"));
			expertPool.setName(rs.getString("name"));
			expertPool.setPassword(rs.getString("password"));
			expertPool.setEnable(rs.getString("enable"));
			expertPool.setAbsence(rs.getString("absence"));
			expertPool.setGender(rs.getString("gender"));
			expertPool.setNationality(rs.getString("nationality"));
			expertPool.setTelNo(rs.getString("telNo"));
			expertPool.setMobileNo(rs.getString("mobileNo"));
			expertPool.setZipCode(rs.getString("zipcode"));
			expertPool.setAddress1(rs.getString("address1"));
			expertPool.setAddress2(rs.getString("address2"));
			expertPool.setCompanyId(rs.getString("companyId"));
			expertPool.setCompany(rs.getString("company"));
			expertPool.setDept(rs.getString("dept"));
			expertPool.setCompanyPosition(rs.getString("companyPosition"));
			expertPool.setJobClass(rs.getString("jobClass"));
			expertPool.setCompanyZipcode(rs.getString("companyZipCode"));
			expertPool.setCompanyAddress1(rs.getString("companyAddress1"));
			expertPool.setCompanyAddress2(rs.getString("companyAddress2"));
			expertPool.setCompanyTelNo(rs.getString("companyTelNo"));
			expertPool.setCompanyFaxNo(rs.getString("companyFaxNo"));
			expertPool.setEmail(rs.getString("email"));
			expertPool.setPhoto(rs.getString("photo"));
			expertPool.setFinalMajor(rs.getString("finalMajor"));
			expertPool.setLangSkill1(rs.getString("langSkill1"));
			expertPool.setLangSkill1Level(rs.getString("langSkill1Level"));
			expertPool.setLangSkill2(rs.getString("langSkill2"));
			expertPool.setLangSkill2Level(rs.getString("langSkill2Level"));
			expertPool.setLangSkill3(rs.getString("langSkill3"));
			expertPool.setLangSkill3Level(rs.getString("langSkill3Level"));
			expertPool.setEssayTitle(rs.getString("essayTitle"));
			expertPool.setIndField(rs.getString("IndField"));
			expertPool.setFuncField(rs.getString("FuncField"));
			expertPool.setConsultingMajor(rs.getString("consultingMajor"));
			expertPool.setConsultingMinor(rs.getString("consultingMinor"));
			expertPool.setConsultingDetail(rs.getString("consultingDetail"));
			expertPool.setResearchEassy(rs.getString("researchEassy"));
			expertPool.setPublishedBook(rs.getString("publishedBook"));
			expertPool.setCreatedDate(rs.getDate("createdDate"));
			expertPool.setCreaterId(rs.getString("createrId"));
			expertPool.setModifiedDate(rs.getDate("modifiedDate"));
			expertPool.setLastModifiedDate(rs.getDate("LastModifiedDate"));
			expertPool.setModifierId(rs.getString("modifierId"));
			expertPool.setRemark(rs.getString("remark"));
			expertPool.setRole(rs.getString("role"));
			expertPool.setExtRole(rs.getString("extRole"));
			expertPool.setUserId(rs.getString("userId"));
			expertPool.setRate(rs.getString("rate"));
			expertPool.setResume(rs.getString("resume"));

			expertPool.setExpertPoolCareerHst(expertPoolCareerHstDao.findAll(rs.getString("ssn")));
			expertPool.setExpertPoolSchoolHst(expertPoolSchoolHstDao.findAll(rs.getString("ssn")));
			expertPool.setExpertPoolSpecialFields(expertPoolSpecialFieldDao.getSpecialFieldList(rs.getString("ssn")));
			return expertPool;
		}
	}

	protected class ExpertPoolFindQuery extends ExpertPoolRetireveQuery {
		protected ExpertPoolFindQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected ExpertPoolFindQuery(DataSource ds) {
			super(ds, "select * from ExpertPool WITH(NOLOCK)  order by name ");
			compile();
		}
	}

	protected class ExpertPoolUserIdQuery extends ExpertPoolRetireveQuery {
		protected ExpertPoolUserIdQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected ExpertPoolUserIdQuery(DataSource ds) {
			super(ds, "select * from ExpertPool  WITH(NOLOCK) where userId=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	protected class BankAccountKMACRetireveQuery extends MappingSqlQuery {
		protected BankAccountKMACRetireveQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected BankAccountKMACRetireveQuery(DataSource ds) {
			super(ds, "select a.sno as emplNo, b.dname as bankName, a.bank_no as bankAccount from paybasis a join DATACHKD b on a.bank_kind = b.DCODE WHERE b.hcode = 'HP03' and a.sno = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BankAccount bankAccount = new BankAccount();
			bankAccount.setBankName(rs.getString("bankName"));
			bankAccount.setBankAccount(rs.getString("bankAccount"));
			return bankAccount;
		}
	}
	
	protected class BankAccountNonKMACRetireveQuery extends MappingSqlQuery {
		protected BankAccountNonKMACRetireveQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected BankAccountNonKMACRetireveQuery(DataSource ds) {
			super(ds, "select a.cust_code as uuid, a.cust_name as userName, b.dname as bankName, a.sum_account as bankAccount from cm9001m a join DATACHKD b on a.sum_bank = b.DCODE WHERE a.use_cls='Y' and b.hcode = 'HP03' and a.cust_code = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BankAccount bankAccount = new BankAccount();
			bankAccount.setBankName(rs.getString("bankName"));
			bankAccount.setBankAccount(rs.getString("bankAccount"));
			return bankAccount;
		}
	}
	
	protected class SMGroupFindQuery extends MappingSqlQuery {
		protected SMGroupFindQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected SMGroupFindQuery(DataSource ds) {
			//super(ds, "select * from smgroup order by id ");
			super(ds, "select * from smgroup  WITH(NOLOCK) where enabled = '1' "
						+ " order by seq, id  ");
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustermGroup group = new CustermGroup();
			group.setId(rs.getString("id"));
			group.setName(rs.getString("name"));
			group.setGroupType(rs.getString("groupType"));
			group.setEnabled(rs.getString("enabled"));
			group.setMemberRule(rs.getString("memberRule"));
			group.setDescription(rs.getString("description"));
			group.setParentId(rs.getString("parentId"));
			group.setPath(rs.getString("path"));
			group.setDepth(rs.getString("depth"));
			return group;
		}
	}

	// Job Date: 2007-09-06	Author: yhyim Description: retreive BU list
	protected class SMBUGroupFindQuery extends MappingSqlQuery {
		protected SMBUGroupFindQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected SMBUGroupFindQuery(DataSource ds) {
			//super(ds, "select * from smgroup order by id ");
			super(ds, "select * from smGroup  WITH(NOLOCK) where enabled = '1' "
						+ " and id like '2%' "
						+ " order by seq  ");
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustermGroup group = new CustermGroup();
			group.setId(rs.getString("id"));
			group.setName(rs.getString("name"));
			group.setGroupType(rs.getString("groupType"));
			group.setEnabled(rs.getString("enabled"));
			group.setMemberRule(rs.getString("memberRule"));
			group.setDescription(rs.getString("description"));
			group.setParentId(rs.getString("parentId"));
			group.setPath(rs.getString("path"));
			group.setDepth(rs.getString("depth"));
			return group;
		}
	}	

	// Job Date: 2007-09-06	Author: yhyim Description: retreive PU list
	protected class SMPUGroupFindQuery extends MappingSqlQuery {
		protected SMPUGroupFindQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected SMPUGroupFindQuery(DataSource ds) {
			//super(ds, "select * from smgroup order by id ");
			super(ds, "select * from smgroup WITH(NOLOCK)  where enabled = '1' "
						+ " and id like '6%' "
						+ " order by seq, id" );
						//+ " order by (case id when '5300' then '5001' "
						//+ "		when '5200' then '5002' "
						//+ "		when '5500' then '5003' "
						//+ "		when '5400' then '5004' "
						//+ "		when '5100' then '5005' "
						//+ "		when '5600' then '5006' "
						//+ "		when '5700' then '5007' else id end)  ");
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustermGroup group = new CustermGroup();
			group.setId(rs.getString("id"));
			group.setName(rs.getString("name"));
			group.setGroupType(rs.getString("groupType"));
			group.setEnabled(rs.getString("enabled"));
			group.setMemberRule(rs.getString("memberRule"));
			group.setDescription(rs.getString("description"));
			group.setParentId(rs.getString("parentId"));
			group.setPath(rs.getString("path"));
			group.setDepth(rs.getString("depth"));
			return group;
		}
	}		
	
	protected class CustermAddUserMemberQuery extends SqlUpdate {
		protected CustermAddUserMemberQuery(DataSource ds) {
			super(ds, "insert into SMGroupUser values (?,?)");
			declareParameter(new SqlParameter(Types.VARCHAR)); // groupId
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			compile();

		}
		protected void add(String parentId, String userId) {
			super.update(new Object[]{parentId, userId});
		}
	}

	protected class CustermRemoveUserMemberQuery extends SqlUpdate {
		protected CustermRemoveUserMemberQuery(DataSource ds) {
			super(ds, "delete from SMGroupUser where userId=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			compile();

		}
		protected void remove(String userId) {
			super.update(new Object[]{userId});
		}
	}

	protected class SMRoleFindQuery extends MappingSqlQuery {
		protected SMRoleFindQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected SMRoleFindQuery(DataSource ds) {
			super(ds, "select * from smrole WITH(NOLOCK) where enable='1'  order by id ");
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustermRole role = new CustermRole();
			role.setId(rs.getString("id"));
			role.setName(rs.getString("name"));
			role.setEnabled(rs.getString("enable"));
			role.setDescription(rs.getString("description"));
			role.setRate(rs.getString("rate"));
			return role;
		}
	}
	
	protected class SMBURoleFindQuery extends MappingSqlQuery {
		protected SMBURoleFindQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected SMBURoleFindQuery(DataSource ds) {
			super(ds, "select * from smrole  WITH(NOLOCK) where enable = '1' and substring(id, 1,2) <= '40' order by id ");
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustermRole role = new CustermRole();
			role.setId(rs.getString("id"));
			role.setName(rs.getString("name"));
			role.setEnabled(rs.getString("enable"));
			role.setDescription(rs.getString("description"));
			role.setRate(rs.getString("rate"));
			return role;
		}
	}
	
	protected class SMPURoleFindQuery extends MappingSqlQuery {
		protected SMPURoleFindQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected SMPURoleFindQuery(DataSource ds) {
			super(ds, "select * from smrole  WITH(NOLOCK) where enable = '1' and substring(id, 1,2) > '40' order by id ");
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustermRole role = new CustermRole();
			role.setId(rs.getString("id"));
			role.setName(rs.getString("name"));
			role.setEnabled(rs.getString("enable"));
			role.setDescription(rs.getString("description"));
			role.setRate(rs.getString("rate"));
			return role;
		}
	}
	
	protected class MenuRoleFindQuery extends MappingSqlQuery {
		protected MenuRoleFindQuery(DataSource ds, String query) {
			super(ds, query);
		}
		protected MenuRoleFindQuery(DataSource ds) {
			super(ds, "select * from Role order by roleSeq");
			compile();
		}
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			CustermRole role = new CustermRole();
			role.setId(rs.getString("roleNum"));
			role.setName(rs.getString("roleName"));
			return role;
		}
	}

	protected class CustermAddUserRoleQuery extends SqlUpdate {
		protected CustermAddUserRoleQuery(DataSource ds) {
			super(ds, "insert into SMUserRole(userId, roleId) values (?,?)");
			declareParameter(new SqlParameter(Types.VARCHAR)); // groupId
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			compile();

		}
		protected void add(String parentId, String userId) {
			super.update(new Object[]{parentId, userId});
		}
	}

	protected class CustermRemoveUserRoleQuery extends SqlUpdate {
		protected CustermRemoveUserRoleQuery(DataSource ds) {
			super(ds, "delete from SMUserRole where userId=?");
			declareParameter(new SqlParameter(Types.VARCHAR)); // userId
			compile();

		}
		protected void remove(String userId) {
			super.update(new Object[]{userId});
		}
	}
	
	protected void initDao() throws Exception {
		this.expertPoolInsertQuery = new ExpertPoolInsertQuery(getDataSource());
		this.expertPoolUpdateQuery = new ExpertPoolUpdateQuery(getDataSource());
		this.expertPoolUpdateQuery2 = new ExpertPoolUpdateQuery2(getDataSource());
		this.restrictUserUpdateQuery = new RestrictUserUpdateQuery(getDataSource());
		this.restrictUserUpdateQuery2 = new RestrictUserUpdateQuery2(getDataSource());
		this.expertPoolDeleteQuery = new ExpertPoolDeleteQuery(getDataSource());
		this.expertPoolRetireveQuery = new ExpertPoolRetireveQuery(getDataSource());
		this.expertPoolByRole = new ExpertPoolRetireveByRoleQuery(getDataSource());
		this.expertPoolFindQuery = new ExpertPoolFindQuery(getDataSource());
		this.bankAccountKMACRetireveQuery = new BankAccountKMACRetireveQuery(getErpDataSource());
		this.bankAccountNonKMACRetireveQuery = new BankAccountNonKMACRetireveQuery(getErpDataSource());
		this.smGroupFindQuery = new SMGroupFindQuery(getDataSource());
		this.custermAddUserMemberQuery = new CustermAddUserMemberQuery(getDataSource());
		this.custermRemoveUserMemberQuery = new CustermRemoveUserMemberQuery(getDataSource());
		this.smRoleFindQuery = new SMRoleFindQuery(getDataSource());
		this.custermAddUserRoleQuery = new CustermAddUserRoleQuery(getDataSource());
		this.custermRemoveUserRoleQuery = new CustermRemoveUserRoleQuery(getDataSource());
		this.expertPoolUserIdQuery = new ExpertPoolUserIdQuery(getDataSource());
		this.smBUGroupFindQuery = new SMBUGroupFindQuery(getDataSource());	// Job Date: 2007-09-06
		this.smPUGroupFindQuery = new SMPUGroupFindQuery(getDataSource());	// Job Date: 2007-09-06
		this.expertPoolHistoryInsertQuery = new ExpertPoolHistoryInsertQuery(getDataSource());		// Job Date: 2008-01-11
		this.expertPoolHistoryUpdateQuery = new ExpertPoolHistoryUpdateQuery(getDataSource());		// Job Date: 2008-01-11
		this.expertSanctionLineInsertQuery = new ExpertSanctionLineInsertQuery(getDataSource());	// Job Date: 2010-07-05
		this.expertSanctionLineUpdateQuery = new ExpertSanctionLineUpdateQuery(getDataSource());	// Job Date: 2010-07-05
		this.expertPoolAccountHistoryInsertQuery = new ExpertPoolAccountHistoryInsertQuery(getDataSource());
		this.smBURoleFindQuery = new SMBURoleFindQuery(getDataSource());
		this.smPURoleFindQuery = new SMPURoleFindQuery(getDataSource());
		this.menuRoleFindQuery = new MenuRoleFindQuery(getDataSource());	// Job Date: 2010-02-05
		this.expertPoolRetireveNameQuery = new ExpertPoolRetireveNameQuery(getDataSource());
		this.expertPoolRetireveUidQuery = new ExpertPoolRetireveUidQuery(getDataSource());
		this.eduSystemAccountUpdateQuery = new EduSystemAccountUpdateQuery(getDataSource());
		this.dailyDownloadLimitInsertQuery = new DailyDownloadLimitInsertQuery(getDataSource());
		this.dailyDownloadLimitUpdateQuery = new DailyDownloadLimitUpdateQuery(getDataSource());
		this.dailyDownloadLimitStateUpdateQuery = new DailyDownloadLimitStateUpdateQuery(getDataSource());
		this.downloadLimitInitQuery = new DownloadLimitInitQuery(getDataSource()); 
		this.passwordResetQuery = new PasswordResetQuery(getDataSource());
		this.workTimeInsertQuery = new WorkTimeInsertQuery(getDataSource());
		this.budgetCheckInsertQuery = new BudgetCheckInsertQuery(getDataSource());
		this.dailyStoreHomeTaxValue = new DailyStoreHomeTaxValue(getDataSource());
		this.expertPoolRetireveQuery2 = new ExpertPoolRetireveQuery2(getDataSource());
		this.expertPoolRetireveQuery3 = new ExpertPoolRetireveQuery3(getDataSource());
		
		super.initDao();
	}

	
	@Override
	public synchronized String getSsn(String jobClass) throws DataAccessException {
		String sql = "	select '" + jobClass + "' + isNull(replicate('0', 6 - LEN(max(substring(ssn, 2, len(ssn)))+1))		"
				+ "				+ convert(varchar, (max(substring(ssn, 2, len(ssn)))+1)), '000001') as ssn 					"
				+ "		from expertpool where jobclass = '" + jobClass + "'";
		return (String) getJdbcTemplate().queryForObject(sql, String.class);
	}
	
	@Override
	public synchronized String getSsn2(String jobClass) throws DataAccessException {
		String sql = "	select '" + jobClass + "' + isNull(replicate('0', 6 - LEN(max(substring(ssn, 2, len(ssn)))+1))		"
				+ "				+ convert(varchar, (max(substring(ssn, 2, len(ssn)))+2)), '000001') as ssn 					"
				+ "		from expertpool where jobclass = '" + jobClass + "'";
		return (String) getJdbcTemplate().queryForObject(sql, String.class);
	}


	public void create(ExpertPool expertPool) throws DataAccessException {
		//if (expertPool.getCompanyPosition().equals("50EC")) {
		//	expertPool.setRate("0.24");
		//} else if (expertPool.getCompanyPosition().equals("51PC")) {
		//	expertPool.setRate("0.21");
		//} else if (expertPool.getCompanyPosition().equals("52FC")) {
		//	expertPool.setRate("0.18");
		//} else if (expertPool.getCompanyPosition().equals("53SC")) {
		//	expertPool.setRate("0.16");
		//} else if (expertPool.getCompanyPosition().equals("54NC")) {
		//	expertPool.setRate("0.14");
		//} else if (expertPool.getCompanyPosition().equals("55NCB")) {
		//	expertPool.setRate("0.12");
		//} else {
		//	expertPool.setRate("1");
		//}
		if (expertPool.getRate() == null || expertPool.getRate().equals("")) {
			expertPool.setRate("0");
		}
		this.expertPoolInsertQuery.insert(expertPool);
	}
	
	public void createHistory(ExpertPool expertPool) throws DataAccessException {
		String year = StringUtil.getCurr("yyyyMMdd").substring(0, 4);
		String month = StringUtil.getCurr("yyyyMMdd").substring(4, 6);
		String day = StringUtil.getCurr("yyyyMMdd").substring(6, 8);		

		this.expertPoolHistoryInsertQuery.insert(expertPool, year, month, day);		
	}
	
	public void createAccountHistory(String accessType, ExpertPool expertPool) throws DataAccessException {
		this.expertPoolAccountHistoryInsertQuery.insert(accessType, expertPool);		
	}

	public void store(ExpertPool expertPool) throws DataAccessException {
		this.expertPoolUpdateQuery.update(expertPool);
	}
	
	public void storeWithPwd(ExpertPool expertPool) throws DataAccessException {
		this.expertPoolUpdateQuery2.update(expertPool);
	}

	public void updateEduSystemAccount(ExpertPool expertPool) throws DataAccessException {
		this.eduSystemAccountUpdateQuery.update(expertPool);
	}
	
	public void updateRestrictUserState(String ssn, String state, String absence) throws DataAccessException {
		this.restrictUserUpdateQuery.updateState(ssn, state, absence);
	}
	
	public void updateRestrictContents(String ssn, String state, String absence, String restrictContents) throws DataAccessException {
		this.restrictUserUpdateQuery2.updateState(ssn, state, absence, restrictContents);
	}
	
	public void storeHistory(ExpertPool expertPool) throws DataAccessException {
		String year = StringUtil.getCurr("yyyyMMdd").substring(0, 4);
		String month = StringUtil.getCurr("yyyyMMdd").substring(4, 6);
		String day = StringUtil.getCurr("yyyyMMdd").substring(6, 8);

		// expertPool에 등록되어 있는 인력 소속이 PU로 변경되는 경우  이력관리에 추가함
		// (예: 비상임이 PU로 채용, 공채 Associate가 PU로 발령 나는 경우 )		
		if (isExpertInHistory(expertPool.getSsn(), year, month))
			this.expertPoolHistoryUpdateQuery.update(expertPool, year, month, day);
		else
			this.expertPoolHistoryInsertQuery.insert(expertPool, year, month, day);
	}
	
	public void storeSanctionLine(ExpertPool expertPool) throws DataAccessException {

		// 사용자 부서 변경 시 결재라인 본인 정보 업데이트 함
		if (isExpertInSanctionLine(expertPool.getSsn()))
			this.expertSanctionLineUpdateQuery.update(expertPool);
		else
			this.expertSanctionLineInsertQuery.insert(expertPool);
	}

	public void remove(String ssn) throws DataAccessException {
		this.expertPoolDeleteQuery.delete(ssn);
	}

	public ExpertPool retrieve(String ssn) throws DataAccessException {
		Object object = this.expertPoolRetireveQuery.findObject(ssn);
		if (object == null) {
			throw new ObjectRetrievalFailureException(ExpertPool.class, ssn);
		}
		return (ExpertPool) object;
	}
	
	public ExpertPool retrieve2(String fileId) throws DataAccessException {
		Object object = this.expertPoolRetireveQuery2.findObject(fileId);

		return (ExpertPool) object;
	}
	
	public ExpertPool retrieve3(String fileId) throws DataAccessException {
		Object object = this.expertPoolRetireveQuery3.findObject(fileId);

		return (ExpertPool) object;
	}
	
	/* 출근 시간 확인 */
	public String selectWorkDayOn(String ssn, String date) throws DataAccessException {
		String result = "";
		try{
			result =  (String) getJdbcTemplate().queryForMap(" select (left(workDate, 2)+'시 '+substring(workDate, 4, 2) + '분 ') as workDate from("
					+ "select top 1 CONVERT(CHAR(8),workDate,108) as workDate From workDay where workType = 'on' and ssn = '" + ssn + "' and CONVERT(CHAR(10),workDate,23) = '" + date + "')q ").get("workDate");
		}catch(EmptyResultDataAccessException e){
			result="";
		}
		return result;
	}
	
	/* 출근 시간 확인 */
	public String selectWorkDayOff(String ssn, String date) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap(" select (left(workDate, 2)+'시 '+substring(workDate, 4, 2) + '분 ') as workDate from("
					+ "select top 1 CONVERT(CHAR(8),workDate,108) as workDate From workDay where workType = 'off' and ssn = '" + ssn + "' and CONVERT(CHAR(10),workDate,23) = '" + date + "')q ").get("workDate");
		}catch(EmptyResultDataAccessException e){
			result="";
		}
		return result;
	}
	
	/* 출근 시간 확인 */
	public String selectWorkDayCnt(String ssn, String date) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap(" select top 1 ssn as ssn From schedule where [type] = 'Up-day' and ssn = '" + ssn + "' and (year+month+day) = '" + date + "' ").get("ssn");
		}catch(EmptyResultDataAccessException e){
			result="";
		}
		return result;
	}
	
	/* 인력 등록 여부 확인 */
	public String selectExpertPoolUidCheck(String uid) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap(" SELECT SSN FROM EXPERTPOOL WHERE UID = SecureDB.DBSEC.ENCRYPT_AES('" + uid + "') ").get("ssn");
		}catch(EmptyResultDataAccessException e){
			result="";
		}
		return result;
	}
	
	public String selectDeptChk(String dept) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap(" select id from smgroup where memberRule='A' and description = (select description from smgroup where memberRule='J' and id= '" + dept + "') ").get("id");
		}catch(EmptyResultDataAccessException e){
			result="";
		}
		return result;
	}
	
	public String selectProjectChk(String fileId) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap("select pm.ssn From projecttaskformattachdata p left join (select projectcode, ssn from projectmember where role='PM' and trainingYN='Y' )pm on p.projectcode = pm.projectcode where attachFileId= '" + fileId + "' ").get("ssn");
		}catch(EmptyResultDataAccessException e){
			result = "";
		}
		return result;
	}
	
	public String selectProjectDeptChk(String fileId) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap("select p.runningDivCode as dept From projecttaskformattachdata pt left join ( select * from project )p on pt.projectCode = p.projectCode where pt.projectcode not in ('', '8888888') and pt.attachFileId= '" + fileId + "' ").get("dept");
		}catch(EmptyResultDataAccessException e){
			result = "";
		}
		return result;
	}
	
	public String selectConsultantDeptChk(String ssn) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap("select isnull(w.parentId, '') as dept from (select e.ssn, sm.id, sm.description from expertpool e left join(select id, name, memberRule, description, parentId from smgroup where enabled='1' and memberRule=(select jobClass from expertpool where ssn='" + ssn + "'))sm on e.dept = sm.id )q left join(select id, name, memberRule, description, parentId from smgroup where enabled='1' and memberRule=(select jobClass from expertpool where ssn='" + ssn + "'))w on q.description = w.description where ssn= '" + ssn + "' and w.parentId = (case when ssn = 'G003279' then '9360' else w.parentId end) ").get("dept");
		}catch(EmptyResultDataAccessException e){
			result = "";
		}
		return result;
	}
	
	public String selectFileProjectCode(String fileId) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap(" select isnull(projectcode, '') as projectcode From ProjectTaskFormAttachData where attachFileId= '" + fileId + "' ").get("projectcode");
		}catch(EmptyResultDataAccessException e){
			result = "";
		}
		return result;
	}
	
	public String selectFileProjectChk(String ssn, String projectCode) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap("select case when count(ssn) > 0 then 'Y' else 'N' end as result from (select  pm.ssn From project p	left join (select * From projectmember)pm on p.projectcode = pm.projectcode where p.projectcode='" + projectCode + "' and ssn='" + ssn + "')q ").get("result");
		}catch(EmptyResultDataAccessException e){
			result = "";
		}
		return result;
	}
	
	public String selectPastFileProjectChk(String fileId, String ssn) throws DataAccessException {
		String result = "";
		try{
			result = (String) getJdbcTemplate().queryForMap(" 	select (case when p.runningDivCode = (select runningDivCode from project p where p.projectCode = (select pt.projectCode from projecttaskformattachdata pt where pt.attachFileId = '" + fileId + "')) then 'Y' else 'N' end) from ( "
					+ " select (case (select parentId from smgroup where id= (select dept From expertpool where ssn = '" + ssn + "' )) "
					+ " when '9300' then '9010' when '9310' then '9010' when '9320' then '9020' when '9330' then '9020' when '9340' then '9060' when '9350' then '9040' else '' end) as runningDivCode "
					+ " )p ").get("checkYN");
		}catch(EmptyResultDataAccessException e){
			result = "";
		}
		return result;
	}
	
	public ExpertPool retrieveUserId(String userId) throws DataAccessException {
		Object object = this.expertPoolUserIdQuery.findObject(userId);
		if (object == null) {
			throw new ObjectRetrievalFailureException(ExpertPool.class, userId);
		}
		return (ExpertPool) object;
	}
	
	public ExpertPool retrieveOfficer(String deptCode) throws DataAccessException {
		//List<ExpertPool> expertList =  new ArrayList<ExpertPool>();
		String query = "SELECT * FROM ExpertPool WITH(NOLOCK) WHERE companyPosition IN ('05CC','06CB','08CF','09CJ') "
				+" and dept IN (CASE " + deptCode + " WHEN '9042' THEN '9040' WHEN '9031' THEN '9030' ELSE " + deptCode + " END) ";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ExpertPool expert = new ExpertPool();
		
		try {
			conn = getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				expert.setSsn(rs.getString("ssn"));
				expert.setName(rs.getString("name"));
				expert.setJobClass(rs.getString("jobclass"));
				expert.setCompanyPosition(rs.getString("companyPosition"));
				expert.setCompanyPositionName(rs.getString("companyPositionName"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {	}
		}
		return expert;
	}	
	
	public ExpertPool retrieveOfficer2(String deptCode) throws DataAccessException {
		//List<ExpertPool> expertList =  new ArrayList<ExpertPool>();
		String query = " SELECT * FROM ExpertPool WITH(NOLOCK) WHERE dept IN 																		"
			+ "			(  																															"
			+ "			SELECT CASE parentId WHEN '2000' THEN '7000' WHEN '6200' THEN '9030' ELSE parentId END AS parentId FROM 					"
			+ "					( 																													"
			+ "					SELECT * 																											"
			+ "					FROM smgroup 																										"
			+ "					WHERE enabled='1'																									"
			+ "					AND	memberRule not in('C','J')																						"		
			+ "					AND id = case	 " + deptCode 
			+ " 				WHEN '9083' THEN '7000' WHEN '9067' THEN '7000' WHEN '9065' THEN '7000' ELSE  convert(char(20), "+ deptCode +")		" 
			+ "					END 																												"
			+ "					)q 																													"
			+ "			) 																															"
			+ "			AND companyPosition IN ('05CC','07CC','06CB','09CJ') 																		" ;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ExpertPool expert = new ExpertPool();
		
		try {
			conn = getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				expert.setSsn(rs.getString("ssn"));
				expert.setName(rs.getString("name"));
				expert.setJobClass(rs.getString("jobclass"));
				expert.setCompanyPosition(rs.getString("companyPosition"));
				expert.setCompanyPositionName(rs.getString("companyPositionName"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {	}
		}
		return expert;
	}
	
	@Override
	public BankAccount retrieveBankAccountKMAC(String emplNo) throws DataAccessException {
		Object object = this.bankAccountKMACRetireveQuery.findObject(emplNo);
		if (object == null) {
			throw new ObjectRetrievalFailureException(ExpertPool.class, emplNo);
		}
		return (BankAccount) object;
	}


	@Override
	public BankAccount retrieveBankAccountNonKMAC(String uid) throws DataAccessException {
		Object object = this.bankAccountNonKMACRetireveQuery.findObject(uid);
		if (object == null) {
			throw new ObjectRetrievalFailureException(ExpertPool.class, uid);
		}
		return (BankAccount) object;
	}


	@SuppressWarnings("unchecked")
	public List<ExpertPool> findAll() throws DataAccessException {
		return this.expertPoolFindQuery.execute();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertPool> findAsName(String name) throws DataAccessException {
		return this.expertPoolRetireveNameQuery.execute(new Object[] { "%" + name + "%" });
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpertPool> findAsUid(String uid) throws DataAccessException {
		return this.expertPoolRetireveUidQuery.execute(new Object[] { uid });
	}

	@SuppressWarnings("unchecked")
	public List<CustermGroup> findGroup() throws DataAccessException {
		return this.smGroupFindQuery.execute();
	}

	public void addUserMember(String parentId, String userId) throws DataAccessException {
		this.custermAddUserMemberQuery.add(parentId, userId);
	}

	public void removeUserMember(String userId) throws DataAccessException {
		this.custermRemoveUserMemberQuery.remove(userId);
	}

	@SuppressWarnings("unchecked")
	public List<CustermRole> findRole() throws DataAccessException {
		return this.smRoleFindQuery.execute();
	}
	
	@SuppressWarnings("unchecked")
	public List<CustermRole> findBURole() throws DataAccessException {
		return this.smBURoleFindQuery.execute();
	}
	
	@SuppressWarnings("unchecked")
	public List<CustermRole> findPURole() throws DataAccessException {
		return this.smPURoleFindQuery.execute();
	}
	
	@SuppressWarnings("unchecked")
	public List<CustermRole> findMenuRole() throws DataAccessException {
		return this.menuRoleFindQuery.execute();
	}

	public void addUserRole(String userId, String roleId) throws DataAccessException {
		this.custermAddUserRoleQuery.add(userId, roleId);
	}

	public void removeUserRole(String userId) throws DataAccessException {
		this.custermRemoveUserRoleQuery.remove(userId);
	}

	// Job Date: 2007-12-18	Author: yhyim	Description: 프로젝트 관련 인원(BU장, 팀장, 프로젝트 멤버) 이메일 산출 쿼리
	public List<ListOrderedMap> getProjectInvolvedMembers(String projectCode) {
		String query = "SELECT * 	"
				+ "	  FROM (															"
				+ "		SELECT e.ssn, e.name, e.email, e.dept, m.role					"
				+ "		  FROM projectMember m, expertpool e, project p					"
				+ "		 WHERE m.ssn = e.ssn 											"
				+ "		   AND m.projectCode = p.projectCode							"
				+ "		   AND m.trainingYN = 'Y'										"
				+ "		   AND e.jobClass IN ('A', 'B', 'C', 'J')						"
				+ "		   AND p.projectCode = '" + projectCode + "'"
				+ "		UNION															"
				+ "		SELECT ssn, name, email, dept, companyPosition as role			"
				+ "		  FROM expertpool 												"
				+ "		 WHERE companyPosition = '10TM' 								"
				+ "		   AND enable = 1												"
				+ "		   AND dept = (													"
				+ "			SELECT e.dept												"
				+ "			  FROM projectMember m, expertpool e						"
				+ "			 WHERE m.ssn = e.ssn 										"
				+ "			   AND m.role = 'PM'										"
				+ "			   AND m.trainingYN = 'Y'									"
				//+ "		   	   AND e.jobClass IN ('A', 'B', 'C', 'J')					"
				+ "			   AND m.projectcode = '" + projectCode + "')"
				+ "		UNION															"
				+ "		SELECT ssn, name, email, dept, companyPosition as role			"
				+ "		  FROM expertpool 												"
				+ "		 WHERE (companyPosition = '08CF' or companyPosition = '09CJ')	"
				+ "		   AND enable = 1												"
				+ "		   AND dept = (													"
				+ "			SELECT substring(e.dept, 0, 4) + '0'						"
				+ "			  FROM projectMember m, expertpool e						"
				+ "			 WHERE m.ssn = e.ssn										"
				+ "			   AND m.role = 'PM' 										"
				+ "			   AND m.trainingYN = 'Y'									"
				//+ "		   	   AND e.jobClass IN ('A', 'B', 'C', 'J')					"
				+ "			   AND m.projectcode = '" + projectCode + "')"
				+ "	  ) T																"
				+ "	WHERE email <> '' AND email IS NOT NULL								"
				+ "	ORDER BY role";
		return getJdbcTemplate().queryForList(query);
	}


	public boolean isExist(String ssn) throws DataAccessException {
		Object object = this.expertPoolRetireveQuery.findObject(ssn);
		return object != null;
	}

	public boolean isExistUserId(String ssn) throws DataAccessException {
		Object object = this.expertPoolUserIdQuery.findObject(ssn);
		return object != null;
	}
	
	public boolean isAbsence(String ssn) throws DataAccessException {
		return ((ExpertPool) this.expertPoolRetireveQuery.findObject(ssn)).getAbsence().equals("1");
	}

//	public void updateLoginInfo(String ssn, String password, String enable, String absence, String modifierId) throws DataAccessException {
//		this.expertPoolLoginInfoUpdateQuery.update(ssn,
//				password,
//				enable,
//				absence,
//				modifierId);
//	}
	
//	public void storeResume(ExpertPool expertPool) throws DataAccessException {
//		this.expertPoolResumeUpdateQuery.resumeUpdate(expertPool.getResume(),
//				expertPool.getModifierId(),
//				expertPool.getSsn());
//	}
	

	/*
	 * 전문가 등록시 같은 주민번호가 있는지 확인
	 */
	public int checkSsn(String ssn) throws DataAccessException {
		int checkSsn = getJdbcTemplate().queryForInt(" SELECT COUNT(*) FROM expertPool WHERE uid = '" + ssn + "' ");
		return checkSsn;
	}

	/*
	 * UserId 등록시 같은 UserId가 있는지 확인
	 */
	public int checkUserId(String userId) throws DataAccessException {
		int checkUserId = getJdbcTemplate().queryForInt(" SELECT COUNT(*) FROM expertPool WHERE userId = '" + userId + "' ");		
		return checkUserId;
	}
	
	/*
	 * 인사발령 이력관리 특정 기간에 주민번호가 있는지 확인
	 */
	public boolean isExpertInHistory(String ssn, String year, String month) throws DataAccessException {
		int checkExpert = getJdbcTemplate().queryForInt(" SELECT COUNT(ssn) FROM deptHistory "
				+ "  WHERE  ssn = '" + ssn + "'	"
				+ "	   AND	year = '" + year + "'	"
				+ "	   AND	month = '" + month + "'	");		

		if (checkExpert > 0)
			return true;
		else
			return false;
	}
	
	/*
	 * 인사발령 이력관리 특정 기간에 주민번호가 있는지 확인
	 */
	public boolean isExpertInSanctionLine(String ssn) throws DataAccessException {
		int checkExpert = getJdbcTemplate().queryForInt(" SELECT COUNT(id) FROM sanctionLine "
				+ "  WHERE  id = '" + ssn + "'	");		

		if (checkExpert > 0)
			return true;
		else
			return false;
	}
	
	public boolean isUserInDailyDownloadLimitLog(String ssn) throws DataAccessException {
		int checkUser = getJdbcTemplate().queryForInt(" SELECT COUNT(ssn) FROM DailyDownloadLimit "
				+ "	WHERE	ssn = '" + ssn + "'	");
		if (checkUser > 0)
			return true;
		else
			return false;
	}
	
	public boolean isDailyDownloadUnlimitUser(String ssn) throws DataAccessException {
		int checkUser = getJdbcTemplate().queryForInt(" SELECT COUNT(ssn) FROM DailyDownloadLimit "
				+ "	WHERE	isUnLimit='Y'	AND	ssn = '" + ssn + "'	");		
		if (checkUser > 0)
			return true;
		else
			return false;
	}
	
	public boolean isCompanyManager(String ssn) throws DataAccessException {
		int checkManager = getJdbcTemplate().queryForInt(" SELECT COUNT(ssn) FROM expertPool "
				+ " WHERE companyPosition <= '10TM' AND ssn = '" + ssn + "'	");
		if (checkManager > 0)
			return true;
		else
			return false;
	}
	
	public String getEmail(String ssn) throws DataAccessException {
		return (String) getJdbcTemplate().queryForMap(" select email from expertPool where ssn = '" + ssn + "' ").get("email");
	}
	
	public String getName(String ssn) throws DataAccessException {
		return (String) getJdbcTemplate().queryForMap(" select name from expertPool where ssn = '" + ssn + "' ").get("name");
	}
	
	public String getMenuRole(String ssn) throws DataAccessException {
		return (String) getJdbcTemplate().queryForMap(" select role from expertPool where ssn = '" + ssn + "' ").get("role");
	}

	@SuppressWarnings("unchecked")
	public List<CustermGroup> findBUGroup() throws DataAccessException {
		return this.smBUGroupFindQuery.execute();
	}

	@SuppressWarnings("unchecked")
	public List<CustermGroup> findPUGroup() throws DataAccessException {
		return this.smPUGroupFindQuery.execute();
	}
	
	/**
	 * @return Returns the expertPoolCareerHstDao.
	 */
	public ExpertPoolCareerHstDao getExpertPoolCareerHstDao() {
		return this.expertPoolCareerHstDao;
	}

	/**
	 * @param expertPoolCareerHstDao The expertPoolCareerHstDao to set.
	 */
	public void setExpertPoolCareerHstDao(ExpertPoolCareerHstDao expertPoolCareerHstDao) {
		this.expertPoolCareerHstDao = expertPoolCareerHstDao;
	}

	public ExpertPoolSpecialFieldDao getExpertPoolSpecialFieldDao() {
		return expertPoolSpecialFieldDao;
	}

	public void setExpertPoolSpecialFieldDao(ExpertPoolSpecialFieldDao expertPoolSpecialFieldDao) {
		this.expertPoolSpecialFieldDao = expertPoolSpecialFieldDao;
	}

	/**
	 * @return Returns the expertPoolSchoolHstDao.
	 */
	public ExpertPoolSchoolHstDao getExpertPoolSchoolHstDao() {
		return this.expertPoolSchoolHstDao;
	}

	/**
	 * @param expertPoolSchoolHstDao The expertPoolSchoolHstDao to set.
	 */
	public void setExpertPoolSchoolHstDao(ExpertPoolSchoolHstDao expertPoolSchoolHstDao) {
		this.expertPoolSchoolHstDao = expertPoolSchoolHstDao;
	}

	public List<ExpertPool> findByRole(String roleNum, String extRoleNum) throws DataAccessException {
		return this.expertPoolByRole.execute(new Object[]{roleNum, extRoleNum });
	}
	
	@Override
	public ExpertPool getDivManager(String divCode) throws DataAccessException {
		return (ExpertPool)getJdbcTemplate().queryForObject("select * from expertPool where ssn in (select data_2 from cmtabledata where table_name='RUNNING_DIV_CODE' and key_1=?) ",
				new Object[] { divCode }, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
						ExpertPool expertPool = new ExpertPool();
						expertPool.setSsn(rs.getString("ssn"));
						expertPool.setName(rs.getString("name"));
						expertPool.setDept(rs.getString("dept"));
						expertPool.setDeptName(rs.getString("deptName"));
						expertPool.setCompanyPosition(rs.getString("companyPosition"));
						expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
						expertPool.setJobClass(rs.getString("jobClass"));
						expertPool.setEmail(rs.getString("email"));
						return expertPool;
					}
				});
	}
	
	// 
	public ExpertPool retrieve(String dept, String companyPosition) throws DataAccessException  {
		//List<ExpertPool> expertList =  new ArrayList<ExpertPool>();
		String query = "SELECT * FROM expertPool WHERE enable='1' "
			+ " AND dept='" + dept + "'"
			+ " AND companyPosition='" + companyPosition + "'" ;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ExpertPool expert = new ExpertPool();
		
		try {
			conn = getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				expert.setSsn(rs.getString("ssn"));
				expert.setName(rs.getString("name"));
				expert.setCompanyPosition(rs.getString("companyPosition"));
				expert.setCompanyPositionName(rs.getString("companyPositionName"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {	}
		}
		return expert;
	}

	public int getExpertPoolCnt(HashMap<String, String> param) throws DataAccessException {
		String query = "";
		query += " 	select	count(e.ssn) ";
		query += " 	from expertPool e left outer join ( ";
		query += " 	select ssn,";
		query += " 	REPLACE(RTRIM((SELECT CAST(specialName AS VARCHAR(MAX)) + ', ' FROM ExpertPoolSpecialField WHERE (ssn = e.ssn) FOR XML PATH (''))),' ',' ') AS consultingMajor";
		query += " 	from ExpertPoolSpecialField e group by ssn";
		query += " 	) s on e.ssn = s.ssn";
		query += " 	where 1=1";
		if (param.get("keyfield").equals("1"))
			query += " 	AND	name like '" + param.get("name") + "'";
		if (param.get("keyfield").equals("2"))
			query += " 	AND	company like '" + param.get("company") + "'";
		if (param.get("keyfield").equals("3"))
			query += " 	AND	jobClass = '" + param.get("jobClass") + "'";
		if (param.get("keyfield").equals("4"))
			query += " 	AND	createrid like '" + param.get("createrid") + "'";
		if (param.get("specialField") != null && param.get("specialField").length() > 0)
			query += " 	AND	e.ssn in (select distinct ssn from ExpertPoolSpecialField where specialId in ( " + param.get("specialField") + " ))";
		if (param.get("restrict").equals(""))
			query += " 	AND e.restrictUser <> 'Y' \n";

		return getJdbcTemplate().queryForInt(query);

	}
	
	public int getExpertPoolExtCnt(HashMap<String, String> param) throws DataAccessException {
		String query = "";
		query += " 	select	count(e.ssn) ";
		query += " 	from expertPool e left outer join ( ";
		query += " 	select ssn,";
		query += " 	REPLACE(RTRIM((SELECT CAST(specialName AS VARCHAR(MAX)) + ', ' FROM ExpertPoolSpecialField WHERE (ssn = e.ssn) FOR XML PATH (''))),' ',' ') AS consultingMajor";
		query += " 	from ExpertPoolSpecialField e group by ssn";
		query += " 	) s on e.ssn = s.ssn";
		query += " 	where 1=1";
		query += " 	AND e.restrictUser <> 'Y' \n";
		query += " 	AND	jobClass like '" + param.get("jobClass") + "' \n";
		query += " 	AND	name like '" + param.get("name") + "' \n";
		query += " 	AND	dept like '" + param.get("dept") + "' \n";
		query += " 	AND	deptName like '" + param.get("deptName") + "' \n";
		query += " 	AND	company like '" + param.get("company") + "' \n";
		if (param.get("keyfield").equals("4"))
			query += " 	AND	createrid like '" + param.get("createrid") + "'";
		if (param.get("keyfield").equals("5"))
			query += " 	AND	dept like '" + param.get("runningDeptCode") + "' \n";
		if (param.get("specialField") != null && param.get("specialField").length() > 0)
			query += " 	AND	e.ssn in (select distinct ssn from ExpertPoolSpecialField where specialId in ( " + param.get("specialField") + " ))";

		return getJdbcTemplate().queryForInt(query);

	}
	
	public int getExpertPoolExtCnt2(HashMap<String, String> param) throws DataAccessException {
		String query = "";
		query += " 	select	count(e.ssn) ";
		query += " 	from expertPool e left outer join ( ";
		query += " 	select ssn,";
		query += " 	REPLACE(RTRIM((SELECT CAST(specialName AS VARCHAR(MAX)) + ', ' FROM ExpertPoolSpecialField WHERE (ssn = e.ssn) FOR XML PATH (''))),' ',' ') AS consultingMajor";
		query += " 	from ExpertPoolSpecialField e group by ssn";
		query += " 	) s on e.ssn = s.ssn";
		query += " 	where 1=1";
		query += "  AND jobClass in ('A','B','J','N') \n";
		query += " 	AND e.restrictUser <> 'Y' \n";
		query += " 	AND	jobClass like '" + param.get("jobClass") + "' \n";
		query += " 	AND	name like '" + param.get("name") + "' \n";
		query += " 	AND	dept like '" + param.get("dept") + "' \n";
		query += " 	AND	deptName like '" + param.get("deptName") + "' \n";
		query += " 	AND	company like '" + param.get("company") + "' \n";
		if (param.get("keyfield").equals("4"))
			query += " 	AND	createrid like '" + param.get("createrid") + "'";
		if (param.get("keyfield").equals("5"))
			query += " 	AND	dept like '" + param.get("runningDeptCode") + "' \n";
		if (param.get("specialField") != null && param.get("specialField").length() > 0)
			query += " and	e.ssn in (select distinct ssn from ExpertPoolSpecialField where specialId in ( " + param.get("specialField") + " ))";

		return getJdbcTemplate().queryForInt(query);

	}
	
	public int getProjectExpertPoolCnt(HashMap<String, String> param) throws DataAccessException {
		String query = "";
		query += " 	select	count(e.ssn) ";
		query += " 	from expertPool e left outer join ( ";
		query += " 	select ssn,";
		query += " 	REPLACE(RTRIM((SELECT CAST(specialName AS VARCHAR(MAX)) + ', ' FROM ExpertPoolSpecialField WHERE (ssn = e.ssn) FOR XML PATH (''))),' ',' ') AS consultingMajor";
		query += " 	from ExpertPoolSpecialField e group by ssn";
		query += " 	) s on e.ssn = s.ssn";
		query += " 	where 1=1 and e.enable='1' ";
		query += " 	AND	jobClass = '" + param.get("jobClass") + "'";
		if (param.get("jobClass").equals("C"))
			query += " 	AND	e.companyPosition <> '엑스퍼트' \n";
		if (param.get("jobClass").equals("H"))
			query += " 	AND	e.companyPosition IN ('61DT','62ET','63FT') \n";
		if (param.get("name") != null)
			query += " 	AND	name like '" + param.get("name") + "' \n";
		if (param.get("rank") != null)
			query += " 	AND	e.rank = '" + param.get("rank") + "' \n";
		if (param.get("specialField") != null && param.get("specialField").length() > 0)
			query += " 	AND	e.ssn in (select distinct ssn from ExpertPoolSpecialField where specialId in ( " + param.get("specialField") + " ))";
		if (param.get("restrict").equals(""))
			query += " 	AND e.restrictUser <> 'Y' \n";

		return getJdbcTemplate().queryForInt(query);

	}
	
	public List<ExpertPool> getExpertPoolList(HashMap<String, String> param)
			throws DataAccessException {
		List<ExpertPool> list = new ArrayList<ExpertPool>();
		String query = "";
		query += " SELECT * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY ssn DESC) rnum, * FROM ( \n";
		query += " 	select	e.ssn, e.name, e.gender, e.company, s.consultingMajor, createrid \n";
		query += " 	from expertPool e left outer join ( \n";
		query += " 	select ssn, \n";
		query += " 	REPLACE(RTRIM((SELECT CAST(specialName AS VARCHAR(MAX)) + ', ' FROM ExpertPoolSpecialField WHERE (ssn = e.ssn) FOR XML PATH (''))),' ',' ') AS consultingMajor \n";
		query += " 	from ExpertPoolSpecialField e group by ssn \n";
		query += " 	) s on e.ssn = s.ssn \n";
		query += " 	where 1=1 \n";
		if (param.get("keyfield").equals("1"))
			query += " 	AND	name like '" + param.get("name") + "' \n";
		if (param.get("keyfield").equals("2"))
			query += " 	AND	company like '" + param.get("company") + "' \n";
		if (param.get("keyfield").equals("3"))
			if(param.get("jobClass").equals("A")){
				query += " 	AND	jobClass in ('" + param.get("jobClass") + "','B')";
			} else if (param.get("jobClass").equals("H")){
				query += " 	AND	jobClass = '" + param.get("jobClass") + "'";
				query += "  AND companyposition != '64GT' ";
			} else if (param.get("jobClass").equals("H2")){
				query += "  AND companyposition = '64GT' ";
			} else {
				query += " 	AND	jobClass = '" + param.get("jobClass") + "'";
			}
		if (param.get("keyfield").equals("4"))
			query += " 	AND	createrid like '" + param.get("createrid") + "' \n"; 
		if (param.get("specialField") != null && param.get("specialField").length() > 0)
			query += " 	AND	e.ssn in (select distinct ssn from ExpertPoolSpecialField where specialId in ( " + param.get("specialField") + " ))";
		if (param.get("restrict").equals(""))
			query += " 	and e.restrictUser <> 'Y' \n";			
		query += " ) RES ) A WHERE rnum BETWEEN (("+param.get("pg")+" * 15) -14) AND ("+param.get("pg")+" * 15) \n";
		query += "order by name \n";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				ExpertPool expertPool = new ExpertPool();
				expertPool.setSsn(rs.getString("ssn"));
				expertPool.setName(rs.getString("name"));
				expertPool.setGender(rs.getString("gender"));
				expertPool.setCompany(rs.getString("company"));
				expertPool.setConsultingMajor(rs.getString("consultingMajor"));
				
				list.add(expertPool);
			}			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {	}
		}	
		return list;
	}
	
	public List<ExpertPool> getExpertPoolExtList(HashMap<String, String> param)
			throws DataAccessException {
		List<ExpertPool> list = new ArrayList<ExpertPool>();
		String query = "";
		query += " SELECT * FROM ( SELECT ROW_NUMBER() OVER (order by (case jobClass when 'A' then '1' when 'B' then '2' when 'J' then '3' when 'H' then '4' when 'N' then '5' when 'C' then '6' when 'D' then '7' when 'E' then '8' else '9' end), name) rnum, * FROM ( \n";
		query += " 	select e.jobClass, e.ssn, e.dept, left(SecureDB.DBSEC.DECRYPT_AES((e.uid)),7) as uid, e.companyTelNo, e.name, e.mobileNo, e.gender, e.email, e.company, e.photo, e.deptName, e.companyPositionName, s.consultingMajor, dbo.getExpertPoolName(e.createrId) as createrName \n";
		query += " 	from expertPool e left outer join ( \n";
		query += " 	select ssn, \n";
		query += " 	REPLACE(RTRIM((SELECT CAST(specialName AS VARCHAR(MAX)) + ', ' FROM ExpertPoolSpecialField WHERE (ssn = e.ssn) FOR XML PATH (''))),' ',' ') AS consultingMajor \n";
		query += " 	from ExpertPoolSpecialField e group by ssn \n";
		query += " 	) s on e.ssn = s.ssn \n";
		query += " 	where 1=1 \n";
		query += " 	and e.restrictUser <> 'Y' \n";	
		query += " 	AND	jobClass like '" + param.get("jobClass") + "' \n";
		query += " 	AND	name like '" + param.get("name") + "' \n";
		query += " 	AND	dept like '" + param.get("dept") + "' \n";
		query += " 	AND	deptName like '" + param.get("deptName") + "' \n";
		query += " 	AND	company like '" + param.get("company") + "' \n";
		query += "  AND e.ssn != 'A000006' ";
		if (param.get("keyfield").equals("3"))
			if(param.get("jobClass").equals("A")){
				query += " 	AND	jobClass in ('" + param.get("jobClass") + "','B')";
			} else if (param.get("jobClass").equals("H")){
				query += " 	AND	jobClass = '" + param.get("jobClass") + "'";
				query += "  AND companyposition != '64GT' ";
			} else if (param.get("jobClass").equals("H2")){
				query += "  AND companyposition = '64GT' ";
			} else {
				query += " 	AND	jobClass = '" + param.get("jobClass") + "'";
			}
		if (param.get("keyfield").equals("5"))
			query += " 	AND	dept like '" + param.get("runningDeptCode") + "' \n";
		if (param.get("keyfield").equals("4"))
			query += " 	AND	createrid like '" + param.get("createrid") + "' \n"; 
		if (param.get("specialField") != null && param.get("specialField").length() > 0)
			query += " 	AND	e.ssn in (select distinct ssn from ExpertPoolSpecialField where specialId in ( " + param.get("specialField") + " ))";
		query += " ) RES ) A WHERE rnum BETWEEN (("+param.get("pg")+" * 10) -9) AND ("+param.get("pg")+" * 10) \n";
		query += "order by (case jobClass when 'A' then '1' when 'B' then '2' when 'J' then '3' when 'H' then '4' when 'N' then '5' when 'C' then '6' when 'D' then '7' when 'E' then '8' else '9' end), name \n";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				ExpertPool expertPool = new ExpertPool();
				expertPool.setSsn(rs.getString("ssn"));
				expertPool.setUid(rs.getString("uid"));
				expertPool.setName(rs.getString("name"));
				expertPool.setGender(rs.getString("gender"));
				expertPool.setCompany(rs.getString("company"));
				expertPool.setDeptName(rs.getString("deptName"));
				expertPool.setConsultingMajor(rs.getString("consultingMajor"));
				expertPool.setCreaterId(rs.getString("createrName"));
				expertPool.setRunningDeptCode(rs.getString("dept"));
				expertPool.setJobClass(rs.getString("jobClass"));
				expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
				expertPool.setEmail(rs.getString("email"));
				expertPool.setCompanyTelNo(rs.getString("companyTelNo"));
				expertPool.setDept(rs.getString("dept"));
				expertPool.setPhoto(rs.getString("photo"));
				expertPool.setMobileNo(rs.getString("mobileNo"));
				list.add(expertPool);
			}			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {	}
		}	
		return list;
	}
	
	public List<ExpertPool> getExpertPoolExtList2(HashMap<String, String> param)
			throws DataAccessException {
		List<ExpertPool> list = new ArrayList<ExpertPool>();
		String query = "";
		query += " SELECT * FROM ( SELECT ROW_NUMBER() OVER (order by (case jobClass when 'A' then '1' when 'B' then '2' when 'J' then '3' when 'H' then '4' when 'N' then '5' when 'C' then '6' when 'D' then '7' when 'E' then '8' else '9' end), name) rnum, * FROM ( \n";
		query += " 	select e.jobClass, e.ssn, e.dept, left(SecureDB.DBSEC.DECRYPT_AES((e.uid)),7) as uid, e.companyTelNo, e.name, e.gender, e.mobileNo, e.photo, e.email, e.company, e.deptName, e.companyPositionName, s.consultingMajor, dbo.getExpertPoolName(e.createrId) as createrName \n";
		query += " 	from expertPool e left outer join ( \n";
		query += " 	select ssn, \n";
		query += " 	REPLACE(RTRIM((SELECT CAST(specialName AS VARCHAR(MAX)) + ', ' FROM ExpertPoolSpecialField WHERE (ssn = e.ssn) FOR XML PATH (''))),' ',' ') AS consultingMajor \n";
		query += " 	from ExpertPoolSpecialField e group by ssn \n";
		query += " 	) s on e.ssn = s.ssn \n";
		query += " 	where 1=1 \n";
		query += " 	and e.restrictUser <> 'Y' \n";	
		query += "  and jobClass in ('A','B','J','N') \n";
		query += " 	AND	jobClass like '" + param.get("jobClass") + "' \n";
		query += " 	AND	name like '" + param.get("name") + "' \n";
		query += " 	AND	dept like '" + param.get("dept") + "' \n";
		query += " 	AND	deptName like '" + param.get("deptName") + "' \n";
		query += " 	AND	company like '" + param.get("company") + "' \n";
		query += "  AND e.ssn != 'A000006' ";
		if (param.get("keyfield").equals("3"))
			if(param.get("jobClass").equals("A")){
				query += " 	AND	jobClass in ('" + param.get("jobClass") + "','B')";
			} else if (param.get("jobClass").equals("H")){
				query += " 	AND	jobClass = '" + param.get("jobClass") + "'";
				query += "  AND companyposition != '64GT' ";
			} else if (param.get("jobClass").equals("H2")){
				query += "  AND companyposition = '64GT' ";
			} else {
				query += " 	AND	jobClass = '" + param.get("jobClass") + "'";
			}
		if (param.get("keyfield").equals("5"))
			query += " 	AND	dept like '" + param.get("runningDeptCode") + "' \n";
		if (param.get("keyfield").equals("4"))
			query += " 	AND	createrid like '" + param.get("createrid") + "' \n"; 
		if (param.get("specialField") != null && param.get("specialField").length() > 0)
			query += " 	AND	e.ssn in (select distinct ssn from ExpertPoolSpecialField where specialId in ( " + param.get("specialField") + " ))";
		query += " ) RES ) A WHERE rnum BETWEEN (("+param.get("pg")+" * 10) -9) AND ("+param.get("pg")+" * 10) \n";
		query += "order by (case jobClass when 'A' then '1' when 'B' then '2' when 'J' then '3' when 'H' then '4' when 'N' then '5' when 'C' then '6' when 'D' then '7' when 'E' then '8' else '9' end), name \n";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				ExpertPool expertPool = new ExpertPool();
				expertPool.setSsn(rs.getString("ssn"));
				expertPool.setUid(rs.getString("uid"));
				expertPool.setName(rs.getString("name"));
				expertPool.setGender(rs.getString("gender"));
				expertPool.setCompany(rs.getString("company"));
				expertPool.setDeptName(rs.getString("deptName"));
				expertPool.setConsultingMajor(rs.getString("consultingMajor"));
				expertPool.setCreaterId(rs.getString("createrName"));
				expertPool.setRunningDeptCode(rs.getString("dept"));
				expertPool.setJobClass(rs.getString("jobClass"));
				expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
				expertPool.setEmail(rs.getString("email"));
				expertPool.setCompanyTelNo(rs.getString("companyTelNo"));
				expertPool.setDept(rs.getString("dept"));
				expertPool.setPhoto(rs.getString("photo"));
				expertPool.setMobileNo(rs.getString("mobileNo"));
				
				list.add(expertPool);
			}			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {	}
		}	
		return list;
	}
	
	public List<ExpertPool> getProjectExpertPoolList(HashMap<String, String> param)
			throws DataAccessException {
		List<ExpertPool> list = new ArrayList<ExpertPool>();
		String query = "";
		query += " SELECT * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY ssn DESC) rnum, * FROM ( \n";
		query += " 	select e.ssn, left(SecureDB.DBSEC.DECRYPT_AES(e.uid),7) as uid, e.name, e.gender, e.company, s.consultingMajor, createrid, companyPosition, companyPositionName, rank, remark \n";
		query += " 	from expertPool e left outer join ( \n";
		query += " 	select ssn, \n";
		query += " 	REPLACE(RTRIM((SELECT CAST(specialName AS VARCHAR(MAX)) + ', ' FROM ExpertPoolSpecialField WHERE (ssn = e.ssn) FOR XML PATH (''))),' ',' ') AS consultingMajor \n";
		query += " 	from ExpertPoolSpecialField e group by ssn \n";
		query += " 	) s on e.ssn = s.ssn \n";
		query += " 	where 1=1 and e.enable='1' \n";
		query += " 	AND	jobClass = '" + param.get("jobClass") + "' \n";
		if (param.get("jobClass").equals("C"))
			query += " 	AND	e.companyPosition <> '엑스퍼트' \n";
		if (param.get("jobClass").equals("H"))
			query += " 	AND	e.companyPosition IN ('61DT','62ET','63FT') \n";
		if (param.get("name") != null)
			query += " 	AND	name like '" + param.get("name") + "' \n";
		if (param.get("rank") != null)
			query += " 	AND	e.rank = '" + param.get("rank") + "' \n";
		if (param.get("specialField") != null && param.get("specialField").length() > 0)
			query += " 	AND	e.ssn in (select distinct ssn from ExpertPoolSpecialField where specialId in ( " + param.get("specialField") + " ))";
		query += " ) RES ) A WHERE rnum BETWEEN (("+param.get("pg")+" * 15) -14) AND ("+param.get("pg")+" * 15) \n";
		query += "order by name \n";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				ExpertPool expertPool = new ExpertPool();
				expertPool.setSsn(rs.getString("ssn"));
				expertPool.setUid(rs.getString("uid"));
				expertPool.setName(rs.getString("name"));
				expertPool.setGender(rs.getString("gender"));
				expertPool.setCompany(rs.getString("company"));
				expertPool.setCompanyPosition(rs.getString("companyPosition"));
				expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
				expertPool.setRank(rs.getString("rank"));
				expertPool.setRemark(rs.getString("remark"));
				expertPool.setConsultingMajor(rs.getString("consultingMajor"));
				
				list.add(expertPool);
			}			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {	}
		}	
		return list;
		}

	public List<ExpertPool> getExpertsBySearch(String indField, String keyWordCon, String keyWord) throws DataAccessException  {
		List<ExpertPool> memberList = new ArrayList<ExpertPool>();
		String query = ""
			+ " SELECT    ssn, name, company, consultingMajor "
			+ " FROM      ExpertPool "
			+ " WHERE     (1 = 1) ";
		if(!indField.equals(""))
			query += " AND (indField= '" + indField + "') ";
		if(!keyWordCon.equals(""))
			query += " AND (" + keyWordCon + " LIKE '" + keyWord + "%') ";

		//System.out.println(query);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				ExpertPool expertPool = new ExpertPool();
				expertPool.setSsn(rs.getString("ssn"));
				expertPool.setName(rs.getString("name"));
				expertPool.setCompany(rs.getString("company"));
				expertPool.setConsultingMajor(rs.getString("consultingMajor"));
				
				memberList.add(expertPool);
			}			
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {	}
		}	

		return memberList;
	}

	@Override
	public void storeDailyDownloadLimitInfo(String ssn) throws DataAccessException {
		// 다운로드 무제한 허용자가 아니거나 보직자가 아닌 경우에 다운로드 제한 처리 함
		if (!isDailyDownloadUnlimitUser(ssn) && !isCompanyManager(ssn)) {
			if (isUserInDailyDownloadLimitLog(ssn))
				this.dailyDownloadLimitUpdateQuery.updateState(ssn);
			else
				this.dailyDownloadLimitInsertQuery.insert(ssn);
		}
	}
	
	@Override
	public void storeHomeTaxValue(String value1, String value2, String value3, String value4, String value5, String value6) throws DataAccessException {
		
		this.dailyStoreHomeTaxValue.insert(value1, value2, value3, value4, value5, value6);
	}
	
	@Override
	public void workTime(String ssn, String workType, String ip) throws DataAccessException {
		this.workTimeInsertQuery.insert(ssn, workType, ip);
	}
	
	public boolean isDailyDownloadLimitUser(String ssn) throws DataAccessException {
		int checkUser = getJdbcTemplate().queryForInt(" SELECT COUNT(ssn) FROM DailyDownloadLimit "
				+ "	WHERE	isLimit = 'Y' and ssn = '" + ssn + "'	");		
		if (checkUser > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public void updateDailyDownloadLimitState(String ssn, String isLimit) throws DataAccessException {
		String isUnLimit = "N";		
		// 관리자가 특정 사용자의 다운로드 제한 해지를 할 때 다운로드 무제한을 허용 함
		if (isLimit.equals("N"))
			isUnLimit = "Y";
		
		if(isUserInDailyDownloadLimitLog(ssn))
			this.dailyDownloadLimitStateUpdateQuery.updateState(ssn, isLimit, isUnLimit);
		else
			this.dailyDownloadLimitInsertQuery.insert(ssn);
	}
	
	@Override
	public void insertBudgetCheck(String projectCode) throws DataAccessException {
			this.budgetCheckInsertQuery.insert(projectCode);
	}
	
	public void initDownloadUnlimitState() {
		// 다운로드  무제한 허용 여부을 초기화
		this.downloadLimitInitQuery.updateState("N", "N");
	}

	@Override
	public int getLoginAttepmtCnt(String ssn) throws DataAccessException {
		String query = "";
		query += " 	SELECT	isNull(loginAttepmtCnt, 0) as loginAttepmtCnt ";
		query += " 	FROM	ExpertPool ";
		query += " 	WHERE	ssn = '" + ssn + "'";
		return getJdbcTemplate().queryForInt(query);
	}


	@Override
	public int increaseLoginAttepmtCnt(String ssn) throws DataAccessException {
		getJdbcTemplate().execute(" " 
				+ " UPDATE ExpertPool "
				+ "	SET loginAttepmtCnt = isNull(loginAttepmtCnt,0) + 1 "
				+ "  WHERE ssn='" + ssn + "' ");
		return getLoginAttepmtCnt(ssn);
	}


	@Override
	public void accountLocked(String ssn) throws DataAccessException {
		getJdbcTemplate().execute(" "
								+ " UPDATE ExpertPool "
								+ "	SET accountNonLocked = 0 , accountLockedDate = getdate()"
								+ "  WHERE ssn='" + ssn + "' ");
	}


	@Override
	public void accountReset(String ssn) throws DataAccessException {
		getJdbcTemplate().execute(" "
				+ " UPDATE ExpertPool "
				+ "	SET loginAttepmtCnt=0, accountNonLocked = 1, accountLockedDate = null"
				+ "  WHERE ssn='" + ssn + "' ");		
	}

	protected class PasswordResetQuery extends SqlUpdate {
		protected PasswordResetQuery(DataSource ds) {
			super(ds, " UPDATE ExpertPool set password=SecureDB.DBSEC.ENCRYPT_PWD(?), lastModifiedDate=getDate() "
					+ " where ssn = ?           ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insert(String ssn, String password) throws DataAccessException {
			return this.update(new Object[] { password, ssn });
		}
	}
	@Override
	public void passwordReset(String ssn, String password) throws DataAccessException {
		passwordResetQuery.insert(ssn, password);
	}


	@Override
	public String getEncPassword(String password) throws DataAccessException {
		String query = "select SecureDB.DBSEC.ENCRYPT_PWD(?)";
		return (String) getJdbcTemplate().queryForObject(query, new Object[]{password}, String.class);
	}
	
	public DataSource getErpDataSource() {
		return this.erpDataSource;
	}

	public void setErpDataSource(DataSource erpDataSource) {
		this.erpDataSource = erpDataSource;
	}
	
}