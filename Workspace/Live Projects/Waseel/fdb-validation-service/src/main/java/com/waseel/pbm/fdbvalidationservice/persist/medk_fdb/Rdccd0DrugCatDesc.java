package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdccd0DrugCatDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDCCD0_DRUG_CAT_DESC"
    ,schema="MEDK_FDB"
)

public class Rdccd0DrugCatDesc  implements java.io.Serializable {


    // Fields    

     private String dcc;
     private String dccDesc;


    // Constructors

    /** default constructor */
    public Rdccd0DrugCatDesc() {
    }

	/** minimal constructor */
    public Rdccd0DrugCatDesc(String dcc) {
        this.dcc = dcc;
    }
    
    /** full constructor */
    public Rdccd0DrugCatDesc(String dcc, String dccDesc) {
        this.dcc = dcc;
        this.dccDesc = dccDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DCC", unique=true, nullable=false, length=1)

    public String getDcc() {
        return this.dcc;
    }
    
    public void setDcc(String dcc) {
        this.dcc = dcc;
    }
    
    @Column(name="DCC_DESC", length=40)

    public String getDccDesc() {
        return this.dccDesc;
    }
    
    public void setDccDesc(String dccDesc) {
        this.dccDesc = dccDesc;
    }
   








}