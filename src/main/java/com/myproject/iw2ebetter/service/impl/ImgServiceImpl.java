package com.myproject.iw2ebetter.service.impl;

import com.myproject.iw2ebetter.mapper.ImgMapper;
import com.myproject.iw2ebetter.pojo.Img;
import com.myproject.iw2ebetter.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "imgCache")
@Service
public class ImgServiceImpl implements ImgService {

    @Autowired
    private ImgMapper imgMapper;

    @Cacheable(key = "#iid")
    @Override
    public Img getImgByIid(Integer iid) {
        return imgMapper.getImgByIid(iid);
    }

    //@Cacheable(key = "#aid",unless = "#result != null")
    @Override
    public List<Img> getImgsByAid(Integer aid) {
        return imgMapper.getImgsByAid(aid);
    }

    @CachePut(key = "#img.iid")
    @Override
    public int addImg(Img img) {
        int status = imgMapper.insertImg(img);
        return status;
    }

    @Override
    public int addImgs(List<Img> imgs) {
        int res = 0;
        for(Img img : imgs){
            res += addImg(img);
        }
        return res;
    }
}
