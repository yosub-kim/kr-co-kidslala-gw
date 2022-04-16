/*
 * $Id: Role.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 6.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;


import org.acegisecurity.GrantedAuthority;

/**
 * 역할 모델
 * @author 최인호
 * @version $Id: Role.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public class Role extends AbstractEntity implements IRole, GrantedAuthority {
	/**
	 * generated serial version id
	 */
	private static final long serialVersionUID = -6067800704233702882L;
	private String orderSeq;
	
	public Role(String id) {
		super(id);
	}
	public Role(String id, String name) {
		super(id, name);
	}
	/*
	 * (non-Javadoc)
	 * @see net.sf.acegisecurity.GrantedAuthority#getAuthority()
	 */
	public String getAuthority() {
		return getId();
	}
	/**
	 * @return the orderSeq
	 */
	public String getOrderSeq() {
		return orderSeq;
	}
	/**
	 * @param orderSeq the orderSeq to set
	 */
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	
}
