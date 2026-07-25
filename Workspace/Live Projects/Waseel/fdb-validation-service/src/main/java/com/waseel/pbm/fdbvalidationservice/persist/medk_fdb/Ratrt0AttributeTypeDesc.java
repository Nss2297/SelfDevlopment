package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Ratrt0AttributeTypeDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RATRT0_ATTRIBUTE_TYPE_DESC"
    ,schema="MEDK_FDB"
)

public class Ratrt0AttributeTypeDesc  implements java.io.Serializable {


    // Fields    

     private Integer attributeTypeCode;
     private String attributeTypeDesc;
     private Integer attributeTypeLength;
     private Integer attributeTypePrecision;


    // Constructors

    /** default constructor */
    public Ratrt0AttributeTypeDesc() {
    }

	/** minimal constructor */
    public Ratrt0AttributeTypeDesc(Integer attributeTypeCode, String attributeTypeDesc) {
        this.attributeTypeCode = attributeTypeCode;
        this.attributeTypeDesc = attributeTypeDesc;
    }
    
    /** full constructor */
    public Ratrt0AttributeTypeDesc(Integer attributeTypeCode, String attributeTypeDesc, Integer attributeTypeLength, Integer attributeTypePrecision) {
        this.attributeTypeCode = attributeTypeCode;
        this.attributeTypeDesc = attributeTypeDesc;
        this.attributeTypeLength = attributeTypeLength;
        this.attributeTypePrecision = attributeTypePrecision;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ATTRIBUTE_TYPE_CODE", unique=true, nullable=false, precision=8, scale=0)

    public Integer getAttributeTypeCode() {
        return this.attributeTypeCode;
    }
    
    public void setAttributeTypeCode(Integer attributeTypeCode) {
        this.attributeTypeCode = attributeTypeCode;
    }
    
    @Column(name="ATTRIBUTE_TYPE_DESC", nullable=false, length=100)

    public String getAttributeTypeDesc() {
        return this.attributeTypeDesc;
    }
    
    public void setAttributeTypeDesc(String attributeTypeDesc) {
        this.attributeTypeDesc = attributeTypeDesc;
    }
    
    @Column(name="ATTRIBUTE_TYPE_LENGTH", precision=8, scale=0)

    public Integer getAttributeTypeLength() {
        return this.attributeTypeLength;
    }
    
    public void setAttributeTypeLength(Integer attributeTypeLength) {
        this.attributeTypeLength = attributeTypeLength;
    }
    
    @Column(name="ATTRIBUTE_TYPE_PRECISION", precision=8, scale=0)

    public Integer getAttributeTypePrecision() {
        return this.attributeTypePrecision;
    }
    
    public void setAttributeTypePrecision(Integer attributeTypePrecision) {
        this.attributeTypePrecision = attributeTypePrecision;
    }
   








}