package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Radige0DdiGcnseqnoExceptId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Radige0DdiGcnseqnoExceptId  implements java.io.Serializable {


    // Fields    

     private Integer ddiMonox;
     private Integer sideAGcnSeqno;
     private Integer sideBGcnSeqno;


    // Constructors

    /** default constructor */
    public Radige0DdiGcnseqnoExceptId() {
    }

    
    /** full constructor */
    public Radige0DdiGcnseqnoExceptId(Integer ddiMonox, Integer sideAGcnSeqno, Integer sideBGcnSeqno) {
        this.ddiMonox = ddiMonox;
        this.sideAGcnSeqno = sideAGcnSeqno;
        this.sideBGcnSeqno = sideBGcnSeqno;
    }

   
    // Property accessors

    @Column(name="DDI_MONOX", nullable=false, precision=5, scale=0)

    public Integer getDdiMonox() {
        return this.ddiMonox;
    }
    
    public void setDdiMonox(Integer ddiMonox) {
        this.ddiMonox = ddiMonox;
    }

    @Column(name="SIDE_A_GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getSideAGcnSeqno() {
        return this.sideAGcnSeqno;
    }
    
    public void setSideAGcnSeqno(Integer sideAGcnSeqno) {
        this.sideAGcnSeqno = sideAGcnSeqno;
    }

    @Column(name="SIDE_B_GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getSideBGcnSeqno() {
        return this.sideBGcnSeqno;
    }
    
    public void setSideBGcnSeqno(Integer sideBGcnSeqno) {
        this.sideBGcnSeqno = sideBGcnSeqno;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Radige0DdiGcnseqnoExceptId) ) return false;
		 Radige0DdiGcnseqnoExceptId castOther = ( Radige0DdiGcnseqnoExceptId ) other; 
         
		 return ( (this.getDdiMonox()==castOther.getDdiMonox()) || ( this.getDdiMonox()!=null && castOther.getDdiMonox()!=null && this.getDdiMonox().equals(castOther.getDdiMonox()) ) )
 && ( (this.getSideAGcnSeqno()==castOther.getSideAGcnSeqno()) || ( this.getSideAGcnSeqno()!=null && castOther.getSideAGcnSeqno()!=null && this.getSideAGcnSeqno().equals(castOther.getSideAGcnSeqno()) ) )
 && ( (this.getSideBGcnSeqno()==castOther.getSideBGcnSeqno()) || ( this.getSideBGcnSeqno()!=null && castOther.getSideBGcnSeqno()!=null && this.getSideBGcnSeqno().equals(castOther.getSideBGcnSeqno()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDdiMonox() == null ? 0 : this.getDdiMonox().hashCode() );
         result = 37 * result + ( getSideAGcnSeqno() == null ? 0 : this.getSideAGcnSeqno().hashCode() );
         result = 37 * result + ( getSideBGcnSeqno() == null ? 0 : this.getSideBGcnSeqno().hashCode() );
         return result;
   }   





}