package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rmirmrh1RoutedMedHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIRMRH1_ROUTED_MED_HIST"
    ,schema="MEDK_FDB"
)

public class Rmirmrh1RoutedMedHist  implements java.io.Serializable {


    // Fields    

     private Rmirmrh1RoutedMedHistId id;
     private Timestamp medRoutedMedIdReplEffDt;


    // Constructors

    /** default constructor */
    public Rmirmrh1RoutedMedHist() {
    }

    
    /** full constructor */
    public Rmirmrh1RoutedMedHist(Rmirmrh1RoutedMedHistId id, Timestamp medRoutedMedIdReplEffDt) {
        this.id = id;
        this.medRoutedMedIdReplEffDt = medRoutedMedIdReplEffDt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medReplRoutedMedId", column=@Column(name="MED_REPL_ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="medPrevRoutedMedId", column=@Column(name="MED_PREV_ROUTED_MED_ID", nullable=false, precision=8, scale=0) ) } )

    public Rmirmrh1RoutedMedHistId getId() {
        return this.id;
    }
    
    public void setId(Rmirmrh1RoutedMedHistId id) {
        this.id = id;
    }
    
    @Column(name="MED_ROUTED_MED_ID_REPL_EFF_DT", nullable=false, length=7)

    public Timestamp getMedRoutedMedIdReplEffDt() {
        return this.medRoutedMedIdReplEffDt;
    }
    
    public void setMedRoutedMedIdReplEffDt(Timestamp medRoutedMedIdReplEffDt) {
        this.medRoutedMedIdReplEffDt = medRoutedMedIdReplEffDt;
    }
   








}