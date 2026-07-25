package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rindmma2IndctsMstrId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rindmma2IndctsMstrId  implements java.io.Serializable {


    // Fields    

     private Integer indcts;
     private Byte indctsSn;


    // Constructors

    /** default constructor */
    public Rindmma2IndctsMstrId() {
    }

    
    /** full constructor */
    public Rindmma2IndctsMstrId(Integer indcts, Byte indctsSn) {
        this.indcts = indcts;
        this.indctsSn = indctsSn;
    }

   
    // Property accessors

    @Column(name="INDCTS", nullable=false, precision=5, scale=0)

    public Integer getIndcts() {
        return this.indcts;
    }
    
    public void setIndcts(Integer indcts) {
        this.indcts = indcts;
    }

    @Column(name="INDCTS_SN", nullable=false, precision=2, scale=0)

    public Byte getIndctsSn() {
        return this.indctsSn;
    }
    
    public void setIndctsSn(Byte indctsSn) {
        this.indctsSn = indctsSn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rindmma2IndctsMstrId) ) return false;
		 Rindmma2IndctsMstrId castOther = ( Rindmma2IndctsMstrId ) other; 
         
		 return ( (this.getIndcts()==castOther.getIndcts()) || ( this.getIndcts()!=null && castOther.getIndcts()!=null && this.getIndcts().equals(castOther.getIndcts()) ) )
 && ( (this.getIndctsSn()==castOther.getIndctsSn()) || ( this.getIndctsSn()!=null && castOther.getIndctsSn()!=null && this.getIndctsSn().equals(castOther.getIndctsSn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIndcts() == null ? 0 : this.getIndcts().hashCode() );
         result = 37 * result + ( getIndctsSn() == null ? 0 : this.getIndctsSn().hashCode() );
         return result;
   }   





}