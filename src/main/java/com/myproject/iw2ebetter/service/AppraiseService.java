package com.myproject.iw2ebetter.service;


import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.pojo.Appraise;
import com.myproject.iw2ebetter.pojo.Img;

import java.util.List;

public interface AppraiseService {

    List<Appraise> getAllAppraisesByEnable(boolean enable);

    List<Appraise> getAllAppraises();

    List<Appraise> getAppraisesByUid(Integer uid);

    //分页的
    PageInfo<Appraise> getAllAppraisesByEnable(Integer pageNo, Integer pageSize);

    //分页的
    PageInfo<Appraise> getAllAppraises(Integer pageNo,Integer pageSize);

    PageInfo<Appraise> getAllAppraisesByUid(Integer pageNo, Integer pageSize, Integer uid);

    Appraise getAppraiseByAid(Integer aid);

    int acceptAppraise(Integer aid);

    int deleteAppraiseByAid(Integer aid);

    int addAppraise(Appraise appraise);

    int updateLikeCount(Appraise appraise);
}
