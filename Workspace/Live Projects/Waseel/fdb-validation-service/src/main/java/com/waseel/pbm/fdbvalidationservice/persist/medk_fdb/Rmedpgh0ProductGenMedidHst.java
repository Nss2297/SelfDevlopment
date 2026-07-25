package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rmedpgh0ProductGenMedidHst entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMEDPGH0_PRODUCT_GEN_MEDID_HST"
    ,schema="MEDK_FDB"
)

public class Rmedpgh0ProductGenMedidHst  implements java.io.Serializable {


    // Fields    

     private Rmedpgh0ProductGenMedidHstId id;
     private Integer prevMedid;
     private String prevMedidNameSourceCd;
     private String prevMedidOldStatusCd;
     private String prevMedidNewStatusCd;
     private String prevMedidDesc;
     private Integer currMedid;
     private String currMedidNameSourceCd;
     private String currMedidOldStatusCd;
     private String currMedidNewStatusCd;
     private String currMedidDesc;


    // Constructors

    /** default constructor */
    public Rmedpgh0ProductGenMedidHst() {
    }

	/** minimal constructor */
    public Rmedpgh0ProductGenMedidHst(Rmedpgh0ProductGenMedidHstId id, Integer prevMedid, String prevMedidNameSourceCd, String prevMedidOldStatusCd, String prevMedidNewStatusCd, String prevMedidDesc, Integer currMedid, String currMedidNameSourceCd, String currMedidNewStatusCd, String currMedidDesc) {
        this.id = id;
        this.prevMedid = prevMedid;
        this.prevMedidNameSourceCd = prevMedidNameSourceCd;
        this.prevMedidOldStatusCd = prevMedidOldStatusCd;
        this.prevMedidNewStatusCd = prevMedidNewStatusCd;
        this.prevMedidDesc = prevMedidDesc;
        this.currMedid = currMedid;
        this.currMedidNameSourceCd = currMedidNameSourceCd;
        this.currMedidNewStatusCd = currMedidNewStatusCd;
        this.currMedidDesc = currMedidDesc;
    }
    
    /** full constructor */
    public Rmedpgh0ProductGenMedidHst(Rmedpgh0ProductGenMedidHstId id, Integer prevMedid, String prevMedidNameSourceCd, String prevMedidOldStatusCd, String prevMedidNewStatusCd, String prevMedidDesc, Integer currMedid, String currMedidNameSourceCd, String currMedidOldStatusCd, String currMedidNewStatusCd, String currMedidDesc) {
        this.id = id;
        this.prevMedid = prevMedid;
        this.prevMedidNameSourceCd = prevMedidNameSourceCd;
        this.prevMedidOldStatusCd = prevMedidOldStatusCd;
        this.prevMedidNewStatusCd = prevMedidNewStatusCd;
        this.prevMedidDesc = prevMedidDesc;
        this.currMedid = currMedid;
        this.currMedidNameSourceCd = currMedidNameSourceCd;
        this.currMedidOldStatusCd = currMedidOldStatusCd;
        this.currMedidNewStatusCd = currMedidNewStatusCd;
        this.currMedidDesc = currMedidDesc;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="productId", column=@Column(name="PRODUCT_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="productionDate", column=@Column(name="PRODUCTION_DATE", nullable=false, length=7) ) } )

    public Rmedpgh0ProductGenMedidHstId getId() {
        return this.id;
    }
    
    public void setId(Rmedpgh0ProductGenMedidHstId id) {
        this.id = id;
    }
    
    @Column(name="PREV_MEDID", nullable=false, precision=8, scale=0)

    public Integer getPrevMedid() {
        return this.prevMedid;
    }
    
    public void setPrevMedid(Integer prevMedid) {
        this.prevMedid = prevMedid;
    }
    
    @Column(name="PREV_MEDID_NAME_SOURCE_CD", nullable=false, length=1)

    public String getPrevMedidNameSourceCd() {
        return this.prevMedidNameSourceCd;
    }
    
    public void setPrevMedidNameSourceCd(String prevMedidNameSourceCd) {
        this.prevMedidNameSourceCd = prevMedidNameSourceCd;
    }
    
    @Column(name="PREV_MEDID_OLD_STATUS_CD", nullable=false, length=1)

    public String getPrevMedidOldStatusCd() {
        return this.prevMedidOldStatusCd;
    }
    
    public void setPrevMedidOldStatusCd(String prevMedidOldStatusCd) {
        this.prevMedidOldStatusCd = prevMedidOldStatusCd;
    }
    
    @Column(name="PREV_MEDID_NEW_STATUS_CD", nullable=false, length=1)

    public String getPrevMedidNewStatusCd() {
        return this.prevMedidNewStatusCd;
    }
    
    public void setPrevMedidNewStatusCd(String prevMedidNewStatusCd) {
        this.prevMedidNewStatusCd = prevMedidNewStatusCd;
    }
    
    @Column(name="PREV_MEDID_DESC", nullable=false, length=70)

    public String getPrevMedidDesc() {
        return this.prevMedidDesc;
    }
    
    public void setPrevMedidDesc(String prevMedidDesc) {
        this.prevMedidDesc = prevMedidDesc;
    }
    
    @Column(name="CURR_MEDID", nullable=false, precision=8, scale=0)

    public Integer getCurrMedid() {
        return this.currMedid;
    }
    
    public void setCurrMedid(Integer currMedid) {
        this.currMedid = currMedid;
    }
    
    @Column(name="CURR_MEDID_NAME_SOURCE_CD", nullable=false, length=1)

    public String getCurrMedidNameSourceCd() {
        return this.currMedidNameSourceCd;
    }
    
    public void setCurrMedidNameSourceCd(String currMedidNameSourceCd) {
        this.currMedidNameSourceCd = currMedidNameSourceCd;
    }
    
    @Column(name="CURR_MEDID_OLD_STATUS_CD", length=1)

    public String getCurrMedidOldStatusCd() {
        return this.currMedidOldStatusCd;
    }
    
    public void setCurrMedidOldStatusCd(String currMedidOldStatusCd) {
        this.currMedidOldStatusCd = currMedidOldStatusCd;
    }
    
    @Column(name="CURR_MEDID_NEW_STATUS_CD", nullable=false, length=1)

    public String getCurrMedidNewStatusCd() {
        return this.currMedidNewStatusCd;
    }
    
    public void setCurrMedidNewStatusCd(String currMedidNewStatusCd) {
        this.currMedidNewStatusCd = currMedidNewStatusCd;
    }
    
    @Column(name="CURR_MEDID_DESC", nullable=false, length=70)

    public String getCurrMedidDesc() {
        return this.currMedidDesc;
    }
    
    public void setCurrMedidDesc(String currMedidDesc) {
        this.currMedidDesc = currMedidDesc;
    }
   








}