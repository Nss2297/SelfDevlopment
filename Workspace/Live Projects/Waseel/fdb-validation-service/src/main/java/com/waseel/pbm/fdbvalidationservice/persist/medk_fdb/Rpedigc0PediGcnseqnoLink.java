package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpedigc0PediGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEDIGC0_PEDI_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rpedigc0PediGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rpedigc0PediGcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rpedigc0PediGcnseqnoLink() {
    }

    
    /** full constructor */
    public Rpedigc0PediGcnseqnoLink(Rpedigc0PediGcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="pediCode", column=@Column(name="PEDI_CODE", nullable=false, precision=6, scale=0) ) } )

    public Rpedigc0PediGcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rpedigc0PediGcnseqnoLinkId id) {
        this.id = id;
    }
   








}