package org.devices.specifications.api.service.config;

import org.devices.specifications.api.service.services.FeedJobService;
import org.devices.specifications.api.service.services.impl.SQLFeedJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FeedJobConfig {

    @Value("${enable.feed.load}")
    private boolean isFeedLoadEnabled;

    @Autowired
    private SQLFeedJobService feedJobService;

    @PostConstruct
    public void startFeedJob() {
        if(isFeedLoadEnabled) {
            feedJobService.startFeedJob();
        }
    }
}
