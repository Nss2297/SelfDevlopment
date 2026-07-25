package com.waseel.prescription.repository.prescriptionservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.model.inquiry.detail.ServiceInquiryResponse;
import com.waseel.prescription.model.prescription.ServiceResponse;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;

@Repository
public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, Long> {

	Optional<ServiceInfo> findByRequestIdAndDrugCode(String requestId, String drugCode);

	Optional<ServiceInfo> findByRequestIdAndScientificCode(String requestId, String scientificCode);

	Optional<ServiceInfo> findByRequestIdAndScientificCodeAndIsDeleted(String requestId, String scientificCode,
			boolean isDeleted);

	List<ServiceInfo> findByIsDeletedAndRequestId(boolean isDeleted, String requestId);

	@Query("SELECT si from ServiceInfo si, ServiceResponseInfo sri" + " WHERE si.id = sri.serviceID"
			+ " AND si.requestId = sri.requestId" + " AND si.isDeleted = '0'" + " AND sri.status = :status"
			+ " AND si.requestId = :requestId")
	List<ServiceInfo> findByIsNotDeletedAndRequestIdAndStatus(@Param("requestId") String requestId,
			@Param("status") String status);

	@Query("SELECT " + " new com.waseel.prescription.model.prescription.ServiceResponse(" 
			+ "  si.scientificCode, si.drugCode, si.unitType"
			+ " ,si.unitPrice,si.quantity,sri.requestedAmount," + " sri.approvedAmount,sri.discount,sri.patientShare"
			+ " ,sri.net,sri.status,sri.statusDescription" + " )" + " FROM  ServiceInfo si, ServiceResponseInfo sri "
			+ " WHERE" + " si.id = sri.serviceID AND" + " si.isDeleted= '0' AND" + " si.requestId = :RequestId")
	List<ServiceResponse> getDetailsOfInquiry(@Param("RequestId") String requestId);

	Optional<ServiceInfo> findByRequestIdAndDrugCodeAndIsDeleted(String requestId, String drugCode, boolean isDeleted);

	@Query("SELECT si FROM ServiceInfo si, "
			+ "ServiceResponseInfo sinfo WHERE si.requestId=sinfo.requestId and si.id=sinfo.serviceID and "
			+ "si.requestId = (SELECT requestId FROM PrescriptionRequest "
			+ "WHERE ePrescriptionReferenceNumber = :EprescriptionReferenceNumber) and si.isDeleted = :IsDeleted and (sinfo.status='APPROVED' or sinfo.status='PARTIALLY_DISPENSED')")
	List<ServiceInfo> findByRequestIdAndIsDeleted(
			@Param("EprescriptionReferenceNumber") String ePrescriptionReferenceNumber,
			@Param("IsDeleted") boolean isDeleted);

	List<ServiceInfo> findDrugsByRequestIdAndIsDeleted(@Param("requestId") String requestId,
			@Param("isDeleted") boolean isDeleted);

	@Query("SELECT si from ServiceInfo si, ServiceResponseInfo sri" + " WHERE si.id = sri.serviceID"
			+ " AND si.requestId = sri.requestId" + " AND si.isDeleted = '0'"
			+ " AND LOWER(sri.status) = LOWER(:status)"
			+ " AND si.requestId = :requestId"
			+ " AND si.scientificCode IS NOT NULL")
	Optional<List<ServiceInfo>> findDrugsByRequestIdAndIsDeletedAndScientificCodeNotNullAndStatus(
			@Param("requestId") String requestId,@Param("status") String status);

	@Query("SELECT si from ServiceInfo si, ServiceResponseInfo sri" + " WHERE si.id = sri.serviceID"
			+ " AND si.requestId = sri.requestId" + " AND si.isDeleted = '0'"
			+ " AND si.requestId = :requestId"
			+ " AND si.scientificCode IS NOT NULL")
	Optional<List<ServiceInfo>> findDrugsByRequestIdAndIsDeletedAndScientificCodeNotNull(
			@Param("requestId") String requestId);

    @Query("SELECT si from ServiceInfo si, ServiceResponseInfo sri" 
    		+ " WHERE si.id = sri.serviceID"
            + " AND si.requestId = sri.requestId" 
    		+ " AND si.isDeleted = '0'" 
            + " AND sri.status IN (:statuses)"
            + " AND si.requestId = :requestId")
    List<ServiceInfo> findByIsDeletedAndRequestIdAndStatuses(@Param("requestId") String requestId,
            @Param("statuses") List<String> statuses);
    
	@Query("SELECT si from ServiceInfo si, ServiceResponseInfo sri" + " WHERE si.id = sri.serviceID"
			+ " AND si.requestId = sri.requestId" + " AND si.isDeleted = '0'"
			+ " AND LOWER(sri.status) = LOWER(:status)" 
			+ " AND si.requestId = :requestId"
			+ " AND si.scientificCode IS NULL")
	Optional<List<ServiceInfo>> findDrugsByRequestIdAndIsDeletedAndScientificCodeNullAndStatus(
			@Param("requestId") String requestId,@Param("status") String status);

	Optional<ServiceInfo> findByRequestIdAndDrugCodeAndDrugListId(String requestId, String drugCode, Long drugListId);

	Optional<ServiceInfo> findByRequestIdAndScientificCodeAndDrugListId(String requestId, String scientificCode,
			Long drugListId);
	
	Optional<List<ServiceInfo>> findByRequestIdAndIsDeletedAndScientificCodeNotNull(String requestId,
			Boolean isDeleted);

	@Query("SELECT new com.waseel.prescription.model.inquiry.detail.ServiceInquiryResponse("
			+ "  si.scientificCode, si.drugCode, si.unitType" + " ,si.unitPrice,si.quantity,sri.requestedAmount,"
			+ " sri.approvedAmount,sri.discount,sri.patientShare" + " ,sri.net,sri.status,sri.statusDescription" + " )"
			+ " FROM  ServiceInfo si, ServiceResponseInfo sri " + " WHERE" + " si.id = sri.serviceID AND"
			+ " si.isDeleted= '0' AND" + " si.requestId = :RequestId")
	List<ServiceInquiryResponse> getServiceDetailsOfInquiry(@Param("RequestId") String requestId);
}
