package com.mia.miababy;

import com.bslee.threelogin.util.UIUtils;

import android.app.Application;

public class MYApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		UIUtils.initContext(this);
	}

}
