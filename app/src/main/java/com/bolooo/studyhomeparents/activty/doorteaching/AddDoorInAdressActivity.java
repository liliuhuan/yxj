package com.bolooo.studyhomeparents.activty.doorteaching;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.CityAreaEntity;
import com.bolooo.studyhomeparents.event.RefreshDoorInListEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.CityAreasUtil;
import com.bolooo.studyhomeparents.request.util.DoorInUtil;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.TimeUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddDoorInAdressActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.tv_contact)
    EditText tvContact;
    @Bind(R.id.tv_contact_phone)
    EditText tvContactPhone;
    @Bind(R.id.tv_area)
    TextView tvArea;
    @Bind(R.id.tv_detail_address)
    EditText tvDetailAddress;
    private boolean isLoadData;
    private String pId;
    private String cId;
    private String aId;

    @Override
    public int initLoadResId() {
        return R.layout.activity_add_door_in_adress;
    }

    @Override
    protected void initView() {
        checkBox.setVisibility(View.GONE);
        title.setText(getString(R.string.new_add_address));
    }

    @Override
    protected void initDate() {
        initJsonData();
    }

    @OnClick({R.id.back,R.id.tv_area, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                hideSoftInput();
                finish();
                break;
            case R.id.tv_area:
                hideSoftInput();
                if (isLoadData)showPickerView();
                break;
            case R.id.tv_save:
                hideSoftInput();
                vertyData();
                break;
        }
    }

    private void hideSoftInput() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                AddDoorInAdressActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void vertyData() {
        String textArea = tvArea.getText().toString();
        String textContact = tvContact.getText().toString();
        String textContactPhone = tvContactPhone.getText().toString();
        String textDetailAddress = tvDetailAddress.getText().toString();
        if (TextUtils.isEmpty(textContact)){
            ToastUtils.showToast("请填写联系人!");
            return;
        }
        if (textContact.length()>8){
            ToastUtils.showToast("联系人最多8个字!");
            return;
        }
        if (TextUtils.isEmpty(textContactPhone)){
            ToastUtils.showToast("请填写联系电话!");
            return;
        }
        if (!TimeUtils.isMobile(textContactPhone)) {
            ToastUtils.showToast("手机号格式不正确");
            return;
        }
        if (TextUtils.isEmpty(textArea)){
            ToastUtils.showToast("请选择地区!");
            return;
        }
        if (TextUtils.isEmpty(textDetailAddress)){
            ToastUtils.showToast("请填写详细地址!");
            return;
        }
        if (textDetailAddress.length() > 50){
            ToastUtils.showToast("细地址最多50个字!");
            return;
        }
        // TODO: 2017/9/6
        Map<String, String> map = new HashMap<>();
        map.put("ContactPerson",textContact);
        map.put("ContactPhone",textContactPhone);
        map.put("ProvinceId",pId);
        map.put("CityId",cId);
        map.put("AreaId",aId);
        map.put("ParentId",SharedPreferencesUtil.getInstance().getUserId());
        map.put("AddressDetail",textDetailAddress);

        DoorInUtil.getInstance().addDoorAddress(map, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(new RefreshDoorInListEvent());
                finish();
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    private List<CityAreaEntity> options1Items = new ArrayList<>();
    private List<ArrayList<CityAreaEntity.CitysEntity>> options2Items = new ArrayList<>();
    private List<ArrayList<ArrayList<CityAreaEntity.CitysEntity.AreasEntity>>> options3Items = new ArrayList<>();
    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                pId = options1Items.get(options1).getId();
                cId = options2Items.get(options1).get(options2).getId();
                aId = options3Items.get(options1).get(options2).get(options3).getId();
                String tx = options1Items.get(options1).getPickerViewText()+" "+
                        options2Items.get(options1).get(options2).getPickerViewText()+" "+
                        options3Items.get(options1).get(options2).get(options3).getPickerViewText();
              //  tvArea.setTextColor(UIUtil.getColor(R.color.text_color_99));
                tvArea.setText(tx);
            }
        }).setTitleText("城市选择")
                .setTitleColor(getResources().getColor(R.color.text_color_77))
                .setTitleBgColor(getResources().getColor(R.color.bg_ff))
                .setDividerColor(getResources().getColor(R.color.text_color_99))
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(16)
                .setCancelColor(getResources().getColor(R.color.bar_bg))
                .setSubmitColor(getResources().getColor(R.color.bar_bg))
                .setOutSideCancelable(true)// default is true
                .setLineSpacingMultiplier(2.4F)
                .build();

        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }
    private void initJsonData() {//解析数据
        String cityData = SharedPreferencesUtil.getInstance().getCityData();
        if (TextUtils.isEmpty(cityData)) requestCityData();
        else loadCityData(cityData);
    }

    private void loadCityData(String cityData) {
        List<CityAreaEntity> jsonBean = com.alibaba.fastjson.JSONObject.parseArray(cityData, CityAreaEntity.class);
        options1Items = jsonBean;
        if (jsonBean != null && jsonBean.size() > 0){
            for (int i=0;i<jsonBean.size();i++){//遍历省份
                ArrayList<CityAreaEntity.CitysEntity> CityList = new ArrayList<>();//该省的城市列表（第二级）
                ArrayList<ArrayList<CityAreaEntity.CitysEntity.AreasEntity>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
                for (int c=0; c<jsonBean.get(i).getCitys().size(); c++){//遍历该省份的所有城市
                    CityAreaEntity.CitysEntity citysEntity = jsonBean.get(i).getCitys().get(c);
                    CityList.add(citysEntity);//添加城市

                    ArrayList<CityAreaEntity.CitysEntity.AreasEntity> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (jsonBean.get(i).getCitys().get(c).getAreas() == null
                            ||jsonBean.get(i).getCitys().get(c).getAreas().size()==0) {
                        // City_AreaList.add(new CityAreaEntity.CitysEntity.AreasEntity());
                    }else {
                        for (int d=0; d < jsonBean.get(i).getCitys().get(c).getAreas().size(); d++) {//该城市对应地区所有数据
                            CityAreaEntity.CitysEntity.AreasEntity areasEntity = jsonBean.get(i).getCitys().get(c).getAreas().get(d);

                            City_AreaList.add(areasEntity);//添加该城市所有地区数据
                        }
                    }
                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                }

                /**
                 * 添加城市数据
                 */
                options2Items.add(CityList);

                /**
                 * 添加地区数据
                 */
                options3Items.add(Province_AreaList);
            }
        }
        isLoadData = true;
    }

    private void requestCityData() {
        CityAreasUtil.getInstance().getCityAreas(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                SharedPreferencesUtil.getInstance().saveCityData(result);
                loadCityData(result);
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }
}
