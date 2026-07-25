package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdamgx0AlrgnGrpXsenseLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMGX0_ALRGN_GRP_XSENSE_LINK"
    ,schema="MEDK_FDB"
)

public class Rdamgx0AlrgnGrpXsenseLink  implements java.io.Serializable {


    // Fields    

     private Rdamgx0AlrgnGrpXsenseLinkId id;


    // Constructors

    /** default constructor */
    public Rdamgx0AlrgnGrpXsenseLink() {
    }

    
    /** full constructor */
    public Rdamgx0AlrgnGrpXsenseLink(Rdamgx0AlrgnGrpXsenseLinkId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="damAlrgnGrp", column=@Column(name="DAM_ALRGN_GRP", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="damAlrgnXsense", column=@Column(name="DAM_ALRGN_XSENSE", nullable=false, precision=4, scale=0) ) } )

    public Rdamgx0AlrgnGrpXsenseLinkId getId() {
        return this.id;
    }
    
    public void setId(Rdamgx0AlrgnGrpXsenseLinkId id) {
        this.id = id;
    }
   








}