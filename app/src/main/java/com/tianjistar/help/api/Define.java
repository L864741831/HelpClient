
package com.tianjistar.help.api;

/**
 * 类说明: 定义网络请求的url
 *
 * @author Victor
 * @email 468034043@qq.co
 * @time 2016年10月29日 下午9:11:53
 */
public class Define {



    public static final String Base_Address = "http://47.94.90.205";// 测试地址
    /**
     * 检查支付密码是否正确
     */
    public static final String CHECK_PAY_IS_PASS = Base_Address + "/a/Payment/checkPayPass";
    /**
     * 一键呼救
     */
    public static final String CALL_FOR_HELP = Base_Address + "/a/mobile/order/sosOrders";
    /**
     * 修改个人信息
     */
    public static final String MODIFY_USER_INFO = Base_Address + "/a/mobile/userModify";
    /**
     * 用户取消订单，前提是救援人员没有接单
     */
    public static final String CANCLE_ORDER = Base_Address + "/a/mobile/order/cancelOrder";
    /**
     * 获取用户信息
     */
    public static final String GET_USER_INFO = Base_Address + "/a/mobile/user";
    /**
     * 查询附近救援点
     */
    public static final String NEARBY_HELP_ADDRESS = Base_Address + "/mobile/office/findlist";
    /**
     * 用户查询订单
     */
    public static final String RESCUE_RECORD = Base_Address + "/a/mobile/order/findUserOrderListAdIos";
    /**
     * 查询余额
     */
    public static final String CHECK_BALANCE = Base_Address + "/a/tyMoney/findMoney";
    /**
     * 用户注册 和找回密码
     */
    public static final String REGISTER = Base_Address + "/mobile/registered";
    /**
     * 发送短信验证码
     */
    public static final String GET_CAPTCHA = Base_Address + "/sms/send";
    /**
     * 检查短信验证码是否正确
     */
    public static final String CHECK_CAPTCHA = Base_Address + "/sms/checkCode";
    /**
     * 检查手机号是否可以注册
     */
    public static final String USER_CHECK = Base_Address + "/mobile/userCheck";
    /**
     * 用户登录
     */
    public static final String LOGIN = Base_Address + "/a/login";
    /**
     * 退出
     */
    public static final String WITHDRAW = Base_Address + "/logout";
    /**
     * 修改登录密码
     */
    public static final String UPDATE_PASSWORD = Base_Address + "/a/mobile/updatePassword";
    /**
     * 单张上传图片
     */
    public static final String UPLOAD_PIC = Base_Address + "/a/alioss/uploadFile";
    /**
     * 多张上传图片
     */
    public static final String UPLOAD_FILES = Base_Address + "/a/alioss/uploadFile";
    /**
     * 公告急救小常识
     */
    public static final String FIRST_AID_COMMON_SENSE = Base_Address + "/article/findPage";
    /**
     * 查看文章
     */
    public static final String VIEW = Base_Address + "/article/view";
    /**
     * 实名认证
     */
    public static final String REAL_NAME_AUTHENTICATION = Base_Address + "/a/mobile/actualName";
    /**
     * 查询救援卡
     */
    public static final String CHECK_HELP_CARD = Base_Address + "/mobile/office/findMemberList";
    /**
     * 购买救援卡
     */
    public static final String BUY = Base_Address + "/a/tyMoney/buy";
    /**
     * 支付宝支付
     */
    public static final String ALIPAY = Base_Address + "/a/pay/app";







    /**
     * 获得交易信息
     */
    public static final String TRANSACTION_RECORD = Base_Address + "/a/tyMoney/transactionRecord";
    /**
     * 检测软件更新
     */
    public static final String CHECK = Base_Address + "/software/check";
    /**
     * 协议查看
     */
    public static final String  PROTOCOL_VIEW = Base_Address + "/protocol/view";
    /**
     * 查看报告或者查看回访
     */
    public static final String  VIEW_DOCUMENT = Base_Address + "/document/viewDocument";
    /**
     * 上传报告或者回访
     */
    public static final String  IPLOAD = Base_Address + "/document/upload";





















    /*

     */
    public static final String API_DOMAIN = "http://47.94.90.205";// API域名 测试环境

    public static final String BASE_URL = "http://47.94.90.205";// 测试



    /**
     * 好友列表
     */
    public static final String URL_friend_list = API_DOMAIN + "User/friend";

    /**
     * 寄语列表
     */
    public static final String URL_wishes = API_DOMAIN + "Sendword/sendwordList";




/*   http://47.94.90.205

   https://apiview.com/p/8054#/api/list*//*

   //public static final String API_DOMAIN = "http://nlcs.tianjistar.com/index.php/Home/";// API域名 测试环境

   public static final String API_DOMAIN = "http://47.94.90.205/index.php/Home/";// API域名 测试环境


   public static final String FIND_HEAD_URL = API_DOMAIN + "User/userinfo";//获取用户信息
   public static final String MINE_FEELING_URL = API_DOMAIN + "Gnosis/gnosisList";//我的心得
   public static final String FAMILY_URL = API_DOMAIN + "Family/familyInfo";//家族
   public static final String SELECT_ZUZHANG_URL = API_DOMAIN + "/Family/choice";//群列表选择族长
   public static final String FAMILY_INFO_URL = API_DOMAIN + "Family/inforMation";//家族信息
   public static final String EDITEX_FAMILY_INFO = API_DOMAIN + "Family/save";//保存编辑家族信息


//   public static final String BASE_URL = "http://zjlist0226.oicp.io:29037";// 测试
   public static final String BASE_URL = "http://tianyou.jiushitao.cn";// 测试
//   public static final String BASE_URL = "http://ty.tianjistar.com";// 测试
//   public static final String BASE_URL = "http://tianyou.tianjistar.com";// 测试
   *//**
     * 用户登录
     *//*
   public static final String LOGIN_URL = BASE_URL + "/a/login?__ajax=true";
   *//**
     * 获取验证码
     *//*
   public static final String GET_CAPTCHA_URL = BASE_URL + "/sms/send";
   *//**
     * 检验验证码
     *//*
   public static final String CHECK_CAPTCHA_URL = BASE_URL + "/sms/checkCode";
   *//**
     * 注册
     *//*
   public static final String REGISTER_URL = BASE_URL + "/mobile/registered";
   *//**
     * 获取用户信息
     *//*
   public static final String GET_USER_INFO_URL = BASE_URL + "/a/mobile/user";
   *//**
     * 修改个人信息
     *//*
   public static final String MODIFY_USER_INFO_URL = BASE_URL + "/a/mobile/userModify";
   *//**
     * 上传图片
     *//*
   public static final String UPLOAD_PIC_URL = BASE_URL + "/a/alioss/uploadFile";
   *//**
     * 修改登录密码
     *//*
   public static final String UPDATE_PASSWORD_URL = BASE_URL + "/a/mobile/updatePassword";
   *//**
     * 退出登录
     *//*
   public static final String WITHDRAW_URL = BASE_URL + "/logout";
   *//**
     * 救援记录
     *//*
   public static final String RESCUE_RECORD_URL = BASE_URL + "/a/mobile/order/findUserOrderListAdIos";
   *//**
     * 急救小常识，系统公告
     *//*
   public static final String FIRST_AID_COMMON_SENSE_URL = BASE_URL + "/article/findPage";
   *//**
     * 常识，公告详情
     *//*
//   public static final String FIRST_AID_DETAILS_URL = BASE_URL + "/article/view";
   *//**
     * 一键呼救
     *//*
   public static final String CALL_FOR_HELP_URL = BASE_URL + "/a/mobile/order/sosOrders";
   *//**
     * 查询附近救援点
     *//*
   public static final String NEARBY_HELP_ADDRESS_URL = BASE_URL + "/mobile/office/findlist";
   *//**
     * 用户取消订单，前提是救援人员没有接单
     *//*
   public static final String CANCLE_ORDER_URL = BASE_URL + "/a/mobile/order/cancelOrder";
   *//**
     * 实名认证
     *//*
   public static final String REAL_NAME_AUTHENTICATION_URL = BASE_URL + "/a/mobile/actualName";
   *//**
     * 支付宝支付
     *//*
   public static final String ALIPAY_URL = BASE_URL + "/a/pay/app";
   *//**
     * 查询余额
     *//*
   public static final String CHECK_BALANCE_URL = BASE_URL + "/a/tyMoney/findMoney";
   *//**
     * 查询救援卡
     *//*
   public static final String CHECK_HELP_CARD_URL = BASE_URL + "/mobile/office/findMemberList";
   *//**
     * 查询订单动态
     *//*
   public static final String CHECK_ORDER_URL = BASE_URL + "/a/orderStauts/findByOrderId";







   *//**
     * 获取验证码
     *//*
   public static final String URL_GET_register = API_DOMAIN + "Login/register";
   *//**
     * 获取忘记密码验证码
     *//*
   public static final String URL_FORGET_GET_YZM=API_DOMAIN+"Login/sms";

   *//**
     * 忘记密码提交新密码
     *//*
   public static final String URL_FORGET_POST_PWD=API_DOMAIN+"Login/forget";
   *//**
     * 创建家族群选择好友列表
     *//*
   public static final String URL_SELECTED_FRIENDS=API_DOMAIN+"User/friend";
   *//**
     * 家谱个人详情（不需传userid和imei）
     *//*
   public static final String URL_jiapu_userDetails=API_DOMAIN+"Jiapu/userDetails";
   *//**
     * 家谱头像预上传
     *//*
   public static final String URL_jiapu_upPhoto=API_DOMAIN+"Addnode/upPhoto";
   *//**
     * 家谱编辑资料
     *//*
   public static final String URL_save=API_DOMAIN+"Addnode/save";
   *//**
     * 创建家谱（添加自己）
     *//*
   public static final String URL_foundJiapu=API_DOMAIN+"Addnode/foundJiapu";
   *//**
     * 创建家族
     *//*
   public static final String URL_CREAT_FAMILY=API_DOMAIN+"Family/foundFamily";
   *//**
     * 搜索好友列表
     *//*
   public static final String URL_SERCH_FRIENDS=API_DOMAIN+"Seek/friendSeek";
   *//**
     * 群信息
     *//*
   public static final String URL_GROUP_INFO=API_DOMAIN+"Group/groupInfo";
   *//**
     * 修改群头像接口
     *//*
   public static final String URL_saveGroupPhoto=API_DOMAIN+"Group/saveGroupPhoto";
   *//**
     *我的导师，贵人表单提交
     *//*
   public static final String URL_mystrengthAdd=API_DOMAIN+"Mystrength/add";
   *//**
     *  *//**
     *我的导师，贵人表单编辑
     *//*
   public static final String URL_mystrengthEdit=API_DOMAIN+"Mystrength/edit";
   *//**
     *我的导师，贵人详情
     *//*
   public static final String URL_mystrengthDetails=API_DOMAIN+"Mystrength/userDetails";
   *//**
     *我的导师，贵人删除接口
     *//*
   public static final String URL_mystrengthDel=API_DOMAIN+"Mystrength/del";
   *//**
     *添加虚拟节点
     *//*
   public static final String URL_addVirtual=API_DOMAIN+"Addnode/addVirtual";
   *//**
     *修改群名称和群聚集地
     *//*
   public static final String URL_saveGroup=API_DOMAIN+"Group/saveGroup";
   *//**
     *搜索群成员
     *//*
   public static final String URL_groupSeek=API_DOMAIN+"Seek/groupSeek";
   *//**
     *纪念日总
     *//*
   public static final String URL_memorial_day=API_DOMAIN+"";
   *//**
     *纪念日上传背景
     *//*
   public static final String URL_memorial_bg=API_DOMAIN+"Souvenir/souvenirBg";
   *//**
     *创建纪念日
     *//*
   public static final String URL_creat_memorialday=API_DOMAIN+"Souvenir/foundSouvenir";
   *//**
     *获取分享链接
     *//*
   public static final String URL_creat_sharurl=API_DOMAIN+"share/getshare";
   *//**
     *邀请长辈
     *//*
   public static final String URL_addZhangbei=API_DOMAIN+"Addnode/addZhangbei";
   *//**
     *
     接口用途： 	邀请配偶
     *//*
   public static final String URL_addPeiou=API_DOMAIN+"Addnode/addPeiou";
   *//**
     *
     接口用途： 	邀请后代
     *//*
   public static final String URL_addHoudai=API_DOMAIN+"Addnode/addHoudai";
   *//**
     *
     接口用途： 邀请兄弟姐妹界面
     *//*
   public static final String URL_addBrothers=API_DOMAIN+"Addnode/addXdjm";
   *//**
     *
     接口用途： 邀请邀请群成员
     *//*
   public static final String URL_addGroupMember=API_DOMAIN+"Group/addGroupMember";
   *//**
     *
     纪念日列表
     *//*
   public static final String URL_souvenirList=API_DOMAIN+"Souvenir/souvenirList";
   *//**
     *
     编辑纪念日
     *//*
   public static final String URL_editext_memorial=API_DOMAIN+"Souvenir/saveSouvenir";
   *//**
     *
     删除纪念日
     *//*
   public static final String URL_delete_memorial=API_DOMAIN+"Souvenir/deleteSouvenir";
   *//**
     *
     好友列表
     *//*
   public static final String URL_friend_list=API_DOMAIN+"User/friend";
   *//**
     *
     我的资源
     *//*
   public static final String URL_myresources=API_DOMAIN+"myresources/refer_list";
   *//**
     *
     获取用户信息
     *//*
   public static final String URL_alluser=API_DOMAIN+"User/userAll";
   *//**
     *
     获取二维码
     *//*
   public static final String URL_qecode=API_DOMAIN+"User/user_qrcode";
   *//**
     *
     图片语音预上传
     *//*
   public static final String URL_PostPic=API_DOMAIN+"Discover/ahead";
   *//**
     *
     更新我的力量个人资料头像
     *//*
   public static final String URL_MystrengthUpPhoto=API_DOMAIN+"Mystrength/upPhoto";
   *//**
     *
     发布
     *//*
   public static final String URL_publish=API_DOMAIN+"Discover/publish";
   *//**
     *
     创建心得
     *//*
   public static final String URL_creat_feelings =API_DOMAIN+"Gnosis/foundGnosis";
   *//**
     *
     删除心得
     *//*
   public static final String URL_delete_feelings=API_DOMAIN+"Gnosis/deleteGnosis";
   *//**
     *
     编辑心得
     *//*
   public static final String URL_edit_feelings=API_DOMAIN+"Gnosis/svaeGnosis";
   *//**
     *
     寄语列表
     *//*
   public static final String URL_wishes=API_DOMAIN+"Sendword/sendwordList";
   *//**
     *
     创建寄语
     *//*
   public static final String URL_create_wishes=API_DOMAIN+"Sendword/foundSendword";

   *//**
     *
     寄语详情
     *//*
   public static final String URL_detail_wishes=API_DOMAIN+"Sendword/sendwordInfo";
   *//**
     *
     编辑寄语
     *//*
   public static final String URL_edite_wishes=API_DOMAIN+"Sendword/editSendword";
   *//**
     *
     删除寄语
     *//*
   public static final String URL_detect_wishes=API_DOMAIN+"Sendword/deleteSendword";

   *//**
     *
     发现列表
     *//*
   public static final String URL_publishlist=API_DOMAIN+"Discover/publishlist";

   *//**
     *
     点赞功能
     *//*
   public static final String URL_publishlike=API_DOMAIN+"Discover/publishlike";
   *//**
     *
     浏览次数的更新
     *//*
   public static final String URL_readsum=API_DOMAIN+"Discover/readsum";
   *//**
     *
     经历详情
     *//*
   public static final String URL_recodeinfo=API_DOMAIN+"Discover/recodeinfo";
   *//**
     *
     获取点赞人详情
     *//*
   public static final String URL_getPraise=API_DOMAIN+"Discover/getPraise";
   *//**
     *
     获取评论详情
     *//*
   public static final String URL_getCommlist=API_DOMAIN+"Discover/getCommlist";
   *//**
     *
     评论点赞接口
     *//*
   public static final String URL_comment_back=API_DOMAIN+"Discover/comment_back";
   *//**
     *
     评论接口
     *//*
   public static final String URL_comment=API_DOMAIN+"Discover/comment";
   *//**
     *
     意见反馈
     *//*
   public static final String URL_faceback=API_DOMAIN+"User/userOpinion";
   *//**
     *
     个人中心头像上传
     *//*
   public static final String URL_updat_head=API_DOMAIN+"User/upPhoto_user";
   *//**
     *
     个人经历接口
     *//*
   public static final String URL_life_experience=API_DOMAIN+"Discover/life_experience";
   *//**
     *
     删除我的经历
     *//*
   public static final String URL_delete_experience=API_DOMAIN+"Discover/delete_experience";
   *//**
     *
     人情账单列表
     *//*
   public static final String URL_person_bill=API_DOMAIN+"Humanbills/humanbillsList";
   *//**
     *
     人情账单编辑
     *//*
   public static final String URL_person_bill_edit=API_DOMAIN+"Humanbills/editHumanbills";
   *//**
     *
     人情账单删除
     *//*
   public static final String URL_person_bill_delete=API_DOMAIN+"Humanbills/deleteHumanbills";
   *//**
     *
     人情账单创建
     *//*
   public static final String URL_person_bill_creat=API_DOMAIN+"Humanbills/foundHumanbills";
   *//**
     *
     修改用户资料
     *//*
   public static final String URL_update_info=API_DOMAIN+"User/writeinfo";
   *//**
     *
     删除或退出群聊
     *//*
   public static final String URL_quitGroupt=API_DOMAIN+"Group/quitGroup";
   *//**
     *
     群列表
     *//*
   public static final String URL_group_list=API_DOMAIN+"Group/groupList";
   *//**
     *
     关于年轮
     *//*
   public static final String URL_about=API_DOMAIN+"User/share_apk";
   *//**
     *
     删除好友
     *//*
   public static final String URL_deleFriend=API_DOMAIN+"User/deleFriend";
   *//**
     *
     修改登录密码
     *//*
   public static final String URL_updata_login_pwd=API_DOMAIN+"User/uppassword";
   *//**
     *
     修改支付密码
     *//*
   public static final String URL_updata_pay_pwd=API_DOMAIN+"Redpacket/UserPayPaw";
   *//**
     *
     获取token
     *//*
   public static final String URL_get_token=API_DOMAIN+"User/rongToken";
   *//**
     *
     我的梦想列表
     *//*
   public static final String URL_dream_list=API_DOMAIN+"mydream/dreamlist";
   *//**
     *
     删除我的梦想
     *//*
   public static final String URL_dream_delete=API_DOMAIN+"mydream/delDream";
   *//**
     *
     编辑我的梦想
     *//*
   public static final String URL_dream_edite=API_DOMAIN+"mydream/editDream";
   *//**
     *
     创建我的梦想
     *//*
   public static final String URL_dream_creat=API_DOMAIN+"mydream/addDream";
   *//**
     *
     创建我的规划
     *//*
   public static final String URL_guihua_creat=API_DOMAIN+"Myproject/foundProject";
   *//**
     *
     我的规划列表
     *//*
   public static final String URL_guihua_list=API_DOMAIN+"Myproject/projectList";
   *//**
     *
     删除规划
     *//*
   public static final String URL_guihua_delete=API_DOMAIN+"Myproject/deleteProject";
   *//**
     *
     编辑规划
     *//*
   public static final String URL_guihua_edit=API_DOMAIN+"Myproject/editProject";
   *//**
     *
     邀请我的好友添加到我的力量
     *//*
   public static final String URL_Invitefriends=API_DOMAIN+"Mystrength/Invitefriends";
   *//**
     *
     邀请我的好友添加到我的力量
     *//*
   public static final String URL_lst=API_DOMAIN+"Mystrength/lst";
   *//**
     *
     *//*
   public static final String URL_redRecord=API_DOMAIN+"Redpacket/Record";
   *//**
     *
     购买记录
     *//*
   public static final String URL_GetRecordForTC=API_DOMAIN+"Redpacket/GetRecordForTC";
   *//**
     *
     查看其他人经历
     *//*
   public static final String URL_other_log=API_DOMAIN+"Discover/Personal_experience";
   *//**
     *查看个人信息

     *//*
   public static final String URL_myinfo=API_DOMAIN+"User/myinfo";
   *//**
     *个人
     *//*
   public static final String URL_persion=API_DOMAIN+"User/userinfo";

   *//**
     *
     钱包
     *//*
   public static final String URL_wallet=API_DOMAIN+"Wallet/GetBlance";
   *//**
     *
     年轮官方账号
     *//*
   public static final String URL_NianLun=API_DOMAIN+"User/official_list";
   *//**
     *
     删除评论
     *//*
   public static final String URL_delete_Commlist=API_DOMAIN+"Discover/delete_Commlist";

   *//**
     *
     添加好友
     *//*
   public static final String URL_addFriend=API_DOMAIN+"User/addFriend";
   *//**
     *
     搜索好友
     *//*
   public static final String URL_searchfriends=API_DOMAIN+"Seek/seek";
   *//**
     *
     新朋友请求列表
     *//*
   public static final String URL_newfriends=API_DOMAIN+"User/newFriends";
   *//**
     *
     同意或者拒绝
     *//*
   public static final String URL_aggressfriends=API_DOMAIN+"User/xyfriends";
   *//**
     *
     银行卡详细信息
     *//*
   public static final String URL_bank_detail=API_DOMAIN+"Wallet/GetMemberBankInfo";
   *//**
     *
     转账
     *//*
   public static final String URL_CodeToPay=API_DOMAIN+"Wallet/CodeToPay";
   *//**
     *
     实名认证
     *//*
   public static final String URL_real_name=API_DOMAIN+"user/realNameAuth";
   *//**
     *
     判断是否实名认证过
     *//*
   public static final String URL_if_real_name=API_DOMAIN+"user/is_realName";
   *//**
     *
     提现
     *//*
   public static final String URL_Withdraw=API_DOMAIN+"Wallet/Withdraw";
   *//**
     *
     根据userid获取uniqid
     *//*
   public static final String URL_get_uniqid=API_DOMAIN+"User/get_uniqid";
   *//**
     *
     根据卡号获取卡类型
     *//*
   public static final String URL_get_cardType=API_DOMAIN+"Bank/BacktrackBankName";

   *//**
     *
     获取交易记录
     *//*
   public static final String URL_totalRecords=API_DOMAIN+"TransactionRecords/GetAllBillSection";
   *//**
     *
     支付密码验证身份
     *//*
   public static final String URL_pwd_check=API_DOMAIN+"Wallet/checkPaypassword";
   *//**
     *
     绑银行卡
     *//*
   public static final String URL_add_bank=API_DOMAIN+"Wallet/BankVerify";
   *//**
     *
     绑银验证验证码
     *//*
   public static final String URL_add_bank_sure=API_DOMAIN+"Wallet/BankAffirm";
   *//**
     *
     绑银获取验证码
     *//*
   public static final String URL_get_bank_yzm=API_DOMAIN+"Wallet/sms";*/

}
