package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Ratrrg0AttributeRegionId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Ratrrg0AttributeRegionId  implements java.io.Serializable {


    // Fields    

     private Integer attributeCode;
     private Integer regionCode;


    // Constructors

    /** default constructor */
    public Ratrrg0AttributeRegionId() {
    }

    
    /** full constructor */
    public Ratrrg0AttributeRegionId(Integer attributeCode, Integer regionCode) {
        this.attributeCode = attributeCode;
        this.regionCode = regionCode;
    }

   
    // Property accessors

    @Column(name="ATTRIBUTE_CODE", nullable=false, precision=8, scale=0)

    public Integer getAttributeCode() {
        return this.attributeCode;
    }
    
    public void setAttributeCode(Integer attributeCode) {
        this.attributeCode = attributeCode;
    }

    @Column(name="REGION_CODE", nullable=false, precision=8, scale=0)

    public Integer getRegionCode() {
        return this.regionCode;
    }
    
    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Ratrrg0AttributeRegionId) ) return false;
		 Ratrrg0AttributeRegionId castOther = ( Ratrrg0AttributeRegionId ) other; 
         
		 return ( (this.getAttributeCode()==castOther.getAttributeCode()) || ( this.getAttributeCode()!=null && castOther.getAttributeCode()!=null && this.getAttributeCode().equals(castOther.getAttributeCode()) ) )
 && ( (this.getRegionCode()==castOther.getRegionCode()) || ( this.getRegionCode()!=null && castOther.getRegionCode()!=null && this.getRegionCode().equals(castOther.getRegionCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAttributeCode() == null ? 0 : this.getAttributeCode().hashCode() );
         result = 37 * result + ( getRegionCode() == null ? 0 : this.getRegionCode().hashCode() );
         return result;
   }   





}