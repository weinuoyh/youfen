package com.moerlong.youfen.constant.state;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 返回前端结果状态类
 *
 * @Author: shaxueting
 * @Date: 2018/9/17 15:04
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    /*是否成功*/
    private boolean success;

    /*额外信息*/

    private String message;

    /*数据主体*/
    private T data;

    private String cost;

    public static <T> Result<T> success(T data, String cost) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setMessage("获取数据成功");
        result.setData(data);
        result.setCost(cost);
        return result;
    }
    public static <T> Result<T> result(T data, String cost) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setMessage("没有获取有效数据");
        result.setData(data);
        result.setCost(cost);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setMessage("获取数据成功");
        result.setData(data);
        return result;
    }
    public static <T> Result<T> result(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setMessage("没有获取有效数据");
        result.setData(data);
        return result;
    }


    public static Result fail(String message) {
        Result result = new Result();
        result.setSuccess(false);

        result.setMessage(message);
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
