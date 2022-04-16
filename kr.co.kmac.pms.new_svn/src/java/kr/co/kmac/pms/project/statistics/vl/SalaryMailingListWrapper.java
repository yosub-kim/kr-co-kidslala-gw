package kr.co.kmac.pms.project.statistics.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.statistics.data.SalaryMailingList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class SalaryMailingListWrapper implements ObjectWrapper {

	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();
	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#getWrappedRecord(java.lang.Object)
	 */
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
				SalaryMailingList salaryMailingList = new SalaryMailingList();
				salaryMailingList.setType(rs.getString("type")); 
				salaryMailingList.setYear(rs.getString("year"));
				salaryMailingList.setMonth(rs.getString("month"));
				salaryMailingList.setProjectCode(rs.getString("projectCode")); 
				salaryMailingList.setProjectName(rs.getString("projectName"));
				salaryMailingList.setJobclass(rs.getString("jobClass"));
				salaryMailingList.setEmail(rs.getString("email"));
				salaryMailingList.setProjectCount(rs.getString("projectCount")); 
				salaryMailingList.setJobClassDesc(rs.getString("jobClassDesc"));
				salaryMailingList.setName(rs.getString("name"));
				salaryMailingList.setSsn(rs.getString("ssn"));
				salaryMailingList.setUid(rs.getString("uid"));
				salaryMailingList.setCompany(rs.getString("company"));
				salaryMailingList.setRealTimeSalaryEachProject(rs.getLong("realTimeSalaryEachProject")); 
				salaryMailingList.setTotalRealTimeSalary(rs.getLong("totalRealTimeSalary"));
				salaryMailingList.setEmailSeq(rs.getString("emailSeq"));
				salaryMailingList.setEmailSendDate(rs.getDate("emailSendDate"));
				salaryMailingList.setEmailSendYN(rs.getString("emailSendYN"));
				
			return salaryMailingList;
		}catch (SQLException e) {
			throw exceptionTranslator.translate("SalaryMailingListWrapper fails", null, e);
		}
		
	}

	/* (non-Javadoc)
	 * @see net.mlw.vlh.adapter.util.ObjectWrapper#setValueListInfo(net.mlw.vlh.ValueListInfo)
	 */
	public void setValueListInfo(ValueListInfo valueListInfo) {
		// TODO Auto-generated method stub

	}
}
