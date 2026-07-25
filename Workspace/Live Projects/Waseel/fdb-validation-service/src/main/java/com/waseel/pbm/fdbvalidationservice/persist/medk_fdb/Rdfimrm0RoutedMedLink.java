package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdfimrm0RoutedMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDFIMRM0_ROUTED_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rdfimrm0RoutedMedLink  implements java.io.Serializable {


    // Fields    

     private Rdfimrm0RoutedMedLinkId id;


    // Constructors

    /** default constructor */
    public Rdfimrm0RoutedMedLink() {
    }

    
    /** full constructor */
    public Rdfimrm0RoutedMedLink(Rdfimrm0RoutedMedLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="fdcde", column=@Column(name="FDCDE", nullable=false, precision=3, scale=0) ) } )

    public Rdfimrm0RoutedMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rdfimrm0RoutedMedLinkId id) {
        this.id = id;
    }
   








}