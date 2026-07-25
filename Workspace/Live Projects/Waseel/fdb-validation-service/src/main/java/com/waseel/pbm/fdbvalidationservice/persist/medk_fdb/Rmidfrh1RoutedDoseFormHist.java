package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rmidfrh1RoutedDoseFormHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIDFRH1_ROUTED_DOSE_FORM_HIST"
    ,schema="MEDK_FDB"
)

public class Rmidfrh1RoutedDoseFormHist  implements java.io.Serializable {


    // Fields    

     private Rmidfrh1RoutedDoseFormHistId id;
     private Timestamp medRoutedDfMedIdRepEfDt;


    // Constructors

    /** default constructor */
    public Rmidfrh1RoutedDoseFormHist() {
    }

	/** minimal constructor */
    public Rmidfrh1RoutedDoseFormHist(Rmidfrh1RoutedDoseFormHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rmidfrh1RoutedDoseFormHist(Rmidfrh1RoutedDoseFormHistId id, Timestamp medRoutedDfMedIdRepEfDt) {
        this.id = id;
        this.medRoutedDfMedIdRepEfDt = medRoutedDfMedIdRepEfDt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medReplRoutedDfMedId", column=@Column(name="MED_REPL_ROUTED_DF_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="medPrevRoutedDfMedId", column=@Column(name="MED_PREV_ROUTED_DF_MED_ID", nullable=false, precision=8, scale=0) ) } )

    public Rmidfrh1RoutedDoseFormHistId getId() {
        return this.id;
    }
    
    public void setId(Rmidfrh1RoutedDoseFormHistId id) {
        this.id = id;
    }
    
    @Column(name="MED_ROUTED_DF_MED_ID_REP_EF_DT", length=7)

    public Timestamp getMedRoutedDfMedIdRepEfDt() {
        return this.medRoutedDfMedIdRepEfDt;
    }
    
    public void setMedRoutedDfMedIdRepEfDt(Timestamp medRoutedDfMedIdRepEfDt) {
        this.medRoutedDfMedIdRepEfDt = medRoutedDfMedIdRepEfDt;
    }
   








}