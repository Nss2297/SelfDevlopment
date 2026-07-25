package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdamgrh0AlrgnGrpHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdamgrh0AlrgnGrpHistId  implements java.io.Serializable {


    // Fields    

     private Integer replDamAlrgnGrp;
     private Integer prevDamAlrgnGrp;


    // Constructors

    /** default constructor */
    public Rdamgrh0AlrgnGrpHistId() {
    }

    
    /** full constructor */
    public Rdamgrh0AlrgnGrpHistId(Integer replDamAlrgnGrp, Integer prevDamAlrgnGrp) {
        this.replDamAlrgnGrp = replDamAlrgnGrp;
        this.prevDamAlrgnGrp = prevDamAlrgnGrp;
    }

   
    // Property accessors

    @Column(name="REPL_DAM_ALRGN_GRP", nullable=false, precision=6, scale=0)

    public Integer getReplDamAlrgnGrp() {
        return this.replDamAlrgnGrp;
    }
    
    public void setReplDamAlrgnGrp(Integer replDamAlrgnGrp) {
        this.replDamAlrgnGrp = replDamAlrgnGrp;
    }

    @Column(name="PREV_DAM_ALRGN_GRP", nullable=false, precision=6, scale=0)

    public Integer getPrevDamAlrgnGrp() {
        return this.prevDamAlrgnGrp;
    }
    
    public void setPrevDamAlrgnGrp(Integer prevDamAlrgnGrp) {
        this.prevDamAlrgnGrp = prevDamAlrgnGrp;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdamgrh0AlrgnGrpHistId) ) return false;
		 Rdamgrh0AlrgnGrpHistId castOther = ( Rdamgrh0AlrgnGrpHistId ) other; 
         
		 return ( (this.getReplDamAlrgnGrp()==castOther.getReplDamAlrgnGrp()) || ( this.getReplDamAlrgnGrp()!=null && castOther.getReplDamAlrgnGrp()!=null && this.getReplDamAlrgnGrp().equals(castOther.getReplDamAlrgnGrp()) ) )
 && ( (this.getPrevDamAlrgnGrp()==castOther.getPrevDamAlrgnGrp()) || ( this.getPrevDamAlrgnGrp()!=null && castOther.getPrevDamAlrgnGrp()!=null && this.getPrevDamAlrgnGrp().equals(castOther.getPrevDamAlrgnGrp()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReplDamAlrgnGrp() == null ? 0 : this.getReplDamAlrgnGrp().hashCode() );
         result = 37 * result + ( getPrevDamAlrgnGrp() == null ? 0 : this.getPrevDamAlrgnGrp().hashCode() );
         return result;
   }   





}