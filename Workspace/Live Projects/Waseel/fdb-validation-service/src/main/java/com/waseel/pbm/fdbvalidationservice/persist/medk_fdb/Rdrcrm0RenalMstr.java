package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdrcrm0RenalMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCRM0_RENAL_MSTR"
    ,schema="MEDK_FDB"
)

public class Rdrcrm0RenalMstr  implements java.io.Serializable {


    // Fields    

     private Rdrcrm0RenalMstrId id;


    // Constructors

    /** default constructor */
    public Rdrcrm0RenalMstr() {
    }

    
    /** full constructor */
    public Rdrcrm0RenalMstr(Rdrcrm0RenalMstrId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="dr2Rt", column=@Column(name="DR2_RT", nullable=false, length=3) ), 
        @AttributeOverride(name="renLoaged", column=@Column(name="REN_LOAGED", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="renHiaged", column=@Column(name="REN_HIAGED", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="fdbdx", column=@Column(name="FDBDX", nullable=false, length=9) ), 
        @AttributeOverride(name="dr2Dostpi", column=@Column(name="DR2_DOSTPI", nullable=false, length=2) ), 
        @AttributeOverride(name="renLocrcl", column=@Column(name="REN_LOCRCL", nullable=false, precision=3, scale=0) ), 
        @AttributeOverride(name="renHicrcl", column=@Column(name="REN_HICRCL", nullable=false, precision=3, scale=0) ), 
        @AttributeOverride(name="renSortOrder", column=@Column(name="REN_SORT_ORDER", nullable=false, precision=3, scale=0) ), 
        @AttributeOverride(name="dosingAdjTypeCd", column=@Column(name="DOSING_ADJ_TYPE_CD", nullable=false, precision=4, scale=0) ), 
        @AttributeOverride(name="renLodosd", column=@Column(name="REN_LODOSD", precision=8, scale=3) ), 
        @AttributeOverride(name="renLodosu", column=@Column(name="REN_LODOSU", length=2) ), 
        @AttributeOverride(name="renHidosd", column=@Column(name="REN_HIDOSD", precision=8, scale=3) ), 
        @AttributeOverride(name="renHidosu", column=@Column(name="REN_HIDOSU", length=2) ), 
        @AttributeOverride(name="renMxdosd", column=@Column(name="REN_MXDOSD", precision=8, scale=3) ), 
        @AttributeOverride(name="renMxdosu", column=@Column(name="REN_MXDOSU", length=2) ), 
        @AttributeOverride(name="renLofreq", column=@Column(name="REN_LOFREQ", precision=4) ), 
        @AttributeOverride(name="renHifreq", column=@Column(name="REN_HIFREQ", precision=4) ), 
        @AttributeOverride(name="renMx1dos", column=@Column(name="REN_MX1DOS", precision=8, scale=3) ), 
        @AttributeOverride(name="renMx1dsu", column=@Column(name="REN_MX1DSU", length=2) ), 
        @AttributeOverride(name="dxid", column=@Column(name="DXID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="renNteSingleDose", column=@Column(name="REN_NTE_SINGLE_DOSE", precision=8, scale=3) ), 
        @AttributeOverride(name="renNteSingleDoseUnitCode", column=@Column(name="REN_NTE_SINGLE_DOSE_UNIT_CODE", length=2) ), 
        @AttributeOverride(name="renFootnote", column=@Column(name="REN_FOOTNOTE") ), 
        @AttributeOverride(name="renMonoId", column=@Column(name="REN_MONO_ID", precision=8, scale=0) ) } )

    public Rdrcrm0RenalMstrId getId() {
        return this.id;
    }
    
    public void setId(Rdrcrm0RenalMstrId id) {
        this.id = id;
    }
   








}