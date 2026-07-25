package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rindmma2IndctsMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RINDMMA2_INDCTS_MSTR"
    ,schema="MEDK_FDB"
)

public class Rindmma2IndctsMstr  implements java.io.Serializable {


    // Fields    

     private Rindmma2IndctsMstrId id;
     private String indctsLbl;
     private String fdbdx;
     private Integer dxid;
     private String proxyInd;
     private String predCode;


    // Constructors

    /** default constructor */
    public Rindmma2IndctsMstr() {
    }

	/** minimal constructor */
    public Rindmma2IndctsMstr(Rindmma2IndctsMstrId id, String indctsLbl, String fdbdx, String predCode) {
        this.id = id;
        this.indctsLbl = indctsLbl;
        this.fdbdx = fdbdx;
        this.predCode = predCode;
    }
    
    /** full constructor */
    public Rindmma2IndctsMstr(Rindmma2IndctsMstrId id, String indctsLbl, String fdbdx, Integer dxid, String proxyInd, String predCode) {
        this.id = id;
        this.indctsLbl = indctsLbl;
        this.fdbdx = fdbdx;
        this.dxid = dxid;
        this.proxyInd = proxyInd;
        this.predCode = predCode;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="indcts", column=@Column(name="INDCTS", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="indctsSn", column=@Column(name="INDCTS_SN", nullable=false, precision=2, scale=0) ) } )

    public Rindmma2IndctsMstrId getId() {
        return this.id;
    }
    
    public void setId(Rindmma2IndctsMstrId id) {
        this.id = id;
    }
    
    @Column(name="INDCTS_LBL", nullable=false, length=1)

    public String getIndctsLbl() {
        return this.indctsLbl;
    }
    
    public void setIndctsLbl(String indctsLbl) {
        this.indctsLbl = indctsLbl;
    }
    
    @Column(name="FDBDX", nullable=false, length=9)

    public String getFdbdx() {
        return this.fdbdx;
    }
    
    public void setFdbdx(String fdbdx) {
        this.fdbdx = fdbdx;
    }
    
    @Column(name="DXID", precision=8, scale=0)

    public Integer getDxid() {
        return this.dxid;
    }
    
    public void setDxid(Integer dxid) {
        this.dxid = dxid;
    }
    
    @Column(name="PROXY_IND", length=1)

    public String getProxyInd() {
        return this.proxyInd;
    }
    
    public void setProxyInd(String proxyInd) {
        this.proxyInd = proxyInd;
    }
    
    @Column(name="PRED_CODE", nullable=false, length=1)

    public String getPredCode() {
        return this.predCode;
    }
    
    public void setPredCode(String predCode) {
        this.predCode = predCode;
    }
   








}