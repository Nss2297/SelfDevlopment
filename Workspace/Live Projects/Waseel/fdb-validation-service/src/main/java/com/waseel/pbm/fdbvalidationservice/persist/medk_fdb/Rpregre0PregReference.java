package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rpregre0PregReference entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPREGRE0_PREG_REFERENCE"
    ,schema="MEDK_FDB"
)

public class Rpregre0PregReference  implements java.io.Serializable {


    // Fields    

     private Integer pregReferenceId;
     private Integer pregRefernceTypeId;
     private String pregReferenceTitle;
     private String pregReferenceAuthor;
     private String pregReferenceName;
     private String pregReferenceIssueDtTxt;
     private String pregReferenceVolume;
     private String pregReferenceSupplementNbr;
     private String pregReferenceEdition;
     private String pregReferenceLocation;
     private Integer pregReferenceAccessedDt;
     private String pregReferenceIssue;
     private String pregReferencePage;
     private String pregReferencePubmedId;
     private String pregReferenceUrlText;


    // Constructors

    /** default constructor */
    public Rpregre0PregReference() {
    }

	/** minimal constructor */
    public Rpregre0PregReference(Integer pregReferenceId, Integer pregRefernceTypeId, String pregReferenceTitle) {
        this.pregReferenceId = pregReferenceId;
        this.pregRefernceTypeId = pregRefernceTypeId;
        this.pregReferenceTitle = pregReferenceTitle;
    }
    
    /** full constructor */
    public Rpregre0PregReference(Integer pregReferenceId, Integer pregRefernceTypeId, String pregReferenceTitle, String pregReferenceAuthor, String pregReferenceName, String pregReferenceIssueDtTxt, String pregReferenceVolume, String pregReferenceSupplementNbr, String pregReferenceEdition, String pregReferenceLocation, Integer pregReferenceAccessedDt, String pregReferenceIssue, String pregReferencePage, String pregReferencePubmedId, String pregReferenceUrlText) {
        this.pregReferenceId = pregReferenceId;
        this.pregRefernceTypeId = pregRefernceTypeId;
        this.pregReferenceTitle = pregReferenceTitle;
        this.pregReferenceAuthor = pregReferenceAuthor;
        this.pregReferenceName = pregReferenceName;
        this.pregReferenceIssueDtTxt = pregReferenceIssueDtTxt;
        this.pregReferenceVolume = pregReferenceVolume;
        this.pregReferenceSupplementNbr = pregReferenceSupplementNbr;
        this.pregReferenceEdition = pregReferenceEdition;
        this.pregReferenceLocation = pregReferenceLocation;
        this.pregReferenceAccessedDt = pregReferenceAccessedDt;
        this.pregReferenceIssue = pregReferenceIssue;
        this.pregReferencePage = pregReferencePage;
        this.pregReferencePubmedId = pregReferencePubmedId;
        this.pregReferenceUrlText = pregReferenceUrlText;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PREG_REFERENCE_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getPregReferenceId() {
        return this.pregReferenceId;
    }
    
    public void setPregReferenceId(Integer pregReferenceId) {
        this.pregReferenceId = pregReferenceId;
    }
    
    @Column(name="PREG_REFERNCE_TYPE_ID", nullable=false, precision=8, scale=0)

    public Integer getPregRefernceTypeId() {
        return this.pregRefernceTypeId;
    }
    
    public void setPregRefernceTypeId(Integer pregRefernceTypeId) {
        this.pregRefernceTypeId = pregRefernceTypeId;
    }
    
    @Column(name="PREG_REFERENCE_TITLE", nullable=false)

    public String getPregReferenceTitle() {
        return this.pregReferenceTitle;
    }
    
    public void setPregReferenceTitle(String pregReferenceTitle) {
        this.pregReferenceTitle = pregReferenceTitle;
    }
    
    @Column(name="PREG_REFERENCE_AUTHOR")

    public String getPregReferenceAuthor() {
        return this.pregReferenceAuthor;
    }
    
    public void setPregReferenceAuthor(String pregReferenceAuthor) {
        this.pregReferenceAuthor = pregReferenceAuthor;
    }
    
    @Column(name="PREG_REFERENCE_NAME")

    public String getPregReferenceName() {
        return this.pregReferenceName;
    }
    
    public void setPregReferenceName(String pregReferenceName) {
        this.pregReferenceName = pregReferenceName;
    }
    
    @Column(name="PREG_REFERENCE_ISSUE_DT_TXT", length=25)

    public String getPregReferenceIssueDtTxt() {
        return this.pregReferenceIssueDtTxt;
    }
    
    public void setPregReferenceIssueDtTxt(String pregReferenceIssueDtTxt) {
        this.pregReferenceIssueDtTxt = pregReferenceIssueDtTxt;
    }
    
    @Column(name="PREG_REFERENCE_VOLUME", length=80)

    public String getPregReferenceVolume() {
        return this.pregReferenceVolume;
    }
    
    public void setPregReferenceVolume(String pregReferenceVolume) {
        this.pregReferenceVolume = pregReferenceVolume;
    }
    
    @Column(name="PREG_REFERENCE_SUPPLEMENT_NBR", length=80)

    public String getPregReferenceSupplementNbr() {
        return this.pregReferenceSupplementNbr;
    }
    
    public void setPregReferenceSupplementNbr(String pregReferenceSupplementNbr) {
        this.pregReferenceSupplementNbr = pregReferenceSupplementNbr;
    }
    
    @Column(name="PREG_REFERENCE_EDITION", length=80)

    public String getPregReferenceEdition() {
        return this.pregReferenceEdition;
    }
    
    public void setPregReferenceEdition(String pregReferenceEdition) {
        this.pregReferenceEdition = pregReferenceEdition;
    }
    
    @Column(name="PREG_REFERENCE_LOCATION", length=80)

    public String getPregReferenceLocation() {
        return this.pregReferenceLocation;
    }
    
    public void setPregReferenceLocation(String pregReferenceLocation) {
        this.pregReferenceLocation = pregReferenceLocation;
    }
    
    @Column(name="PREG_REFERENCE_ACCESSED_DT", precision=8, scale=0)

    public Integer getPregReferenceAccessedDt() {
        return this.pregReferenceAccessedDt;
    }
    
    public void setPregReferenceAccessedDt(Integer pregReferenceAccessedDt) {
        this.pregReferenceAccessedDt = pregReferenceAccessedDt;
    }
    
    @Column(name="PREG_REFERENCE_ISSUE", length=80)

    public String getPregReferenceIssue() {
        return this.pregReferenceIssue;
    }
    
    public void setPregReferenceIssue(String pregReferenceIssue) {
        this.pregReferenceIssue = pregReferenceIssue;
    }
    
    @Column(name="PREG_REFERENCE_PAGE", length=80)

    public String getPregReferencePage() {
        return this.pregReferencePage;
    }
    
    public void setPregReferencePage(String pregReferencePage) {
        this.pregReferencePage = pregReferencePage;
    }
    
    @Column(name="PREG_REFERENCE_PUBMED_ID", length=50)

    public String getPregReferencePubmedId() {
        return this.pregReferencePubmedId;
    }
    
    public void setPregReferencePubmedId(String pregReferencePubmedId) {
        this.pregReferencePubmedId = pregReferencePubmedId;
    }
    
    @Column(name="PREG_REFERENCE_URL_TEXT", length=500)

    public String getPregReferenceUrlText() {
        return this.pregReferenceUrlText;
    }
    
    public void setPregReferenceUrlText(String pregReferenceUrlText) {
        this.pregReferenceUrlText = pregReferenceUrlText;
    }
   








}