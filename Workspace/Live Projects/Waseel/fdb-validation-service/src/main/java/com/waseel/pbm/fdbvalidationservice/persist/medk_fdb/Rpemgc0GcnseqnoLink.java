package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpemgc0GcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEMGC0_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rpemgc0GcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rpemgc0GcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rpemgc0GcnseqnoLink() {
    }

    
    /** full constructor */
    public Rpemgc0GcnseqnoLink(Rpemgc0GcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="pec", column=@Column(name="PEC", nullable=false, precision=6, scale=0) ) } )

    public Rpemgc0GcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rpemgc0GcnseqnoLinkId id) {
        this.id = id;
    }
   








}