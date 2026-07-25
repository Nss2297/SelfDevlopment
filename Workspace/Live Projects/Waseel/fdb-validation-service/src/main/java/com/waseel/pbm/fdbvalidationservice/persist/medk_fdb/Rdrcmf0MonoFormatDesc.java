package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcmf0MonoFormatDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCMF0_MONO_FORMAT_DESC"
    ,schema="MEDK_FDB"
)

public class Rdrcmf0MonoFormatDesc  implements java.io.Serializable {


    // Fields    

     private Short drcMonoFormatCd;
     private String drcMonoFormatCdDesc;


    // Constructors

    /** default constructor */
    public Rdrcmf0MonoFormatDesc() {
    }

    
    /** full constructor */
    public Rdrcmf0MonoFormatDesc(Short drcMonoFormatCd, String drcMonoFormatCdDesc) {
        this.drcMonoFormatCd = drcMonoFormatCd;
        this.drcMonoFormatCdDesc = drcMonoFormatCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DRC_MONO_FORMAT_CD", unique=true, nullable=false, precision=4, scale=0)

    public Short getDrcMonoFormatCd() {
        return this.drcMonoFormatCd;
    }
    
    public void setDrcMonoFormatCd(Short drcMonoFormatCd) {
        this.drcMonoFormatCd = drcMonoFormatCd;
    }
    
    @Column(name="DRC_MONO_FORMAT_CD_DESC", nullable=false, length=50)

    public String getDrcMonoFormatCdDesc() {
        return this.drcMonoFormatCdDesc;
    }
    
    public void setDrcMonoFormatCdDesc(String drcMonoFormatCdDesc) {
        this.drcMonoFormatCdDesc = drcMonoFormatCdDesc;
    }
   








}