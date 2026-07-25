package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rddimrg0RoutedGenLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDIMRG0_ROUTED_GEN_LINK"
    ,schema="MEDK_FDB"
)

public class Rddimrg0RoutedGenLink  implements java.io.Serializable {


    // Fields    

     private Rddimrg0RoutedGenLinkId id;


    // Constructors

    /** default constructor */
    public Rddimrg0RoutedGenLink() {
    }

    
    /** full constructor */
    public Rddimrg0RoutedGenLink(Rddimrg0RoutedGenLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedGenId", column=@Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="ddiCodex", column=@Column(name="DDI_CODEX", nullable=false, precision=5, scale=0) ) } )

    public Rddimrg0RoutedGenLinkId getId() {
        return this.id;
    }
    
    public void setId(Rddimrg0RoutedGenLinkId id) {
        this.id = id;
    }
   








}