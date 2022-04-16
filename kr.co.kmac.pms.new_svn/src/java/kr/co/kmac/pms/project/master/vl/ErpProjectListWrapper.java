package kr.co.kmac.pms.project.master.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.project.master.data.ErpProject;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class ErpProjectListWrapper implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	@Override
	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			ErpProject erpProject = new ErpProject();
			erpProject.setProjectType(rs.getString("PJTTYPE"));
			erpProject.setProjectTypeName(rs.getString("PJTTYPENAME"));
			erpProject.setBuCode(rs.getString("BUCODE"));
			erpProject.setBuCodeName(rs.getString("BUCODENAME"));
			erpProject.setPuCode(rs.getString("PUCODE"));
			erpProject.setPuCodeName(rs.getString("PUCODENAME"));
			erpProject.setIndsType(rs.getString("INDSTYPE"));
			erpProject.setIndsTypeName(rs.getString("INDSTYPENAME"));
			erpProject.setProjectCode(rs.getString("PROJID"));
			erpProject.setProjectName(rs.getString("PJTNAME"));
			erpProject.setPromType(rs.getString("PROMTYPE"));
			erpProject.setPromTypeName(rs.getString("PROMTYPENAME"));
			erpProject.setCustom(rs.getString("CUSTOM"));
			erpProject.setCustomName(rs.getString("CUSTOMNAME"));
			erpProject.setWorkFromDt(rs.getString("WORKFROMDT"));
			erpProject.setWorkToDt(rs.getString("WORKTODT"));
			erpProject.setAcptType(rs.getString("ACPTTYPE"));
			erpProject.setAcptDate(rs.getString("ACPTDATE"));
			erpProject.setAcptEmpNo(rs.getString("ACPTEMPNO"));
			erpProject.setCoGRP(rs.getString("CO_GRP"));
			erpProject.setInputEmpNo(rs.getString("INPUTEMP"));
			erpProject.setInputDate(rs.getDate("INPUTDATE"));
			erpProject.setUpdEmpNo(rs.getString("UPDEMPNO"));
			erpProject.setUpdDate(rs.getDate("UPDDATE"));

			return erpProject;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ErpProjectListWrapper fails", null, e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
