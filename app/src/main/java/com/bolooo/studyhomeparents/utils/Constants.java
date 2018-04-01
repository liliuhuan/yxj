package com.bolooo.studyhomeparents.utils;

/**
 * 常量
 * nanfeifei
 * 2017/2/22 12:02
 *
 * @version 3.7.0
 */
public class Constants {
 /*是否是开发模式*/
 public final static boolean DEVELOPER_MODE = false;
 public static String baseUrl = "http://test.uxueja.com/";
 public static String CHAT_URL = "http://test.uxueja.com/unite/parentChat.html";
 static {
  if(DEVELOPER_MODE){  /*如果是开发模式，则使用测试环境*/
   baseUrl = "http://test.uxueja.com/";
   CHAT_URL = "http://test.uxueja.com/unite/parentChat.html";
   imageUrl = baseUrl+"file/";
  }else{  /*如果上线模式，则使用正式环境  */
   baseUrl = "http://uxueja.com/";
   imageUrl = baseUrl+"file/";
   CHAT_URL = "http://uxueja.com/unite/parentChat.html";
  }
 }
  public static String imageUrl = baseUrl+"file/";
  public final static String cityUrl = "http://us2080.com/shared/citys/";
  public final static String shareCourseUrl = "http://uxueja.com/vue/teacher/lesson?";
  public final static String EMPTY_URL = "http://www.us2080.com/doc/uclient_joinus.html";

  public static String dynamicDetailUrl="http://uxueja.com/vue/dynamicDetail/zonezanDetail?uzoneId=";

 /*使用帮助*/
 public static String useHelp = "http://us2080.com/doc/uclient_faq.html";

 /*关于我们*/
 public static String aboutUs = "http://www.us2080.com/doc/uteacher_about.html";
 //public static String aboutUs = "http://bolooo.com/mobile/about-mobile.html";
 //vip协议
  public static String VIP_URL="http://www.us2080.com/doc/uclient_vip.html";
 // 推荐教师数据缓存
  public static String RECOMMENT_TEACHER= "recommeNt_teacher";
 // 身边好课数据缓存
  public static String HOT_COURESE = "hot_course";
  public static String CITYDATA = "city_list";

  public  static int screenWidth ;
  public  static int screenHeight;
 // APP_ID 替换为你的应用从官方网站申请到的合法appId
  public static final String APP_ID = "wx0bc2b463504212e6";
  /*上传文件*/
  public final static String UPLOAD_FILE_URL = "api/FileUpload";
  /*我的页面家长信息*/
  public final static String PARENT_URL = "api/Parent";
  /*我关注的讲师列表*/
  public final static String FAVORITE_TEACHER_LIST_URL = "api/FavoriteTeacher";
  /*我关注的课程列表*/
  public final static String FAVORITE_CORRICULUM_LIST_URL = "api/CourseWantGo";
  /*我的宝贝信息*/
  public final static String BABY_LIST_URL = "api/Children";
  /*获取宝贝信息*/
  public final static String BABY_INFO_URL = "api/Children";
  /*添加宝贝*/
  public final static String ADD_BABY_URL = "api/Children";
  /*编辑宝贝*/
  public final static String EDIT_BABY_URL = "api/Children";
  /*消息通知*/
  public final static String MESSAGE_LIST_URL = "api/SysNotice";
  /*邮票购买列表*/
  public final static String UTICKET_LIST_URL = "api/UTicket";
 /*获取支付宝支付所需信息(邮票购买)*/
 public final static String UTICKET_ALIPAY_URL = "api/AliPay";
 /*获取微信支付所需信息(邮票购买)*/
 public final static String UTICKET_WEICHAT_URL = "api/WeChatAppPay";
 /*支付结果验证*/
 public final static String AUTH_PAY_RSULT_URL = "api/Pay";
  /*邮票购买记录列表*/
  public final static String UTICKET_RECORD_LIST_URL = "api/UTicketRecord";
 //邮票记录详情
  public final static String UTICKET_RECORD_DETAIL_URL = "api/UTicketRecord";
  /*积分兑换*/
  public final static String POINT_EXCHANGE_URL = "api/Parent";
  /*根据家长Id查询家长不同状态订单列表*/
  public final static String MINE_LESSONS_LIST_URL = "api/Order";
  /*扫码上课页面数据*/
  public final static String QR_CODE_LESSONS_URL = "api/MyCourse";
  /*获取点评标签*/
  public final static String COMMENT_TAG_URL = "api/CommentTag";
  /*点评课程*/
  public final static String SUBMIT_COMMENT_URL = "api/CourseDynamic";
  /*购课详情*/
  public final static String LESSONS_ORDER_DETAIL_URL = "api/Order";
  /*微信登录*/
  public final static String WEICHAT_LOGIN_URL = "api/WeChatLogin";
 /*微信绑定手机号*/
 public final static String LOGIN_URL = "api/Parent";
 /*热门讲师*/
 public final static String HOT_TEAHCER_URL = "api/Teacher?";
 public final static String NEW_HOT_TEAHCER_URL = "api/Teacher?";
 /*课程类型*/
 public final static String COURSE_TYPE_URL= "api/CourseType";
 /*获得课程*/
 public final static String FRIST_LESSONS_URL = "api/Course?";
 /*评价列表*/
 public final static String COURSE_DYNAMIC_URL = "api/CourseDynamic?";
 /*l留言*/
 public final static String TEACHER_MESSAGE_URL = "api/Message";
 /*关注教师取消关注教师*/
 public final static String FAV_TEACHER_URL = "api/FavoriteTeacher";
 /*想去课程*/
 public final static String WANT_GO_URL = "api/CourseWantGo?";
/*获取验证码*/
 public final static String GET_VERTY_CODE_URL = "api/VerifyCode";
/*想去课程*/
 public final static String CHOOSE_INTENTTION_URL = "api/MyLearnIntention";
/*热门课程标签*/
 public final static String HOT_COURSE_TAG_URL = "api/HotCourseTag";
 /*报名课程列表*/
 public final static String SIGN_UP_URL = "api/CourseFrequency?";
 /*查询课程时间安排*/
 public final static String FRAQUENCY_DETAIL_URL = "api/FrequencyDetail?";
 /*baby列表*/
 public final static String CHILD_LIST_URL = "api/Children";
/*baby订单列表*/
 public final static String CHILD_Order_URL = "api/Order";
/*首页广告*/
 public final static String MAIN_AD_URL = "api/Advertisement";
 /*讲师页面 讲师相册*/
 public final static String TEACHER_PICS = "api/TeacherImage?";
 /*检查更新*/
 public final static String UPDATE_APP_URL = baseUrl+"api/VersionInfo?app=2";
 /*闪屏广告*/
 public final static String SPLASH_URL = "api/SysSetting";
 /*根据游票数获取该游票产品价格*/
 public final static String UTICKET_URL = "api/UTicket?";
 //验证孩子是否可以报名接口
 public final static String CHECK_CHILD = "api/OrderDetail";
 //发布动态
 public final static String PUBLISH_DYNAMIC="api/UZone";
 //老版本文件上传
 public final static String FILE_OLD_UPLOAD = "api/FileUpload";
 //获取动态
 public final static String GET_UZONE = "api/UZone?";
 //动态点赞
 public final static String ADD_DYNAMICZAN = "api/UZoneZan";
 //点赞列表
 public final static String UZONEZAN_LIST = "api/UZoneZan";
 //动态评论列表
 public final static String DYNAMIC_COMMENT_LIST = "api/UZoneComment";
 //发布评论
 public final static String PUBLISH_COMMENT = "api/UZoneComment";
 //获取vip状态
 public final static String VIP_STATE = "api/Parent";
 //获取vip产品列表
 public final static String VIP_PRODUCT = "api/UTicket";

 //获取vip孩子列表页接口：
 public final static String VIP_CHILDLIST = "api/VIP";
 //根据Id删除孩子
 public final static String CHILD_DELETE = "api/Children";

 public final static String CITY_LIST = "api/City";
 //新的首页接口
 public final static String NEW_HOME_COURSE = "api/Course";

 //新的热门城市
 public final static String NEW_HOT_CITY = "api/City";
 //获取讲师详情的图片信息
 public final static String UTIKET_URL = "api/SysSetting";
 //新消息list
 public final static String NEW_MESSAGE_URL = "api/Notice";
 //聊天list
 public final static String CHAT_LIST_URL = "api/UChat";
 //发送聊天信息
 public final static String CHAT_MESSAGE_URL = "api/UChat";
 //根据教师Id 获取教师的基本信息
 public final static String TEACHER_BASIC_INFO= "api/Teacher";

 //家长端：获取教师最新课程、与教师所有留言列表
 public final static String LEAVE_MESSAGE_URL= "api/Message";
 //我的订单列表
 public final static String MY_ORDER_LIST= "api/Order";
 //取消订单列表
 public final static String CANCEL_ORDER= "api/Order";
 //获取收页的轮播图
 public final static String ADVERTISEMENT_IMGAES= "api/Advertisement";
 //获取套餐的信息
 public final static String COMBO_INFO= "api/Package";
 //创建套餐订单
 public final static String CREATE_COMBO_ORDER= "api/PackageOrder";
 //判别孩子是否可以报名套餐
 public final static String CHECK_CHILD_COMBO= "api/PackageOrder";
 //获取套餐订单分页列表
 public final static String GET_COMBO_LIST= "api/PackageOrder";
 //获取未支付订单统计
 public final static String GET_NO_PAY_ORDER= "api/PackageOrder";

 //城市列表
 public final static String GET_CITY_LIST = "api/Province";
//家长端：根据课程Id获取可报名课程信息与课程学习班列表信息
public final static String NEW_COURSE_DOOR_LIST = "api/Course";
 //ADD地址
public final static String ADD_DOOR_ADDRESS = "api/DropInAddress";
 //获取地址列表
public final static String GET_DOOR_ADDRESS_LIST = "api/DropInAddress";
 //家长端：购课时在家长端添加上门购课或自由购课 2017-09-06 for version 1.6.0
public final static String DOOR_OR_FREE_BUY_COURSE = "api/CourseFrequency";

 //家长端：获取分类列表
public final static String GET_DIRECTORY_LIST_URL = "api/DirectoryType";
 //家长端：找课堂 for version 1.7.0 add in 2017-10-13
public final static String LOOK_COURSE_LIST_URL = "api/Course";
 //家长端：获取首页 for version 1.7.0 add in 2017-10-13
public final static String NEW_HOME_DATA_URL = "api/Course";

}
