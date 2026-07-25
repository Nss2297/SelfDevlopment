package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Ratrrg0AttributeRegion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RATRRG0_ATTRIBUTE_REGION"
    ,schema="MEDK_FDB"
)

public class Ratrrg0AttributeRegion  implements java.io.Serializable {


    // Fields    

     private Ratrrg0AttributeRegionId id;


    // Constructors

    /** default constructor */
    public Ratrrg0AttributeRegion() {
    }

    
    /** full constructor */
    public Ratrrg0AttributeRegion(Ratrrg0AttributeRegionId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="attributeCode", column=@Column(name="ATTRIBUTE_CODE", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="regionCode", column=@Column(name="REGION_CODE", nullable=false, precision=8, scale=0) ) } )

    public Ratrrg0AttributeRegionId getId() {
        return this.id;
    }
    
    public void setId(Ratrrg0AttributeRegionId id) {
        this.id = id;
    }
   








}