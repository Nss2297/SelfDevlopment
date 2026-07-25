package com.waseel.pbm.pbmadminservice.repository.mdss;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.pbmadminservice.model.customization.ServiceCodeModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;

public interface DrugServiceRepository extends JpaRepository<DrugService, Long>, JpaSpecificationExecutor<DrugService> {

	Optional<DrugService> findByOtherCodesValueAndDrugListId(String sfdaCode, Long drugListId);

	void deleteByOtherCodesValue(String sfdaCode);

	@Query(value = "SELECT excelServiceCodeSet.COLUMN_VALUE as serviceCode, " + "CASE "
			+ "    WHEN mdssServiceCodeSet.\"Other_Codes_Value\" IS NULL THEN 0 " + "    ELSE 1 " + "END as isValid "
			+ "FROM TABLE( " + "    SYS.ODCIVARCHAR2LIST( " + "        :serviceCodes " + "    ) "
			+ ") excelServiceCodeSet " + "LEFT JOIN MDSS.\"DrugService\" mdssServiceCodeSet on"
			+ " excelServiceCodeSet.COLUMN_VALUE = mdssServiceCodeSet.\"Other_Codes_Value\""
			+ " AND mdssServiceCodeSet.\"DrugListId\" = :drugListId", nativeQuery = true)
	List<ServiceCodeModel> findByServiceCodes(@Param("serviceCodes") List<String> serviceCodes,
			@Param("drugListId") Long drugListId);

	List<DrugService> findByOtherCodesValueInAndDrugListId(Set otherCodesValue, Long drugListId);
}
