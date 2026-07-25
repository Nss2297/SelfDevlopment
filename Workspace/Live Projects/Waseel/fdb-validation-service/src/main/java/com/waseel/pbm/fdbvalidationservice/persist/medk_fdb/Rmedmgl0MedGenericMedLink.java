package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rmedmgl0MedGenericMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMEDMGL0_MED_GENERIC_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rmedmgl0MedGenericMedLink  implements java.io.Serializable {


    // Fields    

     private Rmedmgl0MedGenericMedLinkId id;
     private Timestamp medConceptObsdatec;


    // Constructors

    /** default constructor */
    public Rmedmgl0MedGenericMedLink() {
    }

	/** minimal constructor */
    public Rmedmgl0MedGenericMedLink(Rmedmgl0MedGenericMedLinkId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rmedmgl0MedGenericMedLink(Rmedmgl0MedGenericMedLinkId id, Timestamp medConceptObsdatec) {
        this.id = id;
        this.medConceptObsdatec = medConceptObsdatec;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medConceptId", column=@Column(name="MED_CONCEPT_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="medConceptIdTyp", column=@Column(name="MED_CONCEPT_ID_TYP", nullable=false, precision=1, scale=0) ), 
        @AttributeOverride(name="genericMedConceptId", column=@Column(name="GENERIC_MED_CONCEPT_ID", nullable=false, precision=8, scale=0) ) } )

    public Rmedmgl0MedGenericMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rmedmgl0MedGenericMedLinkId id) {
        this.id = id;
    }
    
    @Column(name="MED_CONCEPT_OBSDATEC", length=7)

    public Timestamp getMedConceptObsdatec() {
        return this.medConceptObsdatec;
    }
    
    public void setMedConceptObsdatec(Timestamp medConceptObsdatec) {
        this.medConceptObsdatec = medConceptObsdatec;
    }
   








}