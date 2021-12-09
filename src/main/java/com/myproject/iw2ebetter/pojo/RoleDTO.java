package com.myproject.iw2ebetter.pojo;

import com.myproject.iw2ebetter.pojo.Role;
import com.myproject.iw2ebetter.pojo.User;

import java.util.Set;

public class RoleDTO extends Role {

    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
