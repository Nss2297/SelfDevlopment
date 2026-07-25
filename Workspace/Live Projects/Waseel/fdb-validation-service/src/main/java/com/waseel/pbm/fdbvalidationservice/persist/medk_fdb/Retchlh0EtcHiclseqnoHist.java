package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retchlh0EtcHiclseqnoHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCHLH0_ETC_HICLSEQNO_HIST"
    ,schema="MEDK_FDB"
)

public class Retchlh0EtcHiclseqnoHist  implements java.io.Serializable {


    // Fields    

     private Retchlh0EtcHiclseqnoHistId id;
     private String etcChangeTypeCode;
     private Timestamp etcEffectiveDate;


    // Constructors

    /** default constructor */
    public Retchlh0EtcHiclseqnoHist() {
    }

	/** minimal constructor */
    public Retchlh0EtcHiclseqnoHist(Retchlh0EtcHiclseqnoHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Retchlh0EtcHiclseqnoHist(Retchlh0EtcHiclseqnoHistId id, String etcChangeTypeCode, Timestamp etcEffectiveDate) {
        this.id = id;
        this.etcChangeTypeCode = etcChangeTypeCode;
        this.etcEffectiveDate = etcEffectiveDate;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="hiclSeqno", column=@Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcRevisionSeqno", column=@Column(name="ETC_REVISION_SEQNO", nullable=false, precision=5, scale=0) ) } )

    public Retchlh0EtcHiclseqnoHistId getId() {
        return this.id;
    }
    
    public void setId(Retchlh0EtcHiclseqnoHistId id) {
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