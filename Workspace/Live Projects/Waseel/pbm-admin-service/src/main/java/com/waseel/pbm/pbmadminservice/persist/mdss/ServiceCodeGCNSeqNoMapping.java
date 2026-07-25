package com.waseel.pbm.pbmadminservice.persist.mdss;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceCodeGCNSeqNoMapping", schema = "MDSS")
public class ServiceCodeGCNSeqNoMapping implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ServiceCode")
	private String serviceCode;
	@Column(name = "GcnSeqNo")
	private Integer gcnSeqNo;
	@Column(name = "ProductPackageUnit")
	private String productPackageUnit;
	@Column(name = "ProductPackageSize")
	private Integer productPackageSize;
	@Column(name = "IsDeleted")
	private Character isDeleted = '0';
	@Column(name = "LastUpdatedDateTime")
	private Timestamp lastUpdatedDateTime = Timestamp.from(Instant.now());
	@Column(name = "Id")
	private Long id;

	public String getProductPackageUnit() {
		return productPackageUnit;
	}

	public void setProductPackageUnit(String productPackageUnit) {
		this.productPackageUnit = productPackageUnit;
	}

	public Integer getProductPackageSize() {
		return productPackageSize;
	}

	public void setProductPackageSize(Integer productPackageSize) {
		this.productPackageSize = productPackageSize;
	}

	public Character getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Character isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceCodeGCNSeqNoMapping() {
	}

	public ServiceCodeGCNSeqNoMapping(String serviceCode, Integer gcnSeqNo) {
		this.serviceCode = serviceCode;
		this.gcnSeqNo = gcnSeqNo;
	}
}
