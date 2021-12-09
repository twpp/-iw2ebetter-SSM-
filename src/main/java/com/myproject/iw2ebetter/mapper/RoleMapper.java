package com.myproject.iw2ebetter.mapper;

import com.myproject.iw2ebetter.pojo.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper {

    @Insert("insert into roles(role) values(#{role})")
    int insertRole(String role);

    @Select("select rid,role from roles where role = #{role}")
    Role getRoleByrole(String role);

}
