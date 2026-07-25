package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rtmmicg0TmMedCnfsnGrp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMMICG0_TM_MED_CNFSN_GRP"
    ,schema="MEDK_FDB"
)

public class Rtmmicg0TmMedCnfsnGrp  implements java.io.Serializable {


    // Fields    

     private Rtmmicg0TmMedCnfsnGrpId id;


    // Constructors

    /** default constructor */
    public Rtmmicg0TmMedCnfsnGrp() {
    }

    
    /** full constructor */
    public Rtmmicg0TmMedCnfsnGrp(Rtmmicg0TmMedCnfsnGrpId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medid", column=@Column(name="MEDID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="tmGroupId", column=@Column(name="TM_GROUP_ID", nullable=false, precision=5, scale=0) ) } )

    public Rtmmicg0TmMedCnfsnGrpId getId() {
        return this.id;
    }
    
    public void setId(Rtmmicg0TmMedCnfsnGrpId id) {
        this.id = id;
    }
   








}