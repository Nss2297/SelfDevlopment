package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rgcnstr0IngredientStrength entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RGCNSTR0_INGREDIENT_STRENGTH"
    ,schema="MEDK_FDB"
)

public class Rgcnstr0IngredientStrength  implements java.io.Serializable {


    // Fields    

     private Rgcnstr0IngredientStrengthId id;
     private Boolean strengthStatusCode;
     private Double strength;
     private Integer strengthUomId;
     private Boolean strengthTypCode;
     private Double volume;
     private Integer volumeUomId;
     private Double altStrength;
     private Integer altStrengthUomId;
     private Boolean altStrengthTypCode;
     private Double timeValue;
     private Integer timeUomId;
     private Double rangeMax;
     private Double rangeMin;


    // Constructors

    /** default constructor */
    public Rgcnstr0IngredientStrength() {
    }

	/** minimal constructor */
    public Rgcnstr0IngredientStrength(Rgcnstr0IngredientStrengthId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rgcnstr0IngredientStrength(Rgcnstr0IngredientStrengthId id, Boolean strengthStatusCode, Double strength, Integer strengthUomId, Boolean strengthTypCode, Double volume, Integer volumeUomId, Double altStrength, Integer altStrengthUomId, Boolean altStrengthTypCode, Double timeValue, Integer timeUomId, Double rangeMax, Double rangeMin) {
        this.id = id;
        this.strengthStatusCode = strengthStatusCode;
        this.strength = strength;
        this.strengthUomId = strengthUomId;
        this.strengthTypCode = strengthTypCode;
        this.volume = volume;
        this.volumeUomId = volumeUomId;
        this.altStrength = altStrength;
        this.altStrengthUomId = altStrengthUomId;
        this.altStrengthTypCode = altStrengthTypCode;
        this.timeValue = timeValue;
        this.timeUomId = timeUomId;
        this.rangeMax = rangeMax;
        this.rangeMin = rangeMin;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="hicSeqn", column=@Column(name="HIC_SEQN", nullable=false, precision=6, scale=0) ) } )

    public Rgcnstr0IngredientStrengthId getId() {
        return this.id;
    }
    
    public void setId(Rgcnstr0IngredientStrengthId id) {
        this.id = id;
    }
    
    @Column(name="STRENGTH_STATUS_CODE", precision=1, scale=0)

    public Boolean getStrengthStatusCode() {
        return this.strengthStatusCode;
    }
    
    public void setStrengthStatusCode(Boolean strengthStatusCode) {
        this.strengthStatusCode = strengthStatusCode;
    }
    
    @Column(name="STRENGTH", scale=6)

    public Double getStrength() {
        return this.strength;
    }
    
    public void setStrength(Double strength) {
        this.strength = strength;
    }
    
    @Column(name="STRENGTH_UOM_ID", precision=8, scale=0)

    public Integer getStrengthUomId() {
        return this.strengthUomId;
    }
    
    public void setStrengthUomId(Integer strengthUomId) {
        this.strengthUomId = strengthUomId;
    }
    
    @Column(name="STRENGTH_TYP_CODE", precision=1, scale=0)

    public Boolean getStrengthTypCode() {
        return this.strengthTypCode;
    }
    
    public void setStrengthTypCode(Boolean strengthTypCode) {
        this.strengthTypCode = strengthTypCode;
    }
    
    @Column(name="VOLUME", scale=6)

    public Double getVolume() {
        return this.volume;
    }
    
    public void setVolume(Double volume) {
        this.volume = volume;
    }
    
    @Column(name="VOLUME_UOM_ID", precision=8, scale=0)

    public Integer getVolumeUomId() {
        return this.volumeUomId;
    }
    
    public void setVolumeUomId(Integer volumeUomId) {
        this.volumeUomId = volumeUomId;
    }
    
    @Column(name="ALT_STRENGTH", scale=6)

    public Double getAltStrength() {
        return this.altStrength;
    }
    
    public void setAltStrength(Double altStrength) {
        this.altStrength = altStrength;
    }
    
    @Column(name="ALT_STRENGTH_UOM_ID", precision=8, scale=0)

    public Integer getAltStrengthUomId() {
        return this.altStrengthUomId;
    }
    
    public void setAltStrengthUomId(Integer altStrengthUomId) {
        this.altStrengthUomId = altStrengthUomId;
    }
    
    @Column(name="ALT_STRENGTH_TYP_CODE", precision=1, scale=0)

    public Boolean getAltStrengthTypCode() {
        return this.altStrengthTypCode;
    }
    
    public void setAltStrengthTypCode(Boolean altStrengthTypCode) {
        this.altStrengthTypCode = altStrengthTypCode;
    }
    
    @Column(name="TIME_VALUE", precision=6, scale=3)

    public Double getTimeValue() {
        return this.timeValue;
    }
    
    public void setTimeValue(Double timeValue) {
        this.timeValue = timeValue;
    }
    
    @Column(name="TIME_UOM_ID", precision=8, scale=0)

    public Integer getTimeUomId() {
        return this.timeUomId;
    }
    
    public void setTimeUomId(Integer timeUomId) {
        this.timeUomId = timeUomId;
    }
    
    @Column(name="RANGE_MAX", scale=6)

    public Double getRangeMax() {
        return this.rangeMax;
    }
    
    public void setRangeMax(Double rangeMax) {
        this.rangeMax = rangeMax;
    }
    
    @Column(name="RANGE_MIN", scale=6)

    public Double getRangeMin() {
        return this.rangeMin;
    }
    
    public void setRangeMin(Double rangeMin) {
        this.rangeMin = rangeMin;
    }
   








}