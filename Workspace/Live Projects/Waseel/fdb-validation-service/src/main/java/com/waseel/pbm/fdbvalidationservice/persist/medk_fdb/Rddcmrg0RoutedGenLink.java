package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rddcmrg0RoutedGenLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDCMRG0_ROUTED_GEN_LINK"
    ,schema="MEDK_FDB"
)

public class Rddcmrg0RoutedGenLink  implements java.io.Serializable {


    // Fields    

     private Rddcmrg0RoutedGenLinkId id;


    // Constructors

    /** default constructor */
    public Rddcmrg0RoutedGenLink() {
    }

    
    /** full constructor */
    public Rddcmrg0RoutedGenLink(Rddcmrg0RoutedGenLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedGenId", column=@Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="ddxcn", column=@Column(name="DDXCN", nullable=false, precision=5, scale=0) ) } )

    public Rddcmrg0RoutedGenLinkId getId() {
        return this.id;
    }
    
    public void setId(Rddcmrg0RoutedGenLinkId id) {
        this.id = id;
    }
   








}