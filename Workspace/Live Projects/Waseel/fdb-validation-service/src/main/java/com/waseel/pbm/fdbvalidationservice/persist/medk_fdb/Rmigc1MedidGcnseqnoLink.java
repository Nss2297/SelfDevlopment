package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmigc1MedidGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIGC1_MEDID_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rmigc1MedidGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer medid;


    // Constructors

    /** default constructor */
    public Rmigc1MedidGcnseqnoLink() {
    }

    
    /** full constructor */
    public Rmigc1MedidGcnseqnoLink(Integer gcnSeqno, Integer medid) {
        this.gcnSeqno = gcnSeqno;
        this.medid = medid;
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
    
    @Column(name="MEDID", nullable=false, precision=8, scale=0)

    public Integer getMedid() {
        return this.medid;
    }
    
    public void setMedid(Integer medid) {
        this.medid = medid;
    }
   








}