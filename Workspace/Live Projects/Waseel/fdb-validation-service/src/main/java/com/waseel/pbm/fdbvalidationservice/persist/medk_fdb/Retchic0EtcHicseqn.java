package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retchic0EtcHicseqn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCHIC0_ETC_HICSEQN"
    ,schema="MEDK_FDB"
)

public class Retchic0EtcHicseqn  implements java.io.Serializable {


    // Fields    

     private Retchic0EtcHicseqnId id;


    // Constructors

    /** default constructor */
    public Retchic0EtcHicseqn() {
    }

    
    /** full constructor */
    public Retchic0EtcHicseqn(Retchic0EtcHicseqnId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="hicSeqn", column=@Column(name="HIC_SEQN", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="etcId", column=@Column(name="ETC_ID", nullable=false, precision=8, scale=0) ) } )

    public Retchic0EtcHicseqnId getId() {
        return this.id;
    }
    
    public void setId(Retchic0EtcHicseqnId id) {
        this.id = id;
    }
   








}