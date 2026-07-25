package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DrugExclusionMetaDataResponseModel {

    private Long exclusionId;
    private String name;
    private String createdDate;
    private String updatedDate;

    public Long getExclusionId() {
        return exclusionId;
    }

    public void setExclusionId(Long exclusionId) {
        this.exclusionId = exclusionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public DrugExclusionMetaDataResponseModel() {
    }

    public DrugExclusionMetaDataResponseModel(Long id, String name, Date createdDate, Date updatedDate) {
        this.exclusionId = id;
        this.name = name;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        if (createdDate != null) {
            this.createdDate = simpleDateFormat.format(createdDate);
        }
        if (updatedDate != null) {
            this.updatedDate = simpleDateFormat.format(updatedDate);
        }
    }
}
