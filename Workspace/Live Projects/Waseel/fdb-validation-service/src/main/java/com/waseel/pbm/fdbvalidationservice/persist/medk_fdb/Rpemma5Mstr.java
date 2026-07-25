package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rpemma5Mstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEMMA5_MSTR"
    ,schema="MEDK_FDB"
)

public class Rpemma5Mstr  implements java.io.Serializable {


    // Fields    

     private Integer pec;
     private String dgname;
     private String lblmsg1;
     private String lblmsg2;
     private Short pemono;
     private String amacde;
     private String phmxcde;
     private String uspcde;
     private String nardcde;
     private Integer ashpcde3;
     private Short pemonos;
     private Short pemonofra;


    // Constructors

    /** default constructor */
    public Rpemma5Mstr() {
    }

	/** minimal constructor */
    public Rpemma5Mstr(Integer pec) {
        this.pec = pec;
    }
    
    /** full constructor */
    public Rpemma5Mstr(Integer pec, String dgname, String lblmsg1, String lblmsg2, Short pemono, String amacde, String phmxcde, String uspcde, String nardcde, Integer ashpcde3, Short pemonos, Short pemonofra) {
        this.pec = pec;
        this.dgname = dgname;
        this.lblmsg1 = lblmsg1;
        this.lblmsg2 = lblmsg2;
        this.pemono = pemono;
        this.amacde = amacde;
        this.phmxcde = phmxcde;
        this.uspcde = uspcde;
        this.nardcde = nardcde;
        this.ashpcde3 = ashpcde3;
        this.pemonos = pemonos;
        this.pemonofra = pemonofra;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PEC", unique=true, nullable=false, precision=6, scale=0)

    public Integer getPec() {
        return this.pec;
    }
    
    public void setPec(Integer pec) {
        this.pec = pec;
    }
    
    @Column(name="DGNAME", length=30)

    public String getDgname() {
        return this.dgname;
    }
    
    public void setDgname(String dgname) {
        this.dgname = dgname;
    }
    
    @Column(name="LBLMSG1", length=27)

    public String getLblmsg1() {
        return this.lblmsg1;
    }
    
    public void setLblmsg1(String lblmsg1) {
        this.lblmsg1 = lblmsg1;
    }
    
    @Column(name="LBLMSG2", length=27)

    public String getLblmsg2() {
        return this.lblmsg2;
    }
    
    public void setLblmsg2(String lblmsg2) {
        this.lblmsg2 = lblmsg2;
    }
    
    @Column(name="PEMONO", precision=4, scale=0)

    public Short getPemono() {
        return this.pemono;
    }
    
    public void setPemono(Short pemono) {
        this.pemono = pemono;
    }
    
    @Column(name="AMACDE", length=3)

    public String getAmacde() {
        return this.amacde;
    }
    
    public void setAmacde(String amacde) {
        this.amacde = amacde;
    }
    
    @Column(name="PHMXCDE", length=3)

    public String getPhmxcde() {
        return this.phmxcde;
    }
    
    public void setPhmxcde(String phmxcde) {
        this.phmxcde = phmxcde;
    }
    
    @Column(name="USPCDE", length=4)

    public String getUspcde() {
        return this.uspcde;
    }
    
    public void setUspcde(String uspcde) {
        this.uspcde = uspcde;
    }
    
    @Column(name="NARDCDE", length=3)

    public String getNardcde() {
        return this.nardcde;
    }
    
    public void setNardcde(String nardcde) {
        this.nardcde = nardcde;
    }
    
    @Column(name="ASHPCDE3", precision=6, scale=0)

    public Integer getAshpcde3() {
        return this.ashpcde3;
    }
    
    public void setAshpcde3(Integer ashpcde3) {
        this.ashpcde3 = ashpcde3;
    }
    
    @Column(name="PEMONOS", precision=4, scale=0)

    public Short getPemonos() {
        return this.pemonos;
    }
    
    public void setPemonos(Short pemonos) {
        this.pemonos = pemonos;
    }
    
    @Column(name="PEMONOFRA", precision=4, scale=0)

    public Short getPemonofra() {
        return this.pemonofra;
    }
    
    public void setPemonofra(Short pemonofra) {
        this.pemonofra = pemonofra;
    }
   








}