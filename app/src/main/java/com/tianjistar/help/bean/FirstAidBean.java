package com.tianjistar.help.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/27.
 */

public class FirstAidBean implements Serializable{


    /**
     * id : 3
     * isNewRecord : false
     * remarks :
     * createDate : 2018-04-26 02:03:17
     * updateDate :
     * title : 系统=公告s
     * titleimg : /us
     * summary : 系统=公告系统=公告
     * clickhit : 1015
     * replyhit :
     * content :
     * keyword :
     * typeId : 2
     * rangeId : 2
     * createName :
     * h5 : mobile/#/user/home/article/3
     */

    private String id;
    private boolean isNewRecord;
    private String remarks;
    private String createDate;
    private String updateDate;
    private String title;
    private String titleimg;
    private String summary;
    private String clickhit;
    private String replyhit;
    private String content;
    private String keyword;
    private String typeId;
    private String rangeId;
    private String createName;
    private String h5;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleimg() {
        return titleimg;
    }

    public void setTitleimg(String titleimg) {
        this.titleimg = titleimg;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getClickhit() {
        return clickhit;
    }

    public void setClickhit(String clickhit) {
        this.clickhit = clickhit;
    }

    public String getReplyhit() {
        return replyhit;
    }

    public void setReplyhit(String replyhit) {
        this.replyhit = replyhit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getRangeId() {
        return rangeId;
    }

    public void setRangeId(String rangeId) {
        this.rangeId = rangeId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getH5() {
        return h5;
    }

    public void setH5(String h5) {
        this.h5 = h5;
    }
}
