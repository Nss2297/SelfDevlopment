package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcmd0MathProcessDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCMD0_MATH_PROCESS_DESC"
    ,schema="MEDK_FDB"
)

public class Rdrcmd0MathProcessDesc  implements java.io.Serializable {


    // Fields    

     private String dcnvMthi;
     private String dcnvMthiDesc;


    // Constructors

    /** default constructor */
    public Rdrcmd0MathProcessDesc() {
    }

	/** minimal constructor */
    public Rdrcmd0MathProcessDesc(String dcnvMthi) {
        this.dcnvMthi = dcnvMthi;
    }
    
    /** full constructor */
    public Rdrcmd0MathProcessDesc(String dcnvMthi, String dcnvMthiDesc) {
        this.dcnvMthi = dcnvMthi;
        this.dcnvMthiDesc = dcnvMthiDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DCNV_MTHI", unique=true, nullable=false, length=1)

    public String getDcnvMthi() {
        return this.dcnvMthi;
    }
    
    public void setDcnvMthi(String dcnvMthi) {
        this.dcnvMthi = dcnvMthi;
    }
    
    @Column(name="DCNV_MTHI_DESC", length=50)

    public String getDcnvMthiDesc() {
        return this.dcnvMthiDesc;
    }
    
    public void setDcnvMthiDesc(String dcnvMthiDesc) {
        this.dcnvMthiDesc = dcnvMthiDesc;
    }
   








}