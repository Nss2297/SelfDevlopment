package com.waseel.dssadminservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.DuplicateTherapyResponseModel;
import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.PcDuplicateTherapyRequestModel;
import com.waseel.dssadminservice.persist.mdss.PCDuplicateTherapy;

@Mapper
public interface PcDuplicateTherapyMapper {

	PcDuplicateTherapyMapper INSTANCE = Mappers.getMapper(PcDuplicateTherapyMapper.class);

	@Mapping(source = "additionalRejectionReason", target = "additionalRejectionReason")
	@Mapping(source = "lastUpdatedDateTime", target = "lastUpdateDateAndTime")
	@Mapping(source = "id.interactedServiceCode", target = "interactedServiceCode")
	@Mapping(source = "id.serviceCode", target = "serviceCode")
	@Mapping(source = "id.payerId", target = "payerId")
	@Mapping(source = "id.moduleName", target = "moduleName")
	@Mapping(source = "seqId", target = "id")
	@Mapping(source = "serviceStatus", target = "serviceStatus")
	DuplicateTherapyResponseModel pcDuplicateTherapyResponseModel(PCDuplicateTherapy pcDuplicateTherapy);
	
	@Mapping(target = "id.serviceCode", source = "serviceCode")
	@Mapping(target = "id.interactedServiceCode", source = "interactedServiceCode")
	@Mapping(target = "id.payerId", source = "payerId")
	@Mapping(target = "id.moduleName", source = "moduleName")
	@Mapping(target = "serviceStatus", source = "serviceStatus")
	@Mapping(target = "additionalRejectionReason", source = "additionalRejectionReason")
	PCDuplicateTherapy mapModelToEntity(PcDuplicateTherapyRequestModel model);
}
