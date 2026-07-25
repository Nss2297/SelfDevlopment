package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rfmlibh0IcdBillableHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLIBH0_ICD_BILLABLE_HIST"
    ,schema="MEDK_FDB"
)

public class Rfmlibh0IcdBillableHist  implements java.io.Serializable {


    // Fields    

     private Rfmlibh0IcdBillableHistId id;
     private Timestamp icdLastBillableDt;


    // Constructors

    /** default constructor */
    public Rfmlibh0IcdBillableHist() {
    }

	/** minimal constructor */
    public Rfmlibh0IcdBillableHist(Rfmlibh0IcdBillableHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rfmlibh0IcdBillableHist(Rfmlibh0IcdBillableHistId id, Timestamp icdLastBillableDt) {
        this.id = id;
        this.icdLastBillableDt = icdLastBillableDt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="icdCd", column=@Column(name="ICD_CD", nullable=false, length=10) ), 
        @AttributeOverride(name="icdCdType", column=@Column(name="ICD_CD_TYPE", nullable=false, length=2) ), 
        @AttributeOverride(name="icdFirstBillableDt", column=@Column(name="ICD_FIRST_BILLABLE_DT", nullable=false, length=7) ) } )

    public Rfmlibh0IcdBillableHistId getId() {
        return this.id;
    }
    
    public void setId(Rfmlibh0IcdBillableHistId id) {
        this.id = id;
    }
    
    @Column(name="ICD_LAST_BILLABLE_DT", length=7)

    public Timestamp getIcdLastBillableDt() {
        return this.icdLastBillableDt;
    }
    
    public void setIcdLastBillableDt(Timestamp icdLastBillableDt) {
        this.icdLastBillableDt = icdLastBillableDt;
    }
   








}