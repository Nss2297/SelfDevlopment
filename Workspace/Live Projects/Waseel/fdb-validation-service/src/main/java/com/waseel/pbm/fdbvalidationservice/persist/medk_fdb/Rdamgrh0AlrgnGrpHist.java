package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdamgrh0AlrgnGrpHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMGRH0_ALRGN_GRP_HIST"
    ,schema="MEDK_FDB"
)

public class Rdamgrh0AlrgnGrpHist  implements java.io.Serializable {


    // Fields    

     private Rdamgrh0AlrgnGrpHistId id;
     private Timestamp damAlrgnGrpReplEffDt;


    // Constructors

    /** default constructor */
    public Rdamgrh0AlrgnGrpHist() {
    }

	/** minimal constructor */
    public Rdamgrh0AlrgnGrpHist(Rdamgrh0AlrgnGrpHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rdamgrh0AlrgnGrpHist(Rdamgrh0AlrgnGrpHistId id, Timestamp damAlrgnGrpReplEffDt) {
        this.id = id;
        this.damAlrgnGrpReplEffDt = damAlrgnGrpReplEffDt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="replDamAlrgnGrp", column=@Column(name="REPL_DAM_ALRGN_GRP", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="prevDamAlrgnGrp", column=@Column(name="PREV_DAM_ALRGN_GRP", nullable=false, precision=6, scale=0) ) } )

    public Rdamgrh0AlrgnGrpHistId getId() {
        return this.id;
    }
    
    public void setId(Rdamgrh0AlrgnGrpHistId id) {
        this.id = id;
    }
    
    @Column(name="DAM_ALRGN_GRP_REPL_EFF_DT", length=7)

    public Timestamp getDamAlrgnGrpReplEffDt() {
        return this.damAlrgnGrpReplEffDt;
    }
    
    public void setDamAlrgnGrpReplEffDt(Timestamp damAlrgnGrpReplEffDt) {
        this.damAlrgnGrpReplEffDt = damAlrgnGrpReplEffDt;
    }
   








}