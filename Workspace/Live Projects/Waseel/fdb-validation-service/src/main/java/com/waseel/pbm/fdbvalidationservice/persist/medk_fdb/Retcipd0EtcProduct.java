package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retcipd0EtcProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCIPD0_ETC_PRODUCT"
    ,schema="MEDK_FDB"
)

public class Retcipd0EtcProduct  implements java.io.Serializable {


    // Fields    

     private Retcipd0EtcProductId id;
     private String etcCommonUseInd;
     private String etcDefaultUseInd;


    // Constructors

    /** default constructor */
    public Retcipd0EtcProduct() {
    }

	/** minimal constructor */
    public Retcipd0EtcProduct(Retcipd0EtcProductId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Retcipd0EtcProduct(Retcipd0EtcProductId id, String etcCommonUseInd, String etcDefaultUseInd) {
        this.id = id;
        this.etcCommonUseInd = etcCommonUseInd;
        this.etcDefaultUseInd = etcDefaultUseInd;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="productId", column=@Column(name="PRODUCT_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ) } )

    public Retcipd0EtcProductId getId() {
        return this.id;
    }
    
    public void setId(Retcipd0EtcProductId id) {
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