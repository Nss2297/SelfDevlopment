package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rhicrh0IngHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rhicrh0IngHistId  implements java.io.Serializable {


    // Fields    

     private Integer replHicSeqn;
     private Integer prevHicSeqn;


    // Constructors

    /** default constructor */
    public Rhicrh0IngHistId() {
    }

    
    /** full constructor */
    public Rhicrh0IngHistId(Integer replHicSeqn, Integer prevHicSeqn) {
        this.replHicSeqn = replHicSeqn;
        this.prevHicSeqn = prevHicSeqn;
    }

   
    // Property accessors

    @Column(name="REPL_HIC_SEQN", nullable=false, precision=6, scale=0)

    public Integer getReplHicSeqn() {
        return this.replHicSeqn;
    }
    
    public void setReplHicSeqn(Integer replHicSeqn) {
        this.replHicSeqn = replHicSeqn;
    }

    @Column(name="PREV_HIC_SEQN", nullable=false, precision=6, scale=0)

    public Integer getPrevHicSeqn() {
        return this.prevHicSeqn;
    }
    
    public void setPrevHicSeqn(Integer prevHicSeqn) {
        this.prevHicSeqn = prevHicSeqn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rhicrh0IngHistId) ) return false;
		 Rhicrh0IngHistId castOther = ( Rhicrh0IngHistId ) other; 
         
		 return ( (this.getReplHicSeqn()==castOther.getReplHicSeqn()) || ( this.getReplHicSeqn()!=null && castOther.getReplHicSeqn()!=null && this.getReplHicSeqn().equals(castOther.getReplHicSeqn()) ) )
 && ( (this.getPrevHicSeqn()==castOther.getPrevHicSeqn()) || ( this.getPrevHicSeqn()!=null && castOther.getPrevHicSeqn()!=null && this.getPrevHicSeqn().equals(castOther.getPrevHicSeqn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getReplHicSeqn() == null ? 0 : this.getReplHicSeqn().hashCode() );
         result = 37 * result + ( getPrevHicSeqn() == null ? 0 : this.getPrevHicSeqn().hashCode() );
         return result;
   }   





}