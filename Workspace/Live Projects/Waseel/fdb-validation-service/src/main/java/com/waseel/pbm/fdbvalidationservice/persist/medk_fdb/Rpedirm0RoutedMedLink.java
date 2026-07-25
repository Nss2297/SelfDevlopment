package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpedirm0RoutedMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEDIRM0_ROUTED_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rpedirm0RoutedMedLink  implements java.io.Serializable {


    // Fields    

     private Rpedirm0RoutedMedLinkId id;


    // Constructors

    /** default constructor */
    public Rpedirm0RoutedMedLink() {
    }

    
    /** full constructor */
    public Rpedirm0RoutedMedLink(Rpedirm0RoutedMedLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="pediCode", column=@Column(name="PEDI_CODE", nullable=false, precision=6, scale=0) ) } )

    public Rpedirm0RoutedMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rpedirm0RoutedMedLinkId id) {
        this.id = id;
    }
   








}