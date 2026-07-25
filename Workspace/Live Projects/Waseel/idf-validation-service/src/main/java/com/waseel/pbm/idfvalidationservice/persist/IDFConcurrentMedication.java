package com.waseel.pbm.idfvalidationservice.persist;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "IDFConcurrentMedication", schema = "MDSS")
public class IDFConcurrentMedication implements Serializable {
	private static final long serialVersionUID = 1L;

	private IDFConcurrentMedicationId id;
	private String additionalRejectionReason;
	private String severity;

	public IDFConcurrentMedication() {

	}

	public IDFConcurrentMedication(IDFConcurrentMedicationId id, String additionalRejectionReason, String severity) {
		super();
		this.id = id;
		this.additionalRejectionReason = additionalRejectionReason;
		this.severity = severity;
	}

	@EmbeddedId
	@AttributeOverride(column = @Column(name = "ServiceCode", length = 250), name = "ServiceCode")
	@AttributeOverride(column = @Column(name = "CUServiceCode", length = 250), name = "CUServiceCode")
	public IDFConcurrentMedicationId getId() {
		return id;
	}

	public void setId(IDFConcurrentMedicationId id) {
		this.id = id;
	}

	@Column(name = "AdditionalRejectionReason", length = 300)
	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = additionalRejectionReason;
	}

	@Column(name = "Severity", length = 200)
	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

}
