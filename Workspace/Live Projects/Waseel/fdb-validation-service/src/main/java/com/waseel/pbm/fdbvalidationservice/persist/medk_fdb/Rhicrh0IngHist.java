package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rhicrh0IngHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHICRH0_ING_HIST"
    ,schema="MEDK_FDB"
)

public class Rhicrh0IngHist  implements java.io.Serializable {


    // Fields    

     private Rhicrh0IngHistId id;
     private Timestamp hicReplEffDt;


    // Constructors

    /** default constructor */
    public Rhicrh0IngHist() {
    }

	/** minimal constructor */
    public Rhicrh0IngHist(Rhicrh0IngHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rhicrh0IngHist(Rhicrh0IngHistId id, Timestamp hicReplEffDt) {
        this.id = id;
        this.hicReplEffDt = hicReplEffDt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="replHicSeqn", column=@Column(name="REPL_HIC_SEQN", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="prevHicSeqn", column=@Column(name="PREV_HIC_SEQN", nullable=false, precision=6, scale=0) ) } )

    public Rhicrh0IngHistId getId() {
        return this.id;
    }
    
    public void setId(Rhicrh0IngHistId id) {
        this.id = id;
    }
    
    @Column(name="HIC_REPL_EFF_DT", length=7)

    public Timestamp getHicReplEffDt() {
        return this.hicReplEffDt;
    }
    
    public void setHicReplEffDt(Timestamp hicReplEffDt) {
        this.hicReplEffDt = hicReplEffDt;
    }
   








}