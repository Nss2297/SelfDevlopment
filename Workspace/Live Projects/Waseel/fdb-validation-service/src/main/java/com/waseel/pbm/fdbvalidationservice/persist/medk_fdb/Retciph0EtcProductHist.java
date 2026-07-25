package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retciph0EtcProductHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCIPH0_ETC_PRODUCT_HIST"
    ,schema="MEDK_FDB"
)

public class Retciph0EtcProductHist  implements java.io.Serializable {


    // Fields    

     private Retciph0EtcProductHistId id;
     private String etcCommonUseInd;
     private String etcDefaultUseInd;
     private String etcChangeTypeCode;
     private Timestamp etcEffectiveDate;


    // Constructors

    /** default constructor */
    public Retciph0EtcProductHist() {
    }

	/** minimal constructor */
    public Retciph0EtcProductHist(Retciph0EtcProductHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Retciph0EtcProductHist(Retciph0EtcProductHistId id, String etcCommonUseInd, String etcDefaultUseInd, String etcChangeTypeCode, Timestamp etcEffectiveDate) {
        this.id = id;
        this.etcCommonUseInd = etcCommonUseInd;
        this.etcDefaultUseInd = etcDefaultUseInd;
        this.etcChangeTypeCode = etcChangeTypeCode;
        this.etcEffectiveDate = etcEffectiveDate;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="productId", column=@Column(name="PRODUCT_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcRevisionSeqno", column=@Column(name="ETC_REVISION_SEQNO", nullable=false, precision=5, scale=0) ) } )

    public Retciph0EtcProductHistId getId() {
        return this.id;
    }
    
    public void setId(Retciph0EtcProductHistId id) {
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