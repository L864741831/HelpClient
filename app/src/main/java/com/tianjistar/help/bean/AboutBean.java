package com.tianjistar.help.bean;

/**
 * Created by Administrator on 2017/12/11.
 */

public class AboutBean {

    /**
     * apk_url : http://www.nl.com/Uploads/nlApk/app-debug1.apk
     * apk_edition : 1.0
     * info : 已有新版本请升级
     */

    private String apk_url;
    private String apk_edition;
    private String info;
    private boolean is_compel;

    private String apkEdition;
    private String apkUrl;

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getApkEdition() {
        return apkEdition;
    }

    public void setApkEdition(String apkEdition) {
        this.apkEdition = apkEdition;
    }

    public boolean is_compel() {
        return is_compel;
    }

    public void setIs_compel(boolean is_compel) {
        this.is_compel = is_compel;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getApk_edition() {
        return apk_edition;
    }

    public void setApk_edition(String apk_edition) {
        this.apk_edition = apk_edition;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
