package com.waseel.pbmschedulerservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.waseel.pbmschedulerservice.persist.businessrules.Scheduler;
import com.waseel.pbmschedulerservice.repository.businessrules.SchedulerRepository;

@Service
public class SchedulerService {

	@Autowired
	SchedulerRepository schedulerRepository;

	private static final Map<String, String> CRON_INTERVAL_MAP = new HashMap<>();

    private void fetchSchedulerDataFromDB() {
        List<Scheduler> schedulerList = schedulerRepository.findAll();
        schedulerList.forEach(scheduler -> {
            String interval = scheduler.getInterval();
            if (!StringUtils.isBlank(interval)
                    && interval.split(" ").length == 6) {
                CRON_INTERVAL_MAP.put(scheduler.getProcessType(), interval);
            }
        });
    }

    private String fetchSchedulerValue(String processType) {
        if (CRON_INTERVAL_MAP.isEmpty()) {
            fetchSchedulerDataFromDB();
        }
        return CRON_INTERVAL_MAP.getOrDefault(processType, "-");
    }

    @Bean
    private String fetchSchedulerValueForPolicyDetails() {
        return fetchSchedulerValue("policy-details");
    }

    @Bean
    private String fetchSchedulerValueForMemberDetails() {
        return fetchSchedulerValue("member-details");
    }
}
