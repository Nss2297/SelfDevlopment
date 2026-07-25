package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpemmoe2Mono entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPEMMOE2_MONO"
    ,schema="MEDK_FDB"
)

public class Rpemmoe2Mono  implements java.io.Serializable {


    // Fields    

     private Rpemmoe2MonoId id;
     private String pemtxtei;
     private String pemtxte;
     private String pemgndr;
     private String pemage;


    // Constructors

    /** default constructor */
    public Rpemmoe2Mono() {
    }

	/** minimal constructor */
    public Rpemmoe2Mono(Rpemmoe2MonoId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rpemmoe2Mono(Rpemmoe2MonoId id, String pemtxtei, String pemtxte, String pemgndr, String pemage) {
        this.id = id;
        this.pemtxtei = pemtxtei;
        this.pemtxte = pemtxte;
        this.pemgndr = pemgndr;
        this.pemage = pemage;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="pemono", column=@Column(name="PEMONO", nullable=false, precision=4, scale=0) ), 
        @AttributeOverride(name="pemonoeSn", column=@Column(name="PEMONOE_SN", nullable=false, precision=3, scale=0) ) } )

    public Rpemmoe2MonoId getId() {
        return this.id;
    }
    
    public void setId(Rpemmoe2MonoId id) {
        this.id = id;
    }
    
    @Column(name="PEMTXTEI", length=1)

    public String getPemtxtei() {
        return this.pemtxtei;
    }
    
    public void setPemtxtei(String pemtxtei) {
        this.pemtxtei = pemtxtei;
    }
    
    @Column(name="PEMTXTE", length=76)

    public String getPemtxte() {
        return this.pemtxte;
    }
    
    public void setPemtxte(String pemtxte) {
        this.pemtxte = pemtxte;
    }
    
    @Column(name="PEMGNDR", length=1)

    public String getPemgndr() {
        return this.pemgndr;
    }
    
    public void setPemgndr(String pemgndr) {
        this.pemgndr = pemgndr;
    }
    
    @Column(name="PEMAGE", length=1)

    public String getPemage() {
        return this.pemage;
    }
    
    public void setPemage(String pemage) {
        this.pemage = pemage;
    }
   








}