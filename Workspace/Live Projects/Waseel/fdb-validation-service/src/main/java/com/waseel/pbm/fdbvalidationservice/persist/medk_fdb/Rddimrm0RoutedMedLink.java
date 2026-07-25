package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rddimrm0RoutedMedLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDIMRM0_ROUTED_MED_LINK"
    ,schema="MEDK_FDB"
)

public class Rddimrm0RoutedMedLink  implements java.io.Serializable {


    // Fields    

     private Rddimrm0RoutedMedLinkId id;


    // Constructors

    /** default constructor */
    public Rddimrm0RoutedMedLink() {
    }

    
    /** full constructor */
    public Rddimrm0RoutedMedLink(Rddimrm0RoutedMedLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedMedId", column=@Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="ddiCodex", column=@Column(name="DDI_CODEX", nullable=false, precision=5, scale=0) ) } )

    public Rddimrm0RoutedMedLinkId getId() {
        return this.id;
    }
    
    public void setId(Rddimrm0RoutedMedLinkId id) {
        this.id = id;
    }
   








}