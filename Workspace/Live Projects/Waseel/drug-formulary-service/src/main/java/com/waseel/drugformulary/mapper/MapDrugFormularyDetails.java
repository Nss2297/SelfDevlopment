package com.waseel.drugformulary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.waseel.drugformulary.model.DrugFormularyDetailsModel;
import com.waseel.drugformulary.persist.businessrules.DrugFormularyDetails;

@Mapper
public interface MapDrugFormularyDetails {

	MapDrugFormularyDetails INSTANCE = Mappers.getMapper(MapDrugFormularyDetails.class);

	DrugFormularyDetailsModel mapDrugFormularyDetailsEntityToDrugFormularyDetailsModel(
			DrugFormularyDetails drugFormularyDetailsEntity);
}
