package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdamxhc0HicAlrgnXsenseLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdamxhc0HicAlrgnXsenseLinkId  implements java.io.Serializable {


    // Fields    

     private Integer hicSeqn;
     private Short damAlrgnXsense;


    // Constructors

    /** default constructor */
    public Rdamxhc0HicAlrgnXsenseLinkId() {
    }

    
    /** full constructor */
    public Rdamxhc0HicAlrgnXsenseLinkId(Integer hicSeqn, Short damAlrgnXsense) {
        this.hicSeqn = hicSeqn;
        this.damAlrgnXsense = damAlrgnXsense;
    }

   
    // Property accessors

    @Column(name="HIC_SEQN", nullable=false, precision=6, scale=0)

    public Integer getHicSeqn() {
        return this.hicSeqn;
    }
    
    public void setHicSeqn(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
    }

    @Column(name="DAM_ALRGN_XSENSE", nullable=false, precision=4, scale=0)

    public Short getDamAlrgnXsense() {
        return this.damAlrgnXsense;
    }
    
    public void setDamAlrgnXsense(Short damAlrgnXsense) {
        this.damAlrgnXsense = damAlrgnXsense;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdamxhc0HicAlrgnXsenseLinkId) ) return false;
		 Rdamxhc0HicAlrgnXsenseLinkId castOther = ( Rdamxhc0HicAlrgnXsenseLinkId ) other; 
         
		 return ( (this.getHicSeqn()==castOther.getHicSeqn()) || ( this.getHicSeqn()!=null && castOther.getHicSeqn()!=null && this.getHicSeqn().equals(castOther.getHicSeqn()) ) )
 && ( (this.getDamAlrgnXsense()==castOther.getDamAlrgnXsense()) || ( this.getDamAlrgnXsense()!=null && castOther.getDamAlrgnXsense()!=null && this.getDamAlrgnXsense().equals(castOther.getDamAlrgnXsense()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getHicSeqn() == null ? 0 : this.getHicSeqn().hashCode() );
         result = 37 * result + ( getDamAlrgnXsense() == null ? 0 : this.getDamAlrgnXsense().hashCode() );
         return result;
   }   





}