package com.bolooo.studyhomeparents.views;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;

/**
 * 选择图片获取方式的Dialog
 * nanfeifei
 * 2017/3/1 10:21
 *
 * @version 3.7.0
 */
public class TakePhoneDialog {
  private Context context;
  AlertDialog dlg;
  public TakePhoneDialog(Context context){
    this.context = context;
  }
  /**
   * 显示拍照选择窗口
   */
  public void showTakePhoneDialog(final OnSelectTakePhoneWay onSelectTakePhoneWay) {
    dlg = new AlertDialog.Builder(context).create();
    dlg.show();
    Window window = dlg.getWindow();
    dlg.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
    // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
    window.setContentView(R.layout.dialog_select_photo);
    window.setGravity(Gravity.CENTER);
    TextView pickFromCupture = (TextView) window.findViewById(R.id.pick_from_cupture);
    TextView pickFromGallery = (TextView) window.findViewById(R.id.pick_from_gallery);
    pickFromCupture.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onSelectTakePhoneWay.onPickFromCapture();
        dlg.cancel();
      }
    });
    pickFromGallery.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onSelectTakePhoneWay.onPickFromGallery();
        dlg.cancel();
      }
    });
  }

  /**
   * 选择图片方式接口
   */
  public interface OnSelectTakePhoneWay{
    /*拍照获得*/
    void onPickFromCapture();
    /*从相册选择*/
    void onPickFromGallery();
  }
}
