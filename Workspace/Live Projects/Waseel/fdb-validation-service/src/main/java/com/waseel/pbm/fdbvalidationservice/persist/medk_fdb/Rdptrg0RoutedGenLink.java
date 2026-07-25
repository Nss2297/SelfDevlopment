package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdptrg0RoutedGenLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDPTRG0_ROUTED_GEN_LINK"
    ,schema="MEDK_FDB"
)

public class Rdptrg0RoutedGenLink  implements java.io.Serializable {


    // Fields    

     private Rdptrg0RoutedGenLinkId id;


    // Constructors

    /** default constructor */
    public Rdptrg0RoutedGenLink() {
    }

    
    /** full constructor */
    public Rdptrg0RoutedGenLink(Rdptrg0RoutedGenLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="routedGenId", column=@Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="dptClassId", column=@Column(name="DPT_CLASS_ID", nullable=false, precision=8, scale=0) ) } )

    public Rdptrg0RoutedGenLinkId getId() {
        return this.id;
    }
    
    public void setId(Rdptrg0RoutedGenLinkId id) {
        this.id = id;
    }
   








}