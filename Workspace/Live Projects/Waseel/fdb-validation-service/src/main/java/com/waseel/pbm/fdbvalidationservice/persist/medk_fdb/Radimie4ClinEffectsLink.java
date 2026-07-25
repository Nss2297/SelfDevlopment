package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Radimie4ClinEffectsLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIMIE4_CLIN_EFFECTS_LINK"
    ,schema="MEDK_FDB"
)

public class Radimie4ClinEffectsLink  implements java.io.Serializable {


    // Fields    

     private Radimie4ClinEffectsLinkId id;


    // Constructors

    /** default constructor */
    public Radimie4ClinEffectsLink() {
    }

    
    /** full constructor */
    public Radimie4ClinEffectsLink(Radimie4ClinEffectsLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="ddiCodex", column=@Column(name="DDI_CODEX", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="adiEfftc", column=@Column(name="ADI_EFFTC", nullable=false, length=3) ) } )

    public Radimie4ClinEffectsLinkId getId() {
        return this.id;
    }
    
    public void setId(Radimie4ClinEffectsLinkId id) {
        this.id = id;
    }
   








}