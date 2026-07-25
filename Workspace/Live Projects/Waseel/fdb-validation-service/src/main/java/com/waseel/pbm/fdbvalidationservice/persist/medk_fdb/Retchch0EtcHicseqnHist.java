package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retchch0EtcHicseqnHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCHCH0_ETC_HICSEQN_HIST"
    ,schema="MEDK_FDB"
)

public class Retchch0EtcHicseqnHist  implements java.io.Serializable {


    // Fields    

     private Retchch0EtcHicseqnHistId id;
     private String etcChangeTypeCode;
     private Timestamp etcEffectiveDate;


    // Constructors

    /** default constructor */
    public Retchch0EtcHicseqnHist() {
    }

	/** minimal constructor */
    public Retchch0EtcHicseqnHist(Retchch0EtcHicseqnHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Retchch0EtcHicseqnHist(Retchch0EtcHicseqnHistId id, String etcChangeTypeCode, Timestamp etcEffectiveDate) {
        this.id = id;
        this.etcChangeTypeCode = etcChangeTypeCode;
        this.etcEffectiveDate = etcEffectiveDate;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="hicSeqn", column=@Column(name="HIC_SEQN", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcRevisionSeqno", column=@Column(name="ETC_REVISION_SEQNO", nullable=false, precision=5, scale=0) ) } )

    public Retchch0EtcHicseqnHistId getId() {
        return this.id;
    }
    
    public void setId(Retchch0EtcHicseqnHistId id) {
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