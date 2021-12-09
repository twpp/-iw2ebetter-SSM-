package com.myproject.iw2ebetter.utils;

public class RedisKeyUtils {

    //保存点评被点赞的数据的key
    public static final String MAP_KEY_APPRAISE_LIKED = "MAP_APPRAISE_LIKED";
    //保存点评被点赞数量的key
    public static final String MAP_KEY_APPRAISE_LIKED_COUNT = "MAP_APPRAISE_LIKED_COUNT";


    /**
     * 拼接被点赞的点评id和点赞的人的id作为key。aid:uid
     *
     * @param aid 点评id
     * @param uid 用户id
     * @return
     */
    public static String getLikedKey(Integer aid, Integer uid) {
        StringBuilder builder = new StringBuilder();
        builder.append(aid);
        builder.append(":");
        builder.append(uid);
        return builder.toString();
    }

    /**
     * 解析出用户id和点评id
     * @param likedKey
     * @return
     */
    public static Integer[] getAidAndUid(String likedKey){
        String[] strings = likedKey.split(":");
        Integer[] aidAndUid = new Integer[2];
        aidAndUid[0] = Integer.parseInt(strings[0]);
        aidAndUid[1] = Integer.parseInt(strings[1]);
        return aidAndUid;
    }
}
