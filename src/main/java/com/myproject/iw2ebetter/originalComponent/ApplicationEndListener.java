package com.myproject.iw2ebetter.originalComponent;

import com.myproject.iw2ebetter.service.AppraiseLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class ApplicationEndListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private AppraiseLikeService appraiseLikeService;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        appraiseLikeService.transLikedCountFromRedis2DB();
        appraiseLikeService.transLikedDataFromRedis2DB();
    }
}
