package kr.co.kmac.pms.customer.manager;

import kr.co.kmac.pms.board.exception.BoardException;
import kr.co.kmac.pms.customer.data.CustomerForm;

public interface CustomerManager {

	public String insertCustomerInfo(CustomerForm customerForm) throws BoardException;
	
	public String selectCustomerInfo(String ssn) throws BoardException;
	
	public String selectManagerChk(String ssn) throws BoardException;

}
