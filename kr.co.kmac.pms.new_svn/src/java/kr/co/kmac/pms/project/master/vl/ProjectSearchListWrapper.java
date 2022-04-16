package kr.co.kmac.pms.project.master.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.master.data.ProjectSearchEntity;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ProjectSearchListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	@Override
	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ProjectSearchEntity projectSearchEntity = new ProjectSearchEntity();
			projectSearchEntity.setProjectCode(rs.getString("projectCode"));
			projectSearchEntity.setProjectName(rs.getString("projectName"));
			projectSearchEntity.setProjectTypeCode(rs.getString("projectTypeCode"));
			projectSearchEntity.setBusinessTypeCode(rs.getString("businessTypeCode"));
			projectSearchEntity.setProcessTypeCode(rs.getString("processTypeCode"));
			projectSearchEntity.setCioTypeCode(rs.getString("cioTypeCode"));
			projectSearchEntity.setRunningDivCode(rs.getString("runningDivCode"));
			projectSearchEntity.setRunningDeptCode(rs.getString("runningDeptCode"));
			projectSearchEntity.setIndustryTypeCode(rs.getString("industryTypeCode"));
			projectSearchEntity.setRealStartDate(rs.getString("realStartDate"));
			projectSearchEntity.setRealEndDate(rs.getString("realEndDate"));
			projectSearchEntity.setProjectState(rs.getString("projectState"));
			projectSearchEntity.setEndGubun(rs.getString("endGubun"));
			projectSearchEntity.setCostOver(rs.getString("costOver"));
			projectSearchEntity.setPayCostOver(rs.getString("payCostOver"));
			projectSearchEntity.setEtcCostOver(rs.getString("etcCostOver"));
			projectSearchEntity.setPreStartDate(rs.getString("preStartDate"));
			projectSearchEntity.setPreEndDate(rs.getString("preEndDate"));
			projectSearchEntity.setBusinessTypeCodeName(rs.getString("businessTypeCodeName"));
			projectSearchEntity.setRunningDivCodeName(rs.getString("runningDivCodeName"));
			projectSearchEntity.setRunningDeptCodeName(rs.getString("runningDeptCodeName"));
			projectSearchEntity.setPmSsn(rs.getString("pmSsn"));
			projectSearchEntity.setPlSsn(rs.getString("plSsn"));
			projectSearchEntity.setBoardArticleCount(rs.getString("boardArticleCount"));
			projectSearchEntity.setDelayState(rs.getString("delayState"));
			projectSearchEntity.setAdminOpen(rs.getString("adminOpen"));	
			projectSearchEntity.setPmname(rs.getString("pmname"));
			projectSearchEntity.setBoardArticleCountQM(rs.getString("boardArticleCountQM"));
			projectSearchEntity.setAttach(rs.getString("attach"));
			projectSearchEntity.setParentProjectCode(rs.getString("parentProjectCode"));
			projectSearchEntity.setCustomerName(rs.getString("customerName"));
			projectSearchEntity.setBusinessFunctionType(rs.getString("businessFunctionType"));

			return projectSearchEntity;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ErpProjectListWrapper fails", null, e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
