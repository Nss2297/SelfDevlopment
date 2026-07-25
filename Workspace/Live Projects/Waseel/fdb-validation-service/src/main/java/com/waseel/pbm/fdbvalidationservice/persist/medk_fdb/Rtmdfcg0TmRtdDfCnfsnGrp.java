package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rtmdfcg0TmRtdDfCnfsnGrp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMDFCG0_TM_RTD_DF_CNFSN_GRP"
    ,schema="MEDK_FDB"
)

public class Rtmdfcg0TmRtdDfCnfsnGrp  implements java.io.Serializable {


    // Fields    

     private Rtmdfcg0TmRtdDfCnfsnGrpId id;


    // Constructors

    /** default constructor */
    public Rtmdfcg0TmRtdDfCnfsnGrp() {
    }

    
    /** full constructor */
    public Rtmdfcg0TmRtdDfCnfsnGrp(Rtmdfcg0TmRtdDfCnfsnGrpId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedDosageFormMedId", column=@Column(name="ROUTED_DOSAGE_FORM_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="tmGroupId", column=@Column(name="TM_GROUP_ID", nullable=false, precision=5, scale=0) ) } )

    public Rtmdfcg0TmRtdDfCnfsnGrpId getId() {
        return this.id;
    }
    
    public void setId(Rtmdfcg0TmRtdDfCnfsnGrpId id) {
        this.id = id;
    }
   








}