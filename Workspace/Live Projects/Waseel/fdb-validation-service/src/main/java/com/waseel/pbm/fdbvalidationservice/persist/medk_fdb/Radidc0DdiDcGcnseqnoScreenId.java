package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Radidc0DdiDcGcnseqnoScreenId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Radidc0DdiDcGcnseqnoScreenId  implements java.io.Serializable {


    // Fields    

     private Integer ddiMonox;
     private Integer gcnSeqno;


    // Constructors

    /** default constructor */
    public Radidc0DdiDcGcnseqnoScreenId() {
    }

    
    /** full constructor */
    public Radidc0DdiDcGcnseqnoScreenId(Integer ddiMonox, Integer gcnSeqno) {
        this.ddiMonox = ddiMonox;
        this.gcnSeqno = gcnSeqno;
    }

   
    // Property accessors

    @Column(name="DDI_MONOX", nullable=false, precision=5, scale=0)

    public Integer getDdiMonox() {
        return this.ddiMonox;
    }
    
    public void setDdiMonox(Integer ddiMonox) {
        this.ddiMonox = ddiMonox;
    }

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Radidc0DdiDcGcnseqnoScreenId) ) return false;
		 Radidc0DdiDcGcnseqnoScreenId castOther = ( Radidc0DdiDcGcnseqnoScreenId ) other; 
         
		 return ( (this.getDdiMonox()==castOther.getDdiMonox()) || ( this.getDdiMonox()!=null && castOther.getDdiMonox()!=null && this.getDdiMonox().equals(castOther.getDdiMonox()) ) )
 && ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDdiMonox() == null ? 0 : this.getDdiMonox().hashCode() );
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         return result;
   }   





}