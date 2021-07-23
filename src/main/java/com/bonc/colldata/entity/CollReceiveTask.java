package com.bonc.colldata.entity;

import java.io.Serializable;
import java.util.List;

/**
 * (CollReceiveTask)实体类
 *
 * @author makejava
 * @since 2021-07-12 16:17:30
 */
public class CollReceiveTask implements Serializable {
    private static final long serialVersionUID = -16781547997230351L;
    
    private String sendTaskCode;
    
    private String sendTaskName;
    
    private String sendTaskVersion;
    
    private String sendTaskCollType;
    
    private String sendTaskDataAgo;
    
    private String sendTaskCollDepartment;

    private String sendIfTemp;

    private String createTime;
    
    private String state;

    public String getSendIfTemp() {
        return sendIfTemp;
    }

    public void setSendIfTemp(String sendIfTemp) {
        this.sendIfTemp = sendIfTemp;
    }

    private List<CollReceiveTaskTable> list;

    public List<CollReceiveTaskTable> getList() {
        return list;
    }

    public void setList(List<CollReceiveTaskTable> list) {
        this.list = list;
    }

    public String getSendTaskCode() {
        return sendTaskCode;
    }

    public void setSendTaskCode(String sendTaskCode) {
        this.sendTaskCode = sendTaskCode;
    }

    public String getSendTaskName() {
        return sendTaskName;
    }

    public void setSendTaskName(String sendTaskName) {
        this.sendTaskName = sendTaskName;
    }

    public String getSendTaskVersion() {
        return sendTaskVersion;
    }

    public void setSendTaskVersion(String sendTaskVersion) {
        this.sendTaskVersion = sendTaskVersion;
    }

    public String getSendTaskCollType() {
        return sendTaskCollType;
    }

    public void setSendTaskCollType(String sendTaskCollType) {
        this.sendTaskCollType = sendTaskCollType;
    }

    public String getSendTaskDataAgo() {
        return sendTaskDataAgo;
    }

    public void setSendTaskDataAgo(String sendTaskDataAgo) {
        this.sendTaskDataAgo = sendTaskDataAgo;
    }

    public String getSendTaskCollDepartment() {
        return sendTaskCollDepartment;
    }

    public void setSendTaskCollDepartment(String sendTaskCollDepartment) {
        this.sendTaskCollDepartment = sendTaskCollDepartment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}