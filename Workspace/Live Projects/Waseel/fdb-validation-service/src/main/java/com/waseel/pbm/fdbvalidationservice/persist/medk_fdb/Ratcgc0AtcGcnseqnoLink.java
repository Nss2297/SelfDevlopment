package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Ratcgc0AtcGcnseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RATCGC0_ATC_GCNSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Ratcgc0AtcGcnseqnoLink  implements java.io.Serializable {


    // Fields    

     private Ratcgc0AtcGcnseqnoLinkId id;
     private Integer atcVer;


    // Constructors

    /** default constructor */
    public Ratcgc0AtcGcnseqnoLink() {
    }

	/** minimal constructor */
    public Ratcgc0AtcGcnseqnoLink(Ratcgc0AtcGcnseqnoLinkId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Ratcgc0AtcGcnseqnoLink(Ratcgc0AtcGcnseqnoLinkId id, Integer atcVer) {
        this.id = id;
        this.atcVer = atcVer;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="atc", column=@Column(name="ATC", nullable=false, length=7) ) } )

    public Ratcgc0AtcGcnseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Ratcgc0AtcGcnseqnoLinkId id) {
        this.id = id;
    }
    
    @Column(name="ATC_VER", precision=6, scale=0)

    public Integer getAtcVer() {
        return this.atcVer;
    }
    
    public void setAtcVer(Integer atcVer) {
        this.atcVer = atcVer;
    }
   








}