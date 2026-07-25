package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rhicl1HicHiclseqnoLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHICL1_HIC_HICLSEQNO_LINK"
    ,schema="MEDK_FDB"
)

public class Rhicl1HicHiclseqnoLink  implements java.io.Serializable {


    // Fields    

     private Rhicl1HicHiclseqnoLinkId id;
     private Boolean hicRelNo;
     private String hic;


    // Constructors

    /** default constructor */
    public Rhicl1HicHiclseqnoLink() {
    }

	/** minimal constructor */
    public Rhicl1HicHiclseqnoLink(Rhicl1HicHiclseqnoLinkId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rhicl1HicHiclseqnoLink(Rhicl1HicHiclseqnoLinkId id, Boolean hicRelNo, String hic) {
        this.id = id;
        this.hicRelNo = hicRelNo;
        this.hic = hic;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="hiclSeqno", column=@Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="hicSeqn", column=@Column(name="HIC_SEQN", nullable=false, precision=6, scale=0) ) } )

    public Rhicl1HicHiclseqnoLinkId getId() {
        return this.id;
    }
    
    public void setId(Rhicl1HicHiclseqnoLinkId id) {
        this.id = id;
    }
    
    @Column(name="HIC_REL_NO", precision=1, scale=0)

    public Boolean getHicRelNo() {
        return this.hicRelNo;
    }
    
    public void setHicRelNo(Boolean hicRelNo) {
        this.hicRelNo = hicRelNo;
    }
    
    @Column(name="HIC", length=6)

    public String getHic() {
        return this.hic;
    }
    
    public void setHic(String hic) {
        this.hic = hic;
    }
   








}