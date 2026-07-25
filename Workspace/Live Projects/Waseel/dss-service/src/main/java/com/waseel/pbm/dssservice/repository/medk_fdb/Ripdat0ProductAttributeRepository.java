package com.waseel.pbm.dssservice.repository.medk_fdb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.dssservice.persist.medk_fdb.Ripdat0ProductAttribute;
import com.waseel.pbm.dssservice.persist.medk_fdb.Ripdat0ProductAttributeId;

public interface Ripdat0ProductAttributeRepository
		extends CrudRepository<Ripdat0ProductAttribute, Ripdat0ProductAttributeId> {

	@Query(value="select \"PRODUCT_ID\" from MEDK_FDB.\"RIPDAT0_PRODUCT_ATTRIBUTE\" \r\n" + 
			"where \"PRODUCT_ATTRIBUTE_VALUE\" like (:serviceCode) and (\"PRODUCT_ATTRIBUTE_CODE\" = 126 or \"PRODUCT_ATTRIBUTE_CODE\" = 130) \r\n" + 
			"order by (\"PRODUCT_ATTRIBUTE_SEQ\") desc \r\n" + 
			"FETCH NEXT 1 ROWS ONLY" , nativeQuery = true)
	Integer findByProductProductAttributeValue(@Param("serviceCode") String serviceCode);
}