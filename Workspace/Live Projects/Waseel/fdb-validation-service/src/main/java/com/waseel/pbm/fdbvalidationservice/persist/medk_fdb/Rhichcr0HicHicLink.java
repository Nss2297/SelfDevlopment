package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rhichcr0HicHicLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHICHCR0_HIC_HIC_LINK"
    ,schema="MEDK_FDB"
)

public class Rhichcr0HicHicLink  implements java.io.Serializable {


    // Fields    

     private Rhichcr0HicHicLinkId id;


    // Constructors

    /** default constructor */
    public Rhichcr0HicHicLink() {
    }

    
    /** full constructor */
    public Rhichcr0HicHicLink(Rhichcr0HicHicLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="hicSeqn", column=@Column(name="HIC_SEQN", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="relatedHicSeqn", column=@Column(name="RELATED_HIC_SEQN", nullable=false, precision=6, scale=0) ) } )

    public Rhichcr0HicHicLinkId getId() {
        return this.id;
    }
    
    public void setId(Rhichcr0HicHicLinkId id) {
        this.id = id;
    }
   








}