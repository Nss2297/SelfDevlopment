package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rddimrd0RefCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDIMRD0_REF_CATEGORY"
    ,schema="MEDK_FDB"
)

public class Rddimrd0RefCategory  implements java.io.Serializable {


    // Fields    

     private String iamrefcat;
     private String iamrefcatd;


    // Constructors

    /** default constructor */
    public Rddimrd0RefCategory() {
    }

	/** minimal constructor */
    public Rddimrd0RefCategory(String iamrefcat) {
        this.iamrefcat = iamrefcat;
    }
    
    /** full constructor */
    public Rddimrd0RefCategory(String iamrefcat, String iamrefcatd) {
        this.iamrefcat = iamrefcat;
        this.iamrefcatd = iamrefcatd;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="IAMREFCAT", unique=true, nullable=false, length=1)

    public String getIamrefcat() {
        return this.iamrefcat;
    }
    
    public void setIamrefcat(String iamrefcat) {
        this.iamrefcat = iamrefcat;
    }
    
    @Column(name="IAMREFCATD", length=40)

    public String getIamrefcatd() {
        return this.iamrefcatd;
    }
    
    public void setIamrefcatd(String iamrefcatd) {
        this.iamrefcatd = iamrefcatd;
    }
   








}