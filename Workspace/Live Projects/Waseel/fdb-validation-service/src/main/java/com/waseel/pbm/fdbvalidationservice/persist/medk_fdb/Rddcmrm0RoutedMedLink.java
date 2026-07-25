package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rddcmrm0RoutedMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDCMRM0_ROUTED_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rddcmrm0RoutedMedLink  implements java.io.Serializable {


    // Fields    

     private Rddcmrm0RoutedMedLinkId id;


    // Constructors

    /** default constructor */
    public Rddcmrm0RoutedMedLink() {
    }

    
    /** full constructor */
    public Rddcmrm0RoutedMedLink(Rddcmrm0RoutedMedLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="ddxcn", column=@Column(name="DDXCN", nullable=false, precision=5, scale=0) ) } )

    public Rddcmrm0RoutedMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rddcmrm0RoutedMedLinkId id) {
        this.id = id;
    }
   








}