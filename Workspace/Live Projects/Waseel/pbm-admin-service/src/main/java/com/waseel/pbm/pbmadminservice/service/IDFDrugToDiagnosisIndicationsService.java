package com.waseel.pbm.pbmadminservice.service;

import com.waseel.pbm.pbmadminservice.enums.EnableDisableStatus;
import com.waseel.pbm.pbmadminservice.model.IDFDrugToDiagnosisIndicationsRequest;
import com.waseel.pbm.pbmadminservice.model.IDFDrugToDiagnosisModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.IdfDrugToDiagnosisIndications;
import com.waseel.pbm.pbmadminservice.repository.mdss.IdfDrugToDiagnosisIndicationsRepository;
import com.waseel.pbm.pbmadminservice.specification.IDFDrugToDiagnosisIndicationsSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class IDFDrugToDiagnosisIndicationsService {

    private final Logger log = LoggerFactory.getLogger(IDFDrugToDiagnosisIndicationsService.class);

    @Autowired
    private IdfDrugToDiagnosisIndicationsRepository idfDrugToDiagnosisIndicationsRepository;

    @Autowired
    private CommonMessageService messageService;

    @Autowired
    private IDFDrugToDiagnosisIndicationsSpecification idfDrugToDiagnosisIndicationsSpecification;

    private static final String DATA_NOT_FOUND = "Data not found";

    public void addIDFDrugToDiagnosisIndications(IDFDrugToDiagnosisIndicationsRequest request) {
        IdfDrugToDiagnosisIndications config = getRecordInDb(request.getIcdDiagnosisCode(), request.getServiceCode());
        if (config == null) {
            IdfDrugToDiagnosisIndications idfIndication = new IdfDrugToDiagnosisIndications();
            idfIndication.setIcdDiagnosisCode(request.getIcdDiagnosisCode());
            idfIndication.setServiceCode(request.getServiceCode());
            idfIndication.setOldServiceCode(request.getOldServiceCode());
            setId(idfIndication);
            idfDrugToDiagnosisIndicationsRepository.save(idfIndication);
            log.info("Data added successfully for IDF DrugToDiagnosis Indications.");
            return;
        } else if (config.getIsDeleted().equals(EnableDisableStatus.TRUE.value())) {
            config.setIsDeleted(EnableDisableStatus.FALSE.value());
            idfDrugToDiagnosisIndicationsRepository.save(config);
            log.info("Data added successfully for IDF DrugToDiagnosis Indications.");
            return;
        }
        log.info("Data already exist for IDF DrugToDiagnosis Indications.");
        throw new IllegalArgumentException("Configuration is already exist");
    }

    private void setId(IdfDrugToDiagnosisIndications config) {
        Long id = idfDrugToDiagnosisIndicationsRepository.findLatestId();
        if (id != null) {
            config.setId(++id);
        }
    }

    public void deleteIDFDrugToDiagnosisIndications(Long id) {
        if (id < 1) {
            log.error(messageService.getMessage("InvalidFields"));
            throw new IllegalArgumentException(messageService.getMessage("InvalidFields"));
        }
        Optional<IdfDrugToDiagnosisIndications> idfDetails = idfDrugToDiagnosisIndicationsRepository
                .findNotDeletedByIcdCodeAndServiceCodeAndId(id);
        if (!idfDetails.isPresent()) {
            log.debug(DATA_NOT_FOUND);
            throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
        }
        idfDetails.get().setIsDeleted(EnableDisableStatus.TRUE.value());
        idfDrugToDiagnosisIndicationsRepository.save(idfDetails.get());
        log.info("Data deleted successfully for IDF DrugToDiagnosis Indications.Id: {} ", idfDetails.get().getId());
    }

    public void updateIDFDrugToDiagnosisIndications(IDFDrugToDiagnosisIndicationsRequest request, Long id) {
        int updateStatus = idfDrugToDiagnosisIndicationsRepository.updateNotDeletedDataById(
                request.getIcdDiagnosisCode(), request.getServiceCode(), request.getOldServiceCode()
                , id, Timestamp.from(Instant.now()));
        if (updateStatus < 1) {
            log.debug(DATA_NOT_FOUND);
            throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
        }
        log.info("Data updated successfully for IDF DrugToDiagnosis Indications.Id: {} ", id);
    }

    public Page<IDFDrugToDiagnosisModel> getIDFDrugToDiagnosisIndicationsDetails(
            int pageNumber, int recordSize, String serviceCode, String icdCode) {
        return idfDrugToDiagnosisIndicationsSpecification
                .findByIcdCodeAndServiceCodeWithPagination(pageNumber, recordSize, serviceCode, icdCode);
    }

    private IdfDrugToDiagnosisIndications getRecordInDb(String icdCode, String serviceCode) {
        List<IdfDrugToDiagnosisIndications> config = idfDrugToDiagnosisIndicationsRepository
                .findByIcdCodeAndServiceCode(icdCode, serviceCode);
        if (!config.isEmpty()) {
            return config.stream()
                    .filter(idfConfig -> idfConfig.getIsDeleted().equals(EnableDisableStatus.FALSE.value())).findAny()
                    .orElse(config.get(0));
        }
        return null;
    }
}
