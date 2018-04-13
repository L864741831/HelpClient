package com.tianjistar.help.app;

import android.os.Environment;

import java.io.File;

/**
 * Author:
 * Version    V1.0
 * Date:
 * Description: APP SharedPreferences KEY 常量工具类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 14-11-22                  1.0                    1.0
 * Why & What is modified:
 */
public class AppSpContact {
    public static final String FILE_DATA_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + "/Tjnla/";
    public static final String FILE_DATA_PICTURE_PATH = FILE_DATA_ROOT_PATH + "picture/";
    public static final String FILE_DATA_AUDIO_PATH = FILE_DATA_ROOT_PATH + "audio/";
    public static final String FILE_DATA_AVATAR_PATH = FILE_DATA_ROOT_PATH + "avatar/";
    public static final String ENCODE_TYPE = "UTF-8";
    public static final String HTTP_INPUT_TYPE = "application/json";

    public static final String USERINFO = "userInfo";/*保存用户信息*/
    public static final String SP_GROUPHEAD = "grouphead";//群主头像
    public static final String SP_GROUPNAME = "groupName";//群主名称
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


    /**
     * HTTP请求服务错误的状态码
     */
    public static final int HTTP_SERVER_ERROR_CODE = 500;
    /**
     * 编码格式
     */

    public final static File ROOT_FILE = new File(Environment.getExternalStorageDirectory(), "/tjkg");/*文件存储根目录*/



}
