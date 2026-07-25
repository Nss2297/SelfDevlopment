package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Ratcgc0AtcGcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Ratcgc0AtcGcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private String atc;


    // Constructors

    /** default constructor */
    public Ratcgc0AtcGcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Ratcgc0AtcGcnseqnoLinkId(Integer gcnSeqno, String atc) {
        this.gcnSeqno = gcnSeqno;
        this.atc = atc;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="ATC", nullable=false, length=7)

    public String getAtc() {
        return this.atc;
    }
    
    public void setAtc(String atc) {
        this.atc = atc;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Ratcgc0AtcGcnseqnoLinkId) ) return false;
		 Ratcgc0AtcGcnseqnoLinkId castOther = ( Ratcgc0AtcGcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getAtc()==castOther.getAtc()) || ( this.getAtc()!=null && castOther.getAtc()!=null && this.getAtc().equals(castOther.getAtc()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getAtc() == null ? 0 : this.getAtc().hashCode() );
         return result;
   }   





}