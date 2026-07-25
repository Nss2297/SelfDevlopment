package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdfimmo0Mono entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDFIMMO0_MONO"
    ,schema="MEDK_FDB"
)

public class Rdfimmo0Mono  implements java.io.Serializable {


    // Fields    

     private Rdfimmo0MonoId id;
     private String txtcde;
     private String fdtxt;


    // Constructors

    /** default constructor */
    public Rdfimmo0Mono() {
    }

	/** minimal constructor */
    public Rdfimmo0Mono(Rdfimmo0MonoId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rdfimmo0Mono(Rdfimmo0MonoId id, String txtcde, String fdtxt) {
        this.id = id;
        this.txtcde = txtcde;
        this.fdtxt = fdtxt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="fdcde", column=@Column(name="FDCDE", nullable=false, precision=3, scale=0) ), 
        @AttributeOverride(name="fdcdeSn", column=@Column(name="FDCDE_SN", nullable=false, precision=3, scale=0) ) } )

    public Rdfimmo0MonoId getId() {
        return this.id;
    }
    
    public void setId(Rdfimmo0MonoId id) {
        this.id = id;
    }
    
    @Column(name="TXTCDE", length=1)

    public String getTxtcde() {
        return this.txtcde;
    }
    
    public void setTxtcde(String txtcde) {
        this.txtcde = txtcde;
    }
    
    @Column(name="FDTXT", length=76)

    public String getFdtxt() {
        return this.fdtxt;
    }
    
    public void setFdtxt(String fdtxt) {
        this.fdtxt = fdtxt;
    }
   








}