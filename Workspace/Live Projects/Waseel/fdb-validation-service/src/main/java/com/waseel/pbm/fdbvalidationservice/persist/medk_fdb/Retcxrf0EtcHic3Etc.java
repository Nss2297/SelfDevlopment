package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retcxrf0EtcHic3Etc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCXRF0_ETC_HIC3_ETC"
    ,schema="MEDK_FDB"
)

public class Retcxrf0EtcHic3Etc  implements java.io.Serializable {


    // Fields    

     private Retcxrf0EtcHic3EtcId id;


    // Constructors

    /** default constructor */
    public Retcxrf0EtcHic3Etc() {
    }

    
    /** full constructor */
    public Retcxrf0EtcHic3Etc(Retcxrf0EtcHic3EtcId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="hic3Seqn", column=@Column(name="HIC3_SEQN", nullable=false, precision=6, scale=0) ) } )

    public Retcxrf0EtcHic3EtcId getId() {
        return this.id;
    }
    
    public void setId(Retcxrf0EtcHic3EtcId id) {
        this.id = id;
    }
   








}