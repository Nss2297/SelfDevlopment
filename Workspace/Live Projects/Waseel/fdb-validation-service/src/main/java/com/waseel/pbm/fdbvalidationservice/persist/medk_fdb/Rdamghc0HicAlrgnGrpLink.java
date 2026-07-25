package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdamghc0HicAlrgnGrpLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMGHC0_HIC_ALRGN_GRP_LINK"
    ,schema="MEDK_FDB"
)

public class Rdamghc0HicAlrgnGrpLink  implements java.io.Serializable {


    // Fields    

     private Rdamghc0HicAlrgnGrpLinkId id;


    // Constructors

    /** default constructor */
    public Rdamghc0HicAlrgnGrpLink() {
    }

    
    /** full constructor */
    public Rdamghc0HicAlrgnGrpLink(Rdamghc0HicAlrgnGrpLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="hicSeqn", column=@Column(name="HIC_SEQN", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="damAlrgnGrp", column=@Column(name="DAM_ALRGN_GRP", nullable=false, precision=6, scale=0) ) } )

    public Rdamghc0HicAlrgnGrpLinkId getId() {
        return this.id;
    }
    
    public void setId(Rdamghc0HicAlrgnGrpLinkId id) {
        this.id = id;
    }
   








}