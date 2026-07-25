package com.waseel.pbm.pbmadminservice.service;

import com.waseel.pbm.pbmadminservice.model.PayerConfigModel;
import com.waseel.pbm.pbmadminservice.specification.PayerConfigSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PayerConfigService {

    private final Logger log = LoggerFactory.getLogger(PayerConfigService.class);

    @Autowired
    private PayerConfigSpecification payerConfigSpecification;

    public Page<PayerConfigModel> getAllPayerDetails(int pageNumber, int recordSize, String payerId) {
        log.info("Page Number :- {}, Record Size :- {}, payerId :- {} ", pageNumber, recordSize, payerId);
        return payerConfigSpecification.findByPayerIdWithPagination(pageNumber, recordSize, payerId);
    }
}
