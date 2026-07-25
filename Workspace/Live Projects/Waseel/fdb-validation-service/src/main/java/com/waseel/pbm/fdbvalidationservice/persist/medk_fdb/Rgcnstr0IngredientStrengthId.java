package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rgcnstr0IngredientStrengthId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rgcnstr0IngredientStrengthId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer hicSeqn;


    // Constructors

    /** default constructor */
    public Rgcnstr0IngredientStrengthId() {
    }

    
    /** full constructor */
    public Rgcnstr0IngredientStrengthId(Integer gcnSeqno, Integer hicSeqn) {
        this.gcnSeqno = gcnSeqno;
        this.hicSeqn = hicSeqn;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="HIC_SEQN", nullable=false, precision=6, scale=0)

    public Integer getHicSeqn() {
        return this.hicSeqn;
    }
    
    public void setHicSeqn(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rgcnstr0IngredientStrengthId) ) return false;
		 Rgcnstr0IngredientStrengthId castOther = ( Rgcnstr0IngredientStrengthId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getHicSeqn()==castOther.getHicSeqn()) || ( this.getHicSeqn()!=null && castOther.getHicSeqn()!=null && this.getHicSeqn().equals(castOther.getHicSeqn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getHicSeqn() == null ? 0 : this.getHicSeqn().hashCode() );
         return result;
   }   





}