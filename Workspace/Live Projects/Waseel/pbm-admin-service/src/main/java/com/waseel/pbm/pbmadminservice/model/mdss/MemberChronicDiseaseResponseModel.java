package com.waseel.pbm.pbmadminservice.model.mdss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonInclude(Include.NON_NULL)
@JsonTypeName("chronicDiseaseInformation")
public class MemberChronicDiseaseResponseModel {
    
    @JsonProperty("chronicDiseaseId")
    private Integer chronicDiseasesId;
    @JsonProperty("chronicDiseaseName")
	private String chronicDiseasesName;
    @JsonProperty("payerId")
    private String payerId;

    public MemberChronicDiseaseResponseModel(Integer chronicDiseasesId, String chronicDiseasesName, String payerId) {
            this.chronicDiseasesId = chronicDiseasesId;
            this.chronicDiseasesName = chronicDiseasesName;
            this.payerId = payerId;
    }

    @JsonProperty("chronicDiseaseId")
    public Integer getChronicDiseasesId() {
        return chronicDiseasesId;
    }

    @JsonProperty("chronicDiseaseId")
    public void setChronicDiseasesId(Integer chronicDiseasesId) {
        this.chronicDiseasesId = chronicDiseasesId;
    }

    @JsonProperty("chronicDiseaseName")
    public String getChronicDiseasesName() {
        return chronicDiseasesName;
    }

    @JsonProperty("chronicDiseaseName")
    public void setChronicDiseasesName(String chronicDiseasesName) {
        this.chronicDiseasesName = chronicDiseasesName;
    }

    @JsonProperty("payerId")
    public String getPayerId() {
        return payerId;
    }

    @JsonProperty("payerId")
    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }
}
