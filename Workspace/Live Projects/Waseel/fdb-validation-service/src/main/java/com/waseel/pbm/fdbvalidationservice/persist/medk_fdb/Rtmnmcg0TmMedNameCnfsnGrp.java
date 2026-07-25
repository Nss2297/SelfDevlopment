package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rtmnmcg0TmMedNameCnfsnGrp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMNMCG0_TM_MED_NAME_CNFSN_GRP"
    ,schema="MEDK_FDB"
)

public class Rtmnmcg0TmMedNameCnfsnGrp  implements java.io.Serializable {


    // Fields    

     private Rtmnmcg0TmMedNameCnfsnGrpId id;


    // Constructors

    /** default constructor */
    public Rtmnmcg0TmMedNameCnfsnGrp() {
    }

    
    /** full constructor */
    public Rtmnmcg0TmMedNameCnfsnGrp(Rtmnmcg0TmMedNameCnfsnGrpId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medNameId", column=@Column(name="MED_NAME_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="tmGroupId", column=@Column(name="TM_GROUP_ID", nullable=false, precision=5, scale=0) ) } )

    public Rtmnmcg0TmMedNameCnfsnGrpId getId() {
        return this.id;
    }
    
    public void setId(Rtmnmcg0TmMedNameCnfsnGrpId id) {
        this.id = id;
    }
   








}