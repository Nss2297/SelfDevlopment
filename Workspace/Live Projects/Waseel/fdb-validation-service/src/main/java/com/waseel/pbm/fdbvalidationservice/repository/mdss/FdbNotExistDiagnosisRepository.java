package com.waseel.pbm.fdbvalidationservice.repository.mdss;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.fdbvalidationservice.persist.mdss.FdbNotExistDiagnosis;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.FdbNotExistDiagnosisId;

public interface FdbNotExistDiagnosisRepository extends CrudRepository<FdbNotExistDiagnosis, FdbNotExistDiagnosisId> {

}
