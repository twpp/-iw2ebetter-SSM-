package com.myproject.iw2ebetter.service.impl;

import com.myproject.iw2ebetter.pojo.AppraiseLike;
import com.myproject.iw2ebetter.pojo.LikeCountDTO;
import com.myproject.iw2ebetter.service.AppraiseLikeService;
import com.myproject.iw2ebetter.service.RedisService;
import com.myproject.iw2ebetter.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void saveLiked2Redis(Integer aid, Integer uid) {
        String key = RedisKeyUtils.getLikedKey(aid, uid);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED, key, AppraiseLike.LIKE);
    }

    @Override
    public void unlikeFromRedis(Integer aid, Integer uid) {
        String key = RedisKeyUtils.getLikedKey(aid, uid);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED, key, AppraiseLike.UNLIKE);
    }

    @Override
    public void deleteLikedFromRedis(Integer aid, Integer uid) {
        String key = RedisKeyUtils.getLikedKey(aid, uid);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED, key);
    }

    @Override
    public void incrementLikedCount(Integer aid) {
        String _aid = aid.toString();
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED_COUNT, _aid, 1);
    }

    @Override
    public void decrementLikedCount(Integer aid) {
        String _aid = aid.toString();
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED_COUNT, _aid, -1);
    }

    @Override
    public List<AppraiseLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED, ScanOptions.NONE);
        List<AppraiseLike> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Integer[] aidAndUid = RedisKeyUtils.getAidAndUid(key);

            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
            AppraiseLike appraiseLike = new AppraiseLike(aidAndUid[0], aidAndUid[1], value);
            list.add(appraiseLike);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED, key);
        }
        return list;
    }

    @Override
    public List<LikeCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED_COUNT, ScanOptions.NONE);
        List<LikeCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();

            //存储点赞数量
            Integer _aid = Integer.parseInt((String)map.getKey());
            //Integer aid = Integer.parseInt(_aid);
            LikeCountDTO dto = new LikeCountDTO(_aid, (Integer) map.getValue());
            list.add(dto);

            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED_COUNT, _aid);
        }
        return list;

    }

    @Override
    public AppraiseLike getAppraiseLikeByAidAndUid(Integer aid, Integer uid) {
        String key = RedisKeyUtils.getLikedKey(aid, uid);
        Integer value = (Integer) redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED, key);
        AppraiseLike al;
        if (value != null) {
            //记录存在
            al = new AppraiseLike(aid, uid, value);
        } else {
            al = null;
        }
        return al;
    }

    @Override
    public LikeCountDTO getLikeCountDTOByAid(Integer aid) {
        Integer count = (Integer) redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED_COUNT, aid.toString());
        LikeCountDTO lcdto;
        if (count != null) {
            lcdto = new LikeCountDTO();
            lcdto.setAid(aid);
            lcdto.setCount(count);
        } else {
            lcdto = null;
        }
        return lcdto;
    }

    @Override
    public void saveLikeCount2Redis(Integer aid, Integer likes) {
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED_COUNT, aid, likes);
    }

    @Override
    public void saveAllLikeCount2Redis(List<Integer> aids, List<Integer> likes) {
        for (int i = 0; i < aids.size();i++) {
            redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_APPRAISE_LIKED_COUNT, aids.get(i), likes.get(i));
        }
    }
}
