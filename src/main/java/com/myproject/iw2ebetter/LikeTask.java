package com.myproject.iw2ebetter;

import com.myproject.iw2ebetter.service.AppraiseLikeService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 点赞数据持久化任务
 */
public class LikeTask extends QuartzJobBean {

    @Autowired
    private AppraiseLikeService appraiseLikeService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        appraiseLikeService.transLikedDataFromRedis2DB();
        appraiseLikeService.transLikedCountFromRedis2DB();
    }
}
