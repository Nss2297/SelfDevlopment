package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Ratrv0AttributeValueDescId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Ratrv0AttributeValueDescId  implements java.io.Serializable {


    // Fields    

     private Integer attributeCode;
     private String attributeValue;


    // Constructors

    /** default constructor */
    public Ratrv0AttributeValueDescId() {
    }

    
    /** full constructor */
    public Ratrv0AttributeValueDescId(Integer attributeCode, String attributeValue) {
        this.attributeCode = attributeCode;
        this.attributeValue = attributeValue;
    }

   
    // Property accessors

    @Column(name="ATTRIBUTE_CODE", nullable=false, precision=8, scale=0)

    public Integer getAttributeCode() {
        return this.attributeCode;
    }
    
    public void setAttributeCode(Integer attributeCode) {
        this.attributeCode = attributeCode;
    }

    @Column(name="ATTRIBUTE_VALUE", nullable=false, length=100)

    public String getAttributeValue() {
        return this.attributeValue;
    }
    
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Ratrv0AttributeValueDescId) ) return false;
		 Ratrv0AttributeValueDescId castOther = ( Ratrv0AttributeValueDescId ) other; 
         
		 return ( (this.getAttributeCode()==castOther.getAttributeCode()) || ( this.getAttributeCode()!=null && castOther.getAttributeCode()!=null && this.getAttributeCode().equals(castOther.getAttributeCode()) ) )
 && ( (this.getAttributeValue()==castOther.getAttributeValue()) || ( this.getAttributeValue()!=null && castOther.getAttributeValue()!=null && this.getAttributeValue().equals(castOther.getAttributeValue()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAttributeCode() == null ? 0 : this.getAttributeCode().hashCode() );
         result = 37 * result + ( getAttributeValue() == null ? 0 : this.getAttributeValue().hashCode() );
         return result;
   }   





}