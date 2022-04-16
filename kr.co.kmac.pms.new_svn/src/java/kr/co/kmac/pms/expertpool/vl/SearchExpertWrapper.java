package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class SearchExpertWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setUserId(rs.getString("userId"));
			expertPool.setPassword(rs.getString("password"));
			expertPool.setSsn(rs.getString("ssn"));
			expertPool.setUid(rs.getString("uid"));
			expertPool.setName(rs.getString("name"));
			expertPool.setEnable(rs.getString("enable"));
			expertPool.setAbsence(rs.getString("absence"));
			expertPool.setGender(rs.getString("gender"));
			expertPool.setNationality(rs.getString("nationality"));
			expertPool.setTelNo(rs.getString("telNo"));
			expertPool.setMobileNo(rs.getString("mobileNo"));
			expertPool.setZipCode(rs.getString("zipCode"));
			expertPool.setAddress1(rs.getString("address1"));
			expertPool.setAddress2(rs.getString("address2"));
			expertPool.setCompany(rs.getString("company"));
			expertPool.setDept(rs.getString("dept"));
			expertPool.setDeptName(rs.getString("deptName"));
			expertPool.setCompanyPosition(rs.getString("companyPosition"));
			expertPool.setCompanyPositionName(rs.getString("companyPositionName"));
			expertPool.setJobClass(rs.getString("jobClass"));
			expertPool.setCompanyZipcode(rs.getString("companyZipcode"));
			expertPool.setCompanyAddress1(rs.getString("companyAddress1"));
			expertPool.setCompanyAddress2(rs.getString("companyAddress2"));
			expertPool.setCompanyTelNo(rs.getString("companyTelNo"));
			expertPool.setCompanyFaxNo(rs.getString("companyFaxNo"));
			expertPool.setEmail(rs.getString("email"));
			expertPool.setIndField(rs.getString("indField"));
			expertPool.setFuncField(rs.getString("funcField"));
			expertPool.setConsultingMajor(rs.getString("consultingMajor"));
			expertPool.setConsultingMinor(rs.getString("consultingMinor"));
			expertPool.setConsultingDetail(rs.getString("consultingDetail"));
			expertPool.setRate(rs.getString("rate"));                                      
			expertPool.setRole(rs.getString("Role"));                                         
			expertPool.setExtRole(rs.getString("extRole"));                                         
			expertPool.setResume(rs.getString("resume"));                                         
			expertPool.setCompanyId(rs.getString("companyId"));
			
			expertPool.setMinAmt(rs.getString("min_Amt"));
			expertPool.setMaxAmt(rs.getString("max_Amt")); 
			expertPool.setMinEdu(rs.getString("Edu_min"));
			expertPool.setMaxEdu(rs.getString("Edu_max"));
			expertPool.setMinMMAmt(rs.getString("MM_MIN_AMT"));
			expertPool.setMaxMMAmt(rs.getString("MM_MAX_AMT"));
			
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolListWrapper fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}