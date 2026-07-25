package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retcgc0EtcGcnseqno entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCGC0_ETC_GCNSEQNO"
    ,schema="MEDK_FDB"
)

public class Retcgc0EtcGcnseqno  implements java.io.Serializable {


    // Fields    

     private Retcgc0EtcGcnseqnoId id;
     private String etcCommonUseInd;
     private String etcDefaultUseInd;


    // Constructors

    /** default constructor */
    public Retcgc0EtcGcnseqno() {
    }

	/** minimal constructor */
    public Retcgc0EtcGcnseqno(Retcgc0EtcGcnseqnoId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Retcgc0EtcGcnseqno(Retcgc0EtcGcnseqnoId id, String etcCommonUseInd, String etcDefaultUseInd) {
        this.id = id;
        this.etcCommonUseInd = etcCommonUseInd;
        this.etcDefaultUseInd = etcDefaultUseInd;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ) } )

    public Retcgc0EtcGcnseqnoId getId() {
        return this.id;
    }
    
    public void setId(Retcgc0EtcGcnseqnoId id) {
        this.id = id;
    }
    
    @Column(name="ETC_COMMON_USE_IND", length=1)

    public String getEtcCommonUseInd() {
        return this.etcCommonUseInd;
    }
    
    public void setEtcCommonUseInd(String etcCommonUseInd) {
        this.etcCommonUseInd = etcCommonUseInd;
    }
    
    @Column(name="ETC_DEFAULT_USE_IND", length=1)

    public String getEtcDefaultUseInd() {
        return this.etcDefaultUseInd;
    }
    
    public void setEtcDefaultUseInd(String etcDefaultUseInd) {
        this.etcDefaultUseInd = etcDefaultUseInd;
    }
   








}