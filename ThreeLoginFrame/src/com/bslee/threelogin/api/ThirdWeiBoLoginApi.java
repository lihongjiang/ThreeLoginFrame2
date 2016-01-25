package com.bslee.threelogin.api;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bslee.threelogin.constans.ThirdAppKey;
import com.bslee.threelogin.db.LoginPlatForm;
import com.bslee.threelogin.model.WeiBoToken;
import com.bslee.threelogin.model.WeiBoUserInfo;
import com.bslee.threelogin.network.HttpUrlUtils;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class ThirdWeiBoLoginApi {

	private static SsoHandler mSinaWeiboSsoHandler;

	public static void login(final Activity activity,
			final OauthListener listener, final OauthLoginListener oauth) {
		mSinaWeiboSsoHandler.authorize(new WeiboAuthListener() {

			@Override
			public void onWeiboException(WeiboException arg0) {
				listener.OauthFail(null);
			}

			@Override
			public void onComplete(Bundle values) {
				final Oauth2AccessToken accessToken = Oauth2AccessToken
						.parseAccessToken(values);

				if (accessToken.isSessionValid()) {
					final WeiBoToken token = new WeiBoToken();
					token.access_token = accessToken.getToken();
					token.uid = accessToken.getUid();
					token.refresh_token = accessToken.getRefreshToken();
					token.expires_time = accessToken.getExpiresTime();
					// 正在为你登录，请稍候
					listener.OauthSuccess(null);
					new Thread(new Runnable() {

						@Override
						public void run() {
							ThirdWeiBoLoginApi.getUserInfo(token, oauth);
						}
					}).start();
					Log.e("Login", "success");

				} else {
					String code = values.getString("code");
					listener.OauthFail(code);
				}
			}

			@Override
			public void onCancel() {
				listener.OauthCancel(null);
			}

		});
	}

	public static SsoHandler getSsoHandler(Activity activity) {
		if (mSinaWeiboSsoHandler == null) {
			AuthInfo auth = new AuthInfo(activity,
					ThirdAppKey.SINAWEIBO_APPKEY,
					ThirdAppKey.SINAWEIBO_REDIRECT_URI,
					ThirdAppKey.SINAWEIBO_SCOPE);
			mSinaWeiboSsoHandler = new SsoHandler(activity, auth);
		}
		return mSinaWeiboSsoHandler;

	}

	public static void getUserInfo(WeiBoToken token, OauthLoginListener oauth) {
		String url = "https://api.weibo.com/2/users/show.json?access_token="
				+ token.access_token + "&uid=" + token.uid;
		Log.v("weibo-user-url", url);
		String data = HttpUrlUtils.httpClientGetJson(url);
		try {
			JSONObject obj = new JSONObject(data);
			if (obj != null) {
				Log.v("weibo-user", obj.toString());

				WeiBoUserInfo info = new WeiBoUserInfo();
				info.gender = obj.getString("gender");
				info.name = obj.getString("name");
				info.profile_image_url = obj.getString("profile_image_url");
				info.idstr = obj.getString("idstr");
				info.id = obj.getLong("id");

				if (info != null && info.idstr != null && info.name != null) {
					token.authtype = LoginPlatForm.WEIBO_PLATPORM;
					info.authtype = LoginPlatForm.WEIBO_PLATPORM;
					oauth.OauthLoginSuccess(token, info);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		oauth.OauthLoginFail();
	}
}
