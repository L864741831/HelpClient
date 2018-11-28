package com.tianjistar.help.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/26.
 */

public class RescueRecordBean implements Serializable{


    /**
     * id : 4700f24b1e694df4a4bfcd368d72fb63
     * isNewRecord : false
     * remarks :
     * createDate : 2018-04-25 14:34:58
     * updateDate : 2018-04-25 16:00:41
     * loginName : mm123
     * user : {"id":"ca4203a6d6aa4b30ab755df38aa4d725","isNewRecord":false,"name":"翟敏","loginFlag":"1","roleNames":"","admin":false}
     * place : 河南省郑州市金水区会展路
     * stateId : 4
     * rangeId : 2
     * userMobile :
     * office : {"id":"3f3a1e8419014af99f651c98770f271f","isNewRecord":false,"name":"河南郑州应急救援协会","sort":30,"type":"2","jingwei":"null,null","parentId":"0"}
     * gradeId : 3
     * confirmDate : 2018-04-25 14:35:38
     * jingwei : 34.780561,113.733971
     * uuid : 000000508262567
     * typeReport : 0
     * typeReturn : 0
     */

    private String id;
    private boolean isNewRecord;
    private String remarks;
    private String createDate;
    private String updateDate;
    private String loginName;
    private UserBean user;
    private String place;
    private String stateId;
    private String rangeId;
    private String userMobile;
    private OfficeBean office;
    private String gradeId;
    private String confirmDate;
    private String jingwei;
    private String uuid;
    private String typeReport;
    private String typeReturn;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getRangeId() {
        return rangeId;
    }

    public void setRangeId(String rangeId) {
        this.rangeId = rangeId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public OfficeBean getOffice() {
        return office;
    }

    public void setOffice(OfficeBean office) {
        this.office = office;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getJingwei() {
        return jingwei;
    }

    public void setJingwei(String jingwei) {
        this.jingwei = jingwei;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }

    public String getTypeReturn() {
        return typeReturn;
    }

    public void setTypeReturn(String typeReturn) {
        this.typeReturn = typeReturn;
    }

    public static class UserBean {
        /**
         * id : ca4203a6d6aa4b30ab755df38aa4d725
         * isNewRecord : false
         * name : 翟敏
         * loginFlag : 1
         * roleNames :
         * admin : false
         */

        private String id;
        private boolean isNewRecord;
        private String name;
        private String loginFlag;
        private String roleNames;
        private boolean admin;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLoginFlag() {
            return loginFlag;
        }

        public void setLoginFlag(String loginFlag) {
            this.loginFlag = loginFlag;
        }

        public String getRoleNames() {
            return roleNames;
        }

        public void setRoleNames(String roleNames) {
            this.roleNames = roleNames;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }
    }

    public static class OfficeBean {
        /**
         * id : 3f3a1e8419014af99f651c98770f271f
         * isNewRecord : false
         * name : 河南郑州应急救援协会
         * sort : 30
         * type : 2
         * jingwei : null,null
         * parentId : 0
         */

        private String id;
        private boolean isNewRecord;
        private String name;
        private int sort;
        private String type;
        private String jingwei;
        private String parentId;

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

        public String getJingwei() {
            return jingwei;
        }

        public void setJingwei(String jingwei) {
            this.jingwei = jingwei;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }
}
