package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retcmed0EtcMedid entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCMED0_ETC_MEDID"
    ,schema="MEDK_FDB"
)

public class Retcmed0EtcMedid  implements java.io.Serializable {


    // Fields    

     private Retcmed0EtcMedidId id;
     private String etcCommonUseInd;
     private String etcDefaultUseInd;


    // Constructors

    /** default constructor */
    public Retcmed0EtcMedid() {
    }

	/** minimal constructor */
    public Retcmed0EtcMedid(Retcmed0EtcMedidId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Retcmed0EtcMedid(Retcmed0EtcMedidId id, String etcCommonUseInd, String etcDefaultUseInd) {
        this.id = id;
        this.etcCommonUseInd = etcCommonUseInd;
        this.etcDefaultUseInd = etcDefaultUseInd;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medid", column=@Column(name="MEDID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ) } )

    public Retcmed0EtcMedidId getId() {
        return this.id;
    }
    
    public void setId(Retcmed0EtcMedidId id) {
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