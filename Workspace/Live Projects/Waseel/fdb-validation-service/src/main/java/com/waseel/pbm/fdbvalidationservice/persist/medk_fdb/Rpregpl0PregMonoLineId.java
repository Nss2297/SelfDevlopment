package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpregpl0PregMonoLineId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpregpl0PregMonoLineId  implements java.io.Serializable {


    // Fields    

     private Integer pregMonoId;
     private Short pregMonoSectionCd;
     private Byte pregMonoSn;


    // Constructors

    /** default constructor */
    public Rpregpl0PregMonoLineId() {
    }

    
    /** full constructor */
    public Rpregpl0PregMonoLineId(Integer pregMonoId, Short pregMonoSectionCd, Byte pregMonoSn) {
        this.pregMonoId = pregMonoId;
        this.pregMonoSectionCd = pregMonoSectionCd;
        this.pregMonoSn = pregMonoSn;
    }

   
    // Property accessors

    @Column(name="PREG_MONO_ID", nullable=false, precision=8, scale=0)

    public Integer getPregMonoId() {
        return this.pregMonoId;
    }
    
    public void setPregMonoId(Integer pregMonoId) {
        this.pregMonoId = pregMonoId;
    }

    @Column(name="PREG_MONO_SECTION_CD", nullable=false, precision=4, scale=0)

    public Short getPregMonoSectionCd() {
        return this.pregMonoSectionCd;
    }
    
    public void setPregMonoSectionCd(Short pregMonoSectionCd) {
        this.pregMonoSectionCd = pregMonoSectionCd;
    }

    @Column(name="PREG_MONO_SN", nullable=false, precision=2, scale=0)

    public Byte getPregMonoSn() {
        return this.pregMonoSn;
    }
    
    public void setPregMonoSn(Byte pregMonoSn) {
        this.pregMonoSn = pregMonoSn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpregpl0PregMonoLineId) ) return false;
		 Rpregpl0PregMonoLineId castOther = ( Rpregpl0PregMonoLineId ) other; 
         
		 return ( (this.getPregMonoId()==castOther.getPregMonoId()) || ( this.getPregMonoId()!=null && castOther.getPregMonoId()!=null && this.getPregMonoId().equals(castOther.getPregMonoId()) ) )
 && ( (this.getPregMonoSectionCd()==castOther.getPregMonoSectionCd()) || ( this.getPregMonoSectionCd()!=null && castOther.getPregMonoSectionCd()!=null && this.getPregMonoSectionCd().equals(castOther.getPregMonoSectionCd()) ) )
 && ( (this.getPregMonoSn()==castOther.getPregMonoSn()) || ( this.getPregMonoSn()!=null && castOther.getPregMonoSn()!=null && this.getPregMonoSn().equals(castOther.getPregMonoSn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPregMonoId() == null ? 0 : this.getPregMonoId().hashCode() );
         result = 37 * result + ( getPregMonoSectionCd() == null ? 0 : this.getPregMonoSectionCd().hashCode() );
         result = 37 * result + ( getPregMonoSn() == null ? 0 : this.getPregMonoSn().hashCode() );
         return result;
   }   





}