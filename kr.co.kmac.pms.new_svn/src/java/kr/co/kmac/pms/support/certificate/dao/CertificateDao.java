package kr.co.kmac.pms.support.certificate.dao;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.support.certificate.data.CertificateData;

public interface CertificateDao {
	
	public String insert(CertificateData certificateData) throws DataAccessException;

}
