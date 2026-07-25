package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rindmrm0RoutedMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RINDMRM0_ROUTED_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rindmrm0RoutedMedLink  implements java.io.Serializable {


    // Fields    

     private Rindmrm0RoutedMedLinkId id;


    // Constructors

    /** default constructor */
    public Rindmrm0RoutedMedLink() {
    }

    
    /** full constructor */
    public Rindmrm0RoutedMedLink(Rindmrm0RoutedMedLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="indcts", column=@Column(name="INDCTS", nullable=false, precision=5, scale=0) ) } )

    public Rindmrm0RoutedMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rindmrm0RoutedMedLinkId id) {
        this.id = id;
    }
   








}