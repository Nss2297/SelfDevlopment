package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Retcctd0EtcChangeTypeDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCCTD0_ETC_CHANGE_TYPE_DESC"
    ,schema="MEDK_FDB"
)

public class Retcctd0EtcChangeTypeDesc  implements java.io.Serializable {


    // Fields    

     private String etcChangeTypeCode;
     private String etcChangeTypeCodeDesc;


    // Constructors

    /** default constructor */
    public Retcctd0EtcChangeTypeDesc() {
    }

	/** minimal constructor */
    public Retcctd0EtcChangeTypeDesc(String etcChangeTypeCode) {
        this.etcChangeTypeCode = etcChangeTypeCode;
    }
    
    /** full constructor */
    public Retcctd0EtcChangeTypeDesc(String etcChangeTypeCode, String etcChangeTypeCodeDesc) {
        this.etcChangeTypeCode = etcChangeTypeCode;
        this.etcChangeTypeCodeDesc = etcChangeTypeCodeDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ETC_CHANGE_TYPE_CODE", unique=true, nullable=false, length=1)

    public String getEtcChangeTypeCode() {
        return this.etcChangeTypeCode;
    }
    
    public void setEtcChangeTypeCode(String etcChangeTypeCode) {
        this.etcChangeTypeCode = etcChangeTypeCode;
    }
    
    @Column(name="ETC_CHANGE_TYPE_CODE_DESC", length=90)

    public String getEtcChangeTypeCodeDesc() {
        return this.etcChangeTypeCodeDesc;
    }
    
    public void setEtcChangeTypeCodeDesc(String etcChangeTypeCodeDesc) {
        this.etcChangeTypeCodeDesc = etcChangeTypeCodeDesc;
    }
   








}