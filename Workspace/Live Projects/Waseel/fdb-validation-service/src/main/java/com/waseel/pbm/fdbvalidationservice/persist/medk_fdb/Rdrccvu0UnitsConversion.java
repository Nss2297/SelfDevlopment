package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdrccvu0UnitsConversion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCCVU0_UNITS_CONVERSION"
    ,schema="MEDK_FDB"
)

public class Rdrccvu0UnitsConversion  implements java.io.Serializable {


    // Fields    

     private Rdrccvu0UnitsConversionId id;
     private String dcnvMthi;
     private Double dcnvCnvf;


    // Constructors

    /** default constructor */
    public Rdrccvu0UnitsConversion() {
    }

	/** minimal constructor */
    public Rdrccvu0UnitsConversion(Rdrccvu0UnitsConversionId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rdrccvu0UnitsConversion(Rdrccvu0UnitsConversionId id, String dcnvMthi, Double dcnvCnvf) {
        this.id = id;
        this.dcnvMthi = dcnvMthi;
        this.dcnvCnvf = dcnvCnvf;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="dcnvPui", column=@Column(name="DCNV_PUI", nullable=false, length=2) ), 
        @AttributeOverride(name="unitsRui", column=@Column(name="UNITS_RUI", nullable=false, length=2) ) } )

    public Rdrccvu0UnitsConversionId getId() {
        return this.id;
    }
    
    public void setId(Rdrccvu0UnitsConversionId id) {
        this.id = id;
    }
    
    @Column(name="DCNV_MTHI", length=1)

    public String getDcnvMthi() {
        return this.dcnvMthi;
    }
    
    public void setDcnvMthi(String dcnvMthi) {
        this.dcnvMthi = dcnvMthi;
    }
    
    @Column(name="DCNV_CNVF", precision=15, scale=5)

    public Double getDcnvCnvf() {
        return this.dcnvCnvf;
    }
    
    public void setDcnvCnvf(Double dcnvCnvf) {
        this.dcnvCnvf = dcnvCnvf;
    }
   








}