package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdamagd1AlrgnGrpDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMAGD1_ALRGN_GRP_DESC"
    ,schema="MEDK_FDB"
)

public class Rdamagd1AlrgnGrpDesc  implements java.io.Serializable {


    // Fields    

     private Integer damAlrgnGrp;
     private String damAlrgnGrpDesc;
     private Boolean damGrpPotentiallyInactvInd;
     private Boolean damAlrgnGrpStatusCd;


    // Constructors

    /** default constructor */
    public Rdamagd1AlrgnGrpDesc() {
    }

	/** minimal constructor */
    public Rdamagd1AlrgnGrpDesc(Integer damAlrgnGrp) {
        this.damAlrgnGrp = damAlrgnGrp;
    }
    
    /** full constructor */
    public Rdamagd1AlrgnGrpDesc(Integer damAlrgnGrp, String damAlrgnGrpDesc, Boolean damGrpPotentiallyInactvInd, Boolean damAlrgnGrpStatusCd) {
        this.damAlrgnGrp = damAlrgnGrp;
        this.damAlrgnGrpDesc = damAlrgnGrpDesc;
        this.damGrpPotentiallyInactvInd = damGrpPotentiallyInactvInd;
        this.damAlrgnGrpStatusCd = damAlrgnGrpStatusCd;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DAM_ALRGN_GRP", unique=true, nullable=false, precision=6, scale=0)

    public Integer getDamAlrgnGrp() {
        return this.damAlrgnGrp;
    }
    
    public void setDamAlrgnGrp(Integer damAlrgnGrp) {
        this.damAlrgnGrp = damAlrgnGrp;
    }
    
    @Column(name="DAM_ALRGN_GRP_DESC", length=50)

    public String getDamAlrgnGrpDesc() {
        return this.damAlrgnGrpDesc;
    }
    
    public void setDamAlrgnGrpDesc(String damAlrgnGrpDesc) {
        this.damAlrgnGrpDesc = damAlrgnGrpDesc;
    }
    
    @Column(name="DAM_GRP_POTENTIALLY_INACTV_IND", precision=1, scale=0)

    public Boolean getDamGrpPotentiallyInactvInd() {
        return this.damGrpPotentiallyInactvInd;
    }
    
    public void setDamGrpPotentiallyInactvInd(Boolean damGrpPotentiallyInactvInd) {
        this.damGrpPotentiallyInactvInd = damGrpPotentiallyInactvInd;
    }
    
    @Column(name="DAM_ALRGN_GRP_STATUS_CD", precision=1, scale=0)

    public Boolean getDamAlrgnGrpStatusCd() {
        return this.damAlrgnGrpStatusCd;
    }
    
    public void setDamAlrgnGrpStatusCd(Boolean damAlrgnGrpStatusCd) {
        this.damAlrgnGrpStatusCd = damAlrgnGrpStatusCd;
    }
   








}