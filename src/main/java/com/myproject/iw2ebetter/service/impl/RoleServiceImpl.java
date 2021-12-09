package com.myproject.iw2ebetter.service.impl;

import com.myproject.iw2ebetter.mapper.RoleMapper;
import com.myproject.iw2ebetter.pojo.Role;
import com.myproject.iw2ebetter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRoleByrole(String role) {
        return roleMapper.getRoleByrole(role);
    }
}
