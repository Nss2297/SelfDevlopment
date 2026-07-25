package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdamxsd0XsenseStatusDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMXSD0_XSENSE_STATUS_DESC"
    ,schema="MEDK_FDB"
)

public class Rdamxsd0XsenseStatusDesc  implements java.io.Serializable {


    // Fields    

     private Boolean damAlrgnXsenseStatusCd;
     private String damAlrgnXsenseStatusCdDsc;


    // Constructors

    /** default constructor */
    public Rdamxsd0XsenseStatusDesc() {
    }

	/** minimal constructor */
    public Rdamxsd0XsenseStatusDesc(Boolean damAlrgnXsenseStatusCd) {
        this.damAlrgnXsenseStatusCd = damAlrgnXsenseStatusCd;
    }
    
    /** full constructor */
    public Rdamxsd0XsenseStatusDesc(Boolean damAlrgnXsenseStatusCd, String damAlrgnXsenseStatusCdDsc) {
        this.damAlrgnXsenseStatusCd = damAlrgnXsenseStatusCd;
        this.damAlrgnXsenseStatusCdDsc = damAlrgnXsenseStatusCdDsc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DAM_ALRGN_XSENSE_STATUS_CD", unique=true, nullable=false, precision=1, scale=0)

    public Boolean getDamAlrgnXsenseStatusCd() {
        return this.damAlrgnXsenseStatusCd;
    }
    
    public void setDamAlrgnXsenseStatusCd(Boolean damAlrgnXsenseStatusCd) {
        this.damAlrgnXsenseStatusCd = damAlrgnXsenseStatusCd;
    }
    
    @Column(name="DAM_ALRGN_XSENSE_STATUS_CD_DSC", length=50)

    public String getDamAlrgnXsenseStatusCdDsc() {
        return this.damAlrgnXsenseStatusCdDsc;
    }
    
    public void setDamAlrgnXsenseStatusCdDsc(String damAlrgnXsenseStatusCdDsc) {
        this.damAlrgnXsenseStatusCdDsc = damAlrgnXsenseStatusCdDsc;
    }
   








}