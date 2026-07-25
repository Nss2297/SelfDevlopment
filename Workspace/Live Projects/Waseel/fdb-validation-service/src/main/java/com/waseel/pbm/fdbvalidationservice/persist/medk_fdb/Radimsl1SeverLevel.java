package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Radimsl1SeverLevel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIMSL1_SEVER_LEVEL"
    ,schema="MEDK_FDB"
)

public class Radimsl1SeverLevel  implements java.io.Serializable {


    // Fields    

     private Radimsl1SeverLevelId id;
     private String ddiSltxt;


    // Constructors

    /** default constructor */
    public Radimsl1SeverLevel() {
    }

	/** minimal constructor */
    public Radimsl1SeverLevel(Radimsl1SeverLevelId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Radimsl1SeverLevel(Radimsl1SeverLevelId id, String ddiSltxt) {
        this.id = id;
        this.ddiSltxt = ddiSltxt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="ddiSl", column=@Column(name="DDI_SL", nullable=false, length=1) ), 
        @AttributeOverride(name="ddiSlsn", column=@Column(name="DDI_SLSN", nullable=false, precision=2, scale=0) ) } )

    public Radimsl1SeverLevelId getId() {
        return this.id;
    }
    
    public void setId(Radimsl1SeverLevelId id) {
        this.id = id;
    }
    
    @Column(name="DDI_SLTXT", length=70)

    public String getDdiSltxt() {
        return this.ddiSltxt;
    }
    
    public void setDdiSltxt(String ddiSltxt) {
        this.ddiSltxt = ddiSltxt;
    }
   








}