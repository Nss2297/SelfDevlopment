package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rfmlitd1IcdCdTypeDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLITD1_ICD_CD_TYPE_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlitd1IcdCdTypeDesc  implements java.io.Serializable {


    // Fields    

     private String icdCdType;
     private String icdCdTypeDesc;


    // Constructors

    /** default constructor */
    public Rfmlitd1IcdCdTypeDesc() {
    }

    
    /** full constructor */
    public Rfmlitd1IcdCdTypeDesc(String icdCdType, String icdCdTypeDesc) {
        this.icdCdType = icdCdType;
        this.icdCdTypeDesc = icdCdTypeDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ICD_CD_TYPE", unique=true, nullable=false, length=2)

    public String getIcdCdType() {
        return this.icdCdType;
    }
    
    public void setIcdCdType(String icdCdType) {
        this.icdCdType = icdCdType;
    }
    
    @Column(name="ICD_CD_TYPE_DESC", nullable=false, length=50)

    public String getIcdCdTypeDesc() {
        return this.icdCdTypeDesc;
    }
    
    public void setIcdCdTypeDesc(String icdCdTypeDesc) {
        this.icdCdTypeDesc = icdCdTypeDesc;
    }
   








}