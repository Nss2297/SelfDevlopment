package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rlactrm0RoutedMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RLACTRM0_ROUTED_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rlactrm0RoutedMedLink  implements java.io.Serializable {


    // Fields    

     private Rlactrm0RoutedMedLinkId id;


    // Constructors

    /** default constructor */
    public Rlactrm0RoutedMedLink() {
    }

    
    /** full constructor */
    public Rlactrm0RoutedMedLink(Rlactrm0RoutedMedLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="lactCode", column=@Column(name="LACT_CODE", nullable=false, precision=6, scale=0) ) } )

    public Rlactrm0RoutedMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rlactrm0RoutedMedLinkId id) {
        this.id = id;
    }
   








}