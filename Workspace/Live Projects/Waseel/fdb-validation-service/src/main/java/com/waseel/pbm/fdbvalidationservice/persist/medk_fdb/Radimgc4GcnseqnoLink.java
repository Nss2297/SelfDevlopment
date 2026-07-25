package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Radimgc4GcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIMGC4_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Radimgc4GcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Radimgc4GcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Radimgc4GcnseqnoLink() {
    }

    
    /** full constructor */
    public Radimgc4GcnseqnoLink(Radimgc4GcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="ddiCodex", column=@Column(name="DDI_CODEX", nullable=false, precision=5, scale=0) ) } )

    public Radimgc4GcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Radimgc4GcnseqnoLinkId id) {
        this.id = id;
    }
   








}