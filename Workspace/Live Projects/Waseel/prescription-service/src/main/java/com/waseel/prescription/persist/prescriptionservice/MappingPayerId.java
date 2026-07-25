package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PAYER_ID_MAPPING", schema = "PRESCRIPTION_SERVICE")
public class MappingPayerId implements Serializable {

	private static final long serialVersionUID = -4583135120059435821L;

	@Id
	@GeneratedValue(generator = "PayerIdMappingSeq")
	@SequenceGenerator(name = "PayerIdMappingSeq", sequenceName = "PAYER_ID_MAPPING_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "MAPPING_ID", unique = true, nullable = false, precision = 0)
	private Long mappingId;

	@Column(name = "TRANSACTION_TYPE", nullable = false, length = 30)
	private String transactionType;

	@Column(name = "PAYER_ID", nullable = false, length = 30)
	private String payerId;

	@Column(name = "MAPPED_PAYER_ID", nullable = false, length = 30)
	private String mapperPayerId;

	@Column(name = "IS_ENABLED", nullable = false)
	private Boolean isEnabled;

	public Long getMappingId() {
		return mappingId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getMapperPayerId() {
		return mapperPayerId;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setMapperPayerId(String mapperPayerId) {
		this.mapperPayerId = mapperPayerId;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public MappingPayerId() {
		super();
	}

	public MappingPayerId(String transactionType, String payerId, String mapperPayerId, Boolean isEnabled) {
		super();
		this.transactionType = transactionType;
		this.payerId = payerId;
		this.mapperPayerId = mapperPayerId;
		this.isEnabled = isEnabled;
	}

	public MappingPayerId(Long mappingId, String transactionType, String payerId, String mapperPayerId,
			Boolean isEnabled) {
		super();
		this.mappingId = mappingId;
		this.transactionType = transactionType;
		this.payerId = payerId;
		this.mapperPayerId = mapperPayerId;
		this.isEnabled = isEnabled;
	}

}
