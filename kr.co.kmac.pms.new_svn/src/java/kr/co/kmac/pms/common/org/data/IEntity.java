/*
 * $Id: IEntity.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.io.Serializable;

/**
 * 엔티티 인터페이스. 엔티티는 아이디, 이름, 활성 상태, 설명 속성을 갖는 객체를 말한다. Security Framework 모델의 기본 인터페이스.
 * 
 * @author 최인호
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
