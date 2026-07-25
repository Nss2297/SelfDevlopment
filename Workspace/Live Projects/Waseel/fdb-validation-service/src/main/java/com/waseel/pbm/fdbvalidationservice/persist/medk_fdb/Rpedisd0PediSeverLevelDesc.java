package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rpedisd0PediSeverLevelDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEDISD0_PEDI_SEVER_LEVEL_DESC"
    ,schema="MEDK_FDB"
)

public class Rpedisd0PediSeverLevelDesc  implements java.io.Serializable {


    // Fields    

     private String pediSl;
     private String pediSlDesc;


    // Constructors

    /** default constructor */
    public Rpedisd0PediSeverLevelDesc() {
    }

    
    /** full constructor */
    public Rpedisd0PediSeverLevelDesc(String pediSl, String pediSlDesc) {
        this.pediSl = pediSl;
        this.pediSlDesc = pediSlDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PEDI_SL", unique=true, nullable=false, length=1)

    public String getPediSl() {
        return this.pediSl;
    }
    
    public void setPediSl(String pediSl) {
        this.pediSl = pediSl;
    }
    
    @Column(name="PEDI_SL_DESC", nullable=false, length=500)

    public String getPediSlDesc() {
        return this.pediSlDesc;
    }
    
    public void setPediSlDesc(String pediSlDesc) {
        this.pediSlDesc = pediSlDesc;
    }
   








}