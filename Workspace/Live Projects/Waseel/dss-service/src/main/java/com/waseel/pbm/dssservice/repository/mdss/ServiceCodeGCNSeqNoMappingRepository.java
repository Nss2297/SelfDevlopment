package com.waseel.pbm.dssservice.repository.mdss;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.ServiceCodeGCNSeqNoMapping;

@Repository
public interface ServiceCodeGCNSeqNoMappingRepository extends CrudRepository<ServiceCodeGCNSeqNoMapping, String> {

	ServiceCodeGCNSeqNoMapping findByserviceCode(String serviceCode);
}
