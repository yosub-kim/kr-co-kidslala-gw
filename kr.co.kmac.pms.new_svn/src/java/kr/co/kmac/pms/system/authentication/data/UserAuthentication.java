package kr.co.kmac.pms.system.authentication.data;

import java.util.Date;

public class UserAuthentication {

	private int seq;
	private String userSsn;
	private Date authReqDate;
	private String authToken;
	private String authValue;
	private String device;//mobile, desktop
	private boolean isValid;

	public UserAuthentication() {
		super();
	}

	public UserAuthentication(String userSsn, String authToken, boolean isValid) {
		super();
		this.userSsn = userSsn;
		this.authToken = authToken;
		this.isValid = isValid;
	}

	public UserAuthentication(String userSsn, String authToken, String device) {
		super();
		this.userSsn = userSsn;
		this.authToken = authToken;
		this.device = device;
	}

	public UserAuthentication(String userSsn, String authToken, String device, boolean isValid) {
		super();
		this.userSsn = userSsn;
		this.authToken = authToken;
		this.device = device;
		this.isValid = isValid;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getUserSsn() {
		return userSsn;
	}

	public void setUserSsn(String userSsn) {
		this.userSsn = userSsn;
	}

	public Date getAuthReqDate() {
		return authReqDate;
	}

	public void setAuthReqDate(Date authReqDate) {
		this.authReqDate = authReqDate;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getAuthValue() {
		return authValue;
	}

	public void setAuthValue(String authValue) {
		this.authValue = authValue;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
