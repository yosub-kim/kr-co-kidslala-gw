package kr.co.kmac.pms.expertpool.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.expertpool.data.ExpertPool;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;


public class ExpertPoolWorkPeriodListWrapper_time implements ObjectWrapper {
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	public Object getWrappedRecord(Object objectToBeWrapped) {
		ResultSet rs = (ResultSet) objectToBeWrapped;
		ExpertPool expertPool = new ExpertPool();
		try {
			expertPool.setUserId(rs.getString("userId"));
			expertPool.setGroupName(rs.getString("groupName"));
			expertPool.setUserName(rs.getString("userName"));
			expertPool.setPosName(rs.getString("posName"));
			expertPool.setGroupSeq(rs.getString("groupSeq"));
			expertPool.setPosName(rs.getString("posName"));
			expertPool.setLabelName(rs.getString("labelName"));
			expertPool.setGroupcount(rs.getString("groupcount"));
			expertPool.setM01(rs.getString("M01"));
			expertPool.setM02(rs.getString("M02"));
			expertPool.setM03(rs.getString("M03"));
			expertPool.setM04(rs.getString("M04"));
			expertPool.setM05(rs.getString("M05"));
			expertPool.setM06(rs.getString("M06"));
			expertPool.setM07(rs.getString("M07"));
			expertPool.setM08(rs.getString("M08"));
			expertPool.setM09(rs.getString("M09"));
			expertPool.setM10(rs.getString("M10"));
			expertPool.setM11(rs.getString("M11"));
			expertPool.setM12(rs.getString("M12"));
			expertPool.setM13(rs.getString("M13"));
			expertPool.setM14(rs.getString("M14"));
			expertPool.setM15(rs.getString("M15"));
			expertPool.setM16(rs.getString("M16"));
			expertPool.setM17(rs.getString("M17"));
			expertPool.setM18(rs.getString("M18"));
			expertPool.setM19(rs.getString("M19"));
			expertPool.setM20(rs.getString("M20"));
			expertPool.setM21(rs.getString("M21"));
			expertPool.setM22(rs.getString("M22"));
			expertPool.setM23(rs.getString("M23"));
			expertPool.setM24(rs.getString("M24"));
			expertPool.setM25(rs.getString("M25"));
			expertPool.setM26(rs.getString("M26"));
			expertPool.setM27(rs.getString("M27"));
			expertPool.setM28(rs.getString("M28"));
			expertPool.setM29(rs.getString("M29"));
			expertPool.setM30(rs.getString("M30"));
			expertPool.setM31(rs.getString("M31"));
		} catch (SQLException e) {
			throw exceptionTranslator.translate("ExpertPoolWorkPeriodListWrapper_time fails", null, e);
		}
		return expertPool;
	}

	public void setValueListInfo(ValueListInfo arg0) {
	}
}