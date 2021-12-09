package com.myproject.iw2ebetter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.mapper.AppraiseMapper;
import com.myproject.iw2ebetter.pojo.Appraise;
import com.myproject.iw2ebetter.pojo.AppraiseLike;
import com.myproject.iw2ebetter.service.AppraiseLikeService;
import com.myproject.iw2ebetter.service.AppraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "appraiseCache")
public class AppraiseServiceImpl implements AppraiseService {

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Autowired
    private AppraiseLikeService appraiseLikeService;

    @Override
    public List<Appraise> getAllAppraisesByEnable(boolean enable) {
        return appraiseMapper.getAllAppraisesByEnable(enable);
    }

    @Override
    public List<Appraise> getAllAppraises() {
        return appraiseMapper.getAllAppraises();
    }

    @Override
    public PageInfo<Appraise> getAllAppraisesByEnable(Integer pageNo, Integer pageSize) {
        //启动分页
        PageHelper.startPage(pageNo,pageSize);
        //这一句话 会自动分页
        List<Appraise> appraises = getAllAppraisesByEnable(false);
        //包装处理分页结果
        PageInfo<Appraise> pageInfo = new PageInfo<>(appraises,5);
        return pageInfo;
    }

    @Override
    public PageInfo<Appraise> getAllAppraises(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Appraise> appraises = getAllAppraisesByEnable(true);
        PageInfo<Appraise> pageInfo = new PageInfo<>(appraises,5);
        return pageInfo;
    }

    @Cacheable(key = "#aid")
    @Override
    public Appraise getAppraiseByAid(Integer aid) {
        Appraise appraise = appraiseMapper.getAppraiseByAid(aid);
        List<AppraiseLike> likes = appraiseLikeService.getLikedListByAid(aid);
        appraise.setLikes(likes.size());
        return appraise;
    }

    @CacheEvict(key = "#aid")
    @Override
    public int acceptAppraise(Integer aid) {
        return appraiseMapper.updateAppraiseAvailable(aid);
    }

    @Override
    public List<Appraise> getAppraisesByUid(Integer uid) {
        List<Appraise> appraises = appraiseMapper.getAppraisesByUid(uid);
        for(Appraise appraise : appraises){
            List<AppraiseLike> likes = appraiseLikeService.getLikedListByUid(uid);
            appraise.setLikes(likes.size());
        }
        return appraises;
    }

    @Override
    public PageInfo<Appraise> getAllAppraisesByUid(Integer pageNo, Integer pageSize,Integer uid) {
        PageHelper.startPage(pageNo,pageSize);
        List<Appraise> appraises = getAppraisesByUid(uid);
        PageInfo<Appraise> pageInfo = new PageInfo<>(appraises);
        return pageInfo;
    }

    @CacheEvict(key = "#aid")
    @Override
    public int deleteAppraiseByAid(Integer aid) {
        return appraiseMapper.deleteAppraiseByAid(aid);
    }


    @Override
    public int addAppraise(Appraise appraise) {
        return appraiseMapper.insertAppraise(appraise);
    }

    @Override
    public int updateLikeCount(Appraise appraise) {
        return appraiseMapper.updateAppraiseLikeCount(appraise);
    }
}
