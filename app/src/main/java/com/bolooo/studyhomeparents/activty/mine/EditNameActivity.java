package com.bolooo.studyhomeparents.activty.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.event.SetInterestEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyTextWatcher;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 修改家长昵称
 * nanfeifei
 * 2017/3/1 18:15
 *
 * @version 3.7.0
 */
public class EditNameActivity extends BaseActivity implements IRequestCallBack {
  @Bind(R.id.nickname_tv) EditText nicknameTv;
  @Bind(R.id.login_btn) Button loginBtn;

  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.edit_name_title));
    nicknameTv.addTextChangedListener(new MyTextWatcher() {
      @Override
      public void afterTextEidte(String s) {
        if (s.length() > 6){
          ToastUtils.showToast("最大只能输入6个字");
          nicknameTv.setText(s.substring(0,6));
          nicknameTv.setSelection(6);
        }
      }
    });
  }

  @Override public int initLoadResId() {
    return R.layout.activity_edit_name;
  }


  @OnClick(R.id.login_btn) public void onClick(View view) {
    switch (view.getId()){
      case R.id.login_btn:{
        String nickName = nicknameTv.getText().toString().trim();
        if(TextUtils.isEmpty(nickName)){
          ToastUtils.showToast("请输入昵称");
          return;
        }
        MineUtils.getInstance().editParent(this, "UserName", nickName);
        break;
      }
    }
  }

  @Override public void onStartLoading() {

  }

  @Override public void onSuccess(String result) {
    ToastUtils.showToast("保存成功");
    EventBus.getDefault().post(new SetInterestEvent());
    EditNameActivity.this.finish();

  }

  @Override public void onError(String error) {
    ToastUtils.showToast(error);
  }
}
