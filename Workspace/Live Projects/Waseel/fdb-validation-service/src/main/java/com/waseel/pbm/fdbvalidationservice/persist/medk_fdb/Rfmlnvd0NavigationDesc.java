package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Rfmlnvd0NavigationDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLNVD0_NAVIGATION_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlnvd0NavigationDesc  implements java.io.Serializable {


    // Fields    

     private String fmlNavCode;
     private String fmlNavCodeDesc;
     private Set<Rfmldsr0DxidSearch> rfmldsr0DxidSearchs = new HashSet<Rfmldsr0DxidSearch>(0);


    // Constructors

    /** default constructor */
    public Rfmlnvd0NavigationDesc() {
    }

	/** minimal constructor */
    public Rfmlnvd0NavigationDesc(String fmlNavCode) {
        this.fmlNavCode = fmlNavCode;
    }
    
    /** full constructor */
    public Rfmlnvd0NavigationDesc(String fmlNavCode, String fmlNavCodeDesc, Set<Rfmldsr0DxidSearch> rfmldsr0DxidSearchs) {
        this.fmlNavCode = fmlNavCode;
        this.fmlNavCodeDesc = fmlNavCodeDesc;
        this.rfmldsr0DxidSearchs = rfmldsr0DxidSearchs;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="FML_NAV_CODE", unique=true, nullable=false, length=2)

    public String getFmlNavCode() {
        return this.fmlNavCode;
    }
    
    public void setFmlNavCode(String fmlNavCode) {
        this.fmlNavCode = fmlNavCode;
    }
    
    @Column(name="FML_NAV_CODE_DESC", length=50)

    public String getFmlNavCodeDesc() {
        return this.fmlNavCodeDesc;
    }
    
    public void setFmlNavCodeDesc(String fmlNavCodeDesc) {
        this.fmlNavCodeDesc = fmlNavCodeDesc;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="rfmlnvd0NavigationDesc")

    public Set<Rfmldsr0DxidSearch> getRfmldsr0DxidSearchs() {
        return this.rfmldsr0DxidSearchs;
    }
    
    public void setRfmldsr0DxidSearchs(Set<Rfmldsr0DxidSearch> rfmldsr0DxidSearchs) {
        this.rfmldsr0DxidSearchs = rfmldsr0DxidSearchs;
    }
   








}