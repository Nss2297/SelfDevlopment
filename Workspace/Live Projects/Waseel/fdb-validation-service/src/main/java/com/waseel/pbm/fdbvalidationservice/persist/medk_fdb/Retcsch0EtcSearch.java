package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Retcsch0EtcSearch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCSCH0_ETC_SEARCH"
    ,schema="MEDK_FDB"
)

public class Retcsch0EtcSearch  implements java.io.Serializable {


    // Fields    

     private Retcsch0EtcSearchId id;


    // Constructors

    /** default constructor */
    public Retcsch0EtcSearch() {
    }

    
    /** full constructor */
    public Retcsch0EtcSearch(Retcsch0EtcSearchId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="etcSearchEtcId", column=@Column(name="ETC_SEARCH_ETC_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="etcProductRelatedEtcId", column=@Column(name="ETC_PRODUCT_RELATED_ETC_ID", nullable=false, precision=8, scale=0) ) } )

    public Retcsch0EtcSearchId getId() {
        return this.id;
    }
    
    public void setId(Retcsch0EtcSearchId id) {
        this.id = id;
    }
   








}