/*
 * $Id: ExpertCategoryTreeNode.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 20.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.code.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import kr.co.kmac.pms.system.authority.data.NodeData;

/**
 * 기준정보를 트리로 표현하기 위한 오브젝트
 * 
 * @author jiwoongLee
 * @version $Id: ExpertCategoryTreeNode.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
public class ExpertCategoryTreeNode {

	private List<ExpertCategoryTreeNode> children = new ArrayList<ExpertCategoryTreeNode>();
	private String text;
	private String id;
	private int depth;
	private boolean leaf = false;
	private boolean checked;

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

	public List<ExpertCategoryTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ExpertCategoryTreeNode> nodeLst) {
		this.children = nodeLst;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}