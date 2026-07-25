package com.waseel.prescription.repository.prescriptionservice;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.model.prescription.CustomizationRequests;
import com.waseel.prescription.model.prescription.EligibilityValidationModel;
import com.waseel.prescription.model.prescription.MedicalValidations;
import com.waseel.prescription.model.prescription.RejectionReasons;
import com.waseel.prescription.model.prescription.ServiceRejectionDTO;
import com.waseel.prescription.model.prescription.ServiceRejections;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;

@Repository
public interface ServiceRejectionRepository extends CrudRepository<ServiceRejection, Long> {

	Optional<List<ServiceRejection>> findByRequestIdAndServiceResponseId(String requestId, long serviceResponseId);

	@Query("SELECT new com.waseel.prescription.model.prescription.MedicalValidations("
			+ " model.drugCode,model.denialCode,model.rejectionReason" + " ) " 
			+ " FROM ServiceRejection model "
			+ " WHERE model.requestId = :requestId " 
			+ " AND model.drugCode = :drugCode")
	List<MedicalValidations> findByRequestIdAndDrugCode(@Param("requestId") String requestId,
			@Param("drugCode") String drugCode);

	@Query("SELECT new com.waseel.prescription.persist.prescriptionservice.ServiceRejection("
			+ " model.denialCode,model.rejectionReason,model.eligibilityReferenceNumber" + " ) "
			+ " FROM ServiceRejection model "
			+ " WHERE model.requestId = (SELECT requestId FROM PrescriptionRequest where ePrescriptionReferenceNumber = :ePrescriptionRefNumber)")
	List<ServiceRejection> findByEprescriptionReferenceNumber(
			@Param("ePrescriptionRefNumber") String ePrescriptionReferenceNumber);

	@Query("SELECT new com.waseel.prescription.model.prescription.EligibilityValidationModel("
			+ " model.eligibilityReferenceNumber,model.denialCode,model.rejectionReason" + " ) "
			+ " FROM ServiceRejection model WHERE model.requestId = :requestId")
	List<EligibilityValidationModel> findByRequestId(@Param("requestId") String requestId);

	Optional<ServiceRejection> findByServiceResponseId(long serviceResponseId);

	Optional<List<ServiceRejection>> findByRequestIdAndIsModifiedByPayer(String requestId, boolean isModifiedByPayer);

	Optional<List<ServiceRejection>> findByRequestIdAndDrugCodeAndIsModifiedByPayer(String requestId, String drugCode,
			boolean isModifiedByPayer);
	
	@Query("SELECT model"
			+ " FROM ServiceRejection model"
			+ " WHERE model.requestId = :requestId"
			+ " AND model.isModifiedByPayer = '0'")
	List<ServiceRejection> getAllRejectionsByRequestIdAndDenialCode(String requestId);


	@Query(nativeQuery = true, value = "SELECT"
			+ "    sr.\"DrugCode\"        AS \"drugCode\","
			+ "    sr.\"DenialCode\"      AS \"denialCode\","
			+ "    ds.\"Display\"         AS \"drugName\","
			+ "    sr.\"RejectionReason\" AS \"rejectionReason\","
			+ "    cd.is_customizable  AS \"isCustomizable\","
			+ "    cd.key_value  AS \"keyValue\""
			+ "FROM"
			+ "    (SELECT * FROM PRESCRIPTION_SERVICE.\"ServiceRejection\" WHERE lower(\"RequestID\") = ?1 AND \"IS_MODIFIED_BY_PAYER\" = ?2) sr "
			+ "LEFT JOIN MDSS.\"DrugService\" ds ON sr.\"DrugCode\" = ds.\"Other_Codes_Value\""
			+ "LEFT JOIN (SELECT "
			+ "                crd.CUSTOMIZATION_VALUE as is_customizable, crm.drug_code  as drug_code, crdk.customization_value AS key_value "
			+ "            FROM "
			+ "                (SELECT "
			+ "                    customization_requests_id, drug_code "
			+ "                 FROM "
			+ "                    MDSS.CUSTOMIZATION_REQUEST_METADATA "
			+ "                 WHERE "
			+ "                    IS_DELETED = ?3 "
			+ "                    AND PAYER_ID = ?4 "
			+ "                    AND STATUS = 'Pending'"
			+ "                )crm "
			+ "            LEFT JOIN "
			+ "                (SELECT "
			+ "                    CUSTOMIZATION_VALUE, customization_request_id "
			+ "                 FROM "
			+ "                    mdss.CUSTOMIZATION_REQUEST_DETAILS "
			+ "                WHERE "
			+ "                    CUSTOMIZATION_KEY='CUSTOMIZABLE' "
			+ "                ) crd on crm.customization_requests_id=crd.customization_request_id"
			+ "            LEFT JOIN "
			+ "                (SELECT "
			+ "                    CUSTOMIZATION_VALUE, customization_request_id "
			+ "                 FROM "
			+ "                    mdss.CUSTOMIZATION_REQUEST_DETAILS "
			+ "                WHERE "
			+ "                    CUSTOMIZATION_KEY in ('ICD_CODE', 'GENDER', 'INTERACTED_DRUG_CODE') "
			+ "                ) crdk ON crm.customization_requests_id = crdk.customization_request_id"
			+ "         )cd ON sr.\"DrugCode\" = cd.drug_code "
			+ "GROUP BY"
			+ "    sr.\"DrugCode\","
			+ "    sr.\"DenialCode\","
			+ "    ds.\"Display\","
			+ "    sr.\"RejectionReason\","
			+ "    cd.\"IS_CUSTOMIZABLE\","
			+ "    cd.key_value")
	List<ServiceRejections> findByRequestIdAndIsModifiedByPayerAndIsDeletedAndPayerId(String requestId,
			boolean isModifiedByPayer, boolean isDeleted, String payerId);
	
	@Query(nativeQuery = true, value = "with EFFECTIVE_DRUG_LIST as ("
			+ "            select \"DrugListId\" from MDSS.\"DrugServiceMetaData\" "
			+ "            where SYSDATE >= \"Effective_Date\" order by \"Effective_Date\" desc "
			+ "            fetch next 1 rows only) "
			+ "SELECT  "
			+ "    sr.\"DrugCode\" AS \"drugCode\", sr.\"DenialCode\" AS \"denialCode\", ds.\"Display\" AS \"drugName\", sr.\"RejectionReason\" AS \"rejectionReason\", '' AS \"scientificCode\", '' AS \"scientificName\" "
			+ "FROM  "
			+ "    (SELECT \"DrugCode\", \"DenialCode\", \"RejectionReason\" FROM \"ServiceRejection\" WHERE \"RequestID\"=?1 AND \"IS_MODIFIED_BY_PAYER\" = ?2 and \"DrugCode\" IS NOT NULL) sr  "
			+ "LEFT JOIN  "
			+ "   (SELECT mds.\"Display\", mds.\"Other_Codes_Value\"  FROM  mdss.\"DrugService\" mds JOIN EFFECTIVE_DRUG_LIST EDL on mds.\"DrugListId\"=edl.\"DrugListId\") ds ON sr.\"DrugCode\" = ds.\"Other_Codes_Value\" "
			+ "UNION "
			+ "SELECT "
			+ "    '' AS \"drugCode\", sr.\"DenialCode\" AS \"denialCode\", '' AS \"drugName\", sr.\"RejectionReason\" AS \"rejectionReason\", ds.\"ScientificCode\" AS \"scientificCode\", ds.\"Ingredients\" AS \"scientificName\" "
			+ "FROM \r\n"
			+ "    (SELECT SCIENTIFIC_CODE, \"DenialCode\", \"RejectionReason\" FROM \"ServiceRejection\" WHERE \"RequestID\"=?1 AND \"IS_MODIFIED_BY_PAYER\" = ?2 and \"DrugCode\" IS NULL) sr "
			+ "LEFT JOIN "
			+ "   (SELECT mds.\"ScientificCode\", mds.\"Ingredients\"  FROM  mdss.\"DrugService\" mds JOIN EFFECTIVE_DRUG_LIST EDL on mds.\"DrugListId\"=edl.\"DrugListId\" GROUP BY mds.\"ScientificCode\", mds.\"Ingredients\") ds ON sr.SCIENTIFIC_CODE = ds.\"ScientificCode\"")
	List<RejectionReasons> fetchByRequestIdAndIsModifiedByPayer(String requestId, boolean isModifiedByPayer);


	@Query(nativeQuery = true, value = "SELECT"
			+ "    crd.customization_value  AS \"isCustomizable\","
			+ "    crm.drug_code            AS \"drugCode\","
			+ "    crdk.customization_value AS \"keyValue\" "
			+ "FROM"
			+ "    ("
			+ "        SELECT"
			+ "            customization_requests_id,"
			+ "            drug_code"
			+ "        FROM"
			+ "            mdss.customization_request_metadata"
			+ "        WHERE\r\n"
			+ "                is_deleted = ?1"
			+ "            AND payer_id = ?2"
			+ "            AND status in( 'Pending', 'Accepted')"
			+ "    ) crm\r\n"
			+ "    LEFT JOIN ("
			+ "        SELECT "
			+ "            customization_value,"
			+ "            customization_request_id"
			+ "        FROM"
			+ "            mdss.customization_request_details"
			+ "        WHERE"
			+ "            customization_key = 'CUSTOMIZABLE'"
			+ "    ) crd ON crm.customization_requests_id = crd.customization_request_id"
			+ "    LEFT JOIN ("
			+ "        SELECT"
			+ "            customization_value,"
			+ "            customization_request_id"
			+ "        FROM"
			+ "            mdss.customization_request_details"
			+ "        WHERE"
			+ "            customization_key IN ( 'ICD_CODE', 'GENDER', 'INTERACTED_DRUG_CODE' )"
			+ "    ) crdk ON crm.customization_requests_id = crdk.customization_request_id")
	List<CustomizationRequests> fetchByIsDeletedAndPayerId(boolean isDeleted, String payerId);
	
	@Query("SELECT new com.waseel.prescription.persist.prescriptionservice.ServiceRejection("
			+ " model.denialCode,model.rejectionReason)"
			+ " FROM ServiceRejection model"
			+ " WHERE model.requestId = :requestId "
			+ "AND model.serviceResponseId = :serviceResponseId")
	Optional<List<ServiceRejection>> findByServiceResponseIdAndRequestId( Long serviceResponseId,String requestId);
	
	@Query("SELECT new com.waseel.prescription.persist.prescriptionservice.ServiceRejection("
			+ " model.denialCode,model.rejectionReason,model.requestId,model.serviceResponseId, model.id)"
			+ " FROM ServiceRejection model"
			+ " WHERE model.requestId = :requestId "
			+ "AND model.serviceResponseId = :serviceResponseId")
	Optional<List<ServiceRejection>> findServiceRejectionsByResponseIdAndRequestId( Long serviceResponseId,String requestId);
	
	Optional<ServiceRejection> findByRequestIdAndDenialCodeAndScientificCode(String requestId,
			String denialCode, String scientificCode);
	
	@Query("SELECT model.rejectionReason"
			+ " FROM ServiceRejection model"
			+ " WHERE model.requestId = :requestId"
			+ " AND model.isModifiedByPayer = '0'")
	List<String> getAllRejectionsByRequestId(String requestId);

	Optional<ServiceRejectionDTO> findByRequestIdAndDenialCodeAndServiceResponseId(String requestId, String denialCode, Long serviceResponseId);

	@Transactional
	@Modifying
	@Query("DELETE FROM ServiceRejection WHERE requestId = :requestId AND id = :id")
	void deleteByRequestIdAndId(String requestId, Long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM ServiceRejection WHERE requestId = :requestId AND serviceResponseId = :serviceResponseId")
	void deleteByRequestIdAndServiceResponseId(String requestId, Long serviceResponseId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM ServiceRejection sr " +
	        "WHERE sr.requestId = :requestId " +
	        "AND sr.serviceResponseId = :serviceResponseId " +
	        "AND NOT (sr.denialCode LIKE 'PYR_%' OR sr.denialCode LIKE 'BR_%')")
	void deleteByRequestIdAndServiceResponseIdAndDenialCode(String requestId, Long serviceResponseId);
	
	@Query("SELECT model.rejectionReason FROM ServiceRejection model WHERE model.requestId = :requestId")
	List<String> findRejectionReasonByRequestId(@Param("requestId") String requestId);

	@Query(nativeQuery = true, value = "SELECT * FROM \"ServiceRejection\" WHERE \"RequestID\" = ?1 AND \"ServiceResponseID\" = ?2")
	Optional<List<ServiceRejectionDTO>> fetchByRequestIdAndServiceResponseId(String requestId, Long serviceResponseId);

	void deleteByRequestIdAndDenialCodeAndScientificCode(String requestId, String denialCode, String scientificCode);
	
	@Query("SELECT new com.waseel.prescription.model.prescription.MedicalValidations("
			+ " model.drugCode,model.scientificCode,model.denialCode,model.rejectionReason" + " ) " 
			+ " FROM ServiceRejection model "
			+ " WHERE model.requestId = :requestId " 
			+ " AND model.scientificCode = :scientificCode")
	List<MedicalValidations> findByRequestIdAndScientificCode(@Param("requestId") String requestId,
			@Param("scientificCode") String scientificCode);
	
	@Query("SELECT model from ServiceRejection model where requestId = :requestId")
	List<ServiceRejection> findAllServicesByRequestId(@Param("requestId") String requestId);
}
