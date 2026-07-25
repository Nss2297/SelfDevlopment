package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpregsl1PregSeverLevel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPREGSL1_PREG_SEVER_LEVEL"
    ,schema="MEDK_FDB"
)

public class Rpregsl1PregSeverLevel  implements java.io.Serializable {


    // Fields    

     private Rpregsl1PregSeverLevelId id;
     private String pregSld;


    // Constructors

    /** default constructor */
    public Rpregsl1PregSeverLevel() {
    }

    
    /** full constructor */
    public Rpregsl1PregSeverLevel(Rpregsl1PregSeverLevelId id, String pregSld) {
        this.id = id;
        this.pregSld = pregSld;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="pregSl", column=@Column(name="PREG_SL", nullable=false, length=1) ), 
        @AttributeOverride(name="pregSlsn", column=@Column(name="PREG_SLSN", nullable=false, precision=2, scale=0) ) } )

    public Rpregsl1PregSeverLevelId getId() {
        return this.id;
    }
    
    public void setId(Rpregsl1PregSeverLevelId id) {
        this.id = id;
    }
    
    @Column(name="PREG_SLD", nullable=false, length=60)

    public String getPregSld() {
        return this.pregSld;
    }
    
    public void setPregSld(String pregSld) {
        this.pregSld = pregSld;
    }
   








}