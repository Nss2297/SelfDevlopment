package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rlblwmd0MultilingualDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RLBLWMD0_MULTILINGUAL_DESC"
    ,schema="MEDK_FDB"
)

public class Rlblwmd0MultilingualDesc  implements java.io.Serializable {


    // Fields    

     private Rlblwmd0MultilingualDescId id;
     private String lblDesc;


    // Constructors

    /** default constructor */
    public Rlblwmd0MultilingualDesc() {
    }

	/** minimal constructor */
    public Rlblwmd0MultilingualDesc(Rlblwmd0MultilingualDescId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rlblwmd0MultilingualDesc(Rlblwmd0MultilingualDescId id, String lblDesc) {
        this.id = id;
        this.lblDesc = lblDesc;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="langCd", column=@Column(name="LANG_CD", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="lblWarn", column=@Column(name="LBL_WARN", nullable=false, length=4) ), 
        @AttributeOverride(name="lblTextsn", column=@Column(name="LBL_TEXTSN", nullable=false, precision=2, scale=0) ) } )

    public Rlblwmd0MultilingualDescId getId() {
        return this.id;
    }
    
    public void setId(Rlblwmd0MultilingualDescId id) {
        this.id = id;
    }
    
    @Column(name="LBL_DESC")

    public String getLblDesc() {
        return this.lblDesc;
    }
    
    public void setLblDesc(String lblDesc) {
        this.lblDesc = lblDesc;
    }
   








}