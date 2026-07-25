package com.waseel.dssadminservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "`CustomizationBatch`", schema = "MDSS")
public class CustomizationBatch implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "`Id`")
    private Long batchId;

    @Column(name = "`BatchReference`")
    private String batchReference;

    @Column(name = "`CreatedDate`")
    private Date createdDate;

    @Column(name = "`Uploader`")
    private String uploader;

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchReference() {
        return this.batchReference;
    }

    public void setBatchReference(String batchReference) {
        this.batchReference = batchReference;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUploader() {
        return this.uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
}
