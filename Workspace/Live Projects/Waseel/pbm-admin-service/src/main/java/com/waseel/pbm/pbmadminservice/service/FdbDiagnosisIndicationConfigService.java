package com.waseel.pbm.pbmadminservice.service;

import com.waseel.pbm.pbmadminservice.enums.EnableDisableStatus;
import com.waseel.pbm.pbmadminservice.model.FdbDiagnosisIndicationConfigModel;
import com.waseel.pbm.pbmadminservice.model.FdbDiagnosisIndicationConfigRequest;
import com.waseel.pbm.pbmadminservice.persist.mdss.FdbDiagnosisIndicationConfig;
import com.waseel.pbm.pbmadminservice.repository.mdss.FDBDiagnosisIndicationConfigRepository;
import com.waseel.pbm.pbmadminservice.specification.FDBDiagnosisIndicationConfigSpecification;
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
public class FdbDiagnosisIndicationConfigService {

    private final Logger log = LoggerFactory.getLogger(FdbDiagnosisIndicationConfigService.class);

    @Autowired
    private FDBDiagnosisIndicationConfigRepository fdbDiagnosisIndicationConfigRepo;

    @Autowired
    private CommonMessageService messageService;

    @Autowired
    private FDBDiagnosisIndicationConfigSpecification fdbDiagnosisIndicationConfigCriteria;

    private static final String DATA_NOT_FOUND = "Data not found";

    public void addFDBDiagnosisConfiguration(FdbDiagnosisIndicationConfigRequest fdbDiagnosisConfig) {
        Optional<FdbDiagnosisIndicationConfig> configOptional = fdbDiagnosisIndicationConfigRepo
                .findByNotDeletedIcdCode(fdbDiagnosisConfig.getIcdCode());
        if (configOptional.isPresent()) {
            throw new IllegalArgumentException("IcdCode is already exist");
        }
        FdbDiagnosisIndicationConfig newConfig = new FdbDiagnosisIndicationConfig();
        newConfig.setIcdCode(fdbDiagnosisConfig.getIcdCode());
        newConfig.setValidateSubChapters(fdbDiagnosisConfig.getValidateSubChapters());
        newConfig.setIsEnabled(fdbDiagnosisConfig.getIsEnabled().charAt(0));
            setId(newConfig);
        fdbDiagnosisIndicationConfigRepo.save(newConfig);
    }

    private void setId(FdbDiagnosisIndicationConfig config) {
        Long id = fdbDiagnosisIndicationConfigRepo.findLatestId();
        if (id != null) {
            config.setId(++id);
        }
    }

    public void deleteFDBDiagnosisConfiguration(Long id) {
        log.info("Id :- {}", id);
        if (id < 1) {
            log.error(messageService.getMessage("InvalidFields"));
            throw new IllegalArgumentException(messageService.getMessage("InvalidFields"));
        }
        FdbDiagnosisIndicationConfig config = findNotDeletedIcdCodeInDB(id);
        if (config == null) {
            log.debug(DATA_NOT_FOUND);
            throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
        }
        config.setIsDeleted(EnableDisableStatus.TRUE.value());
        fdbDiagnosisIndicationConfigRepo.save(config);
        log.info("Data deleted successfully for FDBDiagnosisConfiguration.Id: {} ", config.getId());
    }

    public void updateFDBDiagnosisConfiguration(FdbDiagnosisIndicationConfigRequest fdbDiagnosisConfig, Long id) {
        FdbDiagnosisIndicationConfig config = findNotDeletedIcdCodeInDB(id);
        if (config == null) {
            log.debug(DATA_NOT_FOUND);
            throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
        }
        config.setValidateSubChapters(fdbDiagnosisConfig.getValidateSubChapters());
        config.setIsEnabled(fdbDiagnosisConfig.getIsEnabled().charAt(0));
        config.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
        fdbDiagnosisIndicationConfigRepo.save(config);
    }

    public Page<FdbDiagnosisIndicationConfigModel> getFDBDiagnosisConfiguration(int pageNumber, int recordSize,
                                                                                String icdCode) {
        log.info("Page Number :- {} Record Size :- {} icdCode :- {}", pageNumber, recordSize, icdCode);
        return fdbDiagnosisIndicationConfigCriteria
                .findByIcdCodeWithPagination(pageNumber, recordSize, icdCode);
    }

    private FdbDiagnosisIndicationConfig findNotDeletedIcdCodeInDB(Long id) {
        Optional<FdbDiagnosisIndicationConfig> config = fdbDiagnosisIndicationConfigRepo
                .findByNotDeletedConfiguration(id);
        if (config.isPresent()) {
            return config.get();
        }
        return null;
    }
}
