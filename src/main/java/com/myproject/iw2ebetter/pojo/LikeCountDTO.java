package com.myproject.iw2ebetter.pojo;


/**
 * 存储点评appraise对应的赞的类
 *
 */
public class LikeCountDTO {

    private Integer aid;
    private Integer count;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LikeCountDTO() {
    }

    public LikeCountDTO(Integer aid, Integer count) {
        this.aid = aid;
        this.count = count;
    }
}
