package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rhichcr0HicHicLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rhichcr0HicHicLinkId  implements java.io.Serializable {


    // Fields    

     private Integer hicSeqn;
     private Integer relatedHicSeqn;


    // Constructors

    /** default constructor */
    public Rhichcr0HicHicLinkId() {
    }

    
    /** full constructor */
    public Rhichcr0HicHicLinkId(Integer hicSeqn, Integer relatedHicSeqn) {
        this.hicSeqn = hicSeqn;
        this.relatedHicSeqn = relatedHicSeqn;
    }

   
    // Property accessors

    @Column(name="HIC_SEQN", nullable=false, precision=6, scale=0)

    public Integer getHicSeqn() {
        return this.hicSeqn;
    }
    
    public void setHicSeqn(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
    }

    @Column(name="RELATED_HIC_SEQN", nullable=false, precision=6, scale=0)

    public Integer getRelatedHicSeqn() {
        return this.relatedHicSeqn;
    }
    
    public void setRelatedHicSeqn(Integer relatedHicSeqn) {
        this.relatedHicSeqn = relatedHicSeqn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rhichcr0HicHicLinkId) ) return false;
		 Rhichcr0HicHicLinkId castOther = ( Rhichcr0HicHicLinkId ) other; 
         
		 return ( (this.getHicSeqn()==castOther.getHicSeqn()) || ( this.getHicSeqn()!=null && castOther.getHicSeqn()!=null && this.getHicSeqn().equals(castOther.getHicSeqn()) ) )
 && ( (this.getRelatedHicSeqn()==castOther.getRelatedHicSeqn()) || ( this.getRelatedHicSeqn()!=null && castOther.getRelatedHicSeqn()!=null && this.getRelatedHicSeqn().equals(castOther.getRelatedHicSeqn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getHicSeqn() == null ? 0 : this.getHicSeqn().hashCode() );
         result = 37 * result + ( getRelatedHicSeqn() == null ? 0 : this.getRelatedHicSeqn().hashCode() );
         return result;
   }   





}