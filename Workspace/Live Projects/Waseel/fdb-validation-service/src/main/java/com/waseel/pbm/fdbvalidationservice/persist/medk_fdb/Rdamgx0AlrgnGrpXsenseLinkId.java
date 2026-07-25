package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdamgx0AlrgnGrpXsenseLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdamgx0AlrgnGrpXsenseLinkId  implements java.io.Serializable {


    // Fields    

     private Integer damAlrgnGrp;
     private Short damAlrgnXsense;


    // Constructors

    /** default constructor */
    public Rdamgx0AlrgnGrpXsenseLinkId() {
    }

    
    /** full constructor */
    public Rdamgx0AlrgnGrpXsenseLinkId(Integer damAlrgnGrp, Short damAlrgnXsense) {
        this.damAlrgnGrp = damAlrgnGrp;
        this.damAlrgnXsense = damAlrgnXsense;
    }

   
    // Property accessors

    @Column(name="DAM_ALRGN_GRP", nullable=false, precision=6, scale=0)

    public Integer getDamAlrgnGrp() {
        return this.damAlrgnGrp;
    }
    
    public void setDamAlrgnGrp(Integer damAlrgnGrp) {
        this.damAlrgnGrp = damAlrgnGrp;
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
		 if ( !(other instanceof Rdamgx0AlrgnGrpXsenseLinkId) ) return false;
		 Rdamgx0AlrgnGrpXsenseLinkId castOther = ( Rdamgx0AlrgnGrpXsenseLinkId ) other; 
         
		 return ( (this.getDamAlrgnGrp()==castOther.getDamAlrgnGrp()) || ( this.getDamAlrgnGrp()!=null && castOther.getDamAlrgnGrp()!=null && this.getDamAlrgnGrp().equals(castOther.getDamAlrgnGrp()) ) )
 && ( (this.getDamAlrgnXsense()==castOther.getDamAlrgnXsense()) || ( this.getDamAlrgnXsense()!=null && castOther.getDamAlrgnXsense()!=null && this.getDamAlrgnXsense().equals(castOther.getDamAlrgnXsense()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDamAlrgnGrp() == null ? 0 : this.getDamAlrgnGrp().hashCode() );
         result = 37 * result + ( getDamAlrgnXsense() == null ? 0 : this.getDamAlrgnXsense().hashCode() );
         return result;
   }   





}