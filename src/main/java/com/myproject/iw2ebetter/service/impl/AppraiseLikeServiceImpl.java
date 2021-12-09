package com.myproject.iw2ebetter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.mapper.AppraiseLikeMapper;
import com.myproject.iw2ebetter.mapper.AppraiseMapper;
import com.myproject.iw2ebetter.pojo.Appraise;
import com.myproject.iw2ebetter.pojo.AppraiseLike;
import com.myproject.iw2ebetter.pojo.LikeCountDTO;
import com.myproject.iw2ebetter.service.AppraiseLikeService;
import com.myproject.iw2ebetter.service.AppraiseService;
import com.myproject.iw2ebetter.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppraiseLikeServiceImpl implements AppraiseLikeService {

    @Autowired
    private AppraiseLikeMapper appraiseLikeMapper;

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Autowired
    private RedisService redisService;



    @Override
    public AppraiseLike save(AppraiseLike appraiseLike) {
        return appraiseLikeMapper.save(appraiseLike);
    }

    @Override
    public AppraiseLike updateStatus(AppraiseLike appraiseLike) {
        return appraiseLikeMapper.update(appraiseLike);
    }

    @Override
    public List<AppraiseLike> saveAll(List<AppraiseLike> appraiseLikes) {
        return appraiseLikeMapper.saveAll(appraiseLikes);
    }

    @Override
    public PageInfo<AppraiseLike> getLikedPageInfoByAid(Integer aid, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<AppraiseLike> appraiseLikeList = getLikedListByUid(aid);
        PageInfo<AppraiseLike> pageInfo = new PageInfo<>(appraiseLikeList,5);
        return pageInfo;
    }

    @Override
    public PageInfo<AppraiseLike> getLikedPageInfoByUid(Integer uid, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<AppraiseLike> appraiseLikeList = getLikedListByUid(uid);
        PageInfo<AppraiseLike> pageInfo = new PageInfo<>(appraiseLikeList,5);
        return pageInfo;
    }

    @Override
    public AppraiseLike getByAidAndUid(Integer aid, Integer uid) {
        return appraiseLikeMapper.findByAidAndUid(aid,uid);
    }

    @Override
    public void transLikedDataFromRedis2DB() {
        List<AppraiseLike> likes = redisService.getLikedDataFromRedis();
        for(AppraiseLike like : likes){
            AppraiseLike al = getByAidAndUid(like.getAid(),like.getUid());
            if(like == null){
                //没有记录 直接存入
                save(like);
            }else{
                //存在需要更新
                al.setStatus(like.getStatus());
                updateStatus(al);
            }
        }
    }

    @Override
    public void transLikedCountFromRedis2DB() {
        List<LikeCountDTO> likeCountDTOs = redisService.getLikedCountFromRedis();
        for (LikeCountDTO likeCountDTO : likeCountDTOs){
            Appraise appraise = appraiseMapper.getAppraiseByAid(likeCountDTO.getAid());
            if(appraise != null){
                //更新点赞数量
                Integer likeNum = appraise.getLikes() + likeCountDTO.getCount();
                appraise.setLikes(likeNum);
                appraiseMapper.updateAppraiseLikeCount(appraise);
            }
        }
    }

    @Override
    public List<AppraiseLike> getLikedListByUid(Integer uid) {
        return appraiseLikeMapper.findByUidAndStatus(uid,AppraiseLike.LIKE);
    }

    @Override
    public List<AppraiseLike> getLikedListByAid(Integer aid) {
        return appraiseLikeMapper.findByUidAndStatus(aid,AppraiseLike.LIKE);
    }
}
