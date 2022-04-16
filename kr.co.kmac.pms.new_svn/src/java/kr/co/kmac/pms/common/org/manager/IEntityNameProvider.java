/*
 * $Id: IEntityNameProvider.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 7.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager;

/**
 * Entity 이름 제공자
 * @author 최인호
 * @version $Id: IEntityNameProvider.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public interface IEntityNameProvider {
	public String getUserName(String id);
	public String getGroupName(String id);
	public String getRoleName(String id);
}
