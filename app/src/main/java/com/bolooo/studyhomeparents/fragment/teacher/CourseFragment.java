package com.bolooo.studyhomeparents.fragment.teacher;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.chat.PrivateLetterWebViewActivity;
import com.bolooo.studyhomeparents.adapter.TeacherCourseAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.event.FocusCurriculum;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;

import butterknife.Bind;

/**
 * Created by Guojunhong on 2017/2/24.
 */

public class CourseFragment extends BaseFragment implements TeacherCourseAdapter.ItransmitNumLisenter {

    @Bind(R.id.rlv_view)
    RecyclerView rlvView;
    TeacherCourseAdapter adapter;
    int total = 0 ;
    private boolean flag = false;
    private RelativeLayout llpop;
    private TextView untickNum;
    private String courseId;
    private String teachName;
    private String teacherID;
    private ImageView courseSignUpImage;
    private int courseStatusType;
    private ImageView tvSignUp;
    private ImageView chatEntrance;

    public static CourseFragment newInstance(String teachId, String courseId, String teachName) {
        CourseFragment fragment = new CourseFragment();
        Bundle bundle =new Bundle();
        bundle.putString("teachId",teachId);
        bundle.putString("courseId",courseId);
        bundle.putString("teachName",teachName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.new_teacher_course_fragment, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (llpop!=null )llpop.setVisibility(View.VISIBLE);
        } else {
            if (llpop!=null)llpop.setVisibility(View.GONE);
        }
    }


    public void onEventMainThread(FocusCurriculum event) {
        adapter.getTeacherCourse(teacherID,courseId);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initView() {
        Bundle arguments = getArguments();
        if (arguments != null){
            teacherID = arguments.getString("teachId");
            courseId = arguments.getString("courseId");
            teachName = arguments.getString("teachName");
            llpop = (RelativeLayout) getActivity().findViewById(R.id.buy_pop);
            //llpop.setVisibility(View.VISIBLE);
            untickNum = (TextView) llpop.findViewById(R.id.uticket_num);
            courseSignUpImage = (ImageView) llpop.findViewById(R.id.course_sign_up);
            tvSignUp = (ImageView) llpop.findViewById(R.id.course_sign_up);
            chatEntrance = (ImageView) llpop.findViewById(R.id.chat_entrance);

            if (!StringUtil.isEmpty(teacherID)){
                adapter = new TeacherCourseAdapter(getActivity(),teacherID,courseId,teachName,tvSignUp);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                rlvView.setLayoutManager(linearLayoutManager);
                adapter.setLisenter(this);
                rlvView.setAdapter(adapter);
//                rlvView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                      @Override
//                      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                          super.onScrollStateChanged(recyclerView, newState);
//                      }
//
//                      @Override
//                      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                          super.onScrolled(recyclerView, dx, dy);
//                          total -= dy;
//                          if (Math.abs(total) > 700 && flag){
//                              if (llpop != null) {
//                                  if (courseStatusType == 2)llpop.setVisibility(View.VISIBLE);
//                              }
//                          }else {
//                              if (llpop != null) {
//                                  llpop.setVisibility(View.GONE);
//                              }
//                          }
//                      }
//                  });
            }
        }

//        tvSignUp.setOnClickListener(view -> {
//            if (checkLogin()){
//                if (courseStatusType == 1){
//                    //showAdialogTips();
//                }else if (courseStatusType == 2){
//                    Bundle bundle = new Bundle();
//                    bundle.putString("courseId",courseId);
//                    bundle.putString("tid",teacherID);
//                    //IntentUtils.startIntentBundle(activity,bundle, SignUpActivity.class);
//                    IntentUtils.startNewIntentBundle(activity,bundle, ClassSignUpActivity.class);
//                }
//            }
//        });
        chatEntrance.setOnClickListener(V->{
            if (checkLogin()){
                Bundle bundle = new Bundle();
                bundle.putString("tid",teacherID);
                bundle.putString("courseId",courseId);
                IntentUtils.startNewIntentBundle(activity,bundle,PrivateLetterWebViewActivity.class);
               // IntentUtils.startNewIntentBundle(activity,bundle,PrivateLetterActivity.class);
            }

        });
    }


    @Override
    public void onTransitNum(int num, String courseId1) {
        untickNum.setText(num+"");
        courseId = courseId1;
    }

    @Override
    public void OnCourseStatus(int courseStatus) {
        courseStatusType = courseStatus;
//        switch (courseStatus){
//            case  1:
//                courseSignUpImage.setImageResource(R.drawable.btn_apply_pre);
//               // tvSignUp.setEnabled(false);
//                break;
//            case  2:
//                courseSignUpImage.setImageResource(R.drawable.btn_apply);
//              //  tvSignUp.setEnabled(true);
//                break;
//            case  3:
//                courseSignUpImage.setImageResource(R.drawable.btn_apply_full);
//              //  tvSignUp.setEnabled(false);
//                break;
//        }

    }

    @Override
    public void OnShowBottomLayout(boolean isshow) {
        if (!isshow) {
            if (llpop!=null )llpop.setVisibility(View.VISIBLE);
        } else {
            if (llpop!=null)llpop.setVisibility(View.GONE);
        }
    }
}
