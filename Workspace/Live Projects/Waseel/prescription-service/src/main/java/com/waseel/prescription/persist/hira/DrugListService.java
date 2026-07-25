package com.waseel.prescription.persist.hira;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * DrugListService entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "`DrugListService`", schema = "HIRA", uniqueConstraints = @UniqueConstraint(columnNames = {
		"`DrugListServiceId`", "`RegistrationNo`", "`TradeName`", "`DrugListServiceId`" }))
public class DrugListService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrugListServiceId id;
	private String tradeName;
	private Double price;
	private String serviceType;
	private Long granularUnit;
	private Date lastUpdatedDate;

	// Constructors

	/** default constructor */
	public DrugListService() {
	}

	/** minimal constructor */
	public DrugListService(DrugListServiceId id) {
		this.id = id;
	}

	/** full constructor */
	public DrugListService(DrugListServiceId id, String tradeName, Double price, String serviceType, Long granularUnit,
			Date lastUpdatedDate) {
		super();
		this.id = id;
		this.tradeName = tradeName;
		this.price = price;
		this.serviceType = serviceType;
		this.granularUnit = granularUnit;
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@EmbeddedId
	@AttributeOverride(name = "registrationNo", column = @Column(name = "`RegistrationNo`", nullable = false, length = 30))
	@AttributeOverride(name = "drugListServiceId", column = @Column(name = "`DrugListServiceId`", nullable = false, precision = 0))
	public DrugListServiceId getId() {
		return this.id;
	}

	public void setId(DrugListServiceId id) {
		this.id = id;
	}

	@Column(name = "`TradeName`", length = 256)
	public String getTradeName() {
		return this.tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	@Column(name = "`Price`", precision = 14)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "`ServiceType`", length = 30)
	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@Column(name = "`GranularUnit`")
	public Long getGranularUnit() {
		return granularUnit;
	}

	public void setGranularUnit(Long granularUnit) {
		this.granularUnit = granularUnit;
	}

	@Column(name = "`LastUpdatedDate`")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

}