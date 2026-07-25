package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rlblwgc0GcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RLBLWGC0_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rlblwgc0GcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rlblwgc0GcnseqnoLinkId id;
     private Byte lblPrty;


    // Constructors

    /** default constructor */
    public Rlblwgc0GcnseqnoLink() {
    }

	/** minimal constructor */
    public Rlblwgc0GcnseqnoLink(Rlblwgc0GcnseqnoLinkId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rlblwgc0GcnseqnoLink(Rlblwgc0GcnseqnoLinkId id, Byte lblPrty) {
        this.id = id;
        this.lblPrty = lblPrty;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="lblWarn", column=@Column(name="LBL_WARN", nullable=false, length=4) ) } )

    public Rlblwgc0GcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rlblwgc0GcnseqnoLinkId id) {
        this.id = id;
    }
    
    @Column(name="LBL_PRTY", precision=2, scale=0)

    public Byte getLblPrty() {
        return this.lblPrty;
    }
    
    public void setLblPrty(Byte lblPrty) {
        this.lblPrty = lblPrty;
    }
   








}