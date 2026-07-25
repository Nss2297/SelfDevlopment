package com.waseel.prescription.repository.mdss;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.prescription.model.common.ScientificCodeModel;
import com.waseel.prescription.model.common.ServiceCodeModel;
import com.waseel.prescription.persist.mdss.DrugService;

public interface DrugServiceRepository extends JpaRepository<DrugService, Long>,
        JpaSpecificationExecutor<DrugService> {

    @Query(value = "with effectiveDrugList as (" +
            "    select \"DrugListId\" from \"DrugServiceMetaData\"" +
            "    where SYSDATE >= \"Effective_Date\"" +
            "    order by \"Effective_Date\" desc" +
            "    fetch next 1 rows only" +
            ")" +
            "SELECT ds.* from \"DrugService\" ds" +
            "    JOIN effectiveDrugList dl on dl.\"DrugListId\" = ds.\"DrugListId\"" +
            "    where ds.\"Other_Codes_Value\"=:drugCode", nativeQuery = true)
    Optional<DrugService> findByOtherCodesValue(String drugCode);

    Optional<DrugService> findFirstByOtherCodesValue(String drugCode);

    @Query(value = "with effectiveDrugList as (" +
            "    select \"DrugListId\" from \"DrugServiceMetaData\"" +
            "    where SYSDATE >= \"Effective_Date\"" +
            "    order by \"Effective_Date\" desc" +
            "    fetch next 1 rows only" +
            ")"
            + "SELECT ds.* "
            + "FROM \"DrugService\" ds "
            + "JOIN effectiveDrugList dl on dl.\"DrugListId\" = ds.\"DrugListId\" "
            + "where ds.\"ScientificCode\"=:scientificCode fetch next 1 rows only", nativeQuery = true)
    Optional<DrugService> findByScientificCode(String scientificCode);
    
    @Query(value = "with effectiveDrugList as (" +
            "    select \"DrugListId\" from \"DrugServiceMetaData\"" +
            "    where SYSDATE >= \"Effective_Date\"" +
            "    order by \"Effective_Date\" desc" +
            "    fetch next 1 rows only" +
            ")"
            + "SELECT ds.* "
            + "FROM \"DrugService\" ds "
            + "JOIN effectiveDrugList dl on dl.\"DrugListId\" = ds.\"DrugListId\" "
            + "WHERE ds.\"ScientificCode\"=:code "
            + " OR ds.\"Other_Codes_Value\" = :code"
            + " FETCH next 1 rows only", nativeQuery = true)
    Optional<DrugService> findByScientificCodeOrOtherCodesValue(String code);

    Optional<List<DrugService>> findByDrugListIdAndScientificCodeIn(Long activeDrugListId, List<String> scientificCodes);

	Optional<List<DrugService>> findByDrugListIdAndOtherCodesValueIn(Long activeDrugListId,
			List<String> brandDrugsWithScientificCode);
	
	Optional<List<DrugService>> findByOtherCodesValueAndDrugListIdIn(String otherCodesValue, Set<Long> drugListId);
	
	Optional<List<DrugService>> findByDrugListId(Long drugListId);
	
	Optional<DrugService> findByOtherCodesValueAndDrugListId(String otherCodesValue, Long drugListId);

	@Query(value = "SELECT excelServiceCodeSet.COLUMN_VALUE as serviceCode, " + "CASE "
			+ "    WHEN mdssServiceCodeSet.\"Other_Codes_Value\" IS NULL THEN 0 " + "    ELSE 1 " + "END as isValid "
			+ "FROM TABLE( " + "    SYS.ODCIVARCHAR2LIST( " + "        :serviceCodes " + "    ) "
			+ ") excelServiceCodeSet " + "LEFT JOIN MDSS.\"DrugService\" mdssServiceCodeSet on"
			+ " excelServiceCodeSet.COLUMN_VALUE = mdssServiceCodeSet.\"Other_Codes_Value\""
			+ " AND mdssServiceCodeSet.\"DrugListId\" = :drugListId", nativeQuery = true)
	List<ServiceCodeModel> findByServiceCodes(@Param("serviceCodes") List<String> serviceCodes,
			@Param("drugListId") Long drugListId);
	

	@Query(value = "SELECT DISTINCT excelServiceCodeSet.COLUMN_VALUE as scientificCode, "
			+ "CASE "
			+ "    WHEN mdssServiceCodeSet.\"ScientificCode\" IS NULL THEN 0 " 
			+ "    ELSE 1 " 
			+ "END as isValid "
			+ "FROM TABLE( " 
			+ "    SYS.ODCIVARCHAR2LIST( " 
			+ "        :scientificCodes " 
			+ "    ) "
			+ ") excelServiceCodeSet " 
			+ "LEFT JOIN MDSS.\"DrugService\" mdssServiceCodeSet on"
			+ " excelServiceCodeSet.COLUMN_VALUE = mdssServiceCodeSet.\"ScientificCode\""
			+ " AND mdssServiceCodeSet.\"DrugListId\" = :drugListId", nativeQuery = true)
	List<ScientificCodeModel> findByScientificCodes(@Param("scientificCodes") List<String> scientificCodes,
			@Param("drugListId") Long drugListId);

	@Query(value = "select \"WASEEL_DRUG_ID\" from \"DrugService\" order by \"WASEEL_DRUG_ID\" desc fetch first 1 row only", nativeQuery =  true)
    Long findFirstWaseelDrugId();

}
