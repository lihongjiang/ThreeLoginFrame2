package com.bslee.threelogin.api;

import com.bslee.threelogin.model.AuthToken;
import com.bslee.threelogin.model.AuthUser;

public interface OauthLoginListener {
	public void OauthLoginSuccess(AuthToken token, AuthUser user);

	public void OauthLoginFail();

}
