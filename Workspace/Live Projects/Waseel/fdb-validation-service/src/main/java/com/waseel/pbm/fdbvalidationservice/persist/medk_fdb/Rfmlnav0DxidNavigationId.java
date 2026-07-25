package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rfmlnav0DxidNavigationId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rfmlnav0DxidNavigationId  implements java.io.Serializable {


    // Fields    

     private Integer dxid;
     private Integer broaderDxid;


    // Constructors

    /** default constructor */
    public Rfmlnav0DxidNavigationId() {
    }

    
    /** full constructor */
    public Rfmlnav0DxidNavigationId(Integer dxid, Integer broaderDxid) {
        this.dxid = dxid;
        this.broaderDxid = broaderDxid;
    }

   
    // Property accessors

    @Column(name="DXID", nullable=false, precision=8, scale=0)

    public Integer getDxid() {
        return this.dxid;
    }
    
    public void setDxid(Integer dxid) {
        this.dxid = dxid;
    }

    @Column(name="BROADER_DXID", nullable=false, precision=8, scale=0)

    public Integer getBroaderDxid() {
        return this.broaderDxid;
    }
    
    public void setBroaderDxid(Integer broaderDxid) {
        this.broaderDxid = broaderDxid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rfmlnav0DxidNavigationId) ) return false;
		 Rfmlnav0DxidNavigationId castOther = ( Rfmlnav0DxidNavigationId ) other; 
         
		 return ( (this.getDxid()==castOther.getDxid()) || ( this.getDxid()!=null && castOther.getDxid()!=null && this.getDxid().equals(castOther.getDxid()) ) )
 && ( (this.getBroaderDxid()==castOther.getBroaderDxid()) || ( this.getBroaderDxid()!=null && castOther.getBroaderDxid()!=null && this.getBroaderDxid().equals(castOther.getBroaderDxid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDxid() == null ? 0 : this.getDxid().hashCode() );
         result = 37 * result + ( getBroaderDxid() == null ? 0 : this.getBroaderDxid().hashCode() );
         return result;
   }   





}