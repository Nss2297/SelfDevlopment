package com.waseel.pbm.fdbvalidationservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * RequestInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PayerValidationConfiguration", schema = "MDSS")

public class PayerValidationConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3336244856242751744L;
	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 0)
	@GeneratedValue(generator = "PayerValidationConfigurationSeq")
	@SequenceGenerator(name = "PayerValidationConfigurationSeq", sequenceName = "PayerValidationSequence_SEQ", allocationSize = 0, initialValue = 1)
	private Long id;

	@Column(name = "PayerId")
	private String payerId;

	@Column(name = "FieldName")
	private String fieldName;

	@Column(name = "ToBeValidated", columnDefinition = "CHAR(1) default ('0')")
	private Character toBeValidated = '0';

	public PayerValidationConfiguration() {

	}

	public PayerValidationConfiguration(String payerId, String fieldName, Character toBeValidated) {
		super();
		this.payerId = payerId;
		this.fieldName = fieldName;
		this.toBeValidated = toBeValidated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Character getToBeValidated() {
		return toBeValidated;
	}

	public void setToBeValidated(Character toBeValidated) {
		this.toBeValidated = toBeValidated;
	}
}