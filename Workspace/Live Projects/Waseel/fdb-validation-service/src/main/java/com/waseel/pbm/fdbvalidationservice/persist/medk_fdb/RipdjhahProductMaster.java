package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RipdjhahProductMaster entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RIPDJHAH_PRODUCT_MASTER", schema = "MEDK_FDB")

public class RipdjhahProductMaster implements java.io.Serializable {

	// Fields

	private String productId;
	private String productType;
	private String ndcfi;
	private String productBrandName;
	private String productLabelName;
	private Integer gcnSeqno;
	private Integer medid;
	private Timestamp productAddDate;
	private Timestamp productObsoleteDate;
	private String productPackageDesc;
	private Double productPackageSize;
	private String productPackageUnit;
	private Integer productPackageCount;
	private Integer labelerId;
	private Integer agentId;

	// Constructors

	/** default constructor */
	public RipdjhahProductMaster() {
	}

	/** minimal constructor */
	public RipdjhahProductMaster(String productId, String productType, String productBrandName, String productLabelName,
			Integer gcnSeqno, Timestamp productAddDate, Double productPackageSize, String productPackageUnit) {
		this.productId = productId;
		this.productType = productType;
		this.productBrandName = productBrandName;
		this.productLabelName = productLabelName;
		this.gcnSeqno = gcnSeqno;
		this.productAddDate = productAddDate;
		this.productPackageSize = productPackageSize;
		this.productPackageUnit = productPackageUnit;
	}

	/** full constructor */
	public RipdjhahProductMaster(String productId, String productType, String ndcfi, String productBrandName,
			String productLabelName, Integer gcnSeqno, Integer medid, Timestamp productAddDate,
			Timestamp productObsoleteDate, String productPackageDesc, Double productPackageSize,
			String productPackageUnit, Integer productPackageCount, Integer labelerId, Integer agentId) {
		this.productId = productId;
		this.productType = productType;
		this.ndcfi = ndcfi;
		this.productBrandName = productBrandName;
		this.productLabelName = productLabelName;
		this.gcnSeqno = gcnSeqno;
		this.medid = medid;
		this.productAddDate = productAddDate;
		this.productObsoleteDate = productObsoleteDate;
		this.productPackageDesc = productPackageDesc;
		this.productPackageSize = productPackageSize;
		this.productPackageUnit = productPackageUnit;
		this.productPackageCount = productPackageCount;
		this.labelerId = labelerId;
		this.agentId = agentId;
	}

	// Property accessors
	@Id

	@Column(name = "PRODUCT_ID", unique = true, nullable = false, length = 25)

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "PRODUCT_TYPE", nullable = false, length = 10)

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Column(name = "NDCFI", length = 1)

	public String getNdcfi() {
		return this.ndcfi;
	}

	public void setNdcfi(String ndcfi) {
		this.ndcfi = ndcfi;
	}

	@Column(name = "PRODUCT_BRAND_NAME", nullable = false, length = 60)

	public String getProductBrandName() {
		return this.productBrandName;
	}

	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
	}

	@Column(name = "PRODUCT_LABEL_NAME", nullable = false, length = 100)

	public String getProductLabelName() {
		return this.productLabelName;
	}

	public void setProductLabelName(String productLabelName) {
		this.productLabelName = productLabelName;
	}

	@Column(name = "GCN_SEQNO", nullable = false, precision = 6, scale = 0)

	public Integer getGcnSeqno() {
		return this.gcnSeqno;
	}

	public void setGcnSeqno(Integer gcnSeqno) {
		this.gcnSeqno = gcnSeqno;
	}

	@Column(name = "MEDID", precision = 8, scale = 0)

	public Integer getMedid() {
		return this.medid;
	}

	public void setMedid(Integer medid) {
		this.medid = medid;
	}

	@Column(name = "PRODUCT_ADD_DATE", nullable = false, length = 7)

	public Timestamp getProductAddDate() {
		return this.productAddDate;
	}

	public void setProductAddDate(Timestamp productAddDate) {
		this.productAddDate = productAddDate;
	}

	@Column(name = "PRODUCT_OBSOLETE_DATE", length = 7)

	public Timestamp getProductObsoleteDate() {
		return this.productObsoleteDate;
	}

	public void setProductObsoleteDate(Timestamp productObsoleteDate) {
		this.productObsoleteDate = productObsoleteDate;
	}

	@Column(name = "PRODUCT_PACKAGE_DESC", length = 25)

	public String getProductPackageDesc() {
		return this.productPackageDesc;
	}

	public void setProductPackageDesc(String productPackageDesc) {
		this.productPackageDesc = productPackageDesc;
	}

	@Column(name = "PRODUCT_PACKAGE_SIZE", nullable = false, precision = 12, scale = 3)

	public Double getProductPackageSize() {
		return this.productPackageSize;
	}

	public void setProductPackageSize(Double productPackageSize) {
		this.productPackageSize = productPackageSize;
	}

	@Column(name = "PRODUCT_PACKAGE_UNIT", nullable = false, length = 1)

	public String getProductPackageUnit() {
		return this.productPackageUnit;
	}

	public void setProductPackageUnit(String productPackageUnit) {
		this.productPackageUnit = productPackageUnit;
	}

	@Column(name = "PRODUCT_PACKAGE_COUNT", precision = 8, scale = 0)

	public Integer getProductPackageCount() {
		return this.productPackageCount;
	}

	public void setProductPackageCount(Integer productPackageCount) {
		this.productPackageCount = productPackageCount;
	}

	@Column(name = "LABELER_ID", precision = 8, scale = 0)

	public Integer getLabelerId() {
		return this.labelerId;
	}

	public void setLabelerId(Integer labelerId) {
		this.labelerId = labelerId;
	}

	@Column(name = "AGENT_ID", precision = 8, scale = 0)

	public Integer getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

}