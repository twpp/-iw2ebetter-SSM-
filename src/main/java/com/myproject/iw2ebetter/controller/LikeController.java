package com.myproject.iw2ebetter.controller;

import com.myproject.iw2ebetter.pojo.*;
import com.myproject.iw2ebetter.service.AppraiseLikeService;
import com.myproject.iw2ebetter.service.RedisService;
import com.myproject.iw2ebetter.service.UserService;
import com.myproject.iw2ebetter.utils.RedisKeyUtils;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.server.UID;
import java.util.List;

@Controller
public class LikeController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppraiseLikeService appraiseLikeService;

    @Autowired
    private UserService userService;

    /**
     * 业务逻辑
     * 判断redis中是否存在 1、点赞实体记录（AppraiseLike）
     * 2、点评点赞总数（appraiseLike_count）
     * 如果存在：就根据 状态更新 点赞记录 以及 点赞总数
     * 如果不存在：获取数据库中点赞记录、以及点评记录数（从点评表中拿取）
     * 如果数据库中点赞记录不存在，就新建一个点赞记录存储到redis 并更新redis中点赞数
     * <p>
     * 如果存在 更新点赞记录 存储到redis
     *
     * @param aid
     * @param status
     * @return
     */
    @ResponseBody
    @GetMapping("/likeOrUnlikeAppraise")
    public ResponseData likeOrUnlikeAppraise(@RequestParam("likes")Integer likes,@RequestParam("aid") Integer aid, Integer status) {
        UserDTO host = userService.getUserWithoutThatHost();
        Integer uid = host.getUid();
        AppraiseLike alRedis = redisService.getAppraiseLikeByAidAndUid(aid, uid);
        LikeCountDTO lcdto = redisService.getLikeCountDTOByAid(aid);

        if(lcdto == null){
            redisService.saveLikeCount2Redis(aid,likes);
        }

        if (alRedis == null) {
            AppraiseLike alDB = appraiseLikeService.getByAidAndUid(aid, uid);
            if (alDB != null) {
                redisService.saveLiked2Redis(aid, uid);
                redisService.incrementLikedCount(aid);
            } else {
                redisService.unlikeFromRedis(aid, uid);
                redisService.decrementLikedCount(aid);
            }
        } else {
            if (status == 1) {
                redisService.unlikeFromRedis(aid, uid);
                redisService.decrementLikedCount(aid);
            } else {
                redisService.saveLiked2Redis(aid, uid);
                redisService.incrementLikedCount(aid);
            }
        }

        lcdto = redisService.getLikeCountDTOByAid(aid);


        return ResponseData.success("点赞成功").addData("likes",lcdto.getCount());
    }
}
