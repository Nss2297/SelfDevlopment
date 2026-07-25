package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Radimsl1SeverLevelId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Radimsl1SeverLevelId  implements java.io.Serializable {


    // Fields    

     private String ddiSl;
     private Byte ddiSlsn;


    // Constructors

    /** default constructor */
    public Radimsl1SeverLevelId() {
    }

    
    /** full constructor */
    public Radimsl1SeverLevelId(String ddiSl, Byte ddiSlsn) {
        this.ddiSl = ddiSl;
        this.ddiSlsn = ddiSlsn;
    }

   
    // Property accessors

    @Column(name="DDI_SL", nullable=false, length=1)

    public String getDdiSl() {
        return this.ddiSl;
    }
    
    public void setDdiSl(String ddiSl) {
        this.ddiSl = ddiSl;
    }

    @Column(name="DDI_SLSN", nullable=false, precision=2, scale=0)

    public Byte getDdiSlsn() {
        return this.ddiSlsn;
    }
    
    public void setDdiSlsn(Byte ddiSlsn) {
        this.ddiSlsn = ddiSlsn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Radimsl1SeverLevelId) ) return false;
		 Radimsl1SeverLevelId castOther = ( Radimsl1SeverLevelId ) other; 
         
		 return ( (this.getDdiSl()==castOther.getDdiSl()) || ( this.getDdiSl()!=null && castOther.getDdiSl()!=null && this.getDdiSl().equals(castOther.getDdiSl()) ) )
 && ( (this.getDdiSlsn()==castOther.getDdiSlsn()) || ( this.getDdiSlsn()!=null && castOther.getDdiSlsn()!=null && this.getDdiSlsn().equals(castOther.getDdiSlsn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDdiSl() == null ? 0 : this.getDdiSl().hashCode() );
         result = 37 * result + ( getDdiSlsn() == null ? 0 : this.getDdiSlsn().hashCode() );
         return result;
   }   





}