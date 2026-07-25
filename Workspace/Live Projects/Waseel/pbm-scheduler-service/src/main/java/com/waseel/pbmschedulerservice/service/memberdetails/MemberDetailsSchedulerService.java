package com.waseel.pbmschedulerservice.service.memberdetails;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsSchedulerService {

    @Scheduled(cron = "#{fetchSchedulerValueForMemberDetails}")
    @Retryable(backoff = @Backoff(delay = 500))
    public void runCronJobForMemberDetails() {

    }
}
