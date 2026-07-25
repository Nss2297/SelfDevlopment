package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rpedima1PediMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEDIMA1_PEDI_MSTR"
    ,schema="MEDK_FDB"
)

public class Rpedima1PediMstr  implements java.io.Serializable {


    // Fields    

     private Integer pediCode;
     private String pediDesc;
     private String pediSl;
     private Short pediMinag;
     private Short pediMaxag;
     private String pediNarrative;


    // Constructors

    /** default constructor */
    public Rpedima1PediMstr() {
    }

	/** minimal constructor */
    public Rpedima1PediMstr(Integer pediCode, String pediDesc, String pediSl, Short pediMinag, Short pediMaxag) {
        this.pediCode = pediCode;
        this.pediDesc = pediDesc;
        this.pediSl = pediSl;
        this.pediMinag = pediMinag;
        this.pediMaxag = pediMaxag;
    }
    
    /** full constructor */
    public Rpedima1PediMstr(Integer pediCode, String pediDesc, String pediSl, Short pediMinag, Short pediMaxag, String pediNarrative) {
        this.pediCode = pediCode;
        this.pediDesc = pediDesc;
        this.pediSl = pediSl;
        this.pediMinag = pediMinag;
        this.pediMaxag = pediMaxag;
        this.pediNarrative = pediNarrative;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PEDI_CODE", unique=true, nullable=false, precision=8, scale=0)

    public Integer getPediCode() {
        return this.pediCode;
    }
    
    public void setPediCode(Integer pediCode) {
        this.pediCode = pediCode;
    }
    
    @Column(name="PEDI_DESC", nullable=false, length=34)

    public String getPediDesc() {
        return this.pediDesc;
    }
    
    public void setPediDesc(String pediDesc) {
        this.pediDesc = pediDesc;
    }
    
    @Column(name="PEDI_SL", nullable=false, length=1)

    public String getPediSl() {
        return this.pediSl;
    }
    
    public void setPediSl(String pediSl) {
        this.pediSl = pediSl;
    }
    
    @Column(name="PEDI_MINAG", nullable=false, precision=4, scale=0)

    public Short getPediMinag() {
        return this.pediMinag;
    }
    
    public void setPediMinag(Short pediMinag) {
        this.pediMinag = pediMinag;
    }
    
    @Column(name="PEDI_MAXAG", nullable=false, precision=4, scale=0)

    public Short getPediMaxag() {
        return this.pediMaxag;
    }
    
    public void setPediMaxag(Short pediMaxag) {
        this.pediMaxag = pediMaxag;
    }
    
    @Column(name="PEDI_NARRATIVE", length=500)

    public String getPediNarrative() {
        return this.pediNarrative;
    }
    
    public void setPediNarrative(String pediNarrative) {
        this.pediNarrative = pediNarrative;
    }
   








}