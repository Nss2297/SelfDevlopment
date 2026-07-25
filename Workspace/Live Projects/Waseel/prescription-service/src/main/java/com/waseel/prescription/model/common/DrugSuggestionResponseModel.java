package com.waseel.prescription.model.common;

import java.util.Date;

public class DrugSuggestionResponseModel extends DrugServiceModel {

	private static final long serialVersionUID = 1L;

	private String isExclusionList;
	

	public DrugSuggestionResponseModel(String unitPrice, String sfdaCode, String sfdaDescription, String scientificName,
			String scientificCode, String dosageForm, String strengthUnit, Long waseelDrugId, Date lastUpdatedDate,
			Long drugFormularyId, Boolean isDeletedDrugForFormulary, String strength, String roaSuggested,
			String isExclusionList) {
		super(unitPrice, sfdaCode, sfdaDescription, scientificName, scientificCode, dosageForm, strengthUnit,
				waseelDrugId, lastUpdatedDate, drugFormularyId, isDeletedDrugForFormulary, strength, roaSuggested);
		this.isExclusionList = isExclusionList;
	}

	public String getIsExclusionList() {
		return isExclusionList;
	}

	public void setIsExclusionList(String isExclusionList) {
		this.isExclusionList = isExclusionList;
	}
}
