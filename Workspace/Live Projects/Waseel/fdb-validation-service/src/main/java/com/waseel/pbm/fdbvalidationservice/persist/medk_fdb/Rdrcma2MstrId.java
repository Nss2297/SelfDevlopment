package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdrcma2MstrId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdrcma2MstrId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private String dr2Rt;
     private Integer dr2Loaged;
     private Integer dr2Hiaged;
     private String fdbdx;
     private String dr2Dostpi;


    // Constructors

    /** default constructor */
    public Rdrcma2MstrId() {
    }

    
    /** full constructor */
    public Rdrcma2MstrId(Integer gcnSeqno, String dr2Rt, Integer dr2Loaged, Integer dr2Hiaged, String fdbdx, String dr2Dostpi) {
        this.gcnSeqno = gcnSeqno;
        this.dr2Rt = dr2Rt;
        this.dr2Loaged = dr2Loaged;
        this.dr2Hiaged = dr2Hiaged;
        this.fdbdx = fdbdx;
        this.dr2Dostpi = dr2Dostpi;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="DR2_RT", nullable=false, length=3)

    public String getDr2Rt() {
        return this.dr2Rt;
    }
    
    public void setDr2Rt(String dr2Rt) {
        this.dr2Rt = dr2Rt;
    }

    @Column(name="DR2_LOAGED", nullable=false, precision=5, scale=0)

    public Integer getDr2Loaged() {
        return this.dr2Loaged;
    }
    
    public void setDr2Loaged(Integer dr2Loaged) {
        this.dr2Loaged = dr2Loaged;
    }

    @Column(name="DR2_HIAGED", nullable=false, precision=5, scale=0)

    public Integer getDr2Hiaged() {
        return this.dr2Hiaged;
    }
    
    public void setDr2Hiaged(Integer dr2Hiaged) {
        this.dr2Hiaged = dr2Hiaged;
    }

    @Column(name="FDBDX", nullable=false, length=9)

    public String getFdbdx() {
        return this.fdbdx;
    }
    
    public void setFdbdx(String fdbdx) {
        this.fdbdx = fdbdx;
    }

    @Column(name="DR2_DOSTPI", nullable=false, length=2)

    public String getDr2Dostpi() {
        return this.dr2Dostpi;
    }
    
    public void setDr2Dostpi(String dr2Dostpi) {
        this.dr2Dostpi = dr2Dostpi;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdrcma2MstrId) ) return false;
		 Rdrcma2MstrId castOther = ( Rdrcma2MstrId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getDr2Rt()==castOther.getDr2Rt()) || ( this.getDr2Rt()!=null && castOther.getDr2Rt()!=null && this.getDr2Rt().equals(castOther.getDr2Rt()) ) )
 && ( (this.getDr2Loaged()==castOther.getDr2Loaged()) || ( this.getDr2Loaged()!=null && castOther.getDr2Loaged()!=null && this.getDr2Loaged().equals(castOther.getDr2Loaged()) ) )
 && ( (this.getDr2Hiaged()==castOther.getDr2Hiaged()) || ( this.getDr2Hiaged()!=null && castOther.getDr2Hiaged()!=null && this.getDr2Hiaged().equals(castOther.getDr2Hiaged()) ) )
 && ( (this.getFdbdx()==castOther.getFdbdx()) || ( this.getFdbdx()!=null && castOther.getFdbdx()!=null && this.getFdbdx().equals(castOther.getFdbdx()) ) )
 && ( (this.getDr2Dostpi()==castOther.getDr2Dostpi()) || ( this.getDr2Dostpi()!=null && castOther.getDr2Dostpi()!=null && this.getDr2Dostpi().equals(castOther.getDr2Dostpi()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getDr2Rt() == null ? 0 : this.getDr2Rt().hashCode() );
         result = 37 * result + ( getDr2Loaged() == null ? 0 : this.getDr2Loaged().hashCode() );
         result = 37 * result + ( getDr2Hiaged() == null ? 0 : this.getDr2Hiaged().hashCode() );
         result = 37 * result + ( getFdbdx() == null ? 0 : this.getFdbdx().hashCode() );
         result = 37 * result + ( getDr2Dostpi() == null ? 0 : this.getDr2Dostpi().hashCode() );
         return result;
   }   





}