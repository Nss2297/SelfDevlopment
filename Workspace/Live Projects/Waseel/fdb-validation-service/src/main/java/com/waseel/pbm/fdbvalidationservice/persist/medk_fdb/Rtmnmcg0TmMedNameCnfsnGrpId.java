package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rtmnmcg0TmMedNameCnfsnGrpId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rtmnmcg0TmMedNameCnfsnGrpId  implements java.io.Serializable {


    // Fields    

     private Integer medNameId;
     private Integer tmGroupId;


    // Constructors

    /** default constructor */
    public Rtmnmcg0TmMedNameCnfsnGrpId() {
    }

    
    /** full constructor */
    public Rtmnmcg0TmMedNameCnfsnGrpId(Integer medNameId, Integer tmGroupId) {
        this.medNameId = medNameId;
        this.tmGroupId = tmGroupId;
    }

   
    // Property accessors

    @Column(name="MED_NAME_ID", nullable=false, precision=8, scale=0)

    public Integer getMedNameId() {
        return this.medNameId;
    }
    
    public void setMedNameId(Integer medNameId) {
        this.medNameId = medNameId;
    }

    @Column(name="TM_GROUP_ID", nullable=false, precision=5, scale=0)

    public Integer getTmGroupId() {
        return this.tmGroupId;
    }
    
    public void setTmGroupId(Integer tmGroupId) {
        this.tmGroupId = tmGroupId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rtmnmcg0TmMedNameCnfsnGrpId) ) return false;
		 Rtmnmcg0TmMedNameCnfsnGrpId castOther = ( Rtmnmcg0TmMedNameCnfsnGrpId ) other; 
         
		 return ( (this.getMedNameId()==castOther.getMedNameId()) || ( this.getMedNameId()!=null && castOther.getMedNameId()!=null && this.getMedNameId().equals(castOther.getMedNameId()) ) )
 && ( (this.getTmGroupId()==castOther.getTmGroupId()) || ( this.getTmGroupId()!=null && castOther.getTmGroupId()!=null && this.getTmGroupId().equals(castOther.getTmGroupId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedNameId() == null ? 0 : this.getMedNameId().hashCode() );
         result = 37 * result + ( getTmGroupId() == null ? 0 : this.getTmGroupId().hashCode() );
         return result;
   }   





}