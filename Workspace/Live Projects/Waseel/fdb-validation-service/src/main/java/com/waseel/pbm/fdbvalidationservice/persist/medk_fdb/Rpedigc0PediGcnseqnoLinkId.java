package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpedigc0PediGcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpedigc0PediGcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer pediCode;


    // Constructors

    /** default constructor */
    public Rpedigc0PediGcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rpedigc0PediGcnseqnoLinkId(Integer gcnSeqno, Integer pediCode) {
        this.gcnSeqno = gcnSeqno;
        this.pediCode = pediCode;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="PEDI_CODE", nullable=false, precision=6, scale=0)

    public Integer getPediCode() {
        return this.pediCode;
    }
    
    public void setPediCode(Integer pediCode) {
        this.pediCode = pediCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpedigc0PediGcnseqnoLinkId) ) return false;
		 Rpedigc0PediGcnseqnoLinkId castOther = ( Rpedigc0PediGcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getPediCode()==castOther.getPediCode()) || ( this.getPediCode()!=null && castOther.getPediCode()!=null && this.getPediCode().equals(castOther.getPediCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getPediCode() == null ? 0 : this.getPediCode().hashCode() );
         return result;
   }   





}