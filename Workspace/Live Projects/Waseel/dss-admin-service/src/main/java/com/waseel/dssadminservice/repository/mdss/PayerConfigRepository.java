package com.waseel.dssadminservice.repository.mdss;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.dssadminservice.model.customization.PayerConfigModel;
import com.waseel.dssadminservice.persist.mdss.PayerConfig;

@Repository
public interface PayerConfigRepository extends JpaRepository<PayerConfig, String> {

	Optional<PayerConfig> findByPayerIdAndPbmPayerTypeAndIsEnabled(String payerId, String pbmPayerType,
			Boolean isEnabled);
	
	@Query(value = "SELECT excelPayerIdSet.COLUMN_VALUE as payerId, "
            + "CASE "
            + "    WHEN mdssPayerConfigSet.\"PayerId\" IS NULL OR mdssPayerConfigSet.\"isEnabled\" = '0' THEN 0 "
            + "    ELSE 1 "
            + "END as isValid "
            + "FROM TABLE( "
            + "    SYS.ODCIVARCHAR2LIST( "
            + "        :payerIds "
            + "    ) "
            + ") excelPayerIdSet "
            + "LEFT JOIN MDSS.\"PayerConfig\" mdssPayerConfigSet on"
            + " excelPayerIdSet.COLUMN_VALUE = mdssPayerConfigSet.\"PayerId\"",
	        nativeQuery = true)
	List<PayerConfigModel> findByPayerIds(@Param("payerIds") List<String> payerIds);
}
