package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rgerirm0RoutedMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RGERIRM0_ROUTED_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rgerirm0RoutedMedLink  implements java.io.Serializable {


    // Fields    

     private Rgerirm0RoutedMedLinkId id;


    // Constructors

    /** default constructor */
    public Rgerirm0RoutedMedLink() {
    }

    
    /** full constructor */
    public Rgerirm0RoutedMedLink(Rgerirm0RoutedMedLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="geriCode", column=@Column(name="GERI_CODE", nullable=false, precision=6, scale=0) ) } )

    public Rgerirm0RoutedMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rgerirm0RoutedMedLinkId id) {
        this.id = id;
    }
   








}