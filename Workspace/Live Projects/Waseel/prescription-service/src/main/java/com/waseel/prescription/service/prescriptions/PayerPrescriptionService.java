package com.waseel.prescription.service.prescriptions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.enums.DssPayerTransactionType;
import com.waseel.prescription.model.pbmpayerapis.PayerPrescriptionRequestModel;
import com.waseel.prescription.model.pbmpayerapis.PayerPrescriptionResponseModel;
import com.waseel.prescription.persist.prescriptionservice.MappingPayerId;
import com.waseel.prescription.repository.prescriptionservice.MappingPayerIdRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;

@Service
public class PayerPrescriptionService {

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@Autowired
	private MappingPayerIdRepository mappingPayerIdRepository;

	private final Logger log = LoggerFactory.getLogger(PayerPrescriptionService.class);

	public Page<PayerPrescriptionResponseModel> getProvidersList(PayerPrescriptionRequestModel request) {
		log.info("PayerId {}", request.getPayerId());
		int pageNumber = request.getPageNumber();
		int recordSize = request.getRecordSize();
		String inputFromDate = request.getFromDate();
		String inputEndDate = request.getEndDate();
		log.info("PayerId {} PageNumber {} RecordSize {} FromDate {} EndDate {}", request.getPayerId(), pageNumber,
				recordSize, inputFromDate, inputEndDate);
		LocalDate fromDate = null;
		LocalDate endDate = null;
		if (!StringUtils.isBlank(inputFromDate)) {
			try {
				fromDate = LocalDate.parse(inputFromDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			} catch (DateTimeParseException e) {
			}
		}
		if (!StringUtils.isBlank(inputEndDate)) {
			try {
				endDate = LocalDate.parse(inputEndDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				endDate = endDate.plusDays(1);
			} catch (DateTimeParseException e) {
			}
		}
		String idNumber = request.getIdNumber() != null ? request.getIdNumber().trim() : request.getIdNumber();
		List<String> payerIds = getListOfPayerIds(request.getPayerId());
		List<Object[]> queryResult = prescriptionRequestRepository.getProvidersListWithPaginationWithNativeQuery(
				request.getStatus(), request.getProviderId(), request.getReferenceNo(), payerIds, recordSize,
				pageNumber, fromDate, endDate, idNumber, request.getMemberName());
		List<PayerPrescriptionResponseModel> payerPrescriptionResponseModels = new ArrayList<>();
		queryResult.forEach(row -> {
			PayerPrescriptionResponseModel model = new PayerPrescriptionResponseModel((String) row[0],
					(BigDecimal) row[1], (Date) row[2], (String) row[3], (String) row[4], (String) row[5],
					(String) row[6]);
			payerPrescriptionResponseModels.add(model);
		});
		return createPage(payerPrescriptionResponseModels, pageNumber, recordSize,
				prescriptionRequestRepository.getProvidersListWithPaginationCountWithNativeQuery(request.getStatus(),
						request.getProviderId(), request.getReferenceNo(), payerIds, fromDate, endDate, idNumber,
						request.getMemberName()));
	}

	private Page<PayerPrescriptionResponseModel> createPage(List<PayerPrescriptionResponseModel> data, int pageNumber,
			int pageSize, int totalCount) {
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
		return new PageImpl<>(data, pageRequest, totalCount);
	}

	private String fetchPayerIdByMappedPayerId(String payerId) {
		return mappingPayerIdRepository.findByPayerIdAndTransactionTypeAndIsEnabled(payerId,
				DssPayerTransactionType.PRESCRIPTION.value(), true).map(MappingPayerId::getMapperPayerId).orElse(null);
	}

	private List<String> getListOfPayerIds(String payerId) {
		return new ArrayList<>(Arrays.asList(payerId, fetchPayerIdByMappedPayerId(payerId)));
	}
}
