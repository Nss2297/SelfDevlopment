package com.waseel.pbm.dssservice.service.managementservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.waseel.pbm.dssservice.enums.RequestStatus;
import com.waseel.pbm.dssservice.enums.ServiceStatus;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.model.Error;
import com.waseel.pbm.dssservice.model.Result;

@Service
public class DssResponseAdapter {
	int rejectedDrugsCount;

	public DssResponse combined(DssRequest request, DssResponse idfResponse, DssResponse fdbResponse,
			DssResponse rtsResponse) {
		rejectedDrugsCount = 0;
		DssResponse dssResponse = new DssResponse();
		List<Result> drugResults = new ArrayList<>();
		dssResponse.setRequestId(request.getRequestId());

		request.getDrugList().forEach(drug -> {
			Result drugResult = new Result();
			List<Error> rejectionReasons = new ArrayList<>();
			if (drug.getNdcDrugCode() != null && !drug.getNdcDrugCode().isEmpty())
				drugResult.setNdcDrugCode(drug.getNdcDrugCode());
			if (drug.getScientificCode() != null && !drug.getScientificCode().isEmpty())
				drugResult.setScientificCode(drug.getScientificCode());
			drugResult.setDispensedQuantity(drug.getDispensedQuantity());
			drugResult.setAmount(drug.getAmount());
			if (!StringUtils.isBlank(drug.getDaysOfSupply()))
				drugResult.setDaysOfSupply(drug.getDaysOfSupply());

			// go through all modules results to set final response ..
			if (idfResponse != null)
				addRejectionReason(idfResponse,
						drugResult.getNdcDrugCode() != null && !drugResult.getNdcDrugCode().isEmpty()
								? drugResult.getNdcDrugCode()
								: drugResult.getScientificCode(),
						rejectionReasons);

			if (fdbResponse != null)
				addRejectionReason(fdbResponse,
						drugResult.getNdcDrugCode() != null && !drugResult.getNdcDrugCode().isEmpty()
								? drugResult.getNdcDrugCode()
								: drugResult.getScientificCode(),
						rejectionReasons);

			if (rtsResponse != null)
				addRejectionReason(rtsResponse,
						drugResult.getNdcDrugCode() != null && !drugResult.getNdcDrugCode().isEmpty()
								? drugResult.getNdcDrugCode()
								: drugResult.getScientificCode(),
						rejectionReasons);

			if (!rejectionReasons.isEmpty()) {
				drugResult.setStatus(ServiceStatus.REJECTED.toString());

				drugResult.setErrors(rejectionReasons);
				rejectedDrugsCount++;
			} else {
				drugResult.setStatus(ServiceStatus.APPROVED.toString());
				drugResult.setErrors(new ArrayList<Error>());
			}
			drugResults.add(drugResult);
		});

		dssResponse.setResults(drugResults);
		if (rejectedDrugsCount < 1) {
			dssResponse.setStatus(RequestStatus.APPROVED.value());
		} else if (rejectedDrugsCount == request.getDrugList().size()) {
			dssResponse.setStatus(RequestStatus.REJECTED.value());
		} else {
			dssResponse.setStatus(RequestStatus.PARTIAL_APPROVED.value());
		}
		dssResponse.setHttpStatusCode(200);
		return dssResponse;
	}

	private void addRejectionReason(DssResponse response, String serviceInfo, List<Error> rejectionReasons) {
		for (Result result : response.getResults()) {
			if (result.getNdcDrugCode() != null && !result.getNdcDrugCode().isEmpty()) {
				if (result.getNdcDrugCode().equals(serviceInfo) && result.getErrors() != null) {
					rejectionReasons.addAll(result.getErrors());
					break;
				}
			} else if (result.getScientificCode() != null && !result.getScientificCode().isEmpty()) {
				if (result.getScientificCode().equals(serviceInfo) && result.getErrors() != null) {
					rejectionReasons.addAll(result.getErrors());
					break;
				}
			}
		}
	}

	private List<Error> removeDuplicatedRejectionReasons(List<Error> rejectionReasons) {
		Collection<Error> uniqueErrors = rejectionReasons.stream()
				.collect(Collectors.toMap(Error::getDescription, Function.identity(), (er1, er2) -> er1)).values();

		return new ArrayList<>(uniqueErrors);
	}
}
