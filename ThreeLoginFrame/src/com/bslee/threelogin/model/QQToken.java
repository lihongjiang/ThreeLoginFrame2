package com.bslee.threelogin.model;

public class QQToken extends AuthToken {

	private Integer ret;

	private String pay_token;

	private String pf;

	private Integer query_authority_cost;

	private Integer authority_cost;

	private String openid;// 唯一ID，与QQ号一一对应

	private String expires_in;//session有效期

	private String pfkey;

	private String msg;

	private String access_token;//session会话

	private Integer login_cost;

	public Integer getRet() {
		return ret;
	}

	public void setRet(Integer ret) {
		this.ret = ret;
	}

	public String getPay_token() {
		return pay_token;
	}

	public void setPay_token(String pay_token) {
		this.pay_token = pay_token;
	}

	public String getPf() {
		return pf;
	}

	public void setPf(String pf) {
		this.pf = pf;
	}

	public Integer getQuery_authority_cost() {
		return query_authority_cost;
	}

	public void setQuery_authority_cost(Integer query_authority_cost) {
		this.query_authority_cost = query_authority_cost;
	}

	public Integer getAuthority_cost() {
		return authority_cost;
	}

	public void setAuthority_cost(Integer authority_cost) {
		this.authority_cost = authority_cost;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getPfkey() {
		return pfkey;
	}

	public void setPfkey(String pfkey) {
		this.pfkey = pfkey;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getLogin_cost() {
		return login_cost;
	}

	public void setLogin_cost(Integer login_cost) {
		this.login_cost = login_cost;
	}

}
