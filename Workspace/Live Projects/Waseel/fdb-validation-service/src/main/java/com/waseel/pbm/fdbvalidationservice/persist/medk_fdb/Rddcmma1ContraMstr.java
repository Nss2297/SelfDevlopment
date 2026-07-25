package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rddcmma1ContraMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDCMMA1_CONTRA_MSTR"
    ,schema="MEDK_FDB"
)

public class Rddcmma1ContraMstr  implements java.io.Serializable {


    // Fields    

     private Rddcmma1ContraMstrId id;
     private String fdbdx;
     private String ddxcnSl;
     private String ddxcnRef;
     private Integer dxid;


    // Constructors

    /** default constructor */
    public Rddcmma1ContraMstr() {
    }

	/** minimal constructor */
    public Rddcmma1ContraMstr(Rddcmma1ContraMstrId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rddcmma1ContraMstr(Rddcmma1ContraMstrId id, String fdbdx, String ddxcnSl, String ddxcnRef, Integer dxid) {
        this.id = id;
        this.fdbdx = fdbdx;
        this.ddxcnSl = ddxcnSl;
        this.ddxcnRef = ddxcnRef;
        this.dxid = dxid;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="ddxcn", column=@Column(name="DDXCN", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="ddxcnSn", column=@Column(name="DDXCN_SN", nullable=false, precision=2, scale=0) ) } )

    public Rddcmma1ContraMstrId getId() {
        return this.id;
    }
    
    public void setId(Rddcmma1ContraMstrId id) {
        this.id = id;
    }
    
    @Column(name="FDBDX", length=9)

    public String getFdbdx() {
        return this.fdbdx;
    }
    
    public void setFdbdx(String fdbdx) {
        this.fdbdx = fdbdx;
    }
    
    @Column(name="DDXCN_SL", length=1)

    public String getDdxcnSl() {
        return this.ddxcnSl;
    }
    
    public void setDdxcnSl(String ddxcnSl) {
        this.ddxcnSl = ddxcnSl;
    }
    
    @Column(name="DDXCN_REF", length=26)

    public String getDdxcnRef() {
        return this.ddxcnRef;
    }
    
    public void setDdxcnRef(String ddxcnRef) {
        this.ddxcnRef = ddxcnRef;
    }
    
    @Column(name="DXID", precision=8, scale=0)

    public Integer getDxid() {
        return this.dxid;
    }
    
    public void setDxid(Integer dxid) {
        this.dxid = dxid;
    }
   








}