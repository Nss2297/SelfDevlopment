package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmedmrd0MoveReasonDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMEDMRD0_MOVE_REASON_DESC"
    ,schema="MEDK_FDB"
)

public class Rmedmrd0MoveReasonDesc  implements java.io.Serializable {


    // Fields    

     private Short moveReasonCd;
     private String moveReasonCdDesc;


    // Constructors

    /** default constructor */
    public Rmedmrd0MoveReasonDesc() {
    }

    
    /** full constructor */
    public Rmedmrd0MoveReasonDesc(Short moveReasonCd, String moveReasonCdDesc) {
        this.moveReasonCd = moveReasonCd;
        this.moveReasonCdDesc = moveReasonCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MOVE_REASON_CD", unique=true, nullable=false, precision=4, scale=0)

    public Short getMoveReasonCd() {
        return this.moveReasonCd;
    }
    
    public void setMoveReasonCd(Short moveReasonCd) {
        this.moveReasonCd = moveReasonCd;
    }
    
    @Column(name="MOVE_REASON_CD_DESC", nullable=false, length=100)

    public String getMoveReasonCdDesc() {
        return this.moveReasonCdDesc;
    }
    
    public void setMoveReasonCdDesc(String moveReasonCdDesc) {
        this.moveReasonCdDesc = moveReasonCdDesc;
    }
   








}