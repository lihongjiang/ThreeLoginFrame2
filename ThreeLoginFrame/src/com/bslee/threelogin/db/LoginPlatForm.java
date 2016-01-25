package com.bslee.threelogin.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginPlatForm {

	private SharedPreferences sp;
	public static final int QQZONE_PLATPORM = 3;
	public static final int WECHAT_PLATPORM = 4;
	public static final int WEIBO_PLATPORM = 2;
	public static final int LOCAL_PLATPORM = 1;

	public LoginPlatForm(Context context) {
		sp = context.getSharedPreferences("platform", Context.MODE_PRIVATE);
	}

	public void setPlat(String type, int plat) {
		Editor editor = sp.edit();
		editor.putInt("PLATPORM", plat);
		editor.commit();
	}

	public int getPlat() {
		return sp.getInt("PLATPORM", LOCAL_PLATPORM);
	}
	
	public boolean isMiyaLogin() {
		return sp.getInt("PLATPORM", LOCAL_PLATPORM)==LOCAL_PLATPORM;
	}
	
	public boolean clear(){
		return sp.edit().clear().commit();
	}
}
