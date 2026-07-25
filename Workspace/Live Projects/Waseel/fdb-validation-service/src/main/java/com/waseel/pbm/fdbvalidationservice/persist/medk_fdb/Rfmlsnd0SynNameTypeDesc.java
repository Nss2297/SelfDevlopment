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
 * Rfmlsnd0SynNameTypeDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLSND0_SYN_NAME_TYPE_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlsnd0SynNameTypeDesc  implements java.io.Serializable {


    // Fields    

     private String dxidSynNmtyp;
     private String dxidSynNmtypDesc;
     private Set<Rfmlsyn0DxidSyn> rfmlsyn0DxidSyns = new HashSet<Rfmlsyn0DxidSyn>(0);


    // Constructors

    /** default constructor */
    public Rfmlsnd0SynNameTypeDesc() {
    }

	/** minimal constructor */
    public Rfmlsnd0SynNameTypeDesc(String dxidSynNmtyp) {
        this.dxidSynNmtyp = dxidSynNmtyp;
    }
    
    /** full constructor */
    public Rfmlsnd0SynNameTypeDesc(String dxidSynNmtyp, String dxidSynNmtypDesc, Set<Rfmlsyn0DxidSyn> rfmlsyn0DxidSyns) {
        this.dxidSynNmtyp = dxidSynNmtyp;
        this.dxidSynNmtypDesc = dxidSynNmtypDesc;
        this.rfmlsyn0DxidSyns = rfmlsyn0DxidSyns;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DXID_SYN_NMTYP", unique=true, nullable=false, length=2)

    public String getDxidSynNmtyp() {
        return this.dxidSynNmtyp;
    }
    
    public void setDxidSynNmtyp(String dxidSynNmtyp) {
        this.dxidSynNmtyp = dxidSynNmtyp;
    }
    
    @Column(name="DXID_SYN_NMTYP_DESC", length=50)

    public String getDxidSynNmtypDesc() {
        return this.dxidSynNmtypDesc;
    }
    
    public void setDxidSynNmtypDesc(String dxidSynNmtypDesc) {
        this.dxidSynNmtypDesc = dxidSynNmtypDesc;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="rfmlsnd0SynNameTypeDesc")

    public Set<Rfmlsyn0DxidSyn> getRfmlsyn0DxidSyns() {
        return this.rfmlsyn0DxidSyns;
    }
    
    public void setRfmlsyn0DxidSyns(Set<Rfmlsyn0DxidSyn> rfmlsyn0DxidSyns) {
        this.rfmlsyn0DxidSyns = rfmlsyn0DxidSyns;
    }
   








}