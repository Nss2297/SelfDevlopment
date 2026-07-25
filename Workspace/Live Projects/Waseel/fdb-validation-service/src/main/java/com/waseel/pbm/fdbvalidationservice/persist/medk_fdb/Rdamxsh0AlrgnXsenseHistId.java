package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdamxsh0AlrgnXsenseHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdamxsh0AlrgnXsenseHistId  implements java.io.Serializable {


    // Fields    

     private Short replDamAlrgnXsense;
     private Short prevDamAlrgnXsense;


    // Constructors

    /** default constructor */
    public Rdamxsh0AlrgnXsenseHistId() {
    }

    
    /** full constructor */
    public Rdamxsh0AlrgnXsenseHistId(Short replDamAlrgnXsense, Short prevDamAlrgnXsense) {
        this.replDamAlrgnXsense = replDamAlrgnXsense;
        this.prevDamAlrgnXsense = prevDamAlrgnXsense;
    }

   
    // Property accessors

    @Column(name="REPL_DAM_ALRGN_XSENSE", nullable=false, precision=4, scale=0)

    public Short getReplDamAlrgnXsense() {
        return this.replDamAlrgnXsense;
    }
    
    public void setReplDamAlrgnXsense(Short replDamAlrgnXsense) {
        this.replDamAlrgnXsense = replDamAlrgnXsense;
    }

    @Column(name="PREV_DAM_ALRGN_XSENSE", nullable=false, precision=4, scale=0)

    public Short getPrevDamAlrgnXsense() {
        return this.prevDamAlrgnXsense;
    }
    
    public void setPrevDamAlrgnXsense(Short prevDamAlrgnXsense) {
        this.prevDamAlrgnXsense = prevDamAlrgnXsense;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdamxsh0AlrgnXsenseHistId) ) return false;
		 Rdamxsh0AlrgnXsenseHistId castOther = ( Rdamxsh0AlrgnXsenseHistId ) other; 
         
		 return ( (this.getReplDamAlrgnXsense()==castOther.getReplDamAlrgnXsense()) || ( this.getReplDamAlrgnXsense()!=null && castOther.getReplDamAlrgnXsense()!=null && this.getReplDamAlrgnXsense().equals(castOther.getReplDamAlrgnXsense()) ) )
 && ( (this.getPrevDamAlrgnXsense()==castOther.getPrevDamAlrgnXsense()) || ( this.getPrevDamAlrgnXsense()!=null && castOther.getPrevDamAlrgnXsense()!=null && this.getPrevDamAlrgnXsense().equals(castOther.getPrevDamAlrgnXsense()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReplDamAlrgnXsense() == null ? 0 : this.getReplDamAlrgnXsense().hashCode() );
         result = 37 * result + ( getPrevDamAlrgnXsense() == null ? 0 : this.getPrevDamAlrgnXsense().hashCode() );
         return result;
   }   





}