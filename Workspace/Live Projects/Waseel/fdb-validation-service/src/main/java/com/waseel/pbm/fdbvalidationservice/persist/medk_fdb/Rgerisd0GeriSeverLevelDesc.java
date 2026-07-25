package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rgerisd0GeriSeverLevelDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RGERISD0_GERI_SEVER_LEVEL_DESC"
    ,schema="MEDK_FDB"
)

public class Rgerisd0GeriSeverLevelDesc  implements java.io.Serializable {


    // Fields    

     private String geriSl;
     private String geriSlDesc;


    // Constructors

    /** default constructor */
    public Rgerisd0GeriSeverLevelDesc() {
    }

    
    /** full constructor */
    public Rgerisd0GeriSeverLevelDesc(String geriSl, String geriSlDesc) {
        this.geriSl = geriSl;
        this.geriSlDesc = geriSlDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="GERI_SL", unique=true, nullable=false, length=1)

    public String getGeriSl() {
        return this.geriSl;
    }
    
    public void setGeriSl(String geriSl) {
        this.geriSl = geriSl;
    }
    
    @Column(name="GERI_SL_DESC", nullable=false)

    public String getGeriSlDesc() {
        return this.geriSlDesc;
    }
    
    public void setGeriSlDesc(String geriSlDesc) {
        this.geriSlDesc = geriSlDesc;
    }
   








}