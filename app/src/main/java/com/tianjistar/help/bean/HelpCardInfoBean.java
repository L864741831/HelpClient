package com.tianjistar.help.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class HelpCardInfoBean {


    /**
     * id : a915c6833c4b4924a187177c208205c7
     * isNewRecord : false
     * remarks : [{"content":"可以用来道路、火灾、户外、生命救援、道路救援包 括道路中发生的一切意外；火灾救援包括：； 户外包 括：；生命救援包括：。","title":"适用于那些救援类型？"},{"content":"天佑救援卡覆盖区域为中国大陆境内，不含港澳台地 区。","title":" 使用地区？"},{"content":"自购买救援卡日期起的一年的时间。","title":" 救援卡使用时间？"}]
     * createDate :
     * updateDate :
     * uuid : 000001101236170
     * type : 1
     * name : 救援卡
     * price : 990.00
     * tyimg : /userfiles/1/images/orders/tyMember/2018/06/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20180606154506.png
     * content : [{"title":"适用于那些救援类型？","content":"可以用来道路、火灾、户外、生命救援、道路救援包 括道路中发生的一切意外；火灾救援包括：； 户外包 括：；生命救援包括：。"},{"title":" 使用地区？","content":"天佑救援卡覆盖区域为中国大陆境内，不含港澳台地 区。"},{"title":" 救援卡使用时间？","content":"自购买救援卡日期起的一年的时间。"}]
     */

    private String id;
    private boolean isNewRecord;
    private String remarks;
    private String createDate;
    private String updateDate;
    private String uuid;
    private String type;
    private String name;
    private String price;
    private String tyimg;
    private List<ContentBean> content;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTyimg() {
        return tyimg;
    }

    public void setTyimg(String tyimg) {
        this.tyimg = tyimg;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * title : 适用于那些救援类型？
         * content : 可以用来道路、火灾、户外、生命救援、道路救援包 括道路中发生的一切意外；火灾救援包括：； 户外包 括：；生命救援包括：。
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
