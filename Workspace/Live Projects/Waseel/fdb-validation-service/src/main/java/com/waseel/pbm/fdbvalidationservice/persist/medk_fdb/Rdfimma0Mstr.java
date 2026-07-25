package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdfimma0Mstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDFIMMA0_MSTR"
    ,schema="MEDK_FDB"
)

public class Rdfimma0Mstr  implements java.io.Serializable {


    // Fields    

     private Short fdcde;
     private String dname;
     private String fdSl;
     private String result;
     private String fdmsg1;
     private String fdmsg2;


    // Constructors

    /** default constructor */
    public Rdfimma0Mstr() {
    }

	/** minimal constructor */
    public Rdfimma0Mstr(Short fdcde) {
        this.fdcde = fdcde;
    }
    
    /** full constructor */
    public Rdfimma0Mstr(Short fdcde, String dname, String fdSl, String result, String fdmsg1, String fdmsg2) {
        this.fdcde = fdcde;
        this.dname = dname;
        this.fdSl = fdSl;
        this.result = result;
        this.fdmsg1 = fdmsg1;
        this.fdmsg2 = fdmsg2;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="FDCDE", unique=true, nullable=false, precision=3, scale=0)

    public Short getFdcde() {
        return this.fdcde;
    }
    
    public void setFdcde(Short fdcde) {
        this.fdcde = fdcde;
    }
    
    @Column(name="DNAME", length=21)

    public String getDname() {
        return this.dname;
    }
    
    public void setDname(String dname) {
        this.dname = dname;
    }
    
    @Column(name="FD_SL", length=1)

    public String getFdSl() {
        return this.fdSl;
    }
    
    public void setFdSl(String fdSl) {
        this.fdSl = fdSl;
    }
    
    @Column(name="RESULT", length=45)

    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    @Column(name="FDMSG1", length=27)

    public String getFdmsg1() {
        return this.fdmsg1;
    }
    
    public void setFdmsg1(String fdmsg1) {
        this.fdmsg1 = fdmsg1;
    }
    
    @Column(name="FDMSG2", length=27)

    public String getFdmsg2() {
        return this.fdmsg2;
    }
    
    public void setFdmsg2(String fdmsg2) {
        this.fdmsg2 = fdmsg2;
    }
   








}