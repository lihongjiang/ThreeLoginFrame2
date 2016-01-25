package com.bslee.threelogin.util;

import android.content.Context;
import android.widget.Toast;

public class UIUtils {

	private static Context mContext;
	
	public static void initContext(Context context){
		mContext=context;
	}
	
	public static Context getContext(){
		return mContext;
	}

	public static void showToastMessage(int resId) {
		Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();	
	}
	
}
