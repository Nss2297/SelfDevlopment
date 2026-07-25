package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rlacted0ExcrtPotentialDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RLACTED0_EXCRT_POTENTIAL_DESC"
    ,schema="MEDK_FDB"
)

public class Rlacted0ExcrtPotentialDesc  implements java.io.Serializable {


    // Fields    

     private Rlacted0ExcrtPotentialDescId id;
     private String lactExcrtDesc;


    // Constructors

    /** default constructor */
    public Rlacted0ExcrtPotentialDesc() {
    }

    
    /** full constructor */
    public Rlacted0ExcrtPotentialDesc(Rlacted0ExcrtPotentialDescId id, String lactExcrtDesc) {
        this.id = id;
        this.lactExcrtDesc = lactExcrtDesc;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="lactExcrt", column=@Column(name="LACT_EXCRT", nullable=false, length=1) ), 
        @AttributeOverride(name="lactExcrtsn", column=@Column(name="LACT_EXCRTSN", nullable=false, precision=2, scale=0) ) } )

    public Rlacted0ExcrtPotentialDescId getId() {
        return this.id;
    }
    
    public void setId(Rlacted0ExcrtPotentialDescId id) {
        this.id = id;
    }
    
    @Column(name="LACT_EXCRT_DESC", nullable=false, length=60)

    public String getLactExcrtDesc() {
        return this.lactExcrtDesc;
    }
    
    public void setLactExcrtDesc(String lactExcrtDesc) {
        this.lactExcrtDesc = lactExcrtDesc;
    }
   








}