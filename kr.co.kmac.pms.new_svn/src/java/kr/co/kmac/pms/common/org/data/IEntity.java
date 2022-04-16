/*
 * $Id: IEntity.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.io.Serializable;

/**
 * ��ƼƼ �������̽�. ��ƼƼ�� ���̵�, �̸�, Ȱ�� ����, ���� �Ӽ��� ���� ��ü�� ���Ѵ�. Security Framework ���� �⺻ �������̽�.
 * 
 * @author ����ȣ
 * @version $Id: IEntity.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IEntity extends Serializable, Comparable {
	public String getId();
	public String getName();
	public void setName(String name);
	public boolean isEnabled();
	public void setEnabled(boolean enabled);
	public String getDescription();
	public void setDescription(String description);
	public void setOrderSeq(String orderSeq);
	public String getOrderSeq();
}
