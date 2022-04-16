package kr.co.kmac.pms.system.gadget.data;

public class MyGadget {
	private String userId;
	private String gadgetId;
	private int ordSeq;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGadgetId() {
		return gadgetId;
	}
	public void setGadgetId(String gadgetId) {
		this.gadgetId = gadgetId;
	}
	public int getOrdSeq() {
		return ordSeq;
	}
	public void setOrdSeq(int ordSeq) {
		this.ordSeq = ordSeq;
	}	
}