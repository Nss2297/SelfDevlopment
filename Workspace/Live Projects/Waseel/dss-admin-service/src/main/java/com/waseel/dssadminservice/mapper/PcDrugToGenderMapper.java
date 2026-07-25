package com.waseel.dssadminservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.waseel.dssadminservice.model.customization.pcdrugtogender.DrugToGenderResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtogender.PcDrugToGenderRequestModel;
import com.waseel.dssadminservice.persist.mdss.PCGender;

@Mapper
public interface PcDrugToGenderMapper {

	PcDrugToGenderMapper INSTANCE = Mappers.getMapper(PcDrugToGenderMapper.class);

	@Mapping(source = "additionalRejectionReason", target = "rejectionReason")
	@Mapping(source = "lastUpdatedDateTime", target = "updateDateAndTime")
	@Mapping(source = "id.serviceCode", target = "serviceCode")
	@Mapping(source = "id.payerId", target = "payerId")
	@Mapping(source = "id.moduleName", target = "moduleName")
	@Mapping(source = "seqId", target = "id")
	DrugToGenderResponseModel pcGenderToDrugToGenderResponseModel(PCGender pcGender);

    @Mapping(target = "id.serviceCode", source = "serviceCode")
    @Mapping(target = "id.payerId", source = "payerId")
    @Mapping(target = "id.moduleName", source = "moduleName")
    @Mapping(target = "serviceStatus", source = "serviceStatus")
	@Mapping(target = "additionalRejectionReason", source = "additionalRejectionReason")
	@Mapping(target = "lastUpdatedDateTime", source = "updateDateAndTime")
	PCGender mapModelToEntity(PcDrugToGenderRequestModel model);
}
