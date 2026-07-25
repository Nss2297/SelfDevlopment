package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rrtgn0RoutedGenMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RRTGN0_ROUTED_GEN_MSTR"
    ,schema="MEDK_FDB"
)

public class Rrtgn0RoutedGenMstr  implements java.io.Serializable {


    // Fields    

     private Integer routedGenId;
     private String routedGenDesc;
     private String gcrt;
     private Integer hiclSeqno;
     private String routedGenStatusCd;


    // Constructors

    /** default constructor */
    public Rrtgn0RoutedGenMstr() {
    }

    
    /** full constructor */
    public Rrtgn0RoutedGenMstr(Integer routedGenId, String routedGenDesc, String gcrt, Integer hiclSeqno, String routedGenStatusCd) {
        this.routedGenId = routedGenId;
        this.routedGenDesc = routedGenDesc;
        this.gcrt = gcrt;
        this.hiclSeqno = hiclSeqno;
        this.routedGenStatusCd = routedGenStatusCd;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ROUTED_GEN_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
    }
    
    @Column(name="ROUTED_GEN_DESC", nullable=false, length=100)

    public String getRoutedGenDesc() {
        return this.routedGenDesc;
    }
    
    public void setRoutedGenDesc(String routedGenDesc) {
        this.routedGenDesc = routedGenDesc;
    }
    
    @Column(name="GCRT", nullable=false, length=1)

    public String getGcrt() {
        return this.gcrt;
    }
    
    public void setGcrt(String gcrt) {
        this.gcrt = gcrt;
    }
    
    @Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getHiclSeqno() {
        return this.hiclSeqno;
    }
    
    public void setHiclSeqno(Integer hiclSeqno) {
        this.hiclSeqno = hiclSeqno;
    }
    
    @Column(name="ROUTED_GEN_STATUS_CD", nullable=false, length=1)

    public String getRoutedGenStatusCd() {
        return this.routedGenStatusCd;
    }
    
    public void setRoutedGenStatusCd(String routedGenStatusCd) {
        this.routedGenStatusCd = routedGenStatusCd;
    }
   








}