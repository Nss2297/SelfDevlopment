package com.waseel.dssadminservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.waseel.dssadminservice.model.sfdamanagement.SFDADrugRequestModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDADrugResponseModel;
import com.waseel.dssadminservice.persist.mdss.DrugService;

@Mapper
public interface DrugServiceMapper {

	DrugServiceMapper INSTANCE = Mappers.getMapper(DrugServiceMapper.class);

	@Mapping(source = "code", target = "gtinCode")
	@Mapping(source = "display", target = "tradeName")
	@Mapping(source = "ingredients", target = "scientificName")
	@Mapping(source = "roaSuggested", target = "administrationRoute")
	@Mapping(source = "otherCodesValue", target = "sfdaCode")
	SFDADrugResponseModel drugServiceToSFDADrugResponseModel(DrugService drugService);
	
	@Mapping(source = "gtinCode", target = "code")
	@Mapping(source = "tradeName", target = "display")
	@Mapping(source = "scientificName", target = "ingredients")
	@Mapping(source = "administrationRoute", target = "roaSuggested")
	void updateDrugServiceFromSFDADrugRequestModel(SFDADrugRequestModel sfdaDrugRequestModel,
			@MappingTarget DrugService drugService);

}
