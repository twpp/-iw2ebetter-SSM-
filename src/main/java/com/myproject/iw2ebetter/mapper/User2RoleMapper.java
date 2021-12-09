package com.myproject.iw2ebetter.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface User2RoleMapper {

    @Insert("insert into user2role(uid,rid) values(#{uid},#{rid})")
    int insertUser2Role(Integer uid,Integer rid);
}
