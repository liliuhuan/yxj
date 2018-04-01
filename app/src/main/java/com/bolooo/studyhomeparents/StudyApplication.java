package com.bolooo.studyhomeparents;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.SDKInitializer;
import com.bolooo.studyhomeparents.entity.UpdateEntity;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.LogUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.db.RealmHelper;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.HttpMethod;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;
import org.lzh.framework.updatepluginlib.util.HandlerUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

public class StudyApplication extends Application {
    public static StudyApplication application = null;
    public static Context context = null;
    private static SharedPreferencesUtil sharedPreferencesUtil = null;
    //设备Id
    public static String deviceId="";
    //设备类型
    public static String DEVICE_TYPE = "2";
    private IWXAPI api;
    public int weichatLoginType = 0;
    public static StudyApplication getInstance() {
        return application;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context=getApplicationContext();
        initWH();
        //initLocation();
        //检测内存泄漏
      //  LeakCanary.install(this);
        SDKInitializer.initialize(context);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setDebugMode(Constants.DEVELOPER_MODE);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        MobclickAgent.enableEncrypt(true);//6.0.0版本及以后
        api = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        api.registerApp(Constants.APP_ID);
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance();
        initUpdateConfig();
        initRealm();//初始化数据库
        XGPushManager.registerPush(context,new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.d("TPush", "注册成功，设备token为：" + data);
                deviceId = data.toString();
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
    }

    private void initWH() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Constants.screenWidth = wm.getDefaultDisplay().getWidth();
        Constants.screenHeight = wm.getDefaultDisplay().getHeight();
    }
    private void initRealm() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(RealmHelper.DB_NAME)
                .schemaVersion(1)
                .rxFactory(new RealmObservableFactory())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
    public static SharedPreferencesUtil getSharedPreferencesUtil() {
        return sharedPreferencesUtil;
    }
    public static Context getContext(){
        return context;
    }
    /*设置检查更新配置*/
    private void initUpdateConfig(){
        UpdateConfig.getConfig()
            // 必填：初始化一个Application框架内使用
            .init(this)
            // 必填：数据更新接口,url与checkEntity两种方式任选一种填写
            //.url(Constants.UPDATE_APP_URL)
            .checkEntity(new CheckEntity().setMethod(HttpMethod.GET).setUrl(Constants.UPDATE_APP_URL))
            // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
            .jsonParser(new UpdateParser() {
                @Override
                public Update parse(String response) {
                        /* 此处根据上面url或者checkEntity设置的检查更新接口的返回数据response解析出
                         * 一个update对象返回即可。更新启动时框架内部即可根据update对象的数据进行处理
                         */
                    LogUtils.i("update===", response);
                    Update update = new Update(response);
                    UpdateEntity updateEntity = JSONObject.parseObject(response, UpdateEntity.class);
                    if(updateEntity!=null){
                        if(updateEntity.IsSuccess){

                            // 此apk包的更新时间
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            try {
                                Date date = null;
                                date = formatter.parse(updateEntity.Data.UpdateTime);
                                update.setUpdateTime(date.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            // 此apk包的下载地址
                            update.setUpdateUrl(updateEntity.Data.DownloadUrl);
                            // 此apk包的版本号
                            update.setVersionCode(updateEntity.Data.VersionNum);
                            // 此apk包的版本名称
                            update.setVersionName(updateEntity.Data.VersionName);
                            // 此apk包的更新内容
                            update.setUpdateContent(updateEntity.Data.UpdateInfo);
                            // 此apk包是否为强制更新
                            update.setForced(updateEntity.Data.IsForcedUpdate);

                            // 是否显示忽略此次版本更新按钮
                            update.setIgnore(true);
                        }
                    }

                    return update;
                }
            })
            // TODO: 2016/5/11 除了以上两个参数为必填。以下的参数均为非必填项。
            .checkCB(new UpdateCheckCB() {

                @Override
                public void onCheckError(int code, String errorMsg) {
                }

                @Override
                public void onUserCancel() {
                }

                @Override
                public void onCheckIgnore(Update update) {
                }

                @Override
                public void onCheckStart() {
                    // 此方法的回调所处线程异于其他回调。其他回调所处线程为UI线程。
                    // 此方法所处线程为你启动更新任务是所在线程
                    HandlerUtil.getMainHandler().post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

                @Override
                public void hasUpdate(Update update) {
                }

                @Override
                public void noUpdate() {
                }
            })
            // apk下载的回调
            .downloadCB(new UpdateDownloadCB(){
                @Override
                public void onUpdateStart() {
                }

                @Override
                public void onUpdateComplete(File file) {
                    ToastUtils.showToast(R.string.update_finish);
                }

                @Override
                public void onUpdateProgress(long current, long total) {
                }

                @Override
                public void onUpdateError(int code, String errorMsg) {
                    ToastUtils.showToast(R.string.update_fail);
                }
            }).strategy(new UpdateStrategy() {
            @Override
            public boolean isShowUpdateDialog(Update update) {
                // 是否在检查到有新版本更新时展示Dialog。
                return true;
            }

            @Override public boolean isAutoInstall() {
                return true;
            }

            @Override
            public boolean isShowDownloadDialog() {
                // 在APK下载时。是否显示下载进度的Dialog
                return true;
            }
        });
    }
}
