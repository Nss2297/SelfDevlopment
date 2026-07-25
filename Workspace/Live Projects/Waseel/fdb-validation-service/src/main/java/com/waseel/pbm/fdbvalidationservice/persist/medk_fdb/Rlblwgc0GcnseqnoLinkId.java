package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rlblwgc0GcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rlblwgc0GcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private String lblWarn;


    // Constructors

    /** default constructor */
    public Rlblwgc0GcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rlblwgc0GcnseqnoLinkId(Integer gcnSeqno, String lblWarn) {
        this.gcnSeqno = gcnSeqno;
        this.lblWarn = lblWarn;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="LBL_WARN", nullable=false, length=4)

    public String getLblWarn() {
        return this.lblWarn;
    }
    
    public void setLblWarn(String lblWarn) {
        this.lblWarn = lblWarn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rlblwgc0GcnseqnoLinkId) ) return false;
		 Rlblwgc0GcnseqnoLinkId castOther = ( Rlblwgc0GcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getLblWarn()==castOther.getLblWarn()) || ( this.getLblWarn()!=null && castOther.getLblWarn()!=null && this.getLblWarn().equals(castOther.getLblWarn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getLblWarn() == null ? 0 : this.getLblWarn().hashCode() );
         return result;
   }   





}