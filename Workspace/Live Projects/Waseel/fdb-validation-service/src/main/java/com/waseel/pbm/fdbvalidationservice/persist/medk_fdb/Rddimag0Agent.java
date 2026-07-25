package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rddimag0Agent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDIMAG0_AGENT"
    ,schema="MEDK_FDB"
)

public class Rddimag0Agent  implements java.io.Serializable {


    // Fields    

     private Rddimag0AgentId id;
     private String ddiAgd;


    // Constructors

    /** default constructor */
    public Rddimag0Agent() {
    }

	/** minimal constructor */
    public Rddimag0Agent(Rddimag0AgentId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rddimag0Agent(Rddimag0AgentId id, String ddiAgd) {
        this.id = id;
        this.ddiAgd = ddiAgd;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="ddiCodex", column=@Column(name="DDI_CODEX", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="ddiAgsn", column=@Column(name="DDI_AGSN", nullable=false, precision=3, scale=0) ) } )

    public Rddimag0AgentId getId() {
        return this.id;
    }
    
    public void setId(Rddimag0AgentId id) {
        this.id = id;
    }
    
    @Column(name="DDI_AGD", length=41)

    public String getDdiAgd() {
        return this.ddiAgd;
    }
    
    public void setDdiAgd(String ddiAgd) {
        this.ddiAgd = ddiAgd;
    }
   








}