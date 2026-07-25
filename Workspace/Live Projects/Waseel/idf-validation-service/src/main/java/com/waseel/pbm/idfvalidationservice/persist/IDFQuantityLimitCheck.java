package com.waseel.pbm.idfvalidationservice.persist;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IDFQuantityLimitCheck", schema = "MDSS")
public class IDFQuantityLimitCheck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1950298940619514547L;

	@EmbeddedId
	private IDFQuantityLimitCheckId id;

	@Column(name = "MaxQuantityLimitInDays")
	private Integer maxQuantityLimitInDays;

	@Column(name = "MaxDurationInDays")
	private Double maxDurationInDays;

	@Column(name = "ProductPackageUnit")
	private String productPackageUnit;

	@AttributeOverrides({ @AttributeOverride(name = "serviceCode", column = @Column(name = "ServiceCode")),
			@AttributeOverride(name = "fromAgeDurationInDays", column = @Column(name = "FromAgeDuration-[InDays]")),
			@AttributeOverride(name = "toAgeDurationInDays", column = @Column(name = "ToAgeDuration-[InDays]")) })
	public IDFQuantityLimitCheckId getId() {
		return id;
	}

	public void setId(IDFQuantityLimitCheckId id) {
		this.id = id;
	}

	public Integer getMaxQuantityLimitInDays() {
		return maxQuantityLimitInDays;
	}

	public void setMaxQuantityLimitInDays(Integer maxQuantityLimitInDays) {
		this.maxQuantityLimitInDays = maxQuantityLimitInDays;
	}

	public Double getMaxDurationInDays() {
		return maxDurationInDays;
	}

	public void setMaxDurationInDays(Double maxDurationInDays) {
		this.maxDurationInDays = maxDurationInDays;
	}

	public String getProductPackageUnit() {
		return productPackageUnit;
	}

	public void setProductPackageUnit(String productPackageUnit) {
		this.productPackageUnit = productPackageUnit;
	}

}
