package kr.co.kmac.pms.emergencyConnection.data;

import java.util.List;

public class EmergencyGroup {
	private String groupName;
	private List<EmergencyConnectionData> groupUserList;
	private int userCnt;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<EmergencyConnectionData> getGroupUserList() {
		return groupUserList;
	}
	public void setGroupUserList(List<EmergencyConnectionData> groupUserList) {
		this.groupUserList = groupUserList;
	}
	public int getUserCnt() {
		return userCnt;
	}
	public void setUserCnt(int userCnt) {
		this.userCnt = userCnt;
	}	
	
	
}
