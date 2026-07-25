package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdrcrl0RenalMonoLine entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCRL0_RENAL_MONO_LINE"
    ,schema="MEDK_FDB"
)

public class Rdrcrl0RenalMonoLine  implements java.io.Serializable {


    // Fields    

     private Rdrcrl0RenalMonoLineId id;
     private Short renMonoSectionCd;
     private Short renMonoFormatCd;
     private String renMonoLineText;


    // Constructors

    /** default constructor */
    public Rdrcrl0RenalMonoLine() {
    }

	/** minimal constructor */
    public Rdrcrl0RenalMonoLine(Rdrcrl0RenalMonoLineId id, Short renMonoSectionCd, Short renMonoFormatCd) {
        this.id = id;
        this.renMonoSectionCd = renMonoSectionCd;
        this.renMonoFormatCd = renMonoFormatCd;
    }
    
    /** full constructor */
    public Rdrcrl0RenalMonoLine(Rdrcrl0RenalMonoLineId id, Short renMonoSectionCd, Short renMonoFormatCd, String renMonoLineText) {
        this.id = id;
        this.renMonoSectionCd = renMonoSectionCd;
        this.renMonoFormatCd = renMonoFormatCd;
        this.renMonoLineText = renMonoLineText;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="renMonoId", column=@Column(name="REN_MONO_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="renMonoLineNumber", column=@Column(name="REN_MONO_LINE_NUMBER", nullable=false, precision=8, scale=0) ) } )

    public Rdrcrl0RenalMonoLineId getId() {
        return this.id;
    }
    
    public void setId(Rdrcrl0RenalMonoLineId id) {
        this.id = id;
    }
    
    @Column(name="REN_MONO_SECTION_CD", nullable=false, precision=4, scale=0)

    public Short getRenMonoSectionCd() {
        return this.renMonoSectionCd;
    }
    
    public void setRenMonoSectionCd(Short renMonoSectionCd) {
        this.renMonoSectionCd = renMonoSectionCd;
    }
    
    @Column(name="REN_MONO_FORMAT_CD", nullable=false, precision=4, scale=0)

    public Short getRenMonoFormatCd() {
        return this.renMonoFormatCd;
    }
    
    public void setRenMonoFormatCd(Short renMonoFormatCd) {
        this.renMonoFormatCd = renMonoFormatCd;
    }
    
    @Column(name="REN_MONO_LINE_TEXT")

    public String getRenMonoLineText() {
        return this.renMonoLineText;
    }
    
    public void setRenMonoLineText(String renMonoLineText) {
        this.renMonoLineText = renMonoLineText;
    }
   








}