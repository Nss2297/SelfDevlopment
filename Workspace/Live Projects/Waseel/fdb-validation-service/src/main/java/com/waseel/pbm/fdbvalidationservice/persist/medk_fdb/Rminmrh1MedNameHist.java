package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rminmrh1MedNameHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMINMRH1_MED_NAME_HIST"
    ,schema="MEDK_FDB"
)

public class Rminmrh1MedNameHist  implements java.io.Serializable {


    // Fields    

     private Rminmrh1MedNameHistId id;
     private Timestamp medNameIdReplEffDt;


    // Constructors

    /** default constructor */
    public Rminmrh1MedNameHist() {
    }

    
    /** full constructor */
    public Rminmrh1MedNameHist(Rminmrh1MedNameHistId id, Timestamp medNameIdReplEffDt) {
        this.id = id;
        this.medNameIdReplEffDt = medNameIdReplEffDt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medReplNameId", column=@Column(name="MED_REPL_NAME_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="medPrevNameId", column=@Column(name="MED_PREV_NAME_ID", nullable=false, precision=8, scale=0) ) } )

    public Rminmrh1MedNameHistId getId() {
        return this.id;
    }
    
    public void setId(Rminmrh1MedNameHistId id) {
        this.id = id;
    }
    
    @Column(name="MED_NAME_ID_REPL_EFF_DT", nullable=false, length=7)

    public Timestamp getMedNameIdReplEffDt() {
        return this.medNameIdReplEffDt;
    }
    
    public void setMedNameIdReplEffDt(Timestamp medNameIdReplEffDt) {
        this.medNameIdReplEffDt = medNameIdReplEffDt;
    }
   








}