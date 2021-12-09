package com.myproject.iw2ebetter.mapper;

import com.myproject.iw2ebetter.pojo.Response;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ResponseMapper {

    List<Response> getAllResponsesByEnable(boolean enable);

    List<Response> getAllResponses();

    Response getResponseByReid(Integer reid);

    @Delete("delete from responses where reid = #{reid}")
    int deleteResponseByReid(Integer reid);

    int updateResponseContentAndTimeByReid(String content, Date modifyTime,Integer reid);

    @Update("update responses set available = true where reid = #{reid}")
    int updateResponseAvailable(Integer reid);

    int insertResponse(Response response);

    List<Response> getAllResponsesByUid(Integer uid);

    List<Response> getResponsesByAid(Integer aid);
}
