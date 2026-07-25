package com.waseel.pbm.pbmadminservice.repository.hira;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.pbmadminservice.model.customization.IcdDiagnosisModel;
import com.waseel.pbm.pbmadminservice.persist.hira.ICDDiagnosis;

public interface ICDDiagnosisRepository
		extends JpaRepository<ICDDiagnosis, String>, JpaSpecificationExecutor<ICDDiagnosis> {
	
	 @Query(value = "SELECT excelDiagnosisSet.COLUMN_VALUE as diagnosisCode, "
	            + "CASE "
	            + "    WHEN hiraDiagnosisSet.\"ICDDiagnosisCode\" IS NULL THEN 0 "
	            + "    ELSE 1 "
	            + "END as isValid "
	            + "FROM TABLE( "
	            + "    SYS.ODCIVARCHAR2LIST( "
	            + "        :diagnosisCodes "
	            + "    ) "
	            + ") excelDiagnosisSet "
	            + "LEFT JOIN HIRA.\"ICDDiagnosis\" hiraDiagnosisSet on"
	            + " excelDiagnosisSet.COLUMN_VALUE = hiraDiagnosisSet.\"ICDDiagnosisCode\"",
	            nativeQuery = true)
	List<IcdDiagnosisModel> findByIcdCodes(@Param("diagnosisCodes") List<String> diagnosisCodes);

	Optional<ICDDiagnosis> findByIcdDiagnosisCode(String icdDiagnosisCode);
}
