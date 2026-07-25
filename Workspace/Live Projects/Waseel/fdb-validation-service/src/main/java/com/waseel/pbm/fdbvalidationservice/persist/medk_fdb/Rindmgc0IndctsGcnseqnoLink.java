package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rindmgc0IndctsGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RINDMGC0_INDCTS_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rindmgc0IndctsGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rindmgc0IndctsGcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rindmgc0IndctsGcnseqnoLink() {
    }

    
    /** full constructor */
    public Rindmgc0IndctsGcnseqnoLink(Rindmgc0IndctsGcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="indcts", column=@Column(name="INDCTS", nullable=false, precision=5, scale=0) ) } )

    public Rindmgc0IndctsGcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rindmgc0IndctsGcnseqnoLinkId id) {
        this.id = id;
    }
   








}