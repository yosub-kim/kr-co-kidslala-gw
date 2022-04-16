/*
 * $Id: PopUpConfig.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 * created by    : Administrator
 * creation-date : 2005. 10. 27
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.popup.data;

/**
 * TODO 클래스 설명
 * @author Administrator
 * @version $Id: PopUpConfig.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 */
public class PopUpConfig {

	private String height;
	private String width;
	private String isEnable;
	private String content;

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return Returns the height.
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * @param height The height to set.
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/**
	 * @return Returns the isEnable.
	 */
	public String getIsEnable() {
		return isEnable;
	}
	/**
	 * @param isEnable The isEnable to set.
	 */
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	/**
	 * @return Returns the width.
	 */
	public String getWidth() {
		return width;
	}
	/**
	 * @param width The width to set.
	 */
	public void setWidth(String width) {
		this.width = width;
	}
}
