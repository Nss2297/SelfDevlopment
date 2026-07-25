package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rlactgc0LactGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RLACTGC0_LACT_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rlactgc0LactGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rlactgc0LactGcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rlactgc0LactGcnseqnoLink() {
    }

    
    /** full constructor */
    public Rlactgc0LactGcnseqnoLink(Rlactgc0LactGcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="lactCode", column=@Column(name="LACT_CODE", nullable=false, precision=6, scale=0) ) } )

    public Rlactgc0LactGcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rlactgc0LactGcnseqnoLinkId id) {
        this.id = id;
    }
   








}