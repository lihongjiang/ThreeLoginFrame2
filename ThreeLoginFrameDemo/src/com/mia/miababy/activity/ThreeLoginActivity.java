package com.mia.miababy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bslee.threelogin.api.OauthListener;
import com.bslee.threelogin.api.OauthLoginListener;
import com.bslee.threelogin.api.ThirdQQLoginApi;
import com.bslee.threelogin.api.ThirdWeiBoLoginApi;
import com.bslee.threelogin.api.ThirdWeiXinLoginApi;
import com.bslee.threelogin.db.LoginPlatForm;
import com.bslee.threelogin.model.AuthToken;
import com.bslee.threelogin.model.AuthUser;
import com.bslee.threelogin.model.QQToken;
import com.bslee.threelogin.model.QQUserInfo;
import com.bslee.threelogin.model.WeiBoToken;
import com.bslee.threelogin.model.WeiBoUserInfo;
import com.bslee.threelogin.model.WeiXinUserInfo;
import com.mia.miababy.R;

public class ThreeLoginActivity extends BaseActivity implements
		OnClickListener {
	private MyReceiver mReceiver;
	private TextView mProressbar;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mProressbar = (TextView) findViewById(R.id.dialog_message);
		if (mReceiver != null) {
			LocalBroadcastManager.getInstance(this).unregisterReceiver(
					mReceiver);
			mReceiver = null;
		}
		mReceiver = new MyReceiver();
		LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
				new IntentFilter("ACTION_WX_LOGIN_SUCEESS"));

		ViewInit();

	}

	private void ViewInit() {
		findViewById(R.id.qq).setOnClickListener(this);
		findViewById(R.id.weibo).setOnClickListener(this);
		findViewById(R.id.weixin).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.qq:
			QQInit();
			mProressbar.setVisibility(View.GONE);
			break;
		case R.id.weibo:
			WeiBoInit();
			mProressbar.setVisibility(View.GONE);
			break;
		case R.id.weixin:
			WeiXinInit();
			mProressbar.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	private void QQInit() {
		ThirdQQLoginApi.getTencent(getApplicationContext());
		ThirdQQLoginApi.login(this, oauth, oauthlogin);
	}

	public void WeiBoInit() {
		ThirdWeiBoLoginApi.getSsoHandler(this);
		ThirdWeiBoLoginApi.login(this, oauth, oauthlogin);
	}

	private void WeiXinInit() {
		ThirdWeiXinLoginApi.getWXAPI(getApplicationContext());
		ThirdWeiXinLoginApi.login(getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
		mReceiver = null;
		super.onDestroy();
	}

	/**
	 * QQ，WeiBo，WeiXin登录成功回调
	 */
	private OauthLoginListener oauthlogin = new OauthLoginListener() {

		@Override
		public void OauthLoginSuccess(final AuthToken token, final AuthUser user) {

			handler.post(new Runnable() {

				@Override
				public void run() {

					String uuid = "";//三方用户唯一ID
					String name = "";
					int type = token.authtype;
					switch (type) {
					case LoginPlatForm.QQZONE_PLATPORM:
						uuid = ((QQToken) token).getOpenid();
						name = ((QQUserInfo) user).getNickname();
						break;

					case LoginPlatForm.WECHAT_PLATPORM:
						uuid = ((WeiXinUserInfo) user).getUnionid();
						name = ((WeiXinUserInfo) user).getNickname();
						break;
					case LoginPlatForm.WEIBO_PLATPORM:
						uuid = ((WeiBoToken) token).getUid();
						name = ((WeiBoUserInfo) user).getName();

						break;
					}
					mProressbar.setText("状态:登录成功\n" +"ID:" + uuid + "\n昵称:" + name);
				}
			});
		}

		@Override
		public void OauthLoginFail() {
			handler.post(new Runnable() {

				@Override
				public void run() {
					mProressbar.setText("登录失败");
				}
			});

		}
	};

	/**
	 * QQ，微博授权回调
	 */
	private OauthListener oauth = new OauthListener() {

		@Override
		public void OauthSuccess(Object obj) {
			mProressbar.setText("正在为你登录");
			mProressbar.setVisibility(View.VISIBLE);
		}

		@Override
		public void OauthFail(Object type) {
			Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void OauthCancel(Object type) {
			Toast.makeText(getApplicationContext(), "取消授权", Toast.LENGTH_SHORT)
					.show();
		}
	};

	/**
	 * 微信授权广播回调
	 * 
	 * @author user
	 * 
	 */
	private class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if ("ACTION_WX_LOGIN_SUCEESS".equals(intent.getAction())) {
				final String code = intent.getStringExtra("code");
				mProressbar.setText("正在为你登录");
				mProressbar.setVisibility(View.VISIBLE);
				new Thread(new Runnable() {

					@Override
					public void run() {
						ThirdWeiXinLoginApi.getOauthAcces(code, oauthlogin);
					}
				}).start();
			}
		}
	}

}
