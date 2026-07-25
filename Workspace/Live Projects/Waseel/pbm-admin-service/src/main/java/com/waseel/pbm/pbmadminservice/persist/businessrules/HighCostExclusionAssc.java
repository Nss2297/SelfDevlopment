package com.waseel.pbm.pbmadminservice.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "HIGH_COST_EXCLUSION_ASSC", schema = "PBM_BUSINESS_RULES")
public class HighCostExclusionAssc implements Serializable {

	private static final long serialVersionUID = 7574215531800600439L;

	@Id
	@GeneratedValue(generator = "HighCostSeq")
	@SequenceGenerator(name = "HighCostSeq", sequenceName = "HIGH_COST_EXC_ASSC_SEQ", allocationSize = 0)
	@Column(name = "HIGH_COST_EXCLUSION_ASSC_ID", nullable = false)
	private Long highCostExclusionAsscId;

	@Column(name = "EXCLUSION_ID", nullable = false)
	private Long exclusionId;

	@Column(name = "PAYER_ID", nullable = false)
	private Long payerId;

	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Date lastUpdateDate;

	@Column(name = "IS_ENABLED", nullable = false, columnDefinition = "CHAR(1) default ('1')")
	private Boolean isEnabled;

	public Long getHighCostExclusionAsscId() {
		return highCostExclusionAsscId;
	}

	public Long getExclusionId() {
		return exclusionId;
	}

	public Long getPayerId() {
		return payerId;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setHighCostExclusionAsscId(Long highCostExclusionAsscId) {
		this.highCostExclusionAsscId = highCostExclusionAsscId;
	}

	public void setExclusionId(Long exclusionId) {
		this.exclusionId = exclusionId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
