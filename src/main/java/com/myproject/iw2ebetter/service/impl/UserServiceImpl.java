package com.myproject.iw2ebetter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.pojo.UserDTO;
import com.myproject.iw2ebetter.mapper.RoleMapper;
import com.myproject.iw2ebetter.mapper.User2RoleMapper;
import com.myproject.iw2ebetter.mapper.UserMapper;
import com.myproject.iw2ebetter.pojo.Role;
import com.myproject.iw2ebetter.pojo.User;
import com.myproject.iw2ebetter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "userCache")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private User2RoleMapper user2RoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int registerUser(UserDTO user) {
        int userResult;
        int user2roleResult = 0;
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        userResult = userMapper.insertUser(user);
        int uid = user.getUid();

        Set<Role> roles = user.getRoles();
        if (roles == null || roles.size() == 0) {
            //对于普通用户 注册就是使用默认的角色
            Role defaultRole = roleMapper.getRoleByrole("user");
            user2roleResult += user2RoleMapper.insertUser2Role(uid, defaultRole.getRid());
        } else {
            for (Role role : roles) {
                user2roleResult += user2RoleMapper.insertUser2Role(uid, role.getRid());
            }
        }
        return userResult * 7 + user2roleResult;
    }

    @CachePut(key = "#username")
    @Override
    public UserDTO getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public int updatePwd(String newPwd) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return userMapper.updatePwd(passwordEncoder.encode(newPwd), username);
    }

    @CacheEvict(key = "#uid")
    @Override
    public int updateInfoByUid(UserDTO user, Integer uid) {
        return userMapper.updateInfoByUid(user, uid);
    }

    @CacheEvict(key = "#username")
    public int updateInfoByUsername(UserDTO user, String username) {
        return userMapper.updateInfoByUsername(user, username);
    }

    @Cacheable(key = "#uid")
    @Override
    public UserDTO getUserByUid(Integer uid) {
        return userMapper.getUserByUid(uid);
    }


    @Override
    public boolean checkPwd(String oldPwd) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        UserDTO user = userMapper.getUserByUsername(username);
        return passwordEncoder.matches(oldPwd, user.getPassword());
    }

    @Override
    public PageInfo<User> getUsers(int pageNo, int pageSize) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        PageHelper.startPage(pageNo, pageSize);
        List<User> users = userMapper.getUsersExceptSelf(username);
        PageInfo<User> pageInfo = new PageInfo<>(users,5);
        return pageInfo;
    }

    @Override
    public int resetPwd(Integer uid) {
        UserDTO user = userMapper.getUserByUid(uid);
        String username = user.getUsername();
        return userMapper.updatePwd(passwordEncoder.encode("123456"),username);
    }

    @Cacheable(key = "#username",unless = "#result != null")
    @Override
    public UserDTO getUserWithoutPasswordByUsername(String username) {
        UserDTO user = getUserByUsername(username);
        user.setPassword(null);
        return user;
    }


    @Cacheable(key = "#uid", unless = "#result != null")
    @Override
    public UserDTO getUserWithoutPasswordByUid(Integer uid) {
        UserDTO user = getUserByUid(uid);
        user.setPassword(null);
        return user;
    }

    @Override
    public UserDTO getUserWithoutThatHost() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        UserDTO user = getUserWithoutPasswordByUsername(username);
        return user;
    }

    @CacheEvict(key = "#uid")
    @Override
    public int uploadImage(Integer uid,String perfixImage) {
        int status = userMapper.updateSrcByUid(uid, perfixImage);
        return status;
    }
}
