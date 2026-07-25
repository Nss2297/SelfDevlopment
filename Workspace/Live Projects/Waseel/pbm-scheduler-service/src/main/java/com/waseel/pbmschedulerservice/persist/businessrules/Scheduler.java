package com.waseel.pbmschedulerservice.persist.businessrules;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "SCHEDULER", schema = "PBM_BUSINESS_RULES")
public class Scheduler implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SCHEDULER_ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulerId;

    @Column(name = "PROCESS_TYPE", length = 30)
    private String processType;

    @Column(name = "INTERVAL", length = 50)
    private String interval;

    public Long getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(Long schedulerId) {
        this.schedulerId = schedulerId;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
