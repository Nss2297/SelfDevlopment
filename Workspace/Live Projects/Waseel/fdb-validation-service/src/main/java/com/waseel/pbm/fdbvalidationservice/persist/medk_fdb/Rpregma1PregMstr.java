package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rpregma1PregMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPREGMA1_PREG_MSTR"
    ,schema="MEDK_FDB"
)

public class Rpregma1PregMstr  implements java.io.Serializable {


    // Fields    

     private Integer pregCode;
     private String pregDesc;
     private String pregSl;
     private String pregBoxedWarningInd;


    // Constructors

    /** default constructor */
    public Rpregma1PregMstr() {
    }

    
    /** full constructor */
    public Rpregma1PregMstr(Integer pregCode, String pregDesc, String pregSl, String pregBoxedWarningInd) {
        this.pregCode = pregCode;
        this.pregDesc = pregDesc;
        this.pregSl = pregSl;
        this.pregBoxedWarningInd = pregBoxedWarningInd;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PREG_CODE", unique=true, nullable=false, precision=6, scale=0)

    public Integer getPregCode() {
        return this.pregCode;
    }
    
    public void setPregCode(Integer pregCode) {
        this.pregCode = pregCode;
    }
    
    @Column(name="PREG_DESC", nullable=false, length=41)

    public String getPregDesc() {
        return this.pregDesc;
    }
    
    public void setPregDesc(String pregDesc) {
        this.pregDesc = pregDesc;
    }
    
    @Column(name="PREG_SL", nullable=false, length=1)

    public String getPregSl() {
        return this.pregSl;
    }
    
    public void setPregSl(String pregSl) {
        this.pregSl = pregSl;
    }
    
    @Column(name="PREG_BOXED_WARNING_IND", nullable=false, length=1)

    public String getPregBoxedWarningInd() {
        return this.pregBoxedWarningInd;
    }
    
    public void setPregBoxedWarningInd(String pregBoxedWarningInd) {
        this.pregBoxedWarningInd = pregBoxedWarningInd;
    }
   








}