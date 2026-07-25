package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Radimma5Mstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIMMA5_MSTR"
    ,schema="MEDK_FDB"
)

public class Radimma5Mstr  implements java.io.Serializable {


    // Fields    

     private Integer ddiCodex;
     private String ddiDes;
     private String ddiSl;
     private Integer ddiMonox;
     private String ddiPgedi;
     private Integer ddiTree;
     private String ddiMfgi;
     private String ddiTriali;
     private String ddiCasei;
     private String ddiAbsi;
     private String ddiIvasi;
     private String ddiRevi;


    // Constructors

    /** default constructor */
    public Radimma5Mstr() {
    }

	/** minimal constructor */
    public Radimma5Mstr(Integer ddiCodex) {
        this.ddiCodex = ddiCodex;
    }
    
    /** full constructor */
    public Radimma5Mstr(Integer ddiCodex, String ddiDes, String ddiSl, Integer ddiMonox, String ddiPgedi, Integer ddiTree, String ddiMfgi, String ddiTriali, String ddiCasei, String ddiAbsi, String ddiIvasi, String ddiRevi) {
        this.ddiCodex = ddiCodex;
        this.ddiDes = ddiDes;
        this.ddiSl = ddiSl;
        this.ddiMonox = ddiMonox;
        this.ddiPgedi = ddiPgedi;
        this.ddiTree = ddiTree;
        this.ddiMfgi = ddiMfgi;
        this.ddiTriali = ddiTriali;
        this.ddiCasei = ddiCasei;
        this.ddiAbsi = ddiAbsi;
        this.ddiIvasi = ddiIvasi;
        this.ddiRevi = ddiRevi;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DDI_CODEX", unique=true, nullable=false, precision=5, scale=0)

    public Integer getDdiCodex() {
        return this.ddiCodex;
    }
    
    public void setDdiCodex(Integer ddiCodex) {
        this.ddiCodex = ddiCodex;
    }
    
    @Column(name="DDI_DES", length=60)

    public String getDdiDes() {
        return this.ddiDes;
    }
    
    public void setDdiDes(String ddiDes) {
        this.ddiDes = ddiDes;
    }
    
    @Column(name="DDI_SL", length=1)

    public String getDdiSl() {
        return this.ddiSl;
    }
    
    public void setDdiSl(String ddiSl) {
        this.ddiSl = ddiSl;
    }
    
    @Column(name="DDI_MONOX", precision=5, scale=0)

    public Integer getDdiMonox() {
        return this.ddiMonox;
    }
    
    public void setDdiMonox(Integer ddiMonox) {
        this.ddiMonox = ddiMonox;
    }
    
    @Column(name="DDI_PGEDI", length=9)

    public String getDdiPgedi() {
        return this.ddiPgedi;
    }
    
    public void setDdiPgedi(String ddiPgedi) {
        this.ddiPgedi = ddiPgedi;
    }
    
    @Column(name="DDI_TREE", precision=5, scale=0)

    public Integer getDdiTree() {
        return this.ddiTree;
    }
    
    public void setDdiTree(Integer ddiTree) {
        this.ddiTree = ddiTree;
    }
    
    @Column(name="DDI_MFGI", length=1)

    public String getDdiMfgi() {
        return this.ddiMfgi;
    }
    
    public void setDdiMfgi(String ddiMfgi) {
        this.ddiMfgi = ddiMfgi;
    }
    
    @Column(name="DDI_TRIALI", length=1)

    public String getDdiTriali() {
        return this.ddiTriali;
    }
    
    public void setDdiTriali(String ddiTriali) {
        this.ddiTriali = ddiTriali;
    }
    
    @Column(name="DDI_CASEI", length=1)

    public String getDdiCasei() {
        return this.ddiCasei;
    }
    
    public void setDdiCasei(String ddiCasei) {
        this.ddiCasei = ddiCasei;
    }
    
    @Column(name="DDI_ABSI", length=1)

    public String getDdiAbsi() {
        return this.ddiAbsi;
    }
    
    public void setDdiAbsi(String ddiAbsi) {
        this.ddiAbsi = ddiAbsi;
    }
    
    @Column(name="DDI_IVASI", length=1)

    public String getDdiIvasi() {
        return this.ddiIvasi;
    }
    
    public void setDdiIvasi(String ddiIvasi) {
        this.ddiIvasi = ddiIvasi;
    }
    
    @Column(name="DDI_REVI", length=1)

    public String getDdiRevi() {
        return this.ddiRevi;
    }
    
    public void setDdiRevi(String ddiRevi) {
        this.ddiRevi = ddiRevi;
    }
   








}