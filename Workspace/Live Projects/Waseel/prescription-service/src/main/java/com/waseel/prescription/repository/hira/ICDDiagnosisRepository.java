package com.waseel.prescription.repository.hira;

import com.waseel.prescription.persist.hira.ICDDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ICDDiagnosisRepository
        extends JpaRepository<ICDDiagnosis, String>, JpaSpecificationExecutor<ICDDiagnosis> {

}
