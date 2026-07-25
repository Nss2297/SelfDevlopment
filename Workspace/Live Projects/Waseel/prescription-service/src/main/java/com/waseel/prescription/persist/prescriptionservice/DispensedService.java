package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DispensedService")
public class DispensedService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PsDispensedServiceSeq")
	@SequenceGenerator(name = "PsDispensedServiceSeq", sequenceName = "PS_DispensedService_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "ServiceID", length = 100)
	private Long serviceID;

	@Column(name = "DispensedID", length = 100)
	private Long dispensedId;

	@Column(name = "DispensedQuantity", nullable = false)
	private BigDecimal dispensedQuantity;

	@Column(name = "PrescribedQuantity", nullable = false)
	private BigDecimal prescribedQuantity;

	public DispensedService() {
		super();
	}

	public DispensedService(Long serviceID, Long dispensedId, BigDecimal dispensedQuantity,
			BigDecimal prescribedQuantity) {
		super();
		this.serviceID = serviceID;
		this.dispensedId = dispensedId;
		this.dispensedQuantity = dispensedQuantity;
		this.prescribedQuantity = prescribedQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getServiceID() {
		return serviceID;
	}

	public void setServiceID(Long serviceID) {
		this.serviceID = serviceID;
	}

	public Long getDispensedId() {
		return dispensedId;
	}

	public void setDispensedId(Long dispensedId) {
		this.dispensedId = dispensedId;
	}

	public BigDecimal getDispensedQuantity() {
		return dispensedQuantity;
	}

	public void setDispensedQuantity(BigDecimal dispensedQuantity) {
		this.dispensedQuantity = dispensedQuantity;
	}

	public BigDecimal getPrescribedQuantity() {
		return prescribedQuantity;
	}

	public void setPrescribedQuantity(BigDecimal prescribedQuantity) {
		this.prescribedQuantity = prescribedQuantity;
	}

}
