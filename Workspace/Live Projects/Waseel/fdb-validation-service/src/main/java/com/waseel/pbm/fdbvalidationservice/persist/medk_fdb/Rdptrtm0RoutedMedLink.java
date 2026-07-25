package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdptrtm0RoutedMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDPTRTM0_ROUTED_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rdptrtm0RoutedMedLink  implements java.io.Serializable {


    // Fields    

     private Rdptrtm0RoutedMedLinkId id;


    // Constructors

    /** default constructor */
    public Rdptrtm0RoutedMedLink() {
    }

    
    /** full constructor */
    public Rdptrtm0RoutedMedLink(Rdptrtm0RoutedMedLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="dptClassId", column=@Column(name="DPT_CLASS_ID", nullable=false, precision=8, scale=0) ) } )

    public Rdptrtm0RoutedMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rdptrtm0RoutedMedLinkId id) {
        this.id = id;
    }
   








}