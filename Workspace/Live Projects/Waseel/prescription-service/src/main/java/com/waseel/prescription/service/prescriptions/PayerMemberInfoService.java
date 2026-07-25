package com.waseel.prescription.service.prescriptions;

import com.waseel.prescription.model.common.PayerMemberInfoModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.service.clienthandler.PbmPayerApisRestHandler;
import com.waseel.prescription.specification.PayerMemberInfoSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PayerMemberInfoService {

    private final Logger log = LoggerFactory.getLogger(PayerMemberInfoService.class);
    
    @Autowired
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

    @Autowired
    private PayerMemberInfoSpecification payerMemberInfoSpecification;

    public List<PayerMemberInfoModel> getMemberInfo(String payerId, String value) {
        log.info("PayerId :- {}, IdNumber :- {} ", payerId, value);
        PayerMemberInfoModel payerMemberInfoModel = new PayerMemberInfoModel(payerId, value);
        List<PayerMemberInfoModel> payerMemberInfoModelList =
                payerMemberInfoSpecification.findByNationalIdAndPayerId(payerMemberInfoModel);
        countAge(payerMemberInfoModelList);
        return payerMemberInfoModelList;
    }

    private void countAge(List<PayerMemberInfoModel> payerMemberInfoModelList) {
        payerMemberInfoModelList.forEach(e -> e.setAge(patientAgeConverter(e.getDob())));
    }

	public ResponseEntity<MemberDemographicDataResponseModel> getMemberDemographicData(Long idNumber) {
		ResponseEntity<MemberDemographicDataResponseModel> resModel = pbmPayerApisRestHandler
				.sendRequestToGetMemberDemographicData(idNumber);
		MemberDemographicDataResponseModel memberDemographicModel = resModel.getBody();
		if (memberDemographicModel != null)
			memberDemographicModel.setAge(patientAgeConverter(memberDemographicModel.getDateOfBirth()));
		return resModel;
	}
    
    public String patientAgeConverter(Date dateOfBirth) {
        if (dateOfBirth != null) {
            long diff = Math.abs(new Date().getTime() - dateOfBirth.getTime());
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            long year = days / 365;
            if (year < 3) {
                long month = (long) Math.ceil((days / 30.41));
                if (month < 3) {
                    return days + " days";
                }
                return month + " months";
            }
            return year + " years";
        }
        return null;
    }
}
