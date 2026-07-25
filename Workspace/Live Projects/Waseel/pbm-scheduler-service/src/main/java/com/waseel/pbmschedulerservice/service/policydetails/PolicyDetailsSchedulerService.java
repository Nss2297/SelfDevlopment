package com.waseel.pbmschedulerservice.service.policydetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PolicyDetailsSchedulerService {

	@Autowired
	private PolicyDetailsService policyDetailsService;

	@Scheduled(cron = "#{fetchSchedulerValueForPolicyDetails}")
	@Retryable(backoff = @Backoff(delay = 500))
	public void runCronJobForPolicyDetails() {
		//policyDetailsService.policyDetailsImplementation();
	}
}
