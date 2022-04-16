/*
 * $Id: User.java,v 1.2 2018/03/21 13:29:21 cvs Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 6.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;

/**
 * 사용자 객체
 * @author 최인호
 * @version $Id: User.java,v 1.2 2018/03/21 13:29:21 cvs Exp $
 */
public class User extends AbstractPrincipal implements IUser, UserDetails {
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -7546434241527426755L;

	private String password = "";
	private String email;
	private String position;
	private String ssn;
	private String loginId;
	private String orderSeq;
	private boolean accountNonLocked;
	
	
	private List groupList;
	private GrantedAuthority[] authorities;

	public User(String id) {
		super(id);
	}
	public User(String id, String name) {
		super(id, name);
	}
	public User(String id, String name, String password) {
		this(id, name);
		setPassword(password);
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return this.email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return this.password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the position.
	 */
	public String getPosition() {
		return this.position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return Returns the ssn.
	 */
	public String getSsn() {
		return this.ssn;
	}
	/**
	 * @param ssn The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @return Returns the groupList.
	 */
	public List getGroupList() {
		return this.groupList;
	}
	
	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IUser#getGroupList(int)
	 */
	public List getGroupList(int groupType) {
		List filtered = new ArrayList();
		if (this.groupList != null && this.groupList.size() > 0) {
			for (Iterator i = this.groupList.iterator(); i.hasNext();) {
				IGroup group = (IGroup) i.next();
				if (group.isSubTypeOf(groupType)) {
					filtered.add(group);
				}
			}
		}
		return filtered;
	}
	/**
	 * @param groupList The groupList to set.
	 */
	public void setGroupList(List groupList) {
		if (this.groupList != null) {
			this.groupList.clear();
		}
		this.groupList = groupList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IUser#belongsToGroup(com.miracom.bpms.security.core.IGroup)
	 */
	public boolean belongsToGroup(IGroup group) {
		if (this.groupList != null) {
			return this.groupList.contains(group);
		}
		return false;
	}
	/**
	 * 그룹 추가. 클라이언트가 직접 이 메소드를 호출하면 안된다.
	 * @param group
	 */
	public void addGroup(IGroup group) {
		if (this.groupList == null) {
			this.groupList = new ArrayList();
		}
		if (!this.groupList.contains(group)) {
			this.groupList.add(group);
		}
	}
	/**
	 * 그룹 삭제. 클라이언트가 직접 이 메소드를 호출하면 안된다.
	 * @param group
	 */
	public void removeGroup(IGroup group) {
		if (this.groupList != null) {
			this.groupList.remove(group);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.AbstractPrincipal#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append(",password=forbidden");
		sb.append(",email=" + this.email);
		sb.append(",position=" + this.position);
		sb.append(",ssn=" + this.ssn);
		if (this.groupList != null) {
			for (Iterator i = this.groupList.iterator(); i.hasNext();) {
				sb.append(",group=(" + ((IGroup) i.next()).getId() + ")");
			}
		}
		return sb.toString();
	}

	// UserDetails

	/*
	 * (non-Javadoc)
	 * @see net.sf.acegisecurity.UserDetails#getAuthorities()
	 */
	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities The authorities to set.
	 */
	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.acegisecurity.UserDetails#getUsername()
	 */
	public String getUsername() {
		return getId();
	}
	/*
	 * (non-Javadoc)
	 * @see net.sf.acegisecurity.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see net.sf.acegisecurity.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
	
	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	/*
	 * (non-Javadoc)
	 * @see net.sf.acegisecurity.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return true;
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
	
	public String getLoginId() {
		return loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	
}
