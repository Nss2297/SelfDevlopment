package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpemgc0GcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpemgc0GcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer pec;


    // Constructors

    /** default constructor */
    public Rpemgc0GcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rpemgc0GcnseqnoLinkId(Integer gcnSeqno, Integer pec) {
        this.gcnSeqno = gcnSeqno;
        this.pec = pec;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="PEC", nullable=false, precision=6, scale=0)

    public Integer getPec() {
        return this.pec;
    }
    
    public void setPec(Integer pec) {
        this.pec = pec;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpemgc0GcnseqnoLinkId) ) return false;
		 Rpemgc0GcnseqnoLinkId castOther = ( Rpemgc0GcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getPec()==castOther.getPec()) || ( this.getPec()!=null && castOther.getPec()!=null && this.getPec().equals(castOther.getPec()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getPec() == null ? 0 : this.getPec().hashCode() );
         return result;
   }   





}