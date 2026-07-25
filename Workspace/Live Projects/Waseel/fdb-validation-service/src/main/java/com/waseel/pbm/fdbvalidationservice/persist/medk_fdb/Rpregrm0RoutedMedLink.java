package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpregrm0RoutedMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPREGRM0_ROUTED_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rpregrm0RoutedMedLink  implements java.io.Serializable {


    // Fields    

     private Rpregrm0RoutedMedLinkId id;


    // Constructors

    /** default constructor */
    public Rpregrm0RoutedMedLink() {
    }

    
    /** full constructor */
    public Rpregrm0RoutedMedLink(Rpregrm0RoutedMedLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="pregCode", column=@Column(name="PREG_CODE", nullable=false, precision=6, scale=0) ) } )

    public Rpregrm0RoutedMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rpregrm0RoutedMedLinkId id) {
        this.id = id;
    }
   








}