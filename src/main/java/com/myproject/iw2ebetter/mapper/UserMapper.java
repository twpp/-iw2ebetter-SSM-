package com.myproject.iw2ebetter.mapper;


import com.myproject.iw2ebetter.pojo.UserDTO;
import com.myproject.iw2ebetter.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper{

    //简单sql这边查

    int insertUser(User user);

    @Update("update users set password = #{newPwd} where username = #{username}")
    int updatePwd(String newPwd,String username);

    @Update("update users set telephone = #{user.telephone},address = #{user.address},name = #{user.name} where uid = #{uid}")
    int updateInfoByUid(User user,Integer uid);

    @Update("update users set telephone = #{user.telephone},address = #{user.address},name = #{user.name} where username = #{username}")
    int updateInfoByUsername(User user,String username);

    UserDTO getUserByUsername(@Param("username") String username);

    UserDTO getUserByUid(Integer uid);

    @Select("select uid,name,telephone,address from users")
    List<User> getUsers();

    @Select("select uid,name,telephone,address from users where username != #{username}")
    List<User> getUsersExceptSelf(@Param("username") String username);

    @Update("delete from users where uid = #{uid}")
    int deleteUserByUid(Integer uid);

    @Update("update users set src = #{src} where uid = #{uid}")
    int updateSrcByUid(Integer uid,String src);
}
