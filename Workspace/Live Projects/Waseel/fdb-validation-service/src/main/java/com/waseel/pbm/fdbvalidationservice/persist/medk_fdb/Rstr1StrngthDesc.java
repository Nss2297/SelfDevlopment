package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rstr1StrngthDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RSTR1_STRNGTH_DESC"
    ,schema="MEDK_FDB"
)

public class Rstr1StrngthDesc  implements java.io.Serializable {


    // Fields    

     private String str60;
     private Double strnum;
     private Double volnum;
     private String strun50;
     private String volun50;


    // Constructors

    /** default constructor */
    public Rstr1StrngthDesc() {
    }

	/** minimal constructor */
    public Rstr1StrngthDesc(String str60) {
        this.str60 = str60;
    }
    
    /** full constructor */
    public Rstr1StrngthDesc(String str60, Double strnum, Double volnum, String strun50, String volun50) {
        this.str60 = str60;
        this.strnum = strnum;
        this.volnum = volnum;
        this.strun50 = strun50;
        this.volun50 = volun50;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="STR60", unique=true, nullable=false, length=60)

    public String getStr60() {
        return this.str60;
    }
    
    public void setStr60(String str60) {
        this.str60 = str60;
    }
    
    @Column(name="STRNUM", precision=11, scale=3)

    public Double getStrnum() {
        return this.strnum;
    }
    
    public void setStrnum(Double strnum) {
        this.strnum = strnum;
    }
    
    @Column(name="VOLNUM", precision=7, scale=3)

    public Double getVolnum() {
        return this.volnum;
    }
    
    public void setVolnum(Double volnum) {
        this.volnum = volnum;
    }
    
    @Column(name="STRUN50", length=50)

    public String getStrun50() {
        return this.strun50;
    }
    
    public void setStrun50(String strun50) {
        this.strun50 = strun50;
    }
    
    @Column(name="VOLUN50", length=50)

    public String getVolun50() {
        return this.volun50;
    }
    
    public void setVolun50(String volun50) {
        this.volun50 = volun50;
    }
   








}