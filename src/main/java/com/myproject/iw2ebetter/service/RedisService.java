package com.myproject.iw2ebetter.service;

import com.myproject.iw2ebetter.pojo.AppraiseLike;
import com.myproject.iw2ebetter.pojo.Img;
import com.myproject.iw2ebetter.pojo.LikeCountDTO;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

/**
 * 管理点评点赞需求 主要与redis打交道
 */
public interface RedisService {

    /**
     * 点赞 默认状态为1 （点赞）
     * @param aid 点评id
     * @param uid 用户id
     */
    void saveLiked2Redis(Integer aid,Integer uid);


    /**
     * 取消点赞 设置状态为 0
     * @param aid 点评id
     * @param uid 用户id
     */
    void unlikeFromRedis(Integer aid,Integer uid);

    /**
     * 删除一个点赞信息 比如一个用户被删除了 或者一个 点评被删除了 就会涉及到这部分
     * @param aid 点评id
     * @param uid 用户id
     */
    void deleteLikedFromRedis(Integer aid,Integer uid);

    /**
     * aid点评赞数 加一
     * @param aid 点评id
     */
    void incrementLikedCount(Integer aid);


    /**
     * aid点评赞数 减一
     * @param aid 点评id
     */
    void decrementLikedCount(Integer aid);

    /**
     * 返回redis中存储的所有点赞数据
     * @return List<AppraiseLike>
     */
    List<AppraiseLike> getLikedDataFromRedis();


    /**
     * 返回redis存储的点赞的数量
     * @return List<LikeCountDTO>
     */
    List<LikeCountDTO> getLikedCountFromRedis();

    /**
     * 获取redis中的指定指定记录
     * @param aid 被点赞点评id
     * @param uid 点赞点评的用户id
     * @return AppraiseLike
     */
    AppraiseLike getAppraiseLikeByAidAndUid(Integer aid,Integer uid);

    /**
     * 获取
     * @param aid 点评id
     * @return LikeCountDTO
     */
    LikeCountDTO getLikeCountDTOByAid(Integer aid);

    /**
     * 存储点评点赞数到redis
     * @param aid
     * @param likes
     */
    void saveLikeCount2Redis(Integer aid, Integer likes);

    /**
     * 批量存放到redis
     * @param aid
     * @param likes
     */
    void saveAllLikeCount2Redis(List<Integer> aid, List<Integer> likes);
}
