package kr.co.kmac.pms.support.certificate.manager;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.support.certificate.data.CertificateData;

public interface CertificateManager {
	
	public String insert(CertificateData certificateData) throws DataAccessException;

}
