package com.mia.miababy.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.bslee.threelogin.api.ThirdWeiXinLoginApi;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelbase.BaseResp.ErrCode;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ThirdWeiXinLoginApi.getWXAPI(getApplicationContext()).handleIntent(
				getIntent(), this);
	}

	@Override
	public void onReq(com.tencent.mm.sdk.modelbase.BaseReq arg0) {

	}

	@Override
	public void onResp(BaseResp resp) {

		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
			String code = ((SendAuth.Resp) resp).code;
			if (code != null) {
				Log.v("code:", code);
				Intent action = new Intent();
				action.setAction("ACTION_WX_LOGIN_SUCEESS");
				action.putExtra("code", code);

				LocalBroadcastManager.getInstance(this).sendBroadcast(action);
			}
			
			int errorCode = resp.errCode;
			if( errorCode != ErrCode.ERR_OK ){
				String resid = errorCode == ErrCode.ERR_USER_CANCEL ? "授权取消" : "授权失败";
				Toast.makeText(this, resid, Toast.LENGTH_SHORT).show();
			}
		}
		
		finish();
		
	}
}
