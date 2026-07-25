package com.waseel.pbm.dssservice.repository.medk_fdb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.dssservice.persist.medk_fdb.Ripdpp0ProductMaster;

public interface Ripdpp0ProductMasterRepository extends CrudRepository<Ripdpp0ProductMaster, Integer> {

	@Query("select model.gcnSeqno from Ripdpp0ProductMaster model where model.productId = :productId")
	Integer findByProductId(@Param("productId") Integer productId);

	@Query("select distinct model.gcnSeqno from Ripdpp0ProductMaster model where model.gcnSeqno = :gcnSeqno")
	Integer findGcnSeqNo(@Param("gcnSeqno") Integer gcnSeqno);

}