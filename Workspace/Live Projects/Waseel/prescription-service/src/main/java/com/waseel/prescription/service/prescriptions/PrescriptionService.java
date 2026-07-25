package com.waseel.prescription.service.prescriptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationRequestModel;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationResponseModel;
import com.waseel.prescription.model.common.MemberInfoModel;
import com.waseel.prescription.model.common.PhysicianModel;
import com.waseel.prescription.model.dss.DssCancellationRequest;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.CustomizationRequestDenialCodes;
import com.waseel.prescription.model.enums.CustomizationRequestDetailSplitParameters;
import com.waseel.prescription.model.enums.PbmRequestType;
import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ValidationType;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionRequestModel;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.model.pbmpayerapis.PolicyInformationModel;
import com.waseel.prescription.model.policyconsumption.CancellAndDispensePolicyRequestModel;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.CustomizationRequests;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.EligibilityValidationModel;
import com.waseel.prescription.model.prescription.PayerMemberPhysicianInfoModel;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.model.prescription.RejectionReasons;
import com.waseel.prescription.model.prescription.ServiceRejectionModel;
import com.waseel.prescription.persist.businessrules.PayerConfiguration;
import com.waseel.prescription.persist.hira.SwitchAccount;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.EligibleDssPolicy;
import com.waseel.prescription.persist.prescriptionservice.MappingPayerId;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRejection;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.businessrules.PayerConfigurationRepository;
import com.waseel.prescription.repository.hira.SwitchAccountRepository;
import com.waseel.prescription.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.EligibleDssPolicyRepository;
import com.waseel.prescription.repository.prescriptionservice.MappingPayerIdRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberPolicyUsageRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceRejectionRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.service.clienthandler.PbmPayerApisRestHandler;
import com.waseel.prescription.service.clienthandler.RestHandler;
import com.waseel.prescription.service.management.BusinessRuleService;
import com.waseel.prescription.service.management.SessionService;
import com.waseel.prescription.service.management.TransactionLogService;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.service.validation.TechnicalValidationService;
import com.waseel.prescription.specification.DiagnosisSpecification;
import com.waseel.prescription.specification.ServiceInfoSpecification;
import com.waseel.prescription.specification.ServiceRejectionSpecification;
import com.waseel.prescription.util.SourceTypeUtil;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class PrescriptionService {

	@Value(value = "${feature.toggle: false}")
	private boolean featureToggleEnabled;
	@Value(value = "${dss.feature.toggle: false}")
	private boolean skipDssToggleEnabled;
	@Autowired
	EligibleDssPolicyRepository eligibleDssPolicyRepository;
	@Autowired
	private NewPrescriptionService newPrescriptionService;
	@Autowired
	private TechnicalValidationService technicalValidationService;
	@Autowired
	private FollowUpPrescriptionService followUpPrescriptionService;
	@Autowired
	PrescriptionRequestRepository prescriptionRequestRepository;
	@Autowired
	private CancellationPrescriptionService cancellationPrescriptionService;
	@Autowired
	private MemberInfoRepository memberInfoRepository;
	@Autowired
	private PayerMemberInfoService payerMemberInfoService;
	@Autowired
	private PhysicianRepository physicianRepository;
	@Autowired
	private PayerConfigurationRepository payerConfigurationRepository;
	@Autowired
	private DiagnosisSpecification diagnosisSpecification;
	@Autowired
	private ServiceInfoSpecification serviceInfoSpecification;
	@Autowired
	private ServiceRejectionSpecification serviceRejectionSpecification;
	@Autowired
	TransactionLogService transactionLogService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private BusinessRuleService businessRuleService;
	@Autowired
	private MemberPolicyUsageRepository memberPolicyUsageRepository;
	@Autowired
	private ServiceInfoRepository serviceInfoRepository;
	@Autowired
	private ServiceResponseInfoRepository serviceResponseInfoRepository;
	@Autowired
	private ServiceRejectionRepository serviceRejectionRepository;
	@Autowired
	private RestHandler restHandler;
	@Autowired
	private MapperService mapperService;
	@Autowired
	private PrescriptionRejectionRepository prescriptionRejectionRepository;
	@Autowired
	private EPrescriptionApprovalService prescriptionApprovalService;
	@Autowired
	private SwitchAccountRepository switchAccountRepository;
	@Autowired
	private DrugServiceRepository drugServiceRepository;
	@Autowired
	private MappingPayerIdRepository mappingPayerIdRepository;
	@Autowired
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;
	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@Autowired
	private FetchBenefitCodeService fetchBenefitCodeService;

	private static final String E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING = "EPrescriptionReferenceNumber is not found or exists.";
	private static final String INVALID_DRUGLIST_ID = "Invalid Effective DrugList Id";

	public PrescriptionResponseModel manageNewOrFollowUpPrescriptionRequest(
			PrescriptionRequestModel prescriptionRequest, ContentCachingRequestWrapper requestWrapper,
			String headerOrigin, String payerId) throws PrescriptionException {
		technicalValidationService.validateDiagnosisType(prescriptionRequest.getDiagnosisCodes(), requestWrapper);
		boolean isFollowUpRequest = technicalValidationService
				.identifyNewFollowUpRequest(prescriptionRequest.getePrescriptionReferenceNumber());
		String sourceType = SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(headerOrigin);
		PrescriptionResponseModel response;
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		setTotalPriceAndPolicyConsumptionDrugList(prescriptionRequest);

		// VALIDATE REQUESTED DRUGS WITH EFFECTIVE DRUG LIST
		validatePrescriptionRequest(prescriptionRequest, isFollowUpRequest, requestWrapper);
		if (isFollowUpRequest) {
			// FOLLOWUP
			PrescriptionRequest preRequest = technicalValidationService.validateFollowUpRequest(prescriptionRequest,
					requestWrapper, providerId);
			response = followUpPrescriptionService.manageFollowUpRequest(prescriptionRequest, requestWrapper,
					preRequest, providerId, sourceType);
		} else {
			// NEW
			String requestId = UUID.randomUUID().toString();
			technicalValidationService.validateFields(requestWrapper, prescriptionRequest, providerId);
			response = newPrescriptionService.newSubmissionController(prescriptionRequest, requestWrapper, requestId,
					providerId, sourceType, payerId);
		}
		return response;
	}

	private void validatePrescriptionRequest(PrescriptionRequestModel prescriptionRequest, Boolean isFollowUp,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		Long drugListId = getDrugListId();
		String ePrescriptionReferenceNumber = Boolean.TRUE.equals(isFollowUp)
				? prescriptionRequest.getePrescriptionReferenceNumber()
				: "";

		Set<String> drugCodeSet = new HashSet<>();
		Set<String> scientificCodeSet = new HashSet<>();

		Set<String> drugIds = new HashSet<>();
		// IDENTIFY NON MATCHING DRUG LIST ID BASED ON DRUG
		prescriptionRequest.getDrugList().stream().forEach(drug -> {
			if (!drug.getDrugListId().equals(drugListId.toString())) {
				drugIds.add(drug.getDrugListId());
				if (drug.getDrugCode() != null) {
					drugCodeSet.add(drug.getDrugCode());
				} else {
					scientificCodeSet.add(drug.getScientificCode());
				}
			}
		});

		if (!drugIds.isEmpty() && (drugIds.size() > 1 || !drugIds.contains(drugListId.toString()))) {
			String errorMsg = prepareInvalidDrugMsg(drugCodeSet, scientificCodeSet, " has invalid DrugList Id");
			throw new PrescriptionException(technicalValidationService
					.populateInvalidPrescriptionResponse(requestWrapper, errorMsg, ePrescriptionReferenceNumber));
		}
		Optional<List<DrugService>> serviceOp = drugServiceRepository.findByDrugListId(drugListId);
		if (!serviceOp.isPresent()) {
			throw new PrescriptionException(technicalValidationService.populateInvalidPrescriptionResponse(
					requestWrapper, "Drug Lists not Found.", ePrescriptionReferenceNumber));
		}
		List<DrugService> services = serviceOp.get();
		List<String> nonMatchingCodes = new ArrayList<>();
		drugCodeSet.clear();
		scientificCodeSet.clear();

		// IDENTIFY NON MATCHING DRUGS BASED ON EFFECTIVE DRUG LIST
		findNonMatchingDrugs(nonMatchingCodes, services, prescriptionRequest.getDrugList(), drugCodeSet,
				scientificCodeSet);

		if (!nonMatchingCodes.isEmpty()) {
			String errorMsg = prepareInvalidDrugMsg(drugCodeSet, scientificCodeSet, " not found.");
			throw new PrescriptionException(technicalValidationService
					.populateInvalidPrescriptionResponse(requestWrapper, errorMsg, ePrescriptionReferenceNumber));
		}
	}

	private String prepareInvalidDrugMsg(Set<String> drugCodeSet, Set<String> scientificCodeSet, String error) {
		String errorDrgMsg = !drugCodeSet.isEmpty() ? ("DrugCodes: " + drugCodeSet + " ") : "";
		String errorSciMsg = !scientificCodeSet.isEmpty() ? "ScientitificCodes: " + scientificCodeSet : "";
		return (!errorDrgMsg.isEmpty() ? errorDrgMsg : "") + (!errorSciMsg.isEmpty() ? errorSciMsg : "") + error;
	}

	private void findNonMatchingDrugs(List<String> nonMatchingCodes, List<DrugService> services,
			List<DrugList> dispensableDrugs, Set<String> drugCodeSet, Set<String> scientificCodeSet) {
		for (DrugList drug : dispensableDrugs) {
			boolean found = false;
			for (DrugService service : services) {
				if ((drug.getDrugCode() != null && service.getOtherCodesValue() != null
						&& service.getOtherCodesValue().equals(drug.getDrugCode()))
						|| (drug.getScientificCode() != null && service.getScientificCode() != null
								&& service.getScientificCode().equals(drug.getScientificCode()))) {
					found = true;
					break;
				}
			}
			if (!found) {
				if (drug.getDrugCode() != null) {
					drugCodeSet.add(drug.getDrugCode());
				} else {
					scientificCodeSet.add(drug.getScientificCode());
				}
				nonMatchingCodes.add(
						StringUtils.isNotBlank(drug.getDrugCode()) ? drug.getDrugCode() : drug.getScientificCode());
			}
		}
	}

	private Long getDrugListId() {
		return drugServiceMetaDataRepository.getActiveDrugServiceList(new Date()).orElse(0L);
	}

	public PrescriptionCancellationResponseModel managePrescriptionCancellationRequest(
			PrescriptionCancellationRequestModel prescriptionCancellationRequestModel,
			ContentCachingRequestWrapper requestWrapper, String headerOrigin) throws PrescriptionException {
		String sourceType = SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(headerOrigin);
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		Timestamp sendingDateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		String eprescriptionReferenceNumber = prescriptionCancellationRequestModel.getePrescriptionReferenceNumber();
		PrescriptionRequest prescriptionRequest = technicalValidationService.validateCancellationRequest(
				prescriptionCancellationRequestModel, requestWrapper, providerId, sourceType);
		prescriptionRequest.setSendDateTime(sendingDateTime);
		boolean requiresOnlyMedicalCheck = false;
		if (featureToggleEnabled) {
			return manageDssResponse(eprescriptionReferenceNumber, prescriptionRequest, requestWrapper,
					requiresOnlyMedicalCheck);
		}
		return policyConsumptionCheck(prescriptionRequest, prescriptionRequest.getPayerId(), providerId,
				eprescriptionReferenceNumber, requestWrapper, sendingDateTime, requiresOnlyMedicalCheck);
	}

	private PrescriptionCancellationResponseModel policyConsumptionCheck(PrescriptionRequest prescriptionRequest,
			String payerId, String providerId, String ePrescriptionReferenceNumber,
			ContentCachingRequestWrapper requestWrapper, Timestamp sendingDateTime, boolean requiresOnlyMedicalCheck)
			throws PrescriptionException {
		String requestId = prescriptionRequest.getRequestId();
		Optional<MemberInfo> memberOpt = memberInfoRepository.findByRequestId(requestId);
		if (memberOpt.isPresent()) {
			String idNumber = String.valueOf(memberOpt.get().getIdNumber());
			PolicyResponseModel policyResponseModel = businessRuleService.policyConsumptionCheckForCancellation(
					idNumber,
					new CancellAndDispensePolicyRequestModel(
							fetchBenefitCodeService.fetchBenefitCodeByRequestId(requestId),
							prescriptionRequest.getCaseType(), payerId, requestId, null, providerId,
							RequestType.CANCELLATION.value()));
			if (null != policyResponseModel) {
				if (StringUtils.isNotBlank(policyResponseModel.getStatus())
						&& policyResponseModel.getStatus().equals(PolicyConsumptionStatus.APPROVED.getValue())) {
					populateMemberPolicyUsage(payerId, providerId, idNumber, policyResponseModel,
							ePrescriptionReferenceNumber, RequestType.CANCELLATION.name());
					return manageDssResponse(ePrescriptionReferenceNumber, prescriptionRequest, requestWrapper,
							requiresOnlyMedicalCheck);
				} else {
					manageInvalidOrRejectedPrescription(sendingDateTime, requestId, policyResponseModel);
					return populateInvalidCancellationResponse(policyResponseModel.getStatusDescription(),
							ePrescriptionReferenceNumber, policyResponseModel.getStatus());
				}
			} else {
				return populateInvalidCancellationResponse("Policy details not found.", ePrescriptionReferenceNumber,
						PolicyConsumptionStatus.INVALID.getValue());
			}
		} else {
			return populateInvalidCancellationResponse("RequestId does not exists.", ePrescriptionReferenceNumber,
					PolicyConsumptionStatus.INVALID.getValue());
		}
	}

	private PrescriptionCancellationResponseModel manageDssResponse(String ePrescriptionReferenceNumber,
			PrescriptionRequest prescriptionRequest, ContentCachingRequestWrapper requestWrapper,
			boolean requiresOnlyMedicalCheck) throws PrescriptionException {
		DssResponse dssResponse = null;
		if (!skipDssToggleEnabled) {
			dssResponse = restHandler.handleCancelPrescriptionRequest(
					new DssCancellationRequest(prescriptionRequest.getRequestId()), requestWrapper,
					ePrescriptionReferenceNumber);
		} else if (skipDssToggleEnabled) {
			ResponseEntity<MemberDemographicDataResponseModel> demographicData = payerMemberInfoService
					.getMemberDemographicData(prescriptionRequest.getMemberInfo().getIdNumber());
			List<String> policies = new ArrayList<>();
			if (demographicData != null && demographicData.getBody() != null) {
				policies = demographicData.getBody().getPolicyInformation().stream()
						.map(PolicyInformationModel::getPolicyNumber).collect(Collectors.toList());
			}
			Optional<List<EligibleDssPolicy>> eligiblePolicy = eligibleDssPolicyRepository.findByPolicyNumber(policies);
			if (eligiblePolicy.isPresent() && !eligiblePolicy.get().isEmpty()) {
				dssResponse = restHandler.handleCancelPrescriptionRequest(
						new DssCancellationRequest(prescriptionRequest.getRequestId()), requestWrapper,
						ePrescriptionReferenceNumber);
			} else {
				dssResponse = new DssResponse(0, 200);
			}
		}
		if (null != dssResponse && null != dssResponse.getCode() && dssResponse.getCode() == HttpStatus.OK.value()) {
			PrescriptionCancellationResponseModel responseModel = cancellationPrescriptionService
					.managePrescriptionCancellationRequest(ePrescriptionReferenceNumber, prescriptionRequest,
							requestWrapper, requiresOnlyMedicalCheck);
			if (responseModel != null && responseModel.getStatus().equalsIgnoreCase("Cancelled")) {
				return manageEPrescriptionApproval(ePrescriptionReferenceNumber, responseModel,
						mapperService.getEPrescriptionRequestModelFromPrescriptionRequest(prescriptionRequest,
								PbmRequestType.CANCELLATION.value(), null));
			}
			return responseModel;
		}
		return mapperService.createPrescriptionCancellationResponse(dssResponse, ePrescriptionReferenceNumber);
	}

	private PrescriptionCancellationResponseModel manageEPrescriptionApproval(String ePrescriptionReferenceNumber,
			PrescriptionCancellationResponseModel responseModel, EPrescriptionRequestModel ePrescriptionRequestModel) {
		PbmRequestType requestType = PbmRequestType.CANCELLATION;
		EPrescriptionResponseModel ePrescriptionResponseModel = prescriptionApprovalService
				.checkEPrescriptionApproval(ePrescriptionRequestModel, requestType);
		if (ePrescriptionResponseModel != null
				&& StringUtils.isBlank(ePrescriptionResponseModel.getApprovalReferenceNumber())) {
			return mapperService.createPrescriptionCancellationResponseFromEPrescriptionResponseModel(
					ePrescriptionResponseModel, ePrescriptionReferenceNumber);
		}
		return responseModel;
	}

	private PrescriptionCancellationResponseModel populateInvalidCancellationResponse(String msg,
			String ePrescriptionReferenceNumber, String status) {
		int httpStatusCode = HttpStatus.OK.value();
		PrescriptionCancellationResponseModel responseModel = new PrescriptionCancellationResponseModel();
		if (status.equals(PolicyConsumptionStatus.INVALID.getValue())) {
			httpStatusCode = HttpStatus.BAD_REQUEST.value();
		}
		if (status.equals(PolicyConsumptionStatus.FAILED.getValue())) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
		}
		responseModel.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		responseModel.setCanCancel(false);
		responseModel.setCanFollowUp(false);
		responseModel.setStatus(status);
		responseModel.setStatusDescription(msg);
		responseModel.setHttpStatusCode(httpStatusCode);
		return responseModel;
	}

	public PayerMemberPhysicianInfoModel getPayerMemberPhysicianDetails(String ePrescriptionReferenceNumber,
			ContentCachingRequestWrapper requestWrapper, String payerId, String headerOrigin)
			throws PrescriptionException {
		PrescriptionRequest request = getPrescriptionRequestObject(ePrescriptionReferenceNumber, requestWrapper);
		PayerMemberPhysicianInfoModel model = new PayerMemberPhysicianInfoModel();
		model.setStatus(request.getStatusCode());
		model.setStatusDescription(request.getStatusDescription());
		String requestId = request.getRequestId();
		model.setMemberInfoModel(setMemberInfoModel(requestId));
		model.setPhysicianModel(setPhysicianInfoModel(requestId));
		String prescriptionPayerId = fetchPayerIdByMappedPayerId(request.getPayerId());
		model.setPayerName(getPayerName(prescriptionPayerId));
		model.setPayerId(prescriptionPayerId);
		model.setCaseType(request.getCaseType());
		setTotalNetAndTotalPatientShare(request, model);
		String providerId = request.getProviderId();
		model.setProviderId(providerId);
		model.setProviderName(fetchProviderName(providerId));
		TransactionLog transactionLog = transactionLogService.addTransaction(RequestType.DETAIL_INQUIRY, payerId,
				providerId, requestId, ePrescriptionReferenceNumber,
				SourceTypeUtil.getSourceTypeBasedOnHeaderOrigin(headerOrigin));
		if (null != transactionLog && null != transactionLog.getTransactionLogId()
				&& !transactionLog.getTransactionLogId().toString().isEmpty()) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
		}
		return model;
	}

	private String fetchProviderName(String providerId) {
		String providerName = "";
		Optional<SwitchAccount> switchAccountOpt = switchAccountRepository
				.findBySwitchAccountIdAndIsEnabledAndCategory(new BigDecimal(providerId), "1", "PROVIDER");
		if (switchAccountOpt.isPresent()) {
			SwitchAccount switchAccount = switchAccountOpt.get();
			providerName = switchAccount.getName();
		}
		return providerName;
	}

	private Optional<PrescriptionRequest> getPrescriptionRequestBasedOnProviderOrPatientReferenceNum(
			String ePrescriptionReferenceNumber) {
		// AS of now it's commented once get confirmation about privileges, will change
		// this
		// String providerId =
		// UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		// if (!StringUtils.isBlank(providerId) && !providerId.equalsIgnoreCase("null"))
		// {
		// return prescriptionRequestRepository
		// .findByePrescriptionReferenceNumberAndProviderId(ePrescriptionReferenceNumber,
		// providerId);
		// } else {
		// List<String> patientEPrescriptionReferenceNum =
		// getPatientReferenceNumbersFromToken();
		// if (patientEPrescriptionReferenceNum != null &&
		// !patientEPrescriptionReferenceNum.isEmpty()
		// && patientEPrescriptionReferenceNum.contains(ePrescriptionReferenceNumber)) {
		// return
		// prescriptionRequestRepository.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		// }
		// }
		// return Optional.empty();
		return prescriptionRequestRepository.findByePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
	}

	private List<String> getPatientReferenceNumbersFromToken() {
		List<SimpleGrantedAuthority> patientAuthorities = UserInfoUtil
				.getAuthority(SecurityContextHolder.getContext().getAuthentication());
		return patientAuthorities.stream().map(GrantedAuthority::getAuthority)
				.filter(authority -> authority.startsWith("prescription-service|"))
				.map(authority -> authority.substring("prescription-service|".length())).collect(Collectors.toList());
	}

	public PrescriptionRequest getPrescriptionRequestObject(String ePrescriptionReferenceNumber,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		Optional<PrescriptionRequest> prescriptionRequestOptional = getPrescriptionRequestBasedOnProviderOrPatientReferenceNum(
				ePrescriptionReferenceNumber);
		if (prescriptionRequestOptional.isPresent()) {
			return prescriptionRequestOptional.get();
		}
		throw new PrescriptionException(technicalValidationService.populateInvalidPrescriptionResponse(requestWrapper,
				E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING, ePrescriptionReferenceNumber));
	}

	private void setTotalNetAndTotalPatientShare(PrescriptionRequest request, PayerMemberPhysicianInfoModel model) {
		BigDecimal payerShare = request.getPayerShare();
		BigDecimal patientShare = request.getPatientShare();
		model.setTotalNet(payerShare != null ? payerShare.setScale(2, RoundingMode.HALF_UP) : new BigDecimal(0));
		model.setTotalPatientShare(
				patientShare != null ? patientShare.setScale(2, RoundingMode.HALF_UP) : new BigDecimal(0));
	}

	private String getPayerName(String payerId) {
		Optional<PayerConfiguration> payerConfigurationOptional = payerConfigurationRepository.findByPayerId(payerId);
		if (payerConfigurationOptional.isPresent()) {
			PayerConfiguration payerConfiguration = payerConfigurationOptional.get();
			return payerConfiguration.getPayerName();
		}
		return null;
	}

	private PhysicianModel setPhysicianInfoModel(String requestId) {
		Optional<Physician> physicianOptional = physicianRepository.findByRequestId(requestId);
		if (physicianOptional.isPresent()) {
			Physician physician = physicianOptional.get();
			PhysicianModel model = new PhysicianModel();
			model.setPhysicianName(physician.getPhysicianName());
			model.setPhysicianLicenseNumber(physician.getPhysicianLicenseNumber());
			model.setPhysicianCategory(physician.getPhysicianCategory());
			model.setPhysicianSpeciality(physician.getPhysicianSpeciality());
			return model;
		}
		return null;
	}

	private MemberInfoModel setMemberInfoModel(String requestId) {
		Optional<MemberInfo> memberInfoOptional = memberInfoRepository.findByRequestId(requestId);
		if (memberInfoOptional.isPresent()) {
			MemberInfo memberInfo = memberInfoOptional.get();
			MemberInfoModel model = new MemberInfoModel();
			model.setAge(payerMemberInfoService.patientAgeConverter(memberInfo.getDob()));
			model.setDob(new SimpleDateFormat("dd/MM/yyyy").format(memberInfo.getDob()));
			model.setMemberName(memberInfo.getMemberName());
			model.setGender(memberInfo.getGender());
			model.setIdNumber(memberInfo.getIdNumber());
			setMemberNationality(memberInfo.getNationality(), model);
			return model;
		}
		return null;
	}

	private void setMemberNationality(String nationality, MemberInfoModel model) {
		if (StringUtils.isBlank(nationality)) {
			nationality = fetchMemberNationality(model.getIdNumber());
		}
		model.setNationality(nationality);
	}

	private String fetchMemberNationality(Long idNumber) {
		ResponseEntity<MemberDemographicDataResponseModel> memberDemographicDataResponseEntity = pbmPayerApisRestHandler
				.sendRequestToGetMemberDemographicData(idNumber);
		if (null != memberDemographicDataResponseEntity
				&& memberDemographicDataResponseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
			MemberDemographicDataResponseModel memberDemographicData = memberDemographicDataResponseEntity.getBody();
			if (null != memberDemographicData) {
				return memberDemographicData.getNationality();
			}
		}
		return "";
	}

	private PrescriptionRequest getPrescriptionRequestObject(String ePrescriptionReferenceNumber)
			throws PrescriptionException {
		Optional<PrescriptionRequest> prescriptionRequestOptional = getPrescriptionRequestBasedOnProviderOrPatientReferenceNum(
				ePrescriptionReferenceNumber);
		if (prescriptionRequestOptional.isPresent()) {
			return prescriptionRequestOptional.get();
		}
		throw new PrescriptionException(E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING);
	}

	public Page<DiagnosisCodes> getDiagnosis(String ePrescriptionReferenceNumber, int pageNumber, int recordSize)
			throws PrescriptionException {
		PrescriptionRequest request = getPrescriptionRequestObject(ePrescriptionReferenceNumber);
		String requestId = request.getRequestId();
		DiagnosisCodes diagnosisCodes = new DiagnosisCodes(requestId);
		return diagnosisSpecification.findByRequestIdWithPagination(pageNumber, recordSize, diagnosisCodes);
	}

	public Iterable<DrugList> getDrugs(String ePrescriptionReferenceNumber, Integer pageNumber, Integer recordSize,
			boolean isPaginated) throws PrescriptionException {
		PrescriptionRequest request = getPrescriptionRequestObject(ePrescriptionReferenceNumber);
		String requestId = request.getRequestId();
		DrugList drugList = new DrugList(requestId);
		Iterable<DrugList> result = serviceInfoSpecification.findByRequestIdWithPagination(pageNumber, recordSize,
				drugList, isPaginated);
		List<ServiceRejection> rejectedDrugs = serviceRejectionRepository
				.getAllRejectionsByRequestIdAndDenialCode(requestId);
		List<String> rejectedDrugsStr = new ArrayList<>();
		if (rejectedDrugs != null && !rejectedDrugs.isEmpty()) {
			List<String> rejectedDrugsList = rejectedDrugs.stream().map(drug -> {
				return drug.getDrugCode() != null ? drug.getDrugCode() : drug.getScientificCode();
			}).collect(Collectors.toList());

			Map<String, List<String>> groupedByDrugCode = rejectedDrugs.stream()
					.filter(service -> service.getDrugCode() != null)
					.collect(Collectors.groupingBy(ServiceRejection::getDrugCode,
							Collectors.mapping(ServiceRejection::getDenialCode, Collectors.toList())));
			// GROUP ALL BR AND DSS REJECTED DURG CODE
			List<String> brAndDssRejectedDrugList = groupedByDrugCode.entrySet().stream()
					.filter(entry -> (entry.getValue().stream().anyMatch(code -> code.startsWith("BR"))
							|| entry.getValue().stream().anyMatch(code -> code.startsWith("PYR")))
							&& entry.getValue().stream()
									.anyMatch(code -> code.startsWith("FDB") || code.startsWith("PC")
											|| code.startsWith("CPREF390") || code.startsWith("IDF")))
					.map(Map.Entry::getKey).collect(Collectors.toList());
			// GROUP ALL BR REJECTED DURG CODE
			List<String> brRejectedDrugList = groupedByDrugCode.entrySet().stream()
					.filter(entry -> entry.getValue().stream().anyMatch(code -> code.startsWith("BR"))
							|| entry.getValue().stream().anyMatch(code -> code.startsWith("PYR")))
					.map(Map.Entry::getKey).collect(Collectors.toList());

			rejectedDrugsList.removeAll(brRejectedDrugList);
			rejectedDrugsList.removeAll(brAndDssRejectedDrugList);

			rejectedDrugsStr.addAll(rejectedDrugsList);
		}
		result.forEach(drug -> {
			Boolean isOverride = false;
			DrugService drugService = null;
			if (!StringUtils.isBlank(drug.getDrugCode())
					&& !drug.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())) {
				drugService = getDrugDescriptionByDrugCode(drug.getDrugCode());
				if (drugService != null)
					drug.setDrugName(drugService.getDisplay());
				if (rejectedDrugs != null && !rejectedDrugs.isEmpty()
						&& rejectedDrugsStr.contains(drugService.getOtherCodesValue())) {
					isOverride = true;
				}
			} else if (!StringUtils.isBlank(drug.getScientificCode())) {
				drugService = getDrugServiceDetailsByScientificCode(drug.getScientificCode());
				drug.setDrugCode(null);
				if (rejectedDrugs != null && !rejectedDrugs.isEmpty()
						&& rejectedDrugsStr.contains(drugService.getOtherCodesValue())
						|| rejectedDrugsStr.contains(drugService.getScientificCode())) {
					isOverride = true;
				}
			}
			if (drugService != null) {
				drug.setScientificName(addWhiteSpaceAfterComma(drugService.getIngredients()));
				drug.setDosageForm(drugService.getDosageForm());
				drug.setStrength(drugService.getStrength());
				drug.setStrengthUnit(drugService.getStrengthUnit());
				drug.setRoaSuggested(drugService.getRoaSuggested());
				drug.setIsOverridableByProvider(isOverride);
			}
		});
		return result;
	}

	private String addWhiteSpaceAfterComma(String inputString) {
		if (!StringUtils.isBlank(inputString)) {
			return inputString.replaceAll(",(?!\\s)", ", ");
		}
		return inputString;
	}

	private DrugService getDrugDescriptionByDrugCode(String drugCode) {
		return drugServiceRepository.findFirstByOtherCodesValue(drugCode).orElse(null);
	}

	private DrugService getDrugServiceDetailsByScientificCode(String scientificCode) {
		return drugServiceRepository.findByScientificCode(scientificCode).orElse(null);
	}

	public List<ServiceRejectionModel> getDrugsMedicalOrBusinessValidation(String ePrescriptionReferenceNumber,
			String category) throws PrescriptionException {
		PrescriptionRequest request = getPrescriptionRequestObject(ePrescriptionReferenceNumber);
		String requestId = request.getRequestId();
		String payerId = fetchPayerIdFromMappingPayerIdConfig(request.getPayerId());
		List<ServiceRejectionModel> serviceRejectionModels = new ArrayList<>();
		List<RejectionReasons> rejectionReasons = serviceRejectionRepository
				.fetchByRequestIdAndIsModifiedByPayer(requestId, false);
		List<CustomizationRequests> customizationRequests = serviceRejectionRepository.fetchByIsDeletedAndPayerId(false,
				payerId);
		rejectionReasons.stream().forEach(serviceRejectionReason -> {
			ServiceRejectionModel serviceRejectionModel = new ServiceRejectionModel();
			String denialCode = serviceRejectionReason.getDenialCode();
			String rejectionReason = serviceRejectionReason.getRejectionReason();
			String drugCode = serviceRejectionReason.getDrugCode();
			serviceRejectionModel.setDenialCode(denialCode);
			serviceRejectionModel.setDrugCode(drugCode);
			serviceRejectionModel.setDrugName(serviceRejectionReason.getDrugName());
			if (null != customizationRequests && !customizationRequests.isEmpty()) {
				customizationRequests.stream()
						.filter(customizationRequest -> customizationRequest.getDrugCode().equals(drugCode))
						.forEach(customizationRequest -> setCustomizableValueForDrug(denialCode,
								customizationRequest.getIsCustomizable(), customizationRequest.getKeyValue(),
								serviceRejectionModel, rejectionReason));
			}
			serviceRejectionModel.setRejectionReason(rejectionReason);
			serviceRejectionModel.setScientificCode(serviceRejectionReason.getScientificCode());
			serviceRejectionModel.setScientificName(serviceRejectionReason.getScientificName());
			serviceRejectionModels.add(serviceRejectionModel);
		});
		List<ServiceRejectionModel> rejections = new ArrayList<>();
		if (StringUtils.isNotBlank(category) && category.equals(ValidationType.MEDICAL.value())) {
			addMedicalRejections(serviceRejectionModels, rejections);
		} else if (StringUtils.isNotBlank(category) && category.equals(ValidationType.BUSINESS.value())) {
			addBusinessRejections(serviceRejectionModels, rejections);
			List<PrescriptionRejection> prescriptionRejections = prescriptionRejectionRepository
					.findByRequestIdAndShowUnderBusinessValidation(requestId, true);
			prescriptionRejections.stream().forEach(prescriptionRejection -> {
				ServiceRejectionModel rejectionModel = new ServiceRejectionModel();
				rejectionModel.setDenialCode(prescriptionRejection.getDenialCode());
				rejectionModel.setRejectionReason(prescriptionRejection.getRejectionReason());
				rejectionModel.setDrugName("-");
				rejections.add(rejectionModel);
			});
		}
		return rejections;
	}

	private String fetchPayerIdFromMappingPayerIdConfig(String payerId) {
		return mappingPayerIdRepository.findByMapperPayerIdAndIsEnabled(payerId, true).map(MappingPayerId::getPayerId)
				.orElse(payerId);
	}

	private void setCustomizableValueForDrug(String denialCode, String customizable, String keyValue,
			ServiceRejectionModel serviceRejectionModel, String rejectionReason) {
		String value = "";
		// Drug to diagnosis indication
		if (denialCode.equals(CustomizationRequestDenialCodes.IDF_CPINDI001.name())
				|| denialCode.equals(CustomizationRequestDenialCodes.FDB_CPINDI001.name())
				|| denialCode.equals(CustomizationRequestDenialCodes.PC_CPINDI001.name())) {
			value = rejectionReason.split(CustomizationRequestDetailSplitParameters.CODE_PARAMETER.value())[1].strip();
		}
		// Drug to diagnosis contra-indication
		if (denialCode.equals(CustomizationRequestDenialCodes.FDB_CPINDC001.name())
				|| denialCode.equals(CustomizationRequestDenialCodes.IDF_CPINDC001.name())
				|| denialCode.equals(CustomizationRequestDenialCodes.PC_CPINDC001.name())) {
			value = rejectionReason.split(CustomizationRequestDetailSplitParameters.CODE_PARAMETER.value())[1].strip()
					.split(CustomizationRequestDetailSplitParameters.COMMA_PARAMETER.value())[0].strip();
		}
		// Drug to drug
		if (denialCode.equals(CustomizationRequestDenialCodes.FDB_CPDDI701.name())
				|| denialCode.equals(CustomizationRequestDenialCodes.IDF_CPDDI701.name())
				|| denialCode.equals(CustomizationRequestDenialCodes.PC_CPDDI701.name())) {
			value = StringUtils
					.substringBetween(rejectionReason, CustomizationRequestDetailSplitParameters.WITH_PARAMETER.value(),
							CustomizationRequestDetailSplitParameters.HAS_PARAMETER.value())
					.strip();
		}
		// duplicate therapy
		if (denialCode.equals(CustomizationRequestDenialCodes.FDB_CPTDE0001.name())
				|| denialCode.equals(CustomizationRequestDenialCodes.PC_CPTDE0001.name())) {
			value = StringUtils.substringAfter(rejectionReason,
					CustomizationRequestDetailSplitParameters.AND_DRUG_PARAMETER.value());
		}
		// Gender
		if (denialCode.equals(CustomizationRequestDenialCodes.FDB_CPGNDR403.name())
				|| denialCode.equals(CustomizationRequestDenialCodes.PC_CPGNDR403.name())
				|| denialCode.equals(CustomizationRequestDenialCodes.IDF_CPGNDR403.name())) {
			value = rejectionReason.split(CustomizationRequestDetailSplitParameters.COLON_PARAMETER.value())[1].strip()
					.split(CustomizationRequestDetailSplitParameters.FOR_PARAMETER.value())[0].strip();
			value = StringUtils.startsWithIgnoreCase(CustomizationRequestDetailSplitParameters.FEMALE_PARAMETER.value(),
					value) ? CustomizationRequestDetailSplitParameters.MALE_PARAMETER.value()
							: CustomizationRequestDetailSplitParameters.FEMALE_PARAMETER.value();
		}
		markIsCustomizable(customizable, serviceRejectionModel, value, keyValue);
	}

	private void markIsCustomizable(String customizable, ServiceRejectionModel serviceRejectionModel, String value,
			String keyValue) {
		if (StringUtils.isNotBlank(value) && keyValue.equals(value)
				&& (customizable != null && StringUtils.isNotBlank(customizable))) {
			boolean isCustomizable = !customizable.equals("0");
			serviceRejectionModel.setIsCustomizable(isCustomizable);
		}
	}

	public Page<EligibilityValidationModel> getEligibilityValidationsOfPrescription(String ePrescriptionReferenceNumber,
			int pageNumber, int recordSize) throws PrescriptionException {
		Optional<PrescriptionRequest> requestOp = prescriptionRequestRepository
				.findByePrescriptionReferenceNumberAndStatusCode(ePrescriptionReferenceNumber,
						RequestStatusType.REJECTED.value());
		if (requestOp.isPresent()) {
			EligibilityValidationModel eligibilityValidationModel = new EligibilityValidationModel(
					requestOp.get().getRequestId());
			return serviceRejectionSpecification.findEligiblityValidaions(eligibilityValidationModel, pageNumber,
					recordSize);
		}
		throw new PrescriptionException(E_PRESCRIPTION_REFERENCE_NUMBER_NOT_EXIST_STRING);
	}

	private void populateMemberPolicyUsage(String payerId, String providerId, String idNumber,
			PolicyResponseModel policyResponseModel, String ePrescriptionReferenceNumber, String status) {
		MemberPolicyUsage memberPolicyUsage = new MemberPolicyUsage(payerId, providerId,
				policyResponseModel.getMemberId(), Long.valueOf(idNumber), policyResponseModel.getPolicyNumber(),
				policyResponseModel.getPolicyClass(), policyResponseModel.getPolicyBenefit(),
				null != policyResponseModel.getBenefitLimitValue() ? policyResponseModel.getBenefitLimitValue()
						: new BigDecimal(0),
				policyResponseModel.getBenefitLimitCurrency(),
				StringUtils.isNotBlank(policyResponseModel.getRemainingLimit())
						? new BigDecimal(policyResponseModel.getRemainingLimit())
						: new BigDecimal(0),
				policyResponseModel.getBenefitRemainingLimitCurrency(), ePrescriptionReferenceNumber, status);
		memberPolicyUsageRepository.save(memberPolicyUsage);
	}

	private void manageInvalidOrRejectedPrescription(Timestamp sendingDateTime, String requestId,
			PolicyResponseModel policyResponseModel) {
		Optional<PrescriptionRequest> prescriptionReqOpt = prescriptionRequestRepository.findByRequestId(requestId);
		if (prescriptionReqOpt.isPresent()) {
			PrescriptionRequest prescriptionRequest = prescriptionReqOpt.get();
			modifyPrescriptionRequest(sendingDateTime, prescriptionRequest, policyResponseModel);
			manageServiceInfo(requestId, policyResponseModel);
		}
		if (policyResponseModel.getStatus().equals(PolicyConsumptionStatus.FAILED.getValue())
				|| policyResponseModel.getStatus().equals(PolicyConsumptionStatus.INVALID.getValue())) {
			populatePrescriptionRejection(policyResponseModel.getDenialCode(),
					policyResponseModel.getDenialDescription(), null, requestId);
		}
	}

	private void modifyPrescriptionRequest(Timestamp sendingDateTime, PrescriptionRequest prescriptionRequest,
			PolicyResponseModel policyResponseModel) {
		String status = policyResponseModel.getStatus();
		prescriptionRequest.setSendDateTime(sendingDateTime);
		prescriptionRequest.setReceivedDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		prescriptionRequest.setStatusCode(status);
		prescriptionRequest.setStatusDescription(policyResponseModel.getStatusDescription());
		if (status.equals(PolicyConsumptionStatus.REJECTED.getValue())) {
			prescriptionRequest.setCanCancel(false);
			prescriptionRequest.setCanFollowUp(false);
			prescriptionRequest.setPatientShare(BigDecimal.ZERO);
			prescriptionRequest.setPayerShare(BigDecimal.ZERO);
		} else {
			prescriptionRequest.setCanCancel(true);
			prescriptionRequest.setCanFollowUp(true);
		}
		prescriptionRequest.setPatientShareCurrency(policyResponseModel.getPatientShareCurrency());
		prescriptionRequest.setPayerShareCurrency(policyResponseModel.getPayerShareCurrency());
		prescriptionRequestRepository.save(prescriptionRequest);
	}

	private void manageServiceInfo(String requestId, PolicyResponseModel policyResponseModel) {
		List<ServiceInfo> serviceList = serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId);
		serviceList.stream().forEach(service -> modifyServiceResponseInfo(requestId, service.getDrugCode(),
				service.getId(), policyResponseModel, service.getScientificCode()));
	}

	private void modifyServiceResponseInfo(String requestId, String drugCode, long serviceId,
			PolicyResponseModel policyResponseModel, String scientificCode) {
		Optional<ServiceResponseInfo> serviceResponseInfoOp = serviceResponseInfoRepository
				.findByRequestIdAndServiceID(requestId, serviceId);
		if (serviceResponseInfoOp.isPresent()) {
			String status = policyResponseModel.getStatus();
			String currency = Currency.SAR.value();
			ServiceResponseInfo serviceResponseInfo = serviceResponseInfoOp.get();
			serviceResponseInfo.setStatus(status);
			serviceResponseInfo.setStatusDescription(policyResponseModel.getStatusDescription());
			serviceResponseInfo.setNet(BigDecimal.ZERO);
			serviceResponseInfo.setPatientShare(BigDecimal.ZERO);
			serviceResponseInfo.setNetCurrency(currency);
			serviceResponseInfo.setPatientShareCurrency(currency);
			serviceResponseInfo = serviceResponseInfoRepository.save(serviceResponseInfo);
			if (status.equals(PolicyConsumptionStatus.REJECTED.getValue())) {
				modifyRejectionReason(drugCode, requestId, serviceResponseInfo.getId(), policyResponseModel,
						scientificCode);
			}
		}
	}

	private void modifyRejectionReason(String drugCode, String requestId, Long serviceResponseInfoId,
			PolicyResponseModel policyResponseModel, String scientificCode) {
		ServiceRejection serviceRejection = new ServiceRejection(drugCode, policyResponseModel.getDenialCode(),
				policyResponseModel.getDenialDescription(), requestId, scientificCode, serviceResponseInfoId);
		serviceRejectionRepository.save(serviceRejection);
	}

	private void addMedicalRejections(List<ServiceRejectionModel> serviceRejectionModels,
			List<ServiceRejectionModel> rejections) {
		List<ServiceRejectionModel> medicalRejections = serviceRejectionModels.stream()
				.filter(serviceRejection -> !serviceRejection.getDenialCode().startsWith("BR_")
						&& !serviceRejection.getDenialCode().startsWith("PYR"))
				.map(service -> service).collect(Collectors.toList());
		if (!medicalRejections.isEmpty()) {
			medicalRejections.stream().forEach(medicalRejection -> medicalRejection.setIsCustomizable(
					null == medicalRejection.getIsCustomizable() ? true : medicalRejection.getIsCustomizable()));
			rejections.addAll(medicalRejections);
		}
	}

	private void addBusinessRejections(List<ServiceRejectionModel> serviceRejectionModels,
			List<ServiceRejectionModel> rejections) {
		List<ServiceRejectionModel> businessRejections = serviceRejectionModels.stream()
				.filter(serviceRejection -> serviceRejection.getDenialCode().startsWith("BR_")
						|| serviceRejection.getDenialCode().startsWith("PYR"))
				.map(service -> service).collect(Collectors.toList());
		if (!businessRejections.isEmpty()) {
			businessRejections.stream().forEach(businessRejection -> {
				businessRejection.setIsCustomizable(null);
			});
			rejections.addAll(businessRejections);
		}
	}

	private void populatePrescriptionRejection(String denialCode, String denialDescription, String referenceNo,
			String requestId) {
		PrescriptionRejection prescriptionRejection = new PrescriptionRejection(denialCode, denialDescription,
				requestId, referenceNo, true);
		prescriptionRejectionRepository.save(prescriptionRejection);
	}

	private void setTotalPriceAndPolicyConsumptionDrugList(PrescriptionRequestModel prescriptionRequestModel) {
		BigDecimal totalPrice = new BigDecimal(0);
		List<DrugList> prescriptionDrugList = prescriptionRequestModel.getDrugList();
		List<DrugListModel> policyConsumptionDrugList = new ArrayList<>();
		for (int index = 0; index < prescriptionDrugList.size(); index++) {
			BigDecimal requestedQuantity = prescriptionDrugList.get(index).getQuantity();
			Double unitPrice = prescriptionDrugList.get(index).getUnitPrice();
			BigDecimal drugPrice = BigDecimal.valueOf(0);
			if (unitPrice != null) {
				drugPrice = BigDecimal.valueOf(prescriptionDrugList.get(index).getUnitPrice());
			}
			BigDecimal drugCost = requestedQuantity.multiply(drugPrice).setScale(2, RoundingMode.HALF_UP);
			totalPrice = totalPrice.add(drugCost);
			DrugListModel drugListModel = new DrugListModel();
			drugListModel.setDrugCode(prescriptionDrugList.get(index).getDrugCode());
			drugListModel.setAmount(drugCost);
			policyConsumptionDrugList.add(drugListModel);
		}
		prescriptionRequestModel.setTotalPrice(totalPrice);
		prescriptionRequestModel.setPolicyConsumptionDrugList(policyConsumptionDrugList);
	}

	private String fetchPayerIdByMappedPayerId(String mappedPayerId) {
		return mappingPayerIdRepository.findByMapperPayerIdAndIsEnabled(mappedPayerId, true)
				.map(MappingPayerId::getPayerId).orElse(mappedPayerId);
	}
}