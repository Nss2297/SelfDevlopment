package com.waseel.pbm.fdbvalidationservice.persist.mdss;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;


/**
 * FdbdiagnosisIndicationConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FDBDiagnosisIndicationConfig", schema = "MDSS")
@Where(clause = "\"IsDeleted\"= '0'")
public class FdbdiagnosisIndicationConfig  implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields    
     private String icdcode;
     private String validateSubChapters;
     private Character isEnabled = '0';
     private Character isDeleted = '0';
     private Timestamp lastUpdatedDateTime;
     
    // Constructors
    /** default constructor */
    public FdbdiagnosisIndicationConfig() {
    }

	/** minimal constructor */
    public FdbdiagnosisIndicationConfig(String icdcode) {
        this.icdcode = icdcode;
    }
    
    /** full constructor */
    public FdbdiagnosisIndicationConfig(String icdcode, String validateSubChapters) {
        this.icdcode = icdcode;
        this.validateSubChapters = validateSubChapters;
    }

    // Property accessors
    @Id 
    @Column(name="ICDCode", unique=true, nullable=false, length=20)
    public String getIcdcode() {
        return this.icdcode;
    }
    
    public void setIcdcode(String icdcode) {
        this.icdcode = icdcode;
    }
    
    @Column(name="ValidateSubChapters", length=1)
    public String getValidateSubChapters() {
        return this.validateSubChapters;
    }
    
    public void setValidateSubChapters(String validateSubChapters) {
        this.validateSubChapters = validateSubChapters;
    }

    @Column(name="IsEnabled", length=1)
	public Character getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Character isEnabled) {
		this.isEnabled = isEnabled;
	}

	 @Column(name="IsDeleted", length=1)
	public Character getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Character isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name="LastUpdatedDateTime")
	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}
    
	
    
}