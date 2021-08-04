package com.bonc.colldata.entity;

import java.io.Serializable;
import java.util.List;

/**
 * (ZgGn)实体类
 *
 * @author ljx
 * @since 2021-08-04 17:22:44
 */
public class ZgGn implements Serializable {
    private static final long serialVersionUID = 950884020619830679L;
    
    private String id;
    
    private String zdlx;
    
    private String zdx;
    
    private String bm;
    
    private String fbm;
    
    private Object state;
    
    private String name;

    private List<ZgGn> child;

    public List<ZgGn> getChild() {
        return child;
    }

    public void setChild(List<ZgGn> child) {
        this.child = child;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZdlx() {
        return zdlx;
    }

    public void setZdlx(String zdlx) {
        this.zdlx = zdlx;
    }

    public String getZdx() {
        return zdx;
    }

    public void setZdx(String zdx) {
        this.zdx = zdx;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getFbm() {
        return fbm;
    }

    public void setFbm(String fbm) {
        this.fbm = fbm;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}