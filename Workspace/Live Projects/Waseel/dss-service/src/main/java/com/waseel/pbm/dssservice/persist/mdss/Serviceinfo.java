package com.waseel.pbm.dssservice.persist.mdss;



import java.io.Serializable;


import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ServiceInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ServiceInfo", schema = "MDSS")

public class Serviceinfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7454920475823122526L;
	// Fields
	private ServiceInfoId id;
	private Timestamp serviceDate;
	private String serviceCode;
	private BigDecimal serviceQuantity;
	private Double serviceAmount;
	private Double daysOfSupply;
	private Character isDeletedFromProvider = '0';
	private Character isCancelled = '0';
	private Character isOverriden = '0';
	private String scientificCode;

	// Constructors
	/** default constructor */
	public Serviceinfo() {
	}

	/** minimal constructor */
	public Serviceinfo(ServiceInfoId id) {
		this.id = id;
	}

	/** full constructor */
	public Serviceinfo(ServiceInfoId id, Timestamp serviceDate, String serviceCode, BigDecimal serviceQuantity,
			Double serviceAmount, Double daysOfSupply, Character isDeletedFromProvider, Character isCancelled,
			Character isOverriden,String scientificCode) {
		super();
		this.id = id;
		this.serviceDate = serviceDate;
		this.serviceCode = serviceCode;
		this.serviceQuantity = serviceQuantity;
		this.serviceAmount = serviceAmount;
		this.daysOfSupply = daysOfSupply;
		this.isDeletedFromProvider = isDeletedFromProvider;
		this.isCancelled = isCancelled;
		this.isOverriden = isOverriden;
		this.scientificCode = scientificCode;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverride(name = "requestId", column = @Column(name = "RequestId", nullable = false, precision = 0))
	@AttributeOverride(name = "serviceId", column = @Column(name = "ServiceId", nullable = false, precision = 0))
	public ServiceInfoId getId() {
		return this.id;
	}

	public void setId(ServiceInfoId id) {
		this.id = id;
	}

	@Column(name = "ServiceDate", length = 7)
	public Timestamp getServiceDate() {
		return this.serviceDate;
	}

	public void setServiceDate(Timestamp serviceDate) {
		this.serviceDate = serviceDate;
	}

	@Column(name = "ServiceCode", length = 100)
	public String getServiceCode() {
		return this.serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	@Column(name = "ServiceQuantity", precision = 0)
	public BigDecimal getServiceQuantity() {
		return this.serviceQuantity;
	}

	public void setServiceQuantity(BigDecimal serviceQuantity) {
		this.serviceQuantity = serviceQuantity;
	}

	@Column(name = "ServiceAmount", length = 50)
	public Double getServiceAmount() {
		return this.serviceAmount;
	}

	public void setServiceAmount(Double serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	@Column(name = "DaysOfSupply", length = 100)
	public Double getDaysOfSupply() {
		return this.daysOfSupply;
	}

	public void setDaysOfSupply(Double daysOfSupply) {
		this.daysOfSupply = daysOfSupply;
	}

	@Column(name = "IsDeletedFromProvider", columnDefinition = "CHAR(1) default ('0')")
	public Character getIsDeletedFromProvider() {
		return isDeletedFromProvider;
	}

	public void setIsDeletedFromProvider(Character isDeletedFromProvider) {
		this.isDeletedFromProvider = isDeletedFromProvider;
	}

	@Column(name = "IsCancelled", columnDefinition = "CHAR(1) default ('0')")
	public Character getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(Character isCancelled) {
		this.isCancelled = isCancelled;
	}

	@Column(name = "IsOverriden", columnDefinition = "CHAR(1) default ('0')")
	public Character getIsOverriden() {
		return isOverriden;
	}

	public void setIsOverriden(Character isOverriden) {
		this.isOverriden = isOverriden;
	}
	
	@Column(name = "ScientificCode", length = 64)
	public String getScientificCode() {
		return this.scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

}