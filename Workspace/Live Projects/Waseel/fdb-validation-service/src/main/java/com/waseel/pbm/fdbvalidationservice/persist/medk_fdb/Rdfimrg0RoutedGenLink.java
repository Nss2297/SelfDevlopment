package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdfimrg0RoutedGenLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDFIMRG0_ROUTED_GEN_LINK"
    ,schema="MEDK_FDB"
)

public class Rdfimrg0RoutedGenLink  implements java.io.Serializable {


    // Fields    

     private Rdfimrg0RoutedGenLinkId id;


    // Constructors

    /** default constructor */
    public Rdfimrg0RoutedGenLink() {
    }

    
    /** full constructor */
    public Rdfimrg0RoutedGenLink(Rdfimrg0RoutedGenLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedGenId", column=@Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="fdcde", column=@Column(name="FDCDE", nullable=false, precision=3, scale=0) ) } )

    public Rdfimrg0RoutedGenLinkId getId() {
        return this.id;
    }
    
    public void setId(Rdfimrg0RoutedGenLinkId id) {
        this.id = id;
    }
   








}