package com.waseel.pbm.idfvalidationservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.idfvalidationservice.persist.IDFQuantityLimitCheck;
import com.waseel.pbm.idfvalidationservice.persist.IDFQuantityLimitCheckId;

@Repository
public interface IDFQuantityLimitCheckRepository
		extends CrudRepository<IDFQuantityLimitCheck, IDFQuantityLimitCheckId> {

	List<IDFQuantityLimitCheck> findByIdServiceCodeAndProductPackageUnitEquals(String serviceCode, String packageUnit);
}
