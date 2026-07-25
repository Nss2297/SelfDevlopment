package com.waseel.pbm.rtsservice.repository.mdss;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.rtsservice.persist.mdss.RequestInfo;

@Repository
public interface RequestInfoRepository extends CrudRepository<RequestInfo, Long> {

	RequestInfo findByrequestId(String requestId);

}
