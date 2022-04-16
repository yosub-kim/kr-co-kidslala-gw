package kr.co.kmac.pms.support.certificate.vl;

import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kmac.pms.support.certificate.data.CertificateData;

import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.util.ObjectWrapper;

public class CertificateSelectWrapper implements ObjectWrapper {
	
	private SQLExceptionTranslator exceptionTranslator = new SQLStateSQLExceptionTranslator();

	@Override
	public Object getWrappedRecord(Object objectToBeWrapped) {
		// TODO Auto-generated method stub
		ResultSet rs = (ResultSet) objectToBeWrapped;
		try {
			CertificateData certificateData = new CertificateData();
			
			certificateData.setNum(rs.getString("num"));
			certificateData.setRegdate(rs.getString("regdate"));
			certificateData.setWriterDept(rs.getString("writerDept"));
			certificateData.setWriter(rs.getString("writer"));
			certificateData.setCtype(rs.getString("ctype"));
			certificateData.setGubun(rs.getString("gubun"));
			certificateData.setUserDept(rs.getString("userDept"));
			certificateData.setUsername(rs.getString("username"));
			certificateData.setStatus(rs.getString("status"));
			certificateData.setManager_approvedate(rs.getString("manager_approvedate"));
			certificateData.setUse_kind(rs.getString("use_kind"));
			certificateData.setUse_place(rs.getString("use_place"));
			certificateData.setPrint_yn(rs.getString("print_yn"));
			certificateData.setCertify_num(rs.getString("certify_num"));
			certificateData.setNewIcon(rs.getString("new"));
			
			return certificateData;
		} catch (SQLException e) {
			throw exceptionTranslator.translate("CertificateSelectWrapper fails", null, e);
		}
	}

	@Override
	public void setValueListInfo(ValueListInfo info) {
		// TODO Auto-generated method stub
		
	}

}
