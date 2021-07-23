package com.bonc.colldata.entity;

import java.io.Serializable;

/**
 * (CollBusinessTableConfig)实体类
 *
 * @author ljx
 * @since 2021-07-14 10:15:24
 */
public class CollBusinessTableConfig implements Serializable {
    private static final long serialVersionUID = 843490871322106448L;
    /**
    * 主键
    */
    private String tableConfigCode;
    /**
    * 表名称
    */
    private String tableConfigTableCode;
    /**
    * 表字段名称
    */
    private String tableConfigName;
    /**
    * 表字段类型
    */
    private String tableConfigType;
    /**
    * 表字段长度
    */
    private String tableConfigSize;
    /**
    * 表字段精度
    */
    private String tableConfigPrecision;
    /**
    * 表字段刻度
    */
    private String tableConfigCalibration;
    /**
    * 是否为空
    */
    private String tableConfigIfnull;
    /**
    * 注释
    */
    private String remarks;
    /**
     * 是否有效
     */
    private String state;
    /**
     * 代码
     */
    private String tableConfigNameCode;


    public String getTableConfigCode() {
        return tableConfigCode;
    }

    public void setTableConfigCode(String tableConfigCode) {
        this.tableConfigCode = tableConfigCode;
    }

    public String getTableConfigTableCode() {
        return tableConfigTableCode;
    }

    public void setTableConfigTableCode(String tableConfigTableCode) {
        this.tableConfigTableCode = tableConfigTableCode;
    }

    public String getTableConfigName() {
        return tableConfigName;
    }

    public void setTableConfigName(String tableConfigName) {
        this.tableConfigName = tableConfigName;
    }

    public String getTableConfigType() {
        return tableConfigType;
    }

    public void setTableConfigType(String tableConfigType) {
        this.tableConfigType = tableConfigType;
    }

    public String getTableConfigSize() {
        return tableConfigSize;
    }

    public void setTableConfigSize(String tableConfigSize) {
        this.tableConfigSize = tableConfigSize;
    }

    public String getTableConfigPrecision() {
        return tableConfigPrecision;
    }

    public void setTableConfigPrecision(String tableConfigPrecision) {
        this.tableConfigPrecision = tableConfigPrecision;
    }

    public String getTableConfigCalibration() {
        return tableConfigCalibration;
    }

    public void setTableConfigCalibration(String tableConfigCalibration) {
        this.tableConfigCalibration = tableConfigCalibration;
    }

    public String getTableConfigIfnull() {
        return tableConfigIfnull;
    }

    public void setTableConfigIfnull(String tableConfigIfnull) {
        this.tableConfigIfnull = tableConfigIfnull;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTableConfigNameCode() {
        return tableConfigNameCode;
    }

    public void setTableConfigNameCode(String tableConfigNameCode) {
        this.tableConfigNameCode = tableConfigNameCode;
    }
}