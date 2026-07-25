package com.waseel.prescription.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.waseel.prescription.model.dispense.SuggestedDrug;
import com.waseel.prescription.model.inquiry.detail.SuggestedDrugInquiry;

@Mapper
public interface MapSuggestedDrugInquiry {
	MapSuggestedDrugInquiry INSTANCE = Mappers.getMapper(MapSuggestedDrugInquiry.class);

	List<SuggestedDrugInquiry> mapPrescriptionDrugToSuggestedDrugInquiry(List<SuggestedDrug> suggestedDrugs);
}
