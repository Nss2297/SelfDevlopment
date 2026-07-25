package com.waseel.policy.repository.businessrules;

import org.springframework.data.repository.CrudRepository;

import com.waseel.policy.persist.businessrules.MemberChronicDiseasesAssociation;

public interface MemberChronicDiseasesAssociationRepository
		extends CrudRepository<MemberChronicDiseasesAssociation, Long> {

}
