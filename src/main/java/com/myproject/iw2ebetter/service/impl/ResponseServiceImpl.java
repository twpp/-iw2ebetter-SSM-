package com.myproject.iw2ebetter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.mapper.ResponseMapper;
import com.myproject.iw2ebetter.pojo.Response;
import com.myproject.iw2ebetter.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "responseCache")
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    private ResponseMapper responseMapper;

    public List<Response> getAllResponsesByEnable(boolean enable) {
        return responseMapper.getAllResponsesByEnable(enable);
    }

    public List<Response> getAllResponses() {
        return responseMapper.getAllResponses();
    }

    public PageInfo<Response> getAllResponsesByEnable(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Response> responses = getAllResponsesByEnable(false);
        PageInfo<Response> pageInfo = new PageInfo<>(responses,5);
        return pageInfo;
    }

    public PageInfo<Response> getAllResponses(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Response> responses = getAllResponsesByEnable(true);
        PageInfo<Response> pageInfo = new PageInfo<>(responses,5);
        return pageInfo;
    }

    @Cacheable(key = "#reid")
    @Override
    public Response getResponseByReid(Integer reid) {
        return responseMapper.getResponseByReid(reid);
    }


    @CacheEvict(key = "#reid")
    @Override
    public int acceptResponse(Integer reid) {
        return responseMapper.updateResponseAvailable(reid);
    }

    @Override
    public List<Response> getAllResponsesByUid(Integer uid) {
        return responseMapper.getAllResponsesByUid(uid);
    }


    @Override
    public PageInfo<Response> getAllResponsesByUid(Integer pageNo, Integer pageSize, Integer uid) {
        PageHelper.startPage(pageNo,pageSize);
        List<Response> responses = getAllResponsesByUid(uid);
        PageInfo<Response> pageInfo = new PageInfo<>(responses);
        return pageInfo;
    }

    @CacheEvict(key = "#reid")
    @Override
    public int deleteResponseByReid(Integer reid) {
        return responseMapper.deleteResponseByReid(reid);
    }

    @Override
    public List<Response> getAllResponsesByAid(Integer aid) {
        return responseMapper.getResponsesByAid(aid);
    }


    @Override
    public int addResponse(Response response) {
        return responseMapper.insertResponse(response);
    }
}
