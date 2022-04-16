/*
 * $Id: NodeData.java,v 1.3 2016/07/05 07:04:28 cvs Exp $
 * created by    : ≥≤πŒ»£
 * creation-date : 2009. 7. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authority.data;

import java.util.List;

public class NodeData {
	private String text;
	private String id;
	private String menuId;
	private int depth;
	private String url;
	private boolean leaf;
	private List<NodeData> children;
	private boolean checked;
	private boolean menuMy;
	private int menuType;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<NodeData> getChildren() {
		return children;
	}

	public void setChildren(List<NodeData> nodeLst) {
		this.children = nodeLst;
	}

	public int getMenuType() {
		return menuType;
	}

	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}

	public boolean isMenuMy() {
		return menuMy;
	}

	public void setMenuMy(boolean menuMy) {
		this.menuMy = menuMy;
	}

}
