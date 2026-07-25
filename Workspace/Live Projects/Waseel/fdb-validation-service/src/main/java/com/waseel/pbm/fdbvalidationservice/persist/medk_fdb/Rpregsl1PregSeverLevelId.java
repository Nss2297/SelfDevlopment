package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpregsl1PregSeverLevelId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpregsl1PregSeverLevelId  implements java.io.Serializable {


    // Fields    

     private String pregSl;
     private Byte pregSlsn;


    // Constructors

    /** default constructor */
    public Rpregsl1PregSeverLevelId() {
    }

    
    /** full constructor */
    public Rpregsl1PregSeverLevelId(String pregSl, Byte pregSlsn) {
        this.pregSl = pregSl;
        this.pregSlsn = pregSlsn;
    }

   
    // Property accessors

    @Column(name="PREG_SL", nullable=false, length=1)

    public String getPregSl() {
        return this.pregSl;
    }
    
    public void setPregSl(String pregSl) {
        this.pregSl = pregSl;
    }

    @Column(name="PREG_SLSN", nullable=false, precision=2, scale=0)

    public Byte getPregSlsn() {
        return this.pregSlsn;
    }
    
    public void setPregSlsn(Byte pregSlsn) {
        this.pregSlsn = pregSlsn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpregsl1PregSeverLevelId) ) return false;
		 Rpregsl1PregSeverLevelId castOther = ( Rpregsl1PregSeverLevelId ) other; 
         
		 return ( (this.getPregSl()==castOther.getPregSl()) || ( this.getPregSl()!=null && castOther.getPregSl()!=null && this.getPregSl().equals(castOther.getPregSl()) ) )
 && ( (this.getPregSlsn()==castOther.getPregSlsn()) || ( this.getPregSlsn()!=null && castOther.getPregSlsn()!=null && this.getPregSlsn().equals(castOther.getPregSlsn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPregSl() == null ? 0 : this.getPregSl().hashCode() );
         result = 37 * result + ( getPregSlsn() == null ? 0 : this.getPregSlsn().hashCode() );
         return result;
   }   





}