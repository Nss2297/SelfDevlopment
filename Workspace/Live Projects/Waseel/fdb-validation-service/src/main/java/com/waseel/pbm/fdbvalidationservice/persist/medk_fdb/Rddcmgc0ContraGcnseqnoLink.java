package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rddcmgc0ContraGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDCMGC0_CONTRA_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rddcmgc0ContraGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rddcmgc0ContraGcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rddcmgc0ContraGcnseqnoLink() {
    }

    
    /** full constructor */
    public Rddcmgc0ContraGcnseqnoLink(Rddcmgc0ContraGcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="ddxcn", column=@Column(name="DDXCN", nullable=false, precision=5, scale=0) ) } )

    public Rddcmgc0ContraGcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rddcmgc0ContraGcnseqnoLinkId id) {
        this.id = id;
    }
   








}