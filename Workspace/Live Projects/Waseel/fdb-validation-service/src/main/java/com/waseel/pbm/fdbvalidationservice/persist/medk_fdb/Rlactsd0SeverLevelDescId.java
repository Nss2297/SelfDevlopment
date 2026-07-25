package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rlactsd0SeverLevelDescId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rlactsd0SeverLevelDescId  implements java.io.Serializable {


    // Fields    

     private String lactSl;
     private Byte lactSlsn;


    // Constructors

    /** default constructor */
    public Rlactsd0SeverLevelDescId() {
    }

    
    /** full constructor */
    public Rlactsd0SeverLevelDescId(String lactSl, Byte lactSlsn) {
        this.lactSl = lactSl;
        this.lactSlsn = lactSlsn;
    }

   
    // Property accessors

    @Column(name="LACT_SL", nullable=false, length=1)

    public String getLactSl() {
        return this.lactSl;
    }
    
    public void setLactSl(String lactSl) {
        this.lactSl = lactSl;
    }

    @Column(name="LACT_SLSN", nullable=false, precision=2, scale=0)

    public Byte getLactSlsn() {
        return this.lactSlsn;
    }
    
    public void setLactSlsn(Byte lactSlsn) {
        this.lactSlsn = lactSlsn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rlactsd0SeverLevelDescId) ) return false;
		 Rlactsd0SeverLevelDescId castOther = ( Rlactsd0SeverLevelDescId ) other; 
         
		 return ( (this.getLactSl()==castOther.getLactSl()) || ( this.getLactSl()!=null && castOther.getLactSl()!=null && this.getLactSl().equals(castOther.getLactSl()) ) )
 && ( (this.getLactSlsn()==castOther.getLactSlsn()) || ( this.getLactSlsn()!=null && castOther.getLactSlsn()!=null && this.getLactSlsn().equals(castOther.getLactSlsn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLactSl() == null ? 0 : this.getLactSl().hashCode() );
         result = 37 * result + ( getLactSlsn() == null ? 0 : this.getLactSlsn().hashCode() );
         return result;
   }   





}