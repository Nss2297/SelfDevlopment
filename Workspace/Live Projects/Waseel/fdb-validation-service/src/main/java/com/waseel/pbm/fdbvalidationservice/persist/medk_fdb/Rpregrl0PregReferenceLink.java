package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpregrl0PregReferenceLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPREGRL0_PREG_REFERENCE_LINK"
    ,schema="MEDK_FDB"
)

public class Rpregrl0PregReferenceLink  implements java.io.Serializable {


    // Fields    

     private Rpregrl0PregReferenceLinkId id;


    // Constructors

    /** default constructor */
    public Rpregrl0PregReferenceLink() {
    }

    
    /** full constructor */
    public Rpregrl0PregReferenceLink(Rpregrl0PregReferenceLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="pregCode", column=@Column(name="PREG_CODE", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="pregReferenceId", column=@Column(name="PREG_REFERENCE_ID", nullable=false, precision=8, scale=0) ) } )

    public Rpregrl0PregReferenceLinkId getId() {
        return this.id;
    }
    
    public void setId(Rpregrl0PregReferenceLinkId id) {
        this.id = id;
    }
   








}