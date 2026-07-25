package com.waseel.pbm.dssservice.persist.medk_fdb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Ripdat0ProductAttributeId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Ripdat0ProductAttributeId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer productId;
	private Integer productAttributeCode;
	private Integer productAttributeSeq;

	// Constructors
	/** default constructor */
	public Ripdat0ProductAttributeId() {
	}

	/** full constructor */
	public Ripdat0ProductAttributeId(Integer productId, Integer productAttributeCode, Integer productAttributeSeq) {
		this.productId = productId;
		this.productAttributeCode = productAttributeCode;
		this.productAttributeSeq = productAttributeSeq;
	}

	// Property accessors
	@Column(name = "PRODUCT_ID", nullable = false, precision = 8, scale = 0)
	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "PRODUCT_ATTRIBUTE_CODE", nullable = false, precision = 8, scale = 0)
	public Integer getProductAttributeCode() {
		return this.productAttributeCode;
	}

	public void setProductAttributeCode(Integer productAttributeCode) {
		this.productAttributeCode = productAttributeCode;
	}

	@Column(name = "PRODUCT_ATTRIBUTE_SEQ", nullable = false, precision = 8, scale = 0)
	public Integer getProductAttributeSeq() {
		return this.productAttributeSeq;
	}

	public void setProductAttributeSeq(Integer productAttributeSeq) {
		this.productAttributeSeq = productAttributeSeq;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Ripdat0ProductAttributeId))
			return false;
		Ripdat0ProductAttributeId castOther = (Ripdat0ProductAttributeId) other;

		return ((this.getProductId() == castOther.getProductId()) || (this.getProductId() != null
				&& castOther.getProductId() != null && this.getProductId().equals(castOther.getProductId())))
				&& ((this.getProductAttributeCode() == castOther.getProductAttributeCode())
						|| (this.getProductAttributeCode() != null && castOther.getProductAttributeCode() != null
								&& this.getProductAttributeCode().equals(castOther.getProductAttributeCode())))
				&& ((this.getProductAttributeSeq() == castOther.getProductAttributeSeq())
						|| (this.getProductAttributeSeq() != null && castOther.getProductAttributeSeq() != null
								&& this.getProductAttributeSeq().equals(castOther.getProductAttributeSeq())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getProductId() == null ? 0 : this.getProductId().hashCode());
		result = 37 * result + (getProductAttributeCode() == null ? 0 : this.getProductAttributeCode().hashCode());
		result = 37 * result + (getProductAttributeSeq() == null ? 0 : this.getProductAttributeSeq().hashCode());
		return result;
	}
}