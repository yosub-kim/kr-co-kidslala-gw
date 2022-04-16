/*
 * $Id: OrgTreeNode.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 9.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

/**
 * 조직 트리 노트
 * 
 * @author jiwoong
 * @version $Id: OrgTreeNode.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public class OrgTreeNode {

	/**
	 * 노드의 아이디
	 */
	private String id;
	/**
	 * 노드의 이름(조직명 or 유저이름)
	 */
	private String name;
	/**
	 * 노드의 확장명 - '홍길동 -> 팀장 홍길동' 으로 가져온다 .
	 */
	private String extendedName;
	/**
	 * 조직트리 상에서 해당 노드가 그룹인지 유저인지 (GROUP || USER)
	 */
	private String objType;

	private String deptName;
	private String deptId;
	private String position;

	/**
	 * 
	 */
	public OrgTreeNode() {
		super();
	}

	/**
	 * @param extendedName
	 * @param id
	 * @param name
	 * @param objType
	 */
	public OrgTreeNode(String id, String name, String extendedName, String objType) {
		super();
		this.id = id;
		this.name = name;
		this.extendedName = extendedName;
		this.objType = objType;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the extendedName.
	 */
	public String getExtendedName() {
		return extendedName;
	}

	/**
	 * @param extendedName The extendedName to set.
	 */
	public void setExtendedName(String extendedName) {
		this.extendedName = extendedName;
	}

	/**
	 * @return Returns the objType.
	 */
	public String getObjType() {
		return objType;
	}

	/**
	 * @param objType The objType to set.
	 */
	public void setObjType(String objType) {
		this.objType = objType;
	}

	/**
	 * @return Returns the deptName.
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName The deptName to set.
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return Returns the deptId.
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId The deptId to set.
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return Returns the position.
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	public static OrgTreeNode valueOf(Group group) {
		OrgTreeNode treeNode = new OrgTreeNode();
		treeNode.setId(group.getId());
		treeNode.setName(group.getName());
		treeNode.setExtendedName(group.getName());
		treeNode.setObjType("GROUP");
		return treeNode;
	}

	public static OrgTreeNode valueOf(User user) {
		OrgTreeNode treeNode = new OrgTreeNode();
		treeNode.setId(user.getId());
		treeNode.setName(user.getName());
		treeNode.setObjType("USER");
		treeNode.setExtendedName(user.getPosition() + " - " + user.getName());
		treeNode.setPosition(user.getPosition());

		for (Object group : user.getGroupList()) {
			treeNode.setDeptId(((Group) group).getId());
			treeNode.setDeptName(((Group) group).getName());
		}

		return treeNode;

	}
}
