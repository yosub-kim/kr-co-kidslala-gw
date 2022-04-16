package kr.co.kmac.pms.support.certificate.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.support.certificate.dao.CertificateDao;
import kr.co.kmac.pms.support.certificate.data.CertificateData;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.web.bind.ServletRequestUtils;

public class CertificateDaoImpl extends JdbcDaoSupport implements CertificateDao {
	
	private CertificateDataInsertQuery certificateDataInsertQuery;
	
	protected void initDao() throws Exception {
		this.certificateDataInsertQuery = new CertificateDataInsertQuery(getDataSource());
	}

	@Override
	public String insert(CertificateData certificateData) throws DataAccessException {
		// TODO Auto-generated method stub
		String certify_num = String.valueOf(getJdbcTemplate().queryForInt("SELECT ISNULL(MAX(CAST(RIGHT(certify_num, 4) AS int)), 0) + 1 FROM certificate WHERE LEFT(certify_num, 4) = year(getdate())"));
		String year		   = DateTime.getYear();
		
		if(certify_num.length() == 1) {
			certify_num = year + "-000" + certify_num;
		} else if(certify_num.length() == 2) {
			certify_num = year + "-00" + certify_num;
		} else if(certify_num.length() == 3) {
			certify_num = year + "-0" + certify_num;
		} else if(certify_num.length() == 1) {
			certify_num = year + "-" + certify_num;
		}
		
		certificateData.setCertify_num(certify_num);
		this.certificateDataInsertQuery.insert(certificateData);
		return certify_num;
	}
	
	protected class CertificateDataInsertQuery extends SqlUpdate {
		
		protected CertificateDataInsertQuery(DataSource ds) {
			super (
					ds,
						"INSERT INTO certificate(certify_num, writerid, userid, writerdept, userdept, ctype, gubun, "
					+	" use_kind, use_place, regdate, status, start_date, managerID, approvedate, manager_approvedate)"
					+ 	" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, getdate(), ?, ?, 'eun', ?, ?)"
				);
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
		
		protected int insert(CertificateData form) throws DataAccessException {
			return this.update(new Object[]{form.getCertify_num(), form.getWriterId(), form.getUserId(), form.getWriterDept(), form.getUserDept(),
					form.getCtype(), form.getGubun(), form.getUse_kind(), form.getUse_place(), form.getStatus(), form.getStart_date(), form.getApprovedate(), form.getManager_approvedate()});
		}
	}

}
