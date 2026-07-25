package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rgcnseq4GcnseqnoMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RGCNSEQ4_GCNSEQNO_MSTR"
    ,schema="MEDK_FDB"
)

public class Rgcnseq4GcnseqnoMstr  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private String hic3;
     private Integer hiclSeqno;
     private String gcdf;
     private String gcrt;
     private String str;
     private Byte gtc;
     private Byte tc;
     private String dcc;
     private String gcnseqGi;
     private String gender;
     private Integer hic3Seqn;
     private String str60;


    // Constructors

    /** default constructor */
    public Rgcnseq4GcnseqnoMstr() {
    }

	/** minimal constructor */
    public Rgcnseq4GcnseqnoMstr(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }
    
    /** full constructor */
    public Rgcnseq4GcnseqnoMstr(Integer gcnSeqno, String hic3, Integer hiclSeqno, String gcdf, String gcrt, String str, Byte gtc, Byte tc, String dcc, String gcnseqGi, String gender, Integer hic3Seqn, String str60) {
        this.gcnSeqno = gcnSeqno;
        this.hic3 = hic3;
        this.hiclSeqno = hiclSeqno;
        this.gcdf = gcdf;
        this.gcrt = gcrt;
        this.str = str;
        this.gtc = gtc;
        this.tc = tc;
        this.dcc = dcc;
        this.gcnseqGi = gcnseqGi;
        this.gender = gender;
        this.hic3Seqn = hic3Seqn;
        this.str60 = str60;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="GCN_SEQNO", unique=true, nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }
    
    @Column(name="HIC3", length=3)

    public String getHic3() {
        return this.hic3;
    }
    
    public void setHic3(String hic3) {
        this.hic3 = hic3;
    }
    
    @Column(name="HICL_SEQNO", precision=6, scale=0)

    public Integer getHiclSeqno() {
        return this.hiclSeqno;
    }
    
    public void setHiclSeqno(Integer hiclSeqno) {
        this.hiclSeqno = hiclSeqno;
    }
    
    @Column(name="GCDF", length=2)

    public String getGcdf() {
        return this.gcdf;
    }
    
    public void setGcdf(String gcdf) {
        this.gcdf = gcdf;
    }
    
    @Column(name="GCRT", length=1)

    public String getGcrt() {
        return this.gcrt;
    }
    
    public void setGcrt(String gcrt) {
        this.gcrt = gcrt;
    }
    
    @Column(name="STR", length=10)

    public String getStr() {
        return this.str;
    }
    
    public void setStr(String str) {
        this.str = str;
    }
    
    @Column(name="GTC", precision=2, scale=0)

    public Byte getGtc() {
        return this.gtc;
    }
    
    public void setGtc(Byte gtc) {
        this.gtc = gtc;
    }
    
    @Column(name="TC", precision=2, scale=0)

    public Byte getTc() {
        return this.tc;
    }
    
    public void setTc(Byte tc) {
        this.tc = tc;
    }
    
    @Column(name="DCC", length=1)

    public String getDcc() {
        return this.dcc;
    }
    
    public void setDcc(String dcc) {
        this.dcc = dcc;
    }
    
    @Column(name="GCNSEQ_GI", length=1)

    public String getGcnseqGi() {
        return this.gcnseqGi;
    }
    
    public void setGcnseqGi(String gcnseqGi) {
        this.gcnseqGi = gcnseqGi;
    }
    
    @Column(name="GENDER", length=1)

    public String getGender() {
        return this.gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    @Column(name="HIC3_SEQN", precision=6, scale=0)

    public Integer getHic3Seqn() {
        return this.hic3Seqn;
    }
    
    public void setHic3Seqn(Integer hic3Seqn) {
        this.hic3Seqn = hic3Seqn;
    }
    
    @Column(name="STR60", length=60)

    public String getStr60() {
        return this.str60;
    }
    
    public void setStr60(String str60) {
        this.str60 = str60;
    }
   








}