package com.waseel.dssadminservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.waseel.dssadminservice.model.customization.pcdrugtoage.DrugToAgeResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtoage.PcDrugToAgeRequestModel;
import com.waseel.dssadminservice.persist.mdss.PCAge;

@Mapper
public interface PcDrugToAgeMapper {

	PcDrugToAgeMapper INSTANCE = Mappers.getMapper(PcDrugToAgeMapper.class);

	@Mapping(target = "id.payerId", source = "payerId")
	@Mapping(target = "id.moduleName", source = "moduleName")
	@Mapping(target = "id.serviceCode", source = "serviceCode")
	@Mapping(target = "fromAgeInDays", source = "fromAgeInDays")
	@Mapping(target = "toAgeInDays", source = "toAgeInDays")
	@Mapping(target = "serviceStatus", source = "serviceStatus")
	@Mapping(target = "additionalRejectionReason", source = "additionalRejectionReason")
	PCAge mapModelToEntity(PcDrugToAgeRequestModel model);

	@Mapping(target = "fromAgeInDays", source = "fromAgeInDays")
	@Mapping(target = "toAgeInDays", source = "toAgeInDays")
	@Mapping(target = "serviceStatus", source = "serviceStatus")
	@Mapping(target = "additionalRejectionReason", source = "additionalRejectionReason")
	@Mapping(target = "id.serviceCode", source = "serviceCode")
	@Mapping(target = "id.payerId", source = "payerId")
	@Mapping(target = "id.moduleName", source = "moduleName")
	void updatePcAgeFromPcDrugToAgeRequestModel(PcDrugToAgeRequestModel pcDrugToAgeRequestModel,
			@MappingTarget PCAge pcAge);

	@Mapping(source = "additionalRejectionReason", target = "rejectionReason")
	@Mapping(source = "lastUpdatedDateTime", target = "updateDateAndTime")
	@Mapping(source = "toAgeInDays", target = "toAgeInDays")
	@Mapping(source = "fromAgeInDays", target = "fromAgeInDays")
	@Mapping(source = "id.serviceCode", target = "serviceCode")
	@Mapping(source = "id.payerId", target = "payerId")
	@Mapping(source = "id.moduleName", target = "moduleName")
	@Mapping(source = "seqId", target = "id")
	@Mapping(source = "serviceStatus", target = "serviceStatus")
	DrugToAgeResponseModel pcAgeToDrugToAgeResponseModel(PCAge pcAge);

}
