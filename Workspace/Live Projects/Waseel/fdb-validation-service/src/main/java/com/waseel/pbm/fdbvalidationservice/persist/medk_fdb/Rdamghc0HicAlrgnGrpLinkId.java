package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdamghc0HicAlrgnGrpLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdamghc0HicAlrgnGrpLinkId  implements java.io.Serializable {


    // Fields    

     private Integer hicSeqn;
     private Integer damAlrgnGrp;


    // Constructors

    /** default constructor */
    public Rdamghc0HicAlrgnGrpLinkId() {
    }

    
    /** full constructor */
    public Rdamghc0HicAlrgnGrpLinkId(Integer hicSeqn, Integer damAlrgnGrp) {
        this.hicSeqn = hicSeqn;
        this.damAlrgnGrp = damAlrgnGrp;
    }

   
    // Property accessors

    @Column(name="HIC_SEQN", nullable=false, precision=6, scale=0)

    public Integer getHicSeqn() {
        return this.hicSeqn;
    }
    
    public void setHicSeqn(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
    }

    @Column(name="DAM_ALRGN_GRP", nullable=false, precision=6, scale=0)

    public Integer getDamAlrgnGrp() {
        return this.damAlrgnGrp;
    }
    
    public void setDamAlrgnGrp(Integer damAlrgnGrp) {
        this.damAlrgnGrp = damAlrgnGrp;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdamghc0HicAlrgnGrpLinkId) ) return false;
		 Rdamghc0HicAlrgnGrpLinkId castOther = ( Rdamghc0HicAlrgnGrpLinkId ) other; 
         
		 return ( (this.getHicSeqn()==castOther.getHicSeqn()) || ( this.getHicSeqn()!=null && castOther.getHicSeqn()!=null && this.getHicSeqn().equals(castOther.getHicSeqn()) ) )
 && ( (this.getDamAlrgnGrp()==castOther.getDamAlrgnGrp()) || ( this.getDamAlrgnGrp()!=null && castOther.getDamAlrgnGrp()!=null && this.getDamAlrgnGrp().equals(castOther.getDamAlrgnGrp()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getHicSeqn() == null ? 0 : this.getHicSeqn().hashCode() );
         result = 37 * result + ( getDamAlrgnGrp() == null ? 0 : this.getDamAlrgnGrp().hashCode() );
         return result;
   }   





}