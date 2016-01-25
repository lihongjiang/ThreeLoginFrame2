package com.bslee.threelogin.model;

public class WeiBoToken extends AuthToken {
	/** session **/
	public String access_token;
	/** 获取新的session **/
	public String refresh_token;
	/** 用户ID **/
	public String uid;
	/** session有效期 **/
	public long expires_time;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getExpires_time() {
		return expires_time;
	}

	public void setExpires_time(long expires_time) {
		this.expires_time = expires_time;
	}

}
