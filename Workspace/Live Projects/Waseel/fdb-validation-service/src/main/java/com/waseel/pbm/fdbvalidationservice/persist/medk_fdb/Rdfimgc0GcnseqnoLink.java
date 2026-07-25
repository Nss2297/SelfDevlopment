package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdfimgc0GcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDFIMGC0_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rdfimgc0GcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rdfimgc0GcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rdfimgc0GcnseqnoLink() {
    }

    
    /** full constructor */
    public Rdfimgc0GcnseqnoLink(Rdfimgc0GcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="fdcde", column=@Column(name="FDCDE", nullable=false, precision=3, scale=0) ) } )

    public Rdfimgc0GcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rdfimgc0GcnseqnoLinkId id) {
        this.id = id;
    }
   








}