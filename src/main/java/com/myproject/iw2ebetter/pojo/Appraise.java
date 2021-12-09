package com.myproject.iw2ebetter.pojo;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Appraise implements Serializable {

    private Integer aid;
    private String title;
    private String content;
    private Date createTime;
    private Boolean available;
    private Integer responses;
    private UserDTO user;
    private Integer likes;
    //通过imgmap来查然后注入
    private List<Img> imgs;


    public Integer getResponses() {
        return responses;
    }

    public void setResponses(Integer responses) {
        this.responses = responses;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Boolean getAvailable() {
        return available;
    }


    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Img> getImgs() {
        return imgs;
    }


    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public void setImgs(List<Img> imgs) {
        this.imgs = imgs;
    }
}
