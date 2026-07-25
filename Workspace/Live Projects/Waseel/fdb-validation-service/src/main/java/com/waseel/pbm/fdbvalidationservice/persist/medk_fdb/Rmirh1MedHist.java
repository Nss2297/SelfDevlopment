package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rmirh1MedHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIRH1_MED_HIST"
    ,schema="MEDK_FDB"
)

public class Rmirh1MedHist  implements java.io.Serializable {


    // Fields    

     private Rmirh1MedHistId id;
     private Timestamp medMedidReplEffDt;


    // Constructors

    /** default constructor */
    public Rmirh1MedHist() {
    }

    
    /** full constructor */
    public Rmirh1MedHist(Rmirh1MedHistId id, Timestamp medMedidReplEffDt) {
        this.id = id;
        this.medMedidReplEffDt = medMedidReplEffDt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medReplMedid", column=@Column(name="MED_REPL_MEDID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="medPrevMedid", column=@Column(name="MED_PREV_MEDID", nullable=false, precision=8, scale=0) ) } )

    public Rmirh1MedHistId getId() {
        return this.id;
    }
    
    public void setId(Rmirh1MedHistId id) {
        this.id = id;
    }
    
    @Column(name="MED_MEDID_REPL_EFF_DT", nullable=false, length=7)

    public Timestamp getMedMedidReplEffDt() {
        return this.medMedidReplEffDt;
    }
    
    public void setMedMedidReplEffDt(Timestamp medMedidReplEffDt) {
        this.medMedidReplEffDt = medMedidReplEffDt;
    }
   








}