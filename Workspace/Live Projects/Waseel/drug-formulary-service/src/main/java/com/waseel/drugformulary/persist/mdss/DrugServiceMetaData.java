package com.waseel.drugformulary.persist.mdss;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "`DrugServiceMetaData`", schema = "MDSS")
public class DrugServiceMetaData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`DrugListId`")
	private long drugListId;

	@Column(name = "`Effective_Date`")
	private Date effectiveDate;

	@Column(name = "`File_Name`")
	private String fileName;

	@Column(name = "`Owner_Name`")
	private String ownerName;

	@Column(name = "`SFDA_Update_Date`")
	private Date sfdaUpdateDate;

	@Column(name = "`SFDA_Version`")
	private String sfdaVersion;

	@Column(name = "`Upload_Date_Time`")
	private Timestamp uploadDateTime;

	public long getDrugListId() {
		return drugListId;
	}

	public void setDrugListId(long drugListId) {
		this.drugListId = drugListId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSfdaVersion() {
		return sfdaVersion;
	}

	public void setSfdaVersion(String sfdaVersion) {
		this.sfdaVersion = sfdaVersion;
	}

	public Date getSfdaUpdateDate() {
		return sfdaUpdateDate;
	}

	public void setSfdaUpdateDate(Date sfdaUpdateDate) {
		this.sfdaUpdateDate = sfdaUpdateDate;
	}

	public Timestamp getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Timestamp uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}
}
