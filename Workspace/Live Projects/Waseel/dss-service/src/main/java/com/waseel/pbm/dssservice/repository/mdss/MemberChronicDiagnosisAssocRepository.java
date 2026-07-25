package com.waseel.pbm.dssservice.repository.mdss;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.dssservice.persist.mdss.MemberChronicDiagnosisAssoc;
import com.waseel.pbm.dssservice.persist.mdss.MemberChronicDiagnosisAssocId;

@Repository
public interface MemberChronicDiagnosisAssocRepository
		extends CrudRepository<MemberChronicDiagnosisAssoc, MemberChronicDiagnosisAssocId> {

	@Query("select mcd.id.diagnosisCode from MemberChronicDiagnosisAssoc mcd , MemberChronicDzAssoc mc where mcd.id.memberChronicDzAssocId = mc.memberChronicDzAssocId and mc.memberId = :memberId and mc.chronicDzInformation.chronicDiseasesId in (:drugChronicDzIds) and mcd.isEnabled ='1' and mc.isEnabled='1'")
	List<String> findDiganosisByMemberIdAndChronicDz(List<Integer> drugChronicDzIds, String memberId);

}

