package com.myproject.iw2ebetter.service;

import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.pojo.Response;

import java.util.List;

public interface ResponseService {

    List<Response> getAllResponsesByEnable(boolean enable);

    List<Response> getAllResponses();

    List<Response> getAllResponsesByUid(Integer uid);

    //分页的
    PageInfo<Response> getAllResponsesByEnable(Integer pageNo, Integer pageSize);

    //分页的
    PageInfo<Response> getAllResponses(Integer pageNo,Integer pageSize);

    PageInfo<Response> getAllResponsesByUid(Integer pageNo, Integer pageSize, Integer uid);

    Response getResponseByReid(Integer reid);

    int acceptResponse(Integer reid);

    int deleteResponseByReid(Integer reid);

    List<Response> getAllResponsesByAid(Integer aid);

    int addResponse(Response response);
}
