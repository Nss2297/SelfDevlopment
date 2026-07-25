package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdamxhc0HicAlrgnXsenseLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMXHC0_HIC_ALRGN_XSENSE_LINK"
    ,schema="MEDK_FDB"
)

public class Rdamxhc0HicAlrgnXsenseLink  implements java.io.Serializable {


    // Fields    

     private Rdamxhc0HicAlrgnXsenseLinkId id;


    // Constructors

    /** default constructor */
    public Rdamxhc0HicAlrgnXsenseLink() {
    }

    
    /** full constructor */
    public Rdamxhc0HicAlrgnXsenseLink(Rdamxhc0HicAlrgnXsenseLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="hicSeqn", column=@Column(name="HIC_SEQN", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="damAlrgnXsense", column=@Column(name="DAM_ALRGN_XSENSE", nullable=false, precision=4, scale=0) ) } )

    public Rdamxhc0HicAlrgnXsenseLinkId getId() {
        return this.id;
    }
    
    public void setId(Rdamxhc0HicAlrgnXsenseLinkId id) {
        this.id = id;
    }
   








}