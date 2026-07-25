package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpemogc0MonoGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEMOGC0_MONO_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rpemogc0MonoGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rpemogc0MonoGcnseqnoLinkId id;


    // Constructors

    /** default constructor */
    public Rpemogc0MonoGcnseqnoLink() {
    }

    
    /** full constructor */
    public Rpemogc0MonoGcnseqnoLink(Rpemogc0MonoGcnseqnoLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="pemono", column=@Column(name="PEMONO", nullable=false, precision=4, scale=0) ) } )

    public Rpemogc0MonoGcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rpemogc0MonoGcnseqnoLinkId id) {
        this.id = id;
    }
   








}