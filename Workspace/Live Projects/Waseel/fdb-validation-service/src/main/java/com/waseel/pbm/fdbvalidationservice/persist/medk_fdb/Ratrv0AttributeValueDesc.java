package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Ratrv0AttributeValueDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RATRV0_ATTRIBUTE_VALUE_DESC"
    ,schema="MEDK_FDB"
)

public class Ratrv0AttributeValueDesc  implements java.io.Serializable {


    // Fields    

     private Ratrv0AttributeValueDescId id;
     private String attributeValueDesc;


    // Constructors

    /** default constructor */
    public Ratrv0AttributeValueDesc() {
    }

    
    /** full constructor */
    public Ratrv0AttributeValueDesc(Ratrv0AttributeValueDescId id, String attributeValueDesc) {
        this.id = id;
        this.attributeValueDesc = attributeValueDesc;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="attributeCode", column=@Column(name="ATTRIBUTE_CODE", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="attributeValue", column=@Column(name="ATTRIBUTE_VALUE", nullable=false, length=100) ) } )

    public Ratrv0AttributeValueDescId getId() {
        return this.id;
    }
    
    public void setId(Ratrv0AttributeValueDescId id) {
        this.id = id;
    }
    
    @Column(name="ATTRIBUTE_VALUE_DESC", nullable=false, length=100)

    public String getAttributeValueDesc() {
        return this.attributeValueDesc;
    }
    
    public void setAttributeValueDesc(String attributeValueDesc) {
        this.attributeValueDesc = attributeValueDesc;
    }
   








}