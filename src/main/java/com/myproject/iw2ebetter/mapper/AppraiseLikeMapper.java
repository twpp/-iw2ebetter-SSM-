package com.myproject.iw2ebetter.mapper;

import com.myproject.iw2ebetter.pojo.AppraiseLike;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppraiseLikeMapper {
    /**
     * 保存用户点赞点评实体
     * @param appraiseLike 用户点赞点评实体
     * @return
     */
    AppraiseLike save(AppraiseLike appraiseLike);

    /**
     * 更新点赞状态
     * @param appraiseLike
     * @return
     */
    @Update("update appraise_like set status = #{status} where aid = #{aid} and uid = #{uid}")
    AppraiseLike update(AppraiseLike appraiseLike);

    /**
     * 批量保存用户点赞点评实体
     * @param appraiseLikes 用户点赞点评实体列表
     * @return
     */
    List<AppraiseLike> saveAll(@Param("list") List<AppraiseLike> appraiseLikes);

    /**
     * 通过点评id以及点赞状态获取用户点赞点评实体集
     * @param aid 点评id
     * @param status 点赞状态码
     * @return
     */
    List<AppraiseLike> findByAidAndStatus(Integer aid, Integer status);

    /**
     * 获取用户点过赞的点赞点评实体集
     * @param uid 用户id
     * @param status 点评状态码
     * @return
     */
    List<AppraiseLike> findByUidAndStatus(Integer uid, Integer status);

    AppraiseLike findByAidAndUid(Integer aid, Integer uid);
}
