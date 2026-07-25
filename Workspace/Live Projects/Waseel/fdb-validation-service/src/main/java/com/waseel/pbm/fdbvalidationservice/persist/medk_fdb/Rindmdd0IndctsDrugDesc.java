package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rindmdd0IndctsDrugDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RINDMDD0_INDCTS_DRUG_DESC"
    ,schema="MEDK_FDB"
)

public class Rindmdd0IndctsDrugDesc  implements java.io.Serializable {


    // Fields    

     private Integer indcts;
     private String indctsDrugDesc;


    // Constructors

    /** default constructor */
    public Rindmdd0IndctsDrugDesc() {
    }

	/** minimal constructor */
    public Rindmdd0IndctsDrugDesc(Integer indcts) {
        this.indcts = indcts;
    }
    
    /** full constructor */
    public Rindmdd0IndctsDrugDesc(Integer indcts, String indctsDrugDesc) {
        this.indcts = indcts;
        this.indctsDrugDesc = indctsDrugDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="INDCTS", unique=true, nullable=false, precision=5, scale=0)

    public Integer getIndcts() {
        return this.indcts;
    }
    
    public void setIndcts(Integer indcts) {
        this.indcts = indcts;
    }
    
    @Column(name="INDCTS_DRUG_DESC", length=100)

    public String getIndctsDrugDesc() {
        return this.indctsDrugDesc;
    }
    
    public void setIndctsDrugDesc(String indctsDrugDesc) {
        this.indctsDrugDesc = indctsDrugDesc;
    }
   








}