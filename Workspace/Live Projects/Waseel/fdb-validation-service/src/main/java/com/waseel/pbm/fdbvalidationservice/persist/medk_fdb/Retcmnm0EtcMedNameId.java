package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retcmnm0EtcMedNameId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCMNM0_ETC_MED_NAME_ID"
    ,schema="MEDK_FDB"
)

public class Retcmnm0EtcMedNameId  implements java.io.Serializable {


    // Fields    

     private Retcmnm0EtcMedNameIdId id;


    // Constructors

    /** default constructor */
    public Retcmnm0EtcMedNameId() {
    }

    
    /** full constructor */
    public Retcmnm0EtcMedNameId(Retcmnm0EtcMedNameIdId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medNameId", column=@Column(name="MED_NAME_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ) } )

    public Retcmnm0EtcMedNameIdId getId() {
        return this.id;
    }
    
    public void setId(Retcmnm0EtcMedNameIdId id) {
        this.id = id;
    }
   








}