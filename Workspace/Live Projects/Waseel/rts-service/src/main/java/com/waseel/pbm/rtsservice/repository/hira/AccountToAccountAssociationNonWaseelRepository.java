package com.waseel.pbm.rtsservice.repository.hira;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.rtsservice.persist.hira.AccountToAccountAssociationNonWaseel;
import com.waseel.pbm.rtsservice.persist.hira.AccountToAccountAssociationNonWaseelId;

public interface AccountToAccountAssociationNonWaseelRepository
		extends CrudRepository<AccountToAccountAssociationNonWaseel, AccountToAccountAssociationNonWaseelId> {

	@Query("select atanw from AccountToAccountAssociationNonWaseel atanw ,PBMTransactionLog tl where tl.requestId =:requestId and atanw.id.code= tl.source")
	AccountToAccountAssociationNonWaseel findProviderNameByRequestId(@Param("requestId") BigDecimal requestId);

}
