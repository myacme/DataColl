package com.bonc.base;


import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 通用 Restful 返回值封装类
 *
 * @author Leucippus
 * @version 0.1 * @since 2018/5/14 14:54
 */
@ApiModel
public class RestRecord implements Serializable {
    @ApiModelProperty(example = "200", notes = "状态码", position = 1)
    private int code = 0;
    @ApiModelProperty(example = "操作成功", notes = "提示信息", position = 2)
    private String msg;
    @ApiModelProperty(example = "{\"username\": \"RGM79\"}", notes = "数据", position = 3)
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public RestRecord() {
    }

    public RestRecord(String msg, Object data) {
        this.code = 200;
        this.msg = msg;
        this.data = data;
    }

    public RestRecord(int code, String msg, Object data, Exception exception) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RestRecord(int code) {
        this.code = code;
    }

    public RestRecord(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public RestRecord(boolean isSuccess) {
        this.code = isSuccess ? 200 : 500;
    }

    public RestRecord(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestRecord(boolean isSuccess, String msg) {
        this.code = isSuccess ? 200 : 500;
        this.msg = msg;
    }

    public RestRecord(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RestRecord(boolean isSuccess, String msg, Object data) {
        this.code = isSuccess ? 200 : 500;
        this.msg = msg;
        this.data = data;
    }


    public RestRecord(int code, String msg, Exception exception) {
        this.code = code;
        this.msg = msg;
    }

    public RestRecord(boolean isSuccess, String msg, Exception exception) {
        this.code = isSuccess ? 200 : 500;
        this.msg = msg;
    }

    public static RestRecord success(Object data) {
        return new RestRecord(200, MessageConstants.MSG_0200, data);
    }

    public static RestRecord success() {
        return new RestRecord(200, MessageConstants.MSG_0200);
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

    public static void main(String[] args) {
        Exception exception = new Exception();
        RestRecord restRecord = new RestRecord(20, "", "", exception);
        System.out.println(restRecord);
    }
}