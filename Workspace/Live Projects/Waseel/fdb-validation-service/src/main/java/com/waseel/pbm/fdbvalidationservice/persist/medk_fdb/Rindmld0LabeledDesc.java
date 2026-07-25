package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rindmld0LabeledDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RINDMLD0_LABELED_DESC"
    ,schema="MEDK_FDB"
)

public class Rindmld0LabeledDesc  implements java.io.Serializable {


    // Fields    

     private String indctsLbl;
     private String indlbldesc;


    // Constructors

    /** default constructor */
    public Rindmld0LabeledDesc() {
    }

	/** minimal constructor */
    public Rindmld0LabeledDesc(String indctsLbl) {
        this.indctsLbl = indctsLbl;
    }
    
    /** full constructor */
    public Rindmld0LabeledDesc(String indctsLbl, String indlbldesc) {
        this.indctsLbl = indctsLbl;
        this.indlbldesc = indlbldesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="INDCTS_LBL", unique=true, nullable=false, length=1)

    public String getIndctsLbl() {
        return this.indctsLbl;
    }
    
    public void setIndctsLbl(String indctsLbl) {
        this.indctsLbl = indctsLbl;
    }
    
    @Column(name="INDLBLDESC", length=90)

    public String getIndlbldesc() {
        return this.indlbldesc;
    }
    
    public void setIndlbldesc(String indlbldesc) {
        this.indlbldesc = indlbldesc;
    }
   








}