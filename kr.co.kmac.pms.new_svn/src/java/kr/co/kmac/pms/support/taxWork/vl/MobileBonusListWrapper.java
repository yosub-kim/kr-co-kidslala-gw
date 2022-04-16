package kr.co.kmac.pms.support.taxWork.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.support.taxWork.data.TaxWorkMobileDataForList;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

public class MobileBonusListWrapper implements ObjectWrapper {
	
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	@Override
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			TaxWorkMobileDataForList taxWorkMobileDataForList = new TaxWorkMobileDataForList();
			
			taxWorkMobileDataForList.setPname(rs.getString("pname"));
			taxWorkMobileDataForList.setDeptName(rs.getString("dept_name"));
			taxWorkMobileDataForList.setTeaAmt(rs.getString("tea_amt"));
			taxWorkMobileDataForList.setYear(rs.getString("year"));
			taxWorkMobileDataForList.setMonth(rs.getString("month"));
			taxWorkMobileDataForList.setTotal(rs.getString("total"));
			
			return taxWorkMobileDataForList;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("MobileBonusListWrapper fails", null,  e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub

	}

}
