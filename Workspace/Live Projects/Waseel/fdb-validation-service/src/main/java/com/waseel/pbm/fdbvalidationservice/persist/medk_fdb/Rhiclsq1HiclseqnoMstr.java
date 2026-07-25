package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rhiclsq1HiclseqnoMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHICLSQ1_HICLSEQNO_MSTR"
    ,schema="MEDK_FDB"
)

public class Rhiclsq1HiclseqnoMstr  implements java.io.Serializable {


    // Fields    

     private Integer hiclSeqno;
     private String gnn;
     private String gnn60;


    // Constructors

    /** default constructor */
    public Rhiclsq1HiclseqnoMstr() {
    }

	/** minimal constructor */
    public Rhiclsq1HiclseqnoMstr(Integer hiclSeqno) {
        this.hiclSeqno = hiclSeqno;
    }
    
    /** full constructor */
    public Rhiclsq1HiclseqnoMstr(Integer hiclSeqno, String gnn, String gnn60) {
        this.hiclSeqno = hiclSeqno;
        this.gnn = gnn;
        this.gnn60 = gnn60;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="HICL_SEQNO", unique=true, nullable=false, precision=6, scale=0)

    public Integer getHiclSeqno() {
        return this.hiclSeqno;
    }
    
    public void setHiclSeqno(Integer hiclSeqno) {
        this.hiclSeqno = hiclSeqno;
    }
    
    @Column(name="GNN", length=30)

    public String getGnn() {
        return this.gnn;
    }
    
    public void setGnn(String gnn) {
        this.gnn = gnn;
    }
    
    @Column(name="GNN60", length=60)

    public String getGnn60() {
        return this.gnn60;
    }
    
    public void setGnn60(String gnn60) {
        this.gnn60 = gnn60;
    }
   








}