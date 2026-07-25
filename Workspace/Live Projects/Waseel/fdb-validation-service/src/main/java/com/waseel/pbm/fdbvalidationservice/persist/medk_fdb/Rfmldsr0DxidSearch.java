package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Rfmldsr0DxidSearch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLDSR0_DXID_SEARCH"
    ,schema="MEDK_FDB"
)

public class Rfmldsr0DxidSearch  implements java.io.Serializable {


    // Fields    

     private Rfmldsr0DxidSearchId id;
     private Rfmlnvd0NavigationDesc rfmlnvd0NavigationDesc;


    // Constructors

    /** default constructor */
    public Rfmldsr0DxidSearch() {
    }

    
    /** full constructor */
    public Rfmldsr0DxidSearch(Rfmldsr0DxidSearchId id, Rfmlnvd0NavigationDesc rfmlnvd0NavigationDesc) {
        this.id = id;
        this.rfmlnvd0NavigationDesc = rfmlnvd0NavigationDesc;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="searchDxid", column=@Column(name="SEARCH_DXID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="relatedDxid", column=@Column(name="RELATED_DXID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="fmlClinCode", column=@Column(name="FML_CLIN_CODE", nullable=false, length=2) ) } )

    public Rfmldsr0DxidSearchId getId() {
        return this.id;
    }
    
    public void setId(Rfmldsr0DxidSearchId id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="FML_NAV_CODE", nullable=false)

    public Rfmlnvd0NavigationDesc getRfmlnvd0NavigationDesc() {
        return this.rfmlnvd0NavigationDesc;
    }
    
    public void setRfmlnvd0NavigationDesc(Rfmlnvd0NavigationDesc rfmlnvd0NavigationDesc) {
        this.rfmlnvd0NavigationDesc = rfmlnvd0NavigationDesc;
    }
   








}