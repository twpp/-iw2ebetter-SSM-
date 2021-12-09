package com.myproject.iw2ebetter.mapper;

import com.myproject.iw2ebetter.pojo.Img;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgMapper {

    @Insert("insert into imgs(src,aid) values(#{src},#{aid})")
    int insertImg(Img img);

    @Delete("delete from imgs where iid = #{iid}")
    int deleteImgByIid(Integer iid);

    @Update("update imgs set src = #{src} where iid = #{iid}")
    int updateSrcByIid(String src,Integer iid);

    @Select("select iid,aid,src from imgs where iid = #{iid}")
    Img getImgByIid(Integer iid);

    @Select("select iid,aid,src from imgs where aid = #{aid}")
    List<Img> getImgsByAid(Integer aid);


}
