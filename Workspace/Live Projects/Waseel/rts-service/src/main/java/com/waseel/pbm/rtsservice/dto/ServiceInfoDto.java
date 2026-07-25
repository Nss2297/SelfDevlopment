package com.waseel.pbm.rtsservice.dto;

import java.sql.Timestamp;

public interface ServiceInfoDto {
	public  String getRequestId();
    public String  getServiceCode();
    public Timestamp getServiceDate();
    public Integer getDaysOfSupply();
}
