package com.bonc.colldata.entity;

import java.io.Serializable;

/**
 * (CollTableConfigDataSource)实体类
 *
 * @author ljx
 * @since 2021-08-01 10:02:44
 */
public class CollTableConfigDataSource implements Serializable {
    private static final long serialVersionUID = 267299300439713605L;
    
    private String dataSourceCode;
    
    private String tableConfigCode;
    
    private String dataSourceTable;
    
    private String dataSourceField;
    
    private String isKey;
    
    private String isEdit;


    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getTableConfigCode() {
        return tableConfigCode;
    }

    public void setTableConfigCode(String tableConfigCode) {
        this.tableConfigCode = tableConfigCode;
    }

    public String getDataSourceTable() {
        return dataSourceTable;
    }

    public void setDataSourceTable(String dataSourceTable) {
        this.dataSourceTable = dataSourceTable;
    }

    public String getDataSourceField() {
        return dataSourceField;
    }

    public void setDataSourceField(String dataSourceField) {
        this.dataSourceField = dataSourceField;
    }

    public String getIsKey() {
        return isKey;
    }

    public void setIsKey(String isKey) {
        this.isKey = isKey;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

}