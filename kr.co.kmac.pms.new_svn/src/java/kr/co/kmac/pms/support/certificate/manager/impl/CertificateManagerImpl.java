package kr.co.kmac.pms.support.certificate.manager.impl;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.support.certificate.dao.CertificateDao;
import kr.co.kmac.pms.support.certificate.data.CertificateData;
import kr.co.kmac.pms.support.certificate.manager.CertificateManager;

public class CertificateManagerImpl implements CertificateManager {

	private CertificateDao certificateDao;

	public CertificateDao getCertificateDao() {
		return certificateDao;
	}

	public void setCertificateDao(CertificateDao certificateDao) {
		this.certificateDao = certificateDao;
	}

	@Override
	public String insert(CertificateData certificateData) throws DataAccessException {
		// TODO Auto-generated method stub
		return getCertificateDao().insert(certificateData);
	}

}
