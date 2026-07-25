package com.waseel.drugexclusionvalidationservice.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HIGH_COST_EXCLUSION_ASSC", schema = "PBM_BUSINESS_RULES")
public class HighCostExclusionAssc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HIGH_COST_EXCLUSION_ASSC_ID", nullable = false)
	private Long highCostExclusionAsscId;

	@Column(name = "EXCLUSION_ID", nullable = false)
	private Long exclusionId;

	@Column(name = "PAYER_ID", nullable = false)
	private Long payerId;

	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Date lastUpdateDate;

	@Column(name = "IS_ENABLED", nullable = false,columnDefinition = "CHAR(1) default ('1')")
	private Boolean isEnabled = true;

	public Long getHighCostExclusionAsscId() {
		return highCostExclusionAsscId;
	}

	public void setHighCostExclusionAsscId(Long highCostExclusionAsscId) {
		this.highCostExclusionAsscId = highCostExclusionAsscId;
	}

	public Long getExclusionId() {
		return exclusionId;
	}

	public void setExclusionId(Long exclusionId) {
		this.exclusionId = exclusionId;
	}

	public Long getPayerId() {
		return payerId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Boolean getIsEanbled() {
		return isEnabled;
	}

	public void setIsEanbled(Boolean isEanbled) {
		this.isEnabled = isEanbled;
	}
}
