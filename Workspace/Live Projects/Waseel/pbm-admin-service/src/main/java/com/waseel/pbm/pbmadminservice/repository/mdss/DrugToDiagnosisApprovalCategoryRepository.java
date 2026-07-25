package com.waseel.pbm.pbmadminservice.repository.mdss;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.pbmadminservice.model.customization.DrugToDiagnosisApprovalCategoryModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugToDiagnosisApprovalCategory;

public interface DrugToDiagnosisApprovalCategoryRepository extends JpaRepository<DrugToDiagnosisApprovalCategory, Long>,
		JpaSpecificationExecutor<DrugToDiagnosisApprovalCategory> {

	List<DrugToDiagnosisApprovalCategory> findByIsEnabled(Character isEnabled);

	Optional<DrugToDiagnosisApprovalCategory> findByNameAndIsEnabled(String name, Character isEnabled);
	
	@Query(value = "SELECT excelApprovalCategorySet.COLUMN_VALUE as name, "
            + "CASE "
            + "    WHEN mdssApprovalCategorySet.\"Name\" IS NULL OR mdssApprovalCategorySet.\"IsEnabled\" = '0' THEN 0 "
            + "    ELSE 1 "
            + "END as isValid "
            + "FROM TABLE( "
            + "    SYS.ODCIVARCHAR2LIST( "
            + "        :approvalCategories "
            + "    ) "
            + ") excelApprovalCategorySet "
            + "LEFT JOIN MDSS.\"DrugToDiagnosisApprovalCategory\" mdssApprovalCategorySet on"
            + " excelApprovalCategorySet.COLUMN_VALUE = mdssApprovalCategorySet.\"Name\"",
        nativeQuery = true)
	List<DrugToDiagnosisApprovalCategoryModel> findByApprovalCategories(
			@Param("approvalCategories") List<String> approvalCategories);

}
