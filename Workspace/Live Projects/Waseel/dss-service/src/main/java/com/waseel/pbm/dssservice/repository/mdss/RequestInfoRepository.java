package com.waseel.pbm.dssservice.repository.mdss;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.pbm.dssservice.persist.mdss.RequestInfo;

import feign.Param;

@Repository
public interface RequestInfoRepository extends CrudRepository<RequestInfo, Long> {

    RequestInfo findByRequestId(String requestId);

    @Query("Select model from RequestInfo model where model.requestId = :requestId AND model.isDeletedFromProvider = 0")
    RequestInfo findExistsRequestByRequestId(String requestId);

    @Transactional
    @Modifying
    @Query("Update RequestInfo set payerId = :payerId,providerId = :providerId where requestId = :requestId")
    int updateRequestInfo(@Param("payerId") String payerId, @Param("providerId") String providerId, @Param("requestId") String requestId);
}
