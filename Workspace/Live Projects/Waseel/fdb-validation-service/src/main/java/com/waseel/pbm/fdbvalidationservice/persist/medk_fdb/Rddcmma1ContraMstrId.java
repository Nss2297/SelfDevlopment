package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rddcmma1ContraMstrId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rddcmma1ContraMstrId  implements java.io.Serializable {


    // Fields    

     private Integer ddxcn;
     private Byte ddxcnSn;


    // Constructors

    /** default constructor */
    public Rddcmma1ContraMstrId() {
    }

    
    /** full constructor */
    public Rddcmma1ContraMstrId(Integer ddxcn, Byte ddxcnSn) {
        this.ddxcn = ddxcn;
        this.ddxcnSn = ddxcnSn;
    }

   
    // Property accessors

    @Column(name="DDXCN", nullable=false, precision=5, scale=0)

    public Integer getDdxcn() {
        return this.ddxcn;
    }
    
    public void setDdxcn(Integer ddxcn) {
        this.ddxcn = ddxcn;
    }

    @Column(name="DDXCN_SN", nullable=false, precision=2, scale=0)

    public Byte getDdxcnSn() {
        return this.ddxcnSn;
    }
    
    public void setDdxcnSn(Byte ddxcnSn) {
        this.ddxcnSn = ddxcnSn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rddcmma1ContraMstrId) ) return false;
		 Rddcmma1ContraMstrId castOther = ( Rddcmma1ContraMstrId ) other; 
         
		 return ( (this.getDdxcn()==castOther.getDdxcn()) || ( this.getDdxcn()!=null && castOther.getDdxcn()!=null && this.getDdxcn().equals(castOther.getDdxcn()) ) )
 && ( (this.getDdxcnSn()==castOther.getDdxcnSn()) || ( this.getDdxcnSn()!=null && castOther.getDdxcnSn()!=null && this.getDdxcnSn().equals(castOther.getDdxcnSn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDdxcn() == null ? 0 : this.getDdxcn().hashCode() );
         result = 37 * result + ( getDdxcnSn() == null ? 0 : this.getDdxcnSn().hashCode() );
         return result;
   }   





}