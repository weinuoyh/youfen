package com.moerlong.youfen.pojo;

import java.util.Date;

public class MobileAttrInfo {
    private Long id;

    private String mobile;

    private String mobileIsp;

    private String mobileCardType;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String areaCode;

    private String areaName;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getMobileIsp() {
        return mobileIsp;
    }

    public void setMobileIsp(String mobileIsp) {
        this.mobileIsp = mobileIsp == null ? null : mobileIsp.trim();
    }

    public String getMobileCardType() {
        return mobileCardType;
    }

    public void setMobileCardType(String mobileCardType) {
        this.mobileCardType = mobileCardType == null ? null : mobileCardType.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
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