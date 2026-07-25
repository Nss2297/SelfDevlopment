package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Radida0DisplayAction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIDA0_DISPLAY_ACTION"
    ,schema="MEDK_FDB"
)

public class Radida0DisplayAction  implements java.io.Serializable {


    // Fields    

     private Integer ddiDisplayActionId;
     private String ddiDisplayActionDesc;


    // Constructors

    /** default constructor */
    public Radida0DisplayAction() {
    }

    
    /** full constructor */
    public Radida0DisplayAction(Integer ddiDisplayActionId, String ddiDisplayActionDesc) {
        this.ddiDisplayActionId = ddiDisplayActionId;
        this.ddiDisplayActionDesc = ddiDisplayActionDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DDI_DISPLAY_ACTION_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getDdiDisplayActionId() {
        return this.ddiDisplayActionId;
    }
    
    public void setDdiDisplayActionId(Integer ddiDisplayActionId) {
        this.ddiDisplayActionId = ddiDisplayActionId;
    }
    
    @Column(name="DDI_DISPLAY_ACTION_DESC", nullable=false, length=50)

    public String getDdiDisplayActionDesc() {
        return this.ddiDisplayActionDesc;
    }
    
    public void setDdiDisplayActionDesc(String ddiDisplayActionDesc) {
        this.ddiDisplayActionDesc = ddiDisplayActionDesc;
    }
   








}