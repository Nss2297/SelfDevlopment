package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Radimmo5Mono entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIMMO5_MONO"
    ,schema="MEDK_FDB"
)

public class Radimmo5Mono  implements java.io.Serializable {


    // Fields    

     private Radimmo5MonoId id;
     private String iamidentn;
     private String iamtextn;
     private String iamrefcat;


    // Constructors

    /** default constructor */
    public Radimmo5Mono() {
    }

	/** minimal constructor */
    public Radimmo5Mono(Radimmo5MonoId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Radimmo5Mono(Radimmo5MonoId id, String iamidentn, String iamtextn, String iamrefcat) {
        this.id = id;
        this.iamidentn = iamidentn;
        this.iamtextn = iamtextn;
        this.iamrefcat = iamrefcat;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="ddiMonox", column=@Column(name="DDI_MONOX", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="adiMonosn", column=@Column(name="ADI_MONOSN", nullable=false, precision=3, scale=0) ) } )

    public Radimmo5MonoId getId() {
        return this.id;
    }
    
    public void setId(Radimmo5MonoId id) {
        this.id = id;
    }
    
    @Column(name="IAMIDENTN", length=1)

    public String getIamidentn() {
        return this.iamidentn;
    }
    
    public void setIamidentn(String iamidentn) {
        this.iamidentn = iamidentn;
    }
    
    @Column(name="IAMTEXTN", length=76)

    public String getIamtextn() {
        return this.iamtextn;
    }
    
    public void setIamtextn(String iamtextn) {
        this.iamtextn = iamtextn;
    }
    
    @Column(name="IAMREFCAT", length=1)

    public String getIamrefcat() {
        return this.iamrefcat;
    }
    
    public void setIamrefcat(String iamrefcat) {
        this.iamrefcat = iamrefcat;
    }
   








}