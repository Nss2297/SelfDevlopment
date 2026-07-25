package com.waseel.pbm.pbmadminservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PayerConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PayerConfig", schema = "MDSS")

public class PayerConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private PayerConfigId id;

	// Constructors

	/** default constructor */
	public PayerConfig() {
	}

	/** full constructor */
	public PayerConfig(PayerConfigId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverride(name = "payerId", column = @Column(name = "PayerId", length = 100))
	@AttributeOverride(name = "isEnabled", column = @Column(name = "isEnabled", length = 1))
	public PayerConfigId getId() {
		return this.id;
	}

	public void setId(PayerConfigId id) {
		this.id = id;
	}

}