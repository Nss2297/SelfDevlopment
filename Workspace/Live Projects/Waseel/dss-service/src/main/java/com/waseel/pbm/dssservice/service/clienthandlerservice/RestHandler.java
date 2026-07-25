package com.waseel.pbm.dssservice.service.clienthandlerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.waseel.pbm.dssservice.enums.ScreeningModules;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.model.PayerCustomizationRequest;
import com.waseel.pbm.dssservice.repository.mdss.PayerModuleConfigurationRepository;
import com.waseel.pbm.dssservice.service.managementservice.DssResponseAdapter;
import com.waseel.pbm.dssservice.service.managementservice.MapperService;

@Service
public class RestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestHandler.class);

	@Value("${idfvalidationservice.url}")
	private String idfURL;

	@Value("${fdbvalidationservice.url}")
	private String fdbURL;

	@Value("${rtsvalidationservice.url}")
	private String rtsURL;

	@Value("${payercustomizationservice.url}")
	private String pcsURL;

	@Autowired
	DssResponseAdapter dssResponseAdapter;

	@Autowired
	PayerModuleConfigurationRepository modulesConfigurationRepo;

	@Autowired
	private MapperService mapperService;

	private static final String COMMON_SENDREQMSG = "Send Request {} to {} Service";

	public DssResponse handleDssRequest(DssRequest request, Long transactionLogId) {
		DssResponse fdbResponse = null;
		DssResponse rtsResponse = null;
		DssResponse idfResponse = null;
		DssResponse pcsResponse = null;
		DssResponse response = null;
		request.setTransactionLogId(transactionLogId);

		if (modulesConfigurationRepo.findByIdAndIsEnabled(request.getPayerId(), ScreeningModules.IDF.value()) != null) {
			idfResponse = sendDssRequestToIdfValidationService(request);
			if (idfResponse != null && (idfResponse.getHttpStatusCode() == HttpStatus.BAD_REQUEST.value()
					|| idfResponse.getHttpStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value())) {
				return idfResponse;
			}
		}

		if (modulesConfigurationRepo.findByIdAndIsEnabled(request.getPayerId(), ScreeningModules.FDB.value()) != null) {
			fdbResponse = sendDssRequestToFdbValidationService(request);
			if (fdbResponse != null && (fdbResponse.getHttpStatusCode() == HttpStatus.BAD_REQUEST.value()
					|| fdbResponse.getHttpStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value())) {
				return fdbResponse;
			}
		}

		if (modulesConfigurationRepo.findByIdAndIsEnabled(request.getPayerId(), ScreeningModules.RTS.value()) != null) {
			rtsResponse = sendDssRequestToRtsValidationService(request);
			if (rtsResponse != null && (rtsResponse.getHttpStatusCode() == HttpStatus.BAD_REQUEST.value()
					|| rtsResponse.getHttpStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value())) {
				return rtsResponse;
			}
		}
		response = dssResponseAdapter.combined(request, idfResponse, fdbResponse, rtsResponse);

		if (modulesConfigurationRepo.findByIdAndIsEnabled(request.getPayerId(),
				ScreeningModules.PAYER_CUSTOMIZATION.value()) != null && response != null) {
			PayerCustomizationRequest pcRequest = new PayerCustomizationRequest();
			pcRequest.setDssRequest(request);
			pcRequest.setDssResponse(response);
			pcsResponse = sendDssResponseToPayerCustomizationService(pcRequest);
			if ((pcsResponse != null)) {
				return pcsResponse;
			}
		}
		return response;
	}

	private DssResponse sendDssResponseToPayerCustomizationService(PayerCustomizationRequest pcRequest) {
		try {
			LOGGER.info("Send Request " + pcRequest.getDssRequest().getRequestId() + " to Payer-Customization Service");
			RestTemplate template = new RestTemplate();

			ResponseEntity<DssResponse> response = template.postForEntity(pcsURL, pcRequest, DssResponse.class);
			LOGGER.info("Valid Response Has Been Recived From Payer-Customization Service For Request Id :"
					+ pcRequest.getDssRequest().getRequestId());
			return response.getBody();
		} catch (HttpClientErrorException e) {
			LOGGER.info(e.getRawStatusCode() + " Has Been Recived From Payer-Customization Service For Request Id :"
					+ pcRequest.getDssRequest().getRequestId());
			if (e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()
					|| e.getRawStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapDssResponse(e.getResponseBodyAsString());
			}
		} catch (Exception e) {
			LOGGER.info(
					"Exception Has Been Thrown While Reading The Response From Payer-Customization service For Request :"
							+ pcRequest.getDssRequest().getRequestId() + " " + e);
		}
		return null;
	}

	private DssResponse sendDssRequestToIdfValidationService(DssRequest request) {
		try {

			LOGGER.info(COMMON_SENDREQMSG, request.getRequestId(), "IDF-Validation");
			RestTemplate template = new RestTemplate();
			ResponseEntity<DssResponse> response = template.postForEntity(idfURL, request, DssResponse.class);
			LOGGER.info("Valid Response Has Been Recived From IDF-Validation Service For Request Id :"
					+ request.getRequestId());
			return response.getBody();
		} catch (HttpClientErrorException e) {
			LOGGER.info(e.getRawStatusCode() + " Has Been Recived From IDF-Validation Service For Request Id :"
					+ request.getRequestId());
			if (e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()
					|| e.getRawStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapDssResponse(e.getResponseBodyAsString());
			}
		} catch (Exception e) {
			LOGGER.info("Exception Has Been Thrown While Reading The Response From IDF-Validation service For Request :"
					+ request.getRequestId() + " " + e);
		}
		return null;
	}

	private DssResponse sendDssRequestToFdbValidationService(DssRequest request) {
		RestTemplate template = new RestTemplate();
		try {

			LOGGER.info(COMMON_SENDREQMSG, request.getRequestId(), "FDB-Validation");
			ResponseEntity<DssResponse> response = template.postForEntity(fdbURL, request, DssResponse.class);
			LOGGER.info("Valid Response Has Been Recived From FDB-Validation Service For Request Id :"
					+ request.getRequestId());
			return response.getBody();
		} catch (HttpClientErrorException e) {
			LOGGER.info(e.getRawStatusCode() + " Has Been Recived From FDB-Validation Service For Request Id :"
					+ request.getRequestId());
			if (e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()
					|| e.getRawStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapDssResponse(e.getResponseBodyAsString());
			}
		} catch (Exception e) {
			LOGGER.info("Exception Has Been Thrown While Reading The Response From FDB-Validation service For Request :"
					+ request.getRequestId() + " " + e);
			e.printStackTrace();
		}
		return null;
	}

	private DssResponse sendDssRequestToRtsValidationService(DssRequest request) {
		try {

			LOGGER.info(COMMON_SENDREQMSG, request.getRequestId(), "RTS");
			RestTemplate template = new RestTemplate();
			ResponseEntity<DssResponse> response = template.postForEntity(rtsURL, request, DssResponse.class);
			LOGGER.info("Valid Response Has Been Recived From RTS Service For Request Id :" + request.getRequestId());
			return response.getBody();
		} catch (HttpClientErrorException e) {
			LOGGER.info(e.getRawStatusCode() + " Has Been Recived From RTS Service For Request Id :"
					+ request.getRequestId());
			if (e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()
					|| e.getRawStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapDssResponse(e.getResponseBodyAsString());
			}
		} catch (Exception e) {
			LOGGER.info("Exception Has Been Thrown While Reading The Response From RTS service For Request :"
					+ request.getRequestId() + " " + e);
			e.printStackTrace();
		}
		return null;
	}
}
