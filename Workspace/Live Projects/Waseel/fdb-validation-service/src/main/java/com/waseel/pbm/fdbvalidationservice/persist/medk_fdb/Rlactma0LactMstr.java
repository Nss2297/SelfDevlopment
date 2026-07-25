package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rlactma0LactMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RLACTMA0_LACT_MSTR"
    ,schema="MEDK_FDB"
)

public class Rlactma0LactMstr  implements java.io.Serializable {


    // Fields    

     private Integer lactCode;
     private String lactDesc;
     private String lactSl;
     private String lactExcrt;
     private String lactLctn;
     private String lactPrctn;


    // Constructors

    /** default constructor */
    public Rlactma0LactMstr() {
    }

	/** minimal constructor */
    public Rlactma0LactMstr(Integer lactCode) {
        this.lactCode = lactCode;
    }
    
    /** full constructor */
    public Rlactma0LactMstr(Integer lactCode, String lactDesc, String lactSl, String lactExcrt, String lactLctn, String lactPrctn) {
        this.lactCode = lactCode;
        this.lactDesc = lactDesc;
        this.lactSl = lactSl;
        this.lactExcrt = lactExcrt;
        this.lactLctn = lactLctn;
        this.lactPrctn = lactPrctn;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="LACT_CODE", unique=true, nullable=false, precision=6, scale=0)

    public Integer getLactCode() {
        return this.lactCode;
    }
    
    public void setLactCode(Integer lactCode) {
        this.lactCode = lactCode;
    }
    
    @Column(name="LACT_DESC", length=40)

    public String getLactDesc() {
        return this.lactDesc;
    }
    
    public void setLactDesc(String lactDesc) {
        this.lactDesc = lactDesc;
    }
    
    @Column(name="LACT_SL", length=1)

    public String getLactSl() {
        return this.lactSl;
    }
    
    public void setLactSl(String lactSl) {
        this.lactSl = lactSl;
    }
    
    @Column(name="LACT_EXCRT", length=1)

    public String getLactExcrt() {
        return this.lactExcrt;
    }
    
    public void setLactExcrt(String lactExcrt) {
        this.lactExcrt = lactExcrt;
    }
    
    @Column(name="LACT_LCTN", length=1)

    public String getLactLctn() {
        return this.lactLctn;
    }
    
    public void setLactLctn(String lactLctn) {
        this.lactLctn = lactLctn;
    }
    
    @Column(name="LACT_PRCTN", length=77)

    public String getLactPrctn() {
        return this.lactPrctn;
    }
    
    public void setLactPrctn(String lactPrctn) {
        this.lactPrctn = lactPrctn;
    }
   








}