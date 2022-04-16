package kr.co.kmac.pms.system.gadget.data;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gadget {
	private String gadgetId;
	private String gadgetRole;
	private String gadgetName;
	private String sqlText;
	private String tableHeader;
	private String linkUrl;
	private int ordSeq;
	private String fixedYN;
	private String gadgetType;
	private String useYN;
	private String specialStyle;
	private String info1;
	private String info2;

	public String getSpecialStyle() {
		return specialStyle;
	}

	public void setSpecialStyle(String specialStyle) {
		this.specialStyle = specialStyle;
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	private List<Content> contentList;

	public String getGadgetId() {
		return gadgetId;
	}

	public void setGadgetId(String gadgetId) {
		this.gadgetId = gadgetId;
	}

	public String getGadgetName() {
		return gadgetName;
	}

	public void setGadgetName(String gadgetName) {
		this.gadgetName = gadgetName;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public int getOrdSeq() {
		return ordSeq;
	}

	public void setOrdSeq(int ordSeq) {
		this.ordSeq = ordSeq;
	}

	public String getFixedYN() {
		return fixedYN;
	}

	public void setFixedYN(String fixedYN) {
		this.fixedYN = fixedYN;
	}

	public String getUseYN() {
		return useYN;
	}

	public void setUseYN(String useYN) {
		this.useYN = useYN;
	}

	public String getGadgetType() {
		return gadgetType;
	}

	public void setGadgetType(String gadgetType) {
		this.gadgetType = gadgetType;
	}

	public List<Content> getContentList() {
		return contentList;
	}

	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}

	public String getGadgetRole() {
		return gadgetRole;
	}

	public void setGadgetRole(String gadgetRole) {
		this.gadgetRole = gadgetRole;
	}

	public String getTableHeader() {
		return tableHeader;
	}

	public void setTableHeader(String tableHeader) {
		this.tableHeader = tableHeader;
	}

	public int getContentListSize() {
		if (this.getContentList() != null)
			return this.getContentList().size();
		else
			return 0;
	}

	public int getColumnCount() {
		if (this.getTableHeader() != null && this.getTableHeader().length() > 0) {
			Pattern p = Pattern.compile("<th ");
			Matcher m = p.matcher(this.getTableHeader());
			int count = 0;
			for (int i = 0; m.find(i); i = m.end()) {
				count++;
			}
			return count;
		} else {
			return 0;
		}
	}

}
