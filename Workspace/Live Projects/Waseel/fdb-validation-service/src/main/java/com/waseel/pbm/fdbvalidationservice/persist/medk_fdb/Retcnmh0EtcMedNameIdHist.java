package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retcnmh0EtcMedNameIdHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCNMH0_ETC_MED_NAME_ID_HIST"
    ,schema="MEDK_FDB"
)

public class Retcnmh0EtcMedNameIdHist  implements java.io.Serializable {


    // Fields    

     private Retcnmh0EtcMedNameIdHistId id;
     private String etcChangeTypeCode;
     private Timestamp etcEffectiveDate;


    // Constructors

    /** default constructor */
    public Retcnmh0EtcMedNameIdHist() {
    }

	/** minimal constructor */
    public Retcnmh0EtcMedNameIdHist(Retcnmh0EtcMedNameIdHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Retcnmh0EtcMedNameIdHist(Retcnmh0EtcMedNameIdHistId id, String etcChangeTypeCode, Timestamp etcEffectiveDate) {
        this.id = id;
        this.etcChangeTypeCode = etcChangeTypeCode;
        this.etcEffectiveDate = etcEffectiveDate;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medNameId", column=@Column(name="MED_NAME_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcRevisionSeqno", column=@Column(name="ETC_REVISION_SEQNO", nullable=false, precision=5, scale=0) ) } )

    public Retcnmh0EtcMedNameIdHistId getId() {
        return this.id;
    }
    
    public void setId(Retcnmh0EtcMedNameIdHistId id) {
        this.id = id;
    }
    
    @Column(name="ETC_CHANGE_TYPE_CODE", length=1)

    public String getEtcChangeTypeCode() {
        return this.etcChangeTypeCode;
    }
    
    public void setEtcChangeTypeCode(String etcChangeTypeCode) {
        this.etcChangeTypeCode = etcChangeTypeCode;
    }
    
    @Column(name="ETC_EFFECTIVE_DATE", length=7)

    public Timestamp getEtcEffectiveDate() {
        return this.etcEffectiveDate;
    }
    
    public void setEtcEffectiveDate(Timestamp etcEffectiveDate) {
        this.etcEffectiveDate = etcEffectiveDate;
    }
   








}