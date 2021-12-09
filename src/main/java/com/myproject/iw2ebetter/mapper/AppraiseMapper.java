package com.myproject.iw2ebetter.mapper;

import com.myproject.iw2ebetter.pojo.Appraise;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppraiseMapper {


    List<Appraise> getAllAppraisesByEnable(boolean enable);

    List<Appraise> getAllAppraises();

    Appraise getAppraiseByAid(Integer aid);

    int insertAppraise(Appraise appraise);

    @Delete("delete from appraises where aid = #{aid}")
    int deleteAppraiseByAid(Integer aid);

    int updateAppraise(Appraise appraise);

    @Update("update appraises set available = true where aid = #{aid}")
    int updateAppraiseAvailable(Integer aid);

    List<Appraise> getAppraisesByUid(Integer uid);

    int updateAppraiseLikeCount(Appraise appraise);
}
