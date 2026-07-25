package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionType;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidExclusionType;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidNetworkId;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidSpecialityId;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NumericValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExclusionTypeRequestModel {

    @NotEmpty(message = "exclusionType {notNullOrEmpty}")
    @IsValidExclusionType(message = "{exclusionTypeValidation}")
    private String exclusionType;

    @NumericValue(message = "exclusionNetwork {onlyNumericValue}", value = "exclusionNetwork")
    @IsValidNetworkId
    private String exclusionNetwork;

    @NumericValue(message = "exclusionProvider {onlyNumericValue}", value = "exclusionProvider")
    private String exclusionProvider;

    private String exclusionProviderName;

    @NumericValue(message = "exclusionSpecialty {onlyNumericValue}", value = "exclusionSpecialty")
    @IsValidSpecialityId
    private String exclusionSpecialty;

    public String getExclusionType() {
        return exclusionType;
    }

    public String getExclusionNetwork() {
        return exclusionNetwork;
    }

    public String getExclusionProvider() {
        return exclusionProvider;
    }

    public String getExclusionSpecialty() {
        return exclusionSpecialty;
    }

    public void setExclusionType(String exclusionType) {
        this.exclusionType = exclusionType;
    }

    public void setExclusionNetwork(String exclusionNetwork) {
        this.exclusionNetwork = !StringUtils.isBlank(exclusionNetwork) ? exclusionNetwork.trim() : exclusionNetwork;
    }

    public void setExclusionProvider(String exclusionProvider) {
        this.exclusionProvider = exclusionProvider;
    }

    public void setExclusionSpecialty(String exclusionSpecialty) {
        this.exclusionSpecialty = exclusionSpecialty;
    }

	public String getExclusionProviderName() {
		return exclusionProviderName;
	}

	public void setExclusionProviderName(String exclusionProviderName) {
		this.exclusionProviderName = exclusionProviderName;
	}

	public ExclusionTypeRequestModel() {
        super();
    }

    public ExclusionTypeRequestModel(String exclusionType, String exclusionNetwork, String exclusionProvider,
                                     String exclusionSpecialty) {
        super();
        this.exclusionType = exclusionType;
        this.exclusionNetwork = exclusionNetwork;
        this.exclusionProvider = exclusionProvider;
        this.exclusionSpecialty = exclusionSpecialty;
    }

    @AssertTrue(message = "exclusionNetwork {notNullOrEmpty} when exclusionType is Network Exclusion")
    public boolean isExclusionNetworkValid() {
        if (ExclusionType.NETWORK_EXCLUSION.value().equalsIgnoreCase(exclusionType)) {
            return !StringUtils.isBlank(exclusionNetwork);
        }
        return true;
    }

    @AssertTrue(message = "exclusionProvider {notNullOrEmpty} when exclusionType is Provider Exclusion")
    public boolean isExclusionProviderValid() {
        if (ExclusionType.PROVIDER_EXCLUSION.value().equalsIgnoreCase(exclusionType)) {
            return !StringUtils.isBlank(exclusionProvider);
        }
        return true;
    }

    @AssertTrue(message = "exclusionProviderName {notNullOrEmpty} when exclusionType is Provider Exclusion")
    public boolean isExclusionProviderNameValid() {
        if (ExclusionType.PROVIDER_EXCLUSION.value().equalsIgnoreCase(exclusionType)) {
            return !StringUtils.isBlank(exclusionProviderName);
        }
        return true;
    }
    
    @AssertTrue(message = "exclusionSpecialty {notNullOrEmpty} when exclusionType is Speciality Exclusion")
    public boolean isExclusionSpecialtyValid() {
        if (ExclusionType.SPECIALITY_EXCLUSION.value().equalsIgnoreCase(exclusionType)) {
            return !StringUtils.isBlank(exclusionSpecialty);
        }
        return true;
    }

}
