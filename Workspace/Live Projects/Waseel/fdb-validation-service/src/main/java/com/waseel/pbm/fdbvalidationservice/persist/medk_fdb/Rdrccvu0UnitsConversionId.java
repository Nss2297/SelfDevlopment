package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdrccvu0UnitsConversionId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdrccvu0UnitsConversionId  implements java.io.Serializable {


    // Fields    

     private String dcnvPui;
     private String unitsRui;


    // Constructors

    /** default constructor */
    public Rdrccvu0UnitsConversionId() {
    }

    
    /** full constructor */
    public Rdrccvu0UnitsConversionId(String dcnvPui, String unitsRui) {
        this.dcnvPui = dcnvPui;
        this.unitsRui = unitsRui;
    }

   
    // Property accessors

    @Column(name="DCNV_PUI", nullable=false, length=2)

    public String getDcnvPui() {
        return this.dcnvPui;
    }
    
    public void setDcnvPui(String dcnvPui) {
        this.dcnvPui = dcnvPui;
    }

    @Column(name="UNITS_RUI", nullable=false, length=2)

    public String getUnitsRui() {
        return this.unitsRui;
    }
    
    public void setUnitsRui(String unitsRui) {
        this.unitsRui = unitsRui;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdrccvu0UnitsConversionId) ) return false;
		 Rdrccvu0UnitsConversionId castOther = ( Rdrccvu0UnitsConversionId ) other; 
         
		 return ( (this.getDcnvPui()==castOther.getDcnvPui()) || ( this.getDcnvPui()!=null && castOther.getDcnvPui()!=null && this.getDcnvPui().equals(castOther.getDcnvPui()) ) )
 && ( (this.getUnitsRui()==castOther.getUnitsRui()) || ( this.getUnitsRui()!=null && castOther.getUnitsRui()!=null && this.getUnitsRui().equals(castOther.getUnitsRui()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDcnvPui() == null ? 0 : this.getDcnvPui().hashCode() );
         result = 37 * result + ( getUnitsRui() == null ? 0 : this.getUnitsRui().hashCode() );
         return result;
   }   





}