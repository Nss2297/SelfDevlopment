package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rpregms0MonoSectionDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPREGMS0_MONO_SECTION_DESC"
    ,schema="MEDK_FDB"
)

public class Rpregms0MonoSectionDesc  implements java.io.Serializable {


    // Fields    

     private Short pregMonoSectionCd;
     private String pregMonoSectionCdDesc;


    // Constructors

    /** default constructor */
    public Rpregms0MonoSectionDesc() {
    }

    
    /** full constructor */
    public Rpregms0MonoSectionDesc(Short pregMonoSectionCd, String pregMonoSectionCdDesc) {
        this.pregMonoSectionCd = pregMonoSectionCd;
        this.pregMonoSectionCdDesc = pregMonoSectionCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PREG_MONO_SECTION_CD", unique=true, nullable=false, precision=4, scale=0)

    public Short getPregMonoSectionCd() {
        return this.pregMonoSectionCd;
    }
    
    public void setPregMonoSectionCd(Short pregMonoSectionCd) {
        this.pregMonoSectionCd = pregMonoSectionCd;
    }
    
    @Column(name="PREG_MONO_SECTION_CD_DESC", nullable=false, length=50)

    public String getPregMonoSectionCdDesc() {
        return this.pregMonoSectionCdDesc;
    }
    
    public void setPregMonoSectionCdDesc(String pregMonoSectionCdDesc) {
        this.pregMonoSectionCdDesc = pregMonoSectionCdDesc;
    }
   








}