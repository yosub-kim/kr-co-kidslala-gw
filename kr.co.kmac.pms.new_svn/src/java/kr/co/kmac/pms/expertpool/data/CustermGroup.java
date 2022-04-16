/*
 * $Id: CustermGroup.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 6.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.data;

public class CustermGroup {

	private String id;
	private String name;
	private String groupType;
	private String enabled;
	private String memberRule;
	private String description;
	private String parentId;
	private String path;
	private String depth;
	
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemberRule() {
		return memberRule;
	}
	public void setMemberRule(String memberRule) {
		this.memberRule = memberRule;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
