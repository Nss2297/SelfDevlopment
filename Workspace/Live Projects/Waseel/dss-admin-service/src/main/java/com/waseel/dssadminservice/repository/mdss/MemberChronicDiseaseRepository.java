package com.waseel.dssadminservice.repository.mdss;

import com.waseel.dssadminservice.model.memberchronic.MemberChronicDiseaseResponseModel;
import com.waseel.dssadminservice.persist.mdss.MemberChronicDzAssoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberChronicDiseaseRepository extends JpaRepository<MemberChronicDzAssoc, Integer> {

    @Query("SELECT new com.waseel.dssadminservice.model.memberchronic.MemberChronicDiseaseResponseModel(" +
        "cdi.chronicDiseasesId, cdi.chronicDiseasesName, mca.payerId) " +
        "FROM MemberChronicDzAssoc mca " +
        "JOIN mca.chronicDzInformation cdi " +
        "WHERE mca.memberId = :memberId")
    List<MemberChronicDiseaseResponseModel> findChronicDiseaseDetailsByMemberId(@Param("memberId") String memberId);

}