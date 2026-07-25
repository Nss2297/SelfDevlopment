package com.waseel.prescription.repository.prescriptionservice;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.model.prescription.ServiceDetailsModel;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;

@Repository
public interface ServiceResponseInfoRepository extends CrudRepository<ServiceResponseInfo, Long> {

	List<ServiceResponseInfo> findByRequestId(String requestId);

	Optional<ServiceResponseInfo> findByRequestIdAndServiceID(String requestId, long serviceID);

	@Query("SELECT model FROM ServiceResponseInfo model WHERE model.serviceID IN (:serviceIds)")
	List<ServiceResponseInfo> findByServiceIds(List<Long> serviceIds);

	@Query("SELECT sri from ServiceInfo si, ServiceResponseInfo sri" + " WHERE si.id = sri.serviceID"
			+ " AND si.requestId = sri.requestId" + " AND si.isDeleted = '0'" + " AND si.requestId = :requestId")
	List<ServiceResponseInfo> findByIsNotDeletedDrugAndRequestId(@Param("requestId") String requestId);
	
	@Query("SELECT sri from ServiceInfo si, ServiceResponseInfo sri" + " WHERE si.id = sri.serviceID"
			+ " AND si.requestId = sri.requestId" + " AND si.isDeleted = '0'" + " AND si.requestId = :requestId"
			+ " AND si.drugCode = :drugCode")
	Optional<ServiceResponseInfo> findByRequestIdAndDrugCode(@Param("requestId") String requestId,
			@Param("drugCode") String drugCode);
	
	@Query("SELECT sri from ServiceInfo si, ServiceResponseInfo sri" + " WHERE si.id = sri.serviceID"
			+ " AND si.requestId = sri.requestId" + " AND si.isDeleted = '0'" + " AND si.requestId = :requestId"
			+ " AND si.scientificCode = :scientificCode")
	Optional<ServiceResponseInfo> findByRequestIdAndScientificCode(@Param("requestId") String requestId,
			@Param("scientificCode") String scientificCode);
	
	@Query("SELECT new com.waseel.prescription.model.prescription.ServiceDetailsModel(si.drugCode,sri.status, si.scientificCode)  "
			+ " FROM ServiceInfo si, ServiceResponseInfo sri" 
			+ " WHERE si.id = sri.serviceID"
			+ " AND si.requestId = sri.requestId" 
			+ " AND si.isDeleted = '0'" 
			+ " AND si.requestId = :requestId"
			+ " AND sri.status IN :drugStatus")
	List<ServiceDetailsModel> getIsNotDeletedDrugAndRequestId(@Param("requestId") String requestId,
			Set<String> drugStatus);
	@Query(nativeQuery = true, value = "SELECT"
			+ "    CASE"
			+ "        WHEN service_count.rejected_status = 0"
			+ "             AND service_count.dispensed_status = 0"
			+ "             AND service_count.pending_status = 0"
			+ "             AND service_count.approved_status > 0 THEN" + "            'APPROVED'"
			+ "        WHEN service_count.approved_status > 0" + "             AND service_count.dispensed_status = 0"
			+ "             AND service_count.pending_status = 0"
			+ "             AND service_count.rejected_status > 0 THEN" + "            'PARTIAL_APPROVED'"
			+ "        WHEN service_count.approved_status = 0" + "             AND service_count.dispensed_status = 0"
			+ "             AND service_count.pending_status = 0"
			+ "             AND service_count.rejected_status > 0 THEN" + "            'REJECTED'"
			+ "        WHEN service_count.approved_status = 0" + "             AND service_count.rejected_status >= 0"
			+ "             AND service_count.pending_status = 0"
			+ "             AND service_count.dispensed_status > 0 THEN" + "            'DISPENSED'"
			+ "        WHEN ( service_count.approved_status >= 0"
			+ "               OR service_count.rejected_status >= 0 )"
			+ "             AND service_count.pending_status = 0"
			+ "             AND service_count.dispensed_status > 0 THEN" + "            'PARTIAL_DISPENSED'"
			+ "        WHEN service_count.pending_status > 0 THEN" + "            'PENDING'"
			+ "    END AS \"prescriptionStatusCode\"" + "FROM" + "    (" 
			+ "        SELECT"
			+ "            SUM(decode(\"Status\", 'APPROVED', 1, 0))  AS approved_status,"
			+ "            SUM(decode(\"Status\", 'REJECTED', 1, 0))  AS rejected_status,"
			+ "            SUM(decode(\"Status\", 'DISPENSED', 1, 0)) AS dispensed_status,"
			+ "            SUM(decode(\"Status\", 'PENDING', 1, 0))   AS pending_status" 
			+ "        FROM \"ServiceInfo\" sinfo JOIN  \"ServiceResponseInfo\" srinfo"
			+ "    ON sinfo.\"ID\" = srinfo.\"ServiceID\""
			+ "    AND sinfo.\"IsDeleted\" = '0'"
			+ "    AND srinfo.\"RequestID\" = :requestId"
			+ "    ) service_count")
	String fetchPrescriptionStatusCodeByRequestId(String requestId);
	
	Optional<ServiceResponseInfo> findByRequestIdAndServiceIDAndStatusNot(String requestId, long serviceID, String status);
}
