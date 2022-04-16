package kr.co.kmac.pms.common.data;

public class TabData {
	private String tabName;
	private String tabURL;
	
	public TabData(String tabName, String tabURL) {
		this.tabName = tabName;
		this.tabURL  = tabURL;
	}
	
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getTabURL() {
		return tabURL;
	}
	public void setTabURL(String tabURL) {
		this.tabURL = tabURL;
	}
	
}