package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rindmrg0RoutedGenLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RINDMRG0_ROUTED_GEN_LINK"
    ,schema="MEDK_FDB"
)

public class Rindmrg0RoutedGenLink  implements java.io.Serializable {


    // Fields    

     private Rindmrg0RoutedGenLinkId id;


    // Constructors

    /** default constructor */
    public Rindmrg0RoutedGenLink() {
    }

    
    /** full constructor */
    public Rindmrg0RoutedGenLink(Rindmrg0RoutedGenLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedGenId", column=@Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="indcts", column=@Column(name="INDCTS", nullable=false, precision=5, scale=0) ) } )

    public Rindmrg0RoutedGenLinkId getId() {
        return this.id;
    }
    
    public void setId(Rindmrg0RoutedGenLinkId id) {
        this.id = id;
    }
   








}