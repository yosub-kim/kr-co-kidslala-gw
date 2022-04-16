package kr.co.kmac.pms.support.taxWork.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.support.taxWork.data.TaxWorkMobileData;
import kr.co.kmac.pms.support.taxWork.data.TaxWorkMobileDataForList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

public class MobileSalaryListWrapper implements ObjectWrapper {
	
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	@Override
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			TaxWorkMobileDataForList taxWorkMobileDataForList = new TaxWorkMobileDataForList();
			
			taxWorkMobileDataForList.setProv_date(rs.getString("prov_date"));
			taxWorkMobileDataForList.setGubun_str(rs.getString("gubun_str"));
			taxWorkMobileDataForList.setRealPay(rs.getString("realPay"));
			taxWorkMobileDataForList.setIdx(rs.getString("idx"));
			
			return taxWorkMobileDataForList;
		} catch(SQLException e) {
			throw exceptionTranslator.translate("MobileSalaryListWrapper fails", null, e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub
		
	}

	

}
