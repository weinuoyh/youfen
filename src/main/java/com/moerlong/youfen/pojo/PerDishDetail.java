package com.moerlong.youfen.pojo;

import java.util.Date;

public class PerDishDetail {
    private Long id;

    private String dishId;

    private String caseTime;

    private String gender;

    private String perfInfo;

    private String perfBasisNo;

    private String relatedParty;

    private String judgCoutName;

    private String idCard;

    private String execCoutName;

    private String concSitu;

    private String obligation;

    private Integer age;

    private String execName;

    private String province;

    private String caseNum;

    private String publishTime;

    private String dataType;

    private Integer oprationUser;

    private Date createTime;

    private Date lastModifyTime;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId == null ? null : dishId.trim();
    }

    public String getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(String caseTime) {
        this.caseTime = caseTime == null ? null : caseTime.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getPerfInfo() {
        return perfInfo;
    }

    public void setPerfInfo(String perfInfo) {
        this.perfInfo = perfInfo == null ? null : perfInfo.trim();
    }

    public String getPerfBasisNo() {
        return perfBasisNo;
    }

    public void setPerfBasisNo(String perfBasisNo) {
        this.perfBasisNo = perfBasisNo == null ? null : perfBasisNo.trim();
    }

    public String getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(String relatedParty) {
        this.relatedParty = relatedParty == null ? null : relatedParty.trim();
    }

    public String getJudgCoutName() {
        return judgCoutName;
    }

    public void setJudgCoutName(String judgCoutName) {
        this.judgCoutName = judgCoutName == null ? null : judgCoutName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getExecCoutName() {
        return execCoutName;
    }

    public void setExecCoutName(String execCoutName) {
        this.execCoutName = execCoutName == null ? null : execCoutName.trim();
    }

    public String getConcSitu() {
        return concSitu;
    }

    public void setConcSitu(String concSitu) {
        this.concSitu = concSitu == null ? null : concSitu.trim();
    }

    public String getObligation() {
        return obligation;
    }

    public void setObligation(String obligation) {
        this.obligation = obligation == null ? null : obligation.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getExecName() {
        return execName;
    }

    public void setExecName(String execName) {
        this.execName = execName == null ? null : execName.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum == null ? null : caseNum.trim();
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public Integer getOprationUser() {
        return oprationUser;
    }

    public void setOprationUser(Integer oprationUser) {
        this.oprationUser = oprationUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}