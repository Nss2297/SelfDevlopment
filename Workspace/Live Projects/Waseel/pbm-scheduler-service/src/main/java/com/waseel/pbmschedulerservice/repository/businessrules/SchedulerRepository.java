package com.waseel.pbmschedulerservice.repository.businessrules;

import com.waseel.pbmschedulerservice.persist.businessrules.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {
}

