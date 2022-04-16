package kr.co.kmac.pms.common.menuMy.data;

import java.util.Date;

/**
 * ¡Ò∞‹√£±‚
 */
public class MenuMy {

	private String menuNum;
	private String menuName;
	private String menuPath;
	private String creatorSsn;
	private int orderSeq;
	private Date createTime;

	public MenuMy() {
		super();
	}

	public MenuMy(String menuNum, String creatorSsn) {
		super();
		this.menuNum = menuNum;
		this.creatorSsn = creatorSsn;
	}

	public MenuMy(String menuNum, String creatorSsn, int orderSeq) {
		super();
		this.menuNum = menuNum;
		this.creatorSsn = creatorSsn;
		this.orderSeq = orderSeq;
	}

	public String getMenuNum() {
		return menuNum;
	}

	public void setMenuNum(String menuNum) {
		this.menuNum = menuNum;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getCreatorSsn() {
		return creatorSsn;
	}

	public void setCreatorSsn(String creatorSsn) {
		this.creatorSsn = creatorSsn;
	}

	public int getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
