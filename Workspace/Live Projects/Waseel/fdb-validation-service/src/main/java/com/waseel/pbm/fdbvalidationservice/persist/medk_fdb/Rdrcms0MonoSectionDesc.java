package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcms0MonoSectionDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCMS0_MONO_SECTION_DESC"
    ,schema="MEDK_FDB"
)

public class Rdrcms0MonoSectionDesc  implements java.io.Serializable {


    // Fields    

     private Short drcMonoSectionCd;
     private String drcMonoSectionCdDesc;


    // Constructors

    /** default constructor */
    public Rdrcms0MonoSectionDesc() {
    }

    
    /** full constructor */
    public Rdrcms0MonoSectionDesc(Short drcMonoSectionCd, String drcMonoSectionCdDesc) {
        this.drcMonoSectionCd = drcMonoSectionCd;
        this.drcMonoSectionCdDesc = drcMonoSectionCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DRC_MONO_SECTION_CD", unique=true, nullable=false, precision=4, scale=0)

    public Short getDrcMonoSectionCd() {
        return this.drcMonoSectionCd;
    }
    
    public void setDrcMonoSectionCd(Short drcMonoSectionCd) {
        this.drcMonoSectionCd = drcMonoSectionCd;
    }
    
    @Column(name="DRC_MONO_SECTION_CD_DESC", nullable=false, length=50)

    public String getDrcMonoSectionCdDesc() {
        return this.drcMonoSectionCdDesc;
    }
    
    public void setDrcMonoSectionCdDesc(String drcMonoSectionCdDesc) {
        this.drcMonoSectionCdDesc = drcMonoSectionCdDesc;
    }
   








}