package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rlactid0EffectsInfantsDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RLACTID0_EFFECTS_INFANTS_DESC"
    ,schema="MEDK_FDB"
)

public class Rlactid0EffectsInfantsDesc  implements java.io.Serializable {


    // Fields    

     private Rlactid0EffectsInfantsDescId id;
     private String lactLctnDesc;


    // Constructors

    /** default constructor */
    public Rlactid0EffectsInfantsDesc() {
    }

    
    /** full constructor */
    public Rlactid0EffectsInfantsDesc(Rlactid0EffectsInfantsDescId id, String lactLctnDesc) {
        this.id = id;
        this.lactLctnDesc = lactLctnDesc;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="lactLctn", column=@Column(name="LACT_LCTN", nullable=false, length=1) ), 
        @AttributeOverride(name="lactLctnsn", column=@Column(name="LACT_LCTNSN", nullable=false, precision=2, scale=0) ) } )

    public Rlactid0EffectsInfantsDescId getId() {
        return this.id;
    }
    
    public void setId(Rlactid0EffectsInfantsDescId id) {
        this.id = id;
    }
    
    @Column(name="LACT_LCTN_DESC", nullable=false, length=60)

    public String getLactLctnDesc() {
        return this.lactLctnDesc;
    }
    
    public void setLactLctnDesc(String lactLctnDesc) {
        this.lactLctnDesc = lactLctnDesc;
    }
   








}