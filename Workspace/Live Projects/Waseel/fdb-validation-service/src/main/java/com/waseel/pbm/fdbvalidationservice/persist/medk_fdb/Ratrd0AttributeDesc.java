package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Ratrd0AttributeDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RATRD0_ATTRIBUTE_DESC"
    ,schema="MEDK_FDB"
)

public class Ratrd0AttributeDesc  implements java.io.Serializable {


    // Fields    

     private Integer attributeCode;
     private String attributeDesc;
     private Integer attributeTypeCode;
     private Integer attributeGroupCode;


    // Constructors

    /** default constructor */
    public Ratrd0AttributeDesc() {
    }

    
    /** full constructor */
    public Ratrd0AttributeDesc(Integer attributeCode, String attributeDesc, Integer attributeTypeCode, Integer attributeGroupCode) {
        this.attributeCode = attributeCode;
        this.attributeDesc = attributeDesc;
        this.attributeTypeCode = attributeTypeCode;
        this.attributeGroupCode = attributeGroupCode;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ATTRIBUTE_CODE", unique=true, nullable=false, precision=8, scale=0)

    public Integer getAttributeCode() {
        return this.attributeCode;
    }
    
    public void setAttributeCode(Integer attributeCode) {
        this.attributeCode = attributeCode;
    }
    
    @Column(name="ATTRIBUTE_DESC", nullable=false, length=100)

    public String getAttributeDesc() {
        return this.attributeDesc;
    }
    
    public void setAttributeDesc(String attributeDesc) {
        this.attributeDesc = attributeDesc;
    }
    
    @Column(name="ATTRIBUTE_TYPE_CODE", nullable=false, precision=8, scale=0)

    public Integer getAttributeTypeCode() {
        return this.attributeTypeCode;
    }
    
    public void setAttributeTypeCode(Integer attributeTypeCode) {
        this.attributeTypeCode = attributeTypeCode;
    }
    
    @Column(name="ATTRIBUTE_GROUP_CODE", nullable=false, precision=8, scale=0)

    public Integer getAttributeGroupCode() {
        return this.attributeGroupCode;
    }
    
    public void setAttributeGroupCode(Integer attributeGroupCode) {
        this.attributeGroupCode = attributeGroupCode;
    }
   








}