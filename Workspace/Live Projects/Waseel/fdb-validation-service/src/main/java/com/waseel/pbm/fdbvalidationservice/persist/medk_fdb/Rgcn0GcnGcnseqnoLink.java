package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rgcn0GcnGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RGCN0_GCN_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rgcn0GcnGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rgcn0GcnGcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rgcn0GcnGcnseqnoLink() {
    }

    
    /** full constructor */
    public Rgcn0GcnGcnseqnoLink(Rgcn0GcnGcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="gcn", column=@Column(name="GCN", nullable=false, precision=5, scale=0) ) } )

    public Rgcn0GcnGcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rgcn0GcnGcnseqnoLinkId id) {
        this.id = id;
    }
   








}