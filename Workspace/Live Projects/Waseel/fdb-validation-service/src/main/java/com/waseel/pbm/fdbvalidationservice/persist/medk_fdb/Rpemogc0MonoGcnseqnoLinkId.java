package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpemogc0MonoGcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpemogc0MonoGcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Short pemono;


    // Constructors

    /** default constructor */
    public Rpemogc0MonoGcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rpemogc0MonoGcnseqnoLinkId(Integer gcnSeqno, Short pemono) {
        this.gcnSeqno = gcnSeqno;
        this.pemono = pemono;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="PEMONO", nullable=false, precision=4, scale=0)

    public Short getPemono() {
        return this.pemono;
    }
    
    public void setPemono(Short pemono) {
        this.pemono = pemono;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpemogc0MonoGcnseqnoLinkId) ) return false;
		 Rpemogc0MonoGcnseqnoLinkId castOther = ( Rpemogc0MonoGcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getPemono()==castOther.getPemono()) || ( this.getPemono()!=null && castOther.getPemono()!=null && this.getPemono().equals(castOther.getPemono()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getPemono() == null ? 0 : this.getPemono().hashCode() );
         return result;
   }   





}