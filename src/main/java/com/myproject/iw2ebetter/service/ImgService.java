package com.myproject.iw2ebetter.service;

import com.myproject.iw2ebetter.pojo.Img;

import java.util.List;

public interface ImgService {

    Img getImgByIid(Integer Iid);

    List<Img> getImgsByAid(Integer aid);

    int addImg(Img img);

    int addImgs(List<Img> imgs);

    //int updateSrc()
}
