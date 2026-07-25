package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rmedmhl0MedHiclseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMEDMHL0_MED_HICLSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rmedmhl0MedHiclseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rmedmhl0MedHiclseqnoLinkId id;
     private Timestamp medConceptObsdatec;


    // Constructors

    /** default constructor */
    public Rmedmhl0MedHiclseqnoLink() {
    }

	/** minimal constructor */
    public Rmedmhl0MedHiclseqnoLink(Rmedmhl0MedHiclseqnoLinkId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rmedmhl0MedHiclseqnoLink(Rmedmhl0MedHiclseqnoLinkId id, Timestamp medConceptObsdatec) {
        this.id = id;
        this.medConceptObsdatec = medConceptObsdatec;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="medConceptId", column=@Column(name="MED_CONCEPT_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="medConceptIdTyp", column=@Column(name="MED_CONCEPT_ID_TYP", nullable=false, precision=1, scale=0) ), 
        @AttributeOverride(name="hiclSeqno", column=@Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="medConceptHiclSrcCd", column=@Column(name="MED_CONCEPT_HICL_SRC_CD", nullable=false, precision=1, scale=0) ) } )

    public Rmedmhl0MedHiclseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rmedmhl0MedHiclseqnoLinkId id) {
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