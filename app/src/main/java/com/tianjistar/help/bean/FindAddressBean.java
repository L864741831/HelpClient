package com.tianjistar.help.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/2.
 */

public class FindAddressBean implements Serializable {


    /**
     * isNewRecord : true
     * name : 河南省金水区救援会
     * sort : 30
     * type : 2
     * address : 河南省郑州市金水区
     * phone : 110119118
     * dimensions : 34.848995
     * longitude : 113.745432
     * parentId : 0
     */

    private boolean isNewRecord;
    private String name;
    private int sort;
    private String type;
    private String address;
    private String phone;
    private String dimensions;
    private String longitude;
    private String parentId;

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
