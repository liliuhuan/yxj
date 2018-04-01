package com.bolooo.studyhomeparents.activty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.base.BaseTakePhoneActivity;
import com.bolooo.studyhomeparents.entity.UserInfoEntity;
import com.bolooo.studyhomeparents.event.LogoutEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.SetInterestEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.request.util.UploadUtils;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.OnClickUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.TakePhoneUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.TakePhoneDialog;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * 家长信息设置
 * nanfeifei
 * 2017/2/28 18:34
 *
 * @version 3.7.0
 */
public class UserInfoActivity extends BaseTakePhoneActivity implements UploadUtils.UploadCallBack,
        TakePhoneDialog.OnSelectTakePhoneWay, IRequestCallBack {
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.header_icon_iv)
    ImageView headerIconIv;
    @Bind(R.id.header_rl)
    RelativeLayout headerRl;
    @Bind(R.id.nickname_tv)
    TextView nicknameTv;
    @Bind(R.id.nickname_lay)
    LinearLayout nicknameLay;
    @Bind(R.id.family_identity_tv)
    TextView familyIdentityTv;
    @Bind(R.id.family_identity_lay)
    LinearLayout familyIdentityLay;
    @Bind(R.id.mobile_tv)
    TextView mobileTv;
    @Bind(R.id.weichat_tv)
    TextView weichatTv;
    @Bind(R.id.weichat_lay)
    LinearLayout weichatLay;
    @Bind(R.id.learning_intent_tv)
    TextView learningIntentTv;
    @Bind(R.id.learning_intent_lay)
    LinearLayout learningIntentLay;
    @Bind(R.id.vip_lay)
    LinearLayout vipLay;
    @Bind(R.id.progressBar)
    WaitProgressBar waitProgressBar;
    TakePhoneDialog takePhoneDialog;
    UserInfoEntity userInfoEntity;
    @Bind(R.id.vip_tv)
    TextView vipTv;
    @Bind(R.id.system_setting)
    LinearLayout systemSetting;
    private IWXAPI api;

    @Override
    protected void initView() {
        initBar();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //userInfoEntity = bundle.getParcelable("userInfoEntity");
        }
        insureBar.setTitle(getString(R.string.user_info_text));
        waitProgressBar.hide();
        takePhoneDialog = new TakePhoneDialog(this);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initDate() {
        super.initDate();
        api = WXAPIFactory.createWXAPI(UserInfoActivity.this,
                Constants.APP_ID);
        if (userInfoEntity == null) {
            return;
        }
        String headPhoto = userInfoEntity.getHeadPhoto();
        if (!TextUtils.isEmpty(userInfoEntity.getHeadPhoto())) {
            glideUtils.loadFileNewImageRound(userInfoEntity.getHeadPhoto(), headerIconIv);
        }else {
            glideUtils.loadRoundImage(userInfoEntity.WeChatHeadPhoto, headerIconIv,R.drawable.noavatar,DensityUtil.dip2px(this,66));
        }
        nicknameTv.setText(userInfoEntity.UserName);
        familyIdentityTv.setText(userInfoEntity.FamilyRole);
        mobileTv.setText(userInfoEntity.Mobile);
        if (userInfoEntity.Intentions != null) {
            StringBuffer learningIntent = new StringBuffer();
            for (int i = 0; i < userInfoEntity.Intentions.size(); i++) {
                learningIntent.append(userInfoEntity.Intentions.get(i).TagName + ",");
            }
            learningIntentTv.setText(learningIntent.substring(0, learningIntent.length() - 1));
        }
        if (TextUtils.isEmpty(userInfoEntity.UnionId)) {
            weichatTv.setText("未绑定");
            weichatLay.setClickable(true);
        } else {
            weichatTv.setText("已绑定");
            weichatLay.setClickable(false);
        }

        if (userInfoEntity.IsVIPOpen) {
            vipLay.setVisibility(View.VISIBLE);
        } else {
            vipLay.setVisibility(View.GONE);
        }
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getParent(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (waitProgressBar != null) {
            waitProgressBar.hide();
        }
    }

    @OnClick({
            R.id.login_btn, R.id.header_rl, R.id.nickname_lay, R.id.family_identity_lay, R.id.weichat_lay,
            R.id.learning_intent_lay, R.id.vip_lay,R.id.system_setting,R.id.untiket_detail,R.id.my_order_list
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                SharedPreferencesUtil.getInstance().clearData();
                EventBus.getDefault().post(new MineEvent());
                EventBus.getDefault().post(new LogoutEvent());
                finish();
                break;
            case R.id.header_rl:
                takePhoneDialog.showTakePhoneDialog(this);
                break;
            case R.id.nickname_lay: {
                Intent intent = new Intent(this, EditNameActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.family_identity_lay: {
                Intent intent = new Intent(this, FamilyIdentityActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.weichat_lay: {
                if (OnClickUtils.isFastDoubleClick()) {
                    return;
                }
                if (api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
                    waitProgressBar.show();
                    StudyApplication.getInstance().weichatLoginType = 2;
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "studyhomeparents";
                    api.sendReq(req);
                } else {
                    ToastUtils.showToast("您还没有安装微信或微信版本过低");
                }

                break;
            }
            case R.id.learning_intent_lay: {
                Intent intent = new Intent(this, SetInterestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sourse", SetInterestActivity.SOURSE_USER_INFO);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            }
            case R.id.vip_lay: {
                if (StringUtil.isEmpty(userInfoEntity.Id)) return;
                if (CommentUtils.isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("pid", userInfoEntity.Id);
                    IntentUtils.startNewIntentBundle(this, bundle, VipActivity.class);
                }

                break;
            }
            case R.id.system_setting:
                IntentUtils.startIntent(this,SystemSettingActivity.class);
                break;
            case R.id.untiket_detail:
                IntentUtils.startIntent(this,UTicketRecordActivity.class);
                break;
            case R.id.my_order_list:
                IntentUtils.startIntent(this,MyOrderListActivity.class);
                break;
        }
    }

    @Override
    public void onPickFromCapture() {
        TakePhoneUtils.getInstance().onPickFromCaptureWithCrop(getTakePhoto());
    }

    @Override

    public void onPickFromGallery() {
        TakePhoneUtils.getInstance().onPickFromGalleryWithCrop(getTakePhoto());
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        List<TImage> images = result.getImages();
        if (images != null && !images.isEmpty()) {
            String imagePath = images.get(0).getCompressPath();
            File file = new File(imagePath);
            glideUtils.loadRoundImage(file, headerIconIv, R.drawable.noavatar,
                    DensityUtil.dip2px(this, 66));
            List<String> list = new ArrayList<>();
            list.add(imagePath);
            UploadUtils.getInstance().uploadFiles(list, this);
            waitProgressBar.show();
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void uploadSucess(List<String> list) {
        waitProgressBar.hide();
        if (list.size() > 0) {
            userInfoEntity.HeadPhoto = list.get(0);
            MineUtils.getInstance().editParent(new IRequestCallBack() {
                @Override
                public void onStartLoading() {
                    waitProgressBar.show();
                }

                @Override
                public void onSuccess(String result) {
                    ToastUtils.showToast("保存成功");
                    waitProgressBar.hide();
                    EventBus.getDefault().post(new MineEvent());
                }

                @Override
                public void onError(String error) {
                    ToastUtils.showToast(error);
                    waitProgressBar.hide();
                }
            }, "HeadPhoto", userInfoEntity.getHeadPhoto());
        }
    }

    @Override
    public void uploadFailure(String error) {
        waitProgressBar.hide();
        ToastUtils.showToast(error);
    }

    @Override
    public void onStartLoading() {
        waitProgressBar.show();
    }

    @Override
    public void onSuccess(String result) {
        waitProgressBar.hide();
        userInfoEntity = JSONObject.parseObject(result, UserInfoEntity.class);
        if (userInfoEntity == null) {
            return;
        }
        initDate();
    }

    @Override
    public void onError(String error) {
        waitProgressBar.hide();
    }

    public void onEventMainThread(SetInterestEvent event) {
        prepareData();
        EventBus.getDefault().post(new MineEvent());
    }

}
