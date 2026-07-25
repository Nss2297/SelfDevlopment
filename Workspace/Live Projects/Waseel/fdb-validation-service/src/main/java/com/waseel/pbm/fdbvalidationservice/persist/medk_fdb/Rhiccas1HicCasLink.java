package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rhiccas1HicCasLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHICCAS1_HIC_CAS_LINK"
    ,schema="MEDK_FDB"
)

public class Rhiccas1HicCasLink  implements java.io.Serializable {


    // Fields    

     private Integer hicSeqn;
     private Integer cas9Tbl;
     private String hic;


    // Constructors

    /** default constructor */
    public Rhiccas1HicCasLink() {
    }

	/** minimal constructor */
    public Rhiccas1HicCasLink(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
    }
    
    /** full constructor */
    public Rhiccas1HicCasLink(Integer hicSeqn, Integer cas9Tbl, String hic) {
        this.hicSeqn = hicSeqn;
        this.cas9Tbl = cas9Tbl;
        this.hic = hic;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="HIC_SEQN", unique=true, nullable=false, precision=6, scale=0)

    public Integer getHicSeqn() {
        return this.hicSeqn;
    }
    
    public void setHicSeqn(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
    }
    
    @Column(name="CAS9_TBL", precision=9, scale=0)

    public Integer getCas9Tbl() {
        return this.cas9Tbl;
    }
    
    public void setCas9Tbl(Integer cas9Tbl) {
        this.cas9Tbl = cas9Tbl;
    }
    
    @Column(name="HIC", length=6)

    public String getHic() {
        return this.hic;
    }
    
    public void setHic(String hic) {
        this.hic = hic;
    }
   








}