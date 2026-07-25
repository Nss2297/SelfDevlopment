package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rlblwmd0MultilingualDescId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rlblwmd0MultilingualDescId  implements java.io.Serializable {


    // Fields    

     private Integer langCd;
     private String lblWarn;
     private Byte lblTextsn;


    // Constructors

    /** default constructor */
    public Rlblwmd0MultilingualDescId() {
    }

    
    /** full constructor */
    public Rlblwmd0MultilingualDescId(Integer langCd, String lblWarn, Byte lblTextsn) {
        this.langCd = langCd;
        this.lblWarn = lblWarn;
        this.lblTextsn = lblTextsn;
    }

   
    // Property accessors

    @Column(name="LANG_CD", nullable=false, precision=5, scale=0)

    public Integer getLangCd() {
        return this.langCd;
    }
    
    public void setLangCd(Integer langCd) {
        this.langCd = langCd;
    }

    @Column(name="LBL_WARN", nullable=false, length=4)

    public String getLblWarn() {
        return this.lblWarn;
    }
    
    public void setLblWarn(String lblWarn) {
        this.lblWarn = lblWarn;
    }

    @Column(name="LBL_TEXTSN", nullable=false, precision=2, scale=0)

    public Byte getLblTextsn() {
        return this.lblTextsn;
    }
    
    public void setLblTextsn(Byte lblTextsn) {
        this.lblTextsn = lblTextsn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rlblwmd0MultilingualDescId) ) return false;
		 Rlblwmd0MultilingualDescId castOther = ( Rlblwmd0MultilingualDescId ) other; 
         
		 return ( (this.getLangCd()==castOther.getLangCd()) || ( this.getLangCd()!=null && castOther.getLangCd()!=null && this.getLangCd().equals(castOther.getLangCd()) ) )
 && ( (this.getLblWarn()==castOther.getLblWarn()) || ( this.getLblWarn()!=null && castOther.getLblWarn()!=null && this.getLblWarn().equals(castOther.getLblWarn()) ) )
 && ( (this.getLblTextsn()==castOther.getLblTextsn()) || ( this.getLblTextsn()!=null && castOther.getLblTextsn()!=null && this.getLblTextsn().equals(castOther.getLblTextsn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLangCd() == null ? 0 : this.getLangCd().hashCode() );
         result = 37 * result + ( getLblWarn() == null ? 0 : this.getLblWarn().hashCode() );
         result = 37 * result + ( getLblTextsn() == null ? 0 : this.getLblTextsn().hashCode() );
         return result;
   }   





}