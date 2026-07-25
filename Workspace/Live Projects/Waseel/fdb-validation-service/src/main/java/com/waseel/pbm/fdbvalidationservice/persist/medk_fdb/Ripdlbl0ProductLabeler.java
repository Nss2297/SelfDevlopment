package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Ripdlbl0ProductLabeler entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RIPDLBL0_PRODUCT_LABELER"
    ,schema="MEDK_FDB"
)

public class Ripdlbl0ProductLabeler  implements java.io.Serializable {


    // Fields    

     private Integer labelerId;
     private String labelerDesc;


    // Constructors

    /** default constructor */
    public Ripdlbl0ProductLabeler() {
    }

    
    /** full constructor */
    public Ripdlbl0ProductLabeler(Integer labelerId, String labelerDesc) {
        this.labelerId = labelerId;
        this.labelerDesc = labelerDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="LABELER_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getLabelerId() {
        return this.labelerId;
    }
    
    public void setLabelerId(Integer labelerId) {
        this.labelerId = labelerId;
    }
    
    @Column(name="LABELER_DESC", nullable=false, length=100)

    public String getLabelerDesc() {
        return this.labelerDesc;
    }
    
    public void setLabelerDesc(String labelerDesc) {
        this.labelerDesc = labelerDesc;
    }
   








}