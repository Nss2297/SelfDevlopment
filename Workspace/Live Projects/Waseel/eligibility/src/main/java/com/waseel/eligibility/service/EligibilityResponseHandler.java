package com.waseel.eligibility.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.eligibility.client.portal.model.TransactionWrapper;
import com.waseel.eligibility.entity.WslGeninfo;
import com.waseel.eligibility.repository.ClaimPropsRepository;

@Service
public class EligibilityResponseHandler {
	
	@Autowired
	ClaimPropsRepository claimPropsRepos;

	public String handleResponse(WslGeninfo wslGeninfo, TransactionWrapper response) {
		String statusAndDesc = updateClaimProps(wslGeninfo.getClaimid(),response);
		String error = handleErrorResponse(wslGeninfo.getClaimid(),response);
		
		if(statusAndDesc == null)
		{
			return error;
		}
		return statusAndDesc;
		
	}

	private String handleErrorResponse(Long claimid, TransactionWrapper response) {
		if (response!= null && response.getErrorResponse() != null && response.getErrorResponse().size() > 0) {
			StringBuilder sb = new StringBuilder();
			response.getErrorResponse().stream().forEach(errorResponseCT -> {
				sb.append(errorResponseCT.getErrorDescription());
			});
			claimPropsRepos.updateEligibilitycheckByClaimid("Failed", claimid,sb.toString());
			return "Failed";
		}
		return null;

	}

	private String updateClaimProps(Long claimid, TransactionWrapper response) {
		if (response != null && response.getEligibilitySubmissionResponse() != null) {
			String status = response.getEligibilitySubmissionResponse().getStatus().getStatusCode().toString();
			String statusDescriptipon = response.getEligibilitySubmissionResponse().getStatus().getStatusDescription().toString();
			claimPropsRepos.updateEligibilitycheckByClaimid(status, claimid, statusDescriptipon);
			// pass both value to topic
			String responseData = status + ":" + statusDescriptipon;
			return responseData;
		}
		claimPropsRepos.updateEligibilitycheckByClaimid("Failed", claimid,"Received Error reponse from payer");
		return null;
	}


}
