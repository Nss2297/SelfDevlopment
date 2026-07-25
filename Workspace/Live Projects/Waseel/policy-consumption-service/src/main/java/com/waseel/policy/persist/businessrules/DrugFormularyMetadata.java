package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DRUG_FORMULARY_METADATA database table.
 * 
 */
@Entity
@Table(name="DRUG_FORMULARY_METADATA")
@NamedQuery(name="DrugFormularyMetadata.findAll", query="SELECT d FROM DrugFormularyMetadata d")
public class DrugFormularyMetadata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FORMULARY_ID")
	private long formularyId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="DELETED_BY")
	private String deletedBy;

	@Column(name="FORMULARY_NAME")
	private String formularyName;

	@Column(name="IS_DELETED")
	private String isDeleted;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="PAYER_ID")
	private String payerId;

	//bi-directional many-to-one association to DrugFormularyDetail
	@OneToMany(mappedBy="drugFormularyMetadata")
	private List<DrugFormularyDetail> drugFormularyDetails;

	//bi-directional many-to-one association to DrugFormularyPolicyAssociation
	@OneToMany(mappedBy="drugFormularyMetadata")
	private List<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociations;

	public DrugFormularyMetadata() {
	}

	public long getFormularyId() {
		return this.formularyId;
	}

	public void setFormularyId(long formularyId) {
		this.formularyId = formularyId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeletedBy() {
		return this.deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public String getFormularyName() {
		return this.formularyName;
	}

	public void setFormularyName(String formularyName) {
		this.formularyName = formularyName;
	}

	public String getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public List<DrugFormularyDetail> getDrugFormularyDetails() {
		return this.drugFormularyDetails;
	}

	public void setDrugFormularyDetails(List<DrugFormularyDetail> drugFormularyDetails) {
		this.drugFormularyDetails = drugFormularyDetails;
	}

	public DrugFormularyDetail addDrugFormularyDetail(DrugFormularyDetail drugFormularyDetail) {
		getDrugFormularyDetails().add(drugFormularyDetail);
		drugFormularyDetail.setDrugFormularyMetadata(this);

		return drugFormularyDetail;
	}

	public DrugFormularyDetail removeDrugFormularyDetail(DrugFormularyDetail drugFormularyDetail) {
		getDrugFormularyDetails().remove(drugFormularyDetail);
		drugFormularyDetail.setDrugFormularyMetadata(null);

		return drugFormularyDetail;
	}

	public List<DrugFormularyPolicyAssociation> getDrugFormularyPolicyAssociations() {
		return this.drugFormularyPolicyAssociations;
	}

	public void setDrugFormularyPolicyAssociations(List<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociations) {
		this.drugFormularyPolicyAssociations = drugFormularyPolicyAssociations;
	}

	public DrugFormularyPolicyAssociation addDrugFormularyPolicyAssociation(DrugFormularyPolicyAssociation drugFormularyPolicyAssociation) {
		getDrugFormularyPolicyAssociations().add(drugFormularyPolicyAssociation);
		drugFormularyPolicyAssociation.setDrugFormularyMetadata(this);

		return drugFormularyPolicyAssociation;
	}

	public DrugFormularyPolicyAssociation removeDrugFormularyPolicyAssociation(DrugFormularyPolicyAssociation drugFormularyPolicyAssociation) {
		getDrugFormularyPolicyAssociations().remove(drugFormularyPolicyAssociation);
		drugFormularyPolicyAssociation.setDrugFormularyMetadata(null);

		return drugFormularyPolicyAssociation;
	}

}