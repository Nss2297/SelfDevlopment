package com.waseel.prescription.mapper;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.waseel.prescription.model.dss.DssDrugList;
import com.waseel.prescription.model.dss.DssRequest;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionRequestModel;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;

@Mapper
public interface MapRequestModel {

    MapRequestModel INSTANCE = Mappers.getMapper(MapRequestModel.class);

    @Mapping(source = "physicianLicenseNumber", target = "pharmacyId")
    DssRequest mapPrescriptionReqToDssReq(PrescriptionRequestModel prescriptionRequest);

    @Mapping(source = "drugCode", target = "ndcDrugCode")
    @Mapping(source = "quantity", target = "dispensedQuantity")
    @Mapping(source = "duration", target = "daysOfSupply")
    DssDrugList mapPrescriptionDrugListToDssDrugList(DrugList drugList);

    List<String> mapDiagnosisCodesToIcdCodes(List<DiagnosisCodes> diagnosisCodes);

    default String fromDiagnosisCodes(DiagnosisCodes diagnosisCodes) {
        return diagnosisCodes == null ? null : diagnosisCodes.getDiagnosisCode();
    }

    List<String> mapServiceStartDate(List<DrugList> drugList);

    default String fromServiceStartDate(DrugList drugList) {
        return drugList == null ? null : drugList.getServiceStartDate();
    }

    default String mapMemberIdToDssReq(PrescriptionRequestModel prescriptionRequest) {
        String memberId = prescriptionRequest.getMemberId();
        return StringUtils.isBlank(memberId) ? prescriptionRequest.getIdNumber() : memberId;
    }

    EPrescriptionRequestModel mapPrescriptionRequestModelToEPrescriptionRequestModel(
            PrescriptionRequestModel prescriptionRequestModel);
}
