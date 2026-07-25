package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rtmrmcg0TmRtdMedCnfsnGrp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMRMCG0_TM_RTD_MED_CNFSN_GRP"
    ,schema="MEDK_FDB"
)

public class Rtmrmcg0TmRtdMedCnfsnGrp  implements java.io.Serializable {


    // Fields    

     private Rtmrmcg0TmRtdMedCnfsnGrpId id;


    // Constructors

    /** default constructor */
    public Rtmrmcg0TmRtdMedCnfsnGrp() {
    }

    
    /** full constructor */
    public Rtmrmcg0TmRtdMedCnfsnGrp(Rtmrmcg0TmRtdMedCnfsnGrpId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="tmGroupId", column=@Column(name="TM_GROUP_ID", nullable=false, precision=5, scale=0) ) } )

    public Rtmrmcg0TmRtdMedCnfsnGrpId getId() {
        return this.id;
    }
    
    public void setId(Rtmrmcg0TmRtdMedCnfsnGrpId id) {
        this.id = id;
    }
   








}