package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdptgc0GcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDPTGC0_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rdptgc0GcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rdptgc0GcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rdptgc0GcnseqnoLink() {
    }

    
    /** full constructor */
    public Rdptgc0GcnseqnoLink(Rdptgc0GcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="dptClassId", column=@Column(name="DPT_CLASS_ID", nullable=false, precision=8, scale=0) ) } )

    public Rdptgc0GcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rdptgc0GcnseqnoLinkId id) {
        this.id = id;
    }
   








}