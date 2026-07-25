package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpemorg0RoutedGenLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEMORG0_ROUTED_GEN_LINK"
    ,schema="MEDK_FDB"
)

public class Rpemorg0RoutedGenLink  implements java.io.Serializable {


    // Fields    

     private Rpemorg0RoutedGenLinkId id;


    // Constructors

    /** default constructor */
    public Rpemorg0RoutedGenLink() {
    }

    
    /** full constructor */
    public Rpemorg0RoutedGenLink(Rpemorg0RoutedGenLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedGenId", column=@Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="pemono", column=@Column(name="PEMONO", nullable=false, precision=4, scale=0) ) } )

    public Rpemorg0RoutedGenLinkId getId() {
        return this.id;
    }
    
    public void setId(Rpemorg0RoutedGenLinkId id) {
        this.id = id;
    }
   








}