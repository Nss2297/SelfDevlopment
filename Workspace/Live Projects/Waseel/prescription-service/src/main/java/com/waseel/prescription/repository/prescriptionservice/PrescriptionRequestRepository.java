package com.waseel.prescription.repository.prescriptionservice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.model.prescription.ProviderPrescriptionDTO;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;

@Repository
public interface PrescriptionRequestRepository extends CrudRepository<PrescriptionRequest, String> {

    Optional<PrescriptionRequest> findByRequestId(String requestId);

    Optional<PrescriptionRequest> findByePrescriptionReferenceNumber(String ePrescriptionReferenceNumber);

    Optional<PrescriptionRequest> findByePrescriptionReferenceNumberAndStatusCode(String ePrescriptionReferenceNumber,
                                                                                  String statusCode);

    Optional<PrescriptionRequest> findByePrescriptionReferenceNumberAndProviderId(String ePrescriptionReferenceNumber,
                                                                                  String providerId);
    
    String COMMON_WHERE_CONDITION = "FROM\n" +
            "         \"PRESCRIPTION_SERVICE\".\"PrescriptionRequest\" pr\n" +
            "    INNER JOIN \"PRESCRIPTION_SERVICE\".\"MemberInfo\" mi ON pr.\"RequestID\" = mi.\"RequestID\"\n" +
            "    INNER JOIN \"HIRA\".\"SwitchAccount\" sa ON pr.\"ProviderID\" = CAST(sa.\"SwitchAccountId\" AS\n" +
            "    varchar(50))\n" +
            "WHERE\n" +
            "    pr.\"PayerID\" IN (:payerIds)\n" +
            "    AND (:status IS NULL OR pr.\"StatusCode\" = :status)\n" +
            "    AND (:providerId IS NULL OR pr.\"ProviderID\" = :providerId)\n" +
            "    AND (:referenceNumber IS NULL OR pr.\"EPrescriptionReferenceNumber\" = :referenceNumber)\n" +
            "    AND (:idNumber IS NULL OR CAST(mi.\"IDNumber\" as varchar(50)) = :idNumber)\n" +
            "    AND (:memberName IS NULL OR mi.\"MemberName\" = :memberName)\n" +
            "    AND (:fromDate IS NULL OR pr.\"SendDateTime\" >= :fromDate)\n" +
            "    AND (:endDate IS NULL OR pr.\"SendDateTime\" <= :endDate)\n";

    @Query(value = "SELECT\n" +
            "    pr.\"EPrescriptionReferenceNumber\",\n" +
            "    mi.\"IDNumber\",\n" +
            "    pr.\"SendDateTime\",\n" +
            "    pr.\"ProviderID\",\n" +
            "    sa.\"Name\",\n" +
            "    pr.\"StatusCode\",\n" +
            "    mi.\"MemberName\"\n" +
            COMMON_WHERE_CONDITION +
            "    order by pr.\"SendDateTime\" desc\n" +
            "offset :pageNumber*:pageSize rows fetch next :pageSize rows only",
            nativeQuery = true)
    List<Object[]> getProvidersListWithPaginationWithNativeQuery(
            @Param("status") String status,
            @Param("providerId") String providerId,
            @Param("referenceNumber") String referenceNumber,
            @Param("payerIds") List<String> payerId,
            @Param("pageSize") int pageSize,
            @Param("pageNumber") int pageNumber,
            @Param("fromDate") LocalDate fromDate,
            @Param("endDate") LocalDate endDate,
            @Param("idNumber") String idNumber,
            @Param("memberName") String memberName);

    @Query(value = "SELECT\n" +
            "    COUNT(1)\n" +
            COMMON_WHERE_CONDITION,
            nativeQuery = true)
    Integer getProvidersListWithPaginationCountWithNativeQuery(
            @Param("status") String status,
            @Param("providerId") String providerId,
            @Param("referenceNumber") String referenceNumber,
            @Param("payerIds") List<String> payerId,
            @Param("fromDate") LocalDate fromDate,
            @Param("endDate") LocalDate endDate,
            @Param("idNumber") String idNumber,
            @Param("memberName") String memberName);
    
    String filterParameters =" (:status IS NULL OR pm.\"StatusCode\"= :status)\r\n"
    		+ "    AND (:providerId IS NULL OR pm.\"ProviderID\"= :providerId)\r\n"
    		+ "    AND (:fromDate IS NULL OR pm.\"SendDateTime\" >= to_date(:fromDate, 'DD-MM-YYYY'))\r\n"
    		+ "    AND (:endDate IS NULL OR TRUNC(pm.\"SendDateTime\") <= to_date(:endDate, 'DD-MM-YYYY'))\r\n"
    		+ "    AND (:memberId IS NULL OR mi.\"MemberID\" = :memberId)\r\n"
    		+ "    AND (:idNumber IS NULL OR mi.\"IDNumber\"= :idNumber)\r\n"
    		+ "    AND (:policyNumber IS NULL OR mi.\"PolicyNumber\" = :policyNumber)\r\n"
    		+ "    AND (:memberName IS NULL OR (mi.\"MemberName\" LIKE (%:memberName%) OR  mi.\"MemberID\" LIKE (%:memberName%)))\r\n "
    		+"    AND (:referenceNumber IS NULL OR pm.\"EPrescriptionReferenceNumber\" = :referenceNumber)\r\n"
    		;
    
    @Query(value = "SELECT\r\n"
    		+ "    pm.\"EPrescriptionReferenceNumber\" AS \"referenceNo\",\r\n"
    		+ "    pm.\"StatusCode\" AS \"status\", \r\n"
    		+ "    pm.\"SendDateTime\" AS \"dateAndTime\",\r\n"
    		+ "    mi.\"MemberID\" AS \"memberId\",\r\n"
    		+ "    mi.\"IDNumber\" AS \"idNumber\",\r\n"
    		+ "    mi.\"PolicyNumber\" AS \"policyNumber\",\r\n"
    		+ "    mi.\"MemberName\" AS \"memberName\",\r\n"
    		+ "    pg.\"PayerName\" AS \"insurance\",\r\n"
    		+ "    pg.\"PayerId\" AS \"payerId\"\r\n"
    		+ "FROM\r\n"
    		+ "    \"PRESCRIPTION_SERVICE\".\"PrescriptionRequest\" pm\r\n"
    		+ "    INNER JOIN \"PRESCRIPTION_SERVICE\".\"MemberInfo\" mi ON pm.\"RequestID\" = mi.\"RequestID\"\r\n"
    		+ "    INNER JOIN PAYER_ID_MAPPING pim ON pm.\"PayerID\" = pim.mapped_payer_id\r\n"
    		+ "    INNER JOIN \"PBM_BUSINESS_RULES\".\"PayerConfiguration\" pg ON pim.payer_id = pg.\"PayerId\"\r\n"
    		+ "WHERE\r\n"
    		+ filterParameters
    		+ "ORDER BY\r\n"
    		+ "    pm.\"SendDateTime\" DESC" +
    		" OFFSET :offset rows fetch next :maxResults rows only"
    		, nativeQuery = true)
	List<ProviderPrescriptionDTO> getProviderPrescriptionPaginatedAPI(
			@Param("referenceNumber") String referenceNumber, @Param("memberId") String memberId,
			@Param("idNumber") String idNumber, @Param("memberName") String memberName,
			@Param("policyNumber") String policyNumber, @Param("status") String status,
			@Param("fromDate") String fromDate, @Param("endDate") String endDate,
			@Param("providerId") String providerId, @Param("offset") Integer offset,
			@Param("maxResults") Integer maxResults);
    
    @Query(value = "SELECT COUNT(1) "
    		+ "FROM\r\n"
    		+ "    \"PRESCRIPTION_SERVICE\".\"PrescriptionRequest\" pm\r\n"
    		+ "    INNER JOIN \"PRESCRIPTION_SERVICE\".\"MemberInfo\" mi ON pm.\"RequestID\" = mi.\"RequestID\"\r\n"
    		+ "    INNER JOIN PAYER_ID_MAPPING pim ON pm.\"PayerID\" = pim.mapped_payer_id\r\n"
    		+ "    INNER JOIN \"PBM_BUSINESS_RULES\".\"PayerConfiguration\" pg ON pim.payer_id = pg.\"PayerId\"\r\n"
    		+ "WHERE\r\n"
    		+ filterParameters
    		, nativeQuery = true)
    Long getProviderPrescriptionPaginatedAPICount(
			@Param("referenceNumber") String referenceNumber, @Param("memberId") String memberId,
			@Param("idNumber") String idNumber, @Param("memberName") String memberName,
			@Param("policyNumber") String policyNumber, @Param("status") String status,
			@Param("fromDate") String fromDate, @Param("endDate") String endDate,
			@Param("providerId") String providerId);
    
    String activePrescriptionFilterParameters =" (pm.\"StatusCode\" IN ('APPROVED', 'PARTIAL_APPROVED', 'PARTIAL_DISPENSED'))"
    		+ "    AND (:providerId IS NULL OR pm.\"ProviderID\"= :providerId)\r\n"
    		+ "    AND (:fromDate IS NULL OR pm.\"SendDateTime\" >= to_date(:fromDate, 'DD-MM-YYYY'))\r\n"
    		+ "    AND (:endDate IS NULL OR TRUNC(pm.\"SendDateTime\") <= to_date(:endDate, 'DD-MM-YYYY'))\r\n"
    		+ "    AND (:memberId IS NULL OR mi.\"MemberID\" = :memberId)\r\n"
    		+ "    AND (:idNumber IS NULL OR mi.\"IDNumber\"= :idNumber)\r\n"
    		+ "    AND (:policyNumber IS NULL OR mi.\"PolicyNumber\" = :policyNumber)\r\n"
    		+ "    AND (:memberName IS NULL OR (mi.\"MemberName\" LIKE (%:memberName%) OR  mi.\"MemberID\" LIKE (%:memberName%)))\r\n "
    		+"    AND (:referenceNumber IS NULL OR pm.\"EPrescriptionReferenceNumber\" = :referenceNumber)\r\n"
    		;
    @Query(value = "SELECT\r\n"
    		+ "    pm.\"EPrescriptionReferenceNumber\" AS \"referenceNo\",\r\n"
    		+ "    pm.\"StatusCode\" AS \"status\", \r\n"
    		+ "    pm.\"SendDateTime\" AS \"dateAndTime\",\r\n"
    		+ "    mi.\"MemberID\" AS \"memberId\",\r\n"
    		+ "    mi.\"IDNumber\" AS \"idNumber\",\r\n"
    		+ "    mi.\"PolicyNumber\" AS \"policyNumber\",\r\n"
    		+ "    mi.\"MemberName\" AS \"memberName\",\r\n"
    		+ "    pg.\"PayerName\" AS \"insurance\",\r\n"
    		+ "    pg.\"PayerId\" AS \"payerId\"\r\n"
    		+ "FROM\r\n"
    		+ "    \"PRESCRIPTION_SERVICE\".\"PrescriptionRequest\" pm\r\n"
    		+ "    INNER JOIN \"PRESCRIPTION_SERVICE\".\"MemberInfo\" mi ON pm.\"RequestID\" = mi.\"RequestID\"\r\n"
    		+ "    INNER JOIN PAYER_ID_MAPPING pim ON pm.\"PayerID\" = pim.mapped_payer_id\r\n"
    		+ "    INNER JOIN \"PBM_BUSINESS_RULES\".\"PayerConfiguration\" pg ON pim.payer_id = pg.\"PayerId\"\r\n"
    		+ "WHERE\r\n"
    		+ activePrescriptionFilterParameters
    		+ "ORDER BY\r\n"
    		+ "    pm.\"SendDateTime\" DESC" +
    		" OFFSET :offset rows fetch next :maxResults rows only"
    		, nativeQuery = true)
	List<ProviderPrescriptionDTO> getProviderPrescriptionPaginatedAPIForActivePrescription(
			@Param("referenceNumber") String referenceNumber, @Param("memberId") String memberId,
			@Param("idNumber") String idNumber, @Param("memberName") String memberName,
			@Param("policyNumber") String policyNumber, @Param("fromDate") String fromDate,
			@Param("endDate") String endDate, @Param("providerId") String providerId, @Param("offset") Integer offset,
			@Param("maxResults") Integer maxResults);
    
    @Query(value = "SELECT COUNT(1) "
    		+ "FROM\r\n"
    		+ "    \"PRESCRIPTION_SERVICE\".\"PrescriptionRequest\" pm\r\n"
    		+ "    INNER JOIN \"PRESCRIPTION_SERVICE\".\"MemberInfo\" mi ON pm.\"RequestID\" = mi.\"RequestID\"\r\n"
    		+ "    INNER JOIN PAYER_ID_MAPPING pim ON pm.\"PayerID\" = pim.mapped_payer_id\r\n"
    		+ "    INNER JOIN \"PBM_BUSINESS_RULES\".\"PayerConfiguration\" pg ON pim.payer_id = pg.\"PayerId\"\r\n"
    		+ "WHERE\r\n"
    		+ activePrescriptionFilterParameters
    		, nativeQuery = true)
	Long getProviderPrescriptionPaginatedAPICountForActivePrescription(@Param("referenceNumber") String referenceNumber,
			@Param("memberId") String memberId, @Param("idNumber") String idNumber,
			@Param("memberName") String memberName, @Param("policyNumber") String policyNumber,
			@Param("fromDate") String fromDate, @Param("endDate") String endDate,
			@Param("providerId") String providerId);
    
    String pharmacyUserFilterParameters ="  (pm.\"StatusCode\" IN ('APPROVED', 'PARTIAL_APPROVED', 'PARTIAL_DISPENSED', 'DISPENSED'))"
    		+ "    AND (:providerId IS NULL OR pm.\"ProviderID\"= :providerId)\r\n"
    		+ "    AND (:fromDate IS NULL OR pm.\"SendDateTime\" >= to_date(:fromDate, 'DD-MM-YYYY'))\r\n"
    		+ "    AND (:endDate IS NULL OR TRUNC(pm.\"SendDateTime\") <= to_date(:endDate, 'DD-MM-YYYY'))\r\n"
    		+ "    AND (:memberId IS NULL OR mi.\"MemberID\" = :memberId)\r\n"
    		+ "    AND (:idNumber IS NULL OR mi.\"IDNumber\"= :idNumber)\r\n"
    		+ "    AND (:policyNumber IS NULL OR mi.\"PolicyNumber\" = :policyNumber)\r\n"
    		+ "    AND (:memberName IS NULL OR (mi.\"MemberName\" LIKE (%:memberName%) OR  mi.\"MemberID\" LIKE (%:memberName%)))\r\n "
    		+"    AND (:referenceNumber IS NULL OR pm.\"EPrescriptionReferenceNumber\" = :referenceNumber)\r\n"
    		;
    @Query(value = "SELECT\r\n"
    		+ "    pm.\"EPrescriptionReferenceNumber\" AS \"referenceNo\",\r\n"
    		+ "    pm.\"StatusCode\" AS \"status\", \r\n"
    		+ "    pm.\"SendDateTime\" AS \"dateAndTime\",\r\n"
    		+ "    mi.\"MemberID\" AS \"memberId\",\r\n"
    		+ "    mi.\"IDNumber\" AS \"idNumber\",\r\n"
    		+ "    mi.\"PolicyNumber\" AS \"policyNumber\",\r\n"
    		+ "    mi.\"MemberName\" AS \"memberName\",\r\n"
    		+ "    pg.\"PayerName\" AS \"insurance\",\r\n"
    		+ "    pg.\"PayerId\" AS \"payerId\"\r\n"
    		+ "FROM\r\n"
    		+ "    \"PRESCRIPTION_SERVICE\".\"PrescriptionRequest\" pm\r\n"
    		+ "    INNER JOIN \"PRESCRIPTION_SERVICE\".\"MemberInfo\" mi ON pm.\"RequestID\" = mi.\"RequestID\"\r\n"
    		+ "    INNER JOIN PAYER_ID_MAPPING pim ON pm.\"PayerID\" = pim.mapped_payer_id\r\n"
    		+ "    INNER JOIN \"PBM_BUSINESS_RULES\".\"PayerConfiguration\" pg ON pim.payer_id = pg.\"PayerId\"\r\n"
    		+ "WHERE\r\n"
    		+ pharmacyUserFilterParameters
    		+ "ORDER BY\r\n"
    		+ "    pm.\"SendDateTime\" DESC" +
    		" OFFSET :offset rows fetch next :maxResults rows only"
    		, nativeQuery = true)
	List<ProviderPrescriptionDTO> getProviderPrescriptionPaginatedAPIForPharmacyUser(
			@Param("referenceNumber") String referenceNumber, @Param("memberId") String memberId,
			@Param("idNumber") String idNumber, @Param("memberName") String memberName,
			@Param("policyNumber") String policyNumber, @Param("fromDate") String fromDate,
			@Param("endDate") String endDate, @Param("providerId") String providerId, @Param("offset") Integer offset,
			@Param("maxResults") Integer maxResults);
    
    @Query(value = "SELECT COUNT(1) "
    		+ "FROM\r\n"
    		+ "    \"PRESCRIPTION_SERVICE\".\"PrescriptionRequest\" pm\r\n"
    		+ "    INNER JOIN \"PRESCRIPTION_SERVICE\".\"MemberInfo\" mi ON pm.\"RequestID\" = mi.\"RequestID\"\r\n"
    		+ "    INNER JOIN PAYER_ID_MAPPING pim ON pm.\"PayerID\" = pim.mapped_payer_id\r\n"
    		+ "    INNER JOIN \"PBM_BUSINESS_RULES\".\"PayerConfiguration\" pg ON pim.payer_id = pg.\"PayerId\"\r\n"
    		+ "WHERE\r\n"
    		+ pharmacyUserFilterParameters
    		, nativeQuery = true)
	Long getProviderPrescriptionPaginatedAPICountForPharmacyUser(@Param("referenceNumber") String referenceNumber,
			@Param("memberId") String memberId, @Param("idNumber") String idNumber,
			@Param("memberName") String memberName, @Param("policyNumber") String policyNumber,
			@Param("fromDate") String fromDate, @Param("endDate") String endDate,
			@Param("providerId") String providerId);
}
