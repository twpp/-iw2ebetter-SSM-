package com.myproject.iw2ebetter.service;

import com.github.pagehelper.PageInfo;
import com.myproject.iw2ebetter.pojo.AppraiseLike;

import java.util.List;

public interface AppraiseLikeService {

    /**
     * 保存点赞记录
     * @param appraiseLike
     * @return
     */
    AppraiseLike save(AppraiseLike appraiseLike);

    /**
     * 更新点赞状态
     * @param appraiseLike
     * @return
     */
    AppraiseLike updateStatus(AppraiseLike appraiseLike);

    /**
     * 批量保存或修改
     * @param appraiseLikes
     * @return
     */
    List<AppraiseLike> saveAll(List<AppraiseLike> appraiseLikes);

    /**
     * 通过点评id获取点赞了这个点评的列表
     * @param aid
     * @param pageNo 起始页
     * @param pageSize 页大小
     * @return
     */
    PageInfo<AppraiseLike> getLikedPageInfoByAid(Integer aid,Integer pageNo,Integer pageSize);

    /**
     * 通过用户id获取其点过赞的列表
     * @param uid
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<AppraiseLike> getLikedPageInfoByUid(Integer uid,Integer pageNo,Integer pageSize);

    /**
     * 通过用户id 和 点评id来查询点赞记录是否存在
     * @param aid
     * @param uid
     * @return
     */
    AppraiseLike getByAidAndUid(Integer aid,Integer uid);

    /**
     * 将redis中的点赞数据转存到数据库
     */
    void transLikedDataFromRedis2DB();

    /**
     * 将redis中的点赞数量转存数据库
     */
    void transLikedCountFromRedis2DB();

    /**
     * 通过用户获取点赞列表
     * @param uid 用户id
     * @return List<AppraiseLike>
     */
    List<AppraiseLike> getLikedListByUid(Integer uid);

    /**
     * 通过点评id获取点赞列表
     * @param aid 点评id
     * @return List<AppraiseLike>
     */
    List<AppraiseLike> getLikedListByAid(Integer aid);



}
