package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdamcsd1XsensitAllergyDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMCSD1_XSENSIT_ALLERGY_DESC"
    ,schema="MEDK_FDB"
)

public class Rdamcsd1XsensitAllergyDesc  implements java.io.Serializable {


    // Fields    

     private Short damAlrgnXsense;
     private String damAlrgnXsenseDesc;
     private Boolean damXsensePotentialInctvInd;
     private Boolean damAlrgnXsenseStatusCd;


    // Constructors

    /** default constructor */
    public Rdamcsd1XsensitAllergyDesc() {
    }

	/** minimal constructor */
    public Rdamcsd1XsensitAllergyDesc(Short damAlrgnXsense) {
        this.damAlrgnXsense = damAlrgnXsense;
    }
    
    /** full constructor */
    public Rdamcsd1XsensitAllergyDesc(Short damAlrgnXsense, String damAlrgnXsenseDesc, Boolean damXsensePotentialInctvInd, Boolean damAlrgnXsenseStatusCd) {
        this.damAlrgnXsense = damAlrgnXsense;
        this.damAlrgnXsenseDesc = damAlrgnXsenseDesc;
        this.damXsensePotentialInctvInd = damXsensePotentialInctvInd;
        this.damAlrgnXsenseStatusCd = damAlrgnXsenseStatusCd;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DAM_ALRGN_XSENSE", unique=true, nullable=false, precision=4, scale=0)

    public Short getDamAlrgnXsense() {
        return this.damAlrgnXsense;
    }
    
    public void setDamAlrgnXsense(Short damAlrgnXsense) {
        this.damAlrgnXsense = damAlrgnXsense;
    }
    
    @Column(name="DAM_ALRGN_XSENSE_DESC", length=50)

    public String getDamAlrgnXsenseDesc() {
        return this.damAlrgnXsenseDesc;
    }
    
    public void setDamAlrgnXsenseDesc(String damAlrgnXsenseDesc) {
        this.damAlrgnXsenseDesc = damAlrgnXsenseDesc;
    }
    
    @Column(name="DAM_XSENSE_POTENTIAL_INCTV_IND", precision=1, scale=0)

    public Boolean getDamXsensePotentialInctvInd() {
        return this.damXsensePotentialInctvInd;
    }
    
    public void setDamXsensePotentialInctvInd(Boolean damXsensePotentialInctvInd) {
        this.damXsensePotentialInctvInd = damXsensePotentialInctvInd;
    }
    
    @Column(name="DAM_ALRGN_XSENSE_STATUS_CD", precision=1, scale=0)

    public Boolean getDamAlrgnXsenseStatusCd() {
        return this.damAlrgnXsenseStatusCd;
    }
    
    public void setDamAlrgnXsenseStatusCd(Boolean damAlrgnXsenseStatusCd) {
        this.damAlrgnXsenseStatusCd = damAlrgnXsenseStatusCd;
    }
   








}