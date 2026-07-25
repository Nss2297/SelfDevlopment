package com.waseel.pbm.dssservice.service.validationservice;
//package com.waseel.pbm.dssservice.service.validationservice;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.pbm.dssservice.enums.DssRejectionCodes;
import com.waseel.pbm.dssservice.enums.RequestStatus;
import com.waseel.pbm.dssservice.enums.ScreeningModules;
import com.waseel.pbm.dssservice.enums.ServiceStatus;
import com.waseel.pbm.dssservice.model.DrugList;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.model.Error;
import com.waseel.pbm.dssservice.model.Result;
import com.waseel.pbm.dssservice.persist.mdss.DrugService;
import com.waseel.pbm.dssservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.dssservice.persist.mdss.PayerValidationConfiguration;
import com.waseel.pbm.dssservice.repository.mdss.CommonRejectionReasonRepository;
import com.waseel.pbm.dssservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.dssservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.dssservice.repository.mdss.PayerModuleConfigurationRepository;
import com.waseel.pbm.dssservice.repository.mdss.PayerValidationConfigurationRepository;
import com.waseel.pbm.dssservice.repository.medk_fdb.Ripdat0ProductAttributeRepository;
import com.waseel.pbm.dssservice.repository.medk_fdb.Ripdpp0ProductMasterRepository;
import com.waseel.pbm.dssservice.service.clienthandlerservice.RestHandler;
import com.waseel.pbm.dssservice.service.managementservice.ChronicDzManagementService;

@Service
public class DssDrugValidationService {

	@Autowired
	RestHandler restHandler;
	@Autowired
	Ripdat0ProductAttributeRepository productAttributeRepo;
	@Autowired
	Ripdpp0ProductMasterRepository productMasterRepo;
	@Autowired
	PayerModuleConfigurationRepository modulesConfigurationRepo;
	@Autowired
	PayerValidationConfigurationRepository payerValidationConfigurationRepo;
	@Autowired
	CommonRejectionReasonRepository commonRejectionReasonRepo;
	@Autowired
	DrugServiceRepository drugServiceRepo;
	@Autowired
	DrugServiceMetaDataRepository drugServiceMetaDataRepo;
	@Autowired
	ChronicDzManagementService chronicDzManagementService;
	private static final String DRUGNOTOFOUND_STR = "(<DrugCode>)";

	public DssResponse validate(DssRequest dssRequest, Long transactionLogId) {
		DssResponse dssResponse = null;
		List<Result> initDrugResults = new ArrayList<>();
		List<DrugList> excludedDrugs = validateDssDrug(dssRequest, initDrugResults);
		if (!excludedDrugs.isEmpty()) {
			// To Stop Sending Requests In Case All services Are Rejected on this level of
			// validation
			if (excludedDrugs.size() < dssRequest.getDrugList().size()) {
				DssRequest editedDssRequest = removeExcludedDrugs(dssRequest, excludedDrugs);
				editedDssRequest = chronicDzManagementService.manageChronicDzValidation(editedDssRequest);
				dssResponse = restHandler.handleDssRequest(editedDssRequest, transactionLogId);
			}
		} else {
			DssRequest editedDssRequest = chronicDzManagementService.manageChronicDzValidation(dssRequest);
			dssResponse = restHandler.handleDssRequest(editedDssRequest, transactionLogId);
		}

		if (dssResponse != null && !initDrugResults.isEmpty() && dssResponse.getHttpStatusCode() == 200) {
			//
			removeAddedChronicDiagRejections(dssResponse, dssRequest);
			addInitDrugResultsToDssResponse(dssRequest, dssResponse, initDrugResults);
			modifyDssResponseStatus(dssResponse);
		} else if (dssResponse != null && initDrugResults.isEmpty() && dssResponse.getHttpStatusCode() == 200) {
			removeAddedChronicDiagRejections(dssResponse, dssRequest);
		} else if (dssResponse == null && !initDrugResults.isEmpty()) { // In Case all Services Are Rejected from This
			// Level of Validation
			dssResponse = setDssResponse(dssRequest, initDrugResults);
		}

		if (dssResponse != null && dssResponse.getHttpStatusCode() == HttpStatus.OK.value()
				&& (dssResponse.getStatus().equalsIgnoreCase(RequestStatus.REJECTED.value())
						|| dssResponse.getStatus().equalsIgnoreCase(RequestStatus.PARTIAL_APPROVED.value()))) {
			setDssResponseErrors(dssResponse);
		}

		return dssResponse;
	}

	private void removeAddedChronicDiagRejections(DssResponse dssResponse, DssRequest dssRequest) {

		for (Result result : dssResponse.getResults()) {

			if (result.getErrors() != null && !result.getErrors().isEmpty()
					&& result.getStatus().equalsIgnoreCase(ServiceStatus.REJECTED.toString())) {

				List<Error> finalRejections = new ArrayList<Error>();

				List<Error> IndicationRejections = result.getErrors().stream()
						.filter(rejection -> dssRequest.getIcdCodes().stream()
								.anyMatch(diganosis -> (rejection.getCode().contains("CPINDC001")
										|| rejection.getCode().contains("CPINDI001"))
										&& rejection.getDescription().contains(diganosis)))
						.collect(Collectors.toList());

				if (IndicationRejections != null && !IndicationRejections.isEmpty()) {
					finalRejections.addAll(IndicationRejections);
				}

				List<Error> otherRejections = result
						.getErrors().stream().filter(
								rejection -> dssRequest.getIcdCodes().stream()
										.anyMatch(diganosis -> (!rejection.getCode().contains("CPINDC001")
												&& !rejection.getCode().contains("CPINDI001"))))
						.collect(Collectors.toList());

				if (otherRejections != null && !otherRejections.isEmpty()) {
					finalRejections.addAll(otherRejections);
				}

				if (finalRejections != null && !finalRejections.isEmpty()) {

					result.setErrors(finalRejections);

				} else {
					result.setStatus(ServiceStatus.APPROVED.toString());
				}

			}
		}

	}

	public List<DrugList> validateDssDrug(DssRequest dssRequest, List<Result> initDrugResults) {
		List<DrugList> excludedDrugs = new ArrayList<>();
		for (DrugList reqDrug : dssRequest.getDrugList()) {
			List<Error> rejectionsList = new ArrayList<>();
			validateRequestedDrug(reqDrug, dssRequest.getPayerId(), rejectionsList,
					getActiveSfda(convertStringToDate(dssRequest.getDateOfService())));
			if (!rejectionsList.isEmpty()) {
				Result drugResult = populateResult(reqDrug, rejectionsList);
				initDrugResults.add(drugResult);
				excludedDrugs.add(reqDrug);
			}
		}
		return excludedDrugs;
	}

	private Optional<DrugServiceMetaData> getActiveSfda(Date dateOfService) {

		return drugServiceMetaDataRepo
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(dateOfService);
	}

	private Date convertStringToDate(String dateOfService) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date effectiveDate = null;
		try {
			effectiveDate = formatter.parse(dateOfService);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return effectiveDate;
	}

	private void validateRequestedDrug(DrugList reqDrug, String payerId, List<Error> rejectionsList,
			Optional<DrugServiceMetaData> drugServiceMetaData) {
		List<Error> rejections = populateRejectionError(reqDrug, payerId, drugServiceMetaData);
		if (!rejections.isEmpty()) {
			rejectionsList.addAll(rejections);
		}
	}

	private Result populateResult(DrugList reqDrug, List<Error> rejectionsList) {
		Result drugResult = new Result();
		if (reqDrug.getNdcDrugCode() != null && !reqDrug.getNdcDrugCode().isEmpty())
			drugResult.setNdcDrugCode(reqDrug.getNdcDrugCode());
		if (reqDrug.getScientificCode() != null && !reqDrug.getScientificCode().isEmpty())
			drugResult.setScientificCode(reqDrug.getScientificCode());
		if (reqDrug.getDispensedQuantity() != null)
			drugResult.setDispensedQuantity(reqDrug.getDispensedQuantity());
		if (reqDrug.getAmount() != null)
			drugResult.setAmount(reqDrug.getAmount());
		if (reqDrug.getDaysOfSupply() != null)
			drugResult.setDaysOfSupply(reqDrug.getDaysOfSupply());
		drugResult.setStatus(ServiceStatus.REJECTED.toString());
		drugResult.setErrors(rejectionsList);
		return drugResult;
	}

	private List<Error> populateRejectionError(DrugList reqDrug, String payerId,
			Optional<DrugServiceMetaData> drugServiceMetaData) {
		List<Error> rejectionsList = new ArrayList<>();
		if ((reqDrug.getNdcDrugCode() == null || reqDrug.getNdcDrugCode().isEmpty())
				&& (reqDrug.getScientificCode() == null || reqDrug.getScientificCode().isEmpty())) {
			Error rejectionReason = new Error();
			rejectionReason.setCode(DssRejectionCodes.DRUG_NOT_FOUND_REJECTIONCODE.value());
			rejectionReason.setDescription(commonRejectionReasonRepo
					.findByRejectionCode(DssRejectionCodes.DRUG_NOT_FOUND_REJECTIONCODE.value())
					.replace(DRUGNOTOFOUND_STR, "undefined"));
			rejectionsList.add(rejectionReason);
		} else if (!isSfdaDrug(reqDrug, drugServiceMetaData)) {
			Error rejectionReason = new Error();
			rejectionReason.setCode(DssRejectionCodes.DRUG_NOT_FOUND_REJECTIONCODE.value());
			rejectionReason.setDescription(commonRejectionReasonRepo
					.findByRejectionCode(DssRejectionCodes.DRUG_NOT_FOUND_REJECTIONCODE.value())
					.replace(DRUGNOTOFOUND_STR,
							(reqDrug.getNdcDrugCode() != null && !reqDrug.getNdcDrugCode().isEmpty())
									? reqDrug.getNdcDrugCode()
									: reqDrug.getScientificCode()));
			rejectionsList.add(rejectionReason);

		} else if (reqDrug.getDispensedQuantity() == null || reqDrug.getDispensedQuantity() == new BigDecimal("0")) {
			Error rejectionReason = new Error();
			rejectionReason.setCode(DssRejectionCodes.QUANTITY_MANDATORY_REJECTIONCODE.value());
			rejectionReason.setDescription(commonRejectionReasonRepo
					.findByRejectionCode(DssRejectionCodes.QUANTITY_MANDATORY_REJECTIONCODE.value()));
			rejectionsList.add(rejectionReason);
		} else if (reqDrug.getAmount() == null) {
			Error rejectionReason = new Error();
			rejectionReason.setCode(DssRejectionCodes.AMOUNT_MANDATORY_REJECTIONCODE.value());
			rejectionReason.setDescription(commonRejectionReasonRepo
					.findByRejectionCode(DssRejectionCodes.AMOUNT_MANDATORY_REJECTIONCODE.value()));
			rejectionsList.add(rejectionReason);
		} else if (!skipDaysOfSupplyValidation(payerId) && (modulesUsingDaysOfSupplyAreActive(payerId)
				&& (reqDrug.getDaysOfSupply() == null || reqDrug.getDaysOfSupply().isBlank()))) {
			Error rejectionReason = new Error();
			rejectionReason.setCode(DssRejectionCodes.DAYSOFSUPPLY_MANDATORY_REJECTIONCODE.value());
			rejectionReason.setDescription(commonRejectionReasonRepo
					.findByRejectionCode(DssRejectionCodes.DAYSOFSUPPLY_MANDATORY_REJECTIONCODE.value())
					.replace(DRUGNOTOFOUND_STR,
							(reqDrug.getNdcDrugCode() != null && !reqDrug.getNdcDrugCode().isEmpty())
									? reqDrug.getNdcDrugCode()
									: reqDrug.getScientificCode()));
			rejectionsList.add(rejectionReason);
		}

		return rejectionsList;
	}

	private boolean isSfdaDrug(DrugList reqDrug, Optional<DrugServiceMetaData> drugServiceMetaData) {
		if (drugServiceMetaData.isPresent()) {
			List<DrugService> drugService = null;
			if (reqDrug.getNdcDrugCode() != null && !reqDrug.getNdcDrugCode().isBlank()) {
				drugService = drugServiceRepo.findByOtherCodesValueAndDrugListId(reqDrug.getNdcDrugCode(),
						drugServiceMetaData.get().getDrugListId());
			} else if (reqDrug.getScientificCode() != null && !reqDrug.getScientificCode().isEmpty()) {
				drugService = drugServiceRepo.findByScientificCodeAndDrugListId(reqDrug.getScientificCode(),
						drugServiceMetaData.get().getDrugListId());
			}
			if (drugService != null && !drugService.isEmpty())
				return true;
		}
		return false;
	}

	private boolean skipDaysOfSupplyValidation(String payerId) {
		Optional<PayerValidationConfiguration> config = payerValidationConfigurationRepo.findFirstByPayerId(payerId);
		return config.isPresent() && config.get().getToBeValidated().equals('0');
	}

	private boolean modulesUsingDaysOfSupplyAreActive(String payerId) {
		if ((modulesConfigurationRepo.findByIdAndIsEnabled(payerId, ScreeningModules.IDF.value().doubleValue()) != null
				&& modulesConfigurationRepo.findByIdAndIsEnabled(payerId,
						ScreeningModules.IDFQL.value().doubleValue()) != null)
				|| (modulesConfigurationRepo.findByIdAndIsEnabled(payerId,
						ScreeningModules.FDB.value().doubleValue()) != null
						&& modulesConfigurationRepo.findByIdAndIsEnabled(payerId,
								ScreeningModules.FDBQL.value().doubleValue()) != null)
				|| (modulesConfigurationRepo.findByIdAndIsEnabled(payerId,
						ScreeningModules.RTS.value().doubleValue()) != null)) {
			return true;
		}

		return false;
	}

	private DssRequest removeExcludedDrugs(DssRequest dssRequest, List<DrugList> excludedDrugs) {
		DssRequest editableRequest = new DssRequest(dssRequest);
		for (DrugList excludedDrug : excludedDrugs) {
			editableRequest.getDrugList().removeIf(e -> e.equals(excludedDrug));
		}
		return editableRequest;
	}

	private void addInitDrugResultsToDssResponse(DssRequest dssRequest, DssResponse dssResponse,
			List<Result> initDrugResults) {
		// Main Purpose of this Method Not Only To add InitDrugList To DSs Response ..
		// But also , to add them in same order that defined in the request ..
		int index = 0;
		for (DrugList reqDrug : dssRequest.getDrugList()) {
			// condition is added to check the Null pointer issue.
			if(initDrugResults != null && !initDrugResults.isEmpty()) {
			for (Result initDrugResult : initDrugResults) {
				/*
				 * if (((reqDrug.getNdcDrugCode() == null || reqDrug.getNdcDrugCode().isEmpty())
				 * && (initDrugResult.getNdcDrugCode() ==
				 * null||initDrugResult.getNdcDrugCode().isEmpty())) ||
				 * (reqDrug.getNdcDrugCode().equals(initDrugResult.getNdcDrugCode()))) {
				 */

				if ((reqDrug.getNdcDrugCode() == null || reqDrug.getNdcDrugCode().isEmpty())
						&& (reqDrug.getScientificCode() == null || reqDrug.getScientificCode().isEmpty())
						&& (initDrugResult.getNdcDrugCode() == null || initDrugResult.getNdcDrugCode().isEmpty())
						&& (initDrugResult.getScientificCode() == null
								|| initDrugResult.getScientificCode().isEmpty())) {

					dssResponse.getResults().add(index, initDrugResult);
					break;
				} else if (reqDrug.getNdcDrugCode() != null && reqDrug.getNdcDrugCode().equals(initDrugResult.getNdcDrugCode())

						&& reqDrug.getScientificCode() != null
						&& reqDrug.getScientificCode().equals(initDrugResult.getScientificCode())) {
					dssResponse.getResults().add(index, initDrugResult);
					break;
				} else if (reqDrug.getNdcDrugCode() != null && reqDrug.getNdcDrugCode().equals(initDrugResult.getNdcDrugCode())) {
					dssResponse.getResults().add(index, initDrugResult);
					break;
				}

			}
			}
			index = index + 1;
		}

	}

	private void modifyDssResponseStatus(DssResponse dssResponse) {
		List<String> servicesStatusList = dssResponse.getResults().stream().map(Result::getStatus)
				.collect(Collectors.toList());
		if (servicesStatusList.stream().distinct().count() > 1)
			dssResponse.setStatus(RequestStatus.PARTIAL_APPROVED.value());
	}

	private void setDssResponseErrors(DssResponse dssReaponse) {
		List<String> responseErrors = new ArrayList<>();
		for (Result drugResult : dssReaponse.getResults()) {
			if (drugResult.getStatus().equals(ServiceStatus.REJECTED.toString())) {
				for (Error rejectionReason : drugResult.getErrors()) {
					responseErrors.add(rejectionReason.getDescription());
				}
			}
		}
		dssReaponse.setErrors(responseErrors);
	}

	private DssResponse setDssResponse(DssRequest dssRequest, List<Result> initDrugResults) {
		DssResponse dssResponse = new DssResponse();
		dssResponse.setRequestId(dssRequest.getRequestId());
		dssResponse.setStatus(RequestStatus.REJECTED.value());
		dssResponse.setResults(initDrugResults);
		dssResponse.setHttpStatusCode(HttpStatus.OK.value());
		dssResponse.setTransactionLogId(dssRequest.getTransactionLogId());
		return dssResponse;
	}
}