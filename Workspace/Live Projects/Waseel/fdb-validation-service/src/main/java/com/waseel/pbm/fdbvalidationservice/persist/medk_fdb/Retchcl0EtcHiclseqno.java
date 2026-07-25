package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retchcl0EtcHiclseqno entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCHCL0_ETC_HICLSEQNO"
    ,schema="MEDK_FDB"
)

public class Retchcl0EtcHiclseqno  implements java.io.Serializable {


    // Fields    

     private Retchcl0EtcHiclseqnoId id;


    // Constructors

    /** default constructor */
    public Retchcl0EtcHiclseqno() {
    }

    
    /** full constructor */
    public Retchcl0EtcHiclseqno(Retchcl0EtcHiclseqnoId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="hiclSeqno", column=@Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ) } )

    public Retchcl0EtcHiclseqnoId getId() {
        return this.id;
    }
    
    public void setId(Retchcl0EtcHiclseqnoId id) {
        this.id = id;
    }
   








}