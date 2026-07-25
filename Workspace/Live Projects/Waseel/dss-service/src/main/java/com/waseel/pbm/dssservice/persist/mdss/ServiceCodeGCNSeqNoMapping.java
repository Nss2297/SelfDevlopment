package com.waseel.pbm.dssservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceCodeGCNSeqNoMapping", schema = "MDSS")
public class ServiceCodeGCNSeqNoMapping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ServiceCode")
	private String serviceCode;

	@Column(name = "GcnSeqNo")
	private Integer gcnSeqNo;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Integer getGcnSeqNo() {
		return gcnSeqNo;
	}

	public void setGcnSeqNo(Integer gcnSeqNo) {
		this.gcnSeqNo = gcnSeqNo;
	}
}
