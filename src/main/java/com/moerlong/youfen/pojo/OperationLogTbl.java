package com.moerlong.youfen.pojo;

import java.util.Date;

public class OperationLogTbl {
    private Long id;

    private String apiName;

    private String apiUri;

    private Date createTime;

    private Boolean isSucceed;

    private String message;

    private Double spendAmount;


    public Double getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(Double spendAmount) {
        this.spendAmount = spendAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }

    public String getApiUri() {
        return apiUri;
    }

    public void setApiUri(String apiUri) {
        this.apiUri = apiUri == null ? null : apiUri.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(Boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}