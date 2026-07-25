package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rpregpl0PregMonoLine entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPREGPL0_PREG_MONO_LINE"
    ,schema="MEDK_FDB"
)

public class Rpregpl0PregMonoLine  implements java.io.Serializable {


    // Fields    

     private Rpregpl0PregMonoLineId id;
     private Integer pregCode;
     private String pregMonoLine;


    // Constructors

    /** default constructor */
    public Rpregpl0PregMonoLine() {
    }

    
    /** full constructor */
    public Rpregpl0PregMonoLine(Rpregpl0PregMonoLineId id, Integer pregCode, String pregMonoLine) {
        this.id = id;
        this.pregCode = pregCode;
        this.pregMonoLine = pregMonoLine;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="pregMonoId", column=@Column(name="PREG_MONO_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="pregMonoSectionCd", column=@Column(name="PREG_MONO_SECTION_CD", nullable=false, precision=4, scale=0) ), 
        @AttributeOverride(name="pregMonoSn", column=@Column(name="PREG_MONO_SN", nullable=false, precision=2, scale=0) ) } )

    public Rpregpl0PregMonoLineId getId() {
        return this.id;
    }
    
    public void setId(Rpregpl0PregMonoLineId id) {
        this.id = id;
    }
    
    @Column(name="PREG_CODE", nullable=false, precision=6, scale=0)

    public Integer getPregCode() {
        return this.pregCode;
    }
    
    public void setPregCode(Integer pregCode) {
        this.pregCode = pregCode;
    }
    
    @Column(name="PREG_MONO_LINE", nullable=false, length=500)

    public String getPregMonoLine() {
        return this.pregMonoLine;
    }
    
    public void setPregMonoLine(String pregMonoLine) {
        this.pregMonoLine = pregMonoLine;
    }
   








}