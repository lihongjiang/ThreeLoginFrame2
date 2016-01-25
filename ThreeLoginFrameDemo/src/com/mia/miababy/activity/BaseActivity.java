package com.mia.miababy.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.bslee.threelogin.api.ThirdQQLoginApi;
import com.bslee.threelogin.api.ThirdWeiBoLoginApi;

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (ThirdQQLoginApi.getTencent(this) != null) {
			ThirdQQLoginApi.getTencent(this).onActivityResult(requestCode,
					resultCode, data);
		}
		if (ThirdWeiBoLoginApi.getSsoHandler(this) != null) {
			ThirdWeiBoLoginApi.getSsoHandler(this).authorizeCallBack(
					requestCode, resultCode, data);
		}
	}

}
