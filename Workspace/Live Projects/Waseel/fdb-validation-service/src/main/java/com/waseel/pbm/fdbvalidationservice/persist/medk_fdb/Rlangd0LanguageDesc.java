package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rlangd0LanguageDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RLANGD0_LANGUAGE_DESC"
    ,schema="MEDK_FDB"
)

public class Rlangd0LanguageDesc  implements java.io.Serializable {


    // Fields    

     private Integer langCd;
     private String langDesc;


    // Constructors

    /** default constructor */
    public Rlangd0LanguageDesc() {
    }

    
    /** full constructor */
    public Rlangd0LanguageDesc(Integer langCd, String langDesc) {
        this.langCd = langCd;
        this.langDesc = langDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="LANG_CD", unique=true, nullable=false, precision=5, scale=0)

    public Integer getLangCd() {
        return this.langCd;
    }
    
    public void setLangCd(Integer langCd) {
        this.langCd = langCd;
    }
    
    @Column(name="LANG_DESC", nullable=false, length=50)

    public String getLangDesc() {
        return this.langDesc;
    }
    
    public void setLangDesc(String langDesc) {
        this.langDesc = langDesc;
    }
   








}