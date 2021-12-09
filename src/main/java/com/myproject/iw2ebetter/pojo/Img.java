package com.myproject.iw2ebetter.pojo;

import java.io.Serializable;

/**
 * 图片类：意图映射各图片的位置
 * 包含头像、点评图片
 */
public class Img implements Serializable {

    public final static String BASE_APPRAISE = "/images/appraise/";
    public final static String BASE_HEADIMAGE = "/images/headImage/";
    private Integer iid;
    private String src;
    private Integer aid;

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }
}
