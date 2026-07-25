package com.waseel.prescription.service.management;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.common.CommonResponse;
import com.waseel.prescription.model.enums.FrequencyType;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.mdss.DrugServiceMetaData;
import com.waseel.prescription.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.service.mapper.MapperService;

import feign.FeignException;

@Service
public class DataPopulationService {
	
	@Autowired
	private MapperService mapperService;

	public CommonResponse populateFailedResponse() {
		CommonResponse response = new CommonResponse();
		response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setResponseDescription("Failed");
		return response;
	}
	
	public MemberDemographicDataResponseModel createInvalidResponse(FeignException ex) {
		MemberDemographicDataResponseModel response = new MemberDemographicDataResponseModel();
		if (ex.status() == HttpStatus.BAD_REQUEST.value() || ex.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()
				|| ex.status() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
			response = mapperService.mapMemberDemographicDataResponseModel(ex.contentUTF8());
		} else if (ex.status() == -1) {
			response.setStatus("FAILED");
			response.setStatusDescription("Not able to call PBM-Payer-Apis-Service");
		}
		return response;
	}

	public List<String> getUnitTypes() {
		return Arrays.stream(UnitType.values()).map(UnitType::value).collect(Collectors.toList());
	}

	public List<String> getFrequencyTypes() {
		return Arrays.stream(FrequencyType.values()).map(FrequencyType::value).collect(Collectors.toList());
	}

	public List<String> getRequestStatusTypes() {
		return Arrays.stream(RequestStatusType.values()).map(RequestStatusType::value).collect(Collectors.toList());
	}
	
	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@Autowired
	private DrugServiceRepository drugServiceRepository;
	
	public String insertDrug() {
		DrugServiceMetaData serviceMetaData = new DrugServiceMetaData();
		serviceMetaData.setEffectiveDate(new Date());
		serviceMetaData.setFileName("Testing.xlsx");
//		String ownerName = UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication());
		serviceMetaData.setOwnerName("PBM");
		serviceMetaData.setSfdaUpdateDate(new Date());
		serviceMetaData.setUploadDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		serviceMetaData.setSfdaVersion("V8");

		serviceMetaData = drugServiceMetaDataRepository.save(serviceMetaData);
		final Long drugListId = serviceMetaData.getDrugListId();
		DrugService drugService = new DrugService();
		drugService.setCategory("PHARMACEUTICAL");
		drugService.setCode("06285101001192");
		drugService.setDiscontinueDate(null);
		drugService.setDisplay("PREDO 5MG TABLETS");
		drugService.setDosageForm("TABLETS");
		drugService.setDrugListId(drugListId);
		drugService.setGranularUnit("30");
		drugService.setIngredients("PREDNISOLONE");
		drugService.setLastUpdatedDate(new Date());
		drugService.setOtherCodesType("SFDA");
		drugService.setOtherCodesValue("146-172-05");
		drugService.setPackageSize("30");
		drugService.setPrice("4154");
		drugService.setScientificCode("8000000489");
		drugService.setStrength("4");
		drugService.setStrengthUnit("MG");
		drugService.setRoaSuggested("ORAL");
		Long waseelDrugId = drugServiceRepository.findFirstWaseelDrugId();
		Long seq = waseelDrugId + 1;
		drugService.setWaseelDrugId(seq);
		drugService = drugServiceRepository.save(drugService);
		return "Drug inserted ";
	}

}
