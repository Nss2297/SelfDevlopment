package com.waseel.authentication.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.authentication.model.portal.enity.AccountToCCHIAssociation;
import com.waseel.authentication.model.portal.enity.AccountToCCHIAssociationId;

public interface AccountToCCHIAssociationRepository
		extends CrudRepository<AccountToCCHIAssociation, AccountToCCHIAssociationId> {
	
	@Query("from AccountToCCHIAssociation where id.accountId.switchAccountId = :accountId")
	AccountToCCHIAssociation findByAccountId(@Param("accountId") BigDecimal accountId);

}
