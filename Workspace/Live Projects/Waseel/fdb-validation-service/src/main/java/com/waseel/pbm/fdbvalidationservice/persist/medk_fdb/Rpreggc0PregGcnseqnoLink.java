package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpreggc0PregGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPREGGC0_PREG_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rpreggc0PregGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rpreggc0PregGcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rpreggc0PregGcnseqnoLink() {
    }

    
    /** full constructor */
    public Rpreggc0PregGcnseqnoLink(Rpreggc0PregGcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="pregCode", column=@Column(name="PREG_CODE", nullable=false, precision=6, scale=0) ) } )

    public Rpreggc0PregGcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rpreggc0PregGcnseqnoLinkId id) {
        this.id = id;
    }
   








}