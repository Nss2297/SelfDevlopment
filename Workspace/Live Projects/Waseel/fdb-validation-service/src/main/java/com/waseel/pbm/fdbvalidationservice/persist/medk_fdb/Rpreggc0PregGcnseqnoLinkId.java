package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpreggc0PregGcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpreggc0PregGcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer pregCode;


    // Constructors

    /** default constructor */
    public Rpreggc0PregGcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rpreggc0PregGcnseqnoLinkId(Integer gcnSeqno, Integer pregCode) {
        this.gcnSeqno = gcnSeqno;
        this.pregCode = pregCode;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="PREG_CODE", nullable=false, precision=6, scale=0)

    public Integer getPregCode() {
        return this.pregCode;
    }
    
    public void setPregCode(Integer pregCode) {
        this.pregCode = pregCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpreggc0PregGcnseqnoLinkId) ) return false;
		 Rpreggc0PregGcnseqnoLinkId castOther = ( Rpreggc0PregGcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getPregCode()==castOther.getPregCode()) || ( this.getPregCode()!=null && castOther.getPregCode()!=null && this.getPregCode().equals(castOther.getPregCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getPregCode() == null ? 0 : this.getPregCode().hashCode() );
         return result;
   }   





}