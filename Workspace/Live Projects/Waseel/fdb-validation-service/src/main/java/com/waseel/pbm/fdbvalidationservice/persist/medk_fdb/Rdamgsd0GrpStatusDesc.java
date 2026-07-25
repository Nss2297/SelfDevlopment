package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdamgsd0GrpStatusDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMGSD0_GRP_STATUS_DESC"
    ,schema="MEDK_FDB"
)

public class Rdamgsd0GrpStatusDesc  implements java.io.Serializable {


    // Fields    

     private Boolean damAlrgnGrpStatusCd;
     private String damAlrgnGrpStatusCdDesc;


    // Constructors

    /** default constructor */
    public Rdamgsd0GrpStatusDesc() {
    }

	/** minimal constructor */
    public Rdamgsd0GrpStatusDesc(Boolean damAlrgnGrpStatusCd) {
        this.damAlrgnGrpStatusCd = damAlrgnGrpStatusCd;
    }
    
    /** full constructor */
    public Rdamgsd0GrpStatusDesc(Boolean damAlrgnGrpStatusCd, String damAlrgnGrpStatusCdDesc) {
        this.damAlrgnGrpStatusCd = damAlrgnGrpStatusCd;
        this.damAlrgnGrpStatusCdDesc = damAlrgnGrpStatusCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DAM_ALRGN_GRP_STATUS_CD", unique=true, nullable=false, precision=1, scale=0)

    public Boolean getDamAlrgnGrpStatusCd() {
        return this.damAlrgnGrpStatusCd;
    }
    
    public void setDamAlrgnGrpStatusCd(Boolean damAlrgnGrpStatusCd) {
        this.damAlrgnGrpStatusCd = damAlrgnGrpStatusCd;
    }
    
    @Column(name="DAM_ALRGN_GRP_STATUS_CD_DESC", length=50)

    public String getDamAlrgnGrpStatusCdDesc() {
        return this.damAlrgnGrpStatusCdDesc;
    }
    
    public void setDamAlrgnGrpStatusCdDesc(String damAlrgnGrpStatusCdDesc) {
        this.damAlrgnGrpStatusCdDesc = damAlrgnGrpStatusCdDesc;
    }
   








}