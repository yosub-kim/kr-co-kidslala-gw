package kr.co.kmac.pms.customer.manager.impl;

import kr.co.kmac.pms.board.exception.BoardException;
import kr.co.kmac.pms.customer.dao.CustomerDao;
import kr.co.kmac.pms.customer.data.CustomerForm;
import kr.co.kmac.pms.customer.manager.CustomerManager;

public class CustomerManagerImpl implements CustomerManager {

	private CustomerDao customerDao;

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public String insertCustomerInfo(CustomerForm customerForm) throws BoardException {
		return getCustomerDao().insert(customerForm);
	}
	
	@Override
	public String selectCustomerInfo(String ssn) throws BoardException {
		return getCustomerDao().select(ssn);
	}
	
	@Override
	public String selectManagerChk(String ssn) throws BoardException {
		return getCustomerDao().select2(ssn);
	}

}
