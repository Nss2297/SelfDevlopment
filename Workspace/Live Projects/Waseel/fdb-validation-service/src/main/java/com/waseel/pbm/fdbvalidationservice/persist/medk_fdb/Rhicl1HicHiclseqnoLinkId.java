package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rhicl1HicHiclseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rhicl1HicHiclseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer hiclSeqno;
     private Integer hicSeqn;


    // Constructors

    /** default constructor */
    public Rhicl1HicHiclseqnoLinkId() {
    }

    
    /** full constructor */
    public Rhicl1HicHiclseqnoLinkId(Integer hiclSeqno, Integer hicSeqn) {
        this.hiclSeqno = hiclSeqno;
        this.hicSeqn = hicSeqn;
    }

   
    // Property accessors

    @Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getHiclSeqno() {
        return this.hiclSeqno;
    }
    
    public void setHiclSeqno(Integer hiclSeqno) {
        this.hiclSeqno = hiclSeqno;
    }

    @Column(name="HIC_SEQN", nullable=false, precision=6, scale=0)

    public Integer getHicSeqn() {
        return this.hicSeqn;
    }
    
    public void setHicSeqn(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rhicl1HicHiclseqnoLinkId) ) return false;
		 Rhicl1HicHiclseqnoLinkId castOther = ( Rhicl1HicHiclseqnoLinkId ) other; 
         
		 return ( (this.getHiclSeqno()==castOther.getHiclSeqno()) || ( this.getHiclSeqno()!=null && castOther.getHiclSeqno()!=null && this.getHiclSeqno().equals(castOther.getHiclSeqno()) ) )
 && ( (this.getHicSeqn()==castOther.getHicSeqn()) || ( this.getHicSeqn()!=null && castOther.getHicSeqn()!=null && this.getHicSeqn().equals(castOther.getHicSeqn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getHiclSeqno() == null ? 0 : this.getHiclSeqno().hashCode() );
         result = 37 * result + ( getHicSeqn() == null ? 0 : this.getHicSeqn().hashCode() );
         return result;
   }   





}