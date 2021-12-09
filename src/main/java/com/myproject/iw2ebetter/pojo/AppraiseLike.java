package com.myproject.iw2ebetter.pojo;

/**
 * 点评-用户点赞类
 */
public class AppraiseLike {

    public final static Integer LIKE = 1;
    public final static Integer UNLIKE = 0;

    private Integer id;

    //被点赞的点评id
    private Integer aid;

    //点赞的用户id
    private Integer uid;

    //点赞状态 默认未点赞
    private Integer status = LIKE;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public AppraiseLike() {
    }

    public AppraiseLike(Integer aid, Integer uid, Integer status) {
        this.aid = aid;
        this.uid = uid;
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }

        AppraiseLike appraiseLike = (AppraiseLike)obj;
        return appraiseLike.getAid() == this.aid && appraiseLike.getUid() == this.uid;
    }
}
