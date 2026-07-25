package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rddimag0AgentId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rddimag0AgentId  implements java.io.Serializable {


    // Fields    

     private Integer ddiCodex;
     private Short ddiAgsn;


    // Constructors

    /** default constructor */
    public Rddimag0AgentId() {
    }

    
    /** full constructor */
    public Rddimag0AgentId(Integer ddiCodex, Short ddiAgsn) {
        this.ddiCodex = ddiCodex;
        this.ddiAgsn = ddiAgsn;
    }

   
    // Property accessors

    @Column(name="DDI_CODEX", nullable=false, precision=5, scale=0)

    public Integer getDdiCodex() {
        return this.ddiCodex;
    }
    
    public void setDdiCodex(Integer ddiCodex) {
        this.ddiCodex = ddiCodex;
    }

    @Column(name="DDI_AGSN", nullable=false, precision=3, scale=0)

    public Short getDdiAgsn() {
        return this.ddiAgsn;
    }
    
    public void setDdiAgsn(Short ddiAgsn) {
        this.ddiAgsn = ddiAgsn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rddimag0AgentId) ) return false;
		 Rddimag0AgentId castOther = ( Rddimag0AgentId ) other; 
         
		 return ( (this.getDdiCodex()==castOther.getDdiCodex()) || ( this.getDdiCodex()!=null && castOther.getDdiCodex()!=null && this.getDdiCodex().equals(castOther.getDdiCodex()) ) )
 && ( (this.getDdiAgsn()==castOther.getDdiAgsn()) || ( this.getDdiAgsn()!=null && castOther.getDdiAgsn()!=null && this.getDdiAgsn().equals(castOther.getDdiAgsn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDdiCodex() == null ? 0 : this.getDdiCodex().hashCode() );
         result = 37 * result + ( getDdiAgsn() == null ? 0 : this.getDdiAgsn().hashCode() );
         return result;
   }   





}