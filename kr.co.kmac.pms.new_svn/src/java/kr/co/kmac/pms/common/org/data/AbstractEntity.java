/*
 * $Id: AbstractEntity.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 11.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import kr.co.kmac.pms.common.util.StringUtil;

/**
 * 조직/권한 Entity 상위 클래스. Entity는 아이디와 이름을 갖고, 활성/비활성 상태를 갖는다.
 * 
 * @author 최인호
 * @version $Id: AbstractEntity.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public abstract class AbstractEntity implements IEntity {
	private static final long serialVersionUID = -6175854506982902291L;
	private String id;
	private boolean enabled = true;
	private String name;
	private String description;

	public AbstractEntity(String id) {
		setId(id);
	}

	public AbstractEntity(String id, String name) {
		setId(id);
		setName(name);
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		if (id == null) {
			throw new IllegalArgumentException("id should not be null");
		}
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}

	public String getHtmlName() {
		return StringUtil.replace(this.name, " ", "&nbsp;");
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the enabled.
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (this.id != null) {
			return this.id.equals(((AbstractEntity) obj).getId());
		} else {
			return ((AbstractEntity) obj).getId() == null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.id == null) {
			return super.hashCode();
		} else {
			return this.getClass().hashCode() + this.id.hashCode() * 13;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id=" + this.id);
		sb.append(",enabled=" + this.enabled);
		sb.append(",name=" + this.name);
		sb.append(",description=" + this.description);
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		String otherId = ((AbstractEntity) o).getId();
		if (getId() != null) {
			return otherId != null ? getId().compareTo(otherId) : 1;
		} else {
			return otherId != null ? -1 : 0;
		}
	}
}
