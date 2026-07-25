package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Ripdagt0ProductAgent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RIPDAGT0_PRODUCT_AGENT"
    ,schema="MEDK_FDB"
)

public class Ripdagt0ProductAgent  implements java.io.Serializable {


    // Fields    

     private Integer agentId;
     private String agentDesc;


    // Constructors

    /** default constructor */
    public Ripdagt0ProductAgent() {
    }

    
    /** full constructor */
    public Ripdagt0ProductAgent(Integer agentId, String agentDesc) {
        this.agentId = agentId;
        this.agentDesc = agentDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="AGENT_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getAgentId() {
        return this.agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }
    
    @Column(name="AGENT_DESC", nullable=false, length=100)

    public String getAgentDesc() {
        return this.agentDesc;
    }
    
    public void setAgentDesc(String agentDesc) {
        this.agentDesc = agentDesc;
    }
   








}