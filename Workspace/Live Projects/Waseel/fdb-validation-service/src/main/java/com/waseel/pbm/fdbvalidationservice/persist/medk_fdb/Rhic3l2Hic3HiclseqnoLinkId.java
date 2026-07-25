package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rhic3l2Hic3HiclseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rhic3l2Hic3HiclseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer hiclSeqno;
     private Integer hic3Seqn;


    // Constructors

    /** default constructor */
    public Rhic3l2Hic3HiclseqnoLinkId() {
    }

    
    /** full constructor */
    public Rhic3l2Hic3HiclseqnoLinkId(Integer hiclSeqno, Integer hic3Seqn) {
        this.hiclSeqno = hiclSeqno;
        this.hic3Seqn = hic3Seqn;
    }

   
    // Property accessors

    @Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getHiclSeqno() {
        return this.hiclSeqno;
    }
    
    public void setHiclSeqno(Integer hiclSeqno) {
        this.hiclSeqno = hiclSeqno;
    }

    @Column(name="HIC3_SEQN", nullable=false, precision=6, scale=0)

    public Integer getHic3Seqn() {
        return this.hic3Seqn;
    }
    
    public void setHic3Seqn(Integer hic3Seqn) {
        this.hic3Seqn = hic3Seqn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rhic3l2Hic3HiclseqnoLinkId) ) return false;
		 Rhic3l2Hic3HiclseqnoLinkId castOther = ( Rhic3l2Hic3HiclseqnoLinkId ) other; 
         
		 return ( (this.getHiclSeqno()==castOther.getHiclSeqno()) || ( this.getHiclSeqno()!=null && castOther.getHiclSeqno()!=null && this.getHiclSeqno().equals(castOther.getHiclSeqno()) ) )
 && ( (this.getHic3Seqn()==castOther.getHic3Seqn()) || ( this.getHic3Seqn()!=null && castOther.getHic3Seqn()!=null && this.getHic3Seqn().equals(castOther.getHic3Seqn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getHiclSeqno() == null ? 0 : this.getHiclSeqno().hashCode() );
         result = 37 * result + ( getHic3Seqn() == null ? 0 : this.getHic3Seqn().hashCode() );
         return result;
   }   





}