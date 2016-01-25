# ThreeLoginFrame

## 初衷
    
   1.不想依赖三方平台，灵活定制。

## 特性

    1.支持QQ，微信，微博三方授权信息
    2.支持QQ，微信，微博获取三方用户信息
    3.只需一行，一个回调搞定三方登录，三方授权
    
    下期添加三方分享接口？doing....


        

#### 注意：1 需要将 com.mia,miababy改成自己应用的包名。         【参考微信官方文档】
#### 注意：2 需要将 tencent1101525942 数字改成自己应用的APPID。【参考QQ官方文档】 

## 你的工程使用

    0 把weiboSdk，ThreeLoginFrame 两个依赖库添加到工程。
    1 具体使用参考ThreeLoginFrameDemo工程。
    2 注册三个平台的appId填写到ThreeLoginFrame工程的ThirdAppKey类中。
    
        //微信
        public static final String WEIXIN_SCOPE="snsapi_userinfo";
    	public static final String WEIXIN_APPID = "XXXX";
    	public static final String WEIXIN_APPSECRET  = "XXXXX";
        //新浪
        public final static String SINAWEIBO_APPKEY = "XXXXXX";  
        public final static String SINAWEIBO_AppSecret="XXXXXXXXXX";
        public final static String SINAWEIBO_REDIRECT_URI = "https://api.weibo.com/oauth2/default.html"; 
        public final static String SINAWEIBO_SCOPE = "email,direct_messages_read,direct_messages_write,"
                + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                + "follow_app_official_microblog," + "invitation_write" ;
        //QQ
        public final static String QQ_APPID = "XXXXXXXX";
        public final static String QQ_SCOPE = "all" ;
        
    3 配置你的工程manifest文件。
 
 
## 配置文件说明


### 0 配置文件修改应用包名
   
     package="com.mia.miababy"    换成自己应用包名

### 1 配置文件添加权限
    
    <!-- QQ -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <!-- 微信 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

###2 配置文件添加三方组件
    
        <!-- QQ认证页面 -->
        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:launchMode="singleTask"
            android:noHistory="true" >
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data android:scheme="tencent1101525942" />
            </intent-filter>
        </activity>
        <activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
        <!-- 注册微信授权回调 -->
        <activity
            android:exported="true"
            android:label="aa"
            android:launchMode="singleTop"
            android:name="com.mia.miababy.wxapi.WXEntryActivity" />
        <!-- 必须注册在微博授权，分享微博时候用到 -->
        <activity
            android:name="com.sina.weibo.sdk.component.WeiboSdkBrowser"
            android:configChanges="keyboardHidden|orientation"
            android:exported="false"
            android:windowSoftInputMode="adjustResize" >
        </activity>
        
## 如有问题，详细咨询QQ：254608684
    
