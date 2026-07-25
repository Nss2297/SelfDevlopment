package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rgerigc0GeriGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RGERIGC0_GERI_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rgerigc0GeriGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rgerigc0GeriGcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rgerigc0GeriGcnseqnoLink() {
    }

    
    /** full constructor */
    public Rgerigc0GeriGcnseqnoLink(Rgerigc0GeriGcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="geriCode", column=@Column(name="GERI_CODE", nullable=false, precision=6, scale=0) ) } )

    public Rgerigc0GeriGcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rgerigc0GeriGcnseqnoLinkId id) {
        this.id = id;
    }
   








}