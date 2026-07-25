package com.waseel.dssadminservice.repository.mdss;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.dssadminservice.model.customization.ServiceCodeModel;
import com.waseel.dssadminservice.persist.mdss.DrugService;

public interface DrugServiceRepository extends JpaRepository<DrugService, Long> {

	Optional<DrugService> findByWaseelDrugId(Long waseelDrugId);

	Optional<Page<DrugService>> findByDrugListId(Long drugListId, Pageable pageable);

	Optional<List<DrugService>> findByDrugListIdAndOtherCodesValue(Long drugListId, String sfdaCode);

	void deleteByWaseelDrugId(Long waseelDrugId);

	void deleteAllByDrugListId(Long drugListId);

	List<DrugService> findByDrugListId(Long drugListId);
	
	@Query(value = "select \"WASEEL_DRUG_ID\" from \"DrugService\" order by \"WASEEL_DRUG_ID\" desc fetch first 1 row only", nativeQuery =  true)
    Long findFirstWaseelDrugId();
	
	@Query(value = "SELECT last_number FROM user_sequences WHERE sequence_name = 'Seq_WaseelDrugId'", nativeQuery = true)
	Long findLatestWaseelDrugIdFromSequence();
	

	@Query(value = "SELECT excelServiceCodeSet.COLUMN_VALUE as serviceCode, "
				+ "CASE "
				+ "    WHEN mdssServiceCodeSet.\"Other_Codes_Value\" IS NULL THEN 0 "
				+ "    ELSE 1 " + "END as isValid "
				+ "FROM TABLE( " + "    SYS.ODCIVARCHAR2LIST( "
				+ "        :serviceCodes "
				+ "    ) "
				+ ") excelServiceCodeSet "
				+ "LEFT JOIN MDSS.\"DrugService\" mdssServiceCodeSet on"
				+ " excelServiceCodeSet.COLUMN_VALUE = mdssServiceCodeSet.\"Other_Codes_Value\""
				+ " AND mdssServiceCodeSet.\"DrugListId\" = :drugListId", nativeQuery = true)
	List<ServiceCodeModel> findByServiceCodes(@Param("serviceCodes") List<String> serviceCodes,
			@Param("drugListId") Long drugListId);
}
