package com.waseel.pbm.fdbvalidationservice.repository.medk_fdb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.fdbvalidationservice.dto.FdbDrugInfo;
import com.waseel.pbm.fdbvalidationservice.persist.medk_fdb.Ripdpp0ProductMaster;


public interface Ripdpp0ProductMasterRepository extends CrudRepository<Ripdpp0ProductMaster, Integer> {

	@Query("select model.gcnSeqno from Ripdpp0ProductMaster model where model.productId = :productId")
	Integer findByProductId(@Param("productId") Integer productId);
	
	@Query("select new com.waseel.pbm.fdbvalidationservice.dto.FdbDrugInfo (model.gcnSeqno , model.productPackageUnit , model.productPackageSize)  from Ripdpp0ProductMaster model where model.productId = :productId")
	FdbDrugInfo findGcnSeqNoAndProductPackageUnitByProductId(@Param("productId") Integer productId);

	@Query("select distinct new com.waseel.pbm.fdbvalidationservice.dto.FdbDrugInfo (model.gcnSeqno , model.productPackageUnit,model.productPackageSize)  from Ripdpp0ProductMaster model where model.gcnSeqno = :gcnSeqno")
	FdbDrugInfo findGcnSeqNoAndProductPackageUnitByGcnSeqNo(@Param("gcnSeqno") Integer gcnSeqno);
}