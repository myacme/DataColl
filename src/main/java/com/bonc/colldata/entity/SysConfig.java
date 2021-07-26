package com.bonc.colldata.entity;

import java.io.Serializable;

/**
 * (SysConfig)实体类
 *
 * @author ljx
 * @since 2021-07-26 17:04:44
 */
public class SysConfig implements Serializable {
    private static final long serialVersionUID = 515820317527882783L;
    
    private String configCode;
    
    private String configValue;


    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

}