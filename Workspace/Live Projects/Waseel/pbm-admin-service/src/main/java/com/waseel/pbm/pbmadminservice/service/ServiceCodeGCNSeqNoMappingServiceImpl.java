package com.waseel.pbm.pbmadminservice.service;

import com.waseel.pbm.pbmadminservice.enums.EnableDisableStatus;
import com.waseel.pbm.pbmadminservice.model.ServiceCodeGCNSeqNoMappingModel;
import com.waseel.pbm.pbmadminservice.model.ServiceCodeGCNSeqNoMappingRequest;
import com.waseel.pbm.pbmadminservice.persist.mdss.ServiceCodeGCNSeqNoMapping;
import com.waseel.pbm.pbmadminservice.persist.medkfdb.Ripdpp0ProductMaster;
import com.waseel.pbm.pbmadminservice.repository.mdss.ServiceCodeGCNSeqNoMappingRepository;
import com.waseel.pbm.pbmadminservice.repository.medkfdb.Ripdpp0ProductMasterRepository;
import com.waseel.pbm.pbmadminservice.specification.ServiceCodeGCNSeqNoMappingSpecification;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class ServiceCodeGCNSeqNoMappingServiceImpl implements ServiceCodeGCNSeqNoMappingService {

    private final Logger log = LoggerFactory.getLogger(ServiceCodeGCNSeqNoMappingServiceImpl.class);

    @Autowired
    private ServiceCodeGCNSeqNoMappingRepository serviceCodeGCNSeqNoMappingRepository;
    @Autowired
    private ServiceCodeGCNSeqNoMappingSpecification serviceCodeGCNSeqNoMappingSpecification;
    @Autowired
    private CommonMessageService messageService;
    @Autowired
    private Ripdpp0ProductMasterRepository ripdpp0ProductMasterRepository;

    @Override
    public Page<ServiceCodeGCNSeqNoMappingModel> getServiceCodeGCNSequenceNumberMappingData(
            int pageNumber, int recordSize, int gcnSeqNumber, String serviceCode) {
        log.info("Page Number :- {} Record Size :- {} GCN Seq Number :- {} Service Code :- {}"
                , pageNumber, recordSize, gcnSeqNumber, serviceCode);
        return serviceCodeGCNSeqNoMappingSpecification
                .findByServiceCodeGCNSeqNumberWithPagination(pageNumber, recordSize, gcnSeqNumber, serviceCode);
    }

    @Override
    public void addServiceCodeGCNSequenceNumberMappingData(
            ServiceCodeGCNSeqNoMappingRequest request) {
        int gcnSeqNumber = request.getGcnSeqNo();
        String serviceCode = request.getServiceCode();
        String productPackageUnit = request.getProductPackageUnit();
        int productPackageSize = request.getProductPackageSize();
        log.info("Product Package Unit :- {} Product Package Size :- {} GCN Seq Number :- {} Service Code :- {}",
                productPackageUnit, productPackageSize, gcnSeqNumber, serviceCode);
        String invalidFieldsMessage = getInvalidFieldMsg();
        if (gcnSeqNumber > 0 && !StringUtils.isBlank(serviceCode) && !StringUtils.isBlank(productPackageUnit)
                && productPackageSize > 0) {
            String message = validateGCNSeqNumberAndServiceCode(gcnSeqNumber, serviceCode);
            if (message == null) {
                ServiceCodeGCNSeqNoMapping serviceCodeGCNSeqNoMapping = new ServiceCodeGCNSeqNoMapping();
                serviceCodeGCNSeqNoMapping.setServiceCode(serviceCode);
                serviceCodeGCNSeqNoMapping.setGcnSeqNo(gcnSeqNumber);
                serviceCodeGCNSeqNoMapping.setProductPackageSize(productPackageSize);
                serviceCodeGCNSeqNoMapping.setProductPackageUnit(productPackageUnit);
                setId(serviceCodeGCNSeqNoMapping);
                serviceCodeGCNSeqNoMappingRepository.save(serviceCodeGCNSeqNoMapping);
                return;
            }
            invalidFieldsMessage = message;
        }
        log.error(invalidFieldsMessage);
        throw new IllegalArgumentException(invalidFieldsMessage);
    }

    private void setId(ServiceCodeGCNSeqNoMapping config) {
        Long id = serviceCodeGCNSeqNoMappingRepository.findLatestId();
        if (id != null) {
            config.setId(++id);
        }
    }

    private String validateGCNSeqNumberAndServiceCode(int gcnSeqNumber, String serviceCode) {
        Optional<Ripdpp0ProductMaster> masterOptional = ripdpp0ProductMasterRepository.findByGcnSeqNo(gcnSeqNumber);
        if (!masterOptional.isPresent()) {
            return "GCN seq number is not present in FDB";
        }
        Optional<ServiceCodeGCNSeqNoMapping> mappingOptional = serviceCodeGCNSeqNoMappingRepository
                .findById(serviceCode);
        if (mappingOptional.isPresent()) {
            return "Service code is already exist";
        }
        return null;
    }

    @Override
    public void updateServiceCodeGCNSequenceNumberMappingData(
            ServiceCodeGCNSeqNoMappingRequest request, Long id) {
        int gcnSeqNumber = request.getGcnSeqNo();
        String serviceCode = request.getServiceCode();
        String productPackageUnit = request.getProductPackageUnit();
        int productPackageSize = request.getProductPackageSize();
        log.info("Product Package Unit :- {} Product Package Size :- {} GCN Seq Number :- {} Service Code :- {}",
                productPackageUnit, productPackageSize, gcnSeqNumber, serviceCode);
        if (gcnSeqNumber < 1 || StringUtils.isBlank(productPackageUnit)
                || productPackageSize < 1 || id < 1) {
            log.error(getInvalidFieldMsg());
            throw new IllegalArgumentException(getInvalidFieldMsg());
        }
        Optional<ServiceCodeGCNSeqNoMapping> mappingOptional =
                serviceCodeGCNSeqNoMappingRepository.findValueById(id);
        if (mappingOptional.isPresent()) {
            ServiceCodeGCNSeqNoMapping mapping = mappingOptional.get();
            mapping.setGcnSeqNo(gcnSeqNumber);
            mapping.setProductPackageUnit(productPackageUnit);
            mapping.setProductPackageSize(productPackageSize);
            mapping.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
            serviceCodeGCNSeqNoMappingRepository.save(mapping);
            return;
        }
        throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
    }

    @Override
    public void deleteServiceCodeGCNSequenceNumberMappingData(Long id) {
        log.info("Id :- {}", id);
        if (id < 1) {
            log.error(messageService.getMessage("InvalidFields"));
            throw new IllegalArgumentException(getInvalidFieldMsg());
        }
        Optional<ServiceCodeGCNSeqNoMapping> mappingOptional = serviceCodeGCNSeqNoMappingRepository
                .findValueById(id);
        if (!mappingOptional.isPresent()) {
            log.debug("Data not found");
            throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
        }
        ServiceCodeGCNSeqNoMapping mapping = mappingOptional.get();
        mapping.setIsDeleted(EnableDisableStatus.TRUE.value());
        serviceCodeGCNSeqNoMappingRepository.save(mapping);
    }

    private String getInvalidFieldMsg() {
        return messageService.getMessage("InvalidFields");
    }
}
