package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rfmldrh0DxidHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rfmldrh0DxidHistId  implements java.io.Serializable {


    // Fields    

     private Integer fmlprvdxid;
     private Integer fmlrepdxid;


    // Constructors

    /** default constructor */
    public Rfmldrh0DxidHistId() {
    }

    
    /** full constructor */
    public Rfmldrh0DxidHistId(Integer fmlprvdxid, Integer fmlrepdxid) {
        this.fmlprvdxid = fmlprvdxid;
        this.fmlrepdxid = fmlrepdxid;
    }

   
    // Property accessors

    @Column(name="FMLPRVDXID", nullable=false, precision=8, scale=0)

    public Integer getFmlprvdxid() {
        return this.fmlprvdxid;
    }
    
    public void setFmlprvdxid(Integer fmlprvdxid) {
        this.fmlprvdxid = fmlprvdxid;
    }

    @Column(name="FMLREPDXID", nullable=false, precision=8, scale=0)

    public Integer getFmlrepdxid() {
        return this.fmlrepdxid;
    }
    
    public void setFmlrepdxid(Integer fmlrepdxid) {
        this.fmlrepdxid = fmlrepdxid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rfmldrh0DxidHistId) ) return false;
		 Rfmldrh0DxidHistId castOther = ( Rfmldrh0DxidHistId ) other; 
         
		 return ( (this.getFmlprvdxid()==castOther.getFmlprvdxid()) || ( this.getFmlprvdxid()!=null && castOther.getFmlprvdxid()!=null && this.getFmlprvdxid().equals(castOther.getFmlprvdxid()) ) )
 && ( (this.getFmlrepdxid()==castOther.getFmlrepdxid()) || ( this.getFmlrepdxid()!=null && castOther.getFmlrepdxid()!=null && this.getFmlrepdxid().equals(castOther.getFmlrepdxid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFmlprvdxid() == null ? 0 : this.getFmlprvdxid().hashCode() );
         result = 37 * result + ( getFmlrepdxid() == null ? 0 : this.getFmlrepdxid().hashCode() );
         return result;
   }   





}