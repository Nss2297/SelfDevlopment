package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rrtgngc0RtdGenGcnseqnoLnk entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RRTGNGC0_RTD_GEN_GCNSEQNO_LNK"
    ,schema="MEDK_FDB"
)

public class Rrtgngc0RtdGenGcnseqnoLnk  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer routedGenId;


    // Constructors

    /** default constructor */
    public Rrtgngc0RtdGenGcnseqnoLnk() {
    }

    
    /** full constructor */
    public Rrtgngc0RtdGenGcnseqnoLnk(Integer gcnSeqno, Integer routedGenId) {
        this.gcnSeqno = gcnSeqno;
        this.routedGenId = routedGenId;
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
    
    @Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
    }
   








}