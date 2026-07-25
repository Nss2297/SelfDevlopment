package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rhic3l2Hic3HiclseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHIC3L2_HIC3_HICLSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rhic3l2Hic3HiclseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rhic3l2Hic3HiclseqnoLinkId id;
     private String hic3;
     private Boolean hic3Relno;


    // Constructors

    /** default constructor */
    public Rhic3l2Hic3HiclseqnoLink() {
    }

	/** minimal constructor */
    public Rhic3l2Hic3HiclseqnoLink(Rhic3l2Hic3HiclseqnoLinkId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rhic3l2Hic3HiclseqnoLink(Rhic3l2Hic3HiclseqnoLinkId id, String hic3, Boolean hic3Relno) {
        this.id = id;
        this.hic3 = hic3;
        this.hic3Relno = hic3Relno;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="hiclSeqno", column=@Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="hic3Seqn", column=@Column(name="HIC3_SEQN", nullable=false, precision=6, scale=0) ) } )

    public Rhic3l2Hic3HiclseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rhic3l2Hic3HiclseqnoLinkId id) {
        this.id = id;
    }
    
    @Column(name="HIC3", length=3)

    public String getHic3() {
        return this.hic3;
    }
    
    public void setHic3(String hic3) {
        this.hic3 = hic3;
    }
    
    @Column(name="HIC3_RELNO", precision=1, scale=0)

    public Boolean getHic3Relno() {
        return this.hic3Relno;
    }
    
    public void setHic3Relno(Boolean hic3Relno) {
        this.hic3Relno = hic3Relno;
    }
   








}