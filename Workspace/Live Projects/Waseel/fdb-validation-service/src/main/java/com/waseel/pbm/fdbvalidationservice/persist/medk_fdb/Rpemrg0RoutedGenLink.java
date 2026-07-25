package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpemrg0RoutedGenLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEMRG0_ROUTED_GEN_LINK"
    ,schema="MEDK_FDB"
)

public class Rpemrg0RoutedGenLink  implements java.io.Serializable {


    // Fields    

     private Rpemrg0RoutedGenLinkId id;


    // Constructors

    /** default constructor */
    public Rpemrg0RoutedGenLink() {
    }

    
    /** full constructor */
    public Rpemrg0RoutedGenLink(Rpemrg0RoutedGenLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedGenId", column=@Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="pec", column=@Column(name="PEC", nullable=false, precision=6, scale=0) ) } )

    public Rpemrg0RoutedGenLinkId getId() {
        return this.id;
    }
    
    public void setId(Rpemrg0RoutedGenLinkId id) {
        this.id = id;
    }
   








}