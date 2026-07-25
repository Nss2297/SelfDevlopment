package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdamxsh0AlrgnXsenseHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMXSH0_ALRGN_XSENSE_HIST"
    ,schema="MEDK_FDB"
)

public class Rdamxsh0AlrgnXsenseHist  implements java.io.Serializable {


    // Fields    

     private Rdamxsh0AlrgnXsenseHistId id;
     private Timestamp damAlrgnXsenseReplEffDt;


    // Constructors

    /** default constructor */
    public Rdamxsh0AlrgnXsenseHist() {
    }

	/** minimal constructor */
    public Rdamxsh0AlrgnXsenseHist(Rdamxsh0AlrgnXsenseHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rdamxsh0AlrgnXsenseHist(Rdamxsh0AlrgnXsenseHistId id, Timestamp damAlrgnXsenseReplEffDt) {
        this.id = id;
        this.damAlrgnXsenseReplEffDt = damAlrgnXsenseReplEffDt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="replDamAlrgnXsense", column=@Column(name="REPL_DAM_ALRGN_XSENSE", nullable=false, precision=4, scale=0) ), 
        @AttributeOverride(name="prevDamAlrgnXsense", column=@Column(name="PREV_DAM_ALRGN_XSENSE", nullable=false, precision=4, scale=0) ) } )

    public Rdamxsh0AlrgnXsenseHistId getId() {
        return this.id;
    }
    
    public void setId(Rdamxsh0AlrgnXsenseHistId id) {
        this.id = id;
    }
    
    @Column(name="DAM_ALRGN_XSENSE_REPL_EFF_DT", length=7)

    public Timestamp getDamAlrgnXsenseReplEffDt() {
        return this.damAlrgnXsenseReplEffDt;
    }
    
    public void setDamAlrgnXsenseReplEffDt(Timestamp damAlrgnXsenseReplEffDt) {
        this.damAlrgnXsenseReplEffDt = damAlrgnXsenseReplEffDt;
    }
   








}