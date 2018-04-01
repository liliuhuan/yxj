package com.bolooo.studyhomeparents.activty;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.activty.mine.MessageListActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.fragment.chat.ChatFragment;
import com.bolooo.studyhomeparents.fragment.home.NewHomeFragment;
import com.bolooo.studyhomeparents.fragment.mine.MineFragments;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.FragmentTabHost;
import com.umeng.analytics.MobclickAgent;

import org.lzh.framework.updatepluginlib.UpdateBuilder;

import butterknife.Bind;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity {

    @Bind(R.id.fragment_tab)
    FragmentTabHost fragment_tab;
    //Tab选项卡的文字
    private String mTextviewArray[] = new String[]{"首页","消息", "我的"};
    private Class fragmentArray[] = {NewHomeFragment.class,ChatFragment.class, MineFragments.class};
    private int mImageViewArray[] = {R.drawable.home_icon,R.drawable.chat_icon, R.drawable.mine_icon};
    private long firstTime;
    private boolean goinMine;

    @Override
    public int initLoadResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle messageBundle = getIntent().getExtras();
        if (messageBundle!= null){
            goinMine = messageBundle.getBoolean("goinMine", false);
            if (goinMine)jumpHorizontal(2);else jumpHorizontal(0);
        }

        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null && !StringUtil.isEmpty(bundle.toString())){
            jumpHorizontal(bundle.getInt("pos"));
        }

        setIntent(intent);
    }
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        fragment_tab.setup(this, getSupportFragmentManager(), R.id.tab_content);
        //得到fragment的个数
        int count = fragmentArray.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = fragment_tab.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            fragment_tab.addTab(tabSpec, fragmentArray[i], null);
            fragment_tab.setActivated(true);
            //设置Tab按钮的背景
            fragment_tab.getTabWidget().setDividerDrawable(null);
            fragment_tab.getTabWidget().getChildAt(i);
        }
    }
    @Override protected void initDate() {
        super.initDate();
        UpdateBuilder.create().check();
    }

    private View getTabItemView(int index) {
        View view = View.inflate(this, R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_Icon1);
        TextView tab_lable = (TextView) view.findViewById(R.id.tab_lable1);
        tab_lable.setText(mTextviewArray[index]);
        imageView.setImageResource(mImageViewArray[index]);
        return view;
    }

    public void jumpHorizontal(int pos){
        fragment_tab.setCurrentTab(pos);
    }
    @Override
    public void onBackPressed() {
        if (goinMine){
            IntentUtils.startIntent(this, MessageListActivity.class);
            jumpHorizontal(2);
            goinMine = false;
        }else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
            } else {
                AppExit();
            }
        }
    }
    public void AppExit() {
        MobclickAgent.onKillProcess(StudyApplication.getInstance());
        finish();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    /**
     * 显示拨打电话窗口
     * @param phone
     */
    public void showServicePhoneDialog(final String phone){
        if(phone!=null){
            if(!"".equals(phone)){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage(phone)
                    .setCancelable(false)
                    .setTitle(getString(R.string.lessons_phone_bt))
                    .setPositiveButton("呼叫", (dialog, id) -> {
                        //			        	   dialog.cancel();
                        MainActivityPermissionsDispatcher.makeServicePhoneWithCheck(MainActivity.this,phone);
                    })
                    .setNegativeButton("取消", (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    /**
     * 拨打服务电话
     */
    @NeedsPermission({ Manifest.permission.CALL_PHONE })
    void makeServicePhone(String phone){
        Intent phoneIntent = new Intent(
            "android.intent.action.CALL", Uri.parse("tel:"+phone));
        startActivity(phoneIntent);
    }
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(MainActivity.this, requestCode,
            grantResults);
    }
    /**
     * 一旦用户拒绝了
     */
    @OnPermissionDenied({
        Manifest.permission.CALL_PHONE
    }) void onMakePhoneDenied() {
        ToastUtils.showToast("您拒绝了拨打电话权限，无法联系老师");
    }

    /**
     * 用户选择的不再询问
     */
    @OnNeverAskAgain({
        Manifest.permission.CALL_PHONE
    }) void onMakePhoneNeverAskAgain() {
        ToastUtils.showToast("您拒绝了拨打电话权限，无法联系老师");
    }
}
