package com.myproject.iw2ebetter.pojo;

import com.myproject.iw2ebetter.pojo.Role;
import com.myproject.iw2ebetter.pojo.User;

import java.io.Serializable;
import java.util.Set;

public class UserDTO extends User implements Serializable {
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
