package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rlactsd0SeverLevelDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RLACTSD0_SEVER_LEVEL_DESC"
    ,schema="MEDK_FDB"
)

public class Rlactsd0SeverLevelDesc  implements java.io.Serializable {


    // Fields    

     private Rlactsd0SeverLevelDescId id;
     private String lactSlDesc;


    // Constructors

    /** default constructor */
    public Rlactsd0SeverLevelDesc() {
    }

    
    /** full constructor */
    public Rlactsd0SeverLevelDesc(Rlactsd0SeverLevelDescId id, String lactSlDesc) {
        this.id = id;
        this.lactSlDesc = lactSlDesc;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="lactSl", column=@Column(name="LACT_SL", nullable=false, length=1) ), 
        @AttributeOverride(name="lactSlsn", column=@Column(name="LACT_SLSN", nullable=false, precision=2, scale=0) ) } )

    public Rlactsd0SeverLevelDescId getId() {
        return this.id;
    }
    
    public void setId(Rlactsd0SeverLevelDescId id) {
        this.id = id;
    }
    
    @Column(name="LACT_SL_DESC", nullable=false, length=60)

    public String getLactSlDesc() {
        return this.lactSlDesc;
    }
    
    public void setLactSlDesc(String lactSlDesc) {
        this.lactSlDesc = lactSlDesc;
    }
   








}