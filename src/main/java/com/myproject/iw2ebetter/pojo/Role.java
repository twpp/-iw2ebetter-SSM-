package com.myproject.iw2ebetter.pojo;

import java.io.Serializable;

public class Role implements Serializable {
    private Integer rid;
    private String role;

    public Role() {
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
