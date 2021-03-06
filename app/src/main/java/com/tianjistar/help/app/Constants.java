package com.tianjistar.help.app;

import android.os.Environment;

import java.io.File;

/**
 * APP常量
 */
public class Constants {
    public static final String FILE_DATA_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/TY_rescue/";

    public final static File ROOT_FILE = new File(Environment.getExternalStorageDirectory(), "/TY_rescue");/*文件存储根目录*/

    public static final String FILE_IMG_PATH = FILE_DATA_ROOT_PATH + "img";

    public static final String FILE_DATA_PICTURE_PATH = FILE_DATA_ROOT_PATH + "picture/";
    public static final String FILE_DATA_AUDIO_PATH = FILE_DATA_ROOT_PATH + "audio/";
    public static final String FILE_DATA_AVATAR_PATH = FILE_DATA_ROOT_PATH + "avatar/";

    public static final String ENCODE_TYPE = "UTF-8";
    public static final String HTTP_INPUT_TYPE = "application/json";


    public static final String SP_GROUPHEAD = "grouphead";//群主头像
    public static final String SP_GROUPNAME = "groupName";//群主名称SSS
    public static final String SP_GROUPID = "groupid";//群id
    public static final String SP_FIRST_LOGIN ="first_login";//是否是第一次登录
    public static final String SP_UID = "uid";//用户uid

    public static final String SP_DEVICEID = "deviceId";//设备id
    public static final String SP_USER_NAME = "username";//用户名
    public static final String SP_USER_PHOTO = "photo";//用户头像
    public static final String SP_USER_BACKGROUND = "background";//背景
    public static final String SP_USER_IS_OPEN= "isopen";//是否公开信息
    public static final String SP_BIRTHDAY = "birthday";//用户生日
    public static final String JUMP_LOGIN= "jump_login";//是否公开信息

    public static final String SP_FIRST_LAUCH ="first_launch";//是否是第一次启动
    public static final String LOGIN_SUCCESS ="login_success";//用户登录成功

    public static final String USER_INFO = "user_info";//用户信息

    public static final String USER_ID = "user_id";//用户id
    public static final String USER_AVATAR = "user_avatar";//用户头像
    public static final String USER_SEX= "user_sex";//用户性别
    public static final String USER_BLOOD = "user_blood";//血型
    public static final String USER_BIRTHDAY = "user_birthday";//生日
    public static final String USER_PHONE = "user_phone";//联系方式
    public static final String USER_NAME = "user_name";//用户昵称
    public static final String USER_UUID = "uuid";//uuid

    /**
     * HTTP请求服务错误的状态码
     */
    public static final int HTTP_SERVER_ERROR_CODE = 500;
    /**
     * 编码格式
     */


    public static final int MAX_FEELING_TITLE=15;//心得标题最大字数
    public static final int MAX_FEELING_CONTENT=9999;//心得内容最大字数

    public static final int MAX_WISHES_CONTENT=9999;//寄语内容最大字数
    public static final int MAX_WISHES_TITLE=15;//寄语标题最大字数

    public static final int MAX_MEMORIAL_CONTENT=999;//纪念日内容最大字数
    public static final int MAX_MEMORIAL_TITLE=15;//纪念日标题最大字数

    public static final int MAX_EXPERINSWE_CONTENT=999;//纪念日内容最大字数

    public static final int MAX_FAMILY_JIA_XUN=450;//家训
    public static final int MAX_FAMILY_CONTENT=999;//家族信息

    public static final int MAX_BILL_CONTENT=450;//人情账单最大字数
    public static final int MAX_BILLL_TITLE=15;//人情账单最大字数


    public static final String SERVER_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwRJT8MwFTfB+qOngFjxJ" +
            "WtqfECknF5lT/XtxPG9dZBkNdzF3T+7rSKgbtyE1pSIFiq3KRw4WZ9rjtYbhpA5g" +
            "074hCpbuvqbnCh1cS2pFPxdLaHs/oXaJH8Y5cqYBWsvPBXea6L/wODVPMXxYlA9C" +
            "eNyO0iPO6p9uIuObSOQrTb5hiEyNwBP+j4Xx1XvyLx+XsmtcSrUCrynmZENvV8ZF" +
            "2w8aKJDb73pMssVOMwqYYbAFSWJlvWMzCI7jXt0vHhM7S/Q6Hoq4qf2oMnurDpK9" +
            "aoA5XjnMfHZmn/QyGO5v2FyZcrKhMzACzOkEvXWQlbR/q9jM1IuG+SpGmKS9buko" +
            "wQIDAQAB" ;//服务器公钥

    //客户端私钥
    public static final String CLIEND_PRIVATE_KEY="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCP1jrDQEG5YXmQ1XPEE9u2r22D4s3ihJ/6Xuc/U7zo3N4+JP373uK0AHtjxvX02dKEoFb77jINMa1KHKD0zdrQTeuSdbtv4MCvFzFeDYUz3NJZRf4F5kMxSPf3AAwzO7rC8MoiTBfxDBNtZAGJuHspfp2+Wv/L50XqJ83WtarhgVIZII6CtfML1ujs0ant+0n0qD1e9mk/IJRn+esxW52DDGIa8Dv2GfwrvbmRtm2j/VDfnKI65LVEuLyIQb4BXj1w8s5TTbTaiWP7pW+noawbMnh2ZNbdSTDHUFO4is0yFhZIhKFXTZeSRo6G+GwyR+FiR3kzN7nin1UYsDRWXCULAgMBAAECggEALGkd7alKYb2p9kEhpFnpTeeubbk6joUajAh/Z/sn6dJAQHnVfc4cuy0m6/DDgl069b43/UNXauF6utZKECv7MrPMV4VIYUvI0obbtEmCZjf5Lp1AyHuHJZF9FmOKYMtBZ+1Gy/4oqkFrQixNmUM/q9kvRVX9guyelqTBllizMTrwO62Hj8VMQHs+tJiHDfmMwp8ARyNnB6eQVdk+pLYhdXcjb06Fsusq1ezdZycr5+aigmqFzPcjkQ/n320I8r7fIk2EcgGaD7hs3auxDke0/UxCBHjm7AbXqwrnQGn4g7X6+AmoR23HPW7RfHwJ1XHIeRjC9uBZX7BXyY/qWVhGWQKBgQDZgIBezdM+t1r1GPaZnVS1VzaGRPZJm2Q3aCZHj3iGm2bkpQizu9gcOjTAi8zX1lnzXTnqbLIFn07THNy0BY/LDogGAQev3BHaAkqelPsoR1Tv2fI55n+AK++zSJND7829s++rIhmmg5Niw5RyqTS5Xg8J3F8Jub1CIN+vw2dUtwKBgQCpS8wO5dkaDLTxxHTBt/D5StI/DRmjpJ0nDHu+eqU8IVd4cet3dX4cZzJE16bftiaBAiN1YTUYhfTcw5NjiFo+52Zbk5EHSt/7JtrGn4nx3nOP3R9WROmznpItpc7p4Zt+pegDio+RHp1StoE7uJ+ZltI/zND3txm4qfY+zG+mTQKBgHjxRXByDxb+qetEGEvO+n1jo4QB8Nl5OL6+gRqloqVm4E4fnrkiqMb5br/qfNs/7ACxRDUgf9U0goZNtEO+G/kTeXutMvMM8jhBsfCAczAwigBdNp4Pl7JTvBP7NTg/ri3gUlLcwVqgK2Jt0qv3fsPck4wuKAKmb6DH2tpHU7v9AoGAS9HlftqTw6XjN8/7VnLx2kQXKyPL0s07kKx3kxRMgkSQbyHeCe813rSc/9XxJ4nlbqutg7lYVTZRNrnkfZQojrmFzwek0TZIX6vEGWaEGi29ZbXrffB5FfezkfwLUhW3VR4qbcOVjrG+Vbfk4wFJgTjZyrcI85guTdTYssFOU5UCgYApCdlczk1K7ZOoV5MGiIV3HifrP3eCnvyzfyvzzGpn1S8RqkA8yJJ7NN/uukAW3wrKmMMu2xcdl23aTUyPUrlUMqf/zIqFKzEf6iVerV9Gh0Ddz5QP+YSq6/QWw/JfATuJx+an0nG1gI719+AXKURZcRIyLtC8ZuTBlN0ixkZzvw==";//客户端私钥

    public static final String UNIQID ="uuiqid";//天机唯一标识 //加密
    public static final String TARGETID = "targetId";//個人聊天id  明文
    public static final String TARGETID_group = "targetId_group";//群聊天id  明文
    public static final String SP_USERNAME = "username";//本地username  加密


}
