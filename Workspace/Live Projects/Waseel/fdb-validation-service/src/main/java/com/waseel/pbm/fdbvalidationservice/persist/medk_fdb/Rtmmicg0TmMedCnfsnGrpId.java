package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rtmmicg0TmMedCnfsnGrpId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rtmmicg0TmMedCnfsnGrpId  implements java.io.Serializable {


    // Fields    

     private Integer medid;
     private Integer tmGroupId;


    // Constructors

    /** default constructor */
    public Rtmmicg0TmMedCnfsnGrpId() {
    }

    
    /** full constructor */
    public Rtmmicg0TmMedCnfsnGrpId(Integer medid, Integer tmGroupId) {
        this.medid = medid;
        this.tmGroupId = tmGroupId;
    }

   
    // Property accessors

    @Column(name="MEDID", nullable=false, precision=8, scale=0)

    public Integer getMedid() {
        return this.medid;
    }
    
    public void setMedid(Integer medid) {
        this.medid = medid;
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
		 if ( !(other instanceof Rtmmicg0TmMedCnfsnGrpId) ) return false;
		 Rtmmicg0TmMedCnfsnGrpId castOther = ( Rtmmicg0TmMedCnfsnGrpId ) other; 
         
		 return ( (this.getMedid()==castOther.getMedid()) || ( this.getMedid()!=null && castOther.getMedid()!=null && this.getMedid().equals(castOther.getMedid()) ) )
 && ( (this.getTmGroupId()==castOther.getTmGroupId()) || ( this.getTmGroupId()!=null && castOther.getTmGroupId()!=null && this.getTmGroupId().equals(castOther.getTmGroupId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedid() == null ? 0 : this.getMedid().hashCode() );
         result = 37 * result + ( getTmGroupId() == null ? 0 : this.getTmGroupId().hashCode() );
         return result;
   }   





}