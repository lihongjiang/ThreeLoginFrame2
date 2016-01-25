package com.bslee.threelogin.api;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.bslee.R;
import com.bslee.threelogin.constans.ThirdAppKey;
import com.bslee.threelogin.db.LoginPlatForm;
import com.bslee.threelogin.model.QQToken;
import com.bslee.threelogin.model.QQUserInfo;
import com.bslee.threelogin.util.UIUtils;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class ThirdQQLoginApi {

	private static Tencent mTencent;

	public static Tencent getTencent(Context context) {
		if (mTencent == null) {
			mTencent = Tencent.createInstance(ThirdAppKey.QQ_APPID, context);
		}
		return mTencent;
	}

	
	private static boolean isQQInstalled() {
		boolean result = false;
		PackageManager packageManager = UIUtils.getContext().getPackageManager();
		if (packageManager != null) {
			try {
				ApplicationInfo info = packageManager.getApplicationInfo("com.tencent.mobileqq",
						PackageManager.GET_META_DATA);
				if (info != null) {
					result = true;
				}
			} catch (PackageManager.NameNotFoundException e) {
				result = false;
			}
		}
		return result;
	}
	
	public static void login(final Activity activity,final OauthListener listener, final OauthLoginListener oauth) {
		
		if (!isQQInstalled()) {
			UIUtils.showToastMessage(R.string.qq_not_install);
			return;
		}
		
		if (mTencent != null) {
			// 本地配置文件注销
			mTencent.logout(activity);
			mTencent.login(activity, ThirdAppKey.QQ_SCOPE, new IUiListener() {

				@Override
				public void onError(UiError arg0) {
					listener.OauthFail(arg0);
				}

				@Override
				public void onComplete(Object arg0) {

					JSONObject obj = (JSONObject) arg0;
					Log.v("qq:", obj.toString());
					try {
						QQToken token = new QQToken();
						token.setAccess_token(obj.getString("access_token"));
						token.setPay_token(obj.getString("pay_token"));
						token.setOpenid(obj.getString("openid"));
						token.setExpires_in(obj.getString("expires_in"));

						if (token != null && token.getAccess_token() != null) {
							listener.OauthSuccess(token);
							mTencent.setOpenId(token.getOpenid());
							mTencent.setAccessToken(token.getAccess_token(),
									token.getExpires_in() + "");
							ThirdQQLoginApi.getUserInfo(activity, oauth, token);
						}
					} catch (Exception e) {
						listener.OauthFail(null);
					}
				}

				@Override
				public void onCancel() {
					listener.OauthCancel(null);
				}
			});
		}
	}

	public static void getUserInfo(final Activity activity,
			final OauthLoginListener oauth, final QQToken token) {
		UserInfo info = new UserInfo(activity, mTencent.getQQToken());
		info.getUserInfo(new IUiListener() {

			@Override
			public void onError(UiError arg0) {
				oauth.OauthLoginFail();
			}

			@Override
			public void onComplete(Object arg0) {
				try {
					JSONObject obj = (JSONObject) arg0;
					Log.v("qq-user", obj.toString());
					QQUserInfo info = new QQUserInfo();
					info.gender = obj.getString("gender");
					info.nickname = obj.getString("nickname");
					info.figureurl_qq_1 = obj.getString("figureurl_qq_1");
					info.figureurl_qq_2 = obj.getString("figureurl_qq_2");
					if (info != null && info.nickname != null) {
						token.authtype = LoginPlatForm.QQZONE_PLATPORM;
						info.authtype = LoginPlatForm.QQZONE_PLATPORM;
						oauth.OauthLoginSuccess(token, info);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				oauth.OauthLoginFail();
			}

			@Override
			public void onCancel() {
				oauth.OauthLoginFail();
			}
		});
	}
}
