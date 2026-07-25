//package com.waseel.pbm.pbmadminservice.repository.mdss;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.waseel.pbm.pbmadminservice.model.RequestInfoModel;
//import com.waseel.pbm.pbmadminservice.persist.mdss.MemberInfo;
//
//import java.util.Optional;
//
//@Repository
//public interface MemberInfoRepository extends JpaRepository<MemberInfo,String>{
//	
//	@Query("SELECT distinct new com.waseel.pbm.pbmadminservice.model.RequestInfoModel("
//			+ " r.requestId,m.memberId,s.serviceDate,r.payerId,r.providerId,r.requestStatus"
//			+ "  )"
//			+ " FROM RequestInfo r, ServiceInfo s,MemberInfo m "
//			+ " WHERE r.requestId = s.id.requestId "
//			+ " AND r.requestId = m.requestId"
//			+ " AND r.isDeletedFromProvider = '0'"
//			+ " AND s.isDeletedFromProvider = '0'"
//			+ " AND s.serviceDate BETWEEN  to_date(:DateFrom, 'DD-MM-YY') AND to_date(:DateTo, 'DD-MM-YY')"
//			+ " AND r.payerId like :PayerId%"
//			+ " AND m.memberId like :MemberId%")
//	Page<RequestInfoModel> searchByMemberIdAndPayerIdAndDate(@Param("DateFrom")String dateFrom,
//                                                             @Param("DateTo")String dateTo,
//                                                             @Param("MemberId")String memberId,
//                                                             @Param("PayerId")String payerId, Pageable pageable);
//
//    Optional<MemberInfo> findByRequestId(String requestId);
//}
