package com.myproject.iw2ebetter.service;

import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.pojo.UserDTO;
import com.myproject.iw2ebetter.pojo.User;



public interface UserService {

    int registerUser(UserDTO user);

    UserDTO getUserByUsername(String username);

    UserDTO getUserWithoutPasswordByUsername(String username);

    int updatePwd(String newPwd);

    int updateInfoByUid(UserDTO user ,Integer uid);

    int updateInfoByUsername(UserDTO user ,String username);

    UserDTO getUserByUid(Integer uid);

    UserDTO getUserWithoutPasswordByUid(Integer uid);

    boolean checkPwd(String oldPwd);

    PageInfo<User> getUsers(int pageNo, int pageSize);

    int resetPwd(Integer uid);

    UserDTO getUserWithoutThatHost();

    int uploadImage(Integer uid,String perfixImage);
}
