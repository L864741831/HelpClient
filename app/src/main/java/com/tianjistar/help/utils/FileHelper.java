package com.tianjistar.help.utils;

import com.tianjistar.help.app.Constants;

import java.io.File;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      15/8/15 21:47
 * Description: 文件操作帮住工具类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 15/8/15      ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class FileHelper {

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true:存在 false:不存在
     */
    public static boolean isFileExists(String filePath) {
        return StringHelper.notEmpty(filePath) && new File(filePath).exists();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return true:成功 false:失败
     */
    public static boolean deleteFile(String filePath) {
        if (StringHelper.notEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
        }

        return false;
    }

    public static String checkAndMkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    public static String getAppPath() {
        return checkAndMkdirs(Constants.FILE_DATA_ROOT_PATH);
    }

    public static String getAvatarDir() {
        return checkAndMkdirs(Constants.FILE_DATA_AVATAR_PATH);
    }

    public static String getPicturePath() {
        return checkAndMkdirs(Constants.FILE_DATA_PICTURE_PATH);
    }
    public static String getAudioPath() {
        return checkAndMkdirs(Constants.FILE_DATA_AUDIO_PATH);
    }
}
